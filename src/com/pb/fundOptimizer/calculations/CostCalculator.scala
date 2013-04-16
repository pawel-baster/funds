package com.pb.fundOptimizer.calculations

import funds.ExtendedDate
import funds.funds.Fund
import java.util.Date
import collection.mutable
import com.pb.fundOptimizer.interfaces.CostCalculationEntry
import com.pb.fundOptimizer.ExperimentHistoryEntry
import collection.mutable.ArrayBuffer
import com.pb.fundOptimizer.exceptions.MissingQuoteException

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/22/12
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */

class CostCalculator(
                      val ma: MovingAverageCalculator
                      ) {
  def calculate(funds: Array[Fund], from: ExtendedDate, to: ExtendedDate, initialValue: Double, initialFund: Int, p: Params): mutable.LinkedHashMap[Int, CostCalculationEntry] = {
    require(initialFund < funds.length)
    require(initialFund >= 0)
    require(p.coefs.length == funds.length, "number of coeficients (" + p.coefs.length + ") is different than the number of funds (" + funds.length + ")")
    require(from.before(to))

    val avgs = ma.calculate(funds, from.addDays(1 - p.window), to, p.window) //dates are inconsistent and that's causing bugs
    require((to.getDayCount() - from.getDayCount()) <= avgs.values.size)
    //println("MA:")
    //avgs.foreach{case (key, row) => { print(key + " "); row.foreach(el => print(el + " ")); println }}

    var fund = initialFund
    //var value = initialValue

    val result = new mutable.LinkedHashMap[Int, CostCalculationEntry]

    var date = from
    while (!date.after(to)) {
      //println("Calculating value change for date: " + date)
      assert(avgs.get(date.getDayCount()).isDefined, "MA should be defined for date: " + date.getDayCount())
      val MARow = avgs.get(date.getDayCount()).get
      assert(p.coefs.length == MARow.length)
      val decisionVars = (p.coefs, MARow).zipped.map {
        case (a, b) => a * b
      }

      assert(funds(fund).getQuoteForDate(date.addDays(-1)).isDefined, "asserting that quote is defined for fund " + funds(fund).shortName + " for date: " + date.addDays(-1))

      //value = value * funds(fund).getQuoteForDate(date).get / funds(fund).getQuoteForDate(date.addDays(-1)).get
      result += date.getDayCount() -> new CostCalculationEntry(0, fund)
      //println("New value: " + value)
      var maxArg = 0;
      for (i <- 1 to (decisionVars.length - 1)) {
        if (decisionVars(maxArg) < decisionVars(i)) {
          maxArg = i
        }
      }

      if ((decisionVars(maxArg) - decisionVars(fund) > p.smoothFactor) && funds(maxArg).getQuoteForDate(date.addDays(-1)).isDefined) {
        fund = maxArg
      }
      result += date.getDayCount() -> new CostCalculationEntry(0, fund)
      date = date.addDays(1)

    /*  if (date.after(to)) {
        println("decisionVars (" + funds.map{ _.shortName }.mkString(", ") + "):" + decisionVars.mkString(", "))
      } */
    }

    calculateValue(funds, result, initialValue, initialFund)

    return result
  }

  def calculateValue(funds: Array[Fund], result: mutable.LinkedHashMap[Int, CostCalculationEntry], initialValue: Double, initialFundIndex: Int) = {
    //println("---")
    var value = initialValue
    var fund = initialFundIndex
    result.foreach {
      case (i: Int, entry: CostCalculationEntry) => {
        //println("I " + i + " " + value)
        val date = ExtendedDate.createFromDays(i)
        val previousDayQuoteOption = funds(fund).getQuoteForDate(date.addDays(-1))
        if (previousDayQuoteOption.isEmpty) {
          throw new MissingQuoteException("Missing quote for fund " + funds(fund).shortName + ", date: " + date.addDays(-1).format("dd-MM-yyyy") + ", dayCount: " + date.addDays(-1).getDayCount())
        }
        value = funds(fund).calculateDailyManagingFee(value * funds(fund).getQuoteForDate(date).get / previousDayQuoteOption.get)
        entry.value = value
        if (entry.fundIdx != fund) {
          //print("C " + fund + " -> " + entry.fundIdx + " ")
          value = funds(fund).calculateManipulationFee(value, funds(entry.fundIdx))
          fund = entry.fundIdx
          //println("C " + value)
        }
      }
    }
  }

  def updateExperimentHistoryValue(funds: Array[Fund], initialValue: Double, initialFundIndex: Int, experimentHistory: ArrayBuffer[ExperimentHistoryEntry]) {
    require(initialFundIndex < funds.length)
    require(experimentHistory.size > 0)
    require(initialValue > 0)

    val ccEntries = translateToCcEntries(experimentHistory)
    calculateValue(funds, ccEntries, initialValue, initialFundIndex)

    experimentHistory.foreach{
      entry => {
        if (ccEntries.get(entry.date.getDayCount()).isDefined) {
          entry.value = Option(ccEntries.get(entry.date.getDayCount()).get.value)
        } else {
          throw new RuntimeException("Missing entry for dayCount: " + entry.date.getDayCount())
        }
      }
    }
  }

  def translateToCcEntries(experimentHistory: ArrayBuffer[ExperimentHistoryEntry]): mutable.LinkedHashMap[Int, CostCalculationEntry] = {
    var result = new mutable.LinkedHashMap[Int, CostCalculationEntry];

    (experimentHistory zip experimentHistory.tail).foreach {
      case (entry1, entry2) => {
        for (dayCount <- entry1.date.getDayCount() until entry2.date.getDayCount()) {
          result += dayCount -> new CostCalculationEntry(0, entry1.fundIndex)
        }
      }
    }

    result += experimentHistory.last.date.getDayCount() -> new CostCalculationEntry(0, experimentHistory.last.fundIndex)

    return result
  }
}

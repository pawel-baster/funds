package funds

import funds.Fund
import java.util.Date
import collection.mutable
import com.pb.fundOptimizer.interfaces.CostCalculationEntry

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
    require(p.coefs.length == funds.length)
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
      //println("decisionVars:" + decisionVars.mkString(", "))

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

      if ((decisionVars(maxArg) - decisionVars(fund) > p.smoothFactor) && funds(maxArg).getQuoteForDate(date).isDefined) {
        fund = maxArg
      }
      result += date.getDayCount() -> new CostCalculationEntry(0, fund)
      date = date.addDays(1)
    }

    calculateValue(funds, result, initialValue, initialFund)

    return result
  }

  def calculateValue(funds: Array[Fund], result: mutable.LinkedHashMap[Int, CostCalculationEntry], initialValue: Double, initialFund: Int) = {
    //println("---")
    var value = initialValue
    var fund = initialFund
    result.foreach {
      case(i: Int, entry : CostCalculationEntry) => {
        //println("I " + i + " " + value)
        val date = ExtendedDate.createFromDays(i)
        value = value * funds(fund).getQuoteForDate(date).get / funds(fund).getQuoteForDate(date.addDays(-1)).get
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
}

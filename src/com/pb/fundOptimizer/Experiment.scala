package com.pb.fundOptimizer

import _root_.funds.{ExtendedDate}
import _root_.funds.funds.Fund
import calculations.Params
import interfaces.{FundOptimizerResultPublishers, FundOptimizer}
import logging.logger
import java.util.Date
import collection.mutable
import collection.mutable.ArrayBuffer

class ExperimentTraceEntry(
  val bestValue: Double,
  val value: Double,
  val fundName: String,
  val params: Params
                            ) {
  /*def toString {
    return
  }*/
}

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
class Experiment(
                  val name: String,
                  val funds: Array[Fund],
                  val from: ExtendedDate,
                  val to: ExtendedDate,
                  val initialParams: Params,
                  val initialFund: Int,
                  val initialValue: Double
                  ) extends Serializable {
  var bestValues: ArrayBuffer[(ExtendedDate, Double)] = ArrayBuffer((new ExtendedDate(), 0))
  var valueAndFundHistory = Map[Int, (String, Double)]()
  var bestParams = initialParams

  def optimize(fundOptimizer: FundOptimizer, resultSerializer: FundOptimizerResultPublishers, initialCount: Int = 100) {

    var bestValue: Double = bestValues.last._2
    logger.info("before optimizing. Recorded best value: " + bestValue + ", Iteration count: " + initialCount + ", Params: " + bestParams)

    val result = fundOptimizer.optimize(funds, from, to, initialFund, bestParams, initialValue, initialCount)

    bestValue = result.value
    bestParams = result.bestParams
    if (result.trace != null) {
      // save new best value record if params have changed
      resultSerializer.export(funds, result, name)
      val valueRecord = (new ExtendedDate(), bestValue)
      bestValues += valueRecord
    }
    logger.info("trying to retrieve fund from id: " + result.trace.last._2)
    val fund = funds(result.trace.last._2.fundIdx)
    val valueAndFundHistoryEntry = (new ExtendedDate().getDayCount() -> (fund.shortName, bestValue))
    valueAndFundHistory += valueAndFundHistoryEntry

    logger.info("after optimizing. Best value: " + bestValue + ", Iteration count: " + initialCount + ", Params: " + bestParams)

    val bestValuesHistory = bestValues.map{
      case(date, value) => date.format("yyy-MM-dd H:mm:ss") + " " + "%.2f" format value
    } mkString("\n")
    logger.info("bestValue history: " + bestValuesHistory)

    val fundsAndValues = valueAndFundHistory.map{
      case (dayCount, entry) => ExtendedDate.createFromDays(dayCount).format("yyy-MM-dd") + entry._1 + " " + entry._2
    } mkString ("\n")
    logger.info("fund and value" + fundsAndValues)
  }
}

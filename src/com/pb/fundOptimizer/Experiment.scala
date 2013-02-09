package com.pb.fundOptimizer

import _root_.funds.{ExtendedDate}
import _root_.funds.funds.Fund
import calculations.Params
import interfaces.{FundOptimizerResultPublishers, FundOptimizer}
import logging.logger
import java.util.Date
import collection.mutable

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
  val bestValues: mutable.LinkedHashMap[ExtendedDate, Double] = mutable.LinkedHashMap(new ExtendedDate() -> 0)
  var bestParams = initialParams

  def optimize(fundOptimizer: FundOptimizer, resultSerializer: FundOptimizerResultPublishers, initialCount: Int = 1000) {
    // @todo optimize
    var bestValue: Double = bestValues.toSeq.sortBy(_._1.getTime).last._2
    logger.info("before optimizing. Best value: " + bestValue + ", Iteration count: " + initialCount + ", Params: " + bestParams)
    val result = fundOptimizer.optimize(funds, from, to, initialFund, bestParams, bestValue, initialValue, initialCount)
    bestValue = result.value
    bestParams = result.bestParams
    if (result.trace != null) {
      resultSerializer.export(funds, result, name)
      bestValues += (new ExtendedDate() -> bestValue)
    }
    logger.info("after optimizing. Best value: " + bestValue + ", Iteration count: " + initialCount + ", Params: " + bestParams)
    val bestValuesHistory = bestValues.toSeq.sortBy(_._1.getTime).map{case(date, value) => "\n" + date.format("yyy-MM-dd") + " " + "%.2f" format value}.mkString
    logger.info("bestValue history: " + bestValuesHistory)
  }
}

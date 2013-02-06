package com.pb.fundOptimizer

import _root_.funds.{ExtendedDate}
import _root_.funds.funds.Fund
import calculations.Params
import interfaces.{FundOptimizerResultExporter, FundOptimizer}
import logging.logger

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
class Experiment(
                  val funds: Array[Fund],
                  val from: ExtendedDate,
                  val to: ExtendedDate,
                  val initialParams: Params,
                  val initialFund: Int,
                  val initialValue: Double
                  ) extends Serializable {
  var bestValue = Double.NegativeInfinity
  var bestParams = initialParams

  def optimize(fundOptimizer: FundOptimizer, resultSerializer: FundOptimizerResultExporter, initialCount: Int = 1000) {
    logger.info("before optimizing. Best value: " + bestValue + ", Iteration count: " + initialCount + ", Params: " + bestParams)
    val result = fundOptimizer.optimize(funds, from, to, initialFund, bestParams, bestValue, initialValue, initialCount)
    bestValue = result.value
    bestParams = result.bestParams
    if (result.trace != null) {
      resultSerializer.export(funds, result)
    }
    logger.info("after optimizing. Best value: " + bestValue + ", Iteration count: " + initialCount + ", Params: " + bestParams)
  }
}

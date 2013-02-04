package com.pb.fundOptimizer

import _root_.funds.{Params, ExtendedDate}
import _root_.funds.funds.Fund
import interfaces.FundOptimizer
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
                  ) extends Serializable  {
  var bestValue = Double.NegativeInfinity
  var bestParams = initialParams

  def optimize(fundOptimizer: FundOptimizer, initialCount: Int = 1000) {
    logger.info("before optimizing. Best value: " + bestValue + ", Iteration count: " + initialCount + ", Params: " + bestParams)
    val result = fundOptimizer.optimize(funds, from, to, initialFund, bestParams, bestValue, initialValue, initialCount)
    bestValue = result.value
    bestParams = result.bestParams
    logger.info("after optimizing. Best value: " + bestValue + ", Iteration count: " + initialCount + ", Params: " + bestParams)
  }
}

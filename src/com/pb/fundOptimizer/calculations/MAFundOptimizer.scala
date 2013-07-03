package com.pb.fundOptimizer.calculations

import _root_.funds.funds.Fund
import funds._
import java.util.Date
import collection.parallel.mutable
import collection.mutable
import collection.parallel.mutable
import scala.{Array, collection}
import collection.mutable.ArrayBuffer
import com.pb.fundOptimizer.interfaces.{FundOptimizerResult, CostCalculationEntry, FundOptimizer}
import java.util
import com.pb.fundOptimizer.logging.logger
import com.pb.fundOptimizer.ExperimentHistoryEntry
import com.pb.fundOptimizer.exceptions.BadIterationException

/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 30.09.12
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */

class MAFundOptimizer(
                       val costCalculator: CostCalculator
                       ) extends FundOptimizer {

  def pickNextParams(currentParams: Params, maxWindow: Int, deviation: Double, i: Int, funds: Array[Fund]): Params = {
    return currentParams.createRandomFromNormal(maxWindow, deviation)
  }

  def optimize(funds: Array[Fund], from: ExtendedDate, to: ExtendedDate, initialFund: Int, initialBestParams: Params, initialValue: Double, count: Int, deviation: Double = 1.0): FundOptimizerResult = {
    //try
    var bestFunds = costCalculator.calculate(funds, from, to, initialValue, initialFund, initialBestParams)
    var bestValue = costFunction(bestFunds, initialBestParams, to)
    var bestParams = initialBestParams
    val maxWindow = to.getDayCount - from.getDayCount - 1
    var i = 0
    var afterLastImprove = 0
    while (afterLastImprove < count) {
      try {
        val params = pickNextParams(bestParams, maxWindow, deviation, i, funds)
        val result = costCalculator.calculate(funds, from, to, initialValue, initialFund, params)
        val value = costFunction(result, params, to)
        if (value > bestValue) {
          println("#" + i + " better params: " + value + " > " + bestValue)
          bestParams = params
          bestValue = value
          bestFunds = result
          afterLastImprove = 0
        }
      } catch {
        case e: BadIterationException => {
          logger.info("Caught an exception: " + e.getMessage())
        }
		  }
      if (i % 100 == 0) {
        logger.info("#" + i)
      }
      i += 1
      afterLastImprove += 1
    }

    return new FundOptimizerResult(bestParams, bestValue, bestFunds)
  }

  def costFunction(result: collection.mutable.LinkedHashMap[Int, CostCalculationEntry], params: Params, to: ExtendedDate): Double = {
    return result.get(to.getDayCount() - 1).get.value - 0.001 * params.coefs.map(c => c * c).sum
  }

  def updateExperimentHistoryValue(funds: Array[Fund], initialValue: Double, initialFundIndex: Int, experimentHistory: ArrayBuffer[ExperimentHistoryEntry]) {
    costCalculator.updateExperimentHistoryValue(funds, initialValue, initialFundIndex, experimentHistory)
  }
}
package com.pb.fundOptimizer.calculations

import _root_.funds.funds.Fund
import funds._
import java.util.Date
import collection.parallel.mutable
import collection.mutable
import collection.parallel.mutable
import scala.{Array, collection}
import com.pb.fundOptimizer.interfaces.{FundOptimizerResult, CostCalculationEntry, FundOptimizer}
import java.util
import com.pb.fundOptimizer.logging.logger
import com.pb.fundOptimizer.ExperimentHistoryEntry

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
  def optimize(funds: Array[Fund], from: ExtendedDate, to: ExtendedDate, initialFund: Int, initialBestParams: Params, initialValue: Double, count: Int): FundOptimizerResult = {

    var bestFunds = costCalculator.calculate(funds, from, to, initialValue, initialFund, initialBestParams)
    var bestValue = costFunction(bestFunds, initialBestParams, to)
    var bestParams = initialBestParams
    for (i <- 1 to count) {
      //println("maxWindow = " + to.getDayCount() + " - " + from.getDayCount() + " - 1")
      val params = bestParams.createRandomFromNormal(to.getDayCount() - from.getDayCount() - 1)
      val result = costCalculator.calculate(funds, from, to, initialValue, initialFund, params)
      //val value = result.get(to.getDayCount() - 1).get.value - 0.001 * params.coefs.map(c => c * c).sum
      val value = costFunction(result, params, to)
      //println(">> result = " + value + ", best: " + bestValue)
      if (value > bestValue) {
        println("#" + i + " better params: " + value + " > " + bestValue)
        bestParams = params
        bestValue = value
        bestFunds = result
      }
    }

    return new FundOptimizerResult(bestParams, bestValue, bestFunds)
  }

  def costFunction(result: collection.mutable.LinkedHashMap[Int, CostCalculationEntry], params: Params, to: ExtendedDate): Double = {
    return result.get(to.getDayCount() - 1).get.value - 0.001 * params.coefs.map(c => c * c).sum
  }

  def calculateValue(funds: Array[Fund], lastHistoryEntry: ExperimentHistoryEntry, newFundIndex: Int): Double = {
    return costCalculator.calculateValue(funds, lastHistoryEntry, newFundIndex)
  }
}
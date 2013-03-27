package com.pb.fundOptimizer.interfaces

import funds.{ExtendedDate}
import funds.funds.Fund
import com.pb.fundOptimizer.calculations.Params
import com.pb.fundOptimizer.ExperimentHistoryEntry
import collection.mutable.ArrayBuffer

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 15:20
 * To change this template use File | Settings | File Templates.
 */


class CostCalculationEntry(
                            var value: Double,
                            val fundIdx: Int
                            ) extends Serializable {
}

class FundOptimizerResult(
                           val bestParams: Params,
                           val value: Double,
                           val trace: collection.mutable.LinkedHashMap[Int, CostCalculationEntry]
                           ) {
}

abstract class FundOptimizer {
  def optimize(funds: Array[Fund], from: ExtendedDate, to: ExtendedDate, initialFund: Int, initialBestParams: Params, initialValue: Double, count: Int, deviation: Double = 1.0): FundOptimizerResult
  def updateExperimentHistoryValue(funds: Array[Fund], initialValue: Double, initialFundIndex: Int, experimentHistory: ArrayBuffer[ExperimentHistoryEntry])
}


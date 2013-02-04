package com.pb.fundOptimizer.interfaces

import funds.{Params, ExtendedDate, CostCalculator}
import funds.funds.Fund

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
                            ) {
}

class FundOptimizerResult(
                           val bestParams: Params,
                           val value: Double,
                           val trace: collection.mutable.LinkedHashMap[Int, CostCalculationEntry]
                           ) {
}

abstract class FundOptimizer {
  def optimize(funds: Array[Fund], from: ExtendedDate, to: ExtendedDate, initialParams: Params, initialFund: Int, initialValue: Double, count: Int): FundOptimizerResult
}


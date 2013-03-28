package com.pb.fundOptimizer.calculations

import com.pb.fundOptimizer.interfaces.FundOptimizer

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 28.03.13
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
class AlternatingMAFundOptimizer (
                                   override val costCalculator: CostCalculator
                                  ) extends MAFundOptimizer(costCalculator) {

  override def pickNextParams(currentParams: Params, maxWindow: Int, deviation: Double, i: Int): Params = {
    if (i  % 2 == 0)
      return currentParams.createRandomFromNormal(maxWindow, deviation)
    else
      return currentParams.createRandomFromNormalModifyOneDimension
  }


}

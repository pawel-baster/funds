package com.pb.fundOptimizer.calculations

import com.pb.fundOptimizer.interfaces.FundOptimizer
import funds.funds.Fund
import util.Random

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

  override def pickNextParams(currentParams: Params, maxWindow: Int, deviation: Double, i: Int, funds: Array[Fund]): Params = {
    val decision = Random.nextInt(100)
    if (decision < 50)
      return currentParams.createRandomFromNormalModifyOneDimension
    else if (decision < 65)
      return currentParams.createRandomFromNormal(maxWindow, deviation)
    else if (decision < 80)
      return currentParams.createRandomFromNormalModifyWindow(maxWindow)
    else if (decision < 98)
      return currentParams.createRandomZeroRandomDimension(deviation)
    else
      return currentParams.readFromBestExperimentParams(funds)
  }
}

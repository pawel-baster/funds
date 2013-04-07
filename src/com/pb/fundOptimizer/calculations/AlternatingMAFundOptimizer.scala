package com.pb.fundOptimizer.calculations

import com.pb.fundOptimizer.interfaces.FundOptimizer
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

  override def pickNextParams(currentParams: Params, maxWindow: Int, deviation: Double, i: Int): Params = {
    val decision = Random.nextInt(10)
    if (decision < 5)
      return currentParams.createRandomFromNormalModifyOneDimension
    else if (decision < 7)
      return currentParams.createRandomFromNormal(maxWindow, deviation)
    else if (decision == 8)
      return currentParams.createRandomFromNormalModifyWindow(maxWindow)
    else // if (decision == 9)
      return currentParams.createRandomZeroRandomDimension(deviation)
  }
}

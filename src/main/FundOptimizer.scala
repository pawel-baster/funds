package funds

import funds.Fund
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 30.09.12
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
class FundOptimizer(
  val costCalculator: CostCalculator,
  val funds: Array[Fund],
  val from: ExtendedDate,
  val to: ExtendedDate,
  val initialParams: Params,
  val initialFund: Int,
  val initialValue: Double
) {
  def optimize(count: Int): Double = {
    var bestResult = Double.NegativeInfinity
    var bestParams = initialParams
    for (i <- 1 to count) {
      val params = bestParams.createRandomFromNormal(((to.getTime - from.getTime)/24.0/3600/1000).toInt - 1)
      val result = costCalculator.calculate(funds, from, to, initialValue, initialFund, params)
      println(">> result = " + result + ", best: " + bestResult)
      if (result > bestResult) {
        bestParams = params
        bestResult = result
        println("-- new best params")
      }
    }
    return bestResult
  }
}

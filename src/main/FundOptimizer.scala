package funds

import funds.Fund
import java.util.Date
import collection.parallel.mutable
import collection.mutable
import collection.parallel.mutable
import scala.collection

/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 30.09.12
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
class FundOptimizerResult(
  val bestParams: Params,
  val value: Double,
  val trace: collection.mutable.LinkedHashMap[Int, CostCalculationEntry]
) {
}

class FundOptimizer(
  val costCalculator: CostCalculator,
  val funds: Array[Fund],
  val from: ExtendedDate,
  val to: ExtendedDate,
  val initialParams: Params,
  val initialFund: Int,
  val initialValue: Double
) {
  def optimize(count: Int): FundOptimizerResult = {
    var bestValue = Double.NegativeInfinity
    var bestParams = initialParams
    var bestFunds : collection.mutable.LinkedHashMap[Int, CostCalculationEntry] = null
    for (i <- 1 to count) {
      //println("maxWindow = " + to.getDayCount() + " - " + from.getDayCount() + " - 1")
      val params = bestParams.createRandomFromNormal(to.getDayCount() - from.getDayCount() - 1)
      val result = costCalculator.calculate(funds, from, to, initialValue, initialFund, params)
      val value = result.get(to.getDayCount()).get.value
      //println(">> result = " + value + ", best: " + bestValue)
      if (value > bestValue) {
        bestParams = params
        bestValue = value
        bestFunds = result
      }
    }
    return new FundOptimizerResult(bestParams, bestValue, bestFunds)
  }
}

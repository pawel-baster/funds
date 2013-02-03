package funds

import currencies.CurrencyDKK
import funds.{FixedDepositFund, Fund}
import java.util.Date
import collection.parallel.mutable
import collection.mutable
import collection.parallel.mutable
import scala.{Array, collection}

/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 30.09.12
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
class MAFundOptimizerResult(
                           val bestParams: Params,
                           val value: Double,
                           val trace: collection.mutable.LinkedHashMap[Int, CostCalculationEntry]
                           ) {
}

class MAFundOptimizer(
                     val costCalculator: CostCalculator,
                     var funds: Array[Fund],
                     var from: ExtendedDate,
                     var to: ExtendedDate,
                     var initialParams: Params,
                     var initialFund: Int,
                     var initialValue: Double
                     ) {
  def optimize(count: Int): MAFundOptimizerResult = {
    var bestValue = Double.NegativeInfinity
    var bestParams = initialParams
    var bestFunds: collection.mutable.LinkedHashMap[Int, CostCalculationEntry] = null
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
    return new MAFundOptimizerResult(bestParams, bestValue, bestFunds)
  }
}

/*object MAFundOptimizer {
  def main(args: Array[String]): Unit = {
    val funds : Array[Fund] = Array(
      new FixedDepositFund(new CurrencyDKK, "test1", 0.01),
      new FixedDepositFund(new CurrencyDKK, "test1", 0.02)
    )
    val to = new ExtendedDate

    val experiment = new MAFundOptimizer(
      new CostCalculator(new MovingAverage),
      funds,
      ExtendedDate.createFromString("2000-01-01", "yyy-mm-dd"),
      to,
      Params.createRandom(funds.length),
      0,
      1
    )

    val result = experiment.optimize(100)
    println("bestValue = " + result.value)
  }
}*/

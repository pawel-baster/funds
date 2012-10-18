package funds

import java.util.Date
import collection.immutable.Range.Double

/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 30.09.12
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
class FundOptimizer {
  def optimize(funds: Array[Fund], from: Date, to: Date) = {
    iteration(funds, from, to)
  }

  def iteration(funds: Array[Fund], from: Date, to: Date) = {
    val params = Array[Double](3)
    val result = calculateCost(funds, from, to)
  }

  def calculateCost(funds: Array[Fund], from: Date, to: Date, params: Array[Double] = null): Double = {
    //val active = 1
    //val value = 1
    // todo statics in scala?
    //val params = new Params(2, (0.0, 0.0))
    /*
      foreach row in ma(funds, window, from, to)
        value = value * funds[date] / funds[date - 1]
        decisionVars = max(row * params)
        new = argmax(row * params)
        if new != active:
          value = value * fee(funds[active], funds[new])
          active = new

     */
    return 3.0
  }
}

package funds

import funds.Fund
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/22/12
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
class CostCalculator(
  val ma: MovingAverage
) {
  def calculate(funds: Array[Fund], from: ExtendedDate, to: ExtendedDate, initialValue: Double, initialFund: Int, p: Params): Double = {
    require(initialFund < funds.length)
    require(initialFund >= 0)
    require(p.coefs.length == funds.length)

    val avgs = ma.calculate(funds, from, to, p.window)
    require((to.getDayCount() - from.getDayCount()) <= avgs.length)
    println("MA:")

    var fund = initialFund
    var value = initialValue



    return value
  }
}

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
  def calculate(funds: Array[Fund], from: Date, to: Date, initialValue: Double, initialFund: Int, p: Params) = {
    assert(initialFund < funds.length)
    assert(initialFund >= 0)
    assert(p.coefs.length == funds.length)

    val avgs = ma.calculate(funds, from, to, p.window)
    value = initialValue
    fund = initialFund
    for (row <- (0 to avgs.length)) {
      decisionVars = p.coefs * avgs(row)
    }
  }
}

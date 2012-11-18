import funds.funds.Fund
import java.util.Date
import funds.MovingAverage

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
  def calculate(funds: Array[Fund], from: Date, to: Date, initialValue: Double, initialFund: Int, p: Params): Double = {
    require(initialFund < funds.length)
    require(initialFund >= 0)
    require(p.coefs.length == funds.length)

    val avgs = ma.calculate(funds, from, to, p.window)
    println("MA:")
    avgs.foreach(row => {row.foreach(el => print(el + " ")); println})
    var value = initialValue
    var fund = initialFund
    for (row <- (0 to avgs.length - 1)) {
      // calculate product
      assert(p.coefs.length == avgs(row).length)
      val decisionVars = (p.coefs, avgs(row)).zipped.map{ case (a,b) => a*b }
      println("decisionVars " + row)
      decisionVars.foreach(row => {println(row)})

      var maxarg = 0;
      for (i <- 1 to decisionVars.length - 1) {
        if (decisionVars(maxarg) < decisionVars(i)) {
          maxarg = i
        }
      }

      // update value:
      val curDate = new Date()
      curDate.setTime(to.getTime + (row + 1 - avgs.length) * 24 * 3600 * 1000)
      val lastDate = new Date()
      lastDate.setTime(curDate.getTime - 24 * 3600 * 1000)
      val newValue = value * funds(fund).getQuoteForDate(curDate).get / funds(fund).getQuoteForDate(lastDate).get
      println("Updating value: " + value + " to " + newValue)
      println("because share value changed from " + funds(fund).getQuoteForDate(lastDate).get + " to " + funds(fund).getQuoteForDate(curDate).get)
      value = newValue

      if (maxarg != fund && (decisionVars(maxarg) - decisionVars(fund) > p.smoothFactor)) {
        println("changing fund: " + fund + " to " + maxarg)
        value = funds(fund).calculateManipulationFee(value, funds(maxarg))
        fund = maxarg
      } else {
        println("(Do not change the fund " + fund + ")")
      }
    }
    return value
  }
}

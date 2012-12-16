package funds

import funds.Fund
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/10/12
 * Time: 7:45 AM
 * To change this template use File | Settings | File Templates.
 */
class MovingAverage {

  def calculate(funds: Array[Fund], from: ExtendedDate, to: ExtendedDate, window: Int): Array[Array[Double]] = {

    val fromDate = from.getDayCount()
    val toDate = to.getDayCount()

    require (window > 0)
    require (window < (toDate - fromDate), "require that " + window + " < " + toDate + " - " + fromDate)

    val ma = Array.ofDim[Double](toDate - fromDate - window + 2, funds.length)

    // initial row
    val date = from
    for (day <- (0 to window -1)) {
      for (fundind <- (0 to funds.length - 1)){
        ma(0)(fundind) += funds(fundind).getQuoteForDate(date).get
      }
      date.addDays(1)
    }

    // iterate: add and subtract
    for (day <- (1 to toDate - fromDate - window + 1)) {
      val oldDate = ExtendedDate.createFromDays(date.getDayCount() - window)
      for (fundind <- (0 to funds.length - 1)){
        ma(day)(fundind) = ma(day-1)(fundind) + funds(fundind).getQuoteForDate(date).get - funds(fundind).getQuoteForDate(oldDate).get
      }
      date.addDays(1)
    }
    // divide by window
    return ma.map(row => {row.map(value => value/window)})
  }
}

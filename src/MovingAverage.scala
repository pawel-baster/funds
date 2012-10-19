import collection.immutable.Range.Int
import java.util
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/10/12
 * Time: 7:45 AM
 * To change this template use File | Settings | File Templates.
 */
class MovingAverage {

  /**
   * static?
   * 100,100,100,100,100 -> 200, 200, 200, 200
   */
  def calculate(funds: Array[Fund], from: Date, to: Date, window: Int): Array[Array[Double]] = { //{funds: Array[Fund], from: Date, to: Date, window: Int) = {

    val fromDate = math.floor(from.getTime()/24.0/3600/1000).toInt
    val toDate = math.floor(to.getTime()/24.0/3600/1000).toInt

    assert (window > 0)
    assert (window < (toDate - fromDate))

    val ma = Array.ofDim[Double](toDate - fromDate - window + 1, funds.length)

    // initial row
    val date = new Date(fromDate * 24 * 3600 * 1000)
    for (day <- (0 to window -1)) {
      for (fundind <- (0 to funds.length - 1)){
        ma(0)(fundind) += funds(fundind).getQuoteForDate(date)
      }
      date.setTime(date.getTime() + 24 * 3600 * 1000)
    }

    // iterate: add and subtract
    for (day <- (1 to toDate - fromDate - window)) {
      val date = new Date()
      date.setTime((fromDate + window + day) * 24 * 3600 * 1000)
      val oldDate = new Date()
      oldDate.setTime((fromDate + day) * 24 * 3600 * 1000)
      for (fundind <- (0 to funds.length - 1)){
        ma(day)(fundind) += ma(day-1)(fundind) + funds(fundind).getQuoteForDate(date) - funds(fundind).getQuoteForDate(oldDate)
      }
    }

    // divide by window
    return ma.map(row => {row.map(value => value/window)})
  }
}

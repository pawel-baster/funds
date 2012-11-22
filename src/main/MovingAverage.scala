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

  def calculate(funds: Array[Fund], from: Date, to: Date, window: Int): Array[Array[Double]] = {

    val fromDate = math.floor(from.getTime()/24.0/3600/1000).toInt
    val toDate = math.floor(to.getTime()/24.0/3600/1000).toInt

    require (window > 0)
    require (window < (toDate - fromDate), "require that " + window + " < " + toDate + " - " + fromDate)

    val ma = Array.ofDim[Double](toDate - fromDate - window + 2, funds.length)

    // initial row
    val date = new Date(fromDate * 24 * 3600 * 1000)
    for (day <- (0 to window -1)) {
      for (fundind <- (0 to funds.length - 1)){
        ma(0)(fundind) += funds(fundind).getQuoteForDate(date).get
      }
      date.setTime(date.getTime() + 24 * 3600 * 1000)
    }

    // iterate: add and subtract
    for (day <- (1 to toDate - fromDate - window + 1)) {
      val oldDate = new Date()
      oldDate.setTime(date.getTime - window * 24 * 3600 * 1000)
      for (fundind <- (0 to funds.length - 1)){
        ma(day)(fundind) = ma(day-1)(fundind) + funds(fundind).getQuoteForDate(date).get - funds(fundind).getQuoteForDate(oldDate).get
        //if (ma.length < 5) println("day=index: " + day + ", value: " + ma(day)(fundind) + " = " + ma(day-1)(fundind) + " + " + funds(fundind).getQuoteForDate(date).get + " - " + funds(fundind).getQuoteForDate(oldDate).get)
      }
      date.setTime(date.getTime() + 24 * 3600 * 1000)
    }
    //println("intermediate result:")
    //if (ma.length < 5) ma.foreach(elem => println(elem(0) + " "))
    // divide by window
    return ma.map(row => {row.map(value => value/window)})
  }
}
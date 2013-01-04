package funds

import funds.Fund
import java.util.Date
import collection.immutable.HashMap

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/10/12
 * Time: 7:45 AM
 * To change this template use File | Settings | File Templates.
 */
class MovingAverage {

  def calculate(funds: Array[Fund], from: ExtendedDate, to: ExtendedDate, window: Int): scala.collection.Map[Int, Array[Double]] = {
//println("start")
    val fromDate = from.getDayCount()
    val toDate = to.getDayCount()

    require (window > 0)
    require (window <= (toDate - fromDate + 1), "require that " + window + " <= 1 + " + toDate + " - " + fromDate)

    val ma = scala.collection.mutable.LinkedHashMap[Int, Array[Double]]()

    // initial row
    var date = from
    val initialRow = new Array[Double](funds.length)
    for (day <- (0 to window -1)) {
      for (fundind <- (0 to funds.length - 1)){
        //println("adding " + funds(fundind).getQuoteForDate(date).get + " to " + initialRow(fundind))
        initialRow(fundind) += funds(fundind).getQuoteForDate(date).get
      }
      date = date.addDays(1)
    }

    var oldValues = initialRow
    var currentDate = date;
    var oldDate = from
    //println("Adding " + date.getDayCount() + " -> " + initialRow(0))
    ma += date.getDayCount() -> initialRow

    while (!date.after(to)) {
      //println(date.toString())
      val values = Array[Double](funds.length)
      //println("values: " + values.length + )
      for (fundind <- (0 to funds.length - 1)){
        values(fundind) = oldValues(fundind) + funds(fundind).getQuoteForDate(date).get - funds(fundind).getQuoteForDate(oldDate).get
      }
      values.foreach(value => print(" " + value))
      //println
      oldDate = date
      date = date.addDays(1)
      ma += date.getDayCount() -> values
      //println("Adding " + date.getDayCount() + " -> " + values(0))
      oldValues = values

    }

    // divide by window
    return ma.mapValues(row => {row.map(value => value/window)})
  }
}

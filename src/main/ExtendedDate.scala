package funds

import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 15.12.12
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
class ExtendedDate extends Date {
  def addDays(count: Int) = {
    //val newDate = new ExtendedDate()
    setTime(getTime + count * 24L * 3600 * 1000)
    //return newDate
  }
  def getDayCount(): Int = {
    return math.floor(getTime / 24.0 / 3600 / 1000).toInt
  }
}

object ExtendedDate {
  def createFromDays(days: Int): ExtendedDate = {
    val newDate = new ExtendedDate()
    newDate.setTime(days * 24L * 3600 * 1000)
    return newDate
  }
}
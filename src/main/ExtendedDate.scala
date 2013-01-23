package funds

import java.util.Date
import java.text.SimpleDateFormat

/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 15.12.12
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
class ExtendedDate extends Date {
  def addDays(count: Int) : ExtendedDate = {
    val newDate = new ExtendedDate()
    newDate.setTime(getTime + count * 24L * 3600 * 1000)
    return newDate
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
  def createFromString(str: String): ExtendedDate = {
    val date = new SimpleDateFormat("dd-MM-yyy").parse(str)
    val newDate = new ExtendedDate
    newDate.setTime(date.getTime)
    return newDate
  }
}
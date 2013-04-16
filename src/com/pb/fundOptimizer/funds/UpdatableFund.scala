package funds.funds

import funds.currencies.{Currency, CurrencyDKK}
import funds.downloaders.Downloader
import funds.ExtendedDate
import collection.immutable.HashMap
import java.util.Date
import java.text.SimpleDateFormat
import com.pb.fundOptimizer.exceptions.ZeroQuoteException

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/29/12
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class UpdatableFund(
                              override val shortName: String,
                              override val currency: Currency,
                              val downloader: Downloader
                              ) extends Fund(currency, shortName) {
  var quotes = new HashMap[Int, Double]
  var dateMin: Option[ExtendedDate] = None
  var dateMax: Option[ExtendedDate] = None
  var lastUpdate = ExtendedDate.createFromString("1970-01-01", "dd-MM-yyy")
  var updateInterval = 24
  var needsSaving = false

  def update()

  def getNeedsSaving(): Boolean = needsSaving
  def setNeedsSaving(value: Boolean) {
    needsSaving = value
  }

  def getQuoteForDate(date: ExtendedDate): Option[Double] = {

    if (dateMax.isEmpty || (date after dateMax.get)) {
      if (new Date().getTime - lastUpdate.getTime > updateInterval * 3600 * 1000) {
        update()
      }
    }

    if (dateMin.isEmpty || dateMax.isEmpty || (date before dateMin.get)) {
      return None
    }

    val dayCount = date.getDayCount()

    if (!quotes.contains(dayCount)) {
      val dayBefore = date.addDays(-1)
      return getQuoteForDate(dayBefore)
    }

    val result = quotes.get(dayCount)
    if (result.getOrElse(1.0) > 0.0) {
      return result
    } else {
      throw new ZeroQuoteException("0 value for fund: " + shortName + ", day: " + date.toString)
    }
  }

  protected def addQuote(date: ExtendedDate, value: Double) {
    val dayCount = date.getDayCount()
    quotes += (dayCount -> value)
    if (dateMax.isEmpty || (date after dateMax.get)) {
      dateMax = Option(date)
    }

    if (dateMin.isEmpty || (date before dateMin.get)) {
      dateMin = Option(date)
    }
  }
}

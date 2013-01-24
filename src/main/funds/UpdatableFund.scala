package funds.funds

import funds.currencies.{Currency, CurrencyDKK}
import funds.downloaders.Downloader
import funds.ExtendedDate
import collection.immutable.HashMap
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/29/12
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class UpdatableFund (
  override val shortName: String,
  override val currency: Currency,
  val downloader: Downloader
) extends Fund(currency, shortName) {
  var quotes = new HashMap[Int, Double]
  var dateMin: Option[Date] = None
  var dateMax: Option[Date] = None
  def update()
  def getQuoteForDate(date: ExtendedDate): Option[Double] = {
    if (dateMax.isEmpty || (date after dateMax.get)) {
      update()
    }

    if (dateMin.isEmpty || dateMax.isEmpty || (date before dateMin.get)) {
      return None
    }

    val dayCount = date.getDayCount()

    if (!quotes.contains(dayCount)) {
      val dayBefore = date.addDays(-1)
      return getQuoteForDate(dayBefore)
    }
    return quotes.get(dayCount)
  }
}

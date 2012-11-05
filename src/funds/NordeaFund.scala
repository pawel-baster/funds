import collection.immutable.HashMap
import currencies.CurrencyDKK
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/25/12
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
class NordeaFund(
  override val shortName: String,
  override val downloader: Downloader
) extends UpdatableFund(shortName, new CurrencyDKK, downloader) {
  var quotes = new HashMap[Date, Double]
  var dateMin = new SimpleDateFormat("dd-MM-yyy").parse("01-01-2000")
  var dateMax = dateMin

  def update() = {
    val csvFile = downloader.download("someUrl")
    csvFile.getLines.drop(1).foreach(l => {
      val cols = l.split(" ")
      val date = new SimpleDateFormat("dd-MM-yyy").parse(cols(0))
      if (quotes.get(date) == None) {
        quotes = quotes + ((date, cols(1).replace(',', '.').toDouble))
        if (date.getTime > dateMax.getTime) {
          dateMax = date
        }
      }
    })
    quotes.foreach(item => println(item))
  }
  def calculateSellFee(value: Double): Double = value

  def calculateBuyFee(value: Double): Double = value

  def getQuoteForDate(date: Date): Double = {
    if (date.getTime > dateMax.getTime) {
      update()
    }

    if (date.getTime > dateMax.getTime || date.getTime < dateMin.getTime) {
      throw new RuntimeException("value for date not found")
    }

    if (!quotes.contains(date)) {
      val dayBefore = new Date()
      dayBefore.setTime(date.getTime - 24 * 3600 * 1000)
      return getQuoteForDate(dayBefore)
    }
    return quotes.getOrElse(date, -1.0)
  }
}

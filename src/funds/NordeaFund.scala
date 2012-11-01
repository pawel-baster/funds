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

  def update() = {
    val csvFile = downloader.download("someUrl")
    csvFile.getLines.drop(1).foreach(l => {
      val cols = l.split(" ")
      val date = new SimpleDateFormat("dd-MM-yyy").parse(cols(0))
      if (quotes.get(date) == None) {
        quotes = quotes + ((date, cols(1).replace(',', '.').toDouble))
      }
    })
    quotes.foreach(item => println(item))
  }
  def calculateSellFee(value: Double): Double = value

  def calculateBuyFee(value: Double): Double = value

  def getQuoteForDate(date: Date): Double = {
    var quote = quotes.get(date)
    if (quote == None) {
      update()
      quote = quotes.get(date)
    }
    if (quote == None) {
      throw new RuntimeException("Missing value for date " + date);
    }
    return quote.getOrElse(-1.0)
  }
}

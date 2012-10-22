import currencies.CurrencyDKK
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/22/12
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
class MockFixedFund(
  override val shortName: String,
  val values: Array[Double]
) extends Fund(new CurrencyDKK, shortName) {
  def getQuoteForDate(date: Date): Double = {
    val idx = (date.getTime() / 24.0 / 3600 / 1000).toInt
    return values(idx)
  }
}
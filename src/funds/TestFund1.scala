import java.util.Date
import currencies.Currency

/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 30.09.12
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
class TestFund1(
  override val currency: Currency,
  override val shortName: String
) extends Fund(currency, shortName) {
  def calculateBuyFee(value: Double) : Double = 0.99
  def calculateSellFee(value: Double) : Double = 0.99
  def getQuoteForDate(date: Date): Option[Double] = Option(100 * math.pow(1 + 0.02, date.getTime() / 365.0 / 24 / 3600 / 1000))
}
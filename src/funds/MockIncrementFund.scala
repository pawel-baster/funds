import java.util.Date
import currencies.CurrencyDKK

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/9/12
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
class MockIncrementFund(
  override val shortName: String,
  val increment: Double
) extends Fund(new CurrencyDKK, shortName) {
  def getQuoteForDate(date: Date): Option[Double] = Option(100 + increment * (date.getTime() / 24.0 / 3600 / 1000))
  def calculateBuyFee(value: Double) : Double = 0.99
  def calculateSellFee(value: Double) : Double = 0.99
}
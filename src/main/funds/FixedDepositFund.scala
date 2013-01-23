package funds.funds

import funds.ExtendedDate
import java.util.Date
import funds.currencies.Currency

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/9/12
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
class FixedDepositFund(
  override val currency: Currency,
  override val shortName: String,
  val rate: Double
) extends Fund(currency, shortName) {
  def getQuoteForDate(date: ExtendedDate): Option[Double] = Option(100 * math.pow(1 + rate, date.getDayCount() / 365.0))
  def calculateBuyFee(value: Double) : Double = value * 0.99
  def calculateSellFee(value: Double) : Double = value * 0.98
}
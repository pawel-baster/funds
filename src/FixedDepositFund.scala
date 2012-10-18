package funds

import funds.{Fund, Currency}
import java.util.Date

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
  def getQuoteForDate(date: Date): Double = 100 * math.pow(1 + rate, date.getTime() / 365.0 / 24 / 3600 / 1000)
}
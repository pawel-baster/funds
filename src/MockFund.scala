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
class MockFund(
  override val shortName: String,
  val increment: Double
) extends Fund(new CurrencyDKK, shortName) {
  def getQuoteForDate(date: Date): Double = increment * (date.getTime() / 24.0 / 3600 / 1000)
}
package funds

import java.util.Date

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
  def getQuoteForDate(date: Date): Double = 100 * math.pow(1 + 0.02, date.getTime() / 365.0 / 24 / 3600 / 1000)
}
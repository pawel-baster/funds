package funds.funds

import funds.currencies.CurrencyDKK
import funds.ExtendedDate
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
                     val values: Array[Double],
                     val buyFee: Double = 0.95,
                     val sellFee: Double = 0.9
                     ) extends Fund(new CurrencyDKK, shortName) {
  def calculateBuyFee(value: Double): Double = buyFee * value

  def calculateSellFee(value: Double): Double = sellFee * value

  def getQuoteForDate(date: ExtendedDate): Option[Double] = {
    val dayCount = date.getDayCount()
    if (0 > dayCount || dayCount >= values.length)
      return None
    return Option(values(dayCount))
  }
}
package funds.funds

import funds.ExtendedDate
import java.util.Date
import funds.currencies.Currency
import sun.reflect.generics.reflectiveObjects.NotImplementedException

/**
 * User: pawel
 * Date: 25.08.12
 * Time: 21:16
 */
abstract class Fund(
  val currency: Currency,
  val shortName: String
) {
  def getQuoteForDate(date: ExtendedDate) : Option[Double]
  def calculateBuyFee(value: Double) : Double
  def calculateSellFee(value: Double) : Double
  def calculateManipulationFee(value: Double, newFund: Fund) : Double = {
    if (newFund.currency.getIsoName() == this.currency.getIsoName()) {
      return newFund.calculateBuyFee(this.calculateSellFee(value))
    } else {
      throw new NotImplementedException();
    }
  }
}

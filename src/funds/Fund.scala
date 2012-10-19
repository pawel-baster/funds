import java.util.Date
import currencies.Currency

/**
 * User: pawel
 * Date: 25.08.12
 * Time: 21:16
 */
abstract class Fund(
  val currency: Currency,
  val shortName: String
) {
  def getQuoteForDate(date: Date) : Double
}

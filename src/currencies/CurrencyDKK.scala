package currencies

import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 26.08.12
 * Time: 19:16
 * To change this template use File | Settings | File Templates.
 */
class CurrencyDKK extends Currency {
  def convertTo(cur: Currency, date: Date) = throw new Exception("not impl")
  def getName(): String = "Danish krone"
  def getIsoName(): String = "DKK"
}

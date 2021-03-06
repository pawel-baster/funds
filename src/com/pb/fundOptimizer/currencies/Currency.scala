package funds.currencies

import java.util.Date

/**
 * User: pawel
 * Date: 25.08.12
 * Time: 21:26
 */
abstract class Currency extends Serializable {
  def convertTo(cur: Currency, date: Date)

  def getName(): String

  def getIsoName(): String
}

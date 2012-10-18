package funds

import java.util.Date

/**
 * User: pawel
 * Date: 25.08.12
 * Time: 21:26
 */
abstract class Currency {
  def convertTo(cur: Currency, date: Date)
  def getName(): String
  def getIsoName(): String
}

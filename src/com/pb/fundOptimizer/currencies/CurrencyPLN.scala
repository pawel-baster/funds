package funds.currencies

import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 27.01.13
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */
class CurrencyPLN extends Currency {
  def convertTo(cur: Currency, date: Date) = throw new Exception("not impl")

  def getName(): String = "Polish Zloty"

  def getIsoName(): String = "PLN"
}
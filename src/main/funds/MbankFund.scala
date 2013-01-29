package main.funds

import funds.currencies.CurrencyDKK
import funds.downloaders.Downloader
import funds.ExtendedDate
import funds.funds.UpdatableFund
import funds.currencies.CurrencyPLN

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 27.01.13
 * Time: 13:43
 * To change this template use File | Settings | File Templates.
 */
class MbankFund(
                 override val downloader: Downloader,
                 override val shortName: String,
                 val fundCode: String
                 ) extends UpdatableFund(shortName, new CurrencyPLN, downloader) {
  def update() = {
    val startDate = if (dateMin.isDefined) dateMin.get else ExtendedDate.createFromString("2000-01-01", "yyy-MM-dd")

    val url = ("http://www.mbank.pl/inwestycje/centrum-inwestora/fundusze/getdata.pl?fund=" + fundCode + "&datod="
      + startDate.format("yyyy-MM-dd") + "&datdo=2100-01-01")
    val datFile = downloader.download(url)
    datFile.getLines.foreach(l => {
      val date = ExtendedDate.createFromString(l.substring(0, 10), "yyy-MM-dd");
      val value = l.substring(10).toDouble
      addQuote(date, value)
    })
  }

  def calculateSellFee(value: Double): Double = value

  def calculateBuyFee(value: Double): Double = value
}
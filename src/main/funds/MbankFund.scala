package main.funds

import funds.currencies.CurrencyDKK
import funds.downloaders.Downloader
import funds.ExtendedDate
import funds.funds.UpdatableFund
import main.currencies.CurrencyPLN

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
  var lastUpdate = ExtendedDate.createFromString("1970-01-01")

  def update() = {

    val url = "http://www.mbank.pl/inwestycje/centrum-inwestora/fundusze/getdata.pl?fund=ALPI&datod=2010-01-01&datdo=2100-01-01"
    val datFile = downloader.download(url)
    datFile.getLines.drop(1).foreach(l => {
      val cols = l.split(" ")
      val date = ExtendedDate.createFromString(cols(0))
      val dayCount = date.getDayCount()
      quotes = quotes + ((dayCount, cols(1).replace(',', '.').toDouble))
      if (dateMax.isEmpty || (date after dateMax.get)) {
        dateMax = Option(date)
      }
      if (dateMin.isEmpty || (date before dateMin.get)) {
        dateMin = Option(date)
      }
    })
  }

  def calculateSellFee(value: Double): Double = value
  def calculateBuyFee(value: Double): Double = value
}
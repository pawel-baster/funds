package fundOptimizer.funds

import funds.currencies.CurrencyDKK
import funds.downloaders.Downloader
import funds.ExtendedDate
import funds.funds.UpdatableFund
import funds.currencies.CurrencyPLN
import com.pb.fundOptimizer.logging.logger

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
                 val fundCode: String,
                 val annualManagingFee: Double = 0.04
                 ) extends UpdatableFund(shortName, new CurrencyPLN, downloader) {
  def update() = {
    logger.info("MbankFund " + fundCode + " starting update. Last Update: " + lastUpdate + ", minDate " + dateMin + ", maxDate: " + dateMax)
    val startDate = if (dateMin.isDefined) dateMin.get.addDays(-10) else ExtendedDate.createFromString("2000-01-01", "yyy-MM-dd")

    val url = ("http://www.mbank.pl/ajax/SFI/drawChart/?curr=&ror=0&date_from=" + startDate.format("yyyy-MM-dd") + "&date_to=2100-01-01&funds[]=" + fundCode)
    val dataFile = downloader.download(url)

    val dataPattern = """(?<=series: \[\{"data":\[\[).*(?=\]\],"name")""".r
    val dataString = dataFile.getLines.mkString(" ")
    val matched = {
      dataPattern findFirstIn dataString
    }

    if (matched.isDefined) {
      val data = matched.get
      data.split("""\],\[""").foreach {
        pair => {
          val tokens = pair.split(',')
          require(tokens.length == 2)
          val date = new ExtendedDate
          date.setTime(tokens(0).toLong - 24 * 3600 * 1000)
          val value = tokens(1)
          addQuote(date, value.toDouble)
        }
      }
    } else {
      throw new Exception("Could not parse imported data")
    }

    logger.info("MbankFund " + fundCode + " update finished. Last Update: " + lastUpdate + ", minDate " + dateMin + ", maxDate: " + dateMax)
    lastUpdate = new ExtendedDate
    setNeedsSaving(true)
  }

  def calculateDailyManagingFee(value: Double): Double = {
    return math.pow((1 - annualManagingFee), 1/365.0) * value
  }

  def calculateSellFee(value: Double): Double = 0.995 * value // prevent too common changes

  def calculateBuyFee(value: Double): Double = value
}
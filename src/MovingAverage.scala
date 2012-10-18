import collection.immutable.Range.Int
import funds.{MockFund, CurrencyDKK, FixedDepositFund, Fund}
import java.util
import java.util.Date
// import scala.Int

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/10/12
 * Time: 7:45 AM
 * To change this template use File | Settings | File Templates.
 */
class MovingAverage {

  /**
   * static?
   * 100,100,100,100,100 -> 200, 200, 200, 200
   */
  def calculate(): Double = { //{funds: Array[Fund], from: Date, to: Date, window: Int) = {
    val from = new Date()
    val to = new Date()
    from.setTime(to.getTime() - 5 * 24 * 3600 * 1000)
    val window = 2
    val funds = Array(
//      new FixedDepositFund(new CurrencyDKK, "test fund1", 0.0002),
//      new FixedDepositFund(new CurrencyDKK, "test fund2", 0.0001),
      new MockFund("mock1", 1),
      new MockFund("mock2", 2)
    )

    println("Mock1 for today: " + new MockFund("", 1).getQuoteForDate(new Date))

    val fromDate = math.floor(from.getTime()/24.0/3600/1000).toInt
    val toDate = math.floor(to.getTime()/24.0/3600/1000).toInt

    println("MA from " + fromDate + " to " + toDate)

    assert (window > 0)
    assert (window < (toDate - fromDate))

    val ma = Array.ofDim[Double](toDate - fromDate - window + 1, funds.length)

    val date = new Date(fromDate * 24 * 3600 * 1000)
    for (day <- (0 to window -1)) {
      date.setTime(date.getTime() + 24 * 3600 * 1000)
      println("initial summation; day: " + (fromDate + day) + ", date: " + date.formatted("y-m-d"))
      for (fundind <- (0 to funds.length - 1)){
        ma(0)(fundind) += funds(fundind).getQuoteForDate(date)
        println("adding: " + funds(fundind).getQuoteForDate(date))
      }
    }

    for (day <- (1 to toDate - fromDate - window)) {
      println("day " + day)
      val date = new Date()
      date.setTime((fromDate + window + day) * 24 * 3600 * 1000)
      val oldDate = new Date()
      oldDate.setTime((fromDate + day) * 24 * 3600 * 1000)
      for (fundind <- (0 to funds.length - 1)){
        ma(day)(fundind) += ma(day-1)(fundind) + funds(fundind).getQuoteForDate(date) - funds(fundind).getQuoteForDate(oldDate)
      }
    }

    ma.foreach(row => {row.foreach(el => print(el + " ")); println})
    /*
       var ma = Array[Double](funds.length)

       (from + window - 1).foreach
         funds.reduce(sum)

       (from + window .. to).foreach
         funds.foreach(sum[fund] + value[i] - value[i - window])

       funds.keys.foreach
         foreach divide by window size
     */
    return 5.0
  }
}

import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/25/12
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */

val fund1 = new NordeaFund("test1", new MockDownloader("src/tests/fixtures/nordea_export_1.csv"))
val date1 = new SimpleDateFormat("dd-MM-yyy").parse("10-10-2012")
val expectedValue1 = 98.76
assert(fund1.getQuoteForDate(date1) == expectedValue1, "Expected value: " + expectedValue1 + ", got: " + fund1.getQuoteForDate(date1))

// assert throws exception
val date2 = new SimpleDateFormat("dd-MM-yyy").parse("22-10-2012")
try {
  val result = fund1.getQuoteForDate(date2)
  assert(false, "Exception not thrown, returned value: " + result)
} catch {
  case e: RuntimeException => assert(true, "Exception expected")
}

val fund2 = new NordeaFund("test2", new MockDownloader("src/tests/fixtures/nordea_export_2.csv")) //mockDownloader
assert(fund2.getQuoteForDate(date2) == 99.13)

println("OK")
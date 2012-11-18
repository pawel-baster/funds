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

val fund1 = new NordeaFund(new MockDownloader("src/tests/fixtures/nordea_export_1.csv"), "test1", "", "")
val date1 = new SimpleDateFormat("dd-MM-yyy").parse("10-10-2012")
val expectedValue1 = 98.76
assert(fund1.getQuoteForDate(date1).get == expectedValue1, "Expected value: " + expectedValue1 + ", got: " + fund1.getQuoteForDate(date1))

// assert throws exception
val outofrange = new SimpleDateFormat("dd-MM-yyy").parse("22-09-2012")
val result = fund1.getQuoteForDate(outofrange)
assert(result == None, "result != None, returned value: " + result)
assert(result.isEmpty, "result not empty, returned value: " + result)

val date2 = new SimpleDateFormat("dd-MM-yyy").parse("22-10-2012")

assert(fund1.getQuoteForDate(date2).get == 98.76)

val fund2 = new NordeaFund(new MockDownloader("src/tests/fixtures/nordea_export_2.csv"), "test2", "", "")
assert(fund2.getQuoteForDate(date2).get == 99.13)

//interpolation:
val interpolationDate = new SimpleDateFormat("dd-MM-yyy").parse("19-10-2012")
val interpolatedValue = fund2.getQuoteForDate(interpolationDate).get
assert(interpolatedValue == 99.42, "Expected (interpolated) 99.42, got: " + interpolatedValue)

val futureDate = new SimpleDateFormat("dd-MM-yyy").parse("22-10-2013")
assert(fund2.getQuoteForDate(futureDate).get == 98.30)

println("OK")
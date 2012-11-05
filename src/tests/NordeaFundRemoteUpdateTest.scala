import java.text.SimpleDateFormat

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 11/1/12
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */

val date1 = new SimpleDateFormat("dd-MM-yyy").parse("12-10-2012")
// No session:
val date2 = new SimpleDateFormat("dd-MM-yyy").parse("14-10-2012")

val fund1 = new NordeaFund() // name, code?
val fund2 = new NordeaFund() // name, code?

val expectedValue1 = 114.17
val actualValue1 = fund1.getQuoteForDate(date1)
val actualValue1b = fund1.getQuoteForDate(date2)
assert(actualValue1 == expectedValue1, "Expected " + expectedValue1 + ", got: " + actualValue1)
assert(actualValue1 == expectedValue1b, "Expected " + expectedValue1 + ", got: " + actualValue1b)

val expectedValue2= 113.57
val actualValue2 = fund2.getQuoteForDate(date1)
val actualValue2b = fund2.getQuoteForDate(date2)
assert(actualValue2 == expectedValue2, "Expected " + expectedValue2 + ", got: " + actualValue2)
assert(actualValue2 == expectedValue2b, "Expected " + expectedValue2 + ", got: " + actualValue2b)

val date3 = new SimpleDateFormat("dd-MM-yyy").parse("13-10-2112")
val date4 = new SimpleDateFormat("dd-MM-yyy").parse("13-10-1912")

val actualValue3 = fund1.getQuoteForDate(date3)
assert(actualValue3 == None, "Expected None, got: " + actualValue3)

val actualValue4 = fund1.getQuoteForDate(date4)
assert(actualValue4 == None, "Expected None, got: " + actualValue4)
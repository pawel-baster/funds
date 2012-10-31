import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/25/12
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */

val fund1 = new NordeaFund("test1", new MockDownloader("src/tests/fixtures/nordea_export_1.csv"))
val date1 = new Date // 10-10-2012
assert(fund1.getQuoteForDate(date1) == 98.76)

// assert throws exception
val date2 = new Date() // 22-10-2012
fund1.getQuoteForDate(date2)

val fund2 = new NordeaFund("test2", new MockDownloader("src/tests/fixtures/nordea_export_2.csv")) //mockDownloader
assert(fund2.getQuoteForDate(date2) == 99.13)
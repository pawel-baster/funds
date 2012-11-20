package test

import funds.downloaders.MockDownloader
import funds.funds.NordeaFund
import org.scalatest._
import java.text.SimpleDateFormat
import matchers.ShouldMatchers

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 11/20/12
 * Time: 7:17 AM
 * To change this template use File | Settings | File Templates.
 */
class NordeaFundUpdateTest extends FunSpec with ShouldMatchers {
  describe("A NordeaFund") {
    it("should return 98.76 for 10-10-2012") {
      val fund1 = new NordeaFund(new MockDownloader("src/test/fixtures/nordea_export_1.csv"), "test1", "", "")
      val date1 = new SimpleDateFormat("dd-MM-yyy").parse("10-10-2012")
      assert(fund1.getQuoteForDate(date1).get === 98.76)
    }

    it("should return None for dates out of range") {
      val fund1 = new NordeaFund(new MockDownloader("src/test/fixtures/nordea_export_1.csv"), "test1", "", "")
      val outofrange = new SimpleDateFormat("dd-MM-yyy").parse("22-09-2012")
      assert(fund1.getQuoteForDate(outofrange) === None)
    }

    it("should return last value for dates in future") {
      val fund1 = new NordeaFund(new MockDownloader("src/test/fixtures/nordea_export_1.csv"), "test1", "", "")
      val future = new SimpleDateFormat("dd-MM-yyy").parse("22-09-2013")
      assert(fund1.getQuoteForDate(future).get === 98.76)
    }
  }
}
package com.pb.fundOptimizer.test

import fundOptimizer.funds.MbankFund
import funds.downloaders.MockDownloader
import funds.ExtendedDate
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10.06.13
 * Time: 19:08
 * To change this template use File | Settings | File Templates.
 */
class MbankFundJsonUpdateTest extends FunSpec with ShouldMatchers {
  describe("An MBank Fund") {
    it("should return None for dates before available history") {
      commonTest("02-03-2013", None)
    }

    it("should return 122.56 for the first date in the history") {
      commonTest("03-03-2013", Option(122.56))
    }

    it("should return 122.76 for the second date in the history") {
      commonTest("04-03-2013", Option(122.76))
    }

    it("should return 135.90 for a random date in the history") {
      commonTest("30-05-2013", Option(135.90))
    }

    it("should return the quote from previous day(s) in case there's no quote for this day") {
      commonTest("01-06-2013", Option(135.90))
    }

    it("should return 128.77 for the latest quote") {
      commonTest("05-06-2013", Option(128.77))
    }

    it("should return last quote for a day after latest quote") {
      commonTest("06-06-2013", Option(128.77))
    }

    it("should return last quote for a day far in future") {
      commonTest("01-01-2015", Option(128.77))
    }
  }

  def commonTest(dateStr: String, expectedResult: Option[Double]) {
    val fund1 = new MbankFund(new MockDownloader("src/com/pb/fundOptimizer/test/fixtures/mbank.json"), "test1", "test1")
    val date1 = ExtendedDate.createFromString(dateStr, "dd-MM-yyy")
    println("testing for data: " + date1 + " (derived from " + dateStr)
    assert(fund1.getQuoteForDate(date1) === expectedResult)
  }
}
package com.pb.fundOptimizer.test

import funds.downloaders.MockDownloader
import funds.ExtendedDate
import funds.funds.NordeaFund
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import fundOptimizer.funds.MbankFund

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 27.01.13
 * Time: 13:33
 * To change this template use File | Settings | File Templates.
 */
class MbankFundUpdateTest extends FunSpec with ShouldMatchers {
  describe("An MBank Fund") {
    it("should return None for dates before available history") {
      commonTest("03-01-2010", None)
    }

    it("should return 119.56 for the first date in the history") {
      commonTest("04-01-2010", Option(119.56))
    }

    it("should return 119.57 for the second date in the history") {
      commonTest("05-01-2010", Option(119.57))
    }

    it("should return 119.61 for a random date in the history") {
      commonTest("08-01-2010", Option(119.61))
    }

    it("should return the quote from previous day(s) in case there's no quote for this day") {
      commonTest("09-01-2010", Option(119.61))
    }

    it("should return 113.98 for the latest quote") {
      commonTest("01-23-2013", Option(138.98))
    }

    it("should return 113.98 for a day after latest quote") {
      commonTest("01-24-2013", Option(138.98))
    }

    it("should return 113.98 for a day far in future") {
      commonTest("01-01-2015", Option(138.98))
    }
  }

  def commonTest(dateStr: String, expectedResult: Option[Double]) {
    val fund1 = new MbankFund(new MockDownloader("src/com/pb/fundOptimizer/test/fixtures/mbank.dat"), "test1", "test1")
    val date1 = ExtendedDate.createFromString(dateStr, "dd-MM-yyy")
    assert(fund1.getQuoteForDate(date1) === expectedResult)
  }
}
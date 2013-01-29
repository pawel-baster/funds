package test

import funds.ExtendedDate
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import main.funds.MbankFund
import main.downloaders.RemoteDownloader

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 1/28/13
 * Time: 9:43 PM
 * To change this template use File | Settings | File Templates.
 */
class MbankRemoteUpdateTest extends FunSpec with ShouldMatchers {
  describe("An MBank Fund") {
    it("should be able to update itself using Internet connection") {
      val fund = new MbankFund(new RemoteDownloader(), "test", "PKCS")
      val quote = fund.getQuoteForDate(ExtendedDate.createFromString("2010-01-06", "yyy-MM-dd"))
      assert(quote.isDefined)
      assert(quote.get === 1654.27)
    }
  }
}

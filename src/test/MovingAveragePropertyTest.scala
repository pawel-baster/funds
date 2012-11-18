package test

import funds.funds.{MockFixedFund, Fund}
import funds.MovingAverage
import org.scalatest._
import matchers.ShouldMatchers
import prop.GeneratorDrivenPropertyChecks
import scala.Predef._
import java.util.Date


/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 18.11.12
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
class MovingAveragePropertyTest extends FunSpec with GeneratorDrivenPropertyChecks with ShouldMatchers {
  describe("A MovingAverage calculator") {
    it("should return input values for random data if window is set to 1") {
      forAll {
        (records: Array[Double]) =>
          whenever(records.size > 2) {
            val funds = Array[Fund](
              new MockFixedFund("test", records)
            )

            val window = 1
            val from = new Date()
            from.setTime(0)
            val to = new Date()
            to.setTime((records.size - 1) * 24 * 3600 * 1000)

            val ma = new MovingAverage
            val result = ma.calculate(funds, from, to, window)

            records.map(Array(_)) should equal(result)
          }
      }
    }

    it("should return a sum of n first records as a first result if window is set to n") {
      forAll(minSuccessful(50), maxDiscarded(1000)) {
        (window: Int, records: Array[Double]) =>
          whenever(0 < window && window < 100000 && records.length > window + 1) {
            val funds = Array[Fund](
              new MockFixedFund("test", records)
            )

            val from = new Date()
            from.setTime(0)
            val to = new Date()
            to.setTime((records.length - 1) * 24L * 3600 * 1000)
            require(to.getTime > 0, "to.getTime negative = " + to.getTime + ", array length: " + records.length)

            val ma = new MovingAverage
            val result = ma.calculate(funds, from, to, window)

            (records.take(window).sum / window) should equal(result(0)(0))
          }
      }
    }

    it("should return a sum of n last records as a last result if window is set to n") {
      forAll(minSuccessful(50), maxDiscarded(1000)) {
        (window: Int, records: Array[Double]) =>
          whenever(0 < window && window < 100000 && records.length > window + 1) {
            val funds = Array[Fund](
              new MockFixedFund("test", records)
            )

            val from = new Date()
            from.setTime(0)
            val to = new Date()
            to.setTime((records.length - 1) * 24L * 3600 * 1000)
            require(to.getTime > 0, "to.getTime negative = " + to.getTime + ", array length: " + records.length)

            val ma = new MovingAverage
            val result = ma.calculate(funds, from, to, window)

            (records.reverse.take(window).sum / window) should equal(result.reverse(0)(0))
          }
      }
    }
  }
}

package test

import funds.funds.{MockFixedFund, Fund}
import funds.{ExtendedDate, MovingAverage}
import org.scalatest._
import matchers.ShouldMatchers
import prop.GeneratorDrivenPropertyChecks
import scala.Predef._
import java.util.Date
import util.Random


/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 18.11.12
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
class MovingAveragePropertyTest extends FunSpec with GeneratorDrivenPropertyChecks with ShouldMatchers {

  def normalizeRecords(records: Array[Double]) : Array[Double] = {
    return records.map(record => if (math.abs(record) < 1000000) record else Random.nextInt(1000000))
  }

  describe("A MovingAverage calculator") {
    it("should return input values for random data if window is set to 1") {
      forAll {
        (records: Array[Double]) =>
          whenever(records.length > 2) {

            val recordsFiltered = normalizeRecords(records)

            val funds = Array[Fund](
              new MockFixedFund("test", recordsFiltered)
            )

            val window = 1
            val from = ExtendedDate.createFromDays(0)
            val to = ExtendedDate.createFromDays(recordsFiltered.length - 1)

            val ma = new MovingAverage
            val result = ma.calculate(funds, from, to, window)

            val firstItemsOnly = result.values.map(item => { item.head }).toArray

            (firstItemsOnly) should equal (recordsFiltered)

            /* (recordsFiltered, firstItemsOnly).zipped.foreach { (expected, actual) => {
                (actual) should be (expected plusOrMinus 0.0001)
              }
            } */
          }
      }
    }

    it("should return a sum of n first records as a first result if window is set to n") {
      forAll(minSuccessful(50), maxDiscarded(1000)) {
        (window: Int, records: Array[Double]) =>
          whenever(0 < window && window < 100000 && records.length > window + 1) {
            val recordsFiltered = normalizeRecords(records)

            val funds = Array[Fund](
              new MockFixedFund("test", recordsFiltered)
            )

            val from = ExtendedDate.createFromDays(0)
            val to = ExtendedDate.createFromDays(recordsFiltered.length - 1)

            require(to.getTime > 0, "to.getTime negative = " + to.getTime + ", array length: " + recordsFiltered.length)

            val ma = new MovingAverage
            val result = ma.calculate(funds, from, to, window)

            (recordsFiltered.take(window).sum / window) should equal(result.get(to.getDayCount()).head)
          }
      }
    }

    it("should return a sum of n last records as a last result if window is set to n") {
      forAll(minSuccessful(50), maxDiscarded(1000)) {
        (window: Int, records: Array[Double]) =>
          whenever(0 < window && window < 100000 && records.length > window + 1 && records.length < 100000) {

            val recordsFiltered = normalizeRecords(records)

            val funds = Array[Fund](
              new MockFixedFund("test", recordsFiltered)
            )

            val from = ExtendedDate.createFromDays(0)
            val to = ExtendedDate.createFromDays(recordsFiltered.length - 1)

            require(to.getTime > 0, "to.getTime negative = " + to.getTime + ", array length: " + recordsFiltered.length)

            val ma = new MovingAverage
            val result = ma.calculate(funds, from, to, window)
            //println("test:")
            //result.foreach(array => println(array(0)))
            (result.get(to.getDayCount()).head) should equal (recordsFiltered.reverse.take(window).sum / window plusOrMinus 0.0001)
            //(records.reverse.take(window).sum / window) should equal(result.reverse(0)(0))
            //(records.reverse.take(window).sum / window - result.reverse(0)(0)) should be < 0.00001
          }
      }
    }
  }
}

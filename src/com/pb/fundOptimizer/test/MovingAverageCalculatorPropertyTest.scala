package com.pb.fundOptimizer.test

import funds.funds.{MockFixedFund, Fund}
import funds.{ExtendedDate}
import org.scalatest._
import matchers.ShouldMatchers
import prop.GeneratorDrivenPropertyChecks
import scala.Predef._
import java.util.Date
import util.{Sorting, Random}
import com.pb.fundOptimizer.calculations.MovingAverageCalculator


/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 18.11.12
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
class MovingAverageCalculatorPropertyTest extends FunSpec with GeneratorDrivenPropertyChecks with ShouldMatchers {

  def normalizeRecords(records: Array[Double]): Array[Double] = {
    return records.map(record => if (math.abs(record) < 1000000) record else Random.nextInt(1000000))
  }

  describe("A MovingAverageCalculator calculator") {
    it("should return input values for random data if window is set to 1") {
      forAll {
        (records: Array[Double]) =>
          whenever(records.length > 2) {

            val recordsFiltered = normalizeRecords(records)

            val funds = Array[Fund](
              new MockFixedFund("com/pb/fundOptimizer/test", recordsFiltered)
            )

            val window = 1
            val from = ExtendedDate.createFromDays(0)
            val to = ExtendedDate.createFromDays(recordsFiltered.length - 1)

            val ma = new MovingAverageCalculator
            val result = ma.calculate(funds, from, to, window)

            val firstItemsOnly = result.toSeq.sortBy(_._1).map(_._2.head).toArray
            //Sorting.quickSort(firstItemsOnly)
            //Sorting.quickSort(recordsFiltered)

            (firstItemsOnly) should equal(recordsFiltered)

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
              new MockFixedFund("com/pb/fundOptimizer/test", recordsFiltered)
            )

            val from = ExtendedDate.createFromDays(0)
            val to = ExtendedDate.createFromDays(recordsFiltered.length - 1)

            require(to.getTime > 0, "to.getTime negative = " + to.getTime + ", array length: " + recordsFiltered.length)

            val ma = new MovingAverageCalculator
            val result = ma.calculate(funds, from, to, window)

            (recordsFiltered.take(window).sum / window) should equal(result.toSeq.sortBy(_._1).head._2.head)
          }
      }
    }

    it("should return a sum of n last records as a last result if window is set to n") {
      forAll(minSuccessful(50), maxDiscarded(1000)) {
        (window: Int, records: Array[Double]) =>
          whenever(0 < window && window < 100000 && records.length > window + 1 && records.length < 100000) {

            val recordsFiltered = normalizeRecords(records)

            val funds = Array[Fund](
              new MockFixedFund("com/pb/fundOptimizer/test", recordsFiltered)
            )

            val from = ExtendedDate.createFromDays(0)
            val to = ExtendedDate.createFromDays(recordsFiltered.length - 1)

            require(to.getTime > 0, "to.getTime negative = " + to.getTime + ", array length: " + recordsFiltered.length)

            val ma = new MovingAverageCalculator
            val result = ma.calculate(funds, from, to, window)
            //println("com.pb.fundOptimizer.test:")
            //result.foreach(array => println(array(0)))
            //(result.get(to.getDayCount()).head) should equal (recordsFiltered.reverse.take(window).sum / window plusOrMinus 0.0001)
            (result.get(to.getDayCount()).head.head) should equal(recordsFiltered.reverse.take(window).sum / window)
            //(records.reverse.take(window).sum / window) should equal(result.reverse(0)(0))
            //(records.reverse.take(window).sum / window - result.reverse(0)(0)) should be < 0.00001
          }
      }
    }
  }
}

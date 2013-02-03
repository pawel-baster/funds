package test

import org.scalatest._
import java.util.Date
import matchers.ShouldMatchers
import scala.Predef._
import funds.funds._
import funds.{ExtendedDate, MovingAverage}
import funds.currencies.CurrencyDKK

/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 17.11.12
 * Time: 20:52
 * To change this template use File | Settings | File Templates.
 */
class MovingAverageTest extends FunSpec with ShouldMatchers {
  describe("A MovingAverage calculator") {

    it("should return the same array if window is set to 1") {
      val funds = Array[Fund](
        new MockFixedFund("com/pb/fundOptimizer/test", Array(1.0, 2.0, 4.0, 8.0, 16.0, 32.0))
      )

      val window = 1
      val from = ExtendedDate.createFromDays(0)
      val to = ExtendedDate.createFromDays(5)

      val expected = scala.collection.Map(
        0 -> Array(1.0),
        1 -> Array(2.0),
        2 -> Array(4.0),
        3 -> Array(8.0),
        4 -> Array(16.0),
        5 -> Array(32.0)
      )

      _commonTest(funds, expected, window, from, to)
    }

    it("should return a valid result if window is set to 2") {
      val funds = Array[Fund](
        new MockFixedFund("com/pb/fundOptimizer/test", Array(1.0, 2.0, 4.0, 8.0, 16.0, 32.0))
      )

      val window = 2
      val from = new ExtendedDate()
      from.setTime(0)
      val to = new ExtendedDate()
      to.setTime(5 * 24 * 3600 * 1000)

      val expected = scala.collection.Map(
        1 -> Array(1.5),
        2 -> Array(3.0),
        3 -> Array(6.0),
        4 -> Array(12.0),
        5 -> Array(24.0)
      )

      _commonTest(funds, expected, window, from, to)
    }

    it("should return a valid result if window is set to 5") {
      val funds = Array[Fund](
        new MockFixedFund("com/pb/fundOptimizer/test", Array(1.0, 2.0, 4.0, 8.0, 16.0, 32.0))
      )

      val window = 5
      val from = new ExtendedDate()
      from.setTime(0)
      val to = new ExtendedDate()
      to.setTime(5 * 24 * 3600 * 1000)

      val expected = scala.collection.Map(
        4 -> Array(6.2),
        5 -> Array(12.4)
      )

      _commonTest(funds, expected, window, from, to)
    }

    it("should return a valid result if window is set to 6") {
      val funds = Array[Fund](
        new MockFixedFund("com/pb/fundOptimizer/test", Array(1.0, 2.0, 4.0, 8.0, 16.0, 32.0))
      )

      val window = 6
      val from = new ExtendedDate()
      from.setTime(0)
      val to = new ExtendedDate()
      to.setTime(5 * 24 * 3600 * 1000)

      val expected = scala.collection.Map(
        5 -> Array(10.5)
      )

      _commonTest(funds, expected, window, from, to)
    }

    it("should shoud fail if window size is too big") {
      val funds = Array[Fund](
        new MockFixedFund("com/pb/fundOptimizer/test", Array(1.0, 2.0, 4.0, 8.0, 16.0, 32.0))
      )

      val window = 7
      val from = new ExtendedDate()
      from.setTime(0)
      val to = new ExtendedDate()
      to.setTime(5 * 24 * 3600 * 1000)

      val expected = scala.collection.Map(
        1 -> Array(-1.0)
      )
      intercept[IllegalArgumentException] {
        _commonTest(funds, expected, window, from, to)
      }
    }

    it("should return a valid result if window is set to 3") {
      val from = new ExtendedDate()
      val to = new ExtendedDate()
      from.setTime(0)
      to.setTime(10 * 24 * 3600 * 1000)
      val window = 3
      val funds = Array(
        new MockIncrementFund("const", 0),
        new MockIncrementFund("linear", 1),
        new FixedDepositFund(new CurrencyDKK, "com.pb.fundOptimizer.test fund2", 0.01)
      )

      val expected = scala.collection.Map(
        2 -> Array(100, 101, 100.00272617997398),
        3 -> Array(100, 102,	100.00545240949477),
        4 -> Array(100, 103,	100.00817871333682),
        5 -> Array(100, 104,	100.01090509150214),
        6 -> Array(100, 105,	100.01363154399274),
        7 -> Array(100, 106,	100.01635807081068),
        8 -> Array(100, 107,	100.019084671958),
        9 -> Array(100, 108, 100.02181134743667),
        10 -> Array(100, 109, 100.02453809724875)
      )

      _commonTest(funds, expected, window, from, to)
    }
  }

  def mapToString(aMap : scala.collection.Map[Int, Array[Double]]) : String = {
    var result = ""
    for ((key, value) <- aMap) result = result + "\n" + key.toString + " -> " + value.mkString
    return result
  }

  def _commonTest(funds: Array[Fund], expectedMA: scala.collection.Map[Int, Array[Double]], window: Int, from: ExtendedDate, to: ExtendedDate) {
    val ma = new MovingAverage

    //println("Expected:")
    //expectedMA.foreach(row => {row.foreach(el => print(el + " ")); println})

    //println("Actual:")

    val result = ma.calculate(funds, from, to, window)
    //result.foreach(row => {row.foreach(el => print(el + " ")); println})

    //assert(expectedMA.deep == result.values.deep, "returned array does not match expected result")
    //ensure order
    //(result.keys.toArray) should equal (expectedMA.keys.toArray)
    //(result.values.toArray) should equal (expectedMA.values.toArray)
    (result.values.size) should equal (expectedMA.values.size)
    result.keys.foreach(key => {
      assert(expectedMA.contains(key), "key missing in expected result: " + key + ", actual: " + mapToString(result) + "\nexpected: " + mapToString(expectedMA))
      assert(result.get(key).get === expectedMA.get(key).get, "failure for key: " + key + ", actual: " + mapToString(result) + "\nexpected: " + mapToString(expectedMA))
    })

    //(result) should equal (expectedMA)
    //assert(result === expectedMA, "actual: " + mapToString(result) + "\nexpected: " + mapToString(expectedMA))
  }
}

package funds.tests

import org.scalatest._
import java.util.Date
import scala.Predef._
import funds.funds._
import funds.MovingAverage
import funds.currencies.CurrencyDKK

/**
 * Created with IntelliJ IDEA.
 * User: pawel
 * Date: 17.11.12
 * Time: 20:52
 * To change this template use File | Settings | File Templates.
 */
class MovingAverageTest extends FunSpec {
  describe("A MovingAverage calculator") {

    it("should return the same array if window is set to 1") {
      val funds = Array[Fund](
        new MockFixedFund("test", Array(1.0, 2.0, 4.0, 8.0, 16.0, 32.0))
      )

      val window = 1
      val from = new Date()
      from.setTime(0)
      val to = new Date()
      to.setTime(5 * 24 * 3600 * 1000)

      val expected = Array(
        Array(1.0),
        Array(2.0),
        Array(4.0),
        Array(8.0),
        Array(16.0),
        Array(32.0)
      )

      _commonTest(funds, expected, window, from, to)
    }

    it("should return a valid result if window is set to 2") {
      val funds = Array[Fund](
        new MockFixedFund("test", Array(1.0, 2.0, 4.0, 8.0, 16.0, 32.0))
      )

      val window = 2
      val from = new Date()
      from.setTime(0)
      val to = new Date()
      to.setTime(5 * 24 * 3600 * 1000)

      val expected = Array(
        Array(1.5),
        Array(3.0),
        Array(6.0),
        Array(12.0),
        Array(24.0)
      )

      _commonTest(funds, expected, window, from, to)
    }

    it("should return a valid result if window is set to 3") {
      val from = new Date()
      val to = new Date()
      from.setTime(0)
      to.setTime(10 * 24 * 3600 * 1000)
      val window = 3
      val funds = Array(
        new MockIncrementFund("const", 0),
        new MockIncrementFund("linear", 1),
        new FixedDepositFund(new CurrencyDKK, "test fund2", 0.01)
      )

      val expected = Array(
        Array(100, 101, 100.00272617997398),
        Array(100, 102,	100.00545240949477),
        Array(100, 103,	100.00817871333682),
        Array(100, 104,	100.01090509150214),
        Array(100, 105,	100.01363154399274),
        Array(100, 106,	100.01635807081068),
        Array(100, 107,	100.019084671958),
        Array(100, 108, 100.02181134743667),
        Array(100, 109, 100.02453809724875)
      )

      _commonTest(funds, expected, window, from, to)
    }
  }


  def _commonTest(funds: Array[Fund], expectedMA: Array[Array[Double]], window: Int, from: Date, to: Date) {
    val ma = new MovingAverage

    //println("Expected:")
    //expectedMA.foreach(row => {row.foreach(el => print(el + " ")); println})

    //println("Actual:")

    val result = ma.calculate(funds, from, to, window)
    //result.foreach(row => {row.foreach(el => print(el + " ")); println})

    assert(expectedMA.deep == result.deep, "returned array does not match expected result")
  }
}

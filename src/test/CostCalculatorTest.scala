package test

import funds.{ExtendedDate, Params, MovingAverage, CostCalculator}
import funds.funds.{MockFixedFund, Fund}
import java.util.Date
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 12/6/12
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
class CostCalculatorTest extends FunSpec  with ShouldMatchers {
  it("should return correct result for best params") {
    runTest(new Params(2, 0, Array(1.0, 0, 0)), 800, 0)
  }

  it("should return correct result for worst params") {
    runTest(new Params(2, 0, Array(0, 1.0, 0)), 100/8.0, 1)
  }

  it("should return correct result for constant params") {
    runTest(new Params(2, 0, Array(0, 0, 1.0)), 100, 2)
  }

  it("should change the fund and apply fees on fund change") {
    runTest(new Params(1, 0, Array(1.0, 0, 0)), 342, 1)
  }

  it("should not change fund if smoothFactor is big enough") {
    runTest(new Params(2, 20, Array(0, 1.0, 0)), 100/8.0, 1)
  }

  def runTest(params: Params, expectedResult: Double, initialFund: Int) {
    val funds = Array[Fund](
      new MockFixedFund("best", Array(1.0, 2.0, 4.0, 8.0, 16.0)),
      new MockFixedFund("worst", Array(16.0, 8.0, 4.0, 2.0, 1.0)),
      new MockFixedFund("medium", Array(1.0, 1.0, 1.0, 1.0, 1.0))
    )

    val from = ExtendedDate.createFromDays(params.window)
    val to = ExtendedDate.createFromDays(4)

    val cc = new CostCalculator(new MovingAverage)
    val result = cc.calculate(funds, from, to, 100, initialFund, params).get(4).get.value
    (result) should equal (expectedResult)
  }

  it("should return correct result for this dataset") {
    val records = Array(1.0, 2.0, 4.0, 6.0, 7.0, 8.0)
    val window = 1
    val funds = Array[Fund](
      new MockFixedFund("test1", records),
      new MockFixedFund("test2", records.reverse)
    )

    val from = ExtendedDate.createFromDays(window)
    val to = ExtendedDate.createFromDays(records.length - 1)

    val cc = new CostCalculator(new MovingAverage)

    val firstAlways = new Params(window, 0, Array(1.0, 0))
    val result = cc.calculate(funds, from, to, 1.0, 0, firstAlways)
    (records.last / records.head) should be (result.get(to.getDayCount()).get.value plusOrMinus 0.000001)
  }
}

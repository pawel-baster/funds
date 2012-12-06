package test

import funds.{Params, MovingAverage, CostCalculator}
import funds.funds.{MockFixedFund, Fund}
import java.util.Date
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 12/6/12
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
class CostCalculatorTest extends FunSpec  with ShouldMatchers {
  it("should return correct result for best params") {
    runTest(new Params(2, 0, Array(1.0, 0, 0)), 1600, 0)
  }

  it("should return correct result for worst params") {
    runTest(new Params(2, 0, Array(0, 1.0, 0)), 100/16.0, 1)
  }

  it("should return correct result for constant params") {
    runTest(new Params(2, 0, Array(0, 0, 1.0)), 100, 2)
  }

  it("should change the fund and apply fees on fund change") {
    runTest(new Params(2, 0, Array(1.0, 0, 0)), 342, 1)
  }

  it("should not change fund if smoothFactor is big enough") {
    runTest(new Params(2, 20, Array(0, 1.0, 0)), 100/16.0, 1)
  }

  def runTest(params: Params, expectedResult: Double, initialFund: Int) {
    val funds = Array[Fund](
      new MockFixedFund("best", Array(1.0, 2.0, 4.0, 8.0, 16.0)),
      new MockFixedFund("worst", Array(16.0, 8.0, 4.0, 2.0, 1.0)),
      new MockFixedFund("medium", Array(1.0, 1.0, 1.0, 1.0, 1.0))
    )

    val from = new Date()
    from.setTime(0)
    val to = new Date()
    to.setTime(4 * 24 * 3600 * 1000)

    val cc = new CostCalculator(new MovingAverage)
    val result = cc.calculate(funds, from, to, 100, initialFund, params)
    (result) should equal (expectedResult)
  }
}

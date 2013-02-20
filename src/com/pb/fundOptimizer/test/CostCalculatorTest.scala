package com.pb.fundOptimizer.test

import funds.{ExtendedDate}
import funds.funds.{MockFixedFund, Fund}
import java.util.Date
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import util.Random
import com.pb.fundOptimizer.calculations.{CostCalculator, Params, MovingAverageCalculator}
import collection.mutable.ArrayBuffer
import com.pb.fundOptimizer.ExperimentHistoryEntry

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 12/6/12
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
class CostCalculatorTest extends FunSpec with ShouldMatchers {
  it("should return correct result for best params") {
    runTest(new Params(2, 0, Array(1.0, 0, 0)), calculateTaxesAndFees(new MockFixedFund("mock", Array()), 800, 100), 0)
  }

  it("should return correct result for worst params") {
    runTest(new Params(2, 0, Array(0, 1.0, 0)), calculateTaxesAndFees(new MockFixedFund("mock", Array()), 100 / 8.0, 100), 1)
  }

  it("should return correct result for constant params") {
    runTest(new Params(2, 0, Array(0, 0, 1.0)), calculateTaxesAndFees(new MockFixedFund("mock", Array()), 100, 100), 2)
  }

  it("should change the fund and apply fees on fund change") {
    runTest(new Params(1, 0, Array(1.0, 0, 0)), calculateTaxesAndFees(new MockFixedFund("mock", Array()), 342, 100), 1)
  }

  it("should not change fund if smoothFactor is big enough") {
    runTest(new Params(2, 20, Array(0, 1.0, 0)), calculateTaxesAndFees(new MockFixedFund("mock", Array()), 100 / 8.0, 100), 1)
  }

  def runTest(params: Params, expectedResult: Double, initialFund: Int) {
    val funds = Array[Fund](
      new MockFixedFund("best", Array(1.0, 2.0, 4.0, 8.0, 16.0)),
      new MockFixedFund("worst", Array(16.0, 8.0, 4.0, 2.0, 1.0)),
      new MockFixedFund("medium", Array(1.0, 1.0, 1.0, 1.0, 1.0))
    )

    val from = ExtendedDate.createFromDays(params.window)
    val to = ExtendedDate.createFromDays(4)

    val cc = new CostCalculator(new MovingAverageCalculator)

    // test CostCalculator.calculate
    val ccResult = cc.calculate(funds, from, to, 100, initialFund, params)
    assert(ccResult.get(to.getDayCount()).isDefined)
    (ccResult.get(to.getDayCount()).get.value) should equal(expectedResult)
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

    val cc = new CostCalculator(new MovingAverageCalculator)

    val firstAlways = new Params(window, 0, Array(1.0, 0))
    val result = cc.calculate(funds, from, to, 1.0, 0, firstAlways)
    val finalValue = calculateTaxesAndFees(new MockFixedFund("mock", Array()), records.last / records.head, 1.0)
    finalValue should be(result.get(to.getDayCount()).get.value plusOrMinus 0.000001)
  }

  it ("should be able to update values in history correctly") {
    val funds = Array[Fund](
      new MockFixedFund("best", Array(1.0, 2.0, 4.0, 8.0, 16.0)),
      new MockFixedFund("worst", Array(16.0, 8.0, 4.0, 2.0, 1.0)),
      new MockFixedFund("medium", Array(1.0, 1.0, 1.0, 1.0, 1.0))
    )

    val from = ExtendedDate.createFromDays(1)
    val to = ExtendedDate.createFromDays(4)

    val cc = new CostCalculator(new MovingAverageCalculator)

    val mockHistory = ArrayBuffer(
      new ExperimentHistoryEntry(1, None, 0, "test", Params.createRandom(3), from)
    )

    val initialValue = 1
    val initialFundIndex = 0
    val finalValue = calculateTaxesAndFees(funds(initialFundIndex), 2, initialValue)

    cc.updateExperimentHistoryValue(funds, initialValue, initialFundIndex, mockHistory)

    (mockHistory.last.value.get) should equal (finalValue)
  }

  it ("should be able to update values in history correctly #2") {
    val funds = Array[Fund](
      new MockFixedFund("best", Array(1.0, 2.0, 4.0, 8.0, 16.0)),
      new MockFixedFund("worst", Array(16.0, 8.0, 4.0, 2.0, 1.0)),
      new MockFixedFund("medium", Array(1.0, 1.0, 1.0, 1.0, 1.0))
    )

    val from = ExtendedDate.createFromDays(1)
    val to = ExtendedDate.createFromDays(4)

    val cc = new CostCalculator(new MovingAverageCalculator)
    val fundIndex = 0
    val mockHistory = ArrayBuffer(
      new ExperimentHistoryEntry(1, None, fundIndex, "test", Params.createRandom(3), from),
      new ExperimentHistoryEntry(1, None, fundIndex, "test", Params.createRandom(3), to)
    )

    val initialValue = 1
    val value = 16
    val finalValue = calculateTaxesAndFees(funds(fundIndex), value, initialValue)

    cc.updateExperimentHistoryValue(funds, initialValue, 0, mockHistory)

    (mockHistory.head.value.get) should equal (2)
    (mockHistory.last.value.get) should equal (finalValue)
  }

  def calculateTaxesAndFees(fund: Fund, value: Double, initialValue: Double): Double = {
    var valueAfterTaxes = fund.calculateSellFee(value)
    valueAfterTaxes = (valueAfterTaxes - initialValue) * 0.8 + initialValue
    return valueAfterTaxes
  }
}

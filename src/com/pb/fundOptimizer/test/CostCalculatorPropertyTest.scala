package com.pb.fundOptimizer.test

import funds.funds.{MockFixedFund, Fund}
import funds.{ExtendedDate}
import org.scalatest.FunSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.matchers.ShouldMatchers
import org.scalacheck.Gen
import util.Random
import com.pb.fundOptimizer.calculations.{CostCalculator, Params, MovingAverageCalculator}

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 11/19/12
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
class CostCalculatorPropertyTest extends FunSpec with GeneratorDrivenPropertyChecks with ShouldMatchers {

  describe("A CostCalculator") {
    it("should return (last/first) if no fund change is performed") {
      forAll {
        (window: Int, records: Array[Double]) =>
          whenever(window > 0 && records.length > 3 && records.length < 100) {

            val recordsFiltered = records.map(record => if (record > 0 && math.abs(record) < 1000000) record else Random.nextInt(1000000) + 1)
            val windowFiltered = window min (records.length - 2)

            val funds = Array[Fund](
              new MockFixedFund("test1", recordsFiltered),
              new MockFixedFund("test2", recordsFiltered.reverse)
            )

            val from = ExtendedDate.createFromDays(windowFiltered)
            val to = ExtendedDate.createFromDays(recordsFiltered.length - 1)

            val cc = new CostCalculator(new MovingAverageCalculator)

            val firstAlways = new Params(windowFiltered, 0, Array(1.0, 0))
            val result = cc.calculate(funds, from, to, 1.0, 0, firstAlways)
            (recordsFiltered.last / recordsFiltered.drop(windowFiltered - 1).head) should be(result.get(to.getDayCount()).get.value plusOrMinus 0.000001)
          }
      }
    }
  }
}

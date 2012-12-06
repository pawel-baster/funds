package test

import funds.funds.{MockFixedFund, Fund}
import funds.{Params, CostCalculator, MovingAverage}
import org.scalatest.FunSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.matchers.ShouldMatchers
import java.util.Date
import org.scalacheck.Gen
import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 11/19/12
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
class CostCalculatorPropertyTest extends FunSpec with GeneratorDrivenPropertyChecks with ShouldMatchers {

  describe("A CostCalculator") {
    it("should return last/first if no fund change is performed") {
      forAll {
        (window: Int, records: Array[Double]) =>
          whenever(window > 0 && records.length > 3 && records.length < 100) {

            val recordsFiltered = records.map(record => if (record > 0  && math.abs(record) < 1000000) record else Random.nextInt(1000000) + 1)
            val windowFiltered = if (0 < window && window < records.length - 1) window else Random.nextInt(records.length - 2) + 1

            val funds = Array[Fund](
              new MockFixedFund("test1", recordsFiltered),
              new MockFixedFund("test2", recordsFiltered.reverse)
            )

            val from = new Date()
            from.setTime(0)
            val to = new Date()
            to.setTime((recordsFiltered.length - 1) * 24L * 3600 * 1000)

            val cc = new CostCalculator(new MovingAverage)

            val firstAlways = new Params(windowFiltered, 0, Array(1.0, 0))
            val result = cc.calculate(funds, from, to, 1.0, 0, firstAlways)
            (recordsFiltered.last / recordsFiltered.head) should be (result plusOrMinus 0.000001)
          }
      }
    }
  }
}

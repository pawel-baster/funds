package test

import funds._
import _root_.funds.funds.{MockFixedFund, Fund}
import org.scalatest.FunSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.matchers.ShouldMatchers
import util.Random
import java.util.Date
import scala.Predef._

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 1/17/13
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
class FundOptimizerPropertyTest  extends FunSpec with GeneratorDrivenPropertyChecks with ShouldMatchers {

  describe("A FundOptimizer") {
    it("should choose a correct fund after sufficient number of iterations") {
      forAll {
        (records: Array[Double]) =>
          whenever(records.length > 4 && records.length < 100) {

            val recordsFiltered = records.map(record => if (record > 0  && math.abs(record) < 1000000) record else Random.nextInt(1000000) + 1)

            val sorted = recordsFiltered.sorted

            val best = new MockFixedFund("best", sorted, 1, 1)
            val worst = new MockFixedFund("worst", sorted.reverse, 1, 1)

            val bestIndex = Random.nextInt(2)

            val funds = if (bestIndex == 0) Array[Fund](best, worst) else Array[Fund](worst, best)

            val window = 2
            val from = ExtendedDate.createFromDays(window)
            val to = ExtendedDate.createFromDays(recordsFiltered.length - 1)

            val initialParams = new Params(window, 0, Array(1.0, 0))
            val initialFund = 1 - bestIndex // start with the wrong one
            val initialValue = 1
            val fundOptimizer = new FundOptimizer(new CostCalculator(new MovingAverage), funds, from, to, initialParams, initialFund, initialValue)

            val result = fundOptimizer.optimize(100)

            assert(result.trace.get(to.getDayCount()).get.value >= initialValue, printFunds(bestIndex, result))
            assert(bestIndex === result.trace.get(to.getDayCount()).get.fundIdx, printFunds(bestIndex, result))
          }
      }
    }
  }

  def printFunds(bestIndex: Int, result: FundOptimizerResult) : String = {
    return result.trace.values.foldLeft("")(_ + _.fundIdx.toString) + ":" + bestIndex
  }
}

import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/24/12
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
val funds = Array[Fund](
  new MockFixedFund("best", Array(1.0, 2.0, 4.0, 8.0, 16.0, 32.0)),
  new MockFixedFund("worst", Array(16.0, 8.0, 4.0, 2.0, 1.0, 0.5)),
  new MockFixedFund("medium", Array(1.0, 1.0, 1.0, 1.0, 1.0, 1.0))
)

val window = 2
val from = new Date()
from.setTime(24 * 3600 * 1000)
val to = new Date()
to.setTime(5 * 24 * 3600 * 1000)


println("--- test 1")

val initialParams = new Params(window, 0, Array(0, 1.0, 0))
val initialFund = 0
val initialValue = 1
val fundOptimizer = new FundOptimizer(new CostCalculator(new MovingAverage), funds, from, to, initialParams, initialFund, initialValue)

val value = fundOptimizer.optimize(1000)

assert(value == 32.0, "Expected 32, got: " + value)


println("--- test 2")

val initialParams2 = new Params(window, 0, Array(0, 1.0, 0))
val initialFund2 = 1
val initialValue2 = 1
val fundOptimizer2 = new FundOptimizer(new CostCalculator(new MovingAverage), funds, from, to, initialParams2, initialFund2, initialValue2)

val value2 = fundOptimizer2.optimize(1000)

assert(value2 == 6.84, "Expected 6.84, got: " + value2)


println("--- test 3")

val initialParams3 = new Params(window, 0, Array(0, 0, 1.0))
val initialFund3 = 2
val initialValue3 = 1
val fundOptimizer3 = new FundOptimizer(new CostCalculator(new MovingAverage), funds, from, to, initialParams3, initialFund3, initialValue3)

val value3 = fundOptimizer3.optimize(1000)

assert(value3 == 13.68, "Expected 13.68, got: " + value3)

println("OK")
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
from.setTime(0)
val to = new Date()
to.setTime(5 * 24 * 3600 * 1000)

val initialParams = new Params(window, 0, Array(0, 1.0, 0))
val initialFund = 0
val initialValue = 1
val fundOptimizer = new FundOptimizer(new CostCalculator(new MovingAverage), funds, from, to, initialParams, initialFund, initialValue)

println("--- test 1")
val value = fundOptimizer.optimize(100)

assert(value == 16.0, "Expected 16, got: " + value)

println("OK")
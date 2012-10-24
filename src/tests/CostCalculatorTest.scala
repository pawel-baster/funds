import java.util.{GregorianCalendar, Date}

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/22/12
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */

val funds = Array[Fund](
  new MockFixedFund("best", Array(1.0, 2.0, 4.0, 8.0, 16.0)),
  new MockFixedFund("worst", Array(16.0, 8.0, 4.0, 2.0, 1.0)),
  new MockFixedFund("medium", Array(1.0, 1.0, 1.0, 1.0, 1.0))
)

val window = 2
val from = new Date()
from.setTime(0)
val to = new Date()
to.setTime(4 * 24 * 3600 * 1000)

val cc = new CostCalculator(new MovingAverage)

println("--- test 1")

val good = new Params(window, Array(1.0, 0, 0))
val result1 = cc.calculate(funds, from, to, 1.0, 0, good)
assert(result1 == 8.0, "expected 16 as a result, got: " + result1)

println("--- test 2")

val bad = new Params(window, Array(0, 1.0, 0))
val result2 = cc.calculate(funds, from, to, 8.0, 1, bad)
assert(result2 == 1.0, "expected 1 as a result, got: " + result2)

println("--- test 3")

val medium = new Params(window, Array(0, 0, 1.0))
val result3 = cc.calculate(funds, from, to, 1.0, 2, medium)
assert(result3 == 1.0, "expected 1 as a result, got: " + result3)

// test fees:
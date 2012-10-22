/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/22/12
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */

val funds = Array(
  new MockFixedFund("best", Array(1.0, 2.0, 4.0, 8.0, 16.0)),
  new MockFixedFund("worst", Array(16.0, 8.0, 4.0, 2.0, 1.0)),
  new MockFixedFund("medium", Array(1.0, 1.0, 1.0, 1.0, 1.0))
)

window = 2

val cc = new CostCalculator(new MovingAverage)

val good = new Params(window, Array(1.0, 0, 0))
val result1 = cc.calculate(1.0, 1, good)
assert(result1 == 16.0, "expected 16 as a result")

val bad = new Params(window, Array(0, 1.0, 0))
val result2 = cc.calculate(16.0, 1, bad)
assert(result2 == 1.0, "expected 1 as a result")

val medium = new Params(window, Array(0, 0, 1.0))
val result3 = cc.calculate(1.0, 1, medium)
assert(result3 == 1.0, "expected 1 as a result")

// test fees:
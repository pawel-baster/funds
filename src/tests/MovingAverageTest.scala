import currencies.CurrencyDKK
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/18/12
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */

val ma = new MovingAverage
val from = new Date()
val to = new Date()
from.setTime(0)
to.setTime(10 * 24 * 3600 * 1000)
val window = 3
val funds = Array(
        new MockFund("const", 0),
        new MockFund("linear", 1),
        new FixedDepositFund(new CurrencyDKK, "test fund2", 0.01)
)

println("Expected:")

val expected = Array(
  Array(100, 101, 100.00272617997398),
  Array(100, 102,	100.00545248381603),
  Array(100, 103,	100.00817886198134),
  Array(100, 104,	100.01090531447197),
  Array(100, 105,	100.01363184128991),
  Array(100, 106,	100.01635844243721),
  Array(100, 107,	100.01908511791589),
  Array(100, 108, 100.02181186772798)
)

expected.foreach(row => {row.foreach(el => print(el + " ")); println})

println("Actual:")

val result = ma.calculate(funds, from, to, window)
result.foreach(row => {row.foreach(el => print(el + " ")); println})

assert(expected.deep == result.deep, "returned array does not match expected result")

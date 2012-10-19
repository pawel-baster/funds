import currencies.CurrencyDKK
import funds.{Fund, FixedDepositFund}
import java.util.Date

val fund1 = new FixedDepositFund(new CurrencyDKK, "test fund1", 0.02)
val fund2 = new FixedDepositFund(new CurrencyDKK, "test fund2", 0.01)

//println("test")
//println(fund1.getQuoteForDate(new Date()))
//println(fund2.getQuoteForDate(new Date()))

val ma = new MovingAverage
ma.calculate(Array(), new Date(), new Date(), 5)
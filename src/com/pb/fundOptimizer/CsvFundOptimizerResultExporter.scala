package com.pb.fundOptimizer

import _root_.funds.ExtendedDate
import _root_.funds.funds.Fund
import interfaces.{FundOptimizerResult, FundOptimizerResultExporter}

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 2/5/13
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
class CsvFundOptimizerResultSerializer extends FundOptimizerResultExporter {
  def export(funds: Array[Fund], result: FundOptimizerResult) {
    result.trace.foreach{
      case (dayCount, entry) => {
        print("\"" + ExtendedDate.createFromDays(dayCount).format("yyy-mm-dd") + "\";")
        print(entry.value + ";")
        print(funds(entry.fundIdx).shortName + ";")
        println
      }
    }
  }
}

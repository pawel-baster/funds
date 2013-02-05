package com.pb.fundOptimizer.interfaces

import funds.ExtendedDate
import funds.funds.Fund

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 2/5/13
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class FundOptimizerResultExporter {
  def export(funds: Array[Fund], result: FundOptimizerResult)
}

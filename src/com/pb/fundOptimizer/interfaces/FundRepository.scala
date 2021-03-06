package com.pb.fundOptimizer.interfaces

import funds.funds.Fund

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
abstract class FundRepository extends Serializable {
  def getFund(name: String): Fund
  def needsSaving(): Boolean
  def markAsSaved()
}

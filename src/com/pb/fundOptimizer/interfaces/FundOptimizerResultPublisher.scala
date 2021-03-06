package com.pb.fundOptimizer.interfaces

import funds.ExtendedDate
import funds.funds.Fund
import com.pb.fundOptimizer.Experiment

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 2/5/13
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class FundOptimizerResultPublisher {
  def publish(experiment: Experiment, result: FundOptimizerResult)
  def publishDigest(experiments: Map[String, Experiment])
}

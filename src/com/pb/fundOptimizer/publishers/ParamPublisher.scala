package com.pb.fundOptimizer.publishers

import com.pb.fundOptimizer.interfaces.{FundOptimizerResult, FundOptimizerResultPublisher}
import com.pb.fundOptimizer.{ExperimentHistoryEntry, Experiment}
import funds.ExtendedDate
import funds.funds.Fund
import java.io.{FileWriter, File}
import com.pb.fundOptimizer.logging.logger
import collection.mutable.ArrayBuffer
import com.pb.fundOptimizer.serializers.JavaSerializer
import com.pb.fundOptimizer.calculations.Params

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 14.04.13
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
class ParamPublisher(
                      val dirPath: String
                      ) extends FundOptimizerResultPublisher {
  def publish(experiment: Experiment, result: FundOptimizerResult) {
    val file = new File(dirPath + File.separator + experiment.name + "_best_params.dat")
    val paramSerializer = new JavaSerializer[Params]
    paramSerializer.serialize(experiment.experimentHistory.last.params, file)
  }

  def publishDigest(experiments: Map[String, Experiment]) { }
}
















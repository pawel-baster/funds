package com.pb.fundOptimizer.publishers

import _root_.funds.ExtendedDate
import _root_.funds.funds.Fund
import com.pb.fundOptimizer.interfaces.{FundRepository, FundOptimizerResult, FundOptimizerResultPublishers}
import java.io.{File, FileWriter}
import com.pb.fundOptimizer.logging.logger
import com.pb.fundOptimizer.{ExperimentHistoryEntry, Experiment}
import collection.mutable.ArrayBuffer

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 2/5/13
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
class CsvFundOptimizerResultPublisher(
                                       val dirPath: String
                                       ) extends FundOptimizerResultPublishers {
  def publish(experiment: Experiment, result: FundOptimizerResult) {
    val filenamePrefix = dirPath + File.separator + experiment.name + "_";
    logger.info("Saving results to " + filenamePrefix + "*")
    publishExperimentHistory(experiment.experimentHistory, filenamePrefix)
    publishLastBestParams(experiment, filenamePrefix)
    publishOptimizedHistory(experiment.funds, result, filenamePrefix)
  }

  def publishExperimentHistory(experimentHistory: ArrayBuffer[ExperimentHistoryEntry], filenamePrefix: String) {
    val filename = filenamePrefix + "experiment_history.csv"
    val fw = new FileWriter(filename)
    experimentHistory.foreach{
      entry => {
        var line = "\"" + entry.date.format("yyyy-MM-dd") + "\";"
        line = line + entry.value.get + ";"
        line = line + entry.fundName + ";\n"
        fw.write(line)
      }
    }
    fw.close()
  }

  def publishLastBestParams(experiment: Experiment, filenamePrefix: String) {
    val filename = filenamePrefix + "best_params.csv"
    val fw = new FileWriter(filename)
    fw.write(experiment.experimentHistory.last.params.toString())
    fw.close()
  }

  def publishOptimizedHistory(experimentFunds: Array[Fund], result: FundOptimizerResult, filenamePrefix: String) {
    val filename = filenamePrefix + "best_history.csv"
    val fw = new FileWriter(filename)
    result.trace.foreach{
      case (dayCount, entry) => {
        val date = ExtendedDate.createFromDays(dayCount);
        var line = date.format("yyy-mm-dd") + ";"
        line += entry.value + ";"
        line += experimentFunds(entry.fundIdx).shortName + ";"
        line += experimentFunds(entry.fundIdx).getQuoteForDate(date).get + "\n"
        fw.write(line)
      }
    }
    fw.close()
  }
}

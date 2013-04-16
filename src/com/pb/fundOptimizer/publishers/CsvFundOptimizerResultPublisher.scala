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
    publishDailyExperimentHistory(experiment.experimentHistory, filenamePrefix)
    publishExperimentHistory(experiment.experimentHistory.last, filenamePrefix)
    publishLastBestParams(experiment, filenamePrefix)
    publishOptimizedHistory(experiment.funds, result, experiment.experimentHistory, filenamePrefix)
  }

  def publishDailyExperimentHistory(experimentHistory: ArrayBuffer[ExperimentHistoryEntry], filenamePrefix: String) {
    val filename = filenamePrefix + "experiment_history_daily.csv"
    val fw = new FileWriter(filename)
    experimentHistory.foreach{
      entry => {
        var line = "\"" + entry.date.format("yyyy-MM-dd") + "\";"
        line += entry.value.get + ";"
        line += entry.fundName + ";"
        line += entry.bestValue + ";"
        line += entry.iterationCount + ";"
        fw.write(line + "\n")
      }
    }
    fw.close()
  }

  def publishExperimentHistory(lastEntry: ExperimentHistoryEntry, filenamePrefix: String) {
    val filename = filenamePrefix + "experiment_history.csv"
    // TODO: append
    val fw = new FileWriter(filename)
    val line = List("\"" + lastEntry.date.format("yyyy-MM-dd"), lastEntry.value.get, lastEntry.fundName, lastEntry.bestValue, lastEntry.iterationCount).mkString(",")
    fw.write(line + "\n")
    fw.close()
  }

  def publishLastBestParams(experiment: Experiment, filenamePrefix: String) {
    val filename = filenamePrefix + "best_params.csv"
    val fw = new FileWriter(filename)
    fw.write(experiment.experimentHistory.last.params.toString + "\n")
    fw.write(experiment.experimentHistory.last.bestValue.toString)
    fw.close()
  }

  def publishOptimizedHistory(experimentFunds: Array[Fund], result: FundOptimizerResult, experimentHistory: ArrayBuffer[ExperimentHistoryEntry], filenamePrefix: String) {
    val filename = filenamePrefix + "best_history.csv"
    val fw = new FileWriter(filename)
    val experimentHistoryMap = experimentHistory.map{ entry => (entry.date.getDayCount(), entry.value)} toMap

    result.trace.foreach{
      case (dayCount, entry) => {
        val date = ExtendedDate.createFromDays(dayCount)
        var line = date.format("yyyy-MM-dd") + ";"
        line += entry.value + ";"
        line += experimentFunds(entry.fundIdx).shortName + ";"
        line += experimentFunds(entry.fundIdx).getQuoteForDate(date).get + ";"
        line += experimentHistoryMap.getOrElse(dayCount, "") + "\n"

        fw.write(line)
      }
    }
    fw.close()
  }

  def publishDigest(experiments: Map[String, Experiment]) {
    val filename = dirPath + File.separator + "digest.html"
    val fw = new FileWriter(filename)
    fw.write("<html><body><table>")
    experiments.values.foreach {
      experiment => {
        val lastHistoryEntry = experiment.experimentHistory.last
        val lastChange = experiment.experimentHistory.reverse.indexWhere( item => { item.fundName != lastHistoryEntry.fundName })
        val lastChangeDescription = if (lastChange > 0) "(" + lastChange + ")" else "(b/z)"
        val values = List(experiment.name, lastHistoryEntry.fundName, lastChangeDescription, "%1.2f" format lastHistoryEntry.value.getOrElse(0), "%1.2f" format lastHistoryEntry.bestValue, lastHistoryEntry.date.format("yyyy-MM-dd"))
        fw.write("<tr><td>" + values.mkString("</td><td>") + "</td><tr>")
        println(values.mkString(", "))
      }
    }
    fw.write("</table>")

    List("best_history.svg", "experiment_history_daily.svg", "experiment_history_with_best_daily.svg").foreach{
      imageName => fw.write("<a href='" + imageName + "'><img src='" + imageName + "' width=640 /></a><br>")
    }

    fw.write("<p>generated at " + new ExtendedDate().format("yyyy-MM-dd HH:mm:ss") + "</p>")
    fw.write("</body></html>")
    fw.close()
  }
}

package com.pb.fundOptimizer.publishers

import _root_.funds.ExtendedDate
import _root_.funds.funds.Fund
import com.pb.fundOptimizer.interfaces.{FundOptimizerResult, FundOptimizerResultPublishers}
import java.io.{File, FileWriter}
import com.pb.fundOptimizer.logging.logger
import com.pb.fundOptimizer.Experiment

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
  def publish(experiment: Experiment) {
    val path = dirPath + File.separator + experiment.name + ".csv";
    logger.info("Saving results to " + path)
    val fw = new FileWriter(path)
    experiment.experimentHistory.foreach{
      entry => {
        var line = "\"" + entry.date.format("yyyy-MM-dd") + "\";"
        line = line + entry.value.get + ";"
        line = line + entry.fundName + ";\n"
        fw.write(line)
      }
    }
    fw.close()
  }
/*
  def export(funds: Array[Fund], result: FundOptimizerResult, filename: String) {
    val path = dirPath + File.separator + filename
    val fw = new FileWriter(path)
    logger.info("Saving results to " + path)
    result.trace.foreach {
      case (dayCount, entry) => {
        var line = "\"" + ExtendedDate.createFromDays(dayCount).format("yyy-mm-dd") + "\";"
        line = line + entry.value + ";"
        line = line + funds(entry.fundIdx).shortName + ";"
        fw.write(line)
      }
    }
    fw.close()
  }*/
}

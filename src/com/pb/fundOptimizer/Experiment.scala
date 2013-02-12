package com.pb.fundOptimizer

import _root_.funds.{ExtendedDate}
import _root_.funds.funds.Fund
import calculations.Params
import interfaces.{FundOptimizerResultPublishers, FundOptimizer}
import logging.logger
import java.util.Date
import collection.mutable
import collection.mutable.ArrayBuffer

class ExperimentHistoryEntry(
  val bestValue: Double,
  val value: Double,
  val fundIndex: Int,
  val fundName: String,
  val params: Params,
  val date: ExtendedDate = new ExtendedDate
                            ) extends Serializable {
  override def toString: String = {
    return date.format("yyy-mm-dd") + " bestValue= " + bestValue + ", value=" + value + ", fundName=" + fundName + ", params=" + params.toString()
  }
}

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
class Experiment(
                  val name: String,
                  val funds: Array[Fund],
                  val from: ExtendedDate,
                  val to: ExtendedDate,
                  val initialParams: Params,
                  val initialFund: Int,
                  val initialValue: Double
                  ) extends Serializable {

  var experimentHistory = ArrayBuffer[ExperimentHistoryEntry]()

  def optimize(fundOptimizer: FundOptimizer, resultSerializer: FundOptimizerResultPublishers, initialCount: Int = 100) {

    val lastHistoryEntry = experimentHistory.lastOption

    var params = Params.createRandom(funds.length)

    if (lastHistoryEntry.isDefined) {
      logger.info("before optimizing. Recorded best value: " + lastHistoryEntry.get.bestValue + ", Iteration count: " + initialCount + ", Params: " + lastHistoryEntry.get.params)
      params = lastHistoryEntry.get.params
    }

    val result = fundOptimizer.optimize(funds, from, to, initialFund, params, initialValue, initialCount)
    val newFundIndex = result.trace.last._2.fundIdx
    val newFundName = funds(newFundIndex).shortName

    val value = if (lastHistoryEntry.isDefined) fundOptimizer.calculateValue(funds, lastHistoryEntry.get, newFundIndex) else initialValue

    // ensure one entry per day
    if (lastHistoryEntry.isDefined && lastHistoryEntry.get.date.getDayCount() == new ExtendedDate().getDayCount()) {
      experimentHistory = experimentHistory.init // remove last element
    }

    val newHistoryEntry = new ExperimentHistoryEntry(result.value, value, newFundIndex, newFundName, result.bestParams)
    experimentHistory += newHistoryEntry

    val historyLog = experimentHistory.map{ _.toString }.mkString("\n")
    logger.info(historyLog)

    //resultSerializer.export(experimentHistory, name)
  }
}

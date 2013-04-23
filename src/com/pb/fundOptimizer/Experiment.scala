package com.pb.fundOptimizer

import _root_.funds.{ExtendedDate}
import _root_.funds.funds.Fund
import calculations.Params
import interfaces.{FundOptimizerResult, FundOptimizerResultPublisher, FundOptimizer}
import logging.logger
import java.util.Date
import collection.mutable
import collection.mutable.ArrayBuffer
import com.sun.corba.se.spi.transport.IIOPPrimaryToContactInfo
import util.Random

class ExperimentHistoryEntry(
  val bestValue: Double,
  var value: Option[Double],
  val fundIndex: Int,
  val fundName: String,
  val params: Params,
  val iterationCount: Int,
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
@SerialVersionUID(-5184925708783380462l)
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
  var deviation = 1.0

  def optimize(fundOptimizer: FundOptimizer, iterationCount: Int): FundOptimizerResult = {

    val lastHistoryEntry = experimentHistory.lastOption

    var params = initialParams

    deviation += 1/deviation
    val randomDeviation = Random.nextDouble() * deviation

    if (lastHistoryEntry.isDefined) {
      logger.info("before optimizing. Recorded best value: " + lastHistoryEntry.get.bestValue + ", Iteration count: " + iterationCount + ", Params: " + lastHistoryEntry.get.params + ", deviation used: " + randomDeviation)
      params = lastHistoryEntry.get.params
    }

    val result = fundOptimizer.optimize(funds, from, to, initialFund, params, initialValue, iterationCount, randomDeviation)
    val newFundIndex = result.trace.last._2.fundIdx
    val newFundName = funds(newFundIndex).shortName

    var newIterationCount = iterationCount

    // ensure one entry per day
    if (lastHistoryEntry.isDefined && lastHistoryEntry.get.date.getDayCount() == new ExtendedDate().getDayCount()) {
      experimentHistory = experimentHistory.init // remove last element
      newIterationCount += lastHistoryEntry.get.iterationCount
    }

    val newHistoryEntry = new ExperimentHistoryEntry(result.value / initialValue, None, newFundIndex, newFundName, result.bestParams, newIterationCount)
    experimentHistory += newHistoryEntry

    fundOptimizer.updateExperimentHistoryValue(funds, initialValue, initialFund, experimentHistory)
    return result
  }

  def extractParamsMap(): Map[String, Double] = {
    require(funds.length == initialParams.coefs.length)
    val fundNames = funds.map(_.shortName)
    val map = (fundNames zip initialParams.coefs).toMap
    //map.foreach{ case(name, coef) => print(name + " -> " + coef + "\n") }
    //throw new Exception()
    return map
  }
}

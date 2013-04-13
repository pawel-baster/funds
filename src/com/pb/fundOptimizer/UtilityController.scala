package com.pb.fundOptimizer

import _root_.funds.ExtendedDate
import _root_.funds.funds.UpdatableFund
import fundOptimizer.downloaders.RemoteDownloader
import fundOptimizer.Model
import funds.MbankFundRepository
import java.io.File
import publishers.CsvFundOptimizerResultPublisher
import serializers.JavaSerializer

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 3/21/13
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
class UtilityController {
}

object UtilityController {
  def main(args: Array[String]): Unit = {
    extractMBankQoutes
  }

  def extractMBankQoutes() {
    val file = new File("data/model.dat")
    val modelSerializer = new JavaSerializer[Model]
    if (!file.exists()) {
      throw new Exception("File does not exist")
    }
    val model = modelSerializer.unserialize(file)
   /*
    val funds = model.fundRepository.asInstanceOf[MbankFundRepository].funds

    val minDate = funds.values.map( fund => fund.asInstanceOf[UpdatableFund].dateMin.getOrElse(new ExtendedDate).getDayCount() ).min
    val maxDate = funds.values.map( fund => fund.asInstanceOf[UpdatableFund].dateMax.getOrElse(new ExtendedDate).getDayCount() ).max

        println(funds.values.map(fund => fund.shortName).mkString(","))
    for (day <- minDate to maxDate) {
      val date = ExtendedDate.createFromDays(day)
      println(date.format("yyyy-MM-dd") + "," + funds.values.map(fund => fund.getQuoteForDate(date).getOrElse("NaN")).mkString(","))
    }*/
  }
}

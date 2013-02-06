package fundOptimizer

import _root_.funds.currencies.{CurrencyPLN, CurrencyDKK}
import _root_.funds.downloaders.Downloader
import _root_.funds.funds.{Fund, FixedDepositFund}
import _root_.funds.{ExtendedDate}
import com.pb.fundOptimizer.Experiment
import com.pb.fundOptimizer.interfaces.{FundOptimizerResultExporter, AbstractSerializer, FundRepository, FundOptimizer}
import com.pb.fundOptimizer.funds.MbankFundRepository
import java.io.{File, ObjectOutputStream, FileOutputStream}
import com.pb.fundOptimizer.calculations.Params
import com.pb.fundOptimizer.logging.logger

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 02.02.13
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
class Model(
             val experiments: Map[String, Experiment],
             val fundRepository: FundRepository
             ) extends Serializable {

  def optimize(fundOptimizer: FundOptimizer, resultExporter: FundOptimizerResultExporter) = {
    experiments.foreach {
      case (name, experiment) => {
        logger.info("Starting experiment: " + name)
        experiment.optimize(fundOptimizer, resultExporter)
      }
    }
  }
}

object Model {

  def createMbankModel(fundRepo: FundRepository): Experiment = {

    val pln = new CurrencyPLN

    val funds: Array[Fund] = Array(
      new FixedDepositFund(pln, "deposit 3%", 0.03),
      new FixedDepositFund(pln, "deposit 2%", 0.02),
      fundRepo.getFund("AIFA"),
      fundRepo.getFund("UNIA"),
      fundRepo.getFund("ALPI"),
      //fundRepo.getFund("ISUR"),
      fundRepo.getFund("SFNE"),
      fundRepo.getFund("AMIS"),
      fundRepo.getFund("DWSP"),
      fundRepo.getFund("SKDE"),
      fundRepo.getFund("PFOA"),
      //fundRepo.getFund("IGLO"),
      fundRepo.getFund("ALAP"),
      fundRepo.getFund("SSAK")
    )

    val from = ExtendedDate.createFromString("01-01-2000", "mm-dd-yyy")
    val to = new ExtendedDate() //.addDays(-10)
    val params = Params.createRandom(funds.length)
    val initialFund = 0
    val initialValue = 1000

    return new Experiment(funds, from, to, params, initialFund, initialValue)
  }
}
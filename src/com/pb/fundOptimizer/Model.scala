package fundOptimizer

import _root_.funds.currencies.CurrencyDKK
import _root_.funds.downloaders.Downloader
import _root_.funds.funds.{Fund, FixedDepositFund}
import _root_.funds.{Params, ExtendedDate}
import com.pb.fundOptimizer.Experiment
import com.pb.fundOptimizer.interfaces.{AbstractSerializer, FundRepository, FundOptimizer}
import com.pb.fundOptimizer.funds.MbankFundRepository
import java.io.{ObjectOutputStream, FileOutputStream}

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 02.02.13
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
class Model(
             val serializer: AbstractSerializer[Model],
             val experiments: Array[Experiment],
             val fundRepository: FundRepository,
             val fileName: String
             ) {

  def optimize(fundOptimizer: FundOptimizer) = {
    experiments.foreach {
      _.optimize(fundOptimizer)
    }
  }

  def serialize() = {
    serializer.serialize(this, fileName)
  }
}

object Model {
  def unserializeOrNew(serializer: AbstractSerializer[Model], fileName: String, downloader: Downloader, fundRepo: FundRepository): Model = {
    val experiment = createMbankModel(fundRepo)
    // @todo: remember about updating end date
    return new Model(serializer, Array(experiment), fundRepo, "data/model.dat")
  }

  def createMbankModel(fundRepo: FundRepository): Experiment = {

    val dkk = new CurrencyDKK

    val funds: Array[Fund] = Array(
      new FixedDepositFund(dkk, "deposit 3%", 0.03),
      new FixedDepositFund(dkk, "deposit 2%", 0.02)
      //fundRepo.getFund("AIFA"),
      //fundRepo.getFund("UNIA")
      /*fundRepo.getFund("ALPI"),
      fundRepo.getFund("ISUR"),
      fundRepo.getFund("SFNE"),
      fundRepo.getFund("AMIS"),
      fundRepo.getFund("DWSP"),
      fundRepo.getFund("SKDE"),
      fundRepo.getFund("PFOA"),
      fundRepo.getFund("IGLO"),
      fundRepo.getFund("ALAP"),
      fundRepo.getFund("SSAK")*/
    )

    val from = ExtendedDate.createFromString("01-01-2000", "mm-dd-yyy")
    val to = new ExtendedDate().addDays(-10)
    val params = Params.createRandom(funds.length)
    val initialFund = 0
    val initialValue = 1000

    return new Experiment(funds, from, to, params, initialFund, initialValue)
  }
}
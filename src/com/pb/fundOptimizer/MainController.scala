package fundOptimizer

import _root_.funds.downloaders.Downloader
import com.pb.fundOptimizer.funds.MbankFundRepository
import downloaders.RemoteDownloader
import com.pb.fundOptimizer.logging.logger
import com.pb.fundOptimizer.serializers.JavaSerializer
import java.io.File
import scala.Array
import com.pb.fundOptimizer.publishers.CsvFundOptimizerResultPublisher
import com.pb.fundOptimizer.calculations.{AlternatingMAFundOptimizer, CostCalculator, MovingAverageCalculator, MAFundOptimizer}

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
class MainController {

}

object MainController {
  def main(args: Array[String]): Unit = {
    logger.info("MainController::main - start")
    val downloader = new RemoteDownloader
    val fundRepo = new MbankFundRepository(downloader)
    val file = new File("data/model.dat")
    val modelSerializer = new JavaSerializer[Model]
    val resultPublisher = new CsvFundOptimizerResultPublisher("data")
    val model = if (file.exists()) modelSerializer.unserialize(file)
    else new Model(Map(), fundRepo)

    if (!model.experiments.contains("MbankExperiment")) {
      model.experiments += "MbankExperiment" -> Model.createMbankModel(fundRepo)
    }

    if (!model.experiments.contains("BestRankedMbankExperiment")) {
      model.experiments += "BestRankedMbankExperiment" -> Model.createBestRankedMbankModel(fundRepo)
    }

    if (!model.experiments.contains("MbankFullExperiment")) {
      model.experiments += "MbankFullExperiment" -> Model.createMbankModelFull(fundRepo)
    }

    if (!model.experiments.contains("MBankWardModel")) {
      model.experiments += "MBankWardModel" -> Model.createWardMbankModel(fundRepo)
    }

    val maCalculator = new MovingAverageCalculator()
    val costCalculator = new CostCalculator(maCalculator)
    val maFundOptimizer = new AlternatingMAFundOptimizer(costCalculator)
    model.optimize(maFundOptimizer, resultPublisher)
    modelSerializer.serialize(model, file)
    logger.info("MainController::main - done")
  }
}
package fundOptimizer

import _root_.funds.downloaders.Downloader
import com.pb.fundOptimizer.funds.MbankFundRepository
import downloaders.RemoteDownloader
import com.pb.fundOptimizer.logging.logger
import com.pb.fundOptimizer.serializers.JavaSerializer
import java.io.File
import scala.Array
import com.pb.fundOptimizer.publishers.CsvFundOptimizerResultPublisher
import com.pb.fundOptimizer.calculations.{CostCalculator, MovingAverageCalculator, MAFundOptimizer}

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
    val resultExporter = new CsvFundOptimizerResultPublisher("data")
    val model = if (file.exists()) modelSerializer.unserialize(file)
    else {
      // @todo: remember about updating end date
      val experiment = Model.createMbankModel(fundRepo)
      new Model(Map("MbankExperiment" -> experiment), fundRepo)
    }
    val maCalculator = new MovingAverageCalculator()
    val costCalculator = new CostCalculator(maCalculator)
    val maFundOptimizer = new MAFundOptimizer(costCalculator)
    model.optimize(maFundOptimizer, resultExporter)
    modelSerializer.serialize(model, file)
  }
}
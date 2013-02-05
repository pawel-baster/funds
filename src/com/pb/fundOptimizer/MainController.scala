package fundOptimizer

import _root_.funds.downloaders.Downloader
import _root_.funds.{MovingAverageCalculator, CostCalculator, MAFundOptimizer}
import com.pb.fundOptimizer.funds.MbankFundRepository
import downloaders.RemoteDownloader
import com.pb.fundOptimizer.logging.logger
import com.pb.fundOptimizer.serializers.JavaSerializer
import java.io.File
import com.pb.fundOptimizer.CsvFundOptimizerResultSerializer

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
    val repo = new MbankFundRepository(downloader)
    val file = new File("data/model.dat")
    val modelSerializer = new JavaSerializer[Model]
    val resultSerializer = new CsvFundOptimizerResultSerializer
    val model = Model.unserializeOrNew(modelSerializer, resultSerializer, file, downloader, repo)
    val maCalculator = new MovingAverageCalculator()
    val costCalculator = new CostCalculator(maCalculator)
    val maFundOptimizer = new MAFundOptimizer(costCalculator)
    model.optimize(maFundOptimizer)
    model.serialize
  }
}
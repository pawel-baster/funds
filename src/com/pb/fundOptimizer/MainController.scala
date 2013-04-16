package fundOptimizer

import _root_.funds.downloaders.Downloader
import com.pb.fundOptimizer.funds.MbankFundRepository
import downloaders.RemoteDownloader
import com.pb.fundOptimizer.logging.logger
import com.pb.fundOptimizer.serializers.JavaSerializer
import java.io.File
import scala.Array
import com.pb.fundOptimizer.publishers.{ParamPublisher, CsvFundOptimizerResultPublisher}
import com.pb.fundOptimizer.calculations.{AlternatingMAFundOptimizer, CostCalculator, MovingAverageCalculator, MAFundOptimizer}
import com.pb.fundOptimizer.interfaces.FundRepository
import com.pb.fundOptimizer.SimpleLock

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
    val lock = new SimpleLock(new File("data/lock"))
    if (lock.acquire()) {
      try {
        val downloader = new RemoteDownloader
        val fundRepo = new MbankFundRepository(downloader)
        val file = new File("data/model.dat")
        val modelSerializer = new JavaSerializer[Model]
        val resultPublishers = List(new CsvFundOptimizerResultPublisher("data"), new ParamPublisher("data"))
        val model = if (file.exists()) modelSerializer.unserialize(file)
        else new Model(Map())

        model.addMissingExperiments(fundRepo)

        val maCalculator = new MovingAverageCalculator()
        val costCalculator = new CostCalculator(maCalculator)
        val maFundOptimizer = new AlternatingMAFundOptimizer(costCalculator)

        val iterationCount = if (args.length == 1) args(0).toInt else 10
        model.optimize(maFundOptimizer, resultPublishers, iterationCount)
        modelSerializer.serialize(model, file)
        logger.info("MainController::main - done")
      } finally {
        lock.release()
      }
    } else {
      logger.info("Lock not acquired. Exiting...")
    }
  }
}
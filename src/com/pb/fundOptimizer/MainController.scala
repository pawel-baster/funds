package fundOptimizer

import _root_.funds.downloaders.Downloader
import _root_.funds.{MovingAverageCalculator, CostCalculator, MAFundOptimizer}
import com.pb.fundOptimizer.funds.MbankFundRepository
import downloaders.RemoteDownloader
import com.pb.fundOptimizer.logging.logger

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
    val filename = "data/model.dat"
    val model = Model.unserializeOrNew(filename, downloader, repo)
    val maCalculator = new MovingAverageCalculator()
    val costCalculator = new CostCalculator(maCalculator)
    val maFundOptimizer = new MAFundOptimizer(costCalculator)
    model.optimize(maFundOptimizer)
    model.serialize
  }
}
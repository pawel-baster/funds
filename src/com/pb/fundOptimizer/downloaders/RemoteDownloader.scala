package fundOptimizer.downloaders

import funds.downloaders.Downloader
import io.{Source, BufferedSource}

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 1/28/13
 * Time: 9:58 PM
 * To change this template use File | Settings | File Templates.
 */
class RemoteDownloader(
                        ) extends Downloader {
  def download(url: String, data: String = ""): BufferedSource = Source.fromURL(url)
}

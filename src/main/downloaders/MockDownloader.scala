package funds.downloaders

import io.{BufferedSource, Source}

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/30/12
 * Time: 7:21 AM
 * To change this template use File | Settings | File Templates.
 */
class MockDownloader(
                      val filename: String
                      ) extends Downloader {
  def download(ignored_url: String, data: String = ""): BufferedSource = Source.fromFile(filename)
}

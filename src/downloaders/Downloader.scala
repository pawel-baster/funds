import io.BufferedSource

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/30/12
 * Time: 7:20 AM
 * To change this template use File | Settings | File Templates.
 */
abstract class Downloader {
  def download(url: String, data: String = ""): BufferedSource
}

package funds.funds

import funds.currencies.{Currency, CurrencyDKK}
import funds.downloaders.Downloader

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/29/12
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class UpdatableFund (
  override val shortName: String,
  override val currency: Currency,
  val downloader: Downloader
) extends Fund(currency, shortName) {
  def update()
}

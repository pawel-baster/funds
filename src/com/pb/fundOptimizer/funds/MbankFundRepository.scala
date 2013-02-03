package com.pb.fundOptimizer.funds

import com.pb.fundOptimizer.interfaces.FundRepository
import fundOptimizer.funds.MbankFund
import funds.downloaders.Downloader
import funds.funds.Fund

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
class MbankFundRepository(
                           val downloader: Downloader
                           ) extends FundRepository {
  val fundCodes = Array("PKCS", "ALPI", "UWIB", "GTPK", "DWSP", "SSAK", "NOAM", "PERA", "PSAP", "PMIS",
    "DGOL", "PFOA", "DWZK", "SFNE", "SRSU", "ALBD", "ALAP", "AMIS", "ISUR", "IGLO", "UAME", "PSAZ",
    "APAK", "APNE", "APAS", "APZO", "PZMS", "AIFA", "UNIA", "GTAK", "SKDE", "PZUP")

  val funds: Map[String, Fund] = fundCodes.map {
    code => (code, new MbankFund(downloader, code, code))
  }.toMap

  def getFund(fundName: String): Fund = {
    return funds.get(fundName).get
  }
}

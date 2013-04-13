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
  val fundCodes = Array("0422", "HSTR", "HTHE", "DTUR", "ISNR", "SKSN", "ISFP", "UTTD", "ISJL", "NOTM", "ISSW", "0425",
    "IGDK", "HAJC", "HEYB", "IANE", "SANE", "KH2A", "UONE", "WAPF", "NOMI", "HSCC", "IGSD", "HESC", "BOCH", "APAS",
    "0424", "I3FL", "SKAF", "PZUZ", "HCCC", "PSSW", "IWAG", "SKAW", "ISDU", "KH3A", "IAKC", "SKAA", "UAME", "PZUP",
    "HGEE", "IESD", "INGA", "ILOK", "SOOP", "HEMB", "UNIO", "INGZ", "IOBL", "SKAO", "INGO", "INGS", "HUKE", "DAGR",
    "IDEP", "SKDE", "NOAK", "AXA5", "NOST", "ALWA", "HUSE", "AIFO", "AXA8", "HEUG", "UNIZ", "PZUM", "AXA4", "HSEE",
    "PKCO", "PKCE", "0420", "WSEA", "DWDP", "UNSW", "PKCP", "HAJD", "PZMS", "PPDP", "IAGR", "MAGR", "0426", "AISW",
    "DWEM", "KHSE", "ISTA", "MSTA", "DWZR", "0427", "APOB", "AXA7", "PKCA", "AIRP", "APZA", "UNIA", "HHKE", "HSGE",
    "HGLE", "NOAM", "PMIS", "AXA3", "PSTP", "INGM", "IAAL", "SAKA", "PZNE", "CARS", "WPPF", "CAEU", "0428", "PFOA",
    "PZBI", "PKCZ", "PSAP", "ALPI", "WGCB", "DWAK", "PZRP", "PZUK", "DWA+", "ALOB", "KH4A", "IPRW", "BOBG", "D25M",
    "PMSJ", "0421", "MINB", "CAAM", "NOGR", "APSW", "PZUG", "PKCS", "AXA2", "UNIP", "INGG", "CADP", "PKRP", "AXA6",
    "CASW", "KH1A", "IKAS", "SKAK", "APPI", "DWSP", "CASK", "ISEL", "SFNE", "CAZR", "NOSK", "ISNA", "CADZ", "MIND",
    "HMLD", "AIOZ", "UMSS", "DWSG", "UNTT", "AINE", "IMOK", "AXA1", "PRWS", "DW50", "PSAL", "PBIG", "ISOK", "SKOR",
    "DSNB", "APOS", "BAGL", "PSAZ", "WEEB", "UANE", "USPP", "IMSP", "SKMS", "PPDU", "APAK", "PDLG", "UDOL", "DZRW",
    "SALT", "HCPB", "PARA", "WMEP", "IZGR", "SKAD", "HCHE", "HTWE", "HGCP", "ALSW", "AIFA", "ALAK", "PJRA", "CISB",
    "HJPE", "WCRB", "PANE", "AIMS", "HHGM", "DSII", "IBNP", "ALAA", "IROS", "APCH", "APNE", "APRW", "ICHI", "HKOE",
    "DIIC", "PSZZ", "HINE", "ALAP", "HAJE", "DALA", "ILRW", "PERA", "UWIB", "HGEP", "APZS", "WIPF", "HLAF", "HEME",
    "HERA", "BSFI", "HLAE", "PSSG", "DWZK", "ISMS", "CAEZ", "HBME", "WCHI", "AMIS", "DROS", "WROB", "HBRB", "HRUS",
    "BGZS", "IEUR", "SKAZ", "BRIC", "ISSR", "SRSU", "ISSP", "ILSE", "ISAL", "HBRI", "APZO", "HBEE", "ALBD", "NOAF",
    "HBRE", "PPDE", "DGOL", "PIBG")

  val fundAnnualManageFees = Map("ALPI" -> 0.01)

  val funds: Map[String, MbankFund] = fundCodes.map {
    code => (code, new MbankFund(downloader, code, code, fundAnnualManageFees.getOrElse(code, 0.04)))
  }.toMap

  def getFund(fundName: String): MbankFund = {
    if (funds.get(fundName).isEmpty) {
      throw new RuntimeException("Not registered mbank fund: " + fundName)
    }
    return funds.get(fundName).get
  }

  def needsSaving(): Boolean = {
    return funds.values.exists(_.getNeedsSaving())
  }

  def markAsSaved() {
    funds.values.foreach(_.setNeedsSaving(false))
  }
}

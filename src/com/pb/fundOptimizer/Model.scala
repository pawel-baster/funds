package fundOptimizer

import _root_.funds.currencies.{CurrencyPLN, CurrencyDKK}
import _root_.funds.downloaders.Downloader
import _root_.funds.funds.{Fund, FixedDepositFund}
import _root_.funds.{ExtendedDate}
import com.pb.fundOptimizer.Experiment
import com.pb.fundOptimizer.interfaces.{FundOptimizerResultPublishers, AbstractSerializer, FundRepository, FundOptimizer}
import com.pb.fundOptimizer.funds.MbankFundRepository
import java.io.{File, ObjectOutputStream, FileOutputStream}
import com.pb.fundOptimizer.calculations.Params
import com.pb.fundOptimizer.logging.logger

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 02.02.13
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
class Model(
             val experiments: Map[String, Experiment],
             val fundRepository: FundRepository
             ) extends Serializable {

  def optimize(fundOptimizer: FundOptimizer, resultPublisher: FundOptimizerResultPublishers) = {
    experiments.foreach {
      case (name, experiment) => {
        logger.info("Starting experiment: " + name)
        experiment.optimize(fundOptimizer)
        resultPublisher.publish(experiment)
      }
    }
  }
}

object Model {

  def createMbankModel(fundRepo: FundRepository): Experiment = {

    val pln = new CurrencyPLN

    val funds: Array[Fund] = Array(
      new FixedDepositFund(pln, "deposit 3%", 0.03),
      //new FixedDepositFund(pln, "deposit 2%", 0.02),
      fundRepo.getFund("AIFA"),
      fundRepo.getFund("UNIA"),
      fundRepo.getFund("ALPI"),
      //fundRepo.getFund("ISUR"),
      fundRepo.getFund("SFNE"),
      fundRepo.getFund("AMIS"),
      fundRepo.getFund("DWSP"),
      fundRepo.getFund("SKDE"),
      fundRepo.getFund("PFOA"),
      //fundRepo.getFund("IGLO"),
      fundRepo.getFund("ALAP")
      //fundRepo.getFund("SSAK")
    )

    val from = ExtendedDate.createFromString("01-01-2000", "mm-dd-yyy")
    val to = new ExtendedDate() //.addDays(-10)
    val params = Params.createRandom(funds.length)
    val initialFund = 0
    val initialValue = 1000

    return new Experiment("MBankExperiment", funds, from, to, params, initialFund, initialValue)
  }

  def createMbankModelFull(fundRepo: FundRepository): Experiment = {

    val pln = new CurrencyPLN

    val funds: Array[Fund] = Array(
      new FixedDepositFund(pln, "deposit 3%", 0.03),
      fundRepo.getFund("0422"),
      fundRepo.getFund("HSTR"),
      fundRepo.getFund("HTHE"),
      fundRepo.getFund("DTUR"),
      fundRepo.getFund("ISNR"),
      fundRepo.getFund("SKSN"),
      fundRepo.getFund("ISFP"),
      fundRepo.getFund("UTTD"),
      fundRepo.getFund("ISJL"),
      fundRepo.getFund("NOTM"),
      fundRepo.getFund("ISSW"),
      fundRepo.getFund("0425"),
      fundRepo.getFund("IGDK"),
      fundRepo.getFund("HAJC"),
      fundRepo.getFund("HEYB"),
      fundRepo.getFund("IANE"),
      fundRepo.getFund("SANE"),
      fundRepo.getFund("KH2A"),
      fundRepo.getFund("UONE"),
      fundRepo.getFund("WAPF"),
      fundRepo.getFund("NOMI"),
      fundRepo.getFund("HSCC"),
      fundRepo.getFund("IGSD"),
      fundRepo.getFund("HESC"),
      fundRepo.getFund("BOCH"),
      fundRepo.getFund("APAS"),
      fundRepo.getFund("0424"),
      fundRepo.getFund("I3FL"),
      fundRepo.getFund("SKAF"),
      fundRepo.getFund("PZUZ"),
      fundRepo.getFund("HCCC"),
      fundRepo.getFund("PSSW"),
      fundRepo.getFund("IWAG"),
      fundRepo.getFund("SKAW"),
      fundRepo.getFund("ISDU"),
      fundRepo.getFund("KH3A"),
      fundRepo.getFund("IAKC"),
      fundRepo.getFund("SKAA"),
      fundRepo.getFund("UAME"),
      fundRepo.getFund("PZUP"),
      fundRepo.getFund("HGEE"),
      fundRepo.getFund("IESD"),
      fundRepo.getFund("INGA"),
      fundRepo.getFund("ILOK"),
      fundRepo.getFund("SOOP"),
      fundRepo.getFund("HEMB"),
      fundRepo.getFund("UNIO"),
      fundRepo.getFund("INGZ"),
      fundRepo.getFund("IOBL"),
      fundRepo.getFund("SKAO"),
      fundRepo.getFund("INGO"),
      fundRepo.getFund("INGS"),
      fundRepo.getFund("HUKE"),
      fundRepo.getFund("DAGR"),
      fundRepo.getFund("IDEP"),
      fundRepo.getFund("SKDE"),
      fundRepo.getFund("NOAK"),
      fundRepo.getFund("AXA5"),
      fundRepo.getFund("NOST"),
      fundRepo.getFund("ALWA"),
      fundRepo.getFund("HUSE"),
      fundRepo.getFund("AIFO"),
      fundRepo.getFund("AXA8"),
      fundRepo.getFund("HEUG"),
      fundRepo.getFund("UNIZ"),
      fundRepo.getFund("PZUM"),
      fundRepo.getFund("AXA4"),
      fundRepo.getFund("HSEE"),
      fundRepo.getFund("PKCO"),
      fundRepo.getFund("PKCE"),
      fundRepo.getFund("0420"),
      fundRepo.getFund("WSEA"),
      fundRepo.getFund("DWDP"),
      fundRepo.getFund("UNSW"),
      fundRepo.getFund("PKCP"),
      fundRepo.getFund("HAJD"),
      fundRepo.getFund("PZMS"),
      fundRepo.getFund("PPDP"),
      fundRepo.getFund("IAGR"),
      fundRepo.getFund("MAGR"),
      fundRepo.getFund("0426"),
      fundRepo.getFund("AISW"),
      fundRepo.getFund("DWEM"),
      fundRepo.getFund("KHSE"),
      fundRepo.getFund("ISTA"),
      fundRepo.getFund("MSTA"),
      fundRepo.getFund("DWZR"),
      fundRepo.getFund("0427"),
      fundRepo.getFund("APOB"),
      fundRepo.getFund("AXA7"),
      fundRepo.getFund("PKCA"),
      fundRepo.getFund("AIRP"),
      fundRepo.getFund("APZA"),
      fundRepo.getFund("UNIA"),
      fundRepo.getFund("HHKE"),
      fundRepo.getFund("HSGE"),
      fundRepo.getFund("HGLE"),
      fundRepo.getFund("NOAM"),
      fundRepo.getFund("PMIS"),
      fundRepo.getFund("AXA3"),
      fundRepo.getFund("PSTP"),
      fundRepo.getFund("INGM"),
      fundRepo.getFund("IAAL"),
      fundRepo.getFund("SAKA"),
      fundRepo.getFund("PZNE"),
      fundRepo.getFund("CARS"),
      fundRepo.getFund("WPPF"),
      fundRepo.getFund("CAEU"),
      fundRepo.getFund("0428"),
      fundRepo.getFund("PFOA"),
      fundRepo.getFund("PZBI"),
      fundRepo.getFund("PKCZ"),
      fundRepo.getFund("PSAP"),
      fundRepo.getFund("ALPI"),
      fundRepo.getFund("WGCB"),
      fundRepo.getFund("DWAK"),
      fundRepo.getFund("PZRP"),
      fundRepo.getFund("PZUK"),
      fundRepo.getFund("DWA+"),
      fundRepo.getFund("ALOB"),
      fundRepo.getFund("KH4A"),
      fundRepo.getFund("IPRW"),
      fundRepo.getFund("BOBG"),
      fundRepo.getFund("D25M"),
      fundRepo.getFund("PMSJ"),
      fundRepo.getFund("0421"),
      fundRepo.getFund("MINB"),
      fundRepo.getFund("CAAM"),
      fundRepo.getFund("NOGR"),
      fundRepo.getFund("APSW"),
      fundRepo.getFund("PZUG"),
      fundRepo.getFund("PKCS"),
      fundRepo.getFund("AXA2"),
      fundRepo.getFund("UNIP"),
      fundRepo.getFund("INGG"),
      fundRepo.getFund("CADP"),
      fundRepo.getFund("PKRP"),
      fundRepo.getFund("AXA6"),
      fundRepo.getFund("CASW"),
      fundRepo.getFund("KH1A"),
      fundRepo.getFund("IKAS"),
      fundRepo.getFund("SKAK"),
      fundRepo.getFund("APPI"),
      fundRepo.getFund("DWSP"),
      fundRepo.getFund("CASK"),
      fundRepo.getFund("ISEL"),
      fundRepo.getFund("SFNE"),
      fundRepo.getFund("CAZR"),
      fundRepo.getFund("NOSK"),
      fundRepo.getFund("ISNA"),
      fundRepo.getFund("CADZ"),
      fundRepo.getFund("MIND"),
      fundRepo.getFund("HMLD"),
      fundRepo.getFund("AIOZ"),
      fundRepo.getFund("UMSS"),
      fundRepo.getFund("DWSG"),
      fundRepo.getFund("UNTT"),
      fundRepo.getFund("AINE"),
      fundRepo.getFund("IMOK"),
      fundRepo.getFund("AXA1"),
      fundRepo.getFund("PRWS"),
      fundRepo.getFund("DW50"),
      fundRepo.getFund("PSAL"),
      fundRepo.getFund("PBIG"),
      fundRepo.getFund("ISOK"),
      fundRepo.getFund("SKOR"),
      fundRepo.getFund("DSNB"),
      fundRepo.getFund("APOS"),
      fundRepo.getFund("BAGL"),
      fundRepo.getFund("PSAZ"),
      fundRepo.getFund("WEEB"),
      fundRepo.getFund("UANE"),
      fundRepo.getFund("USPP"),
      fundRepo.getFund("IMSP"),
      fundRepo.getFund("SKMS"),
      fundRepo.getFund("PPDU"),
      fundRepo.getFund("APAK"),
      fundRepo.getFund("PDLG"),
      fundRepo.getFund("UDOL"),
      fundRepo.getFund("DZRW"),
      fundRepo.getFund("SALT"),
      fundRepo.getFund("HCPB"),
      fundRepo.getFund("PARA"),
      fundRepo.getFund("WMEP"),
      fundRepo.getFund("IZGR"),
      fundRepo.getFund("SKAD"),
      fundRepo.getFund("HCHE"),
      fundRepo.getFund("HTWE"),
      fundRepo.getFund("HGCP"),
      fundRepo.getFund("ALSW"),
      fundRepo.getFund("AIFA"),
      fundRepo.getFund("ALAK"),
      fundRepo.getFund("PJRA"),
      fundRepo.getFund("CISB"),
      fundRepo.getFund("HJPE"),
      fundRepo.getFund("WCRB"),
      fundRepo.getFund("PANE"),
      fundRepo.getFund("AIMS"),
      fundRepo.getFund("HHGM"),
      fundRepo.getFund("DSII"),
      fundRepo.getFund("IBNP"),
      fundRepo.getFund("ALAA"),
      fundRepo.getFund("IROS"),
      fundRepo.getFund("APCH"),
      fundRepo.getFund("APNE"),
      fundRepo.getFund("APRW"),
      fundRepo.getFund("ICHI"),
      fundRepo.getFund("HKOE"),
      fundRepo.getFund("DIIC"),
      fundRepo.getFund("PSZZ"),
      fundRepo.getFund("HINE"),
      fundRepo.getFund("ALAP"),
      fundRepo.getFund("HAJE"),
      fundRepo.getFund("DALA"),
      fundRepo.getFund("ILRW"),
      fundRepo.getFund("PERA"),
      fundRepo.getFund("UWIB"),
      fundRepo.getFund("HGEP"),
      fundRepo.getFund("APZS"),
      fundRepo.getFund("WIPF"),
      fundRepo.getFund("HLAF"),
      fundRepo.getFund("HEME"),
      fundRepo.getFund("HERA"),
      fundRepo.getFund("BSFI"),
      fundRepo.getFund("HLAE"),
      fundRepo.getFund("PSSG"),
      fundRepo.getFund("DWZK"),
      fundRepo.getFund("ISMS"),
      fundRepo.getFund("CAEZ"),
      fundRepo.getFund("HBME"),
      fundRepo.getFund("WCHI"),
      fundRepo.getFund("AMIS"),
      fundRepo.getFund("DROS"),
      fundRepo.getFund("WROB"),
      fundRepo.getFund("HBRB"),
      fundRepo.getFund("HRUS"),
      fundRepo.getFund("BGZS"),
      fundRepo.getFund("IEUR"),
      fundRepo.getFund("SKAZ"),
      fundRepo.getFund("BRIC"),
      fundRepo.getFund("ISSR"),
      fundRepo.getFund("SRSU"),
      fundRepo.getFund("ISSP"),
      fundRepo.getFund("ILSE"),
      fundRepo.getFund("ISAL"),
      fundRepo.getFund("HBRI"),
      fundRepo.getFund("APZO"),
      fundRepo.getFund("HBEE"),
      fundRepo.getFund("ALBD"),
      fundRepo.getFund("NOAF"),
      fundRepo.getFund("HBRE"),
      fundRepo.getFund("PPDE"),
      fundRepo.getFund("DGOL"),
      fundRepo.getFund("PIBG")
    )

    val from = ExtendedDate.createFromString("01-01-2000", "mm-dd-yyy")
    val to = new ExtendedDate() //.addDays(-10)
    val params = Params.createRandom(funds.length)
    val initialFund = 0
    val initialValue = 1000

    return new Experiment("MBankFullExperiment", funds, from, to, params, initialFund, initialValue)
  }
}
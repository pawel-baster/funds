package fundOptimizer

import _root_.funds.currencies.{CurrencyPLN, CurrencyDKK}
import _root_.funds.downloaders.Downloader
import _root_.funds.funds.{Fund, FixedDepositFund}
import _root_.funds.{ExtendedDate}
import com.pb.fundOptimizer.Experiment
import com.pb.fundOptimizer.interfaces._
import com.pb.fundOptimizer.funds.MbankFundRepository
import java.io.{File, ObjectOutputStream, FileOutputStream}
import com.pb.fundOptimizer.calculations.Params
import com.pb.fundOptimizer.logging.logger
import collection.mutable.ArrayBuffer
import scala.Array

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 02.02.13
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
@SerialVersionUID(-308204942811692548l)
class Model(
             var experiments: Map[String, Experiment],
             val fundRepository: FundRepository
             ) extends Serializable {

  def optimize(fundOptimizer: FundOptimizer, resultPublisher: FundOptimizerResultPublishers, iterationCount: Int) = {
    var results = ArrayBuffer[FundOptimizerResult]()
    experiments.foreach {
      case (name, experiment) => {
        logger.info("Starting experiment: " + name)
        val result = experiment.optimize(fundOptimizer, iterationCount)
        results += result
        resultPublisher.publish(experiment, result)
        logger.info("Finished experiment: " + name + "\n")
      }
    }
    resultPublisher.publishDigest(experiments)
  }

  def addMissingExperiments(fundRepo: FundRepository) {
    val initialExperiments = List(
      Model.createGenericMbankModel(fundRepo, "Mbank-basic", // 10.672788067957082
        List("AIFA", "UNIA", "ALPI", "SFNE", "AMIS", "DWSP", "SKDE", "PFOA", "ALAP"),
        Option(new Params(258, 0.1, Array(-0.007389062822940697, 0.8774525747103762, -0.012406997679502475, 4.396475560226619, -12.543010567936044, 0.0, 0.0, 0.0, -19.039253484718053, -6.289036116633324)))
      ),

      Model.createGenericMbankModel(fundRepo, "Mbank-ward", // 2.6237115490868668
        List("UNIO", "INGO", "UNIZ", "KHSE", "UNIA", "INGZ", "KH2A"),
        Option(new Params(186, 0.1, Array(0.10270600993399664, 0.0, 0.0, 0.0, 0.0, 6.04788004496128, -3.0449583784410454, 7.24532292349778)))
      ),

      Model.createGenericMbankModel(fundRepo, "Mbank-bestRanked", // 7.617378507498258
        List("BSFI", "CARS", "BOBG", "HHGM", "HHKE", "HINE", "IAKC", "IKAS", "IWAG", "INGZ", "INGA", "INGS", "DWAK", "DWZR", "KHSE", "KH2A", "KH1A", "PKCA", "PKRP", "PKCS", "PERA", "PJRA", "PRWS", "PARA", "PMSJ", "PPDU", "PPDE", "SKAA", "SKAK", "SKAW", "UNIO", "UNIZ", "UWIB"),
        Option(new Params(76, 0.1, Array(0.3687201431198547, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -5.0864052614660675, 0.0, 0.0, 0.0, 0.0, 7.454066909318624, 0.0, 3.035904254932817, 0.0, 0.0, 0.0, -9.157166577390598, 0.0, -6.935023404309045, -4.963806617178026, -14.59418113591456, 0.0, 0.0, 0.0, 3.183523631990695, 0.0, 0.0, 0.0)))
      ),

      Model.createGenericMbankModel(fundRepo, "Mbank-full",  // 8.749365507026244
        List("0422", "HSTR", "HTHE", "DTUR", "ISNR", "SKSN", "ISFP", "UTTD", "ISJL", "NOTM", "ISSW", "0425", "IGDK", "HAJC", "HEYB", "IANE", "SANE", "KH2A", "UONE", "WAPF", "NOMI", "HSCC", "IGSD", "HESC", "BOCH", "APAS", "0424", "I3FL", "SKAF", "PZUZ", "HCCC", "PSSW", "IWAG", "SKAW", "ISDU", "KH3A", "IAKC", "SKAA", "UAME", "PZUP", "HGEE", "IESD", "INGA", "ILOK", "SOOP", "HEMB", "UNIO", "INGZ", "IOBL", "SKAO", "INGO", "INGS", "HUKE", "DAGR", "IDEP", "SKDE", "NOAK", "AXA5", "NOST", "ALWA", "HUSE", "AIFO", "AXA8", "HEUG", "UNIZ", "PZUM", "AXA4", "HSEE", "PKCO", "PKCE", "0420", "WSEA", "DWDP", "UNSW", "PKCP", "HAJD", "PZMS", "PPDP", "IAGR", "MAGR", "0426", "AISW", "DWEM", "KHSE", "ISTA", "MSTA", "DWZR", "0427", "APOB", "AXA7", "PKCA", "AIRP", "APZA", "UNIA", "HHKE", "HSGE", "HGLE", "NOAM", "PMIS", "AXA3", "PSTP", "INGM", "IAAL", "SAKA", "PZNE", "CARS", "WPPF", "CAEU", "0428", "PFOA", "PZBI", "PKCZ", "PSAP", "ALPI", "WGCB", "DWAK", "PZRP", "PZUK", "DWA+", "ALOB", "KH4A", "IPRW", "BOBG", "D25M", "PMSJ", "0421", "MINB", "CAAM",
          "NOGR", "APSW", "PZUG", "PKCS", "AXA2", "UNIP", "INGG", "CADP", "PKRP", "AXA6", "CASW", "KH1A", "IKAS", "SKAK", "APPI", "DWSP", "CASK", "ISEL", "SFNE", "CAZR", "NOSK", "ISNA", "CADZ", "MIND", "HMLD", "AIOZ", "UMSS", "DWSG", "UNTT", "AINE", "IMOK", "AXA1", "PRWS", "DW50", "PSAL", "PBIG", "ISOK", "SKOR", "DSNB", "APOS", "BAGL", "PSAZ", "WEEB", "UANE", "USPP", "IMSP", "SKMS", "PPDU", "APAK", "PDLG", "UDOL", "DZRW", "SALT", "HCPB", "PARA", "WMEP", "IZGR", "SKAD", "HCHE", "HTWE", "HGCP", "ALSW", "AIFA", "ALAK", "PJRA", "CISB", "HJPE", "WCRB", "PANE", "AIMS", "HHGM", "DSII", "IBNP", "ALAA", "IROS", "APCH", "APNE", "APRW", "ICHI", "HKOE", "DIIC", "PSZZ", "HINE", "ALAP", "HAJE", "DALA", "ILRW", "PERA", "UWIB", "HGEP", "APZS", "WIPF", "HLAF", "HEME", "HERA", "BSFI", "HLAE", "PSSG", "DWZK", "ISMS", "CAEZ", "HBME", "WCHI", "AMIS", "DROS", "WROB", "HBRB", "HRUS", "BGZS", "IEUR", "SKAZ", "BRIC", "ISSR", "SRSU", "ISSP", "ILSE", "ISAL", "HBRI", "APZO", "HBEE", "ALBD", "NOAF", "HBRE", "PPDE", "DGOL", "PIBG"),
        Option(new Params(147, 0.1, Array(0.8683821956484502, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 5.957067512977513, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -6.10247499976872, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -3.032178315071362, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -15.242629518230887, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -11.737968496661695,
          0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -6.360311055258882, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0))
        )
      ),

      // joint funds:
      Model.createGenericMbankModel(fundRepo, "MBank-alianz", List("ALAK", "AMIS", "ALAP", "ALAA", "ALOB", "ALPI", "ALWA", "ALBD", "ALSW"))
    )

    initialExperiments.foreach {
      experiment => {
        if (!experiments.contains(experiment.name)) {
          experiments += experiment.name -> experiment
        }
      }
    }
  }
}

object Model {



  def createBestRankedMbankModel(fundRepo: FundRepository): Experiment = {

    val pln = new CurrencyPLN

    val funds: Array[Fund] = Array(
      new FixedDepositFund(pln, "deposit 3%", 0.03),
      fundRepo.getFund("BSFI"),
      fundRepo.getFund("CARS"),
      fundRepo.getFund("BOBG"),
      fundRepo.getFund("HHGM"),
      fundRepo.getFund("HHKE"),
      fundRepo.getFund("HINE"),
      fundRepo.getFund("IAKC"),
      fundRepo.getFund("IKAS"),
      fundRepo.getFund("IWAG"),
      fundRepo.getFund("INGZ"),
      fundRepo.getFund("INGA"),
      fundRepo.getFund("INGS"),
      fundRepo.getFund("DWAK"),
      fundRepo.getFund("DWZR"),
      fundRepo.getFund("KHSE"),
      fundRepo.getFund("KH2A"),
      fundRepo.getFund("KH1A"),
      fundRepo.getFund("PKCA"),
      fundRepo.getFund("PKRP"),
      fundRepo.getFund("PKCS"),
      fundRepo.getFund("PERA"),
      fundRepo.getFund("PJRA"),
      fundRepo.getFund("PRWS"),
      fundRepo.getFund("PARA"),
      fundRepo.getFund("PMSJ"),
      fundRepo.getFund("PPDU"),
      fundRepo.getFund("PPDE"),
      fundRepo.getFund("SKAA"),
      fundRepo.getFund("SKAK"),
      fundRepo.getFund("SKAW"),
      fundRepo.getFund("UNIO"),
      fundRepo.getFund("UNIZ"),
      fundRepo.getFund("UWIB")
    )

    val from = ExtendedDate.createFromString("01-01-2000", "dd-MM-yyy")
    val to = new ExtendedDate()

    val params = new Params(76, 0.1, Array(0.3687201431198547, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -5.0864052614660675, 0.0, 0.0, 0.0, 0.0, 7.454066909318624, 0.0, 3.035904254932817, 0.0, 0.0, 0.0, -9.157166577390598, 0.0, -6.935023404309045, -4.963806617178026, -14.59418113591456, 0.0, 0.0, 0.0, 3.183523631990695, 0.0, 0.0, 0.0))
    val initialFund = 0
    val initialValue = 1000

    return new Experiment("BestRankedMbankExperiment", funds, from, to, params, initialFund, initialValue)
  }

  def createGenericMbankModel(fundRepo: FundRepository, name: String, fundNames: List[String], params: Option[Params] = None) : Experiment = {
    val pln = new CurrencyPLN

    val mbankFunds = (fundNames map {
      fundName => fundRepo.getFund(fundName)
    })

    val funds =  List(new FixedDepositFund(pln, "deposit 3%", 0.03)) ++ mbankFunds

    val from = ExtendedDate.createFromString("01-01-2000", "dd-MM-yyy")
    val to = new ExtendedDate()
    val initialParams = params.getOrElse(Params.createRandom(funds.length));
    val initialFund = 0
    val initialValue = 1000
    return new Experiment(name, funds.toArray, from, to, initialParams, initialFund, initialValue)
  }
}
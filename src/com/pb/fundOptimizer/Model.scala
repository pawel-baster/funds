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
import com.pb.fundOptimizer.serializers.JavaSerializer

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 02.02.13
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
class Model(
             var experiments: Map[String, Experiment]
             ) extends Serializable {

  def optimize(fundOptimizer: FundOptimizer, resultPublishers: List[FundOptimizerResultPublisher], iterationCount: Int) = {
    var results = ArrayBuffer[FundOptimizerResult]()
    experiments.foreach {
      case (name, experiment) => {
        logger.info("Starting experiment: " + name)
        val result = experiment.optimize(fundOptimizer, iterationCount)
        results += result
        resultPublishers.foreach(_.publish(experiment, result))
        logger.info("Finished experiment: " + name + "\n")
      }
    }
    resultPublishers.foreach(_.publishDigest(experiments))
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

      Model.createGenericMbankModel(fundRepo, "Mbank-bestRanked-no-woif-no-hsbc", // 13.182462253599947
        List("BSFI", "CARS", "BOBG", "IAKC", "IKAS", "IWAG", "INGZ", "INGA", "INGS", "DWAK", "DWZR", "KHSE", "KH2A", "KH1A", "PKCA", "PKRP", "PKCS", "PERA", "PJRA", "PRWS", "PARA", "PMSJ", "PPDU", "PPDE", "SKAA", "SKAK", "SKAW", "UNIO", "UNIZ", "UWIB"),
        Option(new Params(75, 0.1, Array(0.369450947003225, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -19.340007081737088, -5.288590877182875, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -2.4121363776349036, 0.0, 0.0, -5.841562948827889, -9.157166577390598, -14.238830104756005, -8.815433367599475, -4.913490746341319, -15.963798244098417, 0.0, 9.514843775340937, 0.0, -12.47032766946157, 0.0, 0.0, 0.0)))
      ),

      Model.createGenericMbankModel(fundRepo, "Mbank-full-no-woif-no-hsbc",
        List("0420", "0421", "0422", "0424", "0425", "0426", "0427", "0428", "AIFA", "AIFO", "AIMS", "AINE", "AIOZ", "AIRP", "AISW", "ALAA", "ALAK", "ALAP", "ALBD", "ALOB", "ALPI", "ALSW", "ALWA", "AMIS", "APAK", "APAS", "APCH", "APNE", "APOB", "APOS", "APPI", "APRW", "APSW", "APZA", "APZO", "APZS", "AXA1", "AXA2", "AXA3", "AXA4", "AXA5", "AXA6", "AXA7", "AXA8", "BAGL", "BGZS", "BOBG", "BOCH", "BRIC", "BSFI", "CAAM", "CADP", "CADZ", "CAEU", "CAEZ", "CARS", "CASK", "CASW", "CAZR", "CISB", "D25M", "DAGR", "DALA", "DGOL", "DIIC", "DROS", "DSII", "DSNB", "DTUR", "DW50", "DWAK", "DWDP", "DWEM", "DWSG", "DWSP", "DWZK", "DWZR", "DZRW", "I3FL", "IAAL", "IAGR", "IAKC", "IANE", "IBNP", "ICHI", "IDEP", "IESD", "IEUR", "IGDK", "IGSD", "IKAS", "ILOK", "ILRW", "ILSE", "IMOK", "IMSP", "INGA", "INGG", "INGM", "INGO", "INGS", "INGZ", "IOBL", "IPRW", "IROS", "ISAL", "ISDU", "ISEL", "ISFP", "ISJL", "ISMS", "ISNA", "ISNR", "ISOK", "ISSP", "ISSR", "ISSW", "ISTA", "IWAG", "IZGR", "KH1A", "KH2A", "KH3A", "KH4A", "KHSE", "MAGR", "MINB", "MIND", "MSTA", "NOAF", "NOAK", "NOAM", "NOGR", "NOMI", "NOSK", "NOST", "NOTM", "PANE", "PARA", "PBIG", "PDLG", "PERA", "PFOA", "PIBG", "PJRA", "PKCA", "PKCE", "PKCO", "PKCP", "PKCS", "PKCZ", "PKRP", "PMIS", "PMSJ", "PPDE", "PPDP", "PPDU", "PRWS", "PSAL", "PSAP", "PSAZ", "PSSG", "PSSW", "PSTP", "PSZZ", "PZBI", "PZMS", "PZNE", "PZRP", "PZUG", "PZUK", "PZUM", "PZUP", "PZUZ", "SAKA", "SALT", "SANE", "SFNE", "SKAA", "SKAD", "SKAF", "SKAK", "SKAO", "SKAW", "SKAZ", "SKDE", "SKMS", "SKOR", "SKSN", "SOOP", "SRSU", "UAME", "UANE", "UDOL", "UMSS", "UNIA", "UNIO", "UNIP", "UNIZ", "UNSW", "UNTT", "UONE", "USPP", "UTTD", "UWIB"),
        Option(new Params(21, 0.1, Array(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 9.122136263632036E-6, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.3246125312400507, 0.0, 0.0, 0.0, 0.0, -0.0663030303191434, 0.0, 0.0, 0.0, 0.0, 5.491393073545438, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -4.404926561126605E-6, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -9.2636974825238E-6, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -2.938389443054082, 0.0, 0.0, 0.0, 0.0, 0.0, -2.713346933106923, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.213652820399356, 0.0, -1.4687662211990478, 0.0, 0.0, -9.624788839729238,
          -5.8981681211331, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.2511157061294515E-5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.35525611751789343, 0.0, 0.0, 0.0, 0.0, 10.623494895531515, 0.0, 0.0, 0.0)))
      ),

      // joint funds:
      Model.createGenericMbankModel(fundRepo, "MBank-alianz", List("ALAK", "AMIS", "ALAP", "ALAA", "ALOB", "ALPI", "ALWA", "ALBD", "ALSW")),
      Model.createGenericMbankModel(fundRepo, "MBank-amplicoB", List("APZS", "APCH", "APZO", "APNE", "APAK", "APRW", "APPI", "APOB", "APOS", "APSW", "APZA", "APAS")),
      Model.createGenericMbankModel(fundRepo, "MBank-amplicoG", List("AIFO", "AIFA", "AIMS", "AIOZ", "AIRP", "AISW", "AINE")),
      Model.createGenericMbankModel(fundRepo, "MBank-axa", List("AXA2", "AXA1", "AXA8", "AXA7", "AXA6", "AXA5", "AXA4", "AXA3")),
      Model.createGenericMbankModel(fundRepo, "MBank-bph", List("BGZS", "CARS", "CAAM", "CAEU", "BAGL", "CAZR", "CAEZ", "CADP", "BOBG", "BOCH", "CASK", "CASW", "CADZ")),
      //Model.createGenericMbankModel(fundRepo, "MBank-hsbc", List("HAJE", "HAJC", "HAJD", "HBRB", "HBRE", "HBEE", "HBRI", "HBME", "HCHE", "HSCC", "HCCC", "HEYB", "HSEE", "HESC", "HEUG", "0422", "HGEE", "HGCP", "HEMB", "0424", "HEME", "HGEP", "HMLD", "HGLE", "HHGM", "HHKE", "HINE", "HJPE", "HKOE", "HLAE", "HLAF", "HRUS", "HSGE", "HTWE", "HTHE", "HSTR", "HUKE", "HCPB", "HUSE")),
      Model.createGenericMbankModel(fundRepo, "MBank-ing1", List("ISAL", "IESD", "IGDK", "IGSD", "INGM", "ISJL", "ISNA", "IPRW", "ILSE", "ILRW", "ISDU")),
      Model.createGenericMbankModel(fundRepo, "MBank-ing2", List("INGZ", "INGA", "ICHI", "INGG", "INGO", "IMOK", "IROS", "ISSP", "IBNP", "ISFP", "ISSW", "INGS", "ISMS")),
      Model.createGenericMbankModel(fundRepo, "MBank-investor", List("DAGR", "DALA", "BRIC", "DGOL", "DWSG", "DIIC", "DSII", "DSNB", "DROS", "DTUR", "DWZK", "DZRW")),
      Model.createGenericMbankModel(fundRepo, "MBank-legg", List("KH1A", "KH2A", "KH3A", "KH4A")),
      Model.createGenericMbankModel(fundRepo, "MBank-noble", List("NOAM", "NOAK", "NOGR", "NOMI", "NOSK", "NOST", "NOTM")),
      Model.createGenericMbankModel(fundRepo, "MBank-pko1", List("PERA", "PRWS", "PJRA", "PARA", "PMSJ", "PPDU", "PPDE")),
      Model.createGenericMbankModel(fundRepo, "MBank-pko2", List("0420", "PSAP", "PBIG", "PDLG", "PIBG", "PPDP", "0421", "PSTP", "PSSG", "PZRP")),
      Model.createGenericMbankModel(fundRepo, "MBank-pzu1", List("PZUP", "PZBI", "PZUK", "PZMS", "PZNE", "PFOA", "PZUG", "PZUM", "PZUZ")),
      Model.createGenericMbankModel(fundRepo, "MBank-pzu2", List("PSSW", "PSZZ", "PSAZ")),
      Model.createGenericMbankModel(fundRepo, "MBank-skarbiec", List("SKAA", "SANE", "SAKA", "SKAZ", "SKDE", "SKAF", "SKAK", "SKAO", "SFNE", "SKAW", "SKOR", "SRSU", "SKSN", "SOOP", "SKMS", "SKAD")),
      Model.createGenericMbankModel(fundRepo, "MBank-uni", List("UMSS", "UAME", "UANE", "USPP", "UDOL", "UNIA", "UNIO", "UNIP", "UNIZ", "0428", "UONE", "UNSW"))
//      Model.createGenericMbankModel(fundRepo, "MBank-woif", List("WAPF", "WCHI", "WCRB", "WGCB", "WEEB", "CISB", "WIPF", "WMEP", "WPPF", "WROB", "WSEA"))
    )

    initialExperiments.foreach {
      experiment => {
        if (!experiments.contains(experiment.name)) {
          logger.info("Recreating experiment: " + experiment.name)
          experiments += experiment.name -> experiment
        }
      }
    }

    // remove obsolete expriments
    //println("Removing obsolete experiments:")
    experiments = experiments.filter {
      case (name: String, e: Experiment) => {
        val exists = initialExperiments.exists(exp => exp.name == name)
        if (!exists) {
          logger.info("Removing experiment: " + name)
        }
        exists
      }
    }
  }
}

object Model {

  def createGenericMbankModel(fundRepo: FundRepository, name: String, fundNames: List[String], params: Option[Params] = None) : Experiment = {
    val pln = new CurrencyPLN

    val mbankFunds = (fundNames map {
      fundName => fundRepo.getFund(fundName)
    })

    val funds =  List(new FixedDepositFund(pln, "deposit 3%", 0.03)) ++ mbankFunds

    val from = ExtendedDate.createFromString("01-01-2000", "dd-MM-yyy")
    val to = new ExtendedDate()
    val paramsFile = new File("data/" + name + "_best_params.dat")
    var initialParams : Params = null
    if (paramsFile.exists) {
      val paramSerializer = new JavaSerializer[Params]
      initialParams = paramSerializer.unserialize(paramsFile)
    } else if (params.isDefined) {
      initialParams = params.get
    } else {
      initialParams = Params.createRandom(funds.length)
    }
    val initialFund = 0
    val initialValue = 1000
    return new Experiment(name, funds.toArray, from, to, initialParams, initialFund, initialValue)
  }
}
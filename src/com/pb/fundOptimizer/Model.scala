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
    var results = ArrayBuffer[FundOptimizerResult]()
    experiments.foreach {
      case (name, experiment) => {
        logger.info("Starting experiment: " + name)
        val result = experiment.optimize(fundOptimizer)
        results += result
        resultPublisher.publish(experiment, result)
      }
    }
    resultPublisher.publishDigest(experiments)
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

    val from = ExtendedDate.createFromString("01-01-2000", "mm-dd-yyy")
    val to = new ExtendedDate() //.addDays(-10)
    val params = Params.createRandom(funds.length)
    val initialFund = 0
    val initialValue = 1000

    return new Experiment("BestRankedMbankExperiment", funds, from, to, params, initialFund, initialValue)
  }

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
    val params = new Params(262, 0.1, Array(-0.1858522093664584,0.8730949913874132,-7.742155714992641,4.578019670902693,6.6824129369706355,-0.7687299614350103,-2.2995632905554606,-3.5353168692081,-0.38200028396527785,-6.681302326506522))
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
    val to = new ExtendedDate()
    val params = new Params(143, 0.1, Array(0.8683821956484502,-4.2771709370940885,-4.647341207846134,1.1465299675711471,6.668209250324887,1.9053013033792507,-2.23825835441779,-2.5343348298180444,-3.5679213529796994,4.686259812087139,-1.1827421460505643,-0.78733102950893,-2.803814689182628,2.2716147868139536,3.13103234450677,-2.2999335053941117,-5.329262870579411,-0.07050703922398371,-2.5549840486865407,4.694922983796607,-0.657679834745228,-4.529515013417168,-4.413423320643688,-2.752629481155971,-2.2849047199404335,-0.482899527047854,-5.060051156672945,2.641038788744086,0.7000581814735674,3.739524496554221,-3.361652944655636,5.926311143100099,1.4488418926918243,1.3542788006843074,1.9353233370896685,-4.650879086904806,-0.6255561310886328,2.9106388853302856,4.967591751928438,5.813799358631487,-3.2032796509615578,-6.018985087289633,-2.643993185925945,4.85507586494518,3.6917266827008834,
      1.7642763812326758,2.447218558835616,-0.12403806874045012,1.4767697091263152,-6.695035383940957,-3.6202491553940193,3.3580797729171517,2.025338428328512,-4.275553124913953,0.15166631744646214,-4.572418757173967,-6.03397587165469,-5.607547326194866,8.368691283964793,-4.2746472006559975,0.5040502876382896,-5.171645164810753,-12.038942423997751,-4.953309490002816,2.7192629437239413,-5.364891931700071,-1.9245667709334635,7.884305929774983,-1.0535484301541043,1.7985622927598812,0.3232548013390493,5.402659453144313,-1.2298392170254364,2.646959042830067,0.1800967491049128,5.885260308286708,1.2344952122471446,5.390720315625123,4.384998760941398,7.976860502040969,2.756105031312941,-2.607148847797849,5.444512991449321,-1.5964630065936207,-3.6521941461007588,-1.7272600015141375,9.08738110282223,-4.257730565739873,-3.727955793498589,-6.911337694779171,4.961608968447537,3.627726727634851,3.0456644733534746,5.790461255836021,6.1287675101379415,-8.603253037299746,-4.130726845701307,-5.157664496126326,-3.554960546599189,
      0.3000449074558481,-2.5032493140671974,-5.381805422019706,1.2008638140074672,15.317015394721146,0.296488022867612,0.2079989955214352,5.518112491490045,-3.495588252319144,7.183994260142747,1.2689709358655756,6.940960664430651,-4.5729256719112685,0.9063706656960735,-2.0610235041047726,-6.167228143384624,-5.370992572632742,-5.860681805793671,4.267667221834627,-7.222952016876961,1.3434891858359652,-2.406713997925209,-5.7006240682839495,0.9715852063485482,-2.0438506390439324,-4.679414638886312,-2.9800044840671798,4.951325420836947,-0.7881745169559304,3.544457735777766,1.3777957773072613,-1.2070011220225598,-3.3172105017212767,-0.11644182428449423,-3.4472372226931594,1.818112461438995,-2.728467393712502,3.4139832029866652,0.9687080650482214,-6.638761010967025,-0.1469253584042911,-7.3020388213126095,3.274232826650703,-0.7156013324907662,1.8862963463148463,-3.7831120369547326,-0.5395948409062709,-2.4056485522237496,1.1258852099113863,-0.4702113981771414,6.104501237392473,-1.6115066632048118,0.14706099223474633,
      -13.328149984300039,-0.8568755227320006,3.7302701583579494,-1.8359431250500704,1.2579953185478696,-2.1694263919033734,6.313874021892504,1.0539689571159616,3.6486654790918562,-3.2815586653095563,-3.5716661835713563,-1.4780282955900435,-2.3506690378903383,-3.310104285424295,-4.206372604163115,-2.09306386669501,2.322967445253584,3.0578103687177034,-3.7693216509644314,5.65244728644727,0.8331275736910753,0.8336360858248235,3.092841037018151,-2.94713373093271,-11.676044656931175,-4.84711754618681,3.8994831949997155,2.2047784479538914,3.7775367577990444,-5.155718171741527,6.581838274078727,-6.286333989911675,5.327381746057876,0.29558680068356313,10.797593399318183,1.8412063947671633,4.621878358565213,-1.9516480141568984,-11.918995589663982,-5.498876304678328,-0.3454084518781245,2.4275126858648663,-3.0078247586881943,-1.973226524541679,5.25953456860277,4.379290868455198,4.072285212162179,-4.690477885159109,5.30584217022931,1.4053624635791093,-3.492236058755621,-0.8825441121100293,1.9835671914732727,4.078230553226692,
      1.568938594236485,2.542793072423735,-3.5056109098376886,-10.455624691411286,3.9309782533385103,-0.025329747544598047,0.5216642720619824,5.736310591291092,2.0720933351801984,-1.9014962973249037,-2.9980902362742614,-6.419114030925914,6.925158303031072,-3.4244581693984366,0.32277101364026717,3.04202364582214,-2.487640106565839,2.874966393977397,-5.011555525988184,3.5489282813092404,2.126998105928028,-4.836500331344244,-2.111482939410193,-1.4693613950100715,3.359071621609395,1.9571976785170397,-0.22031978580087205,2.822871479177072,2.0008906786623815,2.7247123428301383,1.4838596530974166,-2.6738124410798516,-2.931644174902022,-3.2970029789042505,2.655092931350132,-2.163489364194189,-1.2771790195958084,-2.0141946091825775,1.6965462831511877,1.0822391751870648,-0.7591840908932368,0.7755251024026548,-3.4673500190265623,-5.035503227604677,1.8474933648758507,5.672710089019051,0.7912064873312921,-6.893702047432309,2.283482547799))
    val initialFund = 0
    val initialValue = 1000

    return new Experiment("MBankFullExperiment", funds, from, to, params, initialFund, initialValue)
  }
}
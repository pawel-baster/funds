package funds

import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/10/12
 * Time: 7:39 AM
 * To change this template use File | Settings | File Templates.
 */
class Params (
  val window: Int,
  val smoothFactor: Double,
  val coefs: Array[Double]
) {
 /* def createRandom(count: Int) = 0
  def createRandom(p: Params) = 1*/
  def createRandomFromNormal(maxWindow: Int): Params = {
    var newWindow = math.round(window + Random.nextGaussian - 1).toInt
    if (newWindow < 1) newWindow = 1
    if (newWindow > maxWindow) newWindow = maxWindow
    val newSmoothFactor = math.abs(smoothFactor + Random.nextGaussian/10.0)
    val newCoefs = coefs.map(coef => coef + Random.nextGaussian())
    return new Params(newWindow, newSmoothFactor, newCoefs)
  }
}

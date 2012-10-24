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
  def createRandomFromNormal(): Params = {
    val newWindow = window // +
    val newSmoothFactor = smoothFactor // +
    val newCoefs = coefs// coefs.map(x => x + )
    return new Params(newWindow, newSmoothFactor, newCoefs)
  }
}

package com.pb.fundOptimizer.calculations

import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/10/12
 * Time: 7:39 AM
 * To change this template use File | Settings | File Templates.
 */
class Params(
              val window: Int,
              val smoothFactor: Double,
              val coefs: Array[Double]
              ) extends Serializable {
  def createRandomFromNormal(maxWindow: Int, deviation: Double = 1.0): Params = {
    var newWindow = math.round(window + Random.nextGaussian - 1).toInt
    if (newWindow < 1) newWindow = 1
    if (newWindow > maxWindow) newWindow = maxWindow

    val newCoefs = coefs.map(coef => coef + deviation * Random.nextGaussian())
    return new Params(newWindow, smoothFactor, newCoefs)
  }

  def createRandomFromNormalModifyOneDimension: Params = {

    var newCoefs = coefs.clone()
    newCoefs(Random.nextInt(newCoefs.length)) += Random.nextGaussian()
    return new Params(window, smoothFactor, newCoefs)
  }

  override def toString(): String = {
    return "params: window=" + window + ", smoothFactor=" + smoothFactor + ", coefs: [" + coefs.mkString(",") + "]"
  }
}

object Params {
  val maxWindow = 300

  def createRandom(coefCount: Int): Params = {
    val coefs: Array[Double] = (1 to coefCount).toArray.map(_ => Random.nextGaussian())
    var params = new Params(
      1 + Random.nextInt(maxWindow),
      0.1,
      coefs
    )
    return params
  }
}
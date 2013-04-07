package com.pb.fundOptimizer.calculations

import util.Random

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 10/10/12
 * Time: 7:39 AM
 * To change this template use File | Settings | File Templates.
 */
@SerialVersionUID(625097364780589551l)
class Params(
              val window: Int,
              val smoothFactor: Double,
              val coefs: Array[Double]
              ) extends Serializable {
  def createRandomFromNormal(maxWindow: Int, deviation: Double = 1.0): Params = {
    var newWindow = Params.generateNewWindow(window, maxWindow)
    val newCoefs = coefs.map(coef => coef + deviation * Random.nextGaussian())
    return new Params(newWindow, smoothFactor, newCoefs)
  }

  def createRandomFromNormalModifyOneDimension: Params = {

    var newCoefs = coefs.clone()
    newCoefs(Random.nextInt(newCoefs.length)) += Random.nextGaussian()
    return new Params(window, smoothFactor, newCoefs)
  }

  def createRandomFromNormalModifyWindow(maxWindow: Int): Params = {
    var newCoefs = coefs.clone()
    var newWindow = Params.generateNewWindow(window, maxWindow)
    return new Params(newWindow, smoothFactor, newCoefs)
  }

  def createRandomZeroRandomDimension(deviation: Double): Params = {
    var newCoefs = coefs.clone()
    val randomDimension = Random.nextInt(newCoefs.length)
    newCoefs(randomDimension) = if (newCoefs(randomDimension) != 0) 0 else Random.nextGaussian() * deviation
    return new Params(window, smoothFactor, newCoefs)
  }

  override def toString(): String = {
    return "params: window=" + window + ", smoothFactor=" + smoothFactor + ", coefs: [" + coefs.mkString(", ") + "]"
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

  def generateNewWindow(window: Int, maxWindow: Int): Int = {
    var newWindow = math.round(window + Random.nextGaussian - 1).toInt
    if (newWindow < 1) newWindow = 1
    if (newWindow > maxWindow) newWindow = maxWindow
    return newWindow
  }
}
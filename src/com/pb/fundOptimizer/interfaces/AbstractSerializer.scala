package com.pb.fundOptimizer.interfaces

import java.io.File

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 20:36
 * To change this template use File | Settings | File Templates.
 */
abstract class AbstractSerializer[T] extends Serializable {
  def unserialize(file: File): T

  def serialize(obj: T, file: File)
}

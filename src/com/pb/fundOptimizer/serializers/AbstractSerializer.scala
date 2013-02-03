package com.pb.fundOptimizer.serializers

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 20:36
 * To change this template use File | Settings | File Templates.
 */
abstract class AbstractSerializer[T] {
  def unserialize(filename: String): T
  def serialize(obj: T, filename: String)
}

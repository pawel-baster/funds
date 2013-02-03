package com.pb.fundOptimizer.serializers

import java.io.{ObjectInputStream, FileInputStream, ObjectOutputStream, FileOutputStream}

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 20:38
 * To change this template use File | Settings | File Templates.
 */
class JavaSerializer[T] extends AbstractSerializer[T] {
  def unserialize(filename: String): T = {
    val fis = new FileInputStream(filename)
    val ois = new ObjectInputStream(fis)
    var obj: T = ois.readObject.asInstanceOf[T]
    ois.close
    return obj
  }

  def serialize(obj: T, filename: String) = {
    val fos = new FileOutputStream(filename)
    val oos = new ObjectOutputStream(fos)
    oos.writeObject(obj)
    oos.close
  }
}

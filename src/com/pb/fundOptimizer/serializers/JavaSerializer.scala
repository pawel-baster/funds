package com.pb.fundOptimizer.serializers

import java.io._
import com.pb.fundOptimizer.interfaces.AbstractSerializer
import com.pb.fundOptimizer.logging.logger

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 20:38
 * To change this template use File | Settings | File Templates.
 */
class JavaSerializer[T] extends AbstractSerializer[T] {
  def unserialize(file: File): T = {
    val fis = new FileInputStream(file)
    val ois = new ObjectInputStream(fis)
    var obj: T = ois.readObject.asInstanceOf[T]
    ois.close
    return obj
  }

  def serialize(obj: T, file: File) {
    logger.info("serializing the model")
    val tempFile = new File(file.getAbsolutePath + ".temp")

    //serialize:
    val fos = new FileOutputStream(tempFile)
    val oos = new ObjectOutputStream(fos)
    oos.writeObject(obj)
    oos.close

    new File(file.getAbsolutePath) renameTo  new File(file.getAbsolutePath + ".bak")
    tempFile.renameTo(file)
  }
}

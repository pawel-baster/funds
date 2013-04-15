package com.pb.fundOptimizer

import _root_.funds.ExtendedDate
import java.io.{FileReader, FileWriter, File}

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 4/15/13
 * Time: 7:56 PM
 * To change this template use File | Settings | File Templates.
 */
class SimpleLock (
  val file: File
) {
  val autobreakInterval = 3600 // 1 hour

  def acquire(): Boolean = {
    if (file.exists()) {
      val fr = new FileReader(file)

      fr.read(cbuf)
      fr.close()
      return false
    } else {
      val fw = new FileWriter(file)
      fw.write(new ExtendedDate().format("yyyy-MM-dd HH:mm"))
      fw.close()
      return true
    }
  }

  def release() {
    file.delete()
  }
}

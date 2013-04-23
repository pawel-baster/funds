package com.pb.fundOptimizer

import _root_.funds.ExtendedDate
import java.io.{FileReader, FileWriter, File}
import io.Source

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
  val autobreakInterval = 5 * 3600 * 1000// 1 hour
  val dateFormat = "yyyy-MM-dd HH:mm"

  def acquire(): Boolean = {
    if (file.exists()) {
      val content = Source.fromFile(file).mkString
      val lockDate = ExtendedDate.createFromString(content, dateFormat)
      if (lockDate.getTime + autobreakInterval > new ExtendedDate().getTime) {
        return false
      }
    }

    val fw = new FileWriter(file)
    fw.write(new ExtendedDate().format(dateFormat))
    fw.close()
    return true
  }

  def release() {
    file.delete()
  }
}

package com.pb.fundOptimizer.logging

import funds.ExtendedDate
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
class logger {

}

object logger {
  def info(line: String) {
    println(new ExtendedDate().format("yyyy-MM-dd H:m:s ") + line)
  }
}

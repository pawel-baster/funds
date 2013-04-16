package com.pb.fundOptimizer.exceptions

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 4/16/13
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
class ZeroQuoteException (
  val message: String
) extends RuntimeException(message) {

}

package com.pb.fundOptimizer.exceptions

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 16.04.13
 * Time: 18:08
 * To change this template use File | Settings | File Templates.
 */
class BadIterationException  (
                               val message: String
                               ) extends RuntimeException(message) {
}
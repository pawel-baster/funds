package com.pb.fundOptimizer.test

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import com.pb.fundOptimizer.serializers.JavaSerializer

class TestClass(
                 val val1: Int,
                 val nestedObject: TestClass = null
                 ) extends Serializable {

}

/**
 * Created with IntelliJ IDEA.
 * User: pb
 * Date: 03.02.13
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */
class JavaSerializerTest extends FunSpec with ShouldMatchers {
  describe("JavaSerializer") {
    it("should return identical object after serialization-unserialization") {
      val nestedObject = new TestClass(1)
      val testObject = new TestClass(2, nestedObject)
      val serializer = new JavaSerializer[TestClass]
      val filename = "src/com/pb/fundOptimizer/test/fixtures/javaSerializer.dat"
      serializer.serialize(testObject, filename)
      val unserializedObject = serializer.unserialize(filename)
      assert(unserializedObject.isInstanceOf[TestClass])
      (testObject.val1) should equal(unserializedObject.val1)
      (testObject.nestedObject.val1) should equal(unserializedObject.nestedObject.val1)
    }
  }
}
object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val i = 10                                      //> i  : Int = 10
  val j = 1 to i                                  //> j  : scala.collection.immutable.Range.Inclusive = Range(1, 2, 3, 4, 5, 6, 7, 
                                                  //| 8, 9, 10)
  val k = j map (_ * 2)                           //> k  : scala.collection.immutable.IndexedSeq[Int] = Vector(2, 4, 6, 8, 10, 12,
                                                  //|  14, 16, 18, 20)
  
}
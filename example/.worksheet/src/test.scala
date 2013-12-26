object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(57); 
  println("Welcome to the Scala worksheet");$skip(13); 
  val i = 10;System.out.println("""i  : Int = """ + $show(i ));$skip(17); 
  val j = 1 to i;System.out.println("""j  : scala.collection.immutable.Range.Inclusive = """ + $show(j ));$skip(24); 
  val k = j map (_ * 2);System.out.println("""k  : scala.collection.immutable.IndexedSeq[Int] = """ + $show(k ))}
  
}

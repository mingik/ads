// Basic string compression: replace repeated consequitive letters with "letter[counter]"
// "aabccccccaaa" -> "a2b1c5a3"
def basicStringCompression(s: String): String = {
  val len = s.size
  var i = 0
  var count = 1
  val sb: StringBuffer = new StringBuffer()
  while ( i+1 < len ) {
    
    while ( (i+1)<len && s(i) == s(i+1) ) {
      count += 1
      i += 1
    }
    sb.append(""+s(i)+count)

    count = 1
    i += 1
  }
  sb.toString
}

// Same, but functional

import scala.collection.mutable.Buffer

def basicStringCompressionFunc(s: List[Char], acc: Buffer[(Char,Int)]): Buffer[(Char,Int)] = {
  s match {
    case he::ta => if ((acc.size > 0) && acc.last._1 == he) {
                      acc(acc.size-1) = (acc.last._1,acc.last._2+1)
                      basicStringCompressionFunc(ta,acc)
                   } else basicStringCompressionFunc(ta, acc :+ (he,1))
    case List() => acc
  }
}

val b = basicStringCompressionFunc("aabcccccaa".toList, Buffer())
b.foldlLeft("")((str,p) => str+p._1+p._2)




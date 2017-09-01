/*

Find longest common suffix of given strings.

 */

def longestCommonSuffixImperative(s1: String, s2: String): String = {
  val (l1, l2) = (s1.length, s2.length)



  if (l1 == 0 || l2 == 0)
    ""
  else {

    val (large, small) = if (l2 < l1) (s1, s2) else (s2, s1)


    var smallIdx = small.length
    var ret = small.substring(smallIdx) // ""
    var next = ret
    var proceed = true

    while (proceed) {
      if (large.endsWith(next)) {
        smallIdx -= 1
        ret = next
        if (smallIdx == -1) {
          proceed = false
        } else {
          next = small.substring(smallIdx)
        }
      } else {
        proceed = false
      }
    }
    ret
  }

}

def longestCommonSuffixFunc(s1: String, s2: String): String = {

  val rl1 = s1.reverse.toList
  val rl2 = s2.reverse.toList

  def longestCommonPrefix(l1: List[Char], l2: List[Char], acc: List[Char]): List[Char] = {

    (l1, l2) match {
      case (h1::t1, h2::t2) =>
        if (h1 == h2)
          longestCommonPrefix(t1, t2, h1::acc)
        else
          acc
      case _ => acc
    }
  }

  longestCommonPrefix(rl1, rl2, Nil).mkString

}

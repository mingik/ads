/**

Generate ALL anagrams of a Given String

Idea: for each current character in a string, recurse on remaining
substring and then 'inject' this character in all available positions
before/after and between.

Usage: scala> anagrams( "hello".toList ).map( li => li.foldLeft("")((s,ch) =>
s+ch) )

*/

/**
'Injects' given character into given list into all possible places
  */
def inject(a: Char, li: List[Char]): List[List[Char]] = {
  li match {
    case List() => List(List(a))
    case b::rest => {
      val recurse: List[List[Char]] = inject(a,rest)
      List(a::(b::rest)) ++ recurse.map(li => b::li)
    }
  }
}

def anagrams(s: List[Char]): List[List[Char]] = {
  s.toList match {
    case Nil => List(List())
    case a :: rest => {
      val recurse = anagrams(rest)
      recurse.flatMap( li => inject(a, li) )
    }
  }
}

def reverse(li: List[Int]): List[Int] = li match {
  case Nil => Nil
  case a::rest => reverse(rest) ++ List(a)
}
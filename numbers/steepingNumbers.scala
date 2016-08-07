def isSteepingHelper(digitList: List[Int]): Boolean = digitList match {
  case Nil => true
  case digit::rest => rest match {
    case Nil => true
    case nextDigit::_ => if (Math.abs(nextDigit - digit) <= 1) isSteepingHelper(rest) else false
  }
}

def isSteeping(n: BigInt): Boolean = {
  isSteepingHelper( n.toString.map( ch => (ch.toInt - '0'.toInt) ).toList )
}


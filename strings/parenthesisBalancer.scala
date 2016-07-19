/**
  Checks if provided string contains balanced paranthesis.
 
  Usage:
  $ scala parenthesisBalancer.scala "(a)b(c)d"
  $ scala parenthesisBalancer.scala "(a)b(cd" 

  */

object ParenthesisBalancer extends App {

  def isBalanced(str: String): Boolean = {
    def helper(left: Int, chars: List[Char]): Boolean = {
      chars match {
        case '(' :: rest => helper(left+1, rest)
        case ')' :: rest =>
          if (left > 0) helper(left-1, rest) else false
        case _ :: rest => helper(left, rest)
        case List() => {
          if (left > 0) false else true
        }
      }
    }
    helper(0, str.toList)
  }

  println(s"input: ${args(0)}, parenthesis balanced: ${isBalanced(args(0))}")
}

ParenthesisBalancer.main(args)

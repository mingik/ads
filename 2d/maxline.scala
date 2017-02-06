/**
  * N points are given on a 2-dimensional plane. What is the maximum number of points on the same straight line that goes through origin?

  *  Solution: sort points by slope and calculate max # of equal (O(n))
  */

object Input {

  case class Point(x:Double,y:Double)

  def getInput(): List[Point] = {
    List()
  }
}

object SolutionOrigin {
  import Input._

  val input: List[Point] = getInput()
  case class OriginLine(k:Double) // y = k*x
  object OriginLine {
    def apply(p: Point): OriginLine = {
      OriginLine(p.x / p.y)
    }
  }
  val answer = input
    .groupBy((p: Point) => OriginLine(p))
    .values
    .map(_.size)
    .max

  println("maximum number of points on the same line through origin is $answer")
}

/**
  * N points are given on a 2-dimensional plane. What is the maximum number of points on the same straight line?

  * Solution: for each pair of points, calculate # of other points that lie on the line defined by pair (i.e. create frequency table: Line through 2 points -> # points on that line). Then find max value. O(n^2)
  */

object Solution {
  import Input._
  val input: List[Point] = getInput()
  case class Line(k:Double, b:Double) // y = k*x + b
  object Line {
    def apply(p1: Point, p2: Point): Line = {
      val k = (p1.y - p2.y) / (p1.x - p2.x)
      Line(k, p1.y - k*p1.x)
    }
  }
  val distinctPairs: List[List[Point]] = input.combinations(2).toList
  val lines: List[Line] = distinctPairs.map((pair: List[Point]) => Line(pair(0), pair(1)))
  def belongsToLine(line: Line, point: Point): Boolean = {
    (point.y - line.k*point.x - line.b) == 0d
  }

  val belongingPoints: List[Int] = lines
    .map(line => {
      input.filter(belongsToLine(line,_)).size
    })
  val answer = belongingPoints.max
  println("maximum number of points on the same line is $answer")
}

import java.util.concurrent._
import scala.util.DynamicVariable
import scala.math.pow
import scala.annotation.tailrec
import scala.collection._
import scala.util.Random

/**
  Implementation of k-means algorithm that finds clusters amoung randomly generated 3d points.
  */

object Par {
  val forkJoinPool = new ForkJoinPool

  class TaskScheduler {
    def schedule[T](what: => T): ForkJoinTask[T] = {
      val task = new RecursiveTask[T] { def compute = what }
      Thread.currentThread match {
        case w: ForkJoinWorkerThread => task.fork
        case _ => forkJoinPool.execute(task)
      }
      task
    }
    def parallel[A, B](whatA: => A, whatB: => B): (A, B) = (whatA, task(whatB).join())
  }

  val scheduler = new DynamicVariable[TaskScheduler](new TaskScheduler)

  def task[T](what: => T): ForkJoinTask[T] = scheduler.value.schedule(what)

  def parallel[A, B](whatA: => A, whatB: => B): (A, B) = scheduler.value.parallel(whatA, whatB)
}

/**
  Point in 3d space.
  */
class Point(val x: Double, val y: Double, val z: Double) {
  private def round(v: Double): Double = (v * 100).toInt / 100.0
  def squareDistanceTo(p: Point): Double = pow(p.x - x, 2)  + pow(p.y - y, 2) + pow(p.z - z, 2)
  override def toString = s"Point(${round(x)}, ${round(y)}, ${round(z)})"
}

object KMeans {
  def generatePoints(k: Int, num: Int): Seq[Point] = {
    def generateCoordinate(rand: Random, counter: Int, bound: Int): Double = ((counter + 1) % bound) * 1.0 / bound + rand.nextDouble() * 0.5

    val randx = new Random(1)
    val randy = new Random(3)
    val randz = new Random(5)

    (0 until num)
      .map(i => new Point(generateCoordinate(randx, i, k), generateCoordinate(randy, i, k), generateCoordinate(randz, i, k))).to[mutable.ArrayBuffer]
  }

  // Step 1) initializes random means
  def initializeMeans(k: Int, points: Seq[Point]): Seq[Point] = {
    val rand = new Random(7)
    (0 until k).map(_ => points(rand.nextInt(points.length))).to[mutable.ArrayBuffer]
  }

  // Step 2) classify all points into groups "around" means (i.e. partition all points with respect to their closiness to means)
  def classify(points: GenSeq[Point], means: GenSeq[Point]): GenMap[Point, GenSeq[Point]] = {
    def findClosest(p: Point, means: GenSeq[Point]): Point = {
      // assert(means.size > 0)
      var minDistance = p.squareDistanceTo(means(0))
      var closest = means(0)
      var step = 1
      while (step < means.length) {
        val distance = p.squareDistanceTo(means(step))
        if (distance < minDistance) {
          minDistance = distance
          closest = means(step)
        }
        step += 1
      }
      closest
    }

    val map: GenMap[Point, GenSeq[Point]] = points groupBy ((p:Point) => findClosest(p, means))

    means.filter(!map.contains(_)).foldLeft(map)((map: GenMap[Point, GenSeq[Point]], mean: Point) => map + (mean, GenSeq[Point]()))
  }

  // Step 3) Reassign current mean values to average values of the groups/partitions
  def update(classified: GenMap[Point, GenSeq[Point]], oldMeans: GenSeq[Point]): GenSeq[Point] = {
    def findAverage(oldMean: Point, points: GenSeq[Point]): Point = if (points.length == 0) oldMean else {
      var x = 0.0
      var y = 0.0
      var z = 0.0
      points.seq.foreach { p =>
        x += p.x
        y += p.y
        z += p.z
      }
      new Point(x / points.length, y / points.length, z / points.length)
    }

    oldMeans.map((om: Point) => findAverage(om, classified(om)))
  }

  // Step 4) Check if algorithm needs to stop (i.e. check if new values for means are almost like the old ones)
  def converged(eta: Double)(oldMeans: GenSeq[Point], newMeans: GenSeq[Point]): Boolean = {
    oldMeans zip newMeans forall {
      case (oldMean: Point, newMean: Point) => oldMean.squareDistanceTo(newMean) <= eta
    }
  }

  // Combine all steps together
  @tailrec
  final def kMeans(points: GenSeq[Point], means: GenSeq[Point], eta: Double): GenSeq[Point] = {
    val updatedMeans: GenSeq[Point] = update(classify(points, means), means)
    if (!converged(eta)(means, updatedMeans))
      kMeans(points, updatedMeans, eta) // recurse
    else
      updatedMeans
  }
}

/** Usage: $ scala kmeans [number of points] [eta] [k] [type]
  where:
  'number of points' value is number of 3d points to generate (i.e. 500000)
  'eta' value is the threshold that will be used to determine when to stop the algorithm (determines when old means and new means are the same)
  'k' value is the number of clusters to find among generated 3d points
  'type' value can be one of "sequential" or "parallel" to determine whether to use parallelization or not
  */

object KMeansApp extends App {

  import KMeans._

  val numPoints = args(0).toInt 
  val eta = args(1).toDouble //0.01
  val k = args(2).toInt //32

  val points = generatePoints(k, numPoints)
  val means = initializeMeans(k, points)

  args(3) match {
    case "sequential" => kMeans(points, means, eta)
    case "parallel" => kMeans(points.par, means.par, eta)
  }
}

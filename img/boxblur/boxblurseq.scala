package boxblur

import java.util.concurrent._
import scala.util.DynamicVariable

object BoxBlurSeq extends App {
  object Par {
    val forkJoinPool = new ForkJoinPool

    class TaskScheduler {
      def schedule[T](what: => T): ForkJoinTask[T] = {
        val t = new RecursiveTask[T] { def compute = what }
        Thread.currentThread match {
          case w: ForkJoinWorkerThread => t.fork
          case _ => forkJoinPool.execute(t)
        }
        t
      }
      def parallel[A, B](whatA: => A, whatB: => B): (A, B) =
        (whatA, task(whatB).join())
    }

    val scheduler = new DynamicVariable[TaskScheduler](new TaskScheduler)

    def task[T](what: => T): ForkJoinTask[T] = scheduler.value.schedule(what)

    def parallel[A, B](whatA: => A, whatB: => B): (A, B) = scheduler.value.parallel(whatA, whatB)
  }

  object Image {
    type RGBA = Int

    def red(c: RGBA): Int = (0xff000000 & c) >>> 24

    def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

    def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

    def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

    def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = {
      (r << 24) | (g << 16) | (b << 8) | (a << 0)
    }

    def clamp(v: Int, min: Int, max: Int): Int = {
      if (v < min) min
      else if (v > max) max
      else v
    }

    class Img(val width: Int, val height: Int, private val data: Array[RGBA]) {
      def this(w: Int, h: Int) = this(w, h, new Array(w * h))
      def apply(x: Int, y: Int): RGBA = data(y * width + x)
      def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c
    }
  }

  object BoxBlur {
    import Image._
    import Par._

    // Computes the blurred pixel value (avergage of all pixels standing in 'radius' distance from pixel (x,y)
    def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {
      if (radius != 0) {
        var dx = clamp(x-radius, 0, src.width-1)
        var aggregated = (0,0,0,0)
        var curPx = rgba(0,0,0,0)
        var counter: Int = 0
        while (dx <= clamp(x+radius, 0, src.width-1)) {
          var dy = clamp(y-radius, 0, src.height-1)
          while (dy <= clamp(y+radius, 0, src.height-1)) {
            curPx = src.apply(dx, dy)
            aggregated = (aggregated._1 + red(curPx), aggregated._2 + green(curPx), aggregated._3 + blue(curPx), aggregated._4 + alpha(curPx))
            counter += 1
            dy += 1
          }
          dx += 1
        }
        rgba(aggregated._1 / counter, aggregated._2 / counter, aggregated._3 / counter, aggregated._4 / counter)
      } else
        src.apply(x, y)
    }

    def horizontalBlur(src: Img, dst: Img, from: Int, end: Int, radius: Int): Unit = {
      var blurred = rgba(0,0,0,0)
      var dy = from
      while (dy < end) {
        var dx = 0
        while (dx < src.width) {
          blurred = boxBlurKernel(src, dx, dy, radius)
          dst.update(dx, dy, blurred)
          dx += 1
        }
        dy += 1
      }
    }

    def parHorizontalBlur(src: Img, dst: Img, numTasks: Int, radius: Int): Unit = {
      val strips = 0 to src.height by (src.height / numTasks)
      var stripTuples = (strips zip strips.tail).toArray

      if (stripTuples.length == 1) {
        horizontalBlur(src, dst, stripTuples(0)._1, stripTuples(0)._2, radius)
      } else {
        var step = 0
        while (step < stripTuples.length) {
          if (step+1 == stripTuples.length) {
            horizontalBlur(src, dst, stripTuples(step)._1, stripTuples(step)._2, radius)
          } else {
          parallel(
            horizontalBlur(src, dst, stripTuples(step)._1, stripTuples(step)._2, radius),
            horizontalBlur(src, dst, stripTuples(step+1)._1, stripTuples(step+1)._2, radius)
          )
          }
          step += 1
        }
      }
    }
  }
}



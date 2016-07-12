import java.util.concurrent._
import scala.util.DynamicVariable

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

object Image {
  type RGBA = Int

  def red(c: RGBA): Int = (0xff000000 & c) >>> 24

  def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

  def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

  def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

  def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = (r << 24) | (g << 16) | (b << 8) | (a << 0)

  def clamp(v: Int, min: Int, max: Int): Int = if (v < min) min else if (v > max) max else v

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
    var dy = from
    while (dy < end) {
      var dx = 0
      while (dx < src.width) {
        dst.update(dx, dy, boxBlurKernel(src, dx, dy, radius))
        dx += 1
      }
      dy += 1
    }
  }

  def horizontalBlurPar(src: Img, dst: Img, numTasks: Int, radius: Int): Unit = {
    val stripWidth = src.height / numTasks
    if (stripWidth > 0) {
      val strips = 0 to src.height by stripWidth
      var stripTuples = (strips zip strips.tail).toArray

      if (stripTuples.length == 1) {
        horizontalBlur(src, dst, stripTuples(0)._1, stripTuples(0)._2, radius)
      } else {
        var step = 1
        while (step < stripTuples.length) {
          parallel(
            task(horizontalBlur(src, dst, stripTuples(step)._1, stripTuples(step)._2, radius)),
            horizontalBlur(src, dst, stripTuples(step+1)._1, stripTuples(step+1)._2, radius))
          step += 2
        }
        if (step == stripTuples.length) {
          horizontalBlur(src, dst, stripTuples(step-1)._1, stripTuples(step-1)._2, radius)
        }
      }
    } else {
      horizontalBlur(src, dst, 0, src.height, radius)
    }
  }

  def verticalBlur(src: Img, dst: Img, from: Int, end: Int, radius: Int): Unit = {
    var dx = from 
    while (dx < end) {
      var dy = 0
      while (dy < src.height) {
        dst.update(dx, dy, boxBlurKernel(src, dx, dy, radius))
        dy += 1
      }
      dx += 1
    }
  }

  def verticalBlurPar(src: Img, dst: Img, numTasks: Int, radius: Int): Unit = {
    var margin = src.width / numTasks
    if (margin > 0) {
      val strips = 0 to src.width by margin
      val stripTuples = (strips zip strips.tail).toArray

      if (stripTuples.length == 1) {
        verticalBlur(src, dst, stripTuples(0)._1, stripTuples(1)._2, radius)
      } else {
        var step = 1
        while (step < stripTuples.length) {
          parallel(
            task(verticalBlur(src, dst, stripTuples(step-1)._1, stripTuples(step-1)._2, radius)),
            verticalBlur(src, dst, stripTuples(step)._1, stripTuples(step)._2, radius))
          step += 2
        }
        if (step == stripTuples.length) {
          verticalBlur(src, dst, stripTuples(step-1)._1, stripTuples(step-1)._2, radius)
        }
      }
    } else 
      verticalBlur(src, dst, 0, src.width, radius)
  }
}


object BoxBlurApp extends App {
  import Image._
  import BoxBlur._
  import java.io._
  import java.awt._
  import java.awt.image._
  import javax.imageio._

  def loadImage(imgPath: String): Img = {
    val stream = new FileInputStream(imgPath)
    try {
      val buff = ImageIO.read(stream)
        val image = new Img(buff.getWidth, buff.getHeight)
        for {
          dx <- 0 until buff.getWidth
          dy <- 0 until buff.getHeight
        } image(dx,dy) = buff.getRGB(dx, dy)
        image
    } finally {
      stream.close()
    }
  }

  def applyFilter(imagePath: String, filterName: String, numTasks: Int, radius: Int) {
    val image = loadImage(imagePath)
    val dst = new Img(image.width, image.height)
    filterName match {
      case "horizontal-box-blur-par" => horizontalBlurPar(image, dst, numTasks, radius)
      case "horizontal-box-blur"     => horizontalBlur(image, dst, 0, image.width, radius)

    }
    try {
      val bufferedImage = new BufferedImage(dst.width, dst.height, BufferedImage.TYPE_INT_ARGB)
      for (x <- 0 until dst.width; y <- 0 until dst.height) bufferedImage.setRGB(x, y, dst(x, y))
      ImageIO.write(bufferedImage, "jpg", new File("./output.jpg"))
    } catch {
      case e: IOException => println("Unable to save image file")
    }
  }

  applyFilter(args(0), args(1), args(2).toInt, args(3).toInt)
}



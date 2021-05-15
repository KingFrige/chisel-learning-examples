package scalaexamples.cats

import cats.syntax.show._
import cats.instances.int.catsStdShowForInt

object catsExample{
  def main(args: Array[String]): Unit = {
    println(1.show)
  }
}

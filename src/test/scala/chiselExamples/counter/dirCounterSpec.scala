package chiselexamples.counter

import org.scalatest._
import chisel3._
import chisel3.tester._
import chisel3.tester.RawTester.test

class dirCounterSpec extends FlatSpec with Matchers {
  test(new dirCounter(10)) { c =>
    // upwards
    c.io.load.poke(true.B)
    c.io.en.poke(true.B)
    c.io.dir.poke(true.B)
    c.io.data.poke(100.U)

    c.clock.step(1)
    c.io.load.poke(false.B)

    c.clock.step(11)
    c.io.cnt.expect(10.U)


    // downwards
    c.io.load.poke(true.B)
    c.io.en.poke(true.B)
    c.io.dir.poke(false.B)
    c.io.data.poke(100.U)

    c.clock.step(1)
    c.io.load.poke(false.B)

    c.clock.step(10)
    c.io.cnt.expect(90.U)
  } 
}


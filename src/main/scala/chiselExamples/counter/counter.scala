package chiselexamples.counter

import chisel3._

class dirCounter(width:Int) extends Module {
  val io = IO(new Bundle {
    val load = Input(Bool())
    val en   = Input(Bool())
    val dir  = Input(Bool())
    val data = Input(UInt(width.W))

    val cnt       = Output(UInt(width.W))
    val overfloat = Output(UInt(width.W))
    val downfloat = Output(UInt(width.W))
  })
  val cnt_next    = Wire(UInt(width.W))

  val cntReg      = RegInit(0.U(width.W))
  val data_shadow = RegInit(0.U(width.W))

  val overfloat = Wire(Bool())
  val downfloat = Wire(Bool())

  when(io.en){
    data_shadow := io.data
  }

  overfloat := io.dir && (cntReg === data_shadow)
  downfloat := (!io.dir) && (cntReg === 0.U)

  when(io.en){
    when(io.load){
      cnt_next := io.data
      }.elsewhen(overfloat){
        cnt_next := 0.U
      }.elsewhen(downfloat){
         cnt_next := data_shadow
      }.otherwise{
          when(io.dir){
            cnt_next := cntReg + 1.U
          }.otherwise{
             cnt_next := cntReg - 1.U
          }
      }
  }.otherwise{
    cnt_next := cntReg
  }

  cntReg := cnt_next
  
  io.overfloat := overfloat
  io.downfloat := downfloat
  io.cnt       := cntReg
  printf("Print during simulation: cnt is %d\n", io.cnt)
}

/**
 * An object extending App to generate the Verilog code.
 */
object dirCounter extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new dirCounter(10))
}

package re

import RegularExpressionVM._

object Test extends App {
  class VMTest(vm: VM, vmname: String, shortName: String) {

    object TooSlow extends Exception

    val a = C('a')
    val optionallyA = Alternate(a, Empty)

    def benchmark(timeout: Double): Unit = {
      def benchmark(k: Int): Unit = {
        def ak(k: Int): RegularExpression = {
          if (k == 0) Empty
          else Concatenate(optionallyA, Concatenate(ak(k-1), a))
        }
        val rx = ak(k)
        val program = rx.compile
        val s = "a" * k
        println(f"$shortName: 'a^$k' matches '(a?)^${k}a^$k'")
        vm.execute(program, s)
      }

      try {
        for (k <- 15 to 30) {
          val t_start = System.nanoTime()
          benchmark(k)
          val t = (System.nanoTime() - t_start) / 1000000000.0
          println(f"$k%6d: $t%5.2fs")
          if (t > timeout) throw TooSlow
        }
      } catch { case TooSlow => println("時間がかかりすぎるので、ここで打ち止め\n") }
    }

    def run(): Unit = {
      println(f"Benchmarking $vmname")
      benchmark(5)
    }
  }

  new VMTest(RecursiveBacktrackingVM, "Recursive backtracking virtual machine", "REC")     .run()
  new VMTest(IterativeBacktrackingVM, "Iterative backtracking virtual machine", "ITER")    .run()
  new VMTest(IterativeBacktrackingVM2, "Iterative backtracking virtual machine", "ITER")    .run()
  new VMTest(KenThompsonVM,           "Ken Thompson's virtual machine",         "Thompson").run()
}

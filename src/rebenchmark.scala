package re

/**
 * Scala の正規表現機能についての実験
 * a?a?..a?aa..a のような正規表現の aaa のような文字列への適用に要する時間の測定
 **/

object Benchmark {
  def benchmark(pattern: scala.util.matching.Regex, string: String): Double = {
      val time_start = System.nanoTime()
      pattern.findFirstIn(string)
      (System.nanoTime() - time_start) / 1000000000.0
  }

  def main(arguments: Array[String]): Unit = {
    var i = 1
    var cont = true
    while (cont) {
      val t = benchmark(("a?" * i + "a" * i).r, "a" * i)
      println(f"$i%2d: $t%5.2fs")
      if (t > 10) cont = false
      i = i + 1
    }
  }
}

/**
ベンチマークの実行結果

実行環境
    Python 3.10.8 (main, Oct 13 2022, 09:48:40)
    [Clang 14.0.0 (clang-1400.0.29.102)] on darwin

    MacBook Air (Apple M1, 16 GB)

 1: 0.00s
 2: 0.00s
 3: 0.00s
 4: 0.00s
 5: 0.00s
 6: 0.00s
 7: 0.00s
 8: 0.00s
 9: 0.00s
10: 0.00s
11: 0.00s
12: 0.00s
13: 0.00s
14: 0.00s
15: 0.00s
16: 0.00s
17: 0.01s
18: 0.01s
19: 0.02s
20: 0.03s
21: 0.06s
22: 0.14s
23: 0.26s
24: 0.54s
25: 1.07s
26: 2.22s
27: 4.55s
28: 9.57s
29: 19.49s
**/

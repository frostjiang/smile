package smile.example.spark

import smile.util.LocalSparkContext


object sparkExample3 {

  def main(args: Array[String]): Unit = {
    val sc = LocalSparkContext.create()

    val lines = sc.textFile("README.md").cache()

//    val words = lines.flatMap(line => line.split(" "))
//      .map(word => (word, 1)).reduceBykey(_+_).collect()
//
//    words.foreach(println)
  }
}

package smile.util

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object LocalSparkContext {

  def create(numThreads: Int = 4,
             maxMemory: String = "4g",
             compressRDD: Boolean = false,
             extraConfigs: Map[String, String] = Map()): SparkContext = {

    require(numThreads >= 1, "Number of threads must be a positive integer")

    val conf = new SparkConf()
      .setMaster(s"local[$numThreads]")
      .setAppName("Spark App")
      .setJars(List[String]())
      .set("spark.executor.memory", maxMemory)
      .set("spark.rdd.compress", if (compressRDD) "true" else "false")

    new SparkContext(conf)
  }
}
package smile.util

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object LocalSparkContext {
  /**
   * This is a helper method that creates a new SparkContext that runs spark in "local" mode
   *
   * @param numThreads The number of threads to start up locally for Spark Execution. Defaults to total number of
   *                   physical cpus on your system
   * @param maxMemory The amount of RAM to limit your program to. NOTE: You must ensure your XmX param is set correctly.
   *                  Defaults to "4g"
   * @param compressRDD Whether to compress RDDs to save memory at the cost of CPU cycles. Defaults to `false`
   */
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
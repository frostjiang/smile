package smile.example.spark

import org.apache.spark.rdd.RDD
import smile.util.LocalSparkContext

/**
 * This is an example of what a driver program for Spark looks like. (A Scala object with a main method)
 *
 * A driver program creates RDDs to work with data. Execution of RDDs (whether its reading in data,
 * applying transformations, etc...) take place on the executor nodes (be it a Mesos slave, a YARN application node,
 * or a Spark slave node, depending on your chosen method of deployment.)
 *
 * In this example, we run Spark in "local" mode so the executor is instantiated within the same process as
 * the driver.
 */
object sparkExample2 {
  def main(args: Array[String]): Unit = {
    // Create a SparkContext that runs a local executor
    val sc = LocalSparkContext.create()

    // Create an RDD backed by lines from a text file.
    // An RDD is an abstraction for specifying # of partitions and transformations to apply on each one
    // Note that nothing actually happens yet on this line, operations in Spark are stored in a DAG and are lazily evaluated.
    val lines: RDD[String] = sc.textFile("README.md", 2).cache()

    // Tell Spark to keep this RDD in memory
    lines.cache()

    // Calling filter() constructs a new RDD, where:
    //   - it depends on the previous RDD that specifies reading in a text file
    //   - adds a filter operation to apply on that data
    // Calling count() triggers execution of the RDD as follows:
    //   --> (1) Driver program requests N executor nodes and ships this RDD for execution
    //   --> (2) Each executor will do the following:
    //         --> (a) read a subset of the lines from the source file AND cache this result
    //         --> (b) filter lines containing "a"
    //         --> (c) count # of lines
    //   --> (3) Driver program requests the line count from each executor and flattens it into a Scala Long object.
    val numAs: Long = lines.filter(line => line.contains("a")).count()

    // For this one, the execution is as follows:
    //   --> (4) Driver program requests N executor nodes again and ships this RDD for execution
    //   --> (5) Each executor will do the following:
    //         --> (a) Use the cached result from the previous computation in (2a)
    //         --> (b) filter lines containing "b"
    //         --> (c) count # of lines
    //   --> (6) Driver program requests the line count from each executor and flattens it into a Scala Long object.
    val numBs: Long = lines.filter(line => line.contains("b")).count()

    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
  }
}

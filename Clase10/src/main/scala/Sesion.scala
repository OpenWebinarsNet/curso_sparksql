import org.apache.spark.sql.SparkSession

object Sesion extends App {
  val spark = SparkSession.builder().master("local").getOrCreate()

}



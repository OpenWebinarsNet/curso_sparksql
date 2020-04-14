import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Sesion extends App {
  val spark = SparkSession.builder().master("local").getOrCreate()

  // Cambiar por ruta donde hemos bajado archivos
  val carpetaDescarga="C:/Users/psz/Desktop/"

  spark.sparkContext.setLogLevel("ERROR")

  val rutaFacturas = carpetaDescarga+"Facturas"
  val facturas = spark.read.json(rutaFacturas)
  facturas.printSchema
  facturas.show

  // total ventas
  println("Total de ventas por tienda")
  val totalPorTienda = facturas.groupBy("tienda").sum("cantidad")
  totalPorTienda.show

  // Media
  println("Media de ventas por tienda")
  val mediaPorTienda = facturas.groupBy("tienda").mean("cantidad")
  mediaPorTienda.show

  // productos
  val rutaProductos = carpetaDescarga+"Productos.csv"
  val productos = spark.read.format("csv").option("header", "true")
    .option("encoding", "ISO-8859-1").load(rutaProductos)
  val pr2col = productos.select(col("NombreProducto"), col("TipoProducto"))

  // join
  var joinedDF = facturas.join(pr2col, facturas("Producto") === pr2col("NombreProducto"), "inner")
  joinedDF = joinedDF.drop("NombreProducto")

  // ventas totales por producto
  val totalPorProducto = joinedDF.groupBy("TipoProducto").sum("cantidad")
  println("Total de ventas por tipo de producto")
  totalPorProducto.show
  
}

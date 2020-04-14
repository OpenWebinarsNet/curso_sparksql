import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Sesion extends App {
  val spark = SparkSession.builder().getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  // pasamos la ruta por comando
  //val carpetaDescarga = "C:/Users/psz/Desktop/"
  val carpetaDescarga=args(0)

  val rutaFacturas=carpetaDescarga+"facturas"
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

  // ventas por meses
  println("Meses ordenados por número de ventas")
  val udfMes = udf((x: String) => x.substring(5, 7))
  val facturasConMes = facturas.withColumn("mes", udfMes(col("fecha")))
  val ventasPorMes = facturasConMes.groupBy("mes").sum("cantidad")
  ventasPorMes.sort(desc("sum(cantidad)")).show

  // producto. glicerina
  println("Filtros on filter, where. Filtro like")
  facturas.filter(col("producto").equalTo("Glicerina")).show

  // where equivale a filter
  facturas.where(col("producto").equalTo("Glicerina")).show

  // like
  facturas.filter(col("producto").like("%ina")).show

  // mayor
  println("Filtro mayor que")
  facturas.filter(col("cantidad").gt(1)).show

  // filtro opuesto
  println("Filtra opuesto (productos que no sean Glicerina)")
  facturas.filter(!col("producto").equalTo("Glicerina")).show

  val claseLibreriaExterna=new utilestemporales.CalculadorPeriodos

  val udfCalcularPeriodo = udf((x: String) => claseLibreriaExterna.clasificaFecha(x) )
  val facturasConPeriodo=facturas.withColumn("PeriodoVentas", udfCalcularPeriodo(col("fecha")))

  facturasConPeriodo.groupBy("PeriodoVentas").sum("cantidad").show


}



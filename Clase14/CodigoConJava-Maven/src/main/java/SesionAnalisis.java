import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.functions.*;
import org.apache.spark.sql.types.DataTypes;

public class SesionAnalisis {
    public static void main(String []args){
        SparkSession spark = SparkSession.builder().master("local").config("spark.sql.warehouse.dir","C:/Users/psz/Desktop").getOrCreate();

        // Cambiar por la ruta habeis descargado los archivos
        String rutaDescarga="C:/Users/psz/Desktop/";

        spark.sparkContext().setLogLevel("ERROR");



        String rutaFacturas = rutaDescarga+"Facturas";
        Dataset<Row> facturas = spark.read().json(rutaFacturas);
        facturas.printSchema();
        facturas.show();

        // total ventas
        System.out.println("Total de ventas por tienda");
        Dataset<Row> totalPorTienda = facturas.groupBy("tienda").sum("cantidad");
        totalPorTienda.show();

        // Media
        System.out.println("Media de ventas por tienda");
        Dataset<Row> mediaPorTienda = facturas.groupBy("tienda").mean("cantidad");
        mediaPorTienda.show();

        // productos
        String rutaProductos = rutaDescarga+"Productos.csv";
        Dataset<Row> productos = spark.read().format("csv").option("header", "true")
                .option("encoding", "ISO-8859-1").load(rutaProductos);
        productos.select(functions.col("NombreProducto"), functions.col("TipoProducto"));

        // join
        Dataset<Row> joinedDF = facturas.join(productos, facturas.col("Producto").equalTo(productos.col("NombreProducto")), "inner");
        joinedDF = joinedDF.drop("NombreProducto");

        // ventas totales por producto
        Dataset<Row> totalPorProducto = joinedDF.groupBy("TipoProducto").sum("cantidad");
        System.out.println("Total de ventas por tipo de producto");
        totalPorProducto.show();

        // ventas por meses
        System.out.println("Meses ordenados por número de ventas");
       // val udfMes = udf((x: String) => x.substring(5, 7))

        UDF1<String,String> udfMes=new UDF1<String, String>() {
            @Override
            public String call(String x) throws Exception {
                return x.substring(5,7);
            }
        };

        spark.udf().register("udfMes",udfMes, DataTypes.StringType);


        Dataset<Row> facturasConMes = null;
        try {
            facturasConMes = facturas.withColumn("mes", functions.callUDF("udfMes",functions.col("fecha")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Dataset<Row> ventasPorMes = facturasConMes.groupBy("mes").sum("cantidad");
        ventasPorMes.sort(functions.desc("sum(cantidad)")).show();

        // producto. glicerina
        System.out.println("Filtros on filter, where. Filtro like");
        facturas.filter(functions.col("producto").equalTo("Glicerina")).show();

        // where equivale a filter
        facturas.where(functions.col("producto").equalTo("Glicerina")).show();

        // like
        facturas.filter(functions.col("producto").like("%ina")).show();

        // mayor
        System.out.println("Filtro mayor que");
        facturas.filter(functions.col("cantidad").gt(1)).show();

        // filtro opuesto
        System.out.println("Filtra opuesto (productos que no sean Glicerina)");
        facturas.filter(functions.col("producto").equalTo("Glicerina")).show();

    }
}

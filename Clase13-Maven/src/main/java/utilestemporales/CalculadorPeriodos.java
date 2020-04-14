package utilestemporales;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculadorPeriodos {
    public String clasificaFecha(String timestamp) {
        String timestampSinHora=timestamp.substring(0, Constantes.PATRON_PARTE_FECHA_TIMESTAMP.length());
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern( Constantes.PATRON_PARTE_FECHA_TIMESTAMP);
        LocalDate fecha= LocalDate.parse(timestampSinHora,formatter);
        if ((fecha.isAfter(Constantes.COMIENZO_REBAJAS_VERANO)) && (fecha.isBefore(Constantes.FIN_REBAJAS_VERANO))){
            return Constantes.PERIODO_REBAJAS_VERANO;
        }
        if ((fecha.isAfter(Constantes.COMIENZO_REBAJAS_INVIERNO))&&(fecha.isBefore(Constantes.FIN_REBAJAS_INVIERNO))){
            return Constantes.PERIODO_REBAJAS_INVIERNO;
        }
        if ((fecha.isAfter(Constantes.COMIENZO_NAVIDAD_ANTERIOR))&&(fecha.isBefore(Constantes.FIN_NAVIDAD_ANTERIOR))){
            return Constantes.PERIODO_NAVIDAD;
        }
        if ((fecha.isAfter(Constantes.COMIENZO_NAVIDAD)) && (fecha.isBefore(Constantes.FIN_NAVIDAD))){
            return Constantes.PERIODO_NAVIDAD;
        }
        return Constantes.PERIODO_ORDINARIO;

    }
}
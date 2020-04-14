package utilestemporales;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public final class Constantes {
    static final String PERIODO_REBAJAS_VERANO="Rebajas de Verano";
    static final String PERIODO_REBAJAS_INVIERNO="Rebajas de Invierno";
    static final String PERIODO_NAVIDAD="Navidad";
    static final String PERIODO_ORDINARIO="Periodo Ordinario";

    static final String PATRON_PARTE_FECHA_TIMESTAMP="yyyy-MM-dd";

    static final int ANIO_ACTUAL= Year.now().getValue();
    static final int ANIO_ANTERIOR= Year.now().getValue()-1;
    static final int ANIO_SIGUIENTE= Year.now().getValue()+1;
    static final int DIA_REYES=7;
    static final int PRIMER_DIA_VENTA_NAVIDAD=9;
    static final int PRIMER_DIA_JUNIO_REBAJAS_VERANO=15;
    static final int ULTIMO_DIA_AGOSTO_REBAJAS_VERANO=31;

    static final LocalDate COMIENZO_NAVIDAD_ANTERIOR = LocalDate.of(ANIO_ANTERIOR, Month.DECEMBER.getValue(),PRIMER_DIA_VENTA_NAVIDAD);
    static final LocalDate FIN_NAVIDAD_ANTERIOR = LocalDate.of(ANIO_ACTUAL,Month.JANUARY.getValue(),DIA_REYES);
    static final LocalDate COMIENZO_NAVIDAD = LocalDate.of(ANIO_ACTUAL,Month.DECEMBER.getValue(),PRIMER_DIA_VENTA_NAVIDAD);
    static final LocalDate FIN_NAVIDAD = LocalDate.of(ANIO_SIGUIENTE,Month.JANUARY.getValue(),DIA_REYES);

    static final LocalDate COMIENZO_REBAJAS_VERANO = LocalDate.of(ANIO_ACTUAL,Month.JUNE.getValue(), PRIMER_DIA_JUNIO_REBAJAS_VERANO);
    static final LocalDate FIN_REBAJAS_VERANO = LocalDate.of(ANIO_ACTUAL,Month.AUGUST.getValue(), ULTIMO_DIA_AGOSTO_REBAJAS_VERANO);

    static final LocalDate COMIENZO_REBAJAS_INVIERNO = LocalDate.of(ANIO_ACTUAL,Month.JANUARY.getValue(),DIA_REYES+1);
    static final LocalDate FIN_REBAJAS_INVIERNO = LocalDate.of(ANIO_ACTUAL,Month.MARCH.getValue(),DIA_REYES+1);
}

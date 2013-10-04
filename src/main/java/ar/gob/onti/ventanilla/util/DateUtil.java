package ar.gob.onti.ventanilla.util;

import javax.persistence.Transient;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Juan Manuel Carrascal
 * @version 1.0
 * @created 12-Jan-2010 11:19:57 AM
 */
public class DateUtil {

    private static SimpleDateFormat shortSDF = new SimpleDateFormat("dd / MM / yyyy");

    // private static SimpleDateFormat longSDF = new
    // SimpleDateFormat("dd de MMMM de yyyy");
    @Transient
    public static boolean getDaysCompareEntryToNow(Timestamp fecEntrada, long days) {
        boolean out = false;
        Date date = DateUtil.getCurrentDate();
        Timestamp fechaActual = new Timestamp(date.getTime());
        // 84600000 milliseconds in a day
        long oneDay = 1 * 24 * 60 * 60 * 1000;
        long tsEntrada = fecEntrada.getTime();
        long tsActual = fechaActual.getTime();
        long tsResult = tsActual - tsEntrada;
        long day = tsResult / oneDay;
        if (day > days) {
            out = true;
        }
        return out;
    }

    public static Timestamp restaTimestampByDays(Timestamp fecha, Long days) {
        Long oneDay = 1l * 24l * 60l * 60l * 1000l;
        days = oneDay * days;
        Long tsResult = fecha.getTime() - days;
        fecha.setTime(tsResult);
        return fecha;
    }

    public static Timestamp sumaTimestampByDays(Timestamp fecha, Long days) {
        Long oneDay = 1l * 24l * 60l * 60l * 1000l;
        days = oneDay * days;
        Long tsResult = fecha.getTime() + days;
        fecha.setTime(tsResult);
        return fecha;
    }

    public static String difTimeStampToTimeStamp(Timestamp fechaInicial, Timestamp fechaFinal) {
        String result = "";
        long oneDay = 24 * 60 * 60 * 1000;
        long oneHour = 60 * 60 * 1000;
        long oneMinute = 60 * 1000;
        if (fechaFinal == null) {
            Date date = DateUtil.getCurrentDate();
            fechaFinal = new Timestamp(date.getTime());
        }
        Long tsEntrada = fechaInicial.getTime();
        Long tsActual = fechaFinal.getTime();
        long tsResult = tsActual - tsEntrada;
        Timestamp tiempoDif = new Timestamp(tsResult);
        System.out.println("Resultado " + tiempoDif);
        /*
         * Obtengo los dias
		 */
        Long dias = tsResult / oneDay;
        /*
		 * Rescato el resto de la divicion de dias para obtener las horas.
		 */
        Long restoDias = tsResult % oneDay;
		/*
		 * Obtengo las horas en base del resto de los dias.
		 */
        Long horas = restoDias / oneHour;
		/*
		 * Rescato el resto de la divicion de horas para obtener las minutos.
		 */
        Long restoHoras = restoDias % oneHour;
		/*
		 * Obtengo los minutos en base del resto de los Horas.
		 */
        Long minutos = restoHoras / oneMinute;
		/*
		 * System.out.println("dias " + dias); System.out.println("horas " +
		 * horas); System.out.println("minutos " + minutos);
		 */
        result = dias + "d " + horas + "h " + minutos + "m";
        return result;
    }

    public static String getMonthName(int monthNumber) {
        String month = null;
        switch (monthNumber) {
            case 0:
                month = " Enero ";
                break;
            case 1:
                month = " Febrero ";
                break;
            case 2:
                month = " Marzo ";
                break;
            case 3:
                month = " Abril ";
                break;
            case 4:
                month = " Mayo ";
                break;
            case 5:
                month = " Junio ";
                break;
            case 6:
                month = " Julio ";
                break;
            case 7:
                month = " Agosto ";
                break;
            case 8:
                month = " Septiembre ";
                break;
            case 9:
                month = " Octubre ";
                break;
            case 10:
                month = " Noviembre ";
                break;
            case 11:
                month = " Diciembre ";
                break;
        }
        return month;
    }

    public static String getFormattedDate(Date date) {
        int day, month, year;
        Calendar cal = new GregorianCalendar();
        String result = "";
        if (date != null) {
            try {
                cal.setTime(date);
                day = cal.get(Calendar.DATE);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);
                result = String.valueOf(day) + " de" + getMonthName(month) + "de " + String.valueOf(year);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Timestamp composeDate(String date) {
        Calendar cal = new GregorianCalendar();
        int year = Integer.parseInt(date.substring(date.lastIndexOf("/") + 1, date.length()));
        int month = Integer.parseInt(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/"))) - 1;
        int day = Integer.parseInt(date.substring(0, date.indexOf("/")));
        cal.set(year, month, day);
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp composeDateGuion(String date) {
        Calendar cal = new GregorianCalendar();
        int year = Integer.parseInt(date.substring(date.lastIndexOf("-") + 1, date.length()));
        int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-"))) - 1;
        int day = Integer.parseInt(date.substring(0, date.indexOf("-")));
        cal.set(year, month, day);
        return new Timestamp(cal.getTime().getTime());
    }

    public static String getShortFormattedDate(Date date) {
		/*
		 * int day,month,year; Calendar cal = new GregorianCalendar(); String
		 * result = ""; if(date != null){ cal.setTime(date); day =
		 * cal.get(Calendar.DATE); month = cal.get(Calendar.MONTH); year =
		 * cal.get(Calendar.YEAR); result = String.valueOf(day) + "_" +
		 * getMonthName(month) + "_" + String.valueOf(year); } return result;
		 */
        if (date != null) {
            return shortSDF.format(date);
        } else {
            return "";
        }
    }

    /**
     * Retorna la fecha actual, la cual se recupera desde la base de datos
     *
     * @return la fecha actual
     */
    // TODO1: este metodo contiene sentencias propietarias del motor de BD
    public static Date getCurrentDate() {
        //return (Date)DBConnection.executeSQLQuery(Constants.INI_PARAM_BD_URL, Constants.INI_PARAM_BD_USER, Constants.INI_PARAM_BD_PASS, "SELECT sysdate FROM dual");
        return new Date(System.currentTimeMillis());
    }
}

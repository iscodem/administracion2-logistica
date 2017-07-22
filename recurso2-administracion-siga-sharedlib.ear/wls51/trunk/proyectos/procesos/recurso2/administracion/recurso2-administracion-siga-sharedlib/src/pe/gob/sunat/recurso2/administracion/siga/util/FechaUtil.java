package pe.gob.sunat.recurso2.administracion.siga.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.WordUtils;

public class FechaUtil {
	
	public static Date parseStringDateToDate(String stringDate, String formatDate) throws Exception {
		Date date = null;
		if (stringDate != null && !stringDate.equals(FormatoConstantes.CADENA_VACIA) && formatDate != null && !formatDate.equals(FormatoConstantes.CADENA_VACIA)) {
			date = new SimpleDateFormat(formatDate).parse(stringDate);
		}
		return date;
	}
	
	public static String formatDateToDateDDMMYYYYHHMM(Date date) {
		String formatDate = FormatoConstantes.CADENA_VACIA;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DDMMYYYYHHMM, Locale.getDefault());
			formatDate = sdf.format(date);
		}
		return formatDate;
	}
	
	public static String formatDateToDateYYYYMMDDHHMMSS(Date date) {
		String formatDate = FormatoConstantes.CADENA_VACIA;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_YYYYMMDDHHMMSS, Locale.getDefault());
			formatDate = sdf.format(date);
		}
		return formatDate;
	}
	
	public static String formatDateToDateDDMMYYYY(Date date) {
		String formatDate = FormatoConstantes.CADENA_VACIA;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DDMMYYYY, Locale.getDefault());
			formatDate = sdf.format(date);
		}
		return formatDate;
	}
	
	public static String formatDateToStringYYYYMMDD(Date date) {
		String formatDate = FormatoConstantes.CADENA_VACIA;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_YYYYMMDD_N, Locale.getDefault());
			formatDate = sdf.format(date);
		}
		return formatDate;
	}
	
	public static String formatDateToDateYYYY(Date date) {
		String formatDate = FormatoConstantes.CADENA_VACIA;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_YYYY, Locale.getDefault());
			formatDate = sdf.format(date);
		}
		return formatDate;
	}
	
	public static String formatDateToDateMM(Date date) {
		String formatDate = FormatoConstantes.CADENA_VACIA;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_MM, Locale.getDefault());
			formatDate = sdf.format(date);
		}
		return formatDate;
	}
	
	public static String formatDateToHourHHMM(Date date) {
		String formatDate = FormatoConstantes.CADENA_VACIA;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_HHMM, Locale.getDefault());
			formatDate = sdf.format(date);
		}
		return formatDate;
	}
	
	public static String formatHourToHHMM(String hourString) {
		StringBuilder formatHour = new StringBuilder(FormatoConstantes.CADENA_VACIA);
		if (hourString != null && hourString.contains(FormatoConstantes.DOS_PUNTOS)) {
			String[] arrayHour = hourString.split(FormatoConstantes.DOS_PUNTOS);
			if (arrayHour.length >= 2) {
				String hora = arrayHour[0];
				String minutos = arrayHour[1];
				formatHour.append(hora);
				formatHour.append(FormatoConstantes.DOS_PUNTOS);
				formatHour.append(minutos);
			}
		}
		return formatHour.toString();
	}	
	
	public static String addMinutosHourFormatHHMM(String hourString, int numMinutos) {
		
		StringBuilder hourFinal = new StringBuilder(FormatoConstantes.CADENA_VACIA);
		if (hourString != null && hourString.contains(FormatoConstantes.DOS_PUNTOS)) {
			String[] arrayHour = hourString.split(FormatoConstantes.DOS_PUNTOS);
			if (arrayHour.length >= 2) {
				Integer hora = new Integer(arrayHour[0]);
				Integer minutos = new Integer(arrayHour[1]);
				minutos += numMinutos;
				
				int cocienteOperacion = minutos/60;
				int restoOperacion = minutos%60;
				
				if (cocienteOperacion == 0) {
					minutos = restoOperacion;
				}
				else {
					hora += cocienteOperacion;
					minutos = restoOperacion;
				}
				
				String horaString = hora.toString();
				String minutosString = minutos.toString();
				
				if (hora.intValue() < 10) {
					horaString = FormatoConstantes.CERO + hora.toString();
				}
				
				if (minutos.intValue() < 10) {
					minutosString = FormatoConstantes.CERO + minutos.toString();
				}
				
				hourFinal.append(horaString);
				hourFinal.append(FormatoConstantes.DOS_PUNTOS);
				hourFinal.append(minutosString);
			}
		}
		return hourFinal.toString();
	}
	
	public static long obtenerDateMilisegundos(Date date) {
		long dateMilisegundos = 0;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			dateMilisegundos = calendar.getTimeInMillis();
		}
		return dateMilisegundos;
	}
	
	public static long numeroDiasBetweenTwoDates(String dateString1, String dateString2) throws Exception {
		
		long numeroDias = 0;
		if (dateString1 != null && !dateString1.equals(FormatoConstantes.CADENA_VACIA) && dateString2 != null && !dateString2.equals(FormatoConstantes.CADENA_VACIA)) {
			Date date1 = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DDMMYYYY).parse(dateString1);
			Date date2 = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DDMMYYYY).parse(dateString2);
			Calendar cal1 = Calendar.getInstance();
	        Calendar cal2 = Calendar.getInstance();
	        cal1.setTime(date1);
	        cal2.setTime(date2);
	        long milis1 = cal1.getTimeInMillis();
	        long milis2 = cal2.getTimeInMillis();
	        
	        if (milis1 > milis2) {
	        	numeroDias = (milis1 - milis2)/(24*60*60*1000);
	        }
	        else {
	        	numeroDias = (milis2 - milis1)/(24*60*60*1000);
	        }
		}
		return numeroDias;
	}
	
	public static long numeroDiasFechaMaxima(Date date1, Date date2) throws Exception {
		
		String date1t = formatDateToDateDDMMYYYY(date1);
		String date2t = formatDateToDateDDMMYYYY(date2);
		
		date1= parseStringDateToDate(date1t, FechaConstantes.DATE_FORMAT_DDMMYYYY);
		date2= parseStringDateToDate(date2t, FechaConstantes.DATE_FORMAT_DDMMYYYY);
		
		long numeroDias = 0;
		if (date1 != null  && date2 != null ) {
			
			Calendar cal1 = Calendar.getInstance();
	        Calendar cal2 = Calendar.getInstance();
	        cal1.setTime(date1);
	        cal2.setTime(date2);
	        long milis1 = cal1.getTimeInMillis();
	        long milis2 = cal2.getTimeInMillis();
	        
	        if (milis1 < milis2) {
	        	numeroDias = (milis2 - milis1  )/(24*60*60*1000);
	        }
	        else {
	        	numeroDias=0;
	        }
		}
		return numeroDias;
	}
		
	
	public static boolean esfechaMayor(String dateString1, String dateString2) throws Exception {
		
		boolean fechaMayor = false;
		if (dateString1 != null && !dateString1.equals(FormatoConstantes.CADENA_VACIA) && dateString2 != null && !dateString2.equals(FormatoConstantes.CADENA_VACIA)) {
			Date date1 = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DDMMYYYY).parse(dateString1);
			Date date2 = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DDMMYYYY).parse(dateString2);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date1);
			cal2.setTime(date2);
			long milis1 = cal1.getTimeInMillis();
			long milis2 = cal2.getTimeInMillis();
			if (milis1 > milis2) {
				fechaMayor = true;
			}
		}
		return fechaMayor;
	}
	
	public static boolean esFechaMenor(String dateString1, String dateString2) throws Exception {
		
		boolean fechaMenor = false;
		if (dateString1 != null && !dateString1.equals(FormatoConstantes.CADENA_VACIA) && dateString2 != null && !dateString2.equals(FormatoConstantes.CADENA_VACIA)) {
			Date date1 = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DDMMYYYY).parse(dateString1);
			Date date2 = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DDMMYYYY).parse(dateString2);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date1);
			cal2.setTime(date2);
			long milis1 = cal1.getTimeInMillis();
			long milis2 = cal2.getTimeInMillis();
			if (milis1 < milis2) {
				fechaMenor = true;
			}
		}
		return fechaMenor;
	}
	
	public static boolean esFechaMenorIgual(String dateString1, String dateString2) throws Exception {
		
		boolean fechaMenorIgual = false;
		if (dateString1 != null && !dateString1.equals(FormatoConstantes.CADENA_VACIA) && dateString2 != null && !dateString2.equals(FormatoConstantes.CADENA_VACIA)) {
			Date date1 = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DDMMYYYY).parse(dateString1);
			Date date2 = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DDMMYYYY).parse(dateString2);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date1);
			cal2.setTime(date2);
			long milis1 = cal1.getTimeInMillis();
			long milis2 = cal2.getTimeInMillis();
			if (milis1 <= milis2) {
				fechaMenorIgual = true;
			}
		}
		return fechaMenorIgual;
	}
	
	public static boolean esFechaMayorIgual(String dateString1, String dateString2) throws Exception {
		
		boolean fechaMayorIgual = false;
		if (dateString1 != null && !dateString1.equals(FormatoConstantes.CADENA_VACIA) && dateString2 != null && !dateString2.equals(FormatoConstantes.CADENA_VACIA)) {
			Date date1 = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DDMMYYYY).parse(dateString1);
			Date date2 = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DDMMYYYY).parse(dateString2);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date1);
			cal2.setTime(date2);
			long milis1 = cal1.getTimeInMillis();
			long milis2 = cal2.getTimeInMillis();
			if (milis1 >= milis2) {
				fechaMayorIgual = true;
			}
		}
		return fechaMayorIgual;
	}
	
	public static boolean esfechaMayor(Date date1, Date date2)  {
		
		boolean fechaMayor = false;
		if (date1 != null && date2 != null ) {
		
			Calendar cal1 = Calendar.getInstance();
	        Calendar cal2 = Calendar.getInstance();
	        cal1.setTime(date1);
	        cal2.setTime(date2);
	        long milis1 = cal1.getTimeInMillis();
	        long milis2 = cal2.getTimeInMillis();
	        
	        if (milis1 > milis2) {
	        	fechaMayor=true;
	        }
	       
		}
		return fechaMayor;
	}
	
	public static String obtenerAnioActual() {
		return formatDateToDateYYYY(new Date());
	}
	
	public static String obtenerMesActual() {
		return formatDateToDateMM(new Date());
	}

	public static String obtenerFechaText(Date date) {
		
		String fechaFormateada = FechaConstantes.CADENA_VACIA;
		if (date != null) {
			SimpleDateFormat diaSemanaSimpleDateFormat = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_EEEE,  new Locale("ES"));
			SimpleDateFormat diaSimpleDateFormat = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_DD,  new Locale("ES"));
			SimpleDateFormat mesSimpleDateFormat = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_MMMM,  new Locale("ES"));
			SimpleDateFormat anioSimpleDateFormat = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_YYYY,  new Locale("ES"));
			StringBuilder fechaActual = new StringBuilder();
			fechaActual.append(WordUtils.capitalizeFully(diaSemanaSimpleDateFormat.format(date)));
			fechaActual.append(FechaConstantes.COMA);
			fechaActual.append(FechaConstantes.ESPACIO);
			fechaActual.append(diaSimpleDateFormat.format(date));
			fechaActual.append(FechaConstantes.ESPACIO);
			fechaActual.append(FechaConstantes.WORD_DE);
			fechaActual.append(FechaConstantes.ESPACIO);
			fechaActual.append(WordUtils.capitalizeFully(mesSimpleDateFormat.format(date)));
			fechaActual.append(FechaConstantes.ESPACIO);
			fechaActual.append(FechaConstantes.WORD_DEL);
			fechaActual.append(FechaConstantes.ESPACIO);
			fechaActual.append(anioSimpleDateFormat.format(date));
			fechaActual.append(FechaConstantes.PUNTO);
			fechaFormateada = fechaActual.toString();
		}
		return fechaFormateada;
	}
	
	public static ArrayList<Mes> obtenerMesesAnio() {
		
		ArrayList<Mes> mesList = new ArrayList<Mes>();
		String[] mesArray = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
		Mes mes;
		for (int i = 0; i < mesArray.length; i++) {
			mes = new Mes();
			mes.setCodigoMes(NumeroUtil.formatNumeroToStringWithCerosLeft(2, (i + 1)));
			mes.setDescripcionMes(mesArray[i]);
			mesList.add(mes);
		}
		return mesList;
	}
	
	public static String fechaActualWithTexto(Date date){
		SimpleDateFormat formateador = new SimpleDateFormat(FechaConstantes.DATE_FORMAT_EEEE_MMMM_YYYY,  new Locale("ES"));
		String fecha = formateador.format(date);
		return fecha;
	}
	
	public static Date sumarRestarDiasFecha(Date fecha, int dias) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de dias a aniadir, o restar en caso de dias <0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos dias aniadidos
	}
	
	public static String obtenerFechaActual() {
		return formatDateToDateDDMMYYYY(new Date());
	}
	
}

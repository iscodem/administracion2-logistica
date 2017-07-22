package pe.gob.sunat.recurso2.administracion.siga.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class NumeroUtil {
	
	public static String formatearNumeros(int numeroDecimales, BigDecimal numero) {
		String numeroFormateado = FormatoConstantes.CADENA_VACIA;
		if (numero != null) {
			numeroFormateado += numero.setScale(numeroDecimales, BigDecimal.ROUND_HALF_UP);
		}
		else {
			numeroFormateado = formatearNumeros(numeroDecimales, BigDecimal.ZERO);
		}
		return numeroFormateado;
	}
	
	public static String formatearMontos(String moneda, int numeroDecimales, BigDecimal monto) {
		String montoFormateado = FormatoConstantes.CADENA_VACIA;
		if (monto != null) {
			montoFormateado += moneda + FormatoConstantes.ESPACIO + monto.setScale(numeroDecimales, BigDecimal.ROUND_HALF_UP);
		}
		else {
			montoFormateado = formatearMontos(moneda, numeroDecimales,BigDecimal.ZERO);
		}
		return montoFormateado;
	}
	
	public static String formatearMontosSoles(BigDecimal monto) {
		String montoSoles = FormatoConstantes.CADENA_VACIA;
		if (monto != null) {
			montoSoles += FormatoConstantes.MONEDA_SOLES + FormatoConstantes.ESPACIO + monto.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		else {
			montoSoles = formatearMontosSoles(BigDecimal.ZERO);
		}
		return montoSoles;
	}
	
	public static String formatNumeroToStringWithCerosLeft(int numeroDigitos, int numero) {
		
	    int numeroValorAbsoluto = Math.abs(numero);
	    StringBuffer sb = new StringBuffer(numeroDigitos);
    	int zeros = numeroDigitos - (int) (Math.log(numeroValorAbsoluto)/Math.log(10)) - 1;
    	if (numero < 0) {
			sb.append(FormatoConstantes.SIGNO_MINUS);
	    }
	    for (int i = 0; i < zeros; i++) {
	    	sb.append(0);
	    }
	    sb.append(numeroValorAbsoluto);
	    return sb.toString();
	}

	public static String formatearMontoToDecimal(int numeroDecimales, BigDecimal numero) {
		
		String montoFormateado = FormatoConstantes.CADENA_VACIA;
		if (numero != null) {
			montoFormateado += numero.setScale(numeroDecimales, BigDecimal.ROUND_HALF_UP);
		}
		return montoFormateado;
	}
	
	public static String formatearMonedaMontoToDecimal(String moneda, int numeroDecimales, BigDecimal monto) {
		
		String montoFormateado = FormatoConstantes.CADENA_VACIA;
		if (monto != null) {
			if (moneda != null && !moneda.equals(FormatoConstantes.CADENA_VACIA)) {
				montoFormateado += moneda + FormatoConstantes.ESPACIO + monto.setScale(numeroDecimales, BigDecimal.ROUND_HALF_UP);
			}
			else {
				montoFormateado += monto.setScale(numeroDecimales, BigDecimal.ROUND_HALF_UP);
			}
		}
		return montoFormateado;
	}
	
	public static String formatearMonedaMontoToDecimal(String moneda, int numeroDecimales, String monto) {
		
		String montoFormateado = FormatoConstantes.CADENA_VACIA;
		if (monto != null) {
			montoFormateado = formatearMonedaMontoToDecimal(moneda, numeroDecimales, new BigDecimal(monto));
		}
		return montoFormateado;
	}
	
	public static String formatearMonedaMontoToDecimal(String moneda, int numeroDecimales, Double monto) {
		
		String montoFormateado = FormatoConstantes.CADENA_VACIA;
		if (monto != null) {
			montoFormateado = formatearMonedaMontoToDecimal(moneda, numeroDecimales, new BigDecimal(monto));
		}
		return montoFormateado;
	}
	
	public static String formatearMonedaMontoToDecimalOnlyPositive(String moneda, int numeroDecimales, BigDecimal monto) {
		
		String montoFormateado = FormatoConstantes.CADENA_VACIA;
		if (monto != null) {
			int resultado = monto.compareTo(BigDecimal.ZERO);
			if (resultado == -1) {
				monto = BigDecimal.ZERO;
			}
			montoFormateado = formatearMonedaMontoToDecimal(moneda, numeroDecimales, monto);
		}
		return montoFormateado;
	}
	
	public static String formatearMonedaMontoToDecimalWithoutNegative(String moneda, int numeroDecimales, BigDecimal monto) {
		
		String montoFormateado = FormatoConstantes.CADENA_VACIA;
		if (monto != null) {
			int resultado = monto.compareTo(BigDecimal.ZERO);
			if (moneda != null && !moneda.equals(FormatoConstantes.CADENA_VACIA)) {
				if (resultado == 0 || resultado == 1) {
					montoFormateado += moneda + FormatoConstantes.ESPACIO + monto.setScale(numeroDecimales, BigDecimal.ROUND_HALF_UP);
				}
				else {
					monto = monto.multiply(new BigDecimal(-1));
					montoFormateado += moneda + FormatoConstantes.ESPACIO + FormatoConstantes.PARENTESIS_INICIO + monto.setScale(numeroDecimales, BigDecimal.ROUND_HALF_UP) + FormatoConstantes.PARENTESIS_FIN;
				}
			}
			else {
				if (resultado == 0 || resultado == 1) {
					montoFormateado += monto.setScale(numeroDecimales, BigDecimal.ROUND_HALF_UP);
				}
				else {
					monto = monto.multiply(new BigDecimal(-1));
					montoFormateado += FormatoConstantes.PARENTESIS_INICIO + monto.setScale(numeroDecimales, BigDecimal.ROUND_HALF_UP) + FormatoConstantes.PARENTESIS_FIN;
				}
			}
		}
		return montoFormateado;
	}
	
	public static String formatearCerosIzquierda(int number, String cantidadCeros){
		Formatter fmt = new Formatter();		
			fmt.format("%0"+cantidadCeros+"d",number);
		return fmt.toString();
	}
	
	/**
	 * @author Samuel Dionisio
	 * @return retorna el monto formateado con la moneda y los decimales.
	 * */
	public static String formatearMonto(String moneda, BigDecimal monto, int cantidadDecimales) {
		
		String montoFormater = FormatoConstantes.CADENA_VACIA;
		if(monto != null){
			NumberFormat formatter = NumberFormat.getInstance(Locale.US);
			formatter.setMaximumFractionDigits(cantidadDecimales);
			formatter.setMinimumFractionDigits(cantidadDecimales);
			montoFormater = formatter.format(monto);
			if(StringUtils.isNotEmpty(moneda)){
				montoFormater = moneda + FormatoConstantes.ESPACIO  + montoFormater;
			}
		}
		return montoFormater;
	}
	
	public static String formatearMonto(String moneda, String monto, int cantidadDecimales) {
		
		String montoFormateado = FormatoConstantes.CADENA_VACIA;
		if (monto != null) {
			montoFormateado = formatearMonto(moneda, new BigDecimal(monto), cantidadDecimales);
		}
		return montoFormateado;
	}
	
	public static String formatearMonto(String moneda, Double monto, int cantidadDecimales) {
		
		String montoFormateado = FormatoConstantes.CADENA_VACIA;
		if (monto != null) {
			montoFormateado = formatearMonto(moneda, new BigDecimal(monto), cantidadDecimales);
		}
		return montoFormateado;
	}
	
}

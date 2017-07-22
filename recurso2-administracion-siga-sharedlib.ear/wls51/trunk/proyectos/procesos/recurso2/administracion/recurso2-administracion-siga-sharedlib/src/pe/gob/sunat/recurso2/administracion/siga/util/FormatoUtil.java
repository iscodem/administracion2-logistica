package pe.gob.sunat.recurso2.administracion.siga.util;

import java.util.HashMap;
import java.util.Map;

public class FormatoUtil {
	public static String validarEmptyToNull(String parameterRequest) {
		String finalParameterRequest = parameterRequest;
		if (FormatoConstantes.CADENA_VACIA.equals(parameterRequest)) {
			finalParameterRequest = null;
		}
		return finalParameterRequest;
	}
	
	public static boolean validarStringDifferentNullAndEmpty(String valueString) {
		
		boolean flagStringValido = false; 
		if (valueString != null && !valueString.equals(FormatoConstantes.CADENA_VACIA)) {
			flagStringValido = true;
		}
		return flagStringValido;
	}
	
	
	public static String obtenerValueParameterRequest(String request, String parameter) {
		
		Map<String, String> map = new HashMap<String, String>();
		if (request != null) {
			String[] requestArray = request.split(FormatoConstantes.REQUEST_AND);
			for (int i = 0; i < requestArray.length; i++) {
				if (requestArray[i] != null && requestArray[i].contains(FormatoConstantes.IGUAL)) {
					String[] parameterArray = requestArray[i].split(FormatoConstantes.IGUAL);
					if (parameterArray.length > 1) {
						map.put(parameterArray[0], parameterArray[1]);
					}
					else {
						map.put(parameterArray[0], FormatoConstantes.CADENA_VACIA);
					}
				}
			}
		}
		
		String valueParameter = map.get(parameter);
		return valueParameter;
	}
	
	public static String getUppperCaseText(String valueText) {
		String upperCaseText = FormatoConstantes.CADENA_VACIA;
		if (valueText != null) {
			upperCaseText = valueText.trim().toUpperCase();
		}
		return upperCaseText;
	}
}

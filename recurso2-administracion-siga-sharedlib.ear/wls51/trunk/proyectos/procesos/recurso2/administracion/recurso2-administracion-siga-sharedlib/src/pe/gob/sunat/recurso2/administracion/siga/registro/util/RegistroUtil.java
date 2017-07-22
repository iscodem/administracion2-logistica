package pe.gob.sunat.recurso2.administracion.siga.registro.util;


public class RegistroUtil {
	
	public static String validarEmptyToNull(String parameterRequest) {
		String valueParameterRequest = parameterRequest;
		if (RegistroConstantes.CADENA_VACIA.equals(parameterRequest)) {
			valueParameterRequest = null;
		}
		return valueParameterRequest;
	}

}

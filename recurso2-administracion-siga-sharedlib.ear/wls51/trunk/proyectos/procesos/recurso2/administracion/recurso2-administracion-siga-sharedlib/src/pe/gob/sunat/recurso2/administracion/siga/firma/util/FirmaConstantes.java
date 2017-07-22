package pe.gob.sunat.recurso2.administracion.siga.firma.util;

public class FirmaConstantes {
	
	///CONSTANCES DE NAVEGACION DE PAGINAS======================================================/////
	
	public static final String REGISTRAR_ENCARGO_PAGE = "encargatura/registrarEncargo";
	public static final String CONSULTAR_ENCARGO_PAGE = "encargatura/consultarEncargo";
	public static final String CONSULTAR_DOCUMENTOS_PAGE = "consulta/consultarDocumentosFirma";
	
	public static final String ERROR_PAGE = "pagErrorSistema";
		
	//ESTADOS DEL ENCARGO DE FUNCIONES=======================================================///
	public static final String ESTADO_ENCARGO_ACTIVO 		= "01";
	public static final String ESTADO_ENCARGO_INACTIVO		= "02";
	public static final String ESTADO_ENCARGO_BAJA 			= "03";
	
	public static final String ESTADO_ENCARGO_ACTIVO_LABEL 		= "ACTIVO";
	public static final String ESTADO_ENCARGO_INACTIVO_LABEL 	= "INACTIVO";
	public static final String ESTADO_ENCARGO_BAJA_LABEL 		= "DE BAJA";
	
	
	//CONSTANTES DE PARAMETROS =========================================================////
	public static final String CODIGO_MODULO_SIGA = "SIGA";
	
	public static final String DETALLE_PARAMETRO ="D";
	public static final String CABECERA_PARAMETRO ="C";
	
	
	public static final String CODIGO_PARAMETRO_ALMACEN_UUOO = "3249";
	public static final String CODIGO_PARAMETRO_ALMACEN_ESTADOS = "3251";
	
	//ACCION A REALIZAR EN EL VIEW================================================================///////////////////////
	
	public static final String FLAG_ACCION_MODIFICAR = "M";
	public static final String FLAG_ACCION_REGISTRAR = "R";
	public static final String FLAG_ACCION_ENVIAR 	= "E";
	public static final String FLAG_ACCION_DESHABILITAR	= "D";

	//CODIGO DE OPERACION - EXITO - ERROR=========================================================/////////////
	public static final String EXITO_OPERACION = "00";
	public static final String ERROR_OPERACION = "01";
	
	//RESOURCE_BUNDLE===============================================================================////////////////
	public static final String RESOURCE_BUNDLE_FIRMA = "iafirmadigital-afiliacion";
	public static final String MENSAJE_ERROR_GENERICO = "error.mensaje.generico";
	
	
	public static final String MENSAJE_ERROR_ENCARGO_ACTIVO= "registrarEncargatura.messageError.encargoactivo";

	
	//=== desde aqui hacia abajo falta alinear
	//EXCEL
	public static final String ARCHIVO_EXCEL_DELECACION_PREFIJO = "Comision_";
	public static final String ARCHIVO_EXCEL_EXTENSION = ".xls";
	
	
	public static final String CERO_DOS = "00";
	public static final String CERO_TRES = "000";
	public static final String CERO_CUATRO = "0000";
	
	
	public static final String TIPO_SEARCH_C ="C";
	public static final String TIPO_SEARCH_D ="D";


	//ETIQUETAS GENERICAS=================================================
		public static String SI = "S";
		public static String NO = "N";
		
		
		public static String OK = "OK";
		public static String DIF = "Diferencia";
		
		public static String CERO = "0";
		public static String UNO  = "1";
		
		public static String INDICADOR_TRUE = "1";
		public static String INDICADOR_FALSE = "*";
		
		public static String OPERACION_NUEVO = "N";
		public static String OPERACION_SECUENCIA = "S";
		public static String OPERACION_VERSION = "V";
		public static String OPERACION_LEVANTA_OBSERVACION = "O";

		public static String TERMINOS_AFILIACION = "0001";
		
		public static String MENSAJE_AFILIACION = "FRMELC001";
		public static String MENSAJE_DESAFILIACION = "FRMELC002"; 
		public static String MENSAJE_DELEGACION_DIRECTA = "FRMELC003";
		public static String MENSAJE_DELEGACION_INDIRECTA = "FRMELC004";
		public static String MENSAJE_DESHABILTAR_DELEGACION = "FRMELC005";
		
		public static String DESTINATARIO_PRINCIPAL = "PARA";
		public static String DESTINATARIO_COPIA = "CC";
		
		public static String PARAMETRO_MENSAJES = "4055";
		public static String PARAMETRO_MODULO_SIGA = "SIGA";

		public static final String LLAVE_FIRMA = "FIRMAELE";
}

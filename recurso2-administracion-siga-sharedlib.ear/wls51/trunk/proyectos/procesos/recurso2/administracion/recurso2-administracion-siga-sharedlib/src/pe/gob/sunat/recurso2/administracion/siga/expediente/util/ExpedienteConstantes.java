package pe.gob.sunat.recurso2.administracion.siga.expediente.util;

public class ExpedienteConstantes {
	
	//PARAMETROS PARA CREAR LOS EXPEDIENTE
	public static final String CODIGO_PROCESO_MOVILIDAD = "260";
	//PARAMETROS PARA ACCIONES LOS EXPEDIENTE
	public static final String ACCION_M_REGISTRO_DDJJ = "001";
	public static final String ACCION_M_APROBAR_DDJJ_COLABORADOR = "002";
	public static final String ACCION_M_CONFORMIDAD_DDJJ_JEFE = "003";
	public static final String ACCION_M_CONFORMIDAD_DDJJ_AUTORIZADOR = "004";
	public static final String ACCION_M_REGISTRO_REEMBOLSO = "005";
	public static final String ACCION_M_APROBAR_REEMBOLSO_COLABORADOR = "006";
	public static final String ACCION_M_CONFORMIDAD_REEMBOLSO_JEFE = "007";
	public static final String ACCION_M_CONFORMIDAD_REEMBOLSO_AUTORIZADOR = "008";
	public static final String ACCION_M_PAGO_CAJA_CHICA= "009";
	public static final String ACCION_M_ABONO_CUENTA= "10";
	public static String ACCION_M_CANCELA_ENVIO_REEMBOLSO= "002";
	
	//PARAMETROS ESTADOS LOS EXPEDIENTE
	public static final String ESTADO_ACCION_001_CONFORME = "001";
	public static final String ESTADO_ACCION_002_CONFORME = "002";
	public static final String ESTADO_ACCION_003_CONFORME = "003";
	public static final String ESTADO_ACCION_004_CONFORME = "004";
	public static final String ESTADO_ACCION_005_ANULADO 	= "005";
	public static final String ESTADO_ACCION_002_ANULADO 	= "002";
	public static final String ESTADO_ACCION_003_ANULADO 	= "003";
	public static final String ESTADO_ACCION_002_RECHAZADO= "002";
	public static final String ESTADO_ACCION_016_CONFORME = "016";
	public static String ESTADO_ACCION_006_CANCELAR_ENVIO	= "006";

	//PARAMETROS PARA CREAR LOS EXPEDIENTE - VIATICOS
	public static final String CODIGO_PROCESO_VIATICO_SOLICITUD = "416";
	public static final String CODIGO_PROCESO_VIATICO_REEMBOLSO = "417";
	public static final String CODIGO_PROCESO_VIATICO_RENDICION = "418";
	
	// PARAMETROS PARA ACCIONES DEL EXPEDIENTE - VIATICOS
	public static final String ACCION_VIATICO_SOLIC_GENERAR_SOLICITUD = "001";
	public static final String ACCION_VIATICO_SOLIC_OTORGAR_ALTA = "002";
	public static final String ACCION_VIATICO_SOLIC_ENVIAR_AUTORIZADOR = "001"; 
	public static final String ACCION_VIATICO_SOLIC_BANDEJA_CAJA_CHICA = "004";
	public static final String ACCION_VIATICO_SOLIC_BANDEJA_CCP = "005";
	public static final String ACCION_VIATICO_SOLIC_BANDEJA_DCP = "006";
	public static final String ACCION_VIATICO_SOLIC_EN_RENDICION = "007";
	
	public static final String ACCION_VIATICO_REEMB_GENERAR_SOLICITUD = "001";
	public static final String ACCION_VIATICO_REEMB_ENVIAR_AUTORIZADOR = "002";
	public static final String ACCION_VIATICO_REEMB_OTORGAR_ALTA = "003";

	// PARAMETROS PARA ACCIONES DEL EXPEDIENTE - VIATICOS - AUTORIZADOR
	public static final String ACCION_VIATICO_SOLIC_AUTORIZADA_AUTORIZADOR = "002";
	public static final String ACCION_VIATICO_SOLIC_OBSERVADA_AUTORIZADOR = "003";
	public static final String ACCION_VIATICO_SOLIC_ANULADA_AUTORIZADOR = "004";
	
	public static final String ACCION_VIATICO_REEMBOLSO_AUTORIZADA_AUTORIZADOR = "001";
	public static final String ACCION_VIATICO_REEMBOLSO_OBSERVADA_AUTORIZADOR = "002";
	public static final String ACCION_VIATICO_REEMBOLSO_ANULADA_AUTORIZADOR = "003";
	
	//PARAMETROS PARA ACCIONES DEL EXPEDIENTE - VIATICOS - RENDICION
	public static final String ACCION_VIATICO_RENDICION_REGISTRADA = "001";
	public static final String ACCION_VIATICO_RENDICION_CERRADA = "002";
	
	//PARAMETROS PARA ESTADOS DE LA ACCION DEL EXPEDIENTE - VIATICOS
	public static final String ESTADO_ACCION_001_CONFORME_V = "001";
	public static final String ESTADO_ACCION_002_CONFORME_V = "002";
	public static final String ESTADO_ACCION_003_CONFORME_V = "003";
	public static final String ESTADO_ACCION_004_CONFORME_V = "004";
	public static final String ESTADO_ACCION_005_ANULADO_V = "005";
	public static final String ESTADO_ACCION_002_ANULADO_V = "002";
	public static final String ESTADO_ACCION_003_ANULADO_V = "003";
	public static final String ESTADO_ACCION_002_RECHAZADO_V = "002";

	// ESTADOS DE ACCION_VIATICO_SOLIC_GENERAR_SOLICITUD (SOLICITUD)
	public static final String ACCION_VIATICO_SOLIC_GENERAR_ESTADO_GENERADO = "001";
	public static final String ACCION_VIATICO_SOLIC_GENERAR_ESTADO_ANULADO = "002";
	public static final String ACCION_VIATICO_SOLIC_GENERAR_ESTADO_EN_LEVANTAMIENTO_OBSERVACION = "003";
	public static final String ACCION_VIATICO_SOLIC_GENERAR_ESTADO_AUTORIZADO_MANUALMENTE = "004";

	// ESTADOS DE ACCION_VIATICO_REEMB_GENERAR_SOLICITUD (REEMBOLSO)
	public static final String ACCION_VIATICO_REEMB_GENERAR_SOLICITUD_ESTADO_GENERADO = "001";
	public static final String ACCION_VIATICO_REEMB_GENERAR_SOLICITUD_ESTADO_ANULADO = "002";
	public static final String ACCION_VIATICO_REEMB_GENERAR_SOLICITUD_ESTADO_AUTORIZADO_MANUALMENTE = "003";
	public static final String ACCION_VIATICO_REEMB_GENERAR_SOLICITUD_ESTADO_EN_LEVANTAMIENTO_OBSERVACION = "004";
	
	// ESTADOS DE ACCION_VIATICO_SOLIC_OTORGAR_ALTA (SOLICITUD)
	public static final String ACCION_VIATICO_SOLIC_OTORGAR_ALTA_ESTADO_EN_ESPERA = "002";
	public static final String ACCION_VIATICO_SOLIC_OTORGAR_ALTA_ESTADO_APROBADO = "003";
	public static final String ACCION_VIATICO_SOLIC_OTORGAR_ALTA_ESTADO_RECHAZADO = "004";

	// ESTADOS DE ACCION_VIATICO_REEMB_OTORGAR_ALTA (REEMBOLSO)
	public static final String ACCION_VIATICO_REEMB_OTORGAR_ALTA_ESTADO_EN_ESPERA = "001";
	public static final String ACCION_VIATICO_REEMB_OTORGAR_ALTA_ESTADO_APROBADO = "002";
	public static final String ACCION_VIATICO_REEMB_OTORGAR_ALTA_ESTADO_RECHAZADO = "003";
	
	//PAGINAS
	public static final String SECUENCIA_EXPEDIENTE_PAGE = "ddjj/consultarDeclaracion/secuenciaExpedienteDeclaracion";
	
}

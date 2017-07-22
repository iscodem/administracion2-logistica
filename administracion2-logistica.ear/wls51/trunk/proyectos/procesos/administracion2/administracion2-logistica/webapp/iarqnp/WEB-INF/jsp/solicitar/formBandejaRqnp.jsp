<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="es">

<head>

	<title><fmt:message key="formBandejaRqnp.titulo" /> </title>

	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<c:set var="contextPathUrl" scope="session" value="${pageContext.request.contextPath}" />
	<link rel="stylesheet" href="/a/js/libs/bootstrap/3.3.1/css/bootstrap.min.css" />
	
	<!-- CSS - Tables -->
	<link rel="stylesheet" href="/a/js/libs/bootstrap/3.3.1/css/jquery.dataTables.css" />
	<link rel="stylesheet" href="/a/js/libs/jquery-ui/1.11.2/css/jquery-ui.min.css" />
	<link rel="stylesheet" href="/a/js/libs/jqgrid/4.6.0/css/ui.jqgrid.css" />
	
	<!-- CSS - DataTime -->
	<link rel="stylesheet" href="/a/js/recurso2/financiera/siga/viaticos/css/bootstrap-datetimepicker.css" />
	<link rel="stylesheet" href="/a/js/recurso2/financiera/siga/viaticos/css/bootstrap-datepicker-IE.css" />
	<link rel="stylesheet" href="/a/js/libs/bootstrap/3.3.1/css/bootstrap.min.css" />

	<link rel="stylesheet" href="${contextPathUrl}/css/common/common.css" />
	<link rel="stylesheet" href="${contextPathUrl}/css/common/general.css" />
	
	<!-- JS - Componentes -->
	<script src="/a/js/recurso2/financiera/siga/viaticos/js/respond.js"></script>
	
	<script>
		if (!Array.prototype.indexOf) {
			Array.prototype.indexOf = function(obj, start) {
				for (var i = (start || 0), j = this.length; i < j; i++) {
					if (this[i] === obj) {
						return i;
					}
				}
				return -1;
			};
		}
		
		if(typeof String.prototype.trim !== 'function') {
			String.prototype.trim = function() {
				return this.replace(/^\s+|\s+$/g, '');
			};
		};
	</script>
</head>

<body>
		<div class="container sigaContainer">
			<div class="row">
				<div class="col-xs-12">
					<div class="panel panel-primary">
						<div class="panel-heading"><fmt:message key="formBandejaRqnp.tituloPanel" /></div>
						<div class="panel-body">
							<div class="row">
								<div class="col-xs-12">
									<div class="panel panel-info">
										<div class="panel-body">
											
											<div class="row marginBottom5">
												<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
													<button id="btnNuevoRqnpCab" name="btnNuevoRqnpCab" type="button" class="btn btn-sm btn-primary">Nuevo</button>
													<span id="mensaje-sin-meta" class="pAdvertencia"> ${mensajeNoPoseeMeta} </span>
												</div>
											</div>
											
											
											<!-- <div class="panel panel-info"> --><!-- no utilizar panel-info para evitar la linea celeste -->
												<div class="row">
													<div id="divRqnpTable" class="col-md-12">
														<table id="tblRqnp"></table>
														<div id="divRqnpPagerTable"></div>
													</div>
												</div>
											<!--</div> -->
											
											
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>						
		</div>							
												
			
			
			<!-- EMERGENTE DE DATOS Envio -->
			<div  dojoType="dijit.Dialog" style= "width: 350px; height: 150px; display:none;"  id="formDialogEnvio" title="Procesando el Envío" execute="" >
			<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>
											<td class="bg" align="center"  colspan="2" > <h5>Se está Procesando el Envío.</h5></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>		
				</tr>
			</table>
		</div>
		
		<!-- modales -->

		<!-- popup modal Anular Requerimiento-->
		<div id="divModalPopupAnularRqnp" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
			<div id="divPopupContainerClassAnularRqnp" class="container sigaModalContainerSmall verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
					<div class="col-xs-12">
						<div id="divPopupPanelClass" class="panel panel-info">
							<div class="panel-heading" id="divPopupMensaje">
							  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
								<fmt:message key="formBandejaRqnp.anularRqnp.modalTitulo" />
							</div>
							<div class="panel-body">
							
							<!-- <form id="formAnularRqnp" method="POST" action="rqnp.htm">	 -->
							<form id="formAnularRqnp" >	
								<p id="msg-anular"></p>
								<div id="divMotivoAnulacion" class="form-group">
									<label for="txtMotivoAnulacion" class="control-label"><fmt:message key="formBandejaRqnp.anularRqnp.modalLblMotivo" /></label>
									<textarea class="form-control" id="txtMotivoAnulacion" name="txtMotivoAnulacion" placeholder="Ingrese motivo de anulación..." required="required"></textarea>
									<span id="spMotivoAnulacion" class="msgError"></span>
								</div>
								
								<div class="text-center">
									<button type="button" id="btnModalPopupAnularRqnpAceptar" class="btn btn-primary btn-sm" data-dismiss="modal">Anular</button>
									<button type="button" id="btnModalPopupAnularRqnpCancelar" class="btn btn-primary btn-sm" data-dismiss="modal">Cancelar</button>
								</div>
								
							</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
<%-- 
		<div id="divMensajeAutorizaEnviar" class="modal fade" data-keyboard="false" data-backdrop="static" >
			<div class="container sigaMensajeConfirmacionContainerBiggest verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
					<div class="col-xs-12">
						<div class="panel panel-primary">
							<div id="divTituloMensajeAutorizaEnviar" class="panel-heading"><fmt:message key="registrarSolicitud.mensajes.avisoEnviarSolicitudViaticoTitulo" /></div>
							<div class="panel-body">
								<div class="row">
									<div class="col-xs-12">
										<p id="spanMensajeAutorizaEnviar" class="popup-alineado-justify"><fmt:message key="registrarSolicitud.mensajes.avisoEnviarSolicitudViatico" /></p>
									</div>
								</div>
								<div class="row"> &nbsp; </div>
								<div class="row">
									<div class="col-xs-5 col-xs-offset-1">
										<button type="button" id="btnMensajeAutorizaEnviarSI" class="btn btn-primary btn-sm btn-block" data-dismiss="modal" title="Aceptar">Aceptar</button>
									</div>
									<div class="col-xs-5">
										<button type="button" id="btnMensajeAutorizaEnviarNO" class="btn btn-primary btn-sm btn-block" data-dismiss="modal"  title="Cancelar">Cancelar</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="divMensajeConfirmacionLoadingAplicativo" class="modal fade" data-keyboard="false" data-backdrop="static" >
			<div class="container sigaMensajeConfirmacionContainer verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
					<div class="col-md-12">
						<div class="panel panel-primary">
							<div id="divTituloPanelMensajeConfirmacionLoadingAplicativo" class="panel-heading divErrorMovilidadMensajeConfirmacionContainer"><fmt:message key="registrarSolicitud.txtCargando" /></div>
							<div class="panel-body">
								<div class="row">
									<div class="col-xs-offset-3">
										<span>
											<img src="/a/imagenes/loader.gif" alt="loader"><fmt:message key="registrarSolicitud.txtAjaxCargando" />
										</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	 --%>	

		<div id="divHiddenPost">
			<form id="formNuevoRqnp" method="POST" action="rqnp.htm"  >
				<input type="hidden" id="action" name="action" value="nuevoRqnp" />
			</form>
			
			<form id="formModificarRqnp" method="POST" action="rqnp.htm"  >
				<input type="hidden" id="action" name="action" value="modificarRqnp" />
				<input type="hidden" id="hidCodigoRqnp" name="hidCodigoRqnp" />
			</form>
			 
			<form id="formEnviarRqnp" method="POST" action="registrarSolicitud.htm">	
				<input type="hidden" id="action" name="action" value="enviarRqnp" />
				<input type="hidden" id="hidCodigoRqnp" name="hidCodigoRqnp" />
			</form>
			<!-- 
			<form id="formAnularRqnp" method="POST" action="rqnp.htm">	
				<input type="hidden" id="action" name="action" value="anularRqnp" />
				<input type="hidden" id="hidtxtCodigoRqnp" name="hidtxtCodigoRqnp" />
				<input type="hidden" id="hidtxtMotivoAnulacion" name="hidtxtMotivoAnulacion" />
			</form>
			 -->
		</div>
		
		<!-- Includes -->
		<jsp:include page="../general/formMensajerias.jsp" />
							
		<script src="/a/js/js.js"></script>
		<script src="/a/js/libs/jquery/1.11.1/jquery.min.js"></script>
		<script src="/a/js/libs/bootstrap/3.3.1/js/bootstrap.min.js"></script>
		<!-- <script src="/a/js/libs/bootstrap/3.3.2/plugins/jquery-validation-1.13.1/dist/jquery.validate.min.js" ></script> -->

		<!-- JS - Tables -->
		<script src="/a/js/libs/jquery/1.11.1/jquery.dataTables.js" type="text/javascript"></script>
		<script src="/a/js/libs/jqgrid/4.6.0/js/jquery.jqGrid.src.js"></script>
		<script src="/a/js/libs/jqgrid/4.6.0/js/i18n/grid.locale-es.js"></script>
		<script src="/a/js/libs/jquery-ui/1.11.2/js/jquery-ui.min.js"></script>

		<!-- JS - DateTime -->
		<script src="/a/js/recurso2/financiera/siga/viaticos/js/moment-with-locales.js"></script>
		<script src="/a/js/recurso2/financiera/siga/viaticos/js/bootstrap-datetimepicker.js"></script>
		<script src="/a/js/recurso2/financiera/siga/viaticos/js/bootstrap-datepicker-IE.js"></script>
		<script src="/a/js/recurso2/financiera/siga/viaticos/js/bootstrap-timepicker-IE.js"></script>

		<!-- JS - Componentes -->
		<script src="/a/js/recurso2/financiera/siga/viaticos/js/jquery.form.js"></script>
		<script src="${contextPathUrl}/js/common/common.js"></script>
		<script src="${contextPathUrl}/js/iarqnp.js"></script>
		<script src="${contextPathUrl}/js/solicitar/formBandejaRqnp.js"></script>


		<script type="text/javascript">
		// grillas de la pantalla principal
			var lsRqnpJSON = '${lsRqnp}';
			
			var requerimientoDatos = {
				'mensajes':{},
				'requerimientosList':lsRqnpJSON == ''? [] : $.parseJSON(lsRqnpJSON),
				'poseeMeta':"${poseeMeta}",
				'mensajeNoPoseeMeta':"${mensajeNoPoseeMeta}",
				'codResponsable' : "${colaborador.codigoEmpleado}",
				'codDependencia' : "${colaborador.codigoDependencia}",
				'uuoo' : "${colaborador.uuoo}"
			}
			
			debugger;
			
			// contexto de la aplicacion
			var contextPathUrl = "${contextPathUrl}";

			$(document).ready(function() {
				initElementsBandejaRqnp(requerimientoDatos.poseeMeta);
								
				if ( isBrowserInternetExplorer() ) {
					var myInterval = setInterval(triggerResizeEvent, 500);
				}
				
			});
		</script>
</body>

</html>
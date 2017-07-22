<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="es">

<head>

<title><fmt:message key="formRegistroRqnpMetas.titulo" /></title>

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
				<div class="panel panel-primary sinMargenPadding">
					<div class="panel-heading"><fmt:message key="formRegistroRqnpMetas.titulo" /></div>
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 col-lg-offset-1 col-md-offset-1 txtTextRightClase">
								<label class="btn-sm"><fmt:message key="formRegistroRqnpMetas.label.numReqNoProgramado" /></label>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
								<input type="text" class="form-control input-sm upperCaseTextClase" id="txtNumReqNoProgramado" value="${mapRqnp.numReqNoProgramado}" disabled />
							</div>
							
							<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 txtTextRightClase">
								<label class="btn-sm"><fmt:message key="formRegistroRqnpMetas.label.fechaRqnp" /></label>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
								<input type="text" class="form-control input-sm upperCaseTextClase" id="txtFechaRqnp" value="${mapRqnp.fechaRqnp}" disabled />
							</div>
							
						</div>
						
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 col-lg-offset-1 col-md-offset-1 txtTextRightClase">
								<label class="btn-sm"><fmt:message key="formRegistroRqnpMetas.label.responsable" /></label>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
								<input type="hidden" id="txtNumRegResponsable" value="${mapRqnp.numRegResponsable}" />
								<input type="text" class="form-control input-sm upperCaseTextClase" value="${mapRqnp.numRegResponsable} - ${mapRqnp.nomResponsable}" disabled />
							</div>
						</div>
						<div class="row">	
							<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 col-lg-offset-1 col-md-offset-1 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpMetas.label.uuoo" /></label>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
								<input type="hidden" id="txtUuooResponsable" value="${mapRqnp.uuooResponsable}" />
								<input type="hidden" id="txtNomUuooResponsable" value="${mapRqnp.nomUuooResponsable}" />
								<input type="text" class="form-control input-sm upperCaseTextClase" value="${mapRqnp.uuooResponsable} - ${mapRqnp.nomUuooResponsable}" disabled />
							</div>
						</div>
						
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 col-lg-offset-1 col-md-offset-1 txtTextRightClase">
								<label class="btn-sm"><fmt:message key="formRegistroRqnpMetas.label.montoAcumulado" /></label>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
								<input type="text" id="txtMontoAcumulado" class="form-control input-sm upperCaseTextClase" value="${mapRqnp.montoRqnp}" disabled />
							</div>
						</div>
						
						<div class="row">
							<div id="divRqnpDetalleTable" class="col-md-12">
								<table id="tblRqnpDetalle"></table>
								<div id="divRqnpDetallePagerTable"></div>
							</div>
						</div>
					</div>
				</div><!-- Fin panel-primary -->
				
				<div class="panel panel-primary sinMargenPadding">
					<div class="panel-heading"><fmt:message key="formRegistroRqnpMetas.tituloAfectacionPresupuestalPOI" /></div>
					<div class="panel-body"> 
						<div class="row">
							<div id="divRqnpMetasTable" class="col-md-12">
								<table id="tblRqnpMetas"></table>
								<div id="divRqnpMetasPagerTable"></div>
							</div>
						</div>
						
					</div>
				</div> 
						
				<div class="panel panel-info">
					<div class="panel-body"> 
						<div class="row paddingBottomTop10 text-center">
							<div class="col-xs-12">
								<button id="btnAgregarItemRqnpDetalle" type="button" class="btn btn-primary btn-sm pull-center boton100">Agregar ítem</button>
								<button id="btnEliminarItemRqnpDetalle" type="button" class="btn btn-primary btn-sm pull-center boton100">Eliminar ítem</button>
								<button id="btnGuardarItemRqnpDetalle" type="button" class="btn btn-primary btn-sm pull-center boton100">Guardar</button>
								<button id="btnSiguienteRqnpDetalle" type="button" class="btn btn-primary btn-sm pull-center boton100">Siguiente</button>
								<button id="btnInicioRqnpDetalle" type="button" class="btn btn-primary btn-sm pull-center boton100">Inicio</button>
							</div>
						</div>
						
					</div>
				</div> 		
				
			</div>
		</div>
	</div>	
	
		<!-- Includes -->
		<jsp:include page="../general/formMensajerias.jsp" />
		
		
		
		
		<div id="divHiddenPost">
			<form id="formRegistroRqnpMetas" method="POST" action="rqnpmetas.htm"  >
				<input type="hidden" id="action" name="action" value="iniciarRqnpMetas" />
				<input type="hidden" id="hidCodigoRqnp" name="hidCodigoRqnp" />
			</form>	
		</div>
		
		<!-- Fin de modales del detalle del requerimiento -->
		
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
		<script src="${contextPathUrl}/js/solicitar/formRegistroRqnpMetas.js"></script>
		
		
			<script type="text/javascript">
		// grillas de la pantalla principal
			var lsRqnpBienesJSON = '${listaBienes}';
			var lsRqnpMetasJSON = '${listaMetas}';
			//var lsRqnpJSON = '${lsRqnp}';
			debugger;
			
			var rqnpMetasDatos = {
				'codigoRqnp'			: "${mapRqnp.codigoRqnp}",
				'anioProceso'			: "${mapRqnp.anioProceso}",
				'textoSeleccioneOpcion'	:"<fmt:message key='formRegistroRqnpCab.seleccioneOpcion' />",
				'rqnpDetalleList'		:lsRqnpBienesJSON == ''? [] : $.parseJSON(lsRqnpBienesJSON)
				};
			
			var rqnpMetasItemDatos = {
				'codigoRqnp'			: "${itemRqnp.codigoRqnp}",
				'codigoBien'			: "${itemRqnp.codigoBien}",
				'rqnpDetalleList'		:lsRqnpBienesJSON == ''? [] : $.parseJSON(lsRqnpBienesJSON),
				'mensajes'				: {
										solicitaConfirmacionGrabado:"<fmt:message key='formRegistroRqnpDetalle.mensajes.solicitaConfirmacionGrabado' />",
										}
				};
			
			// contexto de la aplicacion
			var contextPathUrl = "${contextPathUrl}";
			
			var errorMessageRqnpMetasDatos = {
				itemYaFueAgregado: "<fmt:message key='formRegistroRqnpDetalle.messageError.itemYaFueAgregado' />",
				diferenteAuc: "<fmt:message key='formRegistroRqnpDetalle.messageError.diferenteAuc' />",
				tipoRuta03: "<fmt:message key='formRegistroRqnpDetalle.messageError.tipoRuta03' />"
			};
		
			$(document).ready(function() {
				
				initElementsRegistroRqnpMetas();
				
				if ( isBrowserInternetExplorer() ) {
					var myInterval = setInterval(triggerResizeEvent, 500);
				}
				
			});
		</script>
		
		
</body>

</html>
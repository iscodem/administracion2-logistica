<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="es">

<head>

<title><fmt:message key="formRegistroRqnpCab.titulo" /></title>

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
					<div class="panel-heading"><fmt:message key="formRegistroRqnpCab.titulo" /></div>
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-12">
								<!-- <div class="panel panel-info" id="divRegistroRqnpCabMod">  -->
								<div id="divRegistroRqnpCabMod">
									<jsp:include page="../general/formRegistroRqnpCabMod.jsp" />
						 		</div>
						 	</div>
						 </div>
					</div>
				</div>
			</div>
		</div>
	</div>
		
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
	<script src="${contextPathUrl}/js/general/buscarColaborador.js"></script>
	<script src="${contextPathUrl}/js/general/formRegistroRqnpCabMod.js"></script>
	
	<script type="text/javascript">
		var rqnpCabTipoAccion='R';
		
		// contexto de la aplicacion
		var contextPathUrl = "${contextPathUrl}";
		
		// grillas de la pantalla principal
		$(document).ready(function() {
			initElementsRegistroRqnpCabMod();
			if ( isBrowserInternetExplorer() ) {
				var myInterval = setInterval(triggerResizeEvent, 500);
			}
			
		});
	</script>
</body>
</html>


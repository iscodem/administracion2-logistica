<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><fmt:message key="formMenu.titulo" /></title>
		<c:set var="contextPathUrl" scope="session" value="${pageContext.request.contextPath}"/>
		<link rel="stylesheet" href="/a/js/libs/bootstrap/3.3.1/css/bootstrap.min.css" />	
		<link rel="stylesheet" href="/a/js/libs/bootstrap/3.3.1/css/jquery.dataTables.css"/>
		<link rel="stylesheet" href="/a/js/libs/jquery-ui/1.11.2/css/jquery-ui.min.css" />
		<link rel="stylesheet" href="/a/js/libs/jqgrid/4.6.0/css/ui.jqgrid.css" />
		<link rel="stylesheet" href="${contextPathUrl}/css/common/common.css" />
	</head>
	<body>
		<h3 style="text-align: center; margin-bottom: -20px;">Men&uacute;</h3>
		
		<div class="row">
			<table width="100%">
				<tr> <td></td><td>USUARIO: ${usuarioBean.login}</td></tr>
				<tr> <td></td><td>NOMBRES: ${usuarioBean.nroRegistro} - ${usuarioBean.nombreCompleto}</td></tr>
				<tr>
					<td width="15%">
						<ul style="margin-top: -40px;">
							<li>
								<form id="formLoginRqnp" method="POST" action="rqnp.htm" >
									<input type="hidden" name="action" value="login" />
									<button id="btnLoginRqnp" name="submit" type="submit" class="buttonHiperlinkClass">Inicio</button>
								</form>
							</li>
							<li class="paddingTop15">
	<%-- 								<a href="${contextPathUrl}/registrarSolicitud.htm?action=mostrarRegistrarViatico"  target="ventana">Reg.Sol(01)</a> --%>
								<form id="formBandejaRqnp" method="POST" action="rqnp.htm" target="ventana">
									<input type="hidden" name="action" value="bandeja" />
									<button id="btnBandejaRqnp" name="submit" type="submit" class="buttonHiperlinkClass">Solicitar</button>
								</form>
							</li>
							<li>
								<form id="formAutorizarRqnp" method="POST" action="bandejaji.htm" target="ventana">
									<input type="hidden" name="action" value="autoriza" />
									<button id="btnAutorizarRqnp" name="submit" type="submit" class="buttonHiperlinkClass">Autorizar</button>
								</form>
							</li>
							<li>
								<form id="formBandejaAuc" method="POST" action="rqnpauc.htm" target="ventana">
									<input type="hidden" name="action" value="aucbandeja" />
									<button id="btnBandejaAuc" name="submit" type="submit" class="buttonHiperlinkClass">Formular RQNP de AU</button>
								</form>
							</li>
							<li>
								<form id="formValidarSolicitudItem" method="POST" action="solincorbien.htm" target="ventana">
									<input type="hidden" name="action" value="iniciarSolicitudAUC" />
									<button id="btnValidarSolicitudItem" name="submit" type="submit" class="buttonHiperlinkClass">Formular RQNP consolidado</button>
								</form>
							</li>
							<li>
								<form id="formAprobarRqnp" method="POST" action="bandejaji.htm" target="ventana">
									<input type="hidden" name="action" value="aprueba" />
									<button id="btnAprobarRqnp" name="submit" type="submit" class="buttonHiperlinkClass">Aprobar</button>
								</form>
							</li>
							
							<li>
								<form id="formConsultarRqnp" method="POST" action="rqnpconsulta.htm" target="ventana">
									<input type="hidden" name="action" value="consulta" />
									<button id="btnConsultarRqnp" name="submit" type="submit" class="buttonHiperlinkClass">Consultar</button>
								</form>
							</li>
							
							</hr>
							<li class="paddingTop15">
								<form id="formConsultarCatalogo" method="POST" action="solincorbien.htm" target="ventana">
									<input type="hidden" name="action" value="iniciarConsultaCatalogo" />
									<button id="btnConsultarCatalogo" name="submit" type="submit" class="buttonHiperlinkClass">Consultar</button>
								</form>
							</li>
							<li>
								<form id="formSolicitarItem" method="POST" action="solincorbien.htm" target="ventana">
									<input type="hidden" name="action" value="iniciarSolicitud" />
									<button id="btnNotificarRendicion" name="submit" type="submit" class="buttonHiperlinkClass">Solicitar item</button>
								</form>
							</li>
							
							<li>
								<form id="formRegistrarReembolso" method="POST" action="registrarReembolso.htm" target="ventana">
									<input type="hidden" name="action" value="mostrarRegistrarViatico" />
									<button id="btnRegistrarReembolso" name="submit" type="submit" class="buttonHiperlinkClass">Validar solicitud de ítem </button>
								</form>
							</li>
							</hr>
							<li class="paddingTop15">
								<form id="formConsultarReembolso" method="POST" action="reembolso.htm" target="ventana">
									<input type="hidden" name="action" value="generarBandejaRevisionReembolso" />
									<button id="btnRevisarReembolso" name="submit" type="submit" class="buttonHiperlinkClass">Encargatura</button>
								</form>
							</li>
						</ul>
					</td>
					<td width="85%" height="650px">
						<iframe id="ventana" name="ventana"  width="99%" height="99%"></iframe>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
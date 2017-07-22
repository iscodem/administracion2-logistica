<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title><fmt:message key="formRegistro.titulo" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" LANGUAGE="JavaScript" SRC="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
<script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/iarqnp.js"> </script>

<script language="JavaScript" type="text/javascript">
dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.Dialog"); 
dojo.require("dojo.date.locale");
dojo.require("dijit.form.RadioButton");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dojo.data.ItemFileWriteStore");
dojo.require("dojo.data.ItemFileReadStore");

dojo.require("dojox.grid.EnhancedGrid");
dojo.require("dojox.grid.enhanced.plugins.Pagination");
dojo.require("dojox.grid.enhanced.plugins.NestedSorting");

dojo.require("dijit.form.SimpleTextarea");
dojo.require("dijit.form.FilteringSelect");
dojo.require("dojo.store.Memory");
dojo.require("dojo._base.connect");
dojo.require("dojo._base.event");
dojo.require("dojo._base.array");

var btnSalir_click = function(){
	//rqnpauc.htm?action=aucbandeja
		document.frmRegistro.action="bandejaauc.htm?action=bandejaauc";
		document.frmRegistro.submit();
}

function btnDescargar_click(){
	document.frmRegistro.action="bandejaauc.htm?action=descargarArchivoAnexo";
	//document.frmBandejaAuc.action="rqnpconsulta.htm?action=iniciaListaAcciones";	
	document.frmRegistro.submit();
}
var CONTEXTO_APP = "${pageContext.request.contextPath}";  

</script>

</head>

<body class="tundra">
	
	<center>
	
		<form id="frmRegistro" name="frmRegistro" action="bandejaauc.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id ="action" name="action" value="registrarCabRqnp"/>
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="${mapRqnp.codigoRqnp}" />
	
			<input type="hidden" id="txtvisor" name="txtvisor" value="0" />
			
			<input type="hidden" id="txtCod_uuoo1" name="txtCod_uuoo1" value="${mapRqnp.cod_uoc1}" />
			<input type="hidden" id="txtCod_uuoo2" name="txtCod_uuoo2" value="${mapRqnp.cod_uoc2}" />
			<input type="hidden" id="txtCod_uuoo3" name="txtCod_uuoo3" value="${mapRqnp.cod_uoc3}" />
			
			<!--<input type="hidden" name="ind_estado" value="0"/>-->
			<table width="95%" cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center"><fmt:message key="formRegistro.tituloFormato" /></div>
											</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td>&nbsp;</td>
							</tr>
						
												
							<tr>
								<td class="bgn">
									<div id="divMensaje" align="center"></div>
								</td>
							</tr>
							<tr>
								<td align="center">
									<table width="90%" cellpadding="2" cellspacing="1">

										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistro.numRqnp" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.codigoReqNoProgramado}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistron.responsable" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.codigoResposanble}"></c:out> &nbsp;&nbsp;
												<c:out value="${mapRqnp.resposanble}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistro.uuoo" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.codigoUuoo}"></c:out> &nbsp;&nbsp;
												<c:out value="${mapRqnp.dependencia}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistro.fechaRqnp" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.fechaRqnp}"></c:out>
											</td>
										</tr>
										
							
										
										
										<tr >											
											<td class="bg" align="left"><fmt:message key="formRegistro.motivo" /></td>
											<td class="bg" align="left">
												
												<input id="txtMotivo" name="txtMotivo" 
													 style="width: 450px;text-transform:uppercase;height:50px;min-height:1.5em;max-height:10em;overflow-y: auto;"
													dojoType="dijit.form.SimpleTextarea" 
													 uppercase = "true" 
													invalidMessage="Por favor ingrese el motivo"													
													maxlength="240"
													value ="${ mapRqnp.motivoRqnp}"
													readonly="readonly"
													onkeydown="preventBackspace();"
												/>
											</td>
										</tr>
										
										
										
										
										
									
										
										
										
									</table>
								</td>
								
							</tr>
							
						</table>
					</td>
				</tr>
			</table>
			<table width="95%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
						<button id="btnDescarga" name="btnDescarga" type="button" dojoType="dijit.form.Button" onclick="btnDescargar_click()"  style="width: 100px;">Descargar Formato</button>
						&nbsp;&nbsp;&nbsp;
						<button id="btnSalir" name="btnSalir" type="button" dojoType="dijit.form.Button" onclick="btnSalir_click()" style="width: 100px;">ir a Bandeja</button>
					</td>
				</tr>
			</table>
	
		</form>

	</center>
</body>
</html>


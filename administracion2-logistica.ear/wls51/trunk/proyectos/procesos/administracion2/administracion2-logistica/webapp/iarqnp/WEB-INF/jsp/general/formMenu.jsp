<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title><fmt:message key="formMenu.titulo" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" LANGUAGE="JavaScript" SRC="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>

<script type="text/javascript" LANGUAGE="JavaScript">
	dojo.require("dijit.form.Form");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dojo.data.ItemFileWriteStore");
	dojo.require("dojox.grid.EnhancedGrid");
    dojo.require("dojox.grid.enhanced.plugins.Pagination");
    dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
    dojo.require("dojo.parser");
    
</script>

<script type="text/javascript">


var menu_registro_rqnp = function(){
	document.formMenu.action="rqnp.htm?action=bandeja";
	//dojo.byId("action").value = "bandeja";
	document.formMenu.submit();
}

var menu_consulta_rqnp = function(){
	//document.formMenu.action="rqnp.htm?action=consulta";
	document.formMenu.action="rqnpconsulta.htm?action=consulta";
	//dojo.byId("action").value = "consulta";
	document.formMenu.submit();
}

var menu_AutorizaJI = function(){
	//document.formMenu.action="rqnp.htm?action=aprueba"; 
	document.formMenu.action="bandejaji.htm?action=autoriza"; 
	//dojo.byId("action").value = "aprueba";
	document.formMenu.submit();
}

var menu_Aprobar = function(){
	//document.formMenu.action="rqnp.htm?action=aprueba"; 
	document.formMenu.action="bandejaji.htm?action=aprueba"; 
	//dojo.byId("accion").value = "aprueba";
	document.formMenu.submit();
}

var menu_consultaJI = function(){
	document.formMenu.action="rqnp.htm?action=seguimiento";
	//dojo.byId("action").value = "seguimiento";
	document.formMenu.submit();
}

var menu_bandeja_auc = function(){
	document.formMenu.action="bandejaauc.htm?action=bandejaauc";
	//dojo.byId("action").value = "apruebaubg";
	document.formMenu.submit();
}
var menu_AutorizaOEC = function(){
	document.formMenu.action="rqnp.htm?action=apruebaoec";
	//dojo.byId("action").value = "apruebaoec";
	document.formMenu.submit();
}

var menu_registro_rqnp_auc = function(){
	document.formMenu.action="rqnpauc.htm?action=aucbandeja";
	//dojo.byId("action").value = "apruebaubg";
	document.formMenu.submit();
}
var menu_solicitar_item = function(){
	document.formMenu.action="solincorbien.htm?action=iniciarSolicitud";
	//dojo.byId("action").value = "apruebaubg";
	document.formMenu.submit();
}
var menu_validar_solicitud_item = function(){
	document.formMenu.action="solincorbien.htm?action=iniciarSolicitudAUC";
	//dojo.byId("action").value = "apruebaubg";
	document.formMenu.submit();
}
var menu_consulta_catalogo = function(){
	document.formMenu.action="solincorbien.htm?action=iniciarConsultaCatalogo";
	//dojo.byId("action").value = "apruebaubg";
	document.formMenu.submit();
}

//Otro EAR de encargos (rrhh-planilla-cas.ear)
var menu_encargos = function(){
	location.href="http://150.200.54.44:7001/ol-ti-iacas/placasS06Alias?action=abreListaEncargos";
	//http://150.200.54.44:7001/ol-ti-iacas/placasS06Alias?action=abreListaEncargos
	//document.formMenu.submit();
}


dojo.addOnLoad(function() {	
	
	divShow("divRqnpRegistro");
	divShow("divRqnpConsulta");
	divShow("divRqnpAutorizaJI");
	divShow("divRqnpAtenderAUC");
	divShow("divRqnpAuc");
	divShow("divValidarSolicitudItem");
	divShow("divEncargos");
	
		
});



</script>

</head>

<body class="tundra">
	<center>

		<form id="formMenu" name="formMenu" action="rqnp.htm" method="POST" dojoType="dijit.form.Form" enctype="multipart/form-data" >
			<!-- input type="hidden" id="action"  name="action" value="iniciarconsulta"/ -->
			<input type="hidden" id="menu" name="menu" value="<c:out value='${menu}'/>" />
		
			
			<table width="95%" cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td  align="center" >
						<table align="center" cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center"><fmt:message key="formMenu.titulo" /></div>
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
			  				    			<td class="bg" align="left">
			  				    			<div id="divRqnpRegistro" style="display: block;" >
			  									&nbsp;&nbsp;&nbsp; <a href="#" onclick="menu_registro_rqnp();"> Solicitar  </a>
			  									</div>
			  									
			              					</td>
			  				 			</tr>
										
										<tr>
			  				    			<td class="bg" align="left">
			  				    			<div id="divRqnpAutorizaJI" style="display: block;" >
			  									&nbsp;&nbsp;&nbsp; <a href="#" onclick="menu_AutorizaJI();"> Autorizar </a>
			  									</div>
			  									
			              					</td>
			  				 			</tr>
										
			  				 			<!-- (INICIO) PAS201780000300007 -->
			  				 			
			  				 			<tr>
			  				    			<td class="bg" align="left">
			  				    			<div id="divRqnpAtenderAUC" style="display: block;" >
			  									&nbsp;&nbsp;&nbsp; <a href="#" onclick="menu_bandeja_auc();"> Formular requerimiento de AU</a> <!-- Atender -->
			  								</div>
			  									
			              					</td>
			  				 			</tr> 
			  				 			
			  				 			<tr>
			  				    			<td class="bg" align="left">
			  				    			<div id="divRqnpAuc" style="display: block;" >
			  									&nbsp;&nbsp;&nbsp; <a href="#" onclick="menu_registro_rqnp_auc();"> Formular requerimiento consolidado</a> <!-- Solicitar Requerimiento -->
			  								</div>
			  									
			              					</td>
			  				 			</tr>
			  				 			
			  				 			<tr>
			  				    			<td class="bg" align="left">
			  				    			<div id="divRqnpAutorizaJI" style="display: block;" >
			  									&nbsp;&nbsp;&nbsp; <a href="#" onclick="menu_Aprobar();"> Aprobar </a> <!-- Autorizar -->
			  									</div>
			  									
			              					</td>
			  				 			</tr>
			  				 			
			  				 			<tr>
			  				    			<td class="bg" align="left">
			  				    			<div id="divRqnpConsulta" style="display: block;" >
													&nbsp;&nbsp;&nbsp;<a href="#" onclick="menu_consulta_rqnp();"> Consultar  </a>
											</div>
			              					</td>
			  				 			</tr>
			  				 			<!-- (FIN) PAS201780000300007 -->
			  				 			
			  				 			<tr><td><hr></td></tr>
			  				 			<tr>
			  				    			<td class="bg" align="left">
			  				    			<div id="divConsultaCatalogo" style="display: block;" >
													&nbsp;&nbsp;&nbsp;<a href="#" onclick="menu_consulta_catalogo();"> Consultar</a> <!-- Consultar Catalogo -->
											</div>
			              					</td>
			  				 			</tr>
			  				 			
			  				 			<tr>
			  				    			<td class="bg" align="left">
			  				    			<div id="divSolicitarItem" style="display: block;" >
													&nbsp;&nbsp;&nbsp;<a href="#" onclick="menu_solicitar_item();"> Solicitar item  </a>
											</div>
			              					</td>
			  				 			</tr>
			  				 			
			  				 			<tr>
			  				    			<td class="bg" align="left">
			  				    			<div id="divValidarSolicitudItem" style="display: block;" >
			  									&nbsp;&nbsp;&nbsp; <a href="#" onclick="menu_validar_solicitud_item();"> Validar solicitud de ítem </a> <!-- Validar solicitud de ítem -->
			  								</div>
			              					</td>
			  				 			</tr>
			  				 			
			  				 			<tr><td><hr></td></tr>
			  				 			
			  				 			<tr>
			  				    			<td class="bg" align="left">
			  				    			<div id="divEncargos" style="display: block;" >
													&nbsp;&nbsp;&nbsp;<a href="#" onclick="menu_encargos();"> Encargatura</a>
											</div>
			              					</td>
			  				 			</tr>
									</table>
								</td>
								
							</tr>
							
						</table>
					</td>
				</tr>
			</table>
			
		</form>
		
	</center>
</body>

<script type="text/javascript">



function divHide(layer_ref) { 

	var state = 'none'; 
	if (document.all) { //IS IE 4 or 5 (or 6 beta) 
		eval( "document.all." + layer_ref + ".style.display = state"); 
	} 
	if (document.layers) { //IS NETSCAPE 4 or below 
		document.layers[layer_ref].display = state; 
	} 
	if (document.getElementById &&!document.all) { 
		hza = document.getElementById(layer_ref); 
		//hza.style.display = state; 
	} 
} 


function divShow(layer_ref) { 
	var state = 'block'; 
	
	if (document.all) { //IS IE 4 or 5 (or 6 beta) 
		eval( "document.all." + layer_ref + ".style.display = state"); 
	} 
	if (document.layers) { //IS NETSCAPE 4 or below 
		document.layers[layer_ref].display = state; 
	} 
	if (document.getElementById &&!document.all) { 
		hza = document.getElementById(layer_ref); 
		//hza.style.display = state; 
	} 
} 


	
</script>
</html>


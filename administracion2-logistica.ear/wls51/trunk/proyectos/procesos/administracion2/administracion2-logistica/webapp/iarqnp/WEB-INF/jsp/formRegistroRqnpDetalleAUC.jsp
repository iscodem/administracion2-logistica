<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<style type="text/css">
.dijitValidationTextBox input.dijitInputInner,
	.dijitNumberTextBox input.dijitInputInner,
	.dijitCurrencyTextBox input.dijitInputInner,
	.dijitSpinner input.dijitInputInner {
		padding-right: 1px;
		text-align: right;
	}
</style>

<title><fmt:message key="formRegistro.titulo" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />


<script type="text/javascript" src="/a/js/js.js"></script>
 <script type="text/javascript" LANGUAGE="JavaScript" SRC="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
 <script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/iarqnp.js"> </script>  
<script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/formRegistroRqnpDetalleAUC_structure.js"> </script>
<script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/formRegistroRqnpDetalleAUC.js"> </script>

<script type="text/javascript">
var tipo_auc="<c:out value='${mapDatos_auc.tipo_auc}'></c:out>";
var cod_dep_auc="<c:out value='${mapDatos_auc.cod_dep}'></c:out>";
var dependenciaQueConsolida = "<c:out value='${mapDatos_auc.uuoo}'></c:out>" + " - " +"<c:out value='${mapDatos_auc.nomUuoo}'></c:out>";

var listaBienes=[
	<c:forEach items="${listaBienes}"  var="bienesBean" varStatus="status">
    	{
    		vcodigoRqnp:"${bienesBean.codigoRqnp}", 
    		vxcodigoBien: "${bienesBean.codigoBien}",
    		vcodigoBien: "${bienesBean.codigoBien}",
    		vtipo_bien: "${bienesBean.tipo_bien}",
    		vcodigoUnidad:"${bienesBean.codigoUnidad}", 
    		vcantBien:"${bienesBean.cantBien}",
    		vxcantBien:"${bienesBean.cantBien}",
    		vprecioUnid: "${bienesBean.precioUnid}" , 
    		vxprecioUnid: "${bienesBean.precioUnid}" , 
    		vsaldo: "${bienesBean.saldo}" ,
    		vprecioTotal: "${bienesBean.precioTotal}" ,
    		vexceso: "${bienesBean.exceso}" ,
    		vdesBien:"${bienesBean.desBien}",
    		vdesUnid:"${bienesBean.desUnid}",
    		vcodClasificador:"${bienesBean.codClasificador}",
    		vxcodClasificador:"${bienesBean.codClasificador}",
    		vdesClasificador:"${bienesBean.desClasificador}",
    		vind_especializado:"${bienesBean.ind_especializado}",
    		vnro_adjuntos:"${bienesBean.nro_adjuntos}",
    		vauct1:"${bienesBean.auct1}",
    		vauct2:"${bienesBean.auct2}",
    		vauct3:"${bienesBean.auct3}",
    		vestadoDesconcentrado:"${bienesBean.estadoDesconcentrado}",
    		vauct_name:"${bienesBean.auct_name}",
    		vimage:" ",
    		vlink:"<a href='#' onclick="+'"'+"adjuntarArchivo('${bienesBean.codigoRqnp}','${bienesBean.codigoBien}','${bienesBean.numeroArchivo}' );"+'"'+"> Adjuntar</a> ",
    		vlinkDetalle:"<a href='#' onclick="+'"'+"btnModificar_detalle_click('${bienesBean.codigoRqnp}','${bienesBean.codigoBien}' );"+'"'+"> Detalle</a> "
		}   
    	${not status.last ? ',' : ''}
	</c:forEach>
];

var vcod_finalidad=[
	<c:forEach items="${lsFinalidad}"  var="finalidad" varStatus="status">
    {	id:"${finalidad.cod}",
    	name:"${finalidad.name}" 
    }
    ${not status.last ? ',' : ''}
	</c:forEach>
	];

var vlsNecesidad=[
	<c:forEach items="${lsNecesidad}"  var="necesidad" varStatus="status">
    {	id:"${necesidad.cod}",
    	name:"${necesidad.name}" 
    }
    ${not status.last ? ',' : ''}
	</c:forEach>
	];

var vlsTipoNecesidad=[
	<c:forEach items="${lsTipoNecesidad}"  var="tipo" varStatus="status">
    {	id:"${tipo.cod}",
    	name:"${tipo.name}" 
    }
    ${not status.last ? ',' : ''}
	</c:forEach>
	];

var vlsPersonal= [
	<c:forEach items="${lsPersonal}"  var="personaBean" varStatus="status">
    	{
    		vcodigoEmpleado:"${personaBean.codigoEmpleado}", 
    		vnombre_completo: "${personaBean.nombre_completo}",
    		vnumero_registro: "${personaBean.numero_registro}",
    		vdependencia: "${personaBean.dependencia}"
		}   
    	${not status.last ? ',' : ''}
	</c:forEach>
];

</script>


<script type="text/javascript">
var btnGuardar_click = function () {
	var grid = dijit.byId('gridDetalle');
	var sw="0";
	var count = parseInt(0);
	if(	grid.store == null){
		alert("Por favor ingrese los ítems del requerimiento DE AUC ");
		return ;
	}else{
		if((grid.store._arrayOfAllItems.length > 0 )){
			grid.store.fetch({
				onItem: function(item, request){
							var val_precio = parseFloat(item.vprecioUnid);
							count++;
							if (val_precio == 0){
								sw="1";
							}
		 				}
			});
		}
	}
	if (count==0){
		alert("Por favor ingrese los ítems del requerimiento DE AUC");
		return ;
	}
	if (sw=="1"){
		alert("El Precio Unitario debe ser mayor que cero");
		return ;
	}
	
	if(!dijit.byId('frmRegistro').validate()){
		alert("Existen Datos Incorrectos");
		return;
	}
	
  	dijit.byId('btnGuardar').setAttribute("disabled", true);
  	
  	dojo.byId("action").value = "registrarRqnpDetalle";
	dojo.byId("jParametro").value = "formRegistroRqnpDetalleAUC"; //MODIFICADO DPC
	
	//
	btnValidarContacto();
	//dijit.byId('frmRegistro').submit()
}

var btnModificar_detalle_click = function(cod_rqnp,cod_bien){
	
	var formDlg = dijit.byId("formDialogRequerimientoDetalle");
	dojo.byId("action").value = "recuperarBienEntregaJson";
	
	dojo.byId("txtCodigoRqnp").value = cod_rqnp; 
	dojo.byId("txtCodigoBien").value = cod_bien; 
	
	formDlg.show();
	formDlg.onFocus();
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			headers: { "Content-Type": "application/json; charset=UTF-8"},
			load : function(response, ioArgs) {
				
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]" && response.data != "{}") {
						
						var data = eval("(" +unescape(response.data )+ ")");
					//	if (response.data.error !="0"){
							var val_nombre = data.raz_social;
							var val_cod_cont=data.cod_cont
							var val_num_ruc=data.num_ruc
							 var  strObject = new  String(val_nombre); 
						
							if (trim(strObject)=="undefined"){
								val_nombre="";
								val_num_ruc="";
							}
							dojo.byId("txtNom_proveedor3").value 		= val_nombre; 
							dojo.byId("txtCod_proveedor").value 		= data.cod_cont;
							dojo.byId("txtNum_ruc_prov3").value 		= val_num_ruc;
							dojo.byId("txtObs_justificacion3").value 	= data.obs_justificacion;
							dojo.byId("txtObs_plazo_entrega3").value 	= data.obs_plazo_entrega;
							dojo.byId("txtObs_lugar_entrega3").value 	= data.obs_lugar_entrega;
							dojo.byId("txtObs_dir_entrega3").value 		= data.obs_dir_entrega;						
					}else{
						
						dojo.byId("txtNom_proveedor3").value 		= ""; 
						dojo.byId("txtCod_proveedor").value 		= "";
						dojo.byId("txtNum_ruc_prov3").value 		= "";
						dojo.byId("txtObs_justificacion3").value 	= "";
						dojo.byId("txtObs_plazo_entrega3").value 	= "";
						dojo.byId("txtObs_lugar_entrega3").value 	= "";
						dojo.byId("txtObs_dir_entrega3").value 		= "";	
					}
				}else{
					var data = eval("(" + response.data + ")");
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				//alert("<fmt:message key='error.ajax'/>");
			},
			form: dojo.byId("frmRegistro")
	};		
	//dojo.xhrPost(enviar);
	dojo.xhrGet(enviar);
	
}

//INICIO DPORRASC PASE
function btnGuardarEntregaDetalle_click(){	
 	var formDlg = dijit.byId("formDialogRequerimientoDetalle");
 	
 	dojo.byId("action").value = "registrarDetalleEntregaBienJson";
 	dojo.byId("txtNum_ruc_prov").value = document.getElementById("txtNum_ruc_prov3").value;
 	dojo.byId("txtObs_justificacion").value = document.getElementById("txtObs_justificacion3").value;
 	dojo.byId("txtObs_plazo_entrega").value = document.getElementById("txtObs_plazo_entrega3").value;
 	dojo.byId("txtObs_lugar_entrega").value = document.getElementById("txtObs_lugar_entrega3").value;
 	dojo.byId("txtObs_dir_entrega").value 	= document.getElementById("txtObs_dir_entrega3").value;
 	
 	if(dojo.byId("txtNum_ruc_prov").value!="" ){
 		//Validar la longitud del ruc
 		var val_ruc =dojo.byId("txtNum_ruc_prov").value;
 		if (val_ruc.length ==11 && dojo.byId("txtCod_proveedor3").value!="-1"){
 			if(dojo.byId("txtObs_justificacion").value==""  ){
 				alert("<fmt:message key='formRegistro.alerta.obs_justificacion'/> " );	
 				return;
 			}
 		}else{
 			alert("<fmt:message key='formRegistro.alerta.cod_proveedor'/> " );	
 			return;
 		}
 	}
 	
 	
 	var val_nombre="";
 	//llamada AJAX
 	var enviar = {
 			handleAs : "json",
 			headers: { "Content-Type": "application/json; charset=UTF-8"},
 			load : function(response, ioArgs) {
 				if(response.data.error==null){
 					if (response.data != "" && response.data != "[]") {
 						var data = eval("(" +unescape( response.data )+ ")");
 						var mensaje = data.mensaje;
 					  		alert(mensaje);
 					  		formDlg.hide();
 					}
 				}else{
 					var data = eval("(" + response.data + ")");
 					alert(response.data.mensaje);
 				}
 			},
 			timeout : 25000,
 			error : function(error, ioArgs) {
 				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
 				//alert("<fmt:message key='error.ajax'/>");
 			},
 			form: dojo.byId("frmRegistro")
 	};	
 	
 	//dojo.xhrPost(enviar);
 	dojo.xhrGet(enviar);
 }
//FIN DPORRASC PASE
	
	
function btnRecuperarContratista(){	
	dojo.byId("action").value = "recuperarContratistaRqnpJson";
	dojo.byId("txtNum_ruc_prov").value = document.getElementById("txtNum_ruc_prov2").value;
	var val_ruc =dojo.byId("txtNum_ruc_prov2").value ;
	if ( val_ruc !=""){
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						
						var data = eval("(" + unescape(response.data )+ ")");
					//	if (response.data.error !="0"){
							var nombre = data.raz_social;
							var val_cod_cont=data.cod_cont
							if (val_cod_cont=="-1"){
								//alert("<fmt:message key='error.registrarRqnp.contratista'/>");
								alert(data.raz_social);
							}else{
								//dojo.byId("txtNum_ruc_prov").value = data.num_ruc;
							}
							dojo.byId("txtNom_proveedor2").value = nombre; 
							dojo.byId("txtCod_proveedor").value = data.cod_cont;
					}
				}else{
					var data = eval("(" + response.data + ")");
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				//alert("<fmt:message key='error.ajax'/>");
			},
			form: dojo.byId("frmRegistro")
	};		
	dojo.xhrPost(enviar);
	}else{
		dojo.byId("txtNom_proveedor").value = ""; 
	}
}	




//DPORRASC
function tipoConsultaSetea(valor){
	dojo.byId("jtipoConsulta").value = valor;
}
function formatear(){
	dojo.byId("txtMontoAcumulado").value =addCommas(dojo.byId("txtMontoAcumulado").value);
}

function btnRecuperarContacto(){	
	dojo.byId("action").value = "recuperarContactoRqnpJson";
	dojo.byId("txtReg_contacto").value = document.getElementById("txtReg_contacto2").value;
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						
						var data = eval("(" + unescape(response.data) + ")");
					//	if (response.data.error !="0"){
							var nombre = data.nom_contacto;
							dojo.byId("txtNom_contacto2").value = nombre; 
							dojo.byId("txtCod_contacto").value = data.cod_contacto;
					
					}
				}else{
					var data = eval("(" + response.data + ")");
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				//alert("<fmt:message key='error.ajax'/>");
			},
			form: dojo.byId("frmRegistro")
	};	
	dojo.xhrPost(enviar);
}	



function btnValidarContacto(){	

	var visor= dojo.byId("txtvisor").value 
		if (visor=="0"){
			dojo.byId("action").value 		= "registrarRqnpDetalleAUC"; //DERIVA AL SERVLET (RqnpAucDetalleController) 
		}else{
			dojo.byId("action").value 		= "modificarRqnpMetasAUC";
		}
	dijit.byId('frmRegistro').submit();
					  
}

</script>

</head>

<body class="tundra">
	<center>

		<form id="frmRegistro" name="frmRegistro" action="rqnpaucdetalle.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="action" name="action" value="registrarRqnpDetalleAUC"/>
			<input type="hidden" id="jParametro" name="jParametro" value="formRegistroDetalle"/>
			<input type="hidden" id="jtipoConsulta" name="jtipoConsulta" value="atencion"/>
			<input type="hidden" id="jcodigo_rqnp" name="jcodigo_rqnp" value="atencion"/>
			<input type="hidden" id="jcodigo_bien" name="jcodigo_bien" value="atencion"/>
			<input type="hidden" id="jcodigo_dep" name="jcodigo_dep" value="<c:out value='${mapRqnp.codigoDependencia}'/>" />
			<input type="hidden" id="janio_ejec" name="janio_ejec" value="<c:out value='${mapRqnp.anioProceso}'/>" />
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
			<input type="hidden" id="txtCodigoBien" name="txtCodigoBien" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
			<input type="hidden" id="txtNum_reg" name="txtNum_reg" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
			<input type="hidden" id="path_url" name="path_url"  value="rqnpauc.htm?action=modificarRqnp"/>
			<input type="hidden" id="txtvisor" name="txtvisor" value="<c:out value='${visor}'/>" />
				
			<input type="hidden" id="txtCod_contacto" name="txtCod_contacto" value="<c:out value='${mapRqnp.cod_contacto}'/>" />
			<input type="hidden" id="jPer_tipoConsulta" name="jPer_tipoConsulta" value="" />
			<input type="hidden" id="jPer_parametro" name="jPer_parametro" value="" />
			<input type="hidden" id="txtCod_proveedor" name="txtCod_proveedor" value="<c:out value='${mapRqnp.cod_cont}'/>" />
			
			<input type="hidden" id="txtCod_proveedor3" name="txtCod_proveedor3" value="<c:out value='${mapRqnp.cod_cont}'/>" />
			<input type="hidden" id="txtNum_ruc_prov" name="txtNum_ruc_prov" value="" /> <!-- ENVIA AL RqnpAucDetalleControlle -->
			
			<input type="hidden" id="txtReg_contacto" name="txtReg_contacto" value="" /> <!-- ENVIA AL RqnpAucDetalleControlle -->
			<input type="hidden" id="txtAnexo_contacto" name="txtAnexo_contacto" value="" />
			<input type="hidden" id="txtCod_necesidad" name="txtCod_necesidad" value="" />
			<input type="hidden" id="txtTipo_necesidad" name="txtTipo_necesidad" value="" />
			<input type="hidden" id="txtNom_tip_necesidad" name="txtNom_tip_necesidad" value="" />
			<input type="hidden" id="txtMotivo" name="txtMotivo" value="" />
			
			<input type="hidden" id="txtCod_finalidad" name="txtCod_finalidad" value="" />
			<input type="hidden" id="txtObs_dir_entrega" name="txtObs_dir_entrega" value="" />
			<input type="hidden" id="txtObs_lugar_entrega" name="txtObs_lugar_entrega" value="" />
			<input type="hidden" id="txtObs_plazo_entrega" name="txtObs_plazo_entrega" value="" />
			<input type="hidden" id="txtObs_justificacion" name="txtObs_justificacion" value="" />
			
			<input type="hidden" id="jtipoRegistroVinculo" name="jtipoRegistroVinculo" value="${mapRqnp.ind_vinculo}" />
			<input type="hidden" id="jtipoRegistroPrestamo" name="jtipoRegistroPrestamo" value="${mapRqnp.ind_prestamo}" />
			
			<!-- INICIO: DPORRAS -->
			<input type="hidden" id="txtNum_UUOO" name="txtNum_UUOO" value="" />
			<input type="hidden" id="txtNum_UUOO1" name="txtNum_UUOO1" value="" />
			<input type="hidden" id="txtNum_UUOO2" name="txtNum_UUOO2" value="" />
			<input type="hidden" id="txtNum_UUOO3" name="txtNum_UUOO3" value="" />
			<input type="hidden" id="txtCod_UUOO1" name="txtCod_UUOO1"  value='${mapRqnp.cod_uoc1}'/>
			<input type="hidden" id="txtCod_UUOO2" name="txtCod_UUOO2"  value='${mapRqnp.cod_uoc2}'/>
			<input type="hidden" id="txtCod_UUOO3" name="txtCod_UUOO3"  value='${mapRqnp.cod_uoc3}'/>
			
			<input type="hidden" id="txtCod_ambito" name="txtCod_ambito"  value=""/>
			<input type="hidden" id="txtCod_tipo_prog" name="txtCod_tipo_prog"  value=""/>
			
			<input type="hidden" id="txtTipo_auc" name="txtTipo_auc"  value='${mapRqnp.tipo_auc}'/>
			<!-- FIN: DPORRAS -->
			
			<!--<input type="hidden" name="ind_estado" value="0"/>-->
			
			<table width="99%" cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center"><fmt:message key="formRegistroAuc.titulo" /></div>
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
											<td class="bg" align="right"><fmt:message key="formRegistro.numRqnp" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.codigoReqNoProgramado}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistron.responsable" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.codigoResposanble}"></c:out> &nbsp;&nbsp;
												<c:out value="${mapRqnp.resposanble}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.uuoo" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.codigoUuoo}"></c:out> &nbsp;&nbsp;
												<c:out value="${mapRqnp.dependencia}"></c:out>
											</td>
										</tr>
										<!-- inicio: dporrasc -->
										<!--
										<tr>											
											<td class="bg" align="right">Tipo de AUC: </td>&nbsp;&nbsp;
											<td class="bg" align="left">
												<c:out value='${mapDatos_auc.tipo_auc}'></c:out> 
											</td>
										</tr>
										-->
										<!--
										<tr>											
											<td class="bg" align="right">Codigo Dep: </td>&nbsp;&nbsp;
											<td class="bg" align="left">
												<c:out value='${mapDatos_auc.cod_dep}'></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right">codigo Plaza: </td>&nbsp;&nbsp;
											<td class="bg" align="left">
												<c:out value='${mapDatos_auc.cod_plaza}'></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right">Nombre dependencia: </td>&nbsp;&nbsp;
											<td class="bg" align="left">
												<c:out value='${mapDatos_auc.nom_dep}'></c:out>
											</td>
										</tr>
										-->
										<!-- fin:dporrasc -->
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.fechaRqnp" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.fechaRqnp}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.Monto" /></td>
											<td class="bg" align="left">
											<input id="txtMontoAcumulado" name="txtMontoAcumulado"   
												value="<c:out value='${mapRqnp.montoRqnp}'></c:out>"
													 style="width:80px;text-align:right"
													dojoType="dijit.form.ValidationTextBox" 
																										
													maxlength="50"
													readonly="readonly"
													
												/>
												&nbsp;&nbsp;&nbsp;
												<button id="btnModificar" name="btnModificar" type="button" dojoType="dijit.form.Button" onclick="btnModificar_click()">Detalle</button>
											
											</td>
										</tr>
										
									</table>
								</td>
								
							</tr>
							
							<tr>
								<td>
									<table width="95%" cellpadding="2" cellspacing="2">
										<tr>
											<td align="center">
												 <table>
												 <tr>
												 <td>
												 		&nbsp;&nbsp;&nbsp;
														<div id="divbtnAdd" style="display: none;" >
															<button id="btnAdd" name="btnAdd" type="button" dojoType="dijit.form.Button" onclick="btnBuscar_click()">Agregar</button>
														</div>
												 </td>
												 <td>
														&nbsp;&nbsp;&nbsp;
														<div id="divbtnDel" style="display: none;" >
															<button id="btnDel" name="btnDel" type="button" dojoType="dijit.form.Button" onclick="btnDel_click()">Eliminar</button>
														</div>
												</td>
												
												<td>
													&nbsp;&nbsp;&nbsp;
													<div id="divbtnGuardar" style="display: none;" >
														<button id="btnGuardar" name="btnGuardar" type="button" dojoType="dijit.form.Button" onclick="btnGuardar_click()">Guardar</button>
													</div>
												</td>
												<td>
													&nbsp;&nbsp;&nbsp;
													<div id="divbtnSiguiente" style="display: none;" >
														<button id="btnSiguiente" name="btnSiguiente" type="button" dojoType="dijit.form.Button" onclick="btnSiguiente_click">Siguiente</button>
													</div>
												</td>
												<!-- 
												<td>
														&nbsp;&nbsp;&nbsp;
														<div id="divbtnSol" style="display: none;" >
															<button id="btnSol" name="btnSol" type="button" dojoType="dijit.form.Button" onclick="btnSol_click()">Solicitar ítem</button>
														</div>
												</td>
												 -->
												<td>
													&nbsp;&nbsp;&nbsp;
													<div id="divbtnSalir" style="display: none;" >
														<button id="btnSalir" name="btnSalir" type="button" dojoType="dijit.form.Button" onclick="btnSalir_click()">Inicio</button>
													</div>
												</td>
												</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							
							<tr>
								<td>
									<table width="100%" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td colspan="2">
												<div id="gridDetalle"  > </div>
											</td>
										</tr>
										<!-- 
										<tr>
											<td colspan="2" align="left">
												<P>(*) La autorización del ítem corresponde al Área Usuaria Canalizadora indicada.</P>
											</td>
										</tr>
										 -->
										<tr>
											<td colspan="2" align="left">
												&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
										<tr>
											<td width="4%">
												<div id="divmsgImage" style="display: none;" >
													<!-- <img name="eje01" src="/a/imagenes/circulorojo.gif"/> -->
												</div>	 
											</td>
											<td width="96%" align="left" >
												<div id="divmsgExceso" style="display: none;" >
													<!-- <P> El ítem NO cuenta con SALDO para su atención por el canal de Compras Directas. Por lo tanto será atendido por otro canal
													</P> -->
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
			
			<!-- DIALOGO CATALOGO ---------------------------------------------------------------------------- -->
			<div  dojoType="dijit.Dialog" style= "width: 720px; height: 400px; display:none;"  id="formDialogCatalogo" title="Buscar ítems" execute="">
			<table width="100%"  height="80%"  cellpadding="1" cellspacing="1" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Consulta de Catálogo</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1">
										<tr>											
											<td class="bg" align="right">Tipo de Búsqueda</td>
											<td class="bg" align="left">
												<input id="tipoConsulta1" name="tipoConsulta" 
												value="C"
													type="radio"
													dojoType="dijit.form.RadioButton" 
													onclick="tipoConsultaSetea(this.value);"
																									
												/>
												<label for="tipoConsulta1">Código</label> 
												
												<input id="tipoConsulta2" name="tipoConsulta" 
													type="radio"
													value="D"
													dojoType="dijit.form.RadioButton" 
													onclick ="tipoConsultaSetea(this.value);"												
												/>
												<label for="tipoConsulta2">Descripción</label> 
												&nbsp;&nbsp;Parámetros:
												
												<input id="txtParametro" name="txtParametro" 
											
												value="<c:out value='${mapCatalogo.parm}'></c:out>"
												style="width: 200px;"
												dojoType="dijit.form.TextBox" trim="true" 
												invalidMessage="Por favor ingrese un parametro"													
												maxlength="150"
												uppercase = "true" 
													
												/>
												&nbsp;&nbsp;&nbsp;
												<button id="btnBuscarCatalogo" name="btnBuscarCatalogo" type="submit" dojoType="dijit.form.Button" onclick="btnBuscarCatalogo_click();return false;">Buscar</button>
												
											</td>
											
										</tr>
										
									</table>
								</td>
								
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							
							<tr>
								<td>
									<table width="100%"  border="0">
										<tr>
											<td>
												<div id="gridConsulta"  > </div>
											</td>
										</tr>								
									</table>
								</td>
							</tr>							
						</table>
					</td>
				</tr>
			</table>
			<table width="98%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
						<table>
							<tr>
								<td>
									<button id="btnAceptar" name="btnAceptar" type="button" dojoType="dijit.form.Button" onclick="btnAceptarCatalogo_click()">Aceptar</button>
								</td>
							
								<td>
									&nbsp;&nbsp;&nbsp;
									<button id="btnSalirCatalogo" name="btnSalirCatalogo" type="button" dojoType="dijit.form.Button" onclick="btnSalirCatalogo_click()">Cancelar</button>
								</td>
							</tr>	
						</table>
					</td>
				</tr>
			</table>
			</div>
			
			
			<!-- VENTANA DE DIALOGO DE PERSONAL -->
			<div  dojoType="dijit.Dialog" style= "width: 550px; height: 400px; display:none;"  id="formDialogPersona" title="Buscar Contacto" execute="">
			<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Buscar Contacto</div>
											</td>
										</tr>
									</table>

								</td>
							</tr>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1">

										<tr>											
											<td class="bg" align="right">Tipo de Busqueda</td>
											<td class="bg" align="left">
												<input id="tipoPerConsulta1" name="tipoPerConsulta" 
												value="C"
													type="radio"
													dojoType="dijit.form.RadioButton" 
													onclick="tipoPerConsultaSetea(this.value);"
																									
												/>
												<label for="tipoPerConsulta1">Código</label> 
												
												<input id="tipoPerConsulta2" name="tipoPerConsulta" 
													type="radio"
													value="D"
													
													dojoType="dijit.form.RadioButton" 
												
													onclick ="tipoPerConsultaSetea(this.value);"												
												/>
												<label for="tipoConsulta2">Nombre</label> 
												&nbsp;&nbsp;&nbsp;
												<input id="txtPerParametro" name="txtPerParametro" 
											
												value="<c:out value='${mapCatalogo.parm}'></c:out>"
												style="width: 150px;"
												dojoType="dijit.form.TextBox" trim="true" 
												invalidMessage="Por favor ingrese un parametro"													
												maxlength="150"
												 uppercase = "true" 
												/>
												
												&nbsp;&nbsp;&nbsp;
												<button id="btnBuscarPersonaPopup" name="btnBuscarPersonaPopup" type="submit" dojoType="dijit.form.Button" onclick="btnBuscarPersona_click();return false;">Buscar</button>
											</td>
											
										</tr>
									</table>
								</td>
								
							</tr>
							
							<tr>
								<td>
									<table width="100%"  border="0">
										<tr>
											<td>
												<div id="gridConsultaPersona"  > </div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table width="98%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
						<table>
							<tr>
								<td>
									<button id="btnAceptarPersona" name="btnAceptarPersona" type="button" dojoType="dijit.form.Button" onclick="btnAceptarPersona_click()">Aceptar</button>
								</td>
							
								<td>
									&nbsp;&nbsp;&nbsp;
									<button id="btnSalirPersona" name="btnSalirPersona" type="button" dojoType="dijit.form.Button" onclick="btnSalirPersona_click()">Cancelar</button>
								</td>
							</tr>	
						</table>
					</td>
				</tr>
			</table>
			</div>
			
			
			<!-- EMERGENTE DE DATOS ENTREGA DETALLE---------------------------------------------------------------------------->
			
			<div  dojoType="dijit.Dialog" style= "width: 650px; height: 250px; display:none;"  id="formDialogRequerimientoDetalle" title="Datos de Entrega del ITEM" execute="" >
			<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Datos de Entrega del ÍTEM</div>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.cod_proveedor" /></td>
											<td class="bg" align="left">
												<input id="txtNum_ruc_prov3" name="txtNum_ruc_prov3" maxlength="11" 
												dojoType="dijit.form.TextBox"  style="width: 100px;text-align:left"
												value=""
												/>
												&nbsp;
												<button id="btnRecuperarContratista3" name="btnRecuperarContratista3" type="button" dojoType="dijit.form.Button"  onclick="btnRecuperarContratistaDetalle()">Recuperar</button>
											</td>
											
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.nom_proveedor" /></td>
											<td class="bg" align="left">
												<input id="txtNom_proveedor3" name="txtNom_proveedor3"   
													value=""
													style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" trim="true" 
													maxlength="100"
													readonly="readonly"
												/>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_justificacion" /></td>
											<td class="bg" align="left">
												<input id="txtObs_justificacion3" name="txtObs_justificacion3"   
													value=""
													style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" trim="true" 
													maxlength="100"													
												/> (*)
											</td>
											
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_plazo_entrega" /></td>
											<td class="bg" align="left">
												<input id="txtObs_plazo_entrega3" name="txtObs_plazo_entrega3"   
													value=""
													style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" trim="true" 
													maxlength="100"
													
												/>
											</td>
											
										</tr>
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_lugar_entrega" /></td>
											<td class="bg" align="left">
												<input id="txtObs_lugar_entrega3" name="txtObs_lugar_entrega3"   
													value=""
													style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" trim="true" 
													maxlength="100"
												/>
											</td>
											
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_dir_entrega" /></td>
											<td class="bg" align="left">
												<input id="txtObs_dir_entrega3" name="txtObs_dir_entrega3"   
													value=""
													 style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" trim="true" 
														maxlength="100"
												/>
											</td>
											
										</tr>
										
										<tr>
									</table>
								</td>
							</tr>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1">
										
									</table>
								</td>
								
							</tr>

							<tr>
								<td align="center">(*)Datos Obligatorios</td>
							</tr>
							
													
						</table>
					</td>
				</tr>
			</table>
			
			<table width="98%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
						<table>
							<tr>
								
							
								<td>
									&nbsp;&nbsp;&nbsp;
									<button id="btnGuardarDetalle" name="btnGuardarDetalle" type="button" dojoType="dijit.form.Button" onclick="btnGuardarEntregaDetalle_click()">Guardar</button>
									
									&nbsp;&nbsp;&nbsp;
									<button id="btnCancelarDetalle" name="btnCancelarDetalle" type="button" dojoType="dijit.form.Button" onclick="btnSalirEntregaDetalle_click()">Cancelar</button>
									
								</td>
							</tr>	
						</table>
					</td>
				</tr>
			</table>
			
			</div>
			
			<!-- EMERGENTE DE DATOS Envio -->
			
			<div  dojoType="dijit.Dialog" style= "width: 350px; height: 150px; display:none;"  id="formDialogEnvio" title="Procesando" execute="" >
			<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>											
											<td class="bg" align="center"  colspan="2" > <h5>Se estan Guardando los datos</h5></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>		
				</tr>
			</table>
			</div>
		</form>
		
	</center>
</body>

</html>


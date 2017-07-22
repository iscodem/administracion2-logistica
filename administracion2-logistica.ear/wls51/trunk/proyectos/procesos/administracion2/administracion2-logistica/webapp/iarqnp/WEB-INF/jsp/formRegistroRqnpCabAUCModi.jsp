<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title><fmt:message key="formRegistro.titulo" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" LANGUAGE="JavaScript" SRC="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
<script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/iarqnp.js"> </script>
<script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/formRegistroRqnpCabAUCModi.js"> </script>


<script type="text/javascript">

var CONTEXTO_APP = "${pageContext.request.contextPath}";  
var  vlsFinalidad= [<c:forEach items="${lsFinalidad}"  var="finalidad" varStatus="status">  {	id:"${finalidad.cod}",	name:"${finalidad.name}"  } ${not status.last ? ',' : ''}	</c:forEach>];
var  vlsNecesidad=[<c:forEach items="${lsNecesidad}"  var="necesidad" varStatus="status">  {	id:"${necesidad.cod}", 	name:"${necesidad.name}" } ${not status.last ? ',' : ''}</c:forEach>	];
var  vlsTipoNecesidad= [<c:forEach items="${lsTipoNecesidad}"  var="tipo" varStatus="status">  {	id:"${tipo.cod}",  	name:"${tipo.name}"   }   ${not status.last ? ',' : ''}	</c:forEach>	];
var  vlsPersonal= [<c:forEach items="${lsPersonal}"  var="personaBean" varStatus="status">     	{
    		vcodigoEmpleado:"${personaBean.codigoEmpleado}", 
    		vnombre_completo: "${personaBean.nombre_completo}",
    		vnumero_registro: "${personaBean.numero_registro}",
    		vdependencia: "${personaBean.dependencia}"	}${not status.last ? ',' : ''}</c:forEach>	];

var msg_contacto="<fmt:message key='error.registrarRqnp.contacto'/>" ;

var val_finalidad="${mapRqnp.cod_finalidad}";
var val_necesidad="${mapRqnp.cod_necesidad}";

var val_cind_tec_tit="${mapRqnp.ind_tec_tit}";
var val_cind_tec_sup="${mapRqnp.ind_tec_sup}";

var registro_vinculo="${mapRqnp.ind_vinculo}";
var registro_prestamo="${mapRqnp.ind_prestamo}";

var btnGuardar_click = function () {
	
	
if(confirm("Confirme si desea Grabar el Requerimiento No Programado")){
	
	if(!dijit.byId('frmRegistro').validate()){
		return;
	}
	
	if(dojo.byId("txtReg_contacto").value==""  ){
		alert("<fmt:message key='formRegistro.alerta.cod_contacto'/> " );	
		return;
	}
	
	if(dojo.byId("txtAnexo_contacto").value==""  ){
		alert("<fmt:message key='formRegistro.alerta.anexo_contacto'/> " );	
		return;
	}
	
	if(dojo.byId("txtNum_ruc_prov").value!=""      ){
		//Validar la longitud del ruc
		var val_ruc =dojo.byId("txtNum_ruc_prov").value;
		if (val_ruc.length ==11 && dojo.byId("txtCod_proveedor").value!="-1"){
			if(dojo.byId("txtObs_justificacion").value==""  ){
				alert("<fmt:message key='formRegistro.alerta.obs_justificacion'/> " );	
				return;
			}
		}else{
			alert("<fmt:message key='formRegistro.alerta.cod_proveedor'/> " );	
			return;
		}
	}
	
	if(dojo.byId("txtCod_necesidad").value=="Seleccione una opción"  ){
		alert("<fmt:message key='formRegistro.alerta.cod_necesidad'/> " );	
		return;
	}
	
	////////////////////////////////////////////////////
	if(dojo.byId("jtipoRegistroVinculo").value=='S'){
		if(dijit.byId('txtTipo_vinculo').get('value')=='04' || dijit.byId('txtTipo_vinculo').get('displayedValue')=="NINGUNO" ){
			alert("Por favor seleccione un tipo de vínculo.");
			return;
		}
		
		//alert("valor del control: "+dijit.byId('txtVinculo').get('displayedValue'));
		if(dijit.byId('txtVinculo').get('displayedValue') =='NINGUNO'){
			alert("Por favor seleccione un vinculo relacionado.");
			return;
		}
	}
	///////////////////////////////////////////////////
	
	if(dojo.byId("txtMotivo").value==""  ){
		alert("<fmt:message key='formRegistro.alerta.motivo'/> " );	
		return;
	}
	
	//AGREGADO FINALMENTE AL PASE
	if(dojo.byId("txtObs_plazo_entrega").value==""  ){
			alert("<fmt:message key='formRegistro.alerta.obs_plazo_entrega'/> " );	
			return;
		}
		
	if(dojo.byId("txtObs_lugar_entrega").value==""  ){
			alert("<fmt:message key='formRegistro.alerta.obs_lugar_entrega'/> " );	
			return;
		}
		
	if(dojo.byId("txtObs_dir_entrega").value==""  ){
			alert("<fmt:message key='formRegistro.alerta.obs_dir_entrega'/> " );	
			return;
		}
	//FIN AGREGADO FINALMENTE AL PASE
	
	//VALIDAR UUOO DE CONFORMIDAD
	if(dojo.byId("txtNum_UUOO1").value==""  ){
		alert("<fmt:message key='formRegistro.alerta.cod_uuoo1'/> " );	
		return;
	} 
	
	//UNICIO Y FIN
	//validar UUOO1
		//CLICK EN UUOO SELECCIONADA
		var val_num_uuoo= dijit.byId("txtNum_UUOO1");
		dojo.connect(val_num_uuoo,'onKeyPress',
			function(evt){  
			var key = evt.which || evt.keyCode;
		    if (key == 13 ){
		    	btnRecuperarUUOO1();
		    }
		    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
		    evt.keyCode = key;
		}
		);

		dojo.connect(val_num_uuoo,'onChange',
			    function(evt){  
			btnRecuperarUUOO1();
		}
		);
		
		//validar UUOO2
		//CLICK EN UUOO SELECCIONADA
		var val_num_uuoo= dijit.byId("txtNum_UUOO2");

		dojo.connect(val_num_uuoo,'onKeyPress',
			function(evt){  
			var key = evt.which || evt.keyCode;
		    if (key == 13 ){
		    	btnRecuperarUUOO2();
		    }
		    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
		    evt.keyCode = key;
		}
		);

		dojo.connect(val_num_uuoo,'onChange',
			    function(evt){  
			btnRecuperarUUOO2();
		}
		);
		
		//validar UUOO3
		//CLICK EN UUOO SELECCIONADA
		var val_num_uuoo= dijit.byId("txtNum_UUOO3");
		dojo.connect(val_num_uuoo,'onKeyPress',
			function(evt){  
			var key = evt.which || evt.keyCode;
		    if (key == 13 ){
		    	btnRecuperarUUOO3();
		    }
		    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
		    evt.keyCode = key;
		}
		);

		dojo.connect(val_num_uuoo,'onChange',
			    function(evt){  
			btnRecuperarUUOO3();
		}
		);
	//FIN E INICIO
	
	
	//AGREGADO DPORRASC
		
	if(dojo.byId("txtCod_UUOO1").value=='-1' || dojo.byId("txtCod_UUOO2").value=='-1' || dojo.byId("txtCod_UUOO3").value=='-1'){
		alert("Ingrese sólo UUOOs válidas, \nverifique los datos ingresados...");
		return;
	}

	if((dojo.byId("txtCod_UUOO1").value==dojo.byId("txtCod_UUOO2").value ) || (dojo.byId("txtCod_UUOO1").value==dojo.byId("txtCod_UUOO3").value )){
		
		alert("Ingresó  UUOOs de conformidad repetidas, \nverifique los datos ingresados...");
		return;
	}/* else if(dojo.byId("txtCod_UUOO2").value==dojo.byId("txtCod_UUOO3").value ){
		if (dojo.byId("txtCod_UUOO2").value!="" && dojo.byId("txtCod_UUOO3").value !="" ){
			alert("Ingresó  UUOOs repetidas, \nverifique los datos ingresados...");
			return;
		}
	}else if (dojo.byId("txtCod_UUOO3").value==dojo.byId("txtCod_UUOO1").value){
		alert("Ingresó  UUOOs repetidas, \nverifique los datos ingresados...");
		return;
	} */

//VALIDAR DATOS DEL COMITE
	
	if(dojo.byId("jtipoRegistroComite").value=="S"){
		
		//AREA USUARIA - MIEMBRO TITULAR
		if(dojo.byId("txtNum_au_tit").value==""  ){
			alert("<fmt:message key='formRegistro.alerta.comite_au_titular'/> " );	
			return;
		}
		
		//AREA USUARIA - MIEMBRO SUPLENTE
		if(dojo.byId("txtNum_au_sup").value==""  ){
			alert("<fmt:message key='formRegistro.alerta.comite_au_suplente'/> " );	
			return;
		}
		
		//ESPECIALISTA TECNICO - MIEMBRO TITULAR
		if(dojo.byId("jtipoRegistroComiteTecTit").value=="N" ){
			if(dojo.byId("txtNom_tec_tit").value=="" ){
				alert("<fmt:message key='formRegistro.alerta.comite_tec_titular'/> " );	
				return;
			}
		}else if(dojo.byId("jtipoRegistroComiteTecTit").value=="S"){
			if(dojo.byId("txtNum_tec_tit").value==""  ){
				alert("<fmt:message key='formRegistro.alerta.comite_tec_titular'/> " );	
				return;
			}
		}	
		
		//ESPECIALISTA TECNICO - MIEMBRO SUPLENTE
		if(dojo.byId("jtipoRegistroComiteTecSup").value=="N"){
			if(dojo.byId("txtNom_tec_sup").value==""  ){
				alert("<fmt:message key='formRegistro.alerta.comite_tec_suplente'/> " );	
				return;
			}else if(dojo.byId("jtipoRegistroComiteTecSup").value=="S"){
				if(dojo.byId("txtNum_tec_sup").value==""  ){
					alert("<fmt:message key='formRegistro.alerta.comite_tec_suplente'/> " );	
					return;
				}
			}
		}
	}
  	btnValidarContactoModi();
}	
}
</script>

</head>

<body class="tundra">
<div dojoType="dojo.data.ItemFileReadStore" url="${pageContext.request.contextPath}/bandejaauc.htm?action=listarAnnosJson" jsId="idAnnosStore"></div>
<div dojoType="dojo.data.ItemFileReadStore" url="${pageContext.request.contextPath}/bandejaauc.htm?action=listarMesesJson" jsId="idMesesStore"></div>
<div dojoType="dojo.data.ItemFileReadStore" url="${pageContext.request.contextPath}/bandejaauc.htm?action=listarTipoViculoJson" jsId="idTipoVinculoStore"></div>
<div dojoType="dojo.data.ItemFileReadStore" url="${pageContext.request.contextPath}/bandejaauc.htm?action=listarVinculoJson&jtipo_vinculo=${mapRqnp.tipo_vinculo}" jsId="idVinculoStore"></div>


	<center>
		<form id="frmRegistro" name="frmRegistro" action="rqnpauc.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id ="action" name="action" value="registrarCabRqnpModi"/>
	
			<input type="hidden" id="txtvisor" name="txtvisor" value="0" />
			
			<input type="hidden" id="txtCod_contacto" name="txtCod_contacto" value="${mapRqnp.cod_contacto}" />
			<input type="hidden" id="jPer_tipoConsulta" name="jPer_tipoConsulta" value="" />
			<input type="hidden" id="jPer_parametro" name="jPer_parametro" value="" />
			<input type="hidden" id="txtCod_proveedor" name="txtCod_proveedor" value="${mapRqnp.cod_cont}" /> 
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="${mapRqnp.codigoRqnp}" />
		
			<!--<input type="hidden" name="ind_estado" value="0"/> ${mapRqnp.cod_uoc1}-->
			<input type="hidden" id="txtCod_UUOO1" name="txtCod_UUOO1" value="${mapRqnp.cod_uoc1}" />
			<input type="hidden" id="txtCod_UUOO2" name="txtCod_UUOO2" value="${mapRqnp.cod_uoc2}" />
			<input type="hidden" id="txtCod_UUOO3" name="txtCod_UUOO3" value="${mapRqnp.cod_uoc3}" />
			
			<!-- AGREGADO PARA EL PASE - -->
			<input type="hidden" id="jtipoRegistroComite" name="jtipoRegistroComite" value="${mapRqnp.ind_comite}" />
			<input type="hidden" id="jtipoRegistroComiteTecTit" name="jtipoRegistroComiteTecTit" value="${mapRqnp.ind_tec_tit}" />
			<input type="hidden" id="jtipoRegistroComiteTecSup" name="jtipoRegistroComiteTecSup" value="${mapRqnp.ind_tec_sup}" />
			
			<input type="hidden" id="txtNum_UUOO" name="txtNum_UUOO" value="" />
			<input type="hidden" id="txtCod_au_tit" name="txtCod_au_tit" value="${mapRqnp.cod_au_tit}" />
			<input type="hidden" id="txtCod_au_sup" name="txtCod_au_sup" value="${mapRqnp.cod_au_sup}" />
			<input type="hidden" id="txtCod_tec_tit" name="txtCod_tec_tit" value="${mapRqnp.cod_tec_tit}" />
			<input type="hidden" id="txtCod_tec_sup" name="txtCod_tec_sup" value="${mapRqnp.cod_tec_sup}" />
			<input type="hidden" id="txtNombre_tec_tit" name="txtNombre_tec_tit" value="${mapRqnp.Nombre_tec_tit}" />
			<input type="hidden" id="txtNombre_tec_sup" name="txtNombre_tec_sup" value="${mapRqnp.Nombre_tec_sup}" />
			
			<input type="hidden" id="jtipoRegistroVinculo" name="jtipoRegistroVinculo" value="${mapRqnp.ind_vinculo}" />
			<input type="hidden" id="jtipoRegistroPrestamo" name="jtipoRegistroPrestamo" value="${mapRqnp.ind_prestamo}" />
			
			<!-- NOMBRE DE MIEMBROS DEL COMITE 
			<input type="hidden" id="txtNom_tec_tit" name="txtNom_tec_tit" value="" />
			<input type="hidden" id="txtNom_tec_sup" name="txtNom_tec_sup" value="" />
			-->
			<input type="hidden" id="txtNum_user_comite" name="txtNum_user_comite" value="" />
			
			<table width="95%" cellpadding="2" cellspacing="2" class="form-table">
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
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.fechaRqnp" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.fechaRqnp}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.cod_contacto" /></td>
											<td class="bg" align="left">
												<input id="txtReg_contacto" name="txtReg_contacto"  value="${mapRqnp.reg_contacto}" maxlength="4" 
												dojoType="dijit.form.ValidationTextBox"  style="width: 100px;text-align:left"
												/>(*)
												&nbsp;
												<button id="btnRecuperarPersona" name="btnRecuperarPersona" type="button" dojoType="dijit.form.Button"  onclick="btnRecuperarContacto()">Recuperar</button>
											
												&nbsp;&nbsp;
												<button id="btnBuscarPersona" name="btnBuscarPersona" type="button" dojoType="dijit.form.Button"  onclick="btnBuscarPersona_click_popup()"> Buscar </button>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.nom_contacto" /></td>
											<td class="bg" align="left">
												<input id="txtNom_contacto" name="txtNom_contacto"   
													value="${mapRqnp.nom_contacto}"
													 style="width: 450px;text-align:left"
													dojoType="dijit.form.ValidationTextBox"  
													maxlength="50"
													readonly="readonly"
												/>
											</td>
										</tr>
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.anexo_contacto" /></td>
											<td class="bg" align="left">
												<input id="txtAnexo_contacto" name="txtAnexo_contacto"   
													value="${mapRqnp.anexo_contacto}"
													style="width: 100px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="5"
												/>(*)
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.cod_proveedor" /></td>
											<td class="bg" align="left">
												<input id="txtNum_ruc_prov" name="txtNum_ruc_prov" maxlength="11" 
												value="${mapRqnp.num_ruc}"
												dojoType="dijit.form.ValidationTextBox"  style="width: 100px;text-align:left"
												/>
												&nbsp;
												<button id="btnRecuperarContratista" name="btnRecuperarContratista" type="button" dojoType="dijit.form.Button"  onclick="btnRecuperarContratista()">Recuperar</button>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.nom_proveedor" /></td>
											<td class="bg" align="left">
												<input id="txtNom_proveedor" name="txtNom_proveedor"   
													value="${mapRqnp.raz_social}"
													 style="width: 450px;text-align:left"
													dojoType="dijit.form.ValidationTextBox"  
														maxlength="70"
													readonly="readonly"
												/>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right">
											<div id="divObs_justificacion_label" style="display: none;" >
												<fmt:message key="formRegistro.obs_justificacion" />
												</div>
											</td>
											<td class="bg" align="left">
											<div id="divObs_justificacion" style="display: none;" >
												<input id="txtObs_justificacion" name="txtObs_justificacion"   
													value="${mapRqnp.obs_justificacion}"
													 style="width: 450px;text-align:left"
													dojoType="dijit.form.ValidationTextBox" 
														maxlength="50"
													
												/>(*)
												</div>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_plazo_entrega" /></td>
											<td class="bg" align="left">
											
												<input id="txtObs_plazo_entrega" name="txtObs_plazo_entrega"   
												
													 style="width: 450px;text-align:left"
													dojoType="dijit.form.ValidationTextBox" 
														maxlength="50"
													value="${mapRqnp.obs_plazo_entrega}"
												/>(*)
											</td>
										</tr>
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_lugar_entrega" /></td>
											<td class="bg" align="left">
												<input id="txtObs_lugar_entrega" name="txtObs_lugar_entrega"   
														value="${mapRqnp.obs_lugar_entrega}"
													 style="width: 450px;text-align:left"
													dojoType="dijit.form.ValidationTextBox"  
														maxlength="50"
													
												/>(*)
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_dir_entrega" /></td>
											<td class="bg" align="left">
												<input id="txtObs_dir_entrega" name="txtObs_dir_entrega"   
													value="${mapRqnp.obs_dir_entrega}"
													 style="width: 450px;text-align:left"
													dojoType="dijit.form.ValidationTextBox" 
														maxlength="50"
												
												/>(*)
											</td>
										</tr>
										
										<tr>											
										<td class="bg" align="right"><fmt:message key="formRegistro.cod_finalidad" /></td>
											<td class="bg" align="left">
												<input id="txtCod_finalidad"   		/>(*)		
											</td>
										</tr>
										
										<tr>											
										<td class="bg" align="right"><fmt:message key="formRegistro.cod_necesidad" /></td>
											<td class="bg" align="left">
												<input id="txtCod_necesidad"   		/>(*)		
											</td>
										</tr>
										<tr>											
										<td class="bg" align="right"><fmt:message key="formRegistro.anio_atencion" /></td>
											<td class="bg" align="left">
												<input id="txtAnio_atencion"   		 name="txtAnio_atencion"   dojoType="dijit.form.FilteringSelect"  autoComplete="true" 
											 		searchAttr="name" required="true" forceValidOption="false"
													promptMessage="Seleccione un Año"
													placeHolder="Seleccione un Año"
											  		store="idAnnosStore"
													style="width:200px;"
													value ="<c:out value='${mapRqnp.anio_atencion}'/>" />(*)		
											</td>
										</tr>
										
										<tr>											
										<td class="bg" align="right"><fmt:message key="formRegistro.mes_atencion" /></td>
											<td class="bg" align="left">
												<input id="txtMes_atencion"   		 name="txtMes_atencion"   dojoType="dijit.form.FilteringSelect"  autoComplete="true" 
											 		searchAttr="name" required="true" forceValidOption="false"
													promptMessage="Seleccione un Mes"
														placeHolder="Seleccione un Mes"
											  		store="idMesesStore"
													style="width:200px;"
													value ="<c:out value='${mapRqnp.mes_atencion}'/>" />(*)		
											</td>
										</tr>
										
										<!-- INICIO IND_VINCULO Y IND_PRESTAMO -->
										<tr>
											<td class="bg" align="right">Ingresará vinculó?</td>
											<td class="bg" align="left">
												<input type="radio" name="optVinculo" id="optVinculoSi" 
													value="S" dojoType="dijit.form.RadioButton"
													onclick="tipoRegistroSeteaVinculo(this.value);" /> 
												<label for="optVinculoSi">SI</label> 
													
												<input type="radio" name="optVinculo" id="optVinculoNo"
													value="N" dojoType="dijit.form.RadioButton" 
													checked="checked"
													onclick="tipoRegistroSeteaVinculo(this.value);" /> 
												<label for="optVinculoNo">NO</label> <br />
											</td>
										</tr>
										<!-- FIN IND_VINCULO Y IND_PRESTAMO -->
										
										<tr>
										<td class="bg" align="right">
											<div id="divTipoVinculoLabel" style='display: none;' ><fmt:message key="formRegistro.tipo_vinculo" /></div>
										</td>
											<td class="bg" align="left">
												<div id="divTipoVinculo" style='display: none;' >
													<input id="txtTipo_vinculo"  required="true" forceValidOption="false" name="txtTipo_vinculo"   dojoType="dijit.form.FilteringSelect"  autoComplete="true" 
												 		searchAttr="name"  
															placeHolder="Seleccione un Tipo de Vínculo"
												  		store="idTipoVinculoStore"
														style="width:450px;"
														value ="<c:out value='${mapRqnp.tipo_vinculo}'/>" />(*)		
												</div>		
											</td>
										</tr>
										<tr>
										<td class="bg" align="right">
											<div id="divVinculoLabel" style='display: none;' ><fmt:message key="formRegistro.vinculo" /></div>
										</td>
											<td class="bg" align="left">
												<div id="divVinculo" style='display: none;' >
												<input id="txtVinculo"  name="txtVinculo"   dojoType="dijit.form.FilteringSelect"  autoComplete="true" 
											 		searchAttr="name"  required="true" forceValidOption="false" 
													promptMessage="Seleccione un  Vínculo"
														placeHolder="Seleccione un Vínculo"	
													store="idVinculoStore"										  		
													style="width:450px;"
														value ="<c:out value='${mapRqnp.vinculo}'/>" />	(*)	
												</div>
											</td>
										</tr>
										
										<tr>
											<!-- INICIO PRESTAMO -->
											<td class="bg" align="right">Sujeto a prestamó?</td>
											<td class="bg" align="left">
												<input type="radio" name="optPrestamo" id="optPrestamoSi" 
													value="S" dojoType="dijit.form.RadioButton"
													onclick="tipoRegistroSeteaPrestamo(this.value);" /> 
												<label for="optPrestamoSi">SI</label> 
													
												<input type="radio" name="optPrestamo" id="optPrestamoNo"
													value="N" dojoType="dijit.form.RadioButton" 
													checked="checked"
													onclick="tipoRegistroSeteaPrestamo(this.value);" /> 
												<label for="optPrestamoNo">NO</label> <br />
											</td>
											<!-- FIN PRESTAMO -->
										</tr>
										<tr >											
											<td class="bg" align="right"><fmt:message key="formRegistro.motivo" /></td>
											<td class="bg" align="left">
												
												<input id="txtMotivo" name="txtMotivo" 
													 style="width: 450px;text-transform:uppercase;height:50px;min-height:1.5em;max-height:10em;overflow-y: auto;"
													dojoType="dijit.form.SimpleTextarea" 
													 uppercase = "true" 
													 value="${mapRqnp.motivoRqnp}"
													invalidMessage="Por favor ingrese el motivo"													
													maxlength="240"
												/>(*)
											</td>
										</tr>
										
										<!-- INICIO:DPORRASC -->
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.cod_uuoo1" /></td>
											<td class="bg" align="left">
												<input id="txtNum_UUOO1" name="txtNum_UUOO1"   
													value="${mapRqnp.codigoUuoo}"
													style="width: 100px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="6"
												/>
												<input id="txtNom_UUOO1" name="txtNom_UUOO1"   
													value="${mapRqnp.dependencia}"
													style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="150"
													readonly="readonly"
													onkeydown="preventBackspace();"
												/>(*)
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.cod_uuoo2" /></td>
											<td class="bg" align="left">
												<input id="txtNum_UUOO2" name="txtNum_UUOO2"   
													value="${mapRqnp.num_uuoo2}"
													style="width: 100px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="6"
												/>
												<input id="txtNom_UUOO2" name="txtNom_UUOO2"   
													value="${mapRqnp.nom_uuoo2}"
													style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="150"
													readonly="readonly"
													onkeydown="preventBackspace();"
												/>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.cod_uuoo3" /></td>
											<td class="bg" align="left">
												<input id="txtNum_UUOO3" name="txtNum_UUOO3"   
													value="${mapRqnp.num_uuoo3}"
													style="width: 100px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="6"
												/>
												<input id="txtNom_UUOO3" name="txtNom_UUOO3"   
													value="${mapRqnp.nom_uuoo3}"
													style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="150"
													readonly="readonly"
													onkeydown="preventBackspace();"
												/>
											</td>
										</tr>
							<!-- FIN:DPORRASC -->
									</table>
								</td>
								
							</tr>
							
						</table>
						
						<table>
							<!-- (INICIO) COPIADO COMITE -->
							<tr><td colspan=2><hr></td></tr>
							
							<!-- (INICIO)AGREGADO PARA EL PASE -->
							<tr><td  class="bg" align="right">MIEMBROS DEL COMITE</td>
							<td class="bg" align="right"></td></tr>
							</tr>
							<tr>
							<td class="bg" align="left">Ingresara miembros del comite?</td>
							<td class="bg" align="left">
								<input type="radio" name="optComite" id="optComiteSi" 
									value="S" dojoType="dijit.form.RadioButton"
									onclick="tipoRegistroSeteaComite(this.value);" /> 
								<label for="optComiteSi">SI</label> <br />
								
								<input type="radio" name="optComite" id="optComiteNo"
									value="N" dojoType="dijit.form.RadioButton" 
									checked="checked"
									onclick="tipoRegistroSeteaComite(this.value);" /> 
								<label for="optComiteNo">NO</label> <br />
							</td>
							</tr>
							
						</table>
							
							<div id="divComite" style='display: none;' >
							<table>
							
							<tr><td  class="bg" align="right">AREA USUARIA</td></tr>
							<tr>											
								<td class="bg" align="right">Titular - </td>
								<td class="bg" align="left">SUNAT</td>
								<td class="bg" align="left">
												<input id="txtNum_au_tit" name="txtNum_au_tit"   
													style="width: 100px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="4"
													value="${mapRqnp.reg_au_tit}"
												/>
												<input id="txtNom_au_tit" name="txtNom_au_tit"   
													value="${mapRqnp.nombre_au_tit}"
													style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="150"
													readonly="readonly"
												/>(*)
								</td>
											
							</tr>
							
							<tr>											
								<td class="bg" align="right">Suplente - </td>
								<td class="bg" align="left">SUNAT </td>	
								<td class="bg" align="left">
												<input id="txtNum_au_sup" name="txtNum_au_sup"   
													style="width: 100px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="4"
													value="${mapRqnp.reg_au_sup}"
												/>
												<input id="txtNom_au_sup" name="txtNom_au_sup"   
													value="${mapRqnp.nombre_au_sup}"
													style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="150"
													readonly="readonly"
												/>(*)
								</td>
											
							</tr>
								
								
								<tr><td  class="bg" align="right">CON CONOCIMIENTO TÉCNICO</td></tr>
							
							<tr>											
								<td class="bg" align="right">Titular</td>
								<td class="bg" align="left">
										<input type="radio" name="optIndTec" id="optIndTecTitSi" 
											value="S" dojoType="dijit.form.RadioButton"

											onclick="tipoRegistroSeteaTecTit(this.value);" /> 
										<label for="optIndTecTit">SUNAT</label> <br />
										
										<input type="radio" name="optIndTec" id="optIndTecTitNo" 
											value="N" dojoType="dijit.form.RadioButton" 
											
											onclick="tipoRegistroSeteaTecTit(this.value);" /> 
										<label for="optIndTecSup">EXTERNO</label> <br />
								</td>
								<td class="bg" align="left">
												<input id="txtNum_tec_tit" name="txtNum_tec_tit"   
													style="width: 100px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="4"
													disabled="disabled"
													value="${mapRqnp.reg_tec_tit}"
												/>
												<input id="txtNom_tec_tit" name="txtNom_tec_tit"   
													value="${mapRqnp.nombre_tec_tit}"
													style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="150"
													disabled="disabled"
												/>(*)
								</td>
											
							</tr>
							
							<tr>											
								<td class="bg" align="right">Suplente</td>
								<td class="bg" align="left">
										<input type="radio" name="optIndSup" id="optIndTecSupSi" 
											value="S" dojoType="dijit.form.RadioButton"
											onclick="tipoRegistroSeteaTecSup(this.value);" /> 
										<label for="optIndTecSupSi">SUNAT</label> <br />
										
										<input type="radio" name="optIndSup" id="optIndTecSupNo" 
											value="N" dojoType="dijit.form.RadioButton" 
											onclick="tipoRegistroSeteaTecSup(this.value);" /> 
										<label for="optIndTecSupNo">EXTERNO</label> <br />
								</td>
								<td class="bg" align="left">
												<input id="txtNum_tec_sup" name="txtNum_tec_sup"   
													style="width: 100px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="4"
													disabled="disabled"
													value="${mapRqnp.reg_tec_sup}"
												/>
												<input id="txtNom_tec_sup" name="txtNom_tec_sup"   
													value="${mapRqnp.nombre_tec_sup}"
													style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" 
													maxlength="150"
													disabled="disabled"
												/>(*)
								</td>
											
							</tr>
							
							</table>
							</div>
						<!-- (FIN)AGREGADO PARA EL PASE -->	
							<!-- (FIN) COMIADO COMITE -->
							<tr>
								<td>(*)Datos Obligatorios</td>
							
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table width="95%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
						<button id="btnGuardar" name="btnGuardar" type="button" dojoType="dijit.form.Button" onclick="btnGuardar_click()"  style="width: 50px;">Grabar</button>
						&nbsp;&nbsp;&nbsp;
						<button id="btnSalir" name="btnSalir" type="button" dojoType="dijit.form.Button" onclick="btnSalir_click()" style="width: 50px;">Atrás</button>
					</td>
				</tr>
			</table>
			
			
			<div  dojoType="dijit.Dialog" style= "width: 550px; height: 350px; display:none;"  id="formDialogPersona" title="Buscar Contacto" execute="">
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
											<td class="bg" align="right">Tipo de Búsqueda</td>
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
		</form>

	</center>
</body>
</html>


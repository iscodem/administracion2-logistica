<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Consulta de Requerimiento No Programado </title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/iarqnp.js"> </script>
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" src="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>

<script type="text/javascript">
	dojo.require("dijit.form.Form");
    dojo.require("dojo.data.ItemFileReadStore");
    dojo.require("dojo.data.ItemFileWriteStore");
    dojo.require("dijit.Dialog"); 
    dojo.require("dijit.form.Button");
    dojo.require("dojox.grid.EnhancedGrid");
    dojo.require("dojox.grid.enhanced.plugins.Pagination");
    dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
    dojo.require("dojo.date.locale");
    dojo.require("dijit.form.FilteringSelect");
    dojo.require("dojo.parser");
    dojo.require("dijit.form.NumberTextBox");
    dojo.require("dijit.form.ComboBox");
    dojo.require("dijit.form.SimpleTextarea");
</script>

<script type="text/javascript">
   function stringDateFormatterForDisplay(value, rowIndex) {
	   var fecha = value.substring(0,2) + "/" + value.substring(3,5) + "/" + value.substring(6);
	   return fecha;
   }
  
  var typeMap = {  
       "StringDate": {
    	   type: String,
    	   deserialize: function(value){
    		   var fecha = value.substring(6) + value.substring(3,5) + value.substring(0,2);
    		   
    		   return fecha;
    	   }
       }
  };
</script>

<script type="text/javascript">
dojo.addOnLoad(function() {
	var store = new dojo.data.ItemFileReadStore(
		{
			typeMap: typeMap,
			data: 
			{ 
				items: [
					<c:forEach items="${lsrqnp}"  var="requerimientoNoProgramado" varStatus="status">
		            	{	vcodigoReqNoProgramado:"<a href='#' onclick='btnVerCabeceraRQNP_click(${requerimientoNoProgramado.codigoRqnp});'> ${requerimientoNoProgramado.codigoReqNoProgramado}  </a> ",
		            		vfechaRqnp: "${requerimientoNoProgramado.fechaRqnp}",
		            		vnombreSolicitante:"${requerimientoNoProgramado.nombreSolicitante}", 
		            		vmotivoSolicitud:"${requerimientoNoProgramado.motivoSolicitud}", 
		            		vnombreEstado:"${requerimientoNoProgramado.nombreEstado}",
		            		vind_solicitud:"${requerimientoNoProgramado.estadoRqnp}",
		            		vcodigoRqnp:"${requerimientoNoProgramado.codigoRqnp}",
		            		vlink:"<a href='#' onclick='btnEdit_click(${requerimientoNoProgramado.codigoRqnp});'> Presupuesto  </a> ",
		            		vaccion:"<a href='#' onclick='btnAcciones_click(${requerimientoNoProgramado.codigoRqnp});'> Seguimiento  </a> "
						}   
		            	${not status.last ? ',' : ''}
					</c:forEach>
				]
			}
		}
	);
	
	var stateStore = new dojo.data.ItemFileWriteStore({
        data: { 
        	identifier: 'id',
        	  label: 'name',

			items: [
			<c:forEach items="${lsEstados}"  var="estado" varStatus="status">
            {	id:"${estado.val_estado}",
            	name:"${estado.desc_estado}" 
            }
            ${not status.last ? ',' : ''}
			</c:forEach>
			]
		}
	}
	); 
	
	
	
	var mesStore = new dojo.data.ItemFileWriteStore({
        data: { 
        	identifier: 'id',
        	  label: 'name',

			items: [
			<c:forEach items="${lsMeses}"  var="mes" varStatus="status">
            {	id:"${mes.cod}",
            	name:"${mes.name}" 
            }
            ${not status.last ? ',' : ''}
			</c:forEach>
			]
		}
	}
	); 
	
	
	 var comboBox = 
		 new dijit.form.FilteringSelect({
         id: "txtEstado",
         name: "txtEstado",
         value: "-1",
         store: stateStore,
         placeHolder: "Seleccione un Estado",
         searchAttr: "name",
         disabled:false
     }, dojo.byId("txtEstado"));
	 
	 
	 dojo.connect(comboBox,'onChange',
			    function(evt){  
			 btnBuscarRequerimientos_click();
		}
		);
	 
	 
	 var comboBoxMes = 
		 new dijit.form.FilteringSelect({
         id: "txtMes",
         name: "txtMes",
         value:"${mess}",
         store: mesStore,
         searchAttr: "name",
         disabled:false
     }, dojo.byId("txtMes"));
	 
	 
	 dojo.connect(comboBoxMes,'onChange',
			    function(evt){  
			 btnBuscarRequerimientos_click();
		}
		);
	 
	construirGrid(store);
	
	///CONSTRUIR CABECERA EN CUADRO DE DALOGO-----------------------------------------
	var storeFinalidad = new dojo.data.ItemFileWriteStore({
        data: { 
        	identifier: 'id',
        	  label: 'name',

			items: [
			<c:forEach items="${lsFinalidad}"  var="finalidad" varStatus="status">
            {	id:"${finalidad.cod}",
            	name:"${finalidad.name}" 
            }
            ${not status.last ? ',' : ''}
			</c:forEach>
			]
		}
	}
	); 
	var comboBoxFinalidad = 
		 new dijit.form.FilteringSelect({
       id: "txtCod_finalidad2",
       name: "txtCod_finalidad2",
       value: "${mapRqnp.cod_finalidad}",
       placeHolder: 'Seleccione una Finalidad',
       style: "width: 250px;",
       store: storeFinalidad,
       disabled:true,
       searchAttr: "name"
   }, dojo.byId("txtCod_finalidad2"));
	
	var storeNecesidad = new dojo.data.ItemFileWriteStore({
        data: { 
        	identifier: 'id',
        	  label: 'name',

			items: [
			<c:forEach items="${lsNecesidad}"  var="necesidad" varStatus="status">
            {	id:"${necesidad.cod}",
            	name:"${necesidad.name}" 
            }
            ${not status.last ? ',' : ''}
			</c:forEach>
			]
		}
	}
	); 
	
	var comboBoxNecesidad = 
		 new dijit.form.FilteringSelect({
        id: "txtCod_necesidad2",
        name: "txtCod_necesidad2",
        value: "${mapRqnp.cod_necesidad}",
        store: storeNecesidad,
        style: "width: 250px;",
        disabled:true,
        searchAttr: "name"
    }, dojo.byId("txtCod_necesidad2"));

	
	
	var storeTipoNecesidad = new dojo.data.ItemFileWriteStore({
        data: { 
        	identifier: 'id',
        	  label: 'name',

			items: [
			<c:forEach items="${lsTipoNecesidad}"  var="tipo" varStatus="status">
            {	id:"${tipo.cod}",
            	name:"${tipo.name}" 
            }
            ${not status.last ? ',' : ''}
			</c:forEach>
			]
		}
	}
	); 
	
	
	var comboBoxTipoNecesidad = 
		 new dijit.form.FilteringSelect({
       id: "txtTipo_necesidad2",
       name: "txtTipo_necesidad2",
       style: "width: 250px;",
       value: "${mapRqnp.cod_tip_nececidad}",
       store: storeTipoNecesidad,
       disabled:true,
       searchAttr: "name"
   }, dojo.byId("txtTipo_necesidad2"));
	
	dojo.connect(comboBoxTipoNecesidad,'onChange',
		    function(evt){  
		 habilitar_Campo();
	}
	);
	
});

function construirGrid(store){

	var layout = [
         {
       	     name: 'N° Requerimiento',
       	  	 field: 'vcodigoReqNoProgramado',
       	     width: '12%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;'
       	 },{
       		 name: 'Fecha',
       	  	 field: 'vfechaRqnp',
       	  	 width: '12%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;',
       	  	formatter: stringDateFormatterForDisplay
       	 },{
       		 name: 'Motivo',
       	     field: 'vmotivoSolicitud',
       	     width: '35%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 },{
      		 name: 'Estado',
      	     field: 'vnombreEstado',
      	     width: '11%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:center;' 
      	 },{
     		 name: 'Adjuntar',
     	     field: 'vind_solicitud',
     	    formatter:getSolicitud ,
     	     width: '10%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:center;' 
     	 },{
     		 name: 'Presupuesto',
     	     field: 'vlink',
     	     width: '10%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:center;' 
     	 },{
     		 name: 'Seguimiento',
     	     field: 'vaccion',
     	     width: '10%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:center;' 
     	 }
       	
       	
   	];
	 
	var grid = new dojox.grid.EnhancedGrid({
		store: store,
		rowSelector: '20px',
		structure: layout,
		escapeHTMLInData: false,
		rowsPerPage: 20,
		rowCount: 20,
		canSort: function(col){ return true; } ,
		style: { height:"420px", width:"98%", left:"1%" } ,
		errorMessage: "Ha ocurrido un error al procesar la consulta",
		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
		noDataMessage: "No se encontraron registros",
		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
		plugins: {
			pagination: {
				pageSizes: ["25", "50", "100", "200", "All"],
				description: true,
				sizeSwitch: false,
				pageStepper: true,
				gotoButton: false,
				maxPageStep: 5,       
				position: "bottom"
			},
			nestedSorting: true
		}
	},dojo.byId("gridConsulta"));
	
	 
	grid.startup();
 
}
</script>

<script type="text/javascript">


var btnEdit_click = function(valor){
	dojo.byId("action").value = "modificarRqnpMetas";
	dojo.byId("txtCodigoRqnp").value = valor;
	dojo.byId("frmDetalleConsulta").submit();
};

var btnAcciones_click = function(valor){
	dojo.byId("action").value = "iniciaListaAcciones";
	dojo.byId("txtCodigoRqnp").value = valor;
	
	dojo.byId("frmDetalleConsulta").submit();
};

var btnArchivo_click = function(valor){
	dojo.byId("action").value = "informeRqnp";
	dojo.byId("txtCodigoRqnp").value = valor;
	
	dojo.byId("frmDetalleConsulta").submit();
};


var btnSalir_click = function(){
	if(confirm("Confirme que desea salir del Sistema.")){
		window.parent.close();
	}
};

function getSolicitud(valor, rowIndex){
	var ind_estado=" - ";
	var grid = dijit.byId('gridConsulta');
	
	var itemIndex = grid.getItem(rowIndex);
	
	var val_cod_rqnp= grid.store.getValue(itemIndex, 'vcodigoRqnp') ;
	
	var vlink="<a href='#' onclick='btnArchivo_click("+'"'+val_cod_rqnp +'"'+");'> Adjuntar Archivo  </a> "
	if (valor=="01" || valor=="03" ){
		return vlink;
	}else{
		return ind_estado;
	}
	
}


function btnBuscarRequerimientos_click(){
	dojo.byId("action").value = "iniciarConsultaBusqueda";
	
	if(!dijit.byId('frmDetalleConsulta').validate()){
		return;
	}
	
	if(dojo.byId("txtAnio").value==""  ){
		alert("Por favor ingrese el Año del requerimiento");
		return;
	}
	
	var formDlg = dijit.byId("formDialogBuscar");
		formDlg.show();
		
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						var data = eval("(" + unescape(response.data) + ")");
						formDlg.hide();
						actualizar(data.lsRqnp);
					}
				}
				else{
					alert(response.data.mensaje);
				}
			},
			timeout : 50000,
			error : function(error, ioArgs) {
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
			},
			form: dojo.byId("frmDetalleConsulta")
	};	
	dojo.xhrPost(enviar);
}

function actualizar(lista){	
	
	var lsCatalogo =lsItems(lista);
	
	var store = new dojo.data.ItemFileWriteStore({
		data: {
			identifier: 'vcodigoReqNoProgramado',
			items: lsCatalogo
		}});
	
	var _grdLstPagAnt= dijit.byId("gridConsulta");
	if (_grdLstPagAnt==null){
		construirGrid(store);
	}else{
		
		//_grdLstPagAnt.destroyRecursive();
		
		_grdLstPagAnt.setStore(store);
	}
}

function lsItems(items){
	try {
		if(items == null) {
			items = "[]";
		}
		var items2 = eval("(" + items + ")");
		
		var cadena = "[";
		dojo.forEach(items2, function(item){
			if (cadena != "[") {
				cadena += ",";
			}
			
			cadena += '{vcodigoReqNoProgramado:"'+"<a href='#' onclick='btnVerCabeceraRQNP_click("+ item.codigoRqnp + " );'>"+ item.codigoReqNoProgramado+  "</a>"+ '",';
			cadena += "vfechaRqnp:'" + item.fechaRqnp + "',";
			cadena += "vnombreSolicitante:'" + item.nombreSolicitante + "',";
			cadena += "vmotivoSolicitud:'" + item.motivoSolicitud + "',";
			cadena += "vnombreEstado:'" + item.nombreEstado + "',";
			cadena += "vind_solicitud:'" + item.estadoRqnp+ "',";
			cadena += "vcodigoRqnp:'" + item.codigoRqnp+ "',";
			
			cadena += 'vlink:"'+"<a href='#' onclick='btnEdit_click("+ item.codigoRqnp + " );'> Presupuesto  </a>"+ '",';
			cadena += 'vaccion:"'+"<a href='#' onclick='btnAcciones_click("+ item.codigoRqnp + " );'> Seguimiento  </a>"+ '"}';
			
		});
		cadena += "]";
			
		var lst_pag_ant = eval("(" + cadena + ")");
		return lst_pag_ant;
	} catch(e) {
		return [];
	}
}
var btnVerCabeceraRQNP_click = function( cod_rqnp){
	
	var formDlg = dijit.byId("formDialogRequerimiento");
	dojo.byId("action").value = "recuperarDatosCabeceraRQNPJson";
	
	dojo.byId("txtCodigoRqnp").value = cod_rqnp; 
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]" && response.data != "{}") {
						
						var data = eval("(" + unescape(response.data) + ")");
					//	if (response.data.error !="0"){
							var val_nombre = data.raz_social;
							var val_cod_cont=data.cod_cont
							var val_num_ruc=data.num_ruc
							 var  strObject = new  String(val_nombre); 
						
							if (trim(strObject)=="undefined"){
								val_nombre="";
								val_num_ruc="";
							}
							
							dojo.byId("txtReg_contacto2").value 		= data.reg_contacto; 
							dojo.byId("txtNom_contacto2").value 		= data.nom_contacto; 
							dojo.byId("txtAnexo_contacto2").value 	= data.anexo_contacto;
							dojo.byId("txtNom_proveedor2").value 		= val_nombre; 
							dojo.byId("txtNum_ruc_prov2").value 		= val_num_ruc;
							dijit.byId("txtCod_finalidad2").setValue(data.cod_finalidad);
							dijit.byId("txtCod_necesidad2").setValue(data.cod_necesidad);
							dijit.byId("txtTipo_necesidad2").setValue(data.cod_tip_nececidad);
							dojo.byId("txtNom_tip_necesidad2").value 	= data.nom_tip_necesidad;
							dojo.byId("txtObs_justificacion2").value 	= data.obs_justificacion;
							dojo.byId("txtObs_plazo_entrega2").value 	= data.obs_plazo_entrega;
							dojo.byId("txtObs_lugar_entrega2").value 	= data.obs_lugar_entrega;
							dojo.byId("txtObs_dir_entrega2").value 		= data.obs_dir_entrega;	
							dojo.byId("txtMotivo2").value 				= data.motivoSolicitud;	
							
					}else{
						
						dojo.byId("txtNom_proveedor2").value 		= ""; 
						dojo.byId("txtNum_ruc_prov2").value 		= "";
						dojo.byId("txtObs_justificacion2").value 	= "";
						dojo.byId("txtObs_plazo_entrega2").value 	= "";
						dojo.byId("txtObs_lugar_entrega2").value 	= "";
						dojo.byId("txtObs_dir_entrega2").value 		= "";	
						
					}
					formDlg.show();
					formDlg.onFocus();
				}else{
					var data = eval("(" +unescape( response.data) + ")");
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				//alert("<fmt:message key='error.ajax'/>");
				
			},
			form: dojo.byId("frmDetalleConsulta")
	};		
	dojo.xhrPost(enviar);
	
	
	
}

	
var btnCancelarDatosEntergaCabecera_click = function( ){
	
	var formDlg = dijit.byId("formDialogRequerimiento");
	formDlg.hide();
	
}

dojo.addOnLoad(function() {	

	var  val_anio= dijit.byId("txtAnio");
	dojo.connect(val_anio,'onKeyPress',
		    function(evt){  
		var key = evt.which || evt.keyCode;
		    if (key == 13 ){
		    	btnBuscarRequerimientos_click();
		        
		    }
	}
	);
});
</script>

</head>
<body  class="tundra">

	<center>
		<form id="frmDetalleConsulta" name="frmDetalleConsulta" action="rqnpconsulta.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="action" name="action" />
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" />
			
			<input type="hidden" id="path_url" name="path_url"  value="rqnpconsulta.htm?action=iniciarconsulta"/>
			<input type="hidden" id="txtvisor" name="txtvisor" value="1" />
			<table width="95%" cellpadding="2" cellspacing="2" class="form-table">
			<tr>
					<td>
					
					</td>
			</tr>
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%" border=0>
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center"><fmt:message key="formConsultaRqnp.titulo" /> - <c:out value="${tituloVista}"></c:out> </div>
											</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td>&nbsp;</td>
							</tr>
							
							<tr>
							 
							<td align="left" >
								<table width="100%" cellpadding="8" cellspacing="2" border="0">
								<tr>
								
									<td width="10%" class="bg" align="right">
										<fmt:message key="formConsultaRqnp.anio" />
									</td>
									<td width="20%" class="bg" align="left">
										<div id="divtxtAnio"  >
											<input id="txtAnio"  name="txtAnio"   style="width: 100px;text-align:left"
											dojoType="dijit.form.NumberTextBox" 
											required="true"
											
											constraints="{min:2000,pattern: '####'}"
   											 invalidMessage="Año Incorrecto"
											value ="<c:out value='${anio}'/>"
											 />	
										</div>
									</td>
									
									<td width="10%" class="bg" align="right">
										<fmt:message key="formConsultaRqnp.mes" />
									</td>
									<td width="30%" class="bg" align="left">
										<div id="divtxtMes"  >
											<input id="txtMes"   value="<c:out value='${mess}'/>"	/>	
										</div>
									</td>
									
									<td width="10%" class="bg" align="right">
										<fmt:message key="formConsultaRqnp.estado" />
									</td>
									<td width="20%" class="bg" align="left">
										<div id="divtxtEstado"  >
										<input id="txtEstado"   	/>	
										</div>
									</td>
									
								</tr>
								</table>
							</td>
							</tr>
							
							<tr>
								<td>
									<table width="100%" cellpadding="8" cellspacing="2" border="0">
										<tr>
											<td>
												<div id="gridConsulta"></div>
											</td>
										</tr>								
									</table>
								</td>
							</tr>							
						</table>
					</td>
				</tr>
			</table>
			
			
			
			
					<div  dojoType="dijit.Dialog" style= "width: 650px; height: 450px; display:none;"  id="formDialogRequerimiento" title="Datos de Detalle del Requerimiento" execute="" >
			<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Datos de Detalle del Requerimiento</div>
											</td>
										</tr>
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.cod_contacto" /></td>
											<td class="bg" align="left">
												<input id="txtReg_contacto2" name="txtReg_contacto2" maxlength="4" 
												dojoType="dijit.form.TextBox"  style="width: 100px;text-align:left"
												value="<c:out value='${mapRqnp.reg_contacto}'></c:out>"
												readonly="readonly"
												/>(*)
												
											</td>
											
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.nom_contacto" /></td>
											<td class="bg" align="left">
												<input id="txtNom_contacto2" name="txtNom_contacto2"   
													value="<c:out value='${mapRqnp.nom_contacto}'></c:out>"
													 style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox"  
														maxlength="20"
													readonly="readonly"
												/>
											</td>
											
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.anexo_contacto" /></td>
											<td class="bg" align="left">
												<input id="txtAnexo_contacto2" name="txtAnexo_contacto2"   
														value="<c:out value='${mapRqnp.anexo_contacto}'></c:out>"
													 style="width: 100px;text-align:left"
													dojoType="dijit.form.TextBox" 
														maxlength="5"
														readonly="readonly"
												/>(*)
											</td>											
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.cod_proveedor" /></td>
											<td class="bg" align="left">
												<input id="txtNum_ruc_prov2" name="txtNum_ruc_prov2" maxlength="11" 
												dojoType="dijit.form.TextBox"  style="width: 100px;text-align:left"
												value="<c:out value='${mapRqnp.num_ruc}'></c:out>"
												readonly="readonly"
												/>
												
											</td>											
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.nom_proveedor" /></td>
											<td class="bg" align="left">
												<input id="txtNom_proveedor2" name="txtNom_proveedor2"   
													value="<c:out value='${mapRqnp.raz_social}'></c:out>"
													 style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" 
														maxlength="50"
													readonly="readonly"
												/>
											</td>
											
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_justificacion" /></td>
											<td class="bg" align="left">
												<input id="txtObs_justificacion2" name="txtObs_justificacion2"   
													value="<c:out value='${mapRqnp.obs_justificacion}'></c:out>"
													 style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox"
														maxlength="50"
													readonly="readonly"
												/>
											</td>
											
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_plazo_entrega" /></td>
											<td class="bg" align="left">
												<input id="txtObs_plazo_entrega2" name="txtObs_plazo_entrega2"   
													value="<c:out value='${mapRqnp.obs_plazo_entrega}'></c:out>"
													 style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" 
														maxlength="50"
														readonly="readonly"
													
												/>
											</td>
											
										</tr>
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_lugar_entrega" /></td>
											<td class="bg" align="left">
												<input id="txtObs_lugar_entrega2" name="txtObs_lugar_entrega2"   
													value="<c:out value='${mapRqnp.obs_lugar_entrega}'></c:out>"
													 style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox"
														maxlength="50"
													readonly="readonly"
												/>
											</td>
											
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_dir_entrega" /></td>
											<td class="bg" align="left">
												<input id="txtObs_dir_entrega2" name="txtObs_dir_entrega2"   
													value="<c:out value='${mapRqnp.obs_dir_entrega}'></c:out>"
													 style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox" 
														maxlength="50"
														readonly="readonly"
												/>
											</td>
											
										</tr>
										
										<tr>											
										<td class="bg" align="right"><fmt:message key="formRegistro.cod_finalidad" /></td>
											<td class="bg" align="left">
												<input id="txtCod_finalidad2"   		/>(*)		
											</td>
										</tr>
										
										<tr>											
										<td class="bg" align="right"><fmt:message key="formRegistro.cod_necesidad" /></td>
											<td class="bg" align="left">
												<input id="txtCod_necesidad2"   />(*)		
											</td>
										</tr>
										<tr>
											<td class="bg" align="right"><fmt:message key="formRegistro.tipo_necesidad" /></td>
											<td class="bg" align="left">
												<input id="txtTipo_necesidad2"   		/>(*)		
											</td>
										</tr>
										
										
										
										<tr>											
											<td class="bg" align="right">
												<div id="divTipoLabel" style="display: block;" >
													<fmt:message key="formRegistro.nom_tip_necesidad" />													
												</div>
											</td>
											<td class="bg" align="left">
												
													<input id="txtNom_tip_necesidad2" name="txtNom_tip_necesidad2"   
													value="<c:out value='${mapRqnp.nom_tip_necesidad}'></c:out>"
													 style="width: 350px;text-align:left"
													dojoType="dijit.form.TextBox"  
												readonly="readonly"
														maxlength="200" />												
											
											</td>
											
										</tr>
									
										
										<tr >											
											<td class="bg" align="right"><fmt:message key="formRegistro.motivo" /></td>
											<td class="bg" align="left">
												
												<input id="txtMotivo2" name="txtMotivo2" 
													 style="width: 350px;text-transform:uppercase;height:50px;min-height:1.5em;max-height:10em;overflow-y: auto;"
													dojoType="dijit.form.SimpleTextarea" 
													 uppercase = "true" 
													 value="<c:out value='${mapRqnp.motivoRqnp}'></c:out>"
													invalidMessage="Por favor ingrese el motivo"													
													maxlength="240"
													disabled="disabled"
													
												/>(*)
											</td>
										</tr>
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
									<button id="btnCancelarDatosEntregaRqnp" name="btnCancelarDatosEntregaRqnp" type="button" dojoType="dijit.form.Button" onclick="btnCancelarDatosEntergaCabecera_click()">Salir</button>
									
									
								</td>
							</tr>	
							
						</table>
					</td>
				</tr>
			</table>
			</div>
		</form>
	
	</center>
	
	<!-- EMERGENTE DE BUSQUEDA DE RQNP---------------------------------------------------------------------------->
			<div  dojoType="dijit.Dialog" style= "width: 350px; height: 150px; display:none;"  id="formDialogBuscar" title="Procesando la búsqueda" execute="" >
			<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>											
											<td class="bg" align="center"  colspan="2" > <h5>Se está procesando la búsqueda...</h5></td>
										</tr>
									</table>
								</td>
							</tr>
																				
						</table>
					</td>		
				</tr>
					
			
			</table>
			</div>
</body>
</html>
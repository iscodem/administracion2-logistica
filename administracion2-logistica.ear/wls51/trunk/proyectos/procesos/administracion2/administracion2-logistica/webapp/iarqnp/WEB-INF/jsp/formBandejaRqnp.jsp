<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Consulta de Requerimiento No Programado </title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" src="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>

<script type="text/javascript">
	dojo.require("dijit.form.Form");
    dojo.require("dojo.data.ItemFileReadStore");
    dojo.require("dijit.form.Button");
    dojo.require("dojox.grid.EnhancedGrid");
    dojo.require("dojox.grid.enhanced.plugins.Pagination");
    dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
    dojo.require("dojo.date.locale");
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
		            	{	vcodigoReqNoProgramado:"${requerimientoNoProgramado.codigoReqNoProgramado}", 
		            		vfechaRqnp: "${requerimientoNoProgramado.fechaRqnp}",
		            		vnombreSolicitante:"${requerimientoNoProgramado.nombreSolicitante}", 
		            		vmotivoSolicitud:"${requerimientoNoProgramado.motivoSolicitud}", 
		            		vnombreEstado:"${requerimientoNoProgramado.nombreEstado}",
		            		
		            		vlink:"<a href='#' onclick='btnEdit_click(${requerimientoNoProgramado.codigoRqnp});'> Modificar  </a> ",
		            		venviar:"<a href='#' onclick='btnValidaEnviar_click(${requerimientoNoProgramado.codigoRqnp});'> Enviar  </a> ",
		            		vanular:"<a href='#' onclick='btnAnularShow_click(${requerimientoNoProgramado.codigoRqnp});'> Anular  </a> "
						}   
		            	${not status.last ? ',' : ''}
					</c:forEach>
				]
			}
		}
	);
	
	construirGrid(store);
});

function construirGrid(store){

	var layout = [
         {
       	     name: 'N° Requerimiento',
       	  	 field: 'vcodigoReqNoProgramado',
       	     width: '15%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;'
       	 },
       	 {
       		 name: 'Fecha',
       	  	 field: 'vfechaRqnp',
       	  	 width: '10%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;',
       	  	formatter: stringDateFormatterForDisplay
       	 },
       	 {
       		 name: 'Motivo',
       	     field: 'vmotivoSolicitud',
       	     width: '35%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 }
       	,
      	 {
      		 name: 'Estado',
      	     field: 'vnombreEstado',
      	     width: '10%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:center;' 
      	 }
       	,
       	{
     		 name: 'Modificar',
     	     field: 'vlink',
     	     width: '10%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:center;' 
     	 }
       	,
       	{
     		 name: 'Enviar',
     	     field: 'venviar',
     	     width: '10%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:center;' 
     	 },
       	{
     		 name: 'Anular',
     	     field: 'vanular',
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
	},document.createElement('div'));
	
	 dojo.byId("gridConsulta").appendChild(grid.domNode);
	grid.startup();
}
</script>

<script type="text/javascript">
var nuevoRqnp = function(){
	document.frmDetalleConsulta.action="rqnp.htm?action=nuevoRqnp";
	//dojo.byId("action").value = "nuevoRqnp";
	dojo.byId("frmDetalleConsulta").submit();
};


var btnNuevo_click = function(){
	var codDependencia=dojo.byId("txtcodDependencia").value;
	existeMetas(codDependencia);
	
};

var btnEdit_click = function(valor){
	dojo.byId("action").value = "modificarRqnp";
	dojo.byId("txtCodigoRqnp").value = valor;
	
	dojo.byId("frmDetalleConsulta").submit();
};

var btnValidaEnviar_click = function(valor){
	//dojo.byId("action").value = "validaMetasBienJson";
	dojo.byId("txtCodigoRqnp").value =valor;
	
	document.frmDetalleConsulta.action="rqnpmetas.htm?action=validaMetasBienJson";

	//document.frmRegistroMetas.submit();

	//llamada AJAX
	var enviar = {
		handleAs : "json",
		load : function(response, ioArgs) {
			if(response.data.error==null){
				
				if (response.data != "" && response.data != "[]") {
					var data = eval("(" + unescape(response.data) + ")");
					if (data.validaMeta=='0'){
						btnEnviar_click(valor);
					}else if( data.validaMeta=='1'){
						document.frmDetalleConsulta.action="rqnp.htm";
						alert("Algunos ítems NO tienen asignado Afectación Presupuestal POI");
						
					}else{
						document.frmDetalleConsulta.action="rqnp.htm";
						alert("El Requerimiento NO tiene ítems Registrados");
					}
				}
			}
			else{
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
	
};


/*
var btnEnviar_click = function(valor){
	if(confirm("Confirme si desea ENVIAR el Requerimiento No Programado")){
		//dojo.byId("action").value = "enviarRqnp";
		dojo.byId("txtCodigoRqnp").value = valor;
		
		//dojo.byId("frmDetalleConsulta").submit();
		
		document.frmDetalleConsulta.action="rqnpmetas.htm?action=enviarRqnp";
		document.frmDetalleConsulta.submit();
	}
};
*/


///////////////////////////////////////////////
var btnEnviar_click = function(valor){
	var sw='0';
	//dojo.byId("action").value = "enviarRqnp";
	document.frmDetalleConsulta.action="rqnpmetas.htm?action=enviarRqnp";
	dojo.byId("txtCodigoRqnp").value = valor;
	if(confirm("Confirme si desea ENVIAR el Requerimiento No Programado")){
		//llamada AJAX
		var formDlg = dijit.byId("formDialogEnvio");
   		formDlg.show();
   		var enviar = {
   			handleAs : "json",
   			load : function(response, ioArgs) {
   				if (response.data != "" && response.data != "[]") {
   					var flag = response.data.flagRegistroDeclaracion;
   					var mensaje = response.data.mensaje;
   					if (flag=="1"){						
   				  		alert("Mensaje: "+mensaje);
   				  		formDlg.hide();
   				  	}
   				  	
   				  	document.frmDetalleConsulta.action="rqnp.htm?action=iniciarbandeja";
					dojo.byId("frmDetalleConsulta").submit();
   				}else{
   					var data = eval("(" + response.data + ")");
   					alert(response.data.mensaje);
   				}
   			},
   			timeout : 60000,
   			error : function(error, ioArgs) {
   				alert("Error [" + error.message + "]");
   			},
   			form: dojo.byId("frmDetalleConsulta")
   		};	
   		dojo.xhrPost(enviar);
	}else{
		document.frmDetalleConsulta.action="rqnp.htm?action=iniciarbandeja";
		dojo.byId("frmDetalleConsulta").submit();
	}
   	
};
///////////////////////////////////////////////


var btnAnular_click = function(){
	if(confirm("Confirme si desea ANULAR el Requerimiento No Programado")){
		dojo.byId("action").value = "anularRqnp";
		dojo.byId("txtAnulacion").value = dojo.byId("txtAnulacionTem").value;
		
		dojo.byId("frmDetalleConsulta").submit();
	}
};

var btnSalir_click = function(){
	if(confirm("Confirme si desea salir del Sistema")){
		window.parent.close();
	}
};


var btnAnularShow_click = function(valor){
	dojo.byId("txtCodigoRqnp").value = valor;
	var formDlg = dijit.byId("formDialogRechazo");
	formDlg.show();
};


var btnSalirRechazo_click = function(rqnp,bien){
	var formDlg = dijit.byId("formDialogRechazo");
	formDlg.hide();
};


//Funciones de validacion para las metas presupuestales
var existeMetas = function(codiDependencia){
	
	dojo.byId("txtcodDependencia").value =codiDependencia;
	
	//document.frmDetalleConsulta.action="rqnpmetas.htm?action=validaMetasBienJson";
	document.frmDetalleConsulta.action="rqnpmetas.htm?action=validaExisteMetaJson";
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						
						var data = eval("(" + unescape(response.data) + ")");
						if (data.validaExisteMeta=='1'){
							nuevoRqnp();
						}else{
							document.frmDetalleConsulta.action="rqnp.htm";
							alert("Su unidad Organizacional no posee Metas asignadas para el año en curso, favor coordinar con la Unidad "+data.uuooDpg +" para proceder con el registro del requerimiento.");
						}
							
					}else{
						alert(response.data.mensaje);
					}
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
	
};
</script>

</head>
<body  class="tundra">

	<center>
		<form id="frmDetalleConsulta" name="frmDetalleConsulta" action="rqnp.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="action" name="action" />
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" />
			<input type="hidden" id="txtAnulacion" name="txtAnulacion" />
			
			<input type="hidden" id="txtcodDependencia" name="txtcodDependencia" value='${mapUsuario.codDependencia}'/>
			
			<input type="hidden" id="txtvisor" name="txtvisor" value="0" />
			
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
												<div align="center">Requerimientos No Programados</div>
											</td>
										</tr>
									</table>

								</td>
							</tr>
							
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" border="0">
										<tr>
											<td align="left">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	<button id="btnNuevoCab" name="btnNuevoCab" type="button" dojoType="dijit.form.Button" onclick="btnNuevo_click()">Nuevo</button>
													
											</td>
										</tr>
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
			
			<table width="98%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
						<button id="btnNuevo" name="btnNuevo" type="button" dojoType="dijit.form.Button" onclick="btnNuevo_click()">Nuevo</button>
						
					</td>
				</tr>
			</table>
			
			
			<div  dojoType="dijit.Dialog"  style= "width: 350px; height: 220px; display:none;" id="formDialogRechazo" title="Confirmación" execute="" >
				<table width="100%"    >
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
								<tr>
									<td>
										<table width="100%" cellpadding="2" cellspacing="1" >
											<tr>
												<td class="T1" colspan="2">
													<div align="center">Anular RQNP</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td align="center">
										<table width="98%" cellpadding="2" cellspacing="1">
											<tr>
											<td class="bg" align="left"> &nbsp;&nbsp;Motivo:</td>
											</tr>
											<tr>											
												<td class="bg" align="left">
													<input id="txtAnulacionTem" name="txtAnulacionTem" 
													value=""
													style="overflow-y: hidden; overflow-x: auto; 
													-moz-box-sizing: border-box; width:300px; height:50px; border: 5px solid gray; 
													margin: 7px; min-height: 1.5em;"
														 
													dojoType="dijit.form.SimpleTextarea" trim="true" 
													invalidMessage="Por favor ingrese un Motivo de Anulación"													
													maxlength="200"
													/>
													&nbsp;&nbsp;&nbsp;	
												</td>
											</tr>
										</table>
									</td>
									
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<table width="98%" cellpadding="2" cellspacing="2" align="right">
					<tr align="right">
						<td align="right">
							<button id="btnAceptar" name="btnAceptar" type="button" dojoType="dijit.form.Button" onclick="btnAnular_click()">Anular</button>
							&nbsp;&nbsp;&nbsp;
							<button id="btnSalirCatalogo" name="btnSalirRechazo" type="button" dojoType="dijit.form.Button" onclick="btnSalirRechazo_click()">Cancelar</button>
						</td>
					</tr>
				</table>
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
		</form>
	</center>
</body>

</html>
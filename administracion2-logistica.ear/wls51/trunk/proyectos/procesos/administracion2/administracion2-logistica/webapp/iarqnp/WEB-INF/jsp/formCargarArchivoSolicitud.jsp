<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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

<c:set var="contextPathUrl" scope="session" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="/a/js/js.js"></script>
<script src="/a/js/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPathUrl}/js/common/jquery.form.js"></script>
<script type="text/javascript" LANGUAGE="JavaScript" SRC="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>

<script type="text/javascript" LANGUAGE="JavaScript">
var contextPathUrl="";
	dojo.require("dijit.form.Form");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dojo.data.ItemFileWriteStore");
	dojo.require("dojox.grid.EnhancedGrid");
    dojo.require("dojox.grid.enhanced.plugins.Pagination");
    dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
    
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dojo.data.ItemFileReadStore");
    dojo.require("dojo.parser");
    
</script>

<script type="text/javascript">
dojo.addOnLoad(function() {
	var store = new dojo.data.ItemFileWriteStore(
		{
			data: 
			{ 
				items: [
					<c:forEach items="${listaArchivos}"  var="archivo" varStatus="status">
		            	{
		            		vnum_reg:"${archivo.num_reg}", 
		            		vsec_reg: "${archivo.sec_reg}",
		            		vfile_name: "${archivo.file_name}",
		            		vtipo_doc: "${archivo.tipo_doc}",
		            		vfec_carga: "${archivo.fec_carga}",
		            		vsize:"${archivo.size}", 
		            		vfile_ext:"${archivo.file_ext}",
		            		vflag_imagen:"${archivo.flag_imagen}",
		            		vlink:"<a href='#' onclick="+'"'+"eliminarArchivo('${archivo.num_reg}','${archivo.sec_reg}');"+'"'+"> Eliminar  </a> ",
		            		vdescargar:"<a href='#' onclick="+'"'+"descargarArchivo('${archivo.num_reg}','${archivo.sec_reg}');"+'"'+"> Descargar  </a> "
						}   
		            	${not status.last ? ',' : ''}
					</c:forEach>
				]
			}
		}
	);
	
	
	var storeTipoDocumento= new dojo.data.ItemFileWriteStore({
        data: { 
        	identifier: 'id',
        	  label: 'name',

			items: [
			<c:forEach items="${listaTiposDocumentos}"  var="tipoDocumento" varStatus="status">
            {	id:"${tipoDocumento.cod}",
            	name:"${tipoDocumento.name}" 
            }
            ${not status.last ? ',' : ''}
			</c:forEach>
			]
		}
	}
	); 
	
	var comboBoxTipoDocumento = 
		 new dijit.form.FilteringSelect({
        id: "txtTipoDocumento",
        name: "txtTipoDocumento",
        value: "00",
        style: "width: 250px;",
        placeHolder: 'Seleccione Tipo Documento',
        store: storeTipoDocumento,
        searchAttr: "name",
        required:"true"
    }, dojo.byId("txtTipoDocumento"));
	
	construirGrid(store);
	
});	



function construirGrid(store){

	var layout = [
		{
		     name: ' ',
		  	 field:'vnum_reg',
		     width: '0%',
		     headerStyles: "text-align: center",
		  	 styles:'text-align:center;'
	 	},
        {
      	     name: 'Número',
      		 field:'vsec_reg',
      	     width: '10%',
      	     headerStyles: "text-align: center",
      	  	 styles:'text-align:center;'
      	 },
         {
       	     name: 'Archivo',
       		 field:'vfile_name',
       	     width: '30%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:left;'
       	 },{
      	     name: 'Tipo Doc',
      		 field:'vtipo_doc',
      	     width: '10%',
      	     headerStyles: "text-align: center",
      	  	 styles:'text-align:center;'
      	 },
       	 {
       		 name: 'Tamaño',
       	  	 field: 'vsize',
       	  	 width: '10%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;'
       	 },{
       		 name: 'Fecha de Carga',
       	  	 field: 'vfec_carga',
       	  	 width: '10%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;'
       	 },
       	 {	 name: 'Extensión',
       	     field: 'vfile_ext',
       	     width: '10%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 }
       	,	
      	 {name: 'Eliminar',
      	     field: 'vlink',
      	     width: '10%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:center;'
      	 }
       	
       	,	
     	 {name: 'Descargar',
     	     field: 'vdescargar',
     	     width: '10%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:center;'
     	 }
   	];

	var grid = new dojox.grid.EnhancedGrid({
		//id:'gridDetalle',
		store: store,
		rowSelector: '20px',
		structure: layout,
		escapeHTMLInData: false,
		rowsPerPage: 10,
		rowCount: 10,
		//onStyleRow: myStyleRow,

		canSort: function(col){ return true; } ,
		style: { height:"170px", width:"99%" } ,
		errorMessage: "Ha ocurrido un error al procesar la consulta",
		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
		noDataMessage: "No se encontraron Registros",
		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
		plugins: {
			pagination: {
				pageSizes: ["10", "20", "30", "40", "All"],
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


var btnSalir_click = function(cod_sol){
	dojo.byId("txtCodigoRqnp").value=cod_sol;
	//document.frmCargarAtras.action="solincorbien.htm?action=iniciarSolicitud";	
	//document.frmCargarAtras.action="solincorbien.htm?action=recuperarSolicitudDelBien";
	
	var val_url =dojo.byId("path_url_back").value;
	document.frmCargarAtras.action=val_url;	
	
	document.frmCargarAtras.submit();
}


function eliminarArchivo(num_reg,sec_reg){
	dojo.byId("txtNumArchivo").value = num_reg;
	dojo.byId("txtSecArchivo").value = sec_reg;
	dojo.byId("path_url2").value = dojo.byId("path_url").value;
	
	if (confirm("Confirme si desea eliminar el archivo seleccionado")){
		document.frmCargarAtras.action="rqnparchivo.htm?action=eliminarArchivoFisicoSolicitud";		
		document.frmCargarAtras.submit();
	}else{
		return false;
	}
}


function descargarArchivo(num_reg,sec_reg){
	dojo.byId("txtNumArchivo").value = num_reg;
	dojo.byId("txtSecArchivo").value = sec_reg;
	document.frmCargarAtras.action="rqnparchivo.htm?action=descargarArchivo";
	document.frmCargarAtras.submit();
}

dojo.addOnLoad(function() {	
	contextPathUrl = "${contextPathUrl}";
});

function validateForm(){
	
	if(dojo.byId("txtTipoDocumento").value==""  ){
		alert("<fmt:message key='formRegArchivo.tipoDocumento.valida'/> " );	
		return false;
	}
	
	if(dojo.byId("txtFileDescripcion").value==""  ){
		alert("<fmt:message key='formRegArchivo.descripcion.valida'/> " );	
		return false;
	}
	
	if(dojo.byId("file").value==""  ){
		alert("<fmt:message key='formRegArchivo.file.valida'/> " );	
		return false;
	}
	
	
	
	if(dijit.byId('frmCargar').validate()){
		cargarArchivo();
        //return true;
    }else{
        alert('El formulario contiene Datos Invalidos.  Por favor Corrija');
        return false;
    }
}

function cargarArchivo(){
	var direccion = $('#file').val();
	var url = contextPathUrl+"/rqnparchivo.htm?action=validarTamanioArchivoJson";
	var options = { 
		type	: "POST",
		dataType: "JSON",
		url		: url,
		enctype	: "multipart/form-data",
		success	: function(respuesta){
					if(respuesta.data.flag == "0"){
						document.frmCargar.action="rqnparchivo.htm?action=cargarArchivoSolicitud";
						document.frmCargar.submit();		
					}else {
						alert("El archivo supera el tamaño permitido (1MB). Adjuntar un archivo de menor tamaño.");
					}
		}, 
		error	:function(){
					alert("El archivo supera el tamaño permitido (1MB). Adjuntar un archivo de menor tamaño.");
				},
		beforeSerialize: function(form, options) {
							options.data = {
								file : $("#file").val()
							};
		}
	};
	$('#frmCargar').ajaxForm(options);
	$('#frmCargar').submit();
}
</script>

</head>

<body class="tundra">
	<center>

		<form id="frmCargar" name="frmCargar" action="rqnparchivo.htm" method="POST" dojoType="dijit.form.Form" enctype="multipart/form-data" >
		<!-- onsubmit="return validateForm()" > -->
	
			<!-- <input type="hidden" id="action"  name="action" value="cargarArchivoSolicitud"/> -->
			<input type="hidden" id="nombreArchivo" name="nombreArchivo"/>
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${txtCodigoRqnp}'/>"/>
			<input type="hidden" id="txtNumArchivo2" name="txtNumArchivo2" value="<c:out value='${mapRqnp.numero_archivo}'/>" />
			<input type="hidden" id="txtvisor" name="txtvisor" value="<c:out value='${visor}'/>" />
		
			<input type="hidden" id="path_url2" name="path_url2" value="<c:out value='${path_url}'/>" />
			<input type="hidden" id="path_url_back2" name="path_url_back2" value="<c:out value='${path_url_back}'/>" />
			<input type="hidden" id="txtCodigoRqnp2" name="txtCodigoRqnp2" value="<c:out value='${txtCodigoRqnp}'/>" />
			
			<input type="hidden" id="jParametro" name="jParametro" value="formRegistroDetalle"/>
			
			<table width="95%" cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center"><fmt:message key="formRegistro.tituloAdjuntarSolicitud" /></div>
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
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.numSol" /></td>
									<td class="bg" align="left">
										<input id="txtCodSol" name="txtCodSol"   
										value="<c:out value='${mapRqnp.cod_solicitud}'></c:out>"
											 style="width: 100px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										/>
										
									</td>
								</tr>
								
								
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.estado" /></td>
									<td class="bg" align="left">
										<input id="txtNomEstado" name="txtNomEstado"   
										value="<c:out value='${mapRqnp.nom_estado}'></c:out>"
											 style="width: 100px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										
										/>
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.solicitante" /></td>
									<td class="bg" align="left">
										<input id="txtNombreSol" name="txtNombreSol"   
										value="<c:out value='${mapRqnp.nombre_sol}'></c:out>"
											 style="width: 250px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										/>
									</td>
								</tr>
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.fecha" /></td>
									<td class="bg" align="left">
										<input id="txtFechaSol" name="txtFechaSol"   
										value="<c:out value='${mapRqnp.fecha_sol}'></c:out>"
											 style="width: 250px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										/>
									</td>
								</tr>
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.uuoo" /></td>
									<td class="bg" align="left">
										<input id="txtNomUuo" name="txtNomUuo"   
										value="<c:out value='${mapRqnp.nombre_uuo}'></c:out>"
											 style="width: 450px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										/>
									</td>
								</tr>
										
									
										
										<tr >											
											<td class="bg" align="right"><fmt:message key="formRegArchivo.tipoDocumento" /></td>
											<td class="bg" align="left">
												
												<input id="txtTipoDocumento"   		/>(*)		
											
											</td>
										</tr>
										
										<tr >											
											<td class="bg" align="right"><fmt:message key="formRegArchivo.descripcion" /></td>
											<td class="bg" align="left">
											
												<input id="txtFileDescripcion" name="txtFileDescripcion"   
													value=""
													 style="width: 450px;text-align:left"
													dojoType="dijit.form.ValidationTextBox"  
														maxlength="50"
													required="true"
												/>(*)
											
											</td>
										</tr>
										
										<tr>
			  				    			<td class="bg" align="right" >  <fmt:message key="formRegistro.archivo"   />	</td>
			  				    			<td class="bg" align="left">
			  									<input id="file" name="file" type="file" class="browse" style="font-size:13px; width:450px"   />&nbsp;&nbsp;&nbsp;
			  									<!-- <button id="btnCargar" name="btnCargar" type="submit"  >Adjuntar</button><br> -->
			  									<button id="btnCargar" name="btnCargar" type="button" onclick="validateForm();">Adjuntar</button><br>
			  									<font color="#FF0000" >(Máximo permitido 1 Mb)</font>
			              					</td>
			  				 			</tr>
			  				 			
			  				 			
									</table>
								</td>
								
							</tr>
							<tr>
								<td>
								<table width="100%" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td >
												<div id="gridConsulta"  > 		</div>
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
						&nbsp;&nbsp;&nbsp;
						<button id="btnSalir" name="btnSalir" type="button" dojoType="dijit.form.Button" onclick="btnSalir_click(${mapRqnp.cod_solicitud})">Atrás</button>
					</td>
				</tr>
			</table>
		</form>
		
		<form id="frmCargarAtras" name="frmCargarAtras" action="rqnp.htm" method="POST" dojoType="dijit.form.Form"  >		
			<input type="hidden" id="nombreArchivo" name="nombreArchivo"/>
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${txtCodigoRqnp}'/>" />
			<input type="hidden" id="txtNumArchivo" name="txtNumArchivo" value="<c:out value='${mapRqnp.numeroArchivo}'/>" />
			<input type="hidden" id="txtCodSol2" name="txtCodSol2" value="<c:out value='${mapRqnp.cod_solicitud}'/>" />
		
			<input type="hidden" id="txtSecArchivo" name="txtSecArchivo" value="" />
			<input type="hidden" id="txtvisor" name="txtvisor" value="<c:out value='${visor}'/>" />
			<input type="hidden" id="path_url" name="path_url" value="<c:out value='${path_url}'/>" />
			<input type="hidden" id="path_url_back" name="path_url_back" value="<c:out value='${path_url_back}'/>" />
			
			<!-- INICIO AGREGADO -->
			<input type="hidden" id="jParametroAuc" name="jParametroAuc" value="formAucRqnpAtenderAUC"/>
			<!-- FIN DE AGREGADO -->
		</form>
		
	</center>
</body>
<script type="text/javascript">

	function addCommas(nStr)
	{
		 nStr += '';
		 nStr=removeCommas(nStr);
	    
	    x = nStr.split('.');
	    
	    x1 = x[0];
	    
	    x2 = x.length > 1 ? '.' + x[1] : '';
	    var rgx = /(\d+)(\d{3})/;
	    while (rgx.test(x1)) {
	        x1 = x1.replace(rgx, '$1' + ',' + '$2');
	    }
	    
	    return x1 + x2.substring(0,3);
	}
	
	function removeCommas(nStr){
		nStr += '';
		var i=0;
		x = nStr.split(',') ;
		j = x.length;
		nStr ='';
		
		while( i <j ){
			nStr += x[i];
			i++;
		}
		
		return nStr;
	}
</script>
</html>


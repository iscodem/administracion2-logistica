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
		            		
		            		vdescargar:"<a href='#' onclick="+'"'+"descargarArchivo('${archivo.num_reg}','${archivo.sec_reg}');"+'"'+"> Descargar  </a> "
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
       	 },
        {
      	     name: 'Tipo Doc',
      		 field:'vtipo_doc',
      	     width: '15%',
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
       	 },
     	 {name: 'Descargar',
     	     field: 'vdescargar',
     	     width: '15%',
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


var btnSalir_click = function(){
		dojo.byId("action").value = "aprueba";	
		dojo.byId("frmCargarAtras").submit();
	
}



function descargarArchivo(num_reg,sec_reg){
	dojo.byId("txtNumArchivo").value = num_reg;
	dojo.byId("txtSecArchivo").value = sec_reg;

	document.frmDescargar.action="rqnparchivo.htm?action=descargarArchivo";
		
	document.frmDescargar.submit();
}

dojo.addOnLoad(function() {	
	dojo.byId("txtMontoAcumulado").value =addCommas(dojo.byId("txtMontoAcumulado").value);	
	
});



</script>

</head>

<body class="tundra">
	<center>

		<form id="frmDescargar" name="frmDescargar" action="rqnp.htm" method="POST" dojoType="dijit.form.Form"  >
			
			<input type="hidden" id="nombreArchivo" name="nombreArchivo"/>
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
			<input type="hidden" id="txtNumArchivo" name="txtNumArchivo" value="<c:out value='${mapRqnp.numeroArchivo}'/>" />
			<input type="hidden" id="txtCodigoBien" name="txtCodigoBien" value="<c:out value='${mapRqnp.codigoBien}'/>" />
			<input type="hidden" id="txtSecArchivo" name="txtSecArchivo" value="" />
			
			<input type="hidden" id="txtvisor" name="txtvisor" value="<c:out value='${visor}'/>" />
			
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
												<div align="center"><fmt:message key="formRegistro.titulo" /></div>
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
											<td class="bg" align="right"><fmt:message key="formRegistro.fechaEnvio" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.fechaenvio}"></c:out>
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
												
											</td>
										</tr>
										
										
										<tr >											
											<td class="bg" align="right"><fmt:message key="formRegistro.motivo" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.motivoRqnp}"></c:out>
											</td>
										</tr>
										<tr >											
											<td class="bg" align="right"><fmt:message key="formRegistro.bien" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.bien}"></c:out>
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
						<button id="btnSalir" name="btnSalir" type="button" dojoType="dijit.form.Button" onclick="btnSalir_click()">Atrás</button>
					</td>
				</tr>
			</table>
		</form>
		<form id="frmCargarAtras" name="frmCargarAtras" action="rqnp.htm" method="POST" dojoType="dijit.form.Form"  >
					<input type="hidden" id="action" name="action" value="modificarRqnp"/>
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


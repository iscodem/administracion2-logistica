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
	
	dojo.require("dijit.form.TextBox");
	
	dojo.require("dijit.form.SimpleTextarea");
	dojo.require("dijit.Dialog");  
	dojo.require("dojo.date.locale");
    dojo.require("dijit.form.RadioButton");
    dojo.require("dojo.data.ItemFileReadStore");
    dojo.require("dojo.data.ItemFileWriteStore");
   
    dojo.require("dojox.grid.EnhancedGrid");
    dojo.require("dojox.grid.enhanced.plugins.Pagination");
    dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
    dojo.require("dojo._base.array");
 
    dojo.require("dojo._base.connect");
    dojo.require("dojo._base.event");
    
    dojo.require("dojo.parser");
   
	
	
</script>

<script type="text/javascript">
dojo.addOnLoad(function() {
	var store = new dojo.data.ItemFileWriteStore(
		{
			
			data: 
			{ 
				items: [
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
		            		vnom_estado: "${bienesBean.nom_estado}" ,
		            		
		            		vdesBien:"${bienesBean.desBien}",
		            		vdesUnid:"${bienesBean.desUnid}",
		            		vdesClasificador:"${bienesBean.desClasificador}",
		            		vind_estado:"${bienesBean.ind_estado}",
		            		vlink:"<a href='#' onclick="+'"'+"adjuntarArchivo('${bienesBean.codigoRqnp}','${bienesBean.codigoBien}','${bienesBean.numeroArchivo}' );"+'"'+"> Adjuntar Archivo  </a> ",
		            	
		            		vimage:" "
						}   
		            	${not status.last ? ',' : ''}
					</c:forEach>
				]
			}
		}
	);
		
	construirGridDetalle(store);
});


function construirGridDetalle(store){

	var layout = [
		{
		     name: ' ',
		  	 field:'vcodigoRqnp',
		  	 formatter: getCodigoRqnp,
		     width: '0%',
		     headerStyles: "text-align: center",
		  	 styles:'text-align:center;'
	 	},{
      	     name: 'Código',
      		 field:'vxcodigoBien',
      	     width: '9%',
      	     headerStyles: "text-align: center",
      	  	 styles:'text-align:center;'
      	 },{
       	     name: ' ',
       		 field:'vcodigoBien',
       		formatter:getCodigoBien,
       	     width: '0%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;'
       	 },{
       		 name: 'Bienes y Servicios',
       	  	 field: 'vdesBien',
       	  	 width: '25%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:left;'
       	 }, {
       		 name: ' ',
       	     field: 'vcodigoUnidad',
       	     width: '0%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;',
       	     formatter:getCodigoUnidad
       	 }, {
       		 name: 'Unid. Med.',
       	     field: 'vdesUnid',
       	     width: '6%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 }, {
      		 name: 'Precio Unit.',
      		 field: 'vxprecioUnid',
      	     formatter:getPrecioUnitariox,
      	 	 regExp:"^[0-9]{1,5}(\.[0-9]{0,2})?$"  , 
      	     width: '7%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' ,
      	     editable: true
      	 }, {
      		 name: ' ',
      		 field: 'vprecioUnid',
      	     formatter:getPrecioUnitario,
      	 	 regExp:"^[0-9]{1,5}(\.[0-9]{0,2})?$"  , 
      	     width: '0%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' 
      	    
      	 },{
      		 name: ' ',
      	     field: 'vcantBien',
      	     width: '0%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' ,
      	    formatter:getCantidad
      	  
      	 }, {
      		 name: 'Saldo',
      		 field: 'vsaldo',
      	     width: '5%',
      	     formatter:getSaldo,
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' 
      	 },{
      		 name: 'Exceso',
      		 field: 'vexceso',
      	     width: '6%',
      	     formatter:getSaldo,
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' 
      	 },{
      		 name: 'Alerta',
      		 field: 'vimage',
      	     width: '3%',
      	     formatter:getImage,
      	     headerStyles: "text-align: center",
      	     styles:'text-align:center;' 
      	 },{
     		 name: 'Estado',
     	     field: 'vnom_estado',
     	     width: '13%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:left;' 
     	     
     	 },{
     		 name: 'Adjuntar',
     		 field: 'vlink',
     	     width: '11%',
     	    formatter:getAdjuntar,
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
		rowsPerPage: 30,
		rowCount: 30,
		//onStyleRow: myStyleRow,

		canSort: function(col){ return true; } ,
		style: { height:"270px", width:"99%" } ,
		errorMessage: "Ha ocurrido un error al procesar la consulta",
		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
		noDataMessage: "No se encontraron registros",
		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
		plugins: {
			pagination: {
				pageSizes: ["30", "60", "120", "180", "All"],
				description: true,
				sizeSwitch: false,
				pageStepper: true,
				gotoButton: false,
				maxPageStep: 5,       
				position: "bottom"
			},
			nestedSorting: true
		}
	},dojo.byId("gridDetalle"));
	  //dojo.byId("gridDetalle").appendChild(grid.domNode);

	 /*  function myStyleRow(row){
			
	       var item = grid.getItem(row.index);
	       if(item){
	          var estado = store.getValue(item, "vestadoDesconcentrado", null);
	         
	          if(estado=="1"){
	              row.customStyles += "color:red;";
	          }
	       }
	       grid.focus.styleRow(row);
	       grid.edit.styleRow(row);
	    } */
	 
	grid.startup();
	   
	dojo.connect(grid,'onKeyEvent',
			    function(evt){  
		var key = evt.which || evt.keyCode;
	    // Capturar el codigo de la tecla presionada. 
	    // Si no es evt.which, usar evt.keyCode (para IE) 
	 
	    if ((key == 13 || key == 190 || key == 46 || key == 8) || (key >= 48 && key <=57)){
	  
	    
	        return true; 
	    }else{
	    	   dojo.stopEvent(evt);
	    	
	    } 
	     
	}
	);
	
	
}


function getCodigoRqnp(valor, item) {
	var vtxtRqnp = '<input id="txtRqnp" name="txtRqnp" type="hidden"  value="'+valor+'"   /> '; 
    return vtxtRqnp; 
}

function getCodigoBien(valor, item) {
	var vtxtCodigo = '<input id="txtCodigo" name="txtCodigo" type="hidden"  value="'+valor+'"   /> '; 
    return vtxtCodigo; 
}

function getCantidad(valor, item) {
	var vtxtCantidad = '<input id="txtCantidad" name="txtCantidad" type="hidden"  value="'+valor+'"   /> '; 
    return vtxtCantidad; 
}
function getCantidadX(valor) {
	
    return addCommas(valor); 
}
function getSaldo(valor) {
	
    return addCommas(valor); 
}

function getCodigoUnidad(valor, item) {
	var vtxtCantidad = '<input id="txtcodUnidad" name="txtcodUnidad" type="hidden"  value="'+valor+'"   /> '; 
    return vtxtCantidad; 
}

function getPrecioUnitario(valor, item) {
	var vtxtCantidad = '<input id="txtPrecioUnit" name="txtPrecioUnit" type="hidden"  value="'+valor+'"   /> '; 
    return vtxtCantidad; 
}
function getAdjuntar(valor , rowIndex){

	var grid = dijit.byId('gridDetalle');
	
	var itemIndex = grid.getItem(rowIndex);
	
	var val_estado= grid.store.getValue(itemIndex, 'vind_estado') ;
	
	
	if (val_estado=="01" || val_estado=="02" || val_estado=="03" ){
		return valor;
	}else{
		return "-";
	}
}

function getValorTrue(valor,rowIndex) {

	var grid = dijit.byId('gridDetalle');
	var tipo_bien="";
	var itemIndex = grid.getItem(rowIndex);

	tipo_bien =grid.store.getValue(itemIndex, 'vtipo_bien') ;
	if (tipo_bien=='B'){
		return false;
		
	}else{
		return true;
	}
	
}
function getPrecioUnitariox(valor,rowIndex) {
	//Validando la expresion Regular
	var patron=/^[0-9]{1,10}(\.[0-9]{0,2})?$/;

	var rsta = valor.match(patron);
	var grid = dijit.byId('gridDetalle');
	var items = grid.selection.getSelected();
	var row =null;
	var prec_uni;
	var prec_unix;
	var cant;
	var mont_total= parseFloat(0);
	var mont_total_txt;
	var tipo_bien="";
	var val_txtPrecioUnit;
	
	if(items.length){
		  dojo.forEach(items, function(selectedItem){
		        if(selectedItem != null){
		        	row=selectedItem;
		        	 dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
		                   if(attribute=='vcantBien') { 
		                	   cant = parseFloat( removeCommas(grid.store.getValues(selectedItem, attribute)));
		                   }
		                   
		                  
		        	 }); 
		        	
		        }
		    }); 
	}

	var itemIndex = grid.getItem(rowIndex);
	tipo_bien =grid.store.getValue(itemIndex, 'vtipo_bien') ;
	var precio= grid.store.getValue(itemIndex, 'vprecioUnid') ;
	if  (tipo_bien=='B'){
		valor =precio;
	}
	
 	if( rsta!=null ){
 		prec_uni= parseFloat(valor);
 		mont_total = cant * prec_uni;
 		mont_total_txt =addCommas(mont_total);
 	
 		 if (row!=null){
 			grid.store.setValue(row, 'vprecioTotal',mont_total_txt);
 	 		grid.store.setValue(row, 'vprecioUnid',valor);  
 		 }
 		
	}else{
		//alert('valor no valido');
	} 

	valor =  addCommas(valor);
	
	return  valor;
}


function getCodigoClasif(valor, item) {
	var vtxtCodClasi = '<input id="txtCodClasi" name="txtCodClasi" type="hidden"  value="'+valor+'"   /> '; 
    return vtxtCodClasi; 
}

function getImage(valor,rowIndex){
	var grid = dijit.byId('gridDetalle');
	var itemIndex =grid.getItem(rowIndex);
	var val_saldo
	var val_exceso
	val_saldo =parseFloat( grid.store.getValue(itemIndex, 'vsaldo') );
	val_exceso =parseFloat( grid.store.getValue(itemIndex, 'vexceso') );
	if (val_saldo ==0){
		divShow('divmsgExceso');
		divShow('divmsgImage');
		return  '<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
	}else if  ( val_exceso >0){
		divShow('divmsgExceso');
		divShow('divmsgImage');
			return  '<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
	}else{
			return  valor;
		
		
	}
}



</script>


<script type="text/javascript">

function adjuntarArchivo(cod_req,cod_bien,num_reg){
	dojo.byId("txtCodigoRqnp").value = cod_req;
	dojo.byId("txtCodigoBien").value = cod_bien;
	dojo.byId("txtNum_reg").value = num_reg;	
	document.frmRegistro.action="rqnparchivo.htm?action=iniciarCargaArchivo";
		
	document.frmRegistro.submit();
}

var btnSalir_click = function(){
	document.frmRegistro.action="rqnpconsulta.htm?action=iniciarconsulta";
		
	document.frmRegistro.submit();
}





function formatear(){
	dojo.byId("txtMontoAcumulado").value =addCommas(dojo.byId("txtMontoAcumulado").value);
	
}
dojo.addOnLoad(function() {	
	
	dijit.byId('btnSalir').setAttribute("disabled", false);
	formatear();
	
	var  val_motivo= dijit.byId("txtParametro");
	dojo.connect(val_motivo,'onKeyPress',
		    function(evt){  
	var key = evt.which || evt.keyCode;
	    if (key == 13 ){
	    	btnBuscarCatalogo_click();
	        
	    }
	}
	);

	dijit.byId("txtMotivo").focus();
	
	var visor = dojo.byId("txtvisor").value;

	divShow("divbtnSalir");

	
});
</script>

</head>

<body class="tundra">
	<center>

		<form id="frmRegistro" name="frmRegistro" action="rqnp.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="action" name="action" value="registrarRqnpDetalle"/>
			
			<input type="hidden" id="txtCodigoBien" name="txtCodigoBien"  />
			<input type="hidden" id="txtNum_reg" name="txtNum_reg"  />
			
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
			<input type="hidden" id="txtvisor" name="txtvisor" value="<c:out value='${visor}'/>" />
			<input type="hidden" id="path_url" name="path_url"  value="rqnpconsulta.htm?action=informeRqnp"/>
			
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
												<div align="center"><fmt:message key="formRegistro.titulo2" /></div>
											
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
												<input id="txtMotivo" name="txtMotivo" 
													 style="width: 450px;text-transform:uppercase;height:50px;min-height:1.5em;max-height:10em;overflow-y: auto;"
													dojoType="dijit.form.SimpleTextarea" trim="true" 
													 uppercase = "true" 
													value="<c:out value='${mapRqnp.motivoRqnp}'></c:out>"
													invalidMessage="Por favor ingrese el motivo"													
													maxlength="240"
													readonly="readonly"
												/>
												
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
										
										
										<tr>
											<td width="4%">
											<div id="divmsgImage" style="display: none;" >
												<img name="eje01" src="/a/imagenes/circulorojo.gif"/>
											</div> 
											</td>
											<td width="96%" align="left" >
											<div id="divmsgExceso" style="display: none;" >
												<P> El ítem NO cuenta con SALDO para su atención por el canal de Compras Directas. Por lo tanto será atendido por otro canal
												</P>
											</div> 
											</td>
										</tr>
										
										<tr>
											<td colspan="2" align="center">
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
			</table>
			
			
			
			
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

function isDecimalKey(evt, txt) {
	
	var key = evt.which || evt.keyCode;
    // Capturar el codigo de la tecla presionada. 
    // Si no es evt.which, usar evt.keyCode (para IE) 

    if ((key == 13 || key == 46 || key == 8) || (key >= 48 && key <=57)){
    // Si la tecla es backspace, enter, punto o digito 
        if (key == 46 && txt.indexOf('.')!=-1){
        // Si es el caracter "." comprobar que sea el unico 
            return false; 
        } 
        return true; 
    } 
    return false; 
}


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
		hza.style.display = state; 
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
		hza.style.display = state; 
	} 
} 




</script>
</html>


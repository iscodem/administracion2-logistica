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

<script type="text/javascript" src="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
<script type="text/javascript" LANGUAGE="JavaScript">
	dojo.require("dijit.form.Form");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.form.ValidationTextBox");
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
		            		vcodigoRqnp		:"${bienesBean.codigoRqnp}", 
		            		vcodigoBien		:"${bienesBean.codigoBien}",
		            		vcodigoUnidad	:"${bienesBean.codigoUnidad}", 
		            		vcantBien		:"${bienesBean.cantBien}",
		            		vprecioUnid		:"${bienesBean.precioUnid}" , 
		            		vprecioTotal	: "${bienesBean.precioTotal}" ,
		            		vsaldo			: "${bienesBean.saldo}" ,
		            		vexceso			: "${bienesBean.exceso}" ,
		            		vimage			:" ",
		            		vdesBien		:"${bienesBean.desBien}",
		            		vdesUnid		:"${bienesBean.desUnid}",
		            		vauct_name		:"${bienesBean.auct_name}"	,
		            		vnum_exp		:"${bienesBean.num_exp}"
						}   
		            	${not status.last ? ',' : ''}
					</c:forEach>
				]
			}
		}
	);
	
	var storeAcciones = new dojo.data.ItemFileWriteStore(
			{
				data: 
				{ 
					items: [
						<c:forEach items="${listaAcciones}"  var="accionBean" varStatus="status">
			            	{
			            		num_exp			:"${accionBean.num_exp}", 
			            		sec_accion		:"${accionBean.sec_accion}",
			            		cod_accion		:"${accionBean.cod_accion}", 
			            		des_accion		:"${accionBean.des_accion}",
			            		cod_emp			:"${accionBean.cod_emp}" ,
			            		nom_emp			:"${accionBean.nom_emp}" ,
			            		fecha_ini		:"${accionBean.fecha_ini}",
			            		fecha_ter		:"${accionBean.fecha_ter}",
			            		observacion		:"${accionBean.observacion}",
			            		estado			:"${accionBean.estado}",
			            		nom_estado		:"${accionBean.nom_estado}"
			            		
							}   
			            	${not status.last ? ',' : ''}
						</c:forEach>
					]
				}
			}
		);
	
	construirGridDetalle(store);
	construirGridAcciones(storeAcciones);
});


function construirGridDetalle(store){

	var layout = [
	      		{
	      	     name: ' ',
	      	  	 field:'vcodigoRqnp',
	      	  	
	      	     width: '0%',
	      	     headerStyles: "text-align: center",
	      	  	 styles:'text-align:center;'
	      	 	},
	               {
	             	     name: 'Código',
	             		 field:'vcodigoBien',
	             	     width: '14%',
	             	     headerStyles: "text-align: center",
	             	  	 styles:'text-align:center;'
	             	 },
	             	 {
	             		 name: 'Bienes y Servicios',
	             	  	 field: 'vdesBien',
	             	  	 width: '37%',
	             	     headerStyles: "text-align: center",
	             	  	 styles:'text-align:left;'
	             	 },
	             	 {
	             		 name: ' ',
	             	     field: 'vcodigoUnidad',
	             	     width: '0%',
	             	     headerStyles: "text-align: center",
	             	     styles:'text-align:center;'
	             	 },
	             	 {
	             		 name: 'Unid. Med.',
	             	     field: 'vdesUnid',
	             	     width: '8%',
	             	     headerStyles: "text-align: center",
	             	     styles:'text-align:center;'
	             	 }
	             	,
	            	 {
	            		 name: 'Precio Unit.',
	            		  field: 'vprecioUnid',
	            	     width: '8%',
	            	     headerStyles: "text-align: center",
	            	     styles:'text-align:right;' ,
	            	    formatter:getPrecioUnit
	            	 }
	             	,{
	            		 name: 'Cant.',
	            	     field: 'vcantBien',
	            	     width: '6%',
	            	     headerStyles: "text-align: center",
	            	     styles:'text-align:right;' ,
	            	   formatter:getPrecioUnit
	            	 },{
	            		 name: 'Total S/.',
	            	     field: 'vprecioTotal',
	            	     width: '8%',
	            	     headerStyles: "text-align: center",
	            	     styles:'text-align:right;', 
	            	    formatter:getPrecioUnit
	            	 }
	            	,{
	           		 name: ' ',
	           	     field: 'vnum_exp',
	           	     width: '0%',
	           	     headerStyles: "text-align: center",
	           	     styles:'text-align:right;' 
	           	   
	           	 },{
	           		 name: '&Aacute;rea T&eacute;cnica',
	           	     field: 'vauct_name',
	           	     width: '19%',
	           	     headerStyles: "text-align: center",
	           	     styles:'text-align:left;' 
	           	   
	           	 }
	  ];

	var grid = new dojox.grid.EnhancedGrid({
		
		store: store,
		rowSelector: '20px',
		structure: layout,
		escapeHTMLInData: false,
		rowsPerPage: 30,
		rowCount: 30,
		canSort: function(col){ return true; } ,
		style: { height:"200px", width:"98%", left:"1%" } ,
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
	 

	grid.startup();
	grid.selection.setSelected(0, true);
	dojo.connect(grid,'onRowClick',
		    function(e){
		
				var valNum_exp;
				
				/// Verificando si se 
			        var items = grid.selection.getSelected();
			        if(items.length) {
			            
			            dojo.forEach(items, function(selectedItem){
			                if(selectedItem !== null){
			                  
			                    dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
			                        /* Get the value of the current attribute:*/
			                        var value = grid.store.getValues(selectedItem, attribute);
			                       if (attribute=="vnum_exp"){
			                    	   valNum_exp =grid.store.getValues(selectedItem, attribute);
			                       }
			                       
			                    }); /* end forEach */
			                     recuperarAcciones(valNum_exp);
			                } 
			            }); /* end forEach */
			        } /* end if */
			}
		    );
}

function construirGridAcciones(store){
	
	var layout = [
	              {
	            	name: ' ',
	            	field:'cod_accion',
	            	width: '0%',
	            	headerStyles: "text-align: center",
	            	styles:'text-align:center;'
	            	 },
	            {name: 'Num.',
	         	  	 field:'sec_accion',
	         	     width: '4%',
	         	     headerStyles: "text-align: center",
	         	  	 styles:'text-align:center;'
	         	 },
            	 {
            		 name: 'Actividad',
            	  	 field: 'des_accion',
            	  	 width: '15%',
            	     headerStyles: "text-align: center",
            	  	 styles:'text-align:left;'
            	 },
            	 {
            		 name: 'Colaborador',
            	     field: 'nom_emp',
            	     width: '20%',
            	     headerStyles: "text-align: center",
            	     styles:'text-align:left;'
            	 },
            	 {
            		 name: 'F. Inicio',
            	     field: 'fecha_ini',
            	     width: '11%',
            	     headerStyles: "text-align: center",
            	     styles:'text-align:center;'
            	 }
	            ,
	           	 {
	           		 name: 'F. Termino',
	           		  field: 'fecha_ter',
	           	     width: '11%',
	           	     headerStyles: "text-align: center",
	           	     styles:'text-align:center;' 
	           	   
	           	 }
	            	,{
	           		 name: 'Acción',
	           	     field: 'observacion',
	           	     width: '30%',
	           	     headerStyles: "text-align: center",
	           	     styles:'text-align:left;' 
	           	 },{
	           		 name: 'Estado',
	           	     field: 'nom_estado',
	           	     width: '9%',
	           	     headerStyles: "text-align: center",
	           	     styles:'text-align:left;' 
	           	 }
	           	
	            	
	            	
	            	
	        	];

	var grid = new dojox.grid.EnhancedGrid({
		//id:'gridListaMetas',
		store: store,
		rowSelector: '20px',
		structure: layout,
		escapeHTMLInData: false,
		rowsPerPage: 10,
		rowCount: 10,
		canSort: function(col){ return true; } ,
		style: { height:"250px", width:"98%", left:"1%" } ,
		errorMessage: "Ha ocurrido un error al procesar la consulta",
		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
		noDataMessage: "No se encontraron registros",
		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
		plugins: {
			pagination: {
				pageSizes: ["10", "20", "30", "All"],
				description: true,
				sizeSwitch: false,
				pageStepper: true,
				gotoButton: false,
				maxPageStep: 10,       
				position: "bottom"
			},
			nestedSorting: true
		}
	},dojo.byId("gridAcciones"));
	 // dojo.byId("gridAcciones").appendChild(grid.domNode);
	grid.startup();
	
	dojo.connect(grid,'onKeyEvent',
		    function(e){  
		tecla = (document.all) ? e.keyCode : e.which;
		if (tecla==8) return true;
		patron =/[0-9]/;
		if (96 <=tecla  && tecla <=105){
			tecla=tecla - 48;
		}
	//alert(tecla);
		te = String.fromCharCode(tecla);
		//alert(te);
		if(! patron.test(te)){
			//alert('no');
			dojo.stopEvent(e);
		}
		dojo.byId("jload").value='1';
	}
	);
}






function getPrecioUnit(valor) {
	return addCommas(valor); 
}

function getMontoX(valor) {
    return addCommas(valor); 
}

function getBien(valor, item) {
	var vtxtPrecioUnit = '<input id="txtBien"  name="txtBien"    value="'+valor+'" dojoType="dijit.form.ValidationTextBox"      onchange="sumarDatos(this.value);" /> '; 
    return vtxtPrecioUnit; 
}


function getSecuencia(valor, item) {
	var vtxtPrecioUnit = '<input id="txtSecuencia"  name="txtSecuencia"    value="'+valor+'" dojoType="dijit.form.ValidationTextBox"      onchange="sumarDatos(this.value);" /> '; 
    return vtxtPrecioUnit; 
}

function getImage(valor,rowIndex){
	var grid = dijit.byId('gridDetalle');
	
	var itemIndex =grid.getItem(rowIndex);
	var val_saldo
	var val_exceso
	val_saldo =parseFloat( grid.store.getValue(itemIndex, 'vsaldo') );
	val_exceso =parseFloat( grid.store.getValue(itemIndex, 'vexceso') );
	if (val_saldo ==0){
		return  '<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
	}else if  ( val_exceso >0){
			return  '<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
	}else{
			return  valor;
	}
}


</script>


<script type="text/javascript">




var btnSalir_click = function(){
	var url =dojo.byId("path_url").value;
	document.frmRegistroMetas.action=url;
	/*if(dojo.byId("txtvisor").value=="0" ){
		document.frmRegistroMetas.action="rqnp.htm?action=iniciarbandeja";
	}else{
		document.frmRegistroMetas.action="rqnp.htm?action=iniciarconsulta";
	}*/
	document.frmRegistroMetas.submit();
}

var btnEnviar_click = function(valor){
	if(confirm("Confirme si desea ENVIAR el Requerimiento No Programado")){
		dojo.byId("action").value = "enviarRqnp";
		dojo.byId("txtCodigoRqnp").value = valor;
		dojo.byId("frmRegistroMetas").submit();
	}
};


function recuperarAcciones(num_exp){
	dojo.byId("action").value = "recuperarListaAccionesJson";
	
	dojo.byId("txtnum_expediente").value = num_exp;
	
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
					if (response.data != "" && response.data != "[]") {
						dojo.byId("jload").value='0';
						
						var grilla = dijit.byId("gridAcciones");
						
						var data = response.listaAcciones;
	    				var store = new dojo.data.ItemFileWriteStore(
	    					{
	    						data: { 
	    							items: data
	    						}
	    					}
	    				);
	    				grilla.setStore(store)
						
					}
				else{
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert("error ajax");
				//alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				alert("<fmt:message key='error.ajax'/>");
			},
			form: dojo.byId("frmRegistroMetas")
	};	
	dojo.xhrPost(enviar);
}


var btnAtras_click = function(){
	dojo.byId("accion").value = "modificarRqnp";
	dojo.byId("frmRegistroMetas").submit();
};

function formatear(){
	
}

dojo.addOnLoad(function() {	
	
	dijit.byId('btnSalir').setAttribute("disabled", false);
		
	divShow("divbtnSalir");
	
});

</script>

</head>

<body class="tundra">
	<center>

		<form id="frmRegistroMetas" name="frmRegistroMetas" action="rqnp.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="accion" name="accion" value="registrarRqnpDetalle"/>
		
			
			
			<input type="hidden" id="jupdate" name="jupdate" value="0"/>
			<input type="hidden" id="jload" name="jload" value="0"/>
			
			<input type="hidden" id="jcodigo_dep" name="jcodigo_dep" value="<c:out value='${mapRqnp.codigoDependencia}'/>" />
			<input type="hidden" id="janio_ejec" name="janio_ejec" value="<c:out value='${mapRqnp.anioProceso}'/>" />
			<input type="hidden" id="jcodigo_bien" name="jcodigo_bien" value="<c:out value='${mapRqnp.jcodigo_bien}'/>"   />
			<input type="hidden" id="txtnum_expediente" name="txtnum_expediente" value=""  />
			
			<input type="hidden" id="jcodigo_bien_old" 	name="jcodigo_bien_old" value="<c:out value='${mapRqnp.jcodigo_bien}'/>"   />
			<input type="hidden" id="jprecio_unid_old" 	name="jprecio_unid_old" value="<c:out value='${mapRqnp.jprecio_unid}'/>"  />
			
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
			
			<input type="hidden" id="path_url" name="path_url" value="<c:out value='${path_url}'/>" />
			
			<!--<input type="hidden" name="ind_estado" value="0"/>-->
			<table width="95%"  height= "240px"   cellpadding="0" cellspacing="0" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center"><fmt:message key="formRegistro.tituloSeguimiento" /></div>
											
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
										
									
										
										
										
										
										
										
									</table>
								</td>
								
							</tr>
										
							
							
							<tr>
								<td>
									<table width="100%" cellpadding="8" cellspacing="2" border="0">
										<tr>
											<td>
												<div id="gridDetalle"  > </div>
											</td>
										</tr>								
									</table>
								</td>
							</tr>
							
							
							
							
						</table>
					</td>
				</tr>
			</table>
			
			
			
			<table width="95%"  height="95%"  cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%" border=0>
							<tr>
									<td colspan="2">
									<table width="100%" cellpadding="0" cellspacing="0" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Seguimiento del Ítem</div>
											</td>
										</tr>
									</table>

								</td>
							</tr>
						

							
							
							<tr>
								<td colspan="2">
									<table width="100%" cellpadding="8" cellspacing="2" border="0">
										<tr>
											<td>
												<div id="gridAcciones"  data-dojo-id="gridAcciones" > </div>
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
										&nbsp;&nbsp;&nbsp;
										<div id="divbtnSalir" style="display: none;" >
											<button id="btnSalir" name="btnSalir" type="button" dojoType="dijit.form.Button" onclick="btnSalir_click()">Atrás</button>
										</div>
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
 alert(key);
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

function redondear2decimales(numero)
{
	var original=parseFloat(numero);
	var result=Math.round(original*100)/100 ;
	return result;
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


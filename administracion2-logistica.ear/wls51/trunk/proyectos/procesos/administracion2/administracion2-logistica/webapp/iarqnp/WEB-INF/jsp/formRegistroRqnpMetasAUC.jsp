<!DOCTYPE html>
<!-- charset=ISO-8859-1" -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPathUrl" scope="session" value="${pageContext.request.contextPath}"/>
<html lang="es">
<head>
<style type="text/css">
.dijitValidationTextBox input.dijitInputInner,.dijitNumberTextBox input.dijitInputInner,.dijitCurrencyTextBox input.dijitInputInner,.dijitSpinner input.dijitInputInner
	{
	padding-right: 1px;
	text-align: right;
}
</style>

<title><fmt:message key="formRegistro.titulo" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/iarqnp.css" />
<link rel="stylesheet" href="${contextPathUrl}/css/common/common.css" />
<link rel="stylesheet" href="${contextPathUrl}/css/common/general.css" />
	
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />
	
	<!-- CSS jQgrid -->   
	<link rel="stylesheet" href="/a/js/libs/bootstrap/3.3.1/css/font-awesome.min.css" />
	<link rel="stylesheet" href="/a/js/libs/jqgrid/4.6.0/css/ui.jqgrid.css" />
	<link rel="stylesheet" href="/a/js/libs/jstree/3.0.0/themes/default/style.min.css" />


<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iarqnp.js"> </script>  
<script type="text/javascript" src="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>

<!-- CSS Bootstrap -->
    <link rel="stylesheet" href="/a/js/libs/bootstrap/3.3.2/css/bootstrap.min.css">
    
<!-- CSS DataTables -->    
    <link rel="stylesheet" href="/a/js/libs/bootstrap/3.3.2/plugins/datatables-1.10.7/media/css/jquery.dataTables.css">
    <link rel="stylesheet" href="/a/js/libs/bootstrap/3.3.2/plugins/datatables-1.10.7/extensions/Responsive/css/dataTables.responsive.css">
    <link rel="stylesheet" href="/a/js/libs/jquery-ui/1.11.2/css/jquery-ui.min.css" />

<script type="text/javascript" LANGUAGE="JavaScript">
	dojo.require("dijit.form.Form");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dijit.Dialog");   
	dojo.require("dojo.date.locale");
    dojo.require("dijit.form.RadioButton");
    dojo.require("dojo.data.ItemFileReadStore"); //GRIDS DE SOLO LECTURA
    dojo.require("dojo.data.ItemFileWriteStore"); //GRIDS QUE PERMITEN ESCRITURA
    dojo.require("dojox.grid.EnhancedGrid"); //PARA CONSTRUIR GRIDS
    dojo.require("dojox.grid.DataGrid"); //PARA EL MANEJO DE DATOS PARA GRIDS
    dojo.require("dojox.grid.enhanced.plugins.Selector"); //PERMITE ELEGIR EL TIPO DE SELECCION DE FILA
    dojo.require("dojox.grid.enhanced.plugins.Pagination"); //AGREGA LA PAGINACION DE GRIDS
    dojo.require("dojox.grid.enhanced.plugins.NestedSorting"); //HABILITA EL ORDENAMIENTO AUTOMATICO
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
		            		vprecioTotal	:"${bienesBean.precioTotal}" ,
		            		vsaldo			:"${bienesBean.saldo}" ,
		            		vexceso			:"${bienesBean.exceso}" ,
		            		vimage			:" ",
		            		vdesBien		:"${bienesBean.desBien}",
		            		vdesUnid		:"${bienesBean.desUnid}",
		            		
		            		//AGREGADO(DPORRASC)
		            		vcodAmbito		:"${bienesBean.des_ambito}",
		            		vcodTipoProg	:"${bienesBean.cod_tipo_prog}",
							vcodPlaza	:"${bienesBean.cod_plaza}" //Agregado
						}   
		            	${not status.last ? ',' : ''}
					</c:forEach>
				]
			}
		}
	);
	
	var storeMetas = new dojo.data.ItemFileWriteStore({
				data: { 
					items: []
				}
			});
	construirGridDetalle(store);
	construirGridMetas(storeMetas);

	//VALIDAR UUOO EVENTO KEYPRESS
	var  val_num_uuoo= dijit.byId("txtNum_UUOOe");
	dojo.connect(val_num_uuoo,'onKeyPress',function(evt){  
		var key = evt.which || evt.keyCode;
	    if (key == 13 ){
	    	btnRecuperarUUOO();
	    }
	    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
	    evt.keyCode = key;
	});

	dojo.connect(val_num_uuoo,'onChange',function(evt){  
		btnRecuperarUUOO();
	});

});


function construirGridDetalle(store){
	var layout = [ 				//PLAN DE LA STRUCTURA DEL GRID DE DATOS
		{
       	     name: 'Codigo',
       		 field:'vcodigoBien',
       	     width: '10%', 
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;'
       	 },{
       		 name: 'Bienes y Servicios',
       	  	 field: 'vdesBien',
       	  	 width: '30%', 
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:left;'
       	 },{
       		 name: ' ',
       	     field: 'vcodigoUnidad',
       	     width: '0%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 },{
       		 name: 'Unid. Med.',
       	     field: 'vdesUnid',
       	     width: '7%', 
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 },{
      		 name: 'Precio Unit.',
      		 field: 'vprecioUnid',
      	     width: '7%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' ,
      	     formatter:getPrecioUnit
      	 },{
      		 name: 'Cantidad',
      	     field: 'vcantBien',
      	     width: '7%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' ,
      	     formatter:getPrecioUnit
      	 },{
      		 name: 'Total S/.',
      	     field: 'vprecioTotal',
      	     width: '7%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;', 
      	    formatter:getPrecioUnit
      	 },{
    		 name: 'Saldo S/.',
    	     field: 'vsaldo',
    	     width: '7%',
    	     headerStyles: "text-align: center",
    	     styles:'text-align:right;', 
    	     formatter:getPrecioUnit
    	 },{
     		 name: 'Exceso S/.',
     	     field: 'vexceso',
     	     width: '7%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:right;', 
     	    formatter:getPrecioUnit
     	 },{
     		 name: 'Alerta',
     	     field: 'vimage',
     	     width: '5%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:right;', 
     	    formatter:getImage
     	 },{
    		 name: 'Ambito',
    	     field: 'vcodAmbito',
    	     width: '13%',
    	     headerStyles: "text-align: center",
    	     styles:'text-align:left;' 
    	 },{
    		 name: 'Tipo Prog',
    	     field: 'vcodTipoProg',
    	     width: '0%',
    	     headerStyles: "text-align: center",
    	     styles:'text-align:right;' 
    	 } ,{
    		 name: '',
    	     field: 'vcodPlaza',
    	     width: '0%',
    	     headerStyles: "text-align: left",
    	     styles:'text-align:right;' 
    	 },{
	     	name: '', 							//NOMBRE DE LA CABECERA QUE SE MUESTRA DE LA COLUMNA
	  		field:'vcodigoRqnp', 				//NOMBRE DEL CAMPO DE REGISTRO DE DATOS PARA MOSTRAR
	     	width: '0%', 						// ANCHO DE LA CELDA
	     	headerStyles: "text-align: center", //STYLE DE LA CABECERA
	  	 	styles:'text-align:center;' 		//STYLE DE LA CELDA
	 	 }
   	];

	var grid = new dojox.grid.EnhancedGrid({
		//id: IDENTIFICADOR DEL GRID (SE OBVIA MUCHAS VECES)
		store: store,  			//DATOS JSON TIPO (var storeMetas = new dojo.data.ItemFileWriteStore)
		rowSelector: '20px', 	//TAMAÑO RESERVADO PARA GUARDAR LA SELECCION
		structure: layout, 		//PLAN O DISPOSICION DE STRUCTURA DEL GRID
		escapeHTMLInData: false,//SI LA INFORMACION ES CONFIABLE
		selectionMode:'single', //OPCIONES: NONE, SINGLE, MULTIPLE, EXTENDED
		rowsPerPage: 30, 		//NUMERO DE FILAS POR PAGINA
		rowCount: 30, 			//CANTIDAD DE FILAS
		canSort: function(col){ return true; } , //PERMITE ORDENAMIENTO DE COLUMNAS
		style: { height:"150px", width:"98%", left:"1%" } , // STYLE DEL GRID (ALTO Y ANCHO)
		errorMessage: "Ha ocurrido un error al procesar la consulta", //MENSAJE SI ERROR EN LA DATA
		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>', //MENSAJE MIENTRAS EL CONTENIDO ESTE CARGANDO
		noDataMessage: "No se encontraron registros", //MENSAJE SI NO SE ENCONTRARON RESULTADOS
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
			nestedSorting: true // HABILITA EL ORDENAMIENTO AUTOMATICO DE COLUMNAS
		}
	},
	
	dojo.byId("gridDetalle"));
	grid.startup();
	//grid.selection.select('row', 1,1);
	//grid.scrollToRow(1);
	grid.selection.setSelected(0, true);  //SELECCIONA LA PRIMERA FILA DEL GRID
	
	//INICIO LLAMADA FUNCION POR DEFECTO
	var valcodigoBien;
	var valcodigoRqnp;
	var valprecioUnid;
	/// Verificando si se selecciono un item (bien para asignar meta)
		
	//LEE LA FILA SELECCIONADA
	var items = grid.selection.getSelected(); 
			      	
	//SI EXISTEN ITEMS SELECCIONADOS
	if(items.length) {
		dojo.forEach(items, function(selectedItem){
			if(selectedItem !== null){
				dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
					/* Get the value of the current attribute:*/
					var value = grid.store.getValues(selectedItem, attribute);
					if (attribute=="vcodigoRqnp"){
						valcodigoRqnp =grid.store.getValues(selectedItem, attribute);
					}
					if (attribute=="vcodigoBien"){
						valcodigoBien =grid.store.getValues(selectedItem, attribute);
					}
					if (attribute=="vprecioUnid"){
						valprecioUnid =grid.store.getValues(selectedItem, attribute);
					}
					if (attribute=="vcodAmbito"){
						valcodAmbito =grid.store.getValues(selectedItem, attribute);
					}
				}); /* end forEach */
				                    
				if (dojo.byId("jupdate").value=="0"){
					dojo.byId("jprecio_unid").value=valprecioUnid;
					dojo.byId("jcodigo_bien").value=valcodigoBien;
					dojo.byId("jcod_ambito").value=valcodAmbito;
					recuperarMetasAUC(valcodigoRqnp,valcodigoBien); 
				                     
				}else{
					btnGuardar_click(valcodigoBien,valprecioUnid);
				}
			} 
		}); /* end forEach */
	} /* end if */
	//FIN LLAMADA FUNCION POR DEFECTO
	
	
	//CONECTA OBJETO CON UN EVENTO EN PARTICULAR
	dojo.connect(grid,'onRowClick', function(e){
		var valcodigoBien;
		var valcodigoRqnp;
		var valprecioUnid;
		/// Verificando si se selecciono un item (bien para asignar meta)
		
		//LEE LA FILA SELECCIONADA
        var items = grid.selection.getSelected(); 
      	
		//SI EXISTEN ITEMS SELECCIONADOS
        if(items.length) { 
            dojo.forEach(items, function(selectedItem){
                if(selectedItem !== null){ //CAMBIADO PARA REPLICA
                    dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
                        /* Get the value of the current attribute:*/
                        var value = grid.store.getValues(selectedItem, attribute);
                       if (attribute=="vcodigoRqnp"){
                    	   valcodigoRqnp =grid.store.getValues(selectedItem, attribute);
                       }
						if (attribute=="vcodigoBien"){
							valcodigoBien =grid.store.getValues(selectedItem, attribute);
                       }if (attribute=="vprecioUnid"){
                    	   valprecioUnid =grid.store.getValues(selectedItem, attribute);
                       }
                       if (attribute=="vcodAmbito"){
                    	   valcodAmbito =grid.store.getValues(selectedItem, attribute);
                       }
                    }); /* end forEach */
                    
                    if (dojo.byId("jupdate").value=="0"){
                    	dojo.byId("jprecio_unid").value=valprecioUnid;
                    	dojo.byId("jcodigo_bien").value=valcodigoBien;
                    	dojo.byId("jcod_ambito").value=valcodAmbito;
	                //recuperarMetasAUC(valcodigoBien,valprecioUnid); DPORRASC
		                recuperarMetasAUC(valcodigoRqnp,valcodigoBien)
		                  
                    }else{
                    	//alert("guardar" + valcodigoRqnp + " - "+ valcodigoBien);
                    	btnGuardar_click(valcodigoBien,valprecioUnid);
                    }
                }
            }); /* end forEach */
        } /* end if */
	});
}



var btnValidaEnviar_click = function(valor){
	var grid =dijit.byId("gridDetalle");
	 var items = grid.selection.getSelected();
     if(items.length) {
    	
         dojo.forEach(items, function(selectedItem){
             if(selectedItem != null){
            	 
                 dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
                     /* Get the value of the current attribute:*/
                     var value = grid.store.getValues(selectedItem, attribute);
                    if (attribute=="vcodigoRqnp"){
                 	   valcodigoRqnp =grid.store.getValues(selectedItem, attribute);
                    }
						if (attribute=="vcodigoBien"){
							valcodigoBien =grid.store.getValues(selectedItem, attribute);
                    }if (attribute=="vprecioUnid"){
                 	   valprecioUnid =grid.store.getValues(selectedItem, attribute);
                    }
                    
                 }); /* end forEach */
                 if (dojo.byId("jupdate").value=="0"){
                	
                	btnValidaEnviar_click2(valor);
                 }else{
                	// btnValidaEnviar_click2(valor);
                 	btnGuardar_click3(valcodigoBien,valprecioUnid,valor);
                 }
             }else{
            	 btnValidaEnviar_click2(valor);
             } 
         }); /* end forEach */
     }else{
    	 
    	 btnValidaEnviar_click2(valor);
     }  /* end if */
};

//MODIFICADO
var btnValidaEnviar_click2 = function(valor){
	//dojo.byId("action").value = "validaMetasBienJson";
	dojo.byId("action").value = "validaMetasBienJsonAUC";
	dojo.byId("txtCodigoRqnp").value =valor;
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						var data = eval("(" + unescape(response.data )+ ")");
						if (data.validaMeta=='0'){
							btnSiguiente_aprobar(valor);
						}else if( data.validaMeta=='1'){
							alert("Algunos ítems NO tienen asignado Afectación Presupuestal POI");
						}else{
							alert("El Requerimiento NO tiene ítems Registrados");
						}
					}
				}else{
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
			},
			form: dojo.byId("frmRegistroMetas")
	};	
	
	dojo.xhrPost(enviar);
}

function construirGridMetas(store){ //RECIBE (storeMetas)
	var layout = [
 	 	{
      	     name: 'UUOO Beneficiaria',
      		 field:'vnombreUUOO',
      	     width: '17%',
      	     headerStyles: "text-align: center",
      	  	 styles:'text-align:left;'
      	 },{
		     name: 'Actividad - Proyecto',
		  	 field:'vubg',
		     width: '17%',
		     headerStyles: "text-align: center",
		  	 styles:'text-align:left;'
	 	},{
      		 name: ' ',
      	     field: 'vcodigoBienM',
      	     width: '0%',
      	     headerStyles: "text-align: center",
      	   formatter:getBien,
      	     styles:'text-align:left;'
      	 },{
       	     name: 'Producto',
       		 field:'vproducto',
       	     width: '17%',
       	  // type: dojox.grid.cells.Editor,
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:left;'//,
       	  	//editable: true
       	 },{
       		 name: 'Acción',
       	  	 field: 'vmeta',
       	  	 width: '17%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:left;'
       	 },
           	{
           		 name: 'Meta Siaf ',
           	     field: 'vmetaSiaf',
           	     width: '17%',
           	     headerStyles: "text-align: center",
           	     styles:'text-align:left;'
           	  //formatter:getSecuencia
           	 },
       	 {
       		 name: ' ',
       	     field: 'vsecuenciaMeta',
       	     width: '0%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;',
       	  	 formatter:getSecuencia
       	 },{
      		 name: 'Cantidad',
      		 field: 'vxcantidadTotal',
      	     width: '5%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' ,
    	   	 formatter:getCantidadX,
        	 editable: true
      	 },{
      		 name: ' ',
      		 field: 'vcantidadTotal',
      	     width: '0%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' ,
    	   	 formatter:getCantidad,
    	   	 type: dojox.grid.cells.Cell ,
        	 editable: true
      	 },{
      		 name: 'Prec. Unit.',
      		 field: 'vprecioUnid',
      	     width: '5%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' ,
      	     formatter:getPrecioUnit
      	 },{
      		 name: 'Total S/.',
      	     field: 'vxmontoSoles',
      	     width: '6%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;',
      	     formatter:getMontoX
      	 },{
     		 name: ' ',
     	     field: 'vmontoSoles',
     	     width: '0%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:right;',
     	     formatter:getMonto
     	 }
   	];

	var grid = new dojox.grid.EnhancedGrid({
		//id:'gridMetas', //ESTUVO COMENTADO
		store: store,
		rowSelector: '20px',
		structure: layout,
		escapeHTMLInData: false,
		rowsPerPage: 30,
		rowCount: 30,
		selectionMode:'multiple', 
		//selectionMode:'single','multiple',
		//updateDelay:3,
		singleClickEdit:true,
		//onDeselected:onDeselectedGridParm,
		//onFocus :onFocusMetas, 
		//onCancelEdit:onCancelEditMetas ,
		onApplyCellEdit:onApplyCellEditMetas,
		//onStartEdit:onStartEditMetas,
		//onCellFocus:onCellFocusMetas,
		canSort: function(col){ return true; } ,
		style: { height:"220px", width:"98%", left:"1%" } ,
		errorMessage: "Ha ocurrido un error al procesar la consulta",
		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
		noDataMessage: "No se encontraron registros",
		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
		plugins: {
			pagination: {
				pageSizes: ["30", "60", "90", "120", "All"],
				description: true,
				sizeSwitch: false,
				pageStepper: true,
				gotoButton: false,
				maxPageStep: 5,       
				position: "bottom"
			},
			nestedSorting: true
		}
	},dojo.byId("gridMetas"));
	 // dojo.byId("gridMetas").appendChild(grid.domNode);
	
	dojo.connect(grid,'onKeyEvent', function(e){  
				tecla = (document.all) ? e.keyCode : e.which;
				//alert(tecla);
				if (tecla==8){  		//TECLA BACKSPACE
					return true;
				}else if (tecla==9){	//TECLA TAB HORIZONTAL
					//dojo.byId("jload").value='0';
				}else{
					patron =/[0-9]/;
					if (96 <=tecla && tecla <=105){
						tecla=tecla - 48; //VALIDA QUE LAS TECHAS SEAN NUMEROS
					}
					
					if ( tecla ==37 || tecla ==39) {
						
					}else{
						te = String.fromCharCode(tecla);
						//alert(te);
						if(!patron.test(te)){
							dojo.stopEvent(e);
						}
						dojo.byId("jload").value='1';
					}
				}
	});
	
	   grid.canEdit = function(inCell, inRowIndex) { 
		    
		    //	console.log("inCell = " + inCell.index);
				if (inCell.index == 7){ //INICIALMENTE 5, ES EL NUMERO DE COLUMNA EDITABLE
			        var visor = dojo.byId('txtvisor').value;
			       
			    //  console.log("visor = " + visor);
		
			    	if (visor=='1'){
			    		return false;
			    	}else{
			    		return true;
			    	}
				}else{
					return false;
				}
		    };
	grid.startup();
}


function getCantidadX(valor, rowIndex) {
	//Validando la expresion Regular
	var patron=/[0-9]{1,9}/;
	
	var rsta = valor.match(patron);
	var grid = dijit.byId('gridMetas');
	var items = grid.selection.getSelected();
	
	valor =  addCommas(valor);
	var val_txtCantidad=new dijit.form.ValidationTextBox({  name: "txtxCantidad" , value: valor , style:"width:85%;text-align:right;" ,   regExp:"[0-9]+[\.|\,]*[0-9]*[\.|\,]*[0-9]*[\.|\,]*[0-9]{0,2}" , invalidMessage:"Por favor ingrese la Cantidad " , required:"true" ,   TabIndex :"-1"} );

	if (dojo.byId("txtvisor").value=="0"){
		return  val_txtCantidad;
	}else{
		return valor;
	}
}


//FUNCIONES PARA USAR COMO FORMATOS DE LOS DATOS DEL GRID
function getCantidad(valor) {
	var vtxtPrecioUnit = '<input id="txtCantidad"  name="txtCantidad"    value="'+valor+'" dojoType="dijit.form.ValidationTextBox"   required="true" invalidMessage="Por favor ingrese la Cantidad "   onchange="sumarDatos(this.value);" /> '; 
    return vtxtPrecioUnit; 
}

function getMonto(valor, item) {
	var vtxtPrecioUnit = '<input id="txtMonto"  name="txtMonto"    value="'+valor+'" dojoType="dijit.form.ValidationTextBox"      onchange="sumarDatos(this.value);" /> ';  
    return vtxtPrecioUnit; 
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
	var vtxtPrecioUnit = '<input id="txtSecuencia"  name="txtSecuencia"    value="'+valor+'" dojoType="dijit.form.ValidationTextBox"   onchange="sumarDatos(this.value);"/> '; 
    return vtxtPrecioUnit; 
}

function getImage(valor,rowIndex){
	var grid = dijit.byId('gridDetalle');
	
	var itemIndex =grid.getItem(rowIndex);
	var val_saldo
	var val_exceso
	val_saldo =parseFloat( grid.store.getValue(itemIndex, 'vsaldo') );
	val_exceso =parseFloat( grid.store.getValue(itemIndex, 'vexceso') );
	
	var gridDet = dijit.byId('gridDetalle');
    var sw='0';
    if(	gridDet.store !=null){
		if((gridDet.store._arrayOfAllItems.length > 0 )){
			gridDet.store.fetch({
				onItem: function(item, request){
					if (item.vexceso.toString() !="0.00" ){
						sw='1';
					}
		 		}
			});
		}
	}
	if (sw=="1"){
		divShow('divmsgExceso');
		divShow('divmsgImage');
	}else{
		divHide('divmsgExceso');
		divHide('divmsgImage');
	}
	
	if (val_saldo ==0){
		
		return  '<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
	}else if  ( val_exceso >0){
		return  '<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
			
	}else{
		return  valor;
	}
}


function onApplyCellEditMetas(inValue,inRowIndex, inFieldIndex){
	if (dojo.byId("jload").value='1') {
		var grid = dijit.byId('gridMetas');
		var prec_uni;
		var prec_unix;
		var cant;
		var mont_total= parseFloat(0);
		var mont_total_txt;
		var rowItem =null;
		var row=null;
		var valor ;
		
		var rowItem  = grid.getItem(inRowIndex);
		prec_uni = parseFloat(removeCommas(grid.store.getValue(rowItem, 'vprecioUnid')));
		valor = removeCommas(grid.store.getValue(rowItem, 'vxcantidadTotal'));
		
		//**********************************************/
    	//SETEANDO DATOS DETALLE----------------------------------------------------------------
 		var grid_bienes = dijit.byId('gridDetalle');
 		var items_bienes =grid_bienes.selection.getSelected();
 		var flagMtoTotal=true;
 		if(items_bienes.length){
 			dojo.forEach(items_bienes, function(selectedItem_bien){
 			      if(selectedItem_bien !== null){
 			      	 dojo.forEach(grid_bienes.store.getAttributes(selectedItem_bien), function(attribute){
 			      		if(attribute=='vcodigoBien') { 
 			      			var tipoItem=grid_bienes.store.getValue(selectedItem_bien, 'vcodigoBien').substr(0,1);
 			      			cantidadOriginal=grid_bienes.store.getValue(selectedItem_bien, 'vcantBien');
 			              	if(tipoItem=='S' && valor=='1'){
 			              		alert("Debe ingresar el monto aproximado en (S/.) que costará el servicio, para que se pueda actualizar la cantidad.\n\nIngrese nuevamente la cantidad para continuar con el procedimiento.");
 			              		flagMtoTotal=false;
 			              	}
 			      		}
 			      	 });
 			      }
 			});
 		}
 		//**********************************************/
		
	 	if(flagMtoTotal){
	 		cant= parseFloat(valor);
	 		//console.log(" cant " + cant);
	 		if (valor =="" ){
		 	 valor =0;	
		 	}
	 		console.log(" valor2 " + valor);
	 		
	 		cant= parseFloat(valor); //CONVIERTE A FORMATO PUTO DECIMAL
	 		console.log(" cant2 " + cant);
	 		
	 		mont_total = cant * prec_uni;
	 		mont_total= redondear2decimales(mont_total);
	 		mont_total= mont_total.toFixed(2); //APLICA EL FORMATO DE 2 DECIMALES AL RESULTADO
	 		mont_total_txt =addCommas(mont_total);
		 	
	 		 if (rowItem!=null){
	 			grid.store.setValue(rowItem, 'vmontoSoles',mont_total);
	 			grid.store.setValue(rowItem, 'vxmontoSoles',mont_total_txt);
	 			
	 			var val_actual=grid.store.getValue(rowItem, 'vcantidadTotal');
	 		
	 			if ( val_actual!=valor && dojo.byId("jload").value=='1' ){ //SE ACTIVA SI YA SE CARGO UNA META 
	 				dojo.byId("jupdate").value='1'; //SE ACTIVA AL ACTUALIZAR LA CANTIDAD DE AFECTACION PRESUPUESTAL
	 			}
	 			// SUMANDO METAS
	 			var val_sum_total =parseFloat(0);
	 			var val_sum_cantidad=parseFloat(0);
	 			var val_saldo=parseFloat(0);
	 			var val_exceso=parseFloat(0);
	 			if(	grid.store !=null){
	        		if((grid.store._arrayOfAllItems.length > 0 )){
	        			grid.store.fetch({
	        				onItem: function(item, request){
	        							val_sum_total =val_sum_total + parseFloat( item.vmontoSoles);
	        							val_sum_cantidad =val_sum_cantidad + parseFloat( item.vcantidadTotal);
	        		 				}
	        			});
	        		}
	        	}
	 			
	 			val_sum_total= redondear2decimales(val_sum_total);
	 			
	 			val_sum_total=val_sum_total.toFixed(2);
	
	 			//SETEANDO DATOS DETALLE----------------------------------------------------------------
	 			var grid_bienes = dijit.byId('gridDetalle');
	 			var items_bienes =grid_bienes.selection.getSelected();
	 			if(items_bienes.length){
	 				  dojo.forEach(items_bienes, function(selectedItem_bien){
	 				        if(selectedItem_bien !== null){
	 				        	
	 				        	 dojo.forEach(grid_bienes.store.getAttributes(selectedItem_bien), function(attribute){
	 				                   if(attribute=='vcantBien') { 
	 				                	  
	 				                	  	val_sum_cantidad = val_sum_cantidad - val_actual + parseFloat(valor);
	 				                	  	
	 				                	  	grid_bienes.store.setValue(selectedItem_bien, 'vcantBien',addCommas(val_sum_cantidad.toString()));
	 				                	   
	 				                   }
	 				                  if(attribute=='vprecioTotal') { 
	 				                	 grid_bienes.store.setValue(selectedItem_bien, 'vprecioTotal',addCommas(val_sum_total.toString()));
	 				                	   
	 				                   }
	     							
	 				                 if(attribute=='vexceso') { 
	 				                	val_saldo = parseFloat( grid_bienes.store.getValue(selectedItem_bien, 'vsaldo'));
	 	     							val_exceso =  val_saldo - val_sum_total;
	 	     							if ( val_exceso <0){
	 	     								val_exceso=val_exceso * (-1);
	 	     								
	 	     							}else if ( val_exceso >0) {
	 	     								val_exceso=0;
	 	     							}
	 	     							val_exceso= redondear2decimales(val_exceso);
	 	     				 			
	 	     							val_exceso=val_exceso.toFixed(2);
	 				                	 grid_bienes.store.setValue(selectedItem_bien, 'vexceso',addCommas(val_exceso.toString()));
	 				                	   
	 				                   }
	 				        	 }); 
		 				        } 
	 				    }); 
	 			}
	 			//grid_bienes.store.save();
	 			// sumando gran total--------------------------------------------------------
	 			var val_grand_total=0;
	 			if(	grid_bienes.store !=null){
	        		if((grid_bienes.store._arrayOfAllItems.length > 0 )){
	        			grid_bienes.store.fetch({
	        				onItem: function(item, request){
	
	        					val_grand_total =val_grand_total + parseFloat( removeCommas(item.vprecioTotal));
	        		 			}
	        			});
	        		}
	        	} 
	 			//val_grand_total= redondear2decimales(val_grand_total);----------------------
				val_grand_total=val_grand_total.toFixed(2);
				dojo.byId("txtMontoAcumulado").value=addCommas(val_grand_total.toString()) ; 
					grid.store.setValue(rowItem, 'vcantidadTotal',valor);
	 		 }
	 		 return valor;
 		 }else{// fin (flagMtoTotal)
					grid.store.setValue(rowItem, 'vxcantidadTotal',cantidadOriginal);
 			}
	} // fin (jload)
}


function onStartEditMetas(inCell, inRowIndex){
	//var grid= dijit.byId("gridMetas");
	//grid.store._saveEverything = function(saveCompleteCallback, saveFailedCallback, newFileContentString){  
	//	console.log("_saveEverything 2.0");  
		
	//	   saveCompleteCallback();
	//	   saveFailedCallback();
	//	}
	//grid.store.save();
	//console.log("onStartEditMetas :" + inCell+ " - "+inRowIndex   );
}

var savecomplete = function() {
    console.log("SAVE COMPLETE");
}
var saveerror = function() {
    console.log("SAVE ERROR");
} 
</script>


<script type="text/javascript">
var btnGuardar_click = function(cod_bien,precioUnid) {

	if(!dijit.byId('frmRegistroMetas').validate()){
		return;
	}
	//validando cantidades
	var grid = dijit.byId('gridMetas');
	
	if(	grid.store !=null){
		if((grid.store._arrayOfAllItems.length > 0 )){
			grid.store.fetch({
				onItem: function(item, request){
				 	var cantidad =item.vcantidadTotal;
				 	if(cantidad ==""){
				 		alert("Registre cantidad de la afectación no se permite valores nulos");
				 	}
		 		}
			});
		}
	}
	
	dojo.byId("jcodigo_bien_old").value = dojo.byId("jcodigo_bien").value;
	dojo.byId("jprecio_unid_old").value = dojo.byId("jprecio_unid").value;

	dojo.byId("jcodigo_bien").value = cod_bien;
	dojo.byId("jprecio_unid").value = precioUnid;

  	//dijit.byId('btnGuardar').setAttribute("disabled", true);
  	
	dojo.byId("action").value = "registrarRqnpMetasJson";
	
	//llamada AJAX
	var enviar = {
			handleAs 	: "json",
			load 		: function(response, ioArgs) {
							if(response.data.error==null){
								if(response.data != "" && response.data != "[]") {
									var data = eval("(" + unescape(response.data) + ")");
									dojo.byId("jload").value='0';
									actualizarMetas(data.listaMetas);
									//dijit.byId('btnEnviar').setAttribute("disabled", false);
								}
							}
							else{
								alert(response.data.mensaje);
							}
						  },
			timeout 	: 25000,
			error 		: function(error, ioArgs) {
							alert("error ajax");
							alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
							//alert("<fmt:message key='error.ajax'/>");
							},
			form		: dojo.byId("frmRegistroMetas")
	};	
	
	dojo.xhrPost(enviar);
	dojo.byId("jupdate").value ="0";

	//dijit.byId('frmRegistroMetas').submit()
}


//GUARDA LOS CAMBIOS EN LA CANTIDAD DE PRESUPUESTO
var btnGuardar_click2 = function () { 

	if(!dijit.byId('frmRegistroMetas').validate()){
		return;
	}
  	//dijit.byId('btnGuardar').setAttribute("disabled", true);
	dojo.byId("action").value = "registrarRqnpMetasJsonClick";
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						//LEE DATOS(JSON) QUE DEVUELVE EL SERVLET (data.listaMetas)
						//alert("Click Guardar(abajo )-registrarRqnpMetasJsonClick: "+data); //AGREGADO: DPORRASC
						
						var data = eval("(" + unescape(response.data) + ")");
						dojo.byId("jload").value='0';
						actualizarMetas(data.listaMetas);
						alert("Se Registraron Correctamente los Datos");
						//dijit.byId('btnEnviar').setAttribute("disabled", false);
					}
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
	dojo.byId("jupdate").value ="0";
}

var btnGuardar_click3 = function (cod_bien,precioUnid, valor) {

	if(!dijit.byId('frmRegistroMetas').validate()){
		return;
	}
  	//dijit.byId('btnGuardar').setAttribute("disabled", true);
	dojo.byId("action").value = "registrarRqnpMetasJsonClick";
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						//LEE DATOS(JSON) QUE DEVUELVE EL SERVLET (data.listaMetas)
						//alert("Click Guardar(abajo )-registrarRqnpMetasJsonClick: "+data); //AGREGADO: DPORRASC
						
						var data = eval("(" + unescape(response.data) + ")");
						dojo.byId("jload").value='0';
						actualizarMetas(data.listaMetas);
						//alert("Se Registraron Correctamente los Datos");
						btnValidaEnviar_click2(valor);
						//dijit.byId('btnEnviar').setAttribute("disabled", false);
					}
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
	dojo.byId("jupdate").value ="0";
	
}

var btnSalir_click = function(){
	if(dojo.byId("txtvisor").value=="0" ){
		document.frmRegistroMetas.action="rqnpauc.htm?action=auciniciarbandeja";
	}else{
		document.frmRegistroMetas.action="rqnpauc.htm?action=auciniciarconsulta";
	}
		document.frmRegistroMetas.submit();
}

var btnEnviar_click = function(valor){
	if(confirm("Confirme si desea ENVIAR el Requerimiento No Programado")){
		dojo.byId("action").value = "enviarRqnp";
		dojo.byId("txtCodigoRqnp").value = valor;
		var formDlg = dijit.byId("formDialogEnvio");
		formDlg.show();
		dojo.byId("frmRegistroMetas").submit();
	}
};

//FUNCION QUE HACE SELECT A "REQUERIMIENTO_NO_PROG_METAS" CODIGO CODIGO DE BIEN
function recuperarMetas(cod_bien,precioUnid){
	dojo.byId("action").value = "recuperarMetasJson";
	dojo.byId("jcodigo_bien").value = cod_bien;
	dojo.byId("jprecio_unid").value = precioUnid;
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
					//	alert(response.data);
						var data = eval("(" + unescape(response.data) + ")");
						dojo.byId("jload").value='0';
						actualizarMetas(data.listaMetas); //(listaMetas)Se recupera del servlet
					}
				}
				else{
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error 	: function(error, ioArgs) {
				alert("error ajax");
				//alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				alert("<fmt:message key='error.ajax'/>");
			},
			form: dojo.byId("frmRegistroMetas")
			

	};	
	dojo.xhrPost(enviar);
}


//DEFINIR VARIABLE GLOBAR DE LA LISTA DE METAS
var listaMetasAuc;
var ind_replica='0';
var ambito_1="";
//FIN DE DEFINIR VARIALE GLOBAL DE LISTA DE METAS

//DEVUELVE METAS DEL ITEM SELECCIONADO, GUARDADO EN LA BD (requerimientonoprogmetas.selectRqnpMetas_bien)
function recuperarMetasAUC(valcodigoRqnp,valcodigoBien){
	dojo.byId("action").value = "recuperarMetasJsonAUC";
	dojo.byId("txtCodigoRqnp").value=valcodigoRqnp;
	dojo.byId("jcodigo_bien").value=valcodigoBien;
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						var data = eval("(" + unescape(response.data) + ")");
						dojo.byId("jload").value='0';
						actualizarMetas(data.listaMetas);
					}
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


function actualizarMetas(lista){	//listaMetas:formato JSON, se obtuvo del servlet como tipo lista.
	
	var lsMetas =lsMetasItems(lista);

	var storeMetas = new dojo.data.ItemFileWriteStore({
		data: {
			//identifier: 'vsecuenciaMeta', PARA IDETINFICADOR DE LA GRILLA
			identifier: 'vsecuenciaMeta', 
			items: lsMetas
		}});
	
	var _grdLstPagAnt= dijit.byId("gridMetas");
	if (_grdLstPagAnt==null ){
		construirGridMetas(storeMetas);
	}else{
		_grdLstPagAnt.setStore(storeMetas);
		_grdLstPagAnt.store.save();
		_grdLstPagAnt.update();
	}
}

function lsMetasItems(items){
	/* 	//FUNCION recuperarMeta() -- LO QUE DEVUELVE EL SERVLET
	JSON={"data":"{
	"listaMetas":"[{
		"precioUnid"	:"28.49",
		"cantidadTotal"	:"0",
		"montoDolar"	:"0.00",
		"codigoRqnp"	:"201400005884",
		"ubg"			:"074 2014 Administración",
		"anioEjec"		:"2014",
		"montoSoles"	:"0.00",
		"producto"		:"074.01 Administración",
		"secuenciaMeta"	:"1079",
		"codigoBien"	:"B715000220021",
		"uuoo"			:"8B2400 DIVISION DE ALMACEN DE BIENES DE USO CONSUMO Y MOBILIARIO",
		"meta"			:"074.01.01 Administración"
	}]"}"} */
	
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
			cadena += "{vcodigoRqnpM:'" 	+ item.codigoRqnp	+ "',";	
			cadena += "vnombreUUOO:'" 		+ item.uuoo	+ "',";
			cadena += "vcodigoBienM:'" 		+ item.codigoBien	+ "',"; //cambia para replica
			cadena += "vanioEjec:'" 		+ item.anioEjec	+ "',";
			cadena += "vsecuenciaMeta:'" 	+ item.secuenciaMeta + "',";
			cadena += "vmetaSiaf:'" 		+ item.metaSiaf + "',";
			cadena += "vcantidadTotal:'" 	+ item.cantidadTotal + "',";//cambia para replica	
			cadena += "vxcantidadTotal:'" 	+ item.cantidadTotal + "',";//cambia para replica	
			cadena += "vmontoSoles	:'" 	+ item.montoSoles  	+ "',";//cambia para replica
			cadena += "vxmontoSoles	:'" 	+ item.montoSoles  	+ "',";//cambia para replica
			cadena += "vprecioUnid	:'" 	+ item.precioUnid 	+ "',";
			cadena += "vproducto	:'" 	+ item.producto  	+ "',";
			cadena += "vmeta	:'" 		+ item.meta	+ "',";
			cadena += "vubg:'" 				+ item.ubg  		+ "'}";
		});
		cadena += "]";
	
		var lst_pag_ant = eval("(" + cadena + ")");
		return lst_pag_ant;
		
	} catch(e) {
		return [];
	}
}

var btnAtras_click = function(){
	document.frmRegistroMetas.action="rqnpauc.htm?action=modificarRqnp";
	//dojo.byId("action").value = "modificarRqnp";
	//dojo.byId("frmRegistroMetas").submit();
	document.frmRegistroMetas.submit();
};

var btnOK_click = function(){
	var formDlg = dijit.byId("formDialogEnvio");
	formDlg.hide();
};



function formatear(){
	dojo.byId("txtMontoAcumulado").value =addCommas(dojo.byId("txtMontoAcumulado").value);
}

dojo.addOnLoad(function() {	
	dijit.byId('btnGuardar').setAttribute("disabled", false);
	dijit.byId('btnSalir').setAttribute("disabled", false);
	formatear();

	if(dojo.byId("txtvisor").value=="0" ){
		divShow("divbtnGuardar");
		//divShow("divbtnEnviar");
		divShow("divbtnAtras");
		divShow("divbtnSiguiente"); //AGREGADO DPORRASC
	}
	divShow("divbtnSalir");
	var grid = dijit.byId('gridDetalle');
	grid.scrollToRow(1);
});

</script>

</head>

<body class="tundra container-fluid">
		<form id="frmRegistroMetas" name="frmRegistroMetas"	action="rqnpaucmetas.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="action" name="action" 	value="registrarRqnpDetalle" /> 
			<input type="hidden" id="jupdate" name="jupdate" value="0" /> 
			<input type="hidden" id="jload" name="jload" value="0" /> 
			<input type="hidden" id="jcodigo_dep" name="jcodigo_dep" value="<c:out value='${mapRqnp.codigoDependencia}'/>" /> 
			<input type="hidden" id="janio_ejec" name="janio_ejec" value="<c:out value='${mapRqnp.anioProceso}'/>" /> 
			<input type="hidden" id="jcodigo_bien" name="jcodigo_bien" value="<c:out value='${mapRqnp.jcodigo_bien}'/>" /> 
			<input type="hidden" id="jprecio_unid" name="jprecio_unid" value="<c:out value='${mapRqnp.jprecio_unid}'/>" /> 
			<input type="hidden" id="jcodigo_bien_old" name="jcodigo_bien_old" value="<c:out value='${mapRqnp.jcodigo_bien}'/>" /> 
			<input type="hidden" id="jprecio_unid_old" name="jprecio_unid_old" value="<c:out value='${mapRqnp.jprecio_unid}'/>" /> 
			<input type="hidden" id="txtCodigoBien" name="txtCodigoBien" value="<c:out value='${mapRqnp.codigoRqnp}'/>" /> 
			<input type="hidden" id="txtNum_reg" name="txtNum_reg" value="<c:out value='${mapRqnp.codigoRqnp}'/>" /> 
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${mapRqnp.codigoRqnp}'/>" /> 
			<input type="hidden" id="txtvisor" name="txtvisor" value="<c:out value='${visor}'/>" />
			<input type="hidden" id="txtCod_objeto" name="txtCod_objeto" value="<c:out value='${mapRqnp.cod_objeto}'/>" />
			
			<!--<input type="hidden" name="ind_estado" value="0"/>-->
			<!-- INICIO: DPORRASC -->
			<input type="hidden" id="txtNum_UUOO" name="txtNum_UUOO" value="<c:out value='${mapRqnp.codigoUuoo}'/>" /> 
			<input type="hidden" id="jsecuFunc" name="jsecuFunc" value="" /> 
				<input type="hidden" id="jcodigo_rqnp" name="jcodigo_rqnp" value="" /> 
				<input type="hidden" id="jcod_ambito" name="jcod_ambito" value="<c:out value='${mapRqnp.jcod_ambito}'/>" /> 
				<input type="hidden" id="jcod_proy" name="jcod_proy" value="" /> 
				<input type="hidden" id="jcod_prod" name="jcod_prod" value="" /> 
				<input type="hidden" id="a_txtCantidad" name="a_txtCantidad" value="" /> 
				<input type="hidden" id="a_txtBien" name="a_txtBien"	value="" /> 
				<input type="hidden" id="a_txtMonto" 	name="a_txtMonto" value="" /> 
				<input type="hidden" id="a_txtSecuencia" 	name="a_txtSecuencia" value="" /> 
				<input type="hidden" id="txtCod_plaza" name="txtCod_plaza" value="" /> 
				<input type="hidden" id="jParametroAuc" name="jParametroAuc" value="" />

			<!-- FIN: DPORRASC -->

			<table width="95%" height="240px" cellpadding="0" cellspacing="0"
				class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1"
										class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center">
													<fmt:message key="formRegistroAucMetas.titulo" /> </div></td>
										</tr>
									</table></td>
							</tr>

							<tr>
								<td>&nbsp;</td>
							</tr>

							<tr>
								<td class="bgn">
									<div id="divMensaje" align="center"></div></td>
							</tr>
							<tr>
								<td align="center">
									<table width="90%" cellpadding="2" cellspacing="1">

										<tr>
											<td class="bg" align="right"><fmt:message
													key="formRegistro.numRqnp" />
											</td>
											<td class="bg" align="left"><c:out
													value="${mapRqnp.codigoReqNoProgramado}"></c:out> 
													</td>
										</tr>

										<tr>
											<td class="bg" align="right"><fmt:message
													key="formRegistron.responsable" />
											</td>
											<td class="bg" align="left"><c:out
													value="${mapRqnp.codigoResposanble}"></c:out> &nbsp;&nbsp;
												<c:out value="${mapRqnp.resposanble}"></c:out></td>
										</tr>

										<tr>
											<td class="bg" align="right"><fmt:message
													key="formRegistro.uuoo" />
											</td>
											<td class="bg" align="left"><c:out
													value="${mapRqnp.codigoUuoo}"></c:out> &nbsp;&nbsp; <c:out
													value="${mapRqnp.dependencia}"></c:out></td>
										</tr>
										<!-- inicio: dporrasc -->
										<!--
										<tr>
											<td class="bg" align="right">Tipo de AUC:</td>&nbsp;&nbsp;
											<td class="bg" align="left"><c:out
													value='${mapDatos_auc.tipo_auc}'></c:out></td>
										</tr>
										
										<tr>
											<td class="bg" align="right">Codigo Dep:</td>&nbsp;&nbsp;
											<td class="bg" align="left"><c:out
													value='${mapDatos_auc.cod_dep}'></c:out></td>
										</tr>
										<tr>
											<td class="bg" align="right">codigo Plaza:</td>&nbsp;&nbsp;
											<td class="bg" align="left"><c:out
													value='${mapDatos_auc.cod_plaza}'></c:out></td>
										</tr>
										
										<tr>
											<td class="bg" align="right">Nombre dependencia:</td>&nbsp;&nbsp;
											<td class="bg" align="left"><c:out
													value='${mapDatos_auc.nom_dep}'></c:out></td>
										</tr>
										-->
										<!-- fin:dporrasc -->
										<tr>
											<td class="bg" align="right"><fmt:message
													key="formRegistro.Monto" />
											</td>
											<td class="bg" align="left"><input
												id="txtMontoAcumulado" name="txtMontoAcumulado"
												value="<c:out value='${mapRqnp.montoRqnp}'></c:out>"
												style="width: 80px; text-align: right"
												dojoType="dijit.form.ValidationTextBox" maxlength="50"
												readonly="readonly" /></td>
										</tr>
									</table>
								</td>
							</tr>
							<br/>
							<tr>
								<td>
									<table width="100%" cellpadding="8" cellspacing="2" border="0">
										<tr>
											<td>
												<div id="gridDetalle"></div></td>
										</tr>
									</table></td>
							</tr>
						</table></td>
				</tr>
			</table>
				<center>
				<!-- INICIO: DPORRASC -->
						<table>
							<tr>
								<td align="right">
									<div id="divbtnAdd" display:none; >
										<button id="btnAdd" name="btnAdd" type="button"
											dojoType="dijit.form.Button"
											onclick="btnBuscarMetasPorUUOO_click_popup()" >Agregar Beneficiaria</button>
									</div>
								</td>
								<td>
									<div id="divbtnDel" display:none; >
										<button id="btnDel" name="btnDel" type="button"
											dojoType="dijit.form.Button"
											onclick="btnEliminarMeta_click()">Eliminar Beneficiaria</button>
									</div></td>
								<td>
									<div id="divbtnReplicar1" display:none; >
										<button id="divbtnReplicar1" name="divbtnReplicar1"
											type="button" dojoType="dijit.form.Button"
											onclick="btnReplicarMetas1_popup()">Replicar Beneficiaria
											</button>
									</div>
								</td>
								<td>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td>
									<div>
										<button id="btnDescargarUUOOs" dojoType="dijit.form.Button" type="button"
										onclick="clickDescargaBeneficiariasExcel()" >Descargar plantilla</button>
									</div>
								</td >
								<td>
									<div>
										<button id="btnAgregarBeneficiarias" dojoType="dijit.form.Button" type="button"
										onclick="importarBeneficiarias()" >Importación masiva</button>
									</div>
								</td >
								
									
							</tr>
						</table> 
				<!-- FIN: DPORRASC -->
				</center>
			<table width="95%" height="95%" cellpadding="2" cellspacing="2"
				class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%" border=0>
							<tr>
								<td colspan="2">
									<table width="100%" cellpadding="0" cellspacing="0"
										class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Beneficiarias y Afectación Presupuestal POI</div></td>
										</tr>
									</table></td>
							</tr>

							<tr>
								<td colspan="2">
									<table width="100%" cellpadding="8" cellspacing="2" border="0">
										<tr>
											<td>
												<div id="gridMetas" data-dojo-id="gridMetas"></div></td>
										</tr>
									</table></td>
							</tr>
						</table> 
						<table>
							<tr>
								<td width="4%">
									<div id="divmsgImage" style="display: none;">
										<img name="eje01" src="/a/imagenes/circulorojo.gif" />
									</div></td>
								<td width="96%" align="left">
									<div id="divmsgExceso" style="display: none;">
										<P>El ítem NO cuenta con SALDO para su atención por el canal de Compras Directas. Por lo tanto será atendido por otro canal</P>
									</div></td>
							</tr>
						</table></td>
				</tr>
			</table>
			<table width="98%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
						<table>
							<tr>
								<td>&nbsp;&nbsp;&nbsp;
									<div id="divbtnGuardar" style="display: none;">
										<button id="btnGuardar" name="btnGuardar" type="button"
											dojoType="dijit.form.Button" onclick="btnGuardar_click2">Guardar</button>
									</div></td>

								<%-- 	<td>
								&nbsp;&nbsp;&nbsp;
								<div id="divbtnEnviar" style="display: none;" >
									<button id="btnEnviar" name="btnEnviar" type="button" dojoType="dijit.form.Button" onclick="btnValidaEnviar_click(${mapRqnp.codigoRqnp})">Enviar</button>
								</div>
							</td>  --%>
								<td>&nbsp;&nbsp;&nbsp;
									<div id="divbtnSiguiente" style="display: none;">
										<button id="btnSiguiente" name="btnSiguiente" type="button"
											dojoType="dijit.form.Button"
											onclick="btnValidaEnviar_click(${mapRqnp.codigoRqnp})">Siguiente</button> <!-- onclick="btnSiguiente_aprobar(${mapRqnp.codigoRqnp})" -->
									</div></td>
								<td>&nbsp;&nbsp;&nbsp;
									<div id="divbtnAtras" style="display: none;">
										<button id="btnAtras" name="btnAtras" type="button"
											dojoType="dijit.form.Button" onclick="btnAtras_click">Atrás</button>
									</div></td>
								<td>&nbsp;&nbsp;&nbsp;
									<div id="divbtnSalir" style="display: none;">
										<button id="btnSalir" name="btnSalir" type="button"
											dojoType="dijit.form.Button" onclick="btnSalir_click()">Inicio</button>
									</div>
								<td>
							</tr>
						</table></td>
				</tr>
			</table>


			<!-- EMERGENTE DE DATOS Envio -->
			<div dojoType="dijit.Dialog" style="width: 350px; height: 150px; display: none;" id="formDialogEnvio" title="Procesando el Envío" execute="">
				<table width="100%" height="75%" cellpadding="2" cellspacing="2">
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" width="100%" height="100%"
								border=0>
								<tr>
									<td align="center">
										<table width="100%" cellpadding="2" cellspacing="1">
											<tr>
												<td class="bg" align="center" colspan="2">
													<h5>Se está Procesando el Envío.</h5>
												</td>
											</tr>
										</table></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>

			<!-- DPC: EMERGENTE DE METAS POR UUOO -->
			<div dojoType="dijit.Dialog" style="width: 700px; height: 450px; display: none;" id="formDialogMetasPorUUOO" title="Buscar metas por UUOO" execute="">
				<table width="100%" height="75%" cellpadding="2" cellspacing="2">
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" width="100%" height="100%" border=0>
								<tr>
									<td colspan="2">
										<table width="100%" cellpadding="2" cellspacing="1">
											<tr>
												<td class="T1" colspan="2">
													<div align="center">Buscar metas por UUOO Beneficiaria</div></td>
											</tr>
										</table>
									</td>
								</tr>

								<!-- cabecera de parametros - EMERGENTE  -->
								<tr>
									<td class="bg" align="right">Codigo UUOO:</td>
									<!-- <fmt:message key="formRegistro.cod_contacto" /> -->
									<td class="bg" align="left"><input id="txtNum_UUOOe"
										name="txtNum_UUOOe" maxlength="6"
										dojoType="dijit.form.TextBox"
										style="width: 100px; text-align: left"
										value="<c:out value='${mapRqnp.codigoUuoo}'/>" />(*)
										&nbsp; &nbsp;
										<button id="btnRecuperarUUOO" name="btnRecuperarUUOO"
											type="button" dojoType="dijit.form.Button"
											onclick="btnBuscarProyProdMetas_click();return false;">Recuperar</button>
									</td>
								</tr>

								<tr>
									<td class="bg" align="right">Descripción:</td>
									<!-- <fmt:message key="formRegistro.nom_contacto" /> -->
									<td class="bg" align="left">
										<input id="txtNom_UUOOe" name="txtNom_UUOOe" value="${mapRqnp.nom_uuoo}"
											style="width: 350px; text-align: left"	dojoType="dijit.form.TextBox" 
											maxlength="20"	readonly="readonly" />
									</td>
								</tr>

								<tr>
									<td colspan="2">
										<table width="100%" border="0">
											<tr>
												<td>
													<div id="gridConsulta"></div></td>
											</tr>
										</table></td>
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
										<button id="btnAceptarMetasPorUUOO"
											name="btnAceptarMetasPorUUOO" type="button"
											dojoType="dijit.form.Button"
											onclick="btnAceptarCatalogo_click">Aceptar</button></td>

									<td>&nbsp;&nbsp;&nbsp;
										<button id="btnSalirMetasPorUUOO" name="btnSalirMetasPorUUOO"
											type="button" dojoType="dijit.form.Button"
											onclick="btnSalirMetasPorUUOO_click()">Cancelar</button></td>
								</tr>
							</table></td>
					</tr>
				</table>
			</div>
			<!-- DPC: FIN DE EMERGENTE POR UNIDAD ORGANIZACIONAL -->
		</form>
		
		<jsp:include page="auc/agregarBeneficiaria.jsp" />
	
	<!-- Bootstrap.js -->
    <script src="/a/js/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="/a/js/libs/jquery-ui/1.11.2/js/jquery-ui.min.js"></script>
    <script src="/a/js/libs/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script src="/a/js/libs/bootbox/4.4.0/bootbox.js"></script>
    
    <script src="${contextPathUrl}/js/common/jquery.form.js"></script>
    <script src="${contextPathUrl}/js/common/common.js"></script>
    
    <!-- JS DataTables -->
    <script src="/a/js/libs/bootstrap/3.3.2/plugins/datatables-1.10.7/media/js/jquery.dataTables.min.js"></script>    
    <script src="/a/js/libs/bootstrap/3.3.2/plugins/datatables-1.10.7/extensions/Responsive/js/dataTables.responsive.js"></script>
	<script src="/a/js/libs/bootstrap/3.3.2/plugins/jquery-validation-1.13.1/dist/jquery.validate.min.js" ></script>
	<script src="${contextPathUrl}/js/auc/agregarBeneficiaria.js" ></script>
	
	<!-- jQgrid Grillas -->
 	<script src="/a/js/libs/chosen/1.2.0/chosen.jquery.min.js"></script>
	<script src="/a/js/libs/jquery/plugins/validation/1.13.1/additional-methods.js"></script>
	<script src="/a/js/libs/jqgrid/4.6.0/js/jquery.jqGrid.src.js"></script>
	<script src="/a/js/libs/jqgrid/4.6.0/js/i18n/grid.locale-es.js"></script>
	<script src="/a/js/libs/jstree/3.0.0/jstree.min.js"></script>
</body>


<script type="text/javascript">
	var contextPathUrl = '${contextPathUrl}';
	
	$(document).ready(function() {
		initElementsRegistrarBeneficiaria();
	});

//INICIO:DPORRASC - CODIGO JAVASCRIPT
function btnRecuperarUUOO(){	
	dojo.byId("action").value = "recuperarCodUUOOJson";
	dojo.byId("txtNum_UUOO").value=dojo.byId("txtNum_UUOOe").value;
	
	if(dojo.byId("txtNum_UUOO").value!=""){
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				//INGRESA SI NO EXISTE ERROR EN DEVOLVER DATOS DEL SERVLET
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						
						var data = eval("(" + unescape(response.data) + ")");
							var nombre = data.nom_dep;						
							dojo.byId("txtNom_UUOOe").value = data.nom_dep;
							dojo.byId("txtNum_UUOO").value = data.cod_dep;
							dojo.byId("txtCod_plaza").value = data.cod_plaza;
							
							//INGRESA A BUSCAR METAS-PROD-PROY
							btnBuscarProyProdMetas_click();
					}
				}else{
					//DEVUELVE DATOS EN ERROR (-1) PARA LOS CAMPOS SOLICITADOS
					var data = eval("(" + response.data + ")");
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs){
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				//alert("<fmt:message key='error.ajax'/>");
			},
			form: dojo.byId("frmRegistroMetas")
	};
	dojo.xhrPost(enviar);
	}else{
		dojo.byId("txtNum_UUOO").value = "";
		dojo.byId("txtNom_UUOOe").value = "";
		
		
		var parm =dijit.byId("txtNum_UUOOe");
		dijit.focus(parm.domNode);
		var _grdLstPagAnt= dijit.byId("gridBusqueda");
		
		var storeCatalogo = new dojo.data.ItemFileWriteStore(
				{
					data: { 
						items: []
					}
				}
			);
		
		if (_grdLstPagAnt==null ){
			construirGrid(storeCatalogo);
			
		}else{
			_grdLstPagAnt.destroyRecursive();
			construirGrid(storeCatalogo);
		}
	}
}
	

//VENTANA EMERGENTE PARA BUSCAR UUOO BENEFICIARIAS
//CARGA UNA GRID VACIA LA PRIMERA VEZ
var btnBuscarMetasPorUUOO_click_popup = function(){
	
	var formDlg = dijit.byId("formDialogMetasPorUUOO");
	
	var _grdLstPagAnt= dijit.byId("gridBusqueda");
	
	document.getElementById("txtNum_UUOOe").value="";
	document.getElementById("txtNom_UUOOe").value="";
	
	formDlg.show();
	formDlg.onFocus();
	
	var parm =dijit.byId("txtNum_UUOOe");
	dijit.focus(parm.domNode);
	
	var storeCatalogo = new dojo.data.ItemFileWriteStore(
			{
				data: { 
					items: []
				}
			}
		);
	
	if (_grdLstPagAnt==null ){
		construirGrid(storeCatalogo);
		
	}else{
		_grdLstPagAnt.destroyRecursive();
		construirGrid(storeCatalogo);
	}
}

//INICIO - PRINCIPAL
//DPORRASC: SE EJECUTA DANDO CLICK EN RECUPERAR
function btnBuscarProyProdMetas_click(){
	if(dojo.byId("txtNum_UUOO").value=="-1" || dojo.byId("txtNum_UUOO").value==""){
		alert("Debe ingresar una UUOO válida.");
		var store = new dojo.data.ItemFileWriteStore(
				{
					data: { 
						items: []
					}
				}
			);
		 		var _grdLstPagAnt= dijit.byId("gridBusqueda");
				
				if (_grdLstPagAnt==null){
					construirGrid(store);
				}else{
					_grdLstPagAnt.setStore(store);
				}
		return;
	}else{ //INICIO DE PRIMER ELSE
	
		dojo.byId("action").value = "recuperarProyProdAccionJsonAUC";
		var sflag='0'; //NO AGREGA LA UUOO
		//DATOS DEL ITEM (BIEN-ITEM)
		
		var num_uuoo_benef=dojo.byId("txtNum_UUOO").value;
		var cod_plaza_uuoo_benef=dojo.byId("txtCod_plaza").value;
		var cod_ambito_item=dojo.byId("jcod_ambito").value;
		
		//VALIDACION DE PLAZA Y AMBITO (AUC)
		var cod_plaza_auc="<c:out value='${mapDatos_auc.cod_plaza}'></c:out>";
		var tipo_auc="<c:out value='${mapDatos_auc.tipo_auc}'></c:out>";	
		if(tipo_auc=='T' && cod_ambito_item=='01'){ //PUEDE AGREGAR CUALQUIER UUOO BENEFICIARIA
			var sflag='1'; //AGREGA LA UUOO
			//alert("toda las UUOOs");
		}else{ //DEBE PERTENECER A LA MISMA PLAZA
			if(trim(cod_plaza_uuoo_benef) == trim(cod_plaza_auc)){
				var sflag='1'; //AGREGA LA UUOO
			}else{
				alert("La UUOO beneficiara pertenece a otra jurisdicci\u00F3n, debe pertenecer a la misma juridicci\u00F3n administrativa ");
			}
		}
		
		//AQUI BLANQUENADO GRID
		var val_parametro=dojo.byId("txtNum_UUOO").value
		//var val_parametro=document.getElementById("txtNum_UUOO").value;
		if (val_parametro.length > 2 && sflag=='1' ) {
				var store = new dojo.data.ItemFileWriteStore(
				{
					data: { 
						items: []
					}
				}
			);
		 		var _grdLstPagAnt= dijit.byId("gridBusqueda");
				
				if (_grdLstPagAnt==null){
					construirGrid(store);
				}else{
					_grdLstPagAnt.setStore(store);
				}
				_grdLstPagAnt.showMessage('<div id="preloaderConsulta" align="center"><h2><span>Procesando Consulta...</span></h2></div>');
		
			//llamada AJAX 
			var enviar = {
					handleAs : "json",
					load : function(response, ioArgs) {
						if(response.data.error==null){
							
							if (response.data != "" && response.data != "[]") {
								
								var data = eval("(" + unescape(response.data) + ")");
								
								//alert("(NUEVO PROYECTOS - PROD - ACCION) Proyectos: "+data.listaProyProdAccion);
								actualizarProyProdAccionAUC(data.listaProyProdAccion); // lee la lista del servlet 
							}
						}else{
							alert(response.data.mensaje);
						}
					},
					timeout : 25000,
					error : function(error, ioArgs) {
						alert("error ajax");
						alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
						//alert("<fmt:message key='error.ajax'/>");
					},
					form: dojo.byId("frmRegistroMetas")
			};	
			
			dojo.xhrPost(enviar);
		}else{
			//alert("Plaza UUOO: "+ cod_plaza_uuoo_benef+"- Plaza de AUC: "+cod_plaza_auc +" deben ser iguales" );
			var sflag='0'; //AGREGA LA UUOO
		}
	}//FIN DE PRIMER ELSE
}

function actualizarProyProdAccionAUC(lista){
	//PROYECTO - PRODUCTO - ACCION
	var lsCatalogo =lsCatalogoItems(lista);
	
	var storeCatalogo = new dojo.data.ItemFileWriteStore({
		data: {
			identifier: 'vsecuFunc',
			items: lsCatalogo
		}});

	var _grdLstPagAnt= dijit.byId("gridBusqueda");
	if (_grdLstPagAnt==null){
		
		construirGrid(storeCatalogo);
		
	}else{
		_grdLstPagAnt.setStore(storeCatalogo);
	}
}


function lsCatalogoItems(items){
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
				
			cadena += "{vsecuFunc:'" + item.secuFunc + "', ";	
			cadena += "vnomActividad:'" + item.nomActividad + "', ";
			cadena += "vnomProducto:'" + item.nomProducto+ "', ";
			cadena += "vmetaSiaf:'" + item.metaSiaf+ "', ";		
			cadena += "vnomMeta:'" + item.nomMeta+ "'} ";	
		});
		cadena += "]";
		
		var lst_pag_ant = eval("(" + cadena + ")");
		
		return lst_pag_ant;
		
	} catch(e) {
		alert("Error detectado: " + e.description)
		//alert("Existe problemas en la base de datos, comuniquese con el administrador del SIGA");
		return [];
	}
}

//DPORRASC
//CLICK EN ACEPTAR PARA AGREGAR ITEMS DE BIEN
function btnAceptarCatalogo_click(){
	var grid= dijit.byId("gridBusqueda"); //Grid (Actividad/Producto/Accion)
	
	var items = grid.selection.getSelected();
	
	var val_uuoo= dojo.byId("txtNom_UUOOe").value;
	var val_nomActividad;
	var val_codigoBienM=dojo.byId("jcodigo_bien").value;
	var val_nomProducto;
	var val_nomMeta;
	var val_secuenciaMeta;
	var val_xcantidadTotal;
	var val_cantidadTotal;
	var val_precioUnid=dojo.byId("jprecio_unid").value;
	var val_montoSoles;
	var val_xmontoSoles;
	var val_codigoRqnp=dojo.byId("txtCodigoRqnp").value;
	var val_rqnp="000";
	var val_idMeta;
	var val_anioEjec=dojo.byId("janio_ejec").value;
	
	//DECLARACION DE ARRAYS
	var a_secuencia;// = new Array();
	var a_codigoBien;// = new Array();
	var a_cantidad;// = new Array();
	var a_monto;// = new Array();
	var i=0;

  if(items.length) { //CANTIDAD DE ITEMS QUE FUERON SELECCIONADOS
     
      dojo.forEach(items, function(selectedItem){
          
    	  if(selectedItem !== null){
             
              dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
                 
              	var sw3='0'; //ALERTA DE MISMA AUC QUE ELIGE EL BIEN/ITEM
              	
              	//EN (value), SE ALMACENA VALOR POR VALOR DE CADA COLUMNA
                  var value = grid.store.getValues(selectedItem, attribute);
                  //alert("value: "+value);
                  switch (attribute) { 
                  /* case 'vidMeta': 
                	  val_idMeta=value;
                     break ; */
                  case 'vsecuFunc': 
                	  val_secuenciaMeta=value;
                     break ;
                  case 'vnomActividad': 
                	  val_nomActividad=value;
                     break ;
                  case 'vnomProducto': 
                	  val_nomProducto=value;
                     break ;
                  case 'vnomMeta': 
                	  val_nomMeta=value;
                      break ;
                  }
	         }); 
	     } 

          /// Verifica si el bien ya fue agregado
          var gridMetas = dijit.byId('gridMetas');
          var sw='0';
          var sw2='0';
          var val_name_auc_aux='';
          if(gridMetas.store !=null){
      		if((gridMetas.store._arrayOfAllItems.length > 0 )){ 
      			gridMetas.store.fetch({
      				onItem: function(item, request){
      					if (item.vsecuenciaMeta.toString() ==val_secuenciaMeta.toString() ){
      						sw='1';
      					}
      					/* if (item.vauct1.toString() !=val_vauct1.toString() ){
      						sw2='1';
      						val_name_auc_aux=item.vauct_name.toString()	
      					} */
      		 		}
      			});
      		}
      	}
			
          // Verifica si el bien ya fue agregado

          if (sw=='0'){
          	if (sw2=='0'){
	         	a_secuencia= val_secuenciaMeta;
	         	a_codigoBien=val_codigoBienM;
	         	a_cantidad=0;
	         	a_monto=0;
	         	
	         	//PASAR PARAMETROS ARRAY PARA REGISTRO EN BD DE POPUP METAS
	         	dojo.byId("a_txtCantidad").value= "0";
	         	dojo.byId("a_txtBien").value= "000";
	         	dojo.byId("a_txtMonto").value= "0";
	         	dojo.byId("a_txtSecuencia").value= val_secuenciaMeta;
	         
	        	registrarMetasRqnpPopup(dojo.byId("a_txtSecuencia").value,dojo.byId("a_txtBien").value,dojo.byId("a_txtCantidad").value,dojo.byId("a_txtMonto").value); 
	         	
	         	
          	}else{
          		alert("El Bien/Servicio debe Pertenecer a la AUC: "+ val_name_auc_aux+". Para Registrar este ítem formule un Requerimiento adicional.");
          	}
       	}else{
       		alert("La Meta para la UUOO seleccionada ya fue Agregado");
       	} 
      }); 
      	
  		//RECUPERA LAS METAS DEL BIEN
      //recuperarMetasAUC(val_codigoRqnp,a_codigoBien);
  }
 
  var formDlg = dijit.byId("formDialogMetasPorUUOO");
		formDlg.hide();
}


//FIN		: ACTUALIZAR_META
//CONSTRIUR GRID UBG-PROYECTOS
function construirGrid(store){
/* 	cadena += "{vsecuFunc:'" + item.codiProy + "', ";	
	cadena += "vanioEjec:'" + item.ubg + "', ";
	cadena += "vcodiDep:'" + item.desc_bien + "', ";
	cadena += "vcodiMeta:'" + item.desc_unidad + "', ";
	cadena += "vcodiProd:'" + item.tipo_bien+ "', ";
	cadena += "vcodiProy:'" + item.codigo_unidad + "', ";
	cadena += "vnomActividad:'" + item.saldo + "', ";
	cadena += "vnomProducto:'" + item.precio_bien + "', ";	
	cadena += "vnomMeta:'" + item.precio_bien + "'} ";	 */
	var layout = [
         /* {
       	     name: 'ID META', //NOMBRE QUE MOSTRARA LA COLUMNA (COMANDA)
       	  	 field: 'vidMeta', //NOMBRE DEL CAMPO DE REGISTRO DE DATOS PARA MOSTRAR
       	     width: '15%', //ANCHO QUE TENDRA LA COLUMNA
       	     headerStyles: "text-align: left", //STYLE DE TEXTO DE LA COLUMNA
       	  	 styles:'text-align:left;font-size:12;font-family: Tahoma, Geneva, sans-serif;'
       	 }, */
       	 {
       	     name: ' ', //NOMBRE QUE MOSTRARA LA COLUMNA (COMANDA)
       	  	 field: 'vsecuFunc', //NOMBRE DEL CAMPO DE REGISTRO DE DATOS PARA MOSTRAR
       	     width: '0%', //ANCHO QUE TENDRA LA COLUMNA
       	     headerStyles: "text-align: left", //STYLE DE TEXTO DE LA COLUMNA
       	  	 styles:'text-align:left;font-size:12;font-family: Tahoma, Geneva, sans-serif;'
       	 },{
       	     name: 'ACTIVIDAD', //NOMBRE QUE MOSTRARA LA COLUMNA (COMANDA)
       	  	 field: 'vnomActividad', //NOMBRE DEL CAMPO DE REGISTRO DE DATOS PARA MOSTRAR
       	     width: '25%', //ANCHO QUE TENDRA LA COLUMNA
       	     headerStyles: "text-align: left", //STYLE DE TEXTO DE LA COLUMNA
       	  	 styles:'text-align:left;font-size:12;font-family: Tahoma, Geneva, sans-serif;'
       	 },{
      	     name: 'PRODUCTOS', //NOMBRE QUE MOSTRARA LA COLUMNA (COMANDA)
      	  	 field: 'vnomProducto', //NOMBRE DEL CAMPO DE REGISTRO DE DATOS PARA MOSTRAR
      	     width: '25%', //ANCHO QUE TENDRA LA COLUMNA
      	     headerStyles: "text-align: left", //STYLE DE TEXTO DE LA COLUMNA
      	  	 styles:'text-align:left;font-size:12;font-family: Tahoma, Geneva, sans-serif;'
      	 },{
      	     name: 'ACCIONES', //NOMBRE QUE MOSTRARA LA COLUMNA (COMANDA)
      	  	 field: 'vnomMeta', //NOMBRE DEL CAMPO DE REGISTRO DE DATOS PARA MOSTRAR
      	     width: '25%', //ANCHO QUE TENDRA LA COLUMNA
      	     headerStyles: "text-align: left", //STYLE DE TEXTO DE LA COLUMNA
      	  	 styles:'text-align:left;font-size:12;font-family: Tahoma, Geneva, sans-serif;'
      	 },{
           	name: 'META SIAF ',
           	field: 'vmetaSiaf',
           	width: '25%',
           	headerStyles: "text-align: center",
           	styles:'text-align:left;'
         }
      	 
   	];
	
	var grid = new dojox.grid.EnhancedGrid({
		id:'gridBusqueda',
		store: store,
		rowSelector: '20px',
		structure: layout,
		escapeHTMLInData: false,
		rowsPerPage: 20,
		rowCount: 20,
		selectionMode:'multiple',
		canSort: function(col){ return true; } ,
		style: { height:"300px", width:"98%", left:"1%" } ,
		errorMessage: "Ha ocurrido un error al procesar la consulta",
		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta METAS...</span></div>',
		noDataMessage: "No se encontraron registros",
		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
		plugins: {
			 pagination: {
				pageSizes: ["20", "40", "80", "120", "All"],
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


 function btnSalirMetasPorUUOO_click(){
	//DPORRASC
	var formDlgUUOO = dijit.byId("formDialogMetasPorUUOO");
	formDlgUUOO.hide(); 
}


function importarBeneficiarias(){
	$("#formModalAgregarBeneficiaria").modal("show");
	$("#btnGrabarBeneficiaria").prop("disabled",true);
	$("#btnBorrarCarga").prop("disabled",true);
	$("#btnAceptarCarga").prop("disabled",false);
	limpiarInputs();
};

 
//ENVIAR A SERVLET PARA DERIVAR A APROBACION
var btnSiguiente_aprobar = function(cod_req){
	dojo.byId("action").value = "recuperarRqnpDetalleAUC";
	dojo.byId("txtCodigoRqnp").value =cod_req;
	dojo.byId("jParametroAuc").value ="formAucRqnpAtenderAUC";
	
	dijit.byId('frmRegistroMetas').submit();
}

//ELIMINAR METAS AGREGADAS DEL POPUP
function btnEliminarMeta_click(){
	var grid = dijit.byId('gridMetas');
	var codigo_bien;
	var codigo_rqnp;
	
	//VARIABLES PARA ELIMINAR META
	var anio_ejec;
	var secu_func;
	
	var sw_del='0';
	var items = grid.selection.getSelected();
	var value;
	if (confirm("Confirme si desea Eliminar el Registro de la META")){
		if(items.length){
		    dojo.forEach(items, function(selectedItem){
		        if(selectedItem !== null){
		        	 dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
		                   if(attribute=='vsecuenciaMeta') { 
		                	   value = grid.store.getValues(selectedItem, attribute);
		                	   secu_func=value;
		                   }
		                   if(attribute=='vcodigoBienM') { 
		                	   value = grid.store.getValues(selectedItem, attribute);
		                	   codigo_bien=value;
		                   }
		                   if(attribute=='vcodigoRqnpM') { 
		                	   value = grid.store.getValues(selectedItem, attribute);
		                	   codigo_rqnp=value;
		                   }
		                   if(attribute=='vanioEjec') { 
		                	   value = grid.store.getValues(selectedItem, attribute);
		                	   anio_ejec=value;
		                   }
		        	 }); 
		        	 if(secu_func!="" && codigo_bien!="" && codigo_rqnp!="" && anio_ejec!=""){
		        		 sw_del='1';
		        	 }else{
		        		 alert("No ha seleccionado un item a eliminar ...");
		        		 return;
		        	 }
		        		 
		        	 if (sw_del=='1'){
		        		 eliminarMetaRqnp(codigo_rqnp,codigo_bien,anio_ejec,secu_func);
		        	 }
		            grid.store.deleteItem(selectedItem);
		        } 
		    }); 
		} 
	}
}

//FUNCION DE ELIMINAR META QUE LLAMA AL SERVLET
function eliminarMetaRqnp(cod_rqnp,cod_bien,anio_ejec,secu_func){
	dojo.byId("action").value = "eliminarMetaRqnpJson";
	dojo.byId("jcodigo_rqnp").value = cod_rqnp;
	dojo.byId("jcodigo_bien").value = cod_bien;
	dojo.byId("janio_ejec").value = anio_ejec;
	dojo.byId("jsecuFunc").value = secu_func;
	//llamada AJAX
	var enviar = {
		handleAs : "json",
		load : function(response, ioArgs) {
			if(response.data.error==null){
				if (response.data != "" && response.data != "[]") {
					var data = eval("(" + response.data + ")");
					var monto = data.grandMontoTotal;
					dojo.byId("txtMontoAcumulado").value = addCommas(monto); 
					onApplyCellEditMetasFinal();
				}
			}else{
				alert(response.data.mensaje);
			}
		},
		timeout : 25000,
		error : function(error, ioArgs) {
			alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
		},
		form: dojo.byId("frmRegistroMetas")
	};	
	dojo.xhrPost(enviar);
}
 
//FUNCION DE REGISTRAR METAS DESDE EL POPRP QUE LLAMA AL SERVLET
function registrarMetasRqnpPopup(a_secuencia,a_codigoBien,a_cantidad,a_monto){
			dojo.byId("action").value = "registrarRqnpMetasPopup";
			
			dojo.byId("a_txtCantidad").value = a_cantidad;
			dojo.byId("a_txtBien").value = a_codigoBien;
			dojo.byId("a_txtMonto").value = a_monto;
			dojo.byId("a_txtSecuencia").value = a_secuencia;
			
			//llamada AJAX
			var enviar = {
				handleAs : "json",
				load : function(response, ioArgs) {
					if(response.data.error==null){
						if (response.data != "" && response.data != "[]") {
							var data = eval("(" + response.data + ")");
							actualizarMetas(data.listaMetas); 
						}
					}else{
						alert(response.data.mensaje);
					}
				},
				timeout : 25000,
				error : function(error, ioArgs) {
					//alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				},
				form: dojo.byId("frmRegistroMetas")
			};	
			dojo.xhrPost(enviar); 
	}

function btnReplicarMetas1_popup(){
	
	alert("Usted procedera a replicar las metas a todo item del mismo ámbito.");
	
	dojo.byId("action").value = "replicarRqnpMetasPopup";
	
	dojo.byId("jcodigo_bien").value ;
	//dojo.byId("a_txtBien").value = a_codigoBien;
	dojo.byId("txtCodigoRqnp").value;
	
/* 	alert("codigo bien:"+dojo.byId("jcodigo_bien").value);
	alert("codigo rqnp:"+dojo.byId("txtCodigoRqnp").value); */
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						var data = eval("(" + response.data + ")");
						//alert("antes de REPLICAR REPLICAR...(DPORRASC)");
						//actualizarMetas(data.listaMetas); 
					}
				}else{
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				//alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
			},
			form: dojo.byId("frmRegistroMetas")
	};	
	dojo.xhrPost(enviar); 
	alert("Se ha replicado las Unidades Beneficiarias de la misma plaza, a todos los items del mismo ambito.");
}


function clickDescargaBeneficiariasExcel(e){
	var txtCodigoRqnp = getValueInputText("txtCodigoRqnp");
	var codAmbito = dojo.byId("jcod_ambito").value;
	
	//var params = $('#formConsultaDocumentosFirma').serialize();
	//$(location).prop( 'href', contextPathUrl + '/rqnpaucmetas.htm?action=exportarExcelBeneficiarias&txtNumDocumento=' + txtNumDocumento+'&txtTipoDocumento='+txtTipoDocumento);
	$(location).prop( 'href', contextPathUrl + '/rqnpaucmetas.htm?action=exportarExcelBeneficiarias&codAmbito='+codAmbito);
}


function onApplyCellEditMetasFinal(){
	if (dojo.byId("jload").value='1') {
		var grid = dijit.byId('gridMetas');
		var mont_total= parseFloat(0);
		var valor ;
		
		// SUMANDO METAS
		var val_montoTotalItem =parseFloat(0);
		var val_cantidadTotalItem=parseFloat(0); 
		var val_saldo=parseFloat(0);
		var val_exceso=parseFloat(0);
		if(	grid.store !=null){
      		if((grid.store._arrayOfAllItems.length > 0 )){
      			grid.store.fetch({
      				onItem: function(item, request){
      					val_montoTotalItem =val_montoTotalItem + parseFloat( item.vmontoSoles);
      					val_cantidadTotalItem =val_cantidadTotalItem + parseFloat( item.vcantidadTotal);
       		 		}
       			});
       		}
       	}
		//**********************************************/
    	//SETEANDO DATOS DETALLE----------------------------------------------------------------
 		var grid_bienes = dijit.byId('gridDetalle');
 		var items_bienes =grid_bienes.selection.getSelected();
 		var flagMtoTotal=true;
 		//**********************************************/
 			if(items_bienes.length){
 				dojo.forEach(items_bienes, function(selectedItem_bien){
 					if(selectedItem_bien !== null){
 						dojo.forEach(grid_bienes.store.getAttributes(selectedItem_bien), function(attribute){
 							if(attribute=='vcantBien') { 
 								grid_bienes.store.setValue(selectedItem_bien, 'vcantBien',addCommas(val_cantidadTotalItem.toString()));
 							}
 							if(attribute=='vprecioTotal') { 
 								grid_bienes.store.setValue(selectedItem_bien, 'vprecioTotal',addCommas(val_montoTotalItem.toString()));
 							}
     							
 							if(attribute=='vexceso') { 
 								val_saldo = parseFloat( grid_bienes.store.getValue(selectedItem_bien, 'vsaldo'));
								val_exceso =  val_saldo - val_montoTotalItem;
 	     						if ( val_exceso <0){
 	     							val_exceso=val_exceso * (-1);
 	     							
 	     						}else if ( val_exceso >0) {
 	     							val_exceso=0;
 	     						}
 	     						val_exceso= redondear2decimales(val_exceso);
 	     						
 	     						val_exceso=val_exceso.toFixed(2);
 								grid_bienes.store.setValue(selectedItem_bien, 'vexceso',addCommas(val_exceso.toString()));
 							}
 						}); 
	 				} 
 				}); 
 			}
 			//grid_bienes.store.save();
 			// sumando gran total--------------------------------------------------------
 			var val_grand_total=0;
 			if(	grid_bienes.store !=null){
        		if((grid_bienes.store._arrayOfAllItems.length > 0 )){
        			grid_bienes.store.fetch({
        				onItem: function(item, request){
        					val_grand_total =val_grand_total + parseFloat( removeCommas(item.vprecioTotal));
        		 		}
        			});
        		}
        	} 
 		 return valor;
	} // fin (jload)
}

</script>

</html>


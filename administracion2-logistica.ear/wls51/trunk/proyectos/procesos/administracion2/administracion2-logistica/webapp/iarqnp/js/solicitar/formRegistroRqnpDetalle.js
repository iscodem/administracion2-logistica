var configRqnpDetalle = null;

function initElementsRegistroRqnpDetalle(){
	debugger;
	setInitElementsRegistroRqnpDetalle();
	setDatosMensajeriaContainer(datosMensajeriaRqnpDetalle); //Setea los mensajes al js generico
	setDatosMensajeriaContainer(datosMensajeriaCatalogo); 
	setInitRqnpDetalleTable();
	//setInitElementsRqnpDetalle();
	evaluaBotones();
}

function setInitElementsRegistroRqnpDetalle(){
	addEventElement("btnAgregarItemRqnpDetalle", "click", clickBtnAgregarItemRqnpDetalle );
	addEventElement("btnEliminarItemRqnpDetalle", "click", clickBtnEliminarItemRqnpDetalle);
	addEventElement("btnGuardarItemRqnpDetalle", "click", clickBtnGuardarItemRqnpDetalle);
	addEventElement("btnSiguienteRqnpDetalle", "click", clickBtnSiguienteRqnpDetalle);
	addEventElement("btnInicioRqnpDetalle", "click", clickBtnInicioRqnpDetalle);

	addEventElement("btnRqnpCabModificar", "click", clickBtnRqnpCabModificar);
}

function clickBtnRqnpCabModificar(){
	showModalElement("divModalPopupRegistroRqnpCabMod"); 
}


function evaluaBotones(){
	var tableRqnpDetalle = $( '#tblRqnpDetalle' );
	var allRowsRqnpDetalle = tableRqnpDetalle.jqGrid('getGridParam','data');
	debugger;
	if(allRowsRqnpDetalle.length>0 ){
		if(comparaRqnpDetalleAgregadoYGuardado()){
			disabledElement("btnGuardarItemRqnpDetalle");
			enabledElement("btnSiguienteRqnpDetalle");
			enabledElement("btnEliminarItemRqnpDetalle");
		}else{
			enabledElement("btnGuardarItemRqnpDetalle");
			disabledElement("btnSiguienteRqnpDetalle");
			enabledElement("btnEliminarItemRqnpDetalle");
		}
	}else{
		disabledElement("btnGuardarItemRqnpDetalle");
		disabledElement("btnSiguienteRqnpDetalle");
		disabledElement("btnEliminarItemRqnpDetalle");
	}
}


function clickBtnAgregarItemRqnpDetalle(){
	//Inicio del popup emergente de busqueda de catalogo, se debe enviar el div contenedor.
	//initBusquedaCatalogo(); 
	setInitCatalogoTable();
	showModalElement("divBuscarCatalogo");
}


function clickBtnEliminarItemRqnpDetalle(){
	var tableRqnpDetalle = $( '#tblRqnpDetalle' );
	var rowId = tableRqnpDetalle.jqGrid("getGridParam", "selrow");
	if (rowId == null) {
		showMensajeGenerico(rqnpDetalleDatos.mensajes.seleccionarItemParaEliminarItem);
	}
	else {
		showMensajeConfirmGenerico(rqnpDetalleDatos.mensajes.solicitaConfirmacionEliminarItem
				, callAceptarEliminarItemRqnpDetalle
				,callCancelarEliminarItemRqnpDetalle);
	}
}

function callAceptarEliminarItemRqnpDetalle(){
	var tableRqnpDetalle = $( '#tblRqnpDetalle' );
	var rowId = tableRqnpDetalle.jqGrid("getGridParam", "selrow");
	if (rowId != null) {
		var rowData = tableRqnpDetalle.getRowData(rowId);
		eliminarItemRqnpDetalle(rowData);
	}
	evaluaBotones();
}

function callCancelarEliminarItemRqnpDetalle(){
	
}

/////////////////////////////////////
function eliminarItemRqnpDetalle (rowData){
	debugger;
	var codigoBien=$.trim(rowData["codigoBien"]);
	var codigoRqnp=$.trim(rowData["codigoRqnp"]);
	
	if(codigoRqnp!=null){
		var dataJson ={'codigoBien':codigoBien, 'codigoRqnp':rqnpDetalleDatos.codigoRqnp};
		$.ajax({
			url: contextPathUrl + "/rqnpdetalle.htm?action=eliminarBienRqnpJson",
			data: dataJson,
			type: "post",
			dataType: "json",
			cache: false,
			async: true,
			beforeSend: function() {
				showModalElement("divScreenBlockRqnpDetalle");
			},
			complete: function() {
				//$("#divMensajeConfirmacionComprobante").modal("hide");
				hideModalElement("divScreenBlockRqnpDetalle");
			},
			success: function(result) {	
				var lsRqnpBienesJSON = result.listaBienes;
				rqnpDetalleDatos.rqnpDetalleList = lsRqnpBienesJSON == ''? [] : $.parseJSON(lsRqnpBienesJSON);
				if(rqnpDetalleDatos.rqnpDetalleList!=null){
					console.log("Excelente");
					showMensajeGenerico(rqnpDetalleDatos.mensajes.exitoItemEliminado);
					//$('#divModalPopupRqnpDetalle').modal('show');
					fillGrilla( '#tblRqnpDetalle', getRqnpDetalleList() );
					evaluaBotones();
					console.log("Fin de botones");
				}
			},
			error: function() {
				showMensajeError("Error - Comuniquese con el administrador del sistema.",null);
			}
		});
	}else{
		tableRqnpDetalle.jqGrid('delRowData', rowId);
	}
}

function clickBtnGuardarItemRqnpDetalle(){
	var arrayListaRqnpDetalle = obtenerArrayListaRqnpDetalle();
	
	$.ajax({
		url: contextPathUrl + "/rqnpdetalle.htm?action=registrarRqnpDetalle",
		data: {
			"codigoRqnp": rqnpDetalleDatos.codigoRqnp,
			"listaRqnpDetalle": JSON.stringify(arrayListaRqnpDetalle)
		},
		type: "post",
		dataType: "json",
		cache: false,
		async: true,
		beforeSend: function() {
			showModalElement("divScreenBlockRqnpDetalle");
		},
		complete: function() {
			//$("#divMensajeConfirmacionComprobante").modal("hide");
			hideModalElement("divScreenBlockRqnpDetalle");
		},
		success: function(result) {	
			var lsRqnpBienesJSON = result.listaBienes;
			rqnpDetalleDatos.rqnpDetalleList = lsRqnpBienesJSON == ''? [] : $.parseJSON(lsRqnpBienesJSON);
			if(rqnpDetalleDatos.rqnpDetalleList!=null){
				console.log("Excelente");
				showMensajeGenerico(rqnpDetalleDatos.mensajes.exitoItemGrabado);
				//$('#divModalPopupRqnpDetalle').modal('show');
				fillGrilla( '#tblRqnpDetalle', getRqnpDetalleList() );
				evaluaBotones();
				console.log("Fin de botones");
			}
		},
		error: function() {
			showMensajeError("Error - Comuniquese con el administrador del sistema.",null);
		}
	});
}



function clickBtnSiguienteRqnpDetalle(){
	var tableRqnpDetalle = $("#tblRqnpDetalle");
	var allRowsRqnpDetalle = tableRqnpDetalle.jqGrid('getGridParam','data');
	if (allRowsRqnpDetalle.length<=0 ) {
		showMensajeGenerico("Por favor, para proceder debe ingresar \u00EDtems al requerimiento.");
	}else{
		debugger;
		setValueInputText('hidCodigoRqnpMetas',rqnpDetalleDatos.codigoRqnp);
		clickSendDataMethodPost('formRegistroRqnpMetas');
	}
}

function clickBtnInicioRqnpDetalle(){
	clickSendDataMethodPost('formInicioBandejaRqnp');
}


function setInitRqnpDetalleTable(){
	
	var table = $( '#tblRqnpDetalle' );
	var heightJqGrid = 350;
	if (table) {
		var tableDiv = $("#divRqnpDetalleTable");
		var widthTable = tableDiv.width();
		table.jqGrid({
			width: widthTable,
			//height: 'auto',
			height: heightJqGrid,
			minHeight: 150,
			autowidth: true,
			datatype: "local",
			cmTemplate: {sortable: false},
			rowList:[20,50,100],
			rowNum: 20, 
			colNames: ["C&oacute;digo","codigoRqnp","Adjunto","Bienes y Servicios","Unid. Med.","codAuc","AUC","Precio Unit.","Saldo","Exceso","Alerta","Archivo","Entrega","Clasificador","Desc. Clasificador","tipoBien","codigoUnidad","tipoRuta","estadoDesconcentrado","cantBien"],
			//Los nombres de columnas "name" son los mismos que el nombre del Bean en Java y el "index" es del objeto que rellena la grilla
			colModel: [
				{name: "codigoBien", index: "codigoBien", width: (1*widthTable/12), align: "center"},
				{name: "codigoRqnp", index: "codigoRqnp", width: (1*widthTable/12), align: "center", hidden:true},
				{name: "nro_adjuntos", index: "nro_adjuntos", width: (0.5*widthTable/12), align: "center", 
					formatter: function(cellvalue, options, rowObject) {
						var cellValue = parseFloat(rowObject.nro_adjuntos);
						var textDisplay='-';
					    if(!isNullOrUndefined(cellValue)){
					    	if(cellValue==0){
					    		textDisplay='-';
					    	}else{
					    		textDisplay = '<img name="adj01" src="/a/imagenes/adjunto.jpg"/>'
					    	}
					    }
					    return textDisplay;
					} 
				},
				{name: "desBien", index: "desBien", width: (3*widthTable/12), align: "left"},
				{name: "desUnid", index: "desUnid", width: (0.8*widthTable/12), align: "center"},
				{name: "auct1", index: "auct1", width: (0*widthTable/12), align: "center", hidden:true},
				{name: "auct_name", index: "auct_name", width: (0*widthTable/12), align: "center", hidden:true},
				{name: "precioUnid", index: "precioUnid", width: (0.8*widthTable/12), align: "center",
					formatter: function(cellvalue, options, rowObject) {
					    var cellValue = parseFloat(rowObject.precioUnid).toFixed(2);
					    if(!isNullOrUndefined(cellValue)){
					    	return cellValue;
					    }
					}
				},
				{name: "saldo", index: "saldo", width: (0.8*widthTable/12), align: "center",
					formatter: function(cellvalue, options, rowObject) {
					    var cellValue = parseFloat(rowObject.saldo).toFixed(2);
					    if(!isNullOrUndefined(cellValue)){
					    	return cellValue;
					    }	
					}
				},
				{name: "exceso", index: "exceso", width: (0.8*widthTable/12), align: "center",
					formatter: function(cellvalue, options, rowObject) {
					    var cellValue = parseFloat(rowObject.exceso).toFixed(2);
					    if(!isNullOrUndefined(cellValue)){
					    	return cellValue;
					    }	
					}
				},
				{name: "alerta", index: "alerta", width: (0.5*widthTable/12), align: "center", 
					formatter: function(cellvalue, options, rowObject) {
					    var cellValue = rowObject.alerta;
					    var textDisplay=' ';
					    if(!isNullOrUndefined(cellValue)){
					    	var  indImagen = parseFloat(cellValue);
						    if (indImagen == 0){
						    	textDisplay = '<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
						    }
					    }
					    
					    return textDisplay;
					}
				},
				{name: "archivo", index: "archivo", width: (0.8*widthTable/12), align: "center", 
					formatter: function(cellvalue, options, rowObject) {
					    var cellValue = rowObject.codigoBien;
					    if(!isNullOrUndefined(cellValue)){
					    	var textDisplay='Archivo';
					        return "<a class=\"jqGridGeneralLinkClass\" href=\"javascript:enviarRqnp('"+cellValue+"')\">"+textDisplay+"</a>";
					    }else{
					    	return '';
					    }
					}
				},
				{name: "entrega", index: "entrega", width: (0.8*widthTable/12), align: "center", 
					formatter: function(cellvalue, options, rowObject) {
					    var cellValue = rowObject.codigoBien;
					    if(!isNullOrUndefined(cellValue)){
					    	var textDisplay='Entrega';
					        return "<a class=\"jqGridGeneralLinkClass\" href=\"javascript:enviarRqnp('"+cellValue+"')\">"+textDisplay+"</a>";
					    }else{
					    	return '';
					    }
					}
				},
				{name: "codClasificador", index: "codClasificador", width: (1*widthTable/12), align: "center"	},
				{name: "desClasificador", index: "desClasificador", width: (1.2*widthTable/12), align: "left"	},
				{name: "tipo_bien", index: "tipo_bien", width: (0*widthTable/12), align: "center", hidden:true	},
				{name: "codigoUnidad", index: "codigoUnidad", width: (0*widthTable/12), align: "center"	, hidden:true},
				{name: "tipo_ruta", index: "tipo_ruta", width: (0*widthTable/12), align: "center", hidden:true	},
				{name: "estadoDesconcentrado", index: "estadoDesconcentrado", width: (0*widthTable/12), align: "center", hidden:true	},
				//{name: "imagen", index: "imagen", width: (0*widthTable/12), align: "center", hidden:true	},
				{name: "cantBien", index: "cantBien", width: (0*widthTable/12), align: "center", hidden:true	}
				
			],
			pager : "#divRqnpDetallePagerTable",
			loadui: "disable"
		});

		// actualizar lista de asignaciones
		fillGrilla( '#tblRqnpDetalle', getRqnpDetalleList() );
	}
	resizeTableSiga("tblRqnpDetalle");
	triggerResizeEvent();
}



function getRqnpDetalleList() {
	if ( !isNullOrUndefined( configRqnpDetalle ) ) return null;
	return rqnpDetalleDatos.rqnpDetalleList;
}


function obtenerArrayListaRqnpDetalle(){
	var tableRqnpDetalle = $('#tblRqnpDetalle' );
	var allRowsRqnpDetalle = tableRqnpDetalle.jqGrid('getGridParam','data');
	var arrayListaRqnpDetalle = new Array();
	for (i in allRowsRqnpDetalle){
		var beanRqnpDetalle= {};
		
		beanRqnpDetalle={
				codigoRqnp		: rqnpDetalleDatos.codigoRqnp,
				codigoBien		: allRowsRqnpDetalle[i].codigoBien,
				codigoUnidad	: allRowsRqnpDetalle[i].codigoUnidad,
				cantBien		: allRowsRqnpDetalle[i].cantBien,
				tipo_ruta		: allRowsRqnpDetalle[i].tipo_ruta,
				codClasificador	: allRowsRqnpDetalle[i].codClasificador,
				auct1			: allRowsRqnpDetalle[i].auct1,
				precioUnid		: allRowsRqnpDetalle[i].precioUnid,
				saldo			: allRowsRqnpDetalle[i].saldo,
				nro_adjuntos	: allRowsRqnpDetalle[i].nro_adjuntos,
				exceso			: allRowsRqnpDetalle[i].exceso,
				tipo_bien		: allRowsRqnpDetalle[i].tipo_bien,
				estadoDesconcentrado:allRowsRqnpDetalle[i].estadoDesconcentrado
		}
		
		arrayListaRqnpDetalle.push(beanRqnpDetalle);
	}
	
	return arrayListaRqnpDetalle;
}


//Funcion de inicio despues de seleccionado los items de catalogo
//Es llamado desde busqueda de catalogo
function procederRegistroItemsSeleccionados(arrayDatosCatalogoSeleccionados){
	var tableRqnpDetalle = $("#tblRqnpDetalle");
	if (arrayDatosCatalogoSeleccionados.length>0 ) {
		//Devuelve el array de items seleccionado con los datos depurados.
		var dataRowCatalogoSeleccionados = setArrayDatosCatalogoSeleccionado(arrayDatosCatalogoSeleccionados); 
		for(var i=0; i<dataRowCatalogoSeleccionados.length;i++){
			console.log("indice: "+i+" - "+dataRowCatalogoSeleccionados[i])
			returnValidaAgregarItem = validaAgregarItem(dataRowCatalogoSeleccionados[i]);
			if(returnValidaAgregarItem.flag){
				//showModalElement('divModalPopupCatalogo');
				dataRowCatalogoSeleccionados.splice(i, 1);
				showMensajeGenerico(returnValidaAgregarItem.mensaje);
				break;
			}else{
				tableRqnpDetalle.jqGrid("addRowData", dataRowCatalogoSeleccionados[i].secuencialRqnpDetalle, dataRowCatalogoSeleccionados[i]);
				console.log("grabado: "+i);
			}
		}
		
		// Ocultar div de mensaje de error
		setHtmlElement('etiquetaErrorBuscarCatalogo', '');
		hideElement('divErrorBuscarCatalogo')
		
		if(dataRowCatalogoSeleccionados.length!=0 ){
			
			if(!returnValidaAgregarItem.flag){
				//tableRqnpDetalle.jqGrid("addRowData", dataRowCatalogoSeleccionados.secuencialRqnpDetalle, dataRowCatalogoSeleccionados);
				hideModalElement("divBuscarCatalogo"); 
			}else{
				//tableRqnpDetalle.jqGrid("addRowData", dataRowCatalogoSeleccionados.secuencialRqnpDetalle, dataRowCatalogoSeleccionados);
				showModalElement("divBuscarCatalogo"); 
			}
		}
	}
	evaluaBotones();
}


function setArrayDatosCatalogoSeleccionado(arrayDatosCatalogoSeleccionados){
	dataRowCatalogoSeleccionados = new Array();
	//Temporales
	for(i in arrayDatosCatalogoSeleccionados){
		var dataRowCatalogo=new Object();
		//anio_pro, cod_plaza, cod_bien
		var saldo = $.trim(arrayDatosCatalogoSeleccionados[i]["saldo"]);
		var exceso = $.trim(arrayDatosCatalogoSeleccionados[i]["exceso"]);
		var codigoBien = $.trim(arrayDatosCatalogoSeleccionados[i]["codigoBien"]);
		var nro_adjuntos = $.trim(arrayDatosCatalogoSeleccionados[i]["nro_adjuntos"]);
		var precioUnid = $.trim(arrayDatosCatalogoSeleccionados[i]["precioUnid"]);
		var saldoActual=parseFloat(saldo).toFixed(2);
		var excesoActual=parseFloat(exceso).toFixed(2);
		var precioUnidActual = parseFloat(precioUnid).toFixed(2);
		var nroAdjuntosActual = parseFloat(0).toFixed(2);
		dataRowCatalogo = {
				secuencialRqnpDetalle: i+"_"+codigoBien,
				codigoBien		: $.trim(arrayDatosCatalogoSeleccionados[i]["codigoBien"]),
				codigoRqnp		: "",
				nro_adjuntos	: nroAdjuntosActual,
				desBien			: $.trim(arrayDatosCatalogoSeleccionados[i]["desBien"]),
				desUnid			: $.trim(arrayDatosCatalogoSeleccionados[i]["desUnid"]),
				auct1			: $.trim(arrayDatosCatalogoSeleccionados[i]["auct1"]),
				auct_name		: $.trim(arrayDatosCatalogoSeleccionados[i]["auct_name"]),
				precioUnid		: precioUnidActual,
				saldo			: saldoActual,
				exceso			: excesoActual,
				alerta			: saldoActual,
				archivo			: "", ///No en Bean
				entrega			: "", ///No en Bean
				codClasificador	: $.trim(arrayDatosCatalogoSeleccionados[i]["codClasificador"]),
				desClasificador	: $.trim(arrayDatosCatalogoSeleccionados[i]["desClasificador"]),
				
				tipo_bien		: $.trim(arrayDatosCatalogoSeleccionados[i]["tipo_bien"]),
				codigoUnidad	: $.trim(arrayDatosCatalogoSeleccionados[i]["codigoUnidad"]),
				tipo_ruta		: $.trim(arrayDatosCatalogoSeleccionados[i]["tipo_ruta"]),
				estadoDesconcentrado:$.trim(arrayDatosCatalogoSeleccionados[i]["estadoDesconcentrado"]),
				cantBien		: 0,
		}
		
		dataRowCatalogoSeleccionados.push(dataRowCatalogo);
	}
	return dataRowCatalogoSeleccionados;
}


function validaAgregarItem(dataRowCatalogo){
	var respuesta = {flag:false, mensaje:""};
	var tableRqnpDetalle = $( '#tblRqnpDetalle' );
	//var allRowsRqnpDetalle = tableRqnpDetalle.getGridParam('data');
	var allRowsRqnpDetalle = tableRqnpDetalle.jqGrid('getGridParam','data');
	var tableCatalogoSelCodigoBien =dataRowCatalogo.codigoBien;
	var tableCatalogoSelAuct1 = dataRowCatalogo.auct1;
	var tableCatalogoSelTipoRuta=dataRowCatalogo.tipo_ruta;
	var tableCatalogoSelAuctName=dataRowCatalogo.auct_name;
	var tableCatalogoSelPrecioBien=parseFloat(dataRowCatalogo.precioUnid);
		if (tableCatalogoSelPrecioBien == 0){
			respuesta.mensaje="El precio unitario del Bien/Servicio seleccionado: "+tableCatalogoSelCodigoBien+" debe ser mayor que cero.";
			respuesta.flag=true;
		}else if(tableCatalogoSelTipoRuta=='03'){
			respuesta.mensaje="El Bien/Servicio seleccionado: "+tableCatalogoSelCodigoBien+" s\u00F3lo puede ser solicitado por la UUOO "+ tableRqnpDetalleAuctName+"." ;
			respuesta.flag=true;
		}else if(allRowsRqnpDetalle.length != 0) {
			for (var i=0; i <allRowsRqnpDetalle.length;i++){
				var tableRqnpDetalleCodigoBien = allRowsRqnpDetalle[i].codigoBien;
				var tableRqnpDetalleAuct1 = allRowsRqnpDetalle[i].auct1;
				var tableRqnpDetalleTipoRuta=allRowsRqnpDetalle[i].tipo_ruta;
				var tableRqnpDetalleAuctName=allRowsRqnpDetalle[i].auct_name;
				var tableRqnpDetallePrecioBien=parseFloat(allRowsRqnpDetalle[i].precioUnid);
				if( tableCatalogoSelCodigoBien == tableRqnpDetalleCodigoBien){
					//respuesta.mensaje=errorMessageRqnpDetalleDatos.itemYaFueAgregado;
					respuesta.mensaje="El item seleccionado de c\u00F3digo: "+tableCatalogoSelCodigoBien+"  ya fue agregado al requerimiento.";
					respuesta.flag=true;
					break;
				}else if (tableCatalogoSelAuct1 != tableRqnpDetalleAuct1){
					respuesta.mensaje="El Bien/Servicio seleccionado de c\u00F3digo: "+tableCatalogoSelCodigoBien+" debe Pertenecer a la UUOO: "+ tableRqnpDetalleAuctName+". Para registrar este \u00EDtem formule un Requerimiento adicional.";
					respuesta.flag=true;
					break;
				}
			}
		}else{
			respuesta.flag=false;
			respuesta.mensaje="OK";
		}
	
	return respuesta;
}


//Operacion con funciones. Si son iguales devuelve true
function comparaRqnpDetalleAgregadoYGuardado(){
	var rpta=false;
	var m=0;
	debugger;
	var allRowsRqnpDetalleTable = $("#tblRqnpDetalle").jqGrid('getGridParam','data');
	var allRowsRqnpDetalleBD = rqnpDetalleDatos.rqnpDetalleList;
	
	if(allRowsRqnpDetalleBD.length == allRowsRqnpDetalleTable.length){
		for(var i=0; i<allRowsRqnpDetalleTable.length;i++){
			for(var j=0;j<allRowsRqnpDetalleBD.length;j++){
				if(allRowsRqnpDetalleTable[i].codigoBien==allRowsRqnpDetalleBD[j].codigoBien){
					rpta = true; //item encontrado
					m++;
				}
			}
		}
	}
	
	if(allRowsRqnpDetalleBD.length == m && m!=0){
		rpta=true
	}else{
		rpta=false;
	}
	
	return rpta;
}
	

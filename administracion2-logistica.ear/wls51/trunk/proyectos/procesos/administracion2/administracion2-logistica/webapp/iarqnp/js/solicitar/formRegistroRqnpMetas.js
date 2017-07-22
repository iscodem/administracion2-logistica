var configRqnpMetas = null;

function initElementsRegistroRqnpMetas(){
	debugger;
	setInitElementsRegistroRqnpMetas();
	setInitRqnpDetalleTable();
	setInitRqnpMetasTable();
	//setInitElementsRqnpDetalle();
	evaluaBotones();
}

function setInitElementsRegistroRqnpMetas(){
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
	alert("siguiente");
	var tableRqnpDetalle = $("#tblRqnpDetalle");
	var allRowsRqnpDetalle = tableRqnpDetalle.jqGrid('getGridParam','data');
	if (allRowsRqnpDetalle.length<=0 ) {
		showMensajeGenerico("Por favor, para proceder debe ingresar \u00EDtems al requerimiento.");
	}else{
		setValueInputText('hidCodigoRqnp',rqnpDetalleDatos.codigoRqnp);
		clickSendDataMethodPost('formRegistroRqnpMetas');
	}
}

function clickBtnInicioRqnpDetalle(){
	clickSendDataMethodPost('formInicioBandejaRqnp');
}


function setInitRqnpDetalleTable(){
	
	var table = $( '#tblRqnpDetalle' );
	var heightJqGrid = 150;
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
	if ( !isNullOrUndefined( configRqnpMetas ) ) return null;
	return rqnpMetasDatos.rqnpDetalleList;
}

function setInitRqnpMetasTable(){
	
	var table = $( '#tblRqnpMetas' );
	var heightJqGrid = 150;
	if (table) {
		var tableDiv = $("#divRqnpMetasTable");
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
			pager : "#divRqnpMetasPagerTable",
			loadui: "disable"
		});

		// actualizar lista de asignaciones
		fillGrilla( '#tblRqnpMetas', getRqnpMetasList() );
	}
	resizeTableSiga("tblRqnpMetas");
	triggerResizeEvent();
}


function getRqnpMetasList() {
	if ( !isNullOrUndefined( configRqnpMetas ) ) return null;
	return rqnpMetasItemDatos.rqnpMetasList;
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


/*
 * Funciones de trabajo de catalogo. 
 */
var valParametro = null;
//var dataBusquedaCatalogo = new Object();
var dataBusquedaCatalogo = new Object();

//Inicia la busqueda del catalogo
function initBusquedaCatalogo(){
	debugger;
	setInitParametrosElementsBuscarCatalogo();
	setInitCatalogoTable();
	setInitElementsBuscarCatalogo();
	//showModalElement("divBuscarCatalogo");
	hideElement(dataBusquedaCatalogo.idDivError);
}

function setInitElementsBuscarCatalogo(){
	//addEventElement("txtParametroCatalogo", "keypress", txtParametroCatalogoKeypress);
	addEventElement("btnBuscarCatalogo", "click", clickBtnBuscarCatalogo);
	addEventElement("btnAceptarCatalogo", "click", clickBtnAceptarCatalogo);
	addEventElement("btnCancelarCatalogo", "click", clickBtnCancelarCatalogo);
	setValueInputText("txtParametroCatalogo","");
}

$( "#txtParametroCatalogo" ).keydown(function( e ) {
	if ( e.which == 13 ) {
		e.preventDefault();
		clickBtnBuscarCatalogo();
	}
});


function OnChangeRadioCatalogo(){
	debugger;
	hideElement(dataBusquedaCatalogo.idDivError);
	dataBusquedaCatalogo.txtParametroCatalogo="";
	setValueInputText("txtParametroCatalogo","");
	$('#tblCatalogo').clearGridData();
}

function setInitParametrosElementsBuscarCatalogo() {
	debugger;
	dataBusquedaCatalogo.idDivLoading = "divLoadingBuscarCatalogo";
	dataBusquedaCatalogo.idDivError = "divErrorBuscarCatalogo";	
	dataBusquedaCatalogo.idEtiquetaError = "etiquetaErrorBuscarCatalogo";
	dataBusquedaCatalogo.idDivScreenBlock = "divScreenBlock";
	dataBusquedaCatalogo.tipoConsultaCatalogo="";
	dataBusquedaCatalogo.txtParametroCatalogo="";
}


function setInitCatalogoTable(){
	setValueInputText("txtParametroCatalogo","");
	var table = $( '#tblCatalogo' );
	var heightJqGrid = 200;
	debugger;
		if (table) {
			var catalogoTableDiv = $("#divCatalogoTable");
			var widthTable = catalogoTableDiv.width();
			//var widthWindow= $( window ).width() - 100;
			//var factorRedimensionamiento = Number(parseFloat(getValueStyleElementBySelector(".sigaModalContainerMedium ", ".sigaModalContainerMedium", "width"))/100);
			//var widthTable = factorRedimensionamiento*catalogoTableDiv.width();
			debugger;
			table.jqGrid({
				width: widthTable,
				//height: 'auto',
				height: heightJqGrid,
				minHeight: 150,
				autowidth: true,
				datatype: "local",
				cmTemplate: {sortable: false},
				multiselect: true,
				rowList:[20,50,100],
				rowNum: 20, 
				colNames: ["C&oacute;digo","Bienes y Servicios","Unid. Med.","&Aacute;rea Usuaria / &Aacute;rea T&eacute;cnica","Precio",
				           "TipoBien","codigoUnidad","saldo","codClasificador","desClasificador","auct1","auct_name","tipo_ruta","exceso","estadoDesconcentrado"],
				colModel: [
					{name: "codigoBien", index: "codigo_bien", width: (2*widthTable/12), align: "center"},
					{name: "desBien", index: "desc_bien", width: (4*widthTable/12), align: "left"},
					{name: "desUnid", index: "desc_unidad", width: (1*widthTable/12), align: "center"},
					{name: "auct_name", index: "auct_name", width: (4*widthTable/12), align: "left"},
					{name: "precioUnid", index: "precio_bien", width: (1*widthTable/12), align: "center"},
					
					{name: "tipo_bien", index: "tipo_bien", width: (0*widthTable/12), align: "center", hidden:true},
					{name: "codigoUnidad", index: "codigo_unidad", width: (0*widthTable/12), align: "center", hidden:true},
					{name: "saldo", index: "saldo", width: (0*widthTable/12), align: "center", hidden:true},
					{name: "codClasificador", index: "codigo_gasto", width: (0*widthTable/12), align: "center", hidden:true},
					{name: "desClasificador", index: "desc_gasto", width: (0*widthTable/12), align: "center", hidden:true},
					{name: "auct1", index: "auct1", width: (0*widthTable/12), align: "center", hidden:true},
					{name: "auct_name", index: "auct_name", width: (0*widthTable/12), align: "center", hidden:true},
					{name: "tipo_ruta", index: "tipo_ruta", width: (0*widthTable/12), align: "center", hidden:true},
					{name: "exceso", index: "exceso", width: (0*widthTable/12), align: "center", hidden:true},
					{name: "estadoDesconcentrado", index: "estadoDesconcentrado", width: (0*widthTable/12), align: "center", hidden:true}
				],
				pager : "#divCatalogoPagerTable",
				loadui: "disable"
			});
			// actualizar lista de asignaciones
			fillGrilla( '#tblCatalogo', getCatalogoList() );
		}
		resizeTableSiga("tblCatalogo");
		triggerResizeEvent();
}

function getCatalogoList() {
	if ( !isNullOrUndefined( valParametro ) ) return null;
	return catalogoDatos.catalogoList;
}
	

function showMessageErrorBuscarCatalogo(errorMessage) {
	debugger;
	setHtmlElement(dataBusquedaCatalogo.idEtiquetaError, errorMessage);
	showElement(dataBusquedaCatalogo.idDivError);
}


function clickBtnBuscarCatalogo() {
	dataBusquedaCatalogo.tipoConsultaCatalogo=getValueOptionButtonElementForClass('.buscarCatalogoPor');
	dataBusquedaCatalogo.txtParametroCatalogo=getUpperCaseValueInputText("txtParametroCatalogo");
	debugger;
	if (typeof dataBusquedaCatalogo != "undefined") {
		hideElement(dataBusquedaCatalogo.idDivError);
		if (dataBusquedaCatalogo.tipoConsultaCatalogo == "") {
			showMessageErrorBuscarCatalogo(errorMessageBuscarCatalogo.seleccionarTipoBusqueda);
		}
		else if (dataBusquedaCatalogo.txtParametroCatalogo == "" || trimText(dataBusquedaCatalogo.txtParametroCatalogo) == "") {
			showMessageErrorBuscarCatalogo(errorMessageBuscarCatalogo.completarDescripcion);
		}
		else if (dataBusquedaCatalogo.txtParametroCatalogo.length < 2) {
			showMessageErrorBuscarCatalogo(errorMessageBuscarCatalogo.cantidadMinimaDescripcion);
		}else if(!patronParametroBusquedaCatalogo.test(dataBusquedaCatalogo.txtParametroCatalogo)){
			showMessageErrorBuscarCatalogo(errorMessageBuscarCatalogo.caracteresEspeciales);
		}
		else {
			var catalogoTable = $("#tblCatalogo");
			catalogoTable.clearGridData();
			setHtmlElement("divCatalogoPagerTable_left", "");
			disabledElement("btnAceptarCatalogo");
			$.ajax({
				url: contextPathUrl + "/catalogo.htm?action=buscarCatalogoJson",
				type: "post",
				dataType: "json",
				cache: false,
				data: {
					"tipoBuscaCatalogo": dataBusquedaCatalogo.tipoConsultaCatalogo,
					"parmBuscaCatalogo": dataBusquedaCatalogo.txtParametroCatalogo
				},
				beforeSend: function() {
					if (typeof dataBusquedaCatalogo.idDivScreenBlock != "undefined") {
						showModalElement(dataBusquedaCatalogo.idDivScreenBlock);
					}
					showElement("divLoadingBuscarCatalogo");
				},
				complete: function() {
					hideElement("divLoadingBuscarCatalogo");
					enabledElement("btnBuscarTodosCatalogo");
					if (typeof dataBusquedaCatalogo.idDivScreenBlock != "undefined") {
						hideModalElement(dataBusquedaCatalogo.idDivScreenBlock);
					}
				},
				success: function(result) {
					var catalogoList = result.listCatalogo;
					debugger;
					if (catalogoList != null && catalogoList.length > 0) {
						consoleLog("catalogoList.length: " + catalogoList.length);
						for (var i = 0; i < catalogoList.length; i++) {
							var catalogo = catalogoList[i];
							var datarow = Object.create;
							//if(catalogo.codigoEstado == "1"){
								datarow = {
									secuencialCatalogo: i + "_" + catalogo.codigo_bien,
									codigoBien: catalogo.codigo_bien,
									desBien: catalogo.desc_bien,
									desUnid: catalogo.desc_unidad,
									tipo_bien: catalogo.tipo_bien,
									codigoUnidad: catalogo.codigo_unidad,
									saldo: catalogo.saldo,
									precioUnid: catalogo.precio_bien,
									codClasificador: catalogo.codigo_gasto,
									desClasificador: catalogo.desc_gasto,
									auct1: catalogo.auct1,
									auct_name: catalogo.auct_name,
									tipo_ruta: catalogo.tipo_ruta,
									exceso: catalogo.exceso,
									estadoDesconcentrado: catalogo.estadoDesconcentrado
								};								
								catalogoTable.jqGrid("addRowData", datarow.secuencialCatalogo, datarow);
							//}
						}
						catalogoTable.trigger("reloadGrid");
						enabledElement("btnAceptarCatalogo");
					}
					else {
						setHtmlElement("divCatalogoPagerTable_left", errorMessageBuscarCatalogo.sinRegistrosBusqueda);
					}
				},
				error: function() {
					setHtmlElement("divCatalogoPagerTable_left", "Error callObtenerCatalogo");
				}
			});
		}
	}
}


function clickBtnAceptarCatalogo() {
	var catalogoTable = $("#tblCatalogo");
	
	var arrayDatosCatalogoSeleccionados=new Array()
	var arraySecuenciasSeleccionadosCatalogo=catalogoTable.jqGrid('getGridParam','selarrrow');
	var returnValidaAgregarItem = {};
	//var rowId = catalogoTable.jqGrid("getGridParam", "selrow");
	if (arraySecuenciasSeleccionadosCatalogo.length<=0) {
		//setHtmlElement("divCatalogoPagerTable_left", errorMessageBuscarCatalogo.seleccionarRegistro);
		showMessageErrorBuscarCatalogo(errorMessageBuscarCatalogo.seleccionarRegistro);
	}
	else {
		for(item in arraySecuenciasSeleccionadosCatalogo){
			var rowId=arraySecuenciasSeleccionadosCatalogo[item];
			var rowData = catalogoTable.getRowData(rowId);
			arrayDatosCatalogoSeleccionados.push(rowData)
		}
		
		//Se hace el llamado al componente que lo llamó
		procederRegistroItemsSeleccionados(arrayDatosCatalogoSeleccionados);
	}
}


function clickBtnCancelarCatalogo() {
	debugger;
	$("#divBuscarCatalogo").modal("hide");
	//removeEventElementsBuscarCatalogo();
	//removeCatalogoTableBuscarCatalogo();
}


function removeEventElementsBuscarCatalogo() {
	removeAllEventsElement("btnBuscarCatalogo");
	removeAllEventsElement("btnAceptarCatalogo");
	removeAllEventsElement("btnCancelarCatalogo");
	dataBusquedaCatalogo={};
}

function removeCatalogoTableBuscarCatalogo() {
	var htmlElement = "<table id=\"tblCatalogo\"></table>";
	htmlElement += "<div id=\"divCatalogoPagerTable\"></div>";
	setHtmlElement("divCatalogoTable", htmlElement);
}

/*
 * Funciones de trabajo de colaborador. 
 */
var valParametro = null;

//Inicia la busqueda del colaborador
function initBusquedaColaborador(){
	setInitParametrosElementsBuscarColaborador();
	setInitColaboradorTable();
	setInitElementsBuscarColaborador();
	showModalElement("divBuscarColaborador");
	hideElement(dataBusquedaColaborador.idDivError);
}

function setInitElementsBuscarColaborador(){
	//addEventElement("txtParametroColaborador", "keypress", txtParametroColaboradorKeypress);
	addEventElement("btnBuscarColaborador", "click", clickBtnBuscarColaborador);
	addEventElement("btnAceptarColaborador", "click", clickBtnAceptarColaborador);
	addEventElement("btnCancelarColaborador", "click", clickBtnCancelarColaborador);
	setValueInputText("txtParametroColaborador","");
}

$( "#txtParametroColaborador" ).keydown(function( e ) {
	if ( e.which == 13 ) {
		e.preventDefault();
		clickBtnBuscarColaborador();
	}
});


function OnChangeRadioPersona(){
	debugger;
	hideElement(dataBusquedaColaborador.idDivError);
	dataBusquedaColaborador.txtParametroColaborador="";
	setValueInputText("txtParametroColaborador","");
	$('#tblColaborador').clearGridData();
}

function setInitParametrosElementsBuscarColaborador() {
	dataBusquedaColaborador.idDivLoading = "divLoadingBuscarColaborador";
	dataBusquedaColaborador.idDivError = "divErrorBuscarColaborador";	
	dataBusquedaColaborador.idEtiquetaError = "etiquetaErrorBuscarColaborador";
	dataBusquedaColaborador.idDivScreenBlock = "divScreenBlock";
	dataBusquedaColaborador.tipoConsultaColaborador="";
	dataBusquedaColaborador.txtParametroColaborador="";
}


function setInitColaboradorTable(){
		var table = $( '#tblColaborador' );
		var heightJqGrid = 200;
		if (table) {
			var colaboradorTableDiv = $("#divColaboradorTable");
			var widthTable=colaboradorTableDiv.width();
			//var widthWindow= $( window ).width() - 100;
			//var factorRedimensionamiento = Number(parseFloat(getValueStyleElementBySelector(".sigaModalContainerMedium ", ".sigaModalContainerMedium", "width"))/100);
			//var widthTable = factorRedimensionamiento*colaboradorTableDiv.width();
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
				colNames: ["N&deg; Reg.","Apellidos y Nombres","Unidad Organizacional","C&oacute;digo Empleado","C&oacute;digo Dependencia"],
				colModel: [
					{name: "numeroRegistro", index: "numeroRegistro", width: (1*widthTable/12), align: "center"},
					{name: "nombreCompleto", index: "nombreCompleto", width: (4*widthTable/12), align: "left"},
					{name: "uuooDetalle", index: "uuooDetalle", width: (7*widthTable/12), align: "left"},
					{name: "codigoEmpleado", index: "codigoEmpleado", width: (0*widthTable/12), align: "center", hidden:true},
					{name: "codigoDependencia", index: "codigoDependencia", width: (0*widthTable/12), align: "center", hidden:true}
				],
				pager : "#divColaboradorPagerTable",
				loadui: "disable"
			});
			// actualizar lista de asignaciones
			fillGrilla( '#tblColaborador', getColaboradorList() );
		}
		resizeTableSiga("tblColaborador");
		triggerResizeEvent();
}

function getColaboradorList() {
	if ( !isNullOrUndefined( valParametro ) ) return null;
	return colaboradorDatos.colaboradorList;
}
	

function showMessageErrorBuscarColaborador(errorMessage) {
	debugger;
	setHtmlElement(dataBusquedaColaborador.idEtiquetaError, errorMessage);
	showElement(dataBusquedaColaborador.idDivError);
}


function clickBtnBuscarColaborador() {
	dataBusquedaColaborador.tipoConsultaColaborador=getValueOptionButtonElementForClass('.buscarPor');
	dataBusquedaColaborador.txtParametroColaborador=getUpperCaseValueInputText("txtParametroColaborador");
	if (typeof dataBusquedaColaborador != "undefined") {
		hideElement(dataBusquedaColaborador.idDivError);
		if (dataBusquedaColaborador.tipoConsultaColaborador == "") {
			showMessageErrorBuscarColaborador(errorMessageBuscarColaborador.seleccionarTipoBusqueda);
		}
		else if (dataBusquedaColaborador.txtParametroColaborador == "" || trimText(dataBusquedaColaborador.txtParametroColaborador) == "") {
			showMessageErrorBuscarColaborador(errorMessageBuscarColaborador.completarDescripcion);
		}
		else if (dataBusquedaColaborador.txtParametroColaborador.length < 2) {
			showMessageErrorBuscarColaborador(errorMessageBuscarColaborador.cantidadMinimaDescripcion);
		}else if(!patronParametroBusquedaColaborador.test(dataBusquedaColaborador.txtParametroColaborador)){
			showMessageErrorBuscarColaborador(errorMessageBuscarColaborador.caracteresEspeciales);
		}
		else {
			var colaboradorTable = $("#tblColaborador");
			colaboradorTable.clearGridData();
			setHtmlElement("divColaboradorPagerTable_left", "");
			disabledElement("btnAceptarColaborador");
			$.ajax({
				url: contextPathUrl + "/colaboradores.htm?action=buscarColaboradores",
				type: "post",
				dataType: "json",
				cache: false,
				data: {
					"tipoBuscaColaborador": dataBusquedaColaborador.tipoConsultaColaborador,
					"parmBuscaColaborador": dataBusquedaColaborador.txtParametroColaborador
				},
				beforeSend: function() {
					if (typeof dataBusquedaColaborador.idDivScreenBlock != "undefined") {
						showModalElement(dataBusquedaColaborador.idDivScreenBlock);
					}
					showElement("divLoadingBuscarColaborador");
				},
				complete: function() {
					hideElement("divLoadingBuscarColaborador");
					enabledElement("btnBuscarTodosColaborador");
					if (typeof dataBusquedaColaborador.idDivScreenBlock != "undefined") {
						hideModalElement(dataBusquedaColaborador.idDivScreenBlock);
					}
				},
				success: function(result) {
					var colaboradorList = result.listColaboradores;
					if (colaboradorList != null && colaboradorList.length > 0){
						consoleLog("colaboradorList.length: " + colaboradorList.length);
						for (var i = 0; i < colaboradorList.length; i++) {
							var colaborador = colaboradorList[i];
							
							if(colaborador.codigoEstado == "1"){
								var datarow = Object.create;
								datarow = {
									secuencialNumeroRegistro: i + "_" + colaborador.numero_registro,
									numeroRegistro: colaborador.numero_registro,
									nombreCompleto: colaborador.nombre_completo,
									uuooDetalle: colaborador.uuoo +" - "+colaborador.dependencia,
									codigoEmpleado: colaborador.codigoEmpleado,
									codigoDependencia: colaborador.codigoDependencia
								};								
								colaboradorTable.jqGrid("addRowData", datarow.secuencialNumeroRegistro, datarow);
							}
						}
						colaboradorTable.trigger("reloadGrid");
						enabledElement("btnAceptarColaborador");
					}
					else {
						setHtmlElement("divColaboradorPagerTable_left", errorMessageBuscarColaborador.sinRegistrosBusqueda);
					}
				},
				error: function() {
					setHtmlElement("divColaboradorPagerTable_left", "Error callObtenerColaborador");
				}
			});
		}
	}
}


function clickBtnAceptarColaborador() {
	debugger;
	var colaboradorTable = $("#tblColaborador");
	var rowId = colaboradorTable.jqGrid("getGridParam", "selrow");
	if (rowId == null) {
		//setHtmlElement("divColaboradorPagerTable_left", errorMessageBuscarColaborador.seleccionarRegistro);
		showMessageErrorBuscarColaborador(errorMessageBuscarColaborador.seleccionarRegistro);
	}
	else {
		var rowData = colaboradorTable.getRowData(rowId);
		dataBusquedaColaborador.codEmpleadoSel=$.trim(rowData["codigoEmpleado"]);
		
		if (typeof dataBusquedaColaborador.codEmpleadoSel != "") {
			setColaboradorSeleccionado(rowData); //Esta función pertenece al componente que invocó la ventana de busqueda de colaboradores.
			// Ocultar div de mensaje de error
			setHtmlElement('etiquetaErrorBuscarColaborador', '');
			hideElement('divErrorBuscarColaborador')
		}
		
		removeEventElementsBuscarColaborador();
		removeColaboradorTableBuscarColaborador();
		$("#divBuscarColaborador").modal("hide");
	}
}


function clickBtnCancelarColaborador() {
	debugger;
	removeEventElementsBuscarColaborador();
	removeColaboradorTableBuscarColaborador();
	$("#divBuscarColaborador").modal("hide");
}


function removeEventElementsBuscarColaborador() {
	removeAllEventsElement("btnBuscarColaborador");
	removeAllEventsElement("btnAceptarColaborador");
	removeAllEventsElement("btnCancelarColaborador");
	dataBusquedaColaborador={};
}

function removeColaboradorTableBuscarColaborador() {
	var htmlElement = "<table id=\"tblColaborador\"></table>";
	htmlElement += "<div id=\"divColaboradorPagerTable\"></div>";
	setHtmlElement("divColaboradorTable", htmlElement);
}
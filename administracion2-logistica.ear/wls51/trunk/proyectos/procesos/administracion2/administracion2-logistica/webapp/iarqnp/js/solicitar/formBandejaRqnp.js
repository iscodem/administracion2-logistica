var configRqnp = null;

function initElementsBandejaRqnp(poseeMeta){
	debugger;
	setInitRequerimientosTable();	
	setInitElementsBandejaRqnp();
	
	if(poseeMeta=='N'){
		disabledElement("btnNuevoRqnpCab");
	}else{
		hideElement('mensaje-sin-meta');
	}
}

function setInitElementsBandejaRqnp(){
	addEventElement("btnNuevoRqnpCab", "click", clickBtnNuevoRqnpCab);
	addEventElement("btnModalPopupAnularRqnpAceptar", "click", clickBtnModalPopupAnularRqnpAceptar);
	addEventElement("btnModalPopupAnularRqnpCancelar", "click", clickBtnModalPopupAnularRqnpCancelar);
	debugger;
}

function setInitRequerimientosTable() {
	var table = $( '#tblRqnp' );
	debugger;
	if (table) {
		var tableDiv = $("#divRqnpTable");
		var widthTable = tableDiv.width();
		table.jqGrid({
			width: widthTable,
			//height: 'auto',
			height: 350,
			minHeight: 150,
			autowidth: true,
			datatype: "local",
			cmTemplate: {sortable: false},
			rowList:[20,50,100],
			rowNum: 20, 
			scrollOffset: 0, 
			colNames: ["N&deg; Requerimiento","codigoRqnp","Fecha","Solicitante","Motivo","Estado","Modificar","Enviar","Anular"],
			colModel: [
				{name: "codigoReqNoProgramado", index: "codigoReqNoProgramado", width: (1.5*widthTable/12), align: "center"},
				{name: "codigoRqnp", index: "codigoRqnp", width: (0*widthTable/12), align: "center", hidden:true},
				{name: "fechaRqnp", index: "fechaRqnp", width: (1.5*widthTable/12), align: "center"},
				{name: "nombreSolicitante", index: "nombreSolicitante", width: (0*widthTable/12), align: "center", hidden:true},
				{name: "motivoSolicitud", index: "motivoSolicitud", width: (3*widthTable/12), align: "left"},
				{name: "nombreEstado", index: "nombreEstado", width: (1.5*widthTable/12), align: "center"},
				{name: "codigoRqnp", index: "codigoRqnp", width: (1.5*widthTable/12), align: "center", 
					formatter: function(cellvalue, options, rowObject) {
					    var cellValue = rowObject.codigoRqnp;
					    if(!isNullOrUndefined(cellValue)){
					    	var textDisplay='Modificar';
					        return "<a class=\"jqGridGeneralLinkClass\" href=\"javascript:modificarRqnp('"+cellValue+"')\">"+textDisplay+"</a>";
					    }else{
					    	return '';
					    }
					} 
				},
				{name: "codigoRqnp", index: "codigoRqnp", width: (1.5*widthTable/12), align: "center", 
					formatter: function(cellvalue, options, rowObject) {
					    var cellValue = rowObject.codigoRqnp;
					    if(!isNullOrUndefined(cellValue)){
					    	var textDisplay='Enviar';
					        return "<a class=\"jqGridGeneralLinkClass\" href=\"javascript:enviarRqnp('"+cellValue+"')\">"+textDisplay+"</a>";
					    }else{
					    	return '';
					    }
					}
				},
				{name: "codigoRqnp", index: "codigoRqnp", width: (1.5*widthTable/12), align: "center", 
					formatter: function(cellvalue, options, rowObject) {
					    var cellValue = rowObject.codigoRqnp;
					    if(!isNullOrUndefined(cellValue)){
					    	var textDisplay='Anular';
					        return "<a class=\"jqGridGeneralLinkClass\" href=\"javascript:anularRqnp('"+cellValue+"')\">"+textDisplay+"</a>";
					    }else{
					    	return '';
					    }
					}
				}
			],
			pager : "#divRqnpPagerTable",
			loadui: "disable"
		});

		// actualizar lista de asignaciones
		fillGrilla( '#tblRqnp', getRequerimientosList() );
	}
	resizeTableSiga("tblRqnp");
	triggerResizeEvent();
}

//INICIO: Implementación de metodos asignados a objetos
function clickBtnNuevoRqnpCab(){
	clickSendDataMethodPost('formNuevoRqnp');
};

function clickBtnModalPopupAnularRqnpAceptar(){
	if(esVacio('txtMotivoAnulacion','spMotivoAnulacion')){
		return false;
	}else{
		var mensaje ='Esta seguro que anular el requerimiento no programado?';
		showMensajeConfirm(mensaje, functionAceptarAnularRqnp, functionCancelarAnularRqnp );
	}
}

function functionAceptarAnularRqnp(){
	setValueInputText('hidtxtCodigoRqnp',getValueInputText('txtCodigoRqnp'));
	//clickSendDataMethodPost('formAnularRqnp');
	
	// traer la informacion de la solicitud del servidor (plan viaje, asignaciones y desplazamientos)
	$.ajax({
		url: contextPathUrl + "/rqnp.htm?action=anularRqnp",
		type: "post",
		dataType: "json",
		cache: false,
		data: {
			"txtCodigoRqnp": getValueInputText('txtCodigoRqnp'),
			"txtMotivoAnulacion": getValueInputText('txtMotivoAnulacion')
		},
		beforeSend: function() {
			showModalElement("divScreenBlock");
		},
		complete: function() {
			hideModalElement("divScreenBlock");
		},
		success: function(result) {
			debugger;
			if ( huboErrorAjax( result ) ) {
				consoleLog( 'error app callAjaxAnularRqnp ' + result.msgError );
				return;
			}else{
				var rqnpJSON = result.lsRqnp == ''? [] : $.parseJSON(result.lsRqnp);
				fillGrilla( '#tblRqnp', rqnpJSON);
			}
		},
		error: function() {
			consoleLog( 'error callAjaxAnularRqnp' );
		}
	});
}

function functionCancelarAnularRqnp(){
	
}

function clickBtnModalPopupAnularRqnpCancelar(){
	
}

//FIN: Implementación de metodos asignados a objetos

function getRequerimientosList() {
	if ( !isNullOrUndefined( configRqnp ) ) return null;
	return requerimientoDatos.requerimientosList;
}

/*
function getDetalleRequerimiento(codigoRqnp){
	//alert("Es una simple prueba (Codigo): "+codigoRqnp);
	//showModalElement("divModalPopup");
	//showModalElement("divModalPopupEmbebido");
	//showModalElement("divModalPopupSINO");
	//showModalElement("divMensajeAutorizaEnviar");
	//showModalElement("divMensajeConfirmacionLoadingAplicativo");
	//showModalElement("divErrorAplicativo");
	//showModalElement("divScreenBlock");
}
*/

function modificarRqnp(codigoRqnp){
	setValueInputText('hidCodigoRqnp',codigoRqnp);
	clickSendDataMethodPost('formModificarRqnp');
}

function enviarRqnp(codigoRqnp){
	setValueInputText('hidCodigoRqnp',codigoRqnp);
	clickSendDataMethodPost('formEnviarRqnp');
}

function anularRqnp(codigoRqnp){
	setValueInputText('txtCodigoRqnp',codigoRqnp);
	setValueInputText('txtMotivoAnulacion','');
	hideElement('spMotivoAnulacion');
	showModalElement("divModalPopupAnularRqnp"); 
}

//////////////////////////////////////////////////////////
//////CODIGO EN DOJO///////////////

var btnValidaEnviar_click = function(valor){
	//dojo.byId("action").value = "validaMetasBienJson";
	dojo.byId("txtCodigoRqnp").value =valor;
	
	document.frmDetalleConsulta.action="rqnpmetas.htm?action=validaMetasBienJson";

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

var btnSalir_click = function(){
	if(confirm("Confirme si desea salir del Sistema")){
		window.parent.close();
	}
};
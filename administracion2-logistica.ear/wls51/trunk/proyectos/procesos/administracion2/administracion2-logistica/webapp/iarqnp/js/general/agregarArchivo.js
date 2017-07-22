var parametroAgregarArchivo = new Object();
function initElementsAgregarArchivo(parametroArchivo){
	parametroAgregarArchivo.codigoBoleto = parametroArchivo.codigoBoleto;
	parametroAgregarArchivo.planViajeId = parametroArchivo.planViajeId;
	parametroAgregarArchivo.paginaOrigen = parametroArchivo.paginaOrigen;
	setInitElementsAgregarArchivo();
	obtenerTipoDocumento();
	
}

function setInitElementsAgregarArchivo(){
	addEventElement("btnAceptarCarga", "click", clickBtnAceptarCarga);
	addEventElement("btnCancelarCarga", "click", clickBtnCancelarCarga);
	addEventElement("btnSiMensaje", "click", clickBtnSiMensaje);
	addEventElement("btnNoMensaje", "click", clickBtnNoMensaje);
	addEventElement("btnAceptarMensaje", "click", clickBtnAceptarMensaje);
	addEventElement("btnAceptarMensajeWarning", "click", clickBtnAceptarMensajeWarning);
	addEventElement("btnAceptarMensajeError", "click",  clickBtnAceptarMensajeErrorAgregarArchivo);//JMCR-ME se cambia el nombre del metodo para evitar confilctos con la version del metodo de firma electronica
	setValueInputText("txtDescripcionArchivo","");
	setValueInputText("txtDescripcionArchivo","");
	$fileupload = $('#file');
	$fileupload.replaceWith($fileupload.clone(true));
}

function clickBtnNoMensaje(){
	$("#divMensajeConfirmacion").modal("hide");
}

function clickBtnAceptarMensajeWarning(){
	$("#divMensajeAdvertencia").modal("hide");
}

function clickBtnAceptarMensajeErrorAgregarArchivo(){//JMCR-ME se cambia el nombre del metodo para evitar confilctos con la version del metodo de firma electronica
	consoleLog("llegue al evento de mensaje de error");
	$("#divMensajeError").modal("hide");	
}

function clickBtnCancelarCarga(){
	removeEventElementsAgregarArchivo();	
	$('#divAgregarArchivo').modal('hide');
}

function clickBtnSiMensaje(){
	callGuardarDocumento(parametroAgregarArchivo);
}

function clickBtnAceptarMensaje(){
	removeEventElementsAgregarArchivo();
	consoleLog("removi los eventos y por eso no puedo cerrar el mensaje de error");
	$("#divAgregarArchivo").modal("hide");
	$("#divMensajeInfomativo").modal("hide");
}

function removeEventElementsAgregarArchivo(){
	consoleLog("dentro del metodo de remover eventos del modalde agregarArchivos");
	removeAllEventsElement("btnAceptarCarga");
	removeAllEventsElement("btnCancelarCarga");
	removeAllEventsElement("btnSiMensaje");
	removeAllEventsElement("btnNoMensaje");
	removeAllEventsElement("btnAceptarMensaje");
	removeAllEventsElement("btnAceptarMensajeWarning");
	removeAllEventsElement("btnAceptarMensajeError");
}

function clickBtnAceptarCarga(){
	
	var direccion = $('#file').val();
	var tipoArchivo = getValueInputText("selCodTipoArchivo");
	
	var estadoTipoExtencion = false;
	
	var filetypeAllow = [".msg",".pdf"]; //array of files types allow.
	
	if($.trim(direccion) != ""){
		var extension = direccion.substring(direccion.length - 4, direccion.length).toLowerCase();//change to lowercase
		if($.inArray(extension, filetypeAllow)<0){
			estadoTipoExtencion = true;
		}
	}
	
	if($.trim(direccion) == ""){
		setHtmlElement("spaMensajeAdvertencia", adjuntarDocumento.errorFile);
		$("#divMensajeAdvertencia").modal("show");
	}else if($.trim($("#txtDescripcionArchivo").val()) == ""){
		setHtmlElement("spaMensajeAdvertencia", adjuntarDocumento.errorDescripcion);
		$("#divMensajeAdvertencia").modal("show");	
	}else if($.trim(tipoArchivo)=="00" || $.trim(tipoArchivo)=="" ){
		setHtmlElement("spaMensajeAdvertencia", adjuntarDocumento.errorTipoDocumento);
		$("#divMensajeAdvertencia").modal("show");	
	}else if(estadoTipoExtencion){
		var  mensaje = "El tipo de extension del archivo es incorrecto.";
		setHtmlElement("spaMensajeAdvertencia", mensaje);
		$("#divMensajeAdvertencia").modal("show");	
	}else {
		setHtmlElement("spaMensajeConfirmacionTitulo", adjuntarDocumento.textoConfimar);
		$("#divMensajeConfirmacion").modal("show");
	}
}

function callGuardarDocumento(parametroAgregarArchivo){
var direccion = $('#file').val();	
  

	if($.trim(direccion) != ""){
		if($.trim($("#txtDescripcionArchivo").val()) != ""){			
			var archivoTable = $("#tblArchivo");
			var  url = contextPathUrl+"/registroGeneral.htm?action=registrarArchivo";
		    var options = { 
		            success: function(respuesta){            	
		            	if(respuesta.mensajeFinal == "OK"){
		            		archivoTable.jqGrid('clearGridData');	
				            		var listArchivosAdj=respuesta.listaArchivosAdjutos;			
									for(var i=0;i<listArchivosAdj.length;i++){
										var archivo= listArchivosAdj[i];
										var datarow = {
												secuenciaReg:archivo.sec_reg,
												numeroArchivo:archivo.num_reg,
												fileName:archivo.file_name,
												tipoDoc:archivo.tipo_doc,
												sizeTag:archivo.size_tag,
												fecCargaTag:archivo.fec_carga_tag,
												seleccion:'0'
													};
										var su=archivoTable.jqGrid('addRowData',archivo.sec_reg, datarow);											
									}
									
									archivoTable.trigger("reloadGrid");
									agregarArchivoService.agregarArchivoAfter(listArchivosAdj);
									setHtmlElement("spaMensajeInformativoTitulo", adjuntarDocumento.textoInformativo);
									$("#divMensajeInfomativo").modal("show");
									
		            	}else {
		            		setHtmlElement("spaMensajeError", respuesta.mensajeFinal);
		            		$("#divMensajeError").modal("show");
		            	}				
						
		            }, 
		            beforeSend: function() {						
		            	showModalElement("divScreenBlock");
					},
					complete: function() {						
						$("#divMensajeConfirmacion").modal("hide");
						hideModalElement("divScreenBlock");
					},
		            error:function(){
		            	setHtmlElement("spaMensajeAdvertencia", adjuntarDocumento.errorMayorCeroMegas);
	            		$("#divMensajeAdvertencia").modal("show");
		            },
		            type: "POST",
		            dataType: 'JSON',
		            url: url,
		            async: false,
		            enctype: "multipart/form-data",
		            beforeSerialize: function(form, options) {
		                options.data = {		                	
		                		tipoDocumento : getValueInputText("selCodTipoArchivo"),
		                		fileDescripcion : getValueInputText("txtDescripcionArchivo"),
		                		codigoPlanViaje : parametroAgregarArchivo.planViajeId,
		                		codigoBoleto : parametroAgregarArchivo.codigoBoleto,
		                		paginaOrigen : parametroAgregarArchivo.paginaOrigen,
		                		file : $("#file").val()           		
		                };
		            },
		    };
		    $('#formArchivo').ajaxForm(options);
		    $('#formArchivo').submit();
		    

		}else{
			setHtmlElement("spaMensajeAdvertencia", adjuntarDocumento.errorDescripcion);
			$("#divMensajeAdvertencia").modal("show");	
		}
	}
	else{
		setHtmlElement("spaMensajeAdvertencia", adjuntarDocumento.errorFile);
		$("#divMensajeAdvertencia").modal("show");
	}
}


function obtenerTipoDocumento(){

	$.ajax({ 
		data:{},
		url: contextPathUrl+"/viatico.htm?action=obtenerTipoDocumento",
		type: "post",
		dataType: "json",
		cache: false,
		beforeSend: function() {
			showModalElement("divScreenBlock");
		},
		complete: function() {
			hideModalElement("divScreenBlock");
		},
		success: function(respuesta){		
			var option = "";
			var listTipDocumentos=respuesta.listTiposDocumentos;
			option = '<option value ="00">--Seleccione--</option>';
					for(var i=0; i<listTipDocumentos.length; i++){
						var tipDocumentos = listTipDocumentos[i];						
							option = option + '<option value ="'+ tipDocumentos.cod +'">'+ tipDocumentos.name +'</option>';				
					}	
					 setHtmlElement("selCodTipoArchivo",option)
		},
		error:function (xhr, ajaxOptions, thrownError) {
			
		}
	});
	 	
}


//Anexo de contacto
/* 
keydown() 	– Se pulsa la tecla, comienzo de la presión.
keypress() 	– La tecla está presionada.
keyup() 	– La tecla es soltada.
blur()		- Al abandonar el foco
*/

function initElementsRegistroRqnpCabMod(){
	debugger;
	setInitElementsRegistroRqnpCab();
	setDatosMensajeriaContainer(datosMensajeriaRqnpCabMod); //Setea los mensajes al js generico
	disabledElement("btnNuevoRqnpCab");
	hideElement('divJustificacionRuc');
	
	if(rqnpCabDatos.indVinculo=='S'){
		showElement('divTipoVinculo');
	}else if(rqnpCabDatos.indVinculo=='N'){
		hideElement('divTipoVinculo');
	}
}

function setInitElementsRegistroRqnpCab(){
	addEventElement("txtNumRegContacto", "keydown", validaCaracterRegistroEmpleado);
	addEventElement("txtNumRegContacto", "keypress", validaRegistroEmpleadoKeypress);
	addEventElement("txtNumRegContacto", "blur", validaRegistroEmpleadoBlur);
	
	addEventElement("txtAnexoContacto", "keydown", validaNumeroEntero);
	
	addEventElement("txtNumRucProveedor", "keydown", validaNumeroEntero);
	addEventElement("txtNumRucProveedor", "keypress", validaObtieneRucProveedorKeypress);
	addEventElement("txtNumRucProveedor", "blur", validaObtieneRucProveedorBlur);
	
	addEventElement("btnBuscarPersona", "click", clickBtnBuscarPersona); 
	addEventElement("btnRecuperarContratista", "click", clickBtnRecuperarContratista);
	
	addEventElement("btnRqnpCabGuardar", "click", clickBtnRqnpCabGuardar);
	addEventElement("btnRqnpCabCancelar", "click", clickBtnRqnpCabCancelar);
	
	addEventElementBySelector(".optTipoVinculo", "click", clickChangeOptTipoVinculo); 
	addEventElementBySelector(".optTipoPrestamo", "click", clickChangeOptTipoPrestamo); 
	
	addEventElement("txtTipoVinculo", "change", clickChangeTipoVinculo); 
}

function clickBtnBuscarPersona(){
	//Inicio del popup emergente de busqueda de colaborador
	initBusquedaColaborador();
}

function clickChangeOptTipoVinculo(){
	debugger;
	activeButtonGroupForClass('.optTipoVinculo');
	var optTipoVinculo= getValueButtonGroupElementForClass('.optTipoVinculo');
	
	if(optTipoVinculo=="S"){
		showElement('divTipoVinculo');
	}else if (optTipoVinculo=="N"){
		hideElement('divTipoVinculo');
	}
	
	reiniciaInputSelect('txtTipoVinculo','0',rqnpCabDatos.textoSeleccioneOpcion); 
	reiniciaInputSelect('txtVinculo','0',rqnpCabDatos.textoSeleccioneOpcion);
	listarTipoViculoJson();
}

function clickChangeOptTipoPrestamo(){
	activeButtonGroupForClass('.optTipoPrestamo');
}

function clickChangeTipoVinculo(){
	debugger;
	var tipoVinculoSel=getValueInputSelect('txtTipoVinculo','0')
	listarVinculoJson(tipoVinculoSel);
}


function listarTipoViculoJson(tipoVinculoSel){	
	$.ajax({
		  url		: contextPathUrl+"/rqnp.htm?action=listarTipoViculoJson",
		  type		: "post",
		  async     : true, 	//Cierra la conexion despues de obtenido la respuesta
		  dataType	: "json", 	//Es el tipo de dato que devolvera el servidor
		  cache		: false,
		  timeout	: "60000",
		  success	: function(result){
			  			var data=result.items;
			  				if(data!=null && $.isArray(data)){
			  					reiniciaInputSelect('txtTipoVinculo','0',rqnpCabDatos.textoSeleccioneOpcion);
			  					fillValueAndTextInputSelect('txtTipoVinculo',data);
						 }},
		  error		: function () {
			  showMensajeError("Error - Comuniquese con el administrador del sistema.",null);
		  }
	});
	
}

function listarVinculoJson(tipoVinculoSel){	
	$.ajax({
		  url		: contextPathUrl+"/rqnp.htm?action=listarVinculoJson&tipoVinculoSel="+tipoVinculoSel,
		  type		: "post",
		  async     : true, 	//Cierra la conexion despues de obtenido la respuesta
		  dataType	: "json", 	//Es el tipo de dato que devolvera el servidor
		  cache		: false,
		  timeout	: "60000",
		  success	: function(result){
			  			var data=result.items;
			  				if(data!=null && $.isArray(data)){
			  					debugger;
			  					reiniciaInputSelect('txtVinculo','0',rqnpCabDatos.textoSeleccioneOpcion);
			  					//El array devuelto por el controller debe tener las columnas (cod,name)
			  					fillValueAndTextInputSelect('txtVinculo',data); 
						 }},
		  error		: function () {
			  showMensajeError("Error - Comuniquese con el administrador del sistema.",null);
		  }
	});
	
}

//Función que recibe el registro seleccionado del componente Colaborador
function setColaboradorSeleccionado(rowData) {
	// setear datos del colaborador
	setValueInputText('txtNumRegContacto', $.trim(rowData['numeroRegistro']));
	setValueInputText('txtCodEmpleadoContacto', $.trim(rowData["codigoEmpleado"]));
	setValueInputText('txtNomContacto', $.trim(rowData["nombreCompleto"]));
	setValueInputText('txtCodDepContacto', $.trim(rowData["codigoDependencia"]));
	
}

//Función que recibe el registro seleccionado del componente Colaborador
function setProveedorSeleccionado(rowData) {
	// setear datos del colaborador
	setValueInputText('txtNumRucProveedor', $.trim(rowData['num_ruc']));
	setValueInputText('txtNumRucProveedor', $.trim(rowData['num_ruc']));
	setValueInputText('txtCodProveedor', $.trim(rowData["cod_cont"]));
	setValueInputText('txtNomProveedor', $.trim(rowData["raz_social"]));
	//setValueInputText('hidCodDepContacto', $.trim(rowData["codigoDependencia"]));
}


function clickBtnRecuperarContratista(){
	
}

function clickBtnRqnpCabGuardar(){
	debugger;
	obtenerRqnpCabDatosFormulario();
	if(validaDatosRqnpCab()){
		showMensajeConfirmGenerico(rqnpCabDatos.mensajes.solicitaConfirmacionGrabado,functionConfirmaGrabadoAceptarRqnpCabMod, functionConfirmaGrabadoCancelarRqnpCabMod);
	}
	return false;
}

function clickBtnRqnpCabCancelar(){
	debugger;
	if(rqnpCabTipoAccion=='M'){
		modificarRqnpCab(rqnpCabDatos.codigoRqnp);
	}else if(rqnpCabTipoAccion=='R'){
		clickSendDataMethodPost('formInicioBandejaRqnp');
	}
}


function functionConfirmaGrabadoAceptarRqnpCabMod(){
	debugger;
	//"metodo de controller: registrarCabEntregaRqnp()  - RqnpDetalleController"
	//rqnpCabTipoAccion | R: Registrar Nuevo; M:Modificar
	registrarCabRqnp(rqnpCabTipoAccion);
	
}

function functionConfirmaGrabadoCancelarRqnpCabMod(){
	hideElement('divModalPopupSINO');
	modificarRqnpCab(rqnpCabDatos.codigoRqnp);
}

function modificarRqnpCab(codigoRqnp){
	debugger;
	setValueInputText('hidCodigoRqnpMod',codigoRqnp);
	clickSendDataMethodPost('formModificarRqnpCab');
}


function obtenerRqnpCabDatosFormulario(){
	rqnpCabDatos.numReqNoProgramado		=getValueInputText('txtNumReqNoProgramado');
	rqnpCabDatos.fechaRqnp				=getValueInputText('txtFechaRqnp');
	rqnpCabDatos.numRegResponsable		=getValueInputText('txtNumRegResponsable');
	rqnpCabDatos.uuooResponsable		=getValueInputText('txtUuooResponsable');
	rqnpCabDatos.nomUuooResponsable		=getValueInputText('txtNomUuooResponsable');
	rqnpCabDatos.numRegContacto			=getValueInputText('txtNumRegContacto');
	rqnpCabDatos.codProveedor			=getValueInputText('txtCodProveedor');  
	rqnpCabDatos.codEmpleadoContacto	=getValueInputText('txtCodEmpleadoContacto'); 
	rqnpCabDatos.codDepContacto			=getValueInputText('txtCodDepContacto'); ////
	rqnpCabDatos.anexoContacto			=getValueInputText('txtAnexoContacto');
	rqnpCabDatos.numRucProveedor		=getValueInputText('txtNumRucProveedor');
	rqnpCabDatos.nomProveedor			=getValueInputText('txtNomProveedor');
	rqnpCabDatos.obsJustificaProveedor	=getValueInputText('txtObsJustificaProveedor');
	rqnpCabDatos.obsPlazoEntrega		=getValueInputText('txtObsPlazoEntrega');
	rqnpCabDatos.obsLugarEntrega		=getValueInputText('txtObsLugarEntrega');
	rqnpCabDatos.obsDirEntrega			=getValueInputText('txtObsDirEntrega');
	rqnpCabDatos.codFinalidad			=getValueInputSelect('txtCodFinalidad','0');
	rqnpCabDatos.codNecesidad			=getValueInputSelect('txtCodNecesidad','0');
	rqnpCabDatos.anioAtencion			=getValueInputSelect('txtAnioAtencion','0');
	rqnpCabDatos.mesAtencion			=getValueInputSelect('txtMesAtencion','0');
	rqnpCabDatos.optVinculo				=getValueButtonGroupElementForClass('.optTipoVinculo');
	rqnpCabDatos.tipoVinculo			=getValueInputSelect('txtTipoVinculo','04');
	rqnpCabDatos.vinculo				=getValueInputSelect('txtVinculo','01');
	rqnpCabDatos.optPrestamo			=getValueButtonGroupElementForClass('.optTipoPrestamo');
	rqnpCabDatos.motivoSustento			=getValueInputText('txtMotivoSustento');
	
	/*
	mapRqnp.put("codigoRqnp"			, rqnp.getCodigoRqnp());
	mapRqnp.put("numReqNoProgramado"	, rqnp.getCodigoReqNoProgramado());
	mapRqnp.put("fechaRqnp"				, FechaUtil.formatDateToDateDDMMYYYYHHMM(rqnp.getFechaRqnp()));
	mapRqnp.put("numRegResponsable"		, rqnp.getNumeroRegistro());
	mapRqnp.put("codEmpleadoResponsable",colaborador.getCodigoEmpleado());
	mapRqnp.put("nomResponsable"		, rqnp.getNombreSolicitante());
	mapRqnp.put("uuooResponsable"		, rqnp.getUuoo());
	mapRqnp.put("codDepResponsable"		, rqnp.getCodigoDependencia());
	mapRqnp.put("nomUuooResponsable"	, rqnp.getNombreUOSolicitante());
	mapRqnp.put("numRegContacto"		, personalContacto.getNumero_registro());
	mapRqnp.put("codEmpleadoContacto"	, rqnp.getCod_contacto());
	mapRqnp.put("codDepContacto"		, rqnp.getCod_contacto());
	mapRqnp.put("nomContacto"			, personalContacto.getNombre_completo());
	mapRqnp.put("anexoContacto"			, rqnp.getAnexo_contacto());
	mapRqnp.put("codProveedor"			, contratista.getCod_cont());
	mapRqnp.put("numRucProveedor"		, contratista.getNum_ruc());
	mapRqnp.put("nomProveedor"			, contratista.getRaz_social()); 
	mapRqnp.put("obsJustificacion"		, rqnp.getObs_justificacion());
	mapRqnp.put("obsPlazoEntrega"		, rqnp.getObs_plazo_entrega());
	mapRqnp.put("obsLugarEntrega"		, rqnp.getObs_lugar_entrega());
	mapRqnp.put("obsDirEntrega"			, rqnp.getObs_dir_entrega());
	mapRqnp.put("codFinalidad"			, rqnp.getCod_finalidad());
	mapRqnp.put("codNecesidad"			, rqnp.getCod_necesidad());
	mapRqnp.put("anioAtencion"			, rqnp.getAnio_atencion());
	mapRqnp.put("mesAtencion"			, rqnp.getMes_atencion());
	mapRqnp.put("anioProceso"			, rqnp.getAnioProceso());
	mapRqnp.put("indVinculo"			, rqnp.getInd_vinculo());
	mapRqnp.put("tipoVinculo"			, rqnp.getTipo_vinculo());
	mapRqnp.put("vinculo"				, rqnp.getVinculo());
	mapRqnp.put("indPrestamo"			, rqnp.getInd_prestamo());
	mapRqnp.put("motivoSustento"		, rqnp.getMotivoSolicitud());
	mapRqnp.put("montoRqnp"				, rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
	mapRqnp.put("motivoRechazo"			, rqnp.getMotivoRechazo());
	*/
}

function validaDatosRqnpCab(){
	var flag=false;
	if(rqnpCabDatos.codEmpleadoContacto=="" || rqnpCabDatos.codEmpleadoContacto== null){
		showMensajeGenerico(errorMessageRqnpCabDatos.numRegistroContacto);
	}else if (rqnpCabDatos.numRegContacto=="" || rqnpCabDatos.numRegContacto==null){
		showMensajeGenerico(errorMessageRqnpCabDatos.numRegistroContacto);
	}else if(rqnpCabDatos.anexoContacto==""){
		showMensajeGenerico(errorMessageRqnpCabDatos.anexoContacto);
	}else if(rqnpCabDatos.numRucProveedor!=""){
		if(rqnpCabDatos.nomProveedor==""){
			showMensajeGenerico(errorMessageRqnpCabDatos.numRucProveedor);
		}
	}else if(rqnpCabDatos.obsPlazoEntrega==""){
		showMensajeGenerico(errorMessageRqnpCabDatos.obsPlazoEntrega);
	}else if(rqnpCabDatos.obsLugarEntrega==""){
		showMensajeGenerico(errorMessageRqnpCabDatos.obsLugarEntrega);
	}else if(rqnpCabDatos.obsDirEntrega==""){
		showMensajeGenerico(errorMessageRqnpCabDatos.obsDirEntrega);
	}else if(rqnpCabDatos.codFinalidad=="0"){
		showMensajeGenerico(errorMessageRqnpCabDatos.codFinalidad);
	}else if(rqnpCabDatos.codNecesidad=="0"){
		showMensajeGenerico(errorMessageRqnpCabDatos.codNecesidad);
	}else if(rqnpCabDatos.optVinculo=="S"){
		if($('#txtTipoVinculo').val()==null || $('#txtTipoVinculo').val()=='undefined'){
			showMensajeGenerico(errorMessageRqnpCabDatos.tipoVinculo);
		}else if($('#txtVinculo').val()==null|| $('#txtVinculo').val()=='undefined'){
			showMensajeGenerico(errorMessageRqnpCabDatos.vinculo);
		}else if(rqnpCabDatos.motivoSustento==""){
			showMensajeGenerico(errorMessageRqnpCabDatos.motivoSustento);
		}else{
			flag=true;
		}
	}else if(rqnpCabDatos.motivoSustento==""){
		showMensajeGenerico(errorMessageRqnpCabDatos.motivoSustento);
	}else{
		flag=true;
	}
	return flag;
}

function seteaDatosAIntputDeFormularioParaController(rqnpCabTipoAccion){
	setValueInputText("hidCodigoRqnp", rqnpCabDatos.codigoRqnp);
	setValueInputText("hidCodEmpleadoContacto", rqnpCabDatos.codEmpleadoContacto);
	setValueInputText("hidAnexoContacto", rqnpCabDatos.anexoContacto);
	setValueInputText("hidNumRucProveedor", rqnpCabDatos.numRucProveedor);
	setValueInputText("hidCodProveedor", rqnpCabDatos.codProveedor);
	setValueInputText("hidObsJustificaProveedor", rqnpCabDatos.obsJustificaProveedor);
	setValueInputText("hidObsPlazoEntrega",rqnpCabDatos.obsPlazoEntrega );
	setValueInputText("hidObsLugarEntrega",rqnpCabDatos.obsLugarEntrega );
	setValueInputText("hidObsDirEntrega",rqnpCabDatos.obsDirEntrega );
	setValueInputText("hidCodFinalidad",rqnpCabDatos.codFinalidad );
	setValueInputText("hidCodNecesidad",rqnpCabDatos.codNecesidad );
	setValueInputText("hidAnioAtencion",rqnpCabDatos.anioAtencion);
	setValueInputText("hidMesAtencion", rqnpCabDatos.mesAtencion);
	setValueInputText("hidOptVinculo", rqnpCabDatos.optVinculo);
	setValueInputText("hidTipoVinculo", rqnpCabDatos.tipoVinculo);
	setValueInputText("hidVinculo", rqnpCabDatos.vinculo);
	setValueInputText("hidOptPrestamo",rqnpCabDatos.optPrestamo );
	setValueInputText("hidMotivoSustento", rqnpCabDatos.motivoSustento);
	setValueInputText("hidMontoRqnp", rqnpCabDatos.montoRqnp);
	
	setValueInputText("hidRqnpCabTipoAccion", rqnpCabTipoAccion);
}


function registrarCabRqnp(rqnpCabTipoAccion){
	debugger;
	seteaDatosAIntputDeFormularioParaController(rqnpCabTipoAccion);
	bloquearScreen();
	clickSendDataMethodPost('formRegistroRqnpCabMod');
}



function modificarCabRqnp(){
	seteaDatosAIntputDeFormularioParaController();
	bloquearScreen();
	clickSendDataMethodPost('formRegistroRqnpCabMod');
}


//Ingresando en un input el numero de registro, se obtiene los datos del empleado
function validaObtieneRucProveedorKeypress(e) {
	var numRucProveedor=$(this).val().toUpperCase();
	if(e.type='keypress' && e.which==13){
		e.preventDefault();
		getProveedorXRuc(numRucProveedor);
		return true;
	}
}


//Ingresado el numero de registro, obtiene el empleado al abandonar el input
function validaObtieneRucProveedorBlur(e) {
	var numRucProveedor=$(this).val().toUpperCase();
	if(e.type='blur'){
		e.preventDefault();
		getProveedorXRuc(numRucProveedor);
		return true;
	}
}


function getProveedorXRuc(numRucProveedor) {
	if (typeof numRucProveedor != "undefined") {
		var longitud =numRucProveedor.length;
		if(longitud<11){
			showMensajeGenerico(errorMessageRqnpCabDatos.numRucProveedor);
		}else{
			$.ajax({
				url: contextPathUrl + "/rqnp.htm?action=recuperarContratistaRqnpJson",
				type: "post",
				dataType: "json",
				cache: false,
				data: {"hidNumRucProveedor": numRucProveedor},
				success: function(result) {
					
					debugger;
					var contratista = result.contratista;
					var indRetorna = result.contratistaOK;
					var datarow={};
					debugger;
					if (indRetorna =="1") {
						if(contratista.cod_cont == "-1" ){
							showMensajeGenerico(errorMessageRqnpCabDatos.numRucProveedor);
						}else{
							datarow = {
									codProveedor: contratista.cod_cont,
									numRucProveedor: contratista.num_ruc,
									razonSocialProveedor: contratista.raz_social
								};	
						}
					}else {
						showMensajeGenerico(errorMessageBuscarColaborador.colaboradorNoExiste);
					}
					setProveedorSeleccionado(datarow);//Esta función pertenece al componente que invocó la ventana de busqueda de colaboradores.
				},
				error: function() {
					showMensajeError("Error - Comuniquese con el administrador del sistema.",null);
				}
			});
		}
		
	}
}


//Ingresando en un input el numero de registro, se obtiene los datos del empleado
function validaRegistroEmpleadoKeypress(e) {
	var numRegistroColaborador=$(this).val().toUpperCase();
	if(isKeypress13(e)){
		getColaboradorXRegistro(numRegistroColaborador);
		return true;
	}
}


//Ingresado el numero de registro, obtiene el empleado al abandonar el input
function validaRegistroEmpleadoBlur(e) {
	var numRegistroColaborador=$(this).val().toUpperCase();
	if(e.type='blur'){
		e.preventDefault();
		getColaboradorXRegistro(numRegistroColaborador);
		return true;
	}
}


function getColaboradorXRegistro(numRegistroColaborador) {
	if (typeof numRegistroColaborador != "undefined") {
		$.ajax({
			url: contextPathUrl + "/colaboradores.htm?action=getColaborador",
			type: "post",
			dataType: "json",
			cache: false,
			data: {"numRegistroColaborador": numRegistroColaborador},
			success: function(result) {
				var colaborador = result.colaborador;
				var indRetorna = result.colaboradorOK;
				var datarow={};
				if (indRetorna =="1") {
					//consoleLog("colaborador.numero_registro: " + colaborador.numero_registro +" colaborador.codigoEstado: "+colaborador.codigoEstado);
					if(colaborador.codigoEstado == "0" ){
						showMensajeGenerico(errorMessageBuscarColaborador.colaboradorDeBaja);
					}else{
						datarow = {
								numeroRegistro: colaborador.numero_registro,
								nombreCompleto: colaborador.nombre_completo,
								uuooDetalle: colaborador.uuoo +" - "+colaborador.dependencia,
								codigoEmpleado: colaborador.codigoEmpleado,
								codigoDependencia: colaborador.codigoDependencia
							};	
					}
				}else {
					debugger;
					showMensajeGenerico(errorMessageBuscarColaborador.colaboradorNoExiste);
				}
				setColaboradorSeleccionado(datarow);//Esta función pertenece al componente que invocó la ventana de busqueda de colaboradores.
			},
			error: function() {
				showMensajeErrorGenerico("Error - Comuniquese con el administrador del sistema.",null);
			}
		});
	}
}


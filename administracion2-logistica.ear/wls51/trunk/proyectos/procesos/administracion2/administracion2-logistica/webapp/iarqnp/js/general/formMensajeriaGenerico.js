var datosMensajeria = null;
/*
  datosMensajeria.divPopupPanelClass 
  datosMensajeria.divPopupContainerClass
  datosMensajeria.divPopupMensaje
  datosMensajeria.divModalPopup
  datosMensajeria.btnPopupAceptar
*/
function setDatosMensajeriaContainer(datosMensajeriaContainer){
	debugger;
	datosMensajeria = datosMensajeriaContainer;
}

function showMensajeGenerico(mensaje, functionAceptar) {
	debugger;
	return showMensajeGenerico(mensaje, functionAceptar, 'exito');
}

function showMensajeErrorGenerico(mensaje, functionAceptar) {
	return showMensajeGenerico(mensaje, functionAceptar, 'error');
}

function showMensajeGenerico(mensaje, functionAceptar, tipoMensaje) {
	debugger;
	//por si se desea manejar los colores de bootstrap
	if (tipoMensaje == 'exito' || tipoMensaje == 'success') {
		$('#'+datosMensajeria.divPopupPanelClass).prop('class', 'panel panel-primary');
	} else if (tipoMensaje == 'error' || tipoMensaje == 'danger') {
		$('#'+datosMensajeria.divPopupPanelClass).prop('class', 'panel panel-danger');
	} else if (tipoMensaje == 'alert' || tipoMensaje == 'warning') {
		$('#'+datosMensajeria.divPopupPanelClass).prop('class', 'panel panel-primary');
	} else {
		$('#'+datosMensajeria.divPopupPanelClass).prop('class', 'panel panel-primary');
	}

	var mensajeTrim = $.trim(mensaje);

	if (mensajeTrim.length < 50) {
		$('#'+datosMensajeria.divPopupContainerClass)
				.prop('class','container sigaMensajeConfirmacionContainer verticalAlignmentHelper');
	} else {
		$('#'+datosMensajeria.divPopupContainerClass).prop('class','container sigaMensajeConfirmacionContainerBigger verticalAlignmentHelper');
	}
	
	$('#'+datosMensajeria.divPopupMensaje).addClass('txtTextJustifyClase');

	if ($('#'+datosMensajeria.divModalPopup).length) {

		// si se tiene el div de popup
		$('#'+datosMensajeria.divPopupMensaje).html(mensaje);

		$('#'+datosMensajeria.divModalPopup).modal({
			keyboard : false
		});

		$('#'+datosMensajeria.btnPopupAceptar).off('click');
		if (estaDefinido(functionAceptar)) {
			$('#'+datosMensajeria.btnPopupAceptar).on('click', functionAceptar);
		}

		// pone el foco el boton aceptar, y de paso fix el bug que deja el foco
		// en algún control de la pantalla padre y puede efectuar operaciones
		// con él.
		setTimeout(function() {
			$('#'+datosMensajeria.btnPopupAceptar).focus();

			// por bug de IE
			if (isBrowserInternetExplorer()) {
				triggerResizeEvent();
			}
		}, 200);

	} else {
		// sino imprimir un simple alert
		alert(mensaje);
	}
}

function showMensajeConfirmGenerico(mensaje, functionAceptar, functionCancelar) {

	var mensajeTrim = $.trim(mensaje);

	if (mensajeTrim.length < 50) {
		$('#'+datosMensajeria.divPopupContainerClassSINO)
				.prop('class','container sigaMensajeConfirmacionContainer verticalAlignmentHelper');
	} else {
		$('#'+datosMensajeria.divPopupContainerClassSINO)
				.prop('class','container sigaMensajeConfirmacionContainerBigger verticalAlignmentHelper');
	}

	if (mensajeTrim.length < 150) {
		$('#'+datosMensajeria.divPopupMensajeSINO).addClass('txtTextLeftClase');
	} else {
		$('#'+datosMensajeria.divPopupMensajeSINO).addClass('txtTextJustifyClase');
	}

	$('#'+datosMensajeria.divPopupPanelClassSINO).prop('class', 'panel panel-primary');

	if ($('#'+datosMensajeria.divModalPopupSINO).length) {

		// si se tiene el div de popup
		$('#'+datosMensajeria.divPopupMensajeSINO).html(mensajeTrim);

		$('#'+datosMensajeria.divModalPopupSINO).modal({
			keyboard : false
		});

		$('#'+datosMensajeria.btnPopupAceptarSINO).off('click');
		if (estaDefinido(functionAceptar)) {
			$('#'+datosMensajeria.btnPopupAceptarSINO).on('click', functionAceptar);
		}

		$('#'+datosMensajeria.btnPopupCancelarSINO).off('click');
		if (estaDefinido(functionCancelar)) {
			$('#'+datosMensajeria.btnPopupCancelarSINO).on('click', functionCancelar);
		}

		// pone el foco el boton aceptar, y de paso fix el bug que deja el foco
		// en algún control de la pantalla padre y puede efectuar operaciones
		// con él.
		setTimeout(function() {
			$('#'+datosMensajeria.btnPopupAceptarSINO).focus();

			// por bug de IE
			if (isBrowserInternetExplorer()) {
				triggerResizeEvent();
			}

		}, 200);
	}
}
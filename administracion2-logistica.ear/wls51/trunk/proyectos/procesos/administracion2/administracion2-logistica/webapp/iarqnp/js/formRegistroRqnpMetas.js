function anular_rqnp(parametro_uit){
	if(confirm("Confirme si desea ANULAR el Requerimiento No Programado")){
		//dojo.byId("action").value = "anularRqnp";
		dojo.byId("txtAnulacion").value = "Anulado automaticamente por superar las "+parametro_uit+" UITs.";
		dojo.byId("formAnulacion").submit();
	}
};

function codigoUITValido(valor){
	var grid =dijit.byId("gridDetalle");
	var items = grid.selection.getSelected();
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
			  }); /* end forEach */
	
		      if (dojo.byId("jupdate").value=="0"){
			     btnValidaEnviar_click2(valor);
			  }else{
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


var btnValidaEnviar_click = function(valor){
	var monto_total_rqnp= dojo.byId("txtMontoAcumulado").value;
	monto_total_rqnp=parseFloat(monto_total_rqnp.replace(",", "").replace(",", ""));
	var tipo_auc= dojo.byId("txtTipo_auc").value;
	var limite_cd= parseFloat(dojo.byId("txtLimite_cd").value);
	var codigo_dep= parseFloat(dojo.byId("jcodigo_dep").value);
	var ind_auc_item= dojo.byId("txtInd_auc_item").value;
	var parametro_uit= dojo.byId("txtParametro_uit").value;
	if(tipo_auc=="N"){ //NO AUC
		if(monto_total_rqnp>limite_cd){
			if (confirm("Este requerimiento supera las "+parametro_uit+" UITs.\nDeber\u00E1 coordinar con la AUC competente para su registro.\nEl sistema proceder\u00E1 a anular este requerimiento, desea continuar(S/N)?")){
				anular_rqnp(parametro_uit);
			};
			return;
		}else{
			codigoUITValido(valor);
		}
	}else{ //SI AUC
		if(ind_auc_item=="S"){
			codigoUITValido(valor);
		}else{
			if(monto_total_rqnp>limite_cd){
				if (confirm("Este requerimiento supera las "+parametro_uit+" UITs.\nDeber\u00E1 coordinar con la AUC competente para su registro.\nEl sistema proceder\u00E1 a anular este requerimiento, desea continuar(S/N)?")){
					anular_rqnp(parametro_uit);
				};
				return;
			}else{
				codigoUITValido(valor);
			}
		}
	}
};


//Devuelve: 0=ok, 1= no Tiene monto, 3= no tiene metas 
var btnValidaEnviar_click2 = function(valor){
	
	dojo.byId("action").value = "validaMetasBienJson";
	dojo.byId("txtCodigoRqnp").value =valor;
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						var data = eval("(" + unescape(response.data )+ ")");
						if (data.validaMeta=='0'){
							btnEnviar_click(valor);
						}else if( data.validaMeta=='1'){
							alert("Existen items que no ha indicado la cantidad de bienes a adquirir \u00F3 el monto en S/. aproximado que costar\u00E1 el servicio.");
						}else{
							alert("El Requerimiento NO tiene \u00EDtems registrados");
						}
					}
				}
				else{
					alert(response.data.mensaje);
					
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert("Error [" + error.message + "]");
			},
			form: dojo.byId("frmRegistroMetas")
	};	
	
	dojo.xhrPost(enviar);
}


var btnGuardar_click = function (cod_bien,precioUnid) {

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
					 		alert("Registre cantidad de la afectaci\u00F3n, NO se permite valores nulos");
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
		handleAs : "json",
		load : function(response, ioArgs) {
			if(response.data.error==null){
				if (response.data != "" && response.data != "[]") {
					
					var data = eval("(" + unescape(response.data) + ")");
					dojo.byId("jload").value='0';
					actualizarMetas(data.listaMetas);
					dijit.byId('btnEnviar').setAttribute("disabled", false);
				}
			}
			else{
				alert(response.data.mensaje);
			}
		},
		timeout : 25000,
		error : function(error, ioArgs) {
			alert("Error [" + error.message + "]");
		},
		form: dojo.byId("frmRegistroMetas")
	};	
	
	dojo.xhrPost(enviar);
	dojo.byId("jupdate").value ="0";

	//dijit.byId('frmRegistroMetas').submit()
}

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
						
						var data = eval("(" + unescape(response.data) + ")");
						dojo.byId("jload").value='0';
						actualizarMetas(data.listaMetas);
						alert("Los datos se han registrado correctamente.");
						dijit.byId('btnEnviar').setAttribute("disabled", false);
					}
				}
				else{
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert("Error [" + error.message + "]");
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
	dojo.byId("jcodigo_bien_old").value = dojo.byId("jcodigo_bien").value;
	dojo.byId("jprecio_unid_old").value = dojo.byId("jprecio_unid").value;
	dojo.byId("jcodigo_bien").value = cod_bien;
	dojo.byId("jprecio_unid").value = precioUnid;

  	//dijit.byId('btnGuardar').setAttribute("disabled", true);
	dojo.byId("action").value = "registrarRqnpMetasJson";
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						
						var data = eval("(" + unescape(response.data )+ ")");
						dojo.byId("jload").value='0';
						actualizarMetas(data.listaMetas);
						dijit.byId('btnEnviar').setAttribute("disabled", false);
						btnValidaEnviar_click2(valor);
					}
				}
				else{
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert("Error [" + error.message + "]");
			},
			form: dojo.byId("frmRegistroMetas")
	};	
	
	dojo.xhrPost(enviar);
	dojo.byId("jupdate").value ="0";

	//dijit.byId('frmRegistroMetas').submit()
}


var btnSalir_click = function(){
	if(dojo.byId("txtvisor").value=="0" ){
		document.frmRegistroMetas.action="rqnp.htm?action=iniciarbandeja";
	}else{
		document.frmRegistroMetas.action="rqnpconsulta.htm?action=iniciarconsulta";
	}
		document.frmRegistroMetas.submit();
}

/*
var btnEnviar_click = function(valor){
	if(confirm("Confirme si desea ENVIAR el Requerimiento No Programado")){
		dojo.byId("action").value = "enviarRqnp";
		dojo.byId("txtCodigoRqnp").value = valor;
		var formDlg = dijit.byId("formDialogEnvio");
		formDlg.show();
		dojo.byId("frmRegistroMetas").submit();
	}
};
*/

///////////////////////////////////////////////
var btnEnviar_click = function(valor){
	var sw='0';
	dojo.byId("action").value = "enviarRqnp";
	dojo.byId("txtCodigoRqnp").value = valor;
	if(confirm("Confirme si desea ENVIAR el Requerimiento No Programado")){
		var formDlg = dijit.byId("formDialogEnvio");
		formDlg.show();
		//llamada AJAX
   		var enviar = {
   			handleAs : "json",
   			load : function(response, ioArgs) {
   				if (response.data != "" && response.data != "[]") {
   					var flag = response.data.flagRegistroDeclaracion;
   					var mensaje = response.data.mensaje;
   					if (flag=="1"){						
   				  		alert("Mensaje: "+mensaje);	
   				  		formDlg.hide();
   				  	}else{
   				  		document.frmRegistroMetas.action="rqnp.htm?action=iniciarbandeja";
						dojo.byId("frmRegistroMetas").submit();
						//formDlg.hide();
   				  		}
   				}else{
   					var data = eval("(" + response.data + ")");
   					alert(response.data.mensaje);
   				}
   			},
   			timeout : 90000,
   			error : function(error, ioArgs) {
   				alert("Error [" + error.message + "]");
   			},
   			form: dojo.byId("frmRegistroMetas")
   		};	
   		dojo.xhrPost(enviar);
	}
   	
};
///////////////////////////////////////////////

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
						actualizarMetas(data.listaMetas);
					}
				}
				else{
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert("Error [" + error.message + "]");
			},
			form: dojo.byId("frmRegistroMetas")
	};	
	dojo.xhrPost(enviar);
}


function actualizarMetas(lista){	
	var lsMetas =lsMetasItems(lista);
	var storeMetas = new dojo.data.ItemFileWriteStore({
		data: {
			identifier: 'vsecuenciaMeta',
			items: lsMetas
		}});
	
	var _grdLstPagAnt= dijit.byId("gridMetas");
	if (_grdLstPagAnt==null){
		
		construirGridMetas(storeMetas);
	}else{
		_grdLstPagAnt.setStore(storeMetas);
		_grdLstPagAnt.store.save();
		_grdLstPagAnt.update();
	}
}


function lsMetasItems(items){
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
			
			cadena += "{vcodigoRqnpM:'" + item.codigoRqnp + "',";	
			cadena += "vcodigoBienM:'" + item.codigoBien + "',";
			cadena += "vanioEjec:'" + item.anioEjec+ "',";
			cadena += "vsecuenciaMeta:'" + item.secuenciaMeta + "',";
			cadena += "vmetaSiaf:'" + item.metaSiaf + "',";
			cadena += "vcantidadTotal:'" + item.cantidadTotal + "',";	
			cadena += "vxcantidadTotal:'" + item.cantidadTotal + "',";	
			cadena += "vmontoSoles	:'" + item.montoSoles  + "',";
			cadena += "vxmontoSoles	:'" + item.montoSoles  + "',";
			cadena += "vprecioUnid	:'" + item.precioUnid  + "',";
			cadena += "vproducto	:'" + item.producto  + "',";
			cadena += "vmeta	:'" + item.meta + "',";
			cadena += "vubg:'" + item.ubg  + "'}";
		});
		cadena += "]";
			
		var lst_pag_ant = eval("(" + cadena + ")");
		return lst_pag_ant;
	} catch(e) {
		return [];
	}
	
}

var btnAtras_click = function(){
	document.frmRegistroMetas.action="rqnp.htm?action=modificarRqnp";
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
		divShow("divbtnEnviar");
		divShow("divbtnAtras");
	}
	divShow("divbtnSalir");
	var grid = dijit.byId('gridDetalle');
	grid.scrollToRow(1);
});

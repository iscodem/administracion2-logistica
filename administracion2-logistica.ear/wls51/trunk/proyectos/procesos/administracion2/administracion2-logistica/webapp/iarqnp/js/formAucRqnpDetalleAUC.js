	function getCheck(valor, rowIndex) {
	var cod_rqnp;
	var ind_esp;
	var cod_bien;
	var grid = dijit.byId('gridDetalle');
	var itemIndex = grid.getItem(rowIndex);
	cod_rqnp =grid.store.getValue(itemIndex, 'vcodigoRqnp') ;
	cod_bien =grid.store.getValue(itemIndex, 'vxcodigoBien') ;
	ind_esp =grid.store.getValue(itemIndex, 'vind_especializado') ;
	
	if(dijit.byId("cbx"+cod_rqnp+cod_bien)){

		 return dijit.byId("cbx"+cod_rqnp+cod_bien);
	}else{

		return  new dijit.form.CheckBox({ id:"cbx"+cod_rqnp+cod_bien,  name: "txtCodigobien", value: cod_rqnp +','+cod_bien+','+ind_esp,  checked:false});
	}
} 


function adjuntarArchivo(cod_req,cod_bien,num_reg){
	dojo.byId("txtCodigoRqnp").value 	= cod_req;
	dojo.byId("txtCodigoBien").value 	= cod_bien;
	dojo.byId("txtNum_reg").value 		= num_reg;
		
	document.frmRegistro.action="rqnparchivo.htm?action=iniciarCargaArchivo";	
	document.frmRegistro.submit();
}


var btnModificar_detalle_click = function(cod_rqnp,cod_bien){
	
	var formDlg = dijit.byId("formDialogRequerimientoDetalle");
	dojo.byId("action").value = "recuperarBienEntregaJson";
	
	dojo.byId("txtCodigoRqnp").value = cod_rqnp; 
	dojo.byId("txtCodigoBien").value = cod_bien; 
	
	formDlg.show();
	formDlg.onFocus();
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			headers: { "Content-Type": "application/json; charset=UTF-8"},
			load : function(response, ioArgs) {
				
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]" && response.data != "{}") {
						
						var data = eval("(" +unescape(response.data )+ ")");
					//	if (response.data.error !="0"){
							var val_nombre = data.raz_social;
							var val_cod_cont=data.cod_cont
							var val_num_ruc=data.num_ruc
							 var  strObject = new  String(val_nombre); 
						
							if (trim(strObject)=="undefined"){
								val_nombre="";
								val_num_ruc="";
							}
							dojo.byId("txtNom_proveedor3").value 		= val_nombre; 
							dojo.byId("txtCod_proveedor").value 		= data.cod_cont;
							dojo.byId("txtNum_ruc_prov3").value 		= val_num_ruc;
							dojo.byId("txtObs_justificacion3").value 	= data.obs_justificacion;
							dojo.byId("txtObs_plazo_entrega3").value 	= data.obs_plazo_entrega;
							dojo.byId("txtObs_lugar_entrega3").value 	= data.obs_lugar_entrega;
							dojo.byId("txtObs_dir_entrega3").value 		= data.obs_dir_entrega;
							dojo.byId("txtCod_bien_Det").value 		= data.cod_bien;	
							dojo.byId("txt_bien_Det").value 		= data.des_bien	;
							dojo.byId("txtDesTec3").value 		= data.des_tecnica;

					}else{
						
						dojo.byId("txtNom_proveedor3").value 		= ""; 
						dojo.byId("txtCod_proveedor").value 		= "";
						dojo.byId("txtNum_ruc_prov3").value 		= "";
						dojo.byId("txtObs_justificacion3").value 	= "";
						dojo.byId("txtObs_plazo_entrega3").value 	= "";
						dojo.byId("txtObs_lugar_entrega3").value 	= "";
						dojo.byId("txtObs_dir_entrega3").value 		= "";	
						dojo.byId("txtCod_bien_Det").value 		= "";	
						dojo.byId("txt_bien_Det").value 		= "";	
						dojo.byId("txtDesTec3").value 		= "";
					}
					
				}else{
					var data = eval("(" + response.data + ")");
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				//alert("<fmt:message key='error.ajax'/>");
			},
			form: dojo.byId("frmRegistro")
	};		
	//dojo.xhrPost(enviar);CUIDADO SE HA CAMBIADO
	dojo.xhrGet(enviar); 
}

	

var btnModificar_item_click = function(cod_rqnp,cod_bien){
	dojo.byId("action").value = "recuperarBienModificar";
	dojo.byId("txtCodigoRqnp").value = cod_rqnp; 
	dojo.byId("txtCodigoBien").value = cod_bien; 
	dijit.byId('frmRegistro').submit();
}


function btnRecuperarContratistaDetalle(){	
	dojo.byId("action").value = "recuperarContratistaRqnpJson";
	dojo.byId("txtNum_ruc_prov").value = document.getElementById("txtNum_ruc_prov3").value;
	var val_ruc =dojo.byId("txtNum_ruc_prov3").value ;
		if ( val_ruc !=""){
		//llamada AJAX
		var enviar = {
				handleAs : "json",
				load : function(response, ioArgs) {
					
					if(response.data.error==null){
						if (response.data != "" && response.data != "[]") {
							
							var data = eval("(" + unescape(response.data )+ ")");
					
								var nombre = data.raz_social;
								
								var val_cod_cont=data.cod_cont
								if (val_cod_cont=="-1"){
									//alert("<fmt:message key='error.registrarRqnp.contratista'/>");
									
									alert(data.raz_social);
								}
								dojo.byId("txtNom_proveedor3").value = nombre; 
								dojo.byId("txtCod_proveedor3").value = data.cod_cont;
								//dojo.byId("txtNum_ruc_prov3").value = data.num_ruc;
						}
					}else{
						var data = eval("(" + response.data + ")");
						var mensaje_update = response.data.mensaje; 
						alert(mensaje_update);
					}
				},
				timeout : 25000,
				error : function(error, ioArgs) {
					
					alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
					//alert("<fmt:message key='error.ajax'/>");
					
				},
				form: dojo.byId("frmRegistro")
		};		
		dojo.xhrPost(enviar);
	}
}	



function btnSalirEntregaDetalle_click(){
	var formDlg = dijit.byId("formDialogRequerimientoDetalle");
	formDlg.hide();
}


var btnAtras_click = function(){
	if(confirm("Confirme si desea Cancelar el Registro")){
		document.frmRegistro.action="rqnpaucmetas.htm?action=modificarRqnpMetasAUC"; 
		document.frmRegistro.submit();
	}
	//document.frmRegistro.action="bandejaauc.htm?action=aucRqnpCab"; 
}

function formatear(){
	dojo.byId("txtMontoAcumulado").value =addCommas(dojo.byId("txtMontoAcumulado").value);
}

function btnGuardarEntregaDetalle_click(){	
	var formDlg = dijit.byId("formDialogRequerimientoDetalle");
	
	dojo.byId("action").value = "registrarDetalleEntregaBienJson";
	dojo.byId("txtNum_ruc_prov").value = document.getElementById("txtNum_ruc_prov3").value;
	dojo.byId("txtObs_justificacion").value = document.getElementById("txtObs_justificacion3").value;
	dojo.byId("txtObs_plazo_entrega").value = document.getElementById("txtObs_plazo_entrega3").value;
	dojo.byId("txtObs_lugar_entrega").value = document.getElementById("txtObs_lugar_entrega3").value;
	dojo.byId("txtObs_dir_entrega").value 	= document.getElementById("txtObs_dir_entrega3").value;
	dojo.byId("txtDesTec").value 	= document.getElementById("txtDesTec3").value;
	
	if(dojo.byId("txtNum_ruc_prov").value!=""  ){
		//Validar la longitud del ruc
		var val_ruc =dojo.byId("txtNum_ruc_prov").value;
		if (val_ruc.length ==11 && dojo.byId("txtCod_proveedor3").value!="-1"){
			if(dojo.byId("txtObs_justificacion").value==""  ){
				alert(msg_obs_justificacion );	
				return;
			}
		}else{
			alert(msg_cod_proveedor );	
			return;
		}
	}
	
	
	var val_nombre="";
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			headers: { "Content-Type": "application/json; charset=UTF-8"},
			load : function(response, ioArgs) {
				
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						
						var data = eval("(" +unescape( response.data )+ ")");
						var mensaje = data.mensaje;
					  		alert(mensaje);
					  		formDlg.hide();
					}
				}else{
					var data = eval("(" + response.data + ")");
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				//alert("<fmt:message key='error.ajax'/>");
				
			},
			form: dojo.byId("frmRegistro")
	};	
	
	//dojo.xhrPost(enviar);
	dojo.xhrGet(enviar);
}



var btnAceptarRechazo_click = function(rqnp,bien){
	dojo.byId("txtRechazo").value = dojo.byId("txtRechazoTem").value;
	var formDlg = dijit.byId("formDialogRechazo");
	formDlg.hide();
	dojo.byId("frmRegistro").submit();
};

var btnSalirRechazo_click = function(rqnp,bien){
	var formDlg = dijit.byId("formDialogRechazo");
	formDlg.hide();
};


var btnAtender_click = function () {
	var canal_display = dijit.byId('txtCod_canal').attr('displayedValue');
	var canal_value =dijit.byId('txtCod_canal').get('value');
	var sw='0';
	for(var i=1; i < document.frmRegistro.txtCodigobien.length; i++){
		
		if(!document.frmRegistro.txtCodigobien[i].checked ){
			
			sw='1';
		}
	}

	if (sw=='0'){
		if(confirm("Confirme si desea ATENDER los \u00EDtems Seleccionados por el canal " +canal_display )){
			dojo.byId("action").value = "contratacionRqnp";
			var ll_rtn= validarAtender();
			if (ll_rtn=="0"){
				dojo.byId("frmRegistro").submit();
			}
			//
		}
	}else{
		alert("Seleccione todos los ítems para atender");
	}
};

function validarAtender(){
	/// Verifica si el bien ya fue agregado
    var gridDet = dijit.byId('gridDetalle');
    var sw='0';

    var val_name_auc_aux='';
    if(	gridDet.store !=null){
		if(gridDet.store._arrayOfAllItems.length > 0 ){
			gridDet.store.fetch({
				onItem: function(item, request){
							if (item.vnro_adjuntos.toString() == "0") {
								alert("Se debe adjuntar los TDR/EETT/Exp.T\u00E9cnico a cada \u00EDtem del requerimiento aprobado");
								sw='-1';
							}
		 				}
			});
		}
	}
    if (sw=="-1"){
    	
    	return "-1"
    }else{
    	return "0"
    }
}

var btnRechazar_click = function(rqnp,bien){
	var sw='0';
		for(var i=0; i < document.frmRegistro.txtCodigobien.length; i++){
			if(document.frmRegistro.txtCodigobien[i].checked){
				sw='1';
			}
		}

		if (sw=='1'){
			if(confirm("Confirme si desea RECHAZAR los ítems Seleccionados")){
				dojo.byId("action").value = "rechazarRqnp";
				var formDlg = dijit.byId("formDialogRechazo");
				formDlg.show();
			}
		}else{
			alert("Seleccione los \u00EDtems que desea Rechazar");
		}
};

function getall(){
	
	var cod_rqnp;
	var cod_bien;

	var grid = dijit.byId('gridDetalle');
	var chk_txt;
	var itemIndex;

	for(var i=0; i < document.frmRegistro.txtCodigobien.length -1 ; i++){
		
		 itemIndex = grid.getItem(i);
		cod_rqnp =grid.store.getValue(itemIndex, 'vCod_rqnp') ;
		cod_bien =grid.store.getValue(itemIndex, 'vcod_bien') ;
	
		
		 chk_txt = dijit.byId("cbx"+ cod_rqnp + cod_bien);
		if (document.frmRegistro.txtcheck.checked){
			chk_txt.setChecked(true);
		}else{
			chk_txt.setChecked(false);
			//document.frmDetalleConsulta.txtCodigobien[i].checked=0;
		}
	}
}	
dojo.addOnLoad(function() {	
	dijit.byId('btnAtender').setAttribute("disabled", false);
	dijit.byId('btnAtras').setAttribute("disabled", false);
	formatear();
	
	var visor = dojo.byId("txtvisor").value;
	divShow("divbtnSiguiente");
	divShow("divbtnAtras");
	
	divShow("divbtnAtender");
	
});





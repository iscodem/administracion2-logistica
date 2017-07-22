
//INICIO: VALIDAR UUOO1 - UUOO2 - UUOO3
function btnRecuperarUUOO1(){	
	dojo.byId("action").value = "recuperarCodUUOOJson";
	dojo.byId("txtNum_UUOO").value=dojo.byId("txtNum_UUOO12").value;
	
	//alert("recuperarCodUUOOJson: " + dojo.byId("txtNum_UUOO1").value);
	if(dojo.byId("txtNum_UUOO12").value!=""){
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						
						var data = eval("(" + unescape(response.data) + ")");
					//if (response.data.error !="0"){
							var nombre = data.nom_dep;
							
							//ALERTA DE VERIFICACION DE CODIGO
							//alert("txtCod_UUOO1: (NUMERO PARA REGISTRO) " + data.cod_dep); 
							
							//dojo.byId("txtNum_UUOO12").value = data.uuoo;
							dojo.byId("txtNom_UUOO12").value = data.nom_dep; 
							dojo.byId("txtCod_UUOO1").value = data.cod_dep; 
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
	
	dojo.xhrPost(enviar);
	}else{
		dojo.byId("txtNom_UUOO12").value = "";
		dojo.byId("txtCod_UUOO1").value = "";
	}
}	

function btnRecuperarUUOO2(){	
	dojo.byId("action").value = "recuperarCodUUOOJson";
	dojo.byId("txtNum_UUOO").value=dojo.byId("txtNum_UUOO22").value;
	
	//alert("recuperarCodUUOOJson: " + dojo.byId("txtNum_UUOO2").value);
	if(dojo.byId("txtNum_UUOO22").value!=""){
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						
						var data = eval("(" + unescape(response.data) + ")");
					//	if (response.data.error !="0"){
							var nombre = data.nom_dep;
							
							/* alert("txtNum_UUOO2: (NUMERO) " + data.nom_dep); 
							alert("txtNom_UUOO2: (NOMBRE) " + data.nom_dep); */ 
							//dojo.byId("txtNum_UUOO22").value = data.uuoo;
							dojo.byId("txtNom_UUOO22").value = data.nom_dep; 
							dojo.byId("txtCod_UUOO2").value = data.cod_dep; 
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
	
	dojo.xhrPost(enviar);
	}else{
		dojo.byId("txtNom_UUOO22").value = "";
		dojo.byId("txtCod_UUOO2").value = "";
	}
}
	
function btnRecuperarUUOO3(){	
	dojo.byId("action").value = "recuperarCodUUOOJson";
	dojo.byId("txtNum_UUOO").value=dojo.byId("txtNum_UUOO32").value;
	
	//alert("recuperarCodUUOOJson: " + dojo.byId("txtNum_UUOO3").value);
	if(dojo.byId("txtNum_UUOO32").value!=""){
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						
						var data = eval("(" + unescape(response.data) + ")");
					//	if (response.data.error !="0"){
							var nombre = data.nom_dep;
							//dojo.byId("txtNum_UUOO32").value = data.uuoo;
							dojo.byId("txtNom_UUOO32").value = data.nom_dep; 
							dojo.byId("txtCod_UUOO3").value = data.cod_dep; 
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
	
	dojo.xhrPost(enviar);
	}else{
		dojo.byId("txtNom_UUOO32").value = "";
		dojo.byId("txtCod_UUOO3").value = "";
	}
}


var btnBuscar_click = function(){
	var formDlg = dijit.byId("formDialogCatalogo");
	dijit.byId("tipoConsulta2").setChecked(true);
	var _grdLstPagAnt= dijit.byId("gridBusqueda");
	document.getElementById("txtParametro").value="";
	
	formDlg.show();
	formDlg.onFocus();
	
	var parm =dijit.byId("txtParametro");
	dijit.focus(parm.domNode);
	
	var storeCatalogo = new dojo.data.ItemFileWriteStore(
			{
				data: { 
					items: []
				}
				
			}
		);
	
	if (_grdLstPagAnt==null){
		construirGrid(storeCatalogo);
	}else{
		_grdLstPagAnt.destroyRecursive()
		construirGrid(storeCatalogo);
	}
}


var btnModificar_click = function(){
	document.frmRegistro.action="rqnpauc.htm?action=modificarRqnpAUC";
	dojo.byId("frmRegistro").submit();
}


function btnBuscarCatalogo_click(){
	dojo.byId("action").value = "buscarCatalogoJson";
	dojo.byId("jParametro").value = document.getElementById("txtParametro").value;
	//dojo.byId("jtipoConsulta").value = "D";
	//AQUI BLANQUENADO GRID
	var val_parametro =document.getElementById("txtParametro").value;
	if (val_parametro.length >2) {

			var store = new dojo.data.ItemFileWriteStore(
			{
				data: 
				{ 
					items: []
				}
			}
		);
			var _grdLstPagAnt= dijit.byId("gridBusqueda");
			if (_grdLstPagAnt==null){
			
				construirGrid(store);
				
			}else{
				_grdLstPagAnt.setStore(store);
				
			}
			_grdLstPagAnt.showMessage('<div id="preloaderConsulta" align="center"><h2><span>Procesando Consulta...</span></h2></div>');
			
		//llamada AJAX
		var enviar = {
				handleAs : "json",
				load : function(response, ioArgs) {
					if(response.data.error==null){

						if (response.data != "" && response.data != "[]") {
							
							var data = eval("(" + unescape(response.data) + ")");
							
							//AQUI SE RECIBE LAS BUSQUEDAS DE CATALOGO PARA AGREGAR BIENES
							//alert("DATA DE CATALOGO es: "+data.lsCatalogo);
							actualizarCatalogo(data.lsCatalogo);
						}
					}
					else{
						alert(response.data.mensaje);
						
					}
				},
				timeout : 25000,
				error : function(error, ioArgs) {
					alert("error ajax");
					alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
					//alert("<fmt:message key='error.ajax'/>");
					
				},
				form: dojo.byId("frmRegistro")
		};	
		
		dojo.xhrPost(enviar);
	}
}


function actualizarCatalogo(lista){	
	
	var lsCatalogo =lsCatalogoItems(lista); //Conviertes la lista en JSON para mostrar en la grilla
	
	var storeCatalogo = new dojo.data.ItemFileWriteStore({
		data: {
			identifier: 'vcodigo_bien',
			items: lsCatalogo
		}});
	
	var _grdLstPagAnt= dijit.byId("gridBusqueda");
	if (_grdLstPagAnt==null){
	
		construirGrid(storeCatalogo);
		
	}else{
		_grdLstPagAnt.setStore(storeCatalogo);
	}
}

function lsCatalogoItems(items){
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
			cadena += "{vcodigo_bien:'" + item.codigo_bien + "', ";	
			cadena += "vdesc_bien:'" + item.desc_bien + "', ";
			cadena += "vdesc_unidad:'" + item.desc_unidad + "', ";
			cadena += "vtipo_bien:'" + item.tipo_bien+ "', ";
			cadena += "vcodigo_unidad:'" + item.codigo_unidad + "', ";
			cadena += "vsaldo:'" + item.saldo + "', ";
			cadena += "vxprecio_bien:'" + item.precio_bien + "', ";	
			cadena += "vprecio_bien:'" + item.precio_bien + "', ";	
			cadena += "vcodigo_gasto:'" + item.codigo_gasto  + "', ";
			cadena += "vdesc_gasto:'" + item.desc_gasto  + "', ";
			cadena += "vauct1:'" + item.auct1  + "', ";
			cadena += "vauct2:'" + item.auct2  + "', ";
			cadena += "vauct3:'" + item.auct3  + "', ";
			cadena += "vauct_name:'" + item.auct_name  + "', ";
			
			cadena += "vcod_tipo_prog:'" + item.cod_tipo_prog + "', ";
			cadena += "vcod_ambito:'" + item.cod_ambito + "', "; 
			cadena += "vestadoDesconcentrado:'" + item.estadoDesconcentrado  + "'}";
		});
		cadena += "]";
		
		//Para veriicar cadena
		//alert("cadena de CATALOGO: "+cadena);

		var lst_pag_ant = eval("(" + cadena + ")");
		return lst_pag_ant;
	} catch(e) {
		 alert("Error detectado: " + e.description)

		return [];
	}
}



function btnSalirEntregaDetalle_click(){
	var formDlg = dijit.byId("formDialogRequerimientoDetalle");
	formDlg.hide();
}

	
function delBienRqnp(cod_rqnp,cod_bien){

	dojo.byId("action").value = "eliminarBienRqnpJson";
	dojo.byId("jcodigo_rqnp").value = cod_rqnp
	dojo.byId("jcodigo_bien").value = cod_bien
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						var data = eval("(" + response.data + ")");
						var monto = data.grandMontoTotal;
						dojo.byId("txtMontoAcumulado").value = addCommas(monto); 
					}
				}else{
					
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert('error Aqui');
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				//alert("<fmt:message key='error.ajax'/>");
				
			},
			form: dojo.byId("frmRegistro")
	};	
	dojo.xhrPost(enviar);
}


function btnDel_click(){
	var grid = dijit.byId('gridDetalle');
	var cod_bien;
	var cod_rqnp;
	var sw_del='0';
	var items = grid.selection.getSelected();
	var value;
	if (confirm("Confirme si desea Eliminar el Registro del Bien")){
		if(items.length){
		    
		    dojo.forEach(items, function(selectedItem){
		        if(selectedItem !== null){
		        	 dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
		                   if(attribute=='vcodigoRqnp') { 
		                	   value = grid.store.getValues(selectedItem, attribute);
		                	   if (value!='000'){
		                		   cod_rqnp=value;
		                		   sw_del='1';
		                	   }else{
		                		   
		                	   }
		                   }
		                   if(attribute=='vcodigoBien') { 
		                	   value = grid.store.getValues(selectedItem, attribute);
		                	   cod_bien=value;
		                   }
		        	 }); 
		        	 if (sw_del=='1'){
		        		 delBienRqnp(cod_rqnp,cod_bien);
		        	 }
		            grid.store.deleteItem(selectedItem);
		        } 
		    }); 
		} 
	}
}


var btnSalir_click = function(){
	if(confirm("Confirme si desea Cancelar el Registro")){
		if(dojo.byId("txtvisor").value=="0" ){
				document.frmRegistro.action="rqnpauc.htm?action=auciniciarbandeja";
		}else{
				document.frmRegistro.action="rqnpauc.htm?action=iniciarconsulta";
		}
		document.frmRegistro.submit();
	}
}

var btnSol_click = function(){
	if(confirm("Confirme si desea Solicitar la incorporaci\u00F3n de un Bien/Servicio al Cat\u00E1logo")){
		document.frmRegistro.action="solincorbien.htm?action=iniciarSolicitud";
		document.frmRegistro.submit();
	}
}

var btnSalirCatalogo_click = function(){
	var formDlg = dijit.byId("formDialogCatalogo");
	formDlg.hide();
}

var btnCancelarDatosEntergaCabecera_click = function(){
	var formDlg = dijit.byId("formDialogRequerimiento");
	formDlg.hide();
}



var btnBuscarPersona_click_popup = function(){
	
	var formDlg = dijit.byId("formDialogPersona");
	dijit.byId("tipoPerConsulta2").setChecked(true);
	var _grdLstPagAnt= dijit.byId("gridBusquedaPersona");
	document.getElementById("txtPerParametro").value="";
	
	formDlg.show();
	formDlg.onFocus();
	
	var parm =dijit.byId("txtPerParametro");
	dijit.focus(parm.domNode);
	
	var storePersona = new dojo.data.ItemFileWriteStore(
			{
				data: 
				{}
			}
		);
	
	if (_grdLstPagAnt==null){
		construirGridPersona(storePersona);
	}else{
		_grdLstPagAnt.destroyRecursive()
		construirGridPersona(storePersona);
	}
	//document.getElementById("txtParametro").focus();
}





function tipoPerConsultaSetea(valor){
	dojo.byId("jPer_tipoConsulta").value = valor;
}




function btnBuscarPersona_click(){

	dojo.byId("action").value = "buscarPersonaJson";
	dojo.byId("jPer_parametro").value = document.getElementById("txtPerParametro").value;
	//dojo.byId("jtipoConsulta").value = "D";
	//AQUI BLANQUENADO GRID
	var val_parametro =document.getElementById("txtPerParametro").value;
	if (val_parametro.length >2) {
			var store = new dojo.data.ItemFileWriteStore({
				data: { 
					items: []
				}
			}
		);
		var _grdLstPagAnt= dijit.byId("gridBusquedaPersona");
		if (_grdLstPagAnt==null){
		
			construirGridPersona(store);
			
		}else{
			_grdLstPagAnt.setStore(store);
			
		}
		_grdLstPagAnt.showMessage('<div id="preloaderConsulta" align="center"><h2><span>Procesando Consulta...</span></h2></div>');
	
	
		//llamada AJAX
		var enviar = {
				handleAs : "json",
				load : function(response, ioArgs) {
					if(response.data.error==null){
						//alert(response.data);
						if (response.data != "" && response.data != "[]") {
							var data = eval("(" +unescape( response.data) + ")");
							actualizarPersona(data.lsPersonal);
						}
					}
					else{
						alert(response.data.mensaje);
					}
				},
				timeout : 25000,
				error : function(error, ioArgs) {
					alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				},
				form: dojo.byId("frmRegistro")
		};	
		dojo.xhrPost(enviar);
	}
	
}


function actualizarPersona(lista){	
	
	var lsPersona =lsPersonaItems(lista);
	
	var storePersona = new dojo.data.ItemFileWriteStore({
		data: {
			identifier: 'vcodigoEmpleado',
			items: lsPersona
		}});
	
	var _grdLstPagAnt= dijit.byId("gridBusquedaPersona");
	if (_grdLstPagAnt==null){
	
		construirGridPersona(storePersona);
		
	}else{
		_grdLstPagAnt.setStore(storePersona);
	}
	
}



function lsPersonaItems(items){
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
	
			cadena += "{vnumero_registro:'" + item.numero_registro + "', ";	
			cadena += "vnombre_completo:'" + item.nombre_completo + "', ";
			cadena += "vcodigoEmpleado:'" + item.codigoEmpleado + "'} ";
		});
		cadena += "]";
		//alert(cadena);	
		var lst_pag_ant = eval("(" + cadena + ")");
		return lst_pag_ant;
	} catch(e) {
		 alert("Error detectado: " + e.description)

		return [];
	}
}



function btnAceptarPersona_click(){
	var grid= dijit.byId("gridBusquedaPersona");
	
	var items = grid.selection.getSelected();
	var  val_codigoEmpleado;
	var val_nombre_completo;
	var val_numero_registro;
	
    if(items.length) {
       
        dojo.forEach(items, function(selectedItem){
            if(selectedItem !== null){
               
                dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
                   
                    var value = grid.store.getValues(selectedItem, attribute);
                    
                    switch (attribute) { 
                    case 'vcodigoEmpleado': 
                    	val_codigoEmpleado=value;
                       break ;
                    case 'vnombre_completo': 
                    	val_nombre_completo=value;
                       break ;
                  
	                case 'vnumero_registro': 
	                	val_numero_registro=value;
			            break ;
		    		} 
                }); 
            } 
        }); 

	}
    
    var  strObject = new  String(val_numero_registro); 
    if ('undefined' == strObject){
    	alert("Debe Seleccionar un Registro");
    }else{
    	 dojo.byId("txtCod_contacto").value=val_codigoEmpleado;
    	 dojo.byId("txtReg_contacto2").value=val_numero_registro;
   	    dojo.byId("txtNom_contacto2").value=val_nombre_completo;
    }
    
    var formDlg = dijit.byId("formDialogPersona");
	formDlg.hide();
}

var btnSalirPersona_click = function(){
	var formDlg = dijit.byId("formDialogPersona");
	formDlg.hide();
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



//CLICK EN ACEPTAR PARA AGREGAR ITEMS DE BIEN
function btnAceptarCatalogo_click(){
	
	var result = true;
	var msg = true;
	var sol_ok=true;
	
	var grid= dijit.byId("gridBusqueda");
	
	var items = grid.selection.getSelected();
	
	var val_codigo_bien;
	var val_desc_bien;
	var val_codigo_unidad;
	var val_desc_unidad;
	var val_precio_bien;
	var val_codigo_gasto;
	var val_desc_gasto ;
	var val_vestadoDesconcentrado;
	var val_vauct1;
	var val_vauct2;
	var val_vauct3;
	var val_auct_name;
	var val_tipo_bien;
	var val_rqnp="000";
	var val_saldo;
	var val_imagen;
	
	if(items.length) { //CANTIDAD DE ITEMS QUE FUERON SELECCIONADOS

        dojo.forEach(items, function(selectedItem){
            if(selectedItem !== null){
               
                dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
                   
                	var sw3='0'; //ALERTA DE MISMA AUC QUE ELIGE EL BIEN/ITEM
                	
                	//EN (value), SE ALMACENA VALOR POR VALOR DE CADA COLUMNA
                    var value = grid.store.getValues(selectedItem, attribute);
                    //alert("value: "+value);
                    switch (attribute) { 
                    case 'vcodigo_bien': 
                    	val_codigo_bien=value;
                       break ;
                    case 'vdesc_bien': 
                    	val_desc_bien=value;
                       break ;
                    case 'vcodigo_unidad': 
                    	val_codigo_unidad=value;
                       break ;
                    case 'vdesc_unidad': 
                    	val_desc_unidad=value;
                       break ;
                    case 'vprecio_bien': 
                    	val_precio_bien=value;
                        break ;
                    case 'vcodigo_gasto': 
                    	val_codigo_gasto=value;
                        break ;
                    case 'vdesc_gasto': 
                    	val_desc_gasto=value;
                        break ;
	                case 'vestadoDesconcentrado': 
	                	val_vestadoDesconcentrado=value;
	                    break ;
	                case 'vauct1': 
	                	val_vauct1=value;
	                    break ;
	                case 'vauct2': 
	                	val_vauct2=value;
	                    break ;
			        case 'vauct3': 
			        	val_vauct3=value;
			            break ;
	                case 'vauct_name': 
			        	val_auct_name=value;
						break ;
	                case 'vtipo_bien': 
			        	val_tipo_bien=value;
			            break ;
			            
			        //INICIO:AGREGADO
	                case 'vcod_tipo_prog': 
			        	val_cod_tipo_prog=value;
			            break ;
	                case 'vcod_ambito': 
			        	val_cod_ambito=value;
			            break ;
			        //FIN:AGREGADO
			        
	                case 'vsaldo': 
	                	val_saldo=value;
	                	
	                	val_imagen = parseFloat( val_saldo);
	                	if (val_imagen ==0){
	                		
	                		val_image='<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
	                	}else{
	                		val_image=' ';
	                	}
			            break ;
			    	} 
	                }); 
	            }
            
        	//VALIDACION
            //ALERTA DE VALIDACION DE ELECCION DE ITEM DE CATALOGO
            
        	//INSCRIBIENDO VALORES AL DOJO
        	dojo.byId("txtCod_ambito").value=val_cod_ambito;
        	dojo.byId("txtCod_tipo_prog").value=val_cod_tipo_prog;
        	
        	/*if (val_cod_tipo_prog!='03'&& msg==true){
        		alert("Alguno de los items no corresponden a la RUTA 03");
        		msg=false;
        		return result;
        	}*/
        	
        	if(val_vauct1!=cod_dep_auc && msg==true && sol_ok==true){
            		//alert("Mensaje: El(los) \u00EDtems seleccionados deben corresponder a la AUC solicitante.");
            		alert("Mensaje: El(los) \u00EDtems seleccionados deben corresponder a la unidad: "+dependenciaQueConsolida+"");
            		
            		result = false;
            		msg=false;
            		sol_ok=false;
            }

				//var tipo_auc=dojo.byId("txtTipo_auc").value; //OBTIENE EL TIPO DE AUC (T � D)
	        	
	        	switch (tipo_auc) {
	        	case 'T': 
	            	if(val_cod_ambito=='01' || val_cod_ambito=='04'){
	            		var gridDet = dijit.byId('gridDetalle');
	                    var sw='0';
	                    var sw2='0'; 
	            		var val_name_auc_aux='';
	                     if(gridDet.store !=null){
	                 		if((gridDet.store._arrayOfAllItems.length > 0 )){
	                 			gridDet.store.fetch({
	                 				onItem: function(item, request){
	                 							if (item.vcodigoBien.toString() ==val_codigo_bien.toString() ){
	                 								sw='1';
	                 							}
	                 							if (item.vauct1.toString() !=val_vauct1.toString() ){
	                 								sw2='1';
	                 								val_name_auc_aux=item.vauct_name.toString()
	                 							}
	                 		 				}
	                 			});
	                 		}
	                 	}
	            	}else{
	            		alert("Mensaje: El \u00EDtem seleccionado no corresponde al AMBITO 01 \u00F3 04");
	    				return;
	            	}
	                break ;
	        	
		        case 'D': 
		        	if(val_cod_ambito=='03' || val_cod_ambito=='04'){
		        		var gridDet = dijit.byId('gridDetalle');
		                var sw='0';
		                var sw2='0'; 
		        		var val_name_auc_aux='';
		                 if(	gridDet.store !=null){
		             		if((gridDet.store._arrayOfAllItems.length > 0 )){
		             			gridDet.store.fetch({
		             				onItem: function(item, request){
		             							if (item.vcodigoBien.toString() ==val_codigo_bien.toString() ){
		             								sw='1';
		             							}
		             							if (item.vauct1.toString() !=val_vauct1.toString() ){
		             								sw2='1';
		             								val_name_auc_aux=item.vauct_name.toString()
		             							}
		             		 				}
		             			});
		             		}
		             	}
		        	}else{
		        		alert("Mensaje: El \u00EDtem seleccionado no corresponde al AMBITO 03 \u00F3 04");
						return;
		        	}
		            break ;
		        
		        //DPORRASC - 20170313
		        case 'N': 
		        	if(val_cod_ambito=='04'){
		        		alert("Mensaje: El \u00EDtem seleccionado deber\u00E1 ser solicitado en un requerimiento regular por la dependencia que lo requiera.");
		        	}
					return;
		            break ;
		    	}	

            if (sw=='0'){
            	if (sw2=='0' && sol_ok==true){
	         		var myNewItem = {vcodigoRqnp: val_rqnp, vcodigoBien: val_codigo_bien,vxcodigoBien: val_codigo_bien, vcodigoUnidad: val_codigo_unidad, vcantBien:0,vxcantBien:0, vprecioUnid: val_precio_bien,vxprecioUnid: val_precio_bien, vdesBien:val_desc_bien 
	         				  ,vdesUnid:val_desc_unidad ,vcodClasificador:val_codigo_gasto ,vxcodClasificador:val_codigo_gasto,vdesClasificador:val_desc_gasto ,vestadoDesconcentrado:val_vestadoDesconcentrado, vprecioTotal:0,
	         				 vauct1:val_vauct1,vauct2:val_vauct2,vauct3:val_vauct3, vauct_name:val_auct_name, vtipo_bien:val_tipo_bien, vsaldo:val_saldo,vimage:val_image, vexceso:0};
	         	      
	         	    gridDet.store.newItem(myNewItem);
	         	    
	         	   var formDlg = dijit.byId("formDialogCatalogo");
	         	   formDlg.hide();
	       		
            	}else{
            		//alert("El Bien/Servicio debe Pertenecer a la AUC: "+ val_name_auc_aux+"\n. Para Registrar este �tem formule un Requerimiento adicional.");
            		return result;
            	}
         	}else{
         		if(items.length>'1'&& msg==true){
         			alert("Mensaje: Uno \u00F3 m\u00E1s \u00EDtems Bien/Servicio ya fueron Agregado");
         			return result;
         			msg=false;
         		}else{
         			alert("Mensaje: El Bien/Servicio ya fue Agregado");
         			return result;
         		}
         	}
        }); 
    }
    
//    if (sw2=='0'){
//    var formDlg = dijit.byId("formDialogCatalogo");
//		formDlg.hide();
//    }
}


var btnSiguiente_click = function(valor){
	var visor= dojo.byId("txtvisor").value 
	if (visor=="0"){
		var grid = dijit.byId('gridDetalle');
		var sw="0";
		var count = parseInt(0);
		if(	grid.store ==null){
			alert("Por favor ingrese los \u00EDtems del requerimiento para ser consolidado.");
			return ;
		}else{
			if((grid.store._arrayOfAllItems.length > 0 )){
				grid.store.fetch({
					onItem: function(item, request){
						var val_precio = parseFloat( item.vprecioUnid);
						count ++;
						if (val_precio ==0){
							sw="1";
						}
			 		}
				});
			}
		}
		
		if (count==0){
			alert("Por favor ingrese los \u00EDtems del requerimiento para ser consolidado");
				return ;
		}
		
		if (sw=="1"){
			alert("El Precio Unitario debe ser mayor que cero");
			return ;
		}
		
		if(!dijit.byId('frmRegistro').validate()){
			return;
		}

	  	//dijit.byId('btnGuardar').setAttribute("disabled", true);
		//dojo.byId("action").value 		= "registrarDetalleRqnpAUC";
	
	}else{
		dojo.byId("action").value 	= "modificarRqnpMetasAUC";
	}
	dojo.byId("jParametro").value 	= "formRegistroRqnpMetasAUC"; //ESTE DATO TOMA (registrarRqnpDetalleAUC) PARA DERIVAR A (formRegistroRqnpMetaAUC.jsp)
	
	//dojo.byId("frmRegistro").submit();

	btnValidarContacto();
};
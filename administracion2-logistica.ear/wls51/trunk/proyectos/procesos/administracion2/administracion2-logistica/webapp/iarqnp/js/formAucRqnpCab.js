
   	


   	var btnSalir_click = function(){
		if(confirm("Confirme si desea Cancelar el Registro")){
			document.frmRegistro.action="bandejaauc.htm?action=bandejaauc";
			document.frmRegistro.submit();
		}
	}
   	

	function btnRecuperarContacto(){	
   			
   			dojo.byId("action").value = "recuperarContactoRqnpJson";
   			//dojo.byId("jtxtReg_contacto").value = document.getElementById("txtReg_contacto").value;
   			var val_reg =dojo.byId("txtReg_contacto").value ;
   			if ( val_reg !=""){
   			//llamada AJAX
   			var enviar = {
   					handleAs : "json",
   					load : function(response, ioArgs) {
   						
   						if(response.data.error==null){
   							if (response.data != "" && response.data != "[]") {
   								
   								var data = eval("(" +unescape( response.data )+ ")");
   							//	if (response.data.error !="0"){
   									var nombre = data.nom_contacto;
   									var val_cod_contacto= data.cod_contacto
   									if (val_cod_contacto=="-1"){
   										alert(msg_contacto );
   										dojo.byId("txtNom_contacto").value = ""; 
   										dojo.byId("txtReg_contacto").value = ""; 
   										dojo.byId("txtCod_contacto").value = data.cod_contacto;
   									}else{
   										dojo.byId("txtNom_contacto").value = nombre; 
   										dojo.byId("txtCod_contacto").value = data.cod_contacto;
   									}
   							}
   						}else{
   							var data = eval("(" + response.data + ")");
   							alert(response.data.mensaje);
   						}
   					},
   					timeout : 25000,
   					error : function(error, ioArgs) {
   						alert("Error [" + error.message + "]");
   					},
   					form: dojo.byId("frmRegistro")
   			};	
   			
   			dojo.xhrPost(enviar);
   			
   			}else{
   				dojo.byId("txtNom_contacto").value = ""; 
   			}

   		}	

	
	
	function	btnRecuperarVinculo(){
		
		var val_tipo_vinculo =dijit.byId('txtTipo_vinculo').attr('value'); 
				
		var formQuery ='&jtipo_vinculo='+val_tipo_vinculo ;
		
		
		var URL = CONTEXTO_APP + '/bandejaauc.htm?action=listarVinculoJson'+formQuery
		   var txtVinculo = dijit.byId("txtVinculo");
		    var kw0 = {
		    		handleAs : "json",
		    		headers: { "Content-Type": "application/json; charset=UTF-8"},
		    		load : function(response, ioArgs) {
		    				var store = new dojo.data.ItemFileReadStore({data:response});
		    				//dijit.byId("txtVinculo").value='';
		    				dijit.byId("txtVinculo").reset();
		    				dijit.byId('txtVinculo').set('placeHolder','Seleccione un Vínculo');
		    				//dijit.byId('txtVinculo').set('displayedValue','');
		    				
		    				//dijit.byId('txtVinculo').get('displayedValue')='';
		    				txtVinculo.store =store;				
		    		},
		    		preventCache : true,
		    		timeout : 10000,
		    		error : function(error, ioArgs) {
		    			//alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
		    			alert("Ha ocurrido el siguiente error:" + error.message + " " +  ioArgs.xhr.status);   
		    		},
		    		url: URL,
		    		sync:true
    };
    dojo.xhrGet(kw0);
}
		
	
   function btnRecuperarUUOO1(){	
   			dojo.byId("action").value = "recuperarCodUUOOJson";
   			var val_num_oouu1 =dojo.byId("txtNum_UUOO1").value ;
   			if ( val_num_oouu1 !=""){
   				dojo.byId("txtNum_UUOO").value =val_num_oouu1;
   			//llamada AJAX
   			var enviar = {
   					handleAs : "json",
   					load : function(response, ioArgs) {
   						if(response.data.error==null){
   							if (response.data != "" && response.data != "[]") {
   								
   								var data = eval("(" + response.data + ")");
   							//	if (response.data.error !="0"){
   									var nombre = data.nom_dep;
   									var val_cod_cont=data.cod_dep
   									if (val_cod_cont=="-1"){
   										var obj=JSON.parse(response.data)
   										alert(obj.mensaje);
   										dojo.byId("txtNom_UUOO1").value = ""; 
   										dojo.byId("txtNum_UUOO1").value = ""; 
   										dojo.byId("txtCod_uuoo1").value = data.cod_dep;
   										
   									}else{
   										dojo.byId("txtNom_UUOO1").value = nombre; 
   										dojo.byId("txtCod_uuoo1").value = data.cod_dep;
   										
   									}
   							}
   						}else{
   							var data = eval("(" + response.data + ")");
   							alert(response.data.mensaje);
   						}
   					},
   					timeout : 25000,
   					error : function(error, ioArgs) {
   						
   						alert("Error [" + error.message + "]");
   						//alert("<fmt:message key='error.ajax'/>");
   						
   					},
   					form: dojo.byId("frmRegistro")
   			};	
   			
   			dojo.xhrPost(enviar);
   			
   			}else{
   				dojo.byId("txtNom_UUOO1").value = ""; 
   			}
   		}
   			
   	function btnRecuperarUUOO2(){	
   			dojo.byId("action").value = "recuperarCodUUOOJson";
   			var val_num_oouu2 =dojo.byId("txtNum_UUOO2").value ;
   			if ( val_num_oouu2 !=""){
   			//llamada AJAX
   			dojo.byId("txtNum_UUOO").value =val_num_oouu2;
   			var enviar = {
   					handleAs : "json",
   					load : function(response, ioArgs) {
   						
   						if(response.data.error==null){
   							if (response.data != "" && response.data != "[]") {
   								
   								var data = eval("(" + response.data + ")");
   							//	if (response.data.error !="0"){
   									var nombre = data.nom_dep;
   									var val_cod_cont=data.cod_dep
   									if (val_cod_cont=="-1"){
   										var obj=JSON.parse(response.data)
   										alert(obj.mensaje);
   										dojo.byId("txtNom_UUOO2").value = ""; 
   										dojo.byId("txtNum_UUOO2").value = "";
   										dojo.byId("txtCod_uuoo2").value = data.cod_dep;
   										
   									}else{
   										dojo.byId("txtNom_UUOO2").value = nombre; 
   										dojo.byId("txtCod_uuoo2").value = data.cod_dep;
   										
   									}
   							}
   						}else{
   							var data = eval("(" + response.data + ")");
   							alert(response.data.mensaje);
   						}
   					},
   					timeout : 25000,
   					error : function(error, ioArgs) {
   						alert("Error [" + error.message + "]");
   					
   						//alert("<fmt:message key='error.ajax'/>");
   						
   					},
   					form: dojo.byId("frmRegistro")
   			};	
   			
   			dojo.xhrPost(enviar);
   			
   			}else{
   				dojo.byId("txtNom_UUOO2").value = ""; 
   			}
   		}	


	function btnRecuperarUUOO3(){	
		dojo.byId("action").value = "recuperarCodUUOOJson";
		var val_num_oouu3 =dojo.byId("txtNum_UUOO3").value ;
		if ( val_num_oouu3 !=""){
		//llamada AJAX
		dojo.byId("txtNum_UUOO").value =val_num_oouu3;
		var enviar = {
				handleAs : "json",
				load : function(response, ioArgs) {
					
					if(response.data.error==null){
						if (response.data != "" && response.data != "[]") {
							
							var data = eval("(" + response.data + ")");
						//	if (response.data.error !="0"){
								var nombre = data.nom_dep;
								var val_cod_cont=data.cod_dep
								if (val_cod_cont=="-1"){
									var obj=JSON.parse(response.data)
									alert(obj.mensaje);
									dojo.byId("txtNom_UUOO3").value = ""; 
									dojo.byId("txtNum_UUOO3").value = ""; 
									dojo.byId("txtCod_uuoo3").value = data.cod_dep;
									
								}else{
									dojo.byId("txtNom_UUOO3").value = nombre; 
									dojo.byId("txtCod_uuoo3").value = data.cod_dep;
									
								}
						}
					}else{
						var data = eval("(" + response.data + ")");
						alert(response.data.mensaje);
					}
				},
				timeout : 25000,
				error : function(error, ioArgs) {
					alert("Error [" + error.message + "]");
					//alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
					//alert("<fmt:message key='error.ajax'/>");
					
				},
				form: dojo.byId("frmRegistro")
		};	
		
		dojo.xhrPost(enviar);
		
		}else{
			dojo.byId("txtNom_UUOO3").value = ""; 
		}
	}
		
   			
	function btnRecuperarContratista(){	
		
		dojo.byId("action").value = "recuperarContratistaRqnpJson";
		//dojo.byId("jtxtReg_contacto").value = document.getElementById("txtReg_contacto").value;
		var val_ruc =dojo.byId("txtNum_ruc_prov").value ;
		if ( val_ruc !=""){
		//llamada AJAX
		var enviar = {
				handleAs : "json",
				load : function(response, ioArgs) {
					
					if(response.data.error==null){
						if (response.data != "" && response.data != "[]") {
							
							var data = eval("(" + response.data + ")");
						//	if (response.data.error !="0"){
								var nombre = data.raz_social;
								var val_cod_cont=data.cod_cont
								if (val_cod_cont=="-1"){
									//alert("<fmt:message key='error.registrarRqnp.contratista'/>");
									alert(data.raz_social);
									dojo.byId("txtNom_proveedor").value = ""; 
									dojo.byId("txtNum_ruc_prov").value = ""; 
									dojo.byId("txtCod_proveedor").value = data.cod_cont;
									divHide("divObs_justificacion");
									divHide("divObs_justificacion_label");
								}else{
									dojo.byId("txtNom_proveedor").value = nombre; 
									dojo.byId("txtCod_proveedor").value = data.cod_cont;
									divShow("divObs_justificacion");
									divShow("divObs_justificacion_label");
									//dojo.byId("txtNum_ruc_prov").value = data.num_ruc;
								}
								
								
						}
					}else{
						var data = eval("(" + response.data + ")");
						alert(response.data.mensaje);
					}
				},
				timeout : 25000,
				error : function(error, ioArgs) {
					alert("Error [" + error.message + "]");
				
					//alert("<fmt:message key='error.ajax'/>");
					
				},
				form: dojo.byId("frmRegistro")
		};	
		
		dojo.xhrPost(enviar);
		
		}else{
			dojo.byId("txtNom_proveedor").value = ""; 
		}
	}	

   			
   			
	function btnValidarGuardar(){	
		

		dojo.byId("action").value = "validarGuardarCabJson";
		//dojo.byId("jtxtReg_contacto").value = document.getElementById("txtReg_contacto").value;
		var val_nombre="";
		//llamada AJAX
		var enviar = {
				handleAs : "json",
				load : function(response, ioArgs) {
					
					if(response.data.error==null){
						if (response.data != "" && response.data != "[]") {
							
							var data = eval("(" + response.data + ")");
							var nombre = data.nom_contacto;
							var val_cod_cont = data.cod_cont;
							if (nombre=="-1"){						
								dojo.byId("txtCod_contacto").value = "";
						  		alert("<fmt:message key='error.registrarRqnp.contacto'/>" );	
						  	}else if (val_cod_cont=="-1"){	
						  		dojo.byId("txtCod_proveedor").value = "";
						  		//alert("<fmt:message key='error.registrarRqnp.contratista'/>");
						  		alert(data.raz_social);
						  	}else{
						  		dojo.byId("action").value = "registrarCabRqnp";
						  		dijit.byId('btnGuardar').setAttribute("disabled", true);
						  		dijit.byId('frmRegistro').submit();
						  	}
						}
					}else{
						var data = eval("(" + response.data + ")");
						alert(response.data.mensaje);
					}
				},
				timeout : 25000,
				error : function(error, ioArgs) {
					
					//alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
					//alert("<fmt:message key='error.ajax'/>");
					alert("Error [" + error.message + "]");
				},
				form: dojo.byId("frmRegistro")
		};	
		
		dojo.xhrPost(enviar);
		
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
					{items: [
								
								]
				}
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
		var val_parametro= document.getElementById("txtPerParametro").value;
		var val_tipo_con =document.getElementById("jPer_tipoConsulta").value;
		
	
		if (val_parametro.length >2) {
			
		
		var formQuery ='&jPer_parametro='+val_parametro +'&jPer_tipoConsulta='+val_tipo_con;
		var URL = CONTEXTO_APP + '/bandejaauc.htm?action=buscarPersonaJson2'+formQuery
		   var grilla = dijit.byId("gridBusquedaPersona");
		
			grilla.showMessage('<div id="preloaderConsulta" align="center"><h2><span>Procesando Consulta...</span></h2></div>');
		
		    var kw0 = {
		    		handleAs : "json",
		    		headers: { "Content-Type": "application/json; charset=UTF-8"},
		    		load : function(response, ioArgs) {
		    				var store = new dojo.data.ItemFileReadStore({data:response});
		    			 
		    			    grilla.setStore(store);				
		    			
		    		},
		    		preventCache : true,
		    		timeout : 10000,
		    		error : function(error, ioArgs) {
		    			//alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
		    			alert("Ha ocurrido el siguiente error:" + error.message + " " +  ioArgs.xhr.status);   
		    		},
		    		url: URL,
		    		sync:true
		    };
		    dojo.xhrGet(kw0);
		    
		   
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
	                    case 'codigoEmpleado': 
	                    	val_codigoEmpleado=value;
	                       break ;
	                    case 'nombre_completo': 
	                    	val_nombre_completo=value;
	                       break ;
	                  
		                case 'numero_registro': 
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
	    	 dojo.byId("txtReg_contacto").value=val_numero_registro;
	   	    dojo.byId("txtNom_contacto").value=val_nombre_completo;
	    }
	    
	    var formDlg = dijit.byId("formDialogPersona");
		formDlg.hide();
	}

	var btnSalirPersona_click = function(){
		var formDlg = dijit.byId("formDialogPersona");
		formDlg.hide();
	}
	
	function btnDescargar_click(){
	
	
		document.frmBandejaAuc.action="bandejaauc.htm?action=descargarArchivoAnexo";
		//document.frmBandejaAuc.action="rqnpconsulta.htm?action=iniciaListaAcciones";	
		document.frmBandejaAuc.submit();
		
		
	}
	
	
//AGREGADO PARA EL PASE - MIEMBROS DEL COMITE
   	
	//TIPO DE REGISTRO (SI/NO) - SI REGISTRA O NO
    function tipoRegistroSeteaComite(valor){
      	dojo.byId("jtipoRegistroComite").value = valor;
      	if (valor=="N"){
      		if(confirm("Los miembros del comit\u00E9 ser\u00E1n borrados, desea continuar?...")){
          		divHide("divComite");
          		dojo.byId("txtNum_au_tit").value="";
          		dojo.byId("txtNom_au_tit").value="";
          		dojo.byId("txtNum_au_sup").value="";
          		dojo.byId("txtNom_au_sup").value="";
          		
          		dojo.byId("txtNum_tec_tit").value="";
          		dojo.byId("txtNom_tec_tit").value="";
          		dojo.byId("txtNum_tec_sup").value="";
          		dojo.byId("txtNom_tec_sup").value="";
      		}
      		
      	}
      	if (valor=="S"){
      		divShow("divComite");
      		//VARICLES PARA EL COMITE
      		
      		if(val_cind_tec_tit=='S'){
      			dijit.byId("optIndTecTitSi").set('checked', true);
      			dijit.byId("optIndTecTitNo").set('checked', false);
      			dijit.byId('txtNum_tec_tit').setAttribute("disabled", false);
          		dijit.byId('txtNom_tec_tit').setAttribute("disabled", true);
      		}else{
      			dijit.byId("optIndTecTitSi").set('checked', false);
      			dijit.byId("optIndTecTitNo").set('checked', true);
      			dijit.byId('txtNum_tec_tit').setAttribute("disabled", true);
          		dijit.byId('txtNom_tec_tit').setAttribute("disabled", false);
      		}
      		
      		if(val_cind_tec_sup=='S'){
      			dijit.byId("optIndTecSupSi").set('checked', true);
      			dijit.byId("optIndTecSupNo").set('checked', false);
      			dijit.byId('txtNum_tec_sup').setAttribute("disabled", false);
          		dijit.byId('txtNom_tec_sup').setAttribute("disabled", true);
      		}else{
      			dijit.byId("optIndTecSupSi").set('checked', false);
      			dijit.byId("optIndTecSupNo").set('checked', true);
      			dijit.byId('txtNum_tec_sup').setAttribute("disabled", true);
          		dijit.byId('txtNom_tec_sup').setAttribute("disabled", false);
      		}
      	}
      }
    

  //TIPO DE REGISTRO (SI/NO) - INDICADOR DE TECNICO DE COMITE
    function tipoRegistroSeteaTecTit(valor){
      	dojo.byId("jtipoRegistroComiteTecTit").value = valor;
      	if (valor=="N"){
      		if(confirm("Desea borrar los datos del especialista Técnico miembro Titular")){
      			dojo.byId("txtNum_tec_tit").value="";
          		dojo.byId("txtNom_tec_tit").value="";
      		}
      		
      		dijit.byId('txtNum_tec_tit').setAttribute("disabled", true);
      		dijit.byId('txtNom_tec_tit').setAttribute("disabled", false);
      	}
      	if (valor=="S"){
      		divShow("divComite");
      		if(confirm("Desea borrar los datos del especialista T�cnico miembro Titular")){
      			dojo.byId("txtNum_tec_tit").value="";
          		dojo.byId("txtNom_tec_tit").value="";
      		}
      		
      		dijit.byId('txtNum_tec_tit').setAttribute("disabled", false);
      		dijit.byId('txtNom_tec_tit').setAttribute("disabled", true);
      	}
      }
    
    function tipoRegistroSeteaTecSup(valor){
      	dojo.byId("jtipoRegistroComiteTecSup").value = valor;
      	if (valor=="N"){
      		if(confirm("Desea borrar los datos del especialista T�cnico miembro Suplente")){
      			dojo.byId("txtNum_tec_sup").value="";
          		dojo.byId("txtNom_tec_sup").value="";
      		}
      		
      		dijit.byId('txtNum_tec_sup').setAttribute("disabled", true);
      		dijit.byId('txtNom_tec_sup').setAttribute("disabled", false);
      	}
      	if (valor=="S"){
      		divShow("divComite");
      		if(confirm("Desea borrar los datos del especialista T�cnico miembro Suplente")){
      			dojo.byId("txtNum_tec_sup").value="";
          		dojo.byId("txtNom_tec_sup").value="";
      		}
      		
      		dijit.byId('txtNum_tec_sup').setAttribute("disabled", false);
      		dijit.byId('txtNom_tec_sup').setAttribute("disabled", true);
      	}
      	

      }

    //AU TITULAR
   	function btnRecuperarComite_au_tit(){	
   	  	
   		//var  val_cod_au_tit= dijit.byId("txtCod_au_tit");
   		
   	  	dojo.byId("action").value = "recuperarComiteRqnpJson";
   	  	dojo.byId("txtNum_user_comite").value=dojo.byId("txtNum_au_tit").value;
   	  	
   	  	var val_num_user_comite =dojo.byId("txtNum_au_tit").value ;
   	  	if ( val_num_user_comite !=""){
   	  	//llamada AJAX
   	  	var enviar = {
   	  			handleAs : "json",
   	  			load : function(response, ioArgs) {
   	  				
   	  				if(response.data.error==null){
   	  					if (response.data != "" && response.data != "[]") {
   	  						
   	  						var data = eval("(" +unescape( response.data )+ ")");
   	  					//	if (response.data.error !="0"){
   	  							var nombre = data.nom_contacto;
   	  							var val_cod_user_comite= data.cod_contacto
   	  							if (val_cod_user_comite=="-1"){
   	  								alert(msg_contacto);
   	  								dojo.byId("txtNom_au_tit").value = nombre; 
   	  								dojo.byId("txtCod_au_tit").value = data.cod_contacto;
   	  							}else{
   	  								dojo.byId("txtNom_au_tit").value = nombre; 
   	  								dojo.byId("txtCod_au_tit").value = data.cod_contacto;
   	  							}
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
   	  		dojo.byId("txtNom_au_tit").value = ""; 
   	  	}

   	  }	
   	
   	
  //AU SUPLENTE
   	function btnRecuperarComite_au_sup(){	
   	  	
   		//var  val_cod_au_tit= dijit.byId("txtCod_au_tit");
   		
   	  	dojo.byId("action").value = "recuperarComiteRqnpJson";
   	 	dojo.byId("txtNum_user_comite").value=dojo.byId("txtNum_au_sup").value;

   	 	var val_num_user_comite =dojo.byId("txtNum_au_sup").value ;
   	 	
   	  	if ( val_num_user_comite !=""){
   	  	//llamada AJAX
   	  	var enviar = {
   	  			handleAs : "json",
   	  			load : function(response, ioArgs) {
   	  				
   	  				if(response.data.error==null){
   	  					if (response.data != "" && response.data != "[]") {
   	  						
   	  						var data = eval("(" +unescape( response.data )+ ")");
   	  					//	if (response.data.error !="0"){
   	  							var nombre = data.nom_contacto;
   	  							var val_cod_user_comite= data.cod_contacto
   	  							if (val_cod_user_comite=="-1"){
   	  								alert(msg_contacto);
   	  								dojo.byId("txtNom_au_sup").value = nombre; 
   	  								dojo.byId("txtCod_au_sup").value = data.cod_contacto;
   	  							}else{
   	  								dojo.byId("txtNom_au_sup").value = nombre; 
   	  								dojo.byId("txtCod_au_sup").value = data.cod_contacto;
   	  							}
   	  							
   	  					
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
   	  		dojo.byId("txtNom_au_sup").value = ""; 
   	  	}

   	  }	
   	
   	
    //TECNICO TITULAR
   	function btnRecuperarComite_tec_tit(){	
   	  	
   		//var  val_cod_au_tit= dijit.byId("txtCod_au_tit");
   		
   	  	dojo.byId("action").value = "recuperarComiteRqnpJson";
   	  	dojo.byId("txtNum_user_comite").value=dojo.byId("txtNum_tec_tit").value;
   	  	
   	  	var val_num_user_comite =dojo.byId("txtNum_tec_tit").value ;
   	  	if ( val_num_user_comite !=""){
   	  	//llamada AJAX
   	  	var enviar = {
   	  			handleAs : "json",
   	  			load : function(response, ioArgs) {
   	  				
   	  				if(response.data.error==null){
   	  					if (response.data != "" && response.data != "[]") {
   	  						
   	  						var data = eval("(" +unescape( response.data )+ ")");
   	  					//	if (response.data.error !="0"){
   	  							var nombre = data.nom_contacto;
   	  							var val_cod_user_comite= data.cod_contacto
   	  							if (val_cod_user_comite=="-1"){
   	  								alert(msg_contacto);
   	  								dojo.byId("txtNom_tec_tit").value = nombre; 
   	  								dojo.byId("txtCod_tec_tit").value = data.cod_contacto;
   	  							}else{
   	  								dojo.byId("txtNom_tec_tit").value = nombre; 
   	  								dojo.byId("txtCod_tec_tit").value = data.cod_contacto;
   	  							}
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
   	  		dojo.byId("txtNom_tec_tit").value = ""; 
   	  	}

   	  }	
   	
   	
  //TECNICO SUPLENTE
   	function btnRecuperarComite_tec_sup(){	
   	  	
   		//var  val_cod_au_tit= dijit.byId("txtCod_au_tit");
   		
   	  	dojo.byId("action").value = "recuperarComiteRqnpJson";
   	 	dojo.byId("txtNum_user_comite").value=dojo.byId("txtNum_tec_sup").value;

   	 	var val_num_user_comite =dojo.byId("txtNum_tec_sup").value ;
   	 	
   	  	if ( val_num_user_comite !=""){
   	  	//llamada AJAX
   	  	var enviar = {
   	  			handleAs : "json",
   	  			load : function(response, ioArgs) {
   	  				
   	  				if(response.data.error==null){
   	  					if (response.data != "" && response.data != "[]") {
   	  						
   	  						var data = eval("(" +unescape( response.data )+ ")");
   	  					//	if (response.data.error !="0"){
   	  							var nombre = data.nom_contacto;
   	  							var val_cod_user_comite= data.cod_contacto
   	  							if (val_cod_user_comite=="-1"){
   	  								alert(msg_contacto);
   	  								dojo.byId("txtNom_tec_sup").value = nombre; 
   	  								dojo.byId("txtCod_tec_sup").value = data.cod_contacto;
   	  							}else{
   	  								dojo.byId("txtNom_tec_sup").value = nombre; 
   	  								dojo.byId("txtCod_tec_sup").value = data.cod_contacto;
   	  							}
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
   	  		dojo.byId("txtNom_tec_sup").value = ""; 
   	  	}
   	  }

///////////////////////////////////
  //TIPO DE REGISTRO (SI/NO) - SI REGISTRA O NO
    function tipoRegistroSeteaVinculo(valor){
    	dojo.byId("jtipoRegistroVinculo").value = valor;
      	if (valor=="N"){
  	  		divHide("divTipoVinculoLabel");
  	  		divHide("divTipoVinculo");
  	  		divHide("divVinculoLabel");
  	  		divHide("divVinculo");
      		//dojo.byId("txtTipo_vinculo").value="";
      		//dojo.byId("txtVinculo").value="";
      		dijit.byId("txtTipo_vinculo").reset();
      		dijit.byId('txtTipo_vinculo').set('placeHolder','Seleccione un Tipo de Vínculo');
      		dijit.byId("txtVinculo").reset();
			dijit.byId('txtVinculo').set('placeHolder','Seleccione un V�nculo');
      	}
      	
      	if (valor=="S"){
      		divShow("divTipoVinculoLabel");
      		divShow("divTipoVinculo");
      		divShow("divVinculoLabel");
      		divShow("divVinculo");
      		dijit.byId("txtTipo_vinculo").reset();
      		dijit.byId('txtTipo_vinculo').set('placeHolder','Seleccione un Tipo de Vínculo');
      		dijit.byId("txtVinculo").reset();
			dijit.byId('txtVinculo').set('placeHolder','Seleccione un V�nculo');
      	}
      }
  //TIPO DE REGISTRO (SI/NO) - SI REGISTRA O NO
  function tipoRegistroSeteaPrestamo(valor){
    	dojo.byId("jtipoRegistroPrestamo").value = valor;
  	}
///////////////////////////////////////////////////////
  function verVinculo(registro_vinculo){
			if(registro_vinculo=="S"){
				divShow("divTipoVinculoLabel");
				divShow("divTipoVinculo");
				divShow("divVinculoLabel");
				divShow("divVinculo");
				dijit.byId("optVinculoSi").set('checked', 'checked');
			}else if (registro_vinculo=="N"){
				dijit.byId("optVinculoNo").set('checked', 'checked');
				divHide("divTipoVinculoLabel");
				divHide("divTipoVinculo");
				divHide("divVinculoLabel");
				divHide("divVinculo");
				//dijit.byId('txtNum_tec_sup').setAttribute("disabled", true);
			}
  }
  function verPrestamo(registro_prestamo){
		if(dojo.byId("jtipoRegistroPrestamo").value=="S"){
				dijit.byId("optPrestamoSi").set('checked', 'checked');
			}else{
				dijit.byId("optPrestamoNo").set('checked', 'checked');
		}

  }
 
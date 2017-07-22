dojo.require("dijit.form.Form");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.Dialog"); 
	dojo.require("dojo.date.locale");
    dojo.require("dijit.form.RadioButton");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dojo.data.ItemFileWriteStore");
   	dojo.require("dojo.data.ItemFileReadStore");
   	dojo.require("dojox.grid.EnhancedGrid");
    dojo.require("dojox.grid.enhanced.plugins.Pagination");
    dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
  	dojo.require("dijit.form.SimpleTextarea");
   	dojo.require("dijit.form.FilteringSelect");
   	dojo.require("dojo.store.Memory");
   	dojo.require("dojo._base.connect");
   	dojo.require("dojo._base.event");
   	dojo.require("dojo._base.array");
   	
   	dojo.addOnLoad(function() {	
   		dijit.byId('btnGuardar').setAttribute("disabled", false);
   		dijit.byId('btnSalir').setAttribute("disabled", false);
   		dijit.byId("txtMotivo").focus();
   		
   		//MIEMBROS DEL COMITE
   		dijit.byId("optIndTecTitSi").set('checked', true);
		dijit.byId("optIndTecSupSi").set('checked', true);
   			
   		var storeFinalidad = new dojo.data.ItemFileWriteStore({
   	        data: { 
   	        	identifier: 'id',
   	        	label: 'name',
   				items: vlsFinalidad
   			}
   		}); 
   		
   		var comboBoxFinalidad = 
   			new dijit.form.FilteringSelect({
   			id: "txtCod_finalidad",
   			name: "txtCod_finalidad",
   	       
   	    value:val_finalidad,
   	       style: "width: 250px;",
   	       placeHolder: 'Seleccione una Finalidad',
   	       store: storeFinalidad,
   	       searchAttr: "name"
   	   }, dojo.byId("txtCod_finalidad"));
   		
   		
   		var storeNecesidad = new dojo.data.ItemFileWriteStore({
   	        data: { 
   	        	identifier: 'id',
   	        	  label: 'name',

   				items: vlsNecesidad
   			}
   		}); 
   		
   		
   		
   		var comboBoxNecesidad = new dijit.form.FilteringSelect({
   	        id: "txtCod_necesidad",
   	        name: "txtCod_necesidad",
   	     value:val_necesidad,
   	        style: "width: 250px;",
   	        placeHolder: 'Seleccione una Necesidad',
   	        store: storeNecesidad,
   	        searchAttr: "name"
   	    }, dojo.byId("txtCod_necesidad"));

   		
   		
   		var storeTipoNecesidad = new dojo.data.ItemFileWriteStore({
   	        data: { 
   	        	identifier: 'id',
   	        	  label: 'name',

   				items: vlsTipoNecesidad
   			}
   		}); 
   		
   		var  val_anexo= dijit.byId("txtAnexo_contacto");
   		
   		dojo.connect(val_anexo,'onKeyPress',function(e){  
   			//var tecla = (document.all) ? e.keyCode : e.which;
   			//tecla = e.keyCode || e.which;
   			var tecla = e.which || e.keyCode;
   			if (tecla==8) return true;
   			patron =/[0-9]/;
   			
   		//alert(tecla);
   			te = String.fromCharCode(tecla);
   			//alert(te);
   			if(! patron.test(te)){
   				//alert('no');
   				dojo.stopEvent(e);
   			}
   			
   		});
   		//Caja de Texto de Contacto

   		var  val_cod_contacto= dijit.byId("txtReg_contacto");
   		 
   			dojo.connect(val_cod_contacto,'onKeyPress',function(evt){  
   				
   				var key = evt.which || evt.keyCode;
   			    if (key == 13 ){
   			    	btnRecuperarContacto();
   			    }
   			    
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			    
   			});
   			//Caja de Texto de CONTRATISTA	
   		var  val_cod_contratista= dijit.byId("txtNum_ruc_prov");
   			 
   			dojo.connect(val_cod_contratista,'onKeyPress',function(evt){  
   				
   				var key = evt.which || evt.keyCode;
   			    if (key == 13 ){
   			    	btnRecuperarContratista();
   			
   			    }
   			    
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			});
   			
   			
   			dojo.connect(val_cod_contacto,'onChange',function(evt){  
   				btnRecuperarContacto();
   			});
   			
   			
   			
   			var  val_nom_tipo= dijit.byId("txtNom_tip_necesidad");
   			
   			dojo.connect(val_nom_tipo,'onKeyPress', function(evt){  
   				
   				var key = evt.which || evt.keyCode;
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			    
   			});
   			
   			var  val_per_parametro= dijit.byId("txtPerParametro");
   			
   			dojo.connect(val_per_parametro,'onKeyPress', function(evt){  
   				
   				var key = evt.which || evt.keyCode;
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			    
   			});
   			
   			var storeMaestro = new dojo.data.ItemFileWriteStore(
   					{
   						
   						data: 
   						{ 
   							
   							items: vlsPersonal
   						}
   					}
   				);
   				
   			var  val_tipo_vinculo= dijit.byId("txtTipo_vinculo");
     		 
   			dojo.connect(val_tipo_vinculo,'onChange', function(evt){  
   					btnRecuperarVinculo();
				   	
   			});
   			//validar UUOO1
   			//CLICK EN UUOO SELECCIONADA
   			var val_num_uuoo= dijit.byId("txtNum_UUOO1");
   			dojo.connect(val_num_uuoo,'onKeyPress',
   				function(evt){  
   				var key = evt.which || evt.keyCode;
   			    if (key == 13 ){
   			    	btnRecuperarUUOO1();
   			    }
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			});

   			dojo.connect(val_num_uuoo,'onChange', function(evt){  
   				btnRecuperarUUOO1();
   			});
   			
   			//validar UUOO2
   			//CLICK EN UUOO SELECCIONADA
   			var val_num_uuoo= dijit.byId("txtNum_UUOO2");

   			dojo.connect(val_num_uuoo,'onKeyPress', function(evt){  
   				var key = evt.which || evt.keyCode;
   			    if (key == 13 ){
   			    	btnRecuperarUUOO2();
   			    }
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			}
   			);

   			dojo.connect(val_num_uuoo,'onChange',function(evt){  
   				btnRecuperarUUOO2();
   			});
   			
   			//validar UUOO3
   			//CLICK EN UUOO SELECCIONADA
   			var val_num_uuoo= dijit.byId("txtNum_UUOO3");
   			dojo.connect(val_num_uuoo,'onKeyPress',
   				function(evt){  
   				var key = evt.which || evt.keyCode;
   			    if (key == 13 ){
   			    	btnRecuperarUUOO3();
   			    }
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			}
   			);

   			dojo.connect(val_num_uuoo,'onChange',
   				    function(evt){  
   				btnRecuperarUUOO3();
   			}
   			);
   			
   			//AGREGADO PARA EL PASE - VALIDACION DEL COMITE ESPECIAL
   			//AU TITULAR
   			var  val_num_user_comite= dijit.byId("txtNum_au_tit");
   			dojo.connect(val_num_user_comite,'onKeyPress',
   				    function(evt){  
   				
   				var key = evt.which || evt.keyCode;
   			    if (key == 13 ){
   			    	btnRecuperarComite_au_tit();
   			    	//btnRecuperarContacto();
   			    }
   			    
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			}
   			);
   			
   			//AU SUPLENTE
   			var  val_num_user_comite= dijit.byId("txtNum_au_sup");
   			dojo.connect(val_num_user_comite,'onKeyPress',function(evt){  
   				
   				var key = evt.which || evt.keyCode;
   			    if (key == 13 ){
   			    	btnRecuperarComite_au_sup();
   			    }
   			    
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			});
   			
   		//TECNICO TITULAR
   			var  val_num_user_comite= dijit.byId("txtNum_tec_tit");
   			dojo.connect(val_num_user_comite,'onKeyPress',function(evt){  
   				
   				var key = evt.which || evt.keyCode;
   			    if (key == 13 ){
   			    	btnRecuperarComite_tec_tit();
   			    	
   			    }
   			    
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			});
   			
   			//TECNICO SUPLENTE
   			var  val_num_user_comite= dijit.byId("txtNum_tec_sup");
   			dojo.connect(val_num_user_comite,'onKeyPress',function(evt){  
   				
   				var key = evt.which || evt.keyCode;
   			    if (key == 13 ){
   			    	btnRecuperarComite_tec_sup();
   			    }
   			    
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			});
   		
   	});
   	


  //INICIO:DPORRASC - CODIGO JAVASCRIPT

  function btnRecuperarUUOO1(){	
  	dojo.byId("action").value = "recuperarCodUUOOJson";
  	dojo.byId("txtNum_UUOO").value=dojo.byId("txtNum_UUOO1").value;
  	
  	//alert("recuperarCodUUOOJson: " + dojo.byId("txtNum_UUOO1").value);
  	
  	if(dojo.byId("txtNum_UUOO1").value!=""){
  	//llamada AJAX
  	var enviar = {
  			handleAs : "json",
  			load : function(response, ioArgs) {
  				
  				if(response.data.error==null){
  					if (response.data != "" && response.data != "[]") {
  						
  						var data = eval("(" + unescape(response.data) + ")");
  					//	if (response.data.error !="0"){
  							var nombre = data.nom_dep;
  							
  							dojo.byId("txtNom_UUOO1").value = data.nom_dep; 
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
  		dojo.byId("txtNom_UUOO1").value = "";
  		dojo.byId("txtCod_UUOO1").value = "";
  	}
  }	

  function btnRecuperarUUOO2(){	
  	dojo.byId("action").value = "recuperarCodUUOOJson";
  	dojo.byId("txtNum_UUOO").value=dojo.byId("txtNum_UUOO2").value;
  	
  	//alert("recuperarCodUUOOJson: " + dojo.byId("txtNum_UUOO2").value);
  	if(dojo.byId("txtNum_UUOO2").value!=""){
  	//llamada AJAX
  	var enviar = {
  			handleAs : "json",
  			load : function(response, ioArgs) {
  				
  				if(response.data.error==null){
  					if (response.data != "" && response.data != "[]") {
  						
  						var data = eval("(" + unescape(response.data) + ")");
  					//	if (response.data.error !="0"){
  							var nombre = data.nom_dep;
  							
  							dojo.byId("txtNom_UUOO2").value = data.nom_dep; 
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
  		dojo.byId("txtNom_UUOO2").value = "";
  		dojo.byId("txtCod_UUOO2").value = "";
  	}
  }
  	
  function btnRecuperarUUOO3(){	
  	dojo.byId("action").value = "recuperarCodUUOOJson";
  	dojo.byId("txtNum_UUOO").value=dojo.byId("txtNum_UUOO3").value;
  	
  	//alert("recuperarCodUUOOJson: " + dojo.byId("txtNum_UUOO3").value);
  	if(dojo.byId("txtNum_UUOO3").value!=""){
  	//llamada AJAX
  	var enviar = {
  			handleAs : "json",
  			load : function(response, ioArgs) {
  				
  				if(response.data.error==null){
  					if (response.data != "" && response.data != "[]") {
  						
  						var data = eval("(" + unescape(response.data) + ")");
  					//	if (response.data.error !="0"){
  							var nombre = data.nom_dep;
  							
  							dojo.byId("txtNom_UUOO3").value = data.nom_dep; 
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
  		dojo.byId("txtNom_UUOO3").value = "";
  		dojo.byId("txtCod_UUOO3").value = "";
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
  								alert(msg_contacto);
  								dojo.byId("txtNom_contacto").value = nombre; 
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
  				
  				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
  				//alert("<fmt:message key='error.ajax'/>");
  			},
  			form: dojo.byId("frmRegistro")
  	};	
  	
  	dojo.xhrPost(enviar);
  	
  	}else{
  		dojo.byId("txtNom_contacto").value = ""; 
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
  								dojo.byId("txtNom_proveedor").value = nombre; 
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
  				
  				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
  				//alert("<fmt:message key='error.ajax'/>");
  				
  			},
  			form: dojo.byId("frmRegistro")
  	};	
  	
  	dojo.xhrPost(enviar);
  	
  	}else{
  		dojo.byId("txtNom_proveedor").value = ""; 
  	}
  }	

  function btnValidarContactoModi(){	
	  	dojo.byId("action").value = "validarContactoRqnpJson";
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
	  					  		alert(msg_contacto );	
	  					  	}else if (val_cod_cont=="-1"){	
	  					  		dojo.byId("txtCod_proveedor").value = "";
	  					  		//alert("<fmt:message key='error.registrarRqnp.contratista'/>");
	  					  		alert(data.raz_social);
	  					  	}else{
	  					  		dojo.byId("action").value = "registrarCabRqnpModi"; //GUARDA LA CABECERA DE RQNP
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
	  				
	  				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
	  				//alert("<fmt:message key='error.ajax'/>");
	  			},
	  			form: dojo.byId("frmRegistro")
	  	};	
	  	
	  	dojo.xhrPost(enviar);
	  } 	
  	
  function btnValidarContacto(){	
  	dojo.byId("action").value = "validarContactoRqnpJson";
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
  					  		alert(msg_contacto );	
  					  	}else if (val_cod_cont=="-1"){	
  					  		dojo.byId("txtCod_proveedor").value = "";
  					  		//alert("<fmt:message key='error.registrarRqnp.contratista'/>");
  					  		alert(data.raz_social);
  					  	}else{
  					  		dojo.byId("action").value = "registrarCabRqnp"; //GUARDA LA CABECERA DE RQNP
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
  				
  				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
  				//alert("<fmt:message key='error.ajax'/>");
  			},
  			form: dojo.byId("frmRegistro")
  	};	
  	
  	dojo.xhrPost(enviar);
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
		    				dijit.byId('txtVinculo').set('placeHolder','Seleccione un V�nculo');
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
  

  function construirGridPersona(store){

  	var layout = [
           {
         	     name: 'Nro Registro',
         	  	 field: 'vnumero_registro',
         	     width: '20%',
         	     headerStyles: "text-align: center",
         	  	 styles:'text-align:center;'
         	 },{
         		 name: 'Apellidos y Nombres',
         	  	 field: 'vnombre_completo',
         	  	 width: '80%',
         	     headerStyles: "text-align: center",
         	  	 styles:'text-align:left;'
         	 },{
         		 name: ' ',
         	  	 field: 'vcodigoEmpleado',
         	  	 width: '0%',
         	     headerStyles: "text-align: center",
         	  	 styles:'text-align:left;'
         	  
         	 }
     	];

  	
  	var grid = new dojox.grid.EnhancedGrid({
  		id:'gridBusquedaPersona',
  		store: store,
  		rowSelector: '20px',
  		structure: layout,
  		escapeHTMLInData: false,
  		rowsPerPage: 20,
  		rowCount: 20,

  		canSort: function(col){ return true; } ,
  		style: { height:"200px", width:"98%", left:"1%" } ,
  		errorMessage: "Ha ocurrido un error al procesar la consulta",
  		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
  		noDataMessage: "No se encontraron registros",
  		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
  		plugins: {
  			pagination: {
  				pageSizes: ["20", "40", "80", "120", "All"],
  				description: true,
  				sizeSwitch: false,
  				pageStepper: true,
  				gotoButton: false,
  				maxPageStep: 5,       
  				position: "bottom"
  			},
  			nestedSorting: true
  		}
  	},document.createElement('div'));
  	  
  	
  	dojo.byId("gridConsultaPersona").appendChild(grid.domNode);
  	grid.startup();
  }

  function tipoPerConsultaSetea(valor){

  	dojo.byId("jPer_tipoConsulta").value = valor;
  }
  
  

  function btnBuscarPersona_click(){
  	
  	dojo.byId("action").value = "buscarPersonaJson";
  	dojo.byId("jPer_parametro").value = document.getElementById("txtPerParametro").value;
  	
  	var val_parametro =document.getElementById("txtPerParametro").value;
  	
  	if (val_parametro.length >2) {
  			
  		//dojo.byId("jtipoConsulta").value = "D";
  		//AQUI BLANQUENADO GRID
  		
  			var store = new dojo.data.ItemFileWriteStore({
  				data: { 
  						items: []
  					}
  			});
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
  							
  							var data = eval("(" + response.data + ")");
  							actualizarPersona(data.lsPersonal);
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
      	alert("Debe seleccionar un registro");
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

   	
   	

   	function actualizar(){	
   		if(document.getElementById("txtNum_Doc").value.length >=7){
   			document.getElementById("trRUC").style.display=''
   		}else{
   			document.getElementById("trRUC").style.display='none'
   		}
   	}



   	var btnSalir_click = function(){
   		if(confirm("Confirme si desea Cancelar el Registro - RQNP AUC")){
   			//document.frmRegistro.action="rqnpauc.htm?action=modificarRqnp"; //COMENTADO PARA EL PASE
   			document.frmRegistro.action="rqnpauc.htm?action=auciniciarbandeja";
   			document.frmRegistro.submit();
   		}
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
      	}
      }
    

    
   	//TIPO DE REGISTRO (SI/NO) - INDICADOR DE TECNICO DE COMITE
    function tipoRegistroSeteaTecTit(valor){
      	dojo.byId("jtipoRegistroComiteTecTit").value = valor;
      	if (valor=="N"){
      		dijit.byId('txtNum_tec_tit').setAttribute("disabled", true);
      		dijit.byId('txtNom_tec_tit').setAttribute("disabled", false);
      		dojo.byId("txtNum_tec_tit").value="";
      		dojo.byId("txtNom_tec_tit").value="";
      	}
      	if (valor=="S"){
      		divShow("divComite");

      		dijit.byId('txtNum_tec_tit').setAttribute("disabled", false);
      		dijit.byId('txtNom_tec_tit').setAttribute("disabled", true);
      		dojo.byId("txtNum_tec_tit").value="";
      		dojo.byId("txtNom_tec_tit").value="";
      		
      	}
      }
    
    function tipoRegistroSeteaTecSup(valor){
      	dojo.byId("jtipoRegistroComiteTecSup").value = valor;
      	if (valor=="N"){
      		
      		dijit.byId('txtNum_tec_sup').setAttribute("disabled", true);
      		dijit.byId('txtNom_tec_sup').setAttribute("disabled", false);
      		dojo.byId("txtNum_tec_sup").value="";
      		dojo.byId("txtNom_tec_sup").value="";
      	}
      	if (valor=="S"){
      		divShow("divComite");
      		
      		dijit.byId('txtNum_tec_sup').setAttribute("disabled", false);
      		dijit.byId('txtNom_tec_sup').setAttribute("disabled", true);
      		dojo.byId("txtNum_tec_sup").value="";
      		dojo.byId("txtNom_tec_sup").value="";
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
        		dijit.byId("txtTipo_vinculo").reset();
        		dijit.byId('txtTipo_vinculo').set('placeHolder','Seleccione un Tipo de Vínculo');
        		dijit.byId("txtVinculo").reset();
  			dijit.byId('txtVinculo').set('placeHolder','Seleccione un Vínculo');
        	}
        	
        	if (valor=="S"){
        		divShow("divTipoVinculoLabel");
        		divShow("divTipoVinculo");
        		divShow("divVinculoLabel");
        		divShow("divVinculo");
        		dijit.byId("txtTipo_vinculo").reset();
        		dijit.byId('txtTipo_vinculo').set('placeHolder','Seleccione un Tipo de Vínculo');
        		dijit.byId("txtVinculo").reset();
  			dijit.byId('txtVinculo').set('placeHolder','Seleccione un Vínculo');
        	}
        }
      
    //TIPO DE REGISTRO (SI/NO) - SI REGISTRA O NO
      function tipoRegistroSeteaPrestamo(valor){
        	dojo.byId("jtipoRegistroPrestamo").value = valor;
     	}

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
			
			if(registro_vinculo!=""){
				//alert("registro_vinculo: "+registro_vinculo);
				//console.log("registro_prestamo: "+registro_prestamo);
				//console.log("registro_vinculo: "+registro_vinculo);
				verVinculo(registro_vinculo);
				verPrestamo(registro_prestamo);
			}else if (registro_vinculo==""){
				//dijit.byId("txtTipo_vinculo").setAttribute("disabled", true);
				//dijit.byId("txtVinculo").setAttribute("disabled", true);
				dijit.byId("txtTipo_vinculo").set('value',"04");
   				dijit.byId("txtVinculo").set('value',"01");
			}
			//dijit.byId("txtMotivo").focus();
			
			/* var URL = CONTEXTO_APP + '/bandejaauc.htm?action=listarNecesidadJson';
			    var storeNecesidad = new dojo.data.ItemFileReadStore({url:URL});
			    var grilla = dijit.byId("txtCod_necesidad");
			    grilla.store =storeNecesidad;
			    //grilla.setStore(storeNecesidad);
			*/

			var storeTipoNecesidad = new dojo.data.ItemFileWriteStore({
		        data: { 
		        	identifier: 'id',
		        	  label: 'name',
					items: val_ls_tipo_necesidad
				}
			}
			); 
			
			var  val_anexo= dijit.byId("txtAnexo_contacto");
			dojo.connect(val_anexo,'onKeyPress',
				    function(e){  
				//var tecla = (document.all) ? e.keyCode : e.which;
				//tecla = e.keyCode || e.which;
				var tecla = e.which || e.keyCode;
				if (tecla==8) return true;
				patron =/[0-9]/;
				te = String.fromCharCode(tecla);
				if(! patron.test(te)){
					dojo.stopEvent(e);
				}
				
			}
			);

			//Caja de Texto de Contacto
			var  val_cod_contacto= dijit.byId("txtReg_contacto");
			 
				dojo.connect(val_cod_contacto,'onKeyPress',
					    function(evt){  
					var key = evt.which || evt.keyCode;
				    if (key == 13 ){
				    	btnRecuperarContacto();
				    }
				    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
				    evt.keyCode = key;
				}
				);
				
			//Caja de Texto de CONTRATISTA	
			var  val_cod_contratista= dijit.byId("txtNum_ruc_prov");
			var  val_cod_contratista_value= dojo.byId("txtNum_ruc_prov").value;
				dojo.connect(val_cod_contratista,'onKeyPress',
					    function(evt){  
					var key = evt.which || evt.keyCode;
				    if (key == 13 ){
				    	btnRecuperarContratista();
				    }
				    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
				    evt.keyCode = key;
				}
				);
				
				if ( val_cod_contratista_value !=""){
					divShow("divObs_justificacion");
					divShow("divObs_justificacion_label");	
				}else{
					dojo.byId("txtNum_ruc_prov").value="";
				}
				
				
				
				//caja de uuoo conformidad 1
				
				var  val_num_uuoo1= dijit.byId("txtNum_UUOO1");
				dojo.connect(val_num_uuoo1,'onKeyPress',
					    function(evt){  
					var key = evt.which || evt.keyCode;
				    if (key == 13 ){
				    	btnRecuperarUUOO1();
				    }
				    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
				    evt.keyCode = key;
				}
				);
				
				
				//caja de uuoo conformidad 2
				
				var  val_num_uuoo2= dijit.byId("txtNum_UUOO2");
				dojo.connect(val_num_uuoo2,'onKeyPress',
					    function(evt){  
					var key = evt.which || evt.keyCode;
				    if (key == 13 ){
				    	btnRecuperarUUOO2();
				    }
				    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
				    evt.keyCode = key;
				}
				);
				
				
			//caja de uuoo conformidad 3
				
				var  val_num_uuoo3= dijit.byId("txtNum_UUOO3");
				dojo.connect(val_num_uuoo3,'onKeyPress',
					    function(evt){  
					var key = evt.which || evt.keyCode;
				    if (key == 13 ){
				    	btnRecuperarUUOO3();
				    }
				    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
				    evt.keyCode = key;
				}
				);
				
				/////////////////////////////////////////////////
				dojo.connect(val_cod_contacto,'onChange',
					    function(evt){  
								btnRecuperarContacto();
				}
				);
				
				var  val_nom_tipo= dijit.byId("txtNom_tip_necesidad");
				
				dojo.connect(val_nom_tipo,'onKeyPress',
					    function(evt){  
					var key = evt.which || evt.keyCode;
				    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
				    evt.keyCode = key;
				    
				}
				);
				
				var  val_per_parametro= dijit.byId("txtPerParametro");
				
				dojo.connect(val_per_parametro,'onKeyPress',
					    function(evt){  
					var key = evt.which || evt.keyCode;
				    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
				    evt.keyCode = key;
				}
				);
				
				var  val_tipo_vinculo= dijit.byId("txtTipo_vinculo");
				 
				dojo.connect(val_tipo_vinculo,'onChange',
					    function(evt){  
					
					   	btnRecuperarVinculo();
				}
				);
				
				//INICIO AGREGADO PARA EL PASE
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
						dojo.connect(val_num_user_comite,'onKeyPress',
							    function(evt){  
							
							var key = evt.which || evt.keyCode;
						    if (key == 13 ){
						    	btnRecuperarComite_au_sup();
						    	
						    }
						    
						    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
						    evt.keyCode = key;
						}
						);
						
					//TECNICO TITULAR
						var  val_num_user_comite= dijit.byId("txtNum_tec_tit");
						dojo.connect(val_num_user_comite,'onKeyPress',
							    function(evt){  
							
							var key = evt.which || evt.keyCode;
						    if (key == 13 ){
						    	btnRecuperarComite_tec_tit();
						    	
						    }
						    
						    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
						    evt.keyCode = key;
						}
						);
						
						//TECNICO SUPLENTE
						var  val_num_user_comite= dijit.byId("txtNum_tec_sup");
						dojo.connect(val_num_user_comite,'onKeyPress',
							    function(evt){  
							
							var key = evt.which || evt.keyCode;
						    if (key == 13 ){
						    	btnRecuperarComite_tec_sup();
						    	
						    }
						    
						    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
						    evt.keyCode = key;
						}
						);
				   	//FIN AGREGADO PARA EL PASE
						
						
				var storeMaestro = new dojo.data.ItemFileWriteStore(
						{
							data: 
							{ 
								items: val_ls_personal
							}
						}
					);
								
			
		}
 
 
 
 
 );
   	
   	
   	
   	function construirGridPersona(store){

			var layout = [
		         {
		       	     name: 'Nro Registro',
		       	  	 field: 'numero_registro',
		       	     width: '20%',
		       	     headerStyles: "text-align: center",
		       	  	 styles:'text-align:center;'
		       	 },
		       	 {
		       		 name: 'Apellidos y Nombres',
		       	  	 field: 'nombre_completo',
		       	  	 width: '80%',
		       	     headerStyles: "text-align: center",
		       	  	 styles:'text-align:left;'
		       	  
		       	 },
		       	 {
		       		 name: ' ',
		       	  	 field: 'codigoEmpleado',
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
dojo.require("dijit.form.Form");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dijit.form.TextBox");
	dojo.require("dijit.form.SimpleTextarea");
	dojo.require("dijit.Dialog");  
	dojo.require("dojo.date.locale");
    dojo.require("dijit.form.RadioButton");
    dojo.require("dojo.data.ItemFileReadStore");
    dojo.require("dojo.data.ItemFileWriteStore");
    dojo.require("dojox.grid.EnhancedGrid");
    dojo.require("dojox.grid.enhanced.plugins.Pagination");
    dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
    dojo.require("dojo._base.array");
    dojo.require("dojo._base.connect");
    dojo.require("dojo._base.event");
    dojo.require("dojo.parser");
    dojo.require("dijit.form.ComboBox");
   	dojo.require("dijit.form.FilteringSelect");
   	dojo.require("dojo.store.Memory");
   	
   	dojo.addOnLoad(function() {
   		dijit.byId('btnGuardar').setAttribute("disabled", false);
   		dijit.byId('btnSalir').setAttribute("disabled", false);
   		formatear();
   		
   		var  val_motivo= dijit.byId("txtParametro");
   		dojo.connect(val_motivo,'onKeyPress',
   			    function(evt){  
   		var key = evt.which || evt.keyCode;
   		    if (key == 13 ){
   		    	btnBuscarCatalogo_click(); 
   		    }
   		   
   		    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   		    evt.keyCode = key;
   		}
   		);
   		
   		var visor = dojo.byId("txtvisor").value;
   		divShow("divbtnSiguiente");
   		divShow("divbtnSalir");

   		if (visor =="0"){
   			divShow("divbtnAdd");
   			divShow("divbtnDel");
   			//divShow("divbtnSol");
   			divShow("divbtnGuardar");
   		}
   		
   		
   		
   		var store = new dojo.data.ItemFileWriteStore(
   			{
   				
   				data: 
   				{ 
   					items: listaBienes
   				}
   			}
   		);
   		
   		var storeCatalogo = new dojo.data.ItemFileWriteStore(
   				{
   					data: 
   					{ 
   						items: [
   							/* <c:forEach items="${lsCatalogo}"  var="catalogoBean" varStatus="status">
   				            	{
   				            		vcodigo_bien:"${catalogoBean.codigo_bien}", 
   				            		vdesc_bien: "${catalogoBean.desc_bien}",
   				            		vtipo_bien: "${catalogoBean.tipo_bien}",
   				            		vdesc_unidad: "${catalogoBean.desc_unidad}",
   				            		vcodigo_unidad: "${catalogoBean.codigo_unidad}",
   				            		vxprecio_bien: "${catalogoBean.precio_bien}",
   				            		vprecio_bien: "${catalogoBean.precio_bien}",
   				            		vcodigo_gasto: "${catalogoBean.codigo_gasto}",
   				            		vdesc_gasto: "${catalogoBean.desc_gasto}",
   				            		vestadoDesconcentrado: "${catalogoBean.estadoDesconcentrado}",
   				            		vauct1:"${catalogoBean.auct1}",
   				            		vauct2:"${catalogoBean.auct2}",
   				            		vauct3:"${catalogoBean.auct3}"	,
   				            		vauct_name:"${catalogoBean.auct_name}"	,
   				            			vsaldo:"${catalogoBean.saldo}"
   								}   
   				            	${not status.last ? ',' : ''}
   							</c:forEach> */
   						]
   					}
   				}
   			);
   		construirGridDetalle(store);
   		//construirGrid(storeCatalogo);
   		///CONSTRUIR CABECERA EN CUADRO DE DALOGO-----------------------------------------
   		var storeFinalidad = new dojo.data.ItemFileWriteStore({
   	        data: { 
   	        	identifier: 'id',
   	        	  label: 'name',

   				items: vcod_finalidad
   			}
   		}
   		); 
   		var comboBoxFinalidad = 
   			new dijit.form.FilteringSelect({
   			id: "txtCod_finalidad2",
   			name: "txtCod_finalidad2",
   			value: "${mapRqnp.cod_finalidad}",
   			placeHolder: 'Seleccione una Finalidad',
   			style: "width: 250px;",
   			store: storeFinalidad,
   			searchAttr: "name"
   	   }, dojo.byId("txtCod_finalidad2"));
   		
   		var storeNecesidad = new dojo.data.ItemFileWriteStore({
   	        data: { 
   	        	identifier: 'id',
   	        	  label: 'name',

   				items: vlsNecesidad
   			}
   		}
   		); 
   		
   		
   		
   		var comboBoxNecesidad = 
   			 new dijit.form.FilteringSelect({
   	        id: "txtCod_necesidad2",
   	        name: "txtCod_necesidad2",
   	        value: "${mapRqnp.cod_necesidad}",
   	        store: storeNecesidad,
   	        style: "width: 250px;",
   	        searchAttr: "name"
   	    }, dojo.byId("txtCod_necesidad2"));

   		
   		
   		var storeTipoNecesidad = new dojo.data.ItemFileWriteStore({
   	        data: { 
   	        	identifier: 'id',
   	        	  label: 'name',

   				items: vlsTipoNecesidad
   			}
   		}
   		); 

   		
   		var  val_anexo= dijit.byId("txtAnexo_contacto2");
   		
   		dojo.connect(val_anexo,'onKeyPress',
   			    function(e){  
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
   			
   		}
   		);
   		
   		//Caja de Texto de Contacto

   		var  val_cod_contacto= dijit.byId("txtReg_contacto2");
   			dojo.connect(val_cod_contacto,'onKeyPress', function(evt){  
   				var key = evt.which || evt.keyCode;
   			    if (key == 13 ){
   			    	btnRecuperarContacto();
   			    }
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			});
   			
   			
   			dojo.connect(val_cod_contacto,'onChange', function(evt){  
   			    	btnRecuperarContacto();
   			});
   			
   			//Caja de Texto de CONTRATISTA	
   			var  val_cod_contratista= dijit.byId("txtNum_ruc_prov2");
   				dojo.connect(val_cod_contratista,'onKeyPress', function(evt){  
   					var key = evt.which || evt.keyCode;
   				    if (key == 13 ){
   				    	btnRecuperarContratista();
   				    }
   				    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   				    evt.keyCode = key;
   				    
   				});
   			
   				
   			
   		var  val_nom_tipo= dijit.byId("txtNom_tip_necesidad2");
   			
   			dojo.connect(val_nom_tipo,'onKeyPress', function(evt){  
   				var key = evt.which || evt.keyCode;
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			});
   			
   		var  val_per_parametro = dijit.byId("txtPerParametro");
   			
   			dojo.connect(val_per_parametro,'onKeyPress', function(evt){  
   				var key = evt.which || evt.keyCode;
   				if (key == 13 ){
   					btnBuscarPersona_click();
   			    }
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			});
   			
   			var storeMaestro = new dojo.data.ItemFileWriteStore({
   						data: { 
   							items: vlsPersonal
   						}
   					});
   			
   			//dojo.byId("txtCod_contacto").value="${mapRqnp.cod_contacto}";
   			
   			//validar UUOO1
   			//CLICK EN UUOO SELECCIONADA
   			var val_num_uuoo= dijit.byId("txtNum_UUOO12");
   			dojo.connect(val_num_uuoo,'onKeyPress', function(evt){  
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
   			var val_num_uuoo= dijit.byId("txtNum_UUOO22");
   			dojo.connect(val_num_uuoo,'onKeyPress', function(evt){  
   				var key = evt.which || evt.keyCode;
   			    if (key == 13 ){
   			    	btnRecuperarUUOO2();
   			    }
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			});

   			dojo.connect(val_num_uuoo,'onChange', function(evt){  
   				btnRecuperarUUOO2();
   			});
   			
   			//validar UUOO3
   			//CLICK EN UUOO SELECCIONADA
   			var val_num_uuoo= dijit.byId("txtNum_UUOO32");
   			dojo.connect(val_num_uuoo,'onKeyPress', function(evt){  
   				var key = evt.which || evt.keyCode;
   			    if (key == 13 ){
   			    	btnRecuperarUUOO3();
   			    }
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			});

   			dojo.connect(val_num_uuoo,'onChange', function(evt){  
   				btnRecuperarUUOO3();
   			});
   	});


   	function construirGridDetalle(store){

   		var layout = [
   			{
   			     name: ' ',
   			  	 field:'vcodigoRqnp',
   			  	 formatter: getCodigoRqnp,
   			     width: '0%',
   			     headerStyles: "text-align: center",
   			  	 styles:'text-align:center;',
  	    	     noresize: true
   		 	},{
   	      	     name: 'C&oacute;digo',
   	      		 field:'vxcodigoBien',
   	      	     width: '9%',
   	      	     headerStyles: "text-align: center",
   	      	  	 styles:'text-align:center;font-size:9;',
  	    	     noresize: true
   	      	 },{
   	   	      	 name: 'Adj.',
   	   	      	 field: 'vnro_adjuntos',
   	   	      	 width: '3%',
   	   	      	 formatter:getNroAdjunto,
   	   	      	 headerStyles: "text-align: center",
   	   	      	 styles:'text-align:center;font-size:9;',
  	    	     noresize: true 
   	   	     },{
   	       	     name: ' ',
   	       		 field:'vcodigoBien',
   	       		 formatter:getCodigoBien,
   	       	     width: '0%',
   	       	     headerStyles: "text-align: center",
   	       	  	 styles:'text-align:center;',
  	    	     noresize: true
   	       	 },{
   	       		 name: 'Bienes y Servicios',
   	       	  	 field: 'vdesBien',
   	       	  	 width: '21%',
   	       	     headerStyles: "text-align: center",
   	       	  	 styles:'text-align:left;font-size:9;',
  	    	     noresize: true
   	       	 }, {
   	       		 name: ' ',
   	       	     field: 'vcodigoUnidad',
   	       	     width: '0%',
   	       	     headerStyles: "text-align: center",
   	       	     styles:'text-align:center;',
   	       	     formatter:getCodigoUnidad,
  	    	     noresize: true
   	       	 },{
   	       		 name: 'Unid. Med.',
   	       	     field: 'vdesUnid',
   	       	     width: '6%',
   	       	     headerStyles: "text-align: center",
   	       	     styles:'text-align:center;font-size:9;',
  	    	     noresize: true
   	       	 },{
   	      		 name: 'Precio Unit.',
   	      		 field: 'vxprecioUnid',
   	      	     formatter:getPrecioUnitariox,
   	      	 	 regExp:"^[0-9]{1,5}(\.[0-9]{0,2})?$"  , 
   	      	     width: '6%',
   	      	     headerStyles: "text-align: center",
   	      	   //editable: true,
   	      	     styles:'text-align:right;font-size:9;',
  	    	     noresize: true
   	      	 },{
   	      		 name: ' ',
   	      		 field: 'vprecioUnid',
   	      	     formatter:getPrecioUnitario,
   	      	 	 regExp:"^[0-9]{1,5}(\.[0-9]{0,2})?$"  , 
   	      	     width: '0%',
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:right;',
  	    	     noresize: true 
   	      	    
   	      	 },{
   	      		 name: ' ',
   	      	     field: 'vcantBien',
   	      	     width: '0%',
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:right;' ,
   	      	    formatter:getCantidad,
 	    	     noresize: true
   	      	 },{
   	      		 name: 'Saldo',
   	      		 field: 'vsaldo',
   	      	     width: '5%',
   	      	     formatter:getSaldo,
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:right;font-size:9;',
  	    	     noresize: true 
   	      	 },{
   	      		 name: 'Exceso',
   	      		 field: 'vexceso',
   	      	     width: '5%',
   	      	     formatter:getSaldo,
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:right;',
  	    	     noresize: true 
   	      	 },{
   	      		 name: 'Alerta',
   	      		 field: 'vimage',
   	      	     width: '3%',
   	      	     formatter:getImage,
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:center;font-size:9;',
  	    	     noresize: true 
   	      	 },{
   	     		 name: 'AUC (*)',
   	     	     field: 'vauct_name',
   	     	     //width: '10%',
   	     	     width: '0%',
   	     	     headerStyles: "text-align: center",
   	     	     styles:'text-align:left;font-size:9;',
  	    	     noresize: true 
   	     	     
   	     	 },{
   	     		 name: 'Archivo',
   	     		 field: 'vlink',
   	     	     width: '6%',   
   	     	     headerStyles: "text-align: center",
   	     	     styles:'text-align:center;font-size:9;',
  	    	     noresize: true
   	     	 },{
   	     		 name: 'Entrega',
   	     		 field: 'vlinkDetalle',
   	     	     width: '6%',   
   	     	     headerStyles: "text-align: center",
   	     	     styles:'text-align:center;font-size:9;',
  	    	     noresize: true
   	     	 },{
   	     		 name: 'Clasificador',
   	     		 field: 'vxcodClasificador',
   	     	     width: '10%',
   	     	     headerStyles: "text-align: center",
   	     	     styles:'text-align:center;font-size:9;',
  	    	     noresize: true
   	     	 },{
   	     		 name: ' ',
   	     		 field: 'vcodClasificador',
   	     	     width: '0%',
   	     	     headerStyles: "text-align: center",
   	     	     styles:'text-align:center;', 
   	     	     formatter:getCodigoClasif,
  	    	     noresize: true
   	     	 },{
   	     		 name: 'Desc. Clasificador',
   	     	     field: 'vdesClasificador',
   	     	     width: '14%',
   	     	     headerStyles: "text-align: center",
   	     	     styles:'text-align:left;font-size:9;',
  	    	     noresize: true 
   	     	     
   	     	 },{
   	     		 name: ' ',
   	     	     field: 'vauct1',
   	     	    formatter:getAuct1,
   	     	     width: '0%',
   	     	     headerStyles: "text-align: center",
   	     	     styles:'text-align:left;',
  	    	     noresize: true 
   	     	     
   	     	 },{
   	    		 name: ' ',
   	    	     field: 'vauct2',
   	    	     formatter:getAuct2,
   	    	     width: '0%',
   	    	     headerStyles: "text-align: center",
   	    	     styles:'text-align:left;',
  	    	     noresize: true 
   	    	     
   	     	},{
   	     		 name: ' ',
   	     	     field: 'vauct3',
   	     	    formatter:getAuct3,
   	     	     width: '0%',
   	     	     headerStyles: "text-align: center",
   	     	     styles:'text-align:left;',
  	    	     noresize: true      
   	     	 },{
   	    		 name: ' ',
   	    	     field: 'vtipo_bien',
   	    	     width: '0%',
   	    	     headerStyles: "text-align: center",
   	    	     styles:'text-align:left;',
  	    	     noresize: true      
   	    	 }
   	   	];
   		

   		var grid = new dojox.grid.EnhancedGrid({
   			//id:'gridDetalle',
   			store: store,
   			rowSelector: '20px',
   			structure: layout,
   			escapeHTMLInData: false,
   			rowsPerPage: 30,
   			rowCount: 30,
   		
   			//onApplyCellEdit:onApplyCellEditDetalle,
   			singleClickEdit:true,
   			canSort: function(col){ return true; } ,
   			style: { height:"270px", width:"1000px" } ,
   			errorMessage: "Ha ocurrido un error al procesar la consulta",
   			loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
   			noDataMessage: "No se encontraron registros",
   			//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
   			plugins: {
   				pagination: {
   					pageSizes: ["30", "60", "120", "180", "All"],
   					description: true,
   					sizeSwitch: false,
   					pageStepper: true,
   					gotoButton: false,
   					maxPageStep: 5,       
   					position: "bottom"
   				},
   				nestedSorting: true
   			}
   		},dojo.byId("gridDetalle"));
   		grid.startup();

   		dojo.connect(grid,'onKeyEvent',function(e){  
   			tecla = (document.all) ? e.keyCode : e.which;
   			if (tecla==8 ) 
   			{
   				return true;
   			}else{
   				patron =/[0-9]/;
   				if (96 <=tecla  && tecla <=105){
   					tecla=tecla - 48;
   				}
   				//alert(tecla);
   				if (tecla== 110 || tecla ==46 || tecla ==37 || tecla ==39) {
   					
   				}else{
   					te = String.fromCharCode(tecla);
   					//alert(te);
   					if(! patron.test(te)){
   						dojo.stopEvent(e);
   					}
   					//dojo.byId("jload").value='1';
   				}
   			}
   		});
   	}
   	
   	

   	function getCodigoRqnp(valor, item) {
   		var vtxtRqnp = '<input id="txtRqnp" name="txtRqnp" type="hidden"  value="'+valor+'"   /> '; 
   	    return vtxtRqnp; 
   	}

   	function getCodigoBien(valor, item) {
   		var vtxtCodigo = '<input id="txtCodigo" name="txtCodigo" type="hidden"  value="'+valor+'"   /> '; 
   	    return vtxtCodigo; 
   	}

   	function getCantidad(valor, item) {
   		var vtxtCantidad = '<input id="txtCantidad" name="txtCantidad" type="hidden"  value="'+valor+'"   /> '; 
   	    return vtxtCantidad; 
   	}
   	function getCantidadX(valor) {
   		
   	    return addCommas(valor); 
   	}
   	function getSaldo(valor) {
   		
   	    return addCommas(valor); 
   	}

   	function getCodigoUnidad(valor, item) {
   		var vtxtCantidad = '<input id="txtcodUnidad" name="txtcodUnidad" type="hidden"  value="'+valor+'"   /> '; 
   	    return vtxtCantidad; 
   	}

   	function getPrecioUnitario(valor, item) {
   		var vtxtCantidad = '<input id="txtPrecioUnit" name="txtPrecioUnit" type="hidden"  value="'+valor+'"   /> '; 
   	    return vtxtCantidad; 
   	}

   	function getAuct1(valor) {
   		var vtxtAuct1 = '<input id="txtAuct1" name="txtAuct1" type="hidden"  value="'+valor+'"   /> '; 
   	    return vtxtAuct1; 
   	}

   	function getAuct2(valor) {
   		var vtxtAuct2 = '<input id="txtAuct2" name="txtAuct2" type="hidden"  value="'+valor+'"   /> '; 
   	    return vtxtAuct2; 
   	}

   	function getAuct3(valor) {
   		var vtxtAuct3 = '<input id="txtAuct3" name="txtAuct3" type="hidden"  value="'+valor+'"   /> '; 
   	    return vtxtAuct3; 
   	}

   	function getValorTrue(valor,rowIndex) {
   		var grid = dijit.byId('gridDetalle');
   		var tipo_bien="";
   		var itemIndex = grid.getItem(rowIndex);

   		tipo_bien =grid.store.getValue(itemIndex, 'vtipo_bien') ;
   		if (tipo_bien=='B'){
   			return false;
   		}else{
   			return true;
   		}
   	}



   	function getPrecioUnitariox(valor,rowIndex) {
   		//Validando la expresion Regular
   		var patron=/^[0-9]{1,10}(\.[0-9]{0,2})?$/;

   		var rsta = valor.match(patron);
   		var grid = dijit.byId('gridDetalle');
   		var items = grid.selection.getSelected();

   		var prec_uni;
   		var prec_unix;
   		
   		var tipo_bien="";
   		var val_txtPrecioUnit;
   		
   		var itemIndex = grid.getItem(rowIndex);
   		tipo_bien =grid.store.getValue(itemIndex, 'vtipo_bien') ;
   		
   		var precio= grid.store.getValue(itemIndex, 'vprecioUnid') ;
   		if  (tipo_bien=='B'){
   			valor =precio;
   		}
   	 	
   		valor =  addCommas(valor);
   		val_txtPrecioUnit=valor;
   		/*if (tipo_bien=='B'){
   			val_txtPrecioUnit=valor;
   		}else{
   			 val_txtPrecioUnit=new dijit.form.ValidationTextBox({  name: "PrecioUnitx" , value: valor , style:"width:85%;text-align:right;"  ,   regExp:"[0-9]+[\.|\,]*[0-9]*[\.|\,]*[0-9]*[\.|\,]*[0-9]{0,2}"  } );
   			 grid.store.setValue(itemIndex, 'vprecioUnid',removeCommas(valor));
   		}*/
   		
   		return  val_txtPrecioUnit;
   	}


   	function getCodigoClasif(valor, item) {
   		var vtxtCodClasi = '<input id="txtCodClasi" name="txtCodClasi" type="hidden"  value="'+valor+'"   /> '; 
   	    return vtxtCodClasi; 
   	}
   	

   	function getNroAdjunto(valor,rowIndex){
   			
   			if (valor ==0){
   				return  '-';
   			}else if  ( valor >0){
   					return  '<img name="adj01" src="/a/imagenes/adjunto.jpg"/>';
   			}else{
   					return  '-';
   			}
   		}
   	function getImage(valor,rowIndex){
   		var grid = dijit.byId('gridDetalle');
   		var itemIndex =grid.getItem(rowIndex);
   		var val_saldo
   		var val_exceso
   		val_saldo =parseFloat( grid.store.getValue(itemIndex, 'vsaldo') );
   		val_exceso =parseFloat( grid.store.getValue(itemIndex, 'vexceso') );
   		
   		  var gridDet = dijit.byId('gridDetalle');
   	      var sw='0';
   	      if(	gridDet.store !=null){
   	  		if((gridDet.store._arrayOfAllItems.length > 0 )){
   	  			gridDet.store.fetch({
   	  				onItem: function(item, request){
   	  							if (item.vexceso.toString()!="0.00" ){
   	  								sw='1';
   	  							}
   	  		 				}
   	  			});
   	  		}
   	  	}
   	 	if (sw=="1"){
   	 		divShow('divmsgExceso');
   			divShow('divmsgImage');
   	 	}else{
   	 		divHide('divmsgExceso');
   	 		divHide('divmsgImage');
   	 	}
   	      
   		if (val_saldo ==0){
   			divShow('divmsgExceso');
   			divShow('divmsgImage');
   			return  '<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
   		}else if  ( val_exceso >0){
   			divShow('divmsgExceso');
   			divShow('divmsgImage');
   			return  '<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
   		}else{
   			divHide('divmsgExceso');
   			divHide('divmsgImage');
   			return  valor;
   		}
   	}

   	function adjuntarArchivo(cod_req,cod_bien,num_reg){
   		dojo.byId("txtCodigoRqnp").value 	= cod_req;
   		dojo.byId("txtCodigoBien").value 	= cod_bien;
   		dojo.byId("txtNum_reg").value 		= num_reg;
   			
   		document.frmRegistro.action="rqnparchivo.htm?action=iniciarCargaArchivo";	
   		document.frmRegistro.submit();
   	}


   	function construirGrid(store){

   		var layout = [
   	         {
   	       	     name: 'C&oacute;digo',
   	       	  	 field: 'vcodigo_bien',
   	       	     width: '13%',
   	       	     headerStyles: "text-align: center",
   	       	  	 styles:'text-align:center;font-size:9;font-family: Tahoma, Geneva, sans-serif;',
  	    	     noresize: true
   	       	 },{
   	       		 name: 'Bienes y Servicios',
   	       	  	 field: 'vdesc_bien',
   	       	  	 width: '42%',
   	       	     headerStyles: "text-align: center",
   	       	  	 styles:'text-align:left;font-size:9;font-family: Tahoma, Geneva, sans-serif;',
  	    	     noresize: true
   	       	  
   	       	 },{
   	       		 name: ' ',
   	       	     field: 'vcodigo_unidad',
   	       	     width: '0%',
   	       	     headerStyles: "text-align: center",
   	       	     styles:'text-align:center;',
  	    	     noresize: true
   	       	 },{
   	       		 name: 'Unid. Med.',
   	       	     field: 'vdesc_unidad',
   	       	     width: '10%',
   	       	     headerStyles: "text-align: center",
   	       	     styles:'text-align:center;font-size:9;font-family: Tahoma, Geneva, sans-serif;',
  	    	     noresize: true
   	       	 },{
   	      		 name: '&Aacute;rea Usuaria / &Aacute;rea T&eacute;cnica',
   	      	     field: 'vauct_name',
   	      	     width: '29%',
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:left;font-size:9;font-family: Tahoma, Geneva, sans-serif;',
  	    	     noresize: true 
   	      	 },{
   	      		 name: 'Precio',
   	      	     field: 'vprecio_bien',
   	      	     width: '6%',
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:right;font-size:9;font-family: Tahoma, Geneva, sans-serif;',
  	    	     noresize: true 
   	      	 },{
   	      		 name: 'Ruta',
   	      	     field: 'vcod_tipo_prog', //vtipo_ruta
   	      	     width: '0%',
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:right;font-size:9;font-family: Tahoma, Geneva, sans-serif;',
  	    	     noresize: true 
   	      	 }
   	   	];

   		
   		var grid = new dojox.grid.EnhancedGrid({
   			id:'gridBusqueda',
   			store: store,
   			rowSelector: '20px',
   			structure: layout,
   			escapeHTMLInData: false,
   			rowsPerPage: 20,
   			rowCount: 20,
   			selectionMode:'multiple',
   			canSort: function(col){ return true; } ,
   			style: { height:"230px", width:"98%", left:"1%" } ,
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
   		  dojo.byId("gridConsulta").appendChild(grid.domNode);
   		  
   		grid.startup();
   	}
   	

   	function construirGridPersona(store){

   		var layout = [
   	         {
   	       	     name: 'Nro Registro',
   	       	  	 field: 'vnumero_registro',
   	       	     width: '20%',
   	       	     headerStyles: "text-align: center",
   	       	  	 styles:'text-align:center;'
   	       	 },
   	       	 {
   	       		 name: 'Apellidos y Nombres',
   	       	  	 field: 'vnombre_completo',
   	       	  	 width: '80%',
   	       	     headerStyles: "text-align: center",
   	       	  	 styles:'text-align:left;'
   	       	  
   	       	 },
   	       	 {
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
   			style: { height:"250px", width:"98%", left:"1%" } ,
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
  	

   	function onApplyCellEditDetalle(inValue,inRowIndex, inFieldIndex){
   		//Validando la expresion Regular
   		var patron=/^[0-9]{1,10}(\.[0-9]{0,2})?$/;
   		console.log("inValue :" + inValue);
   		var rsta = inValue.match(patron);
   		var grid = dijit.byId('gridDetalle');
   		var items = grid.selection.getSelected();
   		var row =null;
   		var prec_uni;
   		var prec_unix;
   		var cant;
   		var mont_total= parseFloat(0);
   		var mont_total_txt;
   		var tipo_bien="";
   		var val_txtPrecioUnit;
   		
   		selectedItem = grid.getItem(inRowIndex);

   		cant = parseFloat( removeCommas(grid.store.getValues(selectedItem, 'vcantBien')));

   		tipo_bien =grid.store.getValue(selectedItem, 'vtipo_bien') ;
   		var precio= grid.store.getValue(selectedItem, 'vprecioUnid') ;
   		if  (tipo_bien=='B'){
   			console.log("BIENEEE" );
   			inValue =precio;
   		}
   		
   	 	if( rsta!=null ){
   	 		console.log("entrooooo" );
   	 		prec_uni= parseFloat(inValue);
   	 		mont_total = cant * prec_uni;
   	 		mont_total_txt =addCommas(mont_total);
   	 		
   	 			grid.store.setValue(selectedItem, 'vprecioTotal',mont_total_txt);
   	 	 		grid.store.setValue(selectedItem, 'vprecioUnid',inValue);  
   		}else{
   			//alert('valor no valido');
   		} 
   	}
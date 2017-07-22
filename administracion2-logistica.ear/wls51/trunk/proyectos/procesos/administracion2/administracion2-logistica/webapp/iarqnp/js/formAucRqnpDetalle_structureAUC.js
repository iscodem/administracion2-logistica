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
   	dojo.require("dojox.grid.enhanced.plugins.IndirectSelection");
   	
   	
   	dojo.addOnLoad(function() {
   		var store = new dojo.data.ItemFileWriteStore(
   			{
   				
   				data: 
   				{ 
   					items: val_listaBienes
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

   				items: val_lsFinalidad
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

   				items: val_lsNecesidad
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

   				items: val_lsTipoNecesidad
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
   			
   			
   			dojo.connect(val_cod_contacto,'onChange',
   				    function(evt){  
   			    	btnRecuperarContacto();
   			}
   			);
   			
   			//Caja de Texto de CONTRATISTA	
   			var  val_cod_contratista= dijit.byId("txtNum_ruc_prov2");
   				 
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
   			
   			
   			
   		var  val_nom_tipo= dijit.byId("txtNom_tip_necesidad2");
   			
   			dojo.connect(val_nom_tipo,'onKeyPress',
   				    function(evt){  
   				var key = evt.which || evt.keyCode;
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			    
   			}
   			);
   			
   		var  val_per_parametro = dijit.byId("txtPerParametro");
   			
   			dojo.connect(val_per_parametro,'onKeyPress',
   				    function(evt){  
   				var key = evt.which || evt.keyCode;
   				
   				if (key == 13 ){
   					btnBuscarPersona_click();
   			        
   			    }
   			    key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
   			    evt.keyCode = key;
   			    
   			}
   			);

   			//dojo.byId("txtCod_contacto").value="${mapRqnp.cod_contacto}";
   	});
   	
   	
   	function construirGridDetalle(store){

   		var layout = [
   			{
			 name: '#',
		     field: 'vcod_bien',
		     width: '2%',
		     headerStyles: "text-align: center",
		     styles:'text-align:center;',
		    formatter:getCheck
		 },{
   	      		 name: 'Adj.',
   	      		 field: 'vnro_adjuntos',
   	      	     width: '3%',
   	      	     formatter:getNroAdjunto,
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:center;font-size:9;' 
   	      	 },{
   			     name: ' ',
   			  	 field:'vcodigoRqnp',
   			  	 formatter: getCodigoRqnp,
   			     width: '0%',
   			     headerStyles: "text-align: center",
   			  	 styles:'text-align:center;'
   		 	},
   	        {
   	      	     name: 'Código',
   	      		 field:'vxcodigoBien',
   	      	     width: '9%',
   	      	     headerStyles: "text-align: center",
   	      	  	 styles:'text-align:center;font-size:9;'
   	      	 },{
   	       	     name: ' ',
   	       		 field:'vcodigoBien',
   	       		formatter:getCodigoBien,
   	       	     width: '0%',
   	       	     headerStyles: "text-align: center",
   	       	  	 styles:'text-align:center;'
   	       	 },{
   	       		 name: 'Bienes y Servicios',
   	       	  	 field: 'vdesBien',
   	       	  	 width: '16%',
   	       	     headerStyles: "text-align: center",
   	       	  	 styles:'text-align:left;font-size:9;'
   	       	 }, {
   	       		 name: ' ',
   	       	     field: 'vcodigoUnidad',
   	       	     width: '0%',
   	       	     headerStyles: "text-align: center",
   	       	     styles:'text-align:center;',
   	       	     formatter:getCodigoUnidad
   	       	 },{
   	       		 name: 'Unid. Med.',
   	       	     field: 'vdesUnid',
   	       	     width: '6%',
   	       	     headerStyles: "text-align: center",
   	       	     styles:'text-align:center;font-size:9;'
   	       	 },{
   	      		 name: 'Cantidad.',
   	      		 field: 'vcantBien',
   	      	  	 regExp:"^[0-9]{1,5}(\.[0-9]{0,2})?$"  , 
   	      	     width: '6%',
   	      	     headerStyles: "text-align: center",
   	      	   editable: true,
   	      	     styles:'text-align:right;font-size:9;'
   	      	     
   	      	 },{
   	      		 name: 'Precio Unit.',
   	      		 field: 'vxprecioUnid',
   	      	     formatter:getPrecioUnitariox,
   	      	 	 regExp:"^[0-9]{1,5}(\.[0-9]{0,2})?$"  , 
   	      	     width: '6%',
   	      	     headerStyles: "text-align: center",
   	      	   editable: true,
   	      	     styles:'text-align:right;font-size:9;'
   	      	     
   	      	 },{
   	      		 name: 'Total ',
   	      		 field: 'vprecioTotal',
   	      	 	 regExp:"^[0-9]{1,5}(\.[0-9]{0,2})?$"  , 
   	      	     width: '6%',
   	      	     headerStyles: "text-align: center",
   	      	   editable: true,
   	      	     styles:'text-align:right;font-size:9;'
   	      	     
   	      	 },
   	      	 {
   	      		 name: ' ',
   	      		 field: 'vprecioUnid',
   	      	     formatter:getPrecioUnitario,
   	      	 	 regExp:"^[0-9]{1,5}(\.[0-9]{0,2})?$"  , 
   	      	     width: '0%',
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:right;' 
   	      	    
   	      	 },{
   	      		 name: 'Saldo',
   	      		 field: 'vsaldo',
   	      	     width: '5%',
   	      	     formatter:getSaldo,
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:right;font-size:9;' 
   	      	 },{
   	      		 name: 'Exceso',
   	      		 field: 'vexceso',
   	      	     width: '5%',
   	      	     formatter:getSaldo,
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:right;' 
   	      	 },{
   	      		 name: 'Alerta',
   	      		 field: 'vimage',
   	      	     width: '3%',
   	      	     formatter:getImage,
   	      	     headerStyles: "text-align: center",
   	      	     styles:'text-align:center;font-size:9;' 
   	      	 },{
   	     		 name: 'Archivo',
   	     		 field: 'vlink',
   	     	     width: '6%',   
   	     	     headerStyles: "text-align: center",
   	     	     styles:'text-align:center;font-size:9;'
   	     	 },{
   	     		 name: 'Entrega',
   	     		 field: 'vlinkDetalle',
   	     	     width: '6%',   
   	     	     headerStyles: "text-align: center",
   	     	     styles:'text-align:center;font-size:9;'
   	     	 },
   	     	 {
   	     		 name: 'item origen',
   	     	     field: 'vitem_origen',
   	     	     width: '8%',
   	     	     headerStyles: "text-align: center",
   	     	     styles:'text-align:left;font-size:9;' 
   	     	 },
   	     	 {
   	    		 name: ' ',
   	    	     field: 'vind_especializado',
   	    	     width: '0%',
   	    	     headerStyles: "text-align: center",
   	    	     styles:'text-align:left;'      
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
   				nestedSorting: true //,
   				//indirectSelection: {headerSelector:true, width:"40px", styles:"text-align: center;"}
   			}
   			
   			
   		},dojo.byId("gridDetalle"));
   		  //dojo.byId("gridDetalle").appendChild(grid.domNode);

   		 /*  function myStyleRow(row){
   				
   		       var item = grid.getItem(row.index);
   		       if(item){
   		          var estado = store.getValue(item, "vestadoDesconcentrado", null);
   		         
   		          if(estado=="1"){
   		              row.customStyles += "color:red;";
   		          }
   		       }
   		       grid.focus.styleRow(row);
   		       grid.edit.styleRow(row);
   		    } */
   		 
   		    grid.canEdit = function(inCell, inRowIndex) { 
   		    
   		    //	console.log("inCell = " + inCell.index);
   				if (inCell.index== 6){
   			        var grid = dijit.byId('gridDetalle');
   			    	var items = grid.selection.getSelected();
   			    	
   			    	var tipo_bien="";
   			    	
   			    	var itemIndex = grid.getItem(inRowIndex);
   			    	tipo_bien =grid.store.getValue(itemIndex, 'vtipo_bien') ;
   			    	
   			    	//console.log("tipo_bien = " + tipo_bien);
   		
   			    	if  (tipo_bien=='B'){
   			    		return false;
   			    	}else{
   			    		return true;
   			    	}
   				}else{
   					return false;
   				}
   				
   		    }; 

   		grid.startup();
   		
   		    
   		    
   		/*dojo.connect(grid,'onKeyEvent',
   				    function(evt){  
   			var key = evt.which || evt.keyCode;
   		    // Capturar el codigo de la tecla presionada. 
   		    // Si no es evt.which, usar evt.keyCode (para IE) 
   		    if ((key == 13 || key == 190 || key == 46 || key == 8) || (key >= 48 && key <=57)){
   		        return true; 
   		    }else{
   		    	   dojo.stopEvent(evt);
   		    } 
   		}
   		);*/
   		
   		dojo.connect(grid,'onKeyEvent',
   			    function(e){  
   			
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
   		}
   		);
   	}
   	
  /////////////////////////////////////////////////////////// 	
   	

   	
   	function getCodigoRqnp(valor, item) {
   		var vtxtRqnp = '<input id="txtRqnp" name="txtRqnp" type="hidden"  value="'+valor+'"   /> '; 
   	    return vtxtRqnp; 
   	}

   	function getCodigoBien(valor, item) {
   		var vtxtCodigo = '<input id="txtCodigo" name="txtCodigo" type="hidden"  value="'+valor+'"   /> '; 
   	    return vtxtCodigo; 
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
   		if (tipo_bien=='B'){
   			val_txtPrecioUnit=valor;
   		}else{
   			 val_txtPrecioUnit=new dijit.form.ValidationTextBox({  name: "PrecioUnitx" , value: valor , style:"width:85%;text-align:right;"  ,   regExp:"[0-9]+[\.|\,]*[0-9]*[\.|\,]*[0-9]*[\.|\,]*[0-9]{0,2}"  } );
   			 grid.store.setValue(itemIndex, 'vprecioUnid',removeCommas(valor));
   		}
   		
   		return  val_txtPrecioUnit;
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
   			return  '<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
   		}else if  ( val_exceso >0){
   				return  '<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
   		}else{
   				return  valor;
   			
   			
   		}
   	}
   	
   	


   	function onApplyCellEditDetalle(inValue,inRowIndex, inFieldIndex){
   		//Validando la expresion Regular
   		var patron=/^[0-9]{1,10}(\.[0-9]{0,2})?$/;
   		//console.log("inValue :" + inValue);
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
   			
   			inValue =precio;
   		}
   		
   	 	if( rsta!=null ){
   	 		
   	 		prec_uni= parseFloat(inValue);
   	 		mont_total = cant * prec_uni;
   	 		mont_total_txt =addCommas(mont_total);
   	 		
   	 			grid.store.setValue(selectedItem, 'vprecioTotal',mont_total_txt);
   	 	 		grid.store.setValue(selectedItem, 'vprecioUnid',inValue);  
   	 		 
   	 		
   		}else{
   			//alert('valor no valido');
   		} 

   		
   	}
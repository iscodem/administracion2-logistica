dojo.require("dijit.form.Form");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dijit.Dialog");  
	dojo.require("dojo.date.locale");
    dojo.require("dijit.form.RadioButton");
    dojo.require("dojo.data.ItemFileReadStore");
    dojo.require("dojo.data.ItemFileWriteStore");
   
    dojo.require("dojox.grid.EnhancedGrid");
    dojo.require("dojox.grid.DataGrid");
    dojo.require("dojox.grid.enhanced.plugins.Selector");

    
    dojo.require("dojox.grid.enhanced.plugins.Pagination");
    dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
    dojo.require("dojo._base.array");
    dojo.require("dojo._base.connect");
    dojo.require("dojo._base.event");
    dojo.require("dojo.parser");
    
    dojo.addOnLoad(function() {
    	var store = new dojo.data.ItemFileWriteStore(
    		{
    			data: 
    			{ 
    				items: vlistaBienes
    			}
    		}
    	);
    	
    	var storeMetas = new dojo.data.ItemFileWriteStore(
    			{
    				data: 
    				{ 
    					items: vlistaMetas
    				}
    			}
    		);
    	
    	construirGridDetalle(store);
    	construirGridMetas(storeMetas);
    });
/////////////////////////////////////////////////////////////////////

    function construirGridDetalle(store){

    	var layout = [
    		{
    	     name: ' ',
    	  	 field:'vcodigoRqnp',
    	     width: '0%',
    	     headerStyles: "text-align: center",
    	  	 styles:'text-align:center;'
    	 	},
             {
           	     name: 'C&oacute;digo',
           		 field:'vcodigoBien',
           	     width: '12%',
           	     headerStyles: "text-align: center",
           	  	 styles:'text-align:center;'
           	 },
           	 {
           		 name: 'Bienes y Servicios',
           	  	 field: 'vdesBien',
           	  	 width: '37%',
           	     headerStyles: "text-align: center",
           	  	 styles:'text-align:left;'
           	 },
           	 {
           		 name: ' ',
           	     field: 'vcodigoUnidad',
           	     width: '0%',
           	     headerStyles: "text-align: center",
           	     styles:'text-align:center;'
           	 },
           	 {
           		 name: 'Unid. Med.',
           	     field: 'vdesUnid',
           	     width: '8%',
           	     headerStyles: "text-align: center",
           	     styles:'text-align:center;'
           	 }
           	,
          	 {
          		 name: 'Precio Unit.',
          		  field: 'vprecioUnid',
          	     width: '8%',
          	     headerStyles: "text-align: center",
          	     styles:'text-align:right;' ,
          	    formatter:getPrecioUnit
          	 }
           	,{
          		 name: 'Cantidad',
          	     field: 'vcantBien',
          	     width: '8%',
          	     headerStyles: "text-align: center",
          	     styles:'text-align:right;' ,
          	   formatter:getPrecioUnit
          	 },{
          		 name: 'Total S/.',
          	     field: 'vprecioTotal',
          	     width: '8%',
          	     headerStyles: "text-align: center",
          	     styles:'text-align:right;', 
          	    formatter:getPrecioUnit
          	 }
          	,{
        		 name: 'Saldo S/.',
        	     field: 'vsaldo',
        	     width: '8%',
        	     headerStyles: "text-align: center",
        	     styles:'text-align:right;', 
        	    formatter:getPrecioUnit
        	 }
          	,{
         		 name: 'Exceso S/.',
         	     field: 'vexceso',
         	     width: '8%',
         	     headerStyles: "text-align: center",
         	     styles:'text-align:right;', 
         	    formatter:getPrecioUnit
         	 }
          	,{
         		 name: 'Alerta',
         	     field: 'vimage',
         	     width: '3%',
         	     headerStyles: "text-align: center",
         	     styles:'text-align:right;', 
         	    formatter:getImage
         	 }
           	
       	];

    	var grid = new dojox.grid.EnhancedGrid({
    		
    		store: store,
    		rowSelector: '20px',
    		structure: layout,
    		escapeHTMLInData: false,
    		selectionMode:'single',
    		rowsPerPage: 30,
    		rowCount: 30,
    		canSort: function(col){ return true; } ,
    		style: { height:"150px", width:"98%", left:"1%" } ,
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
    	//grid.selection.select('row', 1,1);
    	//grid.scrollToRow(1);
    	grid.selection.setSelected(0, true); 
    	dojo.connect(grid,'onRowClick',
    		    function(e){
    		
    				var valcodigoBien;
    				var valcodigoRqnp;
    				var valprecioUnid;
    				/// Verificando si se 
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
    			                       }if (attribute=="vprecioUnid"){
    			                    	   valprecioUnid =grid.store.getValues(selectedItem, attribute);
    			                       }
    			                       
    			                    }); /* end forEach */
    			                    if (dojo.byId("jupdate").value=="0"){
    			                     recuperarMetas(valcodigoBien,valprecioUnid);
    			                    }else{
    			                    	btnGuardar_click(valcodigoBien,valprecioUnid);
    			                    }
    			                } 
    			            }); /* end forEach */
    			        } /* end if */
    			}
    		    );
    }
    
    ///////////////////////////////////////////////////////////////////////////

    function construirGridMetas(store){

    	var layout = [
    		{
    		     name: 'Actividad',
    		  	 field:'vubg',
    		     width: '17%',
    		     headerStyles: "text-align: center",
    		  	 styles:'text-align:left;'
    	 	},
          	{
          		 name: ' ',
          	     field: 'vcodigoBienM',
          	     width: '0%',
          	     headerStyles: "text-align: center",
          	     formatter:getBien,
          	     styles:'text-align:left;'
          	 },
             {
           	     name: 'Producto',
           		 field:'vproducto',
           	     width: '17%',
           	  //	type: dojox.grid.cells.Editor,
           	     headerStyles: "text-align: center",
           	  	 styles:'text-align:left;'//,
           	  	//editable: true
           	 },
           	 {
           		 name: 'Acci&oacute;n',
           	  	 field: 'vmeta',
           	  	 width: '17%',
           	     headerStyles: "text-align: center",
           	  	 styles:'text-align:left;'
           	 },
           	{
           		 name: 'Meta Siaf ',
           	     field: 'vmetaSiaf',
           	     width: '17%',
           	     headerStyles: "text-align: center",
           	     styles:'text-align:left;'
           	  //formatter:getSecuencia
           	 },
           	 {
           		 name: ' ',
           	     field: 'vsecuenciaMeta',
           	     width: '0%',
           	     headerStyles: "text-align: center",
           	     styles:'text-align:center;',
           	  formatter:getSecuencia
           	 },
          	 {
          		 name: 'Cantidad',
          		 field: 'vxcantidadTotal',
          	     width: '7%',
          	     headerStyles: "text-align: center",
          	     styles:'text-align:right;' ,
        	   	 formatter:getCantidadX,
            	 editable: true
          	 }
           	,
          	 {
          		 name: ' ',
          		 field: 'vcantidadTotal',
          	     width: '0%',
          	     headerStyles: "text-align: center",
          	     styles:'text-align:right;' ,
        	   	 formatter:getCantidad,
        	   	type: dojox.grid.cells.Cell ,
            	 editable: true
          	 },
          	 {
          		 name: 'Precio Unit.',
          		  field: 'vprecioUnid',
          	     width: '7%',
          	     headerStyles: "text-align: center",
          	     styles:'text-align:right;' ,
          	    formatter:getPrecioUnit
          	 }
           	,{
          		 name: 'Total S/.',
          	     field: 'vxmontoSoles',
          	     width: '7%',
          	     headerStyles: "text-align: center",
          	     styles:'text-align:right;',
          	     formatter:getMontoX
              	 
          	 }
           	,{
         		 name: ' ',
         	     field: 'vmontoSoles',
         	     width: '0%',
         	     headerStyles: "text-align: center",
         	     styles:'text-align:right;',
         	     formatter:getMonto
             	 
         	 }
           	
           	
       	];

    	var grid = new dojox.grid.EnhancedGrid({
    		//id:'gridListaMetas',
    		store: store,
    		rowSelector: '20px',
    		structure: layout,
    		escapeHTMLInData: false,
    		rowsPerPage: 30,
    		rowCount: 30,
    		selectionMode:'single',
    		//selectionMode:'single',
    		//updateDelay:3,
    		singleClickEdit:true,
    		//onDeselected:onDeselectedGridParm,
    		//onFocus :onFocusMetas, 
    		//onCancelEdit:onCancelEditMetas ,
    		onApplyCellEdit:onApplyCellEditMetas,
    		//onStartEdit:onStartEditMetas,
    		//onCellFocus:onCellFocusMetas,
    		canSort: function(col){ return true; } ,
    		style: { height:"150px", width:"98%", left:"1%" } ,
    		errorMessage: "Ha ocurrido un error al procesar la consulta",
    		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
    		noDataMessage: "No se encontraron registros",
    		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
    		plugins: {
    			pagination: {
    				pageSizes: ["30", "60", "90", "120", "All"],
    				description: true,
    				sizeSwitch: false,
    				pageStepper: true,
    				gotoButton: false,
    				maxPageStep: 5,       
    				position: "bottom"
    			},
    			nestedSorting: true
    		}
    	},dojo.byId("gridMetas"));
    	 // dojo.byId("gridMetas").appendChild(grid.domNode);
    	
    	
    	
    	dojo.connect(grid,'onKeyEvent', function(e){  
    		tecla = (document.all) ? e.keyCode : e.which;
    		//alert(tecla);
    		if (tecla==8) 
    		{
    			return true;
    		}else if ( tecla==9 ){
    			//dojo.byId("jload").value='0';
    		}else{
    			patron =/[0-9]/;
    			if (96 <=tecla  && tecla <=105){
    				tecla=tecla - 48;
    			}
    			
    			if ( tecla ==37 || tecla ==39) {
    				
    			}else{
    				te = String.fromCharCode(tecla);
    				//alert(te);
    				if(! patron.test(te)){
    					dojo.stopEvent(e);
    				}
    				dojo.byId("jload").value='1';
    			}
    		}
    	});
    	
    	   grid.canEdit = function(inCell, inRowIndex) { 
    				if (inCell.index == 6){
    			        var visor = dojo.byId('txtvisor').value;
    			    	if  (visor=='1'){
    			    		return false;
    			    	}else{
    			    		return true;
    			    	}
    				}else{
    					return false;
    				}
    		    };
    	grid.startup();
    }
    ///////////////////////////////////////////////////////////////////////////////////
    
    function getCantidadX(valor, rowIndex) {
    	//Validando la expresion Regular
    	var patron=/[0-9]{1,9}/;
    	var rsta = valor.match(patron);
    	var grid = dijit.byId('gridMetas');
    	var items = grid.selection.getSelected();
    	
    	valor =  addCommas(valor);
    	var val_txtCantidad=new dijit.form.ValidationTextBox({  name: "txtxCantidad" , value: valor , style:"width:85%;text-align:right;" ,   regExp:"[0-9]+[\.|\,]*[0-9]*[\.|\,]*[0-9]*[\.|\,]*[0-9]{0,2}" , invalidMessage:"Por favor ingrese la Cantidad " , required:"true" ,   TabIndex :"-1"} );

    	if (dojo.byId("txtvisor").value=="0"){
    		return  val_txtCantidad;
    	}else{
    		return valor;
    	}
    }

    function getCantidad(valor) {
    	var vtxtPrecioUnit = '<input id="txtCantidad"  name="txtCantidad"    value="'+valor+'" dojoType="dijit.form.ValidationTextBox"   required="true" invalidMessage="Por favor ingrese la Cantidad "   onchange="sumarDatos(this.value);" /> '; 
        return vtxtPrecioUnit; 
    }

    function getMonto(valor, item) {
    	var vtxtPrecioUnit = '<input id="txtMonto"  name="txtMonto"    value="'+valor+'" dojoType="dijit.form.ValidationTextBox"      onchange="sumarDatos(this.value);" /> '; 
        return vtxtPrecioUnit; 
    }

    function getPrecioUnit(valor) {
    	return addCommas(valor); 
    }

    function getMontoX(valor) {
        return addCommas(valor); 
    }

    function getBien(valor, item) {
    	var vtxtPrecioUnit = '<input id="txtBien"  name="txtBien"    value="'+valor+'" dojoType="dijit.form.ValidationTextBox"      onchange="sumarDatos(this.value);" /> '; 
        return vtxtPrecioUnit; 
    }


    function getSecuencia(valor, item) {
    	var vtxtPrecioUnit = '<input id="txtSecuencia"  name="txtSecuencia"    value="'+valor+'" dojoType="dijit.form.ValidationTextBox"      onchange="sumarDatos(this.value);" /> '; 
        return vtxtPrecioUnit; 
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
    							if (item.vexceso.toString() !="0.00" ){
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
  
    
    


    function onApplyCellEditMetas(inValue,inRowIndex, inFieldIndex){
    	if (dojo.byId("jload").value='1') {
    		var grid = dijit.byId('gridMetas');
    		var prec_uni;
    		var prec_unix;
    		var cant;
    		var mont_total= parseFloat(0);
    		var mont_total_txt;
    		var rowItem =null;
    		var row=null;
    		var valor ;
    		var cantidadOriginal;
    		var rowItem  = grid.getItem(inRowIndex);
    		prec_uni = parseFloat( removeCommas(grid.store.getValue(rowItem, 'vprecioUnid')));
    		valor = removeCommas(grid.store.getValue(rowItem, 'vxcantidadTotal'));
    	 	
    	 	//**********************************************/
    	 	//SETEANDO DATOS DETALLE----------------------------------------------------------------
 			var grid_bienes = dijit.byId('gridDetalle');
 			var items_bienes =grid_bienes.selection.getSelected();
 			var flagMtoTotal=true;
 			if(items_bienes.length){
 				dojo.forEach(items_bienes, function(selectedItem_bien){
 				      if(selectedItem_bien !== null){
 				      	 dojo.forEach(grid_bienes.store.getAttributes(selectedItem_bien), function(attribute){
 				      		if(attribute=='vcodigoBien') { 
 				      			var tipoItem=grid_bienes.store.getValue(selectedItem_bien, 'vcodigoBien').substr(0,1);
 				      			cantidadOriginal=grid_bienes.store.getValue(selectedItem_bien, 'vcantBien');
 				              	if(tipoItem=='S' && valor=='1'){
 				              		alert("Debe ingresar el monto aproximado en (S/.) que costar\u00E1 el servicio.\n\nEst\u00E1 seguro de la cantidad \u00F3 monto contratado del servicio?");
 				              		flagMtoTotal=false;
 				              	}
 				      		}
 				      	 });
 				      }
 				});
 			}
 			//**********************************************/
    	 	
 			if(flagMtoTotal){
    	 	
				if (valor =="" ){
			 	
			 	}
			 	cant= parseFloat(valor);
			 	if (valor =="" ){
			 		valor =0;	
				}
			  	 		
			 	cant= parseFloat(valor);
				
			 	mont_total = cant * prec_uni;
			 	mont_total= redondear2decimales(mont_total);
				mont_total= mont_total.toFixed(2);
			 	mont_total_txt =addCommas(mont_total);
				 	
			 	 if (rowItem!=null){
			 		grid.store.setValue(rowItem, 'vmontoSoles',mont_total);
			 		grid.store.setValue(rowItem, 'vxmontoSoles',mont_total_txt);
			    	 	
			 		var val_actual=grid.store.getValue(rowItem, 'vcantidadTotal');
			   	 		
			 		if ( val_actual!=valor && dojo.byId("jload").value=='1' ){
			 			dojo.byId("jupdate").value='1';
			 		}
			 		// SUMANDO METAS
					var val_sum_total =parseFloat(0);
					var val_sum_cantidad=parseFloat(0);
					var val_saldo=parseFloat(0);
					var val_exceso=parseFloat(0);
					if(	grid.store !=null){
			    		if((grid.store._arrayOfAllItems.length > 0 )){
			       			grid.store.fetch({
			   				onItem: function(item, request){
								val_sum_total =val_sum_total + parseFloat( item.vmontoSoles);
								val_sum_cantidad =val_sum_cantidad + parseFloat( item.vcantidadTotal);
			 				}
			   			});
			    		}
					}
			    				
			    	val_sum_total= redondear2decimales(val_sum_total);
			    	
			    	val_sum_total=val_sum_total.toFixed(2);
			    	
			    	//SETEANDO DATOS DETALLE----------------------------------------------------------------
			    	//Actualizar para la grilla de bienes
			    	if(items_bienes.length){
			    		dojo.forEach(items_bienes, function(selectedItem_bien){
			    		if(selectedItem_bien !== null){
			    			dojo.forEach(grid_bienes.store.getAttributes(selectedItem_bien), function(attribute){
			    				if(attribute=='vcantBien') { 
			    					val_sum_cantidad = val_sum_cantidad - val_actual + parseFloat(valor);
			    					grid_bienes.store.setValue(selectedItem_bien, 'vcantBien',addCommas(val_sum_cantidad.toString()));
				    	 			}
					    	 		if(attribute=='vprecioTotal') { 
					    	 			 grid_bienes.store.setValue(selectedItem_bien, 'vprecioTotal',addCommas(val_sum_total.toString()));
					    	 		}
					    	     							
					    	 		if(attribute=='vexceso') { 
					    	 			val_saldo = parseFloat( grid_bienes.store.getValue(selectedItem_bien, 'vsaldo'));
					    	 	     	val_exceso =  val_saldo - val_sum_total;
					    	 	     	if ( val_exceso <0){
					    	 	     		val_exceso=val_exceso * (-1);
					    	 	     		
					    	 	     	}else if ( val_exceso >0) {
					    	 	     		val_exceso=0;
					    	 	     	}
					    	 	     		val_exceso= redondear2decimales(val_exceso);
					    	 	     						
					    	 	     	val_exceso=val_exceso.toFixed(2);
				    	 				grid_bienes.store.setValue(selectedItem_bien, 'vexceso',addCommas(val_exceso.toString()));
					    	 		}
			    	 		}); 
			    	 	}
			    	 	}); 
			    	 }
			    	 			
			    	//grid_bienes.store.save();
			        // sumando gran total--------------------------------------------------------
			        var val_grand_total=0;
			        if(	grid_bienes.store !=null){
			        	if((grid_bienes.store._arrayOfAllItems.length > 0 )){
			        		grid_bienes.store.fetch({
			        			onItem: function(item, request){
			        			val_grand_total =val_grand_total + parseFloat( removeCommas(item.vprecioTotal));
			        	 	}
			           		});
			        	}
			        } 
			        
			        //val_grand_total= redondear2decimales(val_grand_total);----------------------
			        val_grand_total=val_grand_total.toFixed(2);
			        dojo.byId("txtMontoAcumulado").value=addCommas( val_grand_total.toString()) ; 
			        grid.store.setValue(rowItem, 'vcantidadTotal',valor);
			    	 			
			    	} // fin (rowItem)
			    			 
			    	return valor;
    	 		
 				}else{// fin (flagMtoTotal)
 					grid.store.setValue(rowItem, 'vxcantidadTotal',cantidadOriginal);
 				} 
    	} // fin (jload)
    }

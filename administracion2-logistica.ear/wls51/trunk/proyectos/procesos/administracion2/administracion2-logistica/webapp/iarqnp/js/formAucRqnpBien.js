
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

 dojo.require("dojo.parser");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.ContentPane");



dojo.addOnLoad(function() {	

	var store = new dojo.data.ItemFileWriteStore(
			{
				data: 
				{items:[]}
			}
		);
		
	//construirGrid(store);
}

);
function construirGrid(store){

	var layout = [
         {
       	     name: 'C\u00F3digo',
       	  	 field: 'codigo_bien',
       	     width: '14%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;font-size:9;font-family: Tahoma, Geneva, sans-serif;'
       	 },
       	 {
       		 name: 'Bienes y Servicios',
       	  	 field: 'desc_bien',
       	  	 width: '40%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:left;font-size:9;font-family: Tahoma, Geneva, sans-serif;'
       	  
       	 },
       	 {
       		 name: ' ',
       	     field: 'codigo_unidad',
       	     width: '0%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 },
       	 {
       		 name: 'Unid. Med.',
       	     field: 'desc_unidad',
       	     width: '10%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;font-size:9;font-family: Tahoma, Geneva, sans-serif;'
       	 },
      	 {
      		 name: 'AUC',
      	     field: 'auct_name',
      	     width: '30%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:left;font-size:9;font-family: Tahoma, Geneva, sans-serif;' 
      	 }
       	,
      	 {
      		 name: 'Precio',
      	     field: 'precio_bien',
      	     width: '6%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;font-size:9;font-family: Tahoma, Geneva, sans-serif;' 
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
		selectionMode:'single',
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

var btnBuscar_click = function(){
	
	var formDlg = dijit.byId("formDialogCatalogoFamilia");
	dijit.byId("tipoConsulta2").setChecked(true);
	var _grdLstPagAnt= dijit.byId("gridBusqueda");

	document.getElementById("txtParametro").value="";
	
	formDlg.show();

	formDlg.onFocus();
	
	var parm =dijit.byId("txtParametro");
	//dijit.focus(parm.domNode);

	var store = new dojo.data.ItemFileWriteStore(
			{
				data: 
				{items:[]}
			}
		);
	
	if (_grdLstPagAnt==null){
		construirGrid(store);
	}else{
		_grdLstPagAnt.destroyRecursive()
		construirGrid(store);
	}
	
	dojo.byId("action").value = "buscarCatalogoJson";
	dojo.byId("jParametro").value = document.getElementById("txtParametro").value;

	var val_parametro =document.getElementById("txtParametro").value;
	var val_tipo_con  =document.getElementById("jtipoConsulta").value;
	var val_txtCod_bien= dojo.byId("txtCod_bien").value

	var grilla= dijit.byId("gridBusqueda");	
		
	//llamada AJAX
	var formQuery ='&jParametro='+val_parametro +'&jtipoConsulta='+val_tipo_con+'&txtCod_bien='+val_txtCod_bien;
	var URL = CONTEXTO_APP + '/bandejaauc.htm?action=buscarCatalogoJson'+formQuery
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
	//document.getElementById("txtParametro").focus();
}
function tipoConsultaSetea(valor){
	dojo.byId("jtipoConsulta").value = valor;
}

function btnBuscarCatalogo_click(){
	dojo.byId("action").value = "buscarCatalogoJson";
	dojo.byId("jParametro").value = document.getElementById("txtParametro").value;

	var val_parametro =document.getElementById("txtParametro").value;
	var val_tipo_con  =document.getElementById("jtipoConsulta").value;
	var val_txtCod_bien= dojo.byId("txtCod_bien").value
	var grilla= dijit.byId("gridBusqueda");	
		
	//llamada AJAX
	var formQuery ='&jParametro='+val_parametro +'&jtipoConsulta='+val_tipo_con+'&txtCod_bien='+val_txtCod_bien;
	var URL = CONTEXTO_APP + '/bandejaauc.htm?action=buscarCatalogoJson'+formQuery
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





function btnAceptarCatalogo_click(){
	var grid= dijit.byId("gridBusqueda");
	
	var items = grid.selection.getSelected();
	var  val_codigo_bien;
	var val_desc_bien;
	var val_codigo_unidad;
	var val_desc_unidad;
	var val_precio_bien;
	var val_codigo_gasto;
	var val_desc_gasto ;
	var val_auct_name;
	var val_tipo_bien;
	var val_rqnp="000";

	
    if(items.length) {
       
        dojo.forEach(items, function(selectedItem){
            if(selectedItem !== null){
               
                dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
                   
                    var value = grid.store.getValues(selectedItem, attribute);
                    
                    switch (attribute) { 
                    case 'codigo_bien': 
                    	val_codigo_bien=value;
                       break ;
                    case 'desc_bien': 
                    	val_desc_bien=value;
                       break ;
                    case 'codigo_unidad': 
                    	val_codigo_unidad=value;
                       break ;
                    case 'desc_unidad': 
                    	val_desc_unidad=value;
                       break ;
                    case 'precio_bien': 
                    	val_precio_bien=value;
                        break ;
                case 'auct_name': 
		        	val_auct_name=value;
		            break ;
                case 'tipo_bien': 
		        	val_tipo_bien=value;
		            break ;
		            
		    	} 
                    
                }); 
            } 
           
            
        }); 
        
        dojo.byId("txtCod_bienM").value = val_codigo_bien;
        dojo.byId("txtDes_bienM").value = val_desc_bien;
        dojo.byId("txtUnidadM").value = val_desc_unidad;
        dojo.byId("txtPrecioM").value = val_precio_bien;
        dojo.byId("txtCod_Unidad").value = val_codigo_unidad;
        
    }
    
    var formDlg = dijit.byId("formDialogCatalogoFamilia");
		formDlg.hide();

}


var btnSalirCatalogo_click = function(){
	var formDlg = dijit.byId("formDialogCatalogoFamilia");
	formDlg.hide();
}


var btnAtras_click = function(){
	//
	if(confirm("Confirme si desea Cancelar el Registro")){
		document.frmRegistro.action="bandejaauc.htm?action=recuperarDetalleAUC";
		document.frmRegistro.submit();
	}
}

var btnGuardar_click = function(){
	//
	if(confirm("Confirme si desea Modificar el \u00EDtem")){
		
		
		dojo.byId("action").value = "registrarModificaBienRqnp";
		
		 
		
		dijit.byId('frmRegistro').submit();
		
	}
}



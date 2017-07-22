dojo.require("dijit.form.Form");
  
dojo.require("dojo.data.ItemFileWriteStore");
dojo.require("dijit.form.Button");
dojo.require("dojox.grid.EnhancedGrid");
dojo.require("dojox.grid.enhanced.plugins.Pagination");
dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
dojo.require("dojo._base.array");

dojo.require("dojo._base.connect");
dojo.require("dojo._base.event");

dojo.require("dojo.date.locale");
dojo.require("dijit.form.Textarea");

dojo.require("dijit.form.SimpleTextarea");
dojo.require("dijit.Dialog");  
dojo.require("dijit.form.CheckBox");
dojo.require("dojo.parser");
   
dojo.require("dijit.form.ComboBox");
dojo.require("dijit.form.FilteringSelect");
	
function stringDateFormatterForDisplay(value, rowIndex) {
	   
   var fecha = value.substring(0,2) + "/" + value.substring(3,5) + "/" + value.substring(6);
   return fecha;
}
   
  
  var typeMap = {  
       "StringDate": {
    	   type: String,
    	   deserialize: function(value){
    		   var fecha = value.substring(6) + value.substring(3,5) + value.substring(0,2);
    		   
    		   return fecha;
    	   }
       }
  };



dojo.addOnLoad(function() {
	var store = new dojo.data.ItemFileWriteStore(
		{
			data: 
			{ 
				items: val_ls_rqnp
			}
		}
	);
	construirGrid(store);
});

function construirGrid(store){

	var layout = [

       	 {
       		 name: 'N&deg; Requerimiento',
       	     field: 'num_rqnp',
       	     width: '10%',
       	     headerStyles: "text-align: center",
       	  	noresize: true,
       	     styles:'text-align:center;'
       	 },
       	 {
       		 name: 'Fecha',
       	  	 field: 'fec_rqnp',
       	  	 width: '6%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;'
       	  	//formatter: stringDateFormatterForDisplay
       	 },
       	 {
      		 name: 'Solicitante',
      	     field: 'solicitante',
      	     width: '13%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:left;' 
      	 },
     	 {
     		 name: 'UUOO Solicitante',
     	     field: 'uuoo_solicitante',
     	     width: '17%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:left;' 
     	 },
     	 {
     		 name: 'Necesidad',
     	     field: 'motivo_sol',
     	     width: '17%',
  
     	     headerStyles: "text-align: center",
     	     styles:'text-align:left;'
     	    	
     	 },
     	 {
     		 name: 'Mes Aten.',
     	     field: 'mes_atencion',
     	     width: '5%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:center;',
     	    formatter:getPrecioX
     	 
     	 },
     	 {
     		 name: 'A&ntilde;o Aten.',
     	     field: 'anio_atencion',
     	     width: '4%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:center;',
     	    formatter:getPrecioX
     	 
     	 },
     	 {
     		 name: 'Valor E.',
     	     field: 'monto_total',
     	     width: '5%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:right;',
     	    formatter:getPrecioX
     	 
     	 },   {
      	     name: 'Atender',
      	  	 field: 'atender',
      	     width: '5%',
      	     headerStyles: "text-align: center",
      	  	 styles:'text-align:center;',
      	  	 formatter:getAtender
      	 },   
      	 /////////////////////////////
      	 {
      	     name: 'Validar',
      	  	 field: 'validar',
      	     width: '5%',
      	     headerStyles: "text-align: center",
      	  	 styles:'text-align:center;',
      	  	 formatter:getValidar
      	 }
      	 /////////////////////////////
      	 ,{
      		 name: 'Seguimiento',
      		 field: 'seguimiento',
      	     width: '6%',
      	   formatter:getSeguimiento,
      	     headerStyles: "text-align: center",
      	     styles:'text-align:center;' 
      	 },{
      		 name: 'Formato ',
      		 field: 'formato',
      	     width: '7%',
      	     formatter:getFormato,
      	     headerStyles: "text-align: center",
      	     styles:'text-align:center;' 
      	 }
      	 
      	 
      	
   	];

	var grid = new dojox.grid.EnhancedGrid({
		store: store,
		rowSelector: '20px',
		structure: layout,
		escapeHTMLInData: false,
		rowsPerPage: 20,
		rowCount: 20,
		
		canSort: function(col){ return true; } ,
		style: { height:"410px", width:"1100px" } ,
		errorMessage: "Ha ocurrido un error al procesar la consulta",
		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
		noDataMessage: "No se encontraron registros",
		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
		plugins: {
			pagination: {
				pageSizes: ["25", "50", "100", "200", "All"],
				description: true,
				sizeSwitch: false,
				pageStepper: true,
				gotoButton: false,
				maxPageStep: 5,       
				position: "bottom"
			},
			nestedSorting: true
		}
	}, dojo.byId("gridConsulta"));
	
	 
	grid.startup();
	
	
	var  val_cod_estado= dijit.byId("txtCod_estado");
	dojo.connect(val_cod_estado,'onChange',
			    function(evt){  
		    	btnRecuperarBandeja();
		});
	
	var  val_anio_act= dijit.byId("txtAnio");
	dojo.connect(val_anio_act,'onChange',
			    function(evt){  
		    	btnRecuperarBandeja();
		});
}



function getPrecioX(valor, rowIndex) {
	var retorno = addCommas(valor);
    return valor ;
}

function getAtender(valor, rowIndex) {
	var grid = dijit.byId('gridConsulta');
	var itemIndex =grid.getItem(rowIndex);
	var val_saldo;
	val_saldo =parseFloat( grid.store.getValue(itemIndex, 'num_auc') );
	if (val_saldo >0){
		return  "<a href='#' onclick='atenderRqnpClic("+valor+");'> Atender   </a>";
	} else{
		return  "-";
	}
}

///////////////////////////////////////////////////////
function getValidar(valor, rowIndex) {
	var grid = dijit.byId('gridConsulta');
	var itemIndex =grid.getItem(rowIndex);
	var val_saldo;
	val_saldo =parseFloat( grid.store.getValue(itemIndex, 'num_auc') );
	if (val_saldo >0){
		return  "<a href='#' onclick='validarRqnpClic("+valor+");'> Validar   </a>";
	} else{
		return  "-";
	}
}
//////////////////////////////////////////////////////


function getSeguimiento(valor, rowIndex) {
	
		return  "<a href='#' onclick='seguimientoRqnpClic("+valor+");'> Seguimiento </a>";
	
}



function getFormato(valor, rowIndex) {
	
	var grid = dijit.byId('gridConsulta');
	var itemIndex =grid.getItem(rowIndex);
	var val_saldo;
	val_saldo =parseFloat( grid.store.getValue(itemIndex, 'num_auc') );
	val_item =parseFloat( grid.store.getValue(itemIndex, 'num_item') );
	//console.log("formato:" + valor);
	if (val_saldo ==0 &&(  val_item >0 )){
		return  "<a href='#' onclick='formatoRqnpClic("+valor+");'> Formato </a>";
	} else{
		return  "-";
	}
}


function getImage(valor,rowIndex){
	var grid = dijit.byId('gridConsulta');
	var itemIndex =grid.getItem(rowIndex);
	var val_num_auc
	val_num_auc =parseFloat( grid.store.getValue(itemIndex, 'vnum_auc') );
	if (val_saldo >0){
		return  valor;
	} else{
		return  '-';
	}
}


function atenderRqnpClic(cod_req){
	dojo.byId("txtCodigoRqnp").value 	= cod_req;
	
	document.frmBandejaAuc.action="bandejaauc.htm?action=aucRqnpCab";	
	document.frmBandejaAuc.submit();
	
}

////////////////////////////////////////////////////////////
function validarRqnpClic(cod_req){
	dojo.byId("txtCodigoRqnp").value 	= cod_req;
	
	document.frmBandejaAuc.action="bandejaauc.htm?action=recuperarRqnp";	
	document.frmBandejaAuc.submit();
	
	
}
///////////////////////////////////////////////////////////


function seguimientoRqnpClic(cod_req){
	dojo.byId("txtCodigoRqnp").value 	= cod_req;
	
	document.frmBandejaAuc.action="rqnpconsulta.htm?action=iniciaListaAcciones";	
	document.frmBandejaAuc.submit();
	
	
}
function formatoRqnpClic(cod_req){
	dojo.byId("txtCodigoRqnp").value 	= cod_req;
	document.frmBandejaAuc.action="bandejaauc.htm?action=descargarArchivoAnexo";
	//document.frmBandejaAuc.action="rqnpconsulta.htm?action=iniciaListaAcciones";	
	document.frmBandejaAuc.submit();
	
	
}


function btnSalirDetalle_click(){
	
	var formDlg = dijit.byId("formDialogDetalle");
	formDlg.hide();
	
};

function btnRecuperarBandeja(){
	var val_anio=  dojo.byId("txtAnio").value;
	var val_estado =dijit.byId('txtCod_estado').get('value') ;
	
	var formQuery ='&anio_act='+val_anio +'&cod_estado='+val_estado;
	var URL = CONTEXTO_APP + '/bandejaauc.htm?action=buscarbandejaaucJson'+formQuery
	   var grilla = dijit.byId("gridConsulta");
		grilla.showMessage('<div id="preloaderConsulta" align="center"><h2><span>Procesando Consulta...</span></h2></div>');
	
	    var kw0 = {
	    		handleAs : "json",
	    		headers: { "Content-Type": "application/json; charset=UTF-8"},
	    		load : function(response, ioArgs) {
	    				//var store = new dojo.data.ItemFileReadStore({data:response});
	    			    //grilla.setStore(store);				
	    			var data = response.items;
    				var store = new dojo.data.ItemFileWriteStore(
    					{
    						data: { 
    							items: data
    						}
    					}
    				);
    				grilla.setStore(store)
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


function addCommas(nStr)
{
	 nStr += '';
	 nStr=removeCommas(nStr);
    
    x = nStr.split('.');
    
    x1 = x[0];
    
    x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    
    return x1 + x2.substring(0,3);
}
function removeCommas(nStr){
	nStr += '';
	var i=0;
	x = nStr.split(',') ;
	j = x.length;
	nStr ='';
	
	while( i <j ){
		nStr += x[i];
		i++;
	}
	
	return nStr;
}

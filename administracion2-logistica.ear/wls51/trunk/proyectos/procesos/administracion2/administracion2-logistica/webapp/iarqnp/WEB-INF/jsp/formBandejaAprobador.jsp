<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Aprobación de Requerimiento No Programado </title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" LANGUAGE="JavaScript" SRC="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>

<script type="text/javascript">
	dojo.require("dijit.form.Form");
	dojo.require("dojo.data.ItemFileReadStore");
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
</script>

<script type="text/javascript">
   /* function stringDateFormatterForDisplay(value, rowIndex) {
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
  };*/
</script>

<script type="text/javascript">
dojo.addOnLoad(function() {
	var store = new dojo.data.ItemFileWriteStore(
		{	
			data: 
			{ 
				items: [
					<c:forEach items="${lsrqnp}"  var="bien" varStatus="status">
		            	{
		            		vcod_rqnp_ext:"${bien.cod_rqnp_ext}", 
		            		vfec_rqnp: "${bien.fec_rqnp}",
		            		vnom_sol:"${bien.nom_sol}", 
		            		vuuoo_sol:"${bien.uuoo_sol}", 
		            		vmotivo_sol:"${bien.motivo_sol}", 
		            		vcod_bien:"${bien.cod_bien}", 
		            		vdes_bien:"${bien.des_bien}",
		            		vunid_bien:"${bien.unid_bien}",
		            		vcantidad:"${bien.cantidad}",
		            		vprec_bien:"${bien.prec_bien}",
		            		vprec_total:"${bien.prec_total}",
		            		vdes_estado:"${bien.des_estado}",
		            		vind_especializado:"${bien.ind_especializado}",
		            		vsaldo:"${bien.saldo}",
		            		vexceso: "${bien.exceso}" ,
		            		vname_auct:"${bien.name_auct}",
		            		vCod_rqnp:"${bien.Cod_rqnp}",
		            		vnumeroArchivo  :"${bien.numeroArchivo}",
		            		vanio_atencion  :"${bien.anio_atencion}",
		            		vmes_atencion  :"${bien.mes_atencion}",
		            		vdescargar:"<a href='#' onclick="+'"'+"descargarArchivo('${bien.Cod_rqnp}','${bien.cod_bien}','${bien.numeroArchivo}' );"+'"'+"> Descargar </a> ",
		            		vlink:"<a href='#' onclick="+'"'+"recuperarMetas('${bien.Cod_rqnp}','${bien.cod_bien}', '${bien.prec_bien}' );"+'"'+"> Afectación  </a> ",
		            		vimage:" "
						}   
		            	${not status.last ? ',' : ''}
					</c:forEach>
				]
			}
		}
	);
	construirGrid(store);
});

function construirGrid(store){

	var layout = [
		{
			 name: '#',
		     field: 'vcod_bien',
		     width: '2%',
		     headerStyles: "text-align: center",
		     styles:'text-align:center;',
		    formatter:getCheck
		 },
        {
      	     name: 'N&deg; Requerimiento',
      	  	 field: 'vcod_rqnp_ext',
      	     width: '7%',
      	     headerStyles: "text-align: center",
      	  	 styles:'text-align:center;'
      	 },
       	 {
       		 name: 'Solicitante',
       	     field: 'vnom_sol',
       	     width: '6%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 },
       	 {
      		 name: '&Iacute;tem',
      	     field: 'vdes_bien',
      	     width: '12%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:left;' 
      	 },
     	 {
     		 name: 'Unid.',
     	     field: 'vunid_bien',
     	     width: '4%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:center;' 
     	 },
     	 {
     		 name: 'Cantidad',
     	     field: 'vcantidad',
     	     width: '3%',
  
     	     headerStyles: "text-align: center",
     	     styles:'text-align:right;',
     	    	formatter:getPrecioX
     	 },
     	 {
     		 name: 'Precio',
     	     field: 'vprec_bien',
     	     width: '3%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:right;',
     	    formatter:getPrecioX
     	 
     	 },
     	 {
     		 name: 'Total S/.',
     	     field: 'vprec_total',
     	     width: '5%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:right;' ,
     	   formatter:getPrecioX
     	 },   {
      	     name: 'Saldo',
      	  	 field: 'vsaldo',
      	     width: '3%',
      	     headerStyles: "text-align: center",
      	  	 styles:'text-align:right;',
      	  		formatter:getPrecioX
      	 },{
      		 name: 'Exceso',
      		 field: 'vexceso',
      	     width: '3%',
      	     formatter:getPrecioX,
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' 
      	 },{
      		 name: 'Mes Aten.',
      		 field: 'vmes_atencion',
      	     width: '4%',
      	        headerStyles: "text-align: center",
      	     styles:'text-align:center;' 
      	 },{
      		 name: 'A&ntilde;o Aten.',
      		 field: 'vanio_atencion',
      	     width: '3%',
      	   
      	     headerStyles: "text-align: center",
      	     styles:'text-align:center;' 
      	 },{
      		 name: 'Alerta',
      		 field: 'vimage',
      	     width: '3%',
      	     formatter:getImage,
      	     headerStyles: "text-align: center",
      	     styles:'text-align:center;' 
      	 }
      	,{
     		 name: 'Descargar',
     	     field: 'vdescargar',
     	    formatter:getDescargar ,
     	     width: '4%',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:center;' 
     	 },
       	 {
       		 name: 'Fecha',
       	  	 field: 'vfec_rqnp',
       	  	 width: '6%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;'
       	  	//formatter: stringDateFormatterForDisplay
       	 }
      	 ,
     	 {
       		 name: 'Motivo',
       	     field: 'vmotivo_sol',
       	     width: '18%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 }
      	,
      	 {
      		 name: 'UUOO',
      	     field: 'vuuoo_sol',
      	     width: '10%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:left;'
      	 	 
      	 },
        {
      	     name: 'AUCT',
      	  	 field: 'vname_auct',
      	     width: '0%',
      	     headerStyles: "text-align: center",
      	  	 styles:'text-align:left;'
      	 },
      	{
      	     name: 'Afectaci&oacute;n',
      	  	 field: 'vlink',
      	     width: '4%',
      	     headerStyles: "text-align: center",
      	  	 styles:'text-align:center;'
      	 },
      	 
    	 {
    		 name: ' ',
    	     field: 'vdes_estado',
    	     width: '0%',
    	     headerStyles: "text-align: center",
    	     styles:'text-align:center;' 
    	 },
      	{
    		 name: ' ',
    	     field: 'vind_especializado',
    	     width: '0%',
    	     headerStyles: "text-align: center",
    	     styles:'text-align:center;' ,
    	    formatter:getEspecializado
    	 },
      	{
	   		 name: ' ',
	   	     field: 'vCod_rqnp',
	   	     width: '0%',
	   	     headerStyles: "text-align: center",
	   	     styles:'text-align:center;' ,
	   	     formatter:getRqnp
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
		style: { height:"520px", width:"1700px" } ,
		errorMessage: "Ha ocurrido un error al procesar la consulta",
		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
		noDataMessage: "No se encontraron registros",
		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
		plugins: {
			pagination: {
				pageSizes: ["20", "40", "60", "100", "200", "All"],
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
}

function construirGridMetas(store){
	var layout = [
		{
		     name: 'Actividad',
		  	 field:'vubg',
		     width: '20%',
		     headerStyles: "text-align: center",
		  	 styles:'text-align:center;'
	 	},{
      		 name: ' ',
      	     field: 'vcodigoBienM',
      	     width: '0%',
      	     headerStyles: "text-align: center",
      	   //formatter:getBien,
      	     styles:'text-align:center;'
      	},{
       	     name: 'Producto',
       		 field:'vproducto',
       	     width: '20%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;'
       	 
       	},{
       		 name: 'Meta',
       	  	 field: 'vmeta',
       	  	 width: '20%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:left;'
       	},{
       		 name: ' ',
       	     field: 'vsecuenciaMeta',
       	     width: '0%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	  //formatter:getSecuencia
       	},{
      		 name: 'Cantidad.',
      		  field: 'vxcantidadTotal',
      	     width: '10%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' ,
      	    	formatter:getPrecioX
        	 
      	},{
      		 name: 'Precio Unit.',
      		  field: 'vprecioUnid',
      	     width: '10%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' ,
      	    	formatter:getPrecioX
      	},{
      		 name: 'Total S/.',
      	     field: 'vxmontoSoles',
      	     width: '10%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;',
      	     formatter:getPrecioX
      	 }
   	];

	var grid = new dojox.grid.EnhancedGrid({
		//id:'gridListaMetas',
		store: store,
		rowSelector: '20px',
		structure: layout,
		escapeHTMLInData: false,
		rowsPerPage: 5,
		rowCount: 5,
		canSort: function(col){ return true; } ,
		style: { height:"140px", width:"98%", left:"1%" } ,
		errorMessage: "Ha ocurrido un error al procesar la consulta",
		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
		noDataMessage: "No se encontraron registros",
		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
		plugins: {
			pagination: {
				pageSizes: ["5", "10", "20", "30", "All"],
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
	
	grid.startup();
}

</script>

<script type="text/javascript">
function getPrecioX(valor, rowIndex) {
	var retorno = addCommas(valor);
    return retorno ;
}


function getRqnp(valor, item) {
	var vtxtRqnp = '<input id="txtCodigoRqnp" name="txtCodigoRqnp" type="hidden"  value="'+valor+'"   /> '; 
    return vtxtRqnp; 
}

function getCheck(valor, rowIndex) {
	var cod_rqnp;
	var ind_esp;
	var cod_bien;
	var grid = dijit.byId('gridConsulta');
	var itemIndex = grid.getItem(rowIndex);
	cod_rqnp =grid.store.getValue(itemIndex, 'vCod_rqnp') ;
	cod_bien =grid.store.getValue(itemIndex, 'vcod_bien') ;
	ind_esp =grid.store.getValue(itemIndex, 'vind_especializado') ;
	
	if(dijit.byId("cbx"+cod_rqnp+cod_bien)){
	
		 return dijit.byId("cbx"+cod_rqnp+cod_bien);
	}else{
		
		return  new dijit.form.CheckBox({ id:"cbx"+cod_rqnp+cod_bien,  name: "txtCodigobien", value: cod_rqnp +','+cod_bien+','+ind_esp,  checked:false});
	}
} 

function getDescargar(valor , rowIndex){

	var grid = dijit.byId('gridConsulta');
	
	var itemIndex = grid.getItem(rowIndex);
	
	var val_archivo= grid.store.getValue(itemIndex, 'vnumeroArchivo') ;

	if (val_archivo==""  ){
		return "Sin Archivos";
	}else{
		return valor;
	}
}

function getEspecializado(valor, item) {
	var vtxtEspecializado = '<input id="txtEspecializado" name="txtEspecializado" type="hidden"  value="'+valor+'"   /> '; 
    return vtxtEspecializado; 
}



/*
var btnAprobar_click = function(rqnp,bien){
	var sw='0';
	
	for(var i=0; i < document.frmBandejaJI.txtCodigobien.length; i++){
		if(document.frmBandejaJI.txtCodigobien[i].checked){
			sw='1';
		}
	}

	if (sw=='1'){
		if(confirm("Confirme si desea APROBAR el Requerimiento No Programado")){
			dojo.byId("accion").value = "aprobarRqnpJI";
			var formDlg = dijit.byId("formDialogEnvio");
			formDlg.show();
			dojo.byId("frmBandejaJI").submit();
			}
	}else{
		alert("Seleccione los ítems que desea Aprobar");
	}
};
*/


var btnAprobar_click = function(rqnp,bien){
	var sw='0';
	
	for(var i=0; i < document.frmBandejaJI.txtCodigobien.length; i++){
		if(document.frmBandejaJI.txtCodigobien[i].checked){
			sw='1';
		}
	}
	
	if (sw=='1'){
		if(confirm("Confirme si desea APROBAR el Requerimiento No Programado")){
			var formDlg = dijit.byId("formDialogEnvio");
			formDlg.show();
			
			dojo.byId("accion").value = "aprobarRqnpJI";
		   	//llamada AJAX
		   	var enviar = {
		   			handleAs : "json",
		   			load : function(response, ioArgs) {
		   				if (response.data != "" && response.data != "[]") {
		   					var flag = response.data.flagRegistroDeclaracion;
		   					var mensaje = response.data.mensaje;
		   					if (flag=="1"){						
		   				  		alert("Mensaje: "+mensaje);	
		   				  		formDlg.hide();
		   				  	}else{
								document.frmBandejaJI.action="bandejaji.htm?accion=aprueba"; 
								dojo.byId("frmBandejaJI").submit();
		   				  	}
		   				}else{
		   					var data = eval("(" + response.data + ")");
		   					alert(response.data.mensaje);
		   				}
		   			},
		   			timeout : 50000,
		   			error : function(error, ioArgs) {
		   				alert("Error [" + error.message + "]");
		   			},
		   			form: dojo.byId("frmBandejaJI")
		   	};	
		   	dojo.xhrPost(enviar);
		   	}
	}else{
		alert("Seleccione los ítems que desea Aprobar");
	}
};



function getall(){
	var cod_rqnp;
	var cod_bien;

	var grid = dijit.byId('gridConsulta');
	var chk_txt;
	var itemIndex;

	for(var i=0; i < document.frmBandejaJI.txtCodigobien.length -1 ; i++){
		
		itemIndex = grid.getItem(i);
		cod_rqnp =grid.store.getValue(itemIndex, 'vCod_rqnp') ;
		cod_bien =grid.store.getValue(itemIndex, 'vcod_bien') ;
	
		chk_txt = dijit.byId("cbx"+ cod_rqnp + cod_bien);
		if (document.frmBandejaJI.txtcheck.checked){
			
			chk_txt.setChecked(true);

		}else{
			
			chk_txt.setChecked(false);
			//document.frmBandejaJI.txtCodigobien[i].checked=0;
		}
		
	}
}
var btnRechazar_click = function(rqnp,bien){
	
var sw='0';
	for(var i=0; i < document.frmBandejaJI.txtCodigobien.length; i++){
		if(document.frmBandejaJI.txtCodigobien[i].checked){
			sw='1';
		}
	}

	if (sw=='1'){
		var formDlg = dijit.byId("formDialogRechazo");
		formDlg.show();
	}else{
		alert("Seleccione los ítems que desea Rechazar");
	}
};

function getImage(valor,rowIndex){
	var grid = dijit.byId('gridConsulta');
	var itemIndex =grid.getItem(rowIndex);
	var val_saldo
	val_saldo =parseFloat( grid.store.getValue(itemIndex, 'vexceso') );
	if (val_saldo >0){
		return  '<img name="eje01" src="/a/imagenes/circulorojo.gif"/>';
	} else{
		return  valor;
	}
}

var btnAceptarRechazo_click = function(rqnp,bien){
	dojo.byId("txtRechazo").value = dojo.byId("txtRechazoTem").value;
	var formDlg = dijit.byId("formDialogRechazo");
	formDlg.hide();
	
	var formDlgRechazo = dijit.byId("formDialogEnvioRechazo");
	formDlgRechazo.show();
	
	dojo.byId("accion").value = "rechazarRqnp";
	
	//llamada AJAX
	var enviar = {
		handleAs : "json",
		load : function(response, ioArgs) {
			if (response.data != "" && response.data != "[]") {
				var flag = response.data.flagRegistroDeclaracion;
				var mensaje = response.data.mensaje;
				if (flag=="1"){						
			  		formDlgRechazo.hide();
			  		alert("Mensaje: "+mensaje);	
			  	}else{
					document.frmBandejaJI.action="bandejaji.htm?accion=aprueba"; 
					dojo.byId("frmBandejaJI").submit();
					formDlgRechazo.hide();
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
	   	form: dojo.byId("frmBandejaJI")
	};	
	
	dojo.xhrPost(enviar);
};

var btnSalirRechazo_click = function(rqnp,bien){
	
	var formDlg = dijit.byId("formDialogRechazo");
	formDlg.hide();
	
};

var btnSalir_click = function(){
	if(confirm("Confirme si desea salir del Sistema")){
		window.parent.close();
	}
};



function recuperarMetas(cod_rqnp,cod_bien,precioUnid){

	dojo.byId("accion").value = "recuperarMetasVistaJson";

	dojo.byId("jcodigo_bien").value = cod_bien;
	dojo.byId("jprecio_unid").value = precioUnid;
	dojo.byId("txtCodigoRqnp").value = cod_rqnp;
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						
						var data = eval("(" + unescape( response.data )+ ")");
						
						actualizarMetas(data.listaMetas);
					}
				}
				else{
					alert(response.data.mensaje);
					
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert("error ajax");
				//alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				alert("<fmt:message key='error.ajax'/>");
				
			},
			form: dojo.byId("frmBandejaJI")
	};	
	
	dojo.xhrPost(enviar);
	
	var formDlg = dijit.byId("formDialogDetalle");
	formDlg.show();
	
}

function btnSalirDetalle_click(){
	
	var formDlg = dijit.byId("formDialogDetalle");
	formDlg.hide();
	
};

function actualizarMetas(lista){	
	
	var lsMetas =lsMetasItems(lista);
	
	var storeMetas = new dojo.data.ItemFileWriteStore({
		data: {
			identifier: 'vsecuenciaMeta',
			items: lsMetas
		}});
	
	var _grdLstPagAnt= dijit.byId("gridMetas");
	if (_grdLstPagAnt==null){
		
		construirGridMetas(storeMetas);
	}else{
		_grdLstPagAnt.setStore(storeMetas);
	}
}


function lsMetasItems(items){
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
			cadena += "{vcodigoRqnpM:'" + item.codigoRqnp + "',";	
			cadena += "vcodigoBienM:'" + item.codigoBien + "',";
			cadena += "vanioEjec:'" + item.anioEjec+ "',";
			cadena += "vsecuenciaMeta:'" + item.secuenciaMeta + "',";
			cadena += "vcantidadTotal:'" + item.cantidadTotal + "',";	
			cadena += "vxcantidadTotal:'" + item.cantidadTotal + "',";	
			cadena += "vmontoSoles	:'" + item.montoSoles  + "',";
			cadena += "vxmontoSoles	:'" + item.montoSoles  + "',";
			cadena += "vprecioUnid	:'" + item.precioUnid  + "',";
			cadena += "vproducto	:'" + item.producto  + "',";
			cadena += "vmeta	:'" + item.meta + "',";
			cadena += "vubg:'" + item.ubg  + "'}";
		});
		cadena += "]";
			
		var lst_pag_ant = eval("(" + cadena + ")");
		return lst_pag_ant;
	} catch(e) {
		return [];
	}
	
}

function descargarArchivo(cod_req,cod_bien,num_reg){
	dojo.byId("txtCodigoRqnp").value = cod_req;
	dojo.byId("txtCodigobien").value = cod_bien;
	dojo.byId("txtNum_reg").value = num_reg;
	
	document.frmBandejaJI.action="rqnparchivo.htm?accion=iniciarDescargaArchivoJI";
		
	document.frmBandejaJI.submit();
}


dojo.addOnLoad(function() {	
	
	var formDlg = dijit.byId("formDialogRechazo");
	formDlg.hide();
	formDlg.style.display='none';
	
});

</script>

</head>
<body  class="tundra">
<div dojoType="dojo.data.ItemFileReadStore" url="${pageContext.request.contextPath}/bandejaauc.htm?accion=listarMesesJson" jsId="idMesesStore"></div>
	<center>
		<form id="frmBandejaJI" name="frmBandejaJI" action="bandejaji.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="accion" name="accion" />
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" />
			<input type="hidden" id="txtCodigobien" name="txtCodigobien" />
			<input type="hidden" id="txtRechazo" name="txtRechazo" />
			<input type="hidden" id="jcodigo_bien" name="jcodigo_bien" />
			<input type="hidden" id="jprecio_unid" name="jprecio_unid" />

			<input type="hidden" id="txtNum_reg"" name="txtNum_reg"" />
			
			<table width="99%" cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%" border=0>
							<tr >
								<td colspan="2">
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2" align="left">
												
												<div align="center" >   &nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;  Aprobar  Requerimientos No Programados <c:out value="${tituloVista}"></c:out>  </div>
											</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr >
								<td colspan="2">&nbsp;</td>
							</tr>
							
							<tr >
								<td align="left" >
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input id="txtcheck" name="txtcheck"   
										value="" dojoType="dijit.form.CheckBox" 
										size="5"
										
										onclick="getall()" />
									<td  align="left" >
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<button id="btnAprobarCab"  name="btnAprobarCab" type="button" dojoType="dijit.form.Button" onclick="btnAprobar_click()">Aprobar</button>
										<button id="btnRechazarCab" name="btnRechazarCab" type="button" dojoType="dijit.form.Button" onclick="btnRechazar_click()">Rechazar</button>
									</td >
							</tr>
							
							<tr>
								<td colspan="2" >
									<table width="100%" cellpadding="8" cellspacing="2" border="0">
										<tr>
											<td>
												<div id="gridConsulta"></div>
											</td>
										</tr>								
									</table>
								</td>
							</tr>	
							<!-- Comentado para no mostrar leyenda de exceso de saldo
							<tr>
								<td width="4%">
									<img name="eje01" src="/a/imagenes/circulorojo.gif"/> 
								</td>
								<td width="96%" align="left" >
									<P> El ítem NO cuenta con SALDO para su atención por el canal de Compras Directas. Por lo tanto será atendido por otro canal
									</P>
								</td>
							</tr>		 
							-->				
						</table>
					</td>
				</tr>
			</table>
			
			<table width="98%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
						<button id="btnAprobar" name="btnAprobar" type="button" dojoType="dijit.form.Button" onclick="btnAprobar_click()">Aprobar</button>
						<button id="btnRechazar" name="btnRechazar" type="button" dojoType="dijit.form.Button" onclick="btnRechazar_click()">Rechazar</button>
					</td>
				</tr>
			</table>
			
			
			<div  dojoType="dijit.Dialog"  style= "width: 350px; height: 220px; display:none;" id="formDialogRechazo" title="Confirmación" execute="" >
			<table width="100%"    >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Rechazar RQNP</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td align="center">
									<table width="98%" cellpadding="2" cellspacing="1">
										<tr>
										<td class="bg" align="left"> &nbsp;&nbsp;Motivo:</td>
										</tr>
										<tr>											
											
											<td class="bg" align="left">
												<input id="txtRechazoTem" name="txtRechazoTem" 
												value=""
												style="overflow-y: hidden; overflow-x: auto; 
												-moz-box-sizing: border-box; width:300px; height:50px; border: 5px solid gray; 
												 margin: 7px; min-height: 1.5em;"
													 
													dojoType="dijit.form.SimpleTextarea" trim="true" 
													invalidMessage="Por favor ingrese un Motivo"													
													maxlength="200"
													
												/>
												
												&nbsp;&nbsp;&nbsp;
												
											</td>
										</tr>
									</table>
								</td>
								
							</tr>

							<tr>
								<td>&nbsp;</td>
							</tr>
							
												
						</table>
					</td>
				</tr>
			</table>
			<table width="98%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
						
						<button id="btnAceptar" name="btnAceptar" type="button" dojoType="dijit.form.Button" onclick="btnAceptarRechazo_click()">Aceptar</button>
						
						&nbsp;&nbsp;&nbsp;
						<button id="btnSalirCatalogo" name="btnSalirRechazo" type="button" dojoType="dijit.form.Button" onclick="btnSalirRechazo_click()">Cancelar</button>
					</td>
				</tr>
			</table>
			</div>
			
			
			<div  dojoType="dijit.Dialog"  style= "width: 800px; height: 280px; display:none;" id="formDialogDetalle" title="Confirmacion" execute="" >
				<table width="100%"    >
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
								<tr>
									<td>
										<table width="100%" cellpadding="2" cellspacing="1" >
											<tr>
												<td class="T1" colspan="2">
													<div align="center">Afectación Presupuestal POI</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" cellpadding="8" cellspacing="2" border="0">
											<tr>
												<td>
													<div id="gridMetas"  data-dojo-id="gridMetas" > </div>
												</td>
											</tr>								
										</table>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
					<table width="98%" cellpadding="2" cellspacing="2">
						<tr>
							<td align="center">
								<button id="btnSalirDetalle" name="btnSalirDetalle" type="button" dojoType="dijit.form.Button" onclick="btnSalirDetalle_click()">Aceptar</button>
							</td>
						</tr>
					</table>
			</div>
			<div  dojoType="dijit.Dialog" style= "width: 350px; height: 150px; display:none;"  id="formDialogEnvio" title="Procesando la Aprobación" execute="" >
			<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>											
											<td class="bg" align="center"  colspan="2" > <h6>Se está Procesando la Aprobación.</h6></td>
										</tr>
									</table>
								</td>
							</tr>
					</table>
				</td>
				</tr>
			</table>
			</div>
			
			<div  dojoType="dijit.Dialog" style= "width: 350px; height: 150px; display:none;"  id="formDialogEnvioRechazo" title="Procesando el Rechazo" execute="" >
			<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>											
											<td class="bg" align="center"  colspan="2" > <h6>Se está Procesando el Rechazo del Requerimiento.</h6></td>
										</tr>
									</table>
								</td>
							</tr>
					</table>
				</td>
				</tr>
			</table>
			</div>
		</form>
	</center>
</body>

<script type="text/javascript">
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
</script>

</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Aprobación de Requerimiento No Programado </title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" LANGUAGE="JavaScript" SRC="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>

<script type="text/javascript">
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
    dojo.require("dijit.Dialog");  
    dojo.require("dijit.form.CheckBox");
    dojo.require("dojo.parser");
    dojo.require("dijit.form.FilteringSelect");
    
    
</script>

<script type="text/javascript">
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
		            		vid:"${bien.Cod_rqnp}"+"${bien.cod_bien}",
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
		            		vlink:"<a href='#' onclick="+'"'+"recuperarMetas('${bien.Cod_rqnp}','${bien.cod_bien}', '${bien.prec_bien}' );"+'"'+"> Detalle  </a> ",
		            		
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
			 name: ' ',
		     field: 'vcod_bien',
		     width: '0%',
		     headerStyles: "text-align: center",
		     styles:'text-align:center;',
		    formatter:getCheck
		 },
       	 {
       		 name: 'Solicitante',
       	     field: 'vnom_sol',
       	     width: '12%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 },
       	 {
       		 name: 'Fecha',
       	  	 field: 'vfec_rqnp',
       	  	 width: '7%',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;'
       	  	//formatter: stringDateFormatterForDisplay
       	 },
       	 {
      		 name: 'Ítem',
      	     field: 'vdes_bien',
      	     width: '14%',
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
      	     width: '4%',
      	     formatter:getPrecioX,
      	     headerStyles: "text-align: center",
      	     styles:'text-align:right;' 
      	 },{
      		 name: 'Alerta',
      		 field: 'vimage',
      	     width: '3%',
      	     formatter:getImage,
      	     headerStyles: "text-align: center",
      	     styles:'text-align:center;' 
      	 },{
      		 name: 'Año Ate.',
      		 field: 'vimage',
      	     width: '3%',
      	     formatter:getImage,
      	     headerStyles: "text-align: center",
      	     styles:'text-align:center;' 
      	 },{
      		 name: 'Mes Ate.',
      		 field: 'vimage',
      	     width: '3%',
      	     formatter:getImage,
      	     headerStyles: "text-align: center",
      	     styles:'text-align:center;' 
      	 },
     	 {
       		 name: 'Motivo',
       	     field: 'vmotivo_sol',
       	     width: '7%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 }
      	,
      	 {
      		 name: 'UO',
      	     field: 'vuuoo_sol',
      	     width: '10%',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:left;'
      	 	 
      	 },
        {
      	     name: 'Número de Requer.',
      	  	 field: 'vcod_rqnp_ext',
      	     width: '7%',
      	     headerStyles: "text-align: center",
      	  	 styles:'text-align:center;'
      	 },
        {
      	     name: 'AUCT',
      	  	 field: 'vname_auct',
      	     width: '9%',
      	     headerStyles: "text-align: center",
      	  	 styles:'text-align:left;'
      	 },
      	{
      	     name: 'Detalle',
      	  	 field: 'vlink',
      	     width: '3%',
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
		style: { height:"410px", width:"1700px" } ,
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
	
	var stateStore = new dojo.data.ItemFileWriteStore({
        data: { 
        	identifier: 'id',
        	  label: 'name',

			items: [
			<c:forEach items="${lsEstados}"  var="estado" varStatus="status">
            {	id:"${estado.val_estado}",
            	name:"${estado.desc_estado}" 
            }
            ${not status.last ? ',' : ''}
			</c:forEach>
			]
		}
	}
	); 
	
	var comboBox = 
		 new dijit.form.FilteringSelect({
        id: "txtEstado",
        name: "txtEstado",
        value: "00",
        store: stateStore,
        
        searchAttr: "name",
        disabled:false
    }, dojo.byId("txtEstado"));
	 
	 
	 dojo.connect(comboBox,'onChange',
			    function(evt){  
			 btnBuscarRequerimientos_click();
		}
		);
	 
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

function getEspecializado(valor, item) {
	var vtxtEspecializado = '<input id="txtEspecializado" name="txtEspecializado" type="hidden"  value="'+valor+'"   /> '; 
    return vtxtEspecializado; 
}

var btnAprobar_click = function(rqnp,bien){
	var sw='0';
	
	for(var i=0; i < document.frmDetalleConsulta.txtCodigobien.length; i++){
		if(document.frmDetalleConsulta.txtCodigobien[i].checked){
			sw='1';
		}
	}
	if (sw=='1'){
		if(confirm("Confirme si desea APROBAR el Requerimiento No Programado")){
			dojo.byId("action").value = "aprobarRqnpJI";
			dojo.byId("frmDetalleConsulta").submit();
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
	for(var i=0; i < document.frmDetalleConsulta.txtCodigobien.length -1 ; i++){
		
		 itemIndex = grid.getItem(i);
		cod_rqnp =grid.store.getValue(itemIndex, 'vCod_rqnp') ;
		cod_bien =grid.store.getValue(itemIndex, 'vcod_bien') ;
	
		
		 chk_txt = dijit.byId("cbx"+ cod_rqnp + cod_bien);
		if (document.frmDetalleConsulta.txtcheck.checked){
			chk_txt.setChecked(true);
		}else{
			chk_txt.setChecked(false);
			//document.frmDetalleConsulta.txtCodigobien[i].checked=0;
		}
	}
}
var btnRechazar_click = function(rqnp,bien){
var sw='0';
	for(var i=0; i < document.frmDetalleConsulta.txtCodigobien.length; i++){
		if(document.frmDetalleConsulta.txtCodigobien[i].checked){
			sw='1';
		}
	}
	if (sw=='1'){
		if(confirm("Confirme si desea RECHAZAR el Requerimiento No Programado")){
			dojo.byId("action").value = "rechazarRqnp";
			var formDlg = dijit.byId("formDialogRechazo");
			formDlg.show();
		}
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
	dojo.byId("frmDetalleConsulta").submit();
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


function recuperarMetas(cod_rqnp){
	var grid = dijit.byId('gridConsulta');
	var cod_bien ;
	var cod_rqnp_new ;
	var precioUnid;
	 if(	grid.store !=null){
 		if((grid.store._arrayOfAllItems.length > 0 )){
 			grid.store.fetch({
 				onItem: function(item, request){
 							if (item.vid.toString() ==cod_rqnp.toString() ){
 								cod_rqnp_new	= item.vid.toString();
 								cod_bien		= item.vid.toString();
 								precioUnid		= item.vid.toString();
 								
 							}
 							
 		 				}
 			});
 		}
 	}
	 alert(cod_rqnp_new);
	 alert(cod_bien);
	 alert(precioUnid);
	dojo.byId("action").value = "recuperarMetasVistaJson";

	dojo.byId("jcodigo_bien").value 	= cod_bien;
	dojo.byId("jprecio_unid").value 	= precioUnid;
	dojo.byId("txtCodigoRqnp").value 	= cod_rqnp_new;
	
	
	
	
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

dojo.addOnLoad(function() {	
	
	var formDlg = dijit.byId("formDialogRechazo");
	formDlg.hide();
	formDlg.style.display='none';
	
	
	
	
	
});

function btnBuscarRequerimientos_click(){

	dojo.byId("action").value = "iniciarConsultaBusquedaJI";
	
	//dojo.byId("jtipoConsulta").value = "D";
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						var data = eval("(" + unescape(response.data) + ")");
						//alert(data.lsRqnp);
						actualizar(data.lsRqnp);
					}
				}
				else{
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
				//alert("<fmt:message key='error.ajax'/>");
			},
			form: dojo.byId("frmDetalleConsulta")
	};	
	dojo.xhrPost(enviar);
}


function actualizar(lista){	
	
	var lsCatalogo =lsItems(lista);
	
	var store = new dojo.data.ItemFileWriteStore({
		data: {
			identifier: 'vid',
			items: lsCatalogo
		}});
	
	var _grdLstPagAnt= dijit.byId("gridConsulta");
	if (_grdLstPagAnt==null){
		
		construirGrid(store);
		
	}else{
		
		//_grdLstPagAnt.destroyRecursive();
		
		_grdLstPagAnt.setStore(store);
	}
	
}



function lsItems(items){
	try {
		if(items == null) {
			items = "[]";
		}
		var items2 = eval("(" + items + ")");
		var cod_id;
		var cadena = "[";
		dojo.forEach(items2, function(item){
			if (cadena != "[") {
				cadena += ",";
			}
			cod_id=  item.Cod_rqnp.toString() + item.cod_bien.toString();
			cadena += "{vid:'" + item.id +"',";	
			cadena += "vcod_rqnp_ext:'" + item.cod_rqnp_ext + "',";
			cadena += "vfec_rqnp:'" + item.fec_rqnp+ "',";
			cadena += "vnom_sol:'" + item.nom_sol + "',";
			cadena += "vuuoo_sol:'" + item.uuoo_sol + "',";	
			cadena += "vmotivo_sol:'" + item.motivo_sol + "',";	
			cadena += "vcod_bien	:'" + item.cod_bien  + "',";
			cadena += "vdes_bien	:'" + item.des_bien  + "',";
			cadena += "vunid_bien	:'" + item.unid_bien  + "',";
			cadena += "vcantidad	:'" + item.cantidad  + "',";
			cadena += "vprec_bien	:'" + item.prec_bien + "',";
			cadena += "vprec_total	:'" + item.prec_total + "',";
			cadena += "vdes_estado	:'" + item.des_estado + "',";
			cadena += "vind_especializado:'" + item.ind_especializado+ "',";
			cadena += "vsaldo	:'" + item.saldo+ "',";
			cadena += "vexceso	:'" + item.exceso+ "',";
			cadena += "vname_auct	:'" + item.name_auct+ "',";
			cadena += "vCod_rqnp	:" + item.Cod_rqnp+ ",";
			
			cadena += "vlink	:'',";
			
			cadena += "vimage:''}";
		
		});
		cadena += "]";
			
		var lst_pag_ant = eval("(" + cadena + ")");
		return lst_pag_ant;
	} catch(e) {
		return [];
	}
}

</script>

</head>
<body  class="tundra">

	<center>
		<form id="frmDetalleConsulta" name="frmDetalleConsulta" action="rqnp.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="action" name="action" />
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" />
			<input type="hidden" id="txtCodigobien" name="txtCodigobien" />
			<input type="hidden" id="txtRechazo" name="txtRechazo" />
			<input type="hidden" id="jcodigo_bien" name="jcodigo_bien" />
			<input type="hidden" id="jprecio_unid" name="jprecio_unid" />
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" />
			
			
			<table width="99%" cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%" border=0>
							<tr >
								<td colspan="2">
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Aprobar  Requerimientos No Programados     - <c:out value="${titulo_jf}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
											</td>
										</tr>
									</table>

								</td>
							</tr>

							<tr >
								<td colspan="2">&nbsp;</td>
							</tr>
							
							
							<tr>
							<td align="left" colspan="2">
								<table width="100%" cellpadding="8" cellspacing="2" border="0">
								<tr>
									<td  width="5%" class="bg" align="right"><fmt:message key="formConsultaRqnp.estado" /></td>
									<td  width="95%" class="bg" align="left">
										<input id="txtEstado"   	/>	
									</td>
								</tr>
								</table>
							</td>
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
						
					
					
					<button id="btnSalir" name="btnSalir" type="button" dojoType="dijit.form.Button" onclick="btnSalir_click()">Salir</button>
					</td>
				</tr>
			</table>
			
			
			<div  dojoType="dijit.Dialog"  style= "width: 350px; height: 220px; display:none;" id="formDialogRechazo" title="Confirmacion" execute="" >
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
												-moz-box-sizing: border-box; width:300px; height:200px; border: 5px solid gray; 
												 margin: 7px; min-height: 2em;"
													 
													dojoType="dijit.form.Textarea" trim="true" 
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
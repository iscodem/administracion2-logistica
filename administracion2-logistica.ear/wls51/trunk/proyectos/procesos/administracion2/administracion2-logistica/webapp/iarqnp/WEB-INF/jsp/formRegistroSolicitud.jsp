<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<style type="text/css">
.numeric input {
    text-align: right;
}

input {
	text-transform: uppercase;
}

</style>

<title>Registro Solicitud de Bien </title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" src="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>

<script type="text/javascript">
	dojo.require("dijit.form.Form");
    dojo.require("dojo.data.ItemFileWriteStore");
    dojo.require("dojo.data.ItemFileReadStore");
    
    dojo.require("dijit.form.Button");
    dojo.require("dojox.grid.EnhancedGrid");
    dojo.require("dojox.grid.enhanced.plugins.Pagination");
    dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
    dojo.require("dojo.date.locale");
  
    dojo.require("dijit.form.ComboBox");
    dojo.require("dijit.form.FilteringSelect");
    dojo.require("dojo.store.Memory");
    dojo.require("dojo._base.connect");
    dojo.require("dojo._base.event");
    dojo.require("dojo._base.array");
    dojo.require("dijit.form.SimpleTextarea");
    dojo.require("dijit.form.ValidationTextBox");
    dojo.require("dijit.form.RadioButton");
    dojo.require("dojo.parser");
</script>


<script type="text/javascript">
var CONTEXTO_APP = "${pageContext.request.contextPath}";  
var msg_contacto="<fmt:message key='error.registrarRqnp.contacto'/>" ;
dojo.addOnLoad(function() {
	var store = new dojo.data.ItemFileWriteStore(
		{
			data: 
			{ 
				items: [
					<c:forEach items="${lsSol}"  var="solicitud" varStatus="status"   >
		            	{
		            		vcod_solicitud:"${solicitud.cod_solicitud}",
		            		vnombre_bien:"${solicitud.nombre_bien}", 
		            		vnombre_sol: "${solicitud.nombre_sol}",
		            		vnom_estado: "${solicitud.nom_estado}",
		            		vfecha_sol: "${solicitud.fecha_sol}",
		            		vfecha_aprueba: "${solicitud.fecha_aprueba}",
		            		////////////////////////////////////////////////
		            		vfecha_envio: "${solicitud.fecha_envio}",
		            		vfecha_rpta: "${solicitud.fecha_rpta}"
		            		////////////////////////////////////////////////
						}
		            	${not status.last ? ',' : ''}
					</c:forEach>
				]
			}
		}
	);
	
	
	 var stateStore = new dojo.data.ItemFileWriteStore({
        data: { 
        	identifier: 'id',
        	  label: 'name',

			items: [
			<c:forEach items="${lsUnidad}"  var="unidad" varStatus="status">
            {	id:"${unidad.cod_unidad}",
            	name:"${unidad.nom_corto}" 
            }
            ${not status.last ? ',' : ''}
			</c:forEach>
			]
		}
	}
	); 
	
	


	 var comboBox = 
		 new dijit.form.FilteringSelect({
         id: "txtUnidad_sol",
         name: "txtUnidad_sol",
         value: "",
         store: stateStore,
         placeHolder: 'Seleccione una Unidad',
         searchAttr: "name",
         disabled:false,
         requiered:true,
         selected: true
     }, dojo.byId("txtUnidad_sol"));
 

 var textBox = 
	 new dijit.form.ValidationTextBox({
     id: "txtPrecio_sol",
     name: "txtPrecio_sol",
     value: "",
     style:"width: 250px;text-align:right",
     trim:"true" ,
	 invalidMessage:"Por favor ingrese el motivo",													
	maxlength:"50"
 }, dojo.byId("txtPrecio_sol"));
 
	dojo.connect(textBox,'onKeyPress',
		    function(evt){  
		
		var key = evt.which || evt.keyCode;
	    // Capturar el codigo de la tecla presionada. 
	    // Si no es evt.which, usar evt.keyCode (para IE) 
	    if ((key == 13 || key == 46 || key == 8) || (key >= 48 && key <=57)){
	    	var val_valor=dojo.byId("txtPrecio_sol").value
	    // Si la tecla es backspace, enter, punto o digito 
	        if (key == 46 && val_valor.indexOf('.')!=-1 ){
	        // Si es el caracter "." comprobar que sea el unico 
	        	dojo.stopEvent(evt);
	        } 
	        
	    }else{
	    	dojo.stopEvent(evt);
	    } 
	}
	);
	
	dojo.connect(textBox,'onChange',
		    function(evt){  
		var val_valor=dojo.byId("txtPrecio_sol").value
		
		dojo.byId("txtPrecio_sol").value= addCommas(val_valor);
	}
	);
	
	
	
	var  val_txt_categoria= dijit.byId("txtCategoria");
	 
	dojo.connect(val_txt_categoria,'onChange',
		    function(evt){  
		
		btnRecuperarSubCategoria();
	}
	);
	

	
	var  val_txt_sub_categoria= dijit.byId("txtSubCategoria");
	 
	dojo.connect(val_txt_sub_categoria,'onChange',
		    function(evt){  
		
		recuperarDependencia();
	}
	);
	
	//Anexo de contacto
	var  val_anexo= dijit.byId("txtAnexo_contacto");
   		
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
	
	dojo.connect(val_cod_contacto,'onChange',
   			function(evt){  
   				btnRecuperarContacto();
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

	construirGrid(store);
	
});

function construirGrid(store){

	var layout = [
		{
		     name: 'Nro Solicitud ',
		  	 field: 'vcod_solicitud',
		  	 width: '35px',
		  	 //width: '7%',
		     headerStyles: "text-align: center",
		  	 styles:'text-align:left;'
		 },
         {
       	     name: 'Bien Solicitado',
       	  	 field: 'vnombre_bien',
       	     //width: '43%',
       	     width: '200px',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:left;'
       	 },
       	 {
       		 name: 'Estado',
       	  	 field: 'vnom_estado',
       	  	 //width: '10%',
       	  	 width: '70px',
       	     headerStyles: "text-align: center",
       	  	 styles:'text-align:center;'
       	  	
       	 },
       	 {
       		 name: 'Fecha Solic.',
       	     field: 'vfecha_sol',
       	     //width: '10%',
       	     width: '70px',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 }
       	 ///////////////////////////////////////////////////
       	 ,
       	 {
       		 name: 'Fecha Envio',
       	     field: 'vfecha_envio',
       	     //width: '10%',
       	     width: '70px',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 }
       	,
      	 {
      		 name: 'Fecha Aprob.',
      	     field: 'vfecha_aprueba',
      	     //width: '10%',
      	     width: '70px',
      	     headerStyles: "text-align: center",
      	     styles:'text-align:center;'
      	 }
       	,
     	 {
     		 name: 'Fecha Rpta.',
     	     field: 'vfecha_rpta',
     	     //width: '10%',
     	     width: '70px',
     	     headerStyles: "text-align: center",
     	     styles:'text-align:center;'
     	 }
       	 ///////////////////////////////////////////////////
   	];

	var grid = new dojox.grid.EnhancedGrid({
		store: store,
		rowSelector: '20px',
		structure: layout,
		escapeHTMLInData: false,
		rowsPerPage: 30,
		rowCount: 30,
		canSort: function(col){ return true; } ,
		style: { height:"600px", width:"98%", left:"1%" } ,
		errorMessage: "Ha ocurrido un error al procesar la consulta",
		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
		noDataMessage: "No se encontraron registros",
		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
		plugins: {
			pagination: {
				pageSizes: ["20", "50", "100", "200", "All"],
				description: true,
				sizeSwitch: false,
				pageStepper: true,
				gotoButton: false,
				maxPageStep: 5,
				position: "bottom"
			},
			nestedSorting: true
		}
	},dojo.byId("gridConsulta"));
	
	
	grid.startup();
	
	dojo.connect(grid,'onRowClick',
		    function(e){
		
				var valcod_solicitud;
			
				/// Verificando si se 
				if (dojo.byId("jcrud").value=="none"){
					
			        var items = grid.selection.getSelected();
			        if(items.length) {
			            console.log("items.longth: "+items.length);
			            dojo.forEach(items, function(selectedItem){
			                if(selectedItem !== null){
			                  
			                    dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
			                        /* Get the value of the current attribute:*/

			                       if (attribute=="vcod_solicitud"){
			                    	  
			                    	   valcod_solicitud =grid.store.getValues(selectedItem, attribute);
			                       }
			                    
			                       if (attribute=="vnom_estado"){
			                    	  var  val_estado_item =grid.store.getValues(selectedItem, attribute);
			                    	 
			                    	  if (val_estado_item=="Generado"){
			                    		  	divShow("divModificar");
			                    		  	divShow("divAdjuntar");
			                    		  	
			                    			divShow("divEnviar");
			                    			divHide("divGuardar");
			                    			divShow("divNuevo");
			                    			divHide("divRespuesta");
			                    	  }else if (val_estado_item=="Atendido" || val_estado_item=="Rechazado AUC" || val_estado_item=="Rechazado DPG"  ){
			                    			divHide("divModificar");
			                    			divHide("divAdjuntar");
			                    			divHide("divEnviar");
			                    			divHide("divGuardar");
			                    			divShow("divNuevo");
			                    			divShow("divRespuesta");
			                    	  }else{
			                    		  	divHide("divModificar");
			                    		  	divHide("divAdjuntar");
			                    			divHide("divEnviar");
			                    			divHide("divGuardar");
			                    			divShow("divNuevo");
			                    			divHide("divRespuesta");
			                    	  }
									}
									
			                    }); /* end forEach */
			                    recuperarSolictud(valcod_solicitud);
			                } 
			            }); /* end forEach */
			        } /* end if */
		    	}else{
		    		
		    		if (confirm("Confirme si desea Guardar el Registro del Bien")){
		    			dojo.stopEvent(e);
		    			btnGuardar_click();
		    		}else{
		    			desHabilita();
		    			dojo.byId("jcrud").value="none";
		    			 var items = grid.selection.getSelected();
					        if(items.length) {
					            
					            dojo.forEach(items, function(selectedItem){
					                if(selectedItem !== null){
					                  
					                    dojo.forEach(grid.store.getAttributes(selectedItem), function(attribute){
					                        /* Get the value of the current attribute:*/
					                        var value = grid.store.getValues(selectedItem, attribute);
					                       if (attribute=="vcod_solicitud"){
					                    	   valcod_solicitud =grid.store.getValues(selectedItem, attribute);
					                       }
					                       if (attribute=="vnom_estado"){
						                    	  var  val_estado_item =grid.store.getValues(selectedItem, attribute);
													
						                    	  if (val_estado_item=="Generado"){
						                    		  	divShow("divModificar");
						                    		  	divShow("divAdjuntar");
						                    			divShow("divEnviar");
						                    			divHide("divGuardar");
						                    			divShow("divNuevo");
						                    			divHide("divRespuesta");
						                    	  }else if (val_estado_item=="Atendido" || val_estado_item=="Rechazado"){
						                    		  divHide("divModificar");
						                    		  	divHide("divAdjuntar");
						                    			divHide("divEnviar");
						                    			divShow("divNuevo");
						                    			divHide("divGuardar");
						                    			divShow("divRespuesta");
						                    	  }else{
						                    		  	divHide("divModificar");
						                    		  	divHide("divAdjuntar");
						                    			divHide("divEnviar");
						                    			divShow("divNuevo");
						                    			divHide("divGuardar");
						                    			divHide("divRespuesta");
						                    	  }
						                    	   
						                       }
					                       
					                    }); /* end forEach */
					                    recuperarSolictud(valcod_solicitud);
					                } 
					            }); /* end forEach */
					        } /* end if */
		    		}
		    	}
			}
		    );

}


function recuperarSolictud(cod_sol){

	dojo.byId("action").value = "recuperarSolictudJson";
	dojo.byId("jcod_sol").value = cod_sol;
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						
						var data = eval("(" + unescape(response.data) + ")");
						lsSolicitudItems(data.map_sol);
						
						dojo.byId("jcrud").value='none';
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
			form: dojo.byId("frmRegistroSolicitud")
	};	
	dojo.xhrPost(enviar);
	
	//btnRecuperarSubCategoria();

}


function lsSolicitudItems(items){
	
	var grid= dijit.byId("gridConsulta");
	var items_bienes =grid.selection.getSelected();
	try {
		if(items == null) {
			items = "[]";
		}
		var items2 = eval("(" + items + ")");
		
		dojo.byId("txtCodSol").value 		= items2.cod_solicitud;
		
		dojo.byId("txtNomEstado").value 	= items2.nom_estado;
		dojo.byId("txtNombreSol").value 	= items2.nombre_sol;
		dojo.byId("txtFechaSol").value 		= items2.fecha_sol;
		
		/////////////////////////////////////////
		dojo.byId("txtFechaEnvio").value 		= items2.fecha_envio;
		dojo.byId("txtFechaAprueba").value 		= items2.fecha_aprueba;
		
		dojo.byId("txtReg_contacto").value		=items2.reg_contacto;
		dojo.byId("txtNom_contacto").value		=items2.nom_contacto;
		dojo.byId("txtAnexo_contacto").value	=items2.anexo_contacto;
		
		dijit.byId("txtObs_motivo").set('value',items2.obs_motivo);
		//dojo.byId("txtObs_motivo").value		=items2.obs_motivo;
		////////////////////////////////////////
		
		dojo.byId("txtNomUuo").value 			= items2.nombre_uuo;
		dojo.byId("txtNomBien").value 			= items2.nombre_bien;
		dojo.byId("txtPresentacion").value 		= items2.presentacion;
		dojo.byId("txtCaract_tecnica").value 	= items2.caract_tecnica;
		dojo.byId("txtCaract_fisica").value 	= items2.caract_fisica;
		dojo.byId("txtPrecio_sol").value 		= addCommas(items2.precio_sol);
		dojo.byId("txtTiempo_expira").value 	= items2.tiempo_expira;
		dojo.byId("txtOtras_caract").value 		= items2.otras_caract;
		dojo.byId("txtSuger_proveedor").value 	= items2.suger_proveedor;
		dojo.byId("txtEnvase").value 			= items2.envase;
		dojo.byId("txtCod_bien_rpta").value 	= items2.cod_bien_rpta;
		dojo.byId("txtFecha_rpta").value 		= items2.fecha_rpta;
		dojo.byId("txtUnidad_rpta").value 		= items2.unidad_rpta;
		dojo.byId("txtNom_atiende").value 		= items2.nom_empl_rpta;
		
		dojo.byId("txtPrecio_rpta").value 		= addCommas(items2.precio_rpta);
		dojo.byId("txtComentario_rpta").value 	= items2.comentario_rpta;
		dojo.byId("txtNom_bien_rpta").value 	= items2.nom_bien_rpta;
		dijit.byId("txtUnidad_sol").set('value',items2.unidad_sol);
		dijit.byId("txtCategoria").set('value',items2.cod_categoria);
		
		dijit.byId("txtSubCategoria").set('value',items2.cod_subcategoria);
		dojo.byId("txtsub_tem").value 			= items2.cod_subcategoria;
	
		dojo.byId("txtNum_reg").value 	= items2.numero_archivo;
		dojo.byId("txtAUC").value 	= items2.auc;
		
		if (items2.tipo_bien=='B'){
			 
		 	dijit.byId("txtTipBien1").set('checked', 'checked');
			
		}else if( items2.tipo_bien=='S'){
			dijit.byId("txtTipBien2").set('checked', 'checked');
			
		}else{
			dijit.byId("txtTipBien3").set('checked', 'checked');
		}
		
		//dojo.byId("txtSubCategoria").setValue(items2.cod_subcategoria);
		//dojo.byId("txtUnidad_sol").value 	= items2.unidad_sol;
		
		if ( dojo.byId("jcrud").value =="insert"){
			var val_nombre_bien=items2.nombre_bien;
			var val_nom_estado=items2.nom_estado;
			var val_fecha_sol=items2.fecha_sol;
			var val_cod_solicitud= items2.cod_solicitud;
			var myNewItem = {vcod_solicitud:val_cod_solicitud,vnombre_bien:val_nombre_bien, vnom_estado:val_nom_estado,
					vfecha_sol:val_fecha_sol};

			grid.store.newItem(myNewItem);
			//grid.store.save(); //AGREGADO
			//grid._refresh(); //AGREGADO
			
		}else if((dojo.byId("jcrud").value =="update" )|| (dojo.byId("jcrud").value =="enviar")) {
			if(items_bienes.length){
				  dojo.forEach(items_bienes, function(selectedItem_bien){
				        if(selectedItem_bien !== null){
				        	grid.store.setValue(selectedItem_bien, 'vnombre_bien',items2.nombre_bien);
				  			grid.store.setValue(selectedItem_bien, 'vnom_estado',items2.nom_estado);
				  			grid.store.setValue(selectedItem_bien, 'vfecha_sol',items2.fecha_sol);
				  			grid.store.setValue(selectedItem_bien, 'vfecha_envio',items2.fecha_envio);
				        }
				  });
			}
		} 
		
	} catch(e) {
		return [];
	}
}


function recuperarDependencia(){
	
	var cod_sub_cat=dijit.byId('txtSubCategoria').attr('value'); 
	
	if ( cod_sub_cat =="") {return}
	
	dojo.byId("action").value = "recuperarDependenciaJson";

	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]" && response.data != "{}") {
						
						var data = eval("(" + unescape(response.data) + ")");
						
						dojo.byId("txtAUC").value 		= data.auc;
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
			form: dojo.byId("frmRegistroSolicitud")
	};	
	dojo.xhrPost(enviar);
	
}


</script>

<script type="text/javascript">

function habilita(){
	dijit.byId("txtTipBien1").setAttribute("disabled", false);
	dijit.byId("txtTipBien2").setAttribute("disabled", false);
	dijit.byId("txtTipBien3").setAttribute("disabled", false);
	dojo.attr("txtNomBien", "readonly", false);
	dojo.attr("txtPresentacion", "readonly", false);
	dojo.attr("txtCaract_tecnica", "readonly", false);
	dojo.attr("txtReg_contacto", "readonly", false);
	dojo.attr("txtAnexo_contacto", "readonly", false);
	dojo.attr("txtCaract_fisica", "readonly", false);
	dojo.attr("txtPrecio_sol", "readonly", false);
	dojo.attr("txtTiempo_expira", "readonly", false);
	dojo.attr("txtOtras_caract", "readonly", false);
	dojo.attr("txtSuger_proveedor", "readonly", false);
	dojo.attr("txtEnvase", "readonly", false);
	dijit.byId("txtUnidad_sol").setAttribute("disabled", false);
	
	dijit.byId("txtObs_motivo").setAttribute("disabled", false);
	dijit.byId("txtCategoria").setAttribute("disabled", false);
	dijit.byId("txtSubCategoria").setAttribute("disabled", false);
	//dijit.byId("txtUnidad_sol").setAttribute("disabled", true);
}


function desHabilita(){
	dijit.byId("txtTipBien1").setAttribute("disabled", true);
	dijit.byId("txtTipBien2").setAttribute("disabled", true);
	dijit.byId("txtTipBien3").setAttribute("disabled", true);
	dojo.attr("txtNomBien", "readonly", true);
	dojo.attr("txtPresentacion", "readonly", true);
	dojo.attr("txtCaract_tecnica", "readonly", true);
	dojo.attr("txtAnexo_contacto", "readonly", true);
	dojo.attr("txtReg_contacto", "readonly", true);
	dojo.attr("txtCaract_fisica", "readonly", true);
	dojo.attr("txtPrecio_sol", "readonly", true);
	dojo.attr("txtTiempo_expira", "readonly", true);
	dojo.attr("txtOtras_caract", "readonly", true);
	dojo.attr("txtSuger_proveedor", "readonly", true);
	dojo.attr("txtEnvase", "readonly", true);
	
	dijit.byId("txtUnidad_sol").setAttribute("disabled", true);
	
	dijit.byId("txtObs_motivo").setAttribute("disabled", true);
	dijit.byId("txtCategoria").setAttribute("disabled", true);
	dijit.byId("txtSubCategoria").setAttribute("disabled", true);
	//dijit.byId("txtUnidad_sol").setAttribute("disabled", true);
	///////////////////////////////////////////////////
	
}


var btnNuevo_click = function(){
	habilita();
	divShow("divGuardar");
	divHide("divNuevo");
	divHide("divAdjuntar");
	divHide("divModificar"); 
	divShow("divMostrarRubros");
	divShow("divBuscarPersona");
	
	divHide("divEnviar");
	divHide("divRespuesta");
	
	dijit.byId("txtTipBien1").set('checked', false);
	dijit.byId("txtTipBien2").set('checked', false);
	dijit.byId("txtTipBien3").set('checked', false);
	
	dojo.byId("txtCodSol").value="";
	dojo.byId("txtNomEstado").value="Generado";
	dojo.byId("txtNomBien").value="";
	dojo.byId("txtPresentacion").value="";
	dojo.byId("txtCaract_tecnica").value="";
	dojo.byId("txtCaract_fisica").value="";
	
	dojo.byId("txtPrecio_sol").value="";
	dojo.byId("txtTiempo_expira").value="";
	dojo.byId("txtOtras_caract").value="";
	dojo.byId("txtSuger_proveedor").value="";
	dojo.byId("txtEnvase").value="";
	dijit.byId("txtUnidad_sol").setValue("");
	dojo.byId("txtCod_bien_rpta").value="";
	dojo.byId("txtFecha_rpta").value="";
	dojo.byId("txtNom_atiende").value="";
	dojo.byId("txtNom_bien_rpta").value="";
	dojo.byId("txtUnidad_rpta").value="";
	dojo.byId("txtPrecio_rpta").value="";
	dojo.byId("txtComentario_rpta").value="";
	dojo.byId("jcrud").value="insert";
	dojo.byId("txtNombreSol").value=dojo.byId("txt_nombreCompleto").value;
	dojo.byId("txtFechaSol").value=dojo.byId("txt_fecha").value;
	///////////////////////////////////
	dojo.byId("txtFechaAprueba").value="";
	dojo.byId("txtFechaEnvio").value="";
	
	dojo.byId("txtReg_contacto").value="";
	dojo.byId("txtAnexo_contacto").value="";
	dijit.byId("txtObs_motivo").reset();
	//dojo.byId("txtObs_motivo").value="";
	dojo.byId("txtNom_contacto").value = "";
	///////////////////////////////////
	dojo.byId("txtNomUuo").value=dojo.byId("txt_uuoo").value;
	dijit.byId("txtCategoria").reset();
	dijit.byId("txtSubCategoria").reset();
	//dijit.byId("txtCategoria").set('value','');
	//dijit.byId("txtSubCategoria").set('value','');
	dojo.byId("txtAUC").value 	= "";
	
	dijit.byId("txtObs_motivo").setAttribute("disabled", false);
	dijit.byId("txtCategoria").setAttribute("disabled", false);
	dijit.byId("txtSubCategoria").setAttribute("disabled", false);
	//dijit.byId("txtUnidad_sol").setAttribute("disabled", true);
	
};

var btnModificar_click = function(){
		divShow("divGuardar");
		divHide("divNuevo");
		divHide("divModificar");
		divHide("divAdjuntar");
		divShow("divMostrarRubros");
		divShow("divBuscarPersona");
		
		divHide("divEnviar");
		
		habilita();
		dojo.byId("jcrud").value="update";
};


//DPORRASC-CATALOGO//////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////
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
   									//alert("<fmt:message key='error.registrarRqnp.contacto'/>" );
   									dojo.byId("txtReg_contacto").value="";
   									dojo.byId("txtNom_contacto").value = ""; 
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
   					//alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
   					//alert("<fmt:message key='error.ajax'/>");
   					
   				},
   				form: dojo.byId("frmRegistroSolicitud")
   		};	
   		
   		dojo.xhrPost(enviar);
   		
   		}else{
   			dojo.byId("txtNom_contacto").value = ""; 
   		}

   	}   
   		
   		
	function btnBuscarPersona_click(){
   		
   		dojo.byId("action").value = "buscarPersonaJson";
   		dojo.byId("jPer_parametro").value = document.getElementById("txtPerParametro").value;
   		
   		var val_parametro =document.getElementById("txtPerParametro").value;
   		
   		if (val_parametro.length >2) {
   				
   			//dojo.byId("jtipoConsulta").value = "D";
   			//AQUI BLANQUENADO GRID
   			
   				var store = new dojo.data.ItemFileWriteStore(
   				{
   					
   					data: 
   					{ 
   						items: [
   							
   						]
   					}
   				}
   			);
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
   						alert("Error [" + error.message + "]");
   						//alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
   						//alert("<fmt:message key='error.ajax'/>");
   						
   					},
   					form: dojo.byId("frmRegistroSolicitud")
   			};	
   			
   			dojo.xhrPost(enviar);
   		}
   		
   	}
	
	
	function tipoPerConsultaSetea(valor){

   		dojo.byId("jPer_tipoConsulta").value = valor;
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
/////////////////////////////////////////////////////////////////////////////////

function valida_Guardar(){
	
	/* if(!dijit.byId('frmRegistroSolicitud').validate()){
		return false;
	} */

	 if(dijit.byId("txtTipBien1").checked) {
		 
	 }else if(dijit.byId("txtTipBien2").checked ){
		 
	 }else if (dijit.byId("txtTipBien3").checked){
		 
	 }else{
		 alert("Por favor seleccione un Tipo de ítem");
		 return false;
	 }
	
	 if(dojo.byId("txtReg_contacto").value==""  ){
			alert("<fmt:message key='formRegSolicitud.alerta.cod_contacto'/> " );	
			return false;
		}
		
		if(dojo.byId("txtAnexo_contacto").value==""  ){
			alert("<fmt:message key='formRegSolicitud.alerta.anexo_contacto'/> " );	
			return false;
		}
		
		//A
		if(dijit.byId('txtObs_motivo').get('value')=="" || dojo.byId("txtObs_motivo").value=="" ){
			alert("Por favor seleccione el motivo de su solicitud.");
			return false;
		}
		
	 
		if(dojo.byId("txtNomBien").value=="" && dojo.byId("txtNomBien").value=="" ){
			alert("Por favor ingrese la Descripción del ítem");
			return false;
		}
		
		//B
		if(dijit.byId('txtCategoria').get('value')=="" || dojo.byId("txtCategoria").value=="" ){
			alert("Por favor seleccione un Rubro General");
			return false;
		}
		//C
		if(dijit.byId('txtSubCategoria').get('value')=="" || dojo.byId("txtSubCategoria").value=="" ){
			alert("Por favor seleccione un Rubro Específico");
			return false;
		}
		
		if(dijit.byId('txtUnidad_sol').get('value')=="" || dojo.byId("txtUnidad_sol").value=="" ){
			alert("Por favor seleccione el Tipo de Unidad del ítem");
			return false;
		}
		
		if(dojo.byId("txtPrecio_sol").value=="" && dojo.byId("txtPrecio_sol").value=="" ){
			alert("Por favor ingrese el Precio del ítem");
			return false;
		}
	
	return true;
}

var btnGuardar_click = function(valor){
	if (  valida_Guardar()== false){
		return 
	}
	divHide("divGuardar");
	divShow("divNuevo");
	divShow("divModificar");
	
	divHide("divBuscarPersona");
	//divHide("divAdjuntar");
	
	divShow("divAdjuntar");
	divShow("divEnviar");
	
	dojo.byId("action").value = "registrarSolictudJson";
	dojo.byId("txtCodigoRqnp").value = valor;
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			headers: { "Content-Type": "application/json; charset=UTF-8"},
		//	headers: { "Content-Type": "application/json", "Accept" : "application/json"},
			load : function(response, ioArgs) {
				if(response.data.error==null){
					
					if (response.data != "" && response.data != "[]") {
						var data = eval("(" + response.data + ")");
						lsSolicitudItems(data.map_sol);
						dojo.byId("jcrud").value='none';
						desHabilita();
						/////////////////////////////
						actualizarLista(data.lsSol);
						//alert("El registro de la solicitud de ítem ha sido satisfactorio.");
						alert("El Registro de su solicitud de ítem ha sido generada preliminarmente.\n\nPuede usar el botón \"ADJUNTAR\", para adjuntar las características técnicas del ítem solicitado.\n\nSi desea que su solicitud sea enviada para su atención, debe darle click al botón \"ENVIAR.\"");

						/////////////////////////////
					}
				}else{
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
			},
			form: dojo.byId("frmRegistroSolicitud")
	};	
	//dojo.xhrPost(enviar);
	dojo.xhrGet(enviar);
};


var btnEnviar_click = function(valor){
	divHide("divGuardar");
	divShow("divNuevo");
	divHide("divModificar");
	
	divHide("divBuscarPersona");
	
	divHide("divAdjuntar");
	divHide("divEnviar");
	
	dojo.byId("action").value = "registrarSolictudJson";
	dojo.byId("txtCodigoRqnp").value = valor; //CODIGO DE SOLICITUD
//	dojo.byId("txtCodSol").value = valor;
	dojo.byId("jcrud").value = "enviar";
	
	//llamada AJAX
	var enviar = {
			handleAs : "json",
			load : function(response, ioArgs) {
				if(response.data.error==null){
					if (response.data != "" && response.data != "[]") {
						var data = eval("(" + unescape (response.data )+ ")");
						
						lsSolicitudItems(data.map_sol);
						dojo.byId("jcrud").value='none';

						//alert("La solicitud de ítem se ha enviado a la AUC competente.");
						alert("La solicitud de ítem ha sido enviada para su atención.");
					}
				}else{
					alert(response.data.mensaje);
				}
			},
			timeout : 25000,
			error : function(error, ioArgs) {
				alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
			},
			form: dojo.byId("frmRegistroSolicitud")
	};	
	dojo.xhrPost(enviar);
	//dojo.byId("frmRegistroSolicitud").submit();
};


var btnDatosRespuesta_click = function(){
	
	var formDlg = dijit.byId("formDialogRespuesta");
	formDlg.show();
	formDlg.onFocus();
	
}

function btnSalirDetalle_click(){
	var formDlg = dijit.byId("formDialogRespuesta");
	formDlg.hide();
}

var btnSalir_click = function(valor){
	//dojo.byId("action").value = "modificarRqnp";
	var val_url =dojo.byId("path_url").value;
	
	dojo.byId("txtCodigoRqnp").value = valor;

	document.frmRegistroSolicitud.action=val_url;	
	document.frmRegistroSolicitud.submit();
};


function	btnRecuperarSubCategoria(){
	
	var val_categoria =dijit.byId('txtCategoria').attr('value'); 
			
	if ( val_categoria =="") {return}
	
  	
	
	var formQuery ='&jcodCategoria='+val_categoria ;

	
	var URL = CONTEXTO_APP + '/solincorbien.htm?action=listarSubCategoriaJson'+formQuery
	   var txtsubcategoria = dijit.byId("txtSubCategoria");
	    var kw0 = {
	    		handleAs : "json",
	    		headers: { "Content-Type": "application/json; charset=UTF-8"},
	    		load : function(response, ioArgs) {
	    				var store = new dojo.data.ItemFileReadStore({data:response});
	    				//dijit.byId("txtVinculo").value='';
	    				dijit.byId("txtSubCategoria").reset();
	    				dijit.byId('txtSubCategoria').set('placeHolder','Seleccione una Sub categoria');
	    				//dijit.byId('txtVinculo').set('displayedValue','');
	    				
	    				//dijit.byId('txtVinculo').get('displayedValue')='';
	    				txtsubcategoria.store =store;		
	    				
	    				var sub_cat =dojo.byId("txtsub_tem").value;
						
						if (sub_cat != '-1'){
							dijit.byId("txtSubCategoria").set('value',sub_cat);
						}else{
							dijit.byId("txtSubCategoria").set('value','');
						}
						dojo.byId("txtsub_tem").value='-1';
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

function adjuntarArchivo(){
	document.frmRegistroSolicitud.action="rqnparchivo.htm?action=iniciarCargaArchivoSolicitud";	
	document.frmRegistroSolicitud.submit();
}


dojo.addOnLoad(function() {	

	dojo.attr("txtNomBien", "readonly", true);
	dojo.attr("txtPresentacion", "readonly", true);
	dojo.attr("txtCaract_tecnica", "readonly", true);
	dojo.attr("txtCaract_fisica", "readonly", true);
	dojo.attr("txtPrecio_sol", "readonly", true);
	dojo.attr("txtTiempo_expira", "readonly", true);
	dojo.attr("txtOtras_caract", "readonly", true);
	dojo.attr("txtSuger_proveedor", "readonly", true);
	dojo.attr("txtEnvase", "readonly", true);
	
	dojo.attr("txtReg_contacto", "readonly", true);
	dojo.attr("txtAnexo_contacto", "readonly", true);
	
	dijit.byId("txtObs_motivo").setAttribute("disabled", true);
	dijit.byId("txtCategoria").setAttribute("disabled", true);
	dijit.byId("txtSubCategoria").setAttribute("disabled", true);
	//dijit.byId("txtUnidad_sol").setAttribute("disabled", true);
});




</script>

</head>
<body  class="tundra">

	<center>
		<form id="frmRegistroSolicitud" name="frmRegistroSolicitud" action="solincorbien.htm" method="POST" dojoType="dijit.form.Form">
		<div dojoType="dojo.data.ItemFileReadStore" url="${pageContext.request.contextPath}/solincorbien.htm?action=listarCategoriaJson" jsId="idCategoriaStore"></div>
		<div dojoType="dojo.data.ItemFileReadStore" url="${pageContext.request.contextPath}/solincorbien.htm?action=listarSubCategoriaJson&jcodCategoria=${mapRqnp.cod_subcategoria}" jsId="idSubCategoriaStore"></div>
		<div dojoType="dojo.data.ItemFileReadStore" url="${pageContext.request.contextPath}/solincorbien.htm?action=listarMotivoJson" jsId="idMotivoStore"></div>
	
			<input type="hidden" id="action" name="action" />
			
			<input type="hidden" id="jestado" name="jestado" value="0"/>
			
			<input type="hidden" id="jcrud" name="jcrud" value="none"/>
			
			<input type="hidden" id="jcod_sol" name="jcod_sol" value="0"/>
			
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${txtCodigoRqnp}'/>"/>
			<input type="hidden" id="txtvisor" name="txtvisor" value="<c:out value='${visor}'/>" />
			<input type="hidden" id="txt_nombreCompleto" name="txt_nombreCompleto" value="<c:out value='${txt_nombreCompleto}'/>" />
			<input type="hidden" id="txt_fecha" name="txt_fecha" value="<c:out value='${txt_fecha}'/>" />
			<input type="hidden" id="txt_uuoo" name="txt_uuoo" value="<c:out value='${txt_uuoo}'/>" />
			<input type="hidden" id="path_url" name="path_url" value="<c:out value='${path_url}'/>" />
			<input type="hidden" id="path_url_back" name="path_url_back" value="solincorbien.htm?action=iniciarSolicitud" />
			<input type="hidden" id="txtNum_reg" name="txtNum_reg" value="<c:out value='${mapSol.numero_archivo}'/>" />
			<input type="hidden" id="txtsub_tem" name="txtsub_tem" value="-1" />
			
			<input type="hidden" id="txtCod_contacto" name="txtCod_contacto" value="" />
			<input type="hidden" id="jPer_parametro" name="jPer_parametro" value="" />
			<input type="hidden" id="jPer_tipoConsulta" name="jPer_tipoConsulta" value="" />
			
			
		<table width="95%" cellpadding="2" cellspacing="2" class="form-table">
			<tr>
				<td>
				<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
					<tr>
						<td class="T1" colspan="2">
							<div align="center"><fmt:message key="formRegSolicitud.tituloSolicitud" /></div>
						</td>
					</tr>
				</table>
				
				
				<!-- INICIO DE NUEVOS BOTONES -->
				
				<table width="98%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
					 	<table>
					 	 	<tr> 
					 	 		<td> 
						 	 		&nbsp;&nbsp;&nbsp;
						 	 		<div id="divNuevo" style="display: block;" >
									<button id="btnNuevo" name="btnNuevo" type="button" dojoType="dijit.form.Button" onclick="btnNuevo_click()">Nuevo</button>
									</div>
								</td>
								
								<td> 
									&nbsp;&nbsp;&nbsp;
									<div id="divModificar" style="display: none;" >
									<button id="btnModificar" name="btnModificar" type="button" dojoType="dijit.form.Button" onclick="btnModificar_click()">Modificar</button>
									</div>
								</td>
								
								<td> 
									&nbsp;&nbsp;&nbsp;
									<div id="divGuardar" style="display: none;" >
									<button id="btnGuardar" name="btnGuardar" type="button" dojoType="dijit.form.Button" onclick="btnGuardar_click()">Guardar</button>
									</div>
								</td>
								
								<td> 
									&nbsp;&nbsp;&nbsp;
									<div id="divAdjuntar" style="display: none;" >
									<button id="btnAdjuntar" name="btnAdjuntar" type="button" dojoType="dijit.form.Button" onclick="adjuntarArchivo()">Adjuntar</button>
									</div>
								</td>
								
								<td> 
						 	 		&nbsp;&nbsp;&nbsp;
						 	 		<div id="divRespuesta" style="display: none;" >
									<button id="btnRespuesta" name="btnRespuesta" type="button" dojoType="dijit.form.Button" onclick="btnDatosRespuesta_click()">Datos Respuesta</button>
									</div>
								</td>
								
								<td> 
									&nbsp;&nbsp;&nbsp;
									<div id="divEnviar" style="display: none;" >
										<button id="btnEnviar" name="btnEnviar" type="button" dojoType="dijit.form.Button" onclick="btnEnviar_click()">Enviar</button>
									</div>
								</td>
								
								<td>
									&nbsp;&nbsp;&nbsp;
									<div id="divSalir" style="display: block;" >
									
									<button id="btnSalir" name="btnSalir" type="button" dojoType="dijit.form.Button" onclick="btnSalir_click(${txtCodigoRqnp})">Atrás</button>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			
			
			<!-- FIN DE NUEVOS BOTONES -->
				

				<table cellpadding="0" cellspacing="0" width="100%">
					
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
										
					<tr>
						<td colspan="2" class="bgn">
							<div id="divMensaje" align="center"></div>
						</td>
					</tr>
					
					
					<tr>
						<td width="45%">
							<table width="100%" cellpadding="8" cellspacing="2" border="0">
								<tr>
									<td>
										<div id="gridConsulta"  > </div>
									</td>
								</tr>								
							</table>
						</td>
						<td width="55%" align="center"  valign="top" >
							<table width="95%" cellpadding="2" cellspacing="1">
							
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.numSol" /></td>
									<td class="bg" align="left">
										<input id="txtCodSol" name="txtCodSol"   
										value="<c:out value='${mapSol.cod_solicitud}'></c:out>"
											style="width: 250px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										/>
										
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.tipoBien" /></td>
									<td class="bg" align="left">
										<input id="txtTipBien1" name="txtTipBien"   
										value="B"
										type="radio" dojoType="dijit.form.RadioButton" 
									
										/>
										<label for="txtTipBien1">Bien</label> 
										
										<input id="txtTipBien2" name="txtTipBien"   
										value="S"
										type="radio" dojoType="dijit.form.RadioButton" 
										
										/>
										<label for="txtTipBien2">Servicio</label> 
										
										<input id="txtTipBien3" name="txtTipBien"   
										value="O"
										type="radio" dojoType="dijit.form.RadioButton" 
										
										/>
										<label for="txtTipBien3">Obra  (*)</label> 
									</td> 
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.estado" /></td>
									<td class="bg" align="left">
										<input id="txtNomEstado" name="txtNomEstado"   
										value="<c:out value='${mapSol.nom_estado}'></c:out>"
											style="width: 250px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										
										/>
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.solicitante" /></td>
									<td class="bg" align="left">
										<input id="txtNombreSol" name="txtNombreSol"   
										value="<c:out value='${mapSol.nombre_sol}'></c:out>"
											style="width: 250px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										/>
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.uuoo" /></td>
									<td class="bg" align="left">
										<input id="txtNomUuo" name="txtNomUuo"   
										value="<c:out value='${mapSol.nombre_uuo}'></c:out>"
											style="width: 380px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										/>
									</td>
								</tr>
								<!-- INICIO - AGREGADO FINALMENTE -->
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.cod_contacto" /></td>
									<td class="bg" align="left">
										<input id="txtReg_contacto" name="txtReg_contacto" maxlength="4" 
											uppercase = "true"  
											value=""
											dojoType="dijit.form.ValidationTextBox"  style="width: 100px; text-transform:uppercase; text-align:left"
											/>(*)
											
											&nbsp;
											<div id="divBuscarPersona" style="display: none;" >
											<button id="btnRecuperarPersona" name="btnRecuperarPersona" type="button" dojoType="dijit.form.Button"  onclick="btnRecuperarContacto()">Recuperar</button>
										
											&nbsp;&nbsp;
											<button id="btnBuscarPersona" name="btnBuscarPersona" type="button" dojoType="dijit.form.Button"  onclick="btnBuscarPersona_click_popup()"> Buscar </button>
											</div>
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.nom_contacto" /></td>
									<td class="bg" align="left">
										<input id="txtNom_contacto" name="txtNom_contacto"   
											value=""
											style="width: 380px;text-align:left"
											dojoType="dijit.form.ValidationTextBox"  
											maxlength="50"
											readonly="readonly"
												/>
									</td>
											
								</tr>
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.anexo_contacto" /></td>
									<td class="bg" align="left">
										<input id="txtAnexo_contacto" name="txtAnexo_contacto"   
											value=""
											style="width: 100px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											invalidMessage="Por favor ingrese anexo del contacto"
											maxlength="5"
											/>(*)
									</td>
											
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.obs_motivo" /></td>
									<td class="bg" align="left">
									<input id="txtObs_motivo" name="txtObs_motivo"   dojoType="dijit.form.FilteringSelect"
									autoComplete="true"
									searchAttr="name" required="true" forceValidOption="false"
									promptMessage="Seleccione motivo de solicitud"
									placeHolder="Seleccione motivo de solicitud"
									store="idMotivoStore"
									style="width:380px;"
									value ="<c:out value=''/>" />(*)	
									</td>
								</tr>
								<!-- FIN - AGREGADO FINALMENTE -->
								
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.fechaSolicitud" /></td>
									<td class="bg" align="left">
										<input id="txtFechaSol" name="txtFechaSol"   
										value="<c:out value='${mapSol.fecha_sol}'></c:out>"
											style="width: 250px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										/>
									</td>
								</tr>
								<tr>
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.fechaEnvio" /></td>
									<td class="bg" align="left">
										<input id="txtFechaEnvio" name="txtFechaEnvio"   
										value="<c:out value='${mapSol.fecha_envio}'></c:out>"
											style="width: 250px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										/>
									</td>
								</tr>
								<tr>
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.fechaAprueba" /></td>
									<td class="bg" align="left">
										<input id="txtFechaAprueba" name="txtFechaAprueba"   
										value="<c:out value='${mapSol.fecha_aprueba}'></c:out>"
											style="width: 250px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										/>
									</td>
								</tr>
								
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.tipoSol" /></td>
									<td class="bg" align="left">
										<input id="txtTipoSol" name="txtTipoSol"   
										value="<c:out value='${mapSol.tipo_sol}'></c:out>"
											style="width: 250px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											maxlength="20"
											readonly="readonly"
										/>
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.descripcion" /></td>
									<td class="bg" align="left">
									<input id="txtNomBien" name="txtNomBien"   
										value="<c:out value='${mapRqnp.nombre_bien}'></c:out>"
											style="width: 350px;text-align:left"
											dojoType="dijit.form.SimpleTextarea" trim="true" 
											invalidMessage="Por favor ingrese una descripción"													
											maxlength="150"
											uppercase = "true" 
										/>(*)
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.presentacion"/></td>
									<td class="bg" align="left">
									<input id="txtPresentacion" name="txtPresentacion"
										value="<c:out value='${mapRqnp.presentacion}'></c:out>"
											style="width: 350px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											invalidMessage="Por favor ingrese el motivo"													
											maxlength="100"
											uppercase = "true" 
										/>
									</td>
								</tr>
								
								<tr>
										<td class="bg" align="right"><fmt:message key="formRegSolicitud.categoria" /></td>
											<td class="bg" align="left">
												<input id="txtCategoria"   		 name="txtCategoria"   dojoType="dijit.form.FilteringSelect"  autoComplete="true" 
											 		searchAttr="name" required="true" forceValidOption="false"
													promptMessage="Seleccione una categoria"
													placeHolder="Seleccione una categoria"
											  		store="idCategoriaStore"
													style="width:350px;"
													value ="<c:out value='${mapRqnp.cod_categoria}'/>" />(*)	
													&nbsp;
											 <div id="divMostrarRubros" style="display: none;" >
												<!-- <button id="btnMostrarRubros" name="btnMostrarRubros" type="button" dojoType="dijit.form.Button"  onclick="window.open('http://intranet/intranet/inicio/recursoshumanos/normatividad/GAdministrativa/2015/prograAtencionBienesServicios/TablaUOEncargadasFormularReq.doc')" >Ver Rubros</button> -->
												<button id="btnMostrarRubros" name="btnMostrarRubros" type="button" dojoType="dijit.form.Button"  onclick="window.open('http://intranet/intranet/inicio/recursoshumanos/normatividad/GAdministrativa/2015/prograAtencionBienesServicios/'+'<%=request.getAttribute("nom_archivo") %>')" >Ver Rubros</button>
											 </div>	
											</td>
								</tr>
								<tr>
										<td class="bg" align="right"><fmt:message key="formRegSolicitud.subcategoria" /></td>
											<td class="bg" align="left">
												<input id="txtSubCategoria"   		 name="txtSubCategoria"   dojoType="dijit.form.FilteringSelect"  autoComplete="true" 
											 		searchAttr="name" required="true" forceValidOption="false"
													promptMessage="Seleccione una sub categoria"
													placeHolder="Seleccione una sub categoria"
											  		store="idSubCategoriaStore"
													style="width:350px;"
													value ="<c:out value='${mapRqnp.cod_subcategoria}'/>" />(*)		
														
											</td>
								</tr>
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.auc" /></td>
									<td class="bg" align="left">
									<input id="txtAUC" name="txtAUC"   
										value="<c:out value='${mapRqnp.auc}'></c:out>"
											style="width: 380px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											invalidMessage="Por favor ingrese el motivo"													
											maxlength="100"
											uppercase = "true" 
											readonly="readonly"
										/>
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.caracTecnica" /></td>
									<td class="bg" align="left">
									<input id="txtCaract_tecnica" name="txtCaract_tecnica"   
										value="<c:out value='${mapRqnp.caract_tecnica}'></c:out>"
											style="width: 350px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											invalidMessage="Por favor ingrese el motivo"													
											maxlength="100"
											uppercase = "true" 
										/>
									</td>
								</tr>
								
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.caracFisica" /></td>
									<td class="bg" align="left">
									<input id="txtCaract_fisica" name="txtCaract_fisica"   
										value="<c:out value='${mapRqnp.caract_fisica}'></c:out>"
											 style="width: 350px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											invalidMessage="Por favor ingrese el motivo"													
											maxlength="100"
											uppercase = "true" 
										/>
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.unidMed" /></td>
									<td class="bg" align="left">
									<input id="txtUnidad_sol"   	/>(*)		
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.precio" /></td>
									<td class="bg" align="left">
									
									<input id="txtPrecio_sol" name="txtPrecio_sol"   
										/> (*)
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.expira" /></td>
									<td class="bg" align="left">
									<input id="txtTiempo_expira" name="txtTiempo_expira"   
										value="<c:out value='${mapRqnp.tiempo_expira}'></c:out>"
											style="width: 350px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											invalidMessage="Por favor ingrese el motivo"													
											maxlength="50"
											uppercase = "true" 
											
										/>
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.otros" /></td>
									<td class="bg" align="left">
									<input id="txtOtras_caract" name="txtOtras_caract"   
										value="<c:out value='${mapRqnp.otras_caract}'></c:out>"
											 style="width: 350px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											invalidMessage="Por favor ingrese el motivo"													
											maxlength="100"
											uppercase = "true" 
										/>
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.proveedor"/></td>
									<td class="bg" align="left">
									<input id="txtSuger_proveedor" name="txtSuger_proveedor"   
										value="<c:out value='${mapRqnp.suger_proveedor}'></c:out>"
											 style="width: 350px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											invalidMessage="Por favor ingrese el motivo"													
											maxlength="100"
											uppercase = "true" 
										/>
									</td>
								</tr>
								
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.envase"/></td>
									<td class="bg" align="left">
									<input id="txtEnvase" name="txtEnvase"   
										value="<c:out value='${mapRqnp.envase}'></c:out>"
											 style="width: 350px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											invalidMessage="Por favor ingrese el motivo"													
											maxlength="100"
											uppercase = "true" 
										/>
									</td>
								</tr>
								<!-- COMENTADO A SOLICITUD DEL USUARIO
								<tr>
								<td class="bg" align="right"></td>
								<td> 
						 	 		&nbsp;&nbsp;&nbsp;
						 	 		<div id="divRespuesta" style="display: none;" >
									<button id="btnRespuesta" name="btnRespuesta" type="button" dojoType="dijit.form.Button" onclick="btnDatosRespuesta_click()">Datos Respuesta</button>
									</div>
								</td>
								</tr>
								 -->
							</table>
						</td>
						
					</tr>
					
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>

				</table>
				</td>
			</tr>
			<tr><td align="center">(*) Datos obligatorios</td></tr>
			</table>
			
			
			<!-- INICIO DE COMENTARIO
			<table width="98%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
					 	<table>
					 	 	<tr> 
					 	 		<td> 
						 	 		&nbsp;&nbsp;&nbsp;
						 	 		<div id="divNuevo" style="display: block;" >
									<button id="btnNuevo" name="btnNuevo" type="button" dojoType="dijit.form.Button" onclick="btnNuevo_click()">Nuevo</button>
									</div>
								</td>
								
								<td> 
									&nbsp;&nbsp;&nbsp;
									<div id="divModificar" style="display: none;" >
									<button id="btnModificar" name="btnModificar" type="button" dojoType="dijit.form.Button" onclick="btnModificar_click()">Modificar</button>
									</div>
								</td>
								
								<td> 
									&nbsp;&nbsp;&nbsp;
									<div id="divGuardar" style="display: none;" >
									<button id="btnGuardar" name="btnGuardar" type="button" dojoType="dijit.form.Button" onclick="btnGuardar_click()">Guardar</button>
									</div>
								</td>
								
								<td> 
									&nbsp;&nbsp;&nbsp;
									<div id="divAdjuntar" style="display: none;" >
									<button id="btnAdjuntar" name="btnAdjuntar" type="button" dojoType="dijit.form.Button" onclick="adjuntarArchivo()">Adjuntar</button>
									</div>
								</td>
								
								<td> 
									&nbsp;&nbsp;&nbsp;
									<div id="divEnviar" style="display: none;" >
										<button id="btnEnviar" name="btnEnviar" type="button" dojoType="dijit.form.Button" onclick="btnEnviar_click()">Enviar</button>
									</div>
								</td>
								
								<td>
									&nbsp;&nbsp;&nbsp;
									<div id="divSalir" style="display: block;" >
									
									<button id="btnSalir" name="btnSalir" type="button" dojoType="dijit.form.Button" onclick="btnSalir_click(${txtCodigoRqnp})">Atrás</button>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			
			FIN DE COMENTARIO
			 -->
			
			
			<div  dojoType="dijit.Dialog" style= "width: 650px; height: 450px; display:none;"  id="formDialogRespuesta" title="Respuesta de la Solicitud" execute="" >
			<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Respuesta de la Solicitud</div>
											</td>
										</tr>
										
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.codBien"/></td>
									<td class="bg" align="left">
								
									<input id="txtCod_bien_rpta" name="txtCod_bien_rpta"   
										value="<c:out value='${mapRqnp.cod_bien_rpta}'></c:out>"
											 style="width: 250px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											invalidMessage="Por favor ingrese el motivo"													
											maxlength="50"
											readonly="readonly"
										/>
										
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.fecAtencion"/></td>
									<td class="bg" align="left">
									<input id="txtFecha_rpta" name="txtFecha_rpta"   
										value="<c:out value='${mapRqnp.fecha_rpta}'></c:out>"
											 style="width: 250px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 
											invalidMessage="Por favor ingrese el motivo"													
											maxlength="50"
											readonly="readonly"
										/>
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.atiende"/></td>
									<td class="bg" align="left">
									<input id="txtNom_atiende" name="txtNom_atiende"   
										value="<c:out value='${mapRqnp.nom_empl_rpta}'></c:out>"
											 style="width: 350px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 												
											maxlength="50"
											readonly="readonly"
										/>
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.desBien"/></td>
									<td class="bg" align="left">
									<input id="txtNom_bien_rpta" name="txtNom_bien_rpta"   
										value="<c:out value='${mapRqnp.nom_bien_rpta}'></c:out>"
											 style="width: 350px;text-align:left;height:100px;"
											dojoType="dijit.form.SimpleTextarea" trim="true" 												
											maxlength="50"
											readonly="readonly"
										/>
									</td>
								</tr>
								
								
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.unidMed"/></td>
									<td class="bg" align="left">
									<input id="txtUnidad_rpta" name="txtUnidad_rpta"   
										value="<c:out value='${mapRqnp.unidad_rpta}'></c:out>"
											 style="width: 350px;text-align:left"
											dojoType="dijit.form.ValidationTextBox" trim="true" 												
											maxlength="50"
											readonly="readonly"
										/>
									</td>
								</tr>
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.precioRpta"/></td>
									<td class="bg" align="left">
									<input id="txtPrecio_rpta" name="txtPrecio_rpta"   
										value="<c:out value='${mapRqnp.precio_rpta}'></c:out>"
											 style="width: 250px;text-align:right"
											dojoType="dijit.form.ValidationTextBox" trim="true" 												
											maxlength="50"
											readonly="readonly"
											class="numeric"
										/>
									</td>
								</tr>
								
								
								<tr>											
									<td class="bg" align="right"><fmt:message key="formRegSolicitud.comentario"/></td>
									<td class="bg" align="left">
									<input id="txtComentario_rpta" name="txtComentario_rpta"   
										value="<c:out value='${mapRqnp.comentario_rpta}'></c:out>"
											 style="width: 350px;text-align:left;height:120px;"
											dojoType="dijit.form.SimpleTextarea"  												
											readonly="readonly"
											
											
										/>
									</td>
								</tr>
									</table>

								</td>
							</tr>
							
													
						</table>
					</td>
				</tr>
			</table>
			
			<table width="98%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
						<table>
							<tr>
								<td>
									&nbsp;&nbsp;&nbsp;
									<button id="btnCancelarDetalle" name="btnCancelarDetalle" type="button" dojoType="dijit.form.Button" onclick="btnSalirDetalle_click()">Salir</button>
								</td>
							</tr>	
						</table>
					</td>
				</tr>
			</table>
			
			</div>
			
			<!-- INICIO DE POPUP PARA BUSCAR PERSONAL -->
			<div  dojoType="dijit.Dialog" style= "width: 550px; height: 350px; display:none;"  id="formDialogPersona" title="Buscar Contacto" execute="">
			<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Buscar Contacto</div>
											</td>
										</tr>
									</table>

								</td>
							</tr>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1">

										<tr>											
											<td class="bg" align="right">Tipo de Búsqueda</td>
											<td class="bg" align="left">
												<input id="tipoPerConsulta1" name="tipoPerConsulta" 
												value="C"
													type="radio"
													dojoType="dijit.form.RadioButton" 
													onclick="tipoPerConsultaSetea(this.value);"
																									
												/>
												<label for="tipoPerConsulta1">Código</label> 
												
												<input id="tipoPerConsulta2" name="tipoPerConsulta" 
													type="radio"
													value="D"
													
													dojoType="dijit.form.RadioButton" 
												
												
													onclick ="tipoPerConsultaSetea(this.value);"												
												/>
												<label for="tipoConsulta2">Nombre</label> 
												&nbsp;&nbsp;&nbsp;
												<input id="txtPerParametro" name="txtPerParametro" 
											
												value="<c:out value='${mapCatalogo.parm}'></c:out>"
													style="width: 150px;"
													dojoType="dijit.form.TextBox" trim="true" 
													invalidMessage="Por favor ingrese un parametro"													
													maxlength="150"
												 uppercase = "true" 
													
												/>
												&nbsp;&nbsp;&nbsp;
												<button id="btnBuscarPersonaPopup" name="btnBuscarPersonaPopup" type="submit" dojoType="dijit.form.Button" onclick="btnBuscarPersona_click();return false;">Buscar</button>
											
											</td>
											
											
										</tr>
										
									</table>
								</td>
								
							</tr>
						
							
							<tr>
								<td>
									<table width="100%"  border="0">
										<tr>
											<td>
												<div id="gridConsultaPersona"  > </div>
											</td>
										</tr>								
									</table>
								</td>
							</tr>							
						</table>
					</td>
				</tr>
			</table>
			<table width="98%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
						<table>
							<tr>
								<td>
									<button id="btnAceptarPersona" name="btnAceptarPersona" type="button" dojoType="dijit.form.Button" onclick="btnAceptarPersona_click()">Aceptar</button>
								</td>
							
								<td>
									&nbsp;&nbsp;&nbsp;
									<button id="btnSalirPersona" name="btnSalirPersona" type="button" dojoType="dijit.form.Button" onclick="btnSalirPersona_click()">Cancelar</button>
								</td>
							</tr>	
						</table>
					</td>
				</tr>
			</table>
			</div>
			<!-- FIN DE POPUP PARA BUSCAR EL PERSONAL -->
		</form>
	
	</center>

</body>
<script type="text/javascript">




function divHide(layer_ref) { 

	var state = 'none'; 
	if (document.all) { //IS IE 4 or 5 (or 6 beta) 
		eval( "document.all." + layer_ref + ".style.display = state"); 
	} 
	if (document.layers) { //IS NETSCAPE 4 or below 
		document.layers[layer_ref].display = state; 
	} 
	if (document.getElementById &&!document.all) { 
		hza = document.getElementById(layer_ref); 
		hza.style.display = state; 
	} 
} 


function divShow(layer_ref) { 
	var state = 'block'; 
	
	if (document.all) { //IS IE 4 or 5 (or 6 beta) 
		eval( "document.all." + layer_ref + ".style.display = state"); 
	} 
	if (document.layers) { //IS NETSCAPE 4 or below 
		document.layers[layer_ref].display = state; 
	} 
	if (document.getElementById &&!document.all) { 
		hza = document.getElementById(layer_ref); 
		hza.style.display = state; 
	} 
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

function isDecimalKey(evt, txt) {

	
	var key = evt.which || evt.keyCode;
    // Capturar el codigo de la tecla presionada. 
    // Si no es evt.which, usar evt.keyCode (para IE) 

    if ((key == 13 || key == 46 || key == 8) || (key >= 48 && key <=57)){
    // Si la tecla es backspace, enter, punto o digito 
        if (key == 46 && txt.indexOf('.')!=-1){
        // Si es el caracter "." comprobar que sea el unico 
            return false; 
        } 
        return true; 
    } 
    return false; 
}
	

function actualizarLista(lista){	//listaMetas:formato JSON, se obtuvo del servlet como tipo lista.
	
	/* 	//FUNCION recuperarMeta() -- LO QUE DEVUELVE EL SERVLET
	JSON={"data":"{
	"listaMetas":"[{
		"precioUnid"	:"28.49",										*********************************
		"cantidadTotal"	:"0",											********(default=0)**************
		"montoDolar"	:"0.00",										********(default=0)**************
		"codigoRqnp"	:"201400005884",								*********************************
		"ubg"			:"074 2014 Administración", 					*********************************
		"anioEjec"		:"2014",										*********************************
		"montoSoles"	:"0.00",										*********************************
		"producto"		:"074.01 Administración", 						*********************************
		"secuenciaMeta"	:"1079",										*********************************
		"codigoBien"	:"B715000220021",								*********************************
		"uuoo"			:"8B2400 DIVISION DE ALMACEN DE BIENES DE USO CONSUMO Y MOBILIARIO", ************
		"meta"			:"074.01.01 Administración"						*********************************
	}]"}"} */
	
	
	
	
	var lsMetas =lsMetasItems(lista);

	var storeMetas = new dojo.data.ItemFileWriteStore({
		data: {
			//identifier: 'vsecuenciaMeta', PARA IDETINFICADOR DE LA GRILLA
			identifier: 'vcod_solicitud', 
			items: lsMetas
		}});
	
	var _grdLstPagAnt= dijit.byId("gridConsulta");
	if (_grdLstPagAnt==null ){
		construirGrid(storeMetas);
	}else{
		_grdLstPagAnt.setStore(storeMetas);
		_grdLstPagAnt.store.save();
		_grdLstPagAnt.update();
	}
}

function lsMetasItems(items){
	/* 	//FUNCION recuperarMeta() -- LO QUE DEVUELVE EL SERVLET
	JSON={"data":"{
	"listaMetas":"[{
		"precioUnid"	:"28.49",
		"cantidadTotal"	:"0",
		"montoDolar"	:"0.00",
		"codigoRqnp"	:"201400005884",
		"ubg"			:"074 2014 Administración",
		"anioEjec"		:"2014",
		"montoSoles"	:"0.00",
		"producto"		:"074.01 Administración",
		"secuenciaMeta"	:"1079",
		"codigoBien"	:"B715000220021",
		"uuoo"			:"8B2400 DIVISION DE ALMACEN DE BIENES DE USO CONSUMO Y MOBILIARIO",
		"meta"			:"074.01.01 Administración"
	}]"}"} */
	
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
			
			//////////////////////////////////////

			/////////////////////////////////////
			cadena += "{vcod_solicitud:'" 	+ item.cod_solicitud	+ "',";	
			cadena += "vnombre_bien:'" 		+ item.nombre_bien	+ "',";
			cadena += "vnombre_sol:'" 		+ item.nombre_sol	+ "',"; 
			cadena += "vnom_estado:'" 		+ item.nom_estado	+ "',";
			cadena += "vfecha_sol:'" 		+ item.fecha_sol + "',";
			cadena += "vfecha_envio:'" 		+ item.fecha_envio + "',";
			cadena += "vfecha_rpta:'" 		+ item.fecha_rpta + "',";
			cadena += "vfecha_aprueba:'" 	+ item.fecha_aprueba + "'}";

		});
		cadena += "]";
	
		var lst_pag_ant = eval("(" + cadena + ")");
		return lst_pag_ant;
		
	} catch(e) {
		return [];
	}
}

	
</script>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Consulta de items de Catalogo</title>

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


var tipoBienStore =  { 
		 identifier: 'id',
		    label: 'name',
		    items: [
	    	     {id:"B", name:"BIENES"},
	        	 {id:"S", name:"SERVICIOS"},
	    	     {id:"O", name:"OBRAS"}
		     ]
		 };
		 
var tipoConsultaStore =  { 
		 identifier: 'id',
		    label: 'name',
		    items: [
	    	     {id:"C", name:"CODIGO DE ITEM"},
	        	 {id:"D", name:"DESCRIPCION DE ITEM"},
	        	 {id:"A", name:"CODIGO DE AT"}
		     ]
		 };

var estadoStore =  { 
		 identifier: 'id',
		    label: 'name',
		    items: [
	    	     {id:"N", name:"PENDIENTE"},
	        	 {id:"I", name:"INACTIVO"},
	        	 {id:"A", name:"ACTIVO"}
		     ]
		 };
		 
dojo.addOnLoad(function() {
	
	var store = new dojo.data.ItemFileWriteStore(
		{
			data: 
			{ 
				items: []
			}
		}
	);
	
	construirGrid(store);
	
	//busqueda con enter

	var  val_parametro= dijit.byId("txtParametro");
		 
		dojo.connect(val_parametro,'onKeyPress',
		    function(evt){  
			
			var key = evt.which || evt.keyCode;
		    if (key == 13 ){
		    	btnBuscarCatalogo_click();
		    	evt.preventDefault();
		    }
		    
		    //key= String.fromCharCode(key).toUpperCase().charCodeAt(0);
		    //evt.keyCode = key;  
			} 
		);
		
		 dojo.connect(val_parametro,'onChange',
		    function(evt){  
			btnBuscarCatalogo_click();
			}
		);	
		 
		 
});

function construirGrid(store){

	var layout = [
       	 {
       		 name: 'Tipo Bien',
       	     field: 'tipo',
       	     width: '2%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 },
       	 {
       		 name: 'Cod. Item',
       	     field: 'codigo_item',
       	     width: '4%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 },
       	 {
       		 name: 'Desc. Item',
       	     field: 'descripcion_item',
       	     width: '9%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 },{
   	      	name: ' Alerta ',
   	      	field: 'alerta',
   	      	width: '2%',
   	      	formatter:getImagen,
   	      	headerStyles: "text-align: center",
   	      	styles:'text-align:center;font-size:9;' 
   	      	 }, 
       	 {
       		 name: 'Estado',
       	     field: 'estado',
       	     width: '3%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	     
       	 },
       	 {
       		 name: 'Unid. Med.',
       	     field: 'unidad_medida',
       	     width: '3%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 },
       	 {
       		 name: 'Prec. Unit.',
       	     field: 'precio_unitario',
       	     width: '2%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 },
       	 {
       		 name: 'Clasificador',
       	     field: 'clasificador_gasto',
       	     width: '4%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 },
       	 {
       		 name: 'Desc Clasificador',
       	     field: 'descripcion_clasificador',
       	     width: '6%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 },
       	 {
       		 name: '&Aacute;rea T&eacute;cnica',
       	     field: 'auc',
       	     width: '9%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 },
       	 {
       		 name: 'ambito',
       	     field: 'ambito',
       	     width: '4%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 },
       	 {
       		 name: 'Ruta Prog',
       	     field: 'ruta_programado',
       	     width: '3%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 },
       	 {
       		 name: 'Ruta No Prog',
       	     field: 'ruta_no_programado',
       	     width: '3%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 },
       	 {
       		 name: 'Rubro general',
       	     field: 'rubro_general',
       	     width: '5%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 },
       	 {
       		 name: 'Rubro Especifico',
       	     field: 'rubro_especifico',
       	     width: '8%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 },
       	 {
       		 name: 'Grupo Catálogo',
       	     field: 'grupo_catalogo',
       	     width: '4%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 },
       	 {
       		 name: 'Grupo Descripción',
       	     field: 'grupo_descripcion',
       	     width: '8%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 },
       	 {
       		 name: 'Clase Catalogo',
       	     field: 'clase_catalogo',
       	     width: '4%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:center;'
       	 },
       	 {
       		 name: 'Clase Descripción',
       	     field: 'clase_descripcion',
       	     width: '6%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 },
       	 {
       		 name: 'Familia Catálogo',
       	     field: 'familia_catalogo',
       	     width: '5%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 },
       	 {
       		 name: 'Familia Descripción',
       	     field: 'familia_descripcion',
       	     width: '6%',
       	     headerStyles: "text-align: center",
       	     styles:'text-align:left;'
       	 }
   	];

	var grid = new dojox.grid.EnhancedGrid({
		id:'gridConsulta',
		store: store,
		//rowSelector: '20px',
		onMouseover:false,
		structure: layout,
		escapeHTMLInData: false,
		rowsPerPage: 20,
		rowCount: 20,
		selectionMode:'none',
		canSort: function(col){ return true; } ,
		style: { height:"450px", width:"2430px" } ,
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
			indirectSelection:false,
			nestedSorting: true
		}
	}, dojo.byId("gridConsulta"));
	grid.startup();
}		 
		 
</script>

<script type="text/javascript">
//AQUI divShow - divHide


function btnBuscarCatalogo_click(){

	dojo.byId("action").value = "buscarConsultaCatalogoJson";
	dojo.byId("jParametro").value = document.getElementById("txtParametro").value;
	
	//AQUI BLANQUENADO GRID
	var val_parametro =document.getElementById("txtParametro").value;
	
	if (val_parametro.length >=0) {
			var store = new dojo.data.ItemFileWriteStore(
			{
				data: 
				{ 
					items: []
				}
			}
		);
	
	var grilla= dijit.byId("gridConsulta");
	//var grilla= dijit.byId("gridConsulta");
	grilla.showMessage('<div id="preloaderConsulta" align="center"><h2><span>Procesando Consulta...</span></h2></div>');
	
	
	if (grilla==null){
		construirGrid(store);
	}else{
		grilla.setStore(store);
	}
	
			
		//llamada AJAX
		var enviar = {
				handleAs : "json",
				load : function(response, ioArgs) {
							if (response.data != "" && response.data != "[]") {
								var data = response.lsCatalogo;
			    				var store = new dojo.data.ItemFileWriteStore(
			    					{
			    						data: { items: data}
			    					}
			    				);
			    				grilla.setStore(store)
								
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
	}
}

function tipoConsultaSetea(valor){

	dojo.byId("jtipoConsulta").value = valor;
}
	

function getImagen(valor,rowIndex){
if (valor =='1'){
			return  '<img name="adj01" src="/a/imagenes/circulorojo.gif"/>';
	}else{
			return  '-';
	}
}


   function colorear(){
	var grid = dijit.byId('gridConsulta');
	dojo.connect(grid, 'onStyleRow',function(row) {
		if (row.index !=5) {
			row.customStyles += "color:red;";
		}
   	})
   }

</script>

</head>
<body  class="tundra">

	<center>
		<form id="frmRegistroSolicitud" name="frmRegistroSolicitud" action="solincorbien.htm" method="POST" dojoType="dijit.form.Form">

	<div dojoType="dojo.data.ItemFileReadStore" data="tipoBienStore" jsId="idTipoBienStore"></div>
	<div dojoType="dojo.data.ItemFileReadStore" data="tipoConsultaStore" jsId="idTipoConsultaStore"></div>
	<div dojoType="dojo.data.ItemFileReadStore" data="estadoStore" jsId="idEstadoStore"></div>
	
	<input type="hidden" id="jParametro" name="jParametro" value="formRegistroDetalle"/>
	<input type="hidden" id="jtipoConsulta" name="jtipoConsulta" value="atencion"/>
	
		<input type="hidden" id="action" name="action" />
		<input type="hidden" id="jestado" name="jestado" value="0"/>
		<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${txtCodigoRqnp}'/>"/>
		<input type="hidden" id="txt_uuoo" name="txt_uuoo" value="<c:out value='${txt_uuoo}'/>" />

		<table width="99%" cellpadding="2" cellspacing="2" class="form-table">
			<tr>
				<td>
					<table cellpadding="0" cellspacing="0" width="100%" border=0>
						<tr>
							<td colspan="2">
								<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
									<tr>
										<td class="T1" colspan="2" align="left">
											<div align="center" > Consulta de ítems de Catálogo</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<!-- inicio busqueda de catálogo -->
						
						<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1">
										<tr>
											<td width="6%" class="bg" align="right">
												Tipo de ítem
											</td>
											<td width="5%" class="bg" align="left">
												<input id="txtTipo_bien" name="txtTipo_bien"   dojoType="dijit.form.FilteringSelect"  autoComplete="true" 
												searchAttr="name" required="true" forceValidOption="false"
												promptMessage="Seleccione tipo de bien"
												store="idTipoBienStore"
												style="width:100px;"
												value="B" />
											</td>
											
											<td width="6%" class="bg" align="right">
												Tipo de Consulta 
											</td>
											<td width="5%" class="bg" align="left">
												<input id="txtTipo_consulta" name="txtTipo_consulta"   dojoType="dijit.form.FilteringSelect"  autoComplete="true" 
												searchAttr="name" required="true" forceValidOption="false"
												promptMessage="Seleccione tipo de consulta"
												store="idTipoConsultaStore"
												style="width:150px;"
												value="D" />
											</td>

											<td width="78%" class="bg" align="left">
												&nbsp;&nbsp;&nbsp;&nbsp;Parámetros:
												
												<input id="txtParametro" name="txtParametro" 
											
												value="<c:out value='${mapCatalogo.parm}'></c:out>"
												style="width: 200px;"
												dojoType="dijit.form.TextBox" trim="true" 
												invalidMessage="Por favor ingrese un parametro"													
												maxlength="150"
												uppercase = "true" 
													
												/>
												&nbsp;&nbsp;&nbsp;
												<button id="btnBuscarCatalogo" name="btnBuscarCatalogo" type="submit" dojoType="dijit.form.Button" onclick="btnBuscarCatalogo_click();return false;">Buscar</button>
											</td>
											
										</tr>
										
									</table>
								</td>
								
							</tr>
						<!-- fin de busqueda de catalogo -->
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						
						<tr>
							<td colspan="2" >
								<table width="100%" cellpadding="8" cellspacing="2" border="0">
									<tr>
										<td>
											<div id="gridConsulta" style="-ms-touch-action: none;"></div>
										</td>
									</tr>	
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>	
			<!-- inicio circulo rojo 
				<td align="left" >
					<table>
						<tr>
							<td width="4%" align="left" >
								 <div id="divmsgImage" style="display: block; " >
									<img style='width: 50%;' name="eje01" src="/a/imagenes/circulorojo.gif"/>
								</div>	 
							</td>
							<td width="96%" align="left" >
								<div id="divmsgExceso" style="display: block;" >
									<P>En caso esta señal se muestre en la columna ALERTA de la lista de ítems, significa que el ítem se encuentra en estado INACTIVO, y no podrá ser utilizado en un requerimiento.
									</P>
								</div>
							</td>
						</tr>
					</table>
				</td>
			fin circulo rojo -->	
			</tr>
		</table>
		
		</form>
	</center>
</body>

</html>
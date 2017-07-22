<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Consulta de Catalogo</title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" src="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>

<script type="text/javascript">
	dojo.require("dijit.form.Form");
    dojo.require("dojo.data.ItemFileReadStore");
    dojo.require("dijit.form.Button");
    dojo.require("dojox.grid.EnhancedGrid");
    dojo.require("dojox.grid.enhanced.plugins.Pagination");
    dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
    dojo.require("dojo.date.locale");
    dojo.require("dijit.form.RadioButton");
    dojo.require("dojo.data.ItemFileWriteStore");
    dojo.require("dijit.form.CheckBox");
    dojo.require("dijit.form.ValidationTextBox");
    dojo.require("dijit.grid.cells.dijit");
    
   

</script>



<script type="text/javascript">
dojo.addOnLoad(function() {
	var store = new dojo.data.ItemFileWriteStore(
		{
			
			data: 
			{ 
				items: [
					<c:forEach items="${lsCatalogo}"  var="catalogoBienesBean" varStatus="status">
		            	{
		            		vcodigo_bien:"${catalogoBienesBean.codigo_bien}", 
		            		vdesc_bien: "${catalogoBienesBean.desc_bien}",
		            		vdesc_unidad:"${catalogoBienesBean.desc_unidad}", 
		            		vprecio_bien:"${catalogoBienesBean.precio_bien}",
		            		vcodigo_gasto: "${catalogoBienesBean.codigo_gasto}" ,  
		            		vdesc_gasto:"${catalogoBienesBean.desc_gasto}"
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
		 
		name: 'Código',
		width: '10%',
		headerStyles: "text-align: center",
		cellStyles:'text-align:center;',
		get: getTextCodigo,
		editable: true
	},{
		name: 'Bienes y Servicios',
		field: 'vdesc_bien',
		width: '35%',
		headerStyles: "text-align: center",
		styles:'text-align:left;',
	},{
		name: 'Unid. Med.',
		field: 'vdesc_unidad',
		width: '10%',
		headerStyles: "text-align: center",
		styles:'text-align:center;',
		formatter:getInput,
		editable: true
	},{
		name: 'Precio Unit.',
		field: 'vprecio_bien',
		width: '10%',
		headerStyles: "text-align: center",
		styles:'text-align:right;' 
	},{
		name: 'Clasificador',
		field: 'vcodigo_gasto',
		width: '15%',
		headerStyles: "text-align: center",
		styles:'text-align:center;' 
	},{
		name: 'Desc. Clasificador',
		field:'vdesc_gasto',
		width: '13%',
		headerStyles: "text-align: center",
		styles:'text-align:left;' ,
		editable: true
	}
	];
	 
	
	
	var grid = new dojox.grid.EnhancedGrid({
		id:'gridBusqueda',
		store:store,
		rowSelector: '20px',
		structure: layout,
		escapeHTMLInData: false,
		rowsPerPage: 10,
		rowCount: 10,
		canSort: function(col){ return true; } ,
		style: { height:"250px", width:"98%", left:"1%" } ,
		errorMessage: "Ha ocurrido un error al procesar la consulta",
		loadingMessage: '<div id="preloaderConsulta" align="center"><span>Procesando Consulta...</span></div>',
		noDataMessage: "No se encontraron registros",
		//sortFields: [{attribute: 'fechaFin', descending: true}, {attribute: 'ind_estado', descending: false}, {attribute: 'numAutorizacion', descending: false}],
		plugins: {
			pagination: {
				pageSizes: ["10", "25", "50", "100", "All"],
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

function getInput(valor, item) {
	var checkbox = '<input id=ipt"'+valor+'"   value="'+valor+'"  />'; 
	return checkbox; 

}


		
function getTextCodigo(valor, item) {
	
	var grid = dijit.byId('gridBusqueda');

	if(item!=null) { 
		var codigo = grid.store.getValue(item, "vcodigo_bien");
		alert(codigo);
		if(dijit.byId("txtCodigo"+codigo)){
			 return dijit.byId("txtCodigo"+codigo);
		}else{
			return  new dijit.form.ValidationTextBox({ id: "txtCodigo" +codigo, name: "txtCodigo", value:codigo  , widgetid:"txtCodigo"+codigo });
		}
	}
}

function getClasGasto(valor, item) {
	
	var grid = dijit.byId('gridBusqueda');

	if(item!=null) { 
		var codigo = grid.store.getValue(item, "vcodigo_bien");
		var valor = grid.store.getValue(item, "vdesc_gasto");
		
		return  new dijit.form.ValidationTextBox({ id: "gto", name: "gto", value:valor });
		
	}
}
</script>

<script type="text/javascript">
var btnBuscar_click = function(){
	if(!dijit.byId('frmConsultaConsulta').validate()){
		return;
	}
	if(dojo.byId("txtParametro").value==""  ){
		alert("Por favor ingrese un parametro de Busqueda");
		return;
	}
	var lenParm=Len(dojo.byId("txtParametro").value);
	
	if (lenParm < 3){
		alert("Por favor ingrese un parametro de almenos 3 Digitos");
		return;
	}
	dojo.byId("action").value = "buscarCatalogo";
	dojo.byId("frmConsultaConsulta").submit();
};

function Len(string_variable){
	
	return string_variable.length;
};

function btnAdd_click(){
	var grid = dijit.byId('gridBusqueda');
	
	  var myNewItem = {vcodigo_bien: "000001", vdesc_bien: "Eduardo", vdesc_unidad: "UNID", vprecio_bien:1000, vcodigo_gasto: "3.2.1",vdesc_gasto:"GAS"};
      /* Insert the new item into the store:*/
      grid.store.newItem(myNewItem);

}
function btnDel_click(){
	var grid = dijit.byId('gridBusqueda');
	/* Get all selected items from the Grid: */
	var items = grid.selection.getSelected();
	if(items.length){
	    /* Iterate through the list of selected items.
	       The current item is available in the variable
	       "selectedItem" within the following function: */
	    dojo.forEach(items, function(selectedItem){
	        if(selectedItem !== null){
	            /* Delete the item from the data store: */
	            grid.store.deleteItem(selectedItem);
	        } /* end if */
	    }); /* end forEach */
	} /* end if */

}




var btnSalir_click = function(){
	if(confirm("Confirme que desea salir del aplicativo sin registrar lo capturado")){
		window.parent.close();
	}
};
</script>

</head>
<body  class="tundra">

	<center>
		<form id="frmConsultaConsulta" name="frmConsultaConsulta" action="rqnp.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="action" name="action" />
			
			
			<table width="95%" cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%" border=0>
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Consulta de Catalogo</div>
											</td>
										</tr>
									</table>

								</td>
							</tr>
							<tr>
								<td align="center">
									<table width="90%" cellpadding="2" cellspacing="1">

										<tr>											
											<td class="bg" align="right">Tipo de Busqueda</td>
											<td class="bg" align="left">
												<input id="tipoConsulta1" name="tipoConsulta" 
												value="C"
													type="radio"
													dojoType="dijit.form.RadioButton" 
													invalidMessage="Por favor ingrese un número de Autorización válido"													
												/>
												<label for="tipoConsulta1">Código</label> 
												
												<input id="tipoConsulta2" name="tipoConsulta" 
													type="radio"
													value="D"
													checked
													dojoType="dijit.form.RadioButton" 
													invalidMessage="Por favor ingrese un número de Autorización válido"													
												/>
												<label for="tipoConsulta2">Descripción</label> 
											</td>
											
											
										</tr>
										<tr>											
											<td class="bg" align="right"> &nbsp;&nbsp;Parametros:</td>
											<td class="bg" align="left">
												<input id="txtParametro" name="txtParametro" 
												value="<c:out value='${mapCatalogo.parm}'></c:out>"
													style="width: 150px;"
													dojoType="dijit.form.ValidationTextBox" trim="true" 
													invalidMessage="Por favor ingrese un parametro"													
													maxlength="50"
													
												/>
												&nbsp;
												<button id="btnBuscar" name="btnBuscar" type="button" dojoType="dijit.form.Button" onclick="btnBuscar_click()">Buscar</button>
											</td>
										</tr>
									</table>
								</td>
								
							</tr>

							<tr>
								<td>&nbsp;</td>
							</tr>
							
							<tr>
								<td>&nbsp;</td>
							</tr>
							
							<tr>
								<td>
									<table width="100%" cellpadding="8" cellspacing="2" border="0">
										<tr>
											<td>
												<div id="gridConsulta" ></div>
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
						<button id="btnadd" name="btnadd" type="button" dojoType="dijit.form.Button" onclick="btnAdd_click()">Add</button>
						<button id="btndel" name="btndel" type="button" dojoType="dijit.form.Button" onclick="btnDel_click()">Del</button>
					
						&nbsp;&nbsp;&nbsp;
						<button id="btnAceptar" name="btnSalir" type="button" dojoType="dijit.form.Button" onclick="btnSalir_click()">Salir</button>
						
					</td>
				</tr>
			</table>
			
		</form>
	
	</center>

</body>
</html>
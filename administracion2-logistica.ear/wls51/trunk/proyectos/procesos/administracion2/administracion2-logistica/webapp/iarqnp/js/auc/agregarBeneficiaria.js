//=========================TABLA BENEFICIARIOS======================================
var tblBeneficiarias= $('#tblBeneficiarias').DataTable( {
     "language": {
		"url"		:	"../a/js/libs/bootstrap/3.3.2/plugins/datatables-1.10.7/plug-ins/1.10.7/i18n/Spanish.json"
				},
	oLanguage : {
		sLengthMenu	: "Mostrar _MENU_ registros",
		sZeroRecords: "No se encontraron resultados",
		sInfo		: "Mostrando del _START_ al _END_ de un total de _TOTAL_ registros",
		sInfoEmpty	: "Registros del 0 al 0 de un total de 0 registros",
		sEmptyTable	: '<i>No hay informacion de Personal</i>',
		sSearch		: "Buscar: ",
		sInfoFiltered: "(filtrado de un total de _MAX_ registros)",
		sLoadingRecords: "Cargando...",
		oPaginate : {
		"sFirst":    "Primero",
        "sLast":     "Ultimo",
        "sNext":     "Siguiente >",
        "sPrevious": "< Anterior"
		},
		oAria: {
			sSortAscending: ": Activar para ordenar la columna de manera ascendente",
			sSortDescending: ": Activar para ordenar la columna de manera descendente"
		}
	},
	columns : [
		{title: "UUOO", 				width: "10%", 	data :	"unidadOrganizacional"},
		{title: "UNIDAD ORGANIZACIONAL",width: "50%",	data :	"descDepeTde"},
		{title: "CANTIDAD", 			width: "5%",	data :	"cantidad"},
		{title: "SECUENCIA", 			width: "10%",	data :	"secuFuncSfu"},
		{title: "META SIAF", 			width: "25%",	data :	"metaSiaf"}
	],
	
	ordering			:	true,
	bScrollAutoCss		:	true,
	bStateSave			:	false,
	bAutoWidth			:	false,
	bScrollCollapse		:	false,
	searching			:	true,
	paging				:	true,
	pagingType			:   "full_numbers",
	iDisplayLength		:	6,
	responsive			:	true,
	bLengthChange		: 	false,
	fnDrawCallback		:	function(oSettings) {
		if (oSettings.fnRecordsTotal() == 0) {
			$('#tblBeneficiarias_paginate').addClass('hiddenDiv'); //el footer de paginacion siempre tiene como ID "miTablaID_paginate"
																//no se oculta automaticamente
																//cuando hay 0 registros, por eso se oculta anadiendo el class .hiddenDiv
        }
		else {
			$('#tblBeneficiarias_paginate').removeClass('hiddenDiv');
		}
    }
}); //Fin de inicializar tblBeneficiarias


$('#tblBeneficiarias tbody').on( 'click', 'tr', function () {
    $(this).toggleClass('selected');
} );

$("#btnOkMensajeAdvertenciaAgregarArchivo").click(function(){
	$("#divMensajeAdvertenciaAgregarArchivo").modal("hide");
});

function initElementsRegistrarBeneficiaria(){
	setInitElementsAgregarBeneficiaria();
}

function setInitElementsAgregarBeneficiaria(){
	addEventElement("btnAceptarCarga", "click", clickBtnAceptarCarga);
	addEventElement("btnGrabarBeneficiaria", "click", clickBtnGrabarBeneficiaria);
	addEventElement("btnBorrarCarga", "click", clickBtnBorrarCarga);
	addEventElement("btnCancelarCarga", "click", clickBtnCancelarCarga);
	addEventElement("btnOkMensajeAdvertenciaAgregarArchivo", "click",clickBtnOkMensajeAdvertenciaAgregarArchivo);
}

function limpiarInputs(){
	$('#tblBeneficiarias').dataTable().fnClearTable(); //limpia registros
	//$('#file').val("");
	$('#file').replaceWith('<input name="file" type="file" id="file" class="file" size="50%"/>');
}

function clickBtnOkMensajeAdvertenciaAgregarArchivo(){
	$("#divMensajeAdvertenciaAgregarArchivo").modal("hide");
}

function clickBtnBorrarCarga(){
	tblBeneficiarias.rows('.selected').remove().draw( false );
}

function clickBtnAceptarCarga(){
	var direccion = $('#file').val();
	if( $.trim(direccion)!="" ) {
		var  url = contextPathUrl+"/rqnparchivo.htm?action=cargarArchivoBeneficiario";
	    var options = {
            success: function(respuesta){
            	if(respuesta.okRegistrarTdBeneficiarios=="OK" || respuesta.okRegistrarTdBeneficiarios=="CONERROR" ){
            		$("#tblBeneficiarias").jqGrid('clearGridData');
            		$("#btnAceptarCarga").prop("disabled",true);
            		$("#btnGrabarBeneficiaria").prop("disabled",false);
            		$("#btnBorrarCarga").prop("disabled",false);
            	
            		if(respuesta.listTdBeneficiarioBean.length > 0){
						$('#tblBeneficiarias').dataTable().fnClearTable(); //limpia registros
						$('#tblBeneficiarias').dataTable().fnAddData(respuesta.listTdBeneficiarioBean); //carga registros en grilla
						$('#tblBeneficiarias').dataTable().fnDraw(); //redibuja objeto
					}else{
						$('#tblBeneficiarias').dataTable().fnClearTable();
					}
            		
            		if( respuesta.okRegistrarTdBeneficiarios=="CONERROR"){
            			$("#divMensajeAdvertenciaAgregarArchivo").modal("show");
    		           	$("#msgAdvertencia").html(respuesta.msg);
            		}
            		
	            }else{ 
		           	$("#divMensajeAdvertenciaAgregarArchivo").modal("show");
		           	$("#msgAdvertencia").html(respuesta.msg);
		        }
            }, 
            type: "POST",
            dataType: 'JSON',
            url: url,
            enctype: "multipart/form-data",
            beforeSerialize: function(form, options) {
            	options.data = {
                		file : $("#file").val()
                };
            }
	    };
	    $('#form').ajaxForm(options);
	    $('#form').submit();
	    
	}else if(direccion==""){
		$("#msgAdvertencia").html("Seleccione un archivo a guardar.");
		$("#divMensajeAdvertenciaAgregarArchivo").modal("show");
	}else{
		$("#msgAdvertencia").html("Debe escribir la descripcion.");
		$("#divMensajeAdvertenciaAgregarArchivo").modal("show");
	}
}

function clickBtnCancelarCarga(){
	//removeEventElementsAgregarArchivo();
	$("#formModalAgregarBeneficiaria").modal("hide");	
	limpiarInputs();
}

function removeEventElementsAgregarArchivo(){
	removeAllEventsElement("btnAceptarCarga");
	removeAllEventsElement("btnCancelarCarga");
}

//Agregar Benefeciarias al POI (JQUERY)
//DPORRASC
function clickBtnGrabarBeneficiaria(){
	var val_uuoo;
	var val_cantidad;
	var val_monto;
	var val_codigoBienM=dojo.byId("jcodigo_bien").value;
	var val_secuenciaMeta;
	var val_cantidadTotal=0;
	var val_precioUnid=dojo.byId("jprecio_unid").value;
	var val_montoTotalSoles=0;
	var val_codigoRqnp=dojo.byId("txtCodigoRqnp").value;
	var val_rqnp="000";
	var val_anioEjec=dojo.byId("janio_ejec").value;
	
	//DECLARACION DE ARRAYS
	var a_secuencia = [];
	var a_codigoBien = [];
	var a_cantidad = [];
	var a_monto = [];
	
	var datos=$('#tblBeneficiarias').DataTable().data();
	
	//$('#tblBeneficiarias tbody tr').each(function(indexFila){
	for(i=0; i<datos.length;i++){
		//Recorre las columnas de la tabla
		val_uuoo=datos[i].unidadOrganizacional;
		val_cantidad=datos[i].cantidad;
		val_monto=Number(val_cantidad)*Number(val_precioUnid);
		val_montoTotalSoles=Number(val_montoTotalSoles) + val_monto;
		val_cantidadTotal=Number(val_cantidadTotal)+Number(val_cantidad);
		val_secuenciaMeta=datos[i].secuFuncSfu;

		/// Verifica si el bien ya fue agregado
		var gridMetas = dijit.byId('gridMetas');
		var sw='0';
		var sw2='0';
		var val_name_auc_aux='';

		if(gridMetas.store !=null){
			if((gridMetas.store._arrayOfAllItems.length > 0 )){ 
				gridMetas.store.fetch({
					onItem: function(item, request){
						if (item.vsecuenciaMeta.toString() ==val_secuenciaMeta){
							sw='1';
						}
					}
				});
			}
		}
			
		// Verifica si el bien ya fue agregado
		if (sw=='0'){
			if (sw2=='0'){
				//PASAR PARAMETROS ARRAY PARA REGISTRO EN BD DE POPUP METAS
				a_secuencia.push(val_secuenciaMeta);
				a_codigoBien.push("000");
				a_cantidad.push(val_cantidad);
				a_monto.push(val_monto);
				
			}else{
				alert("El Bien/Servicio debe Pertenecer a la misma Área Técnica: "+ val_name_auc_aux+". Para Registrar este ítem formule un Requerimiento adicional.");
			}
		}else{
			alert("La Meta para la UUOO seleccionada ya fue Agregado");
		} 
	};
	
	registrarRqnpMetasExcel(a_secuencia,a_codigoBien,a_cantidad,a_monto); 
		
}

//Registrar Metas desde el excel
function registrarRqnpMetasExcel(a_secuencia,a_codigoBien,a_cantidad,a_monto){
	dojo.byId("action").value = "registrarRqnpMetasExcel";
	dojo.byId("a_txtCantidad").value = a_cantidad;
	dojo.byId("a_txtBien").value = a_codigoBien;
	dojo.byId("a_txtMonto").value = a_monto;
	dojo.byId("a_txtSecuencia").value = a_secuencia;
	
	$("#formModalAgregarBeneficiaria").modal("hide");
	
	//llamada AJAX
	var enviar = {
		handleAs : "json",
		load : function(response, ioArgs) {
			if(response.data.error==null){
				if (response.data != "" && response.data != "[]") {
					var data = eval("(" + response.data + ")");
					var valMontoTotal=response.mapRqnp.montoRqnp.toFixed(2);
					dojo.byId("txtMontoAcumulado").value=addCommas(valMontoTotal.toString()) ; 
					//dojo.byId("txtMontoAcumulado").value = (response.mapRqnp.montoRqnp);
					actualizarMetas(data.listaMetas); 
					onApplyCellEditMetasFinal();
				}
			}else{
				alert(response.data.mensaje);
			}
		},
		timeout : 25000,
		error : function(error, ioArgs) {
			//alert("<fmt:message key='error.ajax'/> [" + error.message + "]");
		},
		form: dojo.byId("frmRegistroMetas")
	};	
	dojo.xhrPost(enviar); 
}

var existePresentacionBD = function(codProducto,codInsumo,codUniCom,codUniFis,cntUnifisica,cntNetProd){
		
		var query = "&codProducto="+codProducto;
		query = query + "&codInsumo="+codInsumo;
		query = query + "&codUniCom="+codUniCom;
		query = query + "&codUniFis="+codUniFis;
		query = query + "&cntUnifisica="+cntUnifisica;
		query = query + "&cntNetProd="+cntNetProd;
		console.log("QUERY STRING AL SERVER PARA OBTENER DUPLICADO->"+query);
		var existe = false;
		var URL = CONTEXTO_APP + '/RectificacionAutomatica.htm?action=existePresentacion'+query;
		var kw = {
				handleAs: "json",      
				load: function(response, ioArgs) {
							if(response.existe=='1')
								existe = true;	      
						},      
				preventCache: false,      
				timeout: 35000,
				sync:true, 
				url:URL,
				error: function(error,ioArgs){
						alert("Ha ocurrido el siguiente error:" + error.message + " " +  ioArgs.xhr.status);      
				}};
		dojo.xhrGet(kw);
	    return existe;
	};
	
	 /**=================================================================================
	* Recarga la grilla con las actividades de importación y exportación. 
	*/	
	var recargarGrillaActividades = function(){
	    var URL = CONTEXTO_APP + '/RectificacionAutomatica.htm?action=listarActividades&ms='+(new Date()).getTime();
	    var store = new dojo.data.ItemFileReadStore({url:URL});
	    var grilla = dijit.byId("gridActividades");
	    grilla.setStore(store);
	};
	
	
	/* ------------------------------------------------------------------------------------------------------  
	 * Envía al servidor una consulta para obtener el detalle del datado del documento de transporte
	 * retorna la información en formato json y la muestra mediante una ventana emergente.
	 */
	function verRazonSocial(ruc){
		var URL = CONTEXTO_APP + '/ListManifiesto.htm?action=obtenerRSPorTipoyNumero&tipo=4&ruc='+ruc;	
		var kw = {
				url:URL,
				handleAs: "json",      
				load: function(response, ioArgs) {
						document.getElementById('divRucAlmacen').innerHTML = ruc;
						document.getElementById('divRazonSocialAlmacen').innerHTML = response.razon;
						dijit.byId('dlgAlmacen').show();
						return response;      
						},      
				preventCache: false,      
				timeout: 35000,
				sync:true,      
				error: function(error,ioArgs){        
						alert("Ha ocurrido el siguiente error:" + error.message + " " +  ioArgs.xhr.status);      
				}};
		dojo.xhrGet(kw);
	}
	
	
	///**********************************************************************************************************************
	
	
	function recargarResultados(){
	    var formQuery = dojo.formToQuery("frmSolicitudes");
	    var URL = CONTEXTO_APP + '/AdmisionSolicitud.htm?'+formQuery;	    
	    var kw0 = {
	    		handleAs : "json",
	    		load : function(response, ioArgs) {
	    			if(response.dependenciaCambiada==true){
	    				alert("La dependencia del RUC:"+response.numeroRuc+", a cambiado desde su registro de:"+response.dependenciaRegistro.codigo+" a:"+response.dependenciaRuc.codigo);
	    			}else{
	    				var store = new dojo.data.ItemFileReadStore({data:response});
	    			    var grilla = dijit.byId("gridResultados");
	    			    grilla.setStore(store);				
	    			}
	    		},
	    		preventCache : true,
	    		timeout : 10000,
	    		error : function(error, ioArgs) {
	    			alert(error.message);
	    		},
	    		url: URL,
	    		sync:true
	    };
	    dojo.xhrGet(kw0);
	}
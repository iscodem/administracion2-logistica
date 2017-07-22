<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<style type="text/css">
.dijitValidationTextBox input.dijitInputInner,
	.dijitNumberTextBox input.dijitInputInner,
	.dijitCurrencyTextBox input.dijitInputInner,
	.dijitSpinner input.dijitInputInner {
		padding-right: 1px;
		text-align: right;
	}
</style>

<title><fmt:message key="formRegistro.titulo" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

</head>

<body class="tundra">
	<div dojoType="dojo.data.ItemFileReadStore" data="canalStore" jsId="idCanalStore"></div>
	
	
	<center>
		<form id="frmRegistroFormato" name="frmRegistroFormato" action="bandejaauc.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
		</form>
		
		<form id="frmRegistro" name="frmRegistro" action="bandejaauc.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="action" name="action" value="registrarRqnpDetalle"/>
			<input type="hidden" id="jParametro" name="jParametro" value="formRegistroDetalle"/>
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
			<input type="hidden" id="txtCodigoBien" name="txtCodigoBien" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
			<input type="hidden" id="txtCodigobien" name="txtCodigobien" />
			<input type="hidden" id="txtNum_reg" name="txtNum_reg" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
			<input type="hidden" id="txtRechazo" name="txtRechazo" value=""/>
			
			<input type="hidden" id="txtvisor" name="txtvisor" value="<c:out value='${visor}'/>" />
			<input type="hidden" id="txtCod_contacto" name="txtCod_contacto" value="<c:out value='${mapRqnp.cod_contacto}'/>" />
				
			<input type="hidden" id="txtCod_proveedor" name="txtCod_proveedor" value="<c:out value='${mapRqnp.cod_cont}'/>" />
			
			
			<input type="hidden" id="txtCod_proveedor3" name="txtCod_proveedor3" value="<c:out value='${mapRqnp.cod_cont}'/>" />
			<input type="hidden" id="txtNum_ruc_prov" name="txtNum_ruc_prov" value="" />
			
			<input type="hidden" id="txtReg_contacto" name="txtReg_contacto" value="" />
			<input type="hidden" id="txtAnexo_contacto" name="txtAnexo_contacto" value="" />
			<input type="hidden" id="txtCod_necesidad" name="txtCod_necesidad" value="" />
			<input type="hidden" id="txtTipo_necesidad" name="txtTipo_necesidad" value="" />
			<input type="hidden" id="txtNom_tip_necesidad" name="txtNom_tip_necesidad" value="" />
			<input type="hidden" id="txtMotivo" name="txtMotivo" value="" />
			
			<input type="hidden" id="txtCod_finalidad" name="txtCod_finalidad" value="" />
			<input type="hidden" id="txtObs_dir_entrega" name="txtObs_dir_entrega" value="" />
			<input type="hidden" id="txtObs_lugar_entrega" name="txtObs_lugar_entrega" value="" />
			<input type="hidden" id="txtObs_plazo_entrega" name="txtObs_plazo_entrega" value="" />
			<input type="hidden" id="txtObs_justificacion" name="txtObs_justificacion" value="" />
			<input type="hidden" id="txtDesTec" name="txtDesTec" value="" />
			
			<input type="hidden" id="path_url" name="path_url"  value="bandejaauc.htm?action=recuperarDetalleAUC"/>
			<!--<input type="hidden" name="ind_estado" value="0"/>-->
			<table width="99%" cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center"><fmt:message key="formRegistroAucAtender.titulo" /></div>
											
											</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td>&nbsp;</td>
							</tr>
												
							<tr>
								<td class="bgn">
									<div id="divMensaje" align="center"></div>
								</td>
							</tr>
							<tr>
								<td align="center">
									<table width="90%" cellpadding="2" cellspacing="1">

										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.numRqnp" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.codigoReqNoProgramado}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistron.responsable" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.codigoResposanble}"></c:out> &nbsp;&nbsp;
												<c:out value="${mapRqnp.resposanble}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.uuoo" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.codigoUuoo}"></c:out> &nbsp;&nbsp;
												<c:out value="${mapRqnp.dependencia}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.fechaRqnp" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.fechaRqnp}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.Monto" /></td>
											<td class="bg" align="left">
											<input id="txtMontoAcumulado" name="txtMontoAcumulado"   
												value="<c:out value='${mapRqnp.montoRqnp}'></c:out>"
													style="width:80px;text-align:right"
													dojoType="dijit.form.ValidationTextBox" 
													maxlength="50"
													readonly="readonly"
													onkeydown="preventBackspace();"
												/>
											</td>
										</tr>
									</table>
								</td>
								
							</tr>
							<tr>
								<td>
									<table width="95%" cellpadding="2" cellspacing="2">
										<tr>
											<td align="center">
												 <table>
												 <tr>
												 <td>
												 	Seleccione un Canal de Atención
												
												<input id="txtCod_canal" name="txtCod_canal"   dojoType="dijit.form.FilteringSelect"  autoComplete="true" 
													searchAttr="name" required="true" forceValidOption="false"
													promptMessage="Seleccione un Canal de Atención"
													store="idCanalStore"
													style="width:200px;"
													value="01"
													missingMessage="Debe de seleccionar un Canal de Atención"
													invalidMessage="Debe de seleccionar un Canal de Atención"	/>
												</td>
												<td>
													&nbsp;&nbsp;&nbsp;
													<div id="divbtnAtender" >
														<button id="btnAtender" name="btnAtender" type="button" dojoType="dijit.form.Button" onclick="btnAtender_click()">Atender</button>
													</div>
												</td>
												<td>
													&nbsp;&nbsp;&nbsp;
													<div id="divbtnSiguiente" >
														<button id="btnRechazar" name="btnRechazar" type="button" dojoType="dijit.form.Button" onclick="btnRechazar_click">Rechazar</button>
													</div>
												</td>
												<td>
													&nbsp;&nbsp;&nbsp;
													<div id="divbtnAtras" >
														<button id="btnAtras" name="btnAtras" type="button" dojoType="dijit.form.Button" onclick="btnAtras_click()">Atras</button>
													</div>
												</td>
												</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							
							<tr>
								<td>
									<table width="100%" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td colspan="2" >
												<div id="gridDetalle"  > </div>
											</td>
										</tr>
										<!-- 
										<tr>
											<td colspan="2" align="left">
												<P>(*) La autorización del ítem corresponde al Área Usuaria Canalizadora Técnica indicada.</P>
											</td>
										</tr>
										 -->
										<tr>
											<td colspan="2" align="left">
												&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
										<tr>
											<td width="4%">
												<div id="divmsgImage" style="display: none;" >
												<img name="eje01" src="/a/imagenes/circulorojo.gif"/>
												</div> 
											</td>
											<td width="96%" align="left" >
												<div id="divmsgExceso" style="display: none;" >
												<P> El ítem NO cuenta con SALDO para su atención por el canal de Compras Directas. Por lo tanto será atendido por otro canal
												</P>
												</div>
											</td>
										</tr>
																		
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			
			
			
			<!-- EMERGENTE DE DATOS ENTREGA DETALLLEEEEE -->
			
			<div  dojoType="dijit.Dialog" style= "width: 650px; height: 350px; display:none;"  id="formDialogRequerimientoDetalle" title="Datos de Entrega del ITEM" execute="" >
			<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Datos de Entrega del &iacute;tem</div>
											</td>
										</tr>
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.numRqnp" /></td>
											<td class="bg" align="left">
												<input id="txtCod_Req_Det" name="txtCod_Req_Det" maxlength="11" 
												dojoType="dijit.form.TextBox"  style="width: 150px;text-align:left"
												readonly="readonly"
												value="<c:out value='${mapRqnp.codigoReqNoProgramado}'/>"
												tabindex="-1"
												onkeydown="preventBackspace();"
												/>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.item" /></td>
											<td class="bg" align="left">
												<input id="txtCod_bien_Det" name="txtCod_bien_Det" maxlength="11" 
												dojoType="dijit.form.TextBox"  style="width: 100px;text-align:left"
												readonly="readonly"
												tabindex="-1"
												/>
												<input id="txt_bien_Det" name="txt_bien_Det" maxlength="11" 
												dojoType="dijit.form.TextBox"  style="width: 350px;text-align:left"
												readonly="readonly"
												tabindex="-1"
												onkeydown="preventBackspace();"
												/>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.cod_proveedor" /></td>
											<td class="bg" align="left">
												<input id="txtNum_ruc_prov3" name="txtNum_ruc_prov3" maxlength="11" 
												dojoType="dijit.form.TextBox"  style="width: 100px;text-align:left"
												value=""
												/>
												&nbsp;
												<button id="btnRecuperarContratista3" name="btnRecuperarContratista3" type="button" dojoType="dijit.form.Button"  onclick="btnRecuperarContratistaDetalle()">Recuperar</button>
								
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.nom_proveedor" /></td>
											<td class="bg" align="left">
												<input id="txtNom_proveedor3" name="txtNom_proveedor3"   
													value=""
													 style="width: 450px;text-align:left"
													dojoType="dijit.form.TextBox" trim="true" 
														maxlength="100"
													readonly="readonly"
													onkeydown="preventBackspace();"
												/>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_justificacion" /></td>
											<td class="bg" align="left">
												<input id="txtObs_justificacion3" name="txtObs_justificacion3"   
													value=""
													style="width: 450px;text-align:left"
													dojoType="dijit.form.TextBox" trim="true" 
													maxlength="100"													
												/>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_plazo_entrega" /></td>
											<td class="bg" align="left">
												<input id="txtObs_plazo_entrega3" name="txtObs_plazo_entrega3"   
													value=""
													style="width: 450px;text-align:left"
													dojoType="dijit.form.TextBox" trim="true" 
													maxlength="100"
													
												/>
											</td>
										</tr>
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_lugar_entrega" /></td>
											<td class="bg" align="left">
												<input id="txtObs_lugar_entrega3" name="txtObs_lugar_entrega3"   
													value=""
													style="width: 450px;text-align:left"
													dojoType="dijit.form.TextBox" trim="true" 
													maxlength="100"
												/>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right"><fmt:message key="formRegistro.obs_dir_entrega" /></td>
											<td class="bg" align="left">
												<input id="txtObs_dir_entrega3" name="txtObs_dir_entrega3"   
													value=""
													style="width: 450px;text-align:left"
													dojoType="dijit.form.TextBox" trim="true" 
													maxlength="100"
												/>
											</td>
										</tr>
										<tr >											
											<td class="bg" align="left"><fmt:message key="formRegistro.des_tec" /></td>
											<td class="bg" align="left">
												
												<input id="txtDesTec3" name="txtDesTec3" 
													style="width: 450px;text-transform:uppercase;height:50px;min-height:1.5em;max-height:10em;overflow-y: auto;"
													dojoType="dijit.form.SimpleTextarea" 
													uppercase = "true" 
													invalidMessage="Por favor ingrese la Descripción Técnica"													
													maxlength="240"
													value =""
												/>(*)
												
											</td>
										</tr>
									</table>

								</td>
							</tr>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1">
									</table>
								</td>
							</tr>
							<tr>
								<td align="center">(*)Datos Obligatorios</td>
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
									<button id="btnGuardarDetalle" name="btnGuardarDetalle" type="button" dojoType="dijit.form.Button" onclick="btnGuardarEntregaDetalle_click()">Guardar</button>
									
									&nbsp;&nbsp;&nbsp;
									<button id="btnCancelarDetalle" name="btnCancelarDetalle" type="button" dojoType="dijit.form.Button" onclick="btnSalirEntregaDetalle_click()">Cancelar</button>
									
								</td>
							</tr>	
						</table>
					</td>
				</tr>
			</table>
			
			</div>
			
			<!--  EMERGENTE DE RECHAZO -->
			
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
												<div align="center">Rechazar &iacute;tems seleccionados</div>
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
			
			<!-- EMERGENTE DE DATOS Envio -->
			<div  dojoType="dijit.Dialog" style= "width: 350px; height: 150px; display:none;"  id="formDialogEnvio" title="Procesando" execute="" >
				<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
								<tr>
									<td align="center">
										<table width="100%" cellpadding="2" cellspacing="1" >
											<tr>											
												<td class="bg" align="center"  colspan="2" > <h5>Se estan procesando los datos</h5></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>		
					</tr>
				</table>
			</div>
			
			<!-- EMERGENTE DE DATOS Rechazo -->
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
	
	
	<script type="text/javascript" src="/a/js/js.js"></script>
	<script type="text/javascript" src="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>  
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/iarqnp.js"> </script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/formAucRqnpDetalle_structure.js"> </script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/formAucRqnpDetalle.js"> </script>

<script type="text/javascript">
var val_listaBienes= [
		<c:forEach items="${listaBienes}"  var="bienesBean" varStatus="status">
       	{
       		vcodigoRqnp:"${bienesBean.codigoRqnp}", 
       		vxcodigoBien: "${bienesBean.codigoBien}",
       		vcodigoBien: "${bienesBean.codigoBien}",
       		vtipo_bien: "${bienesBean.tipo_bien}",
       		vcodigoUnidad:"${bienesBean.codigoUnidad}", 
       		vcantBien:"${bienesBean.cantBien}",
       		vxcantBien:"${bienesBean.cantBien}",
       		vprecioUnid: "${bienesBean.precioUnid}" , 
       		vxprecioUnid: "${bienesBean.precioUnid}" , 
       		vsaldo: "${bienesBean.saldo}" ,
       		vprecioTotal: "${bienesBean.precioTotal}" ,
       		vexceso: "${bienesBean.exceso}" ,
       		vdesBien:"${bienesBean.desBien}",
       		vdesUnid:"${bienesBean.desUnid}",
       		vitem_origen:"${bienesBean.item_origen}",
       		vind_especializado:"${bienesBean.ind_especializado}",
       		vnro_adjuntos:"${bienesBean.nro_adjuntos}",
       		vestadoDesconcentrado:"${bienesBean.estadoDesconcentrado}",
       		vauct_name:"${bienesBean.auct_name}",
       		vimage:" ",
       		vlink:"<a href='#' onclick="+'"'+"adjuntarArchivo('${bienesBean.codigoRqnp}','${bienesBean.codigoBien}','${bienesBean.numeroArchivo}' );"+'"'+"> Adjuntar</a> ",
       		vlinkDetalle:"<a href='#' onclick="+'"'+"btnModificar_detalle_click('${bienesBean.codigoRqnp}','${bienesBean.codigoBien}' );"+'"'+"> Detalle</a> ",
       		vlinkModificar:"<a href='#' onclick="+'"'+"btnModificar_item_click('${bienesBean.codigoRqnp}','${bienesBean.codigoBien}' );"+'"'+"> Modificar</a> "
			}   
       	${not status.last ? ',' : ''}
		</c:forEach>
	];

var val_lsFinalidad =[	<c:forEach items="${lsFinalidad}"  var="finalidad" varStatus="status">
   	            {	id:"${finalidad.cod}",
   	            	name:"${finalidad.name}" 
   	            }
   	            ${not status.last ? ',' : ''}
   				</c:forEach>
   				];
   				
  var val_lsNecesidad= [
   				<c:forEach items="${lsNecesidad}"  var="necesidad" varStatus="status">
   	            {	id:"${necesidad.cod}",
   	            	name:"${necesidad.name}" 
   	            }
   	            ${not status.last ? ',' : ''}
   				</c:forEach>
   				];
  
  var val_lsTipoNecesidad=[
   				<c:forEach items="${lsTipoNecesidad}"  var="tipo" varStatus="status">
   	            {	id:"${tipo.cod}",
   	            	name:"${tipo.name}" 
   	            }
   	            ${not status.last ? ',' : ''}
   				</c:forEach>
   				];
  

  var msg_obs_justificacion="<fmt:message key='formRegistro.alerta.obs_justificacion'/> ";
 var msg_cod_proveedor = "<fmt:message key='formRegistro.alerta.cod_proveedor'/> ";

 var canalStore =  { 
	 identifier: 'id',
	    label: 'name',
	    items: [
    	     {name:"Contrataciones", id:"01"}
	     ]
	 };

</script>
</body>

</html>


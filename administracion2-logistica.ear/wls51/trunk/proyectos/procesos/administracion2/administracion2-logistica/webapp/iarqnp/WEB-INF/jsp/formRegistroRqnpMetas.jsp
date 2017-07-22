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

<script type="text/javascript" src="/a/js/js.js"></script>
<script src="/a/js/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
<script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/iarqnp.js"> </script>
<script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/formRegistroRqnpMetas_structure.js"> </script>
<script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/formRegistroRqnpMetas.js"> </script>

<script type="text/javascript" LANGUAGE="JavaScript">

var vlistaBienes=[<c:forEach items="${listaBienes}"  var="bienesBean" varStatus="status">
    	{	vcodigoRqnp		:"${bienesBean.codigoRqnp}", 
    		vcodigoBien		:"${bienesBean.codigoBien}",
    		vcodigoUnidad	:"${bienesBean.codigoUnidad}", 
    		vcantBien		:"${bienesBean.cantBien}",
    		vprecioUnid		:"${bienesBean.precioUnid}" , 
    		vprecioTotal	: "${bienesBean.precioTotal}" ,
    		vsaldo			: "${bienesBean.saldo}" ,
    		vexceso			: "${bienesBean.exceso}" ,
    		vimage			:" ",
    		vdesBien		:"${bienesBean.desBien}",
    		vdesUnid		:"${bienesBean.desUnid}"
		}  	${not status.last ? ',' : ''}
		</c:forEach>];


var vlistaMetas=[<c:forEach items="${listaMetas}"  var="metasBean" varStatus="status">
    	{	vcodigoRqnpM	:"${metasBean.codigoRqnp}", 
    		vcodigoBienM	: "${metasBean.codigoBien}",
    		vanioEjec		:"${metasBean.anioEjec}", 
    		vsecuenciaMeta	:"${metasBean.secuenciaMeta}",
    		vcantidadTotal	: "${metasBean.cantidadTotal}" ,
    		vxcantidadTotal	: "${metasBean.cantidadTotal}" ,
    		vmontoSoles		:"${metasBean.montoSoles}",
    		vxmontoSoles	:"${metasBean.montoSoles}",
    		vprecioUnid		:"${metasBean.precioUnid}",
    		vubg			:"${metasBean.ubg}",
    		vproducto		:"${metasBean.producto}",
    		vmeta			:"${metasBean.meta}",
    		vmetaSiaf			:"${metasBean.metaSiaf}"
		}  ${not status.last ? ',' : ''}
	</c:forEach>
	];
</script>

</head>

<body class="tundra">
	<center>

		<form id="frmRegistroMetas" name="frmRegistroMetas" action="rqnpmetas.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="action" name="action" value="registrarRqnpDetalle"/>
		
			
			<input type="hidden" id="jupdate" name="jupdate" value="0"/>
			<input type="hidden" id="jload" name="jload" value="0"/>
			
			<input type="hidden" id="jcodigo_dep" name="jcodigo_dep" value="<c:out value='${mapRqnp.codigoDependencia}'/>" />
			<input type="hidden" id="janio_ejec" name="janio_ejec" value="<c:out value='${mapRqnp.anioProceso}'/>" />
			<input type="hidden" id="jcodigo_bien" name="jcodigo_bien" value="<c:out value='${mapRqnp.jcodigo_bien}'/>"   />
			<input type="hidden" id="jprecio_unid" name="jprecio_unid" value="<c:out value='${mapRqnp.jprecio_unid}'/>"  />
			
			<input type="hidden" id="jcodigo_bien_old" 	name="jcodigo_bien_old" value="<c:out value='${mapRqnp.jcodigo_bien}'/>"   />
			<input type="hidden" id="jprecio_unid_old" 	name="jprecio_unid_old" value="<c:out value='${mapRqnp.jprecio_unid}'/>"  />
			<input type="hidden" id="txtCodigoBien" name="txtCodigoBien" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
			<input type="hidden" id="txtNum_reg" name="txtNum_reg" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
			
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${mapRqnp.codigoRqnp}'/>" />
			<input type="hidden" id="txtvisor" name="txtvisor" value="<c:out value='${visor}'/>" />
			<input type="hidden" id="txtLimite_cd" name="txtLimite_cd" value="<c:out value='${mapRqnp.limite_cd}'/>" />
			<input type="hidden" id="txtTipo_auc" name="txtTipo_auc" value="<c:out value='${mapRqnp.tipo_auc}'/>" />
			<input type="hidden" id="txtInd_auc_item" name="txtInd_auc_item" value="<c:out value='${mapRqnp.ind_auc_item}'/>" />
			<input type="hidden" id="txtParametro_uit" name="txtParametro_uit" value="<c:out value='${mapRqnp.parametro_uit}'/>" />
			
			<!--<input type="hidden" name="ind_estado" value="0"/>-->
			<table width="95%"  height= "240px"   cellpadding="0" cellspacing="0" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center"><fmt:message key="formRegistro.titulo" /></div>
											
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
											<td class="bg" align="right"><fmt:message key="formRegistro.Monto" /></td>
											<td class="bg" align="left">
											<input id="txtMontoAcumulado" name="txtMontoAcumulado"   
												value="<c:out value='${mapRqnp.montoRqnp}'></c:out>"
													 style="width:80px;text-align:right"
													dojoType="dijit.form.ValidationTextBox" 
																										
													maxlength="50"
													readonly="readonly"
												/>
												
											</td>
										</tr>
										
										<%-- <tr>											
											<td class="bg" align="right">Limite Compra Directa:</td>
											<td class="bg" align="left">
											
												<c:out value="${mapRqnp.limite_cd}"></c:out> &nbsp;&nbsp;
												
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right">Es AUC? (s/n):</td>
											<td class="bg" align="left">
											
												<c:out value="${mapRqnp.tipo_auc}"></c:out> &nbsp;&nbsp; 
												
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="right">Indicador de AUC item = AU Solicitante:</td>
											<td class="bg" align="left">
											
												<c:out value="${mapRqnp.ind_auc_item}"></c:out> &nbsp;&nbsp; 
												
											</td>
										</tr> --%>
										
									</table>
								</td>
								
							</tr>
							
							<tr>
								<td>
									<table width="100%" cellpadding="8" cellspacing="2" border="0">
										<tr>
											<td>
												<div id="gridDetalle"  > </div>
											</td>
										</tr>								
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			
			<table width="95%"  height="95%"  cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%" border=0>
							<tr>
									<td colspan="2">
									<table width="100%" cellpadding="0" cellspacing="0" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Afectación Presupuestal POI</div>
											</td>
										</tr>
									</table>

								</td>
							</tr>
						
							<tr>
								<td colspan="2">
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
								<td width="4%">
									<div id="divmsgImage" style="display: none;" >
										<img name="eje01" src="/a/imagenes/circulorojo.gif"/>
									</div>	 
								</td>
								<td width="96%" align="left" >
									<div id="divmsgExceso" style="display: none;" >
									<P>El ítem NO cuenta con SALDO para su atención por el canal de Compras Directas. Por lo tanto será atendido por otro canal</P>
									</div>
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
								<div id="divbtnGuardar" style="display: none;" >
									<button id="btnGuardar" name="btnGuardar" type="button" dojoType="dijit.form.Button" onclick="btnGuardar_click2">Guardar</button>
								</div>
							</td>
							
							<td>
								&nbsp;&nbsp;&nbsp;
								<div id="divbtnEnviar" style="display: none;" >
									<button id="btnEnviar" name="btnEnviar" type="button" dojoType="dijit.form.Button" onclick="btnValidaEnviar_click(${mapRqnp.codigoRqnp})">Enviar</button>
								</div>
							</td>
							<td>
								&nbsp;&nbsp;&nbsp;
								<div id="divbtnAtras" style="display: none;" >
									<button id="btnAtras" name="btnAtras" type="button" dojoType="dijit.form.Button" onclick="btnAtras_click">Atrás</button>
								</div>
							</td>
							<td>
								&nbsp;&nbsp;&nbsp;
								<div id="divbtnSalir" style="display: none;" >
									<button id="btnSalir" name="btnSalir" type="button" dojoType="dijit.form.Button" onclick="btnSalir_click()">Inicio</button>
								</div>
							<td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			
			<!-- EMERGENTE DE DATOS Envio -->
			<div  dojoType="dijit.Dialog" style= "width: 350px; height: 150px; display:none;"  id="formDialogEnvio" title="Procesando el Envío" execute="" >
			<table width="100%"  height="75%"  cellpadding="2" cellspacing="2" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td align="center">
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>											
											<td class="bg" align="center"  colspan="2" > <h5>Se está Procesando el Envío.</h5></td>
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
		
		<!-- Formulario de anulacion automatica por el sistema -->
		<form id="formAnulacion" name="formAnulacion" action="rqnp.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="action" name="action" value="anularRqnp"/>
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="<c:out value='${mapRqnp.codigoRqnp}'/>"/>
			<input type="hidden" id="txtAnulacion" name="txtAnulacion" />
		</form>
	</center>
	
</body>

</html>


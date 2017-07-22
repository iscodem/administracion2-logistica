<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title><fmt:message key="formRegistro.titulo" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" LANGUAGE="JavaScript" SRC="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
<script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/iarqnp.js"> </script>
<script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/formAucRqnpBien.js"> </script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/iarqnp.css" />

<script language="JavaScript" type="text/javascript">
    
	  var CONTEXTO_APP = "${pageContext.request.contextPath}";  
	
	var val_ls_tipo_necesidad=[<c:forEach items="${lsTipoNecesidad}"  var="tipo" varStatus="status">   {	id:"${tipo.cod}",	name:"${tipo.name}"    }${not status.last ? ',' : ''}	</c:forEach>];
	
	
	var val_ls_personal= [<c:forEach items="${lsPersonal}"  var="personaBean" varStatus="status">	{ vcodigoEmpleado:"${personaBean.codigoEmpleado}",	vnombre_completo: "${personaBean.nombre_completo}",	vnumero_registro: "${personaBean.numero_registro}",	vdependencia: "${personaBean.dependencia}"	}${not status.last ? ',' : ''}	</c:forEach>];
	
	
	var val_cod_necesidad ="${mapRqnp.cod_necesidad}";
	
	
	
   	
	
</script>


</head>

<body class="tundra">
	
	<center>
	
		<form id="frmRegistro" name="frmRegistro" action="bandejaauc.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id ="action" name="action" value="registrarCabRqnp"/>
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" value="${mapRqnp.codigoRqnp}" />
			<input type="hidden" id="txtCod_bien" name="txtCod_bien" value="${mapRqnp.cod_bien}" />
			<input type="hidden" id="txtCod_Unidad" name="txtCod_Unidad" value="${mapRqnp.cod_bien}" />
			<input type="hidden" id="jParametro" name="jParametro" value="formRegistroDetalle"/>
			<input type="hidden" id="jtipoConsulta" name="jtipoConsulta" value="atencion"/>
			
			
			<!--<input type="hidden" name="ind_estado" value="0"/>-->
			<table width="95%" cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center"><fmt:message key="formRegistro.titulo_item" /></div>
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
									<table width="90%" cellpadding="2" cellspacing="1" class="form-table">

										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistro.numRqnp" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.codigoReqNoProgramado}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistron.responsable" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.codigoResposanble}"></c:out> &nbsp;&nbsp;
												<c:out value="${mapRqnp.resposanble}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistro.uuoo" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.codigoUuoo}"></c:out> &nbsp;&nbsp;
												<c:out value="${mapRqnp.dependencia}"></c:out>
											</td>
										</tr>
										
										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistro.fechaRqnp" /></td>
											<td class="bg" align="left">
												<c:out value="${mapRqnp.fechaRqnp}"></c:out>
											</td>
										</tr>
									
																				
										
										
										
									</table>
									<br/>
									<div class="itemSeleccionadoBox" align="center" >  Ítem Origen </div>
									<table width="90%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistro.bien" /></td>
											<td class="bg" align="left">
												<input id="txtCod_bienO" name="txtCod_bienO"   
													value="${mapRqnp.item_origen}"
													 style="width: 100px;text-align:left"
													dojoType="dijit.form.ValidationTextBox"  
														maxlength="50"
													readonly="readonly"
													tabindex="-1"
													onkeydown="preventBackspace();"
												/>
												<input id="txtDes_bienO" name="txtDes_bienO"   
													value="${mapRqnp.desBien_origen}"
													 style="width: 550px;text-align:left"
													dojoType="dijit.form.ValidationTextBox"  
														maxlength="50"
													readonly="readonly"
													tabindex="-1"
													onkeydown="preventBackspace();"
												/>
											</td>
											
										</tr>
										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistro.unidad" /></td>
											<td class="bg" align="left">
												<input id="txtUnidadO" name="txtUnidadO"   
													value="${mapRqnp.desUnid_origen}"
													 style="width: 100px;text-align:left"
													dojoType="dijit.form.TextBox" 
														maxlength="5"
														readonly="readonly"
														tabindex="-1"
														onkeydown="preventBackspace();"
												/>(*)
											</td>
											
										</tr>

										
										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistro.precio" /></td>
											<td class="bg" align="left">
												<input id="txtPrecioO" name="txtPrecioO" maxlength="11" 
												dojoType="dijit.form.ValidationTextBox"  style="width: 100px;text-align:right"
												value ="${ mapRqnp.unid_precio_origen}"
												readonly="readonly"
												tabindex="-1"
												onkeydown="preventBackspace();"
												/>
											
											</td>
											
											
										</tr>
										
										
									</table>
									<br/>
									<br/>
									<div class="itemSeleccionadoBox" align="center" >  Ítem Modificado </div>
									<table width="90%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistro.bien" /></td>
											<td class="bg" align="left">
												<input id="txtCod_bienM" name="txtCod_bienM"   
													value="${mapRqnp.cod_bien}"
													 style="width: 100px;text-align:left"
													dojoType="dijit.form.ValidationTextBox"  
														maxlength="50"
													readonly="readonly"
													tabindex="-1"
													onkeydown="preventBackspace();"
												/>
												<button id="btnAdd" name="btnAdd" type="button" dojoType="dijit.form.Button" onclick="btnBuscar_click()">Cambiar</button>
												
												<input id="txtDes_bienM" name="txtDes_bienM"   
													value="${mapRqnp.des_bien}"
													 style="width: 450px;text-align:left"
													dojoType="dijit.form.ValidationTextBox"  
														maxlength="50"
													readonly="readonly"
													tabindex="-1"
													onkeydown="preventBackspace();"
												/>
											</td>
											
										</tr>
										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistro.unidad" /></td>
											<td class="bg" align="left">
												<input id="txtUnidadM" name="txtUnidadM"   
													value="${mapRqnp.des_unidad}"
													 style="width: 100px;text-align:left"
													dojoType="dijit.form.TextBox" 
														maxlength="5"
														readonly="readonly"
														tabindex="-1"
														onkeydown="preventBackspace();"
												/>(*)
											</td>
											
										</tr>

										
										<tr>											
											<td class="bg" align="left"><fmt:message key="formRegistro.precio" /></td>
											<td class="bg" align="left">
												<input id="txtPrecioM" name="txtPrecioM" maxlength="11" 
												dojoType="dijit.form.ValidationTextBox"  style="width: 100px;text-align:right"
												value ="${ mapRqnp.unid_precio}"
												readonly="readonly"
												tabindex="-1"
												onkeydown="preventBackspace();"
												/>
											
											</td>
											
											
										</tr>
										
										
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
			<table width="95%" cellpadding="2" cellspacing="2">
				<tr>
					<td align="center">
						<button id="btnGuardar" name="btnGuardar" type="button" dojoType="dijit.form.Button" onclick="btnGuardar_click()"  style="width: 50px;">Grabar</button>
						&nbsp;&nbsp;&nbsp;
						<button id="btnSalir" name="btnSalir" type="button" dojoType="dijit.form.Button" onclick="btnAtras_click()" style="width: 50px;">Atrás</button>
					</td>
				</tr>
			</table>
			
				<!-- DIALOGO CATALOGO ---------------------------------------------------------------------------- -->
			<div  dojoType="dijit.Dialog" style= "width: 650px; height: 400px; display:none;"  id="formDialogCatalogoFamilia" title="Buscar ítems Familia" execute="">
			<table width="100%"  height="80%"  cellpadding="1" cellspacing="1" >
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%"  height="100%" border=0>
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" >
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Consulta de Catálogo Familia</div>
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
												<input id="tipoConsulta1" name="tipoConsulta" 
												value="C"
													type="radio"
													dojoType="dijit.form.RadioButton" 
													onclick="tipoConsultaSetea(this.value);"
																									
												/>
												<label for="tipoConsulta1">Código</label> 
												
												<input id="tipoConsulta2" name="tipoConsulta" 
													type="radio"
													value="D"
													
													dojoType="dijit.form.RadioButton" 
												
												
													onclick ="tipoConsultaSetea(this.value);"												
												/>
												<label for="tipoConsulta2">Descripción</label> 
												&nbsp;&nbsp;Parámetros:
												
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

							<tr>
								<td>&nbsp;</td>
							</tr>
							
							<tr>
								<td>
									<table width="100%"  border="0">
										<tr>
											<td>
												<div id="gridConsulta"  > </div>
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
									<button id="btnAceptar" name="btnAceptar" type="button" dojoType="dijit.form.Button" onclick="btnAceptarCatalogo_click()">Aceptar</button>
								</td>
							
								<td>
									&nbsp;&nbsp;&nbsp;
									<button id="btnSalirCatalogo" name="btnSalirCatalogo" type="button" dojoType="dijit.form.Button" onclick="btnSalirCatalogo_click()">Cancelar</button>
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

</html>




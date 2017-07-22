<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Atender  Requerimiento No Programado </title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" LANGUAGE="JavaScript" SRC="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
<script type="text/javascript" LANGUAGE="JavaScript" src="${pageContext.request.contextPath}/js/formBandejaAUC.js"> </script>

<script type="text/javascript">
var CONTEXTO_APP = "${pageContext.request.contextPath}";  
var val_ls_rqnp=[
	<c:forEach items="${lsrqnp}"  var="rqnp" varStatus="status">
 	{
 		num_rqnp:"${rqnp.num_rqnp}", 
 		fec_rqnp: "${rqnp.fec_rqnp}",
 		solicitante:"${rqnp.solicitante}", 
 		uuoo_solicitante:"${rqnp.uuoo_solicitante}", 
 		motivo_sol:"${rqnp.motivo_sol}", 
 		cod_resp:"${rqnp.cod_resp}", 
 		cod_dep:"${rqnp.cod_dep}",
 		monto_total:"${rqnp.monto_total}",
 		cod_rqnp:"${rqnp.cod_rqnp}",
 		num_auc:"${rqnp.num_auc}",
 		num_item:"${rqnp.num_item}",
 		atender:  "${rqnp.cod_rqnp}",
 		validar:  "${rqnp.cod_rqnp}",
 		seguimiento: "${rqnp.cod_rqnp}",
 		formato    : "${rqnp.cod_rqnp}",
 		anio_atencion    : "${rqnp.anio_atencion}",
 		mes_atencion    : "${rqnp.mes_atencion}"
		}   
 	${not status.last ? ',' : ''}
	</c:forEach>
]
var estadoStore =  { 
		 identifier: 'id',
		    label: 'name',
		    items: [
	    	     {name:"Todos", id:"%"},
	        	 {name:"Enviado AT", id:"02"}

		     ]
		 };
</script>



<script type="text/javascript">


</script>

</head>
<body  class="tundra">
	<div dojoType="dojo.data.ItemFileReadStore" data="estadoStore" jsId="idEstadoStore"></div>
	<div dojoType="dojo.data.ItemFileReadStore" url="${pageContext.request.contextPath}/bandejaauc.htm?action=listarAnnosJson" jsId="idAnnosStore"></div>
	<center>
		<form id="frmBandejaAuc" name="frmBandejaAuc" action="bandejaauc.htm" method="POST" dojoType="dijit.form.Form">
			<input type="hidden" id="action" name="action" />
			<input type="hidden" id="txtCodigoRqnp" name="txtCodigoRqnp" />
			<input type="hidden" id="path_url" name="path_url"  value="bandejaauc.htm?action=bandejaauc"/>
			
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
												<div align="center"><fmt:message key="formRegistroAucAtender.titulo" /> </div>
											</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr >
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
							 
							<td align="left" >
								<table width="100%" cellpadding="8" cellspacing="2" border="0">
								<tr>
								
									<td width="10%" class="bg" align="right">
										<fmt:message key="formConsultaRqnp.anio" />
									</td>
									<td width="20%" class="bg" align="left">
									
										<input id="txtAnio"   		 name="txtAnio"   dojoType="dijit.form.FilteringSelect"  autoComplete="true" 
											 searchAttr="name" required="true" forceValidOption="false"
											promptMessage="Seleccione un Año"
											  store="idAnnosStore"
											style="width:200px;"
											value ="<c:out value='${anio}'/>" />
											
										
									</td>
									
									<td width="10%" class="bg" align="right">
										<fmt:message key="formRegistro.estado" />
									</td>
									<td width="30%" class="bg" align="left">
										
											<input id="txtCod_estado"   		 name="txtCod_estado"   dojoType="dijit.form.FilteringSelect"  autoComplete="true" 
											 searchAttr="name" required="true" forceValidOption="false"
											promptMessage="Seleccione un Estado"
											  store="idEstadoStore"
											style="width:200px;"
											value="02" />
										
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
											
						</table>
					</td>
				</tr>
			</table>
			
			
			
				
		</form>
	
	</center>

</body>


</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

			<div class="panel panel-info" id="divRegistroRqnpCabMod"> 
				<div class="panel-heading"><fmt:message key="formRegistroRqnpCab.tituloDetalle" /></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 col-lg-offset-1 col-md-offset-1 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.numReqNoProgramado" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
							<input type="text" class="form-control input-sm upperCaseTextClase" id="txtNumReqNoProgramado" value="${mapRqnp.numReqNoProgramado}" disabled />
						</div>
						
						<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.fechaRqnp" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
							<input type="text" class="form-control input-sm upperCaseTextClase" id="txtFechaRqnp" value="${mapRqnp.fechaRqnp}" disabled />
						</div>
						
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.responsable" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
							<input type="hidden" id="txtNumRegResponsable" value="${mapRqnp.numRegResponsable}" />
							<input type="text" class="form-control input-sm upperCaseTextClase" value="${mapRqnp.numRegResponsable} - ${mapRqnp.nomResponsable}" disabled />
						</div>
					</div>
					<div class="row">	
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.uuoo" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
							<input type="hidden" id="txtUuooResponsable" value="${mapRqnp.uuooResponsable}" />
							<input type="hidden" id="txtNomUuooResponsable" value="${mapRqnp.nomUuooResponsable}" />
							<input type="text" class="form-control input-sm upperCaseTextClase" value="${mapRqnp.uuooResponsable} - ${mapRqnp.nomUuooResponsable}" disabled />
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.numRegistroContacto" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
							<input type="text" class="form-control input-sm upperCaseTextClase" id="txtNumRegContacto" value="${mapRqnp.numRegContacto}" maxlength="4" />
							<input type="hidden" id="txtCodEmpleadoContacto" name="txtCodEmpleadoContacto" value="${mapRqnp.codEmpleadoContacto}" />
							<input type="hidden" id="txtCodDepContacto" name="txtCodDepContacto" value="${mapRqnp.codDepContacto}" />
						</div>
						<div class="col-xs-12 col-sm-12 col-md-5 col-lg-5">
							<input type="text" class="form-control input-sm upperCaseTextClase" id="txtNomContacto" value="${mapRqnp.nomContacto}" disabled />
						</div>
						<div class="col-xs-12 col-sm-12 col-md-1 col-lg-1" >
							<button id="btnBuscarPersona" name="btnBuscarPersona" type="button" class="btn btn-sm btn-primary">Buscar</button>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.anexoContacto" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
							<input type="text" class="form-control input-sm upperCaseTextClase" id="txtAnexoContacto" value="${mapRqnp.anexoContacto}" maxlength="6"/>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.numRucproveedor" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
							<input type="text" class="form-control input-sm upperCaseTextClase" id="txtNumRucProveedor" value="${mapRqnp.numRucProveedor}" maxlength="11"/>
							<input type="hidden" id="txtCodProveedor" name="txtCodProveedor" value="${mapRqnp.codProveedor}" />
						</div>
						<div class="col-xs-12 col-sm-12 col-md-5 col-lg-5">
							<input type="text" class="form-control input-sm upperCaseTextClase" id="txtNomProveedor" value="${mapRqnp.nomProveedor}" disabled />
						</div>
						<div class="col-xs-12 col-sm-12 col-md-1 col-lg-1">
							<button id="btnRecuperarContratista" name="btnRecuperarContratista" type="button" class="btn btn-sm btn-primary" >Buscar</button>
						</div>
					</div>
					<div class="row" id="divJustificacionRuc">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.obsJustificaProveedor" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
							<input type="text" class="form-control input-sm upperCaseTextClase" id="txtObsJustificaProveedor" value="${mapRqnp.obsJustificacion}"/>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.obsPlazoEntrega" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
							<input type="text" class="form-control input-sm " id="txtObsPlazoEntrega" value="${mapRqnp.obsPlazoEntrega}"/>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.obsLugarEntrega" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
							<input type="text" class="form-control input-sm " id="txtObsLugarEntrega" value="${mapRqnp.obsLugarEntrega}"/>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.obsDirEntrega" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
							<input type="text" class="form-control input-sm " id="txtObsDirEntrega" value="${mapRqnp.obsDirEntrega}"/>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.codFinalidad" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
							<select id="txtCodFinalidad" name="txtCodFinalidad" class="form-control input-sm upperCaseTextClase required">
								<option value="0" selected disabled><fmt:message key="formRegistroRqnpCab.seleccioneOpcion" /></option>
								<c:set var="cod_finalidad" value="${mapRqnp.codFinalidad }"/>
								<c:forEach items="${lsFinalidad}" var="item">
									<c:choose>
										<c:when test="${item.cod==cod_finalidad}">
											<option value="${item.cod}" selected>${item.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${item.cod}">${item.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach> 
							</select>
						</div>
						
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.codNecesidad" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
							<!-- <input type="text" class="form-control input-sm upperCaseTextClase" id="txtCod_necesidad" name="txtCod_necesidad" /> -->
							<select id="txtCodNecesidad" name="txtCodNecesidad"class="form-control input-sm upperCaseTextClase required">
								<option value="0" selected disabled ><fmt:message key="formRegistroRqnpCab.seleccioneOpcion" /></option>
								<c:set var="cod_necesidad" value="${mapRqnp.codNecesidad}"/>
								<c:forEach items="${lsNecesidad}" var="item">
									<c:choose>
										<c:when test="${item.cod==cod_necesidad}">
											<option value="${item.cod}" selected>${item.name}</option>
										</c:when>    
										<c:otherwise>
											<option value="${item.cod}">${item.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach> 
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.anioAtencion" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
							<select id="txtAnioAtencion" name="txtAnioAtencion"  class="form-control input-sm upperCaseTextClase">
								<option value="0" selected disabled><fmt:message key="formRegistroRqnpCab.seleccioneOpcion" /></option>
									<c:set var="anio_atencion" value="${mapRqnp.anioAtencion}"/>
									<c:forEach items="${arrAnios}" var="item">
										<c:choose>
											<c:when test="${item.codigoAnio==anio_atencion}">
												<option value="${item.codigoAnio}" selected>${item.descripcionAnio}</option>
											</c:when>    
											<c:otherwise>
												<option value="${item.codigoAnio}">${item.descripcionAnio}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach> 
							</select>
							
						</div>
						
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.mesAtencion" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
							<select id="txtMesAtencion" name="txtMesAtencion" class="form-control input-sm upperCaseTextClase">
								<option value="0" selected disabled><fmt:message key="formRegistroRqnpCab.seleccioneOpcion" /></option>
									<c:set var="mes_atencion" value="${mapRqnp.mesAtencion}"/>
									<c:forEach items="${arrMeses}" var="item">
										<c:choose>
											<c:when test="${item.codigoMes==mes_atencion}"> 
												<option value="${item.codigoMes}" selected>${item.descripcionMes}</option>
											</c:when>    
											<c:otherwise>
												<option value="${item.codigoMes}">${item.descripcionMes}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach> 
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.tituloVinculo" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
							<!-- <input type="radio" class="form-control input-sm upperCaseTextClase" id="optVinculo" name="optVinculo" value="" onclick="tipoRegistroSeteaVinculo(this.value);"/> -->
							
							<div class="btn-group optTipoVinculo input-sm upperCaseTextClase sinBorderMargenPadding">
								<c:if test="${mapRqnp.indVinculo=='S'}">
									<button type="button" class="btn btn-sm btn-primary optVinculo active" value="S" >SI</button>
									<button type="button" class="btn btn-sm btn-default optVinculo " value="N" >NO</button>
								</c:if>
								<c:if test="${mapRqnp.indVinculo=='N'}">
									<button type="button" class="btn btn-sm btn-default optVinculo " value="S" >SI</button>
									<button type="button" class="btn btn-sm btn-primary optVinculo active" value="N" >NO</button>
								</c:if>
							</div>
						</div>
					</div>
					
					<div class="row" id="divTipoVinculo">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase ">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.tipoVinculo" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
							<select id="txtTipoVinculo" name="txtTipoVinculo"  class="form-control input-sm upperCaseTextClase">
								<option value="0" selected disabled><fmt:message key="formRegistroRqnpCab.seleccioneOpcion" /></option>
									<c:set var="tipo_vinculo" value="${mapRqnp.tipoVinculo}"/>
									<c:forEach items="${lsTipoVinculo}" var="item">
										<c:choose>
											<c:when test="${item.cod==tipo_vinculo}">
												<option value="${item.cod}" selected>${item.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${item.cod}">${item.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach> 
							</select>
						</div>
						
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.vinculo" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
							<select id="txtVinculo" name="txtVinculo" class="form-control input-sm upperCaseTextClase">
								<option value="0" selected disabled><fmt:message key="formRegistroRqnpCab.seleccioneOpcion" /></option>
									<c:set var="vinculo" value="${mapRqnp.vinculo}"/>
									<c:forEach items="${lsVinculo}" var="item">
										<c:choose>
											<c:when test="${item.cod== vinculo}">
												<option value="${item.cod}" selected>${item.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${item.cod}">${item.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach> 
							</select>
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.tituloPrestamo" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
							<!-- <input type="radio" class="form-control input-sm upperCaseTextClase" id="optPrestamo" name="optPrestamo" value="" onclick="tipoRegistroSeteaPrestamo(this.value);" /> -->
							<div class="btn-group optTipoPrestamo input-sm upperCaseTextClase sinBorderMargenPadding"> 
								<button type="button" class="btn btn-sm btn-default optPrestamo" value="S">SI</button>
								<button type="button" class="btn btn-sm btn-primary optPrestamo active" value="N">NO</button>
							</div>
						</div>
					</div>
					<div class="row">	
						<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 txtTextRightClase">
							<label class="btn-sm"><fmt:message key="formRegistroRqnpCab.label.motivoSustento" /></label>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
							<textarea class="form-control input-sm " id="txtMotivoSustento" name="txtMotivoSustento" placeholder="Por favor ingrese el motivo">${mapRqnp.motivoSustento}</textarea>
						</div>
					</div>
					
					<div class="row text-center">	
						<p><span>(*)Datos Obligatorios</span></p>
					</div>
					<div class="row text-center">
					<!-- <div class="text-center"> -->
						<button type="button" id="btnRqnpCabGuardar" class="btn btn-primary btn-sm" data-dismiss="modal" >Grabar</button>
						<button type="button" id="btnRqnpCabCancelar" class="btn btn-primary btn-sm" data-dismiss="modal" >Cancelar</button>
					<!-- </div> -->
					</div>
				</div>
			</div>
							
							
				
	<div id="divHiddenPost">
		<form id="formRegistroRqnpCabMod" method="POST" action="rqnp.htm"  >
			<input type="hidden" id="action" name="action" value="registrarCabRqnp" />
			<input type="hidden" id="hidCodigoRqnp" name="hidCodigoRqnp" value="" />
			<input type="hidden" id="hidCodEmpleadoContacto" name="hidCodEmpleadoContacto" value="" />
			<input type="hidden" id="hidCodDepContacto" name="hidCodDepContacto"  value=""/>
			<input type="hidden" id="hidAnexoContacto" name="hidAnexoContacto"  value=""/>
			<input type="hidden" id="hidNumRucProveedor" name="hidNumRucProveedor"  value=""/>
			<input type="hidden" id="hidCodProveedor" name="hidCodProveedor"  value=""/>
			<input type="hidden" id="hidObsJustificaProveedor" name="hidObsJustificaProveedor"  value=""/>
			<input type="hidden" id="hidObsPlazoEntrega" name="hidObsPlazoEntrega"  value=""/>
			<input type="hidden" id="hidObsLugarEntrega" name="hidObsLugarEntrega"  value=""/>
			<input type="hidden" id="hidObsDirEntrega" name="hidObsDirEntrega"  value=""/>
			<input type="hidden" id="hidCodFinalidad" name="hidCodFinalidad"  value=""/>
			<input type="hidden" id="hidCodNecesidad" name="hidCodNecesidad"  value=""/>
			<input type="hidden" id="hidAnioAtencion" name="hidAnioAtencion"  value=""/>
			<input type="hidden" id="hidMesAtencion" name="hidMesAtencion"  value=""/>
			<input type="hidden" id="hidOptVinculo" name="hidOptVinculo"  value=""/>
			<input type="hidden" id="hidTipoVinculo" name="hidTipoVinculo"  value=""/>
			<input type="hidden" id="hidVinculo" name="hidVinculo"  value=""/>
			<input type="hidden" id="hidOptPrestamo" name="hidOptPrestamo"  value=""/>
			<input type="hidden" id="hidMotivoSustento" name="hidMotivoSustento"  value=""/>
			<input type="hidden" id="hidMontoRqnp" name="hidMontoRqnp"  value=""/>
			<input type="hidden" id="hidRqnpCabTipoAccion" name="hidRqnpCabTipoAccion"  value=""/>
			
			<!-- 
			mapRqnp.put("codigoRqnp"			, rqnp.getCodigoRqnp());
			mapRqnp.put("numReqNoProgramado"	, rqnp.getCodigoReqNoProgramado());
			mapRqnp.put("fechaRqnp"				, FechaUtil.formatDateToDateDDMMYYYYHHMM(rqnp.getFechaRqnp()));
			mapRqnp.put("numRegResponsable"		, rqnp.getNumeroRegistro());
			mapRqnp.put("codEmpleadoResponsable",colaborador.getCodigoEmpleado());
			mapRqnp.put("nomResponsable"		, rqnp.getNombreSolicitante());
			mapRqnp.put("uuooResponsable"		, rqnp.getUuoo());
			mapRqnp.put("codDepResponsable"		, rqnp.getCodigoDependencia());
			mapRqnp.put("nomUuooResponsable"	, rqnp.getNombreUOSolicitante());
			mapRqnp.put("numRegContacto"		, personalContacto.getNumero_registro());
			mapRqnp.put("codEmpleadoContacto"	, rqnp.getCod_contacto());
			mapRqnp.put("codDepContacto"		, rqnp.getCod_contacto());
			mapRqnp.put("nomContacto"			, personalContacto.getNombre_completo());
			mapRqnp.put("anexoContacto"			, rqnp.getAnexo_contacto());
			mapRqnp.put("codProveedor"			, contratista.getCod_cont());
			mapRqnp.put("numRucProveedor"		, contratista.getNum_ruc());
			mapRqnp.put("nomProveedor"			, contratista.getRaz_social()); 
			mapRqnp.put("obsJustificacion"		, rqnp.getObs_justificacion());
			mapRqnp.put("obsPlazoEntrega"		, rqnp.getObs_plazo_entrega());
			mapRqnp.put("obsLugarEntrega"		, rqnp.getObs_lugar_entrega());
			mapRqnp.put("obsDirEntrega"			, rqnp.getObs_dir_entrega());
			mapRqnp.put("codFinalidad"			, rqnp.getCod_finalidad());
			mapRqnp.put("codNecesidad"			, rqnp.getCod_necesidad());
			mapRqnp.put("anioAtencion"			, rqnp.getAnio_atencion());
			mapRqnp.put("mesAtencion"			, rqnp.getMes_atencion());
			mapRqnp.put("anioProceso"			, rqnp.getAnioProceso());
			mapRqnp.put("indVinculo"			, rqnp.getInd_vinculo());
			mapRqnp.put("tipoVinculo"			, rqnp.getTipo_vinculo());
			mapRqnp.put("vinculo"				, rqnp.getVinculo());
			mapRqnp.put("indPrestamo"			, rqnp.getInd_prestamo());
			mapRqnp.put("motivoSustento"		, rqnp.getMotivoSolicitud());
			mapRqnp.put("montoRqnp"				, rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
			mapRqnp.put("motivoRechazo"			, rqnp.getMotivoRechazo());
			-->
			
		</form>	
		
		<form id="formModificarRqnpCab" method="POST" action="rqnp.htm"  >
			<input type="hidden" id="action" name="action" value="modificarRqnp" />
			<input type="hidden" id="hidCodigoRqnpMod" name="hidCodigoRqnpMod" />
		</form>	
		
		<form id="formInicioBandejaRqnp" method="POST" action="rqnp.htm"  >
			<input type="hidden" id="action" name="action" value="iniciarbandeja" />
			<input type="hidden" id="hidCodigoRqnpMod" name="hidCodigoRqnpMod" />
		</form>	
		
	</div>	

		<!-- Includes -->
		<jsp:include page="../general/buscarColaborador.jsp" />
		<%-- <jsp:include page="../general/formMensajerias.jsp" /> --%>
		
		
		<!-- Modales para la  mensajeria del detalle del requerimiento -->
		<div id="divModalPopupRqnpCabMod" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
			<div id="divPopupContainerClassRqnpCabMod" class="container sigaMensajeConfirmacionContainer verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
					<div class="col-xs-12">
						<div id="divPopupPanelClassRqnpCabMod" class="panel panel-primary"> 
							<div class="panel-heading" id="divPopupMensajeRqnpCabMod">Mensaje</div>
							<div class="panel-body">
								<div class="text-center">
									<button type="button" id="btnPopupAceptarRqnpCabMod" class="btn btn-primary btn-sm" data-dismiss="modal">Aceptar</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Mensaje de confirmacion | confirm() -->
		<div id="divModalPopupSINORqnpCabMod" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
			<div id="divPopupContainerClassSINORqnpCabMod" class="container sigaMensajeConfirmacionContainer verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
					<div class="col-xs-12">
						<div id="divPopupPanelClassSINORqnpCabMod" class="panel panel-info">
							<div class="panel-heading" id="divPopupMensajeSINORqnpCabMod">Mensaje</div>
							<div class="panel-body">
								<div class="text-center">
									<button type="button" id="btnPopupAceptarSINORqnpCabMod" class="btn btn-primary btn-sm " data-dismiss="modal" title="Si">SI</button>
									<button type="button" id="btnPopupCancelarSINORqnpCabMod" class="btn btn-primary btn-sm " data-dismiss="modal" title="No">NO</button>
								</div>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<div id="divErrorAplicativoRqnpCabMod" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
			<div class="container sigaMensajeConfirmacionContainer verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
					<div class="col-xs-12">
						<div class="panel panel-danger">
							<div id="divTituloErrorAplicativoRqnpCabMod" class="panel-heading divErrorRqnpMensajeConfirmacionContainer"><fmt:message key="registrarSolicitud.errorGenerico.tituloModal" /></div>
							<div class="panel-body">
								<div id="divErrorAplicativoMensajeRqnpCabMod" class="row">
									<div class="col-xs-12 txtTextJustifyClase">
										<span id="spanErrorAplicativoRqnpCabMod"><fmt:message key="registrarSolicitud.errorGenerico.mensaje" /></span>
									</div>
								</div>
								<div class="row paddingTop10">
									<div class="col-xs-12">
										<button type="button" id="btnErrorAplicativoAceptarRqnpCabMod" class="btn btn-danger btn-sm center-block centerBlockBotonSmallClass" data-dismiss="modal" title="<fmt:message key='registrarSolicitud.errorGenerico.aceptar' />"><fmt:message key="registrarSolicitud.errorGenerico.aceptar" /></button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<div id="divScreenBlockRqnpCabMod" class="modal fade divScreenBlockClass" data-keyboard="false" data-backdrop="static">
			<div class="container verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
				</div>
			</div>
		</div>
		<!-- Fin de modales del detalle del requerimiento -->
		
		
		<script src="${contextPathUrl}/js/general/formMensajeriaGenerico.js"></script>
<%-- 	
	<script src="${contextPathUrl}/js/common/common.js"></script>
	<script src="${contextPathUrl}/js/iarqnp.js"></script>
	<script src="${contextPathUrl}/js/general/buscarColaborador.js"></script>
	<script src="${contextPathUrl}/js/general/formRegistroRqnpCabMod.js"></script>
	 --%>
	
	<script type="text/javascript">
	// grillas de la pantalla principal formRegistroRqnpCabMod
		
		var rqnpCabDatos = {
			'numReqNoProgramado'			:"${mapRqnp.numReqNoProgramado}",
			'codigoRqnp'					:"${mapRqnp.codigoRqnp}",
			'codEmpleadoResponsable'		:"${mapRqnp.codEmpleadoResponsable}",
			'fechaRqnp'						:"${mapRqnp.fechaRqnp}",
			'numRegResponsable'				:"${mapRqnp.numRegResponsable}",
			'uuooResponsable'				:"${mapRqnp.uuooResponsable}",
			'nomUuooResponsable'			:"${mapRqnp.nomUuooResponsable}",
			'nomResponsable'				:"${mapRqnp.nomResponsable}",
			'codDepResponsable'				:"${mapRqnp.codDepResponsable}",
			'nomUuooResponsable'			:"${mapRqnp.nomUuooResponsable}",
			'codEmpleadoContacto'			:"${mapRqnp.codEmpleadoContacto}",
			'numRegContacto'				:"${mapRqnp.numRegContacto}",
			
			'anioProceso'					:"${mapRqnp.anioProceso}",
			'mesProceso'					:"${mapRqnp.mesProceso}",
			'codNecesidad'					:"${mapRqnp.codNecesidad}",
			'codFinalidad'					:"${mapRqnp.codFinalidad}",
			
			'indVinculo'					:"${mapRqnp.indVinculo}",
			'tipoVinculo'					:"${mapRqnp.tipoVinculo}",
			'vinculo'						:"${mapRqnp.vinculo}",
			'indPrestamo'					:"${mapRqnp.indPrestamo}",
			'motivoSustento'				:"${mapRqnp.motivoSustento}",
			
			'montoRqnp'						:"${mapRqnp.montoRqnp}",
			'motivoRechazo'					:"${mapRqnp.motivoRechazo}",
			'textoSeleccioneOpcion'			:"<fmt:message key='formRegistroRqnpCab.seleccioneOpcion' />",
			'mensajes': {
				solicitaConfirmacionGrabado:"<fmt:message key='formRegistroRqnpCab.mensajes.solicitaConfirmacionGrabado' />"
				}
		};
		debugger;
		var errorMessageRqnpCabDatos = {
			numRegistroContacto: "<fmt:message key='formRegistroRqnpCab.messageError.numRegistroContacto' />",
			anexoContacto: "<fmt:message key='formRegistroRqnpCab.messageError.anexoContacto' />",
			numRucProveedor: "<fmt:message key='formRegistroRqnpCab.messageError.numRucProveedor' />",
			obsJustificaProveedor: "<fmt:message key='formRegistroRqnpCab.messageError.obsJustificaProveedor' />",
			obsPlazoEntrega: "<fmt:message key='formRegistroRqnpCab.messageError.obsPlazoEntrega' />",
			obsLugarEntrega: "<fmt:message key='formRegistroRqnpCab.messageError.obsLugarEntrega' />",
			obsDirEntrega: "<fmt:message key='formRegistroRqnpCab.messageError.obsDirEntrega' />",
			codFinalidad: "<fmt:message key='formRegistroRqnpCab.messageError.codFinalidad' />",
			codNecesidad: "<fmt:message key='formRegistroRqnpCab.messageError.codNecesidad' />",
			tipoVinculo: "<fmt:message key='formRegistroRqnpCab.messageError.tipoVinculo' />",
			vinculo: "<fmt:message key='formRegistroRqnpCab.messageError.vinculo' />",
			motivoSustento: "<fmt:message key='formRegistroRqnpCab.messageError.motivoSustento' />"
		};
		
		var datosMensajeriaRqnpCabMod={
				'divPopupPanelClass'	:'divPopupPanelClassRqnpCabMod',
				'divPopupContainerClass':'divPopupContainerClassRqnpCabMod',
				'divPopupMensaje'		:'divPopupMensajeRqnpCabMod',
				'divModalPopup'			:'divModalPopupRqnpCabMod',
				'btnPopupAceptar'		:'btnPopupAceptarRqnpCabMod',
				'divScreenBlockRqnpDetalle':'divScreenBlockRqnpCabMod',
				
				'divPopupContainerClassSINO':'divPopupContainerClassSINORqnpCabMod',
				'divPopupMensajeSINO':'divPopupMensajeSINORqnpCabMod',
				'divPopupPanelClassSINO':'divPopupPanelClassSINORqnpCabMod',
				'divModalPopupSINO':'divModalPopupSINORqnpCabMod',
				'divPopupMensajeSINO':'divPopupMensajeSINORqnpCabMod',
				'btnPopupAceptarSINO':'btnPopupAceptarSINORqnpCabMod',
				'btnPopupCancelarSINO':'btnPopupCancelarSINORqnpCabMod'
				}
		
	</script>
					
					
		

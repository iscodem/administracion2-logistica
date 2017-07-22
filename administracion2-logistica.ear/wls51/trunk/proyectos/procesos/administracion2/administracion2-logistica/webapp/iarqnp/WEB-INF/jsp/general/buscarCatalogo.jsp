<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="divBuscarCatalogo" class="modal fade" data-keyboard="false" data-backdrop="static" >
	<div class="container sigaModalContainerMedium verticalAlignmentHelper">
	<div class="row verticalAlignCenter">
		<div class="row">
			<div class="col-xs-12">
				<div class="panel panel-primary">
					<div class="panel-heading"><fmt:message key="buscarCatalogo.titulo" /></div>
					<div class="panel-body">
						
						<div class="row">
							<div class="col-xs-12">
								<div class="panel panel-info">
									<div class="panel-heading"><fmt:message key="buscarCatalogo.parametrosBusqueda" /></div>
									<div class="panel-body">
										<div class="row paddingBottomTop10">
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
												 <form class="form-inline" role="form" autocomplete="off">
													<div class="radio">
														<label>
														    <input type="radio" class="buscarCatalogoPor" id="porCodigo" name="tipoConsulta" value="C"  onclick="OnChangeRadioCatalogo(this)"> Por C&oacute;digo
														</label>
													</div>
													<div class="radio">	
														<label>
														    <input type="radio" class="buscarCatalogoPor" id="porDescripcion" name="tipoConsulta" value="D" checked="checked" onclick="OnChangeRadioCatalogo(this)"> Por Descripci&oacute;n
														</label>
													</div>
													
													<div class="form-group">
														<input type="text" id="txtParametroCatalogo" name="txtParametroCatalogo" class="form-control input-sm" placeholder="Ingrese descripciÛn..">
													</div>
													
													<div class="form-group">
														<input type="button" id="btnBuscarCatalogo" class="btn btn-primary btn-sm" value="Buscar"><br>
													</div>
												</form>
											</div>
										</div>
										
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div id="divLoadingBuscarCatalogo" class="col-xs-12 divLoadingCompleteRowClase">
								<span>
									<img src="/a/imagenes/loader.gif" alt="loader"><fmt:message key="buscarCatalogo.txtAjaxCargando" />
								</span>
							</div>
						</div>
						
						<div class="row divError" id="divErrorBuscarCatalogo" >
							<div class="col-xs-12">
								<label id="etiquetaErrorBuscarCatalogo" class="btn-sm center-block"></label>
							</div>
						</div>
								
						<div class="row">
							<div id="divCatalogoTable" class="col-md-12">
								<table id="tblCatalogo"></table>
								<div id="divCatalogoPagerTable"></div>
							</div>
						</div>

						<div class="paddingTop10"></div> <!-- espacioDivisor -->
						
						<div class="panel panel-info">
								<div class="row paddingBottomTop10">
									<div class="col-xs-6">
										<button id="btnAceptarCatalogo" type="button" class="btn btn-primary btn-sm pull-right"><fmt:message key="buscarCatalogo.btnAceptar" /></button>
									</div>
									<div class="col-xs-6">
										<button id="btnCancelarCatalogo" type="button" class="btn btn-primary btn-sm pull-left"><fmt:message key="buscarCatalogo.btnCancelar" /></button>
									</div>
								</div>
						</div>
						
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>
</div>
	
		<!-- Modales para la  mensajeria del detalle del requerimiento -->
		<div id="divModalPopupCatalogo" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
			<div id="divPopupContainerClassCatalogo" class="container sigaMensajeConfirmacionContainer verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
					<div class="col-xs-12">
						<div id="divPopupPanelClassCatalogo" class="panel panel-primary"> 
							<div class="panel-heading" id="divPopupMensajeCatalogo">Mensaje</div>
							<div class="panel-body">
								<div class="text-center">
									<button type="button" id="btnPopupAceptarCatalogo" class="btn btn-primary btn-sm" data-dismiss="modal">Aceptar</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Mensaje de confirmacion | confirm() -->
		<div id="divModalPopupSINOCatalogo" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
			<div id="divPopupContainerClassSINOCatalogo" class="container sigaMensajeConfirmacionContainer verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
					<div class="col-xs-12">
						<div id="divPopupPanelClassSINOCatalogo" class="panel panel-info">
							<div class="panel-heading" id="divPopupMensajeSINOCatalogo">Mensaje</div>
							<div class="panel-body">
								<div class="text-center">
									<button type="button" id="btnPopupAceptarSINOCatalogo" class="btn btn-primary btn-sm " data-dismiss="modal" title="Si">SI</button>
									<button type="button" id="btnPopupCancelarSINOCatalogo" class="btn btn-primary btn-sm " data-dismiss="modal" title="No">NO</button>
								</div>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<div id="divErrorAplicativoCatalogo" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
			<div class="container sigaMensajeConfirmacionContainer verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
					<div class="col-xs-12">
						<div class="panel panel-danger">
							<div id="divTituloErrorAplicativoCatalogo" class="panel-heading divErrorRqnpMensajeConfirmacionContainer"><fmt:message key="registrarSolicitud.errorGenerico.tituloModal" /></div>
							<div class="panel-body">
								<div id="divErrorAplicativoMensajeCatalogo" class="row">
									<div class="col-xs-12 txtTextJustifyClase">
										<span id="spanErrorAplicativoCatalogo"><fmt:message key="registrarSolicitud.errorGenerico.mensaje" /></span>
									</div>
								</div>
								<div class="row paddingTop10">
									<div class="col-xs-12">
										<button type="button" id="btnErrorAplicativoAceptarCatalogo" class="btn btn-danger btn-sm center-block centerBlockBotonSmallClass" data-dismiss="modal" title="<fmt:message key='registrarSolicitud.errorGenerico.aceptar' />"><fmt:message key="registrarSolicitud.errorGenerico.aceptar" /></button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<div id="divScreenBlockCatalogo" class="modal fade divScreenBlockClass" data-keyboard="false" data-backdrop="static">
			<div class="container verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
				</div>
			</div>
		</div>
		<!-- Fin de modales del detalle del requerimiento -->


	<script type="text/javascript">
		var lsCatalogoJSON='${lsCatalogo}';
		var catalogoDatos = {
				'mensajes':{},
				'catalogoList':lsCatalogoJSON == ''? [] : $.parseJSON(lsCatalogoJSON)
			}
			
		var errorMessageBuscarCatalogo = {
			completarDescripcion: "<fmt:message key='buscarCatalogo.messageError.completarDescripcion' />",
			cantidadMinimaDescripcion: "<fmt:message key='buscarCatalogo.messageError.cantidadMinimaDescripcion' />",
			sinRegistrosBusqueda: "<fmt:message key='buscarCatalogo.messageError.sinRegistrosBusqueda' />",
			seleccionarRegistro: "<fmt:message key='buscarCatalogo.messageError.seleccionarRegistro' />",
			seleccionarTipoBusqueda: "<fmt:message key='buscarCatalogo.messageError.seleccionarTipoBusqueda' />",
			catalogoNoExiste: "<fmt:message key='buscarCatalogo.messageError.catalogoNoExiste' />",
			catalogoDeBaja: "<fmt:message key='buscarCatalogo.messageError.catalogoDeBaja' />",
			caracteresEspeciales: "<fmt:message key='buscarCatalogo.messageError.caracteresEspeciales' />"
		};
		
		var datosMensajeriaCatalogo={
				'divPopupPanelClass'	:'divPopupPanelClassCatalogo',
				'divPopupContainerClass':'divPopupContainerClassCatalogo',
				'divPopupMensaje'		:'divPopupMensajeCatalogo',
				'divModalPopup'			:'divModalPopupCatalogo',
				'btnPopupAceptar'		:'btnPopupAceptarCatalogo',
				'divScreenBlockRqnpDetalle':'divScreenBlockCatalogo',
				
				'divPopupContainerClassSINO':'divPopupContainerClassSINOCatalogo',
				'divPopupMensajeSINO':'divPopupMensajeSINOCatalogo',
				'divPopupPanelClassSINO':'divPopupPanelClassSINOCatalogo',
				'divModalPopupSINO':'divModalPopupSINOCatalogo',
				'divPopupMensajeSINO':'divPopupMensajeSINOCatalogo',
				'btnPopupAceptarSINO':'btnPopupAceptarSINOCatalogo',
				'btnPopupCancelarSINO':'btnPopupCancelarSINOCatalogo'
				}
		
		//var dataBusquedaCatalogo = new Object();
		var patronParametroBusquedaCatalogo = /^[0-9a-zA-Z·ÈÌÛ˙‡ËÏÚ˘¿»Ã“Ÿ¡…Õ”⁄Ò—¸‹_\s]+$/;
		
	</script>
	
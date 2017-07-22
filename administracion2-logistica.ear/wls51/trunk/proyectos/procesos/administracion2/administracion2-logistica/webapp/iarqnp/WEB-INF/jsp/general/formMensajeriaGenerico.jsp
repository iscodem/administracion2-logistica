<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- popup modal -->
		<div id="divModalPopup" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
			<div id="divPopupContainerClass" class="container sigaMensajeConfirmacionContainer verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
					<div class="col-xs-12">
						<div id="divPopupPanelClass" class="panel panel-info">
							<div class="panel-heading" id="divPopupMensaje">Mensaje</div>
							<div class="panel-body">
								<div class="text-center">
									<button type="button" id="btnPopupAceptar" class="btn btn-primary btn-sm" data-dismiss="modal">Aceptar</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
					
		<!-- Mensaje de confirmacion | confirm() -->
		<div id="divModalPopupSINO" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
			<div id="divPopupContainerClassSINO" class="container sigaMensajeConfirmacionContainer verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
					<div class="col-xs-12">
						<div id="divPopupPanelClassSINO" class="panel panel-info">
							<div class="panel-heading" id="divPopupMensajeSINO">Mensaje</div>
							<div class="panel-body">
								<div class="text-center">
									<button type="button" id="btnPopupAceptarSINO" class="btn btn-primary btn-sm " data-dismiss="modal" title="Si">SI</button>
									<button type="button" id="btnPopupCancelarSINO" class="btn btn-primary btn-sm " data-dismiss="modal" title="No">NO</button>
								</div>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<div id="divErrorAplicativo" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
			<div class="container sigaMensajeConfirmacionContainer verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
					<div class="col-xs-12">
						<div class="panel panel-danger">
							<div id="divTituloErrorAplicativo" class="panel-heading divErrorRqnpMensajeConfirmacionContainer"><fmt:message key="registrarSolicitud.errorGenerico.tituloModal" /></div>
							<div class="panel-body">
								<div id="divErrorAplicativoMensaje" class="row">
									<div class="col-xs-12 txtTextJustifyClase">
										<span id="spanErrorAplicativo"><fmt:message key="registrarSolicitud.errorGenerico.mensaje" /></span>
									</div>
								</div>
								<div class="row paddingTop10">
									<div class="col-xs-12">
										<button type="button" id="btnErrorAplicativoAceptar" class="btn btn-danger btn-sm center-block centerBlockBotonSmallClass" data-dismiss="modal" title="<fmt:message key='registrarSolicitud.errorGenerico.aceptar' />"><fmt:message key="registrarSolicitud.errorGenerico.aceptar" /></button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<div id="divScreenBlock" class="modal fade divScreenBlockClass" data-keyboard="false" data-backdrop="static">
			<div class="container verticalAlignmentHelper">
				<div class="row verticalAlignCenter">
				</div>
			</div>
		</div>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="divAgregarArchivo" class="modal fade" data-keyboard="false" data-backdrop="static">
	<div class="container viaticoModalContainerMedium">
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<div class="panel panel-primary">
					<div class="panel-heading"><fmt:message key="adjuntarDocumento.agregar.titulo" /></div>
					<div class="panel-body">
							<form id="formArchivo"  name="formArchivo" enctype="multipart/form-data" onKeypress="if(event.keyCode == 13) event.returnValue = false;">
								
								<div class="row">
										<div class="col-xs-12 col-sm-2 col-md-2 col-lg-2">
											<label for="selCodTipoArchivo" class="btn-sm"><fmt:message key="adjuntarDocumento.agregar.lblTipoArchivo" /></label>
										</div>
										<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">											
											<select class="form-control input-sm upperCaseTextClase" id="selCodTipoArchivo" name="selCodTipoArchivo" data-placeholder="Seleccione un estado...">
											</select>	
										</div>
								</div>
								<div class="row">
										<div class="col-xs-12 col-sm-2 col-md-2 col-lg-2">
											<label for="txtDescripcionArchivo" class="btn-sm"><fmt:message key="adjuntarDocumento.agregar.lblDescripcion" /></label>
										</div>
										<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">											
											<input class="form-control input-sm margen-input upperCaseTextClase" type="text" id="txtDescripcionArchivo" name="txtDescripcionArchivo" ></input>																			
										</div>
								</div>
							
								<div class="space space-6"></div>
								 							
										<div class="row">
											<div class="col-xs-12 col-sm-2 col-md-2 col-lg-2">
												<label for="lblNombreArchivo" class="btn-sm"><fmt:message key="adjuntarDocumento.agregar.lblNombreArchivo" /></label>
											</div>
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
												
												<input id="file" type="file" name="file"  class="file" accept=".msg, .pdf">
												<p class="pAdvertencia">(M&aacute;ximo permitido 1Mb)</p>
												<input type="hidden" name="hidNumeroRegitroArchivo" id="hidNumeroRegitroArchivo" value="">
												<input type="hidden" name="hidNumeroDocumento" id="hidNumeroDocumento" value="">
												<div id="divHiddenForm"></div>
											</div>										
										</div>								
							
									
								<div class="space space-6"></div>
								
									<div class="row">
										<div class="text-center">
											<button type="button" id="btnAceptarCarga" class="btn btn-sm btn-primary">Aceptar</button>
											<button type="button" id="btnCancelarCarga" class="btn btn-sm btn-primary">Cancelar</button>
										</div>
									</div>					
								
							</form>
							
							
							
							
							<div id="divMensajeConfirmacion" class="modal fade" data-keyboard="false" data-backdrop="static" >
								<div class="container viaticoMensajeConfirmacionContainer verticalAlignmentHelper">
									<div class="row verticalAlignCenter">
										<div class="col-xs-12">
											<div class="panel panel-primary">
												<div class="panel-heading text-center"><span id="spaMensajeConfirmacionTitulo"></span></div>
												<div class="panel-body">
													<div class="row paddingTop10 marginBottom10">
														<div class="text-center">
															<button id="btnSiMensaje" type="button" class="btn btn-primary btn-sm"><fmt:message key="comprobantePago.messageConfirmacion.Si"/></button>
															<button id="btnNoMensaje" type="button" class="btn btn-primary btn-sm"><fmt:message key="comprobantePago.messageConfirmacion.No"/></button>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						
						
						
						
							<div id="divMensajeInfomativo" class="modal fade" data-keyboard="false" data-backdrop="static" >
								<div class="container viaticoMensajeConfirmacionContainer verticalAlignmentHelper">
									<div class="row verticalAlignCenter">
										<div class="col-xs-12">
											<div class="panel panel-primary">
												<div class="panel-heading text-center"><span id="spaMensajeInformativoTitulo"></span></div>
												<div class="panel-body">
													<div class="row paddingTop10 marginBottom10">
														<div class="text-center">
															<span id="spaMensajeInformativoTexto"></span>
<%-- 															<button id="btnSiMensaje" type="button" class="btn btn-primary btn-sm"><fmt:message key="almacen.messageInformativo.Si"/></button> --%>
<%-- 															<button id="btnNoMensaje" type="button" class="btn btn-primary btn-sm"><fmt:message key="almacen.messageInformativo.No"/></button> --%>
															<button id="btnAceptarMensaje" type="button" class="btn btn-primary btn-sm"><fmt:message key="comprobantePago.messageInformar.Aceptar"/></button>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							
							<div id="divMensajeAdvertencia" class="modal fade" data-keyboard="false" data-backdrop="static" >
								<div class="container viaticoMensajeConfirmacionContainer verticalAlignmentHelper">
									<div class="row verticalAlignCenter">
										<div class="col-xs-12">
											<div class="panel panel-warning">
												<div class="panel-heading text-center"><span id="spaMensajeAdvertencia"></span></div>
												<div class="panel-body">
													<div class="row paddingTop10 marginBottom10">
														<div class="text-center">													   
															<button id="btnAceptarMensajeWarning" type="button" class="btn btn-warning btn-sm"><fmt:message key="comprobantePago.messageAdvertencia.Aceptar"/></button>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							
							<div id="divMensajeError" class="modal fade" data-keyboard="false" data-backdrop="static" >
								<div class="container viaticoMensajeConfirmacionContainer verticalAlignmentHelper">
									<div class="row verticalAlignCenter">
										<div class="col-xs-12">
											<div class="panel panel-danger">
												<div class="panel-heading text-center"><span id="spaMensajeError"></span></div>
												<div class="panel-body">
													<div class="row paddingTop10 marginBottom10">
														<div class="text-center">													   
															<button id="btnAceptarMensajeError" type="button" class="btn btn-danger btn-sm"><fmt:message key="comprobantePago.messageError.Aceptar"/></button>
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
			</div>
		</div>
	</div>
</div>



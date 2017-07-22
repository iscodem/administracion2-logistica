<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="formModalAgregarBeneficiaria" class="modal fade" data-keyboard="false" data-backdrop="static" >
	<div class="container">
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<div class="panel panel-primary" style="height:540px; max-height:540px; width:80%; max-width:80%; margin: 0 auto;">
					<div class="panel-heading">Agregar Archivo para unidades beneficiarias</div>
					<!-- <div class="panel-body">
						<div class="panel panel-default col-xs-12">
						-->
							<form id="form"  name="form" enctype="multipart/form-data">
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">
											<div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
												<label for="txtNombreArchivo" class="control-label text-muted alineartext">Nombre de Archivo:</label>
											</div>
											
											<div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
												<input id="file" type="file" name="file"  class="file" size="50%">
												<p class="pAdvertencia">(M&aacute;ximo permitido 1Mb)</p>
												<!-- <div id="divHiddenForm"></div> -->
											</div>
										</div>	
									</div>
									
									<div class="row">	
										<div class="col-md-12">
										    <div class="panel panel-primary">
											   <div class="panel-heading">Carga de Unidades Beneficiarias</div>
											  <div class="panel-body">
												<div class="table-responsive">
												  	<table id="tblBeneficiarias" class="table table-condensed table-striped table-bordered" 
												  			style="font-size:0.7em;">
												    </table>
												</div>
											  </div>
											</div><!--fin panel-->
										</div><!--fin col-->
									</div><!--fin row-->
									
									
									<div class="row">
										<div class="panel-body">
											<div class="text-center">
												<button type="button" id="btnAceptarCarga" class="btn btn-sm btn-primary">Procesar excel</button>
												<button type="button" id="btnGrabarBeneficiaria" class="btn btn-sm btn-primary">Grabar </button>
												<button type="button" id="btnBorrarCarga" class="btn btn-sm btn-primary">Eliminar</button>
												<button type="button" id="btnCancelarCarga" class="btn btn-sm btn-primary">Cancelar</button>
											</div>
										</div>
									</div>
								
								</div>
								
							</form>
							
						<!-- </div> -->
						<!-- /.row -->
					<!-- </div> -->
				</div>
			</div> <!--.col-xs-12 -->
			
		</div> <!--.row -->
		
		<div class="row">
			<div id="divMensajeAdvertenciaAgregarArchivo" class="modal fade" data-keyboard="false" data-backdrop="static" >
				<div class="container rqnpMensajeConfirmacionContainer verticalAlignmentHelper">
					<div class="row verticalAlignCenter">
						<div class="col-xs-12">
							<div class="panel panel-primary">
								<div  class="panel-heading text-center">
									<span id ="msgAdvertencia"></span>
								</div>
								<div class="panel-body">
									<div class="row paddingTop10 marginBottom10">
										<div class="text-center">
											<button id="btnOkMensajeAdvertenciaAgregarArchivo" type="button" class="btn btn-primary btn-sm">Aceptar</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div><!-- .container -->
</div>

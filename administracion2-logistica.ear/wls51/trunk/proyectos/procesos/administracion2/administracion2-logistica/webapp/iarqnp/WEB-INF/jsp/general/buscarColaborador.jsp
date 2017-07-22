<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="divBuscarColaborador" class="modal fade" data-keyboard="false" data-backdrop="static" >
	<div class="container sigaModalContainerMedium verticalAlignmentHelper">
	<div class="row verticalAlignCenter">
		<div class="row">
			<div class="col-xs-12">
				<div class="panel panel-primary">
					<div class="panel-heading"><fmt:message key="buscarColaborador.titulo" /></div>
					<div class="panel-body">
						
						<div class="row">
							<div class="col-xs-12">
								<div class="panel panel-info">
									<div class="panel-heading"><fmt:message key="buscarColaborador.parametrosBusqueda" /></div>
									<div class="panel-body">
										<div class="row paddingBottomTop10">
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
												 <form class="form-inline" role="form" autocomplete="off">
													<div class="radio">
														<label>
														    <input type="radio" class="buscarPor" id="porRegistro" name="tipoConsulta" value="01"  onclick="OnChangeRadioPersona(this)"> Por Registro
														</label>
													</div>
													<div class="radio">	
														<label>
														    <input type="radio" class="buscarPor" id="porNombre" name="tipoConsulta" value="02" checked="checked" onclick="OnChangeRadioPersona(this)"> Por Nombre
														</label>
													</div>
													<div class="radio">	
														<label>
														    <input type="radio" class="buscarPor" id="porUUOO" name="tipoConsulta" value="03" onclick="OnChangeRadioPersona(this)"> Por UUOO
														</label>
													</div>
													
													<div class="form-group">
														<input type="text" id="txtParametroColaborador" name="txtParametroColaborador" class="form-control input-sm" placeholder="Ingrese descripciÛn..">
													</div>
													
													<div class="form-group">
														<input type="button" id="btnBuscarColaborador" class="btn btn-primary btn-sm" value="Buscar"><br>
													</div>
												</form>
											</div>
										</div>
										
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div id="divLoadingBuscarColaborador" class="col-xs-12 divLoadingCompleteRowClase">
								<span>
									<img src="/a/imagenes/loader.gif" alt="loader"><fmt:message key="buscarColaborador.txtAjaxCargando" />
								</span>
							</div>
						</div>
						
						<div class="row divError" id="divErrorBuscarColaborador" >
							<div class="col-xs-12">
								<label id="etiquetaErrorBuscarColaborador" class="btn-sm center-block"></label>
							</div>
						</div>
							
						
						<div class="row">
							<div id="divColaboradorTable" class="col-md-12">
								<table id="tblColaborador"></table>
								<div id="divColaboradorPagerTable"></div>
							</div>
						</div>
						
						<div class="paddingTop10"></div> <!-- espacioDivisor -->
						
						<div class="panel panel-info">
							<div class="row paddingBottomTop10">
								<div class="col-xs-6">
									<button id="btnAceptarColaborador" type="button" class="btn btn-primary btn-sm pull-right"><fmt:message key="buscarColaborador.btnAceptar" /></button>
								</div>
								<div class="col-xs-6">
									<button id="btnCancelarColaborador" type="button" class="btn btn-primary btn-sm pull-left"><fmt:message key="buscarColaborador.btnCancelar" /></button>
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

	<script type="text/javascript">
		//var lsColaboradoresJSON = '${lsRqnp}';
		var lsColaboradorJSON="";
		var colaboradorDatos = {
				'mensajes':{},
				'colaboradorList':lsColaboradorJSON == ''? [] : $.parseJSON(lsColaboradorJSON)
			}
			
		var errorMessageBuscarColaborador = {
			completarDescripcion: "<fmt:message key='buscarColaborador.messageError.completarDescripcion' />",
			cantidadMinimaDescripcion: "<fmt:message key='buscarColaborador.messageError.cantidadMinimaDescripcion' />",
			sinRegistrosBusqueda: "<fmt:message key='buscarColaborador.messageError.sinRegistrosBusqueda' />",
			seleccionarRegistro: "<fmt:message key='buscarColaborador.messageError.seleccionarRegistro' />",
			seleccionarTipoBusqueda: "<fmt:message key='buscarColaborador.messageError.seleccionarTipoBusqueda' />",
			colaboradorNoExiste: "<fmt:message key='buscarColaborador.messageError.colaboradorNoExiste' />",
			colaboradorDeBaja: "<fmt:message key='buscarColaborador.messageError.colaboradorDeBaja' />",
			caracteresEspeciales: "<fmt:message key='buscarColaborador.messageError.caracteresEspeciales' />"
			
		};
		
		var dataBusquedaColaborador = new Object();
		var patronParametroBusquedaColaborador = /^[0-9a-zA-Z·ÈÌÛ˙‡ËÏÚ˘¿»Ã“Ÿ¡…Õ”⁄Ò—¸‹_\s]+$/;
		
	</script>
	
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<title>Error del Sistema</title>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />

</head>

<body>
	<center>
			<table width="95%" cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center">Requerimientos No Programados</div>
											</td>
										</tr>
									</table>

								</td>
							</tr>

							<tr>
								<td>&nbsp;</td>
							</tr>
							
							<tr>
								<td align="right">
									<table width="98%" cellpadding="2" cellspacing="1">
										<tr>
											<td align="left">
												La Aplicacion ha retornado el siguiente mensaje:
											</td>
										</tr>
										<tr>
											<td align="left">
												<br>
												<div style="color: red;">
													<b><spring:message code="error.mensaje.generico"/></b>
													${excepAttr.message} 
												</div>
												
												<br>
												<br>
												<br>
												
												<b><spring:message code="error.accion.mensaje"/></b>
												
											</td>
										</tr>
										
										<tr>
											<td align="left">
												<input class="button" type="button" value="Anterior" onclick="history.go(-1)">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
	</center>
</body>
</html>
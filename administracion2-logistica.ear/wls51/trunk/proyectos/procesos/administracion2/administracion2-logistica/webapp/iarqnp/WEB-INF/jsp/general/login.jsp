<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<c:set var="contextPathUrl" scope="session" value="${pageContext.request.contextPath}"/>
	<c:url var="loginUrl" scope="session" value="rqnp.htm" />
	<title><fmt:message key="formLogin.titulo" /></title>
	<!-- CSS - Bootstrap -->
	<link rel="stylesheet" href="/a/js/libs/bootstrap/3.3.1/css/bootstrap.min.css" />
	<!-- CSS - Common -->
	<link rel="stylesheet" href="${contextPathUrl}/css/common/common.css" />
	<!--[if IE]>
		<link rel="stylesheet" href="${contextPathUrl}/css/common/commonIE.css" />
	<![endif]-->
	<!-- JS - Componentes -->
	<!--[if IE]>
		<script src="${contextPathUrl}/js/common/respond.js"></script>
	<![endif]-->
</head>
<body>
	<div class="container sigaContainer">
		<c:if test="${not empty errorMessageLogin}">
			<div id="divErrorMessageLogin" class="row">
				<div class="col-xs-12">
					<div class="alert alert-danger" role="alert">
						<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
						${errorMessageLogin}
					</div>
				</div>
			</div>
		</c:if>
		<div class="row">
			<div class="col-xs-12 col-sm-6 col-sm-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">Login</div>
					<div class="panel-body">
						<form id="formLogin" name="formLogin" action="${loginUrl}" method="POST">
							<input type="hidden" name="action" value="iniciarSesion" />
                           	<input type="hidden" name="username" value="" />
                           	<input type="hidden" name="password" value="" />
							<div class="row">
								<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
									<label class="btn-sm">Username:</label>
								</div>
								<div class="col-xs-12 col-sm-12 col-md-10 col-lg-10">
									<input type="text" class="form-control input-sm upperCaseTextClase" id="txtUsername" maxlength="20" onkeypress="return submitLoginOnKeypressInput(event);"/>
								</div>
								<div class="clearfix hidden-xs hidden-sm visible-md visible-lg"></div>
								<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
									<label class="btn-sm">Password:</label>
								</div>
								<div class="col-xs-12 col-sm-12 col-md-10 col-lg-10">
									<input type="password" class="form-control input-sm upperCaseTextClase" id="txtPassword" onkeypress="return submitLoginOnKeypressInput(event);"/>
								</div>
							</div>
							<div class="row paddingTop10 text-center">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
									<button id="btnSubmitLogin" type="button" class="btn btn-primary btn-sm centerBlockBotonSmallClass">Login</button>
									<button id="btnSubmitLogin" name="reset" type="reset" class="btn btn-primary btn-sm centerBlockBotonSmallClass">Limpiar</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="/a/js/libs/jquery/1.11.1/jquery.min.js"></script>
	<!-- JS - Bootstrap -->
	<script src="/a/js/libs/bootstrap/3.3.1/js/bootstrap.min.js"></script>
	<!-- JS - Componentes -->
	<script src="${contextPathUrl}/js/common/common.js"></script>
	<script src="${contextPathUrl}/js/general/login.js"></script>
	<script type="text/javascript">
		var contextPathUrl = "${contextPathUrl}";
		
		$(document).ready(function() {
			initElementsLogin();
		});
	</script>
</body>
</html>

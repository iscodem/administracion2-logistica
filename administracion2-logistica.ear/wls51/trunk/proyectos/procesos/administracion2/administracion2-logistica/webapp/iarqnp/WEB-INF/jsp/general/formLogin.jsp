<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<style type="text/css">
.dijitValidationTextBox input.dijitInputInner,
.dijitNumberTextBox input.dijitInputInner,
.dijitCurrencyTextBox input.dijitInputInner,
.dijitSpinner input.dijitInputInner {
	padding-right: 1px;
	text-align: right;
}

input {
text-transform: uppercase;
}
</style>

<title><fmt:message key="formLogin.titulo" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/a/css/estilos2_0.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dijit/themes/tundra/tundra.css" />
<link rel="stylesheet" type="text/css" href="/a/js/dojo1.6.0/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css" />

<script type="text/javascript" src="/a/js/js.js"></script>
<script type="text/javascript" LANGUAGE="JavaScript" SRC="/a/js/dojo1.6.0/dojo/dojo.js" djConfig="parseOnLoad: true"></script>

<script type="text/javascript" LANGUAGE="JavaScript">
	dojo.require("dijit.form.Form");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dojo.data.ItemFileWriteStore");
	dojo.require("dojox.grid.EnhancedGrid");
    dojo.require("dojox.grid.enhanced.plugins.Pagination");
    dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
    dojo.require("dojo.parser");
    
</script>

<script type="text/javascript">


dojo.addOnLoad(function() {	
	
	
});



</script>

</head>

<body class="tundra">
	<center>

		<form id="formLogin" name="formLogin" action="rqnp.htm" method="POST" dojoType="dijit.form.Form" enctype="multipart/form-data" >
			<input type="hidden" name="action" value="menu"/>
		
		
			
			<table width="95%" cellpadding="2" cellspacing="2" class="form-table">
				<tr>
					<td  align="center" >
						<table align="center" cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td>
									<table width="100%" cellpadding="2" cellspacing="1" class="form-table">
										<tr>
											<td class="T1" colspan="2">
												<div align="center"><fmt:message key="formLogin.titulo" /></div>
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
									<table width="90%" cellpadding="2" cellspacing="1">
										<tr>
			  				    			<td class="bg" align="right"><fmt:message key="formLogin.usuario" /></td>
			  				    			<td class="bg" align="left">
			  									&nbsp;&nbsp;
															<input id="txtLogin" name="txtLogin" type="text" value="DLOCK" dojoType="dijit.form.ValidationTextBox" uppercase = "true"  style="font-size:13px; width:250px"/>
			  									&nbsp;&nbsp;&nbsp;
			  									
			              					</td>
			  				 			</tr>
										
										<tr>
			  				    			<td class="bg" align="right"><fmt:message key="formLogin.clave" /></td>
			  				    			<td class="bg" align="left">
			  									&nbsp;&nbsp;&nbsp;<input id="txtClave" name="txtClave" type="password" value="LPINTO" dojoType="dijit.form.ValidationTextBox" uppercase = "true"  style="font-size:13px; width:250px"/>
			  									&nbsp;&nbsp;&nbsp;
			  									&nbsp;&nbsp;&nbsp;<button id="btnEntrar" name="btnEntrar" type="submit"  >Entrar</button>
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


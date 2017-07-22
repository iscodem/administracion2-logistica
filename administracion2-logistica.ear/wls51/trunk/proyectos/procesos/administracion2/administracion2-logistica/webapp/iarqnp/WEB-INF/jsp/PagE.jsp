<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD><TITLE>.:: Página de Errores ::.</TITLE></HEAD>
<jsp:useBean id="beanErr" scope="request" class="pe.gob.sunat.framework.core.bean.MensajeBean" />
<%@ page import="java.util.*" %>
<link href="/a/css/estilos2_0.css" rel="stylesheet">
<body topmargin="20" leftmargin="20" >
<div align="left"  class="msg"><img border="0" src="/a/imagenes/rad_INFO01.gif">
La aplicaci&oacute;n ha retornado el siguiente problema :
</div>
<p class="bgn-neg-alert">
<jsp:getProperty name="beanErr" property="mensajeerror" />
</p>
<br>
<div class="bg">
<%   beanErr.setPosicion(0);
for ( int i=0; i < beanErr.getListsize() ; i++ )
{
%>
<%= beanErr.getListamensajes() %> <br>
<%   } %>
</div>
<br>
<HR size="1px">
<div align="left"  class="bgn">
Acci&oacute;n a realizar :
</div>
<div align="left" class="bg">
<% if (beanErr.getMensajesol()!=null && beanErr.getMensajesol().length()>0){ %>
<jsp:getProperty name="beanErr" property="mensajesol"/>
<% } else { %>
Por favor intente nuevamente realizar la operaci&oacute;n, si el problema persiste, avisar a nuestro <strong><a href="mailto:webmaster@sunat.gob.pe">webmaster</a></strong> o
comunicarse con Atención a Usuarios.
<% } %>
</div>
<HR size="5px">
<div align="LEFT">
<input class="button" type="button" value="Anterior" onclick="history.go(-1)">
</div>
</body>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<jsp:useBean id="beanErr" scope="session" class="pe.gob.sunat.framework.core.bean.MensajeBean" />
<%@ page import="java.util.*" %>
<link href="/a/css/estilos.css" rel="stylesheet">
<body topmargin="20" leftmargin="20" >
<SCRIPT LANGUAGE="JavaScript" SRC="/a/js/js.js"></SCRIPT>
<div align="left" class="msg"><img border="0" src="/a/imagenes/rad_INFO01.gif">
La aplicaci&oacute;n ha retornado el siguiente mensaje :
</div>
<p class="error">
<% String mensajeError = beanErr.getMensajeerror();%>
<%if(mensajeError!=null){%>
<li><%=mensajeError%></li>
<%}%>
</p>
<br>
<div>
<%
Object[] lista = beanErr.getListaobjetos0();
for (int i=0; i< lista.length ; i++){
%>
<li><%=lista[i]!="null"?lista[i].toString():"No se pudo obtener el error."%></li>
<br>
<%}%>
</div>
<br>
<HR size="1px">
<div align="left" class="msg">
Acci&oacute;n a realizar :
</div>
<div align="left" class="bg">
<jsp:getProperty name="beanErr" property="mensajesol" />
</div>
<HR size="5px" color="#0000ff">
<div align="LEFT">
<input class="button" type="button" value="Anterior" onclick="history.go(-1)">
</div>
<%@ include file="/Footer.jsp"%>

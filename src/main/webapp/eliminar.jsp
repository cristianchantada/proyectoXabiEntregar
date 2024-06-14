<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.cristian.carrito.models.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Eliminar producto</title>
</head>
<body>
<% 

	int codigo = Integer.parseInt(request.getParameter("codigo"));
	Carrito carrito = (Carrito) session.getAttribute("carrito");
	carrito.eliminarproducto(codigo);
	session.setAttribute("carrito", carrito);
	response.sendRedirect("index.jsp");
	
%>

</body>
</html>
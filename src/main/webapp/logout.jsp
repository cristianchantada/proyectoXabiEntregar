<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cristian.carrito.*"%>
<%@page import="jakarta.servlet.http.Cookie"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Logout</title>
<link rel="stylesheet" href="styles/style.css">
</head>
<body>
<% 
	session.removeAttribute("login");
	Cookie cookie = new Cookie("userEmail", ""); 
	cookie.setMaxAge(0);
	response.addCookie(cookie);
	response.sendRedirect("index.jsp");
%>
</body>
</html>
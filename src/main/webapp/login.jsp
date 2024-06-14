<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.time.LocalTime"%>
<%@ page import="java.time.temporal.ChronoUnit"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cristian.carrito.models.*"%>
<%@ page import="com.cristian.carrito.controllers.*"%>
<%@ page import="java.io.IOException"%>
<%@page import="javax.swing.SwingConstants"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="jakarta.servlet.http.Cookie"%>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="styles/style.css">
<title>Login</title>
</head>
<body>

	<%
	boolean showWrongAccess = false;
	boolean showLimitAccess = false;

	if (request.getMethod().equals("POST") && request.getParameter("password") != null) {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Cliente cliente = new Cliente();
		ClienteDao clienteDao = new ClienteDao();
		cliente = clienteDao.getClientByEmail(email);
				
		LocalTime lastAccessTime = cliente.getAccessTime();
		long minutesDifference;
		
		if(lastAccessTime != null){
			minutesDifference = lastAccessTime.until(LocalTime.now(), ChronoUnit.SECONDS);
		} else {
			minutesDifference = 0;
		}

		int accessCounter = 0;
		// Si han pasado más de 5 minutos desde el último acceso fallido,
		// el contador de entradas fallidas se setea a cero siempre.
		if(minutesDifference > 120){		
			accessCounter = 0;
			cliente.setAccessCounter(0);
		}
	
		if (cliente.getAccessCounter() >= 5 && minutesDifference < 120) {
			showLimitAccess = true;
			accessCounter = 5;
		} else if (password.equals(cliente.getPassword())) {			
						
			session.setAttribute("login", "true");
			session.setMaxInactiveInterval(120);
			
			Cookie cookie = new Cookie("userEmail", cliente.getEmail()); 
			cookie.setMaxAge(120);
			response.addCookie(cookie);
			response.sendRedirect("index.jsp");			
			// Siempre que entremos correctamente a la web, el contador de accesos se setea a cero
			accessCounter = 0;
		} else {
			showWrongAccess = true;
			accessCounter = cliente.getAccessCounter() + 1;
			ClienteDao clienteDao2 = new ClienteDao();
			clienteDao2.updateClientAccessTime(LocalTime.now(),  cliente.getNif());
		}
		ClienteDao clienteDao3 = new ClienteDao();
		clienteDao3.updateAccessCounter(accessCounter, cliente.getNif());
	}
	
	%>
	<div class="main-page-container login-form-container">
		<form action="login.jsp" method="POST">
			<label for="email">Correo electrónico</label> <input type="email"
				id="email" name="email" required /> <label for="">Contraseña</label>
			<input type="password" id="password" name="password" required />
			<button type="submit">Iniciar sesión</button>
		</form>
		<a href="index.jsp" >Volver</a>
	</div>
	
	<%
	if (showLimitAccess) {
	%>
	<p style="color: red;">Se ha alcanzado el límite de intentos, debes esperar al menos 2 minutos para volver a intentarlo</p>

	<%
	} else if (showWrongAccess) {
	%>
	<p style="color: red;">Email o contraseña incorrecta, por favor, inténtelo de nuevo</p>

	<%
	}
	%>
</body>
</html>

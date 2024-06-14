<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insercción masiva</title>
</head>
<body>
	<h1>Insercción masiva de archivos</h1>
	<form action="/CarritoDeCompra/insert-all" method="POST">
		<textarea rows="30" cols="70" name="products"></textarea>
		<input type="submit" value="Insertar productos">
	</form>
</body>
</html>
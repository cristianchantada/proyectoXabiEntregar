<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Random"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.cristian.carrito.models.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Xogos Tradicionais Galegos</title>
<link rel="stylesheet" href="styles/style.css">
</head>
<body>
	<header>
		<h1>Xogos Tradicionais Galegos</h1>

		<%
		List<Producto> listaProductos = (List<Producto>) request.getAttribute("listaProductos");

		int productsPerPage = (int) request.getAttribute("productsPerPage");
		int lastProductPage = (int) request.getAttribute("theLastProduct");
		int firstProductPage = (int) request.getAttribute("theFirstProduct");
		String orden = (String) request.getAttribute("orden");
		boolean isTheLast = (boolean) request.getAttribute("isTheLast");

		if (session.getAttribute("login") == null) {
		%>
		<nav>
			<div>
				<a class="massive-insertion" href="/CarritoDeCompra/insert-all">Ir
					a insercción masiva de productos</a>
			</div>
			<form action="login.jsp" method="post">
				<input type="submit" value="login">
			</form>
			<%
			} else {
			%>
			<form action="logout.jsp" method="post" class="logout-form">
				<input type="submit" value="logout">
			</form>
			<%
			}
			%>
		</nav>
		<form class="products-per-page-form"
			action="/CarritoDeCompra/get-n-products" method="POST">
			<div class="selects-a-input-form-container">
				<div class="products-per-page-and-a-order">
					<div class="per-page-container">
						<label for="ProductsPerPage">Productos por página:</label> <select
							name="ProductsPerPage">
							<option value="10" <%=productsPerPage == 10 ? "selected" : ""%>>10</option>
							<option value="25" <%=productsPerPage == 20 ? "selected" : ""%>>20</option>
							<option value="50" <%=productsPerPage == 50 ? "selected" : ""%>>50</option>
							<option value="100" <%=productsPerPage == 100 ? "selected" : ""%>>100</option>
						</select>
					</div>
					<div class="order-by-container">
						<p>ordenar:</p>
						<a class="a-button"
							href="<%=request.getContextPath()%>/get-n-products?orden=ASC&productsPerPage=<%=productsPerPage%>">precio
							asc</a> <a class="a-button"
							href="<%=request.getContextPath()%>/get-n-products?orden=DESC&productsPerPage=<%=productsPerPage%>">precio
							desc</a> <a class="a-button"
							href="<%=request.getContextPath()%>/get-n-products?orden=destacado&productsPerPage=<%=productsPerPage%>">destacados</a>
					</div>
				</div>
				<div class="prev-next-form">
					<%
					if (firstProductPage > 0) {
					%>
					<input type="submit" name="prev" value="<< Previous">
					<%
					}
					if (!isTheLast) {
					%>
					<input type="submit" name="next" value="Next >>">
					<%
					}
					%>
				</div>
			</div>
			<input type="hidden" name="theLastProduct"
				value="<%=lastProductPage%>"> <input type="hidden"
				name="theFirstProduct" value="<%=firstProductPage%>"> <input
				type="hidden" name="orden" value="<%=orden%>">
		</form>
		</div>
	</header>
	<main>
		<div class="container">
			<div class="products-container">
				<%
				Catalogo catalogo = new Catalogo();
				for (Producto producto : listaProductos) {
					/* Borrar Random cuando tengamos todas las fotos */
					Random rand = new Random();
					int randomNumber = rand.nextInt(8 - 1 + 1) + 1;
				%>
				<div class="product-card">
					<div class="product-image">
						<img src="imgs/<%=/*producto.getCodigo()*/randomNumber%>.png"
							alt="<%=producto.getNombre()%>">
					</div>
					<div class="product-details">
						<h4><%=producto.getNombre()%></h4>
						<p><%=producto.getPrecio()%>€
						</p>
						<p><a class="a-button" href="<%=request.getContextPath()%>/descripcion?id=<%= producto.getCodigo()%>" >descripción</a>
						</p>
						<%
						if (producto.isDestacado()) {
						%>
						<p class="destacado">&#9733; DESTACADO &#9733;</p>

						<%
						}
						%>

						<%
						int ratingProducto = (int) Producto.roundToNearest(producto.getRating());
						String estrellitas = "";
						for (int i = 0; i < ratingProducto; i++) {
							estrellitas += "&#9733;";
						}
						if (producto.getVotos() > 0) {
						%>

						<p>rating:</p>
						<p class="destacado estrellas"><%=estrellitas%>
						</p>


						<%
						}
						if (session.getAttribute("login") != null) {
						%>
						<div class="starts-container">
							<form action="<%=request.getContextPath()%>/votar" method="post">

								<select class="destacado estrellas" name="stars">
									<option class="destacado estrellas" value="1">&#9733;</option>
									<option class="destacado estrellas" value="2">&#9733;&#9733;</option>
									<option class="destacado estrellas" value="3">&#9733;&#9733;&#9733;</option>
									<option class="destacado estrellas" value="4">&#9733;&#9733;&#9733;&#9733;</option>
									<option class="destacado estrellas" value="5">&#9733;&#9733;&#9733;&#9733;&#9733;</option>
								</select> <input type="submit" value="votar"> <input
									type="hidden" name="codigo" value="<%=producto.getCodigo()%>">
								<input type="hidden" name="theLastProduct"
									value="<%=lastProductPage%>"> <input type="hidden"
									name="theFirstProduct" value="<%=firstProductPage%>"> <input
									type="hidden" name="orden" value="<%=orden%>">
							</form>
						</div>
						<a
							href="comprar?codigo=<%=producto.getCodigo()%>&ProductsPerPage=<%=productsPerPage%>&theFirstProduct=<%=firstProductPage%>&theLastProduct=<%=lastProductPage%>"
							class="btn">Comprar</a>
						<%}%>
					</div>
				</div>
				<%
				}
				%>
			</div>
			<%
			if (session.getAttribute("login") != null) {
			%>

			<div class="cart-container">
				<h3>Carrito de la compra</h3>
				<%
				Carrito carrito;
				if (session.getAttribute("carrito") != null) {
					carrito = (Carrito) session.getAttribute("carrito");
				} else {
					carrito = new Carrito();
					session.setAttribute("carrito", carrito);
				}
				List<ElementoDeCarrito> elementosCarrito = carrito.getElementosCarrito();
				double total = 0;
				for (ElementoDeCarrito e : elementosCarrito) {
					total += e.getProducto().getPrecio() * e.getCantidad();

					/* Borrar Random cuando tengamos todas las fotos */
					Random rand = new Random();
					int randomNumber = rand.nextInt(8 - 1 + 1) + 1;
				%>
				<div class="cart-total">
					<h4>
						Total:
						<%=total%>€
					</h4>
				</div>
				<div class="cart-item">
					<div class="cart-product-image-container">
						<img src="imgs/<%=/*producto.getCodigo()*/randomNumber%>.png"
							alt="<%=e.getProducto().getNombre()%>">
					</div>
					<div class="cart-item-details">
						<h4><%=e.getProducto().getNombre()%></h4>
						<p>
							Precio:
							<%=e.getProducto().getPrecio()%>€
						</p>
						<p>
							Cantidad:
							<%=e.getCantidad()%></p>
						<p>
							Total:
							<%=e.getProducto().getPrecio() * e.getCantidad()%>€
						</p>
						<%
						if (e.getProducto().isDestacado()) {
						%>
						<p class="destacado">&#9733; DESTACADO &#9733;</p>
						<%
						}
						%>
						<a href="eliminar.jsp?codigo=<%=e.getProducto().getCodigo()%>">Eliminar
							unidad</a>
					</div>
				</div>
				<%
				}
				%>
				<form action="pagar.jsp" method="post" id="carritoForm">
					<!-- Otros campos del formulario -->
					<%
					if (session.getAttribute("login") != null) {
						List<ElementoDeCarrito> elementosCarritoPagar = carrito.getElementosCarrito();
						for (ElementoDeCarrito e : elementosCarritoPagar) {
					%>
					<input type="hidden" name="productosCarrito[]"
						value="<%=e.getProducto().getCodigo()%>"> <input
						type="hidden" name="cantidades[]" value="<%=e.getCantidad()%>">			
					<%
					}
					
					if(!elementosCarritoPagar.isEmpty()){
					%>
					<div class="cart-pay">
						<input type="submit"  value="Pagar" mclass="checkout-btn">
					</div>
					<%}}
					%>

				</form>
			</div>
			<%
			}
			%>
		</div>

	</main>
</body>
</html>

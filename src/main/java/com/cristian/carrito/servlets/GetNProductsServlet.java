package com.cristian.carrito.servlets;

import java.io.IOException;
import java.util.List;

import com.cristian.carrito.controllers.ProductoDao;
import com.cristian.carrito.models.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/get-n-products")
public class GetNProductsServlet extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String orden = req.getParameter("orden");
		req.setAttribute("orden", orden);
		int productsPerPage = Integer.parseInt(req.getParameter("productsPerPage"));
		req.setAttribute("productsPerPage", productsPerPage);

		ProductoDao productoDao = new ProductoDao();
		List<Producto> listaProductos = productoDao.getNProductsOrderBy(0, 10 + 1, orden); 
		
		if(listaProductos.size() < productsPerPage + 1) {
			req.setAttribute("isTheLast", true);
		} else {
			listaProductos.remove(listaProductos.size() - 1);
			req.setAttribute("isTheLast", false);
		}
		
		req.setAttribute("listaProductos", listaProductos);
		req.setAttribute("theLastProduct", 9);
		req.setAttribute("theFirstProduct", 0);
		req.setAttribute("isTheLast", false);
		getServletContext().getRequestDispatcher("/tienda.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String productsPerPageStr = req.getParameter("ProductsPerPage");
		String prev = req.getParameter("prev");
		String next = req.getParameter("next");
		String orden = req.getParameter("orden");
		req.setAttribute("orden", orden);

		int theFirstProductInPreviousPage = Integer.parseInt(req.getParameter("theFirstProduct"));
		int theLastProductInPreviousPage = Integer.parseInt(req.getParameter("theLastProduct"));
		
		int firstProductNow = 0;
		int productsPerPage = 0;
		
		try {
			productsPerPage = Integer.parseInt(productsPerPageStr);
		} catch (NumberFormatException e) {
			ProductoDao productDao = new ProductoDao();
			List<Producto> listaProductos = productDao.getAll();
			req.setAttribute("theLastProduct", 0);
			req.setAttribute("listaProductos", listaProductos);
			req.setAttribute("productsPerPage", 999999);
			req.setAttribute("theFirstProduct", 0);
			req.setAttribute("isTheLast", true);
			req.getRequestDispatcher("/tienda.jsp").forward(req, resp);
		}

		if (prev != null) {
			req.setAttribute("theLastProduct", theLastProductInPreviousPage - productsPerPage);			
			firstProductNow = theFirstProductInPreviousPage - productsPerPage;
			if(firstProductNow <= 0) {
				firstProductNow = 0;
				req.setAttribute("theLastProduct", productsPerPage);
			}
		} else if (next != null) {
			req.setAttribute("theLastProduct", theLastProductInPreviousPage + productsPerPage);
			firstProductNow = theLastProductInPreviousPage + 1;
		}

		ProductoDao productDao = new ProductoDao();
		List<Producto> listaProductos = productDao.getNProductsOrderBy(firstProductNow, productsPerPage + 1, orden);
		
		if(listaProductos.size() < productsPerPage + 1) {
			req.setAttribute("isTheLast", true);
		} else {
			listaProductos.remove(listaProductos.size() - 1);
			req.setAttribute("isTheLast", false);
		}
		
		req.setAttribute("listaProductos", listaProductos);
		req.setAttribute("productsPerPage", productsPerPage);
		req.setAttribute("theFirstProduct", firstProductNow);
		getServletContext().getRequestDispatcher("/tienda.jsp").forward(req, resp);
	}

}

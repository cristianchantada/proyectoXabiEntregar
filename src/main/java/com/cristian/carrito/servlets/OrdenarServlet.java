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

@WebServlet("/prdenar")
public class OrdenarServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String orden = req.getParameter("orden");
		int productsPerPage = Integer.parseInt(req.getParameter("productsPerPage"));
		
		ProductoDao productoDao = new ProductoDao();
		List<Producto> listaProductos = productoDao.getNProductsOrderBy(0, productsPerPage + 1, orden);
		 		
		req.setAttribute("orden", orden);
		req.setAttribute("listaProductos", listaProductos);
		getServletContext().getRequestDispatcher("/tienda.jsp").forward(req, resp);
	}
}

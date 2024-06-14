package com.cristian.carrito.servlets;

import java.io.IOException;
import java.util.List;
import java.lang.NumberFormatException;

import com.cristian.carrito.controllers.ProductoDao;
import com.cristian.carrito.models.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet({"/index", "/index.html", "/index.jsp"})
public class indexServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		ProductoDao productoDao = new ProductoDao();
		
		String orden = req.getParameter("orden");
		String productsPerPageStr = req.getParameter("productsPerPage");
		
		if(orden == null) {
			orden = "id";
		}
		
		int productsPerPage;
		if(productsPerPageStr != null) {
			try {
				productsPerPage = Integer.parseInt(productsPerPageStr);
			} catch(NumberFormatException e) {
				productsPerPage = 10;
			}
		} else {
			productsPerPage = 10;
		}
		
		
		List<Producto> listaProductos = productoDao.getNProductsOrderBy(0, productsPerPage + 1, orden); 
		
		if(listaProductos.size() < productsPerPage + 1) {
			req.setAttribute("isTheLast", true);
		} else {
			listaProductos.remove(listaProductos.size() - 1);
			req.setAttribute("isTheLast", false);
		}
		
		req.setAttribute("orden", "id");
		req.setAttribute("listaProductos", listaProductos);
		req.setAttribute("theLastProduct", 9);
		req.setAttribute("theFirstProduct", 0);
		req.setAttribute("productsPerPage", 10);
		
		req.getRequestDispatcher("/tienda.jsp").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductoDao productoDao = new ProductoDao();
		
		String orden = req.getParameter("orden");
		String productsPerPageStr = req.getParameter("productsPerPage");
		
		if(orden == null) {
			orden = "id";
		}
		
		int productsPerPage;
		if(productsPerPageStr != null) {
			try {
				productsPerPage = Integer.parseInt(productsPerPageStr);
			} catch(NumberFormatException e) {
				productsPerPage = 10;
			}
		} else {
			productsPerPage = 10;
		}
		
		
		List<Producto> listaProductos = productoDao.getNProductsOrderBy(0, productsPerPage + 1, orden); 
		
		if(listaProductos.size() < productsPerPage + 1) {
			req.setAttribute("isTheLast", true);
		} else {
			listaProductos.remove(listaProductos.size() - 1);
			req.setAttribute("isTheLast", false);
		}
		
		req.setAttribute("orden", "id");
		req.setAttribute("listaProductos", listaProductos);
		req.setAttribute("theLastProduct", 9);
		req.setAttribute("theFirstProduct", 0);
		req.setAttribute("productsPerPage", 10);
		
		req.getRequestDispatcher("/tienda.jsp").forward(req, resp);
		
	}
	
}

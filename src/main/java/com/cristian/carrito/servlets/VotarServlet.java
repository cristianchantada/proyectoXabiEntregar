package com.cristian.carrito.servlets;

import java.io.IOException;

import com.cristian.carrito.controllers.ProductoDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/votar")
public class VotarServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int NumEstrellasStr = Integer.parseInt(req.getParameter("stars"));		
		int productoCodigo = Integer.parseInt(req.getParameter("codigo"));
		
		ProductoDao productoDao = new ProductoDao();
		productoDao.sumVoto(NumEstrellasStr, productoCodigo);

		req.setAttribute("orden", req.getParameter("orden"));
		req.setAttribute("productsPerPage", req.getParameter("productsPerPage"));	
		req.setAttribute("theLastProduct", req.getParameter("theLastProduct")); 
		req.setAttribute("firstProductPage", req.getParameter("firstProductPage"));	
		getServletContext().getRequestDispatcher("/index").forward(req, resp);		
	}
	
}

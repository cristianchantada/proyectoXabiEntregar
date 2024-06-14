package com.cristian.carrito.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.cristian.carrito.controllers.ProductoDao;
import com.cristian.carrito.models.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/insert-all")
public class MasiveInsertServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/masive-insert.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String productosStr = (String) req.getParameter("products");
		
		  List<Producto> listaProductos = new ArrayList<>();

	        try (BufferedReader reader = new BufferedReader(new StringReader(productosStr))) {
	            String linea;
	            while ((linea = reader.readLine()) != null) {
	                String[] campos = linea.split(";");
	                
	                String nombre = campos[0];
	                double precio = 0;
	                
	                try {
	                	precio = Double.parseDouble(campos[1]);
	                } catch(NumberFormatException e) {
	                	System.out.println("Error al parsear a doble el precio en la Linea: " + linea);
	                }
	                
	                String imagen = campos[2];
	                
	                Producto newProducto = new Producto();
	                newProducto.setNombre(nombre);
	                newProducto.setPrecio(precio);
	                newProducto.setImg(imagen);
	                
	                if(campos.length >= 4) {
	                	boolean destacado = Boolean.parseBoolean(campos[3]);
	                	newProducto.setDestacado(destacado);
	                }
	                
	                
	                listaProductos.add(newProducto);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        ProductoDao productoDao = new ProductoDao();
	        productoDao.saveAll(listaProductos);
	        
	        resp.sendRedirect(req.getContextPath() + "/index.jsp");
	
	}

}

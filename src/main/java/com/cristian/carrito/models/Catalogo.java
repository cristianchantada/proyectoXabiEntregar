package com.cristian.carrito.models;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
	
	private List<Producto> productos = new ArrayList<>();
	
	public Catalogo() {

	}
	
	public List<Producto> getProductos(){
		return productos;
	}
	
	public Producto productoConCodigo(int codigo) {
		Producto producto = (Producto) productos
				.stream()
				.filter(p -> p.getCodigo() == codigo);
		return producto;
	}
	
}

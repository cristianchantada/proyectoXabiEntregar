package com.cristian.carrito.models;

public class ElementoDeCarrito {
	
	private Producto producto;
	private int cantidad;
	
	public ElementoDeCarrito() {}

	public ElementoDeCarrito(Producto producto, int cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public void incrementaCantidad() {
		cantidad++;
	}
	
	public void decrementarCantidad() {
		cantidad--;
	}



}
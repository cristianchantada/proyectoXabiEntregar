package com.cristian.carrito.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cristian.carrito.controllers.ProductoDao;

public class Carrito {
	
	private List<ElementoDeCarrito> elementosCarrito = new ArrayList<>();
	
	public Carrito(){}
	
	public Carrito(List<ElementoDeCarrito> elementosCarrito) {
		this.elementosCarrito = elementosCarrito;
	}
	
	
	public List<ElementoDeCarrito> getElementosCarrito() {
		return elementosCarrito;
	}

	public void setElementosCarrito(List<ElementoDeCarrito> elementosCarrito) {
		this.elementosCarrito = elementosCarrito;
	}

	public boolean existeElementoEncarrito(int codigo) {
		boolean localizado = false;
		System.out.println("Codigo: " + codigo );
		for(ElementoDeCarrito e : elementosCarrito) {
			System.out.println(".getCodigo: " + e.getProducto().getCodigo());
			
			if(e.getProducto().getCodigo() == codigo) {
				System.out.println("El producto está REPETIDO");

				localizado = true;
			}
		}
		return localizado;
	}
	
	public int posicionElementoCarrito(int codigo) {
		for(int i = 0; i < elementosCarrito.size(); i++) {
			ElementoDeCarrito e = elementosCarrito.get(i);
			if(e.getProducto().getCodigo() == codigo) {
				return i;
			}
		}
		return -1;		
	}
	
	public void meterProducto(int codigo) throws SQLException {
		if(existeElementoEncarrito(codigo)){
			for(ElementoDeCarrito e: elementosCarrito) {
				if(e.getProducto().getCodigo() == codigo) {
					e.setCantidad(e.getCantidad() + 1);	
				}
			}
		} else {
			ProductoDao productoDao = new ProductoDao();
			ElementoDeCarrito elementoCarrito = productoDao.getByid(codigo);
			elementoCarrito.setCantidad(1);
			elementosCarrito.add(elementoCarrito);}
	}		
	
	
	public void eliminarproducto(int codigo) {
		Iterator<ElementoDeCarrito> iterator = elementosCarrito.iterator();
			while (iterator.hasNext()) {
		        ElementoDeCarrito e = iterator.next();
		        if (e.getProducto().getCodigo() == codigo) {
		            if (e.getCantidad() > 1) {
		                e.setCantidad(e.getCantidad() - 1);
		                return;
		            } else {
		            	iterator.remove();
		            	return;
		            }
		        }
		    }
		}  
	
}

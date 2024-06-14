package com.cristian.carrito.models;

public class Producto {
	
	private int codigo;
	private String nombre;
	private double precio;
	private String img;
	private boolean destacado;
	private int votos;
	private int estrellas;
	private float rating;
	
	public Producto(){}
	
	public Producto(int codigo) {
		this.codigo = codigo;
	}
	
	
	public Producto(int codigo, String nombre, double precio, String img) {
		this(codigo);
		this.nombre = nombre;
		this.precio = precio;
		this.img = img;
	}
	
	public Producto(int codigo, String nombre, double precio, String img, boolean destacado) {
		this(codigo, nombre, precio, img );
		this.destacado = destacado;
	}


	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public boolean isDestacado() {
		return destacado;
	}

	public void setDestacado(boolean destacado) {
		this.destacado = destacado;
	}
	


	public int getVotos() {
		return votos;
	}


	public void setVotos(int votos) {
		this.votos = votos;
	}


	public int getEstrellas() {
		return estrellas;
	}


	public void setEstrellas(int estrellas) {
		this.estrellas = estrellas;
	}


	public float getRating() {
		return rating;
	}


	public void setRating(float rating) {
		this.rating = rating;
	}
	
	public static double roundToNearest(float numero) {
		
		double numeroDouble = Double.valueOf(numero);
        double parteEntera = Math.floor(numeroDouble);
        double decimal = numero - parteEntera;
        // Redondear hacia arriba si el decimal es 0.5 o más
        if (decimal >= 0.5) {
            return parteEntera + 1;
        } else { // Redondear hacia abajo si el decimal es menor que 0.5
            return parteEntera;
        }
    }

	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Producto producto = (Producto) obj;
        return nombre == producto.nombre;
    }
	
}

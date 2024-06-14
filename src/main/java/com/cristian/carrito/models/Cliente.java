package com.cristian.carrito.models;

import java.time.LocalTime;
import java.util.List;

public class Cliente {

	private String nif;
	private String nombre;
	private String email;
	private String telefono;
	private String password;
	private LocalTime accessTime;
	private int accessCounter;

	public Cliente() {
	}

	public Cliente(String nif) {
		this.nif = nif;
	}

	public Cliente(String nif, String nombre, String telefono, String password) {
		this(nif);
		this.nombre = nombre;
		this.password = password;
		this.telefono = telefono;
	}
	
	public Cliente(String nif, String nombre, String telefono, String password, int accessCounter) {
		this(nif, nombre, telefono, password);
		this.accessCounter = accessCounter;
	}
	
	public Cliente(String nif, String nombre, String telefono, String password, int accessCounter, LocalTime accessTime) {
		this(nif, nombre, telefono, password, accessCounter);
		this.accessTime = accessTime;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getCorreo() {
		return email;
	}

	public void setCorreo(String correo) {
		this.email = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalTime getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(LocalTime accessTime) {
		this.accessTime = accessTime;
	}
	
	

	public int getAccessCounter() {
		return accessCounter;
	}

	public void setAccessCounter(int accessCounter) {
		this.accessCounter = accessCounter;
	}

	@Override
	public String toString() {
		return "El cliente ha sido creado con los siguientes datos:" + "\n\tNombre: " + nombre + "\n\tNIF: " + nif
				+ "\n\tTeléfono: " + telefono + "\n\tCorreo electrónico: " + email;
	}

	public static boolean clienteExiste(String nif, List<Cliente> lc) {
		if (lc != null) {
			for (Cliente c : lc) {
				if (nif.equals(c.nif))
					return true;
			}
			return false;
		}
		return false;
	}
	
	public static String clienteExisteNifReturn(String nif, List<Cliente> lc) {
		if (lc != null) {
			for (Cliente c : lc) {
				if (nif.equals(c.nif))
					return nif;
			}
			return "";
		}
		return "";
	}

}

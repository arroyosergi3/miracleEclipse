package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	private String apellido;

	private String contrasena;

	private String email;

	@Id
	private int id;

	private String nombre;

	private String pais;

	private String rol;

	private String sexo;

	
	public Usuario() {
		super();
	}

	

	public Usuario(int id, String apellido, String contrasena, String email, String nombre, String pais, String rol,
			String sexo) {
		super();
		this.apellido = apellido;
		this.contrasena = contrasena;
		this.email = email;
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
		this.rol = rol;
		this.sexo = sexo;
	}



	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContraseña(String contraseña) {
		this.contrasena = contraseña;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Object getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}
package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

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
	@Id
	private int id;

	private String nombre;
	private String apellido;
	private String email;
	private String rol;
	private String contrasena;
	private String pais;
	private String sexo;
	
	 @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Alquilere> alquileres;

	
	public Usuario() {
		super();
	}

	

	public Usuario(String nombre, String apellido, String email, String sexo, String pais, String contrasena, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.sexo = sexo;
        this.pais = pais;
        this.contrasena = contrasena;
        this.rol = rol;
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
	
	public List<Alquilere> getAlquileres() {
        return alquileres;
    }

    public void setAlquileres(List<Alquilere> alquileres) {
        this.alquileres = alquileres;
    }
    

}
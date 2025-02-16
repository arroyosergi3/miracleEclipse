package com.example.demo.model;

import java.io.Serializable;
import java.util.List;


import jakarta.persistence.*;


/**
 * The persistent class for the marcas database table.
 * 
 */
@Entity
@Table(name="marcas")
@NamedQuery(name="Marca.findAll", query="SELECT m FROM Marca m")
public class Marca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nombre;

	//bi-directional many-to-one association to Usuaria
		@OneToMany(mappedBy="marca")
		private List<Producto> productos;
		
	public Marca() {
	}
	
	

	public Marca(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	public Marca( String nombre) {
		this.nombre = nombre;
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
	
	public List<Producto> getProductos() {
		return this.productos;
	}

	public void setUsuarias(List<Producto> usuarias) {
		this.productos = usuarias;
	}

	public Producto addUsuaria(Producto usuaria) {
		getProductos().add(usuaria);
		usuaria.setMarca(this);

		return usuaria;
	}

	public Producto removeUsuaria(Producto usuaria) {
		getProductos().remove(usuaria);
		usuaria.setMarca(null);

		return usuaria;
	}
	

}
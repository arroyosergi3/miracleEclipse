package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the productos database table.
 * 
 */
@Entity
@Table(name="productos")
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	@Lob
	private String descripcion;
	private boolean estado;
	@ManyToOne
	@JoinColumn(name="id_marca")
	private Marca marca;
	private String nombre;
	private BigDecimal precio;
	private String ruta;
	
	 @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Alquilere> alquileres;

	public Producto() {
	}
	
	
	
	

	public Producto(int id, String nombre,BigDecimal precio, boolean estado2, String descripcion, Marca marca,  String ruta) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.estado = estado2;
		this.marca = marca;
		this.nombre = nombre;
		this.precio = precio;
		this.ruta = ruta;
	}





	public Producto(String nombre2, BigDecimal precio2, boolean estado2, String descripcion2, Marca byId,
			String ruta2) {
		super();
		this.descripcion = descripcion2;
		this.estado = estado2;
		this.marca = byId;
		this.nombre = nombre2;
		this.precio = precio2;
		this.ruta = ruta2;
		
	}





	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}


	public Marca getMarca() {
		return this.marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	public List<Alquilere> getAlquileres() {
        return alquileres;
    }

    public void setAlquileres(List<Alquilere> alquileres) {
        this.alquileres = alquileres;
    }
    
	

}
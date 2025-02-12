package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;


/**
 * The persistent class for the alquileres database table.
 * 
 */
@Entity
@Table(name="alquileres")
@NamedQuery(name="Alquilere.findAll", query="SELECT a FROM Alquilere a")
public class Alquilere implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="fecha_fin")
	private Date fechaFin;

	@Column(name="fecha_inicio")
	private Date fechaInicio;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)  // Clave foránea a Usuario
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", nullable = false)  // Clave foránea a Producto
    private Producto producto;

	public Alquilere() {
	}

	

	public Alquilere(int id, Usuario idUsuario,  Producto idProducto, Date fechaInicio, Date fechaFin ) {
		super();
		this.id = id;
		this.fechaFin = fechaFin;
		this.fechaInicio = fechaInicio;
		this.producto = idProducto;
		this.usuario = idUsuario;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto idProducto) {
		this.producto = idProducto;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario idUsuario) {
		this.usuario = idUsuario;
	}

}
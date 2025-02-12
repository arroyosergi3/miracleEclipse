package com.example.demo.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Marca;
import com.example.demo.model.Producto;
import com.example.demo.repository.marcaRepository;
import com.example.demo.repository.productoRepository;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/productos")
public class ProductoController {
	@Autowired
	productoRepository proRep;
	@Autowired
	marcaRepository marca;
	
	@GetMapping("/obtener")
	public List<DTO> getProductos() { 
		List<DTO> listaUsariosDTO = new ArrayList<>();
		List<Producto> productos = proRep.findAll();
		for (Producto p : productos) {
			DTO dtoProducto = new DTO();
			dtoProducto.put("id", p.getId());
			dtoProducto.put("nombre", p.getNombre());
			dtoProducto.put("precio", p.getPrecio());
			dtoProducto.put("estado", p.getEstado());
			dtoProducto.put("descripcion", p.getDescripcion());
			dtoProducto.put("marca", p.getMarca().getId());
			dtoProducto.put("ruta", p.getRuta());
			listaUsariosDTO.add(dtoProducto);
		}

		return listaUsariosDTO;
	}

	// CAMBIAR POR ID's		
	@PostMapping("/obtenerPorId")
	public List<DTO> getProductosFiltrado(int id) { 
		List<DTO> listaUsariosDTO = new ArrayList<>();
		List<Producto> productos = proRep.findAll();
		for (Producto p : productos) {
			DTO dtoProducto = new DTO();
			dtoProducto.put("id", p.getId());
			dtoProducto.put("nombre", p.getNombre());
			dtoProducto.put("precio", p.getPrecio());
			dtoProducto.put("estado", p.getEstado());
			dtoProducto.put("descripcion", p.getDescripcion());
			dtoProducto.put("marca", p.getMarca().getId());
			dtoProducto.put("ruta", p.getRuta());
			listaUsariosDTO.add(dtoProducto);
		}

		return listaUsariosDTO;
	}

	@PostMapping(path = "/obtener1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO getProducto(@RequestBody DTO soloid, HttpServletRequest request) {
		DTO dtoProducto = new DTO();
		Producto p = proRep.findById(Integer.parseInt(soloid.get("id").toString()));
		if (p != null) {
			dtoProducto.put("id", p.getId());
			dtoProducto.put("nombre", p.getNombre());
			dtoProducto.put("precio", p.getPrecio());
			dtoProducto.put("estado", p.getEstado());
			dtoProducto.put("descripcion", p.getDescripcion());
			dtoProducto.put("marca", p.getMarca().getId());
			dtoProducto.put("ruta", p.getRuta());

		} else {
			dtoProducto.put("Result", "fail");
		}
		return dtoProducto;
	}

	
	@PostMapping(path = "/borrar1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO deleteUsuario(@RequestBody DTO soloid, HttpServletRequest request) {
		DTO dtoUsuaria = new DTO();
		Producto u = proRep.findById(Integer.parseInt(soloid.get("id").toString()));
		if (u != null) {
			proRep.delete(u);
			dtoUsuaria.put("borrado", "success");
		} else {
			dtoUsuaria.put("borrado", "fail");
		}
		return dtoUsuaria;
	}
	
	@PostMapping(path = "/anadirnuevo")
	public void anadirUsuario(@RequestBody DatosAltaProducto p, HttpServletRequest request) {
		proRep.save(new Producto(p.id,p.nombre,p.precio ,p.estado , p.descripcion , marca.findById(p.marca),p.ruta));
	}

	
	
	@PostMapping(path = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO actualizarProducto(@RequestBody DatosAltaProducto datos, HttpServletRequest request) {
	    DTO response = new DTO();
	    try {
	        Producto productoExistente = proRep.findById(datos.id);

	        if (productoExistente == null) {
	            response.put("status", "fail");
	            response.put("message", "Producto no encontrado");
	            return response;
	        }

	        Marca marcaProducto = marca.findById(datos.marca);
	        if (marcaProducto == null) {
	            response.put("status", "fail");
	            response.put("message", "Marca no encontrada");
	            return response;
	        }

	        // Actualizar los valores del producto existente
	        productoExistente.setNombre(datos.nombre);
	        productoExistente.setPrecio(datos.precio);
	        productoExistente.setEstado(datos.estado);
	        productoExistente.setDescripcion(datos.descripcion);
	        productoExistente.setMarca(marcaProducto);
	        productoExistente.setRuta(datos.ruta);

	        // Guardar los cambios
	        proRep.save(productoExistente);

	        response.put("status", "success");
	        response.put("message", "Producto actualizado correctamente");
	    } catch (Exception e) {
	        response.put("status", "fail");
	        response.put("message", "Error al actualizar el producto: " + e.getMessage());
	    }
	    return response;
	}

	

	static class DatosAltaProducto {
		 int id;
		 String descripcion;
		 byte estado;
		 int marca;
		 String nombre;
		 BigDecimal precio;
		 String ruta;
		public DatosAltaProducto(int id, String descripcion, byte estado, int marca, String nombre,
				BigDecimal precio, String ruta) {
			super();
			this.id = id;
			this.descripcion = descripcion;
			this.estado = estado;
			this.marca = marca;
			this.nombre = nombre;
			this.precio = precio;
			this.ruta = ruta;
		}
		 
		

	}
}
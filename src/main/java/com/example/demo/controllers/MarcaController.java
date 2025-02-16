package com.example.demo.controllers;

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
import com.example.demo.repository.marcaRepository;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/marcas")
public class MarcaController {
	
	@Autowired
	marcaRepository marRep;
	
	@GetMapping("/obtener")
	public List<DTO> getProductos() { 
		List<DTO> listaUsariosDTO = new ArrayList<>();
		List<Marca> productos = marRep.findAll();
		for (Marca p : productos) {
			DTO dtoProducto = new DTO();
			dtoProducto.put("id", p.getId());
			dtoProducto.put("nombre", p.getNombre());
			listaUsariosDTO.add(dtoProducto);
		}

		return listaUsariosDTO;
	}

	// CAMBIAR POR ID's		
	@PostMapping("/obtenerPorId")
	public List<DTO> getProductosFiltrado(int id) { 
		List<DTO> listaUsariosDTO = new ArrayList<>();
		List<Marca> productos = marRep.findAll();
		for (Marca p : productos) {
			DTO dtoProducto = new DTO();
			dtoProducto.put("id", p.getId());
			dtoProducto.put("nombre", p.getNombre());
			listaUsariosDTO.add(dtoProducto);
		}

		return listaUsariosDTO;
	}

	@PostMapping(path = "/obtener1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO getProducto(@RequestBody DTO soloid, HttpServletRequest request) {
		DTO dtoProducto = new DTO();
		Marca p = marRep.findById(Integer.parseInt(soloid.get("id").toString()));
		if (p != null) {
			dtoProducto.put("id", p.getId());
			dtoProducto.put("nombre", p.getNombre());

		} else {
			dtoProducto.put("Result", "fail");
		}
		return dtoProducto;
	}

	
	@PostMapping(path = "/borrar1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO deleteUsuario(@RequestBody DTO soloid, HttpServletRequest request) {
		DTO dtoUsuaria = new DTO();
		Marca u = marRep.findById(Integer.parseInt(soloid.get("id").toString()));
		if (u != null) {
			marRep.delete(u);
			dtoUsuaria.put("borrado", "success");
		} else {
			dtoUsuaria.put("borrado", "fail");
		}
		return dtoUsuaria;
	}
	
	@PostMapping(path = "/anadirnuevo")
	public void anadirUsuario(@RequestBody DatosAltaMarca p, HttpServletRequest request) {
		marRep.save(new Marca(p.nombre));	    
	}

	@PostMapping(path = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO actualizarMarca(@RequestBody DatosAltaMarca datos, HttpServletRequest request) {
	    DTO response = new DTO();
	    try {
	        Marca marcaExistente = marRep.findById(datos.id);

	        if (marcaExistente == null) {
	            response.put("status", "fail");
	            response.put("message", "Marca no encontrada");
	            return response;
	        }

	        // Actualizar los valores de la marca existente
	        marcaExistente.setNombre(datos.nombre);

	        // Guardar los cambios
	        marRep.save(marcaExistente);

	        response.put("status", "success");
	        response.put("message", "Marca actualizada correctamente");
	    } catch (Exception e) {
	        response.put("status", "fail");
	        response.put("message", "Error al actualizar la marca: " + e.getMessage());
	    }
	    return response;
	}


	

	static class DatosAltaMarca {
		int id;
		 String nombre;
		 public DatosAltaMarca(int id, String nombre) {
				super();
				this.id = id;
				this.nombre = nombre;
			}
		 public DatosAltaMarca( String nombre) {
				super();
				this.nombre = nombre;
			}
		
		 
		

	}
}
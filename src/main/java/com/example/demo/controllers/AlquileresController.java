package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Alquilere;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.alquilereRepository;
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/alquileres")
public class AlquileresController {
	@Autowired
	alquilereRepository alRep;
	@GetMapping("/obtener")
	public List<DTO> getProductos() {
		List<DTO> listaUsariosDTO = new ArrayList<>();
		List<Alquilere> alquileres = alRep.findAll();
		for (Alquilere p : alquileres) {
			DTO dtoProducto = new DTO();
			dtoProducto.put("id", p.getId());
			dtoProducto.put("id_usuario", p.getUsuario());
			dtoProducto.put("id_producto", p.getProducto());
			dtoProducto.put("fecha_inicio", p.getFechaInicio());
			dtoProducto.put("fecha_fin", p.getFechaFin());
			
			listaUsariosDTO.add(dtoProducto);
		}

		return listaUsariosDTO;
	}
	
	@PostMapping(path = "/misProductos", consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<DTO> getMisProductos(@RequestBody DTO soloid, HttpServletRequest request) {
		List<DTO> listaUsariosDTO = new ArrayList<>();
		List<Alquilere> alquileres = alRep.findByUsuario_Id(Integer.parseInt(soloid.get("id_usuario").toString()));
		for (Alquilere p : alquileres) {
				
				DTO dtoProducto = new DTO();
				dtoProducto.put("id", p.getId());
				dtoProducto.put("id_usuario", p.getUsuario().getId());
				dtoProducto.put("id_producto", p.getProducto().getId());
				dtoProducto.put("fecha_inicio", p.getFechaInicio());
				dtoProducto.put("fecha_fin", p.getFechaFin());
				
				listaUsariosDTO.add(dtoProducto);
			}
			
		

		return listaUsariosDTO;
	}


	@PostMapping(path = "/obtener1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO getProducto(@RequestBody DTO soloid, HttpServletRequest request) {
		DTO dtoProducto = new DTO();
		Alquilere p = alRep.findById(Integer.parseInt(soloid.get("id").toString()));
		if (p != null) {
			dtoProducto.put("id", p.getId());
			dtoProducto.put("id_usuario", p.getUsuario());
			dtoProducto.put("id_producto", p.getProducto());
			dtoProducto.put("fecha_inicio", p.getFechaInicio());
			dtoProducto.put("fecha_fin", p.getFechaFin());

		} else {
			dtoProducto.put("Result", "fail");
		}
		return dtoProducto;
	}

	
	@PostMapping(path = "/borrar1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO deleteUsuario(@RequestBody DTO soloid, HttpServletRequest request) {
		DTO dtoUsuaria = new DTO();
		Alquilere u = alRep.findById(Integer.parseInt(soloid.get("id").toString()));
		if (u != null) {
			alRep.delete(u);
			dtoUsuaria.put("borrado", "success");
		} else {
			dtoUsuaria.put("borrado", "fail");
		}
		return dtoUsuaria;
	}
	
	@PostMapping(path = "/anadirnuevo")
	public void anadirUsuario(@RequestBody DatosAltaAlquiler p, HttpServletRequest request) {
		alRep.save(new Alquilere(p.id, p.id_usuario ,p.id_producto, p.fecha_inicio,p.fecha_fin));
	}

	

	static class DatosAltaAlquiler {
		 int id;
		 Usuario id_usuario;
		 Producto id_producto;
		 Date fecha_inicio;
		 Date fecha_fin;
		public DatosAltaAlquiler(int id, Usuario id_usuario, Producto id_producto, Date fecha_inicio, Date fecha_fin) {
			super();
			this.id = id;
			this.id_usuario = id_usuario;
			this.id_producto = id_producto;
			this.fecha_inicio = fecha_inicio;
			this.fecha_fin = fecha_fin;
		}
		 
		

	}
}
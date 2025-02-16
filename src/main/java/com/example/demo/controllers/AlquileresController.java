package com.example.demo.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.example.demo.repository.productoRepository;
import com.example.demo.repository.usuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/alquiler")
public class AlquileresController {
	@Autowired
	alquilereRepository alRep;
	@Autowired
	usuarioRepository usuarioRep;
	@Autowired
	productoRepository productoRep;
	
	@GetMapping("/obtener")
	public List<DTO> getProductos() {
		List<DTO> listaUsariosDTO = new ArrayList<>();
		List<Alquilere> alquileres = alRep.findAll();
		for (Alquilere p : alquileres) {
			DTO dtoProducto = new DTO();
			dtoProducto.put("id", p.getId());
			dtoProducto.put("id_usuario", p.getUsuario().getId());
			dtoProducto.put("id_producto", p.getProducto().getId());
			dtoProducto.put("fecha_inicio", p.getFechaInicio().toString());
			dtoProducto.put("fecha_fin", p.getFechaFin().toString());
			
			listaUsariosDTO.add(dtoProducto);
		}

		return listaUsariosDTO;
	}
	
	
	@PostMapping(path = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO actualizarAlquiler(@RequestBody DatosAltaAlquiler datos, HttpServletRequest request) {
	    DTO response = new DTO();
	    try {
	        Alquilere alquilerExistente = alRep.findById(datos.id);

	        if (alquilerExistente == null) {
	            response.put("status", "fail");
	            response.put("message", "Alquiler no encontrado");
	            return response;
	        }
	        Usuario usuarioAlquiler = usuarioRep.findById(datos.id_usuario).orElse(new Usuario());
	         
	        Producto productoAlquiler = productoRep.findById(datos.id_producto).orElse(new Producto());
	        
	        if (usuarioAlquiler == null || productoAlquiler == null) {
	            response.put("status", "fail");
	            response.put("message", "Usuario o Producto no encontrado");
	            return response;
	        }

	        // Actualizar los valores del alquiler
	       
		        alquilerExistente.setUsuario( usuarioAlquiler);
			
		        alquilerExistente.setProducto(productoAlquiler);	
	        
	        alquilerExistente.setFechaInicio(datos.fecha_inicio);
	        alquilerExistente.setFechaFin(datos.fecha_fin);

	        // Guardar los cambios
	        alRep.save(alquilerExistente);

	        response.put("status", "success");
	        response.put("message", "Alquiler actualizado correctamente");
	    } catch (Exception e) {
	        response.put("status", "fail");
	        response.put("message", "Error al actualizar el alquiler: " + e.getMessage());
	    }
	    return response;
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
				dtoProducto.put("fecha_inicio", p.getFechaInicio().toString());
				dtoProducto.put("fecha_fin", p.getFechaFin().toString());
				
				listaUsariosDTO.add(dtoProducto);
			}
			
		

		return listaUsariosDTO;
	}

	@PostMapping(path = "/estaAlquilado", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO getDisponibles(@RequestBody  Map<String, Object>  comprobacion, HttpServletRequest request) {
		DTO dtoProducto = new DTO();
		LocalDate fecha = null;
		LocalDate fecha1 = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (comprobacion.get("fecha_inicio") != null && comprobacion.get("fecha_fin") != null) {
        	fecha = LocalDate.parse(comprobacion.get("fecha_inicio").toString(), formatter);
  		  fecha1 = LocalDate.parse(comprobacion.get("fecha_fin").toString(), formatter);
		}
        boolean isAlquilado = false;
        String idProductoObj = (String) comprobacion.get("id_producto");

        if (idProductoObj == null) {
            dtoProducto.put("error", "id_producto es null");
            return dtoProducto;
        }

        try {
            int idProducto = Integer.parseInt(idProductoObj);
            isAlquilado = alRep.isProductoAlquilado(idProducto, fecha, fecha1);
        } catch (NumberFormatException e) {
            dtoProducto.put("error", "id_producto no es un número válido");
            return dtoProducto;
        } catch (Exception e) {
            dtoProducto.put("error", "Error desconocido: " + e.getMessage());
            return dtoProducto;
        }

		
		if(isAlquilado) {
			dtoProducto.put("alquilado", "true");

		}else {
			dtoProducto.put("alquilado", "false");

		}
			
		return dtoProducto;

	}
	

	@PostMapping(path = "/obtener1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO getProducto(@RequestBody DTO soloid, HttpServletRequest request) {
		DTO dtoProducto = new DTO();
		Alquilere p = alRep.findById(Integer.parseInt(soloid.get("id").toString()));
		if (p != null) {
			dtoProducto.put("id", p.getId());
			dtoProducto.put("id_usuario", p.getUsuario().getId());
			dtoProducto.put("id_producto", p.getProducto().getId());
			dtoProducto.put("fecha_inicio", p.getFechaInicio().toString());
			dtoProducto.put("fecha_fin", p.getFechaFin().toString());

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
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

import com.example.demo.model.Producto;
import com.example.demo.repository.productoRepository;

import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/productos")
public class ProductoController {
	@Autowired
	productoRepository proRep;
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
			dtoProducto.put("marca", p.getMarca());
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
			dtoProducto.put("marca", p.getMarca());
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
		proRep.save(new Producto(p.id, p.descripcion ,p.estado, p.marca,p.nombre,p.precio,p.ruta));
	}

	

	static class DatosAltaProducto {
		 int id;
		 String descripcion;
		 byte estado;
		 String marca;
		 String nombre;
		 BigDecimal precio;
		 String ruta;
		public DatosAltaProducto(int id, String descripcion, byte estado, String marca, String nombre,
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
	/*
	@PostMapping(path = "/autentica", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO autenticaUsuario(@RequestBody DatosAutenticaUsuario datos, HttpServletRequest request,
			HttpServletResponse response) {
		DTO dto = new DTO();
		dto.put("result", "fail");
		Usuaria usuarioAutenticado = usuRep.findByUsernameAndPass(datos.username, datos.pass);
		if (usuarioAutenticado != null) {
			dto.put("result", "ok");
			dto.put("jwt", AutenticadorJWT.codificaJWT(usuarioAutenticado));
			Cookie cook = new Cookie("jwt", AutenticadorJWT.codificaJWT(usuarioAutenticado));
			cook.setMaxAge(-1);
			response.addCookie(cook);
		}
		return dto;
	}
/*
	@GetMapping(path = "/quienes")
	public DTO getAutenticado(HttpServletRequest request) {
		DTO dtoUsuario = new DTO();
		dtoUsuario.put("result", "fail");
		Cookie[] c = request.getCookies();
		int idUsuarioAutenticado = -1;
		for (Cookie co : c) {
			if (co.getName().equals("jwt")) {
				idUsuarioAutenticado = AutenticadorJWT.getIdUsuarioDesdeJWT(co.getValue());

			}
		}
		Usuaria u = usuRep.findById(idUsuarioAutenticado);
		if (u != null) {
			dtoUsuario.put("id", u.getId());
			dtoUsuario.put("nombre", u.getNombre());
			dtoUsuario.put("fecha_nac", u.getFechaNac().toString());
			if (u.getFechaElim() != null) {
				dtoUsuario.put("fecha_elim", u.getFechaElim().toString());
			} else {
				dtoUsuario.put("fecha_elim", new Date(0));
			}
			dtoUsuario.put("idDeRol", u.getUsuarioTipo().getId());
			dtoUsuario.put("Rol", u.getUsuarioTipo().getRol());
		}
		return dtoUsuario;
	}

	
	static class DatosAutenticaUsuario {
		String username;
		String pass;

		public DatosAutenticaUsuario(String username, String pass) {
			super();
			this.username = username;
			this.pass = pass;
		}

	}
*/
}
package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.example.demo.model.Usuario;
import com.example.demo.repository.productoRepository;
import com.example.demo.repository.usuarioRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.DatatypeConverter;

@CrossOrigin
@RestController
@RequestMapping("/productos")
public class ProductoController {
	@Autowired
	productoRepository proRep;
	@GetMapping("/obtener")
	public List<DTO> getProductos() {
		List<DTO> listaUsariosDTO = new ArrayList<DTO>();
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
	/*
	@PostMapping(path = "/anadirnuevo")
	public void anadirUsuario(@RequestBody DatosAltaUsuario u, HttpServletRequest request) {
		usuRep.save(new Usuaria(u.id, null, u.fechaNac, DatatypeConverter.parseBase64Binary(u.img), u.nombre, u.pass,
				u.username, usuTipo.findById(u.rol)));
	}

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

	static class DatosAltaUsuario {
		int id;
		Date fechaNac;
		Date fechaElim;
		String img;
		// byte [] img;
		String nombre;
		String username;
		String pass;
		int rol;

		public DatosAltaUsuario(int id, Date fechaNac, Date fechaElim, String img, String nombre, String username,
				String pass, int rol) {
			super();
			this.id = id;
			this.fechaNac = fechaNac;
			this.fechaElim = fechaElim;
			this.img = img;
			this.nombre = nombre;
			this.username = username;
			this.pass = pass;
			this.rol = rol;
		}

	}
*/
}
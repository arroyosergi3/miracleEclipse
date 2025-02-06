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

import com.example.demo.model.Usuario;
import com.example.demo.repository.usuarioRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.DatatypeConverter;

@CrossOrigin
@RestController
@RequestMapping("/usuaria")
public class Usuariocontroller {

	@Autowired
	usuarioRepository usuRep;
	
/*
	@GetMapping("/obtener")
	public List<DTO> getUsuarios() {
		List<DTO> listaUsariosDTO = new ArrayList<DTO>();
		List<Usuario> usuarios = usuRep.findAll();
		for (Usuario u : usuarios) {
			DTO dtoUsuaria = new DTO();
			dtoUsuaria.put("id", u.getId());
			dtoUsuaria.put("nombre", u.getNombre());

			if (u.getFechaElim() != null) {

				dtoUsuaria.put("fecha_elim", u.getFechaElim().toString());
			} else {
				dtoUsuaria.put("fecha_elim", new Date(0));
			}

			dtoUsuaria.put("idDeRol", u.getUsuarioTipo().getId());
			dtoUsuaria.put("Rol", u.getUsuarioTipo().getRol());

			listaUsariosDTO.add(dtoUsuaria);
		}

		return listaUsariosDTO;
	}

	@PostMapping(path = "/obtener1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO getUsuario(@RequestBody DTO soloid, HttpServletRequest request) {
		DTO dtoUsuaria = new DTO();
		Usuaria u = usuRep.findById(Integer.parseInt(soloid.get("id").toString()));
		if (u != null) {

			dtoUsuaria.put("id", u.getId());
			dtoUsuaria.put("nombre", u.getNombre());
			dtoUsuaria.put("fecha_nac", u.getFechaNac().toString());

			if (u.getFechaElim() != null) {

				dtoUsuaria.put("fecha_elim", u.getFechaElim().toString());
			} else {
				dtoUsuaria.put("fecha_elim", new Date(0));
			}

			dtoUsuaria.put("idDeRol", u.getUsuarioTipo().getId());
			dtoUsuaria.put("Rol", u.getUsuarioTipo().getRol());
		} else {
			dtoUsuaria.put("Result", "fail");
		}
		return dtoUsuaria;
	}

	@PostMapping(path = "/borrar1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO deleteUsuario(@RequestBody DTO soloid, HttpServletRequest request) {
		DTO dtoUsuaria = new DTO();
		Usuaria u = usuRep.findById(Integer.parseInt(soloid.get("id").toString()));
		if (u != null) {
			usuRep.delete(u);
			dtoUsuaria.put("borrado", "success");
		} else {
			dtoUsuaria.put("borrado", "fail");
		}
		return dtoUsuaria;
	}

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
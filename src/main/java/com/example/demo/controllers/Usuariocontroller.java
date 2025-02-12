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

import jakarta.servlet.http.Cookie;
import com.example.demo.jwtSecurity.AutenticadorJWT;
import com.example.demo.model.Usuario;
import com.example.demo.repository.marcaRepository;
import com.example.demo.repository.usuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/usuario")
public class Usuariocontroller {

	@Autowired
	usuarioRepository usuRep;

	@GetMapping("/obtener")
	public List<DTO> getUsuarios() {
		List<DTO> listaUsariosDTO = new ArrayList<DTO>();
		List<Usuario> usuarios = usuRep.findAll();
		for (Usuario u : usuarios) {
			DTO dtoUsuaria = new DTO();
			dtoUsuaria.put("id", u.getId());
			dtoUsuaria.put("nombre", u.getNombre());
			dtoUsuaria.put("apellido", u.getApellido());
			dtoUsuaria.put("email", u.getEmail());
			dtoUsuaria.put("sexo", u.getSexo());
			dtoUsuaria.put("pais", u.getPais());
			dtoUsuaria.put("contrasena", u.getContrasena());
			dtoUsuaria.put("rol", u.getRol());
			listaUsariosDTO.add(dtoUsuaria);
		}

		return listaUsariosDTO;
	}

	
	
	@PostMapping(path = "/obtener1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO getUsuario(@RequestBody DTO soloid, HttpServletRequest request) {
		DTO dtoUsuario = new DTO();
		Usuario u = usuRep.findById(Integer.parseInt(soloid.get("id").toString()));
		if (u != null) {

			dtoUsuario.put("id", u.getId());
			dtoUsuario.put("nombre", u.getNombre());
			dtoUsuario.put("apellido", u.getApellido());
			dtoUsuario.put("email", u.getEmail());
			dtoUsuario.put("sexo", u.getSexo());
			dtoUsuario.put("pais", u.getPais());
			dtoUsuario.put("contrasena", u.getContrasena());
			dtoUsuario.put("rol", u.getRol());

		}
		return dtoUsuario;
	}

	@PostMapping(path = "/borrar1", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO deleteUsuario(@RequestBody DTO soloid, HttpServletRequest request) {
		DTO dtoUsuaria = new DTO();
		Usuario u = usuRep.findById(Integer.parseInt(soloid.get("id").toString()));
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
		usuRep.save(new Usuario( u.nombre, u.apellido, u.email, u.sexo, u.pais, u.contrasena, u.rol));
	}

	@PostMapping(path = "/autentica", consumes = MediaType.APPLICATION_JSON_VALUE)
	public DTO autenticaUsuario(@RequestBody DatosAutenticaUsuario datos, HttpServletRequest request,
			HttpServletResponse response) {
		DTO dto = new DTO();
		dto.put("result", "fail");
		// usuarioAutenticado = usuRep.findByEmailAndContrasena(datos.email, datos.contrasena);
		Usuario usuarioAutenticado = usuRep.autenticar(datos.email, datos.contrasena);

		if (usuarioAutenticado != null) { 
			dto.put("result", "ok");
			dto.put("id_usuario", usuarioAutenticado.getId());
			dto.put("rol", usuarioAutenticado.getRol());
			dto.put("jwt", AutenticadorJWT.codificaJWT(usuarioAutenticado));
			
			
			
			Cookie cook = new Cookie("jwt", AutenticadorJWT.codificaJWT(usuarioAutenticado));
			cook.setMaxAge(-1);
			response.addCookie(cook);
		}else {
			Usuario usuarioEncontrado = usuRep.buscarUsuarioPorMail(datos.email);
			if (usuarioEncontrado != null) {
				dto.put("email", usuarioEncontrado.getEmail());
			}else {
				dto.put("result", "nullUser");
			}
			
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
		Usuario u = usuRep.findById(idUsuarioAutenticado);
		if (u != null) {
			dtoUsuario.put("id", u.getId());
			dtoUsuario.put("nombre", u.getNombre());
			dtoUsuario.put("apellido", u.getApellido());
			dtoUsuario.put("email", u.getEmail());
			dtoUsuario.put("sexo", u.getSexo());
			dtoUsuario.put("pais", u.getPais());
			dtoUsuario.put("contrasena", u.getContrasena());
			dtoUsuario.put("rol", u.getRol());
		
	}
		return dtoUsuario;
	}

	static class DatosAutenticaUsuario {
		String email;
		String contrasena;

		public DatosAutenticaUsuario(String email, String contrasena) {
			super();
			this.email = email;
			this.contrasena = contrasena;
		}

	}

	static class DatosAltaUsuario {
		
		String nombre;
		String apellido;
		String email;
		String sexo;
		String pais;
		String contrasena;
		String rol;

		public DatosAltaUsuario(String nombre, String apellido, String email, String rol, String contrasena,
	            String pais, String sexo) {
	        this.nombre = nombre;
	        this.apellido = apellido;
	        this.email = email;
	        this.sexo = sexo;
	        this.pais = pais;
	        this.contrasena = contrasena;
	        this.rol = rol;
	    }

	}

}
package com.example.demo.jwtSecurity;

import java.security.Key;


import jakarta.servlet.http.HttpServletRequest;

import com.example.demo.model.Usuario;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.impl.crypto.MacProvider;


public class AutenticadorJWT {

	

	private static Key key = null; // Clave de encriptaciÃ³n que usaremos para generar el JWT. Diferente en cada ejecuciÃ³n

	

	/**

	 * From the user class, it generates a JWT that contains its id 

	 */

	public static String codificaJWT (Usuario u) {

		String jws = Jwts.builder().setSubject(""+u.getId()).

				signWith(SignatureAlgorithm.HS512, getGeneratedKey()).compact();

		return jws;

	}

	

	/**

	 * From a jwt, the user id is located

	 */

	public static int getIdUsuarioDesdeJWT (String jwt) {

		try {

			String stringIdUsuario = Jwts.parser().setSigningKey(getGeneratedKey()).parseClaimsJws(jwt).getBody().getSubject();

			int idUsuario = Integer.parseInt(stringIdUsuario);

			return idUsuario;

		}

		catch (Exception ex) {

			ex.printStackTrace();

			return -1;

		}

	}

	

	//Repite

	public static String getIdUsuarioDesdeJWT2 (String jwt) {

		try {

			String stringIdUsuario = Jwts.parser().setSigningKey(getGeneratedKey()).parseClaimsJws(jwt).getBody().getSubject();

			//int idUsuario = Integer.parseInt(stringIdUsuario);

			return stringIdUsuario;

		}

		catch (Exception ex) {

			ex.printStackTrace();

			return "-1";

		}

	}

	

	

	/**

	 * Obtiene el id de un usuario almacenado en un JWT que proviene de un request

	 */

	public static int getIdUsuarioDesdeJwtIncrustadoEnRequest (HttpServletRequest request) {

		String autHeader = request.getHeader("Authorization");

		if (autHeader != null && autHeader.length() > 8) {

			String jwt = autHeader.substring(7);

			return getIdUsuarioDesdeJWT(jwt);

		}

		else {

			return -1; // Cuando no se puede obtener el usuario devolvemos -1 como mensaje de error

		}

	}

	

	/**

	 * Genera una nueva clave cada vez que se inicia el servidor

	 * @return

	 */

	private static Key getGeneratedKey () {

		if (key == null) {

			key = MacProvider.generateKey();

		}

		return key;

	}


}
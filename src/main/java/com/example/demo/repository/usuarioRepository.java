package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Usuario;

import jakarta.transaction.Transactional;

@Repository
public interface usuarioRepository extends JpaRepository<Usuario, Serializable> {

	@Override
	@Bean
	public abstract List<Usuario> findAll();
	public abstract Usuario findById(int id);
	public abstract Usuario findByNombre(String email);

	@Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.contrasena = :contrasena")
	Usuario autenticar(@Param("email") String email, @Param("contrasena") String contrasena);
	public abstract Usuario findByEmailAndContrasena(String email, String contrasena);
	

	@Query("SELECT u FROM Usuario u WHERE u.email = :email ")
	Usuario buscarUsuarioPorMail(@Param("email") String email);
	
	@Override
	@Transactional
	public abstract void delete(Usuario u);
	@Transactional
	public abstract void deleteById(int id);
	@Override  
	@Transactional
	public abstract Usuario save (Usuario u);
}

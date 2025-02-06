package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Usuario;

import jakarta.transaction.Transactional;

@Repository
public interface usuarioRepository extends JpaRepository<Usuario, Serializable> {

	@Bean
	public abstract List<Usuario> findAll();
	public abstract Usuario findById(int id);
	public abstract Usuario findByNombre(String email);
	public abstract Usuario findByEmailAndContraseña(String email, String contraseña);
	@Transactional
	public abstract void delete(Usuario u);
	@Transactional
	public abstract void deleteById(int id);
	@Transactional
	public abstract Usuario save (Usuario u);
}

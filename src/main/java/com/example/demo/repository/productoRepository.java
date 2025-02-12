package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Producto;

import jakarta.transaction.Transactional;

@Repository
public interface productoRepository extends JpaRepository<Producto, Serializable> {

	@Override
	@Bean
	public abstract List<Producto> findAll();
	
	public abstract Producto findById(int id);
	public abstract Producto findByNombre(String nombre);
	@Override
	@Transactional
	public abstract void delete(Producto u);
	@Transactional
	public abstract void deleteById(int id);
	@Override
	@Transactional
	public abstract Producto save (Producto u);
}

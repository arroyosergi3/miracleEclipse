package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Marca;
import com.example.demo.model.Usuario;


@Repository
public interface marcaRepository extends JpaRepository<Marca,Serializable> {

	@Bean
	public abstract List<Marca> findAll();
	public abstract Marca findById(int id);
	
	@Transactional 
	public abstract void deleteById(int id);
	
	@SuppressWarnings("unchecked")
	@Transactional
	public abstract Marca save(Marca u);
}

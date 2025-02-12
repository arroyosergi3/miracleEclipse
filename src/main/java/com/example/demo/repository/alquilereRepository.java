package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Alquilere;

import jakarta.transaction.Transactional;

@Repository
public interface alquilereRepository extends JpaRepository<Alquilere, Serializable> {

	@Override
	@Bean
	public abstract List<Alquilere> findAll();
	public abstract Alquilere findById(int id);
	
	// Método para obtener alquileres por el id del usuario (relacionado a través de la entidad Usuario)
    public List<Alquilere> findByUsuario_Id(int idUsuario);
    
    
    @Query("SELECT a FROM Alquilere a ")
	List<Alquilere> buscarTodos();
    
    
	@Override
	@Transactional
	public abstract void delete(Alquilere u);
	@Transactional
	public abstract void deleteById(int id);
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public abstract Alquilere save (Alquilere u);
}

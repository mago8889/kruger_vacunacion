package com.kruger.services;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kruger.model.Rol;

public interface RolService extends CrudRepository<Rol, Long> {
	
	List<Rol> findByidRol(Long idRol);

}

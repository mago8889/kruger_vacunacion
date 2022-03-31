package com.kruger.services;

import org.springframework.data.repository.CrudRepository;

import com.kruger.model.Empleados;

public interface EmpleadoService extends CrudRepository<Empleados, Long> {
	
	

}

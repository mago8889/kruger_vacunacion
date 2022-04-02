package com.kruger.services;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kruger.model.Empleados;

public interface EmpleadoService extends CrudRepository<Empleados, Long> {
	
	Empleados findByidEmpleado(Long idEmpleado);
	
	Optional<Empleados> findBycedula(int cedula);

}

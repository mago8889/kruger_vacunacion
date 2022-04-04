package com.kruger.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kruger.model.Vacuna;

public interface VacunaService extends CrudRepository<Vacuna, Long> {
	
	List<Vacuna> findByfechaVacunacionBetween(Date fecInicio, Date fecFin);

}

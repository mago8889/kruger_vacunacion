package com.kruger.services;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kruger.model.TipoVacuna;

public interface TipoVacunaService extends CrudRepository<TipoVacuna, Long> {
	
	List<TipoVacuna> findBynombre (String nombre);

}

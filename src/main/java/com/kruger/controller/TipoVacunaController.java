package com.kruger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruger.model.TipoVacuna;
import com.kruger.services.TipoVacunaService;

@RestController
@RequestMapping("/api")
public class TipoVacunaController {
	
	@Autowired
	TipoVacunaService tipoVacunaService;
	
	@GetMapping("/tipovacuna/getall")
	public List<TipoVacuna> getAll(){
		
		List<TipoVacuna> tipoVacunas = (List<TipoVacuna>) tipoVacunaService.findAll();
		return tipoVacunas;
	}
}

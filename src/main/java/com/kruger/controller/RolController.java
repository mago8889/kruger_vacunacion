package com.kruger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruger.clases.Response;
import com.kruger.model.Rol;
import com.kruger.services.RolService;

@RestController
@RequestMapping("/api")
public class RolController {
	
	@Autowired
	RolService rolService;
	
	@GetMapping("/rol/getall")
	public Response getAll(){
		
		Response response = new Response();
		
		try {
			List<Rol> roles = (List<Rol>) rolService.findAll();
			
			response.setId(0);
			response.setDescription("Listado de roles");
			response.setObjectResponse(roles);
			return response;
			
		}catch (Exception e) {
			response.setId(1);
			response.setDescription("Error getAll Rol");
			return response;
		} 
	}

}

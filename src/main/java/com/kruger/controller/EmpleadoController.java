package com.kruger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruger.clases.Credenciales;
import com.kruger.clases.RegistroFinal;
import com.kruger.clases.RegistroInicial;
import com.kruger.clases.Response;
import com.kruger.clases.Utilidades;
import com.kruger.model.Empleados;
import com.kruger.services.EmpleadoService;

@RestController
@RequestMapping("/api")
public class EmpleadoController {
	
	@Autowired
	EmpleadoService empleadoService;
	
	//@Autowired
    //PasswordEncoder encoder;
	
	@GetMapping("/empleado/getall")
	public Response getAll() {
		
		Response response = new Response();
		try {
			List<Empleados>empleados = (List<Empleados>) empleadoService.findAll();
			response.setId(0);
			response.setDescription("Listado de Empleados");
			response.setObjectResponse(empleados);
			
			return response;
		} catch (Exception e) {
			response.setId(1);
			response.setDescription("Error getAll de Empleados" + e);
			
			return response;
		}
	}
	
	@PostMapping("/empleado/signup")
	public Response signUp (@RequestBody RegistroInicial registroInicial) {
		
		Response response = new Response();
		
		try {
			Empleados empleado = new Empleados();
			empleado.setCedula(registroInicial.getCedula());
			empleado.setNombres(registroInicial.getNombres());
			empleado.setApellidos(registroInicial.getApellidos());
			empleado.setCorreo(registroInicial.getCorreo());
			empleado.setEstado("N");
			empleado.setRol(registroInicial.getRol());
			empleadoService.save(empleado);
			
			response.setId(0);
			response.setDescription("Empleado registrado");
			
			return response;
		}catch (Exception e) {
			
			response.setId(1);
			response.setDescription("Error en el registro de empleado" + e);
			
			return response;
		}
	}
	
	@PostMapping("/empleado/activateUser/{id}")
	public Response activateUser (@PathVariable Long id) {
		
		Response response = new Response();
		
		try {
			Empleados empleado = empleadoService.findByidEmpleado(id);
			empleado.setEstado("Y");	
			
			Utilidades utilidades = new Utilidades();
			String claveAuto = utilidades.randomText();
			
			//empleado.setClave(encoder.encode(claveAuto));
			empleado.setClave(claveAuto);
			empleadoService.save(empleado);	
			
			Credenciales credenciales = new Credenciales();
			credenciales.setUser(empleado.getCedula()+"");
			credenciales.setClave(claveAuto);
			
			response.setId(0);
			response.setDescription("Empleado activo");
			response.setObjectResponse(credenciales);
			
			return response;
			
		}catch (Exception e) {
			
			response.setId(1);
			response.setDescription("Error en el registro de empleado" + e);
			
			return response;
		}
	}
	
	@PostMapping("/empleado/signin")
	public Response signin (@RequestBody Credenciales credenciales) {
		
		Response response = new Response();
		
		try {
			Empleados empleado = empleadoService.findBycedula(Integer.parseInt(credenciales.getUser()));
			
			if(empleado.getDireccion() == null) {
				response.setId(1);
				response.setDescription("Para ingresar debe completar la informacion restante");
				return response;
			}else {
				response.setId(0);
				response.setDescription("Bienvenido");
				return response;
			}			
			
		}catch (Exception e) {
			
			response.setId(1);
			response.setDescription("Error en sigin" + e);
			
			return response;
		}
	}
	
	@PostMapping("/empleado/updateinfo")
	public Response updateInfo (@RequestBody RegistroFinal registroFinal) {
		
		Response response = new Response();
		
		try {
			Empleados empleado = empleadoService.findByidEmpleado(registroFinal.getIdUser());
			empleado.setDireccion(registroFinal.getDireccion());
			empleado.setCelular(registroFinal.getCelular());
			empleado.setVacuna(registroFinal.getVacuna());
			
			response.setId(0);
			response.setDescription("Registro completo");
			return response;
			
		}catch (Exception e) {
			
			response.setId(1);
			response.setDescription("Error en updateInfo" + e);
			
			return response;
		}
	}

}

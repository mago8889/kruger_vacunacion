package com.kruger.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.kruger.model.Rol;
import com.kruger.security.JwtProvider;
import com.kruger.security.JwtResponse;
import com.kruger.services.EmpleadoService;

@RestController
@RequestMapping("/api")
public class EmpleadoController {
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	EmpleadoService empleadoService;
	
	@Autowired
    PasswordEncoder encoder;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@GetMapping("/empleado/getall")
	@PostAuthorize("hasRole('Administrador')")
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
	@PreAuthorize("hasRole('Administrador')")
	public Response signUp (@RequestBody RegistroInicial registroInicial) {
		
		Response response = new Response();
		
		try {
			Empleados empleado = new Empleados();
			empleado.setCedula(registroInicial.getCedula());
			empleado.setNombres(registroInicial.getNombres());
			empleado.setApellidos(registroInicial.getApellidos());
			empleado.setCorreo(registroInicial.getCorreo());
			empleado.setEstado("N");
			empleado.setRoles(registroInicial.getRoles());
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
	@PreAuthorize("hasRole('Administrador')")
	public Response activateUser (@PathVariable Long id) {
		
		Response response = new Response();
		
		try {
			Empleados empleado = empleadoService.findByidEmpleado(id);
			empleado.setEstado("Y");	
			
			Utilidades utilidades = new Utilidades();
			String claveAuto = utilidades.randomText();
			
			empleado.setClave(encoder.encode(claveAuto));
			//empleado.setClave(claveAuto);
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
	
	@PostMapping("/empleado/free/signin")
	public ResponseEntity<?> signin (@RequestBody Credenciales credenciales) {
		
		Response response = new Response();
		
		try {
			Optional<Empleados> empleado = empleadoService.findBycedula(Integer.parseInt(credenciales.getUser()));
			
			if(empleado.get().getDireccion() == null) {
				return new ResponseEntity<String>("Fail -> Para ingresar debe completar la informacion restante!", HttpStatus.NOT_ACCEPTABLE);
				
			}else {
				Optional<Empleados> emple = empleadoService.findBycedula(Integer.parseInt(credenciales.getUser()));
				
				Authentication authentication = authenticationManager.authenticate(
		                new UsernamePasswordAuthenticationToken(
		                		credenciales.getUser(),
		                		credenciales.getClave()
		                )
		        );		 
		        SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtProvider.generateJwtToken(authentication);
				
				List<Rol> roles = emple.get().getRoles();
				
				return ResponseEntity.ok(new JwtResponse(jwt, roles, emple.get().getIdEmpleado()));
			}			
			
		}catch (Exception e) {
			
			return new ResponseEntity<String>("Fail -> Error sigin!", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@PostMapping("/empleado/updateinfo")
	@PreAuthorize("hasRole('Empleado')")
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

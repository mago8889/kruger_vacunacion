package com.kruger.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.kruger.model.TipoVacuna;
import com.kruger.model.Vacuna;
import com.kruger.security.JwtProvider;
import com.kruger.security.JwtResponse;
import com.kruger.services.EmpleadoService;
import com.kruger.services.VacunaService;

@RestController
@RequestMapping("/api")
public class EmpleadoController {
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	EmpleadoService empleadoService;
	
	@Autowired
	VacunaService vacunaService;
	
	@Autowired
    PasswordEncoder encoder;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@GetMapping("/empleado/getall")
	//@PreAuthorize("hasRole('Administrador')")
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
	//@PreAuthorize("hasRole('Administrador')")
	public Response signUp (@Valid @RequestBody RegistroInicial registroInicial, BindingResult result) {
		
		Response response = new Response();
		Utilidades utilidades = new Utilidades();
		
		try {
			if(!result.hasErrors()){
				Optional<Empleados> empleados = empleadoService.findBycedula(registroInicial.getCedula());
				if(empleados.isEmpty()) {
					if(utilidades.validaCedula(registroInicial.getCedula()+"")) {
						if(utilidades.validaEmail(registroInicial.getCorreo())) {
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
						}else {
							response.setId(1);
							response.setDescription("Email invalido");						
							return response;
						}
						
					}else {
						response.setId(1);
						response.setDescription("Cedula invalida");					
						return response;
					}			
				}else {
					response.setId(1);
					response.setDescription("Ya existe un registro con la cedula: " + registroInicial.getCedula());				
					return response;
				}
			}else {
				response.setId(1);
				response.setDescription("Todos los campos son obligatorios");				
				return response;
			}
					
		}catch (Exception e) {			
			response.setId(1);
			response.setDescription("Error en el registro de empleado" + e);			
			return response;
		}
	}
	
	@PostMapping("/empleado/activateUser/{id}")
	//@PreAuthorize("hasRole('Administrador')")
	public Response activateUser (@PathVariable Long id) {
		
		Response response = new Response();
		
		try {
			Empleados empleado = empleadoService.findByidEmpleado(id);
			empleado.setEstado("Y");	
			
			Utilidades utilidades = new Utilidades();
			String claveAuto = utilidades.randomText();
			
			empleado.setClave(encoder.encode(claveAuto));
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
	
	@PutMapping("/empleado/updateinfo")
	//@PreAuthorize("hasRole('Empleado')")
	public Response updateInfo (@RequestBody RegistroFinal registroFinal) {
		
		Response response = new Response();
		
		try {
			Empleados empleado = empleadoService.findByidEmpleado(registroFinal.getIdEmpleado());
			empleado.setDireccion(registroFinal.getDireccion());
			empleado.setCelular(registroFinal.getCelular());
			empleadoService.save(empleado);
			
			Vacuna vacuna = new Vacuna();
			for(Vacuna vac : registroFinal.getVacuna()) {				
				vacuna.setNumeroDosis(vac.getNumeroDosis());
				vacuna.setEmpleado(empleado);
				vacuna.setFechaVacunacion(new Date());
				vacuna.setTipoVacuna(vac.getTipoVacuna());
				vacunaService.save(vacuna);
			}			
			
			response.setId(0);
			response.setDescription("Registro completo");
			return response;
			
		}catch (Exception e) {
			
			response.setId(1);
			response.setDescription("Error en updateInfo" + e);
			
			return response;
		}
	}
	
	@GetMapping("/empleado/findestadovac/{estadovac}")
	public Response findByEstadoVacunacion(@PathVariable String estadovac){
		
		List<Empleados> empleados = (List<Empleados>) empleadoService.findAll();
		List<Empleados> empleadosVacFil = new ArrayList<Empleados>();
		String desc = null;
		for(Empleados empleados2 : empleados) {
			if(estadovac.equals("Y")) {
				desc = "Empleados vacunados";
				if(empleados2.getVacuna().size() > 0) {
					empleadosVacFil.add(empleados2);
				}
			}else if(estadovac.equals("N")) {
				desc = "Empleados sin vacuna";
				if(empleados2.getVacuna().size() == 0) {
					empleadosVacFil.add(empleados2);
				}
			}			
		}
		
		Response response = new Response();
		response.setId(0);
		response.setDescription(desc);
		response.setObjectResponse(empleadosVacFil);
		
		return response;
	}
	
	@GetMapping("/empleado/findtipovac")
	public Response findByTipoVacuna(@RequestBody TipoVacuna tipoVacuna){
		
		List<Empleados> empleados = (List<Empleados>) empleadoService.findAll();
		List<Empleados> empleadosResp = new ArrayList<Empleados>();
		for(Empleados empleados2 : empleados) {
			List<Vacuna> vacunas = empleados2.getVacuna();
			for(Vacuna vacuna : vacunas) {
				if(vacuna.getTipoVacuna().getIdTipoVacuna().equals(tipoVacuna.getIdTipoVacuna())) {
					empleadosResp.add(empleados2);
					break;
				}
			}
		}
		
		Response response = new Response();
		response.setId(0);
		response.setDescription("Empleados con vacuna: "+tipoVacuna.getNombre());
		response.setObjectResponse(empleadosResp);
		
		return response;
	}
	
	@GetMapping("/empleado/findfechavac/{fechainicio}/{fechafin}")
	public Response findByTipoVacuna(@PathVariable String fechainicio, @PathVariable String fechafin){
		
		Response response = new Response();
		
		try {
			Date vd_fechainicio = new SimpleDateFormat("yyyy-mm-dd").parse(fechainicio + "00:00:00.000");
			Date vd_fechafin = new SimpleDateFormat("yyyy-mm-dd").parse(fechafin + "00:00:00.000");
			
			List<Vacuna> vacunas = vacunaService.findByfechaVacunacionBetween(vd_fechainicio, vd_fechafin);			
			
			response.setId(0);
			response.setDescription("Vacunas en rango de ferchas");
			response.setObjectResponse(vacunas);
			return response;
			
		} catch (Exception e) {
			response.setId(1);
			response.setDescription("Error findByTipoVacuna");
			return response;
		}
		
		
		
	}

}

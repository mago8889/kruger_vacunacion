package com.kruger.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kruger.model.Empleados;
import com.kruger.services.EmpleadoService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	EmpleadoService empleadoService;


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String cedula) throws UsernameNotFoundException {

		Empleados empleados = empleadoService.findBycedula(Integer.parseInt(cedula))
                .orElseThrow(() -> 
                      new UsernameNotFoundException("Usuario no encontrado -> username o email : " + cedula)
      );

      return UserPrinciple.build(empleados);
		
	}

}

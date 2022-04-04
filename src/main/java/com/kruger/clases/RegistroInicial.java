package com.kruger.clases;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.kruger.model.Rol;

public class RegistroInicial {

	@NotEmpty
	private int cedula;
	
	@Pattern(regexp = "[a-zA-Z]+",message = "Se debe ingresar solo letras")
	@NotEmpty
	private String nombres;
	
	@Pattern(regexp = "[a-zA-Z]+",message = "Se debe ingresar solo letras")
	@NotEmpty
	private String apellidos;
	
	@NotEmpty
	@Email (message = "Error de formato de correo electrónico")
	private String correo;
	
	@NotEmpty
	private List<Rol> roles;
	
	public List<Rol> getRoles() {
		return roles;
	}
	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	public int getCedula() {
		return cedula;
	}
	public void setCedula(int cedula) {
		this.cedula = cedula;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}	
	
}

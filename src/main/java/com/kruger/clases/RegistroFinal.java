package com.kruger.clases;

import java.util.List;

import com.kruger.model.Vacuna;

public class RegistroFinal {
	
	private Long idEmpleado;
	private String direccion;
	private int celular;
	private List<Vacuna> vacuna;
	
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getCelular() {
		return celular;
	}
	public void setCelular(int celular) {
		this.celular = celular;
	}
	public List<Vacuna> getVacuna() {
		return vacuna;
	}
	public void setVacuna(List<Vacuna> vacuna) {
		this.vacuna = vacuna;
	}

}

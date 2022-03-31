package com.kruger.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TBL_ROL")
public class Rol {
	
	@Id
	@SequenceGenerator(name = "SEQROL", sequenceName = "SEQROL", allocationSize = 1) 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQROL")
	@Column(name="id_rol")
	private Long idRol;
	
	@Column(name="nombre", nullable=false)
	private String nombre;

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}

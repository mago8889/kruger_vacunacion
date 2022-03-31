package com.kruger.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TBL_TIPOVACUNA")
public class TipoVacuna {
	
	@Id
	@SequenceGenerator(name = "SEQTIPOVACUNA", sequenceName = "SEQTIPOVACUNA", allocationSize = 1) 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQTIPOVACUNA")
	@Column(name="id_tipo_vacuna")
	private Long idTipoVacuna;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	public Long getIdTipoVacuna() {
		return idTipoVacuna;
	}
	public void setIdTipoVacuna(Long idTipoVacuna) {
		this.idTipoVacuna = idTipoVacuna;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}

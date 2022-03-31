package com.kruger.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TBL_VACUNA")
public class Vacuna {
	
	@Id
	@SequenceGenerator(name = "SEQVACUNA", sequenceName = "SEQVACUNA", allocationSize = 1) 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQVACUNA")
	@Column(name="id_vacuna")
	private Long idVacuna;
	
	@OneToOne
    @JoinColumn(name = "id_tipo_vacuna", nullable = true)
	private TipoVacuna tipoVacuna;
	
	@Column(name="fecha_vacunacion", nullable=false)
	private Date fechaVacunacion;
	
	@Column(name="numero_dosis", nullable=false)
	private int numeroDosis;
	
	@ManyToOne
    @JoinColumn(name="id_empleado", nullable=false)
	private Empleados empleado;
	
	public Long getIdVacuna() {
		return idVacuna;
	}
	public void setIdVacuna(Long idVacuna) {
		this.idVacuna = idVacuna;
	}
	public TipoVacuna getTipoVacuna() {
		return tipoVacuna;
	}
	public void setTipoVacuna(TipoVacuna tipoVacuna) {
		this.tipoVacuna = tipoVacuna;
	}
	public Date getFechaVacunacion() {
		return fechaVacunacion;
	}
	public void setFechaVacunacion(Date fechaVacunacion) {
		this.fechaVacunacion = fechaVacunacion;
	}
	public int getNumeroDosis() {
		return numeroDosis;
	}
	public void setNumeroDosis(int numeroDosis) {
		this.numeroDosis = numeroDosis;
	}
	public Empleados getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleados empleado) {
		this.empleado = empleado;
	}
}

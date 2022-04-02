package com.kruger.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TBL_EMPLEADOS")
public class Empleados {
	
	@Id
	@SequenceGenerator(name = "SEQEMPLEADO", sequenceName = "SEQEMPLEADO", allocationSize = 1) 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQEMPLEADO")
	@Column(name="id_empleado")
	private Long idEmpleado;
	
	@Column(name="cedula", nullable=false)
	private int cedula;
	
	@Column(name="clave", nullable=true)
	private String clave;
	
	@Column(name="nombres", nullable=false)
	private String nombres;
	
	@Column(name="apellidos", nullable=false)
	private String apellidos;
	
	@Column(name="correo", nullable=false)
	private String correo;
	
	@Column(name="fechaNacimiento", nullable=true)
	private Date fechaNacimiento;
	
	@Column(name="direccion", nullable=true)
	private String direccion;
	
	@Column(name="celular", nullable=true)
	private int celular;
	
	@Column(name="estado", nullable=true)
	private String estado;
	
	@OneToMany(mappedBy="empleado")
	private List<Vacuna> vacuna;

	@ManyToMany(cascade = {CascadeType.MERGE},fetch= FetchType.EAGER)
	@JoinTable(name = "TBL_USER_ROL", joinColumns = @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado"),
    inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id_rol"))
	private List<Rol> roles;
	
	//@ManyToOne
	//@JoinColumn(name = "id_rol", nullable=true)
	//private Rol rol;
	
	public String getClave() {
		return clave;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> list) {
		this.roles = list;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Vacuna> getVacuna() {
		return vacuna;
	}

	public void setVacuna(List<Vacuna> vacuna) {
		this.vacuna = vacuna;
	}

}

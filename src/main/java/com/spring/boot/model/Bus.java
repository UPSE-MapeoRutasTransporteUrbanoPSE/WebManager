package com.spring.boot.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Bus {
	  
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("placa")
	private String placa;
	
	@JsonProperty("numeroDisco")
	private int numeroDisco;
	
	@JsonProperty("capacidad")
	private int capacidad;
	
	@JsonProperty("idCooperativa")
	private String idCooperativa;
	
	@JsonProperty("idEstadoActualTemporal")
	private String idEstadoActualTemporal;
	
	@JsonProperty("estado")
	private Boolean estado;
	
	public transient Integer orden;
	public transient String nombreCooperativa;
	
	public Bus() {	}

	public Bus(String id, String placa, int numeroDisco, int capacidad, String idCooperativa,
			String idEstadoActualTemporal, Boolean estado) {
		super();
		this.id = id;
		this.placa = placa;
		this.numeroDisco = numeroDisco;
		this.capacidad = capacidad;
		this.idCooperativa = idCooperativa;
		this.idEstadoActualTemporal = idEstadoActualTemporal;
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public int getNumeroDisco() {
		return numeroDisco;
	}

	public void setNumeroDisco(int numeroDisco) {
		this.numeroDisco = numeroDisco;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getIdCooperativa() {
		return idCooperativa;
	}

	public void setIdCooperativa(String idCooperativa) {
		this.idCooperativa = idCooperativa;
	}

	public String getIdEstadoActualTemporal() {
		return idEstadoActualTemporal;
	}

	public void setIdEstadoActualTemporal(String idEstadoActualTemporal) {
		this.idEstadoActualTemporal = idEstadoActualTemporal;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}	
	
	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getNombreCooperativa() {
		return nombreCooperativa;
	}

	public void setNombreCooperativa(String nombreCooperativa) {
		this.nombreCooperativa = nombreCooperativa;
	}

	@Override
	public String toString() {
		return "Bus [id=" + id + ", placa=" + placa + ", numeroDisco=" + numeroDisco + ", capacidad=" + capacidad
				+ ", idCooperativa=" + idCooperativa + ", idEstadoActualTemporal=" + idEstadoActualTemporal
				+ ", estado=" + estado + "]";
	}
	
		
}
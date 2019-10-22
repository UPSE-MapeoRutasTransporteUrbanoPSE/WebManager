package com.spring.boot.model;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Davids Adrian Gonzalez Tigrero
 *
 */
public class Reporte {
	
	@JsonProperty("id")
    private String id;
	
	@JsonProperty("idUsuario")
    private String idUsuario;
	
	@JsonProperty("asunto")
	private String asunto;
	
	@JsonProperty("numeroDisco")
	private Integer numeroDisco;
	
	@JsonProperty("ubicacion")
	private String ubicacion;
	
	@JsonProperty("fecha")
	private Date fecha;
	
	@JsonProperty("idCooperativa")
	private String idCooperativa;
	
	@JsonProperty("mensaje")
	private String mensaje;
	
	@JsonProperty("estado")
	private Boolean estado;
	
	public transient Integer orden;
	public transient String nombreCooperativa;
	public transient String fechaString;
	
	
	
   

	

	public Reporte(String id, String idUsuario, String asunto, Integer numeroDisco, String ubicacion, Date fecha,
			String idCooperativa, String mensaje, Boolean estado, Integer orden, String nombreCooperativa) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.asunto = asunto;
		this.numeroDisco = numeroDisco;
		this.ubicacion = ubicacion;
		this.fecha = fecha;
		this.idCooperativa = idCooperativa;
		this.mensaje = mensaje;
		this.estado = estado;
		this.orden = orden;
		this.nombreCooperativa = nombreCooperativa;
	}



	 public Reporte() {    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public Integer getNumeroDisco() {
		return numeroDisco;
	}

	public void setNumeroDisco(Integer numeroDisco) {
		this.numeroDisco = numeroDisco;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getIdCooperativa() {
		return idCooperativa;
	}

	public void setIdCooperativa(String idCooperativa) {
		this.idCooperativa = idCooperativa;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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

	



	public String getFechaString() {
		return fechaString;
	}



	public void setFechaString(String fechaString) {
		this.fechaString = fechaString;
	}



	@Override
	public String toString() {
		return "Reporte [id=" + id + ", idUsuario=" + idUsuario + ", asunto=" + asunto + ", numeroDisco=" + numeroDisco
				+ ", ubicacion=" + ubicacion + ", fecha=" + fecha + ", idCooperativa=" + idCooperativa + ", mensaje="
				+ mensaje + ", estado=" + estado + "]";
	}
	 



	
	
	
	
}

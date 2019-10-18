package com.spring.boot.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.data.geo.Point;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstadoBusTemporal {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("creationDate")
	private Date creationDate;
	
	@JsonProperty("velocidad")
	private int velocidad;
	
	@JsonProperty("cantidadUsuarios")
	private int cantidadUsuarios;
	
	@JsonProperty("posicionActual")
	private Point posicionActual;
	
	@JsonProperty("estadoPuerta")
	private Boolean estadoPuerta;
	
	@JsonProperty("idx")
	private int idx;
	
	@JsonProperty("linea")
	private int linea;
		
	
	public EstadoBusTemporal(int velocidad, int cantidadUsuarios, Point posicionActual,Boolean estadoPuerta,int linea, int idx) {
		super();
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
		this.velocidad = velocidad;
		this.cantidadUsuarios = cantidadUsuarios;
		this.posicionActual = posicionActual;
		this.estadoPuerta = estadoPuerta;
		this.creationDate = now.getTime();
		this.linea = linea;
		this.idx = idx;
	}
	public EstadoBusTemporal(EstadoBusTemporal bus) {
		this.velocidad = bus.velocidad;
		this.cantidadUsuarios = bus.cantidadUsuarios;
		this.posicionActual = bus.posicionActual;
		this.estadoPuerta = bus.estadoPuerta;
		this.creationDate = bus.creationDate;
		this.linea = bus.linea;
	}
	public EstadoBusTemporal() {
		super();
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("America/Guayaquil"));
		this.creationDate = now.getTime();
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	public int getCantidadUsuarios() {
		return cantidadUsuarios;
	}
	public void setCantidadUsuarios(int cantidadUsuarios) {
		this.cantidadUsuarios = cantidadUsuarios;
	}
	public Point getPosicionActual() {
		return posicionActual;
	}
	public void setPosicionActual(Point posicionActual) {
		this.posicionActual = posicionActual;
	}
	public Boolean getEstadoPuerta() {
		return estadoPuerta;
	}
	public void setEstadoPuerta(Boolean estadoPuerta) {
		this.estadoPuerta = estadoPuerta;
	}
	public int getLinea() {
		return linea;
	}
	public void setLinea(int linea) {
		this.linea = linea;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	@Override
	public String toString() {
		return "EstadoBusTemporal [id=" + id + ", creationDate=" + creationDate + ", velocidad=" + velocidad
				+ ", cantidadUsuarios=" + cantidadUsuarios + ", posicionActual=" + posicionActual + ", estadoPuerta="
				+ estadoPuerta + ", idx=" + idx + ", linea=" + linea + "]";
	}
}
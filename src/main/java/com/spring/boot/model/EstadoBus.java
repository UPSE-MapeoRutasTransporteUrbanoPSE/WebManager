package com.spring.boot.model;


import java.util.Calendar;

public class EstadoBus {
	private Calendar creationDate;
	private int velocidad;
	private int cantidadUsuarios;
	private Point posicionActual;
	private Boolean estadoPuerta;
	private String placa;
	private int linea;
	transient String estadoActual;
	transient Integer disco;
	transient Integer capacidad;
	
	public EstadoBus() {}
	
	public EstadoBus(Calendar creationDate, int velocidad, int cantidadUsuarios, Point posicionActual,
			Boolean estadoPuerta,String placa, int linea) {
		super();
		this.creationDate = creationDate;
		this.velocidad = velocidad;
		this.cantidadUsuarios = cantidadUsuarios;
		this.posicionActual = posicionActual;
		this.estadoPuerta = estadoPuerta;
		this.placa = placa;
		this.linea = linea;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
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
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}	

	public String getEstadoActual() {
		return estadoActual;
	}

	public void setEstadoActual(String estadoActual) {
		this.estadoActual = estadoActual;
	}
	
	public Integer getDisco() {
		return disco;
	}

	public void setDisco(Integer disco) {
		this.disco = disco;
	}
	
	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	@Override
	public String toString() {
		return "EstadoBus [creationDate=" + creationDate + ", velocidad=" + velocidad + ", cantidadUsuarios="
				+ cantidadUsuarios + ", posicionActual=" + posicionActual + ", estadoPuerta=" + estadoPuerta
				+ ", linea=" + linea + ", estadoActual=" + estadoActual + "]";
	}	
}

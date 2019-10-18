package com.spring.boot.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;



public class HistorialEstadoBus {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("fecha")	
	private Date fecha;
	
	@JsonProperty("creadoEn")
	private Date creadoEn;
	
	@JsonProperty("placaId")		
	private String placaId;
	
	@JsonProperty("placa")
	private String placa;
	
	@JsonProperty("listaEstados")
	private List<EstadoBus> listaEstados;
	
	
	public HistorialEstadoBus() {}	

	public HistorialEstadoBus(String id, Date fecha, Date creadoEn, String placaId, String placa,
			List<EstadoBus> listaEstados) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.creadoEn = creadoEn;
		this.placaId = placaId;
		this.placa = placa;
		this.listaEstados = listaEstados;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(Date creadoEn) {
		this.creadoEn = creadoEn;
	}

	public String getPlacaId() {
		return placaId;
	}

	public void setPlacaId(String placaId) {
		this.placaId = placaId;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public List<EstadoBus> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<EstadoBus> listaEstados) {
		this.listaEstados = listaEstados;
	}

	@Override
	public String toString() {
		return "HistorialEstadoBus [id=" + id + ", fecha=" + fecha + ", creadoEn=" + creadoEn + ", placaId=" + placaId
				+ ", placa=" + placa + ", listaEstados=" + listaEstados + "]";
	}	
	
}
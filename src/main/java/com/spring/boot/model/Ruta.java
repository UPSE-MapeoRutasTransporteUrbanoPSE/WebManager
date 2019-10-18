package com.spring.boot.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ruta {

	@JsonProperty("id")
	private String id;

	@JsonProperty("linea")
	private String linea;

	@JsonProperty("listasPuntos")
	private List<Point> listasPuntos;

	@JsonProperty("listasParadas")
	private List<String> listasParadas;

	@JsonProperty("lugaresInteres")
	private List<String> lugaresInteres;

	@JsonProperty("listPuntoString")
	private String listPuntoString;

	@JsonProperty("listParadaString")
	private String listParadaString;
	
	@JsonProperty("color")
	private String color;

	@JsonProperty("estado")
	private Boolean estado;

	public Ruta() {
	}

	public Ruta(String id, String linea, List<Point> listasPuntos, List<String> listasParadas,
			List<String> lugaresInteres, String listPuntoString, String listParadaString, Boolean estado) {
		super();
		this.id = id;
		this.listPuntoString = listPuntoString;
		this.listParadaString = listParadaString;
		this.linea = linea;
		this.listasPuntos = listasPuntos;
		this.listasParadas = listasParadas;
		this.lugaresInteres = lugaresInteres;
		this.estado = true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public List<Point> getListasPuntos() {
		return listasPuntos;
	}

	public void setListasPuntos(List<Point> listasPuntos) {
		this.listasPuntos = listasPuntos;
	}

	public List<String> getListasParadas() {
		return listasParadas;
	}

	public void setListasParadas(List<String> listasParadas) {
		this.listasParadas = listasParadas;
	}

	public List<String> getLugaresInteres() {
		return lugaresInteres;
	}

	public void setLugaresInteres(List<String> lugaresInteres) {
		this.lugaresInteres = lugaresInteres;
	}

	public String getListPuntoString() {
		return listPuntoString;
	}

	public void setListPuntoString(String listPuntoString) {
		this.listPuntoString = listPuntoString;
	}

	public String getListParadaString() {
		return listParadaString;
	}

	public void setListParadaString(String listParadaString) {
		this.listParadaString = listParadaString;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}	
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Ruta [id=" + id + ", linea=" + linea + ", listasPuntos=" + listasPuntos + ", listasParadas="
				+ listasParadas + ", lugaresInteres=" + lugaresInteres + ", listPuntoString=" + listPuntoString
				+ ", listParadaString=" + listParadaString + ", estado=" + estado + "]";
	}
}
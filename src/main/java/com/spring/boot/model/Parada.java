package com.spring.boot.model;



import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Representa una Parada
 * @author Davids Adrian Gonzalez Tigrero
 */
public class Parada {
	@JsonProperty("id")
	private String id;
	
	/**
	 * Nombre de Parada
	 */
	@JsonProperty("nombre")
	private String nombre;
	
	/**
	 * Url de Foto.
	 */
	@JsonProperty("urlFoto")
	private String urlFoto;
	
	/**
	 * Posición de Parada.
	 */
	@JsonProperty("coordenada")
    private Point coordenada;
	
	@JsonProperty("estado")
    private Boolean estado;	
	
	public Parada() {	}
	
	
	
	
	
	public Parada(String id, String nombre, String urlFoto, Point coordenada, Boolean estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.urlFoto = urlFoto;
		this.coordenada = coordenada;
		this.estado = estado;
	}





	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUrlFoto() {
		return urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	public Point getCoordenada() {
		return coordenada;
	}
	public void setCoordenada(Point coordenada) {
		this.coordenada = coordenada;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "Parada [id=" + id + ", nombre=" + nombre + ", urlFoto=" + urlFoto + ", coordenada=" + coordenada
				+ ", estado=" + estado + "]";
	}
	
}
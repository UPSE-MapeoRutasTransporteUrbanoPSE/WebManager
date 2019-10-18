package com.spring.boot.seguridad.modelo;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class SegMenu {
	@IdPrefix
	private String prefix = "menu";
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE,delimiter = "::")
	private String id;
	@Field
	private String IdPadre;
	@Field
	private String titulo;
	@Field
	private String url;
	@Field
	private Boolean estado;
	
	public SegMenu() {
		super();
		this.estado = true; 
	}
	
	public SegMenu(String idPadre, String titulo, String url, Boolean estado) {
		super();				
		IdPadre = idPadre;
		this.titulo = titulo;
		this.url = url;
		this.estado = estado;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdPadre() {
		return IdPadre;
	}
	public void setIdPadre(String idPadre) {
		IdPadre = idPadre;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "SegMenu [prefix=" + prefix + ", id=" + id + ", IdPadre=" + IdPadre + ", titulo=" + titulo + ", url="
				+ url + ", estado=" + estado + "]";
	}	
}

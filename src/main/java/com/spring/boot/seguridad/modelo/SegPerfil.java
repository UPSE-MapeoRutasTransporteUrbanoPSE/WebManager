package com.spring.boot.seguridad.modelo;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class SegPerfil {
	
	@IdPrefix
	private String prefix = "perfil";
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE,delimiter = "::")
	private String id;
	@Field
	private String idMenu;
	@Field
	private String nombre;		
	@Field
	private Boolean estado;
	
	
	public SegPerfil() {
		super();
		this.estado = true;
	}


	public SegPerfil(String idMenu, String nombre, Boolean estado) {
		super();
		this.idMenu = idMenu;
		this.nombre = nombre;
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


	public String getIdMenu() {
		return idMenu;
	}


	public void setIdMenu(String idMenu) {
		this.idMenu = idMenu;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Boolean getEstado() {
		return estado;
	}


	public void setEstado(Boolean estado) {
		this.estado = estado;
	}


	@Override
	public String toString() {
		return "SegPerfil [prefix=" + prefix + ", id=" + id + ", idMenu=" + idMenu + ", nombre=" + nombre + ", estado="
				+ estado + "]";
	}
			
	
}

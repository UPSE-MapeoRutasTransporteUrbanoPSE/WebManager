package com.spring.boot.seguridad.modelo;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class SegSubMenu {
	@IdPrefix
	private String prefix = "submenu";
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE,delimiter = "::")
	private String id;	
	@Field
	private String subtitulo;
	@Field
	private String idMenu;
	@Field
	private String suburl;
	@Field
	private Boolean estado;
	
	public SegSubMenu() {
		super();
		this.estado = true;
	}
}

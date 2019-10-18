package com.spring.boot.seguridad.modelo;

import javax.validation.constraints.Email;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdPrefix;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class SegUsuario {
	@IdPrefix
	private String prefix = "usuario";
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE,delimiter = "::")
	private String id;
	@Field
	private String idPerfil;
	@Field
	private String usuario;
	@Field
	private String clave;
	@Field
	private String movil;
	@Field
	@Email(message="Correo no valido")
	private String email;	
	@Field
	private Boolean estado;
	
	public SegUsuario() {
		super();
		this.estado = true;
	}

	public SegUsuario(String idPerfil, String usuario, String clave, String movil,
			@Email(message = "Correo no valido") String email, Boolean estado) {
		super();
		this.idPerfil = idPerfil;
		this.usuario = usuario;
		this.clave = clave;
		this.movil = movil;
		this.email = email;
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

	public String getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "SegUsuario [prefix=" + prefix + ", id=" + id + ", idPerfil=" + idPerfil + ", usuario=" + usuario
				+ ", clave=" + clave + ", movil=" + movil + ", email=" + email + ", estado=" + estado + "]";
	}
	
	
	
}

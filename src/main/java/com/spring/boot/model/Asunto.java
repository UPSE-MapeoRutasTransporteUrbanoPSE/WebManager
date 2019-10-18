package com.spring.boot.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Asunto {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("asuntos")
	private List<String> asuntos = new ArrayList<String>();
	
	
	public Asunto() {}
	
	public Asunto(String id, List<String> asuntos) {
		super();
		this.id = "Asuntos";
		this.asuntos = asuntos;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getAsuntos() {
		return asuntos;
	}
	public void setAsuntos(List<String> asuntos) {
		this.asuntos = asuntos;
	}
}

package com.spring.boot.model;

public class CooperativaGraphModel {
	
	private transient String nombreCooperativa;
	private transient Integer valor;
	
	
	public CooperativaGraphModel() {}
	
	public CooperativaGraphModel(String nombreCooperativa, Integer valor) {
		super();
		this.nombreCooperativa = nombreCooperativa;
		this.valor = valor;
	}

	public String getNombreCooperativa() {
		return nombreCooperativa;
	}

	public void setNombreCooperativa(String nombreCooperativa) {
		this.nombreCooperativa = nombreCooperativa;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "CooperativaGraphModel [nombreCooperativa=" + nombreCooperativa + ", valor=" + valor + "]";
	}
	
	
	

}

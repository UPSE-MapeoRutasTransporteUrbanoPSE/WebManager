package com.spring.boot.model;

import java.util.List;

public class ReporteModel {
	
	
	private String code;

	private List<Reporte> listaDenuncia;

	public ReporteModel(String code,List<Reporte> listaDenuncia) {
		super();
		this.code = code;
		this.listaDenuncia = listaDenuncia;
	}
	
	

	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public List<Reporte> getListaDenuncia() {
		return listaDenuncia;
	}

	public void setListaDenuncia(List<Reporte> listaDenuncia) {
		this.listaDenuncia = listaDenuncia;
	}
	
	
}

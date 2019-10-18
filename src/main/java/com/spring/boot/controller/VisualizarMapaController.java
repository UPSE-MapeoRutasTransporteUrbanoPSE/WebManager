package com.spring.boot.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.boot.constant.DeclaracionVariable;

@Controller
@RequestMapping("/mapa")
public class VisualizarMapaController {
	private static final Log logMapa = LogFactory.getLog(VisualizarMapaController.class);
	
	
	@GetMapping("/calor")
	public String mostrarMapaCalor(Model model) {
		
		
	
		
		return DeclaracionVariable.GS_MAPA_CALOR;
	}
		

}

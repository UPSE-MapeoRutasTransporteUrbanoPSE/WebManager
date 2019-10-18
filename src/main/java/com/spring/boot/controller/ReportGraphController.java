package com.spring.boot.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.model.CooperativaGraphModel;
import com.spring.boot.model.Reporte;
import com.spring.boot.services.ServicioDenuncia;
import com.spring.boot.services.ServicioDenunciaGraph;

@RestController
@RequestMapping("/reporteQuejaGraph")
@SuppressWarnings("unused")
public class ReportGraphController {
	private static final Log LogReporteQuejaCooperativa = LogFactory.getLog(ReportGraphController.class);
	
	private static List<CooperativaGraphModel> listaReporteCooperativaGraph = new ArrayList<CooperativaGraphModel>();

	@Resource
	private ServicioDenunciaGraph servicioDenunciaG;
	
	@GetMapping("/generarPdfGraph")
	public List<CooperativaGraphModel> ReportGraph() {
		
		listaReporteCooperativaGraph.clear();
		
		CooperativaGraphModel cgm= new CooperativaGraphModel();
		cgm.setNombreCooperativa("horizonte");
		cgm.setValor(50);
		listaReporteCooperativaGraph.add(cgm);
		
		CooperativaGraphModel cgm1= new CooperativaGraphModel();
		cgm1.setNombreCooperativa("trancisa");
		cgm1.setValor(20);
		listaReporteCooperativaGraph.add(cgm1);
		
		CooperativaGraphModel cgm2= new CooperativaGraphModel();
		cgm2.setNombreCooperativa("unificacion");
		cgm2.setValor(30);		
		listaReporteCooperativaGraph.add(cgm2);
		
		
		
		return listaReporteCooperativaGraph;
	}
	
	
	
	@GetMapping("/listaReporteQuejaCooperativaPdfGraph")
	public void listaReporteQuejaPdf(HttpServletResponse response) throws IOException {

		try {

				
		
				File file = servicioDenunciaG.generarDenunciaPdf(ReportGraph(), Locale.ENGLISH);
				response.setContentType("application/pdf");
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
		

			

				LogReporteQuejaCooperativa.info("archivo generado");
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
}

package com.spring.boot.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import com.spring.boot.model.Reporte;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class ServicioDenuncia {

	private static final Log LogServicioDenucia = LogFactory.getLog(ServicioDenuncia.class);
	
	// Path to the jrxml template
		private final String urlDenunciaJasper = "/reportes/reporteDenuncia.jrxml";
		
	 public File generarDenunciaPdf(List<Reporte> listaDenuncia, Locale locale) throws IOException {

		 
	        // creacion de un archivo temporal
	        File pdfFile = File.createTempFile("denuncia", ".pdf");
	        
	       LogServicioDenucia.info(" archivo temporal es "+pdfFile.toString());

	        // inicializar un FileOutputStream
	        try(FileOutputStream pos = new FileOutputStream(pdfFile))
	        {
	        	final JasperReport report = cargarVista();
	        	
	        	// creacion de parametros
	        	LogServicioDenucia.info(" listaDenuncia "+listaDenuncia.toString());

	            // creacion de un datasource vacio.
	            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDenuncia);
	            LogServicioDenucia.info("dataSource " + dataSource.toString());
	            final Map<String, Object> parameters = parameters(dataSource, locale);
	            
	         // Render the PDF file
	            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);
	            
	            
	            
	            
	        }
	        
	        
			
	        catch (final Exception e)
	        {
	        	LogServicioDenucia.error(String.format("ha ocurido un error al crear el archivo pfd: %s", e));
	            
	        }
	        return pdfFile;
	      }
	 
	 
	// llenar vista con parametros
	    private Map<String, Object> parameters(JRBeanCollectionDataSource dataSource, Locale locale) {
	        final Map<String, Object> parameters = new HashMap<>();

	        String urlLogo = "/static/imgs/logoAnt.jpg/";
	        String urlLogoBuses = "/static/imgs/logoBuses.png/";
	        
	        
	        
	        
	        parameters.put("logo", getClass().getResourceAsStream(urlLogo));
	        parameters.put("logo1", getClass().getResourceAsStream(urlLogoBuses));
	        parameters.put("denuncia",  dataSource);
	        
	        parameters.put("REPORT_LOCALE", locale);
	        
	        

	        return parameters;
	    }
	 
	 
	// cargar denuncia jrxml vista
	    private JasperReport cargarVista() throws JRException {
	    	
	    	LogServicioDenucia.info(String.format("urlDenunciaJasper : %s", urlDenunciaJasper));

	        
	        final InputStream reportInputStream = getClass().getResourceAsStream(urlDenunciaJasper);
	        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

	        return JasperCompileManager.compileReport(jasperDesign);
	    }
}

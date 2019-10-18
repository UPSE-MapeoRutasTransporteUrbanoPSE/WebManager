package com.spring.boot.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.constant.DeclaracionVariable;
import com.spring.boot.constant.HeadersAuth;
import com.spring.boot.model.Ruta;

@Controller
@RequestMapping("/archivo")
public class ArchivoGpxController {

	private static final Log logArchivoGpx = LogFactory.getLog(ArchivoGpxController.class);
	HeadersAuth headersAuth = new HeadersAuth();

	@PostMapping(value = "/subirArchivo")
	public String subirArchivo(@RequestParam("file") MultipartFile file, @RequestParam("linea") String linea,
			@RequestParam("color") String color, Model model) throws IOException {

		ResponseEntity<List<Ruta>> mapaLineas;
		Boolean existe = false;

		if (!file.isEmpty()) {
			RestTemplate restTemplateRuta = new RestTemplate();
			try {
				HttpHeaders headers = new HttpHeaders();
				headers = (HttpHeaders) headersAuth.headers();
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);

				MultiValueMap<String, Object> parametros = new LinkedMultiValueMap<>();
				parametros.add("file", getUserFileResource(file));
				parametros.add("linea", linea);
				parametros.add("color", color);

				mapaLineas = restTemplateRuta.exchange(DeclaracionVariable.DIR_WEB_SERVICE_LINEA, HttpMethod.GET,
						new HttpEntity<String>(headersAuth.headers()), new ParameterizedTypeReference<List<Ruta>>() {
						});

				for (int i = 0; i < mapaLineas.getBody().size(); i++) {
					if (mapaLineas.getBody().get(i).getLinea().equals(linea)) {
						existe = true;
					}
				}

				if (existe) {					
					restTemplateRuta.exchange(DeclaracionVariable.DIR_WEB_SERVICE_SUBIR_ARCHIVO, HttpMethod.PUT,
							new HttpEntity<MultiValueMap<String, Object>>(parametros, headers), Ruta.class);
				} else {					
					restTemplateRuta.exchange(DeclaracionVariable.DIR_WEB_SERVICE_SUBIR_ARCHIVO, HttpMethod.POST,
							new HttpEntity<MultiValueMap<String, Object>>(parametros, headers), Ruta.class);
				}

				restTemplateRuta.setErrorHandler(new DefaultResponseErrorHandler() {
					protected boolean hasError(HttpStatus statusCode) {
						return false;
					}
				});

			} catch (HttpClientErrorException e) {
				logArchivoGpx.error(e.getStatusCode());
				logArchivoGpx.error(e.getStatusText());
				logArchivoGpx.error(e.getMostSpecificCause());
				logArchivoGpx.error("la causa " + e.getCause());
				logArchivoGpx.info("no sube bien " + restTemplateRuta.getErrorHandler().hashCode());
			}

		} else {
			model.addAttribute("message", "POR FAVOR SELECCIONE EL ARCHIVO...!!");
		}
		return DeclaracionVariable.INDEX;

	}

	public static Resource getUserFileResource(MultipartFile fileModelo) throws IOException {
		Path tempFile = Files.createTempFile("ruta", ".gpx");
		Files.write(tempFile, fileModelo.getBytes());
		File file = tempFile.toFile();
		return new FileSystemResource(file);
	}
}

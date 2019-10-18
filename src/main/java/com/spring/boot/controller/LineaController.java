package com.spring.boot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.spring.boot.constant.DeclaracionVariable;
import com.spring.boot.constant.HeadersAuth;
import com.spring.boot.model.Parada;
import com.spring.boot.model.Point;
import com.spring.boot.model.Ruta;

@Controller
@RequestMapping("/linea")
public class LineaController {
	private static final Log logLinea = LogFactory.getLog(LineaController.class);
	HeadersAuth headersAuth = new HeadersAuth();
	private static Integer linea = 0;
	private static Integer linea8 = 0;
	private static Integer linea11 = 0;
	

	RestTemplate restTemplateParadas = new RestTemplate();
	

	@GetMapping("/linea7")
	public String mostrarLinea7(Model model) throws IOException {
		logLinea.info("mostrarLinea7()");

		RestTemplate restTemplateLinea = new RestTemplate();
		try {
			linea = 7;
			linea8 = 8;
			linea11 = 11;

			ResponseEntity<Parada> parada = null;
			ResponseEntity<Parada> parada8 = null;
			ResponseEntity<Parada> parada11 = null;
			List<Parada> ListaParadas = new ArrayList<Parada>();
			List<Parada> ListaParadas8 = new ArrayList<Parada>();
			List<Parada> ListaParadas11 = new ArrayList<Parada>();

			String paradaLatLng7 = "";
			String paradaLatLng8 = "";
			String paradaLatLng11 = "";
			HttpEntity<String> entity = new HttpEntity<String>(headersAuth.headers());

			logLinea.info("antes");
			ResponseEntity<Ruta> ruta = restTemplateLinea.exchange(
					DeclaracionVariable.DIR_WEB_SERVICE_LINEA + "/" + linea, HttpMethod.GET, entity, Ruta.class);
			ResponseEntity<Ruta> ruta8 = restTemplateLinea.exchange(
					DeclaracionVariable.DIR_WEB_SERVICE_LINEA + "/" + linea8, HttpMethod.GET, entity, Ruta.class);
			ResponseEntity<Ruta> ruta11 = restTemplateLinea.exchange(
					DeclaracionVariable.DIR_WEB_SERVICE_LINEA + "/" + linea11, HttpMethod.GET, entity, Ruta.class);

			logLinea.info("despues " + ruta.getBody().toString());

			List<Point> listaRuta = ruta.getBody().getListasPuntos();
			List<Point> listaRuta8 = ruta8.getBody().getListasPuntos();
			List<Point> listaRuta11 = ruta11.getBody().getListasPuntos();

			String rutaLatLng = "";
			String rutaLatLng8 = "";
			String rutaLatLng11 = "";

			try {
				for (int i = 0; i < ruta.getBody().getListasParadas().size(); i++) {

					logLinea.info("ListaParadas sixe body" + ruta.getBody().getListasParadas().size());
					logLinea.info("idParada " + ruta.getBody().getListasParadas().get(i));
					parada = restTemplateParadas.exchange(DeclaracionVariable.DIR_WEB_SERVICE_PARADAS + "/"
							+ ruta.getBody().getListasParadas().get(i), HttpMethod.GET, entity, Parada.class); // peticion
																												// de un
																												// objeto
					logLinea.info("si pasa del metodo");
					logLinea.info("parada.getBody()" + parada.getBody().toString());
					ListaParadas.add(parada.getBody());
					logLinea.info("error en la lista paradas ");
				}
			} catch (Exception e) {
				System.out.println("error " + e);
			}

			try {
				for (int i = 0; i < ruta8.getBody().getListasParadas().size(); i++) {

					logLinea.info("ListaParadas sixe body" + ruta8.getBody().getListasParadas().size());
					logLinea.info("idParada " + ruta8.getBody().getListasParadas().get(i));
					parada8 = restTemplateParadas.exchange(DeclaracionVariable.DIR_WEB_SERVICE_PARADAS + "/"
							+ ruta8.getBody().getListasParadas().get(i), HttpMethod.GET, entity, Parada.class); // peticion
																												// de un
																												// objeto
					logLinea.info("si pasa del metodo");
					logLinea.info("parada.getBody()" + parada8.getBody().toString());
					ListaParadas8.add(parada8.getBody());
					logLinea.info("error en la lista paradas ");
				}
			} catch (Exception e) {
				System.out.println("error " + e);
			}

			try {
				for (int i = 0; i < ruta11.getBody().getListasParadas().size(); i++) {

					logLinea.info("ListaParadas sixe body" + ruta11.getBody().getListasParadas().size());
					logLinea.info("idParada " + ruta11.getBody().getListasParadas().get(i));
					parada11 = restTemplateParadas.exchange(DeclaracionVariable.DIR_WEB_SERVICE_PARADAS + "/"
							+ ruta11.getBody().getListasParadas().get(i), HttpMethod.GET, entity, Parada.class); // peticion
																													// de
																													// un
																													// objeto
					logLinea.info("si pasa del metodo");
					logLinea.info("parada.getBody()" + parada11.getBody().toString());
					ListaParadas11.add(parada11.getBody());
					logLinea.info("error en la lista paradas ");
				}
			} catch (Exception e) {
				System.out.println("error " + e);
			}

			// logLinea.info("listaParadas " + ListaParadas.toString());

			for (int i = 0; i < ListaParadas.size(); i++) {
				paradaLatLng7 += "{" + "lat: " + ListaParadas.get(i).getCoordenada().getY() + ", lng: "
						+ ListaParadas.get(i).getCoordenada().getX() + "},";
			}

			for (int i = 0; i < ListaParadas8.size(); i++) {
				paradaLatLng8 += "{" + "lat: " + ListaParadas8.get(i).getCoordenada().getY() + ", lng: "
						+ ListaParadas8.get(i).getCoordenada().getX() + "},";
			}

			for (int i = 0; i < ListaParadas11.size(); i++) {
				paradaLatLng11 += "{" + "lat: " + ListaParadas11.get(i).getCoordenada().getY() + ", lng: "
						+ ListaParadas11.get(i).getCoordenada().getX() + "},";
			}

			for (Point point : listaRuta) {
				rutaLatLng += "{" + "lat: " + point.getY() + ", lng: " + point.getX() + "},";
			}

			for (Point point : listaRuta8) {
				rutaLatLng8 += "{" + "lat: " + point.getY() + ", lng: " + point.getX() + "},";
			}

			for (Point point : listaRuta11) {
				rutaLatLng11 += "{" + "lat: " + point.getY() + ", lng: " + point.getX() + "},";
			}

			if (rutaLatLng.endsWith(",")) {
				rutaLatLng = rutaLatLng.substring(0, rutaLatLng.length() - 1);
			}

			if (rutaLatLng8.endsWith(",")) {
				rutaLatLng8 = rutaLatLng8.substring(0, rutaLatLng8.length() - 1);
			}

			if (rutaLatLng11.endsWith(",")) {
				rutaLatLng11 = rutaLatLng11.substring(0, rutaLatLng11.length() - 1);
			}

			if (paradaLatLng7.endsWith(",")) {
				paradaLatLng7 = paradaLatLng7.substring(0, paradaLatLng7.length() - 1);
			}

			if (paradaLatLng8.endsWith(",")) {
				paradaLatLng8 = paradaLatLng8.substring(0, paradaLatLng8.length() - 1);
			}

			if (paradaLatLng11.endsWith(",")) {
				paradaLatLng11 = paradaLatLng11.substring(0, paradaLatLng11.length() - 1);
			}

			rutaLatLng = "[" + rutaLatLng + "]";
			paradaLatLng7 = "[" + paradaLatLng7 + "]";
			rutaLatLng8 = "[" + rutaLatLng8 + "]";
			paradaLatLng8 = "[" + paradaLatLng8 + "]";
			rutaLatLng11 = "[" + rutaLatLng11 + "]";
			paradaLatLng11 = "[" + paradaLatLng11 + "]";
			
			
			model.addAttribute("rutaJava7", rutaLatLng);
			model.addAttribute("linea", linea);
			model.addAttribute("paradas7", paradaLatLng7);
			model.addAttribute("paradas8", paradaLatLng8);
			model.addAttribute("paradas11", paradaLatLng11);

			System.out.println("ruta " + rutaLatLng);
			System.out.println("paradas 7 " + paradaLatLng7);
			System.out.println("paradas 8 " + paradaLatLng8);
			System.out.println("paradas 11 " + paradaLatLng11);
			System.out.println("linea " + linea);

			restTemplateLinea.setErrorHandler(new DefaultResponseErrorHandler() {
				protected boolean hasError(HttpStatus statusCode) {
					return false;
				}
			});

		} catch (HttpClientErrorException e) {
			System.out.println("error " + e.getMessage());

		}

		return DeclaracionVariable.INDEX;

	}

	@GetMapping("/linea8")
	public String mostrarLinea8(Model model) throws IOException {
		logLinea.info("mostrarLinea8()");

		RestTemplate restTemplateLinea = new RestTemplate();
		try {
			linea = 7;
			linea8 = 8;
			linea11 = 11;

			ResponseEntity<Parada> parada = null;
			ResponseEntity<Parada> parada8 = null;
			ResponseEntity<Parada> parada11 = null;
			List<Parada> ListaParadas = new ArrayList<Parada>();
			List<Parada> ListaParadas8 = new ArrayList<Parada>();
			List<Parada> ListaParadas11 = new ArrayList<Parada>();

			String paradaLatLng7 = "";
			String paradaLatLng8 = "";
			String paradaLatLng11 = "";

			HttpEntity<String> entity = new HttpEntity<String>(headersAuth.headers());

			logLinea.info("antes");
			ResponseEntity<Ruta> ruta = restTemplateLinea.exchange(
					DeclaracionVariable.DIR_WEB_SERVICE_LINEA + "/" + linea, HttpMethod.GET, entity, Ruta.class);
			ResponseEntity<Ruta> ruta8 = restTemplateLinea.exchange(
					DeclaracionVariable.DIR_WEB_SERVICE_LINEA + "/" + linea8, HttpMethod.GET, entity, Ruta.class);
			ResponseEntity<Ruta> ruta11 = restTemplateLinea.exchange(
					DeclaracionVariable.DIR_WEB_SERVICE_LINEA + "/" + linea11, HttpMethod.GET, entity, Ruta.class);

			logLinea.info("despues " + ruta.getBody().toString());

			List<Point> listaRuta = ruta.getBody().getListasPuntos();
			List<Point> listaRuta8 = ruta8.getBody().getListasPuntos();
			List<Point> listaRuta11 = ruta11.getBody().getListasPuntos();

			String rutaLatLng = "";
			String rutaLatLng8 = "";
			String rutaLatLng11 = "";

			try {
				for (int i = 0; i < ruta.getBody().getListasParadas().size(); i++) {

					logLinea.info("ListaParadas sixe body" + ruta.getBody().getListasParadas().size());
					logLinea.info("idParada8 " + ruta.getBody().getListasParadas().get(i));
					parada = restTemplateParadas.exchange(DeclaracionVariable.DIR_WEB_SERVICE_PARADAS + "/"
							+ ruta.getBody().getListasParadas().get(i), HttpMethod.GET, entity, Parada.class); // peticion
																												// de un
																												// objeto
					logLinea.info("si pasa del metodo");
					logLinea.info("parada.getBody()" + parada.getBody().toString());
					ListaParadas.add(parada.getBody());
					logLinea.info("error en la lista paradas ");
				}
			} catch (Exception e) {
				System.out.println("error " + e);
			}

			try {
				for (int i = 0; i < ruta8.getBody().getListasParadas().size(); i++) {

					logLinea.info("ListaParadas sixe body" + ruta8.getBody().getListasParadas().size());
					logLinea.info("idParada " + ruta8.getBody().getListasParadas().get(i));
					parada8 = restTemplateParadas.exchange(DeclaracionVariable.DIR_WEB_SERVICE_PARADAS + "/"
							+ ruta8.getBody().getListasParadas().get(i), HttpMethod.GET, entity, Parada.class); // peticion
																												// de un
																												// objeto
					logLinea.info("si pasa del metodo");
					logLinea.info("parada.getBody()" + parada8.getBody().toString());
					ListaParadas8.add(parada8.getBody());
					logLinea.info("error en la lista paradas ");
				}
			} catch (Exception e) {
				System.out.println("error " + e);
			}

			try {
				for (int i = 0; i < ruta11.getBody().getListasParadas().size(); i++) {

					logLinea.info("ListaParadas sixe body" + ruta11.getBody().getListasParadas().size());
					logLinea.info("idParada " + ruta11.getBody().getListasParadas().get(i));
					parada11 = restTemplateParadas.exchange(DeclaracionVariable.DIR_WEB_SERVICE_PARADAS + "/"
							+ ruta11.getBody().getListasParadas().get(i), HttpMethod.GET, entity, Parada.class); // peticion
																													// de
																													// un
																													// objeto
					logLinea.info("si pasa del metodo");
					logLinea.info("parada.getBody()" + parada11.getBody().toString());
					ListaParadas11.add(parada11.getBody());
					logLinea.info("error en la lista paradas ");
				}
			} catch (Exception e) {
				System.out.println("error " + e);
			}

			// logLinea.info("listaParadas " + ListaParadas.toString());

			for (int i = 0; i < ListaParadas.size(); i++) {
				paradaLatLng7 += "{" + "lat: " + ListaParadas.get(i).getCoordenada().getY() + ", lng: "
						+ ListaParadas.get(i).getCoordenada().getX() + "},";
			}

			for (int i = 0; i < ListaParadas8.size(); i++) {
				paradaLatLng8 += "{" + "lat: " + ListaParadas8.get(i).getCoordenada().getY() + ", lng: "
						+ ListaParadas8.get(i).getCoordenada().getX() + "},";
			}

			for (int i = 0; i < ListaParadas11.size(); i++) {
				paradaLatLng11 += "{" + "lat: " + ListaParadas11.get(i).getCoordenada().getY() + ", lng: "
						+ ListaParadas11.get(i).getCoordenada().getX() + "},";
			}

			for (Point point : listaRuta) {
				rutaLatLng += "{" + "lat: " + point.getY() + ", lng: " + point.getX() + "},";
			}

			for (Point point : listaRuta8) {
				rutaLatLng8 += "{" + "lat: " + point.getY() + ", lng: " + point.getX() + "},";
			}

			for (Point point : listaRuta11) {
				rutaLatLng11 += "{" + "lat: " + point.getY() + ", lng: " + point.getX() + "},";
			}

			if (rutaLatLng.endsWith(",")) {
				rutaLatLng = rutaLatLng.substring(0, rutaLatLng.length() - 1);
			}

			if (rutaLatLng8.endsWith(",")) {
				rutaLatLng8 = rutaLatLng8.substring(0, rutaLatLng8.length() - 1);
			}

			if (rutaLatLng11.endsWith(",")) {
				rutaLatLng11 = rutaLatLng11.substring(0, rutaLatLng11.length() - 1);
			}

			if (paradaLatLng7.endsWith(",")) {
				paradaLatLng7 = paradaLatLng7.substring(0, paradaLatLng7.length() - 1);
			}

			if (paradaLatLng8.endsWith(",")) {
				paradaLatLng8 = paradaLatLng8.substring(0, paradaLatLng8.length() - 1);
			}

			if (paradaLatLng11.endsWith(",")) {
				paradaLatLng11 = paradaLatLng11.substring(0, paradaLatLng11.length() - 1);
			}

			rutaLatLng = "[" + rutaLatLng + "]";
			paradaLatLng7 = "[" + paradaLatLng7 + "]";
			rutaLatLng8 = "[" + rutaLatLng8 + "]";
			paradaLatLng8 = "[" + paradaLatLng8 + "]";
			rutaLatLng11 = "[" + rutaLatLng11 + "]";
			paradaLatLng11 = "[" + paradaLatLng11 + "]";
			model.addAttribute("rutaJava8", rutaLatLng8);
			model.addAttribute("linea", linea8);
			model.addAttribute("paradas7", paradaLatLng7);
			model.addAttribute("paradas8", paradaLatLng8);
			model.addAttribute("paradas11", paradaLatLng11);

			System.out.println("ruta8 " + rutaLatLng8);
			System.out.println("paradas 7 " + paradaLatLng7);
			System.out.println("paradas 8 " + paradaLatLng8);
			System.out.println("paradas 11 " + paradaLatLng11);
			System.out.println("linea8 " + linea8);

			restTemplateLinea.setErrorHandler(new DefaultResponseErrorHandler() {
				protected boolean hasError(HttpStatus statusCode) {
					return false;
				}
			});

		} catch (HttpClientErrorException e) {
			System.out.println("error " + e.getMessage());

		}

		return DeclaracionVariable.INDEX;

	}

	@GetMapping("/linea11")
	public String mostrarLinea11(Model model) throws IOException {
		logLinea.info("mostrarLinea7()");

		RestTemplate restTemplateLinea = new RestTemplate();
		try {
			linea = 7;
			linea8 = 8;
			linea11 = 11;

			ResponseEntity<Parada> parada = null;
			ResponseEntity<Parada> parada8 = null;
			ResponseEntity<Parada> parada11 = null;
			List<Parada> ListaParadas = new ArrayList<Parada>();
			List<Parada> ListaParadas8 = new ArrayList<Parada>();
			List<Parada> ListaParadas11 = new ArrayList<Parada>();

			String paradaLatLng7 = "";
			String paradaLatLng8 = "";
			String paradaLatLng11 = "";

			HttpEntity<String> entity = new HttpEntity<String>(headersAuth.headers());

			logLinea.info("antes");
			ResponseEntity<Ruta> ruta = restTemplateLinea.exchange(
					DeclaracionVariable.DIR_WEB_SERVICE_LINEA + "/" + linea, HttpMethod.GET, entity, Ruta.class);
			ResponseEntity<Ruta> ruta8 = restTemplateLinea.exchange(
					DeclaracionVariable.DIR_WEB_SERVICE_LINEA + "/" + linea8, HttpMethod.GET, entity, Ruta.class);
			ResponseEntity<Ruta> ruta11 = restTemplateLinea.exchange(
					DeclaracionVariable.DIR_WEB_SERVICE_LINEA + "/" + linea11, HttpMethod.GET, entity, Ruta.class);

			logLinea.info("despues " + ruta.getBody().toString());

			List<Point> listaRuta = ruta.getBody().getListasPuntos();
			List<Point> listaRuta8 = ruta8.getBody().getListasPuntos();
			List<Point> listaRuta11 = ruta11.getBody().getListasPuntos();

			String rutaLatLng = "";
			String rutaLatLng8 = "";
			String rutaLatLng11 = "";

			try {
				for (int i = 0; i < ruta.getBody().getListasParadas().size(); i++) {

					logLinea.info("ListaParadas sixe body" + ruta.getBody().getListasParadas().size());
					logLinea.info("idParada " + ruta.getBody().getListasParadas().get(i));
					parada = restTemplateParadas.exchange(DeclaracionVariable.DIR_WEB_SERVICE_PARADAS + "/"
							+ ruta.getBody().getListasParadas().get(i), HttpMethod.GET, entity, Parada.class); // peticion
																												// de un
																												// objeto
					logLinea.info("si pasa del metodo");
					logLinea.info("parada.getBody()" + parada.getBody().toString());
					ListaParadas.add(parada.getBody());
					logLinea.info("error en la lista paradas ");
				}
			} catch (Exception e) {
				System.out.println("error " + e);
			}

			try {
				for (int i = 0; i < ruta8.getBody().getListasParadas().size(); i++) {

					logLinea.info("ListaParadas sixe body" + ruta8.getBody().getListasParadas().size());
					logLinea.info("idParada " + ruta8.getBody().getListasParadas().get(i));
					parada8 = restTemplateParadas.exchange(DeclaracionVariable.DIR_WEB_SERVICE_PARADAS + "/"
							+ ruta8.getBody().getListasParadas().get(i), HttpMethod.GET, entity, Parada.class); // peticion
																												// de un
																												// objeto
					logLinea.info("si pasa del metodo");
					logLinea.info("parada.getBody()" + parada8.getBody().toString());
					ListaParadas8.add(parada8.getBody());
					logLinea.info("error en la lista paradas ");
				}
			} catch (Exception e) {
				System.out.println("error " + e);
			}

			try {
				for (int i = 0; i < ruta11.getBody().getListasParadas().size(); i++) {

					logLinea.info("ListaParadas sixe body" + ruta11.getBody().getListasParadas().size());
					logLinea.info("idParada " + ruta11.getBody().getListasParadas().get(i));
					parada11 = restTemplateParadas.exchange(DeclaracionVariable.DIR_WEB_SERVICE_PARADAS + "/"
							+ ruta11.getBody().getListasParadas().get(i), HttpMethod.GET, entity, Parada.class); // peticion
																													// de
																													// un
																													// objeto
					logLinea.info("si pasa del metodo");
					logLinea.info("parada.getBody()" + parada11.getBody().toString());
					ListaParadas11.add(parada11.getBody());
					logLinea.info("error en la lista paradas ");
				}
			} catch (Exception e) {
				System.out.println("error " + e);
			}

			// logLinea.info("listaParadas " + ListaParadas.toString());

			for (int i = 0; i < ListaParadas.size(); i++) {
				paradaLatLng7 += "{" + "lat: " + ListaParadas.get(i).getCoordenada().getY() + ", lng: "
						+ ListaParadas.get(i).getCoordenada().getX() + "},";
			}

			for (int i = 0; i < ListaParadas8.size(); i++) {
				paradaLatLng8 += "{" + "lat: " + ListaParadas8.get(i).getCoordenada().getY() + ", lng: "
						+ ListaParadas8.get(i).getCoordenada().getX() + "},";
			}

			for (int i = 0; i < ListaParadas11.size(); i++) {
				paradaLatLng11 += "{" + "lat: " + ListaParadas11.get(i).getCoordenada().getY() + ", lng: "
						+ ListaParadas11.get(i).getCoordenada().getX() + "},";
			}

			for (Point point : listaRuta) {
				rutaLatLng += "{" + "lat: " + point.getY() + ", lng: " + point.getX() + "},";
			}

			for (Point point : listaRuta8) {
				rutaLatLng8 += "{" + "lat: " + point.getY() + ", lng: " + point.getX() + "},";
			}

			for (Point point : listaRuta11) {
				rutaLatLng11 += "{" + "lat: " + point.getY() + ", lng: " + point.getX() + "},";
			}

			if (rutaLatLng.endsWith(",")) {
				rutaLatLng = rutaLatLng.substring(0, rutaLatLng.length() - 1);
			}

			if (rutaLatLng8.endsWith(",")) {
				rutaLatLng8 = rutaLatLng8.substring(0, rutaLatLng8.length() - 1);
			}

			if (rutaLatLng11.endsWith(",")) {
				rutaLatLng11 = rutaLatLng11.substring(0, rutaLatLng11.length() - 1);
			}

			if (paradaLatLng7.endsWith(",")) {
				paradaLatLng7 = paradaLatLng7.substring(0, paradaLatLng7.length() - 1);
			}

			if (paradaLatLng8.endsWith(",")) {
				paradaLatLng8 = paradaLatLng8.substring(0, paradaLatLng8.length() - 1);
			}

			if (paradaLatLng11.endsWith(",")) {
				paradaLatLng11 = paradaLatLng11.substring(0, paradaLatLng11.length() - 1);
			}

			rutaLatLng = "[" + rutaLatLng + "]";
			paradaLatLng7 = "[" + paradaLatLng7 + "]";
			rutaLatLng8 = "[" + rutaLatLng8 + "]";
			paradaLatLng8 = "[" + paradaLatLng8 + "]";
			rutaLatLng11 = "[" + rutaLatLng11 + "]";
			paradaLatLng11 = "[" + paradaLatLng11 + "]";
			model.addAttribute("rutaJava11", rutaLatLng11);
			model.addAttribute("linea", linea11);
			model.addAttribute("paradas7", paradaLatLng7);
			model.addAttribute("paradas8", paradaLatLng8);
			model.addAttribute("paradas11", paradaLatLng11);

			System.out.println("ruta11 " + rutaLatLng11);
			System.out.println("paradas 7 " + paradaLatLng7);
			System.out.println("paradas 8 " + paradaLatLng8);
			System.out.println("paradas 11 " + paradaLatLng11);
			System.out.println("linea11 " + linea11);

			restTemplateLinea.setErrorHandler(new DefaultResponseErrorHandler() {
				protected boolean hasError(HttpStatus statusCode) {
					return false;
				}
			});

		} catch (HttpClientErrorException e) {
			System.out.println("error " + e.getMessage());

		}

		return DeclaracionVariable.INDEX;

	}

}

package com.spring.boot.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.spring.boot.constant.DeclaracionVariable;
import com.spring.boot.constant.HeadersAuth;
import com.spring.boot.model.Bus;
import com.spring.boot.model.Cooperativa;
import com.spring.boot.model.EstadoBus;
import com.spring.boot.model.Point;
import com.spring.boot.model.Ruta;

import jdk.nashorn.internal.parser.JSONParser;

@Controller
@RequestMapping("/mapa")
@SuppressWarnings("unused")
@EnableAsync
public class MapaLinea {
	private static final Log logMapaLinea = LogFactory.getLog(MapaLinea.class);

	RestTemplate restTemplateMapaLinea = new RestTemplate();
	HeadersAuth headersAuth = new HeadersAuth();

	private static ResponseEntity<Ruta> mapaLinea;
	private static ResponseEntity<List<Ruta>> mapaLineas;
	private static ResponseEntity<EstadoBus> estadoActualBus;
	private static ResponseEntity<List<EstadoBus>> estadoActualBuses;
	private static ResponseEntity<Bus> bus; 
	private static Integer linea = null;
	private static String lineaString = null;

	@GetMapping("/linea")
	public ModelAndView linea(@RequestParam("linea") String numeroLinea) {
		logMapaLinea.info("si entra a linea");
		lineaString=numeroLinea;
		ModelAndView mav = new ModelAndView(DeclaracionVariable.MAPA_LINEA);
		List<String> ListPuntoString = new ArrayList<>();
		List<String> ListPadaraString = new ArrayList<>();
		List<Integer> ListLineaInteger = new ArrayList<>();
		String colorRgb = null;

		try {
			mapaLinea = restTemplateMapaLinea.exchange(DeclaracionVariable.DIR_WEB_SERVICE_LINEA + numeroLinea,
					HttpMethod.GET, new HttpEntity<String>(headersAuth.headers()), Ruta.class);
		} catch (Exception e) {
			logMapaLinea.error(e.getMessage());
		}

		colorRgb = "'" + mapaLinea.getBody().getColor() + "'";

		mav.addObject("ruta", mapaLinea.getBody().getListPuntoString());
		mav.addObject("parada", mapaLinea.getBody().getListParadaString());
		mav.addObject("color", colorRgb);
		mav.addObject("accedeLinea", "1");

		logMapaLinea.info("retorna correcto mapa linea");
		logMapaLinea.info("paradaString tiene "+mapaLinea.getBody().getListParadaString());
		logMapaLinea.info("paradaList tiene "+mapaLinea.getBody().getListasParadas());
		return mav;
	}

	@GetMapping("/lineas")
	public ModelAndView lineas() {
		ModelAndView mav = new ModelAndView(DeclaracionVariable.MAPA_LINEA);
		List<String> ListPuntoString = new ArrayList<>();
		List<String> ListPadaraString = new ArrayList<>();
		List<String> ListColorString = new ArrayList<>();
		String colorRgb = null;

		try {
			mapaLineas = restTemplateMapaLinea.exchange(DeclaracionVariable.DIR_WEB_SERVICE_LINEA, HttpMethod.GET,
					new HttpEntity<String>(headersAuth.headers()), new ParameterizedTypeReference<List<Ruta>>() {
					});
		} catch (Exception e) {
			logMapaLinea.error(e.getMessage());
		}

		for (int i = 0; i < mapaLineas.getBody().size(); i++) {
			ListPuntoString.add(mapaLineas.getBody().get(i).getListPuntoString());
			ListPadaraString.add(mapaLineas.getBody().get(i).getListParadaString());
			colorRgb = "'" + mapaLineas.getBody().get(i).getColor() + "'";
			ListColorString.add(colorRgb);
		}

		mav.addObject("rutas", ListPuntoString);
		mav.addObject("paradas", ListPadaraString);
		mav.addObject("colores", ListColorString);

		return mav;

	}

	@GetMapping("/busPlaca")
	@ResponseBody
	public EstadoBus busPosicionActual(@RequestParam("placa") String placa) {
		
		String estadoActualLatLng = null;
		List<EstadoBus> ListaBusActual = new ArrayList<>();
		EstadoBus estadoBus = new EstadoBus();
		
		try {			
			estadoActualBus = restTemplateMapaLinea.exchange(
					DeclaracionVariable.DIR_WEB_SERVICE_BUSES + placa + "/estadoActual", HttpMethod.GET,
					new HttpEntity<String>(headersAuth.headers()), new ParameterizedTypeReference<EstadoBus>() {
					});					
		} catch (HttpClientErrorException e) {
			logMapaLinea.info("getStatusCodeValue " + estadoActualBus.getStatusCodeValue());
			logMapaLinea.info("getStatusCode " + estadoActualBus.getStatusCode().name());
			logMapaLinea.info("e getMessage" + e.getMessage());
			logMapaLinea.info("e getCause" + e.getCause());

		}

		if(estadoActualBus.getBody().getLinea()== Integer.parseInt(lineaString)) {
			BusController busController = new BusController();
		bus= busController.busByPlaca(placa);
		
			
			linea = estadoActualBus.getBody().getLinea();
			Point estadoActual = estadoActualBus.getBody().getPosicionActual();
			estadoActualLatLng = "{" + "lat: " + estadoActual.getY() + ", lng: " + estadoActual.getX() + "}";
			
			estadoBus.setEstadoActual(estadoActualLatLng);
			estadoBus.setLinea(estadoActualBus.getBody().getLinea());
			estadoBus.setCantidadUsuarios(estadoActualBus.getBody().getCantidadUsuarios());
			estadoBus.setVelocidad(estadoActualBus.getBody().getVelocidad());	
			estadoBus.setPlaca(estadoActualBus.getBody().getPlaca());
			estadoBus.setDisco(bus.getBody().getNumeroDisco()); 
			estadoBus.setCapacidad(bus.getBody().getCapacidad());
		}else {
			logMapaLinea.info("retorna nulo");
			return null;
		}		
		return estadoBus;
	}

	@GetMapping("/busesLinea")
	@ResponseBody
	public List<EstadoBus> busesPosicionActual() {		
		List<EstadoBus> listaEstadoActualBuses = new ArrayList<EstadoBus>();
		String estadoActualLatLng = null;
		BusController busController = new BusController();		
		try {			
			estadoActualBuses = restTemplateMapaLinea.exchange(
					DeclaracionVariable.DIR_WEB_SERVICE_BUSES + lineaString + "/allEstadoActual", HttpMethod.GET,
					new HttpEntity<String>(headersAuth.headers()), new ParameterizedTypeReference<List<EstadoBus>>() {
					});			
		} catch (Exception e) {
			logMapaLinea.error("busesPosicionActual() : " + estadoActualBus.getStatusCodeValue());
			logMapaLinea.error("busesPosicionActual() : " + estadoActualBus.getStatusCode());
			logMapaLinea.info("e getMessage" + e.getMessage());
			logMapaLinea.info("e getCause" + e.getCause());
		}

		for (int i = 0; i < estadoActualBuses.getBody().size(); i++) {
			bus= busController.busByPlaca(estadoActualBuses.getBody().get(i).getPlaca());
			EstadoBus estadoBusesLinea = new EstadoBus();
			Point estadoActual = estadoActualBuses.getBody().get(i).getPosicionActual();
			estadoActualLatLng = "{" + "lat: " + estadoActual.getY() + ", lng: " + estadoActual.getX() + "}";
			estadoBusesLinea.setCantidadUsuarios(estadoActualBuses.getBody().get(i).getCantidadUsuarios());
			estadoBusesLinea.setEstadoActual(estadoActualLatLng);
			estadoBusesLinea.setLinea(estadoActualBuses.getBody().get(i).getLinea());
			estadoBusesLinea.setVelocidad(estadoActualBuses.getBody().get(i).getVelocidad());
			estadoBusesLinea.setPlaca(estadoActualBuses.getBody().get(i).getPlaca());
			estadoBusesLinea.setDisco(bus.getBody().getNumeroDisco()); 
			estadoBusesLinea.setCapacidad(bus.getBody().getCapacidad());
			listaEstadoActualBuses.add(estadoBusesLinea);
		}

		logMapaLinea.info("listaEstadoActualBuses " + listaEstadoActualBuses.toString());
		return listaEstadoActualBuses;
	}
	
	
	@GetMapping("/busesLineas")
	@ResponseBody
	public List<EstadoBus> busesLineasPosicionActual() {		
		List<EstadoBus> listaEstadoActualBuses = new ArrayList<EstadoBus>();
		BusController busController = new BusController();		
		String estadoActualLatLng = null;
		
		try {			
			estadoActualBuses = restTemplateMapaLinea.exchange(
					DeclaracionVariable.DIR_WEB_SERVICE_BUSES +"/allEstadoActual", HttpMethod.GET,
					new HttpEntity<String>(headersAuth.headers()), new ParameterizedTypeReference<List<EstadoBus>>() {
					});			
		} catch (Exception e) {
			logMapaLinea.error("busesLineasPosicionActual() : " + estadoActualBus.getStatusCodeValue());
			logMapaLinea.error("busesLineasPosicionActual() : " + estadoActualBus.getStatusCode());
			logMapaLinea.info("e getMessage" + e.getMessage());
			logMapaLinea.info("e getCause" + e.getCause());
		}

		for (int i = 0; i < estadoActualBuses.getBody().size(); i++) {
			bus= busController.busByPlaca(estadoActualBuses.getBody().get(i).getPlaca());
			EstadoBus estadoBusesLineas = new EstadoBus();
			Point estadoActual = estadoActualBuses.getBody().get(i).getPosicionActual();
			estadoActualLatLng = "{" + "lat: " + estadoActual.getY() + ", lng: " + estadoActual.getX() + "}";
			estadoBusesLineas.setEstadoActual(estadoActualLatLng);
			estadoBusesLineas.setLinea(estadoActualBuses.getBody().get(i).getLinea());
			estadoBusesLineas.setCantidadUsuarios(estadoActualBuses.getBody().get(i).getCantidadUsuarios());
			estadoBusesLineas.setLinea(estadoActualBuses.getBody().get(i).getLinea());
			estadoBusesLineas.setVelocidad(estadoActualBuses.getBody().get(i).getVelocidad());
			estadoBusesLineas.setPlaca(estadoActualBuses.getBody().get(i).getPlaca());
			estadoBusesLineas.setDisco(bus.getBody().getNumeroDisco()); 
			estadoBusesLineas.setCapacidad(bus.getBody().getCapacidad());
			listaEstadoActualBuses.add(estadoBusesLineas); 
		}

		logMapaLinea.info("estadoActualLatLng " + listaEstadoActualBuses.toString());
		return listaEstadoActualBuses;
	}


}

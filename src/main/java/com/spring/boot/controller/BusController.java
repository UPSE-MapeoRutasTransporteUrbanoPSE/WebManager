package com.spring.boot.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.spring.boot.constant.DeclaracionVariable;
import com.spring.boot.constant.HeadersAuth;
import com.spring.boot.converter.ConverterBus;
import com.spring.boot.converter.ConverterCooperativa;
import com.spring.boot.model.Bus;
import com.spring.boot.model.Cooperativa;
import com.spring.boot.model.Reporte;

/**
 * @author RENATO RODRIGUEZ
 *
 */
@Controller
@RequestMapping("/bus")
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class BusController {

	private static final Log LogBus = LogFactory.getLog(BusController.class);

	private static String placaModifica = null;
	RestTemplate restTemplateBus = new RestTemplate();
	RestTemplate restTemplateCooperativa = new RestTemplate();
	HeadersAuth headersAuth = new HeadersAuth();
	private static ResponseEntity<List<Cooperativa>> listaCooperativa;

	@Autowired
	@Qualifier("ConverterBus")
	private ConverterBus converterBus;

	@GetMapping("/listaBus")
	public ModelAndView listaBus() {
		ModelAndView mav = new ModelAndView(DeclaracionVariable.GS_BUS);
		try {
			List<Bus> nuevaListaBus = new ArrayList<>();
			listaCooperativa = restTemplateCooperativa.exchange(DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS,
					HttpMethod.GET, new HttpEntity<String>(headersAuth.headers()),
					new ParameterizedTypeReference<List<Cooperativa>>() {
					});

			try {
				ResponseEntity<List<Bus>> listaBuse = restTemplateBus.exchange(
						DeclaracionVariable.DIR_WEB_SERVICE_BUSES, HttpMethod.GET,
						new HttpEntity<String>(headersAuth.headers()), new ParameterizedTypeReference<List<Bus>>() {
						});

				List<Bus> listaBus = listaBuse.getBody();
				List<Bus> listaBusNoNull = new ArrayList<Bus>();

				for (int k = 0; k < listaBus.size(); k++) {
					if (listaBus.get(k).getIdCooperativa() != null) {

						Bus nuevoBusNoNull = new Bus();
						nuevoBusNoNull.setCapacidad(listaBus.get(k).getCapacidad());
						nuevoBusNoNull.setEstado(listaBus.get(k).getEstado());
						nuevoBusNoNull.setIdCooperativa(listaBus.get(k).getIdCooperativa());
						nuevoBusNoNull.setNumeroDisco(listaBus.get(k).getNumeroDisco());
						nuevoBusNoNull.setPlaca(listaBus.get(k).getPlaca());
						listaBusNoNull.add(nuevoBusNoNull);
					} else {

					}

				}

				for (int i = 0; i < listaBusNoNull.size(); i++) {

					List<Cooperativa> listaCoo = listaCooperativa.getBody();

					Bus nuevoBus = new Bus();
					nuevoBus.setCapacidad(listaBusNoNull.get(i).getCapacidad());
					nuevoBus.setEstado(listaBusNoNull.get(i).getEstado());
					nuevoBus.setIdCooperativa(listaBusNoNull.get(i).getIdCooperativa());

					for (int j = 0; j < listaCoo.size(); j++) {

						if (listaBusNoNull.get(i).getIdCooperativa().equals(listaCoo.get(j).getId())) {
							nuevoBus.setNombreCooperativa(listaCoo.get(j).getNombre());
						}
					}

					nuevoBus.setNumeroDisco(listaBusNoNull.get(i).getNumeroDisco());
					nuevoBus.setPlaca(listaBusNoNull.get(i).getPlaca());
					nuevoBus.setOrden(i + 1);
					nuevaListaBus.add(nuevoBus);

				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			mav.addObject("listaCooperativa", listaCooperativa.getBody());
			mav.addObject("listaBus", nuevaListaBus);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mav;
	}

	@PostMapping("/addBus")
	public String addBus(@ModelAttribute(name = "busModel") Bus busModel) {
		if (placaModifica == null) {
			Map params = new HashMap();
			params.put("placa", busModel.getPlaca());
			params.put("numeroDisco", busModel.getNumeroDisco());
			params.put("capacidad", busModel.getCapacidad());
			params.put("idCooperativa", busModel.getIdCooperativa());

			Bus bus = restTemplateBus.postForObject(DeclaracionVariable.DIR_WEB_SERVICE_BUSES,
					new HttpEntity<>(params, headersAuth.headers()), Bus.class);

			if (bus.equals(null)) {
				return "error";
			}
		} else {
			modificaBus(busModel);
			placaModifica = null;
		}
		return "redirect:/bus/listaBus";
	}

	@GetMapping("/eliminaBus")
	public ModelAndView eliminaBus(@RequestParam(name = "placa", required = true) String placa) {
		try {
			restTemplateBus.exchange(DeclaracionVariable.DIR_WEB_SERVICE_BUSES + placa, HttpMethod.DELETE,
					new HttpEntity<String>(headersAuth.headers()), Bus.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return listaBus();
	}

	@GetMapping("/limpiaIdBus")
	public ModelAndView limpiaIdBus() {
		placaModifica = null;
		return listaBus();
	}

	public ResponseEntity<Bus> modificaBus(Bus busModel) {

		ResponseEntity<Bus> ReBus = busByPlaca(placaModifica);
		try {
			ResponseEntity<Bus> buserror = restTemplateBus.exchange(DeclaracionVariable.DIR_WEB_SERVICE_BUSES,
					HttpMethod.PUT, new HttpEntity<>(converterBus.nuevaBus(ReBus, busModel), headersAuth.headers()),
					Bus.class);
			return buserror;
		} catch (Exception e) {
			System.out.println("eeror de bus  " + e);
			System.out.println("eeror de bus  " + e.getMessage());
			System.out.println("eeror de bus  " + e.getCause());
			return null;
		}
	}

	@ResponseBody
	@GetMapping("/busByPlaca")
	public ResponseEntity<Bus> busByPlaca(String placa) {
		placaModifica = placa;
		ResponseEntity<Bus> bus = restTemplateBus.exchange(DeclaracionVariable.DIR_WEB_SERVICE_BUSES_PLACA + placa,
				HttpMethod.GET, new HttpEntity<String>(headersAuth.headers()), Bus.class);
		return bus;
	}
}
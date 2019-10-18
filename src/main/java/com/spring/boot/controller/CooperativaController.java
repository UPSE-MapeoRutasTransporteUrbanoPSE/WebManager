
package com.spring.boot.controller;

import java.util.ArrayList;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.constant.DeclaracionVariable;
import com.spring.boot.constant.HeadersAuth;
import com.spring.boot.converter.ConverterCooperativa;
import com.spring.boot.model.Bus;
import com.spring.boot.model.Cooperativa;

/**
 * @author RENATO RODRIGUEZ
 */
@Controller
@RequestMapping("/cooperativa")
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class CooperativaController {

	private static final Log LogCooperactiva = LogFactory.getLog(CooperativaController.class);
	private static String idCooperativaModifica = null;
	private static Integer variableRe, variableMo = 0;
	RestTemplate restTemplateCooperativa = new RestTemplate();
	HeadersAuth headersAuth = new HeadersAuth();
	private static ResponseEntity<List<Cooperativa>> listaCooperativa;

	@Autowired
	@Qualifier("ConverterCooperativa")
	private ConverterCooperativa converterCooperativa;

	@GetMapping("/listaCooperativa")
	public ModelAndView listaCooperativa() {
		ModelAndView mav = new ModelAndView(DeclaracionVariable.GS_COOPERATIVA);
		try {
			List<Cooperativa> nuevaListaCooperativa = new ArrayList<>();
			listaCooperativa = restTemplateCooperativa.exchange(DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS,
					HttpMethod.GET, new HttpEntity<String>(headersAuth.headers()),
					new ParameterizedTypeReference<List<Cooperativa>>() {
					});

			List<Cooperativa> listaCoo = listaCooperativa.getBody();

			for (int i = 0; i < listaCoo.size(); i++) {
				Cooperativa nuevaCooperativa = new Cooperativa();
				nuevaCooperativa.setDescripcion(listaCoo.get(i).getDescripcion());
				nuevaCooperativa.setDireccion(listaCoo.get(i).getDireccion());
				nuevaCooperativa.setEmail(listaCoo.get(i).getEmail());
				nuevaCooperativa.setId(listaCoo.get(i).getId());
				nuevaCooperativa.setNombre(listaCoo.get(i).getNombre());
				nuevaCooperativa.setOrden(i + 1);
				nuevaCooperativa.setTelefono(listaCoo.get(i).getTelefono());
				nuevaCooperativa.setEstado(listaCoo.get(i).getEstado());
				nuevaListaCooperativa.add(nuevaCooperativa);
			}

			mav.addObject("listaCooperativa", nuevaListaCooperativa);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mav;
	}

	@PostMapping("/addCooperativa")
	public String addCooperativa(@ModelAttribute(name = "cooperativaModel") Cooperativa cooperativaModel) {
		try {
			if (idCooperativaModifica == null) {
				Map params = new HashMap();
				params.put("nombre", cooperativaModel.getNombre());
				params.put("descripcion", cooperativaModel.getDescripcion());
				params.put("direccion", cooperativaModel.getDireccion());
				params.put("telefono", cooperativaModel.getTelefono());
				params.put("email", cooperativaModel.getEmail());

				Cooperativa cooperativa = restTemplateCooperativa.postForObject(
						DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS,
						new HttpEntity<>(params, headersAuth.headers()), Cooperativa.class);

				if (cooperativa.equals(null)) {
					LogCooperactiva.info("NO SE PUDO REGISTRAR LOS DATOS DE LA COOPERATIVA");
					return "error";
				}
			} else {
				modificaCooperativa(cooperativaModel);
				idCooperativaModifica = null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/cooperativa/listaCooperativa";
	}

	public void modificaCooperativa(Cooperativa cooperativaModel) {
		try {
			ResponseEntity<Cooperativa> ReCoo = cooperativaById(idCooperativaModifica);

			restTemplateCooperativa.exchange(DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS, HttpMethod.PUT,
					new HttpEntity<>(converterCooperativa.nuevaCooperativa(ReCoo, cooperativaModel),
							headersAuth.headers()),
					Cooperativa.class);// se manda a mloldificar el objeto
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@GetMapping("/eliminaCooperativa")
	public ModelAndView eliminaCooperativa(
			@RequestParam(name = "idCooperativa", required = true) String idCooperativa) {
		try {
			restTemplateCooperativa.exchange(DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS + "/" + idCooperativa,
					HttpMethod.DELETE, new HttpEntity<String>(headersAuth.headers()), Cooperativa.class); // peticion de
																											// un objeto

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return listaCooperativa();

	}

	@GetMapping("/limpiaIdCooperativa")
	public ModelAndView limpiaIdCooperativa() {
		try {
			idCooperativaModifica = null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return listaCooperativa();

	}

	@ResponseBody
	@GetMapping("/cooperativaById")
	public ResponseEntity<Cooperativa> cooperativaById(String idCooperativa) {
		try {
			idCooperativaModifica = idCooperativa;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return restTemplateCooperativa.exchange(DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS + "/" + idCooperativa,
				HttpMethod.GET, new HttpEntity<String>(headersAuth.headers()), Cooperativa.class);
	}
}

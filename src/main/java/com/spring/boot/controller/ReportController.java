package com.spring.boot.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.constant.DeclaracionVariable;
import com.spring.boot.constant.HeadersAuth;
import com.spring.boot.model.Cooperativa;
import com.spring.boot.model.Reporte;
import com.spring.boot.services.ServicioDenuncia;


@RestController
@RequestMapping("/reporteQueja")
@SuppressWarnings("unused")
public class ReportController {

	/**
	 * variables globales para la clase
	 */
	private static final Log LogReporteQueja = LogFactory.getLog(ReportController.class);
	RestTemplate restTemplateReporteQueja = new RestTemplate();
	RestTemplate restTemplateCooperativa = new RestTemplate();
	HeadersAuth headersAuth = new HeadersAuth();
	private static ResponseEntity<List<Reporte>> listaReporte;
	private static ResponseEntity<Cooperativa> nombreCooperativa;
	private static List<Reporte> nuevaListaReporte = new ArrayList<Reporte>();
	private static Reporte nuevoReporte = new Reporte();
	private static List<Reporte> listaReporteFiltro = new ArrayList<Reporte>();
	private static ResponseEntity<List<Cooperativa>> listaCooperativa;

	public static String desde = null;
	public static String hasta = null;

	public static Integer x = 0;

	public static Date desdeParametro = null;
	public static Date hastaParametro = null;

	@Resource
	private ServicioDenuncia servicioDenuncia;

	
	/**
	 * OBTIENE LA LISTA DE REPORTES(QUEJAS) MAPEO(/REPORTES)
	 * 
	 * @return
	 */
	@GetMapping("/listaReporteQueja")
	public ModelAndView listaReporteQueja() {
		nuevaListaReporte.clear();
		ModelAndView mav = new ModelAndView(DeclaracionVariable.RP_REPORTE_QUEJA);
		try {
			LogReporteQueja.info("model and view : listaReporteQueja()");

			listaReporte = restTemplateReporteQueja.exchange(DeclaracionVariable.DIR_WEB_SERVICE_REPORTE,
					HttpMethod.GET, new HttpEntity<String>(headersAuth.headers()),
					new ParameterizedTypeReference<List<Reporte>>() {
					});

			LogReporteQueja.info("solo llega aqui :V ");
			listaCooperativa = restTemplateCooperativa.exchange(DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS,
					HttpMethod.GET, new HttpEntity<String>(headersAuth.headers()),
					new ParameterizedTypeReference<List<Cooperativa>>() {
					});

			LogReporteQueja.info("aqui");

			List<Reporte> listaReporteAux = listaReporte.getBody();
			for (int j = 0; j < listaReporteAux.size(); j++) {
				if (listaReporteAux.get(j).getIdCooperativa() == null) {
					LogReporteQueja.info("hay nulos en la posicion " + j);
				} else {
					LogReporteQueja
							.info("no hay nulos en la posicion " + j + " " + listaReporteAux.get(j).getIdCooperativa());
					Reporte nuevoReporte = new Reporte();

					nuevoReporte.setAsunto(listaReporteAux.get(j).getAsunto());
					nuevoReporte.setNumeroDisco(listaReporteAux.get(j).getNumeroDisco());
					nuevoReporte.setUbicacion(listaReporteAux.get(j).getUbicacion());
					// Date fecha=listaReporteAux.get(j).getFecha().dateTime.toDate();

					nuevoReporte.setFecha(listaReporteAux.get(j).getFecha());
					nuevoReporte.setMensaje(listaReporteAux.get(j).getMensaje());
					nuevoReporte.setIdCooperativa(listaReporteAux.get(j).getIdCooperativa());
					LogReporteQueja.info("antes e buscar coopeativa para que retorne string "
							+ listaReporteAux.get(j).getIdCooperativa());

					LogReporteQueja.info("el id cooperativa es " + j + " " + listaReporteAux.get(j).getIdCooperativa());

					nombreCooperativa = restTemplateCooperativa.exchange(
							DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS + "/"
									+ listaReporteAux.get(j).getIdCooperativa(),
							HttpMethod.GET, new HttpEntity<String>(headersAuth.headers()), Cooperativa.class); // peticion
																												// de un
																												// objeto

					LogReporteQueja.info("si busco cooperativa " + nombreCooperativa.getBody().getNombre());

					nuevoReporte.setNombreCooperativa(nombreCooperativa.getBody().getNombre());
					nuevoReporte.setOrden(j + 1);
					LogReporteQueja.info("lo que hay en nuevo " + nuevoReporte.toString());

					nuevaListaReporte.add(nuevoReporte);
				}
			}

			mav.addObject("listaQueja", nuevaListaReporte);// agrego la lista a la vista
			mav.addObject("listaCooperativa", listaCooperativa.getBody());// agrego la lista a la vista
			return mav;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mav;
	}

	@GetMapping("/listaReporteQuejaPdf")
	public void listaReporteQuejaPdf(HttpServletResponse response) throws IOException {

		try {

			// filtro por fecha

			Reporte reporte = new Reporte();
			listaReporteFiltro.add(0, reporte);
			nuevaListaReporte.add(0, reporte);

			LogReporteQueja.info("antes " + x);
			if (x == 0) {
				LogReporteQueja.info("si es null");
				File file = servicioDenuncia.generarDenunciaPdf(nuevaListaReporte, Locale.ENGLISH);
				response.setContentType("application/pdf");
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			}

			if (x == 1) {
				LogReporteQueja.info("si es 0");
				File file = servicioDenuncia.generarDenunciaPdf(listaReporteFiltro, Locale.ENGLISH);
				response.setContentType("application/pdf");
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			}

			// response.setContentType("application/pdf");

			// response.setHeader("Content-Disposition", String.format("attachment;
			// filename=\"" + file.getName()));
			// response.setContentLength((int) file.length());

			// InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			// FileCopyUtils.copy(inputStream, response.getOutputStream());

			LogReporteQueja.info("archivo generado");
			listaReporteFiltro.remove(0);
			nuevaListaReporte.remove(0);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@GetMapping(value = "/consultafiltro")
	public ModelAndView consultaFiltro(@RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date desde,
			@RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date hasta,
			@RequestParam("idCooperativa") String idCooperativa) throws ParseException {

		LogReporteQueja.info("inicial desde alla" + desde + " final desde alla " + hasta);
		if (idCooperativa.isEmpty()) {
			idCooperativa = null;

		}
		listaReporteFiltro.clear();
		ModelAndView mav = new ModelAndView(DeclaracionVariable.RP_REPORTE_QUEJA);
		Calendar cal1 = null;
		Calendar cal2 = null;

		if (desde == null && hasta == null) {

		} else {
			cal1 = new GregorianCalendar();
			cal1.setTime(desde);
			cal1.add(GregorianCalendar.DAY_OF_YEAR, 1);

			cal2 = new GregorianCalendar();
			cal2.setTime(hasta);
			cal2.add(GregorianCalendar.DAY_OF_YEAR, 1);

			desdeParametro = desde;
			hastaParametro = hasta;
		}

		if (idCooperativa != null && (desde == null && hasta == null)) {
			for (int i = 0; i < nuevaListaReporte.size(); i++) {
				if (idCooperativa.equals(nuevaListaReporte.get(i).getIdCooperativa())) {
					Reporte nuevoReporte = new Reporte();

					LogReporteQueja.info("asunto nueva lista reporte " + nuevaListaReporte.get(i).getAsunto());
					nuevoReporte.setAsunto(nuevaListaReporte.get(i).getAsunto());
					nuevoReporte.setNumeroDisco(nuevaListaReporte.get(i).getNumeroDisco());
					nuevoReporte.setUbicacion(nuevaListaReporte.get(i).getUbicacion());
					// Date fecha=listaReporteAux.get(j).getFecha().dateTime.toDate();

					nuevoReporte.setFecha(nuevaListaReporte.get(i).getFecha());
					nuevoReporte.setMensaje(nuevaListaReporte.get(i).getMensaje());
					nuevoReporte.setIdCooperativa(nuevaListaReporte.get(i).getIdCooperativa());

					nombreCooperativa = restTemplateCooperativa.exchange(
							DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS + "/" + idCooperativa, HttpMethod.GET,
							new HttpEntity<String>(headersAuth.headers()), Cooperativa.class); // peticion de un objeto

					LogReporteQueja.info("si busco cooperativa " + nombreCooperativa.getBody().getNombre());

					nuevoReporte.setNombreCooperativa(nombreCooperativa.getBody().getNombre());
					nuevoReporte.setOrden(i + 1);
					listaReporteFiltro.add(nuevoReporte);
				}

			}

		}

		if ((desde != null && hasta != null) && idCooperativa != null) {

			LogReporteQueja.info("si esta lleno los 3");

			for (int i = 0; i < nuevaListaReporte.size(); i++) {
				Calendar cal3 = new GregorianCalendar();
				cal3.setTime(nuevaListaReporte.get(i).getFecha());

				LogReporteQueja.info("si esta lleno  for");
				if (((cal3.after(cal1) && cal3.before(cal2)) || cal3.equals(cal2) || cal3.equals(cal1))
						&& idCooperativa.equals(nuevaListaReporte.get(i).getIdCooperativa())) {
					Reporte nuevoReporte = new Reporte();
					LogReporteQueja.info("si esta lleno  comparacion rango");
					nuevoReporte.setAsunto(nuevaListaReporte.get(i).getAsunto());
					nuevoReporte.setNumeroDisco(nuevaListaReporte.get(i).getNumeroDisco());
					nuevoReporte.setUbicacion(nuevaListaReporte.get(i).getUbicacion());

					nuevoReporte.setFecha(nuevaListaReporte.get(i).getFecha());
					nuevoReporte.setMensaje(nuevaListaReporte.get(i).getMensaje());
					nuevoReporte.setIdCooperativa(nuevaListaReporte.get(i).getIdCooperativa());

					LogReporteQueja.info("idCooperativa " + idCooperativa);
					nombreCooperativa = restTemplateCooperativa.exchange(
							DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS + "/" + idCooperativa, HttpMethod.GET,
							new HttpEntity<String>(headersAuth.headers()), Cooperativa.class); // peticion de un objeto

					LogReporteQueja.info("si busco cooperativa " + nombreCooperativa.getBody().getNombre());

					nuevoReporte.setNombreCooperativa(nombreCooperativa.getBody().getNombre());
					nuevoReporte.setOrden(i + 1);
					listaReporteFiltro.add(nuevoReporte);
				}

			}

		}

		if ((desde != null && hasta != null) && idCooperativa == null) {

			LogReporteQueja.info("si esta lleno solo fecha");

			for (int i = 0; i < nuevaListaReporte.size(); i++) {
				Calendar cal3 = new GregorianCalendar();
				cal3.setTime(nuevaListaReporte.get(i).getFecha());

				LogReporteQueja.info("si esta lleno  for");
				if ((cal3.after(cal1) && cal3.before(cal2)) || cal3.equals(cal2) || cal3.equals(cal1)) {
					Reporte nuevoReporte = new Reporte();
					LogReporteQueja.info("si esta lleno  comparacion rango");
					nuevoReporte.setAsunto(nuevaListaReporte.get(i).getAsunto());
					nuevoReporte.setNumeroDisco(nuevaListaReporte.get(i).getNumeroDisco());
					nuevoReporte.setUbicacion(nuevaListaReporte.get(i).getUbicacion());

					nuevoReporte.setFecha(nuevaListaReporte.get(i).getFecha());
					nuevoReporte.setMensaje(nuevaListaReporte.get(i).getMensaje());
					nuevoReporte.setIdCooperativa(nuevaListaReporte.get(i).getIdCooperativa());

					LogReporteQueja.info("idCooperativa " + idCooperativa);
					nombreCooperativa = restTemplateCooperativa.exchange(
							DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS + "/"
									+ nuevaListaReporte.get(i).getIdCooperativa(),
							HttpMethod.GET, new HttpEntity<String>(headersAuth.headers()), Cooperativa.class); // peticion
																												// de un
																												// objeto

					LogReporteQueja.info("si busco cooperativa " + nombreCooperativa.getBody().getNombre());

					nuevoReporte.setNombreCooperativa(nombreCooperativa.getBody().getNombre());
					nuevoReporte.setOrden(i + 1);
					listaReporteFiltro.add(nuevoReporte);
				}

			}

		}

		if (idCooperativa == null && (desde == null && hasta == null)) {
			mav.addObject("listaQueja", nuevaListaReporte);// agrego la lista a la vista
			x = 0;
		} else {
			mav.addObject("listaQueja", listaReporteFiltro);// agrego la lista a la vista
			x = 1;
		}

		mav.addObject("desde", desde);// agrego la lista a la vista
		mav.addObject("hasta", hasta);// agrego la lista a la vista
		mav.addObject("listaCooperativa", listaCooperativa.getBody());// agrego la lista a la vista
		mav.addObject("idCooperativa", idCooperativa);// agrego la lista a la vista

		LogReporteQueja.info("desde" + desde);
		LogReporteQueja.info("hasta" + hasta);

		return mav;

	}

}

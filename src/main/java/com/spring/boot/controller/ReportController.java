package com.spring.boot.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.constant.DeclaracionVariable;
import com.spring.boot.constant.HeadersAuth;
import com.spring.boot.model.Cooperativa;
import com.spring.boot.model.Reporte;
import com.spring.boot.services.ServicioDenuncia;

@Controller
@RequestMapping("/reporteQueja")
@SuppressWarnings("unused")
public class ReportController {
	private static final Log LogReporteQueja = LogFactory.getLog(ReportController.class);

	RestTemplate restTemplateReporteQueja = new RestTemplate();
	RestTemplate restTemplateCooperativa = new RestTemplate();

	HeadersAuth headersAuth = new HeadersAuth();

	private static ResponseEntity<List<Reporte>> listaReporte;
	private static ResponseEntity<Cooperativa> nombreCooperativa;
	private static ResponseEntity<List<Cooperativa>> listaCooperativa;

	private static List<Reporte> nuevaListaReporte = new ArrayList<Reporte>();
	private static List<Reporte> listaReporteFiltro = new ArrayList<Reporte>();

	private static Reporte nuevoReporte = new Reporte();

	public static String desde = null;
	public static String hasta = null;
	public static Integer x = 0;
	public static Integer contador = 0;

	@Resource
	private ServicioDenuncia servicioDenuncia;

	@GetMapping("/vistaReporte")
	public ModelAndView vistaRepoprteDenuncia() {
		ModelAndView mav = new ModelAndView(DeclaracionVariable.RP_REPORTE_QUEJA);
		listaCooperativa = restTemplateCooperativa.exchange(DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS,
				HttpMethod.GET, new HttpEntity<String>(headersAuth.headers()),
				new ParameterizedTypeReference<List<Cooperativa>>() {
				});
		mav.addObject("ListaCooperativa", listaCooperativa.getBody());
		return mav;
	}

	@GetMapping("/listaReporteQueja")
	@ResponseBody
	public List<Reporte> listaReporteQueja() {	
		contador = 0;
		listaReporteFiltro.clear();
		try {
			listaReporte = restTemplateReporteQueja.exchange(DeclaracionVariable.DIR_WEB_SERVICE_REPORTE,
					HttpMethod.GET, new HttpEntity<String>(headersAuth.headers()),
					new ParameterizedTypeReference<List<Reporte>>() {
					});

			List<Reporte> listaReporteAux = listaReporte.getBody();
			for (int j = 0; j < listaReporteAux.size(); j++) {
				if (listaReporteAux.get(j).getIdCooperativa() == null) {
					LogReporteQueja.info("hay nulos en la posicion " + j);
				} else {
					reporteFiltro(listaReporte, j);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return listaReporteFiltro;
	}

	@GetMapping("/listaReporteQuejaPdf")
	public void listaReporteQuejaPdf(HttpServletResponse response) throws IOException {
		try {
			Reporte reporte = new Reporte();
			listaReporteFiltro.add(0, reporte);

			LogReporteQueja.info("reporte");
			File file = servicioDenuncia.generarDenunciaPdf(listaReporteFiltro, Locale.ENGLISH);
			response.setContentType("application/pdf");
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());

			// response.setContentType("application/pdf");

			// response.setHeader("Content-Disposition", String.format("attachment;
			// filename=\"" + file.getName()));
			// response.setContentLength((int) file.length());

			// InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			// FileCopyUtils.copy(inputStream, response.getOutputStream());

			LogReporteQueja.info("archivo generado");
			listaReporteFiltro.remove(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@GetMapping("/consultafiltro")
	@ResponseBody
	public List<Reporte> consultaFiltro(
			@RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date desde,
			@RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date hasta,
			@RequestParam("idCooperativa") String idCooperativa) throws ParseException {
		
		contador = 0;
		listaReporteFiltro.clear();

		listaReporte = restTemplateReporteQueja.exchange(DeclaracionVariable.DIR_WEB_SERVICE_REPORTE, HttpMethod.GET,
				new HttpEntity<String>(headersAuth.headers()), new ParameterizedTypeReference<List<Reporte>>() {
				});

		SimpleDateFormat formatea = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal1 = null;
		Calendar cal2 = null;
		Date fechaDesde = null;
		Date fechaHasta = null;

		if (idCooperativa.isEmpty()) {
			idCooperativa = null;
		}

		if (desde != null && hasta != null) {
			cal1 = new GregorianCalendar();
			cal1.setTime(desde);
			cal1.add(GregorianCalendar.DAY_OF_YEAR, 1);
			fechaDesde = formatea.parse(formatea.format(cal1.getTime()));

			cal2 = new GregorianCalendar();
			cal2.setTime(hasta);
			cal2.add(GregorianCalendar.DAY_OF_YEAR, 1);
			fechaHasta = formatea.parse(formatea.format(cal2.getTime()));
		}

		if (idCooperativa != null && (desde == null && hasta == null)) {
			for (int i = 0; i < listaReporte.getBody().size(); i++) {
				if (idCooperativa.equals(listaReporte.getBody().get(i).getIdCooperativa())) {
					reporteFiltro(listaReporte, i);
				}
			}
		}

		if ((desde != null && hasta != null) && idCooperativa != null) {
			for (int i = 0; i < listaReporte.getBody().size(); i++) {
				Calendar cal3 = new GregorianCalendar();
				cal3.setTime(listaReporte.getBody().get(i).getFecha());
				Date fechaComparar = formatea.parse(formatea.format(cal3.getTime()));

				if ((fechaComparar.getTime() >= fechaDesde.getTime() && fechaComparar.getTime() <= fechaHasta.getTime())
						&& idCooperativa.equals(listaReporte.getBody().get(i).getIdCooperativa())) {
					reporteFiltro(listaReporte, i);
				}
			}
		}

		if ((desde != null && hasta != null) && idCooperativa == null) {
			for (int i = 0; i < listaReporte.getBody().size(); i++) {
				Calendar cal3 = new GregorianCalendar();
				cal3.setTime(listaReporte.getBody().get(i).getFecha());
				Date fechaComparar = formatea.parse(formatea.format(cal3.getTime()));

				if (fechaComparar.getTime() >= fechaDesde.getTime()
						&& fechaComparar.getTime() <= fechaHasta.getTime()) {
					reporteFiltro(listaReporte, i);
				}
			}
		}
		return listaReporteFiltro;
	}

	public ResponseEntity<Cooperativa> nombreCooperativa(String idCooperativa) {
		nombreCooperativa = restTemplateCooperativa.exchange(
				DeclaracionVariable.DIR_WEB_SERVICE_COOPERATIVAS + "/" + idCooperativa, HttpMethod.GET,
				new HttpEntity<String>(headersAuth.headers()), Cooperativa.class);
		return nombreCooperativa;
	}

	public List<Reporte> reporteFiltro(ResponseEntity<List<Reporte>> listaReporte, Integer pos) {
		Reporte nuevoReporte = new Reporte();

		nuevoReporte.setAsunto(listaReporte.getBody().get(pos).getAsunto());
		nuevoReporte.setNumeroDisco(listaReporte.getBody().get(pos).getNumeroDisco());
		nuevoReporte.setUbicacion(listaReporte.getBody().get(pos).getUbicacion());
		nuevoReporte
				.setFechaString(new SimpleDateFormat("yyyy-MM-dd").format(listaReporte.getBody().get(pos).getFecha()));
		nuevoReporte.setMensaje(listaReporte.getBody().get(pos).getMensaje());
		nuevoReporte.setIdCooperativa(listaReporte.getBody().get(pos).getIdCooperativa());
		nuevoReporte.setNombreCooperativa(
				nombreCooperativa(listaReporte.getBody().get(pos).getIdCooperativa()).getBody().getNombre());
		nuevoReporte.setOrden(contador = contador + 1);
		listaReporteFiltro.add(nuevoReporte);
		return listaReporteFiltro;
	}
}
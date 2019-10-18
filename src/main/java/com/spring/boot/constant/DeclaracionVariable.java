package com.spring.boot.constant;

public class DeclaracionVariable {	
	//VARIALBES PARA ENLAZAR EL PROYECTO AL WEB SERVICES//http://facsistel.upse.edu.ec:8082
	public static final String DIR_WEB_SERVICE_LOCAL_U = "http://192.168.101.1:8082/";
	public static final String DIR_WEB_SERVICE_LOCAL = "http://localhost:8082/";
	public static final String DIR_WEB_SERVICE_SERVIDOR = "http://facsistel.upse.edu.ec:8082/";
	
	
	
	  public static final String DIR_WEB_SERVICE_COOPERATIVAS =
	  DIR_WEB_SERVICE_SERVIDOR+"cooperativas"; public static final String
	  DIR_WEB_SERVICE_BUSES = DIR_WEB_SERVICE_SERVIDOR+"buses/"; public static
	  final String DIR_WEB_SERVICE_BUSES_PLACA =
	  DIR_WEB_SERVICE_SERVIDOR+"buses/byPlaca/"; public static final String
	  DIR_WEB_SERVICE_SUBIR_ARCHIVO= DIR_WEB_SERVICE_SERVIDOR+"rutas/archivoGPX";
	  public static final String DIR_WEB_SERVICE_LINEA=
	  DIR_WEB_SERVICE_SERVIDOR+"rutas/"; public static final String
	  DIR_WEB_SERVICE_REPORTE= DIR_WEB_SERVICE_SERVIDOR+"denuncias"; public static
	  final String DIR_WEB_SERVICE_PARADAS= DIR_WEB_SERVICE_SERVIDOR+"paradas";
	  
	 
	
	
	
	/*
	 * public static final String DIR_WEB_SERVICE_COOPERATIVAS =
	 * DIR_WEB_SERVICE_LOCAL+"cooperativas"; public static final String
	 * DIR_WEB_SERVICE_BUSES = DIR_WEB_SERVICE_LOCAL+"buses/"; public static final
	 * String DIR_WEB_SERVICE_BUSES_PLACA = DIR_WEB_SERVICE_LOCAL+"buses/byPlaca/";
	 * public static final String DIR_WEB_SERVICE_SUBIR_ARCHIVO=
	 * DIR_WEB_SERVICE_LOCAL+"rutas/archivoGPX"; public static final String
	 * DIR_WEB_SERVICE_LINEA= DIR_WEB_SERVICE_LOCAL+"rutas"; public static final
	 * String DIR_WEB_SERVICE_REPORTE= DIR_WEB_SERVICE_LOCAL+"denuncias"; public
	 * static final String DIR_WEB_SERVICE_PARADAS= DIR_WEB_SERVICE_LOCAL+"paradas";
	 */
	
	
	public static final String USER="renato@gmail.com";
	public static final String PASS="abc123";
	
	
	//VARIALBES PARA ENLAZAR LAS VISTAS	
	public static final String INDEX = "index";
	public static final String GS_COOPERATIVA="/gestion/gestionCooperativa";
	public static final String GS_MAPA_CALOR="/gestion/mapaCalor";
	public static final String MAPA_LINEA="/mapaLinea";
	public static final String GS_BUS="/gestion/gestionBus";
	public static final String RP_REPORTE_QUEJA="/reporte/reporteQueja";
	public static final String ERROR_500 ="/error/500";
}

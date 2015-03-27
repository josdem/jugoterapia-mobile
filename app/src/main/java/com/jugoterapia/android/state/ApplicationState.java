package com.jugoterapia.android.state;


public class ApplicationState {
	/**
	 * uncomment when localhost 
	 */
	
//	public static final String URL_MOBILE_SERVER = "http://192.168.0.102:7171";	
//	public static final String URL_MOBILE_SERVER = "http://192.168.2.6:8080";
	public static final String URL_MOBILE_SERVER = "http://josdem.io:8082";
	public static final String SERVICE_NAME = "/jugoterapia-server/beverage/";
	public static final String CATEGORIES_URL = URL_MOBILE_SERVER + SERVICE_NAME + "categories";
	public static final String BEVERAGES_URL = URL_MOBILE_SERVER + SERVICE_NAME + "beverages";
	public static final String RECIPE_URL = URL_MOBILE_SERVER + SERVICE_NAME + "beverage";
	
	public static final String SEARCHING_RECIPES_MESSAGE = "Buscando recetas";
	public static final String BRINGING_RECIPE_MESSAGE = "Trayendo receta";
	public static final String WAITING_MESSAGE = "Por favor espere...";
	public static final String CONNECTION_TITLE = "Conexión";
	public static final String CONNECTION_MESSAGE = "Por favor verifica tu conexión a Internet";
}
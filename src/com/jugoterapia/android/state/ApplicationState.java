package com.jugoterapia.android.state;


public class ApplicationState {
	/**
	 * uncomment when localhost 
	 */
	
//	public static final String URL_MOBILE_SERVER = "http://192.168.0.102:7171";	
//	public static final String URL_MOBILE_SERVER = "http://192.168.0.114:7171";
	public static final String URL_MOBILE_SERVER = "http://162.209.56.86:7070";
	public static final String SERVICE_NAME = "/jugoterapia-jersey-loader";
	public static final String CATEGORIES_URL = URL_MOBILE_SERVER + SERVICE_NAME + "/rest/beverage/categories";
	public static final String BEVERAGES_URL = URL_MOBILE_SERVER + SERVICE_NAME + "/rest/beverage/beverages";
	public static final String RECIPE_URL = URL_MOBILE_SERVER + SERVICE_NAME + "/rest/beverage/recipe";
	
	public static final String SEARCHING_RECIPES_MESSAGE = "Buscando recetas";
	public static final String BRINGING_RECIPE_MESSAGE = "Trayendo receta";
	public static final String WAITING_MESSAGE = "Por favor espere...";
	public static final String CONNECTION_TITLE = "Conexión";
	public static final String CONNECTION_MESSAGE = "Por favor verifica tu conexión a Internet";
}
/*
  Copyright 2017 José Luis De la Cruz Morales <joseluis.delacruz@gmail.com>

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package com.jugoterapia.josdem.state;

public class ApplicationState {
	/**
	 * uncomment when localhost
	 */

	//public static final String URL_MOBILE_SERVER = "http://192.168.100.4:8080";
	public static final String URL_MOBILE_SERVER = "http://jugoterapia.josdem.io";
	public static final String SERVICE_NAME = "/jugoterapia-server/beverage/";
	public static final String CATEGORIES_URL = URL_MOBILE_SERVER + SERVICE_NAME + "categories";
	public static final String BEVERAGES_URL = URL_MOBILE_SERVER + SERVICE_NAME + "beverages";
	public static final String RECIPE_URL = URL_MOBILE_SERVER + SERVICE_NAME + "beverage";

	public static final String CONNECTION_TITLE = "Conexión";
	public static final String CONNECTION_MESSAGE = "Por favor verifica tu conexión a Internet";
	public static final String PARSING_CATEGORY_MESSAGE = "No fue posible obtener las categorías";
	public static final String PARSING_BEVERAGE_MESSAGE = "No fue posible obtener las recetas";
}

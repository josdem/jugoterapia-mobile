/*
  Copyright 2015 José Luis De la Cruz Morales <joseluis.delacruz@gmail.com>

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

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

public class ApplicationState {
	public static final String URL_MOBILE_SERVER = "https://webflux.josdem.io/";
	public static final String CONNECTION_TITLE = "Mensaje";
	public static final String CONNECTION_MESSAGE = "Por favor verifica tu conexión a Internet";
	private static  Map<String,String> defaults = new HashMap<>();

	private static FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

	public static void setup(){
		firebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
						.setDeveloperModeEnabled(true)
						.build());

		defaults.put("serviceUrl", ApplicationState.URL_MOBILE_SERVER);

		final Task<Void> fetch = firebaseRemoteConfig.fetch(0);
		fetch.addOnSuccessListener(new OnSuccessListener<Void>() {
			@Override
			public void onSuccess(Void aVoid) {
				firebaseRemoteConfig.activateFetched();
			}
		});
	}

	public static String buildServiceUrl(){
		String serviceUrl = (String) firebaseRemoteConfig.getString("serviceUrl");
		Log.d("serviceUrl", firebaseRemoteConfig.getString("serviceUrl"));
		if (serviceUrl == null) {
			return defaults.get("serviceUrl");
		}
		return (String) firebaseRemoteConfig.getString("serviceUrl");
	}


}
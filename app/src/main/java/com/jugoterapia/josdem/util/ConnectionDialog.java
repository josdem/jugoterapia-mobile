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

package com.jugoterapia.josdem.util;

import com.jugoterapia.josdem.R;
import com.jugoterapia.josdem.state.ApplicationState;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ConnectionDialog {

	public static void show(final Activity activity) {
		new AlertDialog.Builder(activity)
		.setTitle(R.string.connection_error)
		.setMessage(R.string.message)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id){
				activity.finish();
			}
		})
		.show();
	}

}

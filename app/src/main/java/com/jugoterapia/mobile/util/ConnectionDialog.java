package com.jugoterapia.mobile.util;

import com.jugoterapia.mobile.state.ApplicationState;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ConnectionDialog {
	
	public ConnectionDialog(final Activity activity) {
		new AlertDialog.Builder(activity)
		.setTitle(ApplicationState.CONNECTION_TITLE)
		.setMessage(ApplicationState.CONNECTION_MESSAGE)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				activity.finish();
			}
		})
		.show();
	}
	
}

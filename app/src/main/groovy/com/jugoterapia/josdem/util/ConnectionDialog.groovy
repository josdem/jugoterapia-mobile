package com.jugoterapia.josdem.util

import com.jugoterapia.josdem.state.ApplicationState

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface

import groovy.transform.CompileStatic

@CompileStatic
class ConnectionDialog {

	ConnectionDialog(final Activity activity) {
		new AlertDialog.Builder(activity)
		.setTitle(ApplicationState.CONNECTION_TITLE)
		.setMessage(ApplicationState.CONNECTION_MESSAGE)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			void onClick(DialogInterface dialog,int id) {
				activity.finish()
			}
		})
		.show()
	}

}

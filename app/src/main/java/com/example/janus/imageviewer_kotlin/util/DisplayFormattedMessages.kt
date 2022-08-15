package com.example.janus.imageviewer_kotlin.util

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog;
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.janus.imageviewer_kotlin.R

object DisplayFormattedMessages {

    fun displayErrorMessageAlertDialog (alertMessage : String, layoutInflator: LayoutInflater, context : Context) : Unit {

// There is a nicely formatted layout in the resources folder for customm AlertDialogs
        var dialogView = layoutInflator.inflate(R.layout.custom_alert_dialog, null)

        var alertDialogBuilder : AlertDialog.Builder = AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(dialogView)

// The custom formatted message consists of a text field and a close button
        var alertDialogMessage : TextView = dialogView.findViewById(R.id.messageTextView_AlertDialog) as TextView
        alertDialogMessage.setText(alertMessage)

        var errorMessageAlertDialog = alertDialogBuilder.create()
        errorMessageAlertDialog.setCanceledOnTouchOutside(true)

        var dialogButton : Button = dialogView.findViewById(R.id.okButton_AlertDialog) as Button
        dialogButton.setOnClickListener(View.OnClickListener {
            errorMessageAlertDialog.dismiss()
        })

        errorMessageAlertDialog.show()

    }
}
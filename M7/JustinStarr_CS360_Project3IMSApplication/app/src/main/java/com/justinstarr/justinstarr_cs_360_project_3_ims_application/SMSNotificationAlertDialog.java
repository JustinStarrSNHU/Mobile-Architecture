package com.justinstarr.justinstarr_cs_360_project_3_ims_application;

import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

// alert dialog box when the sms button is selected from main activity
public class SMSNotificationAlertDialog {
    public static AlertDialog doubleButton(final MainActivity context){

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.sms_permissions)
                .setIcon(R.drawable.sms_notification)
                .setCancelable(false)
                .setMessage(R.string.sms_permissions_message)
                // actions for the enable button for the alert dialog
                .setPositiveButton(R.string.sms_enable_button, (dialog, arg1) -> {
                    Toast.makeText(context, "SMS Alerts Enabled", Toast.LENGTH_SHORT).show();
                    MainActivity.AllowSendSMS();
                    dialog.cancel();
                })
                // actions for the Disable button for the alert dialog
                .setNegativeButton(R.string.sms_disable_button, (dialog, arg1) -> {
                    Toast.makeText(context, "SMS Alerts Disable", Toast.LENGTH_SHORT).show();
                    MainActivity.DenySendSMS();
                    dialog.cancel();
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}

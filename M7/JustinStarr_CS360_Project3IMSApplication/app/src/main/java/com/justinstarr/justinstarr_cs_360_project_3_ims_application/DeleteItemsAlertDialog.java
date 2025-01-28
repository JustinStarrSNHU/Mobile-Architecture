package com.justinstarr.justinstarr_cs_360_project_3_ims_application;

import androidx.appcompat.app.AlertDialog;

// alert dialog for the delete all items in inventory button from main activity
public class DeleteItemsAlertDialog {
    public static AlertDialog doubleButton(final MainActivity context) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.alert_dialog_delete_title)
                .setIcon(R.drawable.delete)
                .setCancelable(false)
                .setMessage(R.string.alert_dialog_delete_msg)
                // actions for the Yes button of the alert dialog
                .setPositiveButton(R.string.alert_dialog_yes_button, (dialog, arg1) -> {
                    MainActivity.YesDeleteItems();
                    dialog.cancel();
                })
                // actions for the No button of the alert dialog
                .setNegativeButton(R.string.alert_dialog_no_button, (dialog, arg1) -> {
                    MainActivity.NoDeleteItems();
                    dialog.cancel();
                });

        // Create the AlertDialog object and return it
        return builder.create();

    }
}
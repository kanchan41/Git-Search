package com.kanchan.GitSearch.Config;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.kanchan.GitSearch.R;

public class Alert {

    protected static Dialog alertDialog;
    public static void alert(Context context, String mesage) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Something Went Wrong!");
        builder.setMessage(mesage);
        builder.setIcon(R.drawable.ic_warning_black_24dp);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


}

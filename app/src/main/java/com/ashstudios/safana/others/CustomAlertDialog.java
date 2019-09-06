package com.ashstudios.safana.others;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.ashstudios.safana.R;


public class CustomAlertDialog {
    private Context context;
    private AlertDialog alertDialog;
    private TextView tv_alert;

    public CustomAlertDialog(Context context) {
        this.context = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.alert_progress,null);
        tv_alert = v.findViewById(R.id.alert_tv);
        //ProgressBar progressBar = new ProgressBar(context);
        //progressBar.setPadding(10,30,10,30);

        final AlertDialog.Builder alertDialogbuilder = new AlertDialog.Builder(context);
        alertDialog = alertDialogbuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setView(v);
    }

    public void show() {
        alertDialog.show();
    }

    public void dismiss() {
        alertDialog.dismiss();
    }

    public void setTextViewText(String text) {
        tv_alert.setText(text);
    }
    public boolean isShowing() {
        return alertDialog.isShowing();
    }
}

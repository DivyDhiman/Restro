package functionalityAll;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.Button;

import callBacks.DialogClick;

/**
 * Created by Abhay dhiman
 */

public class CentraliseAlertDialog {

    public void alertDialogShow(Context context, String title, String message, final String positive, final String negative, final DialogClick dialogClick) {
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
                dialogClick.onClick(positive);
            }
        });

        alertDialog.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
                dialogClick.onClick(positive);
            }
        });


        android.support.v7.app.AlertDialog alert_dg = alertDialog.create();
        alert_dg.show();

        Button button_background_1 = alert_dg.getButton(DialogInterface.BUTTON_NEGATIVE);
        button_background_1.setTextColor(Color.BLACK);

        Button button_background_2 = alert_dg.getButton(DialogInterface.BUTTON_POSITIVE);
        button_background_2.setTextColor(Color.RED);
    }
}

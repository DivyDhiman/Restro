package functionalityAll;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import restro.bts.com.restro.R;

/**
 * Created by Abhay dhiman
 */

public class CentraliseToast {
    private ViewGroup viewgroup;
    private View view;

    public void toastShow(Context context, String message) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.toast_layout, null);
        TextView toast_message = (TextView) view.findViewById(R.id.toast_message);
        toast_message.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, -0, 280);

        toast.setView(view);
        toast.show();
    }

    public void snackBarShow(CoordinatorLayout coordinatorLayout, String message) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
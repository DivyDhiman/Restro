package functionalityAll;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Abhay dhiman
 */

//Checking Functionality class
public class CentraliseCheck {

    //Check is Edit Text is empty or not
    public boolean emptyCheckEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean emptyCheckTextView(TextView textView) {
        if (textView.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public String spacePut(String text) {
        return text.replaceAll("\\B|\\b", " ");
    }

    //Check is email id is correct in format or not
    public boolean emailEditText(EditText editText) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(editText.getText().toString());
        return matcher.matches();
    }

    //Check password and confirm password matched or not
    public boolean matchesEditText(EditText editText, String match) {
        if (editText.getText().toString().equals(match)) {
            return true;
        } else {
            return false;
        }
    }

    //Check password and confirm password matched or not
    public boolean matchesTextView(String editText, String match) {
        if (editText.equals(match)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean editTextLength(EditText editText, int start, int end) {
        if (editText.getText().toString().trim().length() >= start && editText.getText().toString().trim().length() <= end) {
            return true;
        } else {
            return false;
        }
    }

    //Check internet connectivity
    public boolean isNetWorkStatusAvailable(Context applicationContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                if (networkInfo.isConnected())
                    return true;
            }
        }
        return false;
    }

    public boolean isGPSStatusAvailable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (gps_enabled && network_enabled) {
            // notify user
            return true;
        } else {
            return false;
        }
    }

}
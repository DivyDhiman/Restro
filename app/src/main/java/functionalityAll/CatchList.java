package functionalityAll;


import android.util.Log;

/**
 * Created by Abhay dhiman
 */

public class CatchList {

    public static void Report(Exception exception){
        Log.e("Exception","Exception"+exception);
//        Crashlytics.getInstance().core.logException(exception);
    }
}

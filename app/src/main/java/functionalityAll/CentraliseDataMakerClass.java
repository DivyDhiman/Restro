package functionalityAll;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import controllerAll.ConfigApiParseKey;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import restro.bts.com.restro.R;

@SuppressWarnings("unchecked")
public class CentraliseDataMakerClass {
    private Context context;
    private String callingUI;
    private Object object;
    private HashMap<String, Object> dataGet;
    private Controller controller;
    private JSONObject dataPass;


    public void DataMaker(Object... args) throws Exception {
        context = (Context) args[0];
        callingUI = (String) args[1];
        object = args[2];
        dataGet = (HashMap<String, Object>) args[3];
        controller = (Controller) context.getApplicationContext();

        try {
            callAPIMethod(callingUI);
        } catch (Exception e) {
            Log.e("EXCEPTIONOCCURSS","EXCEPTIONOCCURSS"+e);
            CatchList.Report(e);
        }
    }

    private void callAPIMethod(String callingUI) throws Exception {
        if (callingUI.equals(context.getString(R.string.get_resto_list_api))) {
            Log.e("dataGet","dataGet"+dataGet.toString());
            new AsyncTaskClass(context, callingUI, object, dataGet).execute();
        }else if (callingUI.equals(context.getString(R.string.google_Path_API))) {
            new AsyncTaskClass(context, callingUI, object, dataGet).execute();
        }else if (callingUI.equals(context.getString(R.string.get_resto_detail_api))) {
            new AsyncTaskClass(context, callingUI, object, dataGet).execute();
        }else if (callingUI.equals(context.getString(R.string.check_email_availability_api))) {
            new AsyncTaskClass(context, callingUI, object, dataGet).execute();
        }else if (callingUI.equals(context.getString(R.string.sign_up_api))) {
            new AsyncTaskClass(context, callingUI, object, dataGet).execute();
        }else if (callingUI.equals(context.getString(R.string.logout_api))) {
            new AsyncTaskClass(context, callingUI, object, dataGet).execute();
        }else if (callingUI.equals(context.getString(R.string.get_category_api))) {
            new AsyncTaskClass(context, callingUI, object, dataGet).execute();
        }else if (callingUI.equals(context.getString(R.string.add_reviews_api))) {
            dataPass = new JSONObject();
            dataPass.put("userid",dataGet.get("userID").toString());
            dataPass.put("venueid",dataGet.get("venueID").toString());
            dataPass.put("review",dataGet.get("review").toString());
            dataPass.put("rating",dataGet.get("ratingCount").toString());
            new AsyncTaskClass(context, callingUI, object, dataPass).execute();
        }else if (callingUI.equals(context.getString(R.string.sign_in_api))) {
            new AsyncTaskClass(context, callingUI, object, dataGet).execute();
        }else if (callingUI.equals(context.getString(R.string.forgot_password_api))) {
            new AsyncTaskClass(context, callingUI, object, dataGet).execute();
        }else if (callingUI.equals(context.getString(R.string.get_review_api))) {
            new AsyncTaskClass(context, callingUI, object, dataGet).execute();
        }else if (callingUI.equals(context.getString(R.string.social_login_api))) {
            new AsyncTaskClass(context, callingUI, object, dataGet).execute();
        }
    }

}

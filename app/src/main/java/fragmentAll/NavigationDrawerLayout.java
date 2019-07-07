package fragmentAll;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.HashMap;

import callBacks.ChildApiCallback;
import controllerAll.Config;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import functionalityAll.CatchList;
import functionalityAll.CatchResponse;
import restro.bts.com.restro.CentraliseWebView;
import restro.bts.com.restro.CuisineScreen;
import restro.bts.com.restro.EditProfileScreen;
import restro.bts.com.restro.IntroScreen;
import restro.bts.com.restro.R;

/**
 * Created by Abhay dhiman
 */

@SuppressWarnings("unchecked")
public class NavigationDrawerLayout extends Fragment implements View.OnClickListener {
    private View rootView;
    private Context context;
    private Controller controller;
    private Intent intent;
    private RelativeLayout profileClick;
    private LinearLayout homeClick,cusineClick,aboutClick,logoutClick;
    private ChildApiCallback childApiCallback;
    private HashMap<String,Object> dataGetter;
    private String responseGet;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.navigation_drawer_fragment_view, container, false);
        context = getActivity();
        controller = (Controller) context.getApplicationContext();

        try {
            callBack();
        } catch (Exception e) {
            Log.e("DrawerExp", "DrawerExp" + e);
            CatchResponse.Report(e);
            e.printStackTrace();
        }
        return rootView;
    }

    private void callBack() throws Exception {

        childApiCallback = new ChildApiCallback() {
            @Override
            public void Data_call_back(Object... args) {
                try {
                    getAPIResponse(args);
                } catch (Exception e) {
                    CatchList.Report(e);
                }
            }
        };

        UIComponents();
    }


    private void getAPIResponse(Object[] args) {
        String callingUI = (String) args[0];
        responseGet = (String) args[1];

        if (callingUI.equals(context.getString(R.string.logout_api))) {
            controller.pdStop();
            if (responseGet.equals(context.getString(R.string.error_Http_not_found))) {
                controller.pdStop();
                controller.toastShow(context, context.getString(R.string.error_Http_not_found));
            } else if (responseGet.equals(context.getString(R.string.error_Http_internal))) {
                controller.pdStop();
                controller.toastShow(context, context.getString(R.string.error_Http_internal));
            } else if (responseGet.equals(context.getString(R.string.error_Http_other))) {
                controller.pdStop();
                controller.toastShow(context, context.getString(R.string.error_Http_other));
            } else if (responseGet.equals(context.getString(R.string.error))) {
                controller.pdStop();
                controller.toastShow(context, context.getString(R.string.error));
            } else if (responseGet.equals(context.getString(R.string.status_error))) {
                controller.pdStop();
            } else if (responseGet.equals(context.getString(R.string.message_error))) {
                controller.pdStop();
            } else {
                controller.toastShow(context,responseGet);
                controller.clearSharedPreference();
                intent = new Intent(context, IntroScreen.class);
                startActivity(intent);
            }
        }
    }

    private void UIComponents() {

        profileClick = rootView.findViewById(R.id.profile_click);
        homeClick = rootView.findViewById(R.id.home_click);
        cusineClick = rootView.findViewById(R.id.cusine_click);
        aboutClick = rootView.findViewById(R.id.about_click);
        logoutClick = rootView.findViewById(R.id.logout_click);

        profileClick.setOnClickListener(this);
        homeClick.setOnClickListener(this);
        cusineClick.setOnClickListener(this);
        aboutClick.setOnClickListener(this);
        logoutClick.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profile_click:
                intent = new Intent(context, EditProfileScreen.class);
                startActivity(intent);
                controller.animAllForward(context);
                break;

            case R.id.home_click:
                controller.closeDrawerLayout();
                break;

            case R.id.cusine_click:
                intent = new Intent(context, CuisineScreen.class);
                startActivity(intent);
                controller.animAllForward(context);
                break;

            case R.id.about_click:
                intent = new Intent(context, CentraliseWebView.class);
                intent.putExtra("urlGet", Config.BASE_URL+Config.ABOUT_HTML);
                startActivity(intent);
                controller.animAllForward(context);
                break;

            case R.id.logout_click:
                controller.pdStart(context,"");
                dataGetter = new HashMap<>();
                dataGetter.put("deviceID", controller.getStringSP(ConfigSharedPreferenceKey.deviceID));
                dataGetter.put("userID", controller.getStringSP(ConfigSharedPreferenceKey.userID));
                controller.DataMaker(context, getString(R.string.logout_api), childApiCallback, dataGetter);
                break;


        }
    }
}


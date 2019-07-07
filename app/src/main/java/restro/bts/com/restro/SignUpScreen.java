package restro.bts.com.restro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import callBacks.DialogCallBack;
import callBacks.LocationCallback;
import callBacks.ParentApiCallback;
import callBacks.RunTimePermissionCallBack;
import callBacks.ToolbarCallBack;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import functionalityAll.BaseActivity;
import functionalityAll.CatchList;
import functionalityAll.CentraliseToolBar;

/**
 * Created by Abhay dhiman
 */

public class SignUpScreen extends BaseActivity implements View.OnClickListener {

    private Controller controller;
    private Context context;
    private CoordinatorLayout coordinatorLayout;
    private TextView headerTxt, subHeaderTxt, alreadyHaveAccount, signIn, signUpWithEmailTxt;
    private EditText nameEt, emailEt, passwordEt, confirmPasswordEt;
    private CardView signUpWithEmail;
    private ToolbarCallBack toolbarCallBack;
    private Intent intent;
    private CentraliseToolBar centraliseToolBar;
    private ParentApiCallback parentApiCallback;
    private HashMap<String, Object> dataGetter;
    private LocationCallback locationCallback;
    private DialogCallBack dialogCallBack;
    private RunTimePermissionCallBack runTimePermissionCallBack;
    private String responseGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_screen);
        context = this;
        controller = (Controller) context.getApplicationContext();

        callBacks();
    }

    private void callBacks() {
        toolbarCallBack = new ToolbarCallBack() {
            @Override
            public void toolBarClick(Object... args) {
                String clickCallback = (String) args[0];

                if (clickCallback.equals(getString(R.string.start_view))) {
                    onBackPressed();
                }
            }
        };

        parentApiCallback = new ParentApiCallback() {
            @Override
            public void Data_call_back(Object... args) {
                try {
                    getAPIResponse(args);
                } catch (Exception e) {
                    CatchList.Report(e);
                }
            }
        };

        runTimePermissionCallBack = new RunTimePermissionCallBack() {
            @Override
            public void GetPermissionResult(String result) {
                if (result.equals(getString(R.string.allow_access_location))) {
                    if (controller.isGPSEnable(context)) {
                        controller.pdStart(context, "");
                        callLocation(locationCallback);
                    } else {
                        controller.dialogCustomShow(context, R.layout.custom_alert_layout, getString(R.string.custom_alert_dialog), dialogCallBack);
                    }
                }
            }
        };


        locationCallback = new LocationCallback() {
            @Override
            public void getLocation(double latitude, double longitude) {
                controller.setBooleanSP(ConfigSharedPreferenceKey.isLocationChnaged, true);
                controller.setStringSP(ConfigSharedPreferenceKey.userLatitude, String.valueOf(latitude));
                controller.setStringSP(ConfigSharedPreferenceKey.userLongitude, String.valueOf(longitude));

                try {
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    Log.e("addresses","addresses"+addresses.toString());
                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);

                    String completeAddress = null;
                    if(cityName != null){
                        if(!cityName.equals("")){
                            completeAddress = cityName;
                        }
                    }

                    if(stateName != null){
                        if(!stateName.equals("")){
                            completeAddress = completeAddress + " " +stateName;
                        }
                    }

                    if(countryName != null){
                        if(!countryName.equals("")){
                            completeAddress = completeAddress + " " + countryName;
                        }
                    }

                    String locality = addresses.get(0).getLocality();
                    if(locality == null){
                        locality = addresses.get(0).getSubLocality();
                    }
                    if(locality == null){
                        locality = addresses.get(0).getAdminArea();
                    }
                    if(locality == null) {
                        locality = addresses.get(0).getSubAdminArea();
                    }

                    @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    dataGetter.put("latitude", controller.getStringSP(ConfigSharedPreferenceKey.userLatitude));
                    dataGetter.put("longitude", controller.getStringSP(ConfigSharedPreferenceKey.userLongitude));
                    dataGetter.put("deviceID", android_id);
                    dataGetter.put("city", locality);
                    controller.setStringSP(ConfigSharedPreferenceKey.deviceID,android_id);
                    controller.setStringSP(ConfigSharedPreferenceKey.userLocation, completeAddress);

                    controller.DataMaker(context, getString(R.string.check_email_availability_api), parentApiCallback, dataGetter);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };

        dialogCallBack = new DialogCallBack() {
            @Override
            public void dialogDataCallback(Object... args) {
                String dialogClickType = (String) args[0];
                if (dialogClickType.equals(getString(R.string.positive_txt))) {
                    intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            }
        };

        setCustomActionBar();
        initialise();
    }


    private void getAPIResponse(Object[] args) {
        String callingUI = (String) args[0];
        responseGet = (String) args[1];

        if (callingUI.equals(context.getString(R.string.check_email_availability_api))) {
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
                controller.DataMaker(context, getString(R.string.sign_up_api), parentApiCallback, dataGetter);
            }
        } else if (callingUI.equals(context.getString(R.string.sign_up_api))) {
            controller.pdStop();
            if (responseGet.equals(context.getString(R.string.error_Http_not_found))) {
                controller.toastShow(context, context.getString(R.string.error_Http_not_found));
            } else if (responseGet.equals(context.getString(R.string.error_Http_internal))) {
                controller.toastShow(context, context.getString(R.string.error_Http_internal));
            } else if (responseGet.equals(context.getString(R.string.error_Http_other))) {
                controller.toastShow(context, context.getString(R.string.error_Http_other));
            } else if (responseGet.equals(context.getString(R.string.error))) {
                controller.toastShow(context, context.getString(R.string.error));
            } else if (responseGet.equals(context.getString(R.string.status_error))) {
            } else if (responseGet.equals(context.getString(R.string.message_error))) {
            } else {
                controller.setStringSP(ConfigSharedPreferenceKey.userID, responseGet);
                intent = new Intent(context, DashBoardScreen.class);
                startActivity(intent);
                controller.animAllForward(context);
            }
        }
    }

    private void initialise() {
        headerTxt = findViewById(R.id.header_txt);
        subHeaderTxt = findViewById(R.id.sub_header_txt);
        alreadyHaveAccount = findViewById(R.id.already_have_account);
        signIn = findViewById(R.id.sign_in);
        signUpWithEmailTxt = findViewById(R.id.sign_up_with_email_txt);
        nameEt = findViewById(R.id.name_et);
        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        confirmPasswordEt = findViewById(R.id.confirm_password_et);
        signUpWithEmail = findViewById(R.id.sign_up_with_email);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        controller.introInline(headerTxt, context);
        controller.josefinsansRegularTextView(subHeaderTxt, context);
        controller.josefinsansRegularTextView(signUpWithEmailTxt, context);
        controller.josefinsansRegularTextView(alreadyHaveAccount, context);
        controller.josefinsansRegularEditText(nameEt, context);
        controller.josefinsansRegularEditText(emailEt, context);
        controller.josefinsansRegularEditText(passwordEt, context);
        controller.josefinsansRegularEditText(confirmPasswordEt, context);
        controller.josefinsansBoldTextView(signIn, context);

        signIn.setOnClickListener(this);
        signUpWithEmail.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                intent = new Intent(context, SignInScreen.class);
                startActivity(intent);
                controller.animAllForward(context);
                break;

            case R.id.sign_up_with_email:
                if (controller.isNetWorkStatusAvailable(context)) {
                    if (controller.emptyCheckEditText(nameEt)) {
                        controller.snackBarShow(coordinatorLayout, getString(R.string.empty_name));
                    } else if (controller.emptyCheckEditText(emailEt)) {
                        controller.snackBarShow(coordinatorLayout, getString(R.string.empty_email));
                    } else if (controller.emptyCheckEditText(passwordEt)) {
                        controller.snackBarShow(coordinatorLayout, getString(R.string.empty_password));
                    } else if (controller.emptyCheckEditText(confirmPasswordEt)) {
                        controller.snackBarShow(coordinatorLayout, getString(R.string.empty_confirm_password));
                    } else if (!controller.editTextLength(nameEt, 1, 40)) {
                        controller.snackBarShow(coordinatorLayout, getString(R.string.name_max_length));
                    } else if (!controller.emailEditText(emailEt)) {
                        controller.snackBarShow(coordinatorLayout, getString(R.string.email_valid));
                    } else if (!controller.editTextLength(emailEt, 1, 40)) {
                        controller.snackBarShow(coordinatorLayout, getString(R.string.email_max_length));
                    } else if (!controller.editTextLength(passwordEt, 6, 14)) {
                        controller.snackBarShow(coordinatorLayout, getString(R.string.password_max_length));
                    } else {
                        if (!controller.matchesEditText(passwordEt, confirmPasswordEt.getText().toString())) {
                            controller.snackBarShow(coordinatorLayout, getString(R.string.password_confirm_password_not_matches));
                        } else {
                            dataGetter = new HashMap<>();
                            dataGetter.put("email", emailEt.getText().toString());
                            dataGetter.put("password", passwordEt.getText().toString());
                            dataGetter.put("name", nameEt.getText().toString());

                            checkPermissions(context, runTimePermissionCallBack, android.Manifest.permission.ACCESS_COARSE_LOCATION, getString(R.string.allow_access_location), 29);
                        }
                    }
                } else {
                    controller.snackBarShow(coordinatorLayout, getString(R.string.enable_internet));
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.animAllBackward(context);
    }

    private void setCustomActionBar() {
        centraliseToolBar = new CentraliseToolBar();
        centraliseToolBar.toolBarCustom(getString(R.string.sign_up_screen_toolbar), context, toolbarCallBack);
    }

}

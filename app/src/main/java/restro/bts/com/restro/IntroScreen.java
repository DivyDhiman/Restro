package restro.bts.com.restro;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

import callBacks.ParentApiCallback;
import callBacks.ToolbarCallBack;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import functionalityAll.BaseActivity;
import functionalityAll.CatchList;
import functionalityAll.CatchResponse;
import functionalityAll.CentraliseToolBar;

/**
 * Created by Abhay dhiman
 */

public class IntroScreen extends BaseActivity implements View.OnClickListener {

    private Controller controller;
    private Context context;
    private ToolbarCallBack toolbarCallBack;
    private TextView headerTxt, loginWithFacebookTxt, loginWithEmailTxt;
    private CardView loginWithFacebook, loginWithEmail;
    private CoordinatorLayout coordinatorLayout;
    private Intent intent;
    private CentraliseToolBar centraliseToolBar;
    private CallbackManager callbackManager;
    private HashMap<String, Object> dataGetter;
    private ParentApiCallback parentApiCallback;
    private String responseGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_screen);
        context = this;
        controller = (Controller) context.getApplicationContext();
        changeStatusBarColor(context, R.color.intro_nav_bar_color, R.color.intro_status_bar_color);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
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

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(
                callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Handle success
                        Log.e("loginResult", "loginResult" + loginResult);

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject json, GraphResponse response) {
                                if (response.getError() != null) {
//                                    Toast.makeText(context, getString(R.string.fberror), Toast.LENGTH_SHORT).show();
                                } else {
//                                    try {

//                                        //Check is Email id is public or not
//                                        if (json.has("email")) {
//                                            str_email = json.getString("email");
//                                            str_firstname = json.getString("name");
//                                            str_id = json.getString("id");
//
//                                        } else {
//                                            str_email = "abc@gmail.com";
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
                                }
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, email,gender, birthday, location");
                        request.setParameters(parameters);
                        request.executeAsync();


//                        final AccessToken accessToken = loginResult.getAccessToken();
//                        GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject user, GraphResponse graphResponse) {
//                                Log.e("user", "user" + user.toString());
//                                Log.e("graphResponse", "graphResponse" + graphResponse.toString());

//                                dataGetter = new HashMap<>();
//                                controller.pdStarts(context,"");
//                                dataGetter.put("latitude", controller.getStringSP(ConfigSharedPreferenceKey.userLatitude));
//                                dataGetter.put("longitude", controller.getStringSP(ConfigSharedPreferenceKey.userLongitude));
                        controller.DataMaker(context, getString(R.string.social_login_api), parentApiCallback, dataGetter);
//                            }
//                        }).executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.e("onCancel", "onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("FacebookException", "FacebookException" + exception);
                    }
                }
        );

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

        setCustomActionBar();
        initialise();
    }


    private void getAPIResponse(Object[] args) {
        String callingUI = (String) args[0];
        responseGet = (String) args[1];

        if (callingUI.equals(context.getString(R.string.social_login_api))) {
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
                intent = new Intent(context, DashBoardScreen.class);
                startActivity(intent);
                controller.animAllForward(context);
            }
        }
    }


    private void initialise() {
        headerTxt = findViewById(R.id.header_txt);
        loginWithFacebookTxt = findViewById(R.id.login_with_facebook_txt);
        loginWithEmailTxt = findViewById(R.id.login_with_email_txt);
        loginWithFacebook = findViewById(R.id.login_with_facebook);
        loginWithEmail = findViewById(R.id.login_with_email);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        controller.introInline(headerTxt, context);
        controller.josefinsansBoldTextView(loginWithFacebookTxt, context);
        controller.josefinsansBoldTextView(loginWithEmailTxt, context);

        loginWithFacebook.setOnClickListener(this);
        loginWithEmail.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_with_facebook:
                if (controller.isNetWorkStatusAvailable(context)) {
                    try {
                        LoginManager.getInstance().logOut();
                    } catch (Exception e) {
                        CatchResponse.Report(e);
                    }
                    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos", "email", "user_birthday", "public_profile"));
                } else {
                    controller.snackBarShow(coordinatorLayout, getString(R.string.enable_internet));
                }
                break;

            case R.id.login_with_email:
                intent = new Intent(context, SignInScreen.class);
                startActivity(intent);
                controller.animAllForward(context);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.animAllBackward(context);
    }

    private void setCustomActionBar() {
        centraliseToolBar = new CentraliseToolBar();
        centraliseToolBar.toolBarCustom(getString(R.string.intro_screen_toolbar), context, toolbarCallBack);
    }
}

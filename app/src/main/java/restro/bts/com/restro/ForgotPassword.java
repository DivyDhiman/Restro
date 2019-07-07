package restro.bts.com.restro;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import callBacks.ParentApiCallback;
import callBacks.ToolbarCallBack;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import functionalityAll.BaseActivity;
import functionalityAll.CentraliseToolBar;

/**
 * Created by Abhay dhiman
 */

public class ForgotPassword extends BaseActivity implements View.OnClickListener {

    private Controller controller;
    private Context context;
    private TextView headerTxt,subHeaderTxt, forgotPasswordTxt;
    private EditText emailEt;
    private CardView forgotPasswordParent;
    private CoordinatorLayout coordinatorLayout;
    private ToolbarCallBack toolbarCallBack;
    private ParentApiCallback parentApiCallback;
    private CentraliseToolBar centraliseToolBar;
    private HashMap<String,Object> dataGetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        context = this;
        controller = (Controller) context.getApplicationContext();

        callBacks();
    }

    private void callBacks() {
        toolbarCallBack = new ToolbarCallBack() {
            @Override
            public void toolBarClick(Object... args) {
                String clickCallback = (String) args[0];
                if(clickCallback.equals(getString(R.string.start_view))){
                    onBackPressed();
                }
            }
        };

        parentApiCallback = new ParentApiCallback() {
            @Override
            public void Data_call_back(Object... args) {

            }
        };

        setCustomActionBar();
        initialise();
    }

    private void initialise() {
        headerTxt = findViewById(R.id.header_txt);
        subHeaderTxt = findViewById(R.id.sub_header_txt);
        forgotPasswordTxt = findViewById(R.id.forgot_password_txt);
        emailEt = findViewById(R.id.email_et);
        forgotPasswordParent = findViewById(R.id.forgot_password_parent);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        controller.introInline(headerTxt,context);
        controller.josefinsansRegularTextView(subHeaderTxt,context);
        controller.josefinsansRegularTextView(forgotPasswordTxt,context);
        controller.josefinsansRegularEditText(emailEt,context);

        forgotPasswordParent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgot_password_parent:
                if (controller.isNetWorkStatusAvailable(context)) {
                    if (controller.emptyCheckEditText(emailEt)) {
                        controller.snackBarShow(coordinatorLayout, getString(R.string.empty_email));
                    }else if (!controller.emailEditText(emailEt)) {
                        controller.snackBarShow(coordinatorLayout, getString(R.string.email_valid));
                    }else {
//                        controller.pdStart(context, "");
                        dataGetter = new HashMap<>();
                        dataGetter.put("email", emailEt.getText().toString().trim());
                        controller.DataMaker(context, getString(R.string.forgot_password_api), parentApiCallback, dataGetter);
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
        centraliseToolBar.toolBarCustom(getString(R.string.login_screen_toolbar),context,toolbarCallBack);
    }

}

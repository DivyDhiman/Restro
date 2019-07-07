package restro.bts.com.restro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import callBacks.ParentApiCallback;
import callBacks.ToolbarCallBack;
import controllerAll.ConfigApiParseKey;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import functionalityAll.BaseActivity;
import functionalityAll.CatchList;
import functionalityAll.CentraliseToolBar;

/**
 * Created by Abhay dhiman
 */

public class AddReview extends BaseActivity implements View.OnClickListener {

    private Controller controller;
    private Context context;
    private ToolbarCallBack toolbarCallBack;
    private CentraliseToolBar centraliseToolBar;
    private ImageView ratingRestro1,ratingRestro2,ratingRestro3,ratingRestro4,ratingRestro5;
    private TextView headerTxt,subHeaderTxt,sendTxt;
    private EditText reviewsEt;
    private CardView sendParent;
    private CoordinatorLayout coordinatorLayout;
    private ParentApiCallback parentApiCallback;
    private int ratingCount = 0;
    private HashMap<String,Object> dataGetter;
    private String venuID,responseGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reviews);
        context = this;
        venuID = getIntent().getExtras().getString("venuID");
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

    private void initialise() {
        headerTxt = findViewById(R.id.header_txt);
        subHeaderTxt = findViewById(R.id.sub_header_txt);
        ratingRestro1 = findViewById(R.id.rating_restro1);
        ratingRestro2 = findViewById(R.id.rating_restro2);
        ratingRestro3 = findViewById(R.id.rating_restro3);
        ratingRestro4 = findViewById(R.id.rating_restro4);
        ratingRestro5 = findViewById(R.id.rating_restro5);
        reviewsEt = findViewById(R.id.reviews_et);
        sendParent = findViewById(R.id.send_parent);
        sendTxt = findViewById(R.id.send_txt);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        controller.introInline(headerTxt,context);
        controller.josefinsansRegularTextView(subHeaderTxt,context);
        controller.josefinsansRegularTextView(sendTxt,context);

        ratingRestro1.setOnClickListener(this);
        ratingRestro2.setOnClickListener(this);
        ratingRestro3.setOnClickListener(this);
        ratingRestro4.setOnClickListener(this);
        ratingRestro5.setOnClickListener(this);
        sendParent.setOnClickListener(this);

        setRating(0);
    }

    private void getAPIResponse(Object[] args) {
        String callingUI = (String) args[0];
        responseGet = (String) args[1];

        if (callingUI.equals(context.getString(R.string.add_reviews_api))) {
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
                controller.setBooleanSP(ConfigSharedPreferenceKey.isRestroReviewAPI, true);
                controller.toastShow(context,responseGet);
                onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_parent:
                if(controller.isNetWorkStatusAvailable(context)){
                    if(controller.emptyCheckEditText(reviewsEt)){
                        controller.snackBarShow(coordinatorLayout,getString(R.string.empty_review));
                    }else {
                        if(ratingCount != 0) {
                            controller.pdStarts(context,"");
                            dataGetter = new HashMap<>();
                            dataGetter.put("userID", controller.getStringSP(ConfigSharedPreferenceKey.userID));
                            dataGetter.put("venueID", venuID);
                            dataGetter.put("review",reviewsEt.getText().toString().trim());
                            dataGetter.put("ratingCount", String.valueOf(ratingCount));
                            controller.DataMaker(context, getString(R.string.add_reviews_api), parentApiCallback, dataGetter);
                        }else {
                            controller.snackBarShow(coordinatorLayout,getString(R.string.empty_rating));
                        }
                    }
                }else {
                    controller.snackBarShow(coordinatorLayout,getString(R.string.enable_internet));
                }
                break;

            case R.id.rating_restro1:
                ratingCount = 1;
                setRating(1);
                break;

            case R.id.rating_restro2:
                ratingCount = 2;
                setRating(2);
                break;

            case R.id.rating_restro3:
                ratingCount = 3;
                setRating(3);
                break;

            case R.id.rating_restro4:
                ratingCount = 4;
                setRating(4);
                break;

            case R.id.rating_restro5:
                ratingCount = 5;
                setRating(5);
                break;
        }
    }

    private void setRating(int ratingGet) {
        switch (ratingGet) {
            case 0:
                ratingRestro1.setImageResource(R.drawable.graystaricon);
                ratingRestro2.setImageResource(R.drawable.graystaricon);
                ratingRestro3.setImageResource(R.drawable.graystaricon);
                ratingRestro4.setImageResource(R.drawable.graystaricon);
                ratingRestro5.setImageResource(R.drawable.graystaricon);
                break;

            case 1:
                ratingRestro1.setImageResource(R.drawable.bigredstar);
                ratingRestro2.setImageResource(R.drawable.graystaricon);
                ratingRestro3.setImageResource(R.drawable.graystaricon);
                ratingRestro4.setImageResource(R.drawable.graystaricon);
                ratingRestro5.setImageResource(R.drawable.graystaricon);
                break;

            case 2:
                ratingRestro1.setImageResource(R.drawable.bigredstar);
                ratingRestro2.setImageResource(R.drawable.bigredstar);
                ratingRestro3.setImageResource(R.drawable.graystaricon);
                ratingRestro4.setImageResource(R.drawable.graystaricon);
                ratingRestro5.setImageResource(R.drawable.graystaricon);
                break;

            case 3:
                ratingRestro1.setImageResource(R.drawable.bigredstar);
                ratingRestro2.setImageResource(R.drawable.bigredstar);
                ratingRestro3.setImageResource(R.drawable.bigredstar);
                ratingRestro4.setImageResource(R.drawable.graystaricon);
                ratingRestro5.setImageResource(R.drawable.graystaricon);
                break;

            case 4:
                ratingRestro1.setImageResource(R.drawable.bigredstar);
                ratingRestro2.setImageResource(R.drawable.bigredstar);
                ratingRestro3.setImageResource(R.drawable.bigredstar);
                ratingRestro4.setImageResource(R.drawable.bigredstar);
                ratingRestro5.setImageResource(R.drawable.graystaricon);
                break;

            case 5:
                ratingRestro1.setImageResource(R.drawable.bigredstar);
                ratingRestro2.setImageResource(R.drawable.bigredstar);
                ratingRestro3.setImageResource(R.drawable.bigredstar);
                ratingRestro4.setImageResource(R.drawable.bigredstar);
                ratingRestro5.setImageResource(R.drawable.bigredstar);
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
        centraliseToolBar.toolBarCustom(getString(R.string.add_reviews_screen_toolbar), context, toolbarCallBack, getString(R.string.location_toolbar));
    }
}
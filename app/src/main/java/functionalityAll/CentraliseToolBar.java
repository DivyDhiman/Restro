package functionalityAll;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.util.ArrayList;
import java.util.HashMap;

import callBacks.ChildApiCallback;
import callBacks.ToolbarCallBack;
import controllerAll.Config;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import restro.bts.com.restro.R;

/**
 * Created by Abhay dhiman
 */

public class CentraliseToolBar implements View.OnClickListener {

    private Context context;
    private Toolbar toolbar;
    private AppCompatActivity appCompatActivity;
    private ToolbarCallBack toolbarCallBack;
    private String type_value, headerTxt;
    private ImageView startView,beforeEndView, endView;
    private TextView startTxt, endTxt;
    private Controller controller;
    private Intent intent;

    public void toolBarCustom(Object... args) {
        this.type_value = (String) args[0];
        this.context = (Context) args[1];
        this.toolbarCallBack = (ToolbarCallBack) args[2];

        appCompatActivity = ((AppCompatActivity) context);
        controller = (Controller) context.getApplicationContext();

        toolbar = appCompatActivity.findViewById(R.id.centralise_toolbar);
        appCompatActivity.setSupportActionBar(toolbar);

        startView = toolbar.findViewById(R.id.start_view);
        startTxt = toolbar.findViewById(R.id.start_txt);
        endTxt = toolbar.findViewById(R.id.end_txt);
        beforeEndView = toolbar.findViewById(R.id.before_end_view);
        endView = toolbar.findViewById(R.id.end_view);


        if (type_value.equals(context.getString(R.string.intro_screen_toolbar))) {
            showHideView(0, toolbar);
        } else if (type_value.equals(context.getString(R.string.dash_board_screen_toolbar))) {
            headerTxt = (String) args[3];
            showHideView(1, toolbar);
        } else if (type_value.equals(context.getString(R.string.login_screen_toolbar))) {
            showHideView(2, toolbar);
        } else if (type_value.equals(context.getString(R.string.sign_up_screen_toolbar))) {
            showHideView(3, toolbar);
        } else if (type_value.equals(context.getString(R.string.edit_profile_screen))) {
            headerTxt = (String) args[3];
            showHideView(4, toolbar);
        }else if (type_value.equals(context.getString(R.string.restro_details_screen_toolbar))) {
            headerTxt = (String) args[3];
            showHideView(5, toolbar);
        }else if (type_value.equals(context.getString(R.string.map_screen_toolbar))) {
            headerTxt = (String) args[3];
            showHideView(6, toolbar);
        }else if(type_value.equals(context.getString(R.string.show_reviews_screen_toolbar))){
            showHideView(7, toolbar);
        }else if(type_value.equals(context.getString(R.string.add_reviews_screen_toolbar))){
            showHideView(8, toolbar);
        }else if(type_value.equals(context.getString(R.string.cuisine_screen_toolbar))){
            showHideView(9, toolbar);
        }else if(type_value.equals(context.getString(R.string.filter_screen_toolbar))){
            showHideView(10, toolbar);
        }else if(type_value.equals(context.getString(R.string.full_screen_toolbar))){
            showHideView(11, toolbar);
        }else if(type_value.equals(context.getString(R.string.web_view_screen_toolbar))){
            showHideView(12, toolbar);
        }

    }


    private void showHideView(int val, Toolbar toolbar) {

        switch (val) {
            case 0:
                startView.setVisibility(View.VISIBLE);
                startView.setBackgroundResource(R.drawable.back_arrow);
                startView.setOnClickListener(this);
                break;

            case 1:
                startView.setVisibility(View.VISIBLE);
                startTxt.setVisibility(View.VISIBLE);
                endView.setVisibility(View.VISIBLE);

                startView.setBackgroundResource(R.drawable.optionbtn);
                endView.setBackgroundResource(R.drawable.filtericon);
                startTxt.setText(headerTxt);
                controller.introInline(startTxt, context);

                startView.setOnClickListener(this);
                endView.setOnClickListener(this);
                break;

            case 2:
                startView.setVisibility(View.VISIBLE);
                startView.setBackgroundResource(R.drawable.back_arrow_black);
                startView.setOnClickListener(this);
                break;

            case 3:
                startView.setVisibility(View.VISIBLE);
                startView.setBackgroundResource(R.drawable.back_arrow_black);
                startView.setOnClickListener(this);
                break;

            case 4:
                startView.setVisibility(View.VISIBLE);
                startTxt.setVisibility(View.VISIBLE);
                endTxt.setVisibility(View.VISIBLE);

                startView.setBackgroundResource(R.drawable.back_arrow_black);
                startTxt.setText(headerTxt);
                endTxt.setText(context.getText(R.string.edit));

                controller.introInline(startTxt,context);
                controller.josefinsansRegularTextView(endTxt,context);

                startView.setOnClickListener(this);
                endTxt.setOnClickListener(this);
                break;

            case 5:
                startView.setVisibility(View.VISIBLE);
                startTxt.setVisibility(View.VISIBLE);
                beforeEndView.setVisibility(View.VISIBLE);
                endView.setVisibility(View.VISIBLE);

                startView.setBackgroundResource(R.drawable.back_arrow);
                startTxt.setText(headerTxt);

                controller.introInline(startTxt,context);
                startView.setOnClickListener(this);
                break;

            case 6:
                startView.setVisibility(View.VISIBLE);
                startTxt.setVisibility(View.VISIBLE);

                startView.setBackgroundResource(R.drawable.back_arrow);
                startTxt.setText(headerTxt);

                controller.introInline(startTxt,context);

                startView.setOnClickListener(this);
                toolbar.setBackgroundColor(ContextCompat.getColor(context,R.color.toolbar_map_screen));
                break;

            case 7:
                startView.setVisibility(View.VISIBLE);
                startView.setBackgroundResource(R.drawable.back_arrow_black);
                startView.setOnClickListener(this);
                break;

            case 8:
                startView.setVisibility(View.VISIBLE);
                startView.setBackgroundResource(R.drawable.back_arrow_black);
                startView.setOnClickListener(this);
                break;

            case 9:
                startView.setVisibility(View.VISIBLE);
                startView.setBackgroundResource(R.drawable.back_arrow_black);
                startView.setOnClickListener(this);
                break;

            case 10:
                startView.setVisibility(View.VISIBLE);
                startView.setBackgroundResource(R.drawable.back_arrow_black);
                startView.setOnClickListener(this);
                break;

            case 11:
                startView.setVisibility(View.VISIBLE);
                startView.setBackgroundResource(R.drawable.back_arrow);
                startView.setOnClickListener(this);
                break;

            case 12:
                startView.setVisibility(View.VISIBLE);
                startView.setBackgroundResource(R.drawable.back_arrow);
                startView.setOnClickListener(this);
                break;

        }
    }


    public void changeTextType(boolean checkType){
        if(checkType){
            endTxt.setText(context.getText(R.string.save));
        }else {
            endTxt.setText(context.getText(R.string.edit));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_view:
                toolbarCallBack.toolBarClick(context.getString(R.string.start_view));
                break;

            case R.id.end_view:
                toolbarCallBack.toolBarClick(context.getString(R.string.end_view));
                break;

            case R.id.end_txt:
                toolbarCallBack.toolBarClick(context.getString(R.string.end_view));
                break;

            case R.id.before_end_view:
                toolbarCallBack.toolBarClick(context.getString(R.string.before_end_view));
                break;

        }
    }


}
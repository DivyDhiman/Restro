package restro.bts.com.restro;


import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;

import callBacks.ToolbarCallBack;
import controllerAll.ConfigApiParseKey;
import controllerAll.Controller;
import functionalityAll.BaseActivity;
import functionalityAll.CentraliseToolBar;

/**
 * Created by Abhay dhiman
 */

public class FullImageView extends BaseActivity {

    private Controller controller;
    private Context context;
    private ToolbarCallBack toolbarCallBack;
    private ImageView imageShow;
    private CentraliseToolBar centraliseToolBar;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_image);
        context = this;
        controller = (Controller) context.getApplicationContext();
        changeStatusBarColor(context,R.color.intro_nav_bar_color,R.color.intro_status_bar_color);

        imageUrl = getIntent().getExtras().getString("imageUrl");

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

        setCustomActionBar();
        initialise();
    }

    private void initialise() {
        imageShow = findViewById(R.id.image_show);

        RequestOptions options = new RequestOptions();
        options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false);
        Glide.with(context).load(imageUrl).apply(options).into(imageShow);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.animAllBackward(context);
    }

    private void setCustomActionBar() {
        centraliseToolBar = new CentraliseToolBar();
        centraliseToolBar.toolBarCustom(getString(R.string.full_screen_toolbar),context,toolbarCallBack);
    }
}

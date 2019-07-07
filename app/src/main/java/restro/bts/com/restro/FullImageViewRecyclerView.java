package restro.bts.com.restro;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashMap;

import adapterAll.CentraliseRecyclerViewAdapter;
import callBacks.RecyclerViewCallBack;
import callBacks.ToolbarCallBack;
import controllerAll.Controller;
import functionalityAll.BaseActivity;
import functionalityAll.CentraliseToolBar;

/**
 * Created by Abhay dhiman
 */

@SuppressWarnings("ALL")
public class FullImageViewRecyclerView extends BaseActivity {


    private Controller controller;
    private Context context;
    private ToolbarCallBack toolbarCallBack;
    private CentraliseToolBar centraliseToolBar;
    private ArrayList<HashMap<String,Object>> data;
    private int getPosition;
    private RecyclerViewCallBack recyclerViewCallBack;
    private CentraliseRecyclerViewAdapter centraliseRecyclerViewAdapter;
    private RecyclerView galleryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_image_recycler);
        context = this;
        controller = (Controller) context.getApplicationContext();
        changeStatusBarColor(context,R.color.intro_nav_bar_color,R.color.intro_status_bar_color);

        data = (ArrayList<HashMap<String, Object>>) getIntent().getExtras().get("dataIntent");
        getPosition = getIntent().getExtras().getInt("postion");

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

        recyclerViewCallBack = new RecyclerViewCallBack() {
            @Override
            public void dataGet(Object... args) {

            }
        };

        setCustomActionBar();
        initialise();
    }

    private void initialise() {
        galleryList = findViewById(R.id.gallery_list);

        centraliseRecyclerViewAdapter = new CentraliseRecyclerViewAdapter(context, data, R.layout.full_image_adapter, getString(R.string.full_image_adapter_view), recyclerViewCallBack, galleryList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(galleryList.getContext(),LinearLayoutManager.HORIZONTAL,false);
        galleryList.getItemAnimator().setChangeDuration(0);
        galleryList.setLayoutManager(linearLayoutManager);
        galleryList.setAdapter(centraliseRecyclerViewAdapter);
        galleryList.smoothScrollToPosition(getPosition);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(galleryList);

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

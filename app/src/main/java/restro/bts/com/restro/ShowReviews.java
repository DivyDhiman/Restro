package restro.bts.com.restro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import adapterAll.CentraliseRecyclerViewAdapter;
import callBacks.ParentApiCallback;
import callBacks.RecyclerViewCallBack;
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

@SuppressWarnings("ALL")
public class ShowReviews extends BaseActivity implements View.OnClickListener {

    private Controller controller;
    private Context context;
    private ToolbarCallBack toolbarCallBack;
    private CentraliseToolBar centraliseToolBar;
    private TextView headerTxt,addReviews;
    private SwipeRefreshLayout swipeRefreshReviews;
    private RecyclerView reviewList;
    private CardView addReviewsParent;
    private RelativeLayout errorDataParent;
    private TextView errorMessageTxt;
    private CoordinatorLayout coordinatorLayout;
    private ArrayList<HashMap<String,Object>> data,dataGetIntent;
    private HashMap<String,Object> dataGetter;
    private CentraliseRecyclerViewAdapter centraliseRecyclerViewAdapter;
    private RecyclerViewCallBack recyclerViewCallBack;
    private Intent intent;
    private ParentApiCallback parentApiCallback;
    private String venuID,responseGet;
    private boolean isReviewHit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_reviews);
        context = this;
        controller = (Controller) context.getApplicationContext();
        dataGetIntent = (ArrayList<HashMap<String, Object>>) getIntent().getExtras().get("dataTips");
        venuID = getIntent().getExtras().getString("venuID");
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

        if (callingUI.equals(context.getString(R.string.get_review_api))) {
            controller.pdStop();
            swipeRefreshReviews.setRefreshing(false);
            Log.e("responseGet","responseGet"+responseGet);
            if (responseGet.equals(context.getString(R.string.error_Http_not_found))) {
                HideVisibleErrorLayout(0, getString(R.string.error_Http_not_found));
            } else if (responseGet.equals(context.getString(R.string.error_Http_internal))) {
                HideVisibleErrorLayout(0, getString(R.string.error_Http_internal));
            } else if (responseGet.equals(context.getString(R.string.error_Http_other))) {
                HideVisibleErrorLayout(0, getString(R.string.error_Http_other));
            } else if (responseGet.equals(context.getString(R.string.error))) {
                HideVisibleErrorLayout(0, getString(R.string.error));
            } else if (responseGet.equals(context.getString(R.string.status_error))) {
                HideVisibleErrorLayout(0, getString(R.string.status_error));
            } else if (responseGet.equals(context.getString(R.string.message_error))) {
                data = new ArrayList<>();
                if(dataGetIntent != null){
                    if(dataGetIntent.size() > 0){
                        for (int i=0; i < dataGetIntent.size();i++) {
                            data.add(dataGetIntent.get(i));
                        }

                        Collections.sort(data, new Comparator<HashMap<String, Object>>() {
                            public int compare(HashMap<String, Object> mapping1, HashMap<String, Object> mapping2) {
                                return Integer.parseInt(mapping2.get(ConfigApiParseKey.RESTRO_REVIEW_CREATED_AT).toString()) - Integer.parseInt(mapping1.get(ConfigApiParseKey.RESTRO_REVIEW_CREATED_AT).toString());
                            }
                        });
                        centraliseRecyclerViewAdapter.updateData(data);
                    }
                }

                HideVisibleErrorLayout(1);
            }else {
                data = new ArrayList<>();
                data = (ArrayList<HashMap<String, Object>>) args[2];
                if(dataGetIntent != null){
                    if(dataGetIntent.size() > 0){
                        for (int i=0; i < dataGetIntent.size();i++) {
                            data.add(dataGetIntent.get(i));
                        }
                    }
                }

                Collections.sort(data, new Comparator<HashMap<String, Object>>() {
                    public int compare(HashMap<String, Object> mapping1, HashMap<String, Object> mapping2) {
                        return Integer.parseInt(mapping2.get(ConfigApiParseKey.RESTRO_REVIEW_CREATED_AT).toString()) - Integer.parseInt(mapping1.get(ConfigApiParseKey.RESTRO_REVIEW_CREATED_AT).toString());
                    }
                });

                centraliseRecyclerViewAdapter.updateData(data);
                HideVisibleErrorLayout(1);
            }
        }
    }

    private void initialise() {
        headerTxt = findViewById(R.id.header_txt);
        reviewList = findViewById(R.id.review_list);
        swipeRefreshReviews = findViewById(R.id.swipe_refresh_reviews);
        addReviewsParent = findViewById(R.id.add_reviews_parent);
        errorDataParent = findViewById(R.id.error_data_parent);
        errorMessageTxt = findViewById(R.id.error_message_txt);
        addReviews = findViewById(R.id.add_reviews);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        controller.introInline(headerTxt,context);
        controller.josefinsansRegularTextView(addReviews,context);

        addReviewsParent.setOnClickListener(this);

        data = new ArrayList<>();
        centraliseRecyclerViewAdapter = new CentraliseRecyclerViewAdapter(context, data, R.layout.restro_review_adapter, getString(R.string.restro_review_adapter_view), recyclerViewCallBack,reviewList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(reviewList.getContext());
        reviewList.getItemAnimator().setChangeDuration(0);
        reviewList.setLayoutManager(linearLayoutManager);
        reviewList.setAdapter(centraliseRecyclerViewAdapter);

        swipeRefreshReviews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshReviews.setRefreshing(true);
                if (controller.isNetWorkStatusAvailable(context)) {
                    controller.setBooleanSP(ConfigSharedPreferenceKey.isRestroReviewAPI, false);
                    dataGetter = new HashMap<>();
                    dataGetter.put("venuID", venuID);
                    dataGetter.put("userID", controller.getStringSP(ConfigSharedPreferenceKey.userID));
                    dataGetter.put("skip", String.valueOf(0));
                    dataGetter.put("limit", "100");
                    controller.DataMaker(context, getString(R.string.get_review_api), parentApiCallback, dataGetter);
                } else {
                    swipeRefreshReviews.setRefreshing(false);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (controller.isNetWorkStatusAvailable(context)) {
            isReviewHit = controller.getBooleanSP(ConfigSharedPreferenceKey.isRestroReviewAPI);
            if (isReviewHit) {
                if(data.size() >0 ){
                    swipeRefreshReviews.setRefreshing(true);
                }else {
                    controller.pdStarts(context, "");
                }

                controller.setBooleanSP(ConfigSharedPreferenceKey.isRestroReviewAPI, false);
                dataGetter = new HashMap<>();
                dataGetter.put("venuID", venuID);
                dataGetter.put("userID", controller.getStringSP(ConfigSharedPreferenceKey.userID));
                dataGetter.put("skip", String.valueOf(0));
                dataGetter.put("limit", "100");
                controller.DataMaker(context, getString(R.string.get_review_api), parentApiCallback, dataGetter);
            }
        }else {
            HideVisibleErrorLayout(0, getString(R.string.enable_internet));
        }
    }

    public void HideVisibleErrorLayout(Object... args) {
        int type = (int) args[0];

        switch (type) {
            case 0:
                String message = (String) args[1];
                if (data.size() > 0) {
                    reviewList.setVisibility(View.GONE);
                    errorDataParent.setVisibility(View.GONE);
                    errorMessageTxt.setText(message);
                    controller.snackBarShow(coordinatorLayout, message);
                } else {
                    reviewList.setVisibility(View.GONE);
                    errorDataParent.setVisibility(View.VISIBLE);
                    errorMessageTxt.setText(message);
                }
                break;

            case 1:
                if (data.size() > 0) {
                    errorDataParent.setVisibility(View.GONE);
                    reviewList.setVisibility(View.VISIBLE);
                } else {
                    errorDataParent.setVisibility(View.VISIBLE);
                    reviewList.setVisibility(View.GONE);
                    errorMessageTxt.setText(getString(R.string.empty_restro_list));
                }
                break;

            case 2:
                String messageCoordinate = (String) args[1];
                errorMessageTxt.setText(messageCoordinate);
                controller.snackBarShow(coordinatorLayout, messageCoordinate);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_reviews_parent:
                intent = new Intent(context,AddReview.class);
                intent.putExtra("venuID",venuID);
                startActivity(intent);
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
        centraliseToolBar.toolBarCustom(getString(R.string.show_reviews_screen_toolbar), context, toolbarCallBack, getString(R.string.location_toolbar));
    }

}

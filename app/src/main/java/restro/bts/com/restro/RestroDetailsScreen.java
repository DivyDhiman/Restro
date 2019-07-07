package restro.bts.com.restro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
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
public class RestroDetailsScreen extends BaseActivity implements View.OnClickListener {

    private Controller controller;
    private Context context;
    private CoordinatorLayout coordinatorLayout;
    private ToolbarCallBack toolbarCallBack;
    private ImageView imageShow, callView, ratingRestro1, ratingRestro2, ratingRestro3, ratingRestro4, ratingRestro5, likeImage;
    private TextView distanceTxt, headerTxt, locationTxt, openNowTxt, openNowDetail, galleryTxt, reviewsTxt, likeCount, checkInCount,
            specialTxt;
    private RecyclerView galleryList;
    private CardView reviews;
    private LinearLayout locationClick;
    private CentraliseToolBar centraliseToolBar;
    private Intent intent;
    private ParentApiCallback parentApiCallback;
    private RecyclerViewCallBack recyclerViewCallBack;
    private ArrayList<HashMap<String, Object>> data, dataTips;
    private HashMap<String, Object> dataGetter, dataIntent;
    private CentraliseRecyclerViewAdapter centraliseRecyclerViewAdapter;
    private String completeAddress, responseGet,specialOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restro_detail_screen);
        context = this;
        controller = (Controller) context.getApplicationContext();
        changeStatusBarColor(context, R.color.intro_nav_bar_color, R.color.intro_status_bar_color);

        dataIntent = (HashMap<String, Object>) getIntent().getExtras().get("dataIntent");
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

        recyclerViewCallBack = new RecyclerViewCallBack() {
            @Override
            public void dataGet(Object... args) {

            }
        };

        setCustomActionBar();
        initialise();
    }


    private void getAPIResponse(Object[] args) {
        String callingUI = (String) args[0];
        responseGet = (String) args[1];

        Log.e("responseGet", "responseGet" + responseGet);
        Log.e("callingUI", "callingUI" + callingUI);

        if (callingUI.equals(context.getString(R.string.get_resto_detail_api))) {
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
                data = new ArrayList<>();
                data = (ArrayList<HashMap<String, Object>>) args[2];
                centraliseRecyclerViewAdapter.updateData(data);
                dataTips = (ArrayList<HashMap<String, Object>>) args[3];
                specialOffer = (String) args[4];
//                specialTxt.setText(specialOffer);
            }
        }
    }

    private void initialise() {
        imageShow = findViewById(R.id.image_show);
        likeImage = findViewById(R.id.like_image);
        likeCount = findViewById(R.id.like_count);
        checkInCount = findViewById(R.id.check_in_count);
        ratingRestro1 = findViewById(R.id.rating_restro1);
        ratingRestro2 = findViewById(R.id.rating_restro2);
        ratingRestro3 = findViewById(R.id.rating_restro3);
        ratingRestro4 = findViewById(R.id.rating_restro4);
        ratingRestro5 = findViewById(R.id.rating_restro5);

        specialTxt = findViewById(R.id.special_txt);
        callView = findViewById(R.id.call_view);
        distanceTxt = findViewById(R.id.distance_txt);
        headerTxt = findViewById(R.id.header_txt);
        locationTxt = findViewById(R.id.location_txt);
        openNowTxt = findViewById(R.id.open_now_txt);
        openNowDetail = findViewById(R.id.open_now_detail);
        galleryTxt = findViewById(R.id.gallery_txt);
        reviewsTxt = findViewById(R.id.reviews_txt);
        reviews = findViewById(R.id.reviews);
        locationClick = findViewById(R.id.location_click);
        galleryList = findViewById(R.id.gallery_list);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        controller.jsosefinsansSemiBoldTextView(distanceTxt, context);
        controller.jsosefinsansSemiBoldTextView(headerTxt, context);
        controller.josefinsansRegularTextView(locationTxt, context);
        controller.jsosefinsansSemiBoldTextView(openNowTxt, context);
        controller.jsosefinsansSemiBoldTextView(openNowDetail, context);
        controller.jsosefinsansSemiBoldTextView(galleryTxt, context);
        controller.jsosefinsansSemiBoldTextView(reviewsTxt, context);
        controller.jsosefinsansSemiBoldTextView(likeCount, context);
        controller.jsosefinsansSemiBoldTextView(checkInCount, context);
        controller.jsosefinsansSemiBoldTextView(specialTxt, context);

        imageShow.setOnClickListener(this);
        locationClick.setOnClickListener(this);
        reviews.setOnClickListener(this);


        if (!dataIntent.get(ConfigApiParseKey.RESTRO_NUMBER).toString().equals(getString(R.string.no_value))) {
            callView.setOnClickListener(this);
        } else {
            callView.setVisibility(View.GONE);
        }

        data = new ArrayList<>();
        centraliseRecyclerViewAdapter = new CentraliseRecyclerViewAdapter(context, data, R.layout.restro_detail_adapter, getString(R.string.restro_detail_adapter_view), recyclerViewCallBack, galleryList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(galleryList.getContext(), 4);
        galleryList.getItemAnimator().setChangeDuration(0);
        galleryList.setLayoutManager(gridLayoutManager);
        galleryList.setAdapter(centraliseRecyclerViewAdapter);

        if (!dataIntent.get(ConfigApiParseKey.RESTRO_ADDRESS).toString().equals(context.getString(R.string.no_value))) {
            completeAddress = dataIntent.get(ConfigApiParseKey.RESTRO_ADDRESS).toString();
        }

        if (!dataIntent.get(ConfigApiParseKey.RESTRO_CITY).toString().equals(context.getString(R.string.no_value))) {
            completeAddress = completeAddress + " " + dataIntent.get(ConfigApiParseKey.RESTRO_CITY).toString();
        }

        if (!dataIntent.get(ConfigApiParseKey.RESTRO_STATE).toString().equals(context.getString(R.string.no_value))) {
            completeAddress = completeAddress + " " + dataIntent.get(ConfigApiParseKey.RESTRO_STATE).toString();
        }

        if (!dataIntent.get(ConfigApiParseKey.RESTRO_COUNTRY).toString().equals(context.getString(R.string.no_value))) {
            completeAddress = completeAddress + " " + dataIntent.get(ConfigApiParseKey.RESTRO_COUNTRY).toString();
        }

        RequestOptions options = new RequestOptions();
        options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false);
        Glide.with(context).load(dataIntent.get(ConfigApiParseKey.RESTRO_IMAGE).toString()).apply(options).into(imageShow);
        headerTxt.setText(dataIntent.get(ConfigApiParseKey.RESTRO_NAME).toString());
        locationTxt.setText(completeAddress);

        double distance = Double.parseDouble(dataIntent.get(ConfigApiParseKey.RESTRO_DISTANCE).toString());
        distanceTxt.setText(String.valueOf(distance / 1000) + " " + context.getString(R.string.kilo_meter));

        if (!dataIntent.get(ConfigApiParseKey.RESTRO_OPEN_NOW).toString().equals(getString(R.string.no_value))) {
            if (dataIntent.get(ConfigApiParseKey.RESTRO_OPEN_NOW).toString().equals("true")) {
                openNowTxt.setText(getString(R.string.open_now));
            } else {
                openNowTxt.setText(getString(R.string.close_now));
            }
        } else {
            openNowTxt.setText(getString(R.string.not_available));
        }

        if (!dataIntent.get(ConfigApiParseKey.RESTRO_OPEN_NOW).toString().equals(getString(R.string.no_value))) {
            openNowDetail.setText(dataIntent.get(ConfigApiParseKey.RESTRO_OPEN_NOW_TIME).toString());
        } else {
            openNowDetail.setText(getString(R.string.not_available));
        }

        double rating = Double.parseDouble(dataIntent.get(ConfigApiParseKey.RESTRO_RATING).toString()) / 2;
        int convertRatingToInt = (int) rating;
        setRating(convertRatingToInt);


        if (!dataIntent.get(ConfigApiParseKey.RESTRO_LIKE_COUNT).toString().equals(context.getString(R.string.no_value))) {
            likeImage.setVisibility(View.VISIBLE);
            likeCount.setVisibility(View.VISIBLE);
            likeCount.setText(dataIntent.get(ConfigApiParseKey.RESTRO_LIKE_COUNT).toString() + " " + context.getString(R.string.likes));
        } else {
            likeImage.setVisibility(View.GONE);
            likeCount.setVisibility(View.GONE);
        }

        if (!dataIntent.get(ConfigApiParseKey.RESTRO_CHECKIN_COUNT).toString().equals(context.getString(R.string.no_value))) {
            checkInCount.setVisibility(View.VISIBLE);
            checkInCount.setText(dataIntent.get(ConfigApiParseKey.RESTRO_CHECKIN_COUNT).toString() + " " + context.getString(R.string.checkin));
        } else {
            checkInCount.setVisibility(View.GONE);
        }


        dataGetter = new HashMap<>();
        dataGetter.put("categoryID", dataIntent.get(ConfigApiParseKey.RESTRO_ID));
        dataGetter.put("radius", "800");
        dataGetter.put("limit", "10");
        controller.DataMaker(context, getString(R.string.get_resto_detail_api), parentApiCallback, dataGetter);
    }

    private void setRating(int ratingGet) {
        switch (ratingGet) {
            case 0:
                ratingRestro1.setImageResource(R.drawable.smallgraystar);
                ratingRestro2.setImageResource(R.drawable.smallgraystar);
                ratingRestro3.setImageResource(R.drawable.smallgraystar);
                ratingRestro4.setImageResource(R.drawable.smallgraystar);
                ratingRestro5.setImageResource(R.drawable.smallgraystar);
                break;

            case 1:
                ratingRestro1.setImageResource(R.drawable.smallredstar);
                ratingRestro2.setImageResource(R.drawable.smallgraystar);
                ratingRestro3.setImageResource(R.drawable.smallgraystar);
                ratingRestro4.setImageResource(R.drawable.smallgraystar);
                ratingRestro5.setImageResource(R.drawable.smallgraystar);
                break;

            case 2:
                ratingRestro1.setImageResource(R.drawable.smallredstar);
                ratingRestro2.setImageResource(R.drawable.smallredstar);
                ratingRestro3.setImageResource(R.drawable.smallgraystar);
                ratingRestro4.setImageResource(R.drawable.smallgraystar);
                ratingRestro5.setImageResource(R.drawable.smallgraystar);
                break;

            case 3:
                ratingRestro1.setImageResource(R.drawable.smallredstar);
                ratingRestro2.setImageResource(R.drawable.smallredstar);
                ratingRestro3.setImageResource(R.drawable.smallredstar);
                ratingRestro4.setImageResource(R.drawable.smallgraystar);
                ratingRestro5.setImageResource(R.drawable.smallgraystar);
                break;

            case 4:
                ratingRestro1.setImageResource(R.drawable.smallredstar);
                ratingRestro2.setImageResource(R.drawable.smallredstar);
                ratingRestro3.setImageResource(R.drawable.smallredstar);
                ratingRestro4.setImageResource(R.drawable.smallredstar);
                ratingRestro5.setImageResource(R.drawable.smallgraystar);
                break;

            case 5:
                ratingRestro1.setImageResource(R.drawable.smallredstar);
                ratingRestro2.setImageResource(R.drawable.smallredstar);
                ratingRestro3.setImageResource(R.drawable.smallredstar);
                ratingRestro4.setImageResource(R.drawable.smallredstar);
                ratingRestro5.setImageResource(R.drawable.smallredstar);
                break;

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_show:
                intent = new Intent(context, FullImageView.class);
                intent.putExtra("imageUrl", dataIntent.get(ConfigApiParseKey.RESTRO_IMAGE).toString());
                startActivity(intent);
                controller.animAllForward(context);
                break;

            case R.id.call_view:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + dataIntent.get(ConfigApiParseKey.RESTRO_NUMBER).toString()));
                startActivity(intent);
                break;

            case R.id.location_click:
                intent = new Intent(context, MapScreen.class);
                intent.putExtra("name", dataIntent.get(ConfigApiParseKey.RESTRO_NAME).toString());
                intent.putExtra("completeAddress", completeAddress);
                intent.putExtra("distance", dataIntent.get(ConfigApiParseKey.RESTRO_DISTANCE).toString());
                intent.putExtra("destinationLatitude", dataIntent.get(ConfigApiParseKey.RESTRO_LATITUDE).toString());
                intent.putExtra("destinationLongitude", dataIntent.get(ConfigApiParseKey.RESTRO_LONGITUDE).toString());
                startActivity(intent);
                break;

            case R.id.reviews:
                controller.setBooleanSP(ConfigSharedPreferenceKey.isRestroReviewAPI, true);
                intent = new Intent(context, ShowReviews.class);
                intent.putExtra("dataTips", dataTips);
                intent.putExtra("venuID", dataIntent.get(ConfigApiParseKey.RESTRO_ID).toString());
                startActivity(intent);
                controller.animAllForward(context);
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
        centraliseToolBar.toolBarCustom(getString(R.string.restro_details_screen_toolbar), context, toolbarCallBack, getString(R.string.near_by));
    }

}

package restro.bts.com.restro;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import callBacks.ToolbarCallBack;
import controllerAll.Config;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import functionalityAll.BaseActivity;
import functionalityAll.CentraliseToolBar;
import functionalityAll.PlaceAPI;

/**
 * Created by Abhay dhiman
 */


public class FilterScreen extends BaseActivity implements View.OnClickListener {
    private Controller controller;
    private Context context;
    private CentraliseToolBar centraliseToolBar;
    private ToolbarCallBack toolbarCallBack;
    private TextView headerTxt, distanceTxt, minTxt, maxTxt, categoryTxt, categorySelect, locationTxt, saveTxt,progressTxt;
    private AutoCompleteTextView locationSelect;
    private LinearLayout categoryParent, locationParent;
    private CardView saveParent;
    private CoordinatorLayout coordinatorLayout;
    private SeekBar simpleProgressBar;
    private PlacesAutoCompleteAdapter mAdapter;
    HandlerThread mHandlerThread;
    Handler mThreadHandler;
    private String description = "";
    private Intent intent;
    private int step = 1,max = 100,min = 0,radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);
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
        setCustomActionBar();
        initialise();
    }

    private void initialise() {
        headerTxt = findViewById(R.id.header_txt);
        distanceTxt = findViewById(R.id.distance_txt);
        minTxt = findViewById(R.id.min_txt);
        maxTxt = findViewById(R.id.max_txt);
        progressTxt = findViewById(R.id.progress_txt);
        categoryTxt = findViewById(R.id.category_txt);
        categorySelect = findViewById(R.id.category_select);
        locationTxt = findViewById(R.id.location_txt);
        locationSelect = findViewById(R.id.location_select);
        saveParent = findViewById(R.id.save_parent);
        saveTxt = findViewById(R.id.save_txt);
        categoryParent = findViewById(R.id.category_parent);
        locationParent = findViewById(R.id.location_parent);
        simpleProgressBar = findViewById(R.id.simple_progress_bar);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        controller.introInline(headerTxt, context);
        controller.josefinsansRegularTextView(distanceTxt, context);
        controller.josefinsansRegularTextView(minTxt, context);
        controller.josefinsansRegularTextView(maxTxt, context);
        controller.josefinsansRegularTextView(progressTxt, context);
        controller.josefinsansRegularTextView(categoryTxt, context);
        controller.josefinsansRegularTextView(categorySelect, context);
        controller.josefinsansRegularTextView(locationTxt, context);
        controller.josefinsansRegularTextView(locationSelect, context);
        controller.josefinsansRegularTextView(saveTxt, context);

        categoryParent.setOnClickListener(this);
        saveParent.setOnClickListener(this);

        simpleProgressBar.setMax( (max - min) / step );
        radius = controller.getIntnSP(ConfigSharedPreferenceKey.userRadius);

        if(radius == Config.DEFAULT_MIN_RADIUS){
            simpleProgressBar.setProgress(0);
        }else {
            simpleProgressBar.setProgress(radius/1000);
        }


        simpleProgressBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser) {
                        int value = min + (progress * step);

                        if(value == 0) {
                            radius = Config.DEFAULT_MIN_RADIUS;
                        }else {
                            radius = value * 1000;
                        }

                        progressTxt.setText(getString(R.string.progress) + ": "+String.valueOf(value)+" "+ getString(R.string.km));
                    }
                }
        );

        String completeAddress = controller.getStringSP(ConfigSharedPreferenceKey.userLocation);
        if(!completeAddress.equals(Config.NO_DATA) && !completeAddress.equals("")){
            locationSelect.setText(completeAddress);
            description = completeAddress;
        }

        mAdapter = new PlacesAutoCompleteAdapter(context, R.layout.autocomplete_list_item);

        if (mThreadHandler == null) {
            mHandlerThread = new HandlerThread(getString(R.string.app_name), android.os.Process.THREAD_PRIORITY_BACKGROUND);
            mHandlerThread.start();

            // Initialize the Handler
            mThreadHandler = new Handler(mHandlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        ArrayList<String> results = mAdapter.resultList;
                        if (results != null && results.size() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.notifyDataSetInvalidated();
                                }
                            });
                        }
                    }
                }
            };
        }

        locationSelect.setAdapter(mAdapter);

        locationSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                description = (String) parent.getItemAtPosition(position);
            }
        });

        locationSelect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String value = s.toString();
                mThreadHandler.removeCallbacksAndMessages(null);
                mThreadHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            mAdapter.resultList = mAdapter.mPlaceAPI.autocomplete(value);
                            if (mAdapter.resultList.size() > 0)
                                mAdapter.resultList.add("footer");
                            mThreadHandler.sendEmptyMessage(1);
                        }catch (Exception e){
                        }
                    }
                }, 500);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.category_parent:
                if (controller.isNetWorkStatusAvailable(context)) {
                    intent = new Intent(context,CuisineScreen.class);
                    startActivity(intent);
                } else {
                    controller.snackBarShow(coordinatorLayout, getString(R.string.enable_internet));
                }
                break;

            case R.id.save_parent:
                if (controller.isNetWorkStatusAvailable(context)) {
                    if (description != null) {
                        if (!description.equals("")) {
                            try {
                                Geocoder coder = new Geocoder(context);
                                List<Address> address = coder.getFromLocationName(description, 5);
                                if (address != null) {
                                    Address location = address.get(0);
                                    controller.setStringSP(ConfigSharedPreferenceKey.userLatitude, String.valueOf(location.getLatitude()));
                                    controller.setStringSP(ConfigSharedPreferenceKey.userLongitude, String.valueOf(location.getLongitude()));
                                    controller.setStringSP(ConfigSharedPreferenceKey.userLocation, description);
                                }
                                controller.setIntSP(ConfigSharedPreferenceKey.userRadius,radius);
                                controller.setBooleanSP(ConfigSharedPreferenceKey.isRestroAPI, true);
                                controller.toastShow(context,getString(R.string.filter_applied_successfully));
                                onBackPressed();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    controller.toastShow(context, getString(R.string.soon_complete));
                }
                break;

        }
    }


    class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

        ArrayList<String> resultList;

        Context mContext;
        int mResource;

        PlaceAPI mPlaceAPI = new PlaceAPI();

        public PlacesAutoCompleteAdapter(Context context, int resource) {
            super(context, resource);

            mContext = context;
            mResource = resource;
        }

        @Override
        public int getCount() {
            // Last item will be the footer
            return resultList.size() - 1;
        }

        @Override
        public String getItem(int position) {
            return resultList.get(position);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        resultList = mPlaceAPI.autocomplete(constraint.toString());

                        // Footer
                        resultList.add("footer");

                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };

            return filter;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Get rid of our Place API Handlers
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacksAndMessages(null);
            mHandlerThread.quit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.animAllBackward(context);
    }

    private void setCustomActionBar() {
        centraliseToolBar = new CentraliseToolBar();
        centraliseToolBar.toolBarCustom(getString(R.string.filter_screen_toolbar), context, toolbarCallBack);
    }
}

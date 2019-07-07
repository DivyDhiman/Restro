package restro.bts.com.restro;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import adapterAll.CentraliseRecyclerViewAdapter;
import callBacks.DialogCallBack;
import callBacks.LocationCallback;
import callBacks.ParentApiCallback;
import callBacks.RecyclerViewCallBack;
import callBacks.RunTimePermissionCallBack;
import callBacks.ToolbarCallBack;
import controllerAll.Config;
import controllerAll.ConfigApiParseKey;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import fragmentAll.NavigationDrawerLayout;
import functionalityAll.BaseActivity;
import functionalityAll.CatchList;
import functionalityAll.CentraliseToolBar;
import functionalityAll.PlaceAPI;

/**
 * Created by Abhay dhiman
 */

@SuppressWarnings("unchecked")
public class DashBoardScreen extends BaseActivity implements View.OnClickListener {

    private Controller controller;
    private Context context;
    private ImageButton locationBtn, searchBtn;
    private AutoCompleteTextView searchBar;
    private SwipeRefreshLayout swipeRefreshRestro;
    private RecyclerView restroList;
    private RecyclerViewCallBack recyclerViewCallBack;
    private RelativeLayout errorDataParent;
    private TextView errorMessageTxt;
    private CoordinatorLayout coordinatorLayout;
    private CentraliseRecyclerViewAdapter centraliseRecyclerViewAdapter;
    private ArrayList<HashMap<String, Object>> data;
    private HashMap<String, Object> dataSub, dataGetter;
    private ParentApiCallback parentApiCallback;
    private ToolbarCallBack toolbarCallBack;
    private CentraliseToolBar centraliseToolBar;
    private LocationCallback locationCallback;
    private DialogCallBack dialogCallBack;
    private RunTimePermissionCallBack runTimePermissionCallBack;
    private boolean checkLocationHit = false, isApiHit = false;
    private String responseGet;
    private Intent intent;
    private PlacesAutoCompleteAdapter mAdapter;
    HandlerThread mHandlerThread;
    Handler mThreadHandler;
    private int radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board_screen);
        context = this;
        controller = (Controller) context.getApplicationContext();
        changeStatusBarColor(context, R.color.intro_nav_bar_color, R.color.intro_status_bar_color);
        controller.initialiseDrawer(context);

        callBacks();
    }

    private void callBacks() {

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

        toolbarCallBack = new ToolbarCallBack() {
            @Override
            public void toolBarClick(Object... args) {
                String clickCallback = (String) args[0];

                if (clickCallback.equals(getString(R.string.start_view))) {
                    controller.openDrawerLayout();
                } else if (clickCallback.equals(getString(R.string.end_view))) {
                    intent = new Intent(context, FilterScreen.class);
                    startActivity(intent);
                }
            }
        };


        runTimePermissionCallBack = new RunTimePermissionCallBack() {
            @Override
            public void GetPermissionResult(String result) {
                if (result.equals(getString(R.string.allow_access_location))) {
                    if (controller.isGPSEnable(context)) {
                        if (data.size() > 0) {
                            swipeRefreshRestro.setRefreshing(true);
                        } else {
                            controller.pdStart(context, "");
                        }
                        locationBtn.setBackgroundResource(R.drawable.redgps);
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

                checkLocationHit = controller.getBooleanSP(ConfigSharedPreferenceKey.isLocationChnaged);

                controller.setBooleanSP(ConfigSharedPreferenceKey.isLocationChnaged, true);
                controller.setStringSP(ConfigSharedPreferenceKey.userLatitude, String.valueOf(latitude));
                controller.setStringSP(ConfigSharedPreferenceKey.userLongitude, String.valueOf(longitude));

                try {
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);

                    String completeAddress = null;
                    if (cityName != null) {
                        if (!cityName.equals("")) {
                            completeAddress = cityName;
                        }
                    }

                    if (stateName != null) {
                        if (!stateName.equals("")) {
                            completeAddress = completeAddress + " " + stateName;
                        }
                    }

                    if (countryName != null) {
                        if (!countryName.equals("")) {
                            completeAddress = completeAddress + " " + countryName;
                        }
                    }

                    searchBar.setText(completeAddress);
                    controller.setStringSP(ConfigSharedPreferenceKey.userLocation, completeAddress);
                } catch (Exception e) {

                }

                radius = controller.getIntnSP(ConfigSharedPreferenceKey.userRadius);
                dataGetter = new HashMap<>();
                dataGetter.put("latitude", latitude);
                dataGetter.put("longitude", longitude);
                dataGetter.put("radius", String.valueOf(radius));
                dataGetter.put("limit", "10");
                controller.DataMaker(context, getString(R.string.get_resto_list_api), parentApiCallback, dataGetter);
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

        if (callingUI.equals(context.getString(R.string.get_resto_list_api))) {
            controller.pdStop();
            swipeRefreshRestro.setRefreshing(false);
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
                HideVisibleErrorLayout(0, getString(R.string.message_error));
            } else {
                data = new ArrayList<>();
                data = (ArrayList<HashMap<String, Object>>) args[2];

                Collections.sort(data, new Comparator<HashMap<String, Object>>() {
                    public int compare(HashMap<String, Object> mapping1, HashMap<String, Object> mapping2) {
                        return Integer.parseInt(mapping1.get(ConfigApiParseKey.RESTRO_DISTANCE).toString()) - Integer.parseInt(mapping2.get(ConfigApiParseKey.RESTRO_DISTANCE).toString());
                    }
                });

                centraliseRecyclerViewAdapter.updateData(data);
                HideVisibleErrorLayout(1);
            }
        }
    }


    private void initialise() {
        locationBtn = findViewById(R.id.location_btn);
        searchBar = findViewById(R.id.search_bar);
        searchBtn = findViewById(R.id.search_btn);
        swipeRefreshRestro = findViewById(R.id.swipe_refresh_restro);
        errorDataParent = findViewById(R.id.error_data_parent);
        errorMessageTxt = findViewById(R.id.error_message_txt);
        restroList = findViewById(R.id.restro_list);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        locationBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);

        swipeRefreshRestro.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshRestro.setRefreshing(true);
                if (controller.isNetWorkStatusAvailable(context)) {
                    radius = controller.getIntnSP(ConfigSharedPreferenceKey.userRadius);
                    dataGetter = new HashMap<>();
                    dataGetter.put("latitude", controller.getStringSP(ConfigSharedPreferenceKey.userLatitude));
                    dataGetter.put("longitude", controller.getStringSP(ConfigSharedPreferenceKey.userLongitude));
                    dataGetter.put("radius", String.valueOf(radius));
                    dataGetter.put("limit", "10");
                    controller.DataMaker(context, getString(R.string.get_resto_list_api), parentApiCallback, dataGetter);
                } else {
                    swipeRefreshRestro.setRefreshing(false);
                }
            }
        });

        data = new ArrayList<>();
        centraliseRecyclerViewAdapter = new CentraliseRecyclerViewAdapter(context, data, R.layout.dash_board_adapter, getString(R.string.restro_list_adapter_view), recyclerViewCallBack, restroList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(restroList.getContext());
        restroList.getItemAnimator().setChangeDuration(0);
        restroList.setLayoutManager(linearLayoutManager);
        restroList.setAdapter(centraliseRecyclerViewAdapter);

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

        searchBar.setAdapter(mAdapter);

        searchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String description = (String) parent.getItemAtPosition(position);
                try {
                    Geocoder coder = new Geocoder(context);
                    List<Address> address = coder.getFromLocationName(description, 5);
                    if (address != null) {
                        Address location = address.get(0);
                        controller.setStringSP(ConfigSharedPreferenceKey.userLatitude, String.valueOf(location.getLatitude()));
                        controller.setStringSP(ConfigSharedPreferenceKey.userLongitude, String.valueOf(location.getLongitude()));
                        controller.setStringSP(ConfigSharedPreferenceKey.userLocation, description);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
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
                        } catch (Exception e) {
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
    protected void onStart() {
        super.onStart();
        try {
            onStartCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onStartCall() throws Exception {
        String completeAddress = controller.getStringSP(ConfigSharedPreferenceKey.userLocation);
        if (!completeAddress.equals(Config.NO_DATA) && !completeAddress.equals("")) {
            if (!completeAddress.trim().equals(searchBar.getText().toString().trim()))
                searchBar.setText(completeAddress);
        }

        checkLocationHit = controller.getBooleanSP(ConfigSharedPreferenceKey.isLocationChnaged);

        isApiHit = controller.getBooleanSP(ConfigSharedPreferenceKey.isRestroAPI);

        if (controller.isNetWorkStatusAvailable(context)) {
            if (!isApiHit) {
                if (!checkLocationHit) {
                    checkPermissions(context, runTimePermissionCallBack, android.Manifest.permission.ACCESS_COARSE_LOCATION, getString(R.string.allow_access_location), 29);
                } else {
                    if (data != null) {
                        if (data.size() < 1) {
                            controller.pdStart(context, "");
                            radius = controller.getIntnSP(ConfigSharedPreferenceKey.userRadius);
                            dataGetter = new HashMap<>();
                            dataGetter.put("latitude", controller.getStringSP(ConfigSharedPreferenceKey.userLatitude));
                            dataGetter.put("longitude", controller.getStringSP(ConfigSharedPreferenceKey.userLongitude));
                            dataGetter.put("radius", String.valueOf(radius));
                            dataGetter.put("limit", "10");
                            controller.DataMaker(context, getString(R.string.get_resto_list_api), parentApiCallback, dataGetter);
                        }
                    }
                }
            } else {
                if (data != null) {
                    if (data.size() > 0) {
                        swipeRefreshRestro.setRefreshing(true);
                    } else {
                        controller.pdStart(context, "");
                    }
                }else {
                    data = new ArrayList<>();
                    controller.pdStart(context, "");
                }

                controller.setBooleanSP(ConfigSharedPreferenceKey.isRestroAPI, false);
                radius = controller.getIntnSP(ConfigSharedPreferenceKey.userRadius);
                dataGetter = new HashMap<>();
                dataGetter.put("latitude", controller.getStringSP(ConfigSharedPreferenceKey.userLatitude));
                dataGetter.put("longitude", controller.getStringSP(ConfigSharedPreferenceKey.userLongitude));
                dataGetter.put("radius", String.valueOf(radius));
                dataGetter.put("limit", "10");
                controller.DataMaker(context, getString(R.string.get_resto_list_api), parentApiCallback, dataGetter);
            }
        } else {
            HideVisibleErrorLayout(0, getString(R.string.enable_internet));
        }
    }


    public void HideVisibleErrorLayout(Object... args) {
        int type = (int) args[0];

        switch (type) {
            case 0:
                String message = (String) args[1];
                if (data.size() > 0) {
                    restroList.setVisibility(View.GONE);
                    errorDataParent.setVisibility(View.GONE);
                    errorMessageTxt.setText(message);
                    controller.snackBarShow(coordinatorLayout, message);
                } else {
                    restroList.setVisibility(View.GONE);
                    errorDataParent.setVisibility(View.VISIBLE);
                    errorMessageTxt.setText(message);
                }
                break;

            case 1:
                if (data.size() > 0) {
                    errorDataParent.setVisibility(View.GONE);
                    restroList.setVisibility(View.VISIBLE);
                } else {
                    errorDataParent.setVisibility(View.VISIBLE);
                    restroList.setVisibility(View.GONE);
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
        switch (view.getId()) {
            case R.id.location_btn:
                checkPermissions(context, runTimePermissionCallBack, android.Manifest.permission.ACCESS_COARSE_LOCATION, getString(R.string.allow_access_location), 29);
                break;

            case R.id.search_btn:
                if (data.size() > 0) {
                    swipeRefreshRestro.setRefreshing(true);
                } else {
                    controller.pdStart(context, "");
                }

                data = new ArrayList<>();
                radius = controller.getIntnSP(ConfigSharedPreferenceKey.userRadius);
                dataGetter = new HashMap<>();
                dataGetter.put("latitude", controller.getStringSP(ConfigSharedPreferenceKey.userLatitude));
                dataGetter.put("longitude", controller.getStringSP(ConfigSharedPreferenceKey.userLongitude));
                dataGetter.put("radius", String.valueOf(radius));
                dataGetter.put("limit", "10");
                controller.DataMaker(context, getString(R.string.get_resto_list_api), parentApiCallback, dataGetter);
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

    public void replaceFragmentDrawer() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.replace_layout_navigation, new NavigationDrawerLayout());
        fragmentTransaction.addToBackStack(null);
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        if (controller.checkDrawerOpen()) {
            controller.closeDrawerLayout();
        } else {
            finish();
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

    private void setCustomActionBar() {
        centraliseToolBar = new CentraliseToolBar();
        centraliseToolBar.toolBarCustom(getString(R.string.dash_board_screen_toolbar), context, toolbarCallBack, getString(R.string.home));
        replaceFragmentDrawer();
    }

}

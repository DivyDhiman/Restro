package restro.bts.com.restro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import callBacks.DialogCallBack;
import callBacks.LocationCallback;
import callBacks.ParentApiCallback;
import callBacks.RunTimePermissionCallBack;
import callBacks.ToolbarCallBack;
import controllerAll.Config;
import controllerAll.ConfigApiParseKey;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import functionalityAll.BaseActivity;
import functionalityAll.CatchList;
import functionalityAll.CentraliseToolBar;

/**
 * Created by Abhay dhiman
 */

public class MapScreen extends BaseActivity {

    private Controller controller;
    private Context context;
    private ArrayList<LatLng> pollyLinesPoints;
    private HashMap<String, Object> dataGet;
    private CoordinatorLayout coordinatorLayout;
    private CentraliseToolBar centraliseToolBar;
    private TextView headerTxt, distanceTxt, addressTxt;
    private GoogleMap mMap;
    private LatLng latLng;
    private Marker marker;
    private boolean checkLocationHit = false;
    private ToolbarCallBack toolbarCallBack;
    private RunTimePermissionCallBack runTimePermissionCallBack;
    private OnMapReadyCallback onMapReadyCallback;
    private LocationCallback locationCallback;
    private DialogCallBack dialogCallBack;
    private ParentApiCallback parentApiCallback;
    private String name,completeAddress,distance,responseGet, getPollyLines;
    private Intent intent;
    private Double latitudeDestination,longitudeDestination;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_screen);
        context = this;
        controller = (Controller) context.getApplicationContext();
        changeStatusBarColor(context, R.color.intro_nav_bar_color, R.color.status_bar_map_screen);

        name  = getIntent().getExtras().getString("name");
        completeAddress = getIntent().getExtras().getString("completeAddress");
        distance = getIntent().getExtras().getString("distance");
        latitudeDestination = Double.valueOf(getIntent().getExtras().getString("destinationLatitude"));
        longitudeDestination = Double.valueOf(getIntent().getExtras().getString("destinationLongitude"));

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

        runTimePermissionCallBack = new RunTimePermissionCallBack() {
            @Override
            public void GetPermissionResult(String result) {
                if (result.equals(getString(R.string.allow_access_location))) {
                    Log.e("isGPSEnable", "isGPSEnable" + controller.isGPSEnable(context));
                    if (controller.isGPSEnable(context)) {
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.address_map);
                        mapFragment.getMapAsync(onMapReadyCallback);
                    } else {
                        controller.dialogCustomShow(context, R.layout.custom_alert_layout, getString(R.string.custom_alert_dialog), dialogCallBack);
                    }
                }
            }
        };


        onMapReadyCallback = new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMyLocationEnabled(true);
                callLocation(locationCallback);
            }
        };

        parentApiCallback = new ParentApiCallback() {
            @Override
            public void Data_call_back(Object... args) {
                try {
                    getAPIResponse(args);
                } catch (Exception e) {
                    CatchList.Report(e);
                    e.printStackTrace();
                }
            }
        };

        locationCallback = new LocationCallback() {
            @Override
            public void getLocation(double latitude, double longitude) {
                checkLocationHit = true;

                controller.pdStart(context, "");
                dataGet = new HashMap<>();
                dataGet.put("latitudeOrigin", String.valueOf(latitude));
                dataGet.put("longitudeOrigin", String.valueOf(longitude));
                dataGet.put("latitudeDestination", String.valueOf(latitudeDestination));
                dataGet.put("longitudeDestination", String.valueOf(longitudeDestination));
                controller.DataMaker(context, getString(R.string.google_Path_API), parentApiCallback, dataGet);
                LatLng latLng = new LatLng(latitudeDestination, longitudeDestination);
                drawPin(latLng);
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

    private void getAPIResponse(Object[] args) throws Exception {
        String callingUI = (String) args[0];
        responseGet = (String) args[1];
        if (callingUI.equals(getString(R.string.google_Path_API))) {
            if (responseGet.equals(getString(R.string.error_Http_not_found))) {
                controller.pdStop();
                controller.snackBarShow(coordinatorLayout, getString(R.string.error_Http_not_found));
            } else if (responseGet.equals(getString(R.string.error_Http_internal))) {
                controller.pdStop();
                controller.snackBarShow(coordinatorLayout, getString(R.string.error_Http_internal));
            } else if (responseGet.equals(getString(R.string.error_Http_other))) {
                controller.pdStop();
                controller.snackBarShow(coordinatorLayout, getString(R.string.error_Http_other));
            } else if (responseGet.equals(getString(R.string.error))) {
                controller.pdStop();
                controller.snackBarShow(coordinatorLayout, getString(R.string.error));
            } else {
                controller.pdStop();

                getPollyLines = (String) args[2];
                pollyLinesPoints = new ArrayList<>();

                pollyLinesPoints = decodePoly(getPollyLines);

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.addAll(pollyLinesPoints);
                polylineOptions.width(10);
                polylineOptions.color(Color.RED);
                if (polylineOptions != null) {
                    mMap.addPolyline(polylineOptions);
                }
            }
        }
    }

    private void initialise() {
        headerTxt = findViewById(R.id.header_txt);
        distanceTxt = findViewById(R.id.distance_txt);
        addressTxt = findViewById(R.id.address_txt);
        controller.josefinsansRegularTextView(headerTxt, context);
        controller.josefinsansBoldTextView(distanceTxt, context);
        controller.josefinsansRegularTextView(addressTxt, context);

        headerTxt.setText(name);

        double distanceDouble = Double.parseDouble(distance);
        distanceTxt.setText(String.valueOf(distanceDouble/1000) + " "+ context.getString(R.string.kilo_meter));
        addressTxt.setText(completeAddress);
    }


    private ArrayList<LatLng> decodePoly(String encoded) {
        ArrayList<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!checkLocationHit) {
            checkPermissions(context, runTimePermissionCallBack, android.Manifest.permission.ACCESS_COARSE_LOCATION, getString(R.string.allow_access_location), 29);
        }
    }

    public void drawPin(LatLng latlng) {
        this.latLng = latlng;
        mMap.getUiSettings().setCompassEnabled(false);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).bearing(90).tilt(30).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if (marker != null) {
            marker.remove();
        }

        marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.redloactionpin)));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.animAllBackward(context);
    }

    private void setCustomActionBar() {
        centraliseToolBar = new CentraliseToolBar();
        centraliseToolBar.toolBarCustom(getString(R.string.map_screen_toolbar), context, toolbarCallBack, getString(R.string.location_toolbar));
    }

}

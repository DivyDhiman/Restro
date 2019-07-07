package functionalityAll;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;

import callBacks.LocationCallback;
import callBacks.RunTimePermissionCallBack;
import restro.bts.com.restro.R;


/**
 * Created by Abhay dhiman
 */

public class BaseActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private int current_api_Version = Build.VERSION.SDK_INT, code;
    private Context context;
    private RunTimePermissionCallBack runTimePermissionCallBack;
    private String get_result;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Location mCurrentLocation;
    protected String mLastUpdateTime;
    private boolean checkLocationGetReady, checkLocation;
    //    private LocationManager manager;
    private FusedLocationProviderClient mFusedLocationClient;
    private double latitude, longitude;
    private LocationCallback locationCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLocation = false;
//        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLastUpdateTime = "";
        updateValuesFromBundle(savedInstanceState);
    }

    public void callLocation(LocationCallback locationCallback) {

        Log.e("locationCallback", "locationCallback");
        this.locationCallback = locationCallback;
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        checkLocationGetReady = true;
    }

    //Update location from google fused api
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }
        }
    }

    //synchronized google fused location api
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    //create location request
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    public int Current_Build_version() {
        return current_api_Version;
    }

    public void changeStatusBarColor(Context context, int color_nav, int color_status) {

        if (current_api_Version >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setNavigationBarColor(ContextCompat.getColor(context, color_nav));
            getWindow().setStatusBarColor(ContextCompat.getColor(context, color_status));
        }
    }


    public void checkPermissions(Context context, RunTimePermissionCallBack runTimePermissionCallBack, String permission, String get_result, int code) {
        this.context = context;
        this.runTimePermissionCallBack = runTimePermissionCallBack;
        this.get_result = get_result;
        this.code = code;

        if (current_api_Version >= Build.VERSION_CODES.LOLLIPOP) {
            if (isAllowed(permission)) {
                runTimePermissionCallBack.GetPermissionResult(get_result);
            } else {
                requestPermission(permission);
            }
        } else {
            runTimePermissionCallBack.GetPermissionResult(get_result);
        }
    }

    private boolean isAllowed(String permission) {

        //  Getting the permission status
        int result = ContextCompat.checkSelfPermission(context, permission);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
        }

        ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == code) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                runTimePermissionCallBack.GetPermissionResult(get_result);
            } else {
                runTimePermissionCallBack.GetPermissionResult(getString(R.string.deny_runtime_permission));
            }
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.e("mCurrentLocation", "mCurrentLocation" + mCurrentLocation);
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            checkLocation = true;
        }
        startLocationUpdates();
    }

    //check connection suspended
    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    //Location update
    protected void startLocationUpdates() {
        Log.e("checkLocation", "checkLocation" + checkLocation);
        if (checkLocation) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    //location update close when activity closed
    protected void stopLocationUpdates() {
        if (checkLocation) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void onResume() {
        super.onResume();
        try {
            if (checkLocationGetReady) {
                if (mGoogleApiClient.isConnected()) {
                    startLocationUpdates();
                }
            }
        } catch (Exception e) {
            CatchResponse.Report(e);
        }
    }

    //when activity goes on pause
    @Override
    protected void onPause() {
        try {
            if (checkLocationGetReady) {
                if (mGoogleApiClient.isConnected()) {
                    stopLocationUpdates();
                }
            }
        } catch (Exception e) {
            CatchResponse.Report(e);
        }
        super.onPause();
    }


    @Override
    protected void onStop() {
        try {
            if (checkLocationGetReady) {
                mGoogleApiClient.disconnect();
            }
        } catch (Exception e) {
            CatchResponse.Report(e);
        }
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.e("location", "location" + location.getLatitude());

        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

        if (checkLocationGetReady) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            checkLocationGetReady = false;
            locationCallback.getLocation(latitude, longitude);
            try {
                if (mGoogleApiClient != null) {
                    if (mGoogleApiClient.isConnected()) {
                        stopLocationUpdates();
                        mGoogleApiClient.disconnect();
                    }
                }
            } catch (Exception e) {
                CatchResponse.Report(e);
            }
        }
    }
}
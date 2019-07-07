package restro.bts.com.restro;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import callBacks.DialogCallBack;
import callBacks.ParentApiCallback;
import callBacks.RunTimePermissionCallBack;
import callBacks.ToolbarCallBack;
import controllerAll.Config;
import controllerAll.ConfigApiParseKey;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import functionalityAll.BaseActivity;
import functionalityAll.CentraliseToolBar;
import functionalityAll.PlaceAPI;

/**
 * Created by Abhay dhiman
 */


public class EditProfileScreen extends BaseActivity implements View.OnClickListener {

    private Controller controller;
    private Context context;
    private CoordinatorLayout coordinatorLayout;
    private TextView nameTxt, locationTxt;
    private EditText nameEt;
    private RelativeLayout profileClick;
    private ImageView profileImage;
    private AutoCompleteTextView locationEt;
    private ToolbarCallBack toolbarCallBack;
    private boolean isSave = false;
    private CentraliseToolBar centraliseToolBar;
    private ParentApiCallback parentApiCallback;
    private PlacesAutoCompleteAdapter mAdapter;
    HandlerThread mHandlerThread;
    Handler mThreadHandler;
    private String description = "";
    private RunTimePermissionCallBack runTimePermissionCallBack;
    private DialogCallBack dialogCallBack;
    private Intent intent;
    private static final int CAMERA_REQUEST = 1888,PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_screen);
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
                } else if (clickCallback.equals(getString(R.string.end_view))) {
                    if (isSave) {
                        if (controller.isNetWorkStatusAvailable(context)) {
                            if (controller.emptyCheckEditText(nameEt)) {
                                controller.snackBarShow(coordinatorLayout, getString(R.string.empty_name));
                            } else if (controller.emptyCheckEditText(locationEt)) {
                                controller.snackBarShow(coordinatorLayout, getString(R.string.empty_location));
                            }else if(!controller.editTextLength(nameEt,1,40)){
                                controller.snackBarShow(coordinatorLayout,getString(R.string.name_max_length));
                            } else {
                                if(description != null) {
                                    if (!description.equals("")) {
                                        try {
                                            Geocoder coder = new Geocoder(context);
                                            List<Address> address = coder.getFromLocationName(description,5);
                                            if (address != null) {
                                                Address location = address.get(0);
                                                controller.setStringSP(ConfigSharedPreferenceKey.userLatitude, String.valueOf(location.getLatitude()));
                                                controller.setStringSP(ConfigSharedPreferenceKey.userLongitude, String.valueOf(location.getLongitude()));
                                                controller.setStringSP(ConfigSharedPreferenceKey.userLocation, description);
                                                controller.setBooleanSP(ConfigSharedPreferenceKey.isRestroAPI,true);
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                isSave = false;
                                centraliseToolBar.changeTextType(isSave);
                                editTextClickHideShow(0);
                            }
                        } else {
                            controller.snackBarShow(coordinatorLayout, getString(R.string.enable_internet));
                        }
                    } else {
                        isSave = true;
                        centraliseToolBar.changeTextType(isSave);
                        editTextClickHideShow(1);
                    }
                }
            }
        };

        parentApiCallback = new ParentApiCallback() {
            @Override
            public void Data_call_back(Object... args) {
                String dialoClickCallback = (String) args[0];
                if (dialoClickCallback.equals(getString(R.string.gallery))) {
                    ImageBrowse();
                } else if (dialoClickCallback.equals(getString(R.string.camera))) {
                    captureImage();
                }
            }
        };

        runTimePermissionCallBack = new RunTimePermissionCallBack() {
            @Override
            public void GetPermissionResult(String result) {
                if (result.equals(getString(R.string.allow_access_storage))) {
                    checkPermissions(context, runTimePermissionCallBack, Manifest.permission.CAMERA, getString(R.string.allow_access_camera), 30);
                } else if (result.equals(getString(R.string.allow_access_camera))) {
                    controller.dialogCustomShow(context, R.layout.activity_profile_dialog, getString(R.string.profile_dialog), dialogCallBack);
                }
            }
        };

        dialogCallBack = new DialogCallBack() {
            @Override
            public void dialogDataCallback(Object... args) {
                String dialoClickCallback = (String) args[0];
                if (dialoClickCallback.equals(getString(R.string.gallery))) {
                    ImageBrowse();
                } else if (dialoClickCallback.equals(getString(R.string.camera))) {
                    captureImage();
                }
            }
        };


        setCustomActionBar();
        initialise();
    }

    private void initialise() {
        profileClick = findViewById(R.id.profile_click);
        profileImage = findViewById(R.id.profile_image);
        nameTxt = findViewById(R.id.name_txt);
        locationTxt = findViewById(R.id.location_txt);
        nameEt = findViewById(R.id.name_et);
        locationEt = findViewById(R.id.location_et);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        controller.josefinsansRegularTextView(nameTxt, context);
        controller.josefinsansRegularTextView(locationTxt, context);
        controller.josefinsansBoldEditText(nameEt, context);
        controller.josefinsansBoldEditText(locationEt, context);

        editTextClickHideShow(0);

        profileClick.setOnClickListener(this);

        String completeAddress = controller.getStringSP(ConfigSharedPreferenceKey.userLocation);
        if(!completeAddress.equals(Config.NO_DATA) && !completeAddress.equals("")){
            locationEt.setText(completeAddress);
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
                        }
                        else {
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

        locationEt.setAdapter(mAdapter);

        locationEt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                description = (String) parent.getItemAtPosition(position);
            }
        });

        locationEt.addTextChangedListener(new TextWatcher() {
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
        switch (view.getId()){
            case R.id.profile_click:
                checkPermissions(context, runTimePermissionCallBack, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.allow_access_storage), 29);
                break;
        }
    }


    public void editTextClickHideShow(int getType) {
        switch (getType) {
            case 0:
                nameEt.setEnabled(false);
                locationEt.setEnabled(false);
                break;

            case 1:
                nameEt.setEnabled(true);
                locationEt.setEnabled(true);
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
            return resultList.size() -1;
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
                    }
                    else {
                        notifyDataSetInvalidated();
                    }
                }
            };

            return filter;
        }
    }

    private void ImageBrowse() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void captureImage() {
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("resultCode", String.valueOf(resultCode));
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                Uri selectedImageUri = data.getData();
                String finalFile = controller.getRealPathFromURI(context,selectedImageUri);

                RequestOptions optionsUserImage = new RequestOptions();
                optionsUserImage.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false).transform(new RoundedCorners(120));
                Glide.with(context).load(finalFile).apply(optionsUserImage).into(profileImage);

//                callImageUpload(finalFile);
            } else if (requestCode == CAMERA_REQUEST) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri tempUri = controller.getImageUri(getApplicationContext(), photo);
                File finalFile = new File(controller.getRealPathFromURI(context,tempUri));

                RequestOptions optionsUserImage = new RequestOptions();
                optionsUserImage.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false).transform(new RoundedCorners(120));
                Glide.with(context).load(finalFile).apply(optionsUserImage).into(profileImage);

//                callImageUpload(String.valueOf(finalFile));
            }
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
        centraliseToolBar.toolBarCustom(getString(R.string.edit_profile_screen), context, toolbarCallBack, getString(R.string.edit_profile));
    }


}

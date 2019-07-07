package restro.bts.com.restro;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import adapterAll.CentraliseRecyclerViewAdapter;
import callBacks.ParentApiCallback;
import callBacks.RecyclerViewCallBack;
import callBacks.ToolbarCallBack;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import functionalityAll.BaseActivity;
import functionalityAll.CatchList;
import functionalityAll.CentraliseToolBar;


/**
 * Created by Abhay dhiman
 */


@SuppressWarnings("unchecked")
public class CuisineScreen extends BaseActivity implements View.OnClickListener {

    private Controller controller;
    private Context context;
    private CentraliseToolBar centraliseToolBar;
    private RecyclerViewCallBack recyclerViewCallBack;
    private TextView headerTxt, saveCusine;
    private RecyclerView cusineList;
    private CardView saveCusinesParent;
    private ArrayList<HashMap<String, Object>> data, selectCuisine;
    private CentraliseRecyclerViewAdapter centraliseRecyclerViewAdapter;
    private HashMap<String, Object> dataGetter;
    private ToolbarCallBack toolbarCallBack;
    private ParentApiCallback parentApiCallback;
    private String responseGet;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cusine_layout);
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

        recyclerViewCallBack = new RecyclerViewCallBack() {
            @Override
            public void dataGet(Object... args) {
                selectCuisine = (ArrayList<HashMap<String, Object>>) args[0];
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

        if (callingUI.equals(context.getString(R.string.get_category_api))) {
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
            }
        }
    }

    private void initialise() {

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType();

        try {
            selectCuisine = new ArrayList<>();
            String getCuisineJson = controller.getStringSP(ConfigSharedPreferenceKey.CuisineList);
            selectCuisine = gson.fromJson(getCuisineJson, type);
        } catch (Exception e) {
            Log.e("Exception","Exception"+e);
        }

        headerTxt = findViewById(R.id.header_txt);
        cusineList = findViewById(R.id.cusine_list);
        saveCusinesParent = findViewById(R.id.save_cusine_parent);
        saveCusine = findViewById(R.id.save_cusine);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        controller.introInline(headerTxt, context);
        controller.josefinsansRegularTextView(saveCusine, context);
        saveCusinesParent.setOnClickListener(this);

        data = new ArrayList<>();
        centraliseRecyclerViewAdapter = new CentraliseRecyclerViewAdapter(context, data, R.layout.save_cusine_adapter, getString(R.string.save_cusine_adapter_view), recyclerViewCallBack, cusineList, selectCuisine);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(cusineList.getContext());
        cusineList.getItemAnimator().setChangeDuration(0);
        cusineList.setLayoutManager(linearLayoutManager);
        cusineList.setAdapter(centraliseRecyclerViewAdapter);


        controller.pdStart(context, "");
        dataGetter = new HashMap<>();
        dataGetter.put("userID", controller.getStringSP(ConfigSharedPreferenceKey.userID));
        controller.DataMaker(context, getString(R.string.get_category_api), parentApiCallback, dataGetter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_cusine_parent:
                Gson gson = new Gson();
                controller.setStringSP(ConfigSharedPreferenceKey.CuisineList,gson.toJson(selectCuisine));
                controller.setBooleanSP(ConfigSharedPreferenceKey.isRestroAPI,true);
                controller.snackBarShow(coordinatorLayout,getString(R.string.cuisine_save_successfully));
                onBackPressed();
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
        centraliseToolBar.toolBarCustom(getString(R.string.cuisine_screen_toolbar), context, toolbarCallBack);
    }

}

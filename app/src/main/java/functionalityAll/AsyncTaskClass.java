package functionalityAll;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import callBacks.ChildApiCallback;
import callBacks.DrawerApiCallback;
import callBacks.ParentApiCallback;
import controllerAll.Config;
import controllerAll.ConfigApiParseKey;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import restro.bts.com.restro.R;


/**
 * Created by Abhay dhiman
 */


@SuppressWarnings("unchecked")
public class AsyncTaskClass extends AsyncTask<String, String, String> {

    private String response, callingUI, type_callback, apiMessage, apiStatus, userID, isBuyer, unreadMessageCount, pollyLines, categoryCommaSepratedList,
            specialOffer;
    private Context context;
    private ApiResponse apiResponse = new ApiResponse();
    private ParentApiCallback parentApiCallback;
    private ChildApiCallback childApiCallback;
    private DrawerApiCallback drawerApiCallback;
    private HashMap<String, Object> dataGet, dataSendSub, dataTipsSub;
    private JSONObject parent, meta, dataObject, data;
    private ArrayList<HashMap<String, Object>> dataSend, dataTips;
    private JSONArray dataArray;
    private Controller controller;


    public AsyncTaskClass(Object... args) {
        try {
            this.context = (Context) args[0];
            this.callingUI = (String) args[1];
            type_callback = "parent";
            controller = (Controller) context.getApplicationContext();
            this.parentApiCallback = (ParentApiCallback) args[2];


        } catch (Exception e) {
            try {
                type_callback = "child";
                this.childApiCallback = (ChildApiCallback) args[2];
            } catch (Exception e_inner) {
                try {
                    type_callback = "drawer";
                    this.drawerApiCallback = (DrawerApiCallback) args[2];
                } catch (Exception e_in_inner) {
                    Log.e("EXCEPTIONOCCUR", "EXCEPTIONOCCUR" + e);
                    CatchList.Report(e);
                }
            }
        }

        if (callingUI.equals(context.getString(R.string.add_reviews_api))) {
            dataObject = (JSONObject) args[3];
        } else {
            dataGet = (HashMap<String, Object>) args[3];
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //Hit all api in Background Task
    @Override
    protected String doInBackground(String... args) {

        try {

            if (callingUI.equals(context.getString(R.string.get_resto_list_api))) {
                Log.e("dataObject", "dataObject" + dataGet.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType();
                try {
                    ArrayList<HashMap<String, Object>> selectCuisine = new ArrayList<>();
                    String getCuisineJson = controller.getStringSP(ConfigSharedPreferenceKey.CuisineList);
                    selectCuisine = gson.fromJson(getCuisineJson, type);
                    if (!getCuisineJson.equals(context.getString(R.string.no_value))) {
                        if (selectCuisine.size() > 0) {
                            for (int i = 0; i < selectCuisine.size(); i++) {
                                if (i == 0) {
                                    categoryCommaSepratedList = selectCuisine.get(i).get("cuisine_id").toString();
                                } else {
                                    categoryCommaSepratedList = categoryCommaSepratedList + "," + selectCuisine.get(i).get("cuisine_id").toString();
                                }
                            }
                        } else {
                            categoryCommaSepratedList = Config.DEFAULT_FOOD_CATEGORY_ID;
                        }
                    } else {
                        categoryCommaSepratedList = Config.DEFAULT_FOOD_CATEGORY_ID;
                    }
                } catch (Exception e) {
                    Log.e("Exception", "Exception" + e);
                    categoryCommaSepratedList = Config.DEFAULT_FOOD_CATEGORY_ID;
                }

                Log.e("categoryCommist", "categoryCommaSeprast" + categoryCommaSepratedList);
                response = apiResponse.ResponseGet(context, Config.Four_Square_Parent_URL + dataGet.get("latitude").toString() + "," + dataGet.get("longitude").toString()
                        + Config.Four_Square_GET_NEAREST_LOCATION + categoryCommaSepratedList + Config.Four_Square_Version + "&radius=" + dataGet.get("radius").toString()) + "&limit=" + dataGet.get("limit").toString();
                Log.e("response", "response" + response);
            } else if (callingUI.equals(context.getString(R.string.google_Path_API))) {
                Log.e("dataObject", "dataObject" + dataGet.toString());
                response = apiResponse.ResponseGet(context, Config.googleDirection1 + dataGet.get("latitudeOrigin").toString() + "," + dataGet.get("longitudeOrigin").toString()
                        + Config.getGoogleDirection2 + dataGet.get("latitudeDestination").toString() + "," + dataGet.get("longitudeDestination").toString() + Config.getGoogleDirection3);
                Log.e("response", "response" + response);
            } else if (callingUI.equals(context.getString(R.string.get_resto_detail_api))) {
                Log.e("dataObject", "dataObject" + dataGet.toString());
                response = apiResponse.ResponseGet(context, Config.FOUR_SQUARE_PARENT_UrL_DETAIL + dataGet.get("categoryID").toString()
                        + Config.FOUR_SQUARE_DETAIL_API + "&radius=" + dataGet.get("radius").toString() + "&limit=" + dataGet.get("limit").toString());
                Log.e("response", "response" + response);
            } else if (callingUI.equals(context.getString(R.string.check_email_availability_api))) {
                Log.e("dataObject", "dataObject" + dataGet.toString());
                response = apiResponse.ResponseGet(context, Config.BASE_URL + Config.User_Service + Config.Get_Email_Id_Availability + dataGet.get("email").toString());
                Log.e("response", "response" + response);
            } else if (callingUI.equals(context.getString(R.string.sign_up_api))) {
                Log.e("dataObject", "dataObject" + dataGet.toString());
                response = apiResponse.ResponseGet(context, Config.BASE_URL + Config.User_Service + Config.Register_User + dataGet.get("email").toString() +
                        "/" + dataGet.get("password").toString() + "/" + dataGet.get("name").toString() + "/" + dataGet.get("deviceID").toString() + "/"
                        + dataGet.get("latitude").toString() + "/" + dataGet.get("longitude").toString() + "/" + dataGet.get("city").toString());
                Log.e("response", "response" + response);
            } else if (callingUI.equals(context.getString(R.string.logout_api))) {
                Log.e("dataObject", "dataObject" + dataGet.toString());
                response = apiResponse.ResponseGet(context, Config.BASE_URL + Config.User_Service + Config.User_Logout + dataGet.get("deviceID").toString() +
                        "/" + dataGet.get("userID").toString());
                Log.e("response", "response" + response);
            } else if (callingUI.equals(context.getString(R.string.get_category_api))) {
                Log.e("dataObject", "dataObject" + dataGet.toString());
                response = apiResponse.ResponseGet(context, Config.BASE_URL + Config.User_Service + Config.Get_Category +
                        "/" + dataGet.get("userID").toString());
                Log.e("response", "response" + response);
            } else if (callingUI.equals(context.getString(R.string.add_reviews_api))) {
                Log.e("dataObject", "dataObject" + dataObject.toString());
                response = apiResponse.Response_Post(context, Config.BASE_URL + Config.Reviews + Config.Post_Review, dataObject, callingUI);
                Log.e("response", "response" + response);
            } else if (callingUI.equals(context.getString(R.string.sign_in_api))) {
                Log.e("dataObject", "dataObject" + dataGet.toString());
                response = apiResponse.ResponseGet(context, Config.BASE_URL + Config.User_Service + Config.User_Login +
                        dataGet.get("email").toString() + "/" + dataGet.get("password").toString() + "/" + dataGet.get("deviceID").toString()
                        + "/" + dataGet.get("latitude").toString());

//                + "/" + dataGet.get("longitude").toString()
                Log.e("response", "response" + response);
            } else if (callingUI.equals(context.getString(R.string.forgot_password_api))) {
                Log.e("dataObject", "dataObject" + dataGet.toString());
                response = apiResponse.ResponseGet(context, Config.BASE_URL + Config.User_Service + Config.Forgot_Password +
                        dataGet.get("email").toString());
                Log.e("response", "response" + response);
            } else if (callingUI.equals(context.getString(R.string.get_review_api))) {
                Log.e("dataObject", "dataObject" + dataGet.toString());
                response = apiResponse.ResponseGet(context, Config.BASE_URL + Config.Reviews + Config.Get_Review_List +
                        dataGet.get("userID").toString() + "/" + dataGet.get("venuID").toString() + "/" +
                        dataGet.get("skip").toString() + "/" + dataGet.get("limit").toString());
                Log.e("response", "response" + response);
            } else if (callingUI.equals(context.getString(R.string.social_login_api))) {
                Log.e("dataObject", "dataObject" + dataGet.toString());
                response = apiResponse.ResponseGet(context, Config.BASE_URL + Config.Reviews + Config.Get_Review_List +
                        dataGet.get("userID").toString() + "/" + dataGet.get("venuID").toString() + "/" +
                        dataGet.get("skip").toString() + "/" + dataGet.get("limit").toString());
                Log.e("response", "response" + response);
            }


            if (!response.equals(context.getString(R.string.error_Http_not_found)) && !response.equals(context.getString(R.string.error_Http_internal))
                    && !response.equals(context.getString(R.string.error_Http_other)) && !response.equals(context.getString(R.string.error))) {


                if (callingUI.equals(context.getString(R.string.get_resto_list_api))) {
                    parent = new JSONObject(response);
                    meta = parent.getJSONObject("meta");
                    apiStatus = meta.getString("code");
                    if (apiStatus.equals("200")) {
                        data = parent.getJSONObject("response");
                        JSONArray groups = data.getJSONArray("groups");
                        JSONObject groupsSub = groups.getJSONObject(0);
                        JSONArray items = groupsSub.getJSONArray("items");
                        dataSend = new ArrayList<>();

                        if (items.length() > 0) {
                            Log.e("data", "data" + items.length());
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject itemsSub = items.getJSONObject(i);
                                JSONObject venue = itemsSub.getJSONObject("venue");

                                dataSendSub = new HashMap<>();
                                dataSendSub.put(ConfigApiParseKey.RESTRO_ID, venue.getString("id"));
                                dataSendSub.put(ConfigApiParseKey.RESTRO_NAME, venue.getString("name"));

                                if (venue.has("contact")) {
                                    JSONObject contact = venue.getJSONObject("contact");
                                    if (contact.has(ConfigApiParseKey.RESTRO_NUMBER)) {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_NUMBER, contact.getString(ConfigApiParseKey.RESTRO_NUMBER));
                                    } else {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_NUMBER, context.getString(R.string.no_value));
                                    }
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_NUMBER, context.getString(R.string.no_value));
                                }

                                JSONObject location = venue.getJSONObject("location");
                                if (location.has(ConfigApiParseKey.RESTRO_ADDRESS)) {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_ADDRESS, location.getString(ConfigApiParseKey.RESTRO_ADDRESS));
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_ADDRESS, context.getString(R.string.no_value));
                                }

                                dataSendSub.put(ConfigApiParseKey.RESTRO_LATITUDE, location.getString(ConfigApiParseKey.RESTRO_LATITUDE));
                                dataSendSub.put(ConfigApiParseKey.RESTRO_LONGITUDE, location.getString(ConfigApiParseKey.RESTRO_LONGITUDE));
                                dataSendSub.put(ConfigApiParseKey.RESTRO_DISTANCE, location.getString(ConfigApiParseKey.RESTRO_DISTANCE));


                                if (location.has(ConfigApiParseKey.RESTRO_POSTAL_CODE)) {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_POSTAL_CODE, location.getString(ConfigApiParseKey.RESTRO_POSTAL_CODE));
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_POSTAL_CODE, context.getString(R.string.no_value));
                                }

                                if (location.has(ConfigApiParseKey.RESTRO_CC)) {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_CC, location.getString(ConfigApiParseKey.RESTRO_CC));
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_CC, context.getString(R.string.no_value));
                                }


                                if (location.has(ConfigApiParseKey.RESTRO_CITY)) {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_CITY, location.getString(ConfigApiParseKey.RESTRO_CITY));
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_CITY, context.getString(R.string.no_value));
                                }

                                if (location.has(ConfigApiParseKey.RESTRO_STATE)) {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_STATE, location.getString(ConfigApiParseKey.RESTRO_STATE));
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_STATE, context.getString(R.string.no_value));
                                }

                                if (location.has(ConfigApiParseKey.RESTRO_COUNTRY)) {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_COUNTRY, location.getString(ConfigApiParseKey.RESTRO_COUNTRY));
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_COUNTRY, context.getString(R.string.no_value));
                                }

                                if (venue.has("stats")) {
                                    JSONObject stats = venue.getJSONObject("stats");
                                    if (stats.has(ConfigApiParseKey.RESTRO_CHECKIN_COUNT)) {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_CHECKIN_COUNT, stats.getString(ConfigApiParseKey.RESTRO_CHECKIN_COUNT));
                                    } else {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_CHECKIN_COUNT, context.getString(R.string.no_value));
                                    }
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_CHECKIN_COUNT, context.getString(R.string.no_value));
                                }


                                if (venue.has("price")) {
                                    JSONObject price = venue.getJSONObject("price");
                                    if (price.has(ConfigApiParseKey.RESTRO_PRICE_MESSAGE)) {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_PRICE_MESSAGE, price.getString(ConfigApiParseKey.RESTRO_PRICE_MESSAGE));
                                    } else {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_PRICE_MESSAGE, context.getString(R.string.no_value));
                                    }
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_PRICE_MESSAGE, context.getString(R.string.no_value));
                                }


                                if (venue.has(ConfigApiParseKey.RESTRO_URL)) {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_URL, venue.getString(ConfigApiParseKey.RESTRO_URL));
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_URL, context.getString(R.string.no_value));
                                }

                                if (venue.has(ConfigApiParseKey.RESTRO_RATING)) {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_RATING, venue.getString(ConfigApiParseKey.RESTRO_RATING));
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_RATING, "0");
                                }

                                if (venue.has("hours")) {
                                    JSONObject hours = venue.getJSONObject("hours");
                                    if (hours.has(ConfigApiParseKey.RESTRO_OPEN_NOW)) {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_OPEN_NOW, hours.getString(ConfigApiParseKey.RESTRO_OPEN_NOW));
                                    } else {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_OPEN_NOW, context.getString(R.string.no_value));
                                    }

                                    if (hours.has(ConfigApiParseKey.RESTRO_OPEN_NOW_TIME)) {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_OPEN_NOW_TIME, hours.getString(ConfigApiParseKey.RESTRO_OPEN_NOW_TIME));
                                    } else {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_OPEN_NOW_TIME, context.getString(R.string.no_value));
                                    }
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_OPEN_NOW, context.getString(R.string.no_value));
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_OPEN_NOW_TIME, context.getString(R.string.no_value));
                                }

                                String finalImage;
                                if (venue.has("photos")) {
                                    JSONObject photos = venue.getJSONObject("photos");
                                    if (photos.has("groups")) {
                                        JSONArray groupsInner = photos.getJSONArray("groups");
                                        if (groupsInner.length() > 0) {
                                            JSONObject groupInnerSub = groupsInner.getJSONObject(0);
                                            if (groupInnerSub.has("items")) {
                                                JSONArray itemsInner = groupInnerSub.getJSONArray("items");
                                                JSONObject itemsInnerSub = itemsInner.getJSONObject(0);
                                                if (itemsInnerSub.length() > 0) {
                                                    String prefix = itemsInnerSub.getString("prefix");
                                                    String suffix = itemsInnerSub.getString("suffix");
                                                    String width = itemsInnerSub.getString("width");
                                                    String height = itemsInnerSub.getString("height");

                                                    finalImage = prefix + width + "x" + height + suffix;
                                                } else {
                                                    finalImage = context.getString(R.string.no_value);
                                                }
                                            } else {
                                                finalImage = context.getString(R.string.no_value);
                                            }
                                        } else {
                                            finalImage = context.getString(R.string.no_value);
                                        }
                                    } else {
                                        finalImage = context.getString(R.string.no_value);
                                    }
                                } else {
                                    finalImage = context.getString(R.string.no_value);
                                }

                                dataSendSub.put(ConfigApiParseKey.RESTRO_IMAGE, finalImage);


                                JSONArray tips = itemsSub.getJSONArray("tips");
                                if (tips.length() > 0) {
                                    JSONObject tipsSub = tips.getJSONObject(0);

                                    if (tipsSub.has(ConfigApiParseKey.RESTRO_TIPS_TEXT)) {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_TIPS_TEXT, tipsSub.getString(ConfigApiParseKey.RESTRO_TIPS_TEXT));
                                    } else {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_TIPS_TEXT, context.getString(R.string.no_value));
                                    }


                                    if (tipsSub.has("likes")) {
                                        JSONObject likes = tipsSub.getJSONObject("likes");
                                        if (likes.has(ConfigApiParseKey.RESTRO_LIKE_COUNT)) {
                                            dataSendSub.put(ConfigApiParseKey.RESTRO_LIKE_COUNT, likes.getString(ConfigApiParseKey.RESTRO_LIKE_COUNT));
                                        } else {
                                            dataSendSub.put(ConfigApiParseKey.RESTRO_LIKE_COUNT, context.getString(R.string.no_value));
                                        }
                                    } else {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_LIKE_COUNT, context.getString(R.string.no_value));
                                    }
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_TIPS_TEXT, context.getString(R.string.no_value));
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_LIKE_COUNT, context.getString(R.string.no_value));
                                }

                                dataSend.add(dataSendSub);
                            }
                        } else {
                            response = context.getString(R.string.message_error);
                        }
                    } else {
                        response = context.getString(R.string.status_error);
                    }
                } else if (callingUI.equals(context.getString(R.string.google_Path_API))) {
                    parent = new JSONObject(response);
                    String status = parent.getString("status");
                    if ("OK".equals(status)) {
                        JSONArray routesArray = parent.getJSONArray("routes");
                        JSONObject route = routesArray.getJSONObject(0);
                        JSONObject poly = route.getJSONObject("overview_polyline");
                        pollyLines = poly.getString("points");
                    } else {
                        response = context.getString(R.string.error);
                    }
                } else if (callingUI.equals(context.getString(R.string.get_resto_detail_api))) {
                    parent = new JSONObject(response);
                    meta = parent.getJSONObject("meta");
                    apiStatus = meta.getString("code");
                    if (apiStatus.equals("200")) {
                        data = parent.getJSONObject("response");
                        dataSend = new ArrayList<>();
                        JSONObject venue = data.getJSONObject("venue");
                        if (data.length() > 0) {

                            if (venue.has("specials")) {
                                JSONObject specials = venue.getJSONObject("specials");
                                if (specials.has("items")) {
                                    JSONArray specialsItems = specials.getJSONArray("items");
                                    specialOffer = String.valueOf(specialsItems);
                                } else {
                                    specialOffer = context.getString(R.string.no_value);
                                }
                            } else {
                                specialOffer = context.getString(R.string.no_value);
                            }

                            String finalImage;
                            dataSendSub = new HashMap<>();
                            if (venue.has("photos")) {
                                JSONObject photos = venue.getJSONObject("photos");
                                if (photos.has("groups")) {
                                    JSONArray groupsInner = photos.getJSONArray("groups");
                                    if (groupsInner.length() > 0) {
                                        JSONObject groupInnerSub = groupsInner.getJSONObject(0);
                                        if (groupInnerSub.has("items")) {
                                            JSONArray itemsInner = groupInnerSub.getJSONArray("items");
                                            if (itemsInner.length() > 0) {
                                                for (int i = 0; i < itemsInner.length(); i++) {
                                                    JSONObject itemsInnerSub = itemsInner.getJSONObject(i);
                                                    dataSendSub = new HashMap<>();
                                                    String prefix = itemsInnerSub.getString("prefix");
                                                    String suffix = itemsInnerSub.getString("suffix");
                                                    String width = itemsInnerSub.getString("width");
                                                    String height = itemsInnerSub.getString("height");

                                                    finalImage = prefix + width + "x" + height + suffix;
                                                    dataSendSub.put(ConfigApiParseKey.RESTRO_IMAGE, finalImage);
                                                    dataSend.add(dataSendSub);
                                                }
                                            } else {
                                                finalImage = context.getString(R.string.no_value);
                                                dataSendSub.put(ConfigApiParseKey.RESTRO_IMAGE, finalImage);
                                                dataSend.add(dataSendSub);
                                            }
                                        } else {
                                            finalImage = context.getString(R.string.no_value);
                                            dataSendSub.put(ConfigApiParseKey.RESTRO_IMAGE, finalImage);
                                            dataSend.add(dataSendSub);
                                        }
                                    } else {
                                        finalImage = context.getString(R.string.no_value);
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_IMAGE, finalImage);
                                        dataSend.add(dataSendSub);
                                    }
                                } else {
                                    finalImage = context.getString(R.string.no_value);
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_IMAGE, finalImage);
                                    dataSend.add(dataSendSub);
                                }
                            } else {
                                finalImage = context.getString(R.string.no_value);
                                dataSendSub.put(ConfigApiParseKey.RESTRO_IMAGE, finalImage);
                                dataSend.add(dataSendSub);
                            }


                            JSONObject tips = venue.getJSONObject("tips");
                            JSONArray groupsTips = tips.getJSONArray("groups");
                            JSONObject groupTipsSub = groupsTips.getJSONObject(0);
                            JSONArray itemsTips = groupTipsSub.getJSONArray("items");

                            Log.e("itemsTips", "itemsTips" + itemsTips.toString());

                            dataTips = new ArrayList<>();
                            for (int i = 0; i < itemsTips.length(); i++) {
                                JSONObject itemsTipsSub = itemsTips.getJSONObject(i);
                                dataTipsSub = new HashMap<>();

                                dataTipsSub.put(ConfigApiParseKey.RESTRO_REVIEW_CREATED_AT, itemsTipsSub.getString(ConfigApiParseKey.RESTRO_REVIEW_CREATED_AT));
                                dataTipsSub.put(ConfigApiParseKey.RESTRO_REVIEW_TEXT, itemsTipsSub.getString(ConfigApiParseKey.RESTRO_REVIEW_TEXT));

                                JSONObject user = itemsTipsSub.getJSONObject("user");

                                String name = user.getString(ConfigApiParseKey.RESTRO_REVIEW_FIRST_NAME) + " " + user.getString(ConfigApiParseKey.RESTRO_REVIEW_LAST_NAME);
                                dataTipsSub.put(ConfigApiParseKey.RESTRO_REVIEW_NAME, name);

                                JSONObject photo = user.getJSONObject("photo");
                                String prefix = photo.getString("prefix");
                                String suffix = photo.getString("suffix");
                                String width = "320";
                                String height = "320";

                                finalImage = prefix + width + "x" + height + suffix;
                                dataTipsSub.put(ConfigApiParseKey.RESTRO_REVIEW_USER_IMAGE, finalImage);
                                dataTips.add(dataTipsSub);
                            }


                        } else {
                            response = context.getString(R.string.message_error);
                        }
                    } else {
                        response = context.getString(R.string.status_error);
                    }
                } else if (callingUI.equals(context.getString(R.string.check_email_availability_api))) {
                    parent = new JSONObject(response);
                    apiMessage = parent.getString("message");
                    apiStatus = parent.getString("status");

                    if (apiStatus.equals("Success")) {
                        if (!apiMessage.equals(context.getString(R.string.email_id_not_found))) {
                            response = context.getString(R.string.message_error);
                        }
                    } else {
                        response = context.getString(R.string.status_error);
                    }
                } else if (callingUI.equals(context.getString(R.string.sign_up_api))) {
                    parent = new JSONObject(response);
                    apiMessage = parent.getString("message");
                    apiStatus = parent.getString("status");

                    if (apiStatus.equals("Success")) {
                        if (!apiMessage.equals(context.getString(R.string.registered_successfully))) {
                            response = context.getString(R.string.message_error);
                        } else {
                            response = parent.getString("userid");
                        }
                    } else {
                        response = context.getString(R.string.status_error);
                    }
                } else if (callingUI.equals(context.getString(R.string.logout_api))) {
                    parent = new JSONObject(response);
                    apiMessage = parent.getString("message");
                    apiStatus = parent.getString("status");

                    if (apiStatus.equals("Success")) {
                        if (!apiMessage.equals(context.getString(R.string.logged_out_successfully))) {
                            response = context.getString(R.string.message_error);
                        } else {
                            response = apiMessage;
                        }
                    } else {
                        response = context.getString(R.string.status_error);
                    }
                } else if (callingUI.equals(context.getString(R.string.get_category_api))) {
                    dataArray = new JSONArray(response);
                    if (dataArray.length() > 0) {
                        dataSend = new ArrayList<>();
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject parentSub = dataArray.getJSONObject(i);
                            dataSendSub = new HashMap<>();
                            dataSendSub.put(ConfigApiParseKey.RESTRO_CUISINE_ID, parentSub.getString(ConfigApiParseKey.RESTRO_CUISINE_ID));
                            dataSendSub.put(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY, parentSub.getString(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY));
                            dataSendSub.put(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY_ID, parentSub.getString(ConfigApiParseKey.RESTRO_CUISINE_CATEGORY_ID));
                            dataSend.add(dataSendSub);
                        }
                    } else {
                        response = context.getString(R.string.status_error);
                    }
                } else if (callingUI.equals(context.getString(R.string.add_reviews_api))) {
                    parent = new JSONObject(response);
                    apiMessage = parent.getString("message");
                    apiStatus = parent.getString("status");

                    if (apiStatus.equals("Success")) {
                        if (!apiMessage.equals(context.getString(R.string.added_successfully))) {
                            response = context.getString(R.string.message_error);
                        } else {
                            response = apiMessage;
                        }
                    } else {
                        response = context.getString(R.string.status_error);
                    }
                } else if (callingUI.equals(context.getString(R.string.get_review_api))) {
                    dataArray = new JSONArray(response);

                    if (dataArray.length() > 0) {
                        dataSend = new ArrayList<>();
                        for (int i = 0; i < dataArray.length(); i++) {
                            parent = dataArray.getJSONObject(i);
                            dataSendSub = new HashMap<>();
                            dataSendSub.put(ConfigApiParseKey.RESTRO_REVIEW_CREATED_AT, parent.getString("date"));
                            dataSendSub.put(ConfigApiParseKey.RESTRO_REVIEW_TEXT, parent.getString("review"));
                            dataSendSub.put(ConfigApiParseKey.RESTRO_REVIEW_NAME, parent.getString(ConfigApiParseKey.RESTRO_REVIEW_NAME));

                            if (parent.has("user_img")) {
                                if (parent.getString("user_img") != null) {
                                    if (!parent.getString("user_img").equals("") && !parent.getString("user_img").equals("null")) {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_REVIEW_USER_IMAGE, parent.getString("user_img"));
                                    } else {
                                        dataSendSub.put(ConfigApiParseKey.RESTRO_REVIEW_USER_IMAGE, "https://igx.4sqi.net/img/general/481x641/38512691_zIZYTPqFIZXKgN5Y4hA6tIDyrcneeL6JhIYg731d6H8.jpg");
                                    }
                                } else {
                                    dataSendSub.put(ConfigApiParseKey.RESTRO_REVIEW_USER_IMAGE, "https://igx.4sqi.net/img/general/481x641/38512691_zIZYTPqFIZXKgN5Y4hA6tIDyrcneeL6JhIYg731d6H8.jpg");
                                }
                            } else {
                                dataSendSub.put(ConfigApiParseKey.RESTRO_REVIEW_USER_IMAGE, "https://igx.4sqi.net/img/general/481x641/38512691_zIZYTPqFIZXKgN5Y4hA6tIDyrcneeL6JhIYg731d6H8.jpg");
                            }
                            dataSend.add(dataSendSub);
                        }
                    } else {
                        response = context.getString(R.string.message_error);
                    }
                } else if (callingUI.equals(context.getString(R.string.sign_in_api))) {
                    parent = new JSONObject(response);
                    apiMessage = parent.getString("message");
                    apiStatus = parent.getString("status");

                    if (apiStatus.equals("Success")) {
                        if (!apiMessage.equals(context.getString(R.string.user_login_successful))) {
                            response = context.getString(R.string.message_error);
                        } else {
                            response = parent.getString("userid");
                        }
                    } else {
                        response = context.getString(R.string.status_error);
                    }
                }
            }

        } catch (Exception e) {
            CatchList.Report(e);
            Log.e("Errorrrrrrrrrr", String.valueOf(e));
        }
        return null;
    }


    @Override
    protected void onPostExecute(String file_url) {

        try {
            //Check is Response is empty or not
            if (response == null) {
                response = context.getString(R.string.error);
            }

            if (callingUI.equals(context.getString(R.string.get_resto_list_api))) {
                if (response.equals(context.getString(R.string.error_Http_not_found))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_internal))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_other))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.status_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.message_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else {
                    parentApiCallback.Data_call_back(callingUI, response, dataSend);
                }
            } else if (callingUI.equals(context.getString(R.string.google_Path_API))) {
                if (response.equals(context.getString(R.string.error_Http_not_found))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_internal))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_other))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else {
                    parentApiCallback.Data_call_back(callingUI, response, pollyLines);
                }
            } else if (callingUI.equals(context.getString(R.string.get_resto_detail_api))) {
                if (response.equals(context.getString(R.string.error_Http_not_found))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_internal))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_other))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.status_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.message_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else {
                    parentApiCallback.Data_call_back(callingUI, response, dataSend, dataTips,specialOffer);
                }
            } else if (callingUI.equals(context.getString(R.string.check_email_availability_api))) {
                if (response.equals(context.getString(R.string.error_Http_not_found))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_internal))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_other))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.status_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.message_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else {
                    parentApiCallback.Data_call_back(callingUI, response);
                }
            } else if (callingUI.equals(context.getString(R.string.sign_up_api))) {
                if (response.equals(context.getString(R.string.error_Http_not_found))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_internal))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_other))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.status_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.message_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else {
                    parentApiCallback.Data_call_back(callingUI, response);
                }
            } else if (callingUI.equals(context.getString(R.string.get_category_api))) {
                if (response.equals(context.getString(R.string.error_Http_not_found))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_internal))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_other))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.status_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.message_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else {
                    parentApiCallback.Data_call_back(callingUI, response, dataSend);
                }
            } else if (callingUI.equals(context.getString(R.string.add_reviews_api))) {
                if (response.equals(context.getString(R.string.error_Http_not_found))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_internal))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_other))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.status_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.message_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else {
                    parentApiCallback.Data_call_back(callingUI, response);
                }
            } else if (callingUI.equals(context.getString(R.string.logout_api))) {
                if (response.equals(context.getString(R.string.error_Http_not_found))) {
                    childApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_internal))) {
                    childApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_other))) {
                    childApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error))) {
                    childApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.status_error))) {
                    childApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.message_error))) {
                    childApiCallback.Data_call_back(callingUI, response);
                } else {
                    childApiCallback.Data_call_back(callingUI, response);
                }
            } else if (callingUI.equals(context.getString(R.string.get_review_api))) {
                if (response.equals(context.getString(R.string.error_Http_not_found))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_internal))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_other))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.status_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.message_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else {
                    parentApiCallback.Data_call_back(callingUI, response, dataSend);
                }
            } else if (callingUI.equals(context.getString(R.string.sign_in_api))) {
                if (response.equals(context.getString(R.string.error_Http_not_found))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_internal))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error_Http_other))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.status_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else if (response.equals(context.getString(R.string.message_error))) {
                    parentApiCallback.Data_call_back(callingUI, response);
                } else {
                    parentApiCallback.Data_call_back(callingUI, response);
                }
            }
        } catch (Exception e) {
            CatchList.Report(e);
        }
    }
}
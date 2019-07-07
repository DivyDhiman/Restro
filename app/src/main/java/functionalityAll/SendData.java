package functionalityAll;

import android.util.Log;

import org.json.JSONArray;

import controllerAll.Config;


public class SendData {

    private static Object dataObject;
    private static int type;
    private static String ID,telCode;
    private static double latitudeGet = 0.0, longitudeGet = 0.0;
    public static String path;
    public static String videoFilterType;
    public static JSONArray getObject;
    public static String amazonURL = "",amazonURLType = "";


    public static void setLatitude(Double latitude){
        latitudeGet = latitude;
    }

    public static void setLongitude(Double longitude){
        longitudeGet = longitude;
    }

    public static void setData(Object...args){
        type = (int) args[0];
        dataObject = args[2];
        ID = (String) args[3];

        if (type == 0){
            telCode = (String) args[4];
        }
    }

    public static Object getData(){
        if(dataObject != null){
            return dataObject;
        }else {
            return Config.NO_DATA;
        }
    }

    public static int getType(){
            return type;
    }

    public static String getID(){
        return ID;
    }

    public static String getTelCode(){
        return telCode;
    }

    public static double getLatitude(){
        return latitudeGet;
    }

    public static double getLongitude(){
        return longitudeGet;
    }


    public static void setAmazonDetails(String amURL, String amURLType){
        amazonURL = amURL;
        amazonURLType = amURLType;
        Log.e("amazonURL","amazonURL"+amazonURL);
        Log.e("amazonURLType","amazonURLType"+amazonURLType);
    }

    public static String getAmazonUrl(){
        return amazonURL;
    }

    public static String getAmazonURLType(){
        return amazonURLType;
    }

    public static void resetAmazonDetails(){
        amazonURL = "";
        amazonURLType = "";
    }

}

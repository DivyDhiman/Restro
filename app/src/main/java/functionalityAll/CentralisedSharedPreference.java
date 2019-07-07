package functionalityAll;

import android.content.Context;
import android.content.SharedPreferences;

import controllerAll.Config;

/**
 * Created by Abhay dhiman
 */

//Shared preference saved class for saving data in shared preferences
public class CentralisedSharedPreference {

    private SharedPreferences Restro;
    private static String file = "Restro";

    //CentralisedSharedPreference constructor
    public CentralisedSharedPreference(Context context) {
        Restro = context.getApplicationContext().getSharedPreferences(file, 0);
    }

    //Shared Preferences saved boolean value
    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editors = Restro.edit();
        editors.putBoolean(key, value);
        editors.commit();
    }

    //Shared Preferences saved string value
    public void setString(String key, String value) {
        SharedPreferences.Editor editors = Restro.edit();
        editors.putString(key, value);
        editors.commit();
    }

    //Shared Preferences saved int value
    public void setInt(String key, int value) {
        SharedPreferences.Editor editors = Restro.edit();
        editors.putInt(key, value);
        editors.commit();
    }

    //Shared Preferences saved long value
    public void setLong(String key, long value) {
        SharedPreferences.Editor editors = Restro.edit();
        editors.putLong(key, value);
        editors.commit();
    }

    //Shared Preferences get long value
    public long getLong(String key) {
        long res = Restro.getLong(key, 0);
        return res;
    }

    //Shared Preferences get boolean value
    public boolean getBoolean(String key) {
        boolean result = Restro.getBoolean(key, false);
        return result;
    }

    //Shared Preferences get string value
    public String getString(String key) {
        String res = Restro.getString(key, Config.NO_DATA);
        return res;
    }

    //Shared Preferences get int value
    public int getInt(String key) {
        int res = Restro.getInt(key, Config.DEFAULT_RADIUS);
        return res;
    }

    public void clearSharedPreference() {
        SharedPreferences.Editor editors = Restro.edit();
        editors.clear();
        editors.commit();
    }

    //Remove values from shared preferences
    public void removeSharedPreferencesKey(String key) {
        Restro.edit().remove(key).commit();
    }
}
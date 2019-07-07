package functionalityAll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import callBacks.DatabaseCallback;
import controllerAll.ConfigApiParseKey;
import controllerAll.ConfigDB;


@SuppressWarnings({"unchecked", "ConstantConditions"})
public class CentralizedDB extends SQLiteOpenHelper {
    private ArrayList<HashMap<String, Object>> data;
    private HashMap<String, Object> dataSub;
    String tableName;
    private SQLiteDatabase sqLiteDatabase;
    private CentralizedDB centralizedDB;
    private DatabaseCallback databaseCallback;
    private String type, messageCount;
    private JSONArray dataObject;

    public CentralizedDB(Context context, int version) {
        super(context, ConfigDB.DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(ConfigDB.CREATING_DB_PUBLIC_PROPOSAL);
//        db.execSQL(ConfigDB.CREATING_DB_PUBLIC_POST);
//        db.execSQL(ConfigDB.CREATING_DB_PRIVATE_POST);
//        db.execSQL(ConfigDB.CREATING_DB_GET_USER_DETAILS);
//        db.execSQL(ConfigDB.CREATING_DB_GET_CHAT_DETAILS);
//        db.execSQL(ConfigDB.CREATING_DB_SELECTED_CATEGORIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void dataInsertionFunction(Object... args) {

//        tableName = (String) args[0];
//        Log.e("Data insert in", "----" + tableName);
//        centralizedDB = (CentralizedDB) args[1];
//        databaseCallback = (DatabaseCallback) args[2];
//        sqLiteDatabase = centralizedDB.getWritableDatabase();
//
//        Log.e("tableName", "tableName" + tableName);
//
//        if (tableName.equals(ConfigDB.PUBLIC_PROPOSAL)) {
//            data = (ArrayList<HashMap<String, Object>>) args[3];
//            Log.e("DataDatabase", "DataDatabase" + data);
//            for (int i = data.size() - 1; i >= 0; i--) {
//                dataSub = new HashMap<>();
//                dataSub = data.get(i);
//                ContentValues cv = new ContentValues();
//
//                cv.put(ConfigApiParseKey.PROPOSAL_DESCRIPTION, dataSub.get(ConfigApiParseKey.PROPOSAL_DESCRIPTION).toString());
//                cv.put(ConfigApiParseKey.PROPOSAL_FIRST_NAME, dataSub.get(ConfigApiParseKey.PROPOSAL_FIRST_NAME).toString());
//                cv.put(ConfigApiParseKey.PROPOSAL_IS_READ, dataSub.get(ConfigApiParseKey.PROPOSAL_IS_READ).toString());
//                cv.put(ConfigApiParseKey.PROPOSAL_LAST_NAME, dataSub.get(ConfigApiParseKey.PROPOSAL_LAST_NAME).toString());
//                cv.put(ConfigApiParseKey.PROPOSAL_MESSAGE, dataSub.get(ConfigApiParseKey.PROPOSAL_MESSAGE).toString());
//
//                cv.put(ConfigApiParseKey.PROPOSAL_PROPOSAL_ID, dataSub.get(ConfigApiParseKey.PROPOSAL_PROPOSAL_ID).toString());
//                cv.put(ConfigApiParseKey.PROPOSAL_TYPE, dataSub.get(ConfigApiParseKey.PROPOSAL_TYPE).toString());
//                cv.put(ConfigApiParseKey.PROPOSAL_USER_ID, dataSub.get(ConfigApiParseKey.PROPOSAL_USER_ID).toString());
//                cv.put(ConfigApiParseKey.PROPOSAL_USER_NAME, dataSub.get(ConfigApiParseKey.PROPOSAL_USER_NAME).toString());
//                cv.put(ConfigApiParseKey.PROPOSAL_USER_IMAGE, dataSub.get(ConfigApiParseKey.PROPOSAL_USER_IMAGE).toString());
//
//                sqLiteDatabase.insert(tableName, "", cv);
//            }
//            databaseCallback.getDatabaseCallback(ConfigDB.TYPE_SET_DATA);
//
//        } else if (tableName.equals(ConfigDB.PUBLIC_POST)) {
//            data = (ArrayList<HashMap<String, Object>>) args[3];
//            Log.e("DataDatabase", "DataDatabase" + data);
//            for (int i = data.size() - 1; i >= 0; i--) {
//                dataSub = new HashMap<>();
//                dataSub = data.get(i);
//                ContentValues cv = new ContentValues();
//
//                cv.put(ConfigApiParseKey.PUBLIC_DESCRIPTION, dataSub.get(ConfigApiParseKey.PUBLIC_DESCRIPTION).toString());
//                cv.put(ConfigApiParseKey.PUBLIC_DISTANCE, dataSub.get(ConfigApiParseKey.PUBLIC_DISTANCE).toString());
//                cv.put(ConfigApiParseKey.PUBLIC_FIRST_NAME, dataSub.get(ConfigApiParseKey.PUBLIC_FIRST_NAME).toString());
//                cv.put(ConfigApiParseKey.PUBLIC_LAST_NAME, dataSub.get(ConfigApiParseKey.PUBLIC_LAST_NAME).toString());
//                cv.put(ConfigApiParseKey.PUBLIC_IS_READ, dataSub.get(ConfigApiParseKey.PUBLIC_IS_READ).toString());
//
//                cv.put(ConfigApiParseKey.PUBLIC_OFFER_TIME, dataSub.get(ConfigApiParseKey.PUBLIC_OFFER_TIME).toString());
//                cv.put(ConfigApiParseKey.PUBLIC_USER_ID, dataSub.get(ConfigApiParseKey.PUBLIC_USER_ID).toString());
//                cv.put(ConfigApiParseKey.PUBLIC_USER_NAME, dataSub.get(ConfigApiParseKey.PUBLIC_USER_NAME).toString());
//                cv.put(ConfigApiParseKey.PUBLIC_OFFER_ID, dataSub.get(ConfigApiParseKey.PUBLIC_OFFER_ID).toString());
//                cv.put(ConfigApiParseKey.PUBLIC_SUB_CATEGORY_ID, dataSub.get(ConfigApiParseKey.PUBLIC_SUB_CATEGORY_ID).toString());
//                cv.put(ConfigApiParseKey.PUBLIC_SUPER_SUB_CATEGORY_ID, dataSub.get(ConfigApiParseKey.PUBLIC_SUPER_SUB_CATEGORY_ID).toString());
//                cv.put(ConfigApiParseKey.PUBLIC_CATEGORY_ID, dataSub.get(ConfigApiParseKey.PUBLIC_CATEGORY_ID).toString());
//                cv.put(ConfigApiParseKey.PUBLIC_USER_IMAGE, dataSub.get(ConfigApiParseKey.PUBLIC_USER_IMAGE).toString());
//
//                sqLiteDatabase.insert(tableName, "", cv);
//            }
//            databaseCallback.getDatabaseCallback(ConfigDB.TYPE_SET_DATA);
//        } else if (tableName.equals(ConfigDB.PRIVATE_POST)) {
//
//            data = (ArrayList<HashMap<String, Object>>) args[3];
//            Log.e("DataDatabase", "DataDatabase" + data);
//            for (int i = data.size() - 1; i >= 0; i--) {
//                dataSub = new HashMap<>();
//                dataSub = data.get(i);
//                ContentValues cv = new ContentValues();
//
//                cv.put(ConfigApiParseKey.PRIVATE_GROUP_ID, dataSub.get(ConfigApiParseKey.PRIVATE_GROUP_ID).toString());
//                cv.put(ConfigApiParseKey.PRIVATE_LAST_MESSAGE, dataSub.get(ConfigApiParseKey.PRIVATE_LAST_MESSAGE).toString());
//                cv.put(ConfigApiParseKey.PRIVATE_LAST_MESSAGE_IS_READ, dataSub.get(ConfigApiParseKey.PRIVATE_LAST_MESSAGE_IS_READ).toString());
//                cv.put(ConfigApiParseKey.PRIVATE_LAST_MESSAGE_TIME, dataSub.get(ConfigApiParseKey.PRIVATE_LAST_MESSAGE_TIME).toString());
//                cv.put(ConfigApiParseKey.PRIVATE_UNREAD_MESSAGE_COUNT, dataSub.get(ConfigApiParseKey.PRIVATE_UNREAD_MESSAGE_COUNT).toString());
//                cv.put(ConfigApiParseKey.PRIVATE_USER_ID, dataSub.get(ConfigApiParseKey.PRIVATE_USER_ID).toString());
//                cv.put(ConfigApiParseKey.PRIVATE_USER_IMAGE, dataSub.get(ConfigApiParseKey.PRIVATE_USER_IMAGE).toString());
//                cv.put(ConfigApiParseKey.PRIVATE_USER_NAME, dataSub.get(ConfigApiParseKey.PRIVATE_USER_NAME).toString());
//
//                sqLiteDatabase.insert(tableName, "", cv);
//            }
//            databaseCallback.getDatabaseCallback(ConfigDB.TYPE_SET_DATA);
//        } else if (tableName.equals(ConfigDB.GET_USER_DETAILSS)) {
//
//            data = (ArrayList<HashMap<String, Object>>) args[3];
//            Log.e("DataDatabase", "DataDatabase" + data);
//            try {
//                for (int i = data.size() - 1; i >= 0; i--) {
//                    Log.e("Inserted", "In Table");
//                    dataSub = new HashMap<>();
//                    Log.e("Inserted", "In Table");
//                    dataSub = data.get(i);
//                    Log.e("Inserted", "In Table");
//                    ContentValues cv = new ContentValues();
//                    Log.e("Inserted", "In Table");
//                    cv.put(ConfigApiParseKey.USER_BUSINESS_NAME, dataSub.get(ConfigApiParseKey.USER_BUSINESS_NAME).toString());
//                    cv.put(ConfigApiParseKey.USER_COUNTRY, dataSub.get(ConfigApiParseKey.USER_COUNTRY).toString());
//                    cv.put(ConfigApiParseKey.USER_FIRST_NAME, dataSub.get(ConfigApiParseKey.USER_FIRST_NAME).toString());
//                    cv.put(ConfigApiParseKey.USER_LAST_NAME, dataSub.get(ConfigApiParseKey.USER_LAST_NAME).toString());
//                    cv.put(ConfigApiParseKey.USER_COUNTRY_ID, dataSub.get(ConfigApiParseKey.USER_COUNTRY_ID).toString());
//                    cv.put(ConfigApiParseKey.USER_STATE_ID, dataSub.get(ConfigApiParseKey.USER_STATE_ID).toString());
//                    cv.put(ConfigApiParseKey.USER_IMAGE, dataSub.get(ConfigApiParseKey.USER_IMAGE).toString());
//                    cv.put(ConfigApiParseKey.USER_TEL_CODE, dataSub.get(ConfigApiParseKey.USER_TEL_CODE).toString());
//                    cv.put(ConfigApiParseKey.USER_PHONE_NUMBER, dataSub.get(ConfigApiParseKey.USER_PHONE_NUMBER).toString());
//                    cv.put(ConfigApiParseKey.USER_STATE, dataSub.get(ConfigApiParseKey.USER_STATE).toString());
//                    cv.put(ConfigApiParseKey.USER_ID, dataSub.get(ConfigApiParseKey.USER_ID).toString());
//                    cv.put(ConfigApiParseKey.USER_NAME, dataSub.get(ConfigApiParseKey.USER_NAME).toString());
//                    Log.e("Inserted", "In Table");
//                    sqLiteDatabase.insert(tableName, "", cv);
//                }
//            } catch (Exception e) {
//                Log.e("Exception=", "Exception" + e);
//            }
//            Log.e("Setting", "In Table");
//            databaseCallback.getDatabaseCallback(ConfigDB.TYPE_SET_DATA);
//
//        } else if (tableName.equals(ConfigDB.CHAT_POSTS)) {
//
//            data = (ArrayList<HashMap<String, Object>>) args[3];
//            String groupID = (String) args[4];
//            Log.e("DataDatabase", "DataDatabase" + groupID);
//            Log.e("DataDatabase", "DataDatabase" + data.size());
////            for (int i = data.size() - 1; i >= 0; i--) {
//            for (int i = 0; i <= data.size() - 1; i++) {
//                dataSub = new HashMap<>();
//                dataSub = data.get(i);
//                Log.e("datasubbb", String.valueOf(data.get(i)));
//                ContentValues cv = new ContentValues();
//
//                cv.put(ConfigDB.CHAT_ID, groupID);
//                cv.put(ConfigApiParseKey.CHAT_BATCH_COUNT, dataSub.get(ConfigApiParseKey.CHAT_BATCH_COUNT).toString());
//                cv.put(ConfigApiParseKey.CHAT_BEHAVIOUR, dataSub.get(ConfigApiParseKey.CHAT_BEHAVIOUR).toString());
//                cv.put(ConfigApiParseKey.CHAT_DELETED_ON, dataSub.get(ConfigApiParseKey.CHAT_DELETED_ON).toString());
//                cv.put(ConfigApiParseKey.CHAT_DELEVERED_ON, dataSub.get(ConfigApiParseKey.CHAT_DELEVERED_ON).toString());
//                cv.put(ConfigApiParseKey.CHAT_FILE_NAME, dataSub.get(ConfigApiParseKey.CHAT_FILE_NAME).toString());
//                cv.put(ConfigApiParseKey.CHAT_GROUP_ID, dataSub.get(ConfigApiParseKey.CHAT_GROUP_ID).toString());
//                cv.put(ConfigApiParseKey.CHAT_HASHTAGS, dataSub.get(ConfigApiParseKey.CHAT_HASHTAGS).toString());
//                cv.put(ConfigApiParseKey.CHAT_IS_DELETED, dataSub.get(ConfigApiParseKey.CHAT_IS_DELETED).toString());
//                cv.put(ConfigApiParseKey.CHAT_IS_DELEVERED, dataSub.get(ConfigApiParseKey.CHAT_IS_DELEVERED).toString());
//                cv.put(ConfigApiParseKey.CHAT_IS_NOTIFICATION_SENT, dataSub.get(ConfigApiParseKey.CHAT_IS_NOTIFICATION_SENT).toString());
//                cv.put(ConfigApiParseKey.CHAT_IS_READ, dataSub.get(ConfigApiParseKey.CHAT_IS_READ).toString());
//                cv.put(ConfigApiParseKey.CHAT_OTHER_USER_ID, dataSub.get(ConfigApiParseKey.CHAT_OTHER_USER_ID).toString());
//                cv.put(ConfigApiParseKey.CHAT_READ_ON, dataSub.get(ConfigApiParseKey.CHAT_READ_ON).toString());
//                cv.put(ConfigApiParseKey.CHAT_MESSAGE, dataSub.get(ConfigApiParseKey.CHAT_MESSAGE).toString());
//                cv.put(ConfigApiParseKey.CHAT_URL, dataSub.get(ConfigApiParseKey.CHAT_URL).toString());
//                cv.put(ConfigApiParseKey.CHAT_THUMBNAIL, dataSub.get(ConfigApiParseKey.CHAT_THUMBNAIL).toString());
//                cv.put(ConfigApiParseKey.CHAT_TYPE_OBJECTS, dataSub.get(ConfigApiParseKey.CHAT_TYPE_OBJECTS).toString());
//                cv.put(ConfigApiParseKey.CHAT_TIME_STAMP, dataSub.get(ConfigApiParseKey.CHAT_TIME_STAMP).toString());
//                cv.put(ConfigApiParseKey.CHAT_MESSAGE_ID, dataSub.get(ConfigApiParseKey.CHAT_MESSAGE_ID).toString());
//                cv.put(ConfigApiParseKey.CHAT_USER_NAME, dataSub.get(ConfigApiParseKey.CHAT_USER_NAME).toString());
//                cv.put(ConfigApiParseKey.CHAT_USER_IMAGE, dataSub.get(ConfigApiParseKey.CHAT_USER_IMAGE).toString());
//                cv.put(ConfigApiParseKey.CHAT_TYPE, dataSub.get(ConfigApiParseKey.CHAT_TYPE).toString());
//
//                sqLiteDatabase.insert(tableName, "", cv);
//            }
//
//            databaseCallback.getDatabaseCallback(ConfigDB.TYPE_SET_DATA);
//
//        } else if (tableName.equals(ConfigDB.SELECTED_CATEGORIES)) {
//
//            data = (ArrayList<HashMap<String, Object>>) args[3];
//            Log.e("DataDatabase", "DataDatabase" + data);
//            Log.e("DataBaseDatabase", "DataBaseDataSize" + data.size());
//            for (int i = data.size() - 1; i >= 0; i--) {
//                dataSub = new HashMap<>();
//                dataSub = data.get(i);
//                ContentValues cv = new ContentValues();
//                Log.e("SelectedCategory", dataSub.get(ConfigApiParseKey.SELECTED_CATEGORY).toString());
//
//                cv.put(ConfigApiParseKey.SELECTED_CATEGORY, dataSub.get(ConfigApiParseKey.SELECTED_CATEGORY).toString());
//                Log.e("SelectedCategory", dataSub.get(ConfigApiParseKey.SELECTED_CATEGORY).toString());
//                sqLiteDatabase.insert(tableName, "", cv);
//                Log.e("SelectedCategory", dataSub.get(ConfigApiParseKey.SELECTED_CATEGORY).toString());
//            }
//            databaseCallback.getDatabaseCallback(ConfigDB.TYPE_SET_DATA);
//        }

    }


    public void dataRetrievalFunction(Object... args) {
//        tableName = (String) args[0];
//        centralizedDB = (CentralizedDB) args[1];
//        databaseCallback = (DatabaseCallback) args[2];
//        sqLiteDatabase = centralizedDB.getWritableDatabase();
//        Cursor cursor;
//
//        if (tableName.equals(ConfigDB.PUBLIC_PROPOSAL)) {
//            cursor = sqLiteDatabase.rawQuery("Select * from " + tableName + " ORDER BY " + ConfigDB.KEY_ID + " desc", null);
//            if (cursor.getCount() > 0) {
//                data = new ArrayList<>();
//                while (cursor.moveToNext()) {
//                    dataSub = new HashMap<>();
//                    dataSub.put(ConfigApiParseKey.PROPOSAL_DESCRIPTION, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PROPOSAL_DESCRIPTION)));
//                    dataSub.put(ConfigApiParseKey.PROPOSAL_FIRST_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PROPOSAL_FIRST_NAME)));
//                    dataSub.put(ConfigApiParseKey.PROPOSAL_IS_READ, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PROPOSAL_IS_READ)));
//                    dataSub.put(ConfigApiParseKey.PROPOSAL_LAST_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PROPOSAL_LAST_NAME)));
//                    dataSub.put(ConfigApiParseKey.PROPOSAL_MESSAGE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PROPOSAL_MESSAGE)));
//                    dataSub.put(ConfigApiParseKey.PROPOSAL_PROPOSAL_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PROPOSAL_PROPOSAL_ID)));
//                    dataSub.put(ConfigApiParseKey.PROPOSAL_TYPE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PROPOSAL_TYPE)));
//                    dataSub.put(ConfigApiParseKey.PROPOSAL_USER_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PROPOSAL_USER_ID)));
//                    dataSub.put(ConfigApiParseKey.PROPOSAL_USER_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PROPOSAL_USER_NAME)));
//                    dataSub.put(ConfigApiParseKey.PROPOSAL_USER_IMAGE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PROPOSAL_USER_IMAGE)));
//                    data.add(dataSub);
//                }
//                databaseCallback.getDatabaseCallback(ConfigDB.TYPE_GET_DATA, data);
//            } else {
//                databaseCallback.getDatabaseCallback(ConfigDB.TYPE_EMPTY_DATA);
//            }
//        } else if (tableName.equals(ConfigDB.PUBLIC_POST)) {
//            cursor = sqLiteDatabase.rawQuery("Select * from " + tableName + " ORDER BY " + ConfigDB.KEY_ID + " desc", null);
//            if (cursor.getCount() > 0) {
//                data = new ArrayList<>();
//                while (cursor.moveToNext()) {
//                    dataSub = new HashMap<>();
//                    dataSub.put(ConfigApiParseKey.PUBLIC_DESCRIPTION, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_DESCRIPTION)));
//                    dataSub.put(ConfigApiParseKey.PUBLIC_DISTANCE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_DISTANCE)));
//                    dataSub.put(ConfigApiParseKey.PUBLIC_FIRST_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_FIRST_NAME)));
//                    dataSub.put(ConfigApiParseKey.PUBLIC_LAST_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_LAST_NAME)));
//                    dataSub.put(ConfigApiParseKey.PUBLIC_IS_READ, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_IS_READ)));
//                    dataSub.put(ConfigApiParseKey.PUBLIC_OFFER_TIME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_OFFER_TIME)));
//                    dataSub.put(ConfigApiParseKey.PUBLIC_USER_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_USER_ID)));
//                    dataSub.put(ConfigApiParseKey.PUBLIC_USER_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_USER_NAME)));
//                    dataSub.put(ConfigApiParseKey.PUBLIC_OFFER_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_OFFER_ID)));
//                    dataSub.put(ConfigApiParseKey.PUBLIC_SUB_CATEGORY_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_SUB_CATEGORY_ID)));
//                    dataSub.put(ConfigApiParseKey.PUBLIC_SUPER_SUB_CATEGORY_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_SUPER_SUB_CATEGORY_ID)));
//                    dataSub.put(ConfigApiParseKey.PUBLIC_CATEGORY_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_CATEGORY_ID)));
//                    dataSub.put(ConfigApiParseKey.PUBLIC_USER_IMAGE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PUBLIC_USER_IMAGE)));
//
//                    data.add(dataSub);
//                }
//                databaseCallback.getDatabaseCallback(ConfigDB.TYPE_GET_DATA, data);
//            } else {
//                databaseCallback.getDatabaseCallback(ConfigDB.TYPE_EMPTY_DATA);
//            }
//        } else if (tableName.equals(ConfigDB.PRIVATE_POST)) {
//            cursor = sqLiteDatabase.rawQuery("Select * from " + tableName + " ORDER BY " + ConfigDB.KEY_ID + " desc", null);
//            if (cursor.getCount() > 0) {
//                data = new ArrayList<>();
//                while (cursor.moveToNext()) {
//                    Log.e("privatedataget", "privatedataget");
//                    dataSub = new HashMap<>();
//                    dataSub.put(ConfigApiParseKey.PRIVATE_USER_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PRIVATE_USER_NAME)));
//                    dataSub.put(ConfigApiParseKey.PRIVATE_GROUP_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PRIVATE_GROUP_ID)));
//                    dataSub.put(ConfigApiParseKey.PRIVATE_LAST_MESSAGE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PRIVATE_LAST_MESSAGE)));
//                    dataSub.put(ConfigApiParseKey.PRIVATE_LAST_MESSAGE_IS_READ, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PRIVATE_LAST_MESSAGE_IS_READ)));
//                    dataSub.put(ConfigApiParseKey.PRIVATE_LAST_MESSAGE_TIME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PRIVATE_LAST_MESSAGE_TIME)));
//                    dataSub.put(ConfigApiParseKey.PRIVATE_UNREAD_MESSAGE_COUNT, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PRIVATE_UNREAD_MESSAGE_COUNT)));
//                    dataSub.put(ConfigApiParseKey.PRIVATE_USER_IMAGE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PRIVATE_USER_IMAGE)));
//                    dataSub.put(ConfigApiParseKey.PRIVATE_USER_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.PRIVATE_USER_ID)));
//
//                    data.add(dataSub);
//                }
//                databaseCallback.getDatabaseCallback(ConfigDB.TYPE_GET_DATA, data);
//            } else {
//                databaseCallback.getDatabaseCallback(ConfigDB.TYPE_EMPTY_DATA);
//            }
//        } else if (tableName.equals(ConfigDB.GET_USER_DETAILSS)) {
//            cursor = sqLiteDatabase.rawQuery("Select * from " + tableName + " ORDER BY " + ConfigDB.KEY_ID + " desc", null);
//            if (cursor.getCount() > 0) {
//                data = new ArrayList<>();
//                while (cursor.moveToNext()) {
//                    Log.e("getuserdetails", "getuserdetails");
//                    dataSub = new HashMap<>();
//                    dataSub.put(ConfigApiParseKey.USER_BUSINESS_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.USER_BUSINESS_NAME)));
//                    dataSub.put(ConfigApiParseKey.USER_COUNTRY, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.USER_COUNTRY)));
//                    dataSub.put(ConfigApiParseKey.USER_FIRST_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.USER_FIRST_NAME)));
//                    dataSub.put(ConfigApiParseKey.USER_LAST_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.USER_LAST_NAME)));
//                    dataSub.put(ConfigApiParseKey.USER_COUNTRY_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.USER_COUNTRY_ID)));
//                    dataSub.put(ConfigApiParseKey.USER_STATE_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.USER_STATE_ID)));
//                    dataSub.put(ConfigApiParseKey.USER_IMAGE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.USER_IMAGE)));
//                    dataSub.put(ConfigApiParseKey.USER_TEL_CODE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.USER_TEL_CODE)));
//                    dataSub.put(ConfigApiParseKey.USER_PHONE_NUMBER, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.USER_PHONE_NUMBER)));
//                    dataSub.put(ConfigApiParseKey.USER_STATE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.USER_STATE)));
//                    dataSub.put(ConfigApiParseKey.USER_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.USER_ID)));
//                    dataSub.put(ConfigApiParseKey.USER_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.USER_NAME)));
//
//                    data.add(dataSub);
//                }
//                databaseCallback.getDatabaseCallback(ConfigDB.TYPE_GET_DATA, data);
//            } else {
//                databaseCallback.getDatabaseCallback(ConfigDB.TYPE_EMPTY_DATA);
//            }
//        } else if (tableName.equals(ConfigDB.CHAT_POSTS)) {
//            String groupId = (String) args[3];
//            Log.e("chatdetails", "chatdetails" + groupId);
//            cursor = sqLiteDatabase.rawQuery("Select * from " + tableName + " where " + ConfigDB.CHAT_ID + " =?", new String[]{groupId});
//
//            if (cursor.getCount() > 0) {
//                data = new ArrayList<>();
//                while (cursor.moveToNext()) {
//                    Log.e("chatdetails", "chatdetails");
//                    dataSub = new HashMap<>();
//                    dataSub.put(ConfigApiParseKey.CHAT_BATCH_COUNT, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_BATCH_COUNT)));
//                    dataSub.put(ConfigApiParseKey.CHAT_BEHAVIOUR, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_BEHAVIOUR)));
//                    dataSub.put(ConfigApiParseKey.CHAT_DELETED_ON, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_DELETED_ON)));
//                    dataSub.put(ConfigApiParseKey.CHAT_DELEVERED_ON, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_DELEVERED_ON)));
//                    dataSub.put(ConfigApiParseKey.CHAT_FILE_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_FILE_NAME)));
//                    dataSub.put(ConfigApiParseKey.CHAT_GROUP_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_GROUP_ID)));
//                    dataSub.put(ConfigApiParseKey.CHAT_HASHTAGS, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_HASHTAGS)));
//                    dataSub.put(ConfigApiParseKey.CHAT_IS_DELETED, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_IS_DELETED)));
//                    dataSub.put(ConfigApiParseKey.CHAT_IS_DELEVERED, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_IS_DELEVERED)));
//                    dataSub.put(ConfigApiParseKey.CHAT_IS_NOTIFICATION_SENT, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_IS_NOTIFICATION_SENT)));
//                    dataSub.put(ConfigApiParseKey.CHAT_IS_READ, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_IS_READ)));
//                    dataSub.put(ConfigApiParseKey.CHAT_OTHER_USER_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_OTHER_USER_ID)));
//                    dataSub.put(ConfigApiParseKey.CHAT_READ_ON, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_READ_ON)));
//                    dataSub.put(ConfigApiParseKey.CHAT_MESSAGE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_MESSAGE)));
//                    dataSub.put(ConfigApiParseKey.CHAT_URL, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_URL)));
//                    dataSub.put(ConfigApiParseKey.CHAT_THUMBNAIL, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_THUMBNAIL)));
//                    dataSub.put(ConfigApiParseKey.CHAT_TYPE_OBJECTS, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_TYPE_OBJECTS)));
//                    dataSub.put(ConfigApiParseKey.CHAT_TIME_STAMP, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_TIME_STAMP)));
//                    dataSub.put(ConfigApiParseKey.CHAT_MESSAGE_ID, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_MESSAGE_ID)));
//                    dataSub.put(ConfigApiParseKey.CHAT_USER_NAME, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_USER_NAME)));
//                    dataSub.put(ConfigApiParseKey.CHAT_USER_IMAGE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_USER_IMAGE)));
//                    dataSub.put(ConfigApiParseKey.CHAT_TYPE, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.CHAT_TYPE)));
//
//                    data.add(dataSub);
//                }
//                databaseCallback.getDatabaseCallback(ConfigDB.TYPE_GET_DATA, data);
//            } else {
//                databaseCallback.getDatabaseCallback(ConfigDB.TYPE_EMPTY_DATA);
//            }
//        }else if (tableName.equals(ConfigDB.SELECTED_CATEGORIES)) {
//            cursor = sqLiteDatabase.rawQuery("Select * from " + tableName + " ORDER BY " + ConfigDB.KEY_ID + " desc", null);
//            if (cursor.getCount() > 0) {
//                data = new ArrayList<>();
//                while (cursor.moveToNext()) {
//                    Log.e("selectedcategoriesdata", "SELECTED_CATEGORIES");
//                    dataSub = new HashMap<>();
//                    dataSub.put(ConfigApiParseKey.SELECTED_CATEGORY, cursor.getString(cursor.getColumnIndex(ConfigApiParseKey.SELECTED_CATEGORY)));
//
//                    data.add(dataSub);
//                }
//                databaseCallback.getDatabaseCallback(ConfigDB.TYPE_GET_DATA, data);
//            } else {
//                databaseCallback.getDatabaseCallback(ConfigDB.TYPE_EMPTY_DATA);
//            }
//        }
    }

    public boolean isDataAvailable(String tableName, CentralizedDB centralizedDB) {
        Log.e("Data Available in", "----" + tableName);
        CentralizedDB centralizedDBCheck = centralizedDB;
        sqLiteDatabase = centralizedDB.getWritableDatabase();

        String tableNameCheck = tableName;
        Cursor cursorCheck = sqLiteDatabase.rawQuery("Select * from " + tableName, null);
        if (cursorCheck.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteTableDB(String tableName, CentralizedDB centralizedDB) {
        CentralizedDB centralizedDBCheck = centralizedDB;
        sqLiteDatabase = centralizedDB.getWritableDatabase();
        int deleteProgress = sqLiteDatabase.delete(tableName, null, null);
    }

    public void deleteTableRowDB(String tableName, CentralizedDB centralizedDB, String groupID) {
        CentralizedDB centralizedDBCheck = centralizedDB;
        sqLiteDatabase = centralizedDB.getWritableDatabase();
        int deleteProgress = sqLiteDatabase.delete(tableName + " where " + ConfigDB.CHAT_ID + " =?", null, new String[]{groupID});
        Log.e("deleteTableRow", "----" + tableName);
    }
}
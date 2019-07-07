package controllerAll;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.File;

import callBacks.DatabaseCallback;
import callBacks.DialogClick;
import functionalityAll.CatchList;
import functionalityAll.CentraliseAlertDialog;
import functionalityAll.CentraliseAnimation;
import functionalityAll.CentraliseCheck;
import functionalityAll.CentraliseCustomDialog;
import functionalityAll.CentraliseDataMakerClass;
import functionalityAll.CentraliseFontChanger;
import functionalityAll.CentraliseSocketFunction;
import functionalityAll.CentraliseToast;
import functionalityAll.CentraliseToolBar;
import functionalityAll.CentralisedSharedPreference;
import functionalityAll.CentralizeDateTime;
import functionalityAll.CentralizedDB;
import functionalityAll.CustomFont;
import functionalityAll.DateTime;
import functionalityAll.DrawerLayoutCustom;
import functionalityAll.FileOperations;
import functionalityAll.ImageURLChanger;
import restro.bts.com.restro.R;

/**
 * Created by Abhay dhiman
 */

//Controller centralised call calling in whole application extended Application
public class Controller extends Application {

    private CentraliseCheck centraliseCheck = new CentraliseCheck();
    private CentraliseAnimation centraliseAnimation = new CentraliseAnimation();
    private ProgressDialog pDialog;
    private CentraliseToast toast_all = new CentraliseToast();
    private CentraliseFontChanger custom = new CentraliseFontChanger();
    private CentraliseToolBar toolBar = new CentraliseToolBar();
    private CentraliseCustomDialog centraliseCustomDialog = new CentraliseCustomDialog();
    private DrawerLayoutCustom drawerLayout = new DrawerLayoutCustom();
    private FileOperations fileOperations = new FileOperations();
    private CentralizeDateTime centralizeDateTime = new CentralizeDateTime();
    private CustomFont customFont = new CustomFont();
    private ImageURLChanger imageURLChanger = new ImageURLChanger();
    private CentralisedSharedPreference centralisedSharedPreference;
    private CentraliseAlertDialog centraliseAlertDialog = new CentraliseAlertDialog();
    private CentraliseSocketFunction centraliseSocketFunction = new CentraliseSocketFunction();
    private CentraliseDataMakerClass centraliseDataMakerClass = new CentraliseDataMakerClass();
    private DateTime dateTime = new DateTime();
    private static Controller sInstance;
    private CentralizedDB centralizedDB;
    private String groupID;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sInstance.initSharedPreference();
        sInstance.initCentraliseDB();
    }

    public void initSharedPreference() {
        centralisedSharedPreference = new CentralisedSharedPreference(this);
    }

    public void initCentraliseDB() {
        centralizedDB = new CentralizedDB(this, ConfigDB.DB_VERSION);
    }

    //Check if Edit Text has empty value or not by calling class CentraliseCheck method Empty_CHeck from controller class
    public boolean emptyCheckEditText(EditText editText) {
        return centraliseCheck.emptyCheckEditText(editText);
    }

    public boolean emptyCheckTextView(TextView textview) {
        return centraliseCheck.emptyCheckTextView(textview);
    }


    //Check if Edit Text has email formate like "@ etc value or not by calling class CentraliseCheck method Email_edittext from controller class
    public boolean emailEditText(EditText editText) {
        return centraliseCheck.emailEditText(editText);
    }

    //Check if Edit Text password nad confirm password value are matches or not by calling class CentraliseCheck method Check_all_confirmpassword from controller class
    public boolean matchesEditText(EditText editText, String match) {
        return centraliseCheck.matchesEditText(editText, match);
    }


    public boolean editTextLength(EditText editText, int start, int end) {
        return centraliseCheck.editTextLength(editText, start, end);
    }

    public void toolBarCustom(Object... args) {
        toolBar.toolBarCustom(args);
    }

    public void changeTextType(boolean checkType){
        toolBar.changeTextType(checkType);
    }

    public void dialogCustomShow(Object... args) {
        centraliseCustomDialog.dialogCustomShow(args);
    }

    public void disableDialog() {
        centraliseCustomDialog.disableDialog();
    }

    public void alertDialogShow(Context context, String title, String message, String positive, String negative, DialogClick dialogClick) {
        centraliseAlertDialog.alertDialogShow(context, title, message, positive, negative, dialogClick);
    }


    public String Space_put(String text) {
        return centraliseCheck.spacePut(text);
    }

    public void animAllForward(Context context) {
        centraliseAnimation.animAllForward(context);
    }

    public void animAllBackward(Context context) {
        centraliseAnimation.animAllBackward(context);
    }

    public void animAllSlideUp(Context context) {
        centraliseAnimation.animAllSlideUp(context);
    }

    public void animAllSlideDown(Context context) {
        centraliseAnimation.animAllSlideDown(context);
    }


    //Check Internet connections
    public boolean isNetWorkStatusAvailable(Context context) {
        return centraliseCheck.isNetWorkStatusAvailable(context);
    }

    public boolean isGPSEnable(Context context) {
        return centraliseCheck.isGPSStatusAvailable(context);
    }


    public void toastShow(Context context, String message) {
        toast_all.toastShow(context, message);
    }

    public void snackBarShow(CoordinatorLayout coordinatorLayout, String message) {
        toast_all.snackBarShow(coordinatorLayout, message);
    }

    public void setStringSP(String key, String value) {
        centralisedSharedPreference.setString(key, value);
    }


    public String getStringSP(String key) {
        return centralisedSharedPreference.getString(key);
    }

       public void setBooleanSP(String key, boolean value) {
        centralisedSharedPreference.setBoolean(key, value);
    }

    public boolean getBooleanSP(String key) {
        return centralisedSharedPreference.getBoolean(key);
    }

    public void setIntSP(String key, int value) {
        centralisedSharedPreference.setInt(key, value);
    }

    public int getIntnSP(String key) {
        return centralisedSharedPreference.getInt(key);
    }

    public void clearSharedPreference() {
        centralisedSharedPreference.clearSharedPreference();
    }

    public void removeSharedPreferencesKey(String key) {
        centralisedSharedPreference.removeSharedPreferencesKey(key);
    }

    public void fontNormal(TextView v, Context c) {
        custom.fontNormal(v, c);
    }

    public void fontBold(TextView v, Context c) {
        custom.fontBold(v, c);
    }

    public void fontLight(TextView v, Context c) {
        custom.fontLight(v, c);
    }


    public void initialiseDrawer(Context context) {
        drawerLayout.initialiseDrawer(context);
    }

    public boolean checkDrawerOpen() {
        return drawerLayout.checkDrawerOpen();
    }

    public void openDrawerLayout() {
        drawerLayout.openDrawerLayout();
    }

    public void closeDrawerLayout() {
        drawerLayout.closeDrawerLayout();
    }

    public void socketInit(final Context context, Object... arg) {
        centraliseSocketFunction.socketInit(context, arg);
    }

    public void socketEmit(Object... data) {
        centraliseSocketFunction.socketEmit(data);
    }

    public void disconnectSocket(String key) {
        centraliseSocketFunction.disconnectSocket(key);
    }

    public void StartLoaderView(RelativeLayout loaderRelativeLayout) {
        if (loaderRelativeLayout != null) {
            loaderRelativeLayout.setVisibility(View.VISIBLE);
        }
    }

    public void StopLoaderView(RelativeLayout loaderRelativeLayout) {
        if (loaderRelativeLayout != null) {
            loaderRelativeLayout.setVisibility(View.GONE);
        }
    }

    //Start Progress Dialog
    public void pdStarts(Context context, String message) {
        pDialog = new ProgressDialog(context);
        try {
            pDialog.show();
        } catch (WindowManager.BadTokenException e) {
            CatchList.Report(e);
        }

        try {
            pDialog.setCancelable(false);
            pDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pDialog.setContentView(R.layout.progress_dialog);
        } catch (Exception e) {
            CatchList.Report(e);
        }
    }


    //Start Progress Dialog
    public void pdStart(Context context, String message) {
        pDialog = new ProgressDialog(context);
        try {
            pDialog.show();
        } catch (WindowManager.BadTokenException e) {
            CatchList.Report(e);
        }

        try {
            pDialog.setCancelable(false);
            pDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pDialog.setContentView(R.layout.progress_dialog);
        } catch (Exception e) {
            CatchList.Report(e);
        }
    }


    //Stop Progress Dialog
    public void pdStop() {
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    public void DataMaker(Object... args) {
        try {
            centraliseDataMakerClass.DataMaker(args);
        } catch (Exception e) {
            Log.e("DrawerExpSa", "DrawerExpSa" + e);
            CatchList.Report(e);
        }
    }

    public String getCurrentTimeInUTC() {
        return dateTime.getCurrentTimeInUTC();
    }

    public String getTime(long time) {
        return dateTime.getTime(time);
    }


    public boolean isFileExist(String imageName, String subFolderName) {
        return fileOperations.isFileExist(imageName, subFolderName);
    }

    public boolean isFileExistInGallery(String imageName, String subFolderName) {
        return fileOperations.isFileExistInGallery(imageName, subFolderName);
    }


    public String getFileName(String imageName, String subFolderName) {
        return fileOperations.getFileName(imageName, subFolderName);
    }

    public String getFileNameFromGallery(String imageName, String subFolderName) {
        return fileOperations.getFileNameFromGallery(imageName, subFolderName);
    }

    public String getFilePath(String imageName, String subFolderName) {
        return fileOperations.getFilePath(imageName, subFolderName);
    }

    public String getFilePathOfGallery(String imageName, String subFolderName) {
        return fileOperations.getFilePathOfGallery(imageName, subFolderName);
    }

    public String getDestinationPath(String subFolderName) {
        return fileOperations.getDestinationPath(subFolderName);
    }

    public String getDestinationPathVisibileToGallery(String subFolderName) {
        return fileOperations.getDestinationPathVisibileToGallery(subFolderName);
    }

    public void copyFile(File sourceFile, File destFile) {
        fileOperations.copyFile(sourceFile, destFile);
    }


    public String getUtcTime() {
        return centralizeDateTime.getUtcTime();
    }

    public String getUtcConvertTime(long time) {
        return centralizeDateTime.getUtcConvertTime(time);
    }


    //    Font For Text View

    public void josefinsansBoldTextView(TextView v, Context c) {
        customFont.josefinsansBoldTextView(v, c);
    }

    public void josefinsansBoldItalicTextView(TextView v, Context c) {
        customFont.josefinsansBoldItalicTextView(v, c);
    }

    public void josefinsansItalicTextView(TextView v, Context c) {
        customFont.josefinsansItalicTextView(v, c);
    }

    public void josefinsansLightTextView(TextView v, Context c) {
        customFont.josefinsansLightTextView(v, c);
    }

    public void josefinsansLightItalicTextView(TextView v, Context c) {
        customFont.josefinsansLightItalicTextView(v, c);
    }

    public void josefinsansRegularTextView(TextView v, Context c) {
        customFont.josefinsansRegularTextView(v, c);
    }

    public void josefinsansSemiBoldItalicTextView(TextView v, Context c) {
        customFont.josefinsansSemiBoldItalicTextView(v, c);
    }

    public void josefinsansThinTextView(TextView v, Context c) {
        customFont.josefinsansThinTextView(v, c);
    }

    public void josefinsansThinItalicTextView(TextView v, Context c) {
        customFont.josefinsansThinItalicTextView(v, c);
    }

    public void jsosefinsansSemiBoldTextView(TextView v, Context c) {
        customFont.jsosefinsansSemiBoldTextView(v, c);
    }

    public void inLineTextView(TextView v, Context c) {
        customFont.inLineTextView(v, c);
    }


//Font for EditTex

    public void josefinsansBoldEditText(EditText v, Context c) {
        customFont.josefinsansBoldEditText(v, c);
    }

    public void josefinsansBoldItalicEditText(EditText v, Context c) {
        customFont.josefinsansBoldItalicEditText(v, c);
    }

    public void josefinsansItalicEditText(EditText v, Context c) {
        customFont.josefinsansItalicEditText(v, c);
    }

    public void josefinsansLightEditText(EditText v, Context c) {
        customFont.josefinsansLightEditText(v, c);
    }

    public void josefinsansLightItalicEditText(EditText v, Context c) {
        customFont.josefinsansLightItalicEditText(v, c);
    }

    public void josefinsansRegularEditText(EditText v, Context c) {
        customFont.josefinsansRegularEditText(v, c);
    }

    public void josefinsansSemiBoldItalicEditText(EditText v, Context c) {
        customFont.josefinsansSemiBoldItalicEditText(v, c);
    }

    public void josefinsansThinEditText(EditText v, Context c) {
        customFont.josefinsansThinEditText(v, c);
    }

    public void josefinsansThinItalicEditText(EditText v, Context c) {
        customFont.josefinsansThinItalicEditText(v, c);
    }

    public void jsosefinsansSemiBoldEditText(EditText v, Context c) {
        customFont.jsosefinsansSemiBoldEditText(v, c);
    }


    //Font for Button

    public void josefinsansBoldButton(Button v, Context c) {
        customFont.josefinsansBoldButton(v, c);
    }

    public void josefinsansBoldItalicButton(Button v, Context c) {
        customFont.josefinsansBoldItalicButton(v, c);
    }

    public void josefinsansItalicButton(Button v, Context c) {
        customFont.josefinsansItalicButton(v, c);
    }

    public void josefinsansLightButton(Button v, Context c) {
        customFont.josefinsansLightButton(v, c);
    }

    public void josefinsansLightItalicButton(Button v, Context c) {
        customFont.josefinsansLightItalicButton(v, c);
    }

    public void josefinsansRegularButton(Button v, Context c) {
        customFont.josefinsansRegularButton(v, c);
    }

    public void josefinsansSemiBoldItalicButton(Button v, Context c) {
        customFont.josefinsansSemiBoldItalicButton(v, c);
    }

    public void josefinsansThinButton(Button v, Context c) {
        customFont.josefinsansThinButton(v, c);
    }

    public void josefinsansThinItalicButton(Button v, Context c) {
        customFont.josefinsansThinItalicButton(v, c);
    }

    public void jsosefinsansSemiBoldButton(Button v, Context c) {
        customFont.jsosefinsansSemiBoldButton(v, c);
    }

    //Font for Button

    public void josefinsansBoldButton(RadioButton v, Context c) {
        customFont.josefinsansBoldButton(v, c);
    }

    public void josefinsansBoldItalicButton(RadioButton v, Context c) {
        customFont.josefinsansBoldItalicButton(v, c);
    }

    public void josefinsansItalicButton(RadioButton v, Context c) {
        customFont.josefinsansItalicButton(v, c);
    }

    public void josefinsansLightButton(RadioButton v, Context c) {
        customFont.josefinsansLightButton(v, c);
    }

    public void josefinsansLightItalicButton(RadioButton v, Context c) {
        customFont.josefinsansLightItalicButton(v, c);
    }

    public void josefinsansRegularButton(RadioButton v, Context c) {
        customFont.josefinsansRegularButton(v, c);
    }

    public void josefinsansSemiBoldItalicButton(RadioButton v, Context c) {
        customFont.josefinsansSemiBoldItalicButton(v, c);
    }

    public void josefinsansThinButton(RadioButton v, Context c) {
        customFont.josefinsansThinButton(v, c);
    }

    public void josefinsansThinItalicButton(RadioButton v, Context c) {
        customFont.josefinsansThinItalicButton(v, c);
    }

    public void jsosefinsansSemiBoldButton(RadioButton v, Context c) {
        customFont.jsosefinsansSemiBoldButton(v, c);
    }

    public void introInline(TextView v, Context c) {
        customFont.introInline(v, c);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        return imageURLChanger.getImageUri(inContext, inImage);
    }


    public String getRealPathFromURI(Context context, Uri uri) {
        return imageURLChanger.getRealPathFromURI(context, uri);
    }

    public boolean centralizedDataCheck(String tableName) {
        return centralizedDB.isDataAvailable(tableName, centralizedDB);
    }

    public void centralizedDBGetData(Object... args) {
        if(args.length == 2) {
            centralizedDB.dataRetrievalFunction(args[0], centralizedDB, args[1]);
        }else {
            centralizedDB.dataRetrievalFunction(args[0], centralizedDB, args[1],args[2]);
        }
    }

    public void centralizedDBSetData(String tableName, DatabaseCallback databaseCallback, Object args) {
        centralizedDB.dataInsertionFunction(tableName, centralizedDB, databaseCallback, args);
    }

    public void centralizedDBSetData(String tableName, DatabaseCallback databaseCallback, Object args, String groupID) {
        centralizedDB.dataInsertionFunction(tableName, centralizedDB, databaseCallback, args,groupID);
    }

    public void centralizedDeleteTableRecord(String tableName) {
        centralizedDB.deleteTableDB(tableName, centralizedDB);
    }

    public void centralizedDeleteTableRow(String tableName, String groupID) {
        centralizedDB.deleteTableRowDB(tableName,centralizedDB,groupID);
    }

}
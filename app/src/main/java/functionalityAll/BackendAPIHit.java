package functionalityAll;

import android.content.Context;


import java.util.HashMap;

import callBacks.ChildApiCallback;
import controllerAll.Controller;
import restro.bts.com.restro.R;

public class BackendAPIHit {
    private static Context mContext;
    private static ChildApiCallback childApiCallback;
    private static Controller controller;
    private static HashMap<String,Object> mDataGetter;

    public static void APIHit(Context context, HashMap<String,Object> dataGetter){
        mContext = context;
        mDataGetter = dataGetter;
        controller = (Controller) context.getApplicationContext();

        BackendHit();
    }

    public static void BackendHit(){
        childApiCallback = new ChildApiCallback() {
            @Override
            public void Data_call_back(Object... args) {

            }
        };
        controller.DataMaker(mContext, mContext.getString(R.string.chat_first_time_api), childApiCallback, mDataGetter);
    }
}

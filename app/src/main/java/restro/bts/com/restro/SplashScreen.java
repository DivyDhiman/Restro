package restro.bts.com.restro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import controllerAll.Config;
import controllerAll.ConfigSharedPreferenceKey;
import controllerAll.Controller;
import functionalityAll.BaseActivity;
import functionalityAll.CatchList;

/**
 * Created by Abhay dhiman
 */

public class SplashScreen extends BaseActivity {

    private Context context;
    private Controller controller;
    private Intent intent;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;

        controller = (Controller) context.getApplicationContext();
        userID = controller.getStringSP(ConfigSharedPreferenceKey.userID);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final int welcomeScreenDisplay = 1800;

        Thread welcomeThread = new Thread() {
            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    while (wait < welcomeScreenDisplay) {
                        sleep(100);
                        wait += 100;
                    }
                } catch (Exception e) {
                    CatchList.Report(e);
                } finally {
                    if (userID.equals(Config.NO_DATA) || userID.equals("") || userID == null) {
                        intent = new Intent(context, IntroScreen.class);
                        startActivity(intent);
                        finish();
                    } else {
                        intent = new Intent(context, DashBoardScreen.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
        welcomeThread.start();
    }
}



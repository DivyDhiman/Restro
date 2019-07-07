package functionalityAll;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;

import restro.bts.com.restro.R;


/**
 * Created by Abhay dhiman
 */

public class DrawerLayoutCustom {

    private android.support.v4.widget.DrawerLayout drawerLayout;

    public void initialiseDrawer(Context context) {
       drawerLayout = (DrawerLayout) ((Activity) context).findViewById(R.id.drawer_layout);
    }

    public boolean checkDrawerOpen() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            return true;
        } else {
            return false;
        }
    }

    public void openDrawerLayout() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void closeDrawerLayout() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

}

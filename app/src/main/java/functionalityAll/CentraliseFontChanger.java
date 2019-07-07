package functionalityAll;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Abhay dhiman
 */

public class CentraliseFontChanger {

    public void fontNormal(TextView v, Context c) {
        Typeface face = Typeface.createFromAsset(c.getAssets(), "fonts/calibri.ttf");
        v.setTypeface(face);
    }

    public void fontBold(TextView v, Context c) {
        Typeface face = Typeface.createFromAsset(c.getAssets(), "fonts/calibrib.ttf");
        v.setTypeface(face);
    }

    public void fontLight(TextView v, Context c) {
        Typeface face = Typeface.createFromAsset(c.getAssets(), "fonts/calibril.ttf");
        v.setTypeface(face);
    }

    }


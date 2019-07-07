package functionalityAll;

import android.app.Activity;
import android.content.Context;

import restro.bts.com.restro.R;


/**
 * Created by Abhay dhiman
 */


public class CentraliseAnimation {


    public void animAllForward(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_exit);
    }

    public void animAllBackward(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_exit);
    }


    public void animAllSlideUp(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.slide_in_top, R.anim.no_animation2);
    }

    public void animAllSlideDown(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.no_animation, R.anim.slide_in_bottom);
    }
}

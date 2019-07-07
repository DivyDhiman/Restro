package functionalityAll;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;



import callBacks.DialogCallBack;
import restro.bts.com.restro.R;

/**
 * Created by Abhay dhiman
 */

@SuppressWarnings("ConstantConditions")
public class CentraliseCustomDialog implements View.OnClickListener {

    private Context context;
    private Dialog customDialog;
    private DialogCallBack dialogCallBack;
    private String type_value;
    private int layout;
    private TextView Camera, Gallery, headeTxt, descriptionTxt, negativeTxt, positiveTxt, processingDetails, sharePicture,
            shareVideo, capturePicture, captureVideo, createProposal, textGoBack;
    private Button yesBtn, noBtn, oK, logOut, cancel,deleteBtn;


    public void dialogCustomShow(Object... args) {
        this.context = (Context) args[0];
        this.layout = (int) args[1];
        this.type_value = (String) args[2];
        this.dialogCallBack = (DialogCallBack) args[3];

        customDialog = new Dialog(context);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(layout);
        customDialog.setCanceledOnTouchOutside(true);
        customDialog.setCancelable(true);
        customDialog.show();

        callViewType(type_value);
    }

    private void callViewType(String type) {
        if (type.equals(context.getString(R.string.profile_dialog))) {
            showHideView(0, customDialog);
        } else if (type.equals(context.getString(R.string.custom_alert_dialog))) {
            showHideView(1, customDialog);
        } else if (type.equals(context.getString(R.string.myaccount_dialog))) {
            showHideView(2, customDialog);
        } else if (type.equals(context.getString(R.string.broadcast_dialog))) {
            showHideView(3, customDialog);
        } else if (type.equals(context.getString(R.string.chat_share_dialog))) {
            showHideView(4, customDialog);
        } else if (type.equals(context.getString(R.string.create_proposal_dialog))) {
            showHideView(5, customDialog);
        } else if (type.equals(context.getString(R.string.chat_share_lost_dialog))) {
            showHideView(6, customDialog);
        } else if (type.equals(context.getString(R.string.logout_dialog))) {
            showHideView(7, customDialog);
        } else if (type.equals(context.getString(R.string.delete_post_dialog))) {
            showHideView(8, customDialog);
        }
    }

    public void showHideView(int val, Dialog customDialog) {
        switch (val) {
            case 0:
                Camera = (TextView) customDialog.findViewById(R.id.camera);
                Gallery = (TextView) customDialog.findViewById(R.id.gallery);

                Gallery.setOnClickListener(this);
                Camera.setOnClickListener(this);
                break;

            case 1:
                headeTxt = (TextView) customDialog.findViewById(R.id.header_txt);
                descriptionTxt = (TextView) customDialog.findViewById(R.id.description_txt);
                negativeTxt = (TextView) customDialog.findViewById(R.id.negative_txt);
                positiveTxt = (TextView) customDialog.findViewById(R.id.positive_txt);

                headeTxt.setVisibility(View.GONE);
                descriptionTxt.setText(context.getString(R.string.please_enable_gps));
                negativeTxt.setText(context.getString(R.string.cancel_dailog));
                positiveTxt.setText(context.getString(R.string.settings_dialog));

                negativeTxt.setOnClickListener(this);
                positiveTxt.setOnClickListener(this);
                break;

//            case 2:
//                yesBtn = (Button) customDialog.findViewById(R.id.yes_btn);
//                noBtn = (Button) customDialog.findViewById(R.id.no_btn);
//                yesBtn.setOnClickListener(this);
//                noBtn.setOnClickListener(this);
//                break;
//
//            case 3:
//                oK = (Button) customDialog.findViewById(R.id.ok);
//
//                processingDetails = (TextView) customDialog.findViewById(R.id.processing_details);
//
//                oK.setOnClickListener(this);
//
//                break;
//
//            case 4:
//                sharePicture = (TextView) customDialog.findViewById(R.id.share_picture);
//                shareVideo = (TextView) customDialog.findViewById(R.id.share_video);
//                capturePicture = (TextView) customDialog.findViewById(R.id.capture_picture);
//                captureVideo = (TextView) customDialog.findViewById(R.id.capture_video);
//                negativeTxt = (TextView) customDialog.findViewById(R.id.negative_txt);
//
//                sharePicture.setOnClickListener(this);
//                shareVideo.setOnClickListener(this);
//                capturePicture.setOnClickListener(this);
//                captureVideo.setOnClickListener(this);
//                negativeTxt.setOnClickListener(this);
//                break;
//
//            case 5:
//                createProposal = (TextView) customDialog.findViewById(R.id.create_proposal_details);
//
//                oK = (Button) customDialog.findViewById(R.id.ok);
//
//                oK.setOnClickListener(this);
//                break;
//
//            case 6:
//                textGoBack = (TextView) customDialog.findViewById(R.id.text_go_back);
//                negativeTxt = (TextView) customDialog.findViewById(R.id.negative_txt);
//                positiveTxt = (TextView) customDialog.findViewById(R.id.positive_txt);
//
//                textGoBack.setText(context.getString(R.string.send_chat_data_lost));
//
//                negativeTxt.setOnClickListener(this);
//                positiveTxt.setOnClickListener(this);
//
//                positiveTxt.setOnClickListener(this);
//                negativeTxt.setOnClickListener(this);
//                break;
//
//            case 7:
//                logOut = (Button) customDialog.findViewById(R.id.logoutbtn);
//                cancel = (Button) customDialog.findViewById(R.id.cancel);
//                logOut.setOnClickListener(this);
//                cancel.setOnClickListener(this);
//                break;
//
//            case 8:
//                deleteBtn = (Button) customDialog.findViewById(R.id.deletebtn);
//                cancel = (Button) customDialog.findViewById(R.id.cancel);
//                deleteBtn.setOnClickListener(this);
//                cancel.setOnClickListener(this);
//                break;

        }
    }


    public void disableDialog() {
        if (customDialog != null)
            customDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gallery:
                dialogCallBack.dialogDataCallback(context.getString(R.string.gallery));
                disableDialog();
                break;

            case R.id.camera:
                dialogCallBack.dialogDataCallback(context.getString(R.string.camera));
                disableDialog();
                break;

            case R.id.negative_txt:
                dialogCallBack.dialogDataCallback(context.getString(R.string.negative_txt));
                disableDialog();
                break;

            case R.id.positive_txt:
                dialogCallBack.dialogDataCallback(context.getString(R.string.positive_txt));
                disableDialog();
                break;

//            case R.id.yes_btn:
//                dialogCallBack.dialogDataCallback(context.getString(R.string.yes_txt));
//                disableDialog();
//                break;
//
//            case R.id.no_btn:
//                dialogCallBack.dialogDataCallback(context.getString(R.string.no_txt));
//                disableDialog();
//                break;
//
//            case R.id.ok:
//                dialogCallBack.dialogDataCallback(context.getString(R.string.ok_txt));
//                disableDialog();
//                break;
//
//            case R.id.share_picture:
//                dialogCallBack.dialogDataCallback(context.getString(R.string.share_picture));
//                disableDialog();
//                break;
//
//            case R.id.share_video:
//                dialogCallBack.dialogDataCallback(context.getString(R.string.share_video));
//                disableDialog();
//                break;
//
//            case R.id.capture_picture:
//                dialogCallBack.dialogDataCallback(context.getString(R.string.capture_picture));
//                disableDialog();
//                break;
//
//            case R.id.capture_video:
//                dialogCallBack.dialogDataCallback(context.getString(R.string.capture_video));
//                disableDialog();
//                break;
//
//            case R.id.logoutbtn:
//                dialogCallBack.dialogDataCallback(context.getString(R.string.logout_txt));
//                disableDialog();
//                break;
//
//            case R.id.cancel:
//                dialogCallBack.dialogDataCallback(context.getString(R.string.cancel_txt));
//                disableDialog();
//                break;
//
//            case R.id.deletebtn:
//                dialogCallBack.dialogDataCallback(context.getString(R.string.delete_txt));
//                disableDialog();
//                break;
        }
    }
}

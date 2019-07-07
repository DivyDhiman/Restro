package restro.bts.com.restro;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import callBacks.ToolbarCallBack;
import controllerAll.Controller;
import functionalityAll.BaseActivity;
import functionalityAll.CentraliseToolBar;

/**
 * Created by Abhay dhiman
 */


public class CentraliseWebView extends BaseActivity {

    private Context context;
    private Controller controller;
    private String urlIntent;
    private TextView headerTxt;
    private WebView webView;
    private CentraliseToolBar centraliseToolBar;
    private ToolbarCallBack toolbarCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.centralise_web_vew);
        context = this;
        controller = (Controller) context.getApplicationContext();
        changeStatusBarColor(context, R.color.intro_nav_bar_color, R.color.intro_status_bar_color);
        urlIntent = getIntent().getExtras().getString("urlGet");
        callBacks();
    }

    private void callBacks() {
        toolbarCallBack = new ToolbarCallBack() {
            @Override
            public void toolBarClick(Object... args) {
                String clickCallback = (String) args[0];
                if(clickCallback.equals(getString(R.string.start_view))){
                    onBackPressed();
                }
            }
        };

        setCustomActionBar();
        initialise();
    }

    private void initialise() {
        headerTxt = findViewById(R.id.header_txt);
        webView = findViewById(R.id.web_view);

        controller.introInline(headerTxt,context);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        controller.pdStart(context,"");
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("load url", url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.e("URL", "URL" + url);
                controller.pdStop();
                return super.shouldInterceptRequest(view, url);
            }

           @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
               controller.pdStop();
            }
        });
        webView.loadUrl(urlIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.animAllBackward(context);
    }


    private void setCustomActionBar() {
        centraliseToolBar = new CentraliseToolBar();
        centraliseToolBar.toolBarCustom(getString(R.string.web_view_screen_toolbar),context,toolbarCallBack);
    }
}


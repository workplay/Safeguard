package com.hkust.swangbv.hsbcsafeguard;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VoicePrintActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_print);
        WebView myWebView =  findViewById(R.id.webview);

        if (myWebView == null) {
            Log.e("ddd","mWebView is null"); }
        else if (myWebView.getSettings() == null) { Log.e("ddd","Settings is null"); }

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("https://azure.microsoft.com/zh-cn/services/cognitive-services/speaker-recognition/#speaker-verification-form");

    }
}

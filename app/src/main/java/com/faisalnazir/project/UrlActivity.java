package com.faisalnazir.project;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class UrlActivity extends AppCompatActivity {

    WebView web1;
    TextView tv1;
    String address;
    ProgressBar pbar;



    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_url);

        web1=(WebView)findViewById (R.id.webView1);
        tv1=(TextView)findViewById (R.id.editText1);
        pbar=(ProgressBar)findViewById (R.id.progressBar1);
        pbar.setVisibility (View.VISIBLE);

            address = getIntent ( ).getStringExtra ("address");

            web1.setScrollBarStyle (WebView.SCROLLBARS_INSIDE_OVERLAY);
            web1.setScrollbarFadingEnabled (false);

            Toast.makeText (UrlActivity.this, "Loading...", Toast.LENGTH_LONG).show ( );




        WebSettings webSetting = web1.getSettings ( );
            webSetting.setBuiltInZoomControls (true);
            webSetting.setJavaScriptEnabled (true);
        web1.setWebChromeClient(new WebChromeClient());

            web1.setWebViewClient (new WebViewClient ( ));

            web1.loadUrl (address);



    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted (view, url, favicon);
            pbar.setVisibility (View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl (url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished (view, url);
            pbar.setVisibility (View.GONE);
        }

    }

    @Override
    public boolean onKeyDown ( int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web1.canGoBack ( )) {
            web1.goBack ( );
            return true;
        }
        return super.onKeyDown (keyCode, event);
    }



}

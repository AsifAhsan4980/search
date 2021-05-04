package com.example.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Webview extends AppCompatActivity {
    ProgressBar progressBar;
    public String url="https://google.com/";
    SwipeRefreshLayout mySwipeRefreshLayout;
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);        isNetworkAvailable();

        mySwipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.refresh);
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
        progressBar=(ProgressBar)findViewById(R.id.prg);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                setTitle("Downloading......");
            }
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view,url);
                progressBar.setVisibility(View.GONE);
                setTitle(view.getTitle());
            }

        });

        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                progressBar.setProgress(newProgress);
                if(newProgress == 100){
                    // Hide the progressbar
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        if(isNetworkAvailable()){
            webview.loadUrl(url);
        }


        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if(isNetworkAvailable()){
                            webview.loadUrl(webview.getUrl());
                        }



                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}
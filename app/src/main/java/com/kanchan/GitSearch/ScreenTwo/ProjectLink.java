package com.kanchan.GitSearch.ScreenTwo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kanchan.GitSearch.Config.Helper;
import com.kanchan.GitSearch.Payload.Item;
import com.kanchan.GitSearch.Payload.SearchItem;
import com.kanchan.GitSearch.R;

import java.util.List;

public class ProjectLink extends Activity {

    WebView webView;
    String _id, url;
    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = new Helper();
        helper.makeFullScreen(ProjectLink.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_link);
        webView = (findViewById(R.id.webView));
        webView.setWebViewClient(new MyBrowser());
        loadWebView();
    }

    public void loadWebView() {
        Intent i = getIntent();
        _id = i.getStringExtra("_id");
        SearchItem searchItem = Helper.searchPayloadSuccess;
        List<Item> datumList = searchItem.getItems();
        for (Item datum : datumList) {
            if (_id.equals(datum.getId() + "")) {
                url = datum.getHtmlUrl();
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.loadUrl(url);
            }
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

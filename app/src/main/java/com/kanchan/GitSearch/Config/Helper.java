package com.kanchan.GitSearch.Config;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.kanchan.GitSearch.Payload.ContributorItem;
import com.kanchan.GitSearch.Payload.Item;
import com.kanchan.GitSearch.Payload.SearchItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class Helper {

    public static SearchItem searchPayloadSuccess = null;
    public static List<Item> items = null;
    public static ResponseBody searchPayloadError = null;

    public String getStringCap(String string) {
        String cap = string.substring(0, 1).toUpperCase() + string.substring(1);
        return cap;
    }

    public void makeFullScreen(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_FULLSCREEN |
                                              WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        int visibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT < 19) { //View.SYSTEM_UI_FLAG_IMMERSIVE is only on API 19+
            visibility |= View.SYSTEM_UI_FLAG_IMMERSIVE;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(visibility);
    }
}

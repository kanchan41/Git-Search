package com.kanchan.GitSearch.ScreenFirst;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kanchan.GitSearch.Adapter.LanguageAdapter;
import com.kanchan.GitSearch.Adapter.SearchListAdapter;
import com.kanchan.GitSearch.Config.APIClient;
import com.kanchan.GitSearch.Config.APIInterface;
import com.kanchan.GitSearch.Config.Helper;
import com.kanchan.GitSearch.Payload.Item;
import com.kanchan.GitSearch.Payload.LanguageJson;
import com.kanchan.GitSearch.Payload.LanguagePayload;
import com.kanchan.GitSearch.Payload.SearchItem;
import com.kanchan.GitSearch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity implements View.OnClickListener {
    ProgressDialog progressDialog;
    APIInterface apiInterface;
    Helper helper = new Helper();
    Call<SearchItem> call;
    SearchView searchView;
    ImageView emptyView, errorView, filter;
    private SearchListAdapter searchListAdapter;
    RecyclerView recyclerView, recyclerViewLanguage;
    TextView lLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper.makeFullScreen(MainActivity.this);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {

        searchView = findViewById(R.id.search);
        emptyView = findViewById(R.id.empty);
        errorView = findViewById(R.id.error);
        recyclerView = findViewById(R.id.rc);
        filter = findViewById(R.id.filter);

        searchView.setActivated(true);
        searchView.setQueryHint("Type your keyword here");
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();
        apiInterface = APIClient.getAPI().create(APIInterface.class);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                initList(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                initList(s);
                return false;
            }
        });
        filter.setOnClickListener(this);
    }

    public void initList(String keyword) {
        if (keyword.length() == 0) {
            recyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            return;
        }
        call = apiInterface.getSearchList(keyword, "10", "stars", "desc");
        call.enqueue(new Callback<SearchItem>() {
            @Override
            public void onResponse(Call<SearchItem> call, Response<SearchItem> response) {
                if (response.isSuccessful()) {
                    Helper.searchPayloadSuccess = response.body();
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    errorView.setVisibility(View.GONE);
                    isSuccessful();
                    progressDialog.dismiss();
                } else {
                    Helper.searchPayloadError = response.errorBody();
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SearchItem> call, Throwable t) {
                call.cancel();
            }
        });
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage(getResources().getString(R.string.pleasewait));
        this.progressDialog.show();
    }

    public void initLanguageList(String keyword) {
        if (keyword.length() == 0) {
            recyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            return;
        }
        call = apiInterface.getLanguageList("language:"+keyword, "10", "stars", "desc");
        call.enqueue(new Callback<SearchItem>() {
            @Override
            public void onResponse(Call<SearchItem> call, Response<SearchItem> response) {
                if (response.isSuccessful()) {
                    Helper.searchPayloadSuccess = response.body();
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    errorView.setVisibility(View.GONE);
                    isSuccessful();
                    progressDialog.dismiss();
                } else {
                    Helper.searchPayloadError = response.errorBody();
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SearchItem> call, Throwable t) {
                call.cancel();
            }
        });
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage(getResources().getString(R.string.pleasewait));
        this.progressDialog.show();
    }
    public void isSuccessful() {
        SearchItem searchItem = Helper.searchPayloadSuccess;
        int totalCount = searchItem.getTotalCount();
        if (totalCount > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
            List<Item> datumList = searchItem.getItems();
//            for (Item datum : datumList) {
            searchListAdapter = new SearchListAdapter(MainActivity.this, datumList);
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManagaer);
            recyclerView.setAdapter(searchListAdapter);
//            }
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        call.cancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = ((Activity) this).getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.searchfilter_dialog,
                        null);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                dialog.setView(dialogLayout, 0, 0, 0, 0);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.gravity = Gravity.BOTTOM;
                dialog.getWindow().setLayout(50, 50);
                lLanguage = (TextView) dialogLayout.findViewById(R.id.Languages);
                recyclerViewLanguage = (RecyclerView) dialogLayout.findViewById(R.id.lanrc);
                final ImageView close = (ImageView) dialogLayout.findViewById(R.id.cancel);
                try {
                    JSONObject jsonObject = new JSONObject(LanguageJson.LanguageJsonFake);
                    JSONArray payload = jsonObject.optJSONArray("Language");
                    List<LanguagePayload> languagePayloadList = new ArrayList<>();
                    for (int i = 0; i < payload.length(); i++) {
                        JSONObject jsonChildNode = payload.getJSONObject(i);
                        LanguagePayload items = new LanguagePayload();
                        items.setLanguageName(jsonChildNode.optString("lan"));
                        languagePayloadList.add(items);
                        LanguageAdapter languageAdapter = new LanguageAdapter(MainActivity.this, languagePayloadList, dialog);
                        LinearLayoutManager horizontalLayoutManagaer
                                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recyclerViewLanguage.setLayoutManager(horizontalLayoutManagaer);
                        recyclerViewLanguage.setAdapter(languageAdapter);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                builder.setView(dialogLayout);
                dialog.show();
                if (dialog.isShowing()) {
                    dialog.setCancelable(false);
                }

        }
    }
}
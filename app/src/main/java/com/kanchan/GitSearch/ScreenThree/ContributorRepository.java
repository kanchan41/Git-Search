package com.kanchan.GitSearch.ScreenThree;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kanchan.GitSearch.Adapter.ContributorAdapter;
import com.kanchan.GitSearch.Adapter.ContributorRepositoryAdapter;
import com.kanchan.GitSearch.Config.APIClient;
import com.kanchan.GitSearch.Config.APIInterface;
import com.kanchan.GitSearch.Config.Helper;
import com.kanchan.GitSearch.Payload.ContributorItem;
import com.kanchan.GitSearch.Payload.Item;
import com.kanchan.GitSearch.Payload.SearchItem;
import com.kanchan.GitSearch.R;
import com.kanchan.GitSearch.ScreenTwo.RepositoryDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContributorRepository extends Activity {

    TextView Name;
    ImageView Avatar, errorView;
    APIInterface apiInterface;
    ProgressDialog progressDialog;
    ContributorRepositoryAdapter contributorAdapter;
    String _userName, _avtarUrl;
    Helper helper = new Helper();
    Call<List<Item>> call;
    List<Item> contributorItems = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper.makeFullScreen(this);
        setContentView(R.layout.activity_contributor_repository);
        recyclerView = (findViewById(R.id.rc));
        Avatar = (findViewById(R.id.avatar));
        Name = (findViewById(R.id.avatarName));
        errorView = (findViewById(R.id.error));
        apiInterface = APIClient.getAPI().create(APIInterface.class);
        initList();
    }

    public void initList() {
        Intent i = getIntent();
        _userName = i.getStringExtra("_name");
        _avtarUrl = i.getStringExtra("_avtarurl");

        Name.setText(_userName);
        Picasso.with(this)
                .load(_avtarUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_warning_black_24dp)
                .into(Avatar);
        getContributorList(_userName);
    }

    public void getContributorList(String login) {
        call = apiInterface.getContributorRepositoryList(login, "10");
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    Helper.items = response.body();
                    for (Item item : response.body()) {
                        contributorItems.add(item);
                        contributorAdapter = new ContributorRepositoryAdapter(ContributorRepository.this, contributorItems);
                        LinearLayoutManager horizontalLayoutManagaer
                                = new LinearLayoutManager(ContributorRepository.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(horizontalLayoutManagaer);
                        recyclerView.setAdapter(contributorAdapter);
                        progressDialog.dismiss();
                    }
                    progressDialog.dismiss();
                    recyclerView.setVisibility(View.VISIBLE);
                    errorView.setVisibility(View.GONE);
                } else {
                    Helper.searchPayloadError = response.errorBody();
                    recyclerView.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
            }
        });
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage(getResources().getString(R.string.pleasewait));
        this.progressDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        call.cancel();
    }
}

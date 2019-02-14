package com.kanchan.GitSearch.ScreenThree;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
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
import com.kanchan.GitSearch.ScreenTwo.ProjectLink;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository2Detail extends Activity implements View.OnClickListener {
    TextView Fork, Star, Watcher, Name, Description, LastUpdatedAt;
    ImageView Avatar, errorView;
    Button projectLink;
    APIInterface apiInterface;
    ProgressDialog progressDialog;
    ContributorAdapter contributorAdapter;
    String _id;
    Helper helper = new Helper();
    Call<List<ContributorItem>> call;
    List<ContributorItem> contributorItems = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper.makeFullScreen(Repository2Detail.this);
        setContentView(R.layout.activity_repository_detail);
        initview();
    }

    public void initview() {
        Fork = (findViewById(R.id.fork));
        Star = (findViewById(R.id.star));
        Watcher = (findViewById(R.id.watcher));
        Name = (findViewById(R.id.Name));
        Description = (findViewById(R.id.des));
        Avatar = (findViewById(R.id.avatar));
        LastUpdatedAt = (findViewById(R.id.updatedat));
        recyclerView = (findViewById(R.id.rc));
        errorView = (findViewById(R.id.error));
        projectLink = (findViewById(R.id.linkb));
        projectLink.setOnClickListener(this);
        apiInterface = APIClient.getAPI().create(APIInterface.class);
        initList();
    }

    public void initList() {
        Intent i = getIntent();
        _id = i.getStringExtra("_id");
        List<Item> item = Helper.items;
        for (Item data : item) {
            if (_id.equals(data.getId() + "")) {
                Name.setText(data.getOwner().getLogin());
                Star.setText(data.getStargazersCount() + "");
                Fork.setText(data.getForksCount() + "");
                Watcher.setText(data.getWatchersCount() + "");
                Description.setText(data.getDescription());
                Description.setMovementMethod(new ScrollingMovementMethod());
                Picasso.with(this)
                        .load(data.getOwner().getAvatarUrl())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.ic_warning_black_24dp)
                        .into(Avatar);
                getContirbutorList(data.getOwner().getLogin(), data.getName());
            }

        }


    }

    public void getContirbutorList(String login, String name) {
        call = apiInterface.getContributorList(login, name);
        call.enqueue(new Callback<List<ContributorItem>>() {
            @Override
            public void onResponse(Call<List<ContributorItem>> call, Response<List<ContributorItem>> response) {
                if (response.isSuccessful()) {
                    for (ContributorItem item : response.body()) {
                        contributorItems.add(item);
                        contributorAdapter = new ContributorAdapter(Repository2Detail.this, contributorItems);
                        LinearLayoutManager horizontalLayoutManagaer
                                = new LinearLayoutManager(Repository2Detail.this, LinearLayoutManager.HORIZONTAL, false);
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
            public void onFailure(Call<List<ContributorItem>> call, Throwable t) {
            }
        });
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage(getResources().getString(R.string.pleasewait));
        this.progressDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call.isExecuted())
            call.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linkb:
                projectLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Repository2Detail.this, ProjectLink2.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("_id", _id);
                        startActivity(i);
                    }
                });
        }
    }
}
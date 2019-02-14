package com.kanchan.GitSearch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kanchan.GitSearch.Payload.ContributorItem;
import com.kanchan.GitSearch.Payload.Item;
import com.kanchan.GitSearch.R;
import com.kanchan.GitSearch.ScreenFirst.MainActivity;
import com.kanchan.GitSearch.ScreenThree.Repository2Detail;
import com.kanchan.GitSearch.ScreenTwo.RepositoryDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContributorRepositoryAdapter extends RecyclerView.Adapter<ContributorRepositoryAdapter.MyViewHolder> {
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Watcher, Fork, Language, Description;

        MyViewHolder(View view) {
            super(view);
            Name = view.findViewById(R.id.Name);
            Watcher = view.findViewById(R.id.star);
            Fork = view.findViewById(R.id.fork);
            Language = view.findViewById(R.id.language);
            Description = view.findViewById(R.id.Description);
        }
    }

    public Context context;
    public List<Item> contributorList = new ArrayList<>();

    public ContributorRepositoryAdapter(Context context, List<Item> contributorList) {
        this.context = context;
        this.contributorList = contributorList;
        LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.contributor_repo_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.Name.setText(contributorList.get(position).getName());
        holder.Watcher.setText(contributorList.get(position).getStargazersCount() + "");
        holder.Language.setText(contributorList.get(position).getLanguage());
        holder.Fork.setText(contributorList.get(position).getForksCount() + "");
        holder.Description.setText(contributorList.get(position).getDescription());
        getposition(holder, position);
    }

    @Override
    public int getItemCount() {
        return contributorList == null ? 0 : contributorList.size();
    }

    private void getposition(ContributorRepositoryAdapter.MyViewHolder v, final int position) {
        v.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Repository2Detail.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("_id", contributorList.get(position).getId() + "");
                context.startActivity(i);
            }
        });

    }
}
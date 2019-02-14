package com.kanchan.GitSearch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kanchan.GitSearch.Payload.ContributorItem;
import com.kanchan.GitSearch.Payload.Item;
import com.kanchan.GitSearch.Payload.LanguagePayload;
import com.kanchan.GitSearch.R;
import com.kanchan.GitSearch.ScreenFirst.MainActivity;
import com.kanchan.GitSearch.ScreenThree.ContributorRepository;
import com.kanchan.GitSearch.ScreenTwo.RepositoryDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContributorAdapter extends RecyclerView.Adapter<ContributorAdapter.MyViewHolder> {

    static class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView avtarName;
        ImageView avtarUrl;

        MyViewHolder(View view) {
            super(view);
            avtarName = view.findViewById(R.id.avatarName);
            avtarUrl = view.findViewById(R.id.avatar);


        }


    }

    public Context context;
    public List<ContributorItem> contributorList = new ArrayList<>();

    public ContributorAdapter(Context context, List<ContributorItem> contributorList) {
        this.context = context;
        this.contributorList = contributorList;
        LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.contributor_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.avtarName.setText(contributorList.get(position).getLogin());
        Picasso.with(context)
                .load(contributorList.get(position).getAvatar_url())
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_warning_black_24dp)
                .into(holder.avtarUrl);
        getposition(holder, position);

    }

    @Override
    public int getItemCount() {
        return contributorList == null ? 0 : contributorList.size();
    }

    private void getposition(MyViewHolder v, final int position) {
        v.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ContributorRepository.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("_name", contributorList.get(position).getLogin());
                i.putExtra("_avtarurl", contributorList.get(position).getAvatar_url());

                context.startActivity(i);
            }
        });

    }
}
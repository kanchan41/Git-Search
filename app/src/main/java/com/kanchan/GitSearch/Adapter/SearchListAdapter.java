package com.kanchan.GitSearch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kanchan.GitSearch.Payload.Item;
import com.kanchan.GitSearch.R;
import com.kanchan.GitSearch.ScreenTwo.RepositoryDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.MyViewHolder> {

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Avatar;
        TextView Star, Fork, Author, Name, Language, Description;

        MyViewHolder(View view) {
            super(view);
            Avatar = view.findViewById(R.id.avatar);
            Star = view.findViewById(R.id.star);
            Name = view.findViewById(R.id.Name);
            Fork = view.findViewById(R.id.fork);
            Author = view.findViewById(R.id.Author);
            Language = view.findViewById(R.id.language);
            Description = view.findViewById(R.id.Description);

        }
    }

    private Context context;
    private List<Item> SearchList;

    public SearchListAdapter(Context context, List<Item> selectedList) {
        this.context = context;
        SearchList = new ArrayList<>();
        this.SearchList = selectedList;
        LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.Name.setText(SearchList.get(position).getName());
        holder.Author.setText(SearchList.get(position).getOwner().getLogin().toUpperCase());
        holder.Star.setText(SearchList.get(position).getWatchersCount().toString());
        holder.Fork.setText(SearchList.get(position).getForks().toString());
        holder.Language.setText(SearchList.get(position).getLanguage());
        holder.Description.setText(SearchList.get(position).getDescription());

        Picasso.with(context)
                .load(SearchList.get(position).getOwner().getAvatarUrl())
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_warning_black_24dp)
                .into(holder.Avatar);
        getposition(holder, position);
    }

    @Override
    public int getItemCount() {
        return SearchList == null ? 0 : SearchList.size();
    }

    private void getposition(MyViewHolder v, final int position) {
        v.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RepositoryDetail.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("_id", SearchList.get(position).getId() + "");
                context.startActivity(i);
            }
        });

    }
}
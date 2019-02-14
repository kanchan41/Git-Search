package com.kanchan.GitSearch.Adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kanchan.GitSearch.Payload.LanguagePayload;
import com.kanchan.GitSearch.R;
import com.kanchan.GitSearch.ScreenFirst.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyViewHolder> {

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Language;

        MyViewHolder(View view) {
            super(view);
            Language = view.findViewById(R.id.language);


        }
    }

    public Context context;
    public List<LanguagePayload> languagePayloads = new ArrayList<>();
    public AlertDialog alertDialog;
    public LanguageAdapter(Context context, List<LanguagePayload> languagePayloads, AlertDialog dialog) {
        this.context = context;
        this.alertDialog = dialog;
        this.languagePayloads = languagePayloads;
        LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.language_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.Language.setText(languagePayloads.get(position).getLanguageName());
        getposition(holder, position);

    }

    @Override
    public int getItemCount() {
        return languagePayloads == null ? 0 : languagePayloads.size();
    }

    private void getposition(MyViewHolder v, final int position) {
        v.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if(context instanceof MainActivity){
                    ((MainActivity)context).initLanguageList(languagePayloads.get(position).getLanguageName());
                }
            }
        });

    }
}
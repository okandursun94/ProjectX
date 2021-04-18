package com.example.project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Models.Episode;
import com.example.project.R;
import java.util.ArrayList;


public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ViewHolder> {

    private ArrayList<Episode> episodeArrayList;
    private LayoutInflater mInflater;
    Context context;
    Episode lastEpisode;
    ViewHolder holder2;

    // data is passed into the constructor
    public EpisodesAdapter(Context context, ArrayList<Episode> data) {
        this.mInflater = LayoutInflater.from(context);
        this.episodeArrayList = data;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.episodes_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_episodes.setText(episodeArrayList.get(position).getEpisode());
    }

    @Override
    public int getItemCount() {
        return episodeArrayList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView tv_episodes;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            tv_episodes = itemView.findViewById(R.id.tv_episodes);
            linearLayout = itemView.findViewById(R.id.linear_layout_adapter);
        }
    }
}
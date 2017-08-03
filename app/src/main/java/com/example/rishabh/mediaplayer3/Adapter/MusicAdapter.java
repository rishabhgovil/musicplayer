package com.example.rishabh.mediaplayer3.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rishabh.mediaplayer3.Interface.OnItemClickListener;
import com.example.rishabh.mediaplayer3.POJO.music;
import com.example.rishabh.mediaplayer3.R;

import java.util.ArrayList;

/**
 * Created by RISHABH on 23-07-2017.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

 public  ArrayList<music> musicArrayList;

    Context context;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public MusicAdapter(ArrayList<music> musicArrayList, Context context) {
        this.musicArrayList = musicArrayList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview=li.inflate(R.layout.music,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final music thismusic = musicArrayList.get(position);
        holder.artist.setText(thismusic.getCurrentartist());
        holder.title.setText(thismusic.getCurrenttitle());
        holder.thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,artist;
        View thisView;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            artist = (TextView)itemView.findViewById(R.id.artist);

            thisView = itemView;
        }
    }
}


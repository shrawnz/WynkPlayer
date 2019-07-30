package com.example.wynkplayer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SongAdapter extends BaseAdapter {

    private ArrayList<Songs> songs;
    private LayoutInflater songInflator;
    private onItemClickListener onItemClickListener;

    public SongAdapter(ArrayList<Songs> songs, Context c){
        this.songs = songs;
        this.songInflator= LayoutInflater.from(c);
    }

    public interface onItemClickListener{
        void onItemClick(TextView songView, View view, Songs song, int i);
    }

    public void setOnItemClickListener(final onItemClickListener mItemClickListener){
        this.onItemClickListener = mItemClickListener;
    }

    @Override
    public int getCount() {
        return this.songs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LinearLayout songListLayout = (LinearLayout)songInflator.inflate(R.layout.songs, viewGroup, false);
        final TextView songName = songListLayout.findViewById(R.id.song_title);
        TextView artistName = songListLayout.findViewById(R.id.song_artist);

        final Songs currentSong = this.songs.get(i);
        songName.setText(currentSong.getSongTitle());
        artistName.setText(currentSong.getSongArtist());

        songName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(songName, view, currentSong, i);
                }
            }
        });

        songListLayout.setTag(i);

        return songListLayout;

    }
}

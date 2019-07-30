package com.example.wynkplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Songs> songList;
    private ListView songListView;

    public void getSongs(){
        Log.d("msg","Inside get songs");
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if(songCursor.moveToFirst()){
            Log.d("val", "true");
        }
        else {
            Log.d("val", "false");
        }
        if(songCursor != null && songCursor.moveToFirst())
        {
            Log.d("log","hello");
            int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
            int songUrl = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            Log.d("songid", ""+ songId);
            do {
                long currentId = songCursor.getLong(songId);
                String currentTitle = songCursor.getString(songTitle);
                String currentUrl = songCursor.getString(songUrl);
                songList.add(new Songs(currentId, currentTitle, currentUrl));
            } while(songCursor.moveToNext());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songList = new ArrayList<Songs>();

        getSongs();
        Log.d("List size", "Size: " + songList.size());
//        String songName = arrayList.get(0).getSongTitle();
//        TextView tv = findViewById(R.id.songText);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
//                startActivity(intent);
//            }
//        });
//        tv.setText(songName);
        songListView = findViewById(R.id.song_list);
        SongAdapter songAdapter = new SongAdapter(songList, this);
        songListView.setAdapter(songAdapter);

        songAdapter.setOnItemClickListener(new SongAdapter.onItemClickListener() {
            @Override
            public void onItemClick(TextView songView, View view, Songs song, int i) {
                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                intent.putExtra("name", song.getSongTitle());
                intent.putExtra("url", song.getSongUrl());
                startActivity(intent);
            }
        });
    }
}

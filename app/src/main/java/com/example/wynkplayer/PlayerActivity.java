package com.example.wynkplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class PlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Button playPauseBtn;
    private Handler songHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        final String songUrl = intent.getStringExtra("url");
        String songName = intent.getStringExtra("name");

//        mediaPlayer = new MediaPlayer();

        TextView tv = findViewById(R.id.title);
        tv.setText(songName);

        seekBar = findViewById(R.id.seekbar);

        playPauseBtn = findViewById(R.id.play_pause_btn);
        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playPauseBtn.getText().equals("Play")){
                    playMedia(songUrl);
                    playPauseBtn.setText("Pause");
                }
                else{
                        mediaPlayer.pause();
                        playPauseBtn.setText("Play");
                }
            }
        });


        Thread seekbarThread = new SeekbarRunnable();
        seekbarThread.start();

    }

    private void playMedia(final String songUrl){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if(mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(songUrl);
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mediaPlayer.start();
                                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                seekBar.setMax(mediaPlayer.getDuration());
                            }
                        });
                    }
                    else {
                        mediaPlayer.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        songHandler.postDelayed(runnable, 100);
    }

    public class SeekbarRunnable extends Thread{

            @Override
            public void run () {
                while(true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (mediaPlayer != null) {
                        seekBar.post(new Runnable() {
                            @Override
                            public void run() {
                                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                            }
                        });

                    }
                }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}

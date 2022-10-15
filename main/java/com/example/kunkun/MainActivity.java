package com.example.kunkun;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MusicService.mediaPlayer = MediaPlayer.create(this, R.raw.jj_jntm);
        MusicService.mediaPlayer.setLooping(true);
        startService(new Intent(MainActivity.this, MusicService.class));

        ImageButton imageButton = findViewById(R.id.imbtn_musicStop);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if (MusicService.isplaying) {
                    stopService(new Intent(MainActivity.this, MusicService.class));
                    imageButton.setBackground(getResources().getDrawable(R.drawable.music_stop));
                } else {
                    startService(new Intent(MainActivity.this, MusicService.class));
                    imageButton.setBackground(getResources().getDrawable(R.drawable.music_play));
                }
            }
        });

    }


    public void musicKun(View view) {
        stopService(new Intent(MainActivity.this, MusicService.class));
        Intent intent = new Intent(MainActivity.this, MusicActivity.class);
        startActivity(intent);
    }

    public void startGame(View view) {
        stopService(new Intent(MainActivity.this, MusicService.class));
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.niganma);
        mediaPlayer.start();
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }
}
package com.example.kunkun;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {
    private static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.jj_jntm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }


    public void musicKunkun(View view) {
        mediaPlayer.stop();
        Intent intent = new Intent(MainActivity.this, MusicActivity.class);
        startActivity(intent);
    }

    public void startGame(View view) {
        mediaPlayer.stop();
        MediaPlayer mediaPlayer2 = MediaPlayer.create(this, R.raw.niganma);
        mediaPlayer2.start();
        Intent intent = new Intent(MainActivity.this, GameRule.class);
        startActivity(intent);
    }
}
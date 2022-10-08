package com.example.kunkun;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class SuccessFindKunkun extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_find_kunkun);

    }

    public void reStart(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.lblhnkg);
        mediaPlayer.start();
        Intent intent = new Intent(SuccessFindKunkun.this, GameRule.class);
        startActivity(intent);
    }
}
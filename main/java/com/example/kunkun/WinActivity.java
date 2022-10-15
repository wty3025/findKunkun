package com.example.kunkun;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

public class WinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        SharedPreferences spWatch = getSharedPreferences("mrsoft", MODE_PRIVATE);
        int watchNum = spWatch.getInt("watchNum", 0);

        SharedPreferences spListen = getSharedPreferences("mrsoft", MODE_PRIVATE);
        int listenNum = spListen.getInt("listenNum", 0);

        TextView textView = findViewById(R.id.watchNum);
        textView.setText("共播放b\uD83D\uDC14m " + listenNum + " 首\n总共观看视频 " + watchNum + " 次");


        VideoView videoView = findViewById(R.id.doubleKun);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.double_kun);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
    }

    public void backToHome(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.lblhnkg);
        mediaPlayer.start();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void kunAdvert(View view) {
        Intent intent = new Intent(this, AdvertActivity.class);
        startActivity(intent);
    }
}
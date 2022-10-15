package com.example.kunkun;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class AdvertActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);

        VideoView videoView = findViewById(R.id.videoAdvert);

        // 获取视频url真实链接并播放视频
        try {
            // 代码的主线程中去访问网络，配置线程监控策略
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectNetwork()
                    .penaltyLog()
                    .build());


            URL url = new URL("https://service-mdz0lmme-1308432187.gz.apigw.tencentcs.com/release/kunkun");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            InputStream in = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String uri = reader.readLine();
            System.out.println(uri);
            videoView.setVideoURI(Uri.parse(uri));
            videoView.start();

            SharedPreferences sp = getSharedPreferences("mrsoft", MODE_PRIVATE);
            int watchNum = sp.getInt("watchNum", 0);
            watchNum += 1;

            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("watchNum", watchNum);
            editor.commit();

            System.out.println(watchNum);

        } catch (Exception e) {
            e.printStackTrace();
        }


        // 监听播放完成的事件
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Intent intent = new Intent(AdvertActivity.this, WinActivity.class);
                startActivity(intent);
            }
        });


        // 调整透明度
        Button btn_replayAdvert = findViewById(R.id.btn_nextAdvert);
        btn_replayAdvert.getBackground().setAlpha(150);
    }


    public void jumpAdvert(View view) {
        Intent intent = new Intent(this, WinActivity.class);
        startActivity(intent);
    }

    public void nextAdvert(View view) {
        Intent intent = new Intent(this, AdvertActivity.class);
        startActivity(intent);
    }
}


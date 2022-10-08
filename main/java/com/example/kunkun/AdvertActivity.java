package com.example.kunkun;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

        VideoView videoView = findViewById(R.id.videoNiganma);


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
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 监听播放完成的事件
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Intent intent = new Intent(AdvertActivity.this, SuccessFindKunkun.class);
                startActivity(intent);
            }
        });


        // 调整透明度
        Button btn_pass = findViewById(R.id.pass2);
        btn_pass.getBackground().setAlpha(150);

    }


    public void jump(View view) {
        Intent intent = new Intent(AdvertActivity.this, SuccessFindKunkun.class);
        startActivity(intent);
    }

    public void next(View view) {
        Intent intent = new Intent(AdvertActivity.this, AdvertActivity.class);
        startActivity(intent);
    }
}


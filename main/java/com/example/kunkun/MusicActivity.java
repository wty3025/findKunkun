package com.example.kunkun;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MusicActivity extends Activity {

    List<MediaPlayer> playingList = new ArrayList<>();  // 播放过的音频放入列表，为了一键停止播放功能
    private static MediaPlayer mediaPlayer;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);



//        Button闪烁代码（暂时不需要）
//        Animation alphaAnimation = new AlphaAnimation(1, 0);
//        alphaAnimation.setDuration(1000);
//        alphaAnimation.setInterpolator(new LinearInterpolator());
//        alphaAnimation.setRepeatCount(Animation.INFINITE);
//        alphaAnimation.setRepeatMode(Animation.REVERSE);


        // 第一批生成12个Button，配置相应参数，点击播放对应音频
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int j = -1;
        RelativeLayout relativeLayout = findViewById(R.id.gameMusic);

        Button[] btn1 = new Button[12];
        String[] button_text = {"1.0倍速", "1.1倍速", "1.2倍速", "1.3倍速", "1.4倍速", "1.5倍速", "1.6倍速", "1.7倍速", "1.8倍速", "1.9倍速", "2.0倍速", "找到坤坤"};

        for (int i = 0; i < 12; i++) {
            btn1[i] = new Button(this);
            btn1[i].setId(i);
            btn1[i].setText(button_text[i]);
            btn1[i].setBackground(getResources().getDrawable(R.drawable.shape_music));

            RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams((width - 50) / 4, 150);

            if (i % 4 == 0)
                j++;

            btParams.leftMargin = 10 + ((width - 50) / 4 + 10) * (i % 4);   // 横坐标定位
            btParams.topMargin = 20 + 180 * j;   // 纵坐标定位

            relativeLayout.addView(btn1[i], btParams);   // 将按钮放入layout组件
        }

        List music = new ArrayList();

        try {
            // 代码的主线程中去访问网络，配置线程监控策略
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectNetwork()
                    .penaltyLog()
                    .build());


            // 获取kunkun_music返回的数据，并插入Button
            URL url = new URL("https://share-1309976108.cos.ap-guangzhou.myqcloud.com/kunkun_music.json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            InputStream in = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));


            int ch;
            StringBuilder result = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                result.append((char) ch);
            }

            String jsonStr = result.toString();     // jsonStr为返回的json
            JSONObject json = JSONObject.parseObject(jsonStr);
            int musicNum = json.getIntValue("fileNum");     // 音频数量

            TextView textView = findViewById(R.id.jiBox);
            textView.setText("坤坤音乐" + musicNum + "首（持续更新中）");

            JSONArray fileList = json.getJSONObject("data").getJSONArray("fileList");
            System.out.println(fileList);

            for (int i = 0; i < musicNum; i++) {
                JSONObject musicContent = fileList.getJSONObject(i);
                String musicName = musicContent.getString("filename");
                music.add(musicName);   // 音频文件名music列表
//                System.out.println(musicName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        System.out.println(music);

        // 第二批Button控件，生成的Button数量由返回的 musicNum 数值决定（凑齐4的倍数）
        relativeLayout = (RelativeLayout) findViewById(R.id.gameMusic2);

        j = -1;

        Button[] btn2 = new Button[music.size()];

        for (int i = 0; i < music.size(); i++) {
            btn2[i] = new Button(this);
            btn2[i].setId(i);
            btn2[i].setTextSize(10);
            btn2[i].setBackground(getResources().getDrawable(R.drawable.shape_music));


            RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams((width - 50) / 4, 150);

            if (i % 4 == 0)
                j++;

            btParams.leftMargin = 10 + ((width - 50) / 4 + 10) * (i % 4);   // 横坐标定位
            btParams.topMargin = 20 + 180 * j;   // 纵坐标定位

            relativeLayout.addView(btn2[i], btParams);   // 将按钮放入layout组件
        }

        for (int i = 0; i <= btn1.length - 1; i++) {
            int finalI = i;

            // 第一批Button插入本地音频
            int[] game_music = {R.raw.s0, R.raw.s1, R.raw.s2, R.raw.s3, R.raw.s4, R.raw.s5, R.raw.s6, R.raw.s7, R.raw.s8, R.raw.s9, R.raw.s10, R.raw.a};
            btn1[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int play_music = game_music[finalI];

                    mediaPlayer = MediaPlayer.create(MusicActivity.this, play_music);
                    mediaPlayer.start();
                    playingList.add(mediaPlayer);
                }
            });
        }


        // 第二批Button用直链音频，json里的filename就是腾讯云COS的key
        for (int i = 0; i <= btn2.length - 1; i++) {
            int finalI = i;

            btn2[i].setText(((String) music.get(finalI)).replace(".mp3", ""));
            btn2[i].setAllCaps(false);
            btn2[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("https://share-1309976108.cos.ap-guangzhou.myqcloud.com/kunkun_music/" + (String) music.get(finalI));

                    mediaPlayer = MediaPlayer.create(MusicActivity.this, uri);
                    mediaPlayer.start();
                    playingList.add(mediaPlayer);


//                    Button停止闪烁代码（暂时不需要）
//                    view.startAnimation(alphaAnimation);
//
//                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mediaPlayer) {
//                            view.clearAnimation();
//                        }
//                    });
                }
            });
        }

    }



    // 停止播放列表里所有音乐，并重置列表
    public void stopMusic(View view) {
        for (int i = playingList.size() - 1; i >= 0; i--) {
            if (playingList.get(i).isPlaying()) {
                playingList.get(i).stop();
            }
            playingList.remove(i);
        }
    }
    

}

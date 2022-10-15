package com.example.kunkun;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


public class GameActivity extends Activity implements View.OnTouchListener {

    private TextView touchPostiton;
    public static int realX, realY;
    private SoundPool sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getPosition();
    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void reStart(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void playAdvert(View view) {
        Intent intent = new Intent(this, AdvertActivity.class);
        startActivity(intent);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void getPosition() {
        // 生成坐标
        realX = new Random().nextInt(980);
        realY = (new Random().nextInt(1820 - 250) + 250);

        touchPostiton = findViewById(R.id.touchPosition);

        RelativeLayout touch = findViewById(R.id.touchScreen);
        touch.setOnTouchListener(this);
    }



    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (sp != null) {
            sp.release();
        }


        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(1);
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attrBuilder.build());
            sp = builder.build();
        } else {
            sp = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
        }

        // 获取X，Y坐标并设置TextView属性
        event.getAction();
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        touchPostiton.setPadding(realX, realY, 0, 0);


        // 计算点击坐标与目标坐标的距离
        int distance = (int) Math.sqrt(Math.pow(realX - x, 2) + Math.pow(realY - y, 2));
        System.out.println("实际坐标： (" + realX + "," + realY + ")");
        System.out.println("点击坐标： (" + x + "," +  y + ")");
        System.out.println("两点距离：" + distance);


        // 图片位置
        ImageView winGame = findViewById(R.id.image_cxk);
        winGame.setX(realX - 100);
        winGame.setY(realY - 100);


        // 确定放第几个音频，距离小于等于100通关， 大于1000的数放同一个音频
        if (distance > 1100) {
            final int soundId0 = sp.load(this, R.raw.s0, 1);
            sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    sp.play(soundId0,1,1,1,0,1);
                }
            });
            System.out.println("播放第10个音频");

        }
        else if (distance <= 100) {
            // 通关，坤坤图片和你干嘛
            int[] picture = {
                    R.drawable.niganma,
                    R.drawable.tieshankao,
                    R.drawable.wudangchuanren,
                    R.drawable.wudangchuanren2,
            };

            int[] music_ganma = {
                    R.raw.a,
                    R.raw.niganma
            };
            int index = new Random().nextInt(5);
            int index2 = new Random().nextInt(2);

            MediaPlayer mediaPlayer2 = MediaPlayer.create(this,music_ganma[index2]);
            mediaPlayer2.start();
            winGame.setImageResource(picture[index]);
            System.out.println("通关");


            // 延时跳转
            try {
                Thread.sleep(2000);
                Intent intent = new Intent(this, WinActivity.class);
                startActivity(intent);
            } catch (InterruptedException ex) {
                System.out.println("出现异常");
            }

        }
        else {
            int[] music = {
                    R.raw.s10,
                    R.raw.s9,
                    R.raw.s8,
                    R.raw.s7,
                    R.raw.s6,
                    R.raw.s5,
                    R.raw.s4,
                    R.raw.s3,
                    R.raw.s2,
                    R.raw.s1,
            };
            System.out.println("播放第" + (distance / 100 - 1) + "个音频");

            final int soundId0 = sp.load(this, music[distance / 100 - 1], 1);
            sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    sp.play(soundId0,1,1,1,0,1);
                }
            });

        }

        return false;
    }

}


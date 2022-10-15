package com.example.kunkun;


import android.content.Intent;
import android.app.Service;
import android.media.MediaPlayer;
import android.os.IBinder;


public class MusicService extends Service {
    protected static MediaPlayer mediaPlayer;
    static boolean isplaying;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        mediaPlayer.start();
        isplaying = true;
    }

    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
        isplaying = false;
    }
}
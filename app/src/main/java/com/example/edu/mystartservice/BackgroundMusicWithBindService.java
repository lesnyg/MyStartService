package com.example.edu.mystartservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BackgroundMusicWithBindService extends Service {

    private static final String TAG = BackgroundMusicWithBindService.class.getSimpleName();

//    private final IBinder myBinder = new MyBinder();
    private MediaPlayer mediaPlayer;


    @Override
    public IBinder onBind(Intent arg0) {
        Log.d("service","onBind");
        return new MyBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);

    }

    public class MyBinder extends Binder {
        BackgroundMusicWithBindService getService() {
            return BackgroundMusicWithBindService.this;
        }
    }

    /**
     * 음악재생중인지 아닌지 반환한다
     *
     * @return 음악재생중인 경우는 true. 나머지는 false.
     */
    public boolean isPlaying() {
        boolean isPlaying = false;
        if (mediaPlayer != null) {
            isPlaying = mediaPlayer.isPlaying();
        }
        return isPlaying;
    }

    /**
     * 음악을 재생한다
     */
    public void play() {
        Log.d(TAG, "play");
        mediaPlayer = MediaPlayer.create(this, R.raw.bensound_clearday);
        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.setVolume(1, 1);
        mediaPlayer.start();
    }

    /**
     * 음악을 정지한다. 모두 정지 중인 경우는 아무것도 하지 않는다
     */
    public void stop() {
        Log.d(TAG, "stop");
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }
}

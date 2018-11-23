package com.example.edu.mystartservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class BindServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private Boolean mIsPlaying;
    private BackgroundMusicWithBindService myServiceBinder;

    // 서비스와의 연결 콜백
    private ServiceConnection myConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder binder) {
            myServiceBinder = ((BackgroundMusicWithBindService.MyBinder) binder).getService();
            Log.e("ServiceConnection","connected");
//            updateButtonEnabled();
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.e("ServiceConnection", "disconnected");
            myServiceBinder = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_service);

        View btn_play = findViewById(R.id.btn_play);
        View btn_stop = findViewById(R.id.btn_stop);

        btn_play.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = null;
        Class<?> cls = null;
        switch (id) {
            case R.id.btn_play:
                if (myServiceBinder != null) {
                    myServiceBinder.play();
                }
                break;
            case R.id.btn_stop:
                if (myServiceBinder != null) {
                    myServiceBinder.stop();
                }
                break;
        }
//        intent = new Intent(this, cls);
//        startActivity(intent);
    }

    private void doBindService() {
        Intent intent = new Intent(this, BackgroundMusicWithBindService.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    private void doUnBindService() {
        if (myServiceBinder != null) {
            mIsPlaying = myServiceBinder.isPlaying();
            unbindService(myConnection);
//            myServiceBinder = null;
        }
    }

    private void doReBindService() {
        if (myServiceBinder != null) {
            mIsPlaying = myServiceBinder.isPlaying();
            unbindService(myConnection);
//            myServiceBinder = null;
        }
    }

    @Override
    protected void onResume() {
        Log.d("activity", "onResume");
        super.onResume();
        if (myServiceBinder == null) {
            // 서비스에 바인드
            doBindService();
//            mIsPlaying = myServiceBinder.isPlaying();
        }
//        startService(new Intent(getApplicationContext(), BackgroundMusicWithBindServiceService.class));
    }

    @Override
    protected void onPause() {
        Log.d("activity", "onPause");
        super.onPause();
        doUnBindService();
    }

}

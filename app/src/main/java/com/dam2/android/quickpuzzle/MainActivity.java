package com.dam2.android.quickpuzzle;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.drawable.TransitionDrawable;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private SoundService mService;
        private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
       // Intent intent = new Intent(this, SoundService.class);


        findViewById(R.id.btPlayMyPhoto).setOnClickListener(this);
        findViewById(R.id.btGallery).setOnClickListener(this);
        findViewById( R.id.ImgVolume).setOnClickListener( this );
        intent = new Intent(getApplicationContext(),SoundService.class);
        SoundService.setmConnection( mConnection );
        bindService( intent, mConnection, Service.BIND_AUTO_CREATE);
        SoundService.setmBound(true );
        int colorFrom = getResources().getColor(R.color.color1);
        int colorTo = getResources().getColor( R.color.color2);

        int colorTo2 = getResources().getColor( R.color.color2);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom,colorTo);
        colorAnimation.setDuration(2500); // milliseconds,
      colorAnimation.setRepeatCount( ValueAnimator.INFINITE );
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                findViewById( R.id.activity_main ).setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                   } else {                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }


        }
    }
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            SoundService.LocalBinder binder = (SoundService.LocalBinder) service;
            mService = binder.getService();
            SoundService.setmBound(true );
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            SoundService.setmBound(false );
        }
    };
    public void onClick(View  view) {
        switch(view.getId()){
            case R.id.btPlayMyPhoto:
                Intent intent1 = new Intent(this, GameActivity.class);

                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
                        0, view.getWidth(), view.getHeight());
                startActivity(intent1,options.toBundle());
                break;
            case R.id.btGallery:
                 intent = new Intent(this, GalleryActivity.class);

               options = ActivityOptions.makeScaleUpAnimation(view, 0,
                        0, view.getWidth(), view.getHeight());
                startActivity(intent,options.toBundle());
                break;
            case R.id.ImgVolume:
                Log.v("IMGVOLUME","IMGVOLUME");
                if(SoundService.ismBound()) {
                    ImageView imgVolume = (ImageView) findViewById( R.id.ImgVolume );
                    imgVolume.setImageResource( R.drawable.volume );
                    intent = new Intent(getApplicationContext(),SoundService.class);
                    stopService(intent);;
                   unbindService(  SoundService.getmConnection() );
                    SoundService.setmBound(false);
                }else{
                    ImageView imgVolume = (ImageView) findViewById( R.id.ImgVolume );
                    imgVolume.setImageResource( R.drawable.mute );
                    intent = new Intent(getApplicationContext(),SoundService.class);
                    bindService( intent, SoundService.getmConnection(), Service.BIND_AUTO_CREATE);
                    SoundService.setmBound(true );
                }
                break;


        }
    }
}

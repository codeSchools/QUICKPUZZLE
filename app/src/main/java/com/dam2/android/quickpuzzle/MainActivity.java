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
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    Intent intentAudio;
/*
    private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {

            switch (focusChange) {
                //perdem el focus per exemple, una altre reproductor de música
                case AudioManager.AUDIOFOCUS_LOSS:
                    mediaPlayer.stop();
                    //Log.d(LOG, "AudioFocus: rebut AUDIOFOCUS_LOSS");
                    mediaPlayer.release();
                    mediaPlayer = null;
                    break;
                //perdem el focus temporalement, per exemple, trucada
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.pause();

                    //Log.d(LOG, "AudioFocus: rebut AUDIOFOCUS_LOSS_TRANSIENT");

                    break;
                //baixem el volum temporalment
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mediaPlayer.setVolume(0.5f, 0.5f);
                    //Log.d(LOG, "AudioFocus: rebut AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    break;

                //es recupera el focus d'audio
                case AudioManager.AUDIOFOCUS_GAIN:
                    mediaPlayer.start();
                    mediaPlayer.setVolume(1.0f, 1.0f);
                    //Log.d(LOG, "AudioFocus: rebut AUDIOFOCUS_GAIN");
                    break;

                default:
                    //Log.e(LOG, "codi desconegut");
            }
        }
    };*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        findViewById(R.id.btPlayMyPhoto).setOnClickListener(this);


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

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }


        }
    }
    public void onClick(View  view) {
        switch(view.getId()){
            case R.id.btPlayMyPhoto:
                Intent intent = new Intent(this, GameActivity.class);

                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
                        0, view.getWidth(), view.getHeight());
                startActivity(intent,options.toBundle());
                break;

        }
    }
}

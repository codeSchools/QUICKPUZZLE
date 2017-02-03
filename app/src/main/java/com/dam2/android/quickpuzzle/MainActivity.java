package com.dam2.android.quickpuzzle;

import android.content.Context;
import android.content.Intent;
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
        findViewById(R.id.btPlay).setOnClickListener(this);
        findViewById(R.id.btPlayMyPhoto).setOnClickListener(this);
        //IntentService sound = new SoundService("general");
        Log.v("log","log1");
        intentAudio = new Intent(this, SoundService.class);
        // TODO bindService(intentAudio,null,BIND_ADJUST_WITH_ACTIVITY);
        Log.v("log","log2");

        /*


        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.tetris1);
        Log.v("log","hola");

        int resultat = audioManager.requestAudioFocus(
                mAudioFocusListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
        Log.v("log",String.valueOf(resultat));

        if (resultat == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mediaPlayer.start();

            //Log.d(LOG, "audioFocus listener aconseguit amb èxit");

        } else if (resultat == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            mediaPlayer.stop();
        } else {
            //Log.d(LOG, "error en la petició del listener de focus ");
        }

        */

    }
    public void onClick(View arg0) {
        switch(arg0.getId()){
            case R.id.btPlay:
                Intent intent = new Intent(this, GameActivity.class);
                bindService(intentAudio,null,0);
                startActivity(intent);
                break;
            case R.id.btPlayMyPhoto:
                Intent intent2 = new Intent(this, GameActivity.class);
                startActivity(intent2);
                break;
        }
    }
}

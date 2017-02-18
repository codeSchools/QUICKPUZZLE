package com.dam2.android.quickpuzzle;


import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by newdarkworld on 03/02/17.
 */

public class SoundService extends Service {
    public static final String TAG = "MyServiceTag";
    public AudioManager audioManager;
    public MediaPlayer mediaPlayer;
    public static boolean mBound;
    public static Intent intent;
    private static ServiceConnection mConnection;
    @Override
    public IBinder onBind(Intent intent) {
        audioManager = (AudioManager) getSystemService( AUDIO_SERVICE );
        mediaPlayer = MediaPlayer.create( this, R.raw.tetris1 );
        mediaPlayer.setLooping( true );
        Log.v( "log", "hola" );

        int resultat = audioManager.requestAudioFocus(
                mAudioFocusListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK );
        Log.v( "log", String.valueOf( resultat ) );

        if (resultat == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mediaPlayer.start();

            //Log.d(LOG, "audioFocus listener aconseguit amb èxit");

        } else if (resultat == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            mediaPlayer.stop();
        } else {
            //Log.d(LOG, "error en la petició del listener de focus ");
        }

        return null;
    }

    public static ServiceConnection getmConnection() {
        return mConnection;
    }

    public static void setmConnection(ServiceConnection Connection) {
        mConnection = Connection;
    }

    public class LocalBinder extends Binder {
        SoundService getService() {
            // Return this instance of LocalService so clients can call public methods
            return SoundService.this;
        }
    }

    public static boolean ismBound() {
        return mBound;
    }

    public static void setmBound(boolean  bound) {
       mBound = bound;
    }

    public static Intent getIntent() {
        return intent;
    }

    public static void setIntent(Intent intent) {
        SoundService.intent = intent;
    }

    @Override
    public void onDestroy(){
        if(mediaPlayer.isPlaying()){
        mediaPlayer.stop();}
        mediaPlayer.release();
        super.onDestroy();
    }



    @Override
    public boolean stopService(Intent name) {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();}
        mediaPlayer.release();
        return super.stopService( name );


    }

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
            //                    mediaPlayer.setVolume(0.5f, 0.5f);
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
    };



}

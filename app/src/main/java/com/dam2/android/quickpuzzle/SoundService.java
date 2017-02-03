package com.dam2.android.quickpuzzle;


import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;

/**
 * Created by newdarkworld on 03/02/17.
 */

public class SoundService extends IntentService {

    public AudioManager audioManager;
    MediaPlayer mediaPlayer;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SoundService(String name) {
        super(name);
    }

    @Override
    public void onHandleIntent(Intent intent){
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer = mediaPlayer.create(this, R.raw.tetris1);

        int resultat = audioManager.requestAudioFocus(
                mAudioFocusListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);

        if (resultat == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mediaPlayer.start();

            //Log.d(LOG, "audioFocus listener aconseguit amb èxit");

        } else if (resultat == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            mediaPlayer.stop();
        } else {
            //Log.d(LOG, "error en la petició del listener de focus ");
        }

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
    };


}

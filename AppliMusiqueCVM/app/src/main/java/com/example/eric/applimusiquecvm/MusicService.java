package com.example.eric.applimusiquecvm;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;

/**
 * Created by eric on 2017-03-01.
 */
public class MusicService extends Service  {

    private MediaPlayer diffuseur; //diffuseur
    private Ecouteur ec;
    private final IBinder musicBind = new MusicBinder();
    private boolean aCommence;  // permet de savoir si la diffusion a commencé
    private EnsembleChansons ensembleChansons;//ajouter référence à votre singleton


    public MediaPlayer getDiffuseur() {
        return diffuseur;
    }



    public void onCreate(){
        super.onCreate();
        ensembleChansons = EnsembleChansons.getInstance();//obtenir instance de votre singleton


        diffuseur= new MediaPlayer();
        ec = new Ecouteur();
        diffuseur.setOnPreparedListener(ec);
        diffuseur.setOnCompletionListener(ec);
        diffuseur.setOnErrorListener(ec);
        initMusicPlayer();
    }

    public void initMusicPlayer()
    {
        diffuseur.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        diffuseur.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    public boolean onUnbind( Intent intent)
    {
        diffuseur.stop();
        diffuseur.release();
        return false;
    }

    public void diffuserChanson(){
        diffuseur.reset();

        // passer id de la chanson courante provenant du Vecteur de votre singleton

        long id = 1 ; // à remplacer
        Uri trackUri = ContentUris.withAppendedId( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id /* remplacer par id chanson*/);

        try {
            diffuseur.setDataSource(getApplicationContext(), trackUri);
        } catch (IOException e) {
            Log.e("MUSIC SERVICE", "problème", e);
        }
        diffuseur.prepareAsync();


    }

    public int getPosition()
    {
        return diffuseur.getCurrentPosition();
    }

    public int getDuree()
    {
        return diffuseur.getDuration();
    }

    public boolean diffuseActuellement()
    {
        return diffuseur.isPlaying();
    }
    public void pause()
    {
        diffuseur.pause();

    }
    public void cherche(int position)
    {
        diffuseur.seekTo(position);
    }

    public void demarre(){
        diffuseur.start();

    }


    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
        }

    private class Ecouteur implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {


        @Override
        public void onCompletion(MediaPlayer mp) {
            if ( diffuseur.getCurrentPosition() >0)
            {

                mp.reset();
                // avancer à la chanson suivante / singleton


                diffuserChanson();

            }
        }

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            mp.reset();
            return false;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
            aCommence=true;




        }
    }

    public boolean isaCommence() {
        return aCommence;
    }
}

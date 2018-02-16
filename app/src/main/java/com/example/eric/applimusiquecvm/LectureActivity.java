package com.example.eric.applimusiquecvm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Hashtable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class LectureActivity extends AppCompatActivity {
    private ImageView btnArtistes, btnChansons,btnListes, btnBack, btnPlay, btnNext, btnRandom, imgPochette, btnHome, btnLecture;
    private Ecouteur ec;
    private EcouteurMenu ecMenu;
    private EcouteurSeekBar ecSeek;
    private MusicService musicSrv;
    private boolean musicBound = false, musiqueJoue = false, aleatoire=false,hasNext = true, oneArtist = false;
    private Intent playIntent;
    private SeekBar barre;
    private int tempsCourant = 0, minutes, secondes, tmpMax = 500, id, position;
    private TextView tmpDebut, tmpFin, zoneArtiste, zoneAlbum, zoneTitre;
    private Handler handler;
    public Runnable handlerTask;
    private Timer timer;
    private String artiste, titre, album, pochette;
    private Vector<Chanson> vecChansons;
    private Drawable img;
    private static int tmpDebutCourant, tmpFinCourant, tmpMaxCourant, idCourant, positionCourant;
    private static String titreCourant, artisteCourant, albumCourant, pochetteCourant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        imgPochette = (ImageView) findViewById(R.id.zonePochette);
        tmpDebut = (TextView) findViewById(R.id.tempsDebut);
        tmpFin = (TextView) findViewById(R.id.tempsFin);
        btnArtistes = (ImageView) findViewById(R.id.buttonArtistes);
        btnChansons = (ImageView) findViewById(R.id.buttonChansons);
        btnListes = (ImageView) findViewById(R.id.buttonListes);
        btnHome= (ImageView) findViewById(R.id.buttonHome);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnPlay = (ImageView) findViewById(R.id.btnPlay);
        btnNext = (ImageView) findViewById(R.id.btnNext);
        btnRandom = (ImageView) findViewById(R.id.btnRandom);
        zoneArtiste = (TextView) findViewById(R.id.zoneArtiste);
        zoneAlbum = (TextView) findViewById(R.id.zoneAlbum);
        zoneTitre = (TextView) findViewById(R.id.zoneTitre);
        barre = (SeekBar) findViewById(R.id.barre);
        btnLecture= (ImageView) findViewById(R.id.buttonPlaying);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("Position");
            id = Integer.parseInt(extras.getString("Id"));
            titre = extras.getString("Titre");
            artiste = extras.getString("Artiste");
            album = extras.getString("Album");
            pochette = extras.getString("Pochette");
            if(extras.getString("Aleatoire").equals("oui")){
                aleatoire = true;
                btnRandom.setImageResource(R.drawable.randomon);
            }

            positionCourant = extras.getInt("Position");
            idCourant = Integer.parseInt(extras.getString("Id"));
            titreCourant = extras.getString("Titre");
            artisteCourant = extras.getString("Artiste");
            albumCourant = extras.getString("Album");
            pochetteCourant = extras.getString("Pochette");
            if(extras.getString("Aleatoire").equals("oui")){
                aleatoire = true;
                btnRandom.setImageResource(R.drawable.randomon);
            }
            if(extras.getString("OneArtist").equals("oui")){
                oneArtist = true;
            }


        }

        if(aleatoire || oneArtist){
            vecChansons = EnsembleChansons.getInstance().getVecDonnees();
        }else{
            vecChansons = EnsembleChansons.getInstance().getVecTemp();
        }

        EnsembleChansons.getInstance().setIdCourant(id);
        barre.setMax(tmpMax);
        zoneArtiste.setText(artisteCourant);
        zoneAlbum.setText(albumCourant);
        zoneTitre.setText(titreCourant);
        img = Drawable.createFromPath(pochetteCourant);
        imgPochette.setImageDrawable(img);

        minutes = 0;
        secondes = 0;
        tmpDebut.setText(String.valueOf(String.format("%02d:%02d", minutes, secondes)));

        tmpMax = vecChansons.elementAt(position).getDuration();
        minutes = ((tmpMax/1000-tempsCourant) % 3600) / 60;
        secondes = (tmpMax/1000-tempsCourant) % 60;
        tmpFin.setText(String.valueOf(String.format("%02d:%02d", minutes, secondes)));

        ec = new Ecouteur();
        ecMenu = new EcouteurMenu();
        ecSeek = new EcouteurSeekBar();

        barre.setOnSeekBarChangeListener(ecSeek);
        btnBack.setOnClickListener(ec);
        btnPlay.setOnClickListener(ec);
        btnNext.setOnClickListener(ec);
        btnRandom.setOnClickListener(ec);
        //btnLecture.setOnClickListener(ecMenu);
        btnHome.setOnClickListener(ecMenu);
        btnArtistes.setOnClickListener(ecMenu);
        btnChansons.setOnClickListener(ecMenu);
        btnListes.setOnClickListener(ecMenu);


    }

    protected void onStart(){
        super.onStart();
        if ( playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            bindService ( playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);

        }else{

        }
    }

    public void avanceSeekBar(){
        handler = new Handler() {
            public void handleMessage(Message msg) {
		        super.handleMessage(msg);
                try{
                    tempsCourant++;
                    barre.setProgress(tempsCourant);

                    minutes = (tempsCourant % 3600) / 60;
                    secondes = tempsCourant % 60;
                    tmpDebut.setText(String.valueOf(String.format("%02d:%02d", minutes, secondes)));

                    tmpMax = vecChansons.elementAt(position).getDuration();
                    barre.setMax(tmpMax/1000);
                    tmpMaxCourant = vecChansons.elementAt(position).getDuration();
                    minutes = ((tmpMax/1000-tempsCourant) % 3600) / 60;
                    secondes = (tmpMax/1000-tempsCourant) % 60;
                    tmpFin.setText(String.valueOf(String.format("%02d:%02d", minutes, secondes)));

                    if(tempsCourant == tmpMax/1000){
                        next();
                    }
                }catch(ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                handler.sendEmptyMessage(0);
            }}, 1000, 1000);
    }

    private ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            musicSrv = binder.getService();
            musicBound = true;

            if (!musiqueJoue) {
                btnPlay.setImageResource(R.drawable.pause);
                musiqueJoue = true;
                avanceSeekBar();
                musicSrv.diffuserChanson();
            } else {
                btnPlay.setImageResource(R.drawable.play);
                musicSrv.pause();
                musiqueJoue = false;
                timer.cancel();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound=false;
        }
    };

    public void next(){
        Hashtable<String, Object> nextChanson = new Hashtable<String, Object>();
        int nbChansons = EnsembleChansons.getInstance().getVecDonnees().size();

        position++;

        try {
            nextChanson = vecChansons.elementAt(position).infosChanson();
        } catch (ArrayIndexOutOfBoundsException e) {
            hasNext = false;
        }


        if (hasNext) {
            if (musiqueJoue) {
                musicSrv.pause();
                timer.cancel();
            }

            tempsCourant = 0;
            barre.setProgress(tempsCourant);

            minutes = 0;
            secondes = 0;
            tmpFin.setText(String.valueOf(String.format("%02d:%02d", minutes, secondes)));

            id = (int) nextChanson.get("id");

            EnsembleChansons.getInstance().setIdCourant(id);
            titre = (String) nextChanson.get("titre");
            artiste = (String) nextChanson.get("artiste");
            album = (String) nextChanson.get("album");
            pochette = (String) nextChanson.get("pochette");

            idCourant = (int) nextChanson.get("id");
            titreCourant = (String) nextChanson.get("titre");
            artisteCourant = (String) nextChanson.get("artiste");
            albumCourant = (String) nextChanson.get("album");
            pochetteCourant = (String) nextChanson.get("pochette");

            zoneArtiste.setText(artiste);
            zoneAlbum.setText(album);
            zoneTitre.setText(titre);
            Drawable img = Drawable.createFromPath(pochette);
            imgPochette.setImageDrawable(img);

            tmpMax = vecChansons.elementAt(position).getDuration();
            minutes = ((tmpMax / 1000 - tempsCourant) % 3600) / 60;
            secondes = (tmpMax / 1000 - tempsCourant) % 60;
            tmpFin.setText(String.valueOf(String.format("%02d:%02d", minutes, secondes)));


            if (musiqueJoue) {
                musicSrv.diffuserChanson();
                avanceSeekBar();
            }
        }else{
            timer.cancel();
            musicSrv.pause();
        }
        hasNext=true;
    }
    //Écouteur qui gère les interactions de l'utilisateur avec le seekbar
    class EcouteurSeekBar implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                timer.cancel();
                tempsCourant = seekBar.getProgress();
                //barre.setProgress(tempsCourant);
                musicSrv.cherche(tempsCourant*1000);
                avanceSeekBar();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    //�couteurs qui gère les interactions avec la musique
    class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnPlay) {
                if (!musiqueJoue) {
                    btnPlay.setImageResource(R.drawable.pause);
                    musiqueJoue = true;
                    avanceSeekBar();
                    musicSrv.diffuserChanson();
                } else {
                    btnPlay.setImageResource(R.drawable.play);
                    musicSrv.pause();
                    musiqueJoue = false;
                    timer.cancel();
                }
            } else if (v.getId() == R.id.btnBack) {
                if (musiqueJoue) {
                    timer.cancel();
                    btnPlay.setImageResource(R.drawable.pause);
                }

                tempsCourant = 0;
                barre.setProgress(tempsCourant);

                minutes = 0;
                secondes = 0;
                tmpDebut.setText(String.valueOf(String.format("%02d:%02d", minutes, secondes)));

                tmpMax = vecChansons.elementAt(position).getDuration();
                minutes = ((tmpMax / 1000 - tempsCourant) % 3600) / 60;
                secondes = (tmpMax / 1000 - tempsCourant) % 60;
                tmpFin.setText(String.valueOf(String.format("%02d:%02d", minutes, secondes)));

                if (musiqueJoue) {
                    musicSrv.diffuserChanson();
                    avanceSeekBar();
                }

            }else if (v.getId() == R.id.btnNext) {
                next();
            }

            if(v.getId()==R.id.btnRandom){
                if(aleatoire){
                    btnRandom.setImageResource(R.drawable.random);
                    EnsembleChansons.getInstance().remplirVecteur();
                    aleatoire = false;

                }else{
                    btnRandom.setImageResource(R.drawable.randomon);
                    EnsembleChansons.getInstance().melanger();
                    position = 0;
                    aleatoire = true;
                }
            }
        }
    }

    class EcouteurMenu implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId()) {
                case R.id.buttonHome:
                    i = new Intent(LectureActivity.this, MainActivity.class);
                    //musicSrv.pause();
                    startActivity(i);
                    break;
                case R.id.buttonArtistes:
                    i = new Intent(LectureActivity.this, ArtistesActivity.class);
                    //musicSrv.pause();
                    startActivity(i);
                    break;
                case R.id.buttonChansons:
                    i = new Intent(LectureActivity.this, ChansonsActivity.class);
                    //musicSrv.pause();
                    startActivity(i);
                    break;
                case R.id.buttonListes:
                    i = new Intent(LectureActivity.this, ListesActivity.class);
                    //musicSrv.pause();
                    startActivity(i);
                    break;

            }
        }
    }
}

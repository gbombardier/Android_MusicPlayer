package com.example.eric.applimusiquecvm;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.eric.applimusiquecvm.adapter.MusicAdapter;

import java.util.Hashtable;
import java.util.Vector;

public class ArtistesActivity extends AppCompatActivity {
    private ListView liste;
    private ArtistesActivity.Ecouteur ec;
    private ImageView btnArtistes, btnChansons,btnListes, btnHome, btnLecture;
    private EcouteurMenu ecMenu;
    private Vector<Hashtable<String, Object>> chansonsCourante;
    private SimpleAdapter adapter;
    private String[] tabCles = {"pochette", "artiste"};
    private int[] tabRef = {R.id.imgArtiste, R.id.nomArtiste};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistes);

        chansonsCourante = new Vector<Hashtable<String, Object>>();
        btnArtistes = (ImageView) findViewById(R.id.buttonArtistes);
        btnChansons = (ImageView) findViewById(R.id.buttonChansons);
        btnListes = (ImageView) findViewById(R.id.buttonListes);
        btnHome= (ImageView) findViewById(R.id.buttonHome);
        btnLecture= (ImageView) findViewById(R.id.buttonPlaying);

        liste = (ListView) findViewById(R.id.listeArtistes);

        EnsembleChansons.getInstance().remplirVecteurArtistes();

        for(Chanson toune: EnsembleChansons.getInstance().getVecDonnees()){
            chansonsCourante.add(toune.infosChanson());
        }

        adapter = new SimpleAdapter(this, chansonsCourante, R.layout.layout_artiste, tabCles, tabRef);
        liste.setAdapter(adapter);

        ec = new Ecouteur();
        ecMenu = new EcouteurMenu();
        liste.setOnItemClickListener(ec);
        btnArtistes.setOnClickListener(ecMenu);
        btnChansons.setOnClickListener(ecMenu);
        btnListes.setOnClickListener(ecMenu);
        btnHome.setOnClickListener(ecMenu);
        //btnLecture.setOnClickListener(ecMenu);

    }

    //Écouteur qui affiche l'intent pour les chansons d'un seul artiste
    class Ecouteur implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(ArtistesActivity.this, OneArtist.class);
            i.putExtra("Artiste", String.valueOf(chansonsCourante.elementAt(position).get("artiste")));
            startActivity(i);
            }
        }

    //Écouteur qui gère le changement de menu
    class EcouteurMenu implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId()) {
                case R.id.buttonHome:
                    i = new Intent(ArtistesActivity.this, MainActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonChansons:
                    i = new Intent(ArtistesActivity.this, ChansonsActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonListes:
                    i = new Intent(ArtistesActivity.this, ListesActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonPlaying:
                    i = new Intent(ArtistesActivity.this, LectureActivity.class);
                    startActivity(i);
                    break;

            }
        }
    }
    }


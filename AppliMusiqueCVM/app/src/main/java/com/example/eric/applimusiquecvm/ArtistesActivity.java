package com.example.eric.applimusiquecvm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.Hashtable;
import java.util.Vector;

public class ArtistesActivity extends AppCompatActivity {
    private ListView liste;
    private ArtistesActivity.Ecouteur ec;
    //private SimpleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistes);

        ec = new Ecouteur();
        EnsembleChansons.getInstance().remplirVecteur();

        liste = (ListView) findViewById(R.id.listeArtistes);

        String[] tabCles = {"pochette", "groupe"};
        int[] tabRef = {R.id.imgArtiste, R.id.nomArtiste};
        SimpleAdapter adapter = new SimpleAdapter(this, EnsembleChansons.getInstance().getVecDonnees(), R.layout.layout_artiste, tabCles, tabRef );

        liste.setAdapter(adapter);

        liste.setOnItemClickListener(ec);

    }


    class Ecouteur implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(ArtistesActivity.this, OneArtist.class);
            i.putExtra("Artiste", EnsembleChansons.getInstance().getVecDonnees().elementAt(position).get("groupe"));
            startActivity(i);
            }
        }
    }


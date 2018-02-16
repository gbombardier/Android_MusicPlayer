package com.example.eric.applimusiquecvm;

import android.content.Intent;
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

public class ChansonsActivity extends AppCompatActivity {
    private ImageView btnArtistes, btnChansons,btnListes, btnRandom, btnHome, btnLecture;
    private EcouteurMenu ecMenu;
    private ListView liste;
    private Ecouteur ec;
    //private SimpleAdapter adapter;
    private Vector<Hashtable<String, Object>> chansonsCourante;
    private MusicAdapter adapter;
    private boolean aleatoire = false;
    private String[] tabCles = {"pochette", "artiste", "titre"};
    private int[] tabRef = {R.id.imgPochette, R.id.nomGroupe, R.id.nomChanson};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chansons);

        EnsembleChansons.getInstance().remplirVecteur();
        liste = (ListView) findViewById(R.id.listeChansons);
        chansonsCourante = new Vector<Hashtable<String, Object>>();
        btnArtistes = (ImageView) findViewById(R.id.buttonArtistes);
        btnChansons = (ImageView) findViewById(R.id.buttonChansons);
        btnListes = (ImageView) findViewById(R.id.buttonListes);
        btnRandom = (ImageView) findViewById(R.id.btnRandom);
        btnHome= (ImageView) findViewById(R.id.buttonHome);
        btnLecture= (ImageView) findViewById(R.id.buttonPlaying);

        adapter = new MusicAdapter(this, EnsembleChansons.getInstance().getVecDonnees());
        liste.setAdapter(adapter);

        ec = new Ecouteur();
        ecMenu = new EcouteurMenu();
        liste.setOnItemClickListener(ec);

        //btnLecture.setOnClickListener(ecMenu);
        btnArtistes.setOnClickListener(ecMenu);
        btnHome.setOnClickListener(ecMenu);
        btnChansons.setOnClickListener(ecMenu);
        btnListes.setOnClickListener(ecMenu);
        btnRandom.setOnClickListener(ecMenu);
    }

    //Écouteur pour partir la lecture d'une chanson
    class Ecouteur implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(ChansonsActivity.this, LectureActivity.class);

            for(Chanson toune : EnsembleChansons.getInstance().getVecDonnees()){
                chansonsCourante.add(toune.infosChanson());
            }

            i.putExtra("Position", position);
            i.putExtra("Id", String.valueOf(chansonsCourante.elementAt(position).get("id")));
            i.putExtra("Artiste", String.valueOf(chansonsCourante.elementAt(position).get("artiste")));
            i.putExtra("Titre", String.valueOf(chansonsCourante.elementAt(position).get("titre")));
            i.putExtra("Pochette", String.valueOf(chansonsCourante.elementAt(position).get("pochette")));
            i.putExtra("Album", String.valueOf(chansonsCourante.elementAt(position).get("album")));
            if(aleatoire){
                i.putExtra("Aleatoire", "oui" );
            }else{
                i.putExtra("Aleatoire", "non" );
            }
            i.putExtra("OneArtist", "non");

            startActivity(i);
        }
    }

    //Écouteur qui gère le changement de menu et l'aléatoire
    class EcouteurMenu implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId()) {
                case R.id.buttonHome:
                    i = new Intent(ChansonsActivity.this, MainActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonArtistes:
                    i = new Intent(ChansonsActivity.this, ArtistesActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonListes:
                    i = new Intent(ChansonsActivity.this, ListesActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonPlaying:
                    i = new Intent(ChansonsActivity.this, LectureActivity.class);
                    startActivity(i);
                    break;

                case R.id.btnRandom:
                    if(aleatoire){
                        aleatoire = false;
                        btnRandom.setImageResource(R.drawable.random);
                        EnsembleChansons.getInstance().remplirVecteur();
                        adapter.notifyDataSetChanged();
                    }else{
                        aleatoire = true;
                        btnRandom.setImageResource(R.drawable.randomon);
                        EnsembleChansons.getInstance().melanger();
                        adapter.notifyDataSetChanged();
                    }


            }
        }
    }
}

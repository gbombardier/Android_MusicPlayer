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
import android.widget.TextView;

import com.example.eric.applimusiquecvm.adapter.MusicAdapter;

import org.w3c.dom.Text;

import java.util.Hashtable;
import java.util.Vector;

public class OneArtist extends AppCompatActivity {
    private ListView liste;
    private ImageView btnArtistes, btnChansons,btnListes, btnHome, btnLecture;
    private Ecouteur ec;
    private EcouteurMenu ecMenu;
    private Vector<Hashtable<String, String>> vecDonnees;
    private Vector<Hashtable<String, Object>> chansonsCourante;
    String artiste="";
    TextView titre;
    private MusicAdapter adapter;
    String[] tabCles = {"pochette", "artiste", "titre"};
    int[] tabRef = {R.id.imgPochette, R.id.nomGroupe, R.id.nomChanson};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_artist);

        btnArtistes = (ImageView) findViewById(R.id.buttonArtistes);
        btnChansons = (ImageView) findViewById(R.id.buttonChansons);
        btnListes = (ImageView) findViewById(R.id.buttonListes);
        btnHome= (ImageView) findViewById(R.id.buttonHome);
        btnLecture= (ImageView) findViewById(R.id.buttonPlaying);

        titre = (TextView) findViewById(R.id.textArtiste);
        liste = (ListView) findViewById(R.id.listeChansonsArtiste);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            artiste = extras.getString("Artiste");
            titre.setText(artiste);
            EnsembleChansons.getInstance().remplirVecteur(artiste);
        }

        chansonsCourante = new Vector<Hashtable<String, Object>>();
        for(Chanson toune: EnsembleChansons.getInstance().getVecDonnees()){
            chansonsCourante.add(toune.infosChanson());
        }

        adapter = new MusicAdapter(this, EnsembleChansons.getInstance().getVecDonnees());

        liste.setAdapter(adapter);


        ec = new Ecouteur();
        ecMenu = new EcouteurMenu();
        liste.setOnItemClickListener(ec);
        //btnLecture.setOnClickListener(ecMenu);
        btnHome.setOnClickListener(ecMenu);
        btnArtistes.setOnClickListener(ecMenu);
        btnChansons.setOnClickListener(ecMenu);
        btnListes.setOnClickListener(ecMenu);
    }

    //Écouteur pour partir la lecture d'une chanson
    class Ecouteur implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(OneArtist.this, LectureActivity.class);
            i.putExtra("Position", position);
            i.putExtra("Id", String.valueOf(chansonsCourante.elementAt(position).get("id")));
            i.putExtra("Artiste", String.valueOf(chansonsCourante.elementAt(position).get("artiste")));
            i.putExtra("Titre", String.valueOf(chansonsCourante.elementAt(position).get("titre")));
            i.putExtra("Pochette", String.valueOf(chansonsCourante.elementAt(position).get("pochette")));
            i.putExtra("Album", String.valueOf(chansonsCourante.elementAt(position).get("album")));
            i.putExtra("Aleatoire", "non");
            i.putExtra("OneArtist", "oui");
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
                    i = new Intent(OneArtist.this, MainActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonPlaying:
                    i = new Intent(OneArtist.this, LectureActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonArtistes:
                    i = new Intent(OneArtist.this, ArtistesActivity.class);
                    i.putExtra("info a transmettre", "message");
                    startActivity(i);
                    break;
                case R.id.buttonChansons:
                    i = new Intent(OneArtist.this, ChansonsActivity.class);
                    i.putExtra("info a transmettre", "message");
                    startActivity(i);
                    break;
                case R.id.buttonListes:
                    i = new Intent(OneArtist.this, ListesActivity.class);
                    i.putExtra("info a transmettre", "message");
                    startActivity(i);
                    break;

            }
        }
    }
}

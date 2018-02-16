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

public class ChansonsActivity extends AppCompatActivity {
    private ListView liste;
    private Ecouteur ec;
    //private SimpleAdapter adapter;
    private Vector<Hashtable<String, String>> vecDonnees;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chansons);

        EnsembleChansons.getInstance().remplirVecteur();
        liste = (ListView) findViewById(R.id.listeChansons);

        ec = new Ecouteur();

        String[] tabCles = {"pochette", "groupe", "chanson"};
        int[] tabRef = {R.id.imgPochette, R.id.nomGroupe, R.id.nomChanson};
        SimpleAdapter adapter = new SimpleAdapter(this, EnsembleChansons.getInstance().getVecDonnees(), R.layout.layout_chanson, tabCles, tabRef );

        liste.setAdapter(adapter);

        liste.setOnItemClickListener(ec);
    }


    class Ecouteur implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(ChansonsActivity.this, LectureActivity.class);
            i.putExtra("Artiste", EnsembleChansons.getInstance().getVecDonnees().elementAt(position).get("groupe"));
            i.putExtra("Chanson", EnsembleChansons.getInstance().getVecDonnees().elementAt(position).get("chanson"));
            i.putExtra("Pochette", EnsembleChansons.getInstance().getVecDonnees().elementAt(position).get("pochette"));
            startActivity(i);
        }
    }
}

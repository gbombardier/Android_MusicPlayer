package com.example.eric.applimusiquecvm;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by 0943547 on 06-12-17.
 */

public class EnsembleChansons {
    private ListView liste;
    private SimpleAdapter adapter;
    private Vector<Hashtable<String, String>> vecDonnees, vecTemp;
    private int indexCourant=0;
    private static EnsembleChansons instance;


    private EnsembleChansons(){
        vecDonnees = new Vector<Hashtable<String, String>>();
        vecTemp = new Vector<Hashtable<String, String>>();

        remplirVecteurTemp();
    }

    public static EnsembleChansons getInstance(){
        if(instance==null){
            instance = new EnsembleChansons();
        }

        return instance;
    }


    public Vector<Hashtable<String, String>> getVecDonnees(){
        return vecDonnees;
    }

    //Construis un vecteur contenant toutes les données
    //Aller chercher les informations grâce à un query
    public void remplirVecteurTemp(){
        Hashtable<String, String> hStars = new Hashtable<String, String>();
        Hashtable<String, String> hDears = new Hashtable<String, String>();
        Hashtable<String, String> hMalajube = new Hashtable<String, String>();

        hStars.put("pochette", String.valueOf(R.drawable.stars));
        hStars.put("groupe", "Stars");
        hStars.put("chanson", "Corona");

        hDears.put("pochette", String.valueOf(R.drawable.dears));
        hDears.put("groupe", "Dears");
        hDears.put("chanson", "Sala Rossa");

        hMalajube.put("pochette", String.valueOf(R.drawable.malajube));
        hMalajube.put("groupe", "Malajube");
        hMalajube.put("chanson", "Métropolis");

        vecTemp.add(hStars);
        vecTemp.add(hDears);
        vecTemp.add(hMalajube);
    }

    //Remplis le vecteur de données avec toutes les chansons
    public void remplirVecteur(){
        vecDonnees.clear();
        for(Hashtable<String, String> hash : vecTemp){
            vecDonnees.add(hash);
        }
    }

    //Remplis le vecteur de données avec les chansons d'un artiste
    public void remplirVecteur(String artiste){
        vecDonnees.clear();
        for(Hashtable<String, String> hash : vecTemp){
            if(hash.get("groupe").equals(artiste)){
                vecDonnees.add(hash);
            }
        }


    }
}

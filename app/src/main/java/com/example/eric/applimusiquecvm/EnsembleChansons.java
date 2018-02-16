package com.example.eric.applimusiquecvm;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

//Classe qui gère le vecteur de chansons
public class EnsembleChansons {
    private Vector<Chanson> vecDonnees, vecTemp;
    private int idCourant=1;
    private static EnsembleChansons instance;


    private EnsembleChansons(){
        vecDonnees = new Vector<Chanson>();
        vecTemp = new Vector<Chanson>();
    }

    public static EnsembleChansons getInstance(){
        if(instance==null){
            instance = new EnsembleChansons();
        }
        return instance;
    }

    //Mélange les chansons lors de la sélection de aléatoire
    public void melanger(){
        Collections.shuffle(vecDonnees);
    }

    public Vector<Chanson> getVecDonnees(){
        return vecDonnees;
    }

    public Vector<Chanson> getVecTemp(){
        return vecTemp;
    }

    public void ajouterChanson(Chanson toune){
        vecTemp.add(toune);
    }

    //Remplis le vecteur de données avec toutes les chansons
    public void remplirVecteur(){
        vecDonnees.clear();
        for(Chanson toune : vecTemp){
            vecDonnees.add(toune);
        }
    }

    //Remplis le vecteur de données pour affichage par artiste
    public void remplirVecteurArtistes(){
        vecDonnees.clear();
        Vector<String> artistes = new Vector<String>();
        boolean dejaLa = false;

        for(Chanson toune : vecTemp){
            for(String artCourant : artistes){
                if(toune.getArtiste().equals(artCourant)){
                    dejaLa = true;
                }
            }
            if(!dejaLa){
                artistes.add(toune.getArtiste());
                vecDonnees.add(toune);
            }
            dejaLa=false;
        }
    }

    //Remplis le vecteur de données avec les chansons d'un artiste
    public void remplirVecteur(String artiste){

        vecDonnees.clear();
        for(Chanson toune : vecTemp){
            if(toune.getArtiste().equals(artiste)){
                vecDonnees.add(toune);
            }
        }
    }

    public void setIdCourant(int id){
        this.idCourant=id;
    }

    public int idCourant(){
        return this.idCourant;
    }
}

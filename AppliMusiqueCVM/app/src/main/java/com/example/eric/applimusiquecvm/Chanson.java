package com.example.eric.applimusiquecvm;

/**
 * Created by 0943547 on 06-12-17.
 */

public class Chanson {

    private int id, pochette;
    private String titre, artiste;


    public Chanson(int id, String titre, String artiste, int pochette){
        this.id = id;
        this.titre = titre;
        this.artiste = artiste;
        this.pochette=pochette;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }



}

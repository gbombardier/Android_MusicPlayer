package com.example.eric.applimusiquecvm;

import java.util.Hashtable;

//Classe qui gère toutes les informations utiles d'une chanson
public class Chanson {

    private int id, duration;
    private String titre, artiste, album, pochette;
    Hashtable<String, Object> infosChanson;



    public Chanson(int id, String titre, String artiste, String album, String pochette, int duration){
        this.id = id;
        this.titre = titre;
        this.artiste = artiste;
        this.album = album;
        this.pochette=pochette;
        this.duration = duration;

        infosChanson= new Hashtable<String, Object>();
        infosChanson.put("id", this.id);
        infosChanson.put("titre", this.titre);
        infosChanson.put("artiste", this.artiste);
        infosChanson.put("album", this.album);
        infosChanson.put("pochette", this.pochette);
        infosChanson.put("duration", this.duration);
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

    public int getDuration() {
        return duration;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public Hashtable<String,Object> infosChanson(){
        return infosChanson;
    }

    public String getPochette() {
        return pochette;
    }

    public String getAlbum() {
        return album;
    }
}

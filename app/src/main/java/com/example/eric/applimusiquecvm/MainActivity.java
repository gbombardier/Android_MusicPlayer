package com.example.eric.applimusiquecvm;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class MainActivity extends Activity {
    private ImageView btnArtistes, btnChansons,btnListes, imgPochette, btnLecture;
    private TextView txtAcceuil;
    private EcouteurMenu ecMenu;
    private ImageView img;
    private int index;
    private static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        btnArtistes = (ImageView) findViewById(R.id.buttonArtistes);
        btnChansons = (ImageView) findViewById(R.id.buttonChansons);
        btnListes = (ImageView) findViewById(R.id.buttonListes);
        imgPochette = (ImageView) findViewById(R.id.imgAcceuil);
        txtAcceuil = (TextView) findViewById(R.id.txtSuggestion);
        btnLecture= (ImageView) findViewById(R.id.buttonPlaying);

        ecMenu = new EcouteurMenu();

        //btnLecture.setOnClickListener(ecMenu);
        imgPochette.setOnClickListener(ecMenu);
        btnArtistes.setOnClickListener(ecMenu);
        btnChansons.setOnClickListener(ecMenu);
        btnListes.setOnClickListener(ecMenu);

        if(!active){
            chercherChansons();
        }


        active = true;

        Random r = new Random();
        index = r.nextInt(EnsembleChansons.getInstance().getVecTemp().size());
        String artiste = EnsembleChansons.getInstance().getVecTemp().elementAt(index).getArtiste();
        txtAcceuil.setText("Que pensez-vous d'un peu de " + artiste + "?");
        String pochette = (String) EnsembleChansons.getInstance().getVecTemp().elementAt(index).getPochette();
        Drawable img = Drawable.createFromPath(pochette);
        imgPochette.setImageDrawable(img);
    }

    //Va chercher toutes les chansons sur la carte sd et les enregistre dans le vecteur temp
    public void chercherChansons(){
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int songAlbumId= songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int songDuration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

        if(songCursor != null && songCursor.moveToFirst() ){
            do {
                int currentId = songCursor.getInt(songId);
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentAlbum = songCursor.getString(songAlbum);
                String currentAlbumId = songCursor.getString(songAlbumId);
                String currentPochette = "";
                int currentDuration = songCursor.getInt(songDuration);

                String[] projection = new String[] {MediaStore.Audio.Albums.ALBUM_ART};
                String where = MediaStore.Audio.Albums._ID + "=?";
                String[] whereArgs = new String[]{currentAlbumId};
                Cursor imgCursor = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, where, whereArgs, null);
                if (imgCursor != null) {
                    if(imgCursor.moveToFirst()) {
                        try {
                            int songPochette = imgCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                            currentPochette = imgCursor.getString(songPochette);
                            if (currentPochette == null) {
                                currentPochette = "";
                            }
                        } catch (CursorIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                    imgCursor.close();
                }


                EnsembleChansons.getInstance().ajouterChanson(new Chanson(currentId, currentTitle, currentArtist, currentAlbum, currentPochette, currentDuration));
            } while(songCursor.moveToNext());

            songCursor.close();
        }
    }

    class EcouteurMenu implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId()) {
                case R.id.imgAcceuil:
                    i = new Intent(MainActivity.this, LectureActivity.class);
                    i.putExtra("Position", index);
                    i.putExtra("Id", String.valueOf(EnsembleChansons.getInstance().getVecTemp().elementAt(index).getId()));
                    i.putExtra("Artiste", String.valueOf(EnsembleChansons.getInstance().getVecTemp().elementAt(index).getArtiste()));
                    i.putExtra("Titre", String.valueOf(EnsembleChansons.getInstance().getVecTemp().elementAt(index).getTitre()));
                    i.putExtra("Pochette", String.valueOf(EnsembleChansons.getInstance().getVecTemp().elementAt(index).getPochette()));
                    i.putExtra("Album", String.valueOf(EnsembleChansons.getInstance().getVecTemp().elementAt(index).getAlbum()));
                    i.putExtra("Aleatoire", "non");
                    i.putExtra("OneArtist", "non");
                    startActivity(i);
                    break;
                case R.id.buttonArtistes:
                    i = new Intent(MainActivity.this, ArtistesActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonChansons:
                    i = new Intent(MainActivity.this, ChansonsActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonListes:
                    i = new Intent(MainActivity.this, ListesActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonPlaying:
                    i = new Intent(MainActivity.this, LectureActivity.class);
                    startActivity(i);
                    break;

            }
        }
    }
}

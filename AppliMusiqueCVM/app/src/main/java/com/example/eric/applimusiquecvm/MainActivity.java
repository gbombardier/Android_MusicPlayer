package com.example.eric.applimusiquecvm;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private Button btnArtistes, btnChansons, btnListes;
    private ImageView img;
    private Ecouteur ec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnArtistes = (Button) findViewById(R.id.buttonArtistes);
        btnChansons = (Button) findViewById(R.id.buttonChansons);
        btnListes = (Button) findViewById(R.id.buttonListes);
        img = (ImageView) findViewById(R.id.imgAcceuil);

        img.setImageResource(R.drawable.jdugas);

        ec = new Ecouteur();

        btnArtistes.setOnClickListener(ec);
        btnChansons.setOnClickListener(ec);
        btnListes.setOnClickListener(ec);
    }

    class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId()) {
                case R.id.buttonArtistes:
                    i = new Intent(MainActivity.this, ArtistesActivity.class);
                    i.putExtra("info a transmettre", "message");
                    startActivity(i);
                    break;
                case R.id.buttonChansons:
                    i = new Intent(MainActivity.this, ChansonsActivity.class);
                    i.putExtra("info a transmettre", "message");
                    startActivity(i);
                    break;
                case R.id.buttonListes:
                    i = new Intent(MainActivity.this, ListesActivity.class);
                    i.putExtra("info a transmettre", "message");
                    startActivity(i);
                    break;

            }
        }
    }
}

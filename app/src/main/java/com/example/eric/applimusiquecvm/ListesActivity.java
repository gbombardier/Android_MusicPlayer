package com.example.eric.applimusiquecvm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ListesActivity extends AppCompatActivity {
    private ImageView btnArtistes, btnChansons,btnListes, btnHome, btnLecture;
    private EcouteurMenu ecMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listes);

        ecMenu = new EcouteurMenu();
        btnArtistes = (ImageView) findViewById(R.id.buttonArtistes);
        btnChansons = (ImageView) findViewById(R.id.buttonChansons);
        btnListes = (ImageView) findViewById(R.id.buttonListes);
        btnHome= (ImageView) findViewById(R.id.buttonHome);
        btnLecture= (ImageView) findViewById(R.id.buttonPlaying);

        btnArtistes.setOnClickListener(ecMenu);
        btnChansons.setOnClickListener(ecMenu);
        btnListes.setOnClickListener(ecMenu);
        btnHome.setOnClickListener(ecMenu);
        //btnLecture.setOnClickListener(ecMenu);
    }

    class EcouteurMenu implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId()) {
                case R.id.buttonHome:
                    i = new Intent(ListesActivity.this, MainActivity.class);
                    startActivity(i);
                    break;
                case R.id.buttonArtistes:
                    i = new Intent(ListesActivity.this, ArtistesActivity.class);
                    i.putExtra("info a transmettre", "message");
                    startActivity(i);
                    break;
                case R.id.buttonChansons:
                    i = new Intent(ListesActivity.this, ChansonsActivity.class);
                    i.putExtra("info a transmettre", "message");
                    startActivity(i);
                    break;
                case R.id.buttonPlaying:
                    i = new Intent(ListesActivity.this, LectureActivity.class);
                    startActivity(i);
                    break;

            }
        }
    }
}

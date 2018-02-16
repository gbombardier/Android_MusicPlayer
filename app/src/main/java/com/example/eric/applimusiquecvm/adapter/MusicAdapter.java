package com.example.eric.applimusiquecvm.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eric.applimusiquecvm.Chanson;
import com.example.eric.applimusiquecvm.R;

import java.util.List;

/**
 * Created by gabb_ on 2017-12-15.
 */

public class MusicAdapter extends ArrayAdapter<Chanson> {
    private LayoutInflater inflater;

    List<Chanson> chansons;
    public MusicAdapter(@NonNull Context context, List<Chanson> chansons) {
        super(context, R.layout.layout_chanson);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.chansons = chansons;
    }

    @Nullable
    @Override
    public Chanson getItem(int position) {
        return chansons.get(position);
    }

    @Override
    public int getCount() {
        return chansons.size();
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if(convertView == null) {
            view = inflater.inflate(R.layout.layout_chanson, null);
            Holder holder = new Holder();
            holder.nomArtisteTextView = (TextView) view.findViewById(R.id.nomGroupe);
            holder.nomChansonTextView = (TextView) view.findViewById(R.id.nomChanson);
            holder.pochetteImageView = (ImageView) view.findViewById(R.id.imgPochette);
            view.setTag(holder);
        } else {
            view = convertView;
        }

        Holder holder = (Holder) view.getTag();

        Chanson chanson = getItem(position);

        holder.nomArtisteTextView.setText(chanson.getArtiste());
        holder.nomChansonTextView.setText(chanson.getTitre());
        holder.pochetteImageView.setImageBitmap(BitmapFactory.decodeFile(chanson.getPochette()));

        return view;
    }

    private class Holder {
        TextView nomChansonTextView;
        TextView nomArtisteTextView;
        ImageView pochetteImageView;
    }
}

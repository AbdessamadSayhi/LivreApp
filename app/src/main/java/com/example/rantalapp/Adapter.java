package com.example.rantalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rantalapp.Livre;
import com.example.rantalapp.R;

import java.util.List;

public class Adapter extends ArrayAdapter {
    List<Livre> data;

    public Adapter(@NonNull Context context, int resource, @NonNull List<Livre> objects) {
        super(context, resource, objects);

        // Affectation de la liste de Livres à la variable "data"
        data=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Récupération du Livre à afficher pour cet élément
        Livre l = data.get(position);

        // Création d'un objet LayoutInflater pour créer la vue à partir du fichier XML
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        // Création de la vue à partir du fichier XML "item.xml"
        convertView=layoutInflater.inflate(R.layout.item,null,false);

        // Récupération des TextView du fichier "item.xml"
        TextView txtV_title=(TextView) convertView.findViewById(R.id.txtV_title);
        TextView txtV_price=(TextView) convertView.findViewById(R.id.txtV_price);

        // Affichage du titre et du prix du Livre dans les TextView
        txtV_price.setText(Double.toString(l.price));
        txtV_title.setText(l.title);

        // Retour de la vue à afficher
        return convertView;
    }
}
package com.example.rantalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DB_Helper DB = new DB_Helper(this);
        List<Livre> livres =new ArrayList<>();

        Livre l = new Livre(1,"Rich dad poor dad",40);
        livres.add(l);

        l = new Livre(2,"Think and grow rich",35);
        livres.add(l);

        l = new Livre(3,"The 7 Habits of Highly Effective People",45);
        livres.add(l);

        l = new Livre(4,"Atomic Habits",50);
        livres.add(l);

        l = new Livre(5,"The Power of Nowt",30);
        livres.add(l);

        ListView listV = findViewById(R.id.listV_item);

        Adapter adapter = new Adapter(this,R.layout.item,livres);

        listV.setAdapter(adapter);


    }
}
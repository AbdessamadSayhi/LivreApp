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

        Livre l = new Livre(1,"aaaaa",1111);
        livres.add(l);

        l = new Livre(2,"bbbb",222);
        livres.add(l);

        l = new Livre(3,"cccc",333);
        livres.add(l);

        ListView listV=findViewById(R.id.listV_item);

        Adapter adapter=new Adapter(this,R.layout.item,livres);

        listV.setAdapter(adapter);


    }
}
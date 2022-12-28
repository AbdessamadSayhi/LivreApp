package com.example.rantalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText repassword;
    private Button signup;
    private Button signin;
    // Déclaration de l'objet DB_Helper qui nous permettra d'accéder à la base de données
    private DB_Helper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des variables avec leur vue correspondante
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);

        // Initialisation de l'objet DB_Helper
        DB = new DB_Helper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Récupération des valeurs saisies par l'utilisateur
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                // Vérification si aucun champ n'est vide
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)) {
                    // Affiche un message Toast si un champ est vide
                    showToast("Tous les champs sont requis");
                } else {
                    // Vérification si les mots de passe correspondent
                    if (pass.equals(repass)) {
                        // Vérification si le nom d'utilisateur existe déjà dans la base de données
                        boolean checkusername = DB.checkUsername(user);
                        if (!checkusername) {
                            // Tentative d'enregistrement des données dans la base de données
                            boolean insert = DB.insertUser(user, pass);
                            if (insert) {
                                showToast("Enregistré avec succès");
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            } else {
                                showToast("échec de l'enregistrement");
                            }
                        } else {
                            showToast("Le nom existe déjà");
                        }
                    } else {
                        showToast("Les mots de passe ne correspondent pas");
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    //Fonction pour afficher des messages Toast
    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

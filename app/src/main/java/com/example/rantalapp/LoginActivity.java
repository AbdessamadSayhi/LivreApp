package com.example.rantalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button signin;
    DB_Helper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        signin = findViewById(R.id.login_signin);
        DB = new DB_Helper(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                // Vérifie si les champs de nom d'utilisateur et de mot de passe sont vides
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
                    // Affiche un message indiquant que tous les champs sont obligatoires
                    showToast("Tous les champs sont obligatoires");
                } else {
                    // Vérifie si le nom d'utilisateur et le mot de passe sont valides
                    Boolean checkuserpass = DB.checkUsernamePassword(user,pass);
                    if (checkuserpass){
                        // Affiche un message indiquant que la connexion a réussi
                        showToast("Connexion réussie");
                        // Redirige vers l'écran principal
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    } else {
                        // Affiche un message indiquant que la connexion a échoué
                        showToast("échec de la connexion");
                    }
                }
            }
        });

    }

    // Affiche un Toast avec le message spécifié
    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

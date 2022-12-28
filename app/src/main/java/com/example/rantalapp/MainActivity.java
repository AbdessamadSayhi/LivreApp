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
    private DB_Helper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);
        DB = new DB_Helper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)) {
                    showToast("Tous les champs sont requis");
                } else {
                    if (pass.equals(repass)) {
                        boolean checkusername = DB.checkUsername(user);
                        if (!checkusername) {
                            boolean insert = DB.insertData(user, pass);
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

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

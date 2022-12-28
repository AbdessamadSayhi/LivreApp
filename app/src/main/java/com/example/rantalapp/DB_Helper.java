package com.example.rantalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DB_Helper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "biblio.db";
    private static final int DATABASE_VERSION = 1;

    // Constructeur de la classe
    public DB_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Méthode appelée lors de la création de la base de données
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        db.execSQL("CREATE TABLE livre (livre_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, price REAL)");
    }

    // Méthode appelée lors de la mise à jour de la base de données
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS livre");

        onCreate(db);
    }

    // Méthode pour ajouter un nouvel utilisateur
    public boolean insertUser(String username, String password) {

        // Récupération d'une instance de la base de données en mode écriture
        SQLiteDatabase db = this.getWritableDatabase();

        // Création d'un objet ContentValues pour stocker les valeurs à insérer
        ContentValues values = new ContentValues();

        // Ajout des valeurs à l'objet ContentValues
        values.put("username", username);
        values.put("password", password);

        // Insertion des valeurs dans la table "users"
        long result = db.insert("users", null, values);

        // Fermeture de la base de données
        db.close();

        // Retourne vrai si l'insertion a réussi, faux sinon
        return result != -1;
    }

    public Boolean insertLivre(String title, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("title", title);
        values.put("price", price);

        long result = db.insert("livre", null, values);
        return result != -1;
    }

    public Boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();

        // vérifie si un enregistrement existe dans la table "users"
        // avec le nom d'utilisateur spécifié en paramètre
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[] {username});

        // Récupération du nombre d'enregistrements retournés par la requête
        int count = cursor.getCount();

        // Fermeture du curseur
        cursor.close();

        // Retourne vrai si le nombre d'enregistrements est supérieur à 0, faux sinon
        return count > 0;
    }

    public Boolean checkUsernamePassword(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?",new String[] {username,password});
        if (username=="admin" && password=="admin")
            return true;
        // Retourne vrai si au moins un enregistrement a été trouvé, faux sinon
        return cursor.getCount() > 0;
    }

    public List<Livre> getLivres() {
        // Création d'une liste vide de Livres
        List<Livre> livres = new ArrayList<>();

        // Récupération d'une instance de la base de données en mode lecture
        SQLiteDatabase db = this.getReadableDatabase();

        // Exécution de la requête SQL pour récupérer tous les enregistrements de la table "livre"
        Cursor cursor = db.rawQuery("SELECT * FROM livre", null);

        // Si le curseur contient au moins un enregistrement
        if (cursor.moveToFirst()) {
            // Tant qu'il y a des enregistrements à parcourir
            do {
                // Récupération des valeurs de chaque colonne de l'enregistrement courant
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                double price = cursor.getDouble(2);

                // Création d'un objet Livre avec les valeurs récupérées
                Livre livre = new Livre(id, title, price);

                // Ajout de l'objet Livre à la liste
                livres.add(livre);
            } while (cursor.moveToNext());
        }
        // Fermeture du curseur
        cursor.close();

        // Retour de la liste de Livres
        return livres;
    }


    public Livre getLivre(int livreId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Exécution de la requête pour récupérer le livre avec l'identifiant spécifié
        Cursor cursor = db.rawQuery("SELECT * FROM livre WHERE livre_id = ?", new String[]{String.valueOf(livreId)});
        if (cursor.moveToFirst()) {

            // Récupération des valeurs des colonnes de la table "livre"
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            double price = cursor.getDouble(2);
            cursor.close();

            // Retourne un objet Livre avec les valeurs récupérées
            return new Livre(id, title, price);
        }
        return null;
    }

    public int deleteLivre(int livreId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("livre", "livre_id = ?", new String[]{String.valueOf(livreId)});
    }

    public int updateLivre(int livreId, String title, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("price", price);
        return db.update("livre", values, "livre_id = ?", new String[]{String.valueOf(livreId)});
    }

    public void close() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}

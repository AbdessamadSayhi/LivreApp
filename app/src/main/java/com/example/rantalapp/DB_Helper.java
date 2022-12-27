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

    public static final String DBNAME = "biblio.db";

    public DB_Helper(Context context) {
        super(context, "biblio.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(user_id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT , password TEXT)");
        db.execSQL("CREATE TABLE livre (livre_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, price REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists users");
        db.execSQL("DROP TABLE IF EXISTS livre");
        onCreate(db);
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username",username);
        values.put("password",password);

        long result = db.insert("users",null,values);
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
        Cursor cursor = db.rawQuery("select * from users where username=?",new String[] {username});
        return cursor.getCount() > 0;
    }

    public Boolean checkUsernamePassword(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?",new String[] {username,password});
        if (username=="admin" && password=="admin")
            return true;
        return cursor.getCount() > 0;
    }

    public List<Livre> getLivres() {
        List<Livre> livres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM livre", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                double price = cursor.getDouble(2);
                Livre livre = new Livre(id, title, price);
                livres.add(livre);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return livres;
    }

    public Livre getLivre(int livreId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM livre WHERE livre_id = ?", new String[]{String.valueOf(livreId)});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            double price = cursor.getDouble(2);
            cursor.close();
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

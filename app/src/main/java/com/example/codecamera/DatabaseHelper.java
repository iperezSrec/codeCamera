package com.example.codecamera;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    String DbName;
    String DbPath;
    Context context;


    public DatabaseHelper(Context mcontext, String name, int version) {
        super(mcontext, name, null, version);

        this.context = mcontext;
        DbName = "trabajadores.db";
        DbPath = "/data/data/" + context.getPackageName() + "/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CheckDatabase(){
        try{
            String path = DbPath + DbName;
            SQLiteDatabase.openDatabase(path, null, 0);
        }catch (Exception e){}

        this.getReadableDatabase();
        CopyDatabase();
    }

    public void CopyDatabase(){
        try{
            InputStream io = context.getAssets().open(DbName);

            String oufilename = DbPath + DbName;
            OutputStream outputStream = new FileOutputStream(oufilename);
            int lenght;
            byte[] buffer = new byte[1024];
            while ((lenght = io.read(buffer)) > 0){
                outputStream.write(buffer, lenght, 0);
            }

            io.close();
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){}
    }

    public void OpenDatabase(){
        String path = DbPath + DbName;
        SQLiteDatabase.openDatabase(path, null, 0);
    }
}

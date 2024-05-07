package com.example.codecamera.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.codecamera.api.UserData;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "trabajadores.db";
    private static final int DB_VERSION = 1;

    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void checkAndRecreateTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, role TEXT, phId TEXT)");
        db.close();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://client.pre.srec.solutions/v1/oasis/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DatabaseService apiService = retrofit.create(DatabaseService.class);

        Call<List<UserData>> call = apiService.getUserList();
        call.enqueue(new Callback<List<UserData>>() {
            @Override
            public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                if (response.isSuccessful()) {
                    List<UserData> userList = response.body();
                    for (UserData user : userList) {
                        insertUser(user.getName(), user.getRole(), user.getPhId());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserData>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void insertUser(String name, String role, String phId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("role", role);
        values.put("phId", phId);
        db.insert("usuarios", null, values);
        db.close();
    }
}

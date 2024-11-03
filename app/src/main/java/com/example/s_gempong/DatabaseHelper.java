package com.example.s_gempong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDB"; // Nama database
    private static final int DATABASE_VERSION = 1; // Versi database
    private static final String TABLE_USERS = "users";

    // Kolom tabel
    private static final String USER_COL_1 = "EMAIL"; // Kolom untuk email
    private static final String USER_COL_2 = "NAME";  // Kolom untuk nama
    private static final String USER_COL_3 = "PASSWORD"; // Kolom untuk password

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Membuat tabel users
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                USER_COL_1 + " TEXT PRIMARY KEY, " +
                USER_COL_2 + " TEXT, " +
                USER_COL_3 + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Menghapus tabel yang sudah ada jika perlu
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Menambah pengguna baru
    public boolean addUser(String name, String email, String password) {
        if (isEmailRegistered(email)) {
            Log.e("DatabaseHelper", "Email sudah terdaftar: " + email);
            return false; // Email sudah terdaftar
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_1, email);
        contentValues.put(USER_COL_2, name);
        contentValues.put(USER_COL_3, password);

        long result = db.insert(TABLE_USERS, null, contentValues);
        if (result == -1) {
            Log.e("DatabaseHelper", "Gagal menambah pengguna: " + email);
        }
        return result != -1;
    }

    // Memeriksa pengguna saat login
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;

        try {
            cursor = db.query(TABLE_USERS, new String[]{USER_COL_1, USER_COL_3},
                    USER_COL_1 + "=? AND " + USER_COL_3 + "=?", new String[]{email, password},
                    null, null, null);

            exists = cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if (!exists) {
            Log.e("DatabaseHelper", "Login gagal untuk pengguna: " + email);
        }

        return exists;
    }

    // Memeriksa jika email sudah terdaftar
    public boolean isEmailRegistered(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;

        try {
            cursor = db.query(TABLE_USERS, new String[]{USER_COL_1}, USER_COL_1 + "=?",
                    new String[]{email}, null, null, null);

            exists = cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return exists;
    }

    // Menutup database
    public void closeDatabase() {
        this.close();
    }
}

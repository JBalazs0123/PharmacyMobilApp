package com.example.pharmacymobilapp.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pharmacymobilapp.model.Pharmacy;

import java.util.ArrayList;
import java.util.List;

public class PharmacyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pharmacy.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PHARMACIES = "pharmacies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_OPENING_HOURS = "opening_hours";

    public PharmacyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PHARMACIES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_OPENING_HOURS + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHARMACIES);
        onCreate(db);
    }

    public void addPharmacy(Pharmacy pharmacy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, pharmacy.getName());
        values.put(COLUMN_ADDRESS, pharmacy.getAddress());
        values.put(COLUMN_OPENING_HOURS, pharmacy.getOpeningHours());

        db.insert(TABLE_PHARMACIES, null, values);
        db.close();
    }

    public List<Pharmacy> getAllPharmacies() {
        List<Pharmacy> pharmacyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PHARMACIES, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Pharmacy pharmacy = new Pharmacy(
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_OPENING_HOURS)));
                pharmacyList.add(pharmacy);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pharmacyList;
    }

    public void updatePharmacy(String name, Pharmacy pharmacy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, pharmacy.getName());
        values.put(COLUMN_ADDRESS, pharmacy.getAddress());
        values.put(COLUMN_OPENING_HOURS, pharmacy.getOpeningHours());

        db.update(TABLE_PHARMACIES, values, COLUMN_NAME + "=?", new String[]{name});
        db.close();
    }

    public void deletePharmacy(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PHARMACIES, COLUMN_NAME + "=?", new String[]{name});
        db.close();
    }
}
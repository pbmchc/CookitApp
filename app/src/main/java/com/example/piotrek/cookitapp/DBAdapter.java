package com.example.piotrek.cookitapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import java.sql.Blob;
import java.sql.SQLException;

/**
 * Created by Piotrek on 2015-01-03.
 */
public class DBAdapter {

    private static final String TAG = "DbAdapter";

    public static final String KEY_ROWID = "_id";
    public static final String KEY_TITLE = "tytul";
    public static final String KEY_INGRED = "skladniki";
    public static final String KEY_STEPS = "etapy";
    public static final String KEY_PHOTO = "zdjecie";
    public static final String KEY_DATE = "data";
    public static final String KEY_FAV = "ulubiony";

    public static String [] ALL_KEYS = new String[] {KEY_ROWID,KEY_TITLE,
    KEY_INGRED, KEY_STEPS, KEY_PHOTO, KEY_DATE, KEY_FAV};

    public static final int COL_ROWID = 0;
    public static final int COL_TITLE = 1;
    public static final int COL_INGRED = 2;
    public static final int COL_STEPS = 3;
    public static final int COL_PHOTO = 4;
    public static final int COL_DATE = 5;
    public static final int COL_FAV = 6;

    public static final String DATABASE_NAME = "Recipes";
    public static final String DATABASE_TABLE = "mainRecipes";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_SQL =
            "CREATE TABLE " + DATABASE_TABLE
            + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TITLE + " TEXT NOT NULL, " + KEY_INGRED + " TEXT NOT NULL, "
            + KEY_STEPS + " TEXT NOT NULL, " + KEY_PHOTO + " BLOB, " + KEY_DATE + " TEXT, " + KEY_FAV + " INTEGER" + ");";

    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }
    public DBAdapter open() throws SQLException
    {
        db = myDBHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        myDBHelper.close();
    }

    public long insertRow(String tytul, String skladniki, String etapy, byte[] zdjecie, String data, int ulubiony)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, tytul);
        initialValues.put(KEY_INGRED, skladniki);
        initialValues.put(KEY_STEPS, etapy);
        initialValues.put(KEY_PHOTO, zdjecie);
        initialValues.put(KEY_DATE, data);
        initialValues.put(KEY_FAV, ulubiony);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteRow (long rowID)
    {
        String where = KEY_ROWID + "=" + rowID;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll()
    {
        Cursor cursor = getAllRows();
        long rowID = cursor.getColumnIndexOrThrow(KEY_ROWID);
        if (cursor.moveToFirst())
        {
            do {
                deleteRow(cursor.getLong((int) rowID));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
    }

    public Cursor getAllRows()
    {
        String where = null;
        Cursor cursor = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null, null, null, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getRow(long rowID)
    {
        String where = KEY_ROWID + "=" + rowID;
        Cursor cursor = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null, null, null, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean updateRow (long rowID, String tytul, String skladniki, String etapy, byte[] zdjecie, String data, int ulubiony)
    {
        String where = KEY_ROWID + "=" + rowID;
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TITLE, tytul);
        newValues.put(KEY_INGRED, skladniki);
        newValues.put(KEY_STEPS, etapy);
        newValues.put(KEY_PHOTO, zdjecie);
        newValues.put(KEY_DATE, data);
        newValues.put(KEY_FAV, ulubiony);

        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super (context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }
        public void onUpgrade(SQLiteDatabase _db, int oldV, int newV)
        {
            Log.w(TAG, "Upgrading app database version from " + oldV + " to " + newV);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(_db);
        }

    }
        }










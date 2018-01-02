package com.example.nayempaiker.notetaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nayempaiker on 8/24/17.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="NoteTaker.db";

    //Table 1 : Storing notes
    public static final String TABLE_NAME="noteList";
    public static final String COL_1="id";
    public static final String COL_2="title";
    public static final String COL_3="details";
    public static final String COL_4="timestamp";

    //Table 2: Storing images.
    public static final String TABLE_NAME_2="Images";
    public static final String COL_2_1="id";
    public static final String COL_2_2="imagePath";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //query for notes
        String query1 = "CREATE TABLE " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT NOT NULL, " +
                COL_3 + " TEXT NOT NULL, " + COL_4 + " TEXT NOT NULL" + ");";

        //query for imagePath
        String query2 = "CREATE TABLE " + TABLE_NAME_2 + " (" + COL_2_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2_2 + " TEXT NOT NULL);";

        //run queries in database
        sqLiteDatabase.execSQL(query1);
        sqLiteDatabase.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //table 1
        String query1 = "DROP TABLE ID EXISTS " + TABLE_NAME;

        //table 2
        String query2 = "DROP TABLE ID EXISTS " + TABLE_NAME_2;

        //run query
        sqLiteDatabase.execSQL(query1);
        sqLiteDatabase.execSQL(query2);

        onCreate(sqLiteDatabase);
    }

    public  boolean insertData(String title, String desc)
    {
        //Get the time and date
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String nowDate = format.format(date);
        //---------------------
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, title);
        cv.put(COL_3, desc);
        cv.put(COL_4, nowDate);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }

    public  boolean insertImage(String path)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2_2, path);

        long result = db.insert(TABLE_NAME_2, null, cv);

        if (result == -1)
            return false;
        else
            return true;
    }


    public Cursor getAllImage()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME_2, null);
        return result;
    }

    public Cursor detailsFromTitle(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 +" = ?" + title, null);
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }
}

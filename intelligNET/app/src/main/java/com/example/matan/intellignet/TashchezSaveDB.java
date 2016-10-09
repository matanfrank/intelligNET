package com.example.matan.intellignet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
/**
 * Created by mtnfr on 24/08/2016.
 */
public class TashchezSaveDB extends SQLiteOpenHelper {
    // Database Name
    public static String DATABASE_NAME = "student_database";

    // Current version of database
    private static final int DATABASE_VERSION = 2;

    // Name of table
    private static final String TABLE_TASHCHEZS = "tashchezs";

    // All Keys used in table
//    public static final String KEY_ID = "_id";
    public static final String KEY_USER = "_name";
    public static final String KEY_TYPE = "_type";
    public static final String KEY_INDEX = "_index";
    public static final String KEY_CONTENT = "_content";


    public static final String TYPE = "tashchez";

    public static String TAG = "tag";

    private static final String CREATE_TABLE_TASHCHEZS = "CREATE TABLE " + TABLE_TASHCHEZS + "(" + KEY_USER + " TEXT, " + KEY_TYPE + " TEXT, " + KEY_INDEX + " INT, " + KEY_CONTENT + " TEXT, "
            + "PRIMARY KEY(" + KEY_USER + ", " + KEY_TYPE + ", " +  KEY_INDEX + "));";
    private Cursor allStudentsCursor;


    public TashchezSaveDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASHCHEZS);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASHCHEZS + ";"); // drop table if exists
        onCreate(db);
    }

    public void add (String user,String type, int index,String content){
        //check if exist already need to erase it

        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("INSERT OR IGNORE INTO " +  TABLE_TASHCHEZS + " (" + KEY_USER + ", " + KEY_TYPE + ", " + KEY_INDEX + ", " + KEY_CONTENT + ") VALUES ('" + user + "', '" + type + "', " + index + ", '" + content + "');");
        db.execSQL("UPDATE " + TABLE_TASHCHEZS + " SET " + KEY_CONTENT + " ='" + content + "'  WHERE " + KEY_USER + "='" + user + "' AND " + KEY_TYPE + "='" + type + "' AND " +KEY_INDEX + "=" + index + ";");
    }

    public void remove (String key){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("TAG", "-" + key + "-");
        db.delete(TABLE_TASHCHEZS, KEY_USER +"= '"+key + "'",null);
    }

    public String getTashchez(String user, String type, int index) {
        final String selectQuery = "SELECT " + KEY_CONTENT +  " FROM "  + TABLE_TASHCHEZS + " WHERE " + KEY_USER + "='"
                + user + "' AND " + KEY_TYPE + "='" + type + "' AND " + KEY_INDEX + "=" + index;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        Log.d("12121212", "dsd" + c.getColumnIndex(KEY_CONTENT));
        if (c != null && c.moveToFirst()){//c.getColumnIndex(KEY_CONTENT) >= 0 && c.getString(c.getColumnIndex(KEY_CONTENT)) != null) {
            c.moveToFirst();
            return c.getString(c.getColumnIndex(KEY_CONTENT));
        }
        else
            return "";
    }


}

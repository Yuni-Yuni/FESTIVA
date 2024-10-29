package com.example.festiva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "fistiva_secondTry.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "events";
    private static final String EVENT_ID = "_id";
    private static final String EVENT_NAME = "event_name";
    private static final String EVENT_DESCRIPTION = "event_description";
    private static final String EVENT_DATA = "event_data";
    private static final String EVENT_START_TIME = "event_start_time";
    private static final String EVENT_END_TIME = "event_end_time";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        EVENT_NAME + " TEXT, " + EVENT_DESCRIPTION + " TEXT, " +
                        EVENT_DATA + " TEXT, " + EVENT_START_TIME + " TEXT, " +
                        EVENT_END_TIME + " TEXT);"; //INTEGER
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void addEvent(String title, String description, String data, String start_time, String end_time){ //int
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EVENT_NAME, title);
        cv.put(EVENT_DESCRIPTION, description);
        cv.put(EVENT_DATA, data);
        cv.put(EVENT_START_TIME, start_time);
        cv.put(EVENT_END_TIME, end_time);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}

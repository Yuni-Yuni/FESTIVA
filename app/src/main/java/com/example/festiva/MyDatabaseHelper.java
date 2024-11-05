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
    private static final String DATABASE_NAME = "FESTIVA_1.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "events";
    private static final String EVENT_ID = "_id";
    private static final String EVENT_NAME = "event_name";
    private static final String EVENT_DESCRIPTION = "event_description";
    private static final String EVENT_DATA_DATA = "event_data_data";
    private static final String EVENT_DATA_MONTH = "event_data_month";
    private static final String EVENT_DATA_YEAR = "event_data_year";
    private static final String EVENT_START_TIME_HOUR = "event_start_time_hour";
    private static final String EVENT_START_TIME_MINUTE = "event_start_time_minute";
    private static final String EVENT_END_TIME_HOUR = "event_end_time_hour";
    private static final String EVENT_END_TIME_MINUTE = "event_end_time_minute";


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
                        EVENT_DATA_DATA + " INTEGER, " + EVENT_DATA_MONTH + " INTEGER, " +
                        EVENT_DATA_YEAR + " INTEGER, " + EVENT_START_TIME_HOUR + " INTEGER, " +
                        EVENT_START_TIME_MINUTE + " INTEGER, " + EVENT_END_TIME_HOUR + " INTEGER, " + EVENT_END_TIME_MINUTE + " INTEGER);"; //INTEGER
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addEvent(String title, String description, int data_data, int data_month, int data_year, int start_time_hour,
                                            int start_time_minute, int end_time_hour, int end_time_minute){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EVENT_NAME, title);
        cv.put(EVENT_DESCRIPTION, description);
        cv.put(EVENT_DATA_DATA, data_data);
        cv.put(EVENT_DATA_MONTH, data_month);
        cv.put(EVENT_DATA_YEAR, data_year);
        cv.put(EVENT_START_TIME_HOUR, start_time_hour);
        cv.put(EVENT_START_TIME_MINUTE, start_time_minute);
        cv.put(EVENT_END_TIME_HOUR, end_time_hour);
        cv.put(EVENT_END_TIME_MINUTE, end_time_minute);

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

    Cursor readAllDataOnSelectedDate(int selectedYear, int selectedMonth, int selectedDay){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EVENT_DATA_DATA + " = " + selectedDay + " AND "
                                    + EVENT_DATA_MONTH + " = " + selectedMonth + " AND " + EVENT_DATA_YEAR + " = " + selectedYear + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
            //Toast.makeText(context, "cursor = " + cursor.getCount(), Toast.LENGTH_LONG).show();

        }
        return cursor;
    }
}
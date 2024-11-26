package com.example.festiva;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FESTIVA_3_0.db";
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
    private static final String EVENT_REMINDER = "event_reminder";
    private static final String EVENT_GREETING_ID = "event_greeting_id";

    private static final String TABLE_NAME_GREETINGS = "greeting";
    private static final String GREETING_ID = "greeting_id";
    private static final String GREETING_NAME = "greeting_name";
    private static final String GREETING_FROM_WHO = "greeting_from_who";
    private static final String GREETING_TO_WHO = "greeting_to_who";
    private static final String GREETING_TEXT = "greeting_text";


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
                        EVENT_START_TIME_MINUTE + " INTEGER, " + EVENT_END_TIME_HOUR + " INTEGER, "
                        + EVENT_END_TIME_MINUTE + " INTEGER, " + EVENT_REMINDER + " INTEGER, " +
                        EVENT_GREETING_ID + " INTEGER);";
        db.execSQL(query);
        query =
                "CREATE TABLE " + TABLE_NAME_GREETINGS +
                        " (" + GREETING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        GREETING_NAME + " TEXT, " + GREETING_FROM_WHO + " TEXT, " +
                        GREETING_TO_WHO + " TEXT, " + GREETING_TEXT + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GREETINGS);
        onCreate(db);
    }

    void addGreeting(String greeting_name, String greeting_from_who,
                     String greeting_to_who){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(GREETING_NAME, greeting_name);
        cv.put(GREETING_FROM_WHO, greeting_from_who);
        cv.put(GREETING_TO_WHO, greeting_to_who);

        db.insert(TABLE_NAME_GREETINGS, null, cv);
    }

    public Cursor getGreeting(String greeting_name, String greeting_from_who, String greeting_to_who) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT greeting_id FROM greeting WHERE greeting_name = ? AND greeting_from_who = ? AND greeting_to_who = ?",
                new String[]{greeting_name, greeting_from_who, greeting_to_who});
    }

    public Cursor getGreetingBuID(String greeting_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM greeting WHERE greeting_id = ?", new String[]{greeting_id});
    }

    void updateGreeting(String greeting_id, String greeting_text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(GREETING_TEXT, greeting_text);

        db.update(TABLE_NAME_GREETINGS, cv, "greeting_id=?", new String[]{greeting_id});
    }

    void addEvent(String title, String description, int data_data, int data_month, int data_year, int start_time_hour,
                                            int start_time_minute, int end_time_hour, int end_time_minute, int reminder, int event_greeting_id){
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
        cv.put(EVENT_REMINDER, reminder);
        cv.put(EVENT_GREETING_ID, event_greeting_id);

        db.insert(TABLE_NAME, null, cv);
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
                                    + EVENT_DATA_MONTH + " = " + selectedMonth + " AND " + EVENT_DATA_YEAR + " = " + selectedYear +
                " ORDER BY " + EVENT_START_TIME_HOUR + ", " + EVENT_START_TIME_MINUTE + ", " + EVENT_END_TIME_HOUR + ", " + EVENT_END_TIME_HOUR + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
            //Toast.makeText(context, "cursor = " + cursor.getCount(), Toast.LENGTH_LONG).show();
        }
        return cursor;
    }

    Cursor readDataForThisMonthFuture(int startTimeHour, int startTimeMinute, int selectedYear, int selectedMonth, int selectedDay){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EVENT_DATA_MONTH + " = " + selectedMonth + " AND " +
                EVENT_DATA_YEAR + " = " + selectedYear + " AND (" +  EVENT_DATA_DATA + " > " + selectedDay + " OR (" +
                EVENT_DATA_DATA + " = " + selectedDay + " AND (" + EVENT_START_TIME_HOUR + " > " + startTimeHour + " OR (" +
                EVENT_START_TIME_HOUR + " = " + startTimeHour + " AND " + EVENT_START_TIME_MINUTE + " >= " + startTimeMinute + ")))) " +
                "ORDER BY " + EVENT_DATA_YEAR + ", " + EVENT_DATA_MONTH + ", " + EVENT_DATA_DATA + ", " + EVENT_START_TIME_HOUR + ", " + EVENT_START_TIME_MINUTE + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readDataForThisMonthPast(int startTimeHour, int startTimeMinute, int selectedYear, int selectedMonth, int selectedDay){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EVENT_DATA_MONTH + " = " + selectedMonth + " AND " +
                EVENT_DATA_YEAR + " = " + selectedYear + " AND (" +  EVENT_DATA_DATA + " < " + selectedDay + " OR (" +
                EVENT_DATA_DATA + " = " + selectedDay + " AND (" + EVENT_START_TIME_HOUR + " < " + startTimeHour + " OR (" +
                EVENT_START_TIME_HOUR + " = " + startTimeHour + " AND " + EVENT_START_TIME_MINUTE + " < " + startTimeMinute + ")))) " +
                "ORDER BY " + EVENT_DATA_YEAR + ", " + EVENT_DATA_MONTH + ", " + EVENT_DATA_DATA + ", " + EVENT_START_TIME_HOUR + ", " + EVENT_START_TIME_MINUTE + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    void updateData(String row_id, String title, String description, int data_data, int data_month, int data_year, int start_time_hour,
                    int start_time_minute, int end_time_hour, int end_time_minute, int reminder, int greeting_ID){
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

        cv.put(EVENT_REMINDER, reminder);
        cv.put(EVENT_GREETING_ID, greeting_ID);

        db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
    }

    public Cursor getEventsForWeek(Calendar startOfWeek, Calendar endOfWeek) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Дата начала и конца недели
        int startDay = startOfWeek.get(Calendar.DAY_OF_MONTH);
        int startMonth = startOfWeek.get(Calendar.MONTH) + 1; // Месяцы в Calendar начинаются с 0
        int startYear = startOfWeek.get(Calendar.YEAR);
        int endDay = endOfWeek.get(Calendar.DAY_OF_MONTH);
        int endMonth = endOfWeek.get(Calendar.MONTH) + 1;
        int endYear = endOfWeek.get(Calendar.YEAR);

        // Запрос для выборки событий, попадающих в диапазон дат
        /*String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EVENT_DATA_YEAR + " = ? AND " +
                EVENT_DATA_MONTH + " = ? AND " + EVENT_DATA_DATA + " >= ?";*/
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EVENT_DATA_YEAR + " = ? AND " +
                EVENT_DATA_MONTH + " = ? AND " + EVENT_DATA_DATA + " >= ?" + " AND " + EVENT_DATA_DATA +
                " <= ? + 6 OR " + EVENT_DATA_YEAR + " = ? AND " +
                EVENT_DATA_MONTH + " = ? AND " + EVENT_DATA_DATA + " <= ?" + " AND " + EVENT_DATA_DATA +
                " >= ? - 6 ORDER BY " + EVENT_DATA_YEAR + ", " + EVENT_DATA_MONTH + ", " + EVENT_DATA_DATA + ", "
                + EVENT_START_TIME_HOUR + ", " + EVENT_START_TIME_MINUTE + ", " + EVENT_END_TIME_HOUR + ", "
                + EVENT_END_TIME_MINUTE + ";";
        String[] args = {
                String.valueOf(startYear), String.valueOf(startMonth), String.valueOf(startDay), String.valueOf(startDay),
                String.valueOf(endYear), String.valueOf(endMonth), String.valueOf(endDay), String.valueOf(endDay)
        };

        Log.e("query", query);


        //Toast.makeText(context, startDay + " | " + endDay, Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, startMonth + " | " + endMonth, Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, startYear + " | " + endYear, Toast.LENGTH_SHORT).show();

        return db.rawQuery(query, args);
    }

    public Cursor getEventById(int eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM events WHERE _id = ?", new String[]{String.valueOf(eventId)});
    }

    public Cursor getEventAndGreeting() {
        String query = "SELECT * FROM " + TABLE_NAME + " INNER JOIN " + TABLE_NAME_GREETINGS + " ON " + TABLE_NAME + "." +
                EVENT_GREETING_ID + " = " + TABLE_NAME_GREETINGS + "." +  GREETING_ID + " WHERE " + EVENT_GREETING_ID + " != 0;";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


}
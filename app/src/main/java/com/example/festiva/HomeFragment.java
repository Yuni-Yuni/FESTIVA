package com.example.festiva;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    MyDatabaseHelper myDB;
    ArrayList<String> event_id, event_title, event_description, event_data_data, event_data_month, event_data_year, event_startTime_hour,
                            event_startTime_minute, event_endTime_hour, event_endTime_minute;
    ArrayList<Integer> event_reminder, event_greeting_id;
    CustomAdapter customAdapter;

    ImageView imageView;
    TextView textEmpty;
    boolean statement = false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        CalendarView calendarView = (CalendarView) rootView.findViewById(R.id.calendarView2);

        //=====================================

        RecyclerView recyclerView = rootView.findViewById(R.id.listOfEvents);

        myDB = new MyDatabaseHelper(getContext());
        event_id = new ArrayList<>();
        event_title = new ArrayList<>();
        event_description = new ArrayList<>();
        event_data_data = new ArrayList<>();
        event_data_month = new ArrayList<>();
        event_data_year = new ArrayList<>();
        event_startTime_hour = new ArrayList<>();
        event_startTime_minute = new ArrayList<>();
        event_endTime_hour = new ArrayList<>();
        event_endTime_minute = new ArrayList<>();
        event_reminder = new ArrayList<>();
        event_greeting_id = new ArrayList<>();

        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        storeDataOnCurrentDate(year, month + 1, day);

        customAdapter = new CustomAdapter(getActivity(), getContext(), event_id, event_title, event_description, event_data_data, event_data_month,
                                event_data_year, event_startTime_hour, event_startTime_minute, event_endTime_hour, event_endTime_minute, event_reminder, event_greeting_id);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //=====================================

        imageView = (ImageView) rootView.findViewById(R.id.emptyField);
        textEmpty = (TextView) rootView.findViewById(R.id.emptyText);
        ImageNoDataAppearence(statement);

        //=====================================

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                storeDataInArraysOnSelectedDate(year, month + 1, dayOfMonth);
                ImageNoDataAppearence(statement);
            }
        });

        return rootView;
    }

    void storeDataOnCurrentDate(int selectedYear, int selectedMonth, int selectedDay){
        Cursor cursor = myDB.readAllDataOnSelectedDate(selectedYear, selectedMonth, selectedDay);
        if(cursor.getCount() == 0){
            //Toast.makeText(getContext(),"No data.", Toast.LENGTH_SHORT).show();
            statement = true;
        }else{
            statement = false;
            while (cursor.moveToNext()){
                event_id.add(cursor.getString(0));
                event_title.add(cursor.getString(1));
                event_description.add(cursor.getString(2));
                event_data_data.add(cursor.getString(3));
                event_data_month.add(cursor.getString(4));
                event_data_year.add(cursor.getString(5));
                event_startTime_hour.add(cursor.getString(6));
                if (Objects.equals(cursor.getString(7), "0")){
                    event_startTime_minute.add("00");
                } else if (cursor.getInt(7) < 10){
                    event_startTime_minute.add("0" + cursor.getString(7));
                } else {
                    event_startTime_minute.add(cursor.getString(7));
                }
                event_endTime_hour.add(cursor.getString(8));
                if (Objects.equals(cursor.getString(9), "0")){
                    event_endTime_minute.add("00");
                } else if (cursor.getInt(9) < 10){
                    event_endTime_minute.add("0" + cursor.getString(9));
                } else {
                    event_endTime_minute.add(cursor.getString(9));
                }
                event_reminder.add(cursor.getInt(10));
                event_greeting_id.add(cursor.getInt(11));
                //Toast.makeText(getContext(), String.valueOf(event_reminder), Toast.LENGTH_SHORT).show();
            }

        }
    }

    void storeDataInArraysOnSelectedDate(int selectedYear, int selectedMonth, int selectedDay){
        customAdapter.deleteData();
        Cursor cursor = myDB.readAllDataOnSelectedDate(selectedYear, selectedMonth, selectedDay);
        if(cursor.getCount() == 0){
            statement = true;
        }else{
            statement = false;
            while (cursor.moveToNext()){
                event_id.add(cursor.getString(0));
                event_title.add(cursor.getString(1));
                event_description.add(cursor.getString(2));
                event_data_data.add(cursor.getString(3));
                event_data_month.add(cursor.getString(4));
                event_data_year.add(cursor.getString(5));
                event_startTime_hour.add(cursor.getString(6));
                if (Objects.equals(cursor.getString(7), "0")){
                    event_startTime_minute.add("00");
                } else if (cursor.getInt(7) < 10){
                    event_startTime_minute.add("0" + cursor.getString(7));
                } else {
                    event_startTime_minute.add(cursor.getString(7));
                }
                event_endTime_hour.add(cursor.getString(8));
                if (Objects.equals(cursor.getString(9), "0")){
                    event_endTime_minute.add("00");
                } else if (cursor.getInt(9) < 10){
                    event_endTime_minute.add("0" + cursor.getString(9));
                } else {
                    event_endTime_minute.add(cursor.getString(9));
                }
                event_reminder.add(cursor.getInt(10));
                event_greeting_id.add(cursor.getInt(11));
            }
        }

        customAdapter.updateData(event_id, event_title, event_description, event_data_data, event_data_month, event_data_year,
                            event_startTime_hour, event_startTime_minute, event_endTime_hour, event_endTime_minute, event_reminder, event_greeting_id);
        customAdapter.notifyDataSetChanged();
    }

    void ImageNoDataAppearence(boolean statement){
        if (statement){
        imageView.setVisibility(View.VISIBLE);
        textEmpty.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
            textEmpty.setVisibility(View.GONE);
        }
    }

}
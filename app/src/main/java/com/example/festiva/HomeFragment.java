package com.example.festiva;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    MyDatabaseHelper myDB;
    ArrayList<String> event_id, event_title, event_description, event_data_data, event_data_month, event_data_year, event_startTime_hour,
                            event_startTime_minute, event_endTime_hour, event_endTime_minute;
    CustomAdapter customAdapter;

    View newView;

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

        storeDataInArrays();

        customAdapter = new CustomAdapter(getContext(), event_id, event_title, event_description, event_data_data, event_data_month,
                                event_data_year, event_startTime_hour, event_startTime_minute, event_endTime_hour, event_endTime_minute);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //=====================================


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                String selectedDate = new StringBuilder().append(mDay)
                        .append("-").append(mMonth + 1).append("-").append(mYear)
                        .append(" ").toString();
                Toast.makeText(getActivity().getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();
            }
        });/**/

        // Inflate the layout for this fragment
        return rootView;
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(getContext(),"No data.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                event_id.add(cursor.getString(0));
                event_title.add(cursor.getString(1));
                event_description.add(cursor.getString(2));
                event_data_data.add(cursor.getString(3));
                event_data_month.add(cursor.getString(4));
                event_data_year.add(cursor.getString(5));
                event_startTime_hour.add(cursor.getString(6));
                event_startTime_minute.add(cursor.getString(7));
                event_endTime_hour.add(cursor.getString(8));
                event_endTime_minute.add(cursor.getString(9));
            }
        }
    }

}
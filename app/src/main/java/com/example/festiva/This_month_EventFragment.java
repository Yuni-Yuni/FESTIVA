package com.example.festiva;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.festiva.databinding.FragmentThisMonthEventBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link This_month_EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class This_month_EventFragment extends Fragment {

    ImageView imageView;
    TextView textEmpty;
    boolean statement = false;

    SharedPreferences.Editor editor;

    MyDatabaseHelper myDB;
    ArrayList<String> event_id, event_title, event_description, event_data_data, event_data_month, event_data_year, event_startTime_hour,
            event_startTime_minute, event_endTime_hour, event_endTime_minute;
    AdapterForMonth customAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public This_month_EventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment This_month_EventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static This_month_EventFragment newInstance(String param1, String param2) {
        This_month_EventFragment fragment = new This_month_EventFragment();
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

        View view = inflater.inflate(R.layout.fragment_this_month__event, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ImageButton homeButton = view.findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("lastOrFuture", false);
                editor.apply();
                MoreFragment moreFragment = new MoreFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, moreFragment);
                transaction.commit();
            }
        });

        MaterialButton last = view.findViewById(R.id.last);
        MaterialButton future = view.findViewById(R.id.future);

        RecyclerView recyclerView = view.findViewById(R.id.listOfEventsForMonth);

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

        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        int hour = currentDate.get(Calendar.HOUR_OF_DAY);
        int minute = currentDate.get(Calendar.MINUTE);

        boolean lastOrFuture = sharedPreferences.getBoolean("lastOrFuture", false);

        if (lastOrFuture){
            //readDataForThisMonthPastForLoad(hour, minute, year, month + 1, day);
            last.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.text_date));
            last.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.page_color));
            future.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.transparent));
            future.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.buttons_color));
            readDataForThisMonthPastForLoad(hour, minute, year, month + 1, day);
        } else {
            //readDataForThisMonthFutureForLoad(hour, minute, year, month + 1, day);
            future.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.text_date));
            future.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.page_color));
            last.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.transparent));
            last.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.buttons_color));
            readDataForThisMonthFutureForLoad(hour, minute, year, month + 1, day);
        }

        customAdapter = new AdapterForMonth(getActivity(), getContext(), event_id, event_title, event_description, event_data_data, event_data_month,
                event_data_year, event_startTime_hour, event_startTime_minute, event_endTime_hour, event_endTime_minute);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("lastOrFuture", true);
                editor.apply();
                last.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.text_date));
                last.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.page_color));
                future.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.transparent));
                future.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.buttons_color));
                readDataForThisMonthPast(hour, minute, year, month + 1, day);
                ImageNoDataAppearence(statement);
            }
        });

        future.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("lastOrFuture", false);
                editor.apply();
                future.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.text_date));
                future.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.page_color));
                last.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.transparent));
                last.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.buttons_color));
                readDataForThisMonthFuture(hour, minute, year, month + 1, day);
                ImageNoDataAppearence(statement);
            }
        });

        imageView = (ImageView) view.findViewById(R.id.emptyField);
        textEmpty = (TextView) view.findViewById(R.id.emptyText);
        ImageNoDataAppearence(statement);

        // Inflate the layout for this fragment
        return view;
    }

    void readDataForThisMonthFutureForLoad(int startTimeHour, int startTimeMinute, int selectedYear, int selectedMonth, int selectedDay){
        Cursor cursor = myDB.readDataForThisMonthFuture(startTimeHour, startTimeMinute, selectedYear, selectedMonth, selectedDay);
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
                } else {
                    event_startTime_minute.add(cursor.getString(7));
                }
                event_endTime_hour.add(cursor.getString(8));
                if (Objects.equals(cursor.getString(9), "0")){
                    event_endTime_minute.add("00");
                } else {
                    event_endTime_minute.add(cursor.getString(9));
                }
            }

        }
    }

    void readDataForThisMonthPastForLoad(int startTimeHour, int startTimeMinute, int selectedYear, int selectedMonth, int selectedDay){
        Cursor cursor = myDB.readDataForThisMonthPast(startTimeHour, startTimeMinute, selectedYear, selectedMonth, selectedDay);
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
                } else {
                    event_startTime_minute.add(cursor.getString(7));
                }
                event_endTime_hour.add(cursor.getString(8));
                if (Objects.equals(cursor.getString(9), "0")){
                    event_endTime_minute.add("00");
                } else {
                    event_endTime_minute.add(cursor.getString(9));
                }
            }

        }
    }

    void readDataForThisMonthFuture(int startTimeHour, int startTimeMinute, int selectedYear, int selectedMonth, int selectedDay){
        customAdapter.deleteData();
        Cursor cursor = myDB.readDataForThisMonthFuture(startTimeHour, startTimeMinute, selectedYear, selectedMonth, selectedDay);
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
                } else {
                    event_startTime_minute.add(cursor.getString(7));
                }
                event_endTime_hour.add(cursor.getString(8));
                if (Objects.equals(cursor.getString(9), "0")){
                    event_endTime_minute.add("00");
                } else {
                    event_endTime_minute.add(cursor.getString(9));
                }
            }
        }

        customAdapter.updateData(event_id, event_title, event_description, event_data_data, event_data_month, event_data_year,
                event_startTime_hour, event_startTime_minute, event_endTime_hour, event_endTime_minute);
        customAdapter.notifyDataSetChanged();
    }

    void readDataForThisMonthPast(int startTimeHour, int startTimeMinute, int selectedYear, int selectedMonth, int selectedDay){
        customAdapter.deleteData();
        Cursor cursor = myDB.readDataForThisMonthPast(startTimeHour, startTimeMinute, selectedYear, selectedMonth, selectedDay);
        if(cursor.getCount() == 0){
            statement = true;
            //Toast.makeText(getContext(),"No data.", Toast.LENGTH_SHORT).show();
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
                } else {
                    event_startTime_minute.add(cursor.getString(7));
                }
                event_endTime_hour.add(cursor.getString(8));
                if (Objects.equals(cursor.getString(9), "0")){
                    event_endTime_minute.add("00");
                } else {
                    event_endTime_minute.add(cursor.getString(9));
                }
            }
        }

        customAdapter.updateData(event_id, event_title, event_description, event_data_data, event_data_month, event_data_year,
                event_startTime_hour, event_startTime_minute, event_endTime_hour, event_endTime_minute);
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
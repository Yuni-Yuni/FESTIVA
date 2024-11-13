package com.example.festiva;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.festiva.databinding.FragmentThisMonthEventBinding;
import com.example.festiva.databinding.FragmentThisWeekEventBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link This_Week_EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class This_Week_EventFragment extends Fragment {

    FragmentThisWeekEventBinding binding;
    private ImageButton btnPrevWeek, btnNextWeek;
    private TextView startWeekText, endWeekText;
    private MyDatabaseHelper dbHelper;
    private EventsAdapter eventsAdapter;

    private RecyclerView recyclerViewEvents;
    private Calendar currentCalendar;

    private Calendar startOfWeek;
    private Calendar endOfWeek;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public This_Week_EventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment This_Week_EventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static This_Week_EventFragment newInstance(String param1, String param2) {
        This_Week_EventFragment fragment = new This_Week_EventFragment();
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

        View view = inflater.inflate(R.layout.fragment_this__week__event, container, false);

        ImageButton homeButton = view.findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoreFragment moreFragment = new MoreFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, moreFragment);
                transaction.commit();
            }
        });

        dbHelper = new MyDatabaseHelper(getContext());
        eventsAdapter = new EventsAdapter();

        startWeekText =  (TextView) view.findViewById(R.id.startWeekDate);
        endWeekText =  (TextView) view.findViewById(R.id.endWeekDate);

        recyclerViewEvents = view.findViewById(R.id.recycler_view_events);
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewEvents.setAdapter(eventsAdapter);

        btnPrevWeek = view.findViewById(R.id.previousWeek);
        btnNextWeek = view.findViewById(R.id.nextWeek);

        startOfWeek = Calendar.getInstance();
        startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        endOfWeek = (Calendar) startOfWeek.clone();
        endOfWeek.add(Calendar.DAY_OF_WEEK, 6);

        //currentCalendar = Calendar.getInstance();
        //updateWeekView();
        updateWeekRangeText();

        btnPrevWeek.setOnClickListener(v -> {
            changeWeek(-1);
        });

        btnNextWeek.setOnClickListener(v -> {
            changeWeek(1);
        });

        loadEventsForCurrentWeek();

        // Inflate the layout for this fragment
        return view;//binding.getRoot();
    }


    private void changeWeek(int offset) {
        startOfWeek.add(Calendar.WEEK_OF_YEAR, offset);
        endOfWeek.add(Calendar.WEEK_OF_YEAR, offset);
        updateWeekRangeText();
        loadEventsForCurrentWeek();
    }

    private void updateWeekRangeText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String start = dateFormat.format(startOfWeek.getTime());
        String end = dateFormat.format(endOfWeek.getTime());
        //weekRangeTextView.setText(start + " - " + end);
        startWeekText.setText(start);
        endWeekText.setText(end);
    }

    private void loadEventsForCurrentWeek() {
        Cursor cursor = dbHelper.getEventsForWeek(startOfWeek, endOfWeek);
        Map<String, List<Event>> eventsByDate = new LinkedHashMap<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("event_name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("event_description"));
                int day = cursor.getInt(cursor.getColumnIndexOrThrow("event_data_data"));
                int month = cursor.getInt(cursor.getColumnIndexOrThrow("event_data_month"));
                int year = cursor.getInt(cursor.getColumnIndexOrThrow("event_data_year"));
                int startHour = cursor.getInt(cursor.getColumnIndexOrThrow("event_start_time_hour"));
                int startMinute = cursor.getInt(cursor.getColumnIndexOrThrow("event_start_time_minute"));
                int endHour = cursor.getInt(cursor.getColumnIndexOrThrow("event_end_time_hour"));
                int endMinute = cursor.getInt(cursor.getColumnIndexOrThrow("event_end_time_minute"));

                Event event = new Event(name, description, day, month, year, startHour, startMinute, endHour, endMinute);

                String dateKey = day + "." + month + "." + year;

                if (!eventsByDate.containsKey(dateKey)) {
                    eventsByDate.put(dateKey, new ArrayList<>());
                }
                eventsByDate.get(dateKey).add(event);
                //Toast.makeText(getContext(), day + " " + name, Toast.LENGTH_LONG).show();
            } while (cursor.moveToNext());
            cursor.close();
        }

        List<EventsByDate> groupedEvents = new ArrayList<>();
        for (Map.Entry<String, List<Event>> entry : eventsByDate.entrySet()) {
            groupedEvents.add(new EventsByDate(entry.getKey(), entry.getValue()));
        }

        eventsAdapter.setEvents(groupedEvents);
    }



}
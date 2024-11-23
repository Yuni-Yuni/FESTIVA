package com.example.festiva;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> event_id, event_title, event_description;
    private ArrayList<String> data_data, data_month, data_year, start_time_hour, start_time_minute, end_time_hour, end_time_minute;
    private ArrayList<Integer> event_reminder, event_greeting_id;
    Activity activity;

    //
    CustomAdapter(Activity activity, Context context, ArrayList<String> event_id, ArrayList<String> event_title, ArrayList<String> event_description,
                  ArrayList<String> data_data, ArrayList<String> data_month, ArrayList<String> data_year, ArrayList<String> start_time_hour,
                  ArrayList<String> start_time_minute, ArrayList<String> end_time_hour, ArrayList<String> end_time_minute,
                  ArrayList<Integer> event_reminder, ArrayList<Integer> event_greeting_id){
        this.activity = activity;
        this.context = context;
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_description = event_description;
        this.data_data = data_data;
        this.data_month = data_month;
        this.data_year = data_year;
        this.start_time_hour = start_time_hour;
        this.start_time_minute = start_time_minute;
        this.end_time_hour = end_time_hour;
        this.end_time_minute = end_time_minute;
        this.event_reminder = event_reminder;
        this.event_greeting_id =event_greeting_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_for_events_on_homepage, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //holder.event_id.setText(String.valueOf(event_id.get(position)));
        holder.event_title.setText(String.valueOf(event_title.get(position)));
        //holder.event_description.setText(String.valueOf(event_description.get(position)));
        holder.event_startHour.setText(String.valueOf(start_time_hour.get(position)));
        holder.event_startMinute.setText(String.valueOf(start_time_minute.get(position)));
        holder.event_endHour.setText(String.valueOf(end_time_hour.get(position)));
        holder.event_endMinute.setText(String.valueOf(end_time_minute.get(position)));

        holder.EventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateEventActivity.class);
                intent.putExtra("id", String.valueOf(event_id.get(position)));
                intent.putExtra("title", String.valueOf(event_title.get(position)));
                intent.putExtra("description", String.valueOf(event_description.get(position)));
                intent.putExtra("dateData", String.valueOf(data_data.get(position)));
                intent.putExtra("dateMonth", String.valueOf(data_month.get(position)));
                intent.putExtra("dateYear", String.valueOf(data_year.get(position)));
                intent.putExtra("startTimeHour", String.valueOf(start_time_hour.get(position)));
                intent.putExtra("startTimeMinute", String.valueOf(start_time_minute.get(position)));
                intent.putExtra("endTimeHour", String.valueOf(end_time_hour.get(position)));
                intent.putExtra("endTimeMinute", String.valueOf(end_time_minute.get(position)));
                intent.putExtra("reminder", event_reminder.get(position));
                intent.putExtra("greeting_id", event_greeting_id.get(position));
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return event_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //TextView event_id, event_title, event_description;
        TextView event_title, event_startHour, event_startMinute, event_endHour, event_endMinute;
        LinearLayout EventLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //event_id = itemView.findViewById(R.id.event_id_text);
            event_title = itemView.findViewById(R.id.event_title);
            event_startHour = itemView.findViewById(R.id.hourStart);
            event_startMinute = itemView.findViewById(R.id.minuteStart);
            event_endHour = itemView.findViewById(R.id.hourEnd);
            event_endMinute = itemView.findViewById(R.id.minuteEnd);
            EventLayout = itemView.findViewById(R.id.EventLayout);

            //event_description = itemView.findViewById(R.id.event_description);

        }
    }

    // Метод для обновления данных
    public void updateData(ArrayList<String> newEventId, ArrayList<String> newEventTitle, ArrayList<String> newEventDescription,
                           ArrayList<String> newDataData, ArrayList<String> newDataMonth, ArrayList<String> newDataYear,
                           ArrayList<String> newStartTimeHour, ArrayList<String> newStartTimeMinute,
                           ArrayList<String> newEndTimeHour, ArrayList<String> newEndTimeMinute, ArrayList<Integer> newEvent_reminder, ArrayList<Integer> newEvent_greeting_id) {
        this.event_id = newEventId;
        this.event_title = newEventTitle;
        this.event_description = newEventDescription;
        this.data_data = newDataData;
        this.data_month = newDataMonth;
        this.data_year = newDataYear;
        this.start_time_hour = newStartTimeHour;
        this.start_time_minute = newStartTimeMinute;
        this.end_time_hour = newEndTimeHour;
        this.end_time_minute = newEndTimeMinute;
        this.event_reminder = newEvent_reminder;
        this.event_greeting_id =newEvent_greeting_id;
    }

    public void deleteData() {
        this.event_id.clear();
        this.event_title.clear();
        this.event_description.clear();
        this.data_data.clear();
        this.data_month.clear();
        this.data_year.clear();
        this.start_time_hour.clear();
        this.start_time_minute.clear();
        this.end_time_hour.clear();
        this.end_time_minute.clear();
        this.event_reminder.clear();
        this.event_greeting_id.clear();
    }
}

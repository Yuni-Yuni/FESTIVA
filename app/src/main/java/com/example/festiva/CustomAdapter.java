package com.example.festiva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> event_id, event_title, event_description;
    private ArrayList<String> data_data, data_month, data_year, start_time_hour, start_time_minute, end_time_hour, end_time_minute;

    CustomAdapter(Context context, ArrayList<String> event_id, ArrayList<String> event_title, ArrayList<String> event_description,
                  ArrayList<String> data_data, ArrayList<String> data_month, ArrayList<String> data_year, ArrayList<String> start_time_hour,
                  ArrayList<String> start_time_minute, ArrayList<String> end_time_hour, ArrayList<String> end_time_minute){
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
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_try_new, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.event_id.setText(String.valueOf(event_id.get(position)));
        holder.event_title.setText(String.valueOf(event_title.get(position)));
        //holder.event_description.setText(String.valueOf(event_description.get(position)));
        holder.event_startHour.setText(String.valueOf(start_time_hour.get(position)));
        holder.event_startMinute.setText(String.valueOf(start_time_minute.get(position)));
        holder.event_endHour.setText(String.valueOf(end_time_hour.get(position)));
        holder.event_endMinute.setText(String.valueOf(end_time_minute.get(position)));

    }

    @Override
    public int getItemCount() {
        return event_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //TextView event_id, event_title, event_description;
        TextView event_title, event_startHour, event_startMinute, event_endHour, event_endMinute;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //event_id = itemView.findViewById(R.id.event_id_text);
            event_title = itemView.findViewById(R.id.event_title);
            event_startHour = itemView.findViewById(R.id.hourStart);
            event_startMinute = itemView.findViewById(R.id.minuteStart);
            event_endHour = itemView.findViewById(R.id.hourEnd);
            event_endMinute = itemView.findViewById(R.id.minuteEnd);
            //event_description = itemView.findViewById(R.id.event_description);
        }
    }

    // Метод для обновления данных
    public void updateData(ArrayList<String> newEventId, ArrayList<String> newEventTitle, ArrayList<String> newEventDescription,
                           ArrayList<String> newDataData, ArrayList<String> newDataMonth, ArrayList<String> newDataYear,
                           ArrayList<String> newStartTimeHour, ArrayList<String> newStartTimeMinute, ArrayList<String> newEndTimeHour, ArrayList<String> newEndTimeMinute) {
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
    }
}

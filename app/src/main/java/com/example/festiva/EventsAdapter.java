package com.example.festiva;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsByDateViewHolder> {

    private List<EventsByDate> eventsByDateList = new ArrayList<>();


    public void setEvents(List<EventsByDate> eventsByDateList) {
        this.eventsByDateList = eventsByDateList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventsByDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_events_by_date, parent, false);
        return new EventsByDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsByDateViewHolder holder, int position) {
        EventsByDate eventsByDate = eventsByDateList.get(position);

        holder.dateTextView.setText(eventsByDate.getDate());

        // Формируем список названий событий для этой даты
        StringBuilder eventsText = new StringBuilder();
        for (Event event : eventsByDate.getEvents()) {
            eventsText.append("❥  ").append(event.getName()).append("\n");
        }
        holder.eventsTextView.setText(eventsText.toString().trim());
    }

    @Override
    public int getItemCount() {
        return eventsByDateList.size();
    }

    public static class EventsByDateViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView eventsTextView;

        public EventsByDateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            eventsTextView = itemView.findViewById(R.id.eventsTextView);
        }
    }
}

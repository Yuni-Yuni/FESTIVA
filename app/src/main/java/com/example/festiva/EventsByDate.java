package com.example.festiva;

import java.util.List;

public class EventsByDate {
    private String date;
    private List<Event> events;

    public EventsByDate(String date, List<Event> events) {
        this.date = date;
        this.events = events;
    }

    public String getDate() {
        return date;
    }

    public List<Event> getEvents() {
        return events;
    }
}

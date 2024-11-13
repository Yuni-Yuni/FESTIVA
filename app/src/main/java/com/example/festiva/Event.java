package com.example.festiva;

public class Event {
    private String name;
    private String description;
    private int day;
    private int month;
    private int year;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    public Event(String name, String description, int day, int month, int year, int startHour, int startMinute, int endHour, int endMinute) {
        this.name = name;
        this.description = description;
        this.day = day;
        this.month = month;
        this.year = year;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public String getName() {
        return name;
    }

    public String getFormattedDate() {
        return day + "." + month + "." + year;
    }

    // Остальные геттеры, если они нужны
}

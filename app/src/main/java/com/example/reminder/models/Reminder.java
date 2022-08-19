package com.example.reminder.models;

public class Reminder {
    private String title;
    private String content;
    private String color;
    private boolean isComplete;
    private long dueDate;
    private int dueTimeMinute;
    private int dueTimeHour;

    public Reminder() {}

    public Reminder(String title, String content, String color, long dueDate, int dueTimeMinute, int dueTimeHour) {
        this.title = title;
        this.content = content;
        this.color = color;
        this.isComplete = false;
        this.dueDate = dueDate;
        this.dueTimeMinute = dueTimeMinute;
        this.dueTimeHour = dueTimeHour;
    }

    public Reminder(String title, String content, String color) {
        this.title = title;
        this.content = content;
        this.color = color;
        this.isComplete = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public int getDueTimeMinute() {
        return dueTimeMinute;
    }

    public void setDueTimeMinute(int dueTimeMinute) {
        this.dueTimeMinute = dueTimeMinute;
    }

    public int getDueTimeHour() {
        return dueTimeHour;
    }

    public void setDueTimeHour(int dueTimeHour) {
        this.dueTimeHour = dueTimeHour;
    }
}

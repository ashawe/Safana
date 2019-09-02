package com.ashstudios.safana.models;

public class TaskModel {
    private String name;
    private String date;

    public TaskModel(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}

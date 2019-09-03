package com.ashstudios.safana.models;

public class AllowanceModel {
    //int icon;
    String url;
    String name;
    String duration;

    public AllowanceModel(String url, String name, String duration) {
        this.url = url;
        //this.icon = icon;
        this.name = name;
        this.duration = duration;
    }

//    public int getIcon() {
//        //return icon;
//    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDuration() {
        return duration;
    }
}

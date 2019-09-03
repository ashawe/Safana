package com.ashstudios.safana.models;

public class LeaveModel {
    String name;
    String reason;
    String imgUrl;
    String emp_id;
    String date;

    public LeaveModel(String name, String reason, String imgUrl, String emp_id, String date) {
        this.name = name;
        this.reason = reason;
        this.imgUrl = imgUrl;
        this.emp_id = emp_id;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

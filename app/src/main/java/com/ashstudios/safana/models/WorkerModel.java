package com.ashstudios.safana.models;

public class WorkerModel {
    String name,role,imgUrl,emp_id;
    boolean isSelected;

    public WorkerModel() {
    }

    public WorkerModel(String name, String role, String imgUrl, String emp_id) {
        this.name = name;
        this.role = role;
        this.imgUrl = imgUrl;
        this.emp_id = emp_id;
        isSelected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

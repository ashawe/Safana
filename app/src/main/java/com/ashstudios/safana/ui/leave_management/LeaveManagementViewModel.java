package com.ashstudios.safana.ui.leave_management;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.ashstudios.safana.models.LeaveModel;

import java.util.ArrayList;

public class LeaveManagementViewModel extends ViewModel {

    ArrayList<LeaveModel> leaveModels;

    public ArrayList<LeaveModel> getLeaveModels() {
        return leaveModels;
    }

    public LeaveManagementViewModel() {
        leaveModels = new ArrayList<>();
        initData();
    }

    private void initData() {
        LeaveModel leaveModel = new LeaveModel("Manav Shah","I'm sick","https://i.imgur.com/wnKtRoZ.png","emp123","10/11/2019");
        leaveModels.add(leaveModel);

        leaveModel = new LeaveModel("Harsh Saglani","Family Trip","https://i.imgur.com/wnKtRoZ.png","emp123","10/11/2019");
        leaveModels.add(leaveModel);

        leaveModel = new LeaveModel("John Doe","I'm sick","https://i.imgur.com/wnKtRoZ.png","emp123","10/11/2019");;
        leaveModels.add(leaveModel);

        leaveModel = new LeaveModel("Rohit Suthar","I'm going on a world tour","https://i.imgur.com/wnKtRoZ.png","emp123","10/11/2019");
        leaveModels.add(leaveModel);

        leaveModel = new LeaveModel("Krunal Pande","I want vacation","https://i.imgur.com/wnKtRoZ.png","emp123","10/11/2019");
        leaveModels.add(leaveModel);
    }


    public void sort(Bundle b) {
        leaveModels.remove(0);
    }
}
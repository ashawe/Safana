package com.ashstudios.safana.ui.worker_details;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.ashstudios.safana.WorkerModel;

import java.util.ArrayList;

public class WorkerDetailsViewModel extends ViewModel {

    private ArrayList<WorkerModel> workerModels;

    public WorkerDetailsViewModel() {
        workerModels = new ArrayList<>();
        initData();
    }

//    "https://i.imgur.com/[0-9a-zA-Z]*.(jpg|png)

    private void initData() {
        WorkerModel workerModel = new WorkerModel("Harsh Saglani","Developer","https://i.imgur.com/wnKtRoZ.png","emp123");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Harsh Saglani","Developer","https://i.imgur.com/wnKtRoZ.png","emp123");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Rohit Suthar","Developer","https://i.imgur.com/wnKtRoZ.png","emp133");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("John Doe","Developer","https://i.imgur.com/wnKtRoZ.png","emp1223");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Akshay Kumar","Developer","https://i.imgur.com/wnKtRoZ.png","emp143");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Carry Minati","Developer","https://i.imgur.com/wnKtRoZ.png","emp143");
    }

    public ArrayList<WorkerModel> getWorkerModels() {
        return workerModels;
    }

    public void sort(Bundle b) {
        workerModels.remove(0);
    }
}
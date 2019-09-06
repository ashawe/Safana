package com.ashstudios.safana.ui.worker_details;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import com.ashstudios.safana.models.WorkerModel;
import com.ashstudios.safana.others.Msg;
import com.ashstudios.safana.others.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WorkerDetailsViewModel extends ViewModel {

    private ArrayList<WorkerModel> workerModels;

    public WorkerDetailsViewModel() {
        workerModels = new ArrayList<>();
//        initData();
    }

//    "https://i.imgur.com/[0-9a-zA-Z]*.(jpg|png)

    public void initData() {
        WorkerModel workerModel = new WorkerModel("Rohan gill","Designer","https://i.imgur.com/wnKtRoZ.png","emp123");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Harsh Saglani","UI/UX","https://i.imgur.com/wnKtRoZ.png","emp123");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Rohit Suthar","Developer","https://i.imgur.com/wnKtRoZ.png","emp133");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("John Doe","Designer","https://i.imgur.com/wnKtRoZ.png","emp1223");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Akshay Kumar","DB Admin","https://i.imgur.com/wnKtRoZ.png","emp143");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Carry Minati","UI/UX","https://i.imgur.com/wnKtRoZ.png","emp143");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Raju Shriwastav","Architect","https://i.imgur.com/wnKtRoZ.png","emp143");
        workerModels.add(workerModel);

        workerModel = new WorkerModel("Aniket Pande ","SYS Admin","https://i.imgur.com/wnKtRoZ.png","emp143");
        workerModels.add(workerModel);
    }

    public ArrayList<WorkerModel> getWorkerModels() {
        return workerModels;
    }

    public void setWorkerModels(ArrayList<WorkerModel> workerModels) {
        this.workerModels = workerModels;
    }

    public void sort(Bundle b) {
        workerModels.remove(0);
    }
}
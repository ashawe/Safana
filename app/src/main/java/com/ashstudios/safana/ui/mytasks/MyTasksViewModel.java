package com.ashstudios.safana.ui.mytasks;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ashstudios.safana.models.TaskModel;

import java.util.ArrayList;

public class MyTasksViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ArrayList<TaskModel> arrayListMutableLiveData;
    public MyTasksViewModel() {
        //mText = new MutableLiveData<>();
        arrayListMutableLiveData = new ArrayList<>();
        getData();
    }

//    public LiveData<String> getText() {
//        return mText;
//    }

    public ArrayList<TaskModel> getArrayListMutableLiveData() {
        return arrayListMutableLiveData;
    }

    public void getData() {
        arrayListMutableLiveData.add(new TaskModel("Implement User Profile","28/03/2018"));
        arrayListMutableLiveData.add(new TaskModel("Implement User Orders","28/04/2019"));
        arrayListMutableLiveData.add(new TaskModel("Test User Profile","28/05/2019"));
        arrayListMutableLiveData.add(new TaskModel("Feedback from Client","25/03/2019"));
        arrayListMutableLiveData.add(new TaskModel("Implement User Profile","22/03/2019"));
    }
}
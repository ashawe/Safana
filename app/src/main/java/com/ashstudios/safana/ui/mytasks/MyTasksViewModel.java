package com.ashstudios.safana.ui.mytasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyTasksViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyTasksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is My Tasks fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.ashstudios.safana.ui.projectstatus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectStatusViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProjectStatusViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is My Project Status fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
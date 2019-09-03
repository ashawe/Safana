package com.ashstudios.safana.ui.project__details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectDetailsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProjectDetailsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
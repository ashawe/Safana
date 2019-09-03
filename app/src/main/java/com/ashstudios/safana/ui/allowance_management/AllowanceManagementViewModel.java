package com.ashstudios.safana.ui.allowance_management;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllowanceManagementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AllowanceManagementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
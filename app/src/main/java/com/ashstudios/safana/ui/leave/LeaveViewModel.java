package com.ashstudios.safana.ui.leave;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LeaveViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LeaveViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Leave fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
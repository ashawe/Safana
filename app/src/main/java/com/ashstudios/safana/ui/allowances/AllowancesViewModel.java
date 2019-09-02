package com.ashstudios.safana.ui.allowances;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllowancesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AllowancesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is allowances fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
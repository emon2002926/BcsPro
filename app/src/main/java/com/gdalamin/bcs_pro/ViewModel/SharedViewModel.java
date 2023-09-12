package com.gdalamin.bcs_pro.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> titleText = new MutableLiveData<>();

    public void setTitleText(String text) {
        titleText.setValue(text);
    }

    public LiveData<String> getTitleText() {
        return titleText;
    }
}


package com.gdalamin.bcs_pro.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> titleText = new MutableLiveData<>();
    private final MutableLiveData<Integer> subCode = new MutableLiveData<>();


    public void setSubCode(int code) {
        subCode.setValue(code);
    }
    public LiveData<Integer> getSubCode(){
        return subCode;
    }

    public void setTitleText(String text) {
        titleText.setValue(text);
    }

    public LiveData<String> getTitleText() {
        return titleText;
    }

}


package com.example.viveinmobiliaria.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


public class AplicarFiltroViewModel extends ViewModel {

    private final MutableLiveData<Boolean> aplicar = new MutableLiveData<>();

    public void selectedAplicar(Boolean aplico){
        aplicar.setValue(aplico);
    }

    public MutableLiveData<Boolean> getAll(){
        return aplicar;
    }
}

package com.example.viveinmobiliaria.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.viveinmobiliaria.Model.Propiedad;

public class SubirFotoViewModel extends ViewModel {

    private final MutableLiveData<Propiedad> propiedadMutableLiveData = new MutableLiveData<>();

    public void selectedAplicar(Propiedad propiedad){
        propiedadMutableLiveData.setValue(propiedad);
    }

    public MutableLiveData<Propiedad> getAll(){
        return propiedadMutableLiveData;
    }
}

package com.example.viveinmobiliaria.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.viveinmobiliaria.Model.Propiedad;

import java.util.ArrayList;
import java.util.List;

public class FiltroViewModel extends ViewModel {

    private final MutableLiveData<List<Propiedad>> listaPropiedades = new MutableLiveData<>();


    public void selectPropiedadList(List<Propiedad> propiedades) {
        listaPropiedades.setValue(propiedades);
    }

    public MutableLiveData<List<Propiedad>> getAll() {
        return listaPropiedades;
    }
}

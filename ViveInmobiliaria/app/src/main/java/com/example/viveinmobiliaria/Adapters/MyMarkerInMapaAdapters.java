package com.example.viveinmobiliaria.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.R;

import java.util.ArrayList;
import java.util.List;

public class MyMarkerInMapaAdapters extends ArrayAdapter<Propiedad> {
    ArrayList<Propiedad> propiedades;

    public MyMarkerInMapaAdapters(Context context, int resource, List<Propiedad> objects) {
        super(context, resource, objects);
    }

    static class ViewHolder {
    }

    public int getCount() {
        return propiedades.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView,
                        final ViewGroup parent) {
        View view = null;
        return view;
    }

}

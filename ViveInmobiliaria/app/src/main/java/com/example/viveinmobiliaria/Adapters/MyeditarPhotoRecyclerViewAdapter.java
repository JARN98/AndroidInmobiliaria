package com.example.viveinmobiliaria.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.viveinmobiliaria.Generator.ServiceGenerator;
import com.example.viveinmobiliaria.Generator.TipoAutenticacion;
import com.example.viveinmobiliaria.Generator.UtilToken;
import com.example.viveinmobiliaria.Listener.EditarPhotosListener;
import com.example.viveinmobiliaria.Model.CrearPropiedadDto;
import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.R;
import com.example.viveinmobiliaria.Responses.CrearPropiedadResponse;
import com.example.viveinmobiliaria.Services.PropertiesService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyeditarPhotoRecyclerViewAdapter extends RecyclerView.Adapter<MyeditarPhotoRecyclerViewAdapter.ViewHolder> {

    private final Propiedad mValues;
    List<String> photos;
    private final EditarPhotosListener mListener;
    private Context context;

    public MyeditarPhotoRecyclerViewAdapter(Context cxt, Propiedad items, EditarPhotosListener listener) {
        context = cxt;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_editarphoto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues;
/*        holder.mIdView.setText(mValues.get(position));
        holder.mContentView.setText(mValues.get(position));*/
        photos = new ArrayList<String>(Arrays.asList(mValues.getPhotos()));

            holder.imageView_photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide
                    .with(context)
                    .load(mValues.getPhotos()[position])
                    .into(holder.imageView_photo);

            holder.imageView_delete_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( eliminarPhoto(mValues.getPhotos()[position]) != -1){
                        int posicion = eliminarPhoto(mValues.getPhotos()[position]);

                        photos.remove(posicion);

                        String[] imagenesActualizadas = photos.toArray(new String[0]);

                        mValues.setPhotos(imagenesActualizadas);

                        actualizarPropiedad();


                    }

                }
            });



    }

    private void actualizarPropiedad() {
        CrearPropiedadDto crearPropiedadDto = new CrearPropiedadDto(
                mValues.getTitle(),
                mValues.getDescription(),
                mValues.getPrice(),
                mValues.getRooms(),
                mValues.getAddress(),
                mValues.getZipcode(),
                mValues.getCity(),
                mValues.getProvince(),
                mValues.getLoc(),
                mValues.getPhotos()

        );
        PropertiesService service = ServiceGenerator.createService(PropertiesService.class, UtilToken.getToken(context), TipoAutenticacion.JWT);
        Call<CrearPropiedadResponse> call = service.editProperty(mValues.getId(), crearPropiedadDto);

        call.enqueue(new Callback<CrearPropiedadResponse>() {
            @Override
            public void onResponse(Call<CrearPropiedadResponse> call, Response<CrearPropiedadResponse> response) {

            }

            @Override
            public void onFailure(Call<CrearPropiedadResponse> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int eliminarPhoto(String photo) {
        boolean encontrado = false;
        int i = 0;

        while (i < photos.size() && !encontrado) {
            if (photo == photos.get(i)) {
                encontrado = true;
            } else {
                i++;
            }
        }

        if (encontrado) {
            return i;
        } else {
            return -1;
        }

    }

    @Override
    public int getItemCount() {
        return mValues.getPhotos().length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Propiedad mItem;
        public final ImageView imageView_photo, imageView_delete_photo;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            imageView_photo = view.findViewById(R.id.imageView_photo);
            imageView_delete_photo = view.findViewById(R.id.imageView_delete_photo);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

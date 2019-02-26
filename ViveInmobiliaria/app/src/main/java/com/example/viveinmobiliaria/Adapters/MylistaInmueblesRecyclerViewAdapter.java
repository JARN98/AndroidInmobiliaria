package com.example.viveinmobiliaria.Adapters;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
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
import com.example.viveinmobiliaria.Generator.UtilUser;
import com.example.viveinmobiliaria.InmuebleDetallado;
import com.example.viveinmobiliaria.Listener.InmueblesListener;
import com.example.viveinmobiliaria.Model.Photo;
import com.example.viveinmobiliaria.Model.Propiedad;
import com.example.viveinmobiliaria.Model.ResponseContainer;
import com.example.viveinmobiliaria.Model.addFavouriteDto;
import com.example.viveinmobiliaria.R;
import com.example.viveinmobiliaria.Services.PropertiesService;

import java.util.List;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MylistaInmueblesRecyclerViewAdapter extends RecyclerView.Adapter<MylistaInmueblesRecyclerViewAdapter.ViewHolder> {

    private final List<Propiedad> mValues;
    private final InmueblesListener mListener;
    private Context contexto;
    private Photo photo;

    public MylistaInmueblesRecyclerViewAdapter(Context cxt, List<Propiedad> items, InmueblesListener listener) {
        contexto = cxt;
        mValues = items;
        mListener = listener;
    }

    public MylistaInmueblesRecyclerViewAdapter(Context cxt, List<Propiedad> items, InmueblesListener listener, Photo foto) {
        contexto = cxt;
        mValues = items;
        mListener = listener;
        photo = foto;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_listainmuebles, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        /*if(mValues.get(position).getCategoryId() != null) {
            holder.textView_category.setText(mValues.get(position).getCategoryId());
        }*/
        holder.textView_price.setText(mValues.get(position).getPrice() + " €");
        holder.textView_title.setText(mValues.get(position).getTitle());
        holder.textView_rooms.setText(mValues.get(position).getRooms() + " habs");
        holder.textView_direccion.setText(mValues.get(position).getAddress());
        holder.textView_ciudad.setText(mValues.get(position).getCity() + " - " + mValues.get(position).getProvince());
        holder.imageView_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PropertiesService service = ServiceGenerator.createService(PropertiesService.class, UtilToken.getToken(contexto), TipoAutenticacion.JWT);
                Call<addFavouriteDto> call = service.addFavPropertie(holder.mItem.getId());

                call.enqueue(new Callback<addFavouriteDto>() {
                    @Override
                    public void onResponse(Call<addFavouriteDto> call, Response<addFavouriteDto> response) {
                        if (response.code() == 200) {
                            holder.imageView_fav.setImageResource(R.drawable.ic_star_black_24dp);
                        } else {
                            Toast.makeText(contexto, "Error en petición", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<addFavouriteDto> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(contexto, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contexto, InmuebleDetallado.class);
                i.putExtra("id", mValues.get(position).getId());
                contexto.startActivity(i);
            }
        });

        Glide
                .with(contexto)
                .load(mValues.get(position).getPhotos()[0])
                .into(holder.imageView);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    /*mListener.verCasa(holder.mItem);*/
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textView_category, textView_title, textView_price, textView_rooms, textView_size, textView_direccion, textView_ciudad;
        public final ImageView imageView_fav, imageView;
        public Propiedad mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            textView_category = view.findViewById(R.id.textView_category);
            textView_title = view.findViewById(R.id.textView_title);
            textView_price = view.findViewById(R.id.textView_price);
            textView_rooms = view.findViewById(R.id.textView_rooms);
            textView_size = view.findViewById(R.id.textView_size);
            textView_direccion = view.findViewById(R.id.textView_direccion);
            textView_ciudad = view.findViewById(R.id.textView_ciudad);
            imageView = view.findViewById(R.id.imageView);
            imageView_fav = view.findViewById(R.id.imageView_fav);

        }
    }
}

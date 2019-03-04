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
import com.example.viveinmobiliaria.Model.PropiedaFavoritasDto;
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

    private List<Propiedad> mValues = null;
    private final InmueblesListener mListener;
    private Context contexto;
    private Photo photo;
    private List<PropiedaFavoritasDto> valores = null;
    private boolean favorito;
    private boolean fragmentoFav;


    public MylistaInmueblesRecyclerViewAdapter(Context cxt, InmueblesListener listener, List<PropiedaFavoritasDto> objetos) {
        contexto = cxt;
        valores = objetos;
        mListener = listener;
    }

    public MylistaInmueblesRecyclerViewAdapter(Context cxt, List<Propiedad> items, InmueblesListener listener) {
        contexto = cxt;
        mValues = items;
        mListener = listener;
    }

    public MylistaInmueblesRecyclerViewAdapter(Context cxt, List<Propiedad> items, InmueblesListener listener, boolean fragmentoDeFavoritos) {
        contexto = cxt;
        mValues = items;
        mListener = listener;
        fragmentoFav = fragmentoDeFavoritos;
    }

    public void setListaFiltrada(List<Propiedad> listaFiltrada) {
        this.mValues = listaFiltrada;
        notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_listainmuebles, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mValues != null) {
            holder.mItem = mValues.get(position);
            eventsNormal(holder, position);
        } else {
            holder.item = valores.get(position);
            eventsMine(holder, position);
        }

    }

    private void eventsMine(final ViewHolder holder, final int position) {

        holder.textView_price.setText(valores.get(position).getPrice() + " €");
        holder.textView_title.setText(valores.get(position).getTitle());
        holder.textView_rooms.setText(valores.get(position).getRooms() + " habs");
        holder.textView_direccion.setText(valores.get(position).getAddress());
        holder.textView_ciudad.setText(valores.get(position).getCity() + " - " + valores.get(position).getProvince());
        holder.imageView_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PropertiesService service = ServiceGenerator.createService(PropertiesService.class, UtilToken.getToken(contexto), TipoAutenticacion.JWT);
                Call<addFavouriteDto> call = service.addFavPropertie(holder.item.getId());

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
                i.putExtra("id", valores.get(position).getId());
                contexto.startActivity(i);
            }
        });

        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if(valores.get(position).getPhotos() != null) {
            Glide
                    .with(contexto)
                    .load(valores.get(position).getPhotos()[0])
                    .into(holder.imageView);
        }


    }

    private void eventsNormal(final ViewHolder holder, final int position) {

        holder.textView_price.setText(mValues.get(position).getPrice() + " €");
        holder.textView_title.setText(mValues.get(position).getTitle());
        holder.textView_rooms.setText(mValues.get(position).getRooms() + " habs");
        holder.textView_direccion.setText(mValues.get(position).getAddress());
        holder.textView_ciudad.setText(mValues.get(position).getCity() + " - " + mValues.get(position).getProvince());
        if (UtilUser.getEmail(contexto) == null) {
            holder.imageView_fav.setVisibility(View.GONE);
        } else {
            favorito = mValues.get(position).isFav();

            if (favorito || fragmentoFav) {
                holder.imageView_fav.setImageResource(R.drawable.ic_star_black_24dp);
            } else {
                holder.imageView_fav.setImageResource(R.drawable.ic_star_border_yellow_24dp);
            }

            holder.imageView_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favorito = mValues.get(position).isFav();
                    if (favorito || fragmentoFav) {
                        eventoOnClickCuandoEsFav(holder);
                        mValues.get(position).setFav(false);
                    } else {
                        eventoOnClickCuandoNoEsFav(holder);
                        mValues.get(position).setFav(true);
                    }
                }
            });


        }


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contexto, InmuebleDetallado.class);
                i.putExtra("id", mValues.get(position).getId());
                contexto.startActivity(i);
            }
        });

        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if(mValues.get(position).getPhotos() != null) {
            Glide
                    .with(contexto)
                    .load(mValues.get(position).getPhotos()[0])
                    .into(holder.imageView);
        }
    }

    private void eventoOnClickCuandoEsFav(final ViewHolder holder) {
        PropertiesService service = ServiceGenerator.createService(PropertiesService.class, UtilToken.getToken(contexto), TipoAutenticacion.JWT);
        Call<addFavouriteDto> call = service.removeFavPropertie(holder.mItem.getId());

        call.enqueue(new Callback<addFavouriteDto>() {
            @Override
            public void onResponse(Call<addFavouriteDto> call, Response<addFavouriteDto> response) {
                if (response.code() == 200) {
                    holder.imageView_fav.setImageResource(R.drawable.ic_star_border_yellow_24dp);
                    Toast.makeText(contexto, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
                    favorito = !favorito;
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

    private void eventoOnClickCuandoNoEsFav(final ViewHolder holder) {

        PropertiesService service = ServiceGenerator.createService(PropertiesService.class, UtilToken.getToken(contexto), TipoAutenticacion.JWT);
        Call<addFavouriteDto> call = service.addFavPropertie(holder.mItem.getId());

        call.enqueue(new Callback<addFavouriteDto>() {
            @Override
            public void onResponse(Call<addFavouriteDto> call, Response<addFavouriteDto> response) {
                if (response.code() == 200) {
                    holder.imageView_fav.setImageResource(R.drawable.ic_star_black_24dp);
                    Toast.makeText(contexto, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
                    favorito = !favorito;
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

    @Override
    public int getItemCount() {
        if (mValues != null) {
            return mValues.size();
        } else {
            return valores.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textView_title, textView_price, textView_rooms, textView_size, textView_direccion, textView_ciudad;
        public final ImageView imageView_fav, imageView;
        public Propiedad mItem;
        public PropiedaFavoritasDto item;

        public ViewHolder(View view) {
            super(view);
            mView = view;
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

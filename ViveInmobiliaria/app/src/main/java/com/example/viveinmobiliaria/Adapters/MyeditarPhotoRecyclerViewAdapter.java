package com.example.viveinmobiliaria.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.viveinmobiliaria.Listener.EditarPhotosListener;
import com.example.viveinmobiliaria.Model.Photo;
import com.example.viveinmobiliaria.R;

import java.util.List;


public class MyeditarPhotoRecyclerViewAdapter extends RecyclerView.Adapter<MyeditarPhotoRecyclerViewAdapter.ViewHolder> {

    private final List<String> mValues;
    private final EditarPhotosListener mListener;
    private Context context;

    public MyeditarPhotoRecyclerViewAdapter(Context cxt, List<String> items, EditarPhotosListener listener) {
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
/*        holder.mIdView.setText(mValues.get(position));
        holder.mContentView.setText(mValues.get(position));*/
        Glide
                .with(context)
                .load(mValues.get(position))
                .into(holder.imageView_photo);



    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public String mItem;
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

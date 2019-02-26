package com.example.viveinmobiliaria.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageAdapter extends PagerAdapter {
    private Context cxt;
    private String[] urls;
    private ImageView[] photos;

    ImageAdapter(Context context, String[] rutaDeImagenes) {
        cxt = context;
        urls = rutaDeImagenes;
    }
    @Override
    public int getCount() {
        return urls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView photo = photos[position];
        photo = new ImageView(cxt);
        photo.setScaleType(ImageView.ScaleType.CENTER_CROP);


        Glide
                .with(this.cxt)
                .load(urls[position])
                .into(photo);


        photo.setImageDrawable(photo.getDrawable());


        container.addView(photo, 0);
        return photo;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}

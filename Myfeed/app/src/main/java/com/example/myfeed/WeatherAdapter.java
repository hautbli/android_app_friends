package com.example.myfeed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    
    private ArrayList<WeatherData> weatherList;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img;
        private TextView textView_region, region;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView_img = itemView.findViewById(R.id.imageView_img);
            textView_region = itemView.findViewById(R.id.textView_region);
            region =itemView.findViewById(R.id.region);

        }
    }

    //생성자
    public WeatherAdapter(ArrayList<WeatherData> list) {

        this.weatherList = list;
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weatheritem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView).load(weatherList.get(position).getMy_imgUrl())
                .override(300,400)
                .into(holder.imageView_img);
        holder.textView_region.setText(String.valueOf(weatherList.get(position).getRegion()));
        holder.region.setText(weatherList.get(position).getRegion_name());

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

}

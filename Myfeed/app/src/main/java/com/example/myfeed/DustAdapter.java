package com.example.myfeed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DustAdapter extends RecyclerView.Adapter<DustAdapter.ViewHolder> {
    
    private ArrayList<DustData> mList;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img;
        private TextView textView_region, textView_dust_h, textView_dust_n;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView_img = (ImageView) itemView.findViewById(R.id.imageView_img);
            textView_region = (TextView) itemView.findViewById(R.id.textView_region);
            textView_dust_h = (TextView) itemView.findViewById(R.id.textView_dust_h);
            textView_dust_n = (TextView) itemView.findViewById(R.id.textView_dust_n);

        }
    }

    //생성자
    public DustAdapter(ArrayList<DustData> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public DustAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dust_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DustAdapter.ViewHolder holder, int position) {
        holder.textView_region.setText(String.valueOf(mList.get(position).getRegion()));
        holder.textView_dust_h.setText(String.valueOf(mList.get(position).getDust_h()));
        holder.textView_dust_n.setText(String.valueOf(mList.get(position).getDust_n()));
        //다 해줬는데도 GlideApp 에러가 나면 rebuild project를 해주자.
       // GlideApp.with(holder.itemView).load(mList.get(position).getImg_url())
//                .override(300,400)
//                .into(holder.imageView_img);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}

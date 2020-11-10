package com.example.myfeed;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {


    private ArrayList<String> mDataset;
    private Activity activity;

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public GalleryViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
    public GalleryAdapter(Activity activity, ArrayList<String> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
    }

    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {// 이거 갤러리 레이아웃 수정하긴 했는데 맞는지 체크
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.gallerysingleview, parent, false);
        return new GalleryViewHolder(cardView);
    }
    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        cardView.setOnClickListener(new View.OnClickListener() { // 갤러리에서 이미지를 선택했을 때
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();

                String path = mDataset.get(holder.getAdapterPosition());
                resultIntent.putExtra("profilePath", mDataset.get(holder.getAdapterPosition())); //프로필 이미지
                resultIntent.putExtra("dialogPath", path); // 업로드 이미지
                resultIntent.putExtra("ad_dialogPath", mDataset.get(holder.getAdapterPosition())); // 업로드 수정 이미지

                activity.setResult(Activity.RESULT_OK, resultIntent);
                activity.finish();
            }
        });

        ImageView imageView = cardView.findViewById(R.id.imageView);
        Glide.with(activity).load(mDataset.get(position)).centerCrop().override(500).into(imageView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mDataset.size();
    }
}

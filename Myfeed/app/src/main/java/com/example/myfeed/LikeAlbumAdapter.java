package com.example.myfeed;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LikeAlbumAdapter extends RecyclerView.Adapter<LikeAlbumAdapter.GalleryViewHolder> {


    private ArrayList<String> mDataset;
    private Activity activity;

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public GalleryViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
    public LikeAlbumAdapter(Activity activity, ArrayList<String> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
    }

    public LikeAlbumAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {// 이거 갤러리 레이아웃 수정하긴 했는데 맞는지 체크
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.gallerysingleview, parent, false);
        return new GalleryViewHolder(cardView);
    }
    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {
        CardView cardView = holder.cardView;


        ImageView imageView = cardView.findViewById(R.id.imageView);
        Glide.with(activity).load(mDataset.get(position)).centerCrop().override(500).into(imageView);


        final int pos = position;

        cardView.setOnClickListener(new View.OnClickListener() { // 좋아요앨범에서 이미지를 선택했을 때
            @Override
            public void onClick(View v) {
                View dialogView = (View) View.inflate(activity, R.layout.myalbum_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(activity);
                ImageView heartphoto = (ImageView) dialogView.findViewById(R.id.heartphoto);
                heartphoto.setImageURI(Uri.parse(mDataset.get(pos)));
                //dlg.setTitle("");
                dlg.setView(dialogView);
                dlg.show();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mDataset.size();
    }
}

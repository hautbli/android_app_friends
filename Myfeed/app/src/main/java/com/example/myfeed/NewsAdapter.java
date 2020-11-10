package com.example.myfeed;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CustomViewHolder> {

    private ArrayList<NewsData> nlist;
    private Activity activity;

    NewsAdapter(Activity activity , ArrayList<NewsData> nlist){
        this.activity = activity;
        this.nlist= nlist;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView title_news, contents_news ;
        CardView cardView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title_news =itemView.findViewById(R.id.title_news);
            this.contents_news =itemView.findViewById(R.id.contents_news);
            this.cardView = itemView.findViewById(R.id.news_cardview);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item,viewGroup,false);
        return new NewsAdapter.CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {
        viewHolder.title_news.setText(nlist.get(position).getTitle_news()); // get!!
        viewHolder.contents_news.setText(nlist.get(position).getContents_news());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( nlist.get(position).getUrl()));
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != nlist ? nlist.size() : 0);
    }
}

package com.example.myfeed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Adapter1 extends RecyclerView.Adapter<Adapter1.CustomViewHolder> {
    private ArrayList<FeedData1> feedList;
    private Context mContext;
    private Activity activity;
    String MY_DATA = "MY_DATA";
    String MY_FEED = "MY_FEED";
    private SharedPreferences preferences;
    UserData userData;
    private ArrayList<Integer> timelist;
    String uid;


    // 마이 피드에 있는 자기 게시물 리사이클러뷰
// 게시물 수정 또는 편집 할 수 있다

    Adapter1(Activity activity, ArrayList<FeedData1> feedList, SharedPreferences preferences, UserData userData, ArrayList<Integer> timelist, String uid) { // 어댑터 생성자
        this.preferences = preferences;
        this.feedList = feedList;
        this.activity = activity;
        this.userData = userData;
        this.timelist = timelist;
        this.uid=uid;
    }

    @NonNull
    @Override
    //뷰홀더 생성
    public Adapter1.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedlist, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    // 화면에 보이는 아이템 뷰를 저장하는 객체, 각 뷰를 보관하는 곳!!!
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {

        FeedData1 feedData = feedList.get(position);

        String feeduid = feedData.getUid(); // 피드 데이터의 겟 유아이디랑 비교해야함!!

        viewHolder.textView.setGravity(Gravity.CENTER);
        //viewHolder.star_default.setVisibility();


        if (feedData.getImageview() != null) { // 현재 null값이어도 그 전 이미지가 로드 됐음.. 그래서 아래 else를 처리해줌..
            Glide.with(activity)
                    .load(feedList.get(position).getImageview())
                    .centerCrop()
                    .override(500)
                    .into(viewHolder.imageView);
        } else { // 디폴트 기본이미지
            viewHolder.imageView.setImageResource(R.drawable.friends);
        }
        viewHolder.textView.setText(feedData.getTextview());

        if (timelist== null){
            timelist= new ArrayList<>();
            viewHolder.star_full.setVisibility(View.INVISIBLE);
            viewHolder.star_default.setVisibility(View.VISIBLE);
        }else {
            if (timelist.contains(feedData.getCurrenttime())) {
                // uid에 저장된 timelist에 current값이 있으면 fullstar가 나오고 아니면 emptystar가 나온다
                viewHolder.star_full.setVisibility(View.VISIBLE);
                viewHolder.star_default.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.star_full.setVisibility(View.INVISIBLE);
                viewHolder.star_default.setVisibility(View.VISIBLE);
            }
        }

        //접속한 uid랑 피드에 저장된 uid 같으면 수정/삭제 보이게!!

            if (feeduid.contains(uid)) {
                viewHolder.edit.setVisibility(View.VISIBLE);
                viewHolder.delete.setVisibility(View.VISIBLE);
            } else {
                viewHolder.edit.setVisibility(View.INVISIBLE);
                viewHolder.delete.setVisibility(View.INVISIBLE);
            }

    }

    //뷰홀더 클래스 //위에서 오버라이드할거쟈네
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView textView;
        Button edit;
        Button delete;
        ImageView star_default;
        ImageView star_full;

        // 리사이클러뷰의 시이작...
        public CustomViewHolder(@NonNull View view) {
            super(view);
            this.imageView = view.findViewById(R.id.imageview_feed);
            this.textView = view.findViewById(R.id.textView_feed);
            this.cardView = view.findViewById(R.id.cardview_recyclerview);
            this.edit = view.findViewById(R.id.editbtn);
            this.delete = view.findViewById(R.id.deletebtn);
            this.star_default = view.findViewById(R.id.star_default);
            this.star_full = view.findViewById(R.id.star_full);

            //mfeedData = feedList.get(getAdapterPosition()); // 인덱스 -1 부터 돼서 추가함..


//            view.setOnCreateContextMenuListener(this); //2. 리스너 등록

//            final CustomViewHolder customViewHolder =  new CustomViewHolder( edit);
            edit.setOnClickListener(v -> {
                mStartActivity(FeedEditActivity.class, getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), feedList.size());
            });

//            final CustomViewHolder customViewHolder1 =  new CustomViewHolder( delete);
            delete.setOnClickListener(v ->{
                feedList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), feedList.size());
                saveData();
            });
//
            star_default.setOnClickListener(v -> { // 빈별을 누르면 uid에 currenttime을 저장한다 timelist로

                SharedPreferences.Editor editor = preferences.edit();
                Gson gson = new Gson();
                String json = preferences.getString(MY_FEED, null); // 키 값 , 기본값
                Type type = new TypeToken<ArrayList<FeedData1>>() {
                }.getType();
                feedList = gson.fromJson(json, type);
                FeedData1 feedData = feedList.get(getAdapterPosition());
                //타임객체
                if (timelist == null) {
                    timelist = new ArrayList<>();
                }
                timelist.add(feedData.getCurrenttime());
                //타임리스트를 데이타로
                userData.setCurrenttimeData(timelist);
                String json1 = gson.toJson(userData);
                editor.putString(MyphotoActivity3.uid, json1);
                editor.commit();


                star_full.setVisibility(View.VISIBLE);
                star_default.setVisibility(View.INVISIBLE);
                saveData();

            });

            star_full.setOnClickListener(v -> { // 꽉찬 별을 누르면 uid리스트에 있는 timelist에 currenttime을 지운다

                SharedPreferences.Editor editor = preferences.edit();
                Gson gson = new Gson();
                String json = preferences.getString(MY_FEED, null); // 키 값 , 기본값
                Type type = new TypeToken<ArrayList<FeedData1>>() {
                }.getType();
                feedList = gson.fromJson(json, type);
                FeedData1 feedData = feedList.get(getAdapterPosition());
                timelist.remove((Integer) feedData.getCurrenttime());
                userData.setCurrenttimeData(timelist);
                String json1 = gson.toJson(userData);
                editor.putString(MyphotoActivity3.uid, json1);
                editor.commit();

                star_full.setVisibility(View.INVISIBLE);
                star_default.setVisibility(View.VISIBLE);
                saveData();

            });
        }

        private void mStartActivity(Class c, int position) {
            Intent intent = new Intent(activity, c);
            intent.putExtra("mychoose", position);
            activity.startActivity(intent);

        }


//@Override
//public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
//    MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
//    MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
//    Edit.setOnMenuItemClickListener(onEditMenu);
//    Delete.setOnMenuItemClickListener(onEditMenu);
//}
//        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//
//                    // 편집 눌렀을 때 ...
//                    case 1001:
//                        mStartActivity(FeedEditActivity.class,getAdapterPosition());
//                        Log.e("ggggggggg","gggggggggg"+getAdapterPosition());
//                        break;
//
//                    case 1002: // 삭제
//                        feedList.remove(getAdapterPosition());
//                        notifyItemRemoved(getAdapterPosition());
//                        notifyItemRangeChanged(getAdapterPosition(), feedList.size());
//
//                        SharedPreferences.Editor editor = preferences.edit();
//                        Gson gson = new Gson();
//                        String json = gson.toJson(feedList);
//                        editor.putString(MY_FEED, json);
//                        //saveData();
//                        break;
//                }
//                return true;
//            }
//        };
    }

    @Override
    public int getItemCount() {

        return (null != feedList ? feedList.size() : 0);
    }

    private void saveData() {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(feedList);
        editor.putString(MY_FEED, json);
        editor.commit();
    }

}

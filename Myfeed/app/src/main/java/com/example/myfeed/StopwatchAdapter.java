package com.example.myfeed;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.data.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class StopwatchAdapter extends RecyclerView.Adapter<StopwatchAdapter.CustomViewHolder> { // 아답터 상속<>!!!

    private ArrayList<StopwatchData> slist;
    private SharedPreferences preferences;
    private Activity activity;
    private UserData userData;


    StopwatchAdapter(Activity activity, ArrayList<StopwatchData> slist, SharedPreferences preferences , UserData userData){ // 어댑터 생성자
        this.activity= activity;
        this.slist = slist;
        this.preferences = preferences;
        this.userData=userData;
    }


    // 뷰홀더 클래스!!!
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView date, starttime,fintime,strolltime;
        Button delete ;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.date =itemView.findViewById(R.id.date);
            this.starttime =itemView.findViewById(R.id.starttime);
            this.fintime =itemView.findViewById(R.id.fintime);
            this.strolltime =itemView.findViewById(R.id.stroll_time);
            this.delete= itemView.findViewById(R.id.delete_btn);

            //기록 삭제
            delete.setOnClickListener(v ->{
                slist.remove(getAdapterPosition());
                SharedPreferences.Editor editor = preferences.edit();
                Gson gson = new Gson();
                userData.setStopwatchData(slist);
                String json = gson.toJson(userData);
                editor.putString(MyphotoActivity3.uid, json);
                editor.commit();
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), slist.size());
                saveData();
            });
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stopwatch_item,viewGroup,false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {

        //StopwatchData stopwatchData = slist.get(position);

        viewHolder.date.setText(slist.get(position).getDate_text()); // get!!
        viewHolder.starttime.setText("산책 시작 : "+slist.get(position).getCurrenttime_start());
        viewHolder.fintime.setText("산책 종료 : "+slist.get(position).getCurrenttime_fin());
        viewHolder.strolltime.setText("산책 시간 : "+slist.get(position).getStroll_time());

    }

    @Override
    public int getItemCount() {
        return (null != slist ? slist.size() : 0);
    }

    private void saveData() {


    }

}

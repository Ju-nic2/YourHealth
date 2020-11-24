package com.example.yourhealth;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class sns_weekly_recyclerAdapter {
    public static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

        // adapter에 들어갈 list 입니다.
        private ArrayList<post> listData = new ArrayList<>();

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
            // return 인자는 ViewHolder 입니다.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
            holder.onBind(listData.get(position));
        }

        @Override
        public int getItemCount() {
            // RecyclerView의 총 개수 입니다.
            return listData.size();
        }

        void addItem(post data) {
            // 외부에서 item을 추가시킬 함수입니다.
            listData.add(data);
        }

        // RecyclerView의 핵심인 ViewHolder 입니다.
        // 여기서 subView를 setting 해줍니다.
        class ItemViewHolder extends RecyclerView.ViewHolder {

            private TextView textView_name;
            private TextView textView_title;
            private CircleImageView circleimageView;

            ItemViewHolder(View itemView) {
                super(itemView);

                textView_name = itemView.findViewById(R.id.name);
                textView_title = itemView.findViewById(R.id.title);
                circleimageView = itemView.findViewById(R.id.profile_image4);
            }

            void onBind(post data) {
                textView_title.setText(data.getTitle());
                textView_name.setText(data.getName());
                circleimageView.setImageResource(data.getResId());
                final int postid = data.getPostID();
                itemView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {

                        ComponentName componentName = new ComponentName(
                                "com.example.yourhealth",
                                "com.example.yourhealth.sns_routine"
                        );
                        Intent intent = new Intent();
                        intent.setComponent(componentName);


                        intent.putExtra("postid", postid);
                        view.getContext().startActivity(intent);


                    }
                });
            }
        }
    }
}

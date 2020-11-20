package com.example.yourhealth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class sns_main_recyclerAdapter extends RecyclerView.Adapter<sns_main_recyclerAdapter.ItemViewHolder>
{
    // adapter에 들어갈 list 입니다.
    private ArrayList<postBox> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itembox, parent, false);
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

    void addItem(postBox data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_name1;
        private TextView textView_title1;
        private TextView textView_name2;
        private TextView textView_title2;
        private TextView textView_name3;
        private TextView textView_title3;
        private TextView textView_category;

        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);
            textView_name1 = itemView.findViewById(R.id.name1);
            textView_title1 = itemView.findViewById(R.id.title1);
            textView_name2 = itemView.findViewById(R.id.name2);
            textView_title2 = itemView.findViewById(R.id.title2);
            textView_name3 = itemView.findViewById(R.id.name3);
            textView_title3 = itemView.findViewById(R.id.title3);

            textView_category = itemView.findViewById(R.id.category);
        }

        void onBind(postBox data) {
            post post1 = data.getPost1();
            post post2 = data.getPost2();
            post post3 = data.getPost3();

            textView_name1.setText(post1.getName());
            textView_title1.setText(post1.getTitle());
            textView_name2.setText(post2.getName());
            textView_title2.setText(post2.getTitle());
            textView_name3.setText(post3.getName());
            textView_title3.setText(post3.getTitle());

            textView_category.setText(data.getCategory());


        }
    }
}

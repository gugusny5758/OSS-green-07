package com.example.hojinseo.osstest;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Home on 2017-10-28.
 */

public class CardNewsAdapter extends RecyclerView.Adapter<CardNewsAdapter.ViewHolder>{
    Context context;
    ArrayList<HashMap<String,String>> cardNewsList; //공지사항 정보 담겨있음

    public CardNewsAdapter(Context context, ArrayList<HashMap<String,String>> noticeList) {
        this.context = context;
        this.cardNewsList = noticeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_view_item,null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d("Position",String.valueOf(position));
        HashMap<String,String> cardNewsItem = cardNewsList.get(position);

        holder.fromContent.setText(cardNewsItem.get("fromContent"));
        if(cardNewsItem.get("imgItem").equals("1"))
            holder.imageView.setImageResource(R.drawable.default_album_cover);
        else
            holder.imageView.setImageResource(R.drawable.default_album_cover2);
        if(holder.fromContent.getText().equals("FaceBook"))
            holder.fromImg.setImageResource(R.drawable.facebook_icon_favorite);
        else
            holder.fromImg.setImageResource(R.drawable.twitter_icon_favorite);

        holder.cv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(v.getContext(), holder.fromContent.getText()+" Card",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.cardNewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fromContent;
        ImageView fromImg;
        ImageView imageView;
        CardView cv;

        public ViewHolder(View v) {
            super(v);
            fromContent = (TextView) v.findViewById(R.id.fromContent);
            fromImg = (ImageView) v.findViewById(R.id.fromImg);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            cv = (CardView) v.findViewById(R.id.cv);
        }
    }
}

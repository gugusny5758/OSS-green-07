package com.example.home.osstest;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



/**
 * Created by Home on 2017-10-28.
 */

public class CardNewsAdapter extends RecyclerView.Adapter<CardNewsAdapter.ViewHolder> {
    Context context;
    ArrayList<CardNewsItem> cardNewsList; //각 Card에 대한 정보를 담고 있음.   //Info about each Card
    CardNewsItem cardNewsItem;

    public CardNewsAdapter(Context context, ArrayList<CardNewsItem> cardNewsList) {
        this.context = context;
        this.cardNewsList = cardNewsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결    //repeated item layout connect to recycler view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_view_item, null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        cardNewsItem = cardNewsList.get(position);
        holder.imageView.setTag(position);

        String where = cardNewsItem.getWhere();
        String headline = cardNewsItem.getHeadline();

        holder.fromContent.setText(headline);

        if (cardNewsItem.getCards() != null) {

            //이미지를 가져옴  //Bring Image
            Uri uri = Uri.fromFile(new File(cardNewsItem.getCards().get(0)));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

                holder.imageView.setImageBitmap(scaled);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        if (where.equals("FaceBook"))
            holder.fromImg.setImageResource(R.drawable.facebook_icon_favorite);
        else if (where.equals("Twitter"))
            holder.fromImg.setImageResource(R.drawable.twitter_icon_favorite);
        else
            holder.fromImg.setImageResource(R.drawable.default_favorite_icon);

        //카드를 클릭했을 때 이벤트    //Event for clicking Card
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent cardNews_view = new Intent(context.getApplicationContext(), CardNewsView.class);
                cardNews_view.putExtra("item", cardNewsList.get((int) holder.imageView.getTag()));
                context.startActivity(cardNews_view);
            }
        });

        //공유버튼을 클릭했을 때 이벤트  //Event for clicking Share Button
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "공유하기", Toast.LENGTH_SHORT).show();  //text:Share
                ((HomeActivity)context).shareImage(cardNewsItem.getCards());


            }
        });

    }


    @Override
    public int getItemCount() {
        return this.cardNewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fromContent;//어디서 가져왔는지에대한 정보  //Info about where it is from
        ImageView fromImg;//각 위치에대한 Favorite Icon   //Favorite Icon on each location
        ImageView imageView;//메인 이미지    //Main Image
        ImageView shareBtn;//공유 버튼  //Share Button
        CardView cv;//전체적인 화면 뷰     //Overall Layout View


        public ViewHolder(View v) {
            super(v);
            fromContent = (TextView) v.findViewById(R.id.fromContent);
            fromImg = (ImageView) v.findViewById(R.id.fromImg);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            shareBtn = (ImageView) v.findViewById(R.id.shareBtn);
            cv = (CardView) v.findViewById(R.id.cv);
        }
    }
}

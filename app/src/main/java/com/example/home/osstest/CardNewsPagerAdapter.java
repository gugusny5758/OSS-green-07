package com.example.home.osstest;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Home on 2017-11-02.
 */

public class CardNewsPagerAdapter extends PagerAdapter {
    LayoutInflater inflater;
    ArrayList<String> cards;

    public CardNewsPagerAdapter(LayoutInflater inflater, ArrayList<String> cards){
        this.inflater = inflater;
        this.cards = cards;
    }

    public Object instantiateItem(ViewGroup container, int position){
        View v = inflater.inflate(R.layout.viewpager_image,null);
        ImageView imgView = (ImageView) v.findViewById(R.id.viewPagerImage);
        //이미지를 가져옴  //Bring Image
        Uri uri = Uri.fromFile(new File(cards.get(position)));

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(inflater.getContext().getContentResolver(),uri);
            int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

            imgView.setImageBitmap(scaled);

        } catch (IOException e) {
            e.printStackTrace();
        }
        container.addView(v);

        return v;
    }

    //Page에서 보이지 않는 View를 제거해주는 Method      //Method : Remove View Unseen at Page
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View)object);

    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

package com.example.home.osstest;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by Home on 2017-11-02.
 */

public class CardNewsView extends AppCompatActivity {

    ViewPager pager;
    ArrayList<String> cards;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardnews_view);

        pager = (ViewPager)findViewById(R.id.viewPager);

        CardNewsPagerAdapter cnpa = new CardNewsPagerAdapter(getLayoutInflater(),(ArrayList<String>) getIntent().getSerializableExtra("Cards"));

        pager.setAdapter(cnpa);
    }
}

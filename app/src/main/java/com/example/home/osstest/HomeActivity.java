package com.example.home.osstest;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.nileshp.multiphotopicker.photopicker.activity.PickImageActivity;

import java.util.ArrayList;

/**
 * Created by Home on 2017-10-28.
 */

public class HomeActivity extends AppCompatActivity {

    ArrayList<CardNewsItem> cardNewsList;
    CardNewsAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

        //타이틀바의 글자를 중앙으로 만들기위해서 CustomBar를 제작.
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        this.getSupportActionBar().setCustomView(R.layout.home_titlebar);

        //CardNews View
        cardNewsList = new ArrayList<CardNewsItem>();
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        rv.setHasFixedSize(true);

        //layoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);

//        //저장되어 있는 정보를 CardNewsItem의 객채로 생성하여 어댑터에 넘겨줌.(일단 테스트용)
//        for (int i = 0; i < 2; i++) {
//            CardNewsItem item = new CardNewsItem("FaceBook", null);
//            cardNewsList.add(item);
//
//            item = new CardNewsItem("Twitter", null);
//            cardNewsList.add(item);
//        }

        //카드 리스트뷰 어댑터에 연결
        adapter = new CardNewsAdapter(getApplicationContext(), cardNewsList);
        rv.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    //Home 메뉴에 대한 이벤트
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addCard: //Card 생성 이벤트
            {
                Intent mIntent = new Intent(getApplicationContext(), PickImageActivity.class);
                mIntent.putExtra(PickImageActivity.KEY_LIMIT_MAX_IMAGE, 60);
                mIntent.putExtra(PickImageActivity.KEY_LIMIT_MIN_IMAGE, 3);
                startActivityForResult(mIntent, PickImageActivity.PICKER_REQUEST_CODE);
            }
            return true;

            case R.id.removeCard://Card  삭제 이벤트

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //현재 Activity에 메뉴 Layout을 지정함.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    //Home 화면에서 뒤로가기 버튼 이벤트
    public void onBackPressed() {
        AlertDialog.Builder logout = new AlertDialog.Builder(this);
        logout.setTitle("안내");
        logout
                .setMessage("로그아웃하시겠습니까?")
                .setCancelable(false)
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HomeActivity.super.onBackPressed();
                    }
                });
        AlertDialog alert = logout.create();
        alert.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (resultCode == -1 && requestCode == PickImageActivity.PICKER_REQUEST_CODE) {
            //가지고 온 이미지들을 이용하여 CardNews 객체 생성.
            CardNewsItem item = new CardNewsItem("MadeInHome", intent.getExtras().getStringArrayList(PickImageActivity.KEY_DATA_RESULT));
            cardNewsList.add(item);
            adapter.notifyDataSetChanged();
        }
    }


}








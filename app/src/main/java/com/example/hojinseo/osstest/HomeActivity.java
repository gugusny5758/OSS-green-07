package com.example.hojinseo.osstest;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.id.content;


/**
 * Created by Home on 2017-10-28.
 */

public class HomeActivity extends AppCompatActivity {
    private static final String TAG_FROMCONTENT = "fromContent";
    private static final String TAG_IMGITEM = "imgItem";
    ArrayList<HashMap<String,String>> cardNewsList;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);
        //타이틀바의 글자를 중앙으로 만들기위해서 CustomBar를 제작.
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        this.getSupportActionBar().setCustomView(R.layout.home_titlebar);

        //CardNews View
        cardNewsList = new ArrayList<HashMap<String, String>>();
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        rv.setHasFixedSize(true);
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        //rv.addItemDecoration(new ItemSpace(15));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);

        //HashMap에 붙이기
        for(int i = 0; i<6; i++) {
            if(i%2==0) {
                HashMap<String, String> posts = new HashMap<String, String>();
                posts.put(TAG_FROMCONTENT, "FaceBook");
                posts.put(TAG_IMGITEM, "1");

                cardNewsList.add(posts);
            }else{
                HashMap<String,String> posts2 = new HashMap<String,String>();
                posts2.put(TAG_FROMCONTENT,"Twitter");
                posts2.put(TAG_IMGITEM,"2");

                cardNewsList.add(posts2);
            }
        }

        //카드 리스트뷰 어댑터에 연결
        CardNewsAdapter adapter = new CardNewsAdapter(getApplicationContext(),cardNewsList);
        Log.e("onCreate[cardNewsList]", "" + cardNewsList.size());
        rv.setAdapter(adapter);

        adapter.notifyDataSetChanged();


    }

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

//    public void makeList() {
//        try {
//            //HashMap에 붙이기
//            HashMap<String,String> posts = new HashMap<String,String>();
//            posts.put(TAG_TITLE,"title");
//            posts.put(TAG_WRITER,"writer");
//            posts.put(TAG_DATE,"date");
//            posts.put(TAG_CONTENT, "content");
//
//            //ArrayList에 HashMap 붙이기
//            cardNewsList.add(posts);
//
//            //카드 리스트뷰 어댑터에 연결
//            CardNewsAdapter adapter = new CardNewsAdapter(getApplicationContext(),cardNewsList);
//            Log.e("onCreate[cardNewsList]", "" + cardNewsList.size());
//            rv.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
}

class ItemSpace extends RecyclerView.ItemDecoration{
    private final int space;

    ItemSpace(int space){
        this.space = space;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parnet, RecyclerView.State state){
        super.getItemOffsets(outRect,view,parnet,state);
        outRect.right = space;

    }

}







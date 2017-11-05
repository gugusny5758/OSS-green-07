package com.example.home.osstest;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.nileshp.multiphotopicker.photopicker.activity.PickImageActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 2017-10-28.
 */

public class HomeActivity extends AppCompatActivity {

    ArrayList<CardNewsItem> cardNewsList;
    CardNewsAdapter adapter;
    private String headLine = "";

    private CallbackManager callbackManager;
    ShareDialog shareDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);
        callbackManager  = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

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

        //카드 리스트뷰 어댑터에 연결
        adapter = new CardNewsAdapter(this, cardNewsList);
        rv.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        Toast.makeText(getApplicationContext(), this.getIntent().getStringExtra("userName")+"님 환영합니다.",Toast.LENGTH_SHORT).show();

    }

    //Home 메뉴에 대한 이벤트
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addCard: //Card 생성 이벤트
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Title");

// Set up the input
                final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        headLine = input.getText().toString();

                        Intent mIntent = new Intent(getApplicationContext(), PickImageActivity.class);
                        mIntent.putExtra(PickImageActivity.KEY_LIMIT_MAX_IMAGE, 60);
                        mIntent.putExtra(PickImageActivity.KEY_LIMIT_MIN_IMAGE, 3);
                        startActivityForResult(mIntent, PickImageActivity.PICKER_REQUEST_CODE);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
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

        if (resultCode == -1 && requestCode == PickImageActivity.PICKER_REQUEST_CODE) {
            //가지고 온 이미지들을 이용하여 CardNews 객체 생성.
            CardNewsItem item = new CardNewsItem(this.getIntent().getStringExtra("from"), intent.getExtras().getStringArrayList(PickImageActivity.KEY_DATA_RESULT),headLine);
            cardNewsList.add(item);
            adapter.notifyDataSetChanged();
        }

        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode()) {
            callbackManager.onActivityResult(requestCode, resultCode, intent);
        }

    }

    public void shareImage(ArrayList<String> cards){
        try {
            List<SharePhoto> convertCards = new ArrayList<SharePhoto>();
            SharePhoto photo;

            for (int i = 0; i < cards.size(); i++) {
                Uri uri = Uri.fromFile(new File(cards.get(i)));
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

                photo = new SharePhoto.Builder()
                        .setBitmap(scaled)
                        .build();
                convertCards.add(photo);
            }

            ShareContent content  = new SharePhotoContent.Builder().addPhotos(convertCards).build();

            if(ShareDialog.canShow(SharePhotoContent.class)){
                shareDialog.show(content);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}








package com.example.home.osstest;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity {

    private TwitterLoginButton loginButton;

    private static final String key = "vjsomtQmsznhYVPqYqbWKQHVC";
    private static final String secret = "WUdyADzUAord54zahdFfx8ksbl1y9NKyKwbPid3IT9JkJqdc9B";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(key, secret))
                .debug(true)
                .build();
        Twitter.initialize(config);
        setContentView(R.layout.activity_main);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        final EditText selectEditText = (EditText) findViewById(R.id.passTextField);

        loginButton = findViewById(R.id.twitterLogBtn);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Log.d("Twiter-Auth","Login Success");
                ((EditText) findViewById(R.id.passTextField)).setText("");//입력했던 비밀번호 리셋
                Intent home_view = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(home_view);
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.d("Twiter-Auth","Login Failure");
                Toast.makeText(getApplicationContext(), "아이디/비밀번호를 확인하세요.",Toast.LENGTH_SHORT).show();
            }
        });
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(selectEditText.getWindowToken(),0);
                switch (view.getId()) {
                    case R.id.logInBtn :
//                        메인 로그인 버튼 이벤트
//                            if(mainLogInCheck()){
                            if(true){
                                ((EditText) findViewById(R.id.passTextField)).setText("");//입력했던 비밀번호 리셋
                                Intent home_view = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(home_view);
                            }else
                                Toast.makeText(getApplicationContext(), "아이디/비밀번호를 확인하세요.",Toast.LENGTH_SHORT).show();
                        break ;
                    case R.id.facebookLogBtn :
//                        페이스북 로그인 버튼 이벤트
                        Toast.makeText(getApplicationContext(), "FaceBook 로그인",Toast.LENGTH_SHORT).show();
                        break ;
                    case R.id.twitterLogBtn :
//                        트위터 로그인 버튼 이벤트
                        Toast.makeText(getApplicationContext(), "Twitter 로그인",Toast.LENGTH_SHORT).show();

                        break ;

                    default:
                        break ;
                }
            }
        } ;

        int[] btnItem = {R.id.logInBtn, R.id.facebookLogBtn, R.id.twitterLogBtn};

        for(int id : btnItem){
            Button btn = (Button) findViewById(id);
            btn.setOnClickListener(onClickListener);
        }


    }

    private boolean mainLogInCheck(){

        EditText idText = (EditText) findViewById(R.id.idTextField);
        EditText passText = (EditText) findViewById(R.id.passTextField);

        if(String.valueOf(idText.getText()).equals("Tester") && String.valueOf(passText.getText()).equals("Tester")){
            return true;
        }else{
            return false;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}



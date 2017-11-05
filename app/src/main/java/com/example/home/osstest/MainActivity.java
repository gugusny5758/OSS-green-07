package com.example.home.osstest;

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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TwitterLoginButton twitterLoginButton;
    private CallbackManager callbackManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Twitter.initialize(this);
        setContentView(R.layout.activity_main);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        final EditText selectEditText = (EditText) findViewById(R.id.passTextField);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(selectEditText.getWindowToken(),0);

                switch (view.getId()) {
                    case R.id.logInBtn :
//                        메인 로그인 버튼 이벤트
//                           if(mainLogInCheck()){
                        if(true){
                                ((EditText) findViewById(R.id.passTextField)).setText("");//입력했던 비밀번호 리셋
                                Intent home_view = new Intent(getApplicationContext(),HomeActivity.class);
                               home_view.putExtra("userName","Tester");
                               home_view.putExtra("from", "main");
                                startActivity(home_view);
                            }else
                                Toast.makeText(getApplicationContext(), "아이디/비밀번호를 확인하세요.",Toast.LENGTH_SHORT).show();
                        break ;
                    case R.id.facebookLogBtn :
//                        페이스북 로그인 버튼 이벤트
                        faceBookLogIn();
                        break ;
//트위터는 버튼 특성에 따라서 따로 만듬.
                    default:
                        break ;
                }
            }
        } ;

        int[] btnItem = {R.id.logInBtn, R.id.facebookLogBtn};

        for(int id : btnItem){
            Button btn = (Button) findViewById(id);
            btn.setOnClickListener(onClickListener);
        }

        //트위터 버튼 이벤트 설정
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitterLogBtn);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls

                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

                String userName = session.getUserName();
                Intent home_view = new Intent(getApplicationContext(),HomeActivity.class);
                home_view.putExtra("userName",userName);
                home_view.putExtra("from", "twitter");
                startActivity(home_view);
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(getApplicationContext(), "아이디/비밀번호를 확인하세요.",Toast.LENGTH_SHORT).show();
            }
        });


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

    private void faceBookLogIn(){
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.facebookLogBtn);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        Intent home_view = new Intent(MainActivity.this,HomeActivity.class);
                        try {
                            home_view.putExtra("userName", (String) object.get("name"));
                            home_view.putExtra("from", "faceBook");
                        }catch (JSONException e){

                        }
                        startActivity(home_view);

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                setResult(3);
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr",error.toString());
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            Log.d("ResultCode", "페이스북 로그인");
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }else{
            Log.d("ResultCode", "트위터 로그인");
            twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        }


    }

}



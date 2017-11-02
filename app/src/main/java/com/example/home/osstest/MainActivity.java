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
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private CallbackManager callbackManager;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//                            if(mainLogInCheck()){
                            if(true){
                                ((EditText) findViewById(R.id.passTextField)).setText("");//입력했던 비밀번호 리셋
                                Intent home_view = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(home_view);
                            }else
                                Toast.makeText(getApplicationContext(), "아이디/비밀번호를 확인하세요.",Toast.LENGTH_SHORT).show();
                        break ;
                    case R.id.facebookLogBtn :
                        facebookLoginOnClick();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void facebookLoginOnClick(){

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult result) {

                GraphRequest request;
                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        if (response.getError() != null) {

                        } else {
                            Log.i("TAG", "user: " + user.toString());
                            Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());
                            setResult(RESULT_OK);

                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                //finish();
            }

            @Override
            public void onCancel() {
                //finish();
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

}





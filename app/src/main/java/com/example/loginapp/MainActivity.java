package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("know","INSIDE MAIN_ACTIVITY");
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("token","");
        Log.d("ACCESS_TOKEN",accessToken);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent nextActivity;

                if(accessToken != ""){
                    Log.d("know","Choosing Home Activity");
                    nextActivity = new Intent(MainActivity.this,AuthorizedContent.class);
                }else{
                    Log.d("know","Choosing Login Activity");
                    nextActivity = new Intent(MainActivity.this, LoginActivity.class);
                }
                Log.d("know","Switching to chosen activity");
                startActivity(nextActivity);
            }
        },4000);


    }


}
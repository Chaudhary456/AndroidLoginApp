package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
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

    private static final String CHANNEL_ID = "LOGIN_APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///Registering Broadcast Receiver
        ContentReceiver contentReceiver = new ContentReceiver();
        registerReceiver(contentReceiver,new IntentFilter("android.intent.action.AIRPLANE_MODE"));
        registerReceiver(contentReceiver,new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED"));
        registerReceiver(contentReceiver,new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED"));
        registerReceiver(contentReceiver,new IntentFilter("android.intent.action.BOOT_COMPLETED"));
        registerReceiver(contentReceiver,new IntentFilter("android.intent.action.MEDIA_SHARED"));

        //Creating Notification Channel
        createNotificationChannel();




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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channel_name = "My Channel";
            String channel_description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channel_name, importance);
            channel.setDescription(channel_description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}
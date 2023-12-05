package com.example.loginapp;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements AsyncTaskListener {

    EditText login_username;
    EditText login_password;
    Button loginButton;

    @Override
    public void onEventPost(String response) {
        LoginResponse loginResponse = LoginResponse.fromJson(response);

        ////////Storing Shared-preference/////////
        SharedPreferences sharedPreferences = getSharedPreferences("accessToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", loginResponse.getToken());
        editor.apply();

        Log.d("login_token",loginResponse.getToken());
        Log.d("login_username",loginResponse.getUsername());
        Log.d("RES_MESSAGE",response.toString());



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Welcome")
                .setMessage(loginResponse.getToken())
                .setPositiveButton("OK", null)
                .show();


        /////////////// Diverting back to Home Activity////////////////
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the second activity after a delay of 2 seconds
                Intent nextActivity = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(nextActivity);
            }
        }, 2000);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_username = findViewById(R.id.loginusername);
        login_password = findViewById(R.id.loginpassword);
        loginButton = findViewById(R.id.loginButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TinyURL = "https://dummyjson.com/auth/login";

                MyAsyncTask login = new MyAsyncTask(LoginActivity.this);
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("username", login_username.getText().toString());
                    jsonObject.put("password", login_password.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                String jsonString = jsonObject.toString();
                login.execute(TinyURL, jsonString);

            }
        });

    }
}
package com.example.loginapp;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements AsyncTaskListener {

    EditText login_username;
    EditText login_password;
    Button loginButton;

    @Override
    public void onEventPost(Response response) {

        Log.d("CODE_MESSAGE", String.valueOf(response.code()));

        if(response.code() == 400){

            ErrorResponse errorResponse = null;
            try {
                errorResponse = ErrorResponse.fromJson(response.body().string());
                Log.d("ERROR_TRY_MESSAGE", errorResponse.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage(errorResponse.getMessage())
                    .setPositiveButton("OK", null)
                    .show();
        }else {

            LoginResponse loginResponse = null;
            try {
                loginResponse = LoginResponse.fromJson(response.body().string());
                Log.d("LOGIN_TRY_MESSAGE", loginResponse.getToken());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ////////Storing Shared-preference/////////
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("firstname", loginResponse.getFirstName());
            editor.putString("lastname", loginResponse.getLastName());
            editor.putString("token", loginResponse.getToken());
            editor.putString("image", loginResponse.getImage());
            editor.putString("email", loginResponse.getEmail());
            editor.apply();


            /////////////// Alert Box ////////////////
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Welcome")
                    .setMessage(loginResponse.getFirstName())
                    .setPositiveButton("OK", null)
                    .show();


            /////////////// Diverting back to Home Activity////////////////
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Start the second activity after a delay of 2 seconds
                    Intent nextActivity = new Intent(LoginActivity.this, AuthorizedContent.class);
                    startActivity(nextActivity);
                }
            }, 2000);

        }
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

                if(login_username.getText().toString().trim().isEmpty() || login_password.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        jsonObject.put("username", login_username.getText().toString());
                        jsonObject.put("password", login_password.getText().toString());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    String jsonString = jsonObject.toString();
                    login.execute(TinyURL, jsonString);
                }
            }
        });

    }

}
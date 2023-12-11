package com.example.loginapp;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements AsyncTaskListener {

    EditText login_username;
    EditText login_password;
    Button loginButton;

    static ProgressBar progressBar;
    @Override
    public void onEventPost(Response response) {

        if(response != null){
            progressBar.setVisibility(View.GONE);
            Log.d("CODE_MESSAGE", String.valueOf(response.code()));

            if(response.code() == 400){

                ErrorResponse errorResponse = null;
                try {
                    errorResponse = ErrorResponse.fromJson(response.body().string());
                    Log.d("ERROR_TRY_MESSAGE", errorResponse.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                showDialog("Error",errorResponse.getMessage());

            }else {

                LoginResponse loginResponse = null;
                try {
                    loginResponse = LoginResponse.fromJson(response.body().string());
                    Log.d("LOGIN_TRY_MESSAGE", loginResponse.getToken());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //Storing Shared-preference
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("firstname", loginResponse.getFirstName());
                editor.putString("lastname", loginResponse.getLastName());
                editor.putString("token", loginResponse.getToken());
                editor.putString("image", loginResponse.getImage());
                editor.putString("email", loginResponse.getEmail());
                editor.apply();


                // Alert Box
                showDialog("Welcome",loginResponse.getFirstName());



                // Diverting back to Home Activity
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Start the second activity after a delay of 2 seconds
                        Intent nextActivity = new Intent(LoginActivity.this, AuthorizedContent.class);
                        startActivity(nextActivity);
                    }
                }, 2000);

                //GENERATING NOTIFICATION
                generateNotification();

            }
        }else{
            showDialog("Technical Glitch","Try Again");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);

        //Disabling Progress Bar
        progressBar.setVisibility(View.GONE);

        login_username = findViewById(R.id.loginusername);
        login_password = findViewById(R.id.loginpassword);
        loginButton = findViewById(R.id.loginButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    //Setting Progress Spinner
                    progressBar.setVisibility(view.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }

    public void showDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private PendingIntent createPendingIntent() {
        Intent intent = new Intent(this, AuthorizedContent.class);
        intent.putExtra("FRAGMENT_TO_LOAD", "FragmentIdentifier");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void generateNotification(){

        PendingIntent pendingIntent = createPendingIntent();

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "LOGIN_APP")
                .setSmallIcon(R.drawable.baseline_android_24)
                .setContentTitle("Login Successful!")
                .setContentText("Tap to see your Profile")
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Hi You have successfully loggedIn , We have prepared a profile for you tap this notification to see your profile page."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);  // Automatically dismiss the notification when tapped

        // Show the notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
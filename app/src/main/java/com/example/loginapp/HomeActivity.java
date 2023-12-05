package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button logout_btn;
    Button github_btn;
    Button linkedin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout_btn = findViewById(R.id.logoutbtn);
        github_btn = findViewById(R.id.github_btn);
        linkedin_btn = findViewById(R.id.linkedin_btn);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("accessToken", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token","");
                editor.apply();

                //////////Diverting to LoginActivity/////////
                Intent nextActivity = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(nextActivity);
            }
        });

        github_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(HomeActivity.this, BrowserActivity.class);
                startActivity(nextActivity);
            }
        });

        linkedin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotUrl("https://in.linkedin.com/in/soumya-chaudhary-972500188");
            }
        });


    }

    private void gotUrl(String url){
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
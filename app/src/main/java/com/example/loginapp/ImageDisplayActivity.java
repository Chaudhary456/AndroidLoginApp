package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        // Retrieve the image URI from the intent
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("imageUri");

        if (imageUriString != null) {
            // Convert the image URI string back to Uri
            Uri imageUri = Uri.parse(imageUriString);

            // Display the image in an ImageView
            ImageView imageView = findViewById(R.id.image_view);
            imageView.setImageURI(imageUri);
        }
    }

}
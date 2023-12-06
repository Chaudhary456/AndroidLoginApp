package com.example.loginapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView profileImageView = view.findViewById(R.id.profileImageView);
        TextView name_data = view.findViewById(R.id.name_data);
        TextView email_data = view.findViewById(R.id.email_data);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String imageUrl = sharedPreferences.getString("image","");
        String firstname = sharedPreferences.getString("firstname","");
        String lastname = sharedPreferences.getString("lastname","");
        String email = sharedPreferences.getString("email","");

        Log.d("EMAIL",email);
        Log.d("FIRSTNAME",firstname);

        name_data.setText(firstname+lastname);
        email_data.setText(email);

        Picasso.get().load(imageUrl).into(profileImageView);
        return view;
    }
}
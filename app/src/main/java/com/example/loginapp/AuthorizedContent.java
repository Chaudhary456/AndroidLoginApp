package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class AuthorizedContent extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    Button github_btn;
    Button linkedin_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized_content);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("FRAGMENT_TO_LOAD")) {
            String fragmentIdentifier = intent.getStringExtra("FRAGMENT_TO_LOAD");

            // Load the appropriate fragment based on the identifier
            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragmentManager.getBackStackEntryCount() > 0) {
                // Pop the last fragment from the back stack
                fragmentManager.popBackStack();
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Check the fragmentIdentifier and add the corresponding fragment
            if ("FragmentIdentifier".equals(fragmentIdentifier)) {
                ProfileFragment fragment = new ProfileFragment();
                fragmentTransaction.replace(R.id.content_main, fragment);
            }
            fragmentTransaction.addToBackStack(null);
            // Commit the transaction
            fragmentTransaction.commit();
        }



        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);


        github_btn = findViewById(R.id.github_btn);
        linkedin_btn = findViewById(R.id.linkedin_btn);

        github_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Log.d("SUCCESS_MSG","TRY LOAD BROWSER ACTIVITY");
                        Intent nextActivity = new Intent(AuthorizedContent.this, BrowserActivity.class);
                        startActivity(nextActivity);

            }
        });

        linkedin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotUrl("https://in.linkedin.com/in/soumya-chaudhary-972500188");
            }
        });

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if(id == R.id.logout){
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token","");
                    editor.putString("image","");
                    editor.putString("firstname","");
                    editor.putString("lastname","");
                    editor.putString("email","");
                    editor.apply();

                    //////////Diverting to LoginActivity/////////
                    Intent nextActivity = new Intent(AuthorizedContent.this, LoginActivity.class);

                    startActivity(nextActivity);
                }
                else if(id == R.id.notes){
                    Log.d("selection","Notes selected");
                    loadFragment(new NotesFragment());
                }else if(id == R.id.settings){
                    Log.d("selection","Settings selected");
                    loadFragment(new SettingsFragment());
                }else if(id == R.id.profile){
                    Log.d("selection","Profile selected");
                    loadFragment(new ProfileFragment());
                }else{
                    Log.d("selection","Home selected");
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    // Check if there are fragments in the back stack
                    if (fragmentManager.getBackStackEntryCount() > 0) {
                        // Pop the last fragment from the back stack
                        fragmentManager.popBackStack();
                    }
//                    loadFragment(new HomeFragment() );
                }


                //////Close Drawer after an item is selected./////////
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }


        });
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            FragmentManager fragmentManager = getSupportFragmentManager();

            // Check if there are fragments in the back stack
            if (fragmentManager.getBackStackEntryCount() > 0) {
                // Pop the last fragment from the back stack
                fragmentManager.popBackStack();
            } else {
                // If no fragments in the back stack, let the default behavior handle the back press
                finishAffinity();
                super.onBackPressed();
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {
            // Pop the last fragment from the back stack
            fragmentManager.popBackStack();
        }

        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_main,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void gotUrl(String url){
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }


}
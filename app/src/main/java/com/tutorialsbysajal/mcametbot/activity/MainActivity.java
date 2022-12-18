package com.tutorialsbysajal.mcametbot.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.tutorialsbysajal.mcametbot.R;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button signOutBtn;
    TextView user_email,user_name;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    // key for storing email.
    public static final String EMAIL_KEY = "email_key";
    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";
    public static final String USER_KEY = "user_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        user_name = findViewById(R.id.user_name);
        toolbar = findViewById(R.id.toolbar);
        signOutBtn = findViewById(R.id.signOutBtn);
        user_email = findViewById(R.id.user_email);
        initilize();

    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(this, "Log out sucessfully...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void initilize() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String em = sharedPreferences.getString(EMAIL_KEY, "SAJAL");
        String userName = sharedPreferences.getString(USER_KEY,"null");
        user_name.setText(userName);
        user_email.setText(em);
    }
}
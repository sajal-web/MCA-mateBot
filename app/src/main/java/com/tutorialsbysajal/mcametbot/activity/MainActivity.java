package com.tutorialsbysajal.mcametbot.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tutorialsbysajal.mcametbot.R;

public class MainActivity extends AppCompatActivity {
    Button signOutBtn;
    TextView user_email;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    // key for storing email.
    public static final String EMAIL_KEY = "email_key";
    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signOutBtn = findViewById(R.id.signOutBtn);
        user_email = findViewById(R.id.user_email);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String em = sharedPreferences.getString(EMAIL_KEY, "SAJAL");
        user_email.setText(em);
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
}
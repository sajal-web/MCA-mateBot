package com.tutorialsbysajal.mcametbot.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.tutorialsbysajal.mcametbot.R;

public class SplashScreen extends AppCompatActivity {
    String email, password;
    TextView emul;
    Handler handler = new Handler(Looper.getMainLooper());
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    // key for storing email.
    public static final String EMAIL_KEY = "email_key";
    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String em = sharedPreferences.getString(EMAIL_KEY, "null");

        emul = findViewById(R.id.emul);


        emul.setText(em);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(emul.getText()!="null"){
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },2000);

    }
}
package com.tutorialsbysajal.mcametbot.activity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tutorialsbysajal.mcametbot.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageSlider imageSlider;
    NavigationView navigationView;
    Toolbar toolbar;
    Button signOutBtn;
    TextView user_email, user_name;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    // key for storing email.
    public static final String EMAIL_KEY = "email_key";
    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";
    public static final String USER_KEY = "user_key";
    FirebaseFirestore database = FirebaseFirestore.getInstance();

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
        imageSlider = findViewById(R.id.imageSlider);

        initilize();
        setupImageSlider();
    }

    private void setupImageSlider() {
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        database.collection("sliderImages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        slideModels.add(new SlideModel(queryDocumentSnapshot.getString("url"), ScaleTypes.FIT));
                        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Can't load image", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Can't load image", Toast.LENGTH_SHORT).show();
            }
        });
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
        getSupportActionBar().setTitle("MCAmateBot");
        toolbar.setTitleTextColor(Color.BLACK);
        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bottom));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        toggle.syncState();
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String em = sharedPreferences.getString(EMAIL_KEY, "SAJAL");
        String userName = sharedPreferences.getString(USER_KEY, "null");
        user_name.setText(userName);
        user_email.setText(em);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }


    public void goToDepartments(View view) {
        Intent intent = new Intent(MainActivity.this, DepartmentList.class);

        startActivity(intent);
    }

    public void goToAboutCollege(View view) {
    Intent intent = new Intent(MainActivity.this,AboutCollege.class);
    startActivity(intent);
    }
}
package com.tutorialsbysajal.mcametbot.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import com.tutorialsbysajal.mcametbot.R;
import com.tutorialsbysajal.mcametbot.adapterclass.DepartmentAdapter;

public class DepartmentList extends AppCompatActivity {
    Toolbar toolbar;
    ListView department_list;
    Animation animation;
    String[] departments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);
        toolbar = findViewById(R.id.toolbar);
        department_list = findViewById(R.id.department_list);
        initilize();

    }

    private void initilize() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("Department List");
        toolbar.setTitleTextColor(Color.BLACK);
        Window window = DepartmentList.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(DepartmentList.this, R.color.white));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bottom));

        // Depart name fetch
        departments = getResources().getStringArray(R.array.departments);
        DepartmentAdapter departmentAdapter = new DepartmentAdapter(DepartmentList.this,departments);
        animation = AnimationUtils.loadAnimation(this,R.anim.animation1);
        department_list.setAdapter(departmentAdapter);
    }
}
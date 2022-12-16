package com.tutorialsbysajal.mcametbot.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tutorialsbysajal.mcametbot.R;
import com.tutorialsbysajal.mcametbot.modelClass.User;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    TextView register_now,tv_forgot_password;
    Button login_btn;
    TextInputEditText id_email, id_password;
    SharedPreferences sharedpreferences;
    String email, password;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";
    // key for storing email.
    public static final String EMAIL_KEY = "email_key";
    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        login_btn = findViewById(R.id.login_btn);
        register_now = findViewById(R.id.register_now);
        id_email = findViewById(R.id.id_email);
        id_password = findViewById(R.id.id_password);
        tv_forgot_password = findViewById(R.id.tv_forgot_password);
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        firebaseAuth = FirebaseAuth.getInstance();

        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);

                startActivity(intent);
            }
        });
        progressDialog = new ProgressDialog(this);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailId = id_email.getText().toString().trim();
                String passwordId = id_password.getText().toString().trim();
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(emailId, passwordId)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                progressDialog.cancel();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                // below two  line is to put value for email and password to shared preference
                                editor.putString(EMAIL_KEY, id_email.getText().toString());
                                editor.putString(PASSWORD_KEY, id_password.getText().toString());
                                // to save our data with key and value
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Toast.makeText(getApplicationContext(), "Login successfull", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String emailId = id_email.getText().toString();
                    progressDialog.setTitle("Sending mail...");
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(emailId)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.cancel();
                                    Toast.makeText(LoginActivity.this, "Password reset email sent, please cheack your inbox to reset password", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
            }
        });

    }

}
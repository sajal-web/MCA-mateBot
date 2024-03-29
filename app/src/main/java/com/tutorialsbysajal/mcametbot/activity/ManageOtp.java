package com.tutorialsbysajal.mcametbot.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tutorialsbysajal.mcametbot.R;

import java.util.concurrent.TimeUnit;

public class ManageOtp extends AppCompatActivity {
    Button verify;
    EditText otp;
    String phoneNumber;
    String otpid;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);
        verify = findViewById(R.id.verify);
        otp = findViewById(R.id.otp);
        mAuth = FirebaseAuth.getInstance();
        // get the phone number from previous activity
        phoneNumber = getIntent().getStringExtra("mobile").toString();
        initiateOtp();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (otp.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the otp...", Toast.LENGTH_SHORT).show();
                } else if (otp.getText().toString().length() != 6) {
                    Toast.makeText(getApplicationContext(), "Plz enter a valid otp...", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, otp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });


    }

    private void initiateOtp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                otpid = s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(ManageOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(ManageOtp.this, LoginActivity.class));
                            finish();

//                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(ManageOtp.this, "SignIn Error", Toast.LENGTH_SHORT).show();
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                            }
                        }
                    }
                });
    }

}
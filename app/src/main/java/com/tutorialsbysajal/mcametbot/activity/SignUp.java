package com.tutorialsbysajal.mcametbot.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;
import com.tutorialsbysajal.mcametbot.R;
import com.tutorialsbysajal.mcametbot.modelClass.User;

public class SignUp extends AppCompatActivity {
    Button gomanageOtp;
    CountryCodePicker cpp;
    EditText mobileNo, name, email, studentId, department, password;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        gomanageOtp = findViewById(R.id.gomanageOtp);
        mobileNo = findViewById(R.id.mobileNo);
        cpp = findViewById(R.id.ccp);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        studentId = findViewById(R.id.studentId);
        department = findViewById(R.id.department);
        password = findViewById(R.id.password);
        cpp.registerCarrierNumberEditText(mobileNo);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        // get the inputs
//        String passwordVal = password.getText().toString();


        progressDialog = new ProgressDialog(this);
        gomanageOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailVal = email.getText().toString().trim();
                String passwordVal = password.getText().toString();
                String nameVal = name.getText().toString();
                String studentIdVal = studentId.getText().toString();
                String departmentVal = department.getText().toString();
                String numberVal = cpp.getFullNumberWithPlus();

                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(emailVal, passwordVal)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Intent intent = new Intent(SignUp.this, ManageOtp.class);
                                intent.putExtra("mobile", cpp.getFullNumberWithPlus().replace(" ", ""));
                                Toast.makeText(SignUp.this, "Login to continue", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                firebaseFirestore.collection("UserDetails")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .set(new User(nameVal, emailVal, numberVal, studentIdVal, departmentVal, passwordVal));
                                startActivity(intent);
//
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

}
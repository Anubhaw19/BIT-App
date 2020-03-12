package com.hnccbits.bit_portal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StudentRegistrationActivity extends AppCompatActivity {
    private EditText name,branch,batch,email,password,confirmPassword;
    private Button signup;
    private FirebaseAuth auth; //declearing of object [auth] of [FirebaseAuth] class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        name=(EditText)findViewById(R.id.txt_username);
        branch=(EditText)findViewById(R.id.txt_branch);
        batch=(EditText)findViewById(R.id.txt_batch);
        email=(EditText)findViewById(R.id.txt_email);
        password=(EditText)findViewById(R.id.txt_password);
        confirmPassword=(EditText)findViewById(R.id.txt_confirm_password);
        signup=(Button)findViewById(R.id.btn_studentSignup);

        auth=FirebaseAuth.getInstance(); //creating an object [auth] of [FirebaseAuth] class.

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String USERNAME=name.getText().toString();
                String BRANCH=branch.getText().toString();
                String BATCH=batch.getText().toString();
                String EMAIL=email.getText().toString();
                String PASSWORD=password.getText().toString();
                String CONFIRM_PASSWORD=confirmPassword.getText().toString();

                if(TextUtils.isEmpty(USERNAME) || TextUtils.isEmpty(PASSWORD)
                        ||TextUtils.isEmpty(BRANCH)||TextUtils.isEmpty(BATCH)
                        ||TextUtils.isEmpty(EMAIL)||TextUtils.isEmpty(CONFIRM_PASSWORD))
                {
                    Toast.makeText(StudentRegistrationActivity.this,"invalid Credentials",Toast.LENGTH_SHORT).show();
                }
                else if (PASSWORD.length()<6)
                {
                    Toast.makeText(StudentRegistrationActivity.this,"password too short",Toast.LENGTH_SHORT).show();
                }
                else if (!TextUtils.equals(PASSWORD,CONFIRM_PASSWORD))
                {
                    Toast.makeText(StudentRegistrationActivity.this,"confirm password should be same as password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // storing of data in real time  @Firebase
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("1.Name",USERNAME);
                    map.put("2.Branch",BRANCH);
                    map.put("3.Batch",BATCH);
                    map.put("4.Email",EMAIL);
                    map.put("5.Password",PASSWORD);
                    //FirebaseDatabase.getInstance().getReference().child("BIT Sindri").child("STUDENT DATA").updateChildren(map);

                    FirebaseDatabase.getInstance().getReference("User Database").child("STUDENT DATA").updateChildren(map);
                    /**
                    *Data not saved to Database
                    *Major Problem
                    *Solve
                    */

                    //calling the method to create user with given Email and password
                    RegisterUser(EMAIL,PASSWORD);

                }

            }
        });

    }

    private void RegisterUser(String email, String password) {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(StudentRegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(StudentRegistrationActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StudentRegistrationActivity.this, MainActivity.class));
                    finish();
                }
                else
                {

                    Toast.makeText(StudentRegistrationActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

package com.example.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText lemail,lpass;
    Button login;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textview = findViewById(R.id.t1);
        String text="Not a Member?Sign Up Now!";
        SpannableString s = new SpannableString(text);

        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

                Intent i = new Intent(login.this,signup.class);
                startActivity(i);
            }
        };

        s.setSpan(clickableSpan,13,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview.setText(s);
        textview.setMovementMethod(LinkMovementMethod.getInstance());

        lemail = findViewById(R.id.email1);
        lpass = findViewById(R.id.password1);
        login=findViewById(R.id.login);
        fAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lemail.getText().toString().trim();
                String password = lpass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(login.this, "Email is required", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(login.this, "Password is required", Toast.LENGTH_SHORT).show();
                }


                //authenciate the user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                        }
                        else{
                            Toast.makeText(login.this, "Email or Password is incorrect"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}


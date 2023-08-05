package com.example.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText sname, semail, spass, scon,sroll;
    Button register;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sname = findViewById(R.id.name);
        semail = findViewById(R.id.email);
        spass = findViewById(R.id.pass);
        scon = findViewById(R.id.confirm_pass);
        sroll= findViewById(R.id.roll);
        register = findViewById(R.id.login1);

        fAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = sname.getText().toString().trim();
                String email = semail.getText().toString().trim();
                String password = spass.getText().toString().trim();
                String con = scon.getText().toString().trim();
                String roll =sroll.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(signup.this, "Name is required", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(signup.this, "Email is required", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(roll)){
                    Toast.makeText(signup.this, "Roll No is Required", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(signup.this, "Password is required", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(con)) {
                    Toast.makeText(signup.this, "Please Enter to confirm Password", Toast.LENGTH_SHORT).show();

                }

                if (password.equals(con)) {

                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(signup.this, "User Created", Toast.LENGTH_SHORT).show();
                                userID=fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference=fstore.collection("users").document(userID);
                                Map<String,Object> user=new HashMap<>();
                                user.put("name",name);
                                user.put("email",email);
                                user.put("rollno",roll);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"OnSuccess: User Profile is created for "+userID);
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), login.class));
                            } else {
                                Toast.makeText(signup.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                if(!password.equals(con)){
                    Toast.makeText(signup.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


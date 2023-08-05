package com.example.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    EditText profileFullName, profileEmail,profileRoll;
    Button saveButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUser user;
    String userId;

    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        String fullName = data.getStringExtra("fullName");
        String email = data.getStringExtra("email");
        String roll=data.getStringExtra("rollno");

        profileFullName = findViewById(R.id.profileFullName);
        profileEmail = findViewById(R.id.profileEmailAddress);
        profileRoll=findViewById(R.id.profileRoll);
        saveButton = findViewById(R.id.save);


        profileEmail.setText(email);
        profileFullName.setText(fullName);
        profileRoll.setText(roll);

        Log.d(TAG, "onCreate: " + fullName + " " + email+" "+roll);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        userId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                profileFullName.setText(documentSnapshot.getString("name"));
                profileEmail.setText(documentSnapshot.getString("email"));
                profileRoll.setText(documentSnapshot.getString("rollno"));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileFullName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty()||profileRoll.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfile.this, "One or Many Fields are Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = profileEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef=fstore.collection("users").document(user.getUid());
                        Map<String,Object> edited= new HashMap<>();
                        edited.put("email",email);
                        edited.put("name",profileFullName.getText().toString());
                        edited.put("rollno",profileRoll.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}


package com.example.student;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class view_profile extends AppCompatActivity {
    TextView fullName,email,roll;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);


        fullName=findViewById(R.id.profileName);
        email=findViewById(R.id.profileEmail);
        roll=findViewById(R.id.profileroll);

        fAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        userId=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference=fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                fullName.setText(documentSnapshot.getString("name"));
                email.setText(documentSnapshot.getString("email"));
                roll.setText(documentSnapshot.getString("rollno"));

            }
        });
    }
}

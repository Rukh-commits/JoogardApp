package com.example.joogard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class Hack1Activity extends AppCompatActivity {
    private static final String TAG="HACK1 ACTIVITY";
    private static final String KEY_DESCRIPTION="Description";
    private EditText editTextDescription;
    private TextView textViewData;
    private ImageView forward, back,sendback;
    private int number=1;
    int doc_count=1;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final DocumentReference noteRef=db.collection("Daily Life").document("Hack "+doc_count);
    CollectionReference collectionReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack1);
        textViewData=findViewById(R.id.text_view_data);
        back = findViewById(R.id.backbtn);
        forward = findViewById(R.id.forwardbtn);
        sendback = findViewById(R.id.sendbackbtn);
        loadNote();
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doc_count++;
                if(doc_count>6)
                    Toast.makeText(Hack1Activity.this, "No further hacks to display", Toast.LENGTH_SHORT).show();
                else
                    loadNote();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doc_count--;
                if(doc_count==0){
                    Toast.makeText(Hack1Activity.this, "Use next button to see more hacks", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadNote(); }
            }
        });

        sendback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hack1Activity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadNote(){
        db.collection("Daily Life").document("Hack "+doc_count).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String description = documentSnapshot.getString(KEY_DESCRIPTION);
                            //Map<String,Object> note=documentSnapshot.getData();
                            textViewData.setText(description);
                        } else {
                            Toast.makeText(Hack1Activity.this, "Document doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e){
                        Toast.makeText(Hack1Activity.this,"Error while Saving",Toast.LENGTH_SHORT).show();
                        Log.d(TAG,e.toString());
                    }
                });
    }

    public void goHome(View view) {
        Intent intent = new Intent(Hack1Activity.this, HomeActivity.class);
        startActivity(intent);
    }


}

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Hack2Activity extends AppCompatActivity {

    private static final String TAG="HACK2 ACTIVITY";
    private static final String KEY_DESCRIPTION="Description";
    private EditText editTextDescription;
    private TextView textViewData;
    private ImageView forward, back,sendback;
    int doc_count=1;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final DocumentReference noteRef=db.collection("Food and Drinks").document("Hack "+doc_count);
    CollectionReference collectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack2);
        editTextDescription=findViewById(R.id.edit_text_description);
        textViewData=findViewById(R.id.text_view_data);
        loadNote();
        forward = findViewById(R.id.forwardbtn);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doc_count++;
                if(doc_count>3)
                    Toast.makeText(Hack2Activity.this, "No further hacks to display", Toast.LENGTH_SHORT).show();
                else
                    loadNote();
            }
        });

        back = findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doc_count--;
                if(doc_count==0){
                    Toast.makeText(Hack2Activity.this, "Use next button to see more hacks", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadNote(); }
            }
        });

        sendback = findViewById(R.id.sendbackbtn);
        sendback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hack2Activity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadNote(){
        db.collection("Food and Drinks").document("Hack "+doc_count).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String description = documentSnapshot.getString(KEY_DESCRIPTION);
                            //Map<String,Object> note=documentSnapshot.getData();
                            textViewData.setText(description);
                        } else {
                            Toast.makeText(Hack2Activity.this, "Hack doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e){
                        Toast.makeText(Hack2Activity.this,"Error while Loading",Toast.LENGTH_SHORT).show();
                        Log.d(TAG,e.toString());
                    }
                });
    }

    public void goHome(View view) {
        Intent intent = new Intent(Hack2Activity.this, HomeActivity.class);
        startActivity(intent);
    }



}
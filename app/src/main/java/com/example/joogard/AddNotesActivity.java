package com.example.joogard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNotesActivity extends AppCompatActivity {
    private static final String TAG="ADDNOTES ACTIVITY";
    private static final String KEY_TITLE="title";
    private static final String KEY_DESCRIPTION="description";
    private EditText editTextTitle;
    private EditText editTextDescription;
    private ImageView back;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef=db.collection("Notes").document("Personal Notes");
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        editTextTitle=findViewById(R.id.edit_text_title);
        editTextDescription=findViewById(R.id.edit_text_description);
        back = findViewById(R.id.backbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNotesActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });

    }


    public void saveNote(View v){
        String title=editTextTitle.getText().toString();
        String description=editTextDescription.getText().toString();

        Map<String,Object> note=new HashMap<>();
        note.put(KEY_TITLE,title);
        note.put(KEY_DESCRIPTION,description);

        db.collection("Notes")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Note written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}

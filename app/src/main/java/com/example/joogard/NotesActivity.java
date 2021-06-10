package com.example.joogard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.joogard.R.layout.activity_notes;

public class NotesActivity extends AppCompatActivity {
    private static final String TAG="Firelog";
    private RecyclerView mMainList;
    private FirebaseFirestore mFirestore;
    private NotesListAdapter notesListAdapter;
    private ImageView add_notesbtn,back;
    private List<Notes> noteslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_notes);
        add_notesbtn=findViewById(R.id.addnotesbtn);
        back=findViewById(R.id.backbtn);
        mMainList=findViewById(R.id.main_list);
        noteslist=new ArrayList<>();
        notesListAdapter=new NotesListAdapter(noteslist);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(notesListAdapter);



        mFirestore=FirebaseFirestore.getInstance();


        mFirestore.collection("Notes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d(TAG,"Error :" +e.getMessage());
                }

                for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                    if(doc.getType()== DocumentChange.Type.ADDED){
                        Notes notes=doc.getDocument().toObject(Notes.class);
                        noteslist.add(notes);
                        notesListAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
        add_notesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToMain = new Intent(NotesActivity.this,AddNotesActivity.class);
                startActivity(intToMain);
            }

        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToMain = new Intent(NotesActivity.this,HomeActivity.class);
                startActivity(intToMain);
            }
        });

    }
}

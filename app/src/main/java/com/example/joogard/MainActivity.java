package com.example.joogard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public EditText email,password;
    public Button SignUpButton;
    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+*_=*])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    public TextView signInText;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth= FirebaseAuth.getInstance();
        email = findViewById(R.id.emailId);
        password = findViewById(R.id.passwordText);
        signInText=findViewById(R.id.signInText);
        SignUpButton = findViewById(R.id.SignUpButton);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email1 = email.getText().toString();
                String password1 = password.getText().toString();
                if(email1.isEmpty()){
                    email.setError("Pleaser enter Email");
                    email.requestFocus();
                }
                else if(password1.isEmpty()){
                    password.setError("Pleaser enter Password");
                    password.requestFocus();
                }
                else if(password1.isEmpty() &&  email1.isEmpty())
                    Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_SHORT);

                else if(!(password1.isEmpty() &&  email1.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (email1.toString().trim().matches(emailPattern) && PASSWORD_PATTERN.matcher(password1).matches()) {
                                if (!task.isSuccessful()) {
                                    mFirebaseAuth.fetchSignInMethodsForEmail(email1)
                                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                                    boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                                                    if (!isNewUser) {
                                                        Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_SHORT).show();

                                                    }
                                                    Toast.makeText(MainActivity.this, "Sign Up UnSuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                } else if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                }
                            }
                            else if(!email1.toString().trim().matches(emailPattern)) {
                                Toast.makeText(MainActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                            }
                            else if(!PASSWORD_PATTERN.matcher(password1).matches()){
                                Toast.makeText(MainActivity.this, "Password too weak", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}

package com.example.loginpage;

import static android.content.ContentValues.TAG;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {
    TextView editTextEmail, editTextPassword, textView;
    Button buttonReg;
    ProgressBar progressbar;
    FirebaseAuth mAuth;
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         //initializing the objects
        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.pswd);
        buttonReg=findViewById(R.id.registerbtn);
        progressbar=findViewById(R.id.progressbar);
        mAuth=FirebaseAuth.getInstance();
        textView=findViewById(R.id.loginnow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }

        });


        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(VISIBLE );
                String email,pswd;
                email=String.valueOf(editTextEmail.getText());
                pswd=String.valueOf(editTextPassword.getText());


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(register.this,"Enter Email ",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pswd)){
                    Toast.makeText(register.this,"Enter pswd ",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, pswd)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                               progressbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(register.this, "Account created.",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),Login.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(register.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                    }

            });
        }
}


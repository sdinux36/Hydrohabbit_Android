package com.example.loginpage;

import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextView editTextEmail, editTextPassword, textView;
    Button buttonlogIn;
    ProgressBar progressbar;
    FirebaseAuth mAuth;

    @Override
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
        setContentView(R.layout.activity_login);
        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.pswd);
        buttonlogIn=findViewById(R.id.loginbtn);
        progressbar=findViewById(R.id.progressbar);
        mAuth=FirebaseAuth.getInstance();
        textView=findViewById(R.id.registernow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),register.class);
                startActivity(intent);
                finish();
            }

        });
        buttonlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(VISIBLE );
                String email,pswd;
                email=String.valueOf(editTextEmail.getText());
                pswd=String.valueOf(editTextPassword.getText());


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this,"Enter Email ",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pswd)){
                    Toast.makeText(Login.this,"Enter pswd ",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, pswd)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                   Toast.makeText(getApplicationContext(),"Log in success",Toast.LENGTH_SHORT).show();
                                   Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                   startActivity(intent);
                                   finish();


                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Login.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}
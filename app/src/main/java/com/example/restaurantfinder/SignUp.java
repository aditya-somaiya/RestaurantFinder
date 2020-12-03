package com.example.restaurantfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    TextInputLayout regEmail, regPhone, regPassword;
    Button registerButton, regToLoginButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
//        if (mAuth.getCurrentUser() != null) {
//            Intent i = new Intent(SignUp.this, HomeActivity.class);
//        }

        regEmail=findViewById(R.id.email);
        regPhone=findViewById(R.id.phone);
        regPassword=findViewById(R.id.password);

        //Save data in FireBase on button click
        registerButton = findViewById(R.id.signup);
        regToLoginButton = findViewById(R.id.login);
        regToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get all values in string
                String email = regEmail.getEditText().getText().toString().trim();
                String phone = regPhone.getEditText().getText().toString().trim();
                String password = regPassword.getEditText().getText().toString().trim();

                if (!validateEmail() | !validatePassword() | !validatePhone()){
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent toHome = new Intent(SignUp.this, ProfileActivity.class);
                                    startActivity(toHome);
                                }
                                else {
                                    // If sign up fails, display a message to the user.
                                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                    Toast.makeText(SignUp.this, "Authentication failed. "+ e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
    public Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        if (val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }
        else if (TextUtils.isEmpty(val)){
            regEmail.setError("Invalid email address");
            return false;
        }
        else{
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    public Boolean validatePhone(){
        String val = regPhone.getEditText().getText().toString();
        if (val.isEmpty()){
            regPhone.setError("Field cannot be empty");
            return false;
        }
        else{
            regPhone.setError(null);
            regPhone.setErrorEnabled(false);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        if (val.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        }
        else if (val.length()<6){
            regPassword.setError("Password is too weak");
            return false;
        }
        else{
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }
}
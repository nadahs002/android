package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLogin extends AppCompatActivity {

    private Button login;
    private Button register;
    private TextView registerLink, customerStatus;
    private EditText passwordDriver, emailDriver;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        login = findViewById(R.id.login);
        register = findViewById(R.id.signup);
        customerStatus = findViewById(R.id.textViewDriverLogin);
        registerLink = findViewById(R.id.registerLink);
        passwordDriver = findViewById(R.id.editTextPassword);
        emailDriver = findViewById(R.id.editTextMail);

        register.setVisibility(View.INVISIBLE);
        register.setEnabled(false);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerLink.setVisibility(View.INVISIBLE);
                login.setVisibility(View.INVISIBLE);

                customerStatus.setText("Register Driver");

                register.setVisibility(View.VISIBLE);
                register.setEnabled(true);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailDriver.getText().toString().trim();
                String password = passwordDriver.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(DriverLogin.this, "Email is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(DriverLogin.this, "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                SignInDriver(email, password);

            }

            private void SignInDriver(String email, String password) {

                loadingBar.setTitle("Driver Login");
                loadingBar.setMessage("Please wait while we check your credentials");
                loadingBar.show();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loadingBar.dismiss();

                                if (task.isSuccessful()) {
                                    Toast.makeText(DriverLogin.this, "logged in", Toast.LENGTH_SHORT).show();
                                    // You can add additional logic here, for example, navigate to another activity
                                    Intent map= new Intent(DriverLogin.this, driverMap.class);
                                    startActivity(map);
                                } else {

                                    Toast.makeText(DriverLogin.this, "check your email and password", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailDriver.getText().toString().trim();
                String password = passwordDriver.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(DriverLogin.this, "Email is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(DriverLogin.this, "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerDriver(email, password);
            }
        });
    }

    private void registerDriver(String email, String password) {
        loadingBar.setTitle("Driver Registration");
        loadingBar.setMessage("Please wait while we register your data");
        loadingBar.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loadingBar.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(DriverLogin.this, "Driver successfully registered", Toast.LENGTH_SHORT).show();
                            // You can add additional logic here, for example, navigate to another activity
                        } else {
                            Log.e("DriverLogin", "Registration failed: " + task.getException());
                            Toast.makeText(DriverLogin.this, "Registration unsuccessful, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

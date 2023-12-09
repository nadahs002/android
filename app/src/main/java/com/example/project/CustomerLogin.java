package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class CustomerLogin extends AppCompatActivity {

    private Button login;
    private Button register ;
    private TextView registerLink , customerStatus;
    private EditText passwordCustomer, emailCustomer;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        login= (Button) findViewById(R.id.login);
        register= (Button) findViewById(R.id.signup);
        customerStatus= findViewById(R.id.textViewCustomerLogin);
        registerLink= findViewById(R.id.registerLink);

        register.setVisibility(View.INVISIBLE);
        register.setEnabled(false);
        passwordCustomer = findViewById(R.id.editTextPassword);
        emailCustomer = findViewById(R.id.editTextMail);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerLink.setVisibility(View.INVISIBLE);
                login.setVisibility(View.INVISIBLE);

                customerStatus.setText("Register Customer");

                register.setVisibility(View.VISIBLE);
                register.setEnabled(true);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailCustomer.getText().toString().trim();
                String password = passwordCustomer.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(CustomerLogin.this, "Email is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(CustomerLogin.this, "Password is required", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(CustomerLogin.this, "logged in", Toast.LENGTH_SHORT).show();
                                    // You can add additional logic here, for example, navigate to another activity
                                } else {

                                    Toast.makeText(CustomerLogin.this, "check your email and password", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailCustomer.getText().toString().trim();
                String password = passwordCustomer.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(CustomerLogin.this, "Email is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(CustomerLogin.this, "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerCustomer(email, password);
            }
        });

    }

    private void registerCustomer(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(CustomerLogin.this, "Email and password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingBar.setTitle("Customer Registration");
        loadingBar.setMessage("Please wait while we register your data");
        loadingBar.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loadingBar.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(CustomerLogin.this, "Customer successfully registered", Toast.LENGTH_SHORT).show();
                            // You can add additional logic here, for example, navigate to another activity
                        } else {
                            Log.e("CustomerLogin", "Registration failed: " + task.getException());
                            Toast.makeText(CustomerLogin.this, "Registration unsuccessful, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class driverOrCustomer extends AppCompatActivity {
    private Button driver;
    private Button customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_or_customer);

        driver=(Button) findViewById(R.id.driver);
        customer=(Button) findViewById(R.id.customer);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginCustomer= new Intent(driverOrCustomer.this, CustomerLogin.class);
                startActivity(loginCustomer);
            }
        });


        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginDriver= new Intent(driverOrCustomer.this, DriverLogin.class);
                startActivity(loginDriver);
            }
        });
    }
}
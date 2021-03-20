package com.example.montej.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.montej.R;

public class Sign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        final Button customer_sign = (Button) findViewById(R.id.customer_sign_in_button);
        customer_sign .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                Intent i = new Intent(Sign.this, Customer_Sign.class);
                startActivity(i);
            }
        });



        final Button seller_sign  = (Button) findViewById(R.id.montej_sign_in_button);
        seller_sign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                Intent i = new Intent(Sign.this, Family_Sign.class);
                startActivity(i);
            }
        });

        final TextView skip  = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                Intent i = new Intent(Sign.this, Customer_Home.class);
                startActivity(i);
            }
        });

    }
}

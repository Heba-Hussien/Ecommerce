package com.example.montej.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;

public class AddPrice extends AppCompatActivity {
String Seller_id,Customer_id,Order_id,Store_type,Store_name,product_name;
EditText price;
String offer_price;
Button Save;
    FireBaseClientLocationHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price);
       Seller_id=getIntent().getStringExtra("Seller_id");
       Customer_id=getIntent().getStringExtra("Customer_id");
        Order_id=getIntent().getStringExtra("Order_id");
        Store_type=getIntent().getStringExtra("Store_type");
        Store_name=getIntent().getStringExtra("Store_name");
        product_name=getIntent().getStringExtra("product_name");
        //Toast.makeText(AddPrice.this, Store_type, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, Store_name, Toast.LENGTH_SHORT).show();


        price=findViewById(R.id.add_price);
       offer_price=price.getText().toString();
       Save=findViewById(R.id.add_priceBtn);


     Save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //  Toast.makeText(AddPrice.this, price.getText().toString(), Toast.LENGTH_SHORT).show();

                if(!price.getText().toString().equals("")){
                helper=new FireBaseClientLocationHelper(AddPrice.this);
                helper.addOffer(Seller_id,Customer_id,price.getText().toString(),Order_id,product_name,Store_name);
                Intent intent=new Intent(AddPrice.this,Family_OrderList.class);
                startActivity(intent);}
                else{
                    Toast.makeText(AddPrice.this, "Offer price is needed", Toast.LENGTH_SHORT).show();}

            }
        });
    }


}

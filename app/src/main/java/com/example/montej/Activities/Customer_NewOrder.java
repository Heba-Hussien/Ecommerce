package com.example.montej.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.SessionManager;

public class Customer_NewOrder extends AppCompatActivity {
    Spinner stores_spinner;
    EditText customer_name,product_name,quantity;
    String store_type,User_id;
    Toolbar mToolbar;
    boolean invalidStore;
    SessionManager sessionManager;
    FireBaseClientLocationHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_new_order);

        product_name=(EditText)findViewById(R.id.new_order_product);
        quantity=(EditText)findViewById(R.id.new_order_quantity);
        mToolbar = (Toolbar) findViewById(R.id.add_order_toolbar);
        sessionManager=new SessionManager(this);
        User_id=sessionManager.getUserId();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        invalidStore=true;

        stores_spinner = (Spinner) findViewById(R.id.new_order_spin);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Stores));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stores_spinner.setAdapter(adapter1);
        stores_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                store_type = stores_spinner.getSelectedItem().toString();
                stores_spinner.getItemIdAtPosition((i));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                invalidStore=false;
            }
        });


        final Button new_order = (Button) findViewById(R.id.new_orderBtn);
        new_order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (validate() == false&&invalidStore==true) {}
                else {
                    helper = new FireBaseClientLocationHelper(Customer_NewOrder.this);
                    helper.addOrder(
                            sessionManager.getFullName(),
                            product_name.getText().toString(),
                            quantity.getText().toString(),
                            "",
                            User_id,
                            "",
                            store_type,
                            "new",
                            ""
                    );

                }}
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(Customer_NewOrder.this, Customer_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean validate() {
        boolean valid = true;

        String product_nameTxt = product_name.getText().toString();
        String quantityTxt = quantity.getText().toString();




        if (product_nameTxt.isEmpty() ) {
            product_name.setError("Enter customer name");
            valid = false;
        } else {
            product_name.setError(null);
        }

        if (quantityTxt.isEmpty()) {
            quantity.setError("Enter password");
            valid = false;
        } else {
            quantity.setError(null);
        }


        return valid;
    }

}

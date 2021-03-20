package com.example.montej.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.montej.Adapters.Customer_OrdersAdapter;
import com.example.montej.Adapters.Order_customAdapter;
import com.example.montej.Adapters.product_CustomAdapter;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.SessionManager;
import com.example.montej.model.Order;
import com.example.montej.model.product;

import java.util.ArrayList;

public class Customer_AllOrders extends AppCompatActivity {
    ListView listView;
    Toolbar mToolbar;
    String User_id;
    FireBaseClientLocationHelper helper;
    private Customer_OrdersAdapter adapter;
    SessionManager sessionManager;
    private TextView emptyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_all_orders);
        listView = findViewById(R.id.FP_list);
        emptyText = findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);
        mToolbar = (Toolbar) findViewById(R.id.family_home_toolbar);
        sessionManager=new SessionManager(this);


        User_id = sessionManager.getUserId();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        helper = new FireBaseClientLocationHelper(Customer_AllOrders.this);
        helper.DisplayOrders(User_id,"customer_id", new FireBaseClientLocationHelper.DisplayOrdersListiner() {
            @Override
            public void onDataFound(ArrayList<Order>products) {
                adapter= new Customer_OrdersAdapter(products,User_id,Customer_AllOrders.this);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

            }
        });


        final Button add_order = (Button) findViewById(R.id.add_order_button);
        add_order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Customer_AllOrders.this, Customer_NewOrder.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(Customer_AllOrders.this, Customer_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

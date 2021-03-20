package com.example.montej.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


import com.example.montej.Adapters.Order_customAdapter;
import com.example.montej.Adapters.product_CustomAdapter;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.SessionManager;
import com.example.montej.model.Order;
import com.example.montej.model.product;

import java.util.ArrayList;

public class Family_OrderList extends AppCompatActivity {
    Toolbar mToolbar;
    ListView listView;
    String Store_type,User_id,Store_name;
    FireBaseClientLocationHelper helper;
    private static Order_customAdapter adapter;
    TextView emptyText;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_order_list);
        listView = (ListView) findViewById(R.id.FP_list);
        emptyText = findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);
        sessionManager=new SessionManager(this);

        Store_type=sessionManager.getStore_type();
        User_id=sessionManager.getUserId();
        Store_name=sessionManager.getStore_name();
      //  Toast.makeText(this, Store_name, Toast.LENGTH_SHORT).show();
        mToolbar = (Toolbar) findViewById(R.id.order_list_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
      //  Toast.makeText(Family_OrderList.this, Store_type, Toast.LENGTH_SHORT).show();


        helper = new FireBaseClientLocationHelper(Family_OrderList.this);
        helper.DisplayOrders(Store_type,"Store_type", new FireBaseClientLocationHelper.DisplayOrdersListiner() {
            @Override
            public void onDataFound(ArrayList<Order>products) {
                adapter= new Order_customAdapter(products,Store_name,User_id,Family_OrderList.this);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            sessionManager.logout();
            Intent i = new Intent(this,Login.class);
            startActivity(i);
            finish();
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(Family_OrderList.this, Family_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

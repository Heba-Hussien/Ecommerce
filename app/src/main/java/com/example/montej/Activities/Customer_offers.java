package com.example.montej.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.montej.Adapters.Offer_customAdapter;
import com.example.montej.Adapters.Order_customAdapter;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.SessionManager;
import com.example.montej.model.Offer;
import com.example.montej.model.Order;

import java.util.ArrayList;

public class Customer_offers extends AppCompatActivity {

    Toolbar mToolbar;
    ListView listView;
    String Store_type,User_id;
    FireBaseClientLocationHelper helper;
    private static Offer_customAdapter adapter;
    SessionManager sessionManager;
    TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_offers);
        listView = (ListView) findViewById(R.id.offer_list);
        emptyText = findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);
        sessionManager=new SessionManager(this);
        User_id=sessionManager.getUserId();
        mToolbar = (Toolbar) findViewById(R.id.offer_list_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        helper = new FireBaseClientLocationHelper(Customer_offers.this);
        helper.GetUserOffers(User_id, new FireBaseClientLocationHelper.DisplayOffersListiner() {
            @Override
            public void onDataFound(ArrayList<Offer> products) {
                adapter= new Offer_customAdapter(products,User_id,Customer_offers.this);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(Customer_offers.this, Customer_Home.class);
                intent.putExtra("Store_type",Store_type);
                intent.putExtra("User_id",User_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

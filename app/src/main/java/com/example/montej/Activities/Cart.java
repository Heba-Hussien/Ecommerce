package com.example.montej.Activities;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.montej.Adapters.CartAdapter;
import com.example.montej.Adapters.Customer_OrdersAdapter;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.SessionManager;
import com.example.montej.model.Order;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    Toolbar mToolbar;
    ArrayList<Order> dataModels;
    ListView listView;
    private static CartAdapter adapter;
    FireBaseClientLocationHelper helper;
    TextView emptyText;
    String User_id;
    int amoun=0;
    Button btn;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        helper=new FireBaseClientLocationHelper(this);
        mToolbar = (Toolbar) findViewById(R.id.cart_toolbar);
        sessionManager=new SessionManager(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        User_id=sessionManager.getUserId();
        listView = (ListView) findViewById(R.id.cart_product_list);
        emptyText = findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);
        //dataModels = new ArrayList<>();
        btn=findViewById(R.id.check);
        helper = new FireBaseClientLocationHelper(Cart.this);
        helper.DisplayCart(User_id,"customer_id", new FireBaseClientLocationHelper.DisplayCartListiner() {
            @Override
            public void onDataFound(ArrayList<Order>products) {
                adapter= new CartAdapter(products,User_id,Cart.this);
                listView.setAdapter(adapter);
                for (int i=0;i<products.size();i++){
                  amoun=amoun+Integer.parseInt(products.get(i).getPrice());
                }
                btn.setText("Check out:  "+amoun);

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

                Intent intent = new Intent(Cart.this,Customer_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

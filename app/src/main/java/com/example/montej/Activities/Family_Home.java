package com.example.montej.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.montej.Adapters.product_CustomAdapter;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.SessionManager;
import com.example.montej.model.*;

import java.util.ArrayList;

public class Family_Home extends AppCompatActivity {
    ListView listView;
    Toolbar mToolbar;
    String Store_type, User_id,store_name;
    FireBaseClientLocationHelper helper;
    private product_CustomAdapter adapter;
    SessionManager sessionManager;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_home);
        listView = findViewById(R.id.FP_list);
        emptyText = findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);
        mToolbar = (Toolbar) findViewById(R.id.family_home_toolbar);
        sessionManager=new SessionManager(this);
        Store_type = sessionManager.getStore_type();
        User_id = sessionManager.getUserId();
        store_name=sessionManager.getStore_name();
        setSupportActionBar(mToolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        helper = new FireBaseClientLocationHelper(Family_Home.this);
        helper.GetSellerProducts(User_id, new FireBaseClientLocationHelper.GetSellerProductsListener() {
            @Override
            public void onDataFound(ArrayList<product> products) {
                adapter = new product_CustomAdapter(products, User_id, Family_Home.this);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

            }
        });


        final Button add_product = (Button) findViewById(R.id.add_product_button);
        add_product.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Family_Home.this, Family_AddProduct.class);
//                i.putExtra("User_id", User_id);
//                i.putExtra("Store_type", Store_type);
//                i.putExtra("Store_name", store_name);

                startActivity(i);
            }
        });
        final Button order_list = (Button) findViewById(R.id.order_list_button);
        order_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Family_Home.this, Family_OrderList.class);
//                i.putExtra("User_id", User_id);
//                i.putExtra("Store_type", Store_type);
//                i.putExtra("Store_name", store_name);

                startActivity(i);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            sessionManager.logout();
            Intent i = new Intent(this,Login.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

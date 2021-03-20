package com.example.montej.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.montej.Adapters.Customer_homeAdapter;
import com.example.montej.Adapters.product_CustomAdapter;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.SessionManager;
import com.example.montej.model.product;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Customer_Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ListView listView;
    String User_id;
    FireBaseClientLocationHelper helper;
    private static Customer_homeAdapter adapter;
    SessionManager sessionManager;
    TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.Customer_home_list);
        emptyText = findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);
        User_id=getIntent().getStringExtra("User_id");
        helper = new FireBaseClientLocationHelper(Customer_Home.this);
        sessionManager=new SessionManager(this);
        helper.GetAllProducts( new FireBaseClientLocationHelper.GetSellerProductsListener() {
            @Override
            public void onDataFound(ArrayList<product>products) {
                adapter= new Customer_homeAdapter(products,Customer_Home.this);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        CircleImageView img = navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        Picasso.get()
                .load(R.drawable.logo)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(img);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Cooking) {

            helper = new FireBaseClientLocationHelper(Customer_Home.this);
            helper.GetStoreProducts("Cooking", new FireBaseClientLocationHelper.GetSellerProductsListener() {
                @Override
                public void onDataFound(ArrayList<product>products) {
                    adapter= new Customer_homeAdapter(products,Customer_Home.this);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onFailure(String message) {

                }
            });

        } else if (id == R.id.Sweets) {
            helper = new FireBaseClientLocationHelper(Customer_Home.this);
            helper.GetStoreProducts("\u200ESweets", new FireBaseClientLocationHelper.GetSellerProductsListener() {
                @Override
                public void onDataFound(ArrayList<product>products) {
                    adapter= new Customer_homeAdapter(products,Customer_Home.this);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onFailure(String message) {

                }
            });

        } else if (id == R.id.Hand_made) {
            helper = new FireBaseClientLocationHelper(Customer_Home.this);
            helper.GetStoreProducts("Hand made", new FireBaseClientLocationHelper.GetSellerProductsListener() {
                @Override
                public void onDataFound(ArrayList<product>products) {
                    adapter= new Customer_homeAdapter(products,Customer_Home.this);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onFailure(String message) {

                }
            });
        } else if (id == R.id.Wooden_works) {
            helper = new FireBaseClientLocationHelper(Customer_Home.this);
            helper.GetStoreProducts("wooden works", new FireBaseClientLocationHelper.GetSellerProductsListener() {
                @Override
                public void onDataFound(ArrayList<product>products) {
                    adapter= new Customer_homeAdapter(products,Customer_Home.this);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onFailure(String message) {

                }
            });

        } else if (id == R.id.Decoration) {
            helper = new FireBaseClientLocationHelper(Customer_Home.this);
            helper.GetStoreProducts("Decoration", new FireBaseClientLocationHelper.GetSellerProductsListener() {
                @Override
                public void onDataFound(ArrayList<product>products) {
                    adapter= new Customer_homeAdapter(products,Customer_Home.this);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onFailure(String message) {

                }
            });

        } else if (id == R.id.party_preparation) {
            helper = new FireBaseClientLocationHelper(Customer_Home.this);
            helper.GetStoreProducts("party preparation", new FireBaseClientLocationHelper.GetSellerProductsListener() {
                @Override
                public void onDataFound(ArrayList<product>products) {
                    adapter= new Customer_homeAdapter(products,Customer_Home.this);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onFailure(String message) {

                }
            });

        }else if (id == R.id.New_order) {
            if(sessionManager.isLoggedIn()){
            Intent intent=new Intent(Customer_Home.this,Customer_AllOrders.class);
            startActivity(intent);}else{
                Toast.makeText(this, "create account to enable this", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(Customer_Home.this,Customer_Sign.class);
                startActivity(intent);
            }
        }else if (id == R.id.Offers) {
            if(sessionManager.isLoggedIn()){
            Intent intent=new Intent(Customer_Home.this,Customer_offers.class);
            intent.putExtra("User_id", User_id);
          //  Toast.makeText(this, User_id, Toast.LENGTH_SHORT).show();
            startActivity(intent);} else{
            Toast.makeText(this, "create account to enable this", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Customer_Home.this,Customer_Sign.class);
            startActivity(intent);
        }

        }
        else if (id == R.id.Logout) {
            sessionManager.logout();
            sessionManager.ClearAllData();
            Intent intent=new Intent(Customer_Home.this,Login.class);
            //  Toast.makeText(this, User_id, Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings&&sessionManager.isLoggedIn()) {
            Intent i = new Intent(Customer_Home.this,Cart.class);
            startActivity(i);
            return true;
        }else{
            Toast.makeText(this, "create account to enable this", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Customer_Home.this,Customer_Sign.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.montej.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.montej.Adapters.CommentsAdapter;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.SessionManager;
import com.example.montej.model.Comment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Customer_ProductDetails extends AppCompatActivity {
    String item_id,item_name,item_description,item_price,item_code,item_image ,seller_id,store_type,Store_name,User_id,customer,seller_phone;
    ImageView imageView;
    TextView product_name, Store_namee , price, description,phone;
    FireBaseClientLocationHelper helper;
    Toolbar mToolbar;
    int num=1;
    Button btn;
    int total_price,total_pricee;
    SessionManager sessionManager;
    ListView listView;
    private static CommentsAdapter adapter;
    private Button add_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_product_details);

        mToolbar = (Toolbar) findViewById(R.id.cu_product_details_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sessionManager=new SessionManager(this);
        helper=new FireBaseClientLocationHelper(this);
        listView=findViewById(R.id.Comments_list);
        User_id=sessionManager.getUserId();
        customer=sessionManager.getFullName();
        phone=findViewById(R.id.seller_phone);
        product_name = (TextView) findViewById(R.id.product_detaile_name);
        Store_namee = (TextView) findViewById(R.id.product_details_store_name);
        price = (TextView) findViewById(R.id.product_detaile_price);
        description = (TextView) findViewById(R.id.product_details_Description);
        imageView = (ImageView) findViewById(R.id.product_details_image);

        item_id=getIntent().getStringExtra("item_ID");
        item_name=getIntent().getStringExtra("item_name");
        item_description=getIntent().getStringExtra("item_description");
        item_code=getIntent().getStringExtra("item_code");
        item_price=getIntent().getStringExtra("item_price");
        item_image=getIntent().getStringExtra("item_image");
        seller_id=getIntent().getStringExtra("user_ID");
        store_type=getIntent().getStringExtra("store_type");
        Store_name=getIntent().getStringExtra("Store_name");
        seller_phone=getIntent().getStringExtra("phone");
        phone.setText("Phone: "+ seller_phone);
        product_name.setText(item_name);
        Store_namee.setText(Store_name);
        price.setText(item_price);
        description.setText(item_description);
        helper.DisplayComment(item_id, new FireBaseClientLocationHelper.DisplayCommentListiner() {
            @Override
            public void onDataFound(ArrayList<Comment> comments) {
             adapter=new CommentsAdapter(comments,"",Customer_ProductDetails.this);
             listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String message) {

            }
        });
        try {
            Picasso.get().load(item_image).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


        add_review=(Button)findViewById(R.id.Add_review);
        add_review.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Customer_ProductDetails.this,Rating.class);
                intent.putExtra("item_ID",item_id);
                startActivity(intent);
            }
        });

        btn=(Button)findViewById(R.id.btn);
        final ImageButton down = (ImageButton) findViewById(R.id.down_btn);
        down.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              if(num>0)
              num=num-1;
                btn.setText(num+"");
            }
        });

        final ImageButton up = (ImageButton) findViewById(R.id.up_btn);
        up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             num=num+1;
                btn.setText(num+"");
            }
        });

        total_price=Integer.parseInt(item_price);
      //  total_pricee=total_price*num;
        final Button AddOrder = findViewById(R.id.Add_to_cart);
        AddOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             //   Toast.makeText(Customer_ProductDetails.this, "price"+total_price+"pricee"+num, Toast.LENGTH_SHORT).show();
                if(sessionManager.isLoggedIn()){

                helper = new FireBaseClientLocationHelper(Customer_ProductDetails.this);
                helper.addOrder(
                        customer,
                        product_name.getText().toString(),
                        num+"",
                        (total_price*num)+"",
                        User_id,
                        "",
                        store_type,
                        "old",
                        item_image

                );}
                else{
                    Toast.makeText(Customer_ProductDetails.this, "create account to enable this", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(Customer_ProductDetails.this,Customer_Sign.class);
                    startActivity(intent);
                }

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(Customer_ProductDetails.this,Customer_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

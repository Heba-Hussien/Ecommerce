package com.example.montej.Activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;

import com.example.montej.SessionManager;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;



public class Family_AddProduct extends AppCompatActivity {
    private String TAG;
    Toolbar mToolbar;
    ImageView imageView;
    EditText product_name, product_code, price, description;
    FireBaseClientLocationHelper helper;
    private Uri filePath=null;
    private int PICK_IMAGE_REQUEST;
    FirebaseStorage storage;
    StorageReference storageReference;
    String downloadImg;
    String Store_type,User_id,Store_name;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        mToolbar = findViewById(R.id.add_product_toolbar);
        imageView = (ImageButton) findViewById(R.id.add_product_imageView);
        PICK_IMAGE_REQUEST = 71;
        TAG = getClass().getSimpleName();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        product_name = (EditText) findViewById(R.id.product_name);
        product_code = (EditText) findViewById(R.id.product_code);
        price = (EditText) findViewById(R.id.product_price);
        description = (EditText) findViewById(R.id.product_description);
        sessionManager=new SessionManager(this);

        Store_type=sessionManager.getStore_type();
        User_id=sessionManager.getUserId();
        Store_name=sessionManager.getStore_name();
//        User_id=getIntent().getStringExtra("User_id");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });

        final Button add_product = (Button) findViewById(R.id.product_add);
        add_product.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {

                if (filePath==null){

                    Toast.makeText(Family_AddProduct.this, "Select product image", Toast.LENGTH_SHORT).show();
                }
                    uploadImage();





            }
        });
    }

    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            Picasso.get().load(filePath).into(imageView);

        }
    }

    private void uploadImage() {

        if (filePath != null) {


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading please wait...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            Task<UploadTask.TaskSnapshot> uploadTask = ref.putFile(filePath);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                       // Toast.makeText(Family_AddProduct.this, "onComplete=: " + downloadUri.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        downloadImg=downloadUri.toString();
                       if(validate()==false){}
                       else {
                        helper = new FireBaseClientLocationHelper(Family_AddProduct.this);
                        helper.addProduct(product_name.getText().toString(),
                                product_code.getText().toString(),
                                price.getText().toString(),
                                description.getText().toString(),
                                downloadImg,
                                Store_type,
                                User_id,
                                Store_name
                        );}


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Family_AddProduct.this, "onFailed=: ", Toast.LENGTH_LONG).show();
                        // Handle failures
                        // ...
                    }
                }
            });
    }
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

                Intent intent = new Intent(Family_AddProduct.this, Family_Home.class);
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
        String product_codeTxt = product_code.getText().toString();
        String product_priceTxt = price.getText().toString();

        if (product_nameTxt.isEmpty() || product_nameTxt.length() < 3) {
            product_name.setError("at least 3 characters");
            valid = false;
        } else {
            product_name.setError(null);
        }
        if (product_codeTxt.isEmpty()) {
            product_code.setError("Enter product code");
            valid = false;
        } else {
            product_code.setError(null);
        }

        if (product_priceTxt.isEmpty()) {
            price.setError("Enter product price");
            valid = false;
        } else {
            price.setError(null);
        }
        if (downloadImg==null){
            valid = false;
            Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

}

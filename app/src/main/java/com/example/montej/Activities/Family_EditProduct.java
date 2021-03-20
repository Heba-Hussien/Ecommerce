package com.example.montej.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.UUID;

public class Family_EditProduct extends AppCompatActivity {
    String item_id,item_name,item_description,item_price,item_code,item_image ,seller_id,store_type;
    Toolbar mToolbar;
    ImageView imageView,edit_img;
    EditText product_name, product_code, price, description;
    FireBaseClientLocationHelper helper;
SessionManager sessionManager;
    private Uri filePath=null;
    private int PICK_IMAGE_REQUEST;
    FirebaseStorage storage;
    StorageReference storageReference;
    String downloadImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        mToolbar = (Toolbar) findViewById(R.id.edit_product_toolbar);
        PICK_IMAGE_REQUEST = 71;
        storage = FirebaseStorage.getInstance();
        sessionManager=new SessionManager(this);
        storageReference = storage.getReference();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        product_name = (EditText) findViewById(R.id.edit_product_name);
        product_code = (EditText) findViewById(R.id.edit_product_code);
        price = (EditText) findViewById(R.id.edit_product_price);
        description = (EditText) findViewById(R.id.edit_product_description);
        imageView = (ImageView) findViewById(R.id.edit_product_image);
        edit_img = (ImageView) findViewById(R.id.edit_image_view);


        item_id=getIntent().getStringExtra("item_ID");
        item_name=getIntent().getStringExtra("item_name");
        item_description=getIntent().getStringExtra("item_description");
        item_code=getIntent().getStringExtra("item_code");
        item_price=getIntent().getStringExtra("item_price");
        item_image=getIntent().getStringExtra("item_image");
        seller_id=getIntent().getStringExtra("user_ID");
        store_type=getIntent().getStringExtra("store_type");

        product_name.setText(item_name);
        product_code.setText(item_code);
        price.setText(item_price);
        description.setText(item_description);
        try {
            Picasso.get().load(item_image).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


        edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

            final Button edit_product = (Button) findViewById(R.id.save_edit_btn);
        edit_product.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                if (filePath==null){
                    helper = new FireBaseClientLocationHelper(Family_EditProduct.this);
                    helper.updateProduct(item_id,product_name.getText().toString(),product_code.getText().toString(),price.getText().toString(),description.getText().toString(),item_image);

                }
                else {
                    uploadImage();

                }
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

                Intent intent = new Intent(Family_EditProduct.this, Family_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

                                helper = new FireBaseClientLocationHelper(Family_EditProduct.this);
                                helper.deleteImage(item_image);
                                helper.updateProduct(item_id,product_name.getText().toString(),product_code.getText().toString(),price.getText().toString(),description.getText().toString(),downloadImg);


                        }


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Family_EditProduct.this, "onFailed=: ", Toast.LENGTH_LONG).show();
                        // Handle failures
                        // ...
                    }
                }
            });
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

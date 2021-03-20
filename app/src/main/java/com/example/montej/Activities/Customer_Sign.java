package com.example.montej.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;

public class Customer_Sign extends AppCompatActivity {
    EditText customer_name,phone,email,user_name,password,address;
    FireBaseClientLocationHelper helper;
    private String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign);

        customer_name=(EditText)findViewById(R.id.customer_name);
        phone=(EditText)findViewById(R.id.customer_phone);
        email=(EditText)findViewById(R.id.customer_email);
        user_name=(EditText)findViewById(R.id.customer_user_name);
        password=(EditText)findViewById(R.id.customer_password);
        address=(EditText)findViewById(R.id.customer_address);
        helper = new FireBaseClientLocationHelper(Customer_Sign.this);
        final Button customer_home = (Button) findViewById(R.id.customer_sign_in);
        customer_home .setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {

                helper.isExist(user_name.getText().toString(), new FireBaseClientLocationHelper.isExist() {
                    @Override
                    public void onDataFound(String exist) {
                        //  Toast.makeText(Family_Sign.this, exist+"", Toast.LENGTH_SHORT).show();
                        if (!validate() ||exist.equals("exist")) {}
                        //   Toast.makeText(Family_Sign.this, validate()+"" +invalidStore + exist, Toast.LENGTH_SHORT).show(); }
                        else {

                            helper.addCustomer(
                                    customer_name.getText().toString(),
                                    "",
                                    email.getText().toString(),
                                    phone.getText().toString(),
                                    user_name.getText().toString(),
                                    password.getText().toString(),
                                    address.getText().toString(),
                                    ""
                            );
                            Intent intent=new Intent(Customer_Sign.this,Login.class);
                            startActivity(intent);
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });

        }
        });


    }
    public boolean validate() {
        boolean valid = true;
        String user_nameTxt = user_name.getText().toString();
        String nameTxt = customer_name.getText().toString();
        String passwordTxt = password.getText().toString();
        String phoneTxt = phone.getText().toString();
        String addressTxt = address.getText().toString();
        String emailTxt = email.getText().toString();

        if (user_nameTxt.isEmpty() ||user_nameTxt.length() < 3) {
            user_name.setError("at least 3 characters");
            valid = false;
        } else {
            user_name.setError(null);
        }


        if (nameTxt.isEmpty() ) {
            customer_name.setError("Enter customer name");
            valid = false;
        } else {
            customer_name.setError(null);
        }

        if (passwordTxt.isEmpty()) {
            password.setError("Enter password");
            valid = false;
        } else {
            password.setError(null);
        }

        if (isValidPhone(phoneTxt)==false){
            phone.setError("Invalid phone number");
            valid=false;
        }
        if (addressTxt.isEmpty() ) {
            address.setError("Insert your address please");
            valid = false;
        } else {
            address.setError(null);
        }

        if (isValidEmail(emailTxt)==false){
            email.setError("Invalid email");
            valid=false;
        }
        return valid;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }
}

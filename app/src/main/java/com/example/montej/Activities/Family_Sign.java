package com.example.montej.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.model.User;

public class Family_Sign extends AppCompatActivity {
    Spinner stores_spinner;
    EditText Family_name,Store_name,phone,email,user_name,password;
    boolean invalidStore;
public static boolean test;
String store_type,x;
    FireBaseClientLocationHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_sign);

        Family_name=(EditText)findViewById(R.id.family_name);
        Store_name=(EditText)findViewById(R.id.stor_name);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        user_name=(EditText)findViewById(R.id.user_name);
        password=(EditText)findViewById(R.id.password);
        invalidStore=true;
        test=true;
        x="ffff";

        stores_spinner = (Spinner) findViewById(R.id.stor_type_spin);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Stores));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stores_spinner.setAdapter(adapter1);
        stores_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 store_type = stores_spinner.getSelectedItem().toString();
                stores_spinner.getItemIdAtPosition((i));
            }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
               invalidStore=false;
        }
    });


        helper = new FireBaseClientLocationHelper(Family_Sign.this);
        final Button family_home =  findViewById(R.id.family_sign_in);

        family_home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                helper.isExist(user_name.getText().toString(), new FireBaseClientLocationHelper.isExist() {
                    @Override
                    public void onDataFound(String exist) {
                      //  Toast.makeText(Family_Sign.this, exist+"", Toast.LENGTH_SHORT).show();
                        if (!validate() ||!invalidStore ||exist.equals("exist")) {}
                   //   Toast.makeText(Family_Sign.this, validate()+"" +invalidStore + exist, Toast.LENGTH_SHORT).show(); }
                else {

                        helper.addSeller(Family_name.getText().toString(),
                            Store_name.getText().toString(),
                            email.getText().toString(),
                            phone.getText().toString(),
                            user_name.getText().toString(),
                            password.getText().toString(),
                            "",
                            store_type

                    );
                    Intent i = new Intent(Family_Sign.this, Login.class);
                     startActivity(i);
                     finish();
                }

                    }

                    @Override
                    public void onFailure(String message) {

                    }
                }); }
        });


    }

    public boolean validate() {
        boolean valid = true;
        String user_nameTxt = user_name.getText().toString();
        String nameTxt = Family_name.getText().toString();
        String passwordTxt = password.getText().toString();
        String phoneTxt = phone.getText().toString();
        String emailTxt = email.getText().toString();

        if (user_nameTxt.isEmpty() ||user_nameTxt.length() < 3) {
            user_name.setError("at least 3 characters");
            valid = false;
        } else {
            user_name.setError(null);
        }


        if (nameTxt.isEmpty() ) {
            Family_name.setError("Enter customer name");
            valid = false;
        } else {
            Family_name.setError(null);
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


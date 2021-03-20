package com.example.montej.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.SessionManager;
import com.example.montej.model.User;

public class Login extends AppCompatActivity {
EditText user_name,password;
FireBaseClientLocationHelper helper;
SessionManager sessionManager;
String txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_name=(EditText)findViewById(R.id.login_user_name);
        password=(EditText)findViewById(R.id.login_password);
        sessionManager=new SessionManager(this);
        final TextView create_account  = (TextView) findViewById(R.id.create_account);
        create_account.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Sign.class);
                startActivity(i);
            }
        });

        final Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                helper = new FireBaseClientLocationHelper(Login.this);
                helper.Login(user_name.getText().toString(), password.getText().toString(), new FireBaseClientLocationHelper.LoginListener() {
                    @Override
                    public void onDataFound(User user) {
                        //Toast.makeText(Login.this, "USER EXIST xD xD", Toast.LENGTH_LONG).show();
                        txt=user.getUser_type();
                        if (txt.equals("1")){

                            sessionManager.setUserId(user.getUser_id());
                            sessionManager.setUserType(user.getUser_type());
                            sessionManager.setUser_name(user.getUser_name());
                            sessionManager.setUserId(user.getUser_id());
                            sessionManager.setFullName(user.getName());
                            sessionManager.setMobile(user.getPhone());
                            sessionManager.setAddres(user.getAddress());
                            sessionManager.setStore_name(user.getStore_name());
                            sessionManager.setStore_type(user.getStore_type());
                            sessionManager.setUserEmail(user.getEmail());
                            sessionManager.LoginSession();

                            Intent i = new Intent(Login.this, Customer_Home.class);
//                            i.putExtra("User_id", user.getUser_id().toString());
                            startActivity(i);
                            finish();
                        }
                        else{

                            sessionManager.setUserId(user.getUser_id());
                            sessionManager.setUserType(user.getUser_type());
                            sessionManager.setUser_name(user.getUser_name());
                            sessionManager.setUserId(user.getUser_id());
                            sessionManager.setFullName(user.getName());
                            sessionManager.setMobile(user.getPhone());
                            sessionManager.setAddres(user.getAddress());
                            sessionManager.setStore_name(user.getStore_name());
                            sessionManager.setStore_type(user.getStore_type());
                            sessionManager.setUserEmail(user.getEmail());
                            sessionManager.LoginSession();
                            Intent i = new Intent(Login.this, Family_Home.class);
//                            i.putExtra("User_id", user.getUser_id());
//                            i.putExtra("Store_type", user.getStore_type());
//                            i.putExtra("Seller_name",user.getStore_name());
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}

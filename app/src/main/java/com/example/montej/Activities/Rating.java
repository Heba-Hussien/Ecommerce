package com.example.montej.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.SessionManager;

import static java.lang.Float.valueOf;

public class Rating extends AppCompatActivity {
    private RatingBar ratingBar;
    FireBaseClientLocationHelper helper;
    SessionManager sessionManager;
    String item_id;
    EditText commentTxt;
    Button btnSubmit;
    private String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        sessionManager=new SessionManager(this);
        helper=new FireBaseClientLocationHelper(this);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar1);
        commentTxt=findViewById(R.id.comment_txt);
        item_id=getIntent().getStringExtra("item_ID");
        btnSubmit=(Button)findViewById(R.id.rating_btn);
        rating=String.valueOf(ratingBar.getRating());
        //Performing action on Button Click

        btnSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast

                if(commentTxt.getText().toString().equals("")||String.valueOf(ratingBar.getRating()).equals("0.0"))
                {
                    Toast.makeText(Rating.this, "Add comment and rate", Toast.LENGTH_SHORT).show();
                }else{
                helper.addComment(sessionManager.getUserId(),
                        commentTxt.getText().toString(),
                        item_id,
                        String.valueOf(ratingBar.getRating()),
                        sessionManager.getFullName()

                        );


//                Toast.makeText(getApplicationContext(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();
            }}

        });
    }}

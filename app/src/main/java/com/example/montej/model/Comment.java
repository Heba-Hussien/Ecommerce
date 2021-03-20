package com.example.montej.model;

import android.widget.ScrollView;

public class Comment {
    String user_name,massage,comment_id,product_id,customer_id;
    String  rate;

    public Comment(String user_name, String massage,String rate,String comment_id,String product_id,String customer_id) {
        this.user_name = user_name;
        this.massage = massage;
        this.rate=rate;
        this.comment_id=comment_id;
        this.product_id=product_id;
        this.customer_id=customer_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;

    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}

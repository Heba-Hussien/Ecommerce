package com.example.montej.model;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;


public class product {
    String product_name;
    String product_code;
    String product_description;
    String  product_price,store_type,seller_id,product_id;
    String img,Store_name;

    public product(String product_name, String product_code, String product_description, String product_price, String store_type, String seller_id, String product_id, String img,String Store_name) {
        this.product_name = product_name;
        this.product_code = product_code;
        this.product_description = product_description;
        this.product_price = product_price;
        this.store_type = store_type;
        this.seller_id = seller_id;
        this.product_id = product_id;
        this.img = img;
        this.Store_name=Store_name;
    }

    public product(String img, String product_name) {
        this.img = img;
        this.product_name = product_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public   String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(  String product_price) {
        this.product_price = product_price;
    }

    public String  getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStore_type() {
        return store_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setStore_type(String store_type) {
        this.store_type = store_type;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getStore_name() {
        return Store_name;
    }

    public void setStore_name(String store_name) {
        Store_name = store_name;
    }
}

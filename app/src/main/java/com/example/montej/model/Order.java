package com.example.montej.model;

public class Order {
    String customer_name,product_name, Quantity ,price,customer_id,seller_id,store_type,order_id,product_stutes,img;

    public Order(String customer_name, String product_name, String quantity, String price, String customer_id, String seller_id, String store_type,String order_id,String product_stutes,String img) {
        this.customer_name = customer_name;
        this.product_name = product_name;
        Quantity = quantity;
        this.price = price;
        this.customer_id = customer_id;
        this.seller_id = seller_id;
        this.store_type = store_type;
        this.order_id=order_id;
        this.product_stutes=product_stutes;
        this.img=img;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;

    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getStore_type() {
        return store_type;
    }

    public void setStore_type(String store_type) {
        this.store_type = store_type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_stutes() {
        return product_stutes;
    }

    public void setProduct_stutes(String product_stutes) {
        this.product_stutes = product_stutes;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

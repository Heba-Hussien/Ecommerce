package com.example.montej.model;

public class Offer {
    String offer_id,Seller_id,customer_id,price,Order_id, store_name,product_name;

    public Offer(String offer_id, String seller_id, String customer_id, String price, String order_id, String seller_name, String product_name) {
        this.offer_id = offer_id;
        Seller_id = seller_id;
        this.customer_id = customer_id;
        this.price = price;
        Order_id = order_id;
        this.store_name = seller_name;
        this.product_name = product_name;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getSeller_id() {
        return Seller_id;
    }

    public void setSeller_id(String seller_id) {
        Seller_id = seller_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}

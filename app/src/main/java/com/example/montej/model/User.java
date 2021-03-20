package com.example.montej.model;

public class User {
    String Name,Store_name,Email,Phone,User_name,Password,Store_type,Address,User_type,User_id;

    public User(String name, String store_name, String email, String phone, String user_name, String password, String store_type, String address, String user_type,String user_id) {
        Name = name;
        Store_name = store_name;
        Email = email;
        Phone = phone;
        User_name = user_name;
        Password = password;
        Store_type = store_type;
        Address = address;
        User_type = user_type;
        User_id=user_id;
    }

    public User() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStore_name() {
        return Store_name;
    }

    public void setStore_name(String store_name) {
        Store_name = store_name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getStore_type() {
        return Store_type;
    }

    public void setStore_type(String store_type) {
        Store_type = store_type;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getUser_type() {
        return User_type;
    }

    public void setUser_type(String user_type) {
        User_type = user_type;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }
}

package com.example.montej;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.montej.Activities.Family_Sign;
import com.example.montej.model.Comment;
import com.example.montej.model.Offer;
import com.example.montej.model.Order;
import com.example.montej.model.User;
import com.example.montej.model.product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class FireBaseClientLocationHelper {
    private String TAG = getClass().getSimpleName();
    SessionManager sessionManager;
    private FirebaseFirestore fireStoreDB;
    Context context;
    ArrayList<product> dataModels = new ArrayList<product>();
    ArrayList<Order> orderlist = new ArrayList<Order>();
    ArrayList<Offer> offerlist = new ArrayList<Offer>();
    ArrayList<Comment> commentlist = new ArrayList<>();

    public FireBaseClientLocationHelper(Context cx) {
        this.context = cx;
        fireStoreDB = FirebaseFirestore.getInstance();
        sessionManager = new SessionManager(context);

    }

    public interface isExist {

        void onDataFound(String exist);

        void onFailure(String message);
    }

    public void isExist(final String User_name, final isExist listener) {
        final boolean[] noDataFound = {false};
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        fireStoreDB.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            String Text = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (User_name.equals(Objects.requireNonNull(document.get("User_name")).toString())) {
                                    Text = "exist";
                                    Toast.makeText(context, "this username already exist", Toast.LENGTH_SHORT).show();


                                    noDataFound[0] = false;


                                } else {
                                    Log.d(TAG, document.getId() + " => " + document.get("User_name") + ">>>>" + document.get("Password"));
                                    noDataFound[0] = true;
                                }
                            }
                            listener.onDataFound(Text);
                            progressDialog.dismiss();
                            if (noDataFound[0]) {
                                listener.onFailure("No Data Found, Check your user name and password");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            listener.onFailure("Error getting documents.");
                        }
                    }
                });

    }
    //////////////////////////////////////////
    public interface seller_phone {

        void onDataFound(String exist);

        void onFailure(String message);
    }

    public void seller_phoneById(final String seller_id, final seller_phone listener) {
        final boolean[] noDataFound = {false};
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        fireStoreDB.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            String Text = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (seller_id.equals(Objects.requireNonNull(document.getId()).toString())) {
                                    Text = (String) Objects.requireNonNull(document.get("Phone"));
                                 //   Toast.makeText(context, Text, Toast.LENGTH_SHORT).show();
                                    noDataFound[0] = false;

                                } else {
                                    Log.d(TAG, document.getId() + " => " + document.get("User_name") + ">>>>" + document.get("Password"));
                                    noDataFound[0] = true;
                                }
                            }
                            listener.onDataFound(Text);
                            progressDialog.dismiss();
                            if (noDataFound[0]) {
                                listener.onFailure("No Data Found, Check your user name and password");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            listener.onFailure("Error getting documents.");
                        }
                    }
                });

    }

    public void addCustomer(String Name, String Store_name, String Email, String Phone, String User_name, String Password, String Address, String Store_type) {
        Map<String, Object> User = new HashMap<>();
        User.put("Name", Name);
        User.put("Store_name", Store_name);
        User.put("Phone", Phone);
        User.put("Email", Email);
        User.put("User_name", User_name);
        User.put("Password", Password);
        User.put("Address", Address);
        User.put("Store_type", Store_type);
        User.put("User_type", 1);

        fireStoreDB.collection("Users")
                .add(User)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(context, "saved Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(context, "check your intent connection", Toast.LENGTH_LONG).show();
                    }
                });

    }

    public void addSeller(String Name, String Store_name, String Email, String Phone, String User_name, String Password, String Address, String Store_type) {
        Map<String, Object> User = new HashMap<>();
        User.put("Name", Name);
        User.put("Store_name", Store_name);
        User.put("Phone", Phone);
        User.put("Email", Email);
        User.put("User_name", User_name);
        User.put("Password", Password);
        User.put("Address", Address);
        User.put("Store_type", Store_type);
        User.put("User_type", 2);

        fireStoreDB.collection("Users")
                .add(User)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(context, "saved Successfully", Toast.LENGTH_LONG).show();
                        //Toast.makeText(Customer_Sign.this, "Sucesss", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(context, "check your intent connection", Toast.LENGTH_LONG).show();

                        //  Toast.makeText(Customer_Sign.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                });

    }

    public void addProduct(String product_name, String product_code, String product_price, String product_description, String img, String store_type, String seller_id, String Store_name) {

        Map<String, Object> Product = new HashMap<>();
        Product.put("product_name", product_name);
        Product.put("product_code", product_code);
        Product.put("product_price", product_price);
        Product.put("product_description", product_description);
        Product.put("product_image", img);
        Product.put("Store_type", store_type);
        Product.put("seller_id", seller_id);
        Product.put("Store_name", Store_name);


        fireStoreDB.collection("Products")
                .add(Product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(context, "saved Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(context, "check your intent connection", Toast.LENGTH_LONG).show();

                        //  Toast.makeText(Customer_Sign.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                });

    }

    public void Acceptoffer(String item_id, String Seller_id, String price) {
        fireStoreDB
                .collection("Orders")
                .document(item_id)
                .update("order_price", price,
                        "seller_id", Seller_id)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(context, "done successfully ", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(context, "check your intent connection", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void addOrder(String customer_name, String product_name, String quantity, String price, String customer_id, String seller_id, String store_type, String product_stutes, String img) {

        Map<String, Object> order = new HashMap<>();
        order.put("customer_name", customer_name);
        order.put("product_name", product_name);
        order.put("order_price", price);
        order.put("product_quantity", quantity);
        order.put("customer_id", customer_id);
        order.put("Store_type", store_type);
        order.put("seller_id", seller_id);
        order.put("product_stutes", product_stutes);
        order.put("img", img);

        fireStoreDB.collection("Orders")
                .add(order)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(context, "done Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(context, "check your intent connection", Toast.LENGTH_LONG).show();

                        //  Toast.makeText(Customer_Sign.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                });

    }

    public void updateProduct(String itemId, String product_name, String product_code, String product_price, String product_description, String img) {
        fireStoreDB
                .collection("Products")
                .document(itemId)
                .update("product_name", product_name,
                        "product_code", product_code,
                        "product_price", product_price,
                        "product_description", product_description,
                        "product_image", img)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(context, "successfully updated!", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(context, "check your intent connection", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void addObject(String name, String txt) {

        Map<String, Object> message = new HashMap<>();
        message.put("message_text", txt + "");
        message.put("user_name", name + "");

        fireStoreDB.collection("Messages")
                .add(message)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    public interface GetSellerProductsListener {
        void onDataFound(ArrayList<product> products);

        void onFailure(String message);
    }

    public void GetSellerProducts(final String User_id, final GetSellerProductsListener listener) {
        final boolean[] noDataFound = {false};
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        fireStoreDB.collection("Products")/./ '
    vxcv'/ v b' b/ /  /  // vcv  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////'vb'v'v'bv
        bbbbbbbbbbbbbbbbbbbbbbbbbb..


































































































































        bblpbplbbbbnjghmmkmb ,mn;wqw    Zere L,;VD; V'
    B'V  ? /
            DFD' FVF' BG V"HJH' BJH.KLJBG MHNNM,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,   "
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (User_id.equals(Objects.requireNonNull(document.get("seller_id")).toString())) {

                                    String s = document.getId();
                                    Log.e(TAG, "ID" + " =>>>>>>>>>>>>>>>>>>>>>>>>> " + document.getData());
                                    product product = new product(
                                            Objects.requireNonNull(document.get("product_name")).toString(),
                                            Objects.requireNonNull(document.get("product_code")).toString(),
                                            Objects.requireNonNull(document.get("product_description")).toString(),
                                            Objects.requireNonNull(document.get("product_price")).toString(),
                                            Objects.requireNonNull(document.get("Store_type")).toString(),
                                            Objects.requireNonNull(document.get("seller_id")).toString(),
                                            Objects.requireNonNull(document.getId()),
                                            Objects.requireNonNull(document.get("product_image")).toString(),
                                            Objects.requireNonNull(document.get("Store_name")).toString()

                                    );
                                    dataModels.add(product);
                                    noDataFound[0] = false;


                                } else {
                                    Log.d(TAG, document.getId() + " => " + document.get("User_name") + ">>>>" + document.get("Password"));
                                    noDataFound[0] = true;
                                }
                            }
                            listener.onDataFound(dataModels);
                            progressDialog.dismiss();
                            if (noDataFound[0]) {
                                listener.onFailure("No Data Found, Check your user name and password");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            listener.onFailure("Error getting documents.");
                        }
                    }
                });

    }

    public void GetStoreProducts(final String Store_type, final GetSellerProductsListener listener) {
        final boolean[] noDataFound = {false};
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        fireStoreDB.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (Store_type.equals(Objects.requireNonNull(document.get("Store_type")).toString())) {

                                    String s = document.getId();
                                    Log.e(TAG, "ID" + " =>>>>>>>>>>>>>>>>>>>>>>>>> " + document.getData());
                                    product product = new product(
                                            Objects.requireNonNull(document.get("product_name")).toString(),
                                            Objects.requireNonNull(document.get("product_code")).toString(),
                                            Objects.requireNonNull(document.get("product_description")).toString(),
                                            Objects.requireNonNull(document.get("product_price")).toString(),
                                            Objects.requireNonNull(document.get("Store_type")).toString(),
                                            Objects.requireNonNull(document.get("seller_id")).toString(),
                                            Objects.requireNonNull(document.getId()),
                                            Objects.requireNonNull(document.get("product_image")).toString(),
                                            Objects.requireNonNull(document.get("Store_name")).toString()

                                    );
                                    dataModels.add(product);
                                    noDataFound[0] = false;


                                } else {
                                    Log.d(TAG, document.getId() + " => " + document.get("User_name") + ">>>>" + document.get("Password"));
                                    noDataFound[0] = true;
                                }
                            }
                            listener.onDataFound(dataModels);
                            progressDialog.dismiss();
                            if (noDataFound[0]) {
                                listener.onFailure("No Data Found, Check your user name and password");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            listener.onFailure("Error getting documents.");
                        }
                    }
                });

    }

    public void GetAllProducts(final GetSellerProductsListener listener) {
        final boolean[] noDataFound = {false};
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        fireStoreDB.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                String s = document.getId();
                                Log.e(TAG, "ID" + " =>>>>>>>>>>>>>>>>>>>>>>>>> " + document.getData());
                                product product = new product(
                                        Objects.requireNonNull(document.get("product_name")).toString(),
                                        Objects.requireNonNull(document.get("product_code")).toString(),
                                        Objects.requireNonNull(document.get("product_description")).toString(),
                                        Objects.requireNonNull(document.get("product_price")).toString(),
                                        Objects.requireNonNull(document.get("Store_type")).toString(),
                                        Objects.requireNonNull(document.get("seller_id")).toString(),
                                        Objects.requireNonNull(document.getId()),
                                        Objects.requireNonNull(document.get("product_image")).toString(),
                                        Objects.requireNonNull(document.get("Store_name")).toString()


                                );
                                dataModels.add(product);
                                noDataFound[0] = false;

                            }
                        }
                        listener.onDataFound(dataModels);
                        progressDialog.dismiss();
                        if (noDataFound[0]) {
                            listener.onFailure("No Data Found, Check your user name and password");
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            listener.onFailure("Error getting documents.");
                        }
                    }
                });

    }

    public interface LoginListener {
        void onDataFound(User user);

        void onFailure(String message);
    }

    public void Login(final String user_name, final String password, final LoginListener listener) {
        final boolean[] noDataFound = {false};
        fireStoreDB.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (user_name.equals(Objects.requireNonNull(document.get("User_name")).toString()) && password.equals(Objects.requireNonNull(document.get("Password")).toString())) {

                                    String s = document.getId();
                                    Log.e(TAG, "ID" + " =>>>>>>>>>>>>>>>>>>>>>>>>> " + document.getData());
                                    User user = new User(
                                            Objects.requireNonNull(document.get("Name")).toString(),
                                            Objects.requireNonNull(document.get("Store_name")).toString(),
                                            Objects.requireNonNull(document.get("Email")).toString(),
                                            Objects.requireNonNull(document.get("Phone")).toString(),
                                            Objects.requireNonNull(document.get("User_name")).toString(),
                                            Objects.requireNonNull(document.get("Password")).toString(),
                                            Objects.requireNonNull(document.get("Store_type")).toString(),
                                            Objects.requireNonNull(document.get("Address")).toString(),
                                            Objects.requireNonNull(document.get("User_type")).toString(),
                                            Objects.requireNonNull(document.getId())
                                    );
                                    noDataFound[0] = false;
                                    listener.onDataFound(user);
                                    break;

                                } else {
                                    Log.d(TAG, document.getId() + " => " + document.get("User_name") + ">>>>" + document.get("Password"));
                                    noDataFound[0] = true;
                                }
                            }
                            if (noDataFound[0]) {
                                listener.onFailure("No Data Found, Check your user name and password");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            listener.onFailure("Error getting documents.");
                        }
                    }
                });


    }

    public void deleteItem(String itemId, String collection) {
        fireStoreDB
                .collection(collection)
                .document(itemId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully Deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error Deleting document", e);
                    }
                });
    }

    public void deleteImage(String imgUri) {
        StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(imgUri);
        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                Log.d(TAG, "onSuccess: deleted file");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                Log.d(TAG, "onFailure: did not delete file");
            }
        });

    }


    public interface DisplayOffersListiner {
        void onDataFound(ArrayList<Offer> products);

        void onFailure(String message);
    }

    public void GetUserOffers(final String Seller_id, final DisplayOffersListiner listener) {
        final boolean[] noDataFound = {false};
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        fireStoreDB.collection("Offers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (Seller_id.equals(Objects.requireNonNull(document.get("Customer_id")).toString())) {

                                    String s = document.getId();
                                    Log.e(TAG, "ID" + " =>>>>>>>>>>>>>>>>>>>>>>>>> " + document.getData());
                                    Offer order = new Offer(
                                            Objects.requireNonNull(document.getId()),
                                            Objects.requireNonNull(document.get("Seller_id")).toString(),
                                            Objects.requireNonNull(document.get("Customer_id")).toString(),
                                            Objects.requireNonNull(document.get("Offer_price")).toString(),
                                            Objects.requireNonNull(document.get("Order_id")).toString(),
                                            Objects.requireNonNull(document.get("Store_name")).toString(),
                                            Objects.requireNonNull(document.get("product_name")).toString()
                                    );
                                    offerlist.add(order);
                                    noDataFound[0] = false;


                                } else {
                                    Log.d(TAG, document.getId() + " => " + document.get("User_name") + ">>>>" + document.get("Password"));
                                    noDataFound[0] = true;
                                }
                            }

                            listener.onDataFound(offerlist);
                            progressDialog.dismiss();
                            if (noDataFound[0]) {
                                listener.onFailure("No Data Found, Check your user name and password");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            listener.onFailure("Error getting documents.");
                        }
                    }
                });
    }


    public interface DisplayOrdersListiner {
        void onDataFound(ArrayList<Order> products);

        void onFailure(String message);
    }

    public void DisplayOrders(final String id, final String doc, final DisplayOrdersListiner listener) {
        final boolean[] noDataFound = {false};
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        fireStoreDB.collection("Orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (id.equals(Objects.requireNonNull(document.get(doc)).toString()) &&
                                        (Objects.requireNonNull(document.get("seller_id")).toString()).equals("") && Objects.requireNonNull(document.get("product_stutes")).equals("new")) {

                                    String s = document.getId();
                                    Log.e(TAG, "ID" + " =>>>>>>>>>>>>>>>>>>>>>>>>> " + document.getData());

                                    Order order = new Order(
                                            Objects.requireNonNull(document.get("customer_name")).toString(),
                                            Objects.requireNonNull(document.get("product_name")).toString(),
                                            Objects.requireNonNull(document.get("product_quantity")).toString(),
                                            Objects.requireNonNull(document.get("order_price")).toString(),
                                            Objects.requireNonNull(document.get("customer_id")).toString(),
                                            Objects.requireNonNull(document.get("seller_id")).toString(),
                                            Objects.requireNonNull(document.get("Store_type")).toString(),
                                            Objects.requireNonNull(document.getId()),
                                            Objects.requireNonNull(document.get("product_stutes").toString()),
                                            Objects.requireNonNull(document.get("img").toString())
                                    );
                                    orderlist.add(order);
                                    noDataFound[0] = false;


                                } else {
                                    Log.d(TAG, document.getId() + " => " + document.get("User_name") + ">>>>" + document.get("Password"));
                                    noDataFound[0] = true;
                                }
                            }

                            listener.onDataFound(orderlist);
                            progressDialog.dismiss();
                            if (noDataFound[0]) {
                                listener.onFailure("No Data Found, Check your user name and password");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            listener.onFailure("Error getting documents.");
                        }
                    }
                });

    }


    public interface DisplayCartListiner {
        void onDataFound(ArrayList<Order> products);

        void onFailure(String message);
    }

    public void DisplayCart(final String id, final String doc, final DisplayCartListiner listener) {
        final boolean[] noDataFound = {false};
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        fireStoreDB.collection("Orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.e(TAG, "ID" + " =>>>>>>>>>>>>>>>>>>>>>>>>> " +id);
                        if (task.isSuccessful()) {
//                            task.continueWithTask(new Continuation<QuerySnapshot, Task<List<QuerySnapshot>>>() {
//                                @Override
//                                public Task<List<QuerySnapshot>> then(@NonNull Task<QuerySnapshot> task) {
//                                    List<Task<QuerySnapshot>> tasks = new ArrayList<Task<QuerySnapshot>>();
//                                    for (DocumentSnapshot ds : task.getResult()) {
//                                        tasks.add(ds.getReference().collection("thingstodo").get());
//                                    }
//
//                                    return Tasks.whenAllSuccess(tasks);
//                                }
//                            })
//                                    .addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
//                                            // BTW, `getResult()` will throw an exception if the task fails unless you first check for `task.isSuccessful()`
//                                            List<QuerySnapshot> list = task.getResult();
//                                            for (QuerySnapshot qs : list) {
//                                                for (DocumentSnapshot document : qs) {
//                                                    if ((id.equals(Objects.requireNonNull(document.get(doc)).toString()) && Objects.requireNonNull(document.get("product_stutes")).equals("old")) ||
//                                                            (id.equals(Objects.requireNonNull(document.get(doc)).toString()) && !Objects.requireNonNull(document.get("seller_id")).equals("+"))) {
//
//                                                        String s = document.getId();
//                                                        Log.e(TAG, "ID" + " =>>>>>>>>>>>>>>>>>>>>>>>>> " + document.getData());
//
//                                                        Order order = new Order(
//                                                                Objects.requireNonNull(document.get("customer_name")).toString(),
//                                                                Objects.requireNonNull(document.get("product_name")).toString(),
//                                                                Objects.requireNonNull(document.get("product_quantity")).toString(),
//                                                                Objects.requireNonNull(document.get("order_price")).toString(),
//                                                                Objects.requireNonNull(document.get("customer_id")).toString(),
//                                                                Objects.requireNonNull(document.get("seller_id")).toString(),
//                                                                Objects.requireNonNull(document.get("Store_type")).toString(),
//                                                                Objects.requireNonNull(document.getId()),
//                                                                Objects.requireNonNull(document.get("product_stutes").toString()),
//                                                                Objects.requireNonNull(document.get("img").toString())
//
//                                                        );
//                                                        orderlist.add(order);
//                                                        noDataFound[0] = false;
//
//
//                                                    } else {
//                                                        Log.d(TAG, document.getId() + " => " + document.get("User_name") + ">>>>" + document.get("Password"));
//                                                        noDataFound[0] = true;
//                                                    }
//                                                }
//                                            }
//                                            listener.onDataFound(orderlist);
//                                            progressDialog.dismiss();
//                                            if (noDataFound[0]) {
//                                                listener.onFailure("No Data Found, Check your user name and password");
//                                            }
//                                        }
//                                    });
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if ((id.equals(Objects.requireNonNull(document.get(doc)).toString()) && Objects.requireNonNull(document.get("product_stutes")).equals("old")) ||
                                        (id.equals(Objects.requireNonNull(document.get(doc)).toString()) && !Objects.requireNonNull(document.get("seller_id")).equals(""))) {

                                    String s = document.getId();
                                    Log.e(TAG, "ID" + " =>>>>>>>>>>>>>>>>>>>>>>>>> " + document.getData());

                                    Order order = new Order(
                                            Objects.requireNonNull(document.get("customer_name")).toString(),
                                            Objects.requireNonNull(document.get("product_name")).toString(),
                                            Objects.requireNonNull(document.get("product_quantity")).toString(),
                                            Objects.requireNonNull(document.get("order_price")).toString(),
                                            Objects.requireNonNull(document.get("customer_id")).toString(),
                                            Objects.requireNonNull(document.get("seller_id")).toString(),
                                            Objects.requireNonNull(document.get("Store_type")).toString(),
                                            Objects.requireNonNull(document.getId()),
                                            Objects.requireNonNull(document.get("product_stutes").toString()),
                                            Objects.requireNonNull(document.get("img").toString())

                                    );
                                    orderlist.add(order);
                                    noDataFound[0] = false;


                                } else {
                                    Log.d(TAG, document.getId() + " => " + document.get("User_name") + ">>>>" + document.get("Password"));
                                    noDataFound[0] = true;
                                }
                            }

                            listener.onDataFound(orderlist);
                            progressDialog.dismiss();
                            if (noDataFound[0]) {
                                listener.onFailure("No Data Found, Check your user name and password");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            listener.onFailure("Error getting documents.");
                        }
                    }
                });

    }


    public void addOffer(String Seller_id, String Customer_id, String product_price, String Order_id, String product_name, String Store_name) {

        Map<String, Object> offer = new HashMap<>();
        offer.put("Seller_id", Seller_id);
        offer.put("Customer_id", Customer_id);
        offer.put("Offer_price", product_price);
        offer.put("Order_id", Order_id);
        offer.put("Store_name", Store_name);
        offer.put("product_name", product_name);
        fireStoreDB.collection("Offers")
                .add(offer)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(context, "Sent Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(context, "check your intent connection", Toast.LENGTH_LONG).show();

                        //  Toast.makeText(Customer_Sign.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                });

    }

    public interface DisplayCommentListiner {
        void onDataFound(ArrayList<Comment> comments);

        void onFailure(String message);
    }

    public void DisplayComment(final String id, final DisplayCommentListiner listener) {
        final boolean[] noDataFound = {false};
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        fireStoreDB.collection("Comments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if ((id.equals(Objects.requireNonNull(document.get("product_id")).toString()))) {

                                    String s = document.getId();
                                    Log.e(TAG, "ID" + " =>>>>>>>>>>>>>>>>>>>>>>>>> " + document.getData());

                                    Comment comment = new Comment(
                                            Objects.requireNonNull(document.get("customer_name")).toString(),
                                            Objects.requireNonNull(document.get("comment")).toString(),
                                            Objects.requireNonNull(document.get("rate")).toString(),
                                            Objects.requireNonNull(document.getId()),
                                            Objects.requireNonNull(document.get("product_id")).toString(),
                                            Objects.requireNonNull(document.get("customer_id")).toString()

                                    );
                                    commentlist.add(comment);
                                    noDataFound[0] = false;


                                } else {
                                    Log.d(TAG, document.getId() + " => " + document.get("User_name") + ">>>>" + document.get("Password"));
                                    noDataFound[0] = true;
                                }
                            }

                            listener.onDataFound(commentlist);
                            progressDialog.dismiss();
                            if (noDataFound[0]) {
                                listener.onFailure("No Data Found, Check your user name and password");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            listener.onFailure("Error getting documents.");
                        }
                    }
                });

    }

    public void addComment(String customer_id, String comment, String product_id, String rate, String customer_name) {

        Map<String, Object> Comment = new HashMap<>();
        Comment.put("customer_id", customer_id);
        Comment.put("customer_name", customer_name);
        Comment.put("product_id", product_id);
        Comment.put("comment", comment);
        Comment.put("rate", rate);
        fireStoreDB.collection("Comments")
                .add(Comment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(context, "done Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(context, "check your intent connection", Toast.LENGTH_LONG).show();

                        //  Toast.makeText(Customer_Sign.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                });

    }


}

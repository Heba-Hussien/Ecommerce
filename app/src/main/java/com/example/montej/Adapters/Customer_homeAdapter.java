package com.example.montej.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.montej.Activities.Customer_ProductDetails;
import com.example.montej.Activities.Family_EditProduct;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.model.product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Customer_homeAdapter extends BaseAdapter {

    private ArrayList<product> dataSet = new ArrayList<product>();
    FireBaseClientLocationHelper helper;
    private LayoutInflater inflater = null;
    Context mContext;
    String phone;
    // View lookup cache


    public Customer_homeAdapter(ArrayList<product> dataSet, Context mContext) {
        this.dataSet = dataSet;
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        helper=new FireBaseClientLocationHelper(mContext);
phone="";
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        try {
            final Customer_homeAdapter.ViewHolder viewHolder;


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.images_list_adapter, null);
                viewHolder = new Customer_homeAdapter.ViewHolder();
                viewHolder.info = (ImageView) convertView.findViewById(R.id.cu_product_imageView);
                viewHolder.textView=(TextView)convertView.findViewById(R.id.textview1);
                convertView.setTag(viewHolder);
            }
         else {
            viewHolder = (Customer_homeAdapter.ViewHolder) convertView.getTag(); }
        if (getItem(position) != null) {

            try {
                Picasso.get().load(dataSet.get(position).getImg()).into(viewHolder.info);
            } catch (Exception e) {
                e.printStackTrace();
            }

            viewHolder.info.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here
                    helper.seller_phoneById(dataSet.get(position).getSeller_id(), new FireBaseClientLocationHelper.seller_phone() {
                        @Override
                        public void onDataFound(String exist) {
                         phone=exist;
                        }

                        @Override
                        public void onFailure(String message) {

                        }
                    });
                    if(!phone.equals("")) {
                        Intent intent = new Intent(mContext, Customer_ProductDetails.class);
                        intent.putExtra("phone", phone);
                        intent.putExtra("item_ID", dataSet.get(position).getProduct_id());
                        intent.putExtra("item_name", dataSet.get(position).getProduct_name());
                        intent.putExtra("item_description", dataSet.get(position).getProduct_description());
                        intent.putExtra("item_code", dataSet.get(position).getProduct_code());
                        intent.putExtra("item_price", dataSet.get(position).getProduct_price());
                        intent.putExtra("item_image", dataSet.get(position).getImg());
                        intent.putExtra("user_ID", dataSet.get(position).getSeller_id());
                        intent.putExtra("store_type", dataSet.get(position).getStore_type());
                        intent.putExtra("Store_name", dataSet.get(position).getStore_name());

                        mContext.startActivity(intent);
                    }

                }
            });
        }


        return convertView;
    } catch (Exception e) {
        e.printStackTrace();
        return convertView;
    }
}

    @Override
    public int getCount () {

        return dataSet.size();
    }

    @Override
    public Object getItem ( int i)

    {
        return dataSet.get(i);
    }

    @Override
    public long getItemId ( int i){

        return i;
    }

/////////////////////////////////
private class ViewHolder {
    TextView textView;
    ImageView info;

}
}



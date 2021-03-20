package com.example.montej.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.montej.Activities.Customer_ProductDetails;
import com.example.montej.Activities.Customer_Sign;
import com.example.montej.Activities.Family_EditProduct;
import com.example.montej.Activities.Family_Home;
import com.example.montej.Activities.Sign;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.model.*;
import com.squareup.picasso.Picasso;


import java.net.URL;
import java.util.ArrayList;


public class product_CustomAdapter  extends BaseAdapter {
    private ArrayList<product> dataSet = new ArrayList<product>();
    FireBaseClientLocationHelper helper;
    private LayoutInflater inflater = null;
    private String user_id;
    Context mContext;


    public product_CustomAdapter(ArrayList<product> dataSet, String user_id, Context mContext) {
        this.dataSet = dataSet;
        this.user_id = user_id;
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }





    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        try {
            final ViewHolder viewHolder;


            if (convertView == null) {

                convertView = inflater.inflate(R.layout.product_adapter, null);
                viewHolder = new ViewHolder();
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.product_name_text);
                viewHolder.txtType = (TextView) convertView.findViewById(R.id.product_code_text);
                viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.product_price_text);
                viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.product_description_text);
                viewHolder.info = (ImageView) convertView.findViewById(R.id.product_imageView);
                viewHolder.delete_btn = (Button) convertView.findViewById(R.id.product_delete);
                viewHolder.update_btn = (Button) convertView.findViewById(R.id.product_edit);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (getItem(position) != null) {
                viewHolder.txtName.setText("name: "+dataSet.get(position).getProduct_name());
                viewHolder.txtType.setText("Code: "+dataSet.get(position).getProduct_code());
                viewHolder.txtVersion.setText("Price: "+dataSet.get(position).getProduct_price());
                viewHolder.txtDescription.setText("Description: "+dataSet.get(position).getProduct_description());

                try {
                    Picasso.get().load(dataSet.get(position).getImg()).into(viewHolder.info);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            viewHolder.update_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here
                    Intent intent = new Intent(mContext,Family_EditProduct.class);
                    intent.putExtra("item_ID", dataSet.get(position).getProduct_id());
                    intent.putExtra("item_name", dataSet.get(position).getProduct_name());
                    intent.putExtra("item_description", dataSet.get(position).getProduct_description());
                    intent.putExtra("item_code", dataSet.get(position).getProduct_code());
                    intent.putExtra("item_price", dataSet.get(position).getProduct_price());
                    intent.putExtra("item_image", dataSet.get(position).getImg());
                    intent.putExtra("user_ID", dataSet.get(position).getSeller_id());
                    intent.putExtra("store_type", dataSet.get(position).getStore_type());
                   mContext.startActivity(intent);
                }
            });

            viewHolder.delete_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    helper = new FireBaseClientLocationHelper(mContext);
                    helper.deleteItem(dataSet.get(position).getProduct_id(),"Products");
                    helper.deleteImage(dataSet.get(position).getImg());
                    dataSet.remove(position);
                    notifyDataSetChanged();

                }
            });

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
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        TextView txtDescription;
        Button delete_btn;
        Button update_btn;
        ImageView info;

    }
}
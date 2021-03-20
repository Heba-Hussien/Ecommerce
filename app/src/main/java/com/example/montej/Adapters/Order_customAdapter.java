package com.example.montej.Adapters;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.montej.Activities.AddPrice;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.model.*;

import java.util.ArrayList;

public class Order_customAdapter  extends BaseAdapter {
    private ArrayList<Order> dataSet = new ArrayList<Order>();
    FireBaseClientLocationHelper helper;
    private LayoutInflater inflater = null;
    private String user_id,Store_name;
    Context mContext;
    AlertDialog alertDialog;


    public Order_customAdapter(ArrayList<Order> dataSet,String Store_name, String user_id, Context mContext) {
        this.dataSet = dataSet;
        this.user_id = user_id;
        this.mContext = mContext;
        this.Store_name=Store_name;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        try {
            final Order_customAdapter.ViewHolder viewHolder;


            if (convertView == null) {

                convertView = inflater.inflate(R.layout.order_adapter, parent, false);
                viewHolder = new Order_customAdapter.ViewHolder();
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.customer_name_text);
                viewHolder.txtType = (TextView) convertView.findViewById(R.id.product_name_text);
                viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.product_quan_text);
               // viewHolder.refuse_btn = (Button) convertView.findViewById(R.id.refuse_order);
                viewHolder.accept_btn = (Button) convertView.findViewById(R.id.accept_order);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (Order_customAdapter.ViewHolder) convertView.getTag();
            }
            if (getItem(position) != null) {
                viewHolder.txtName.setText("Customer name: "+dataSet.get(position).getCustomer_name());
                viewHolder.txtType.setText("Product name: "+dataSet.get(position).getProduct_name());
                viewHolder.txtVersion.setText("Quantity: "+dataSet.get(position).getQuantity());

               // Toast.makeText(mContext, dataSet.get(position).getStore_type(), Toast.LENGTH_SHORT).show();

            }

            viewHolder.accept_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent= new Intent(mContext, AddPrice.class);
                    intent.putExtra("Customer_id",dataSet.get(position).getCustomer_id());
                    intent.putExtra("Seller_id",user_id);
                    intent.putExtra("Order_id",dataSet.get(position).getOrder_id());
                    intent.putExtra("Store_type",dataSet.get(position).getStore_type());
                    intent.putExtra("Store_name",Store_name);
                    intent.putExtra("product_name",dataSet.get(position).getProduct_name());
                    mContext.startActivity(intent);

                }
            });

            viewHolder.refuse_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    helper = new FireBaseClientLocationHelper(mContext);

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



    private class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        Button refuse_btn;
        Button accept_btn;

    }


}

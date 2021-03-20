package com.example.montej.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.montej.Activities.AddPrice;
import com.example.montej.Activities.Rating;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.model.Offer;
import com.example.montej.model.Order;

import java.util.ArrayList;

public class Offer_customAdapter extends BaseAdapter {
    private ArrayList<Offer> dataSet = new ArrayList<Offer>();
    FireBaseClientLocationHelper helper;
    private LayoutInflater inflater = null;
    private String user_id;
    Context mContext;
    AlertDialog alertDialog;


    public Offer_customAdapter(ArrayList<Offer> dataSet, String user_id, Context mContext) {
        this.dataSet = dataSet;
        this.user_id = user_id;
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        try {
            final Offer_customAdapter.ViewHolder viewHolder;


            if (convertView == null) {

                convertView = inflater.inflate(R.layout.offer_adapter, parent, false);
                viewHolder = new Offer_customAdapter.ViewHolder();
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.seller_name_text);
                viewHolder.txtType = (TextView) convertView.findViewById(R.id.product_name_text);
                viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.price);
                viewHolder.refuse_btn = (Button) convertView.findViewById(R.id.refuse_offer);
                viewHolder.accept_btn = (Button) convertView.findViewById(R.id.accept_offer);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (Offer_customAdapter.ViewHolder) convertView.getTag();
            }
            if (getItem(position) != null) {
                viewHolder.txtName.setText(dataSet.get(position).getStore_name());
                viewHolder.txtType.setText(dataSet.get(position).getProduct_name());
                viewHolder.txtVersion.setText(dataSet.get(position).getPrice());

                // Toast.makeText(mContext, dataSet.get(position).getStore_type(), Toast.LENGTH_SHORT).show();

            }

            viewHolder.accept_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    helper = new FireBaseClientLocationHelper(mContext);
                    helper.Acceptoffer(dataSet.get(position).getOrder_id(),dataSet.get(position).getSeller_id(),dataSet.get(position).getPrice());
//                    helper.deleteItem(dataSet.get(position).getOffer_id(),"Offers");
//                    dataSet.remove(position);
//                    notifyDataSetChanged();
                }
            });

            viewHolder.refuse_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    helper = new FireBaseClientLocationHelper(mContext);
                    helper.deleteItem(dataSet.get(position).getOffer_id(),"Offers");
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



package com.example.montej.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.model.Order;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    private ArrayList<Order> dataSet = new ArrayList<Order>();
    FireBaseClientLocationHelper helper;
    private LayoutInflater inflater = null;
    private String user_id;
    Context mContext;
    AlertDialog alertDialog;


    public CartAdapter(ArrayList<Order> dataSet, String user_id, Context mContext) {
        this.dataSet = dataSet;
        this.user_id = user_id;
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        try {
            final CartAdapter.ViewHolder viewHolder;


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.cart_adapter, parent, false);
                viewHolder = new CartAdapter.ViewHolder();
              //  LayoutInflater inflater = LayoutInflater.from(getContext());
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.product_name_text);
                viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.order_price);
                viewHolder.txtQuntity = (TextView) convertView.findViewById(R.id.order_quanitiy);
                viewHolder.info = (ImageView) convertView.findViewById(R.id.product_imageView);
                viewHolder.delete_btn=convertView.findViewById(R.id.cart_delete);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (CartAdapter.ViewHolder) convertView.getTag();
            }
            if (getItem(position) != null) {
                viewHolder.txtName.setText("Product name: "+dataSet.get(position).getProduct_name());
                viewHolder.txtPrice.setText(dataSet.get(position).getPrice());
                viewHolder.txtQuntity.setText("Quantity: "+dataSet.get(position).getQuantity());


                try {
                    Picasso.get().load(dataSet.get(position).getImg()).into(viewHolder.info);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            viewHolder.delete_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    helper = new FireBaseClientLocationHelper(mContext);
                    helper.deleteItem(dataSet.get(position).getOrder_id(),"Orders");
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




    private  class ViewHolder {
        TextView txtName;
        TextView txtPrice;
        TextView txtQuntity;
        ImageView info;
        ImageButton delete_btn;

    }
}

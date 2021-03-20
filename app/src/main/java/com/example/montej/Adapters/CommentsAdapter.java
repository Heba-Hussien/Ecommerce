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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.montej.Activities.Family_EditProduct;
import com.example.montej.FireBaseClientLocationHelper;
import com.example.montej.R;
import com.example.montej.model.Comment;
import com.example.montej.model.product;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class CommentsAdapter extends BaseAdapter {
    private ArrayList<Comment> dataSet = new ArrayList<>();
    FireBaseClientLocationHelper helper;
    private LayoutInflater inflater = null;
    private String user_id;
    Context mContext;


    public CommentsAdapter(ArrayList<Comment> dataSet, String user_id, Context mContext) {
        this.dataSet = dataSet;
        this.user_id = user_id;
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        try {
            final CommentsAdapter.ViewHolder viewHolder;


            if (convertView == null) {

                convertView = inflater.inflate(R.layout.comments_adapter, null);
                viewHolder = new CommentsAdapter.ViewHolder();
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.comment_user);
                viewHolder.txtType = (TextView) convertView.findViewById(R.id.comment_content);
                viewHolder.rate =  convertView.findViewById(R.id.ratingBar2);


                convertView.setTag(viewHolder);

            } else {
                viewHolder = (CommentsAdapter.ViewHolder) convertView.getTag();
            }
            if (getItem(position) != null) {
                viewHolder.txtName.setText( dataSet.get(position).getUser_name());
                viewHolder.txtType.setText( dataSet.get(position).getMassage());
                viewHolder.rate.setRating(Float.valueOf(dataSet.get(position).getRate()));
                viewHolder.rate.setIsIndicator(true);


            }


            return convertView;
        } catch (Exception e) {
            e.printStackTrace();
            return convertView;
        }
    }

    @Override
    public int getCount() {

        return dataSet.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSet.get(i);
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    /////////////////////////////////
    private class ViewHolder {
        TextView txtName;
        TextView txtType;
        RatingBar rate;
    }
}
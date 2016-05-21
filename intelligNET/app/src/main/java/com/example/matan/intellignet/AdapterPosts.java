package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by matan on 16/05/2016.
 */
public class AdapterPosts extends ArrayAdapter<TypePost>
{
    private Activity activity;
    private final int mLayout;

    public AdapterPosts(Activity activity, int layout, ArrayList data) {
        super(activity, layout, data);
        this.mLayout = layout;
        this.activity = activity;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolderItem viewHolder;

        if(convertView == null)
        {
            // inflate the layout
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(mLayout, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.name = (TextView) convertView.findViewById(R.id.nameTextView);
            viewHolder.type = (TextView) convertView.findViewById(R.id.typeTextView);
            viewHolder.index = (TextView) convertView.findViewById(R.id.indexTextView);
            viewHolder.date = (TextView) convertView.findViewById(R.id.dateTextView);
            viewHolder.content = (TextView) convertView.findViewById(R.id.contentTextView);

            // store the holder with the view
            convertView.setTag(viewHolder);
        }
        else
        {
            // just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) convertView.getTag();
        }


        TypePost post = getItem(position);


        // assign values if the object is not null
        if(post != null) {
            // get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
            viewHolder.name.setText(post.getName());
            viewHolder.type.setText(post.getType());
            viewHolder.index.setText(post.getIndex());
            viewHolder.date.setText(post.getDate());
            viewHolder.content.setText(post.getContent());
        }


        return convertView;
    }


    public static class ViewHolderItem {

        TextView name;
        TextView type;
        TextView index;
        TextView date;
        TextView content;

    }
}

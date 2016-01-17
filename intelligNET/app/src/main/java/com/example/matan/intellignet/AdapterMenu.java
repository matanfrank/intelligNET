package com.example.matan.intellignet;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * Created by matan on 16/12/2015.
 */
public class AdapterMenu extends ArrayAdapter<TypeMenuCell>
{
    private Activity activity;
    private final int mLayout;


    public AdapterMenu(Activity activity, int layout, ArrayList data) {
        super(activity, layout, data);
        this.mLayout = layout;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(mLayout, parent, false);
        }

        TypeMenuCell menuCell = getItem(position);

        TextView text = (TextView)convertView.findViewById(R.id.menu_text);
        text.setText(menuCell.getTxt());


        ImageView img =(ImageView)convertView.findViewById(R.id.menu_image);
        img.setImageResource(menuCell.getPic());


        return convertView;
    }
}

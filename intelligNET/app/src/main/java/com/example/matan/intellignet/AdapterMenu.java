package com.example.matan.intellignet;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * Created by matan on 16/12/2015.
 */
public class AdapterMenu extends ArrayAdapter<TypeMenuCell>
{
    private Activity activity;
    private final int mLayout;
    private ArrayList data;
    private GridView gridView;

    public AdapterMenu(Activity activity, int layout, ArrayList data, GridView gridView) {
        super(activity, layout, data);
        this.mLayout = layout;
        this.activity = activity;
        this.data = data;
        this.gridView = gridView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(mLayout, parent, false);

            //change the cell size so the main activity menu will fill every screen in any device
            //get the screen size
            DisplayMetrics displaymetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

            int screenHeight = displaymetrics.heightPixels;
            int gridHeight = (int)(screenHeight*0.67); // 0.67 chose bcuz its barely the value of the weight of the grid 28/33
            int numOfRows = data.size()/gridView.getNumColumns();
            int heightOfCell = gridHeight/numOfRows;

            //i want this menu much just in main activity because the rest menus will cover the all screen for sure
            if(activity.getClass() == MainActivity.class)
            {
                    ViewGroup.LayoutParams lp = convertView.getLayoutParams();
                    lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    lp.height = heightOfCell;
                    convertView.requestLayout();
            }
        }
        TypeMenuCell menuCell = getItem(position);

        TextView text = (TextView)convertView.findViewById(R.id.menu_text);
        text.setText(menuCell.getTxt());


        ImageView img =(ImageView)convertView.findViewById(R.id.menu_image);
        img.setImageResource(menuCell.getPic());


        return convertView;
    }
}

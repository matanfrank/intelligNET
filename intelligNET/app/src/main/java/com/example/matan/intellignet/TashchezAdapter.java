package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by matan on 16/12/2015.
 */
public class TashchezAdapter extends ArrayAdapter<TypeTashchezCell>
{
    private Activity activity;
    private int slvLayout;
    private int defLayout;
    private LayoutInflater inflater;

    public TashchezAdapter(Activity activity, int defLayout, int slvLayout, ArrayList data) {
        super(activity, defLayout, data);
        this.activity = activity;
        this.slvLayout = slvLayout;
        this.defLayout = defLayout;
//        inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }


//    @Override
//    public int getItemViewType(int position)
//    {
//        if(getItem(position).cellType == TypeTashchezGrid.DEFINITION)
//             return defLayout;
//        else
//            return slvLayout;
//    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent)
        {
//            ViewHolder viewHolder = null;
//            TypeTashchezCell tashchezCell = getItem(position);
//            int type = getItemViewType(position);
//
//            if(convertView == null)
//            {
//                viewHolder = new ViewHolder();
//                if(type == R.layout.cell_definition_tashchez)
//                {
//                    Log.d("123", position + "def inflate");
//                    convertView = inflater.inflate(defLayout, null);////////maybe null
//                    viewHolder.textView = (TextView)convertView.findViewById(R.id.tashchez_text_view);
//
//                    viewHolder.imageViewDef =(ImageView)convertView.findViewById(R.id.tashchez_image_def);
//                }
//
//                if(type == R.layout.cell_solve_tashchez)
//                {
//                    Log.d("123", position + "slv inflate");
//                    convertView = inflater.inflate(slvLayout, null);////////maybe null
//                    viewHolder.editText = (EditText)convertView.findViewById(R.id.tashchez_edit_text);
//
//                    viewHolder.imageViewSlv =(ImageView)convertView.findViewById(R.id.tashchez_image_solve);
//                }
//                convertView.setTag(viewHolder);
//            }
//            else
//            {
//                viewHolder = (ViewHolder)convertView.getTag();
//            }
//
//
//            if(type == R.layout.cell_definition_tashchez)
//            {
//                viewHolder.imageViewDef.setImageResource(tashchezCell.background);
//                viewHolder.textView.setText(tashchezCell.content);
//            }
//            if(type == R.layout.cell_solve_tashchez)
//                viewHolder.imageViewSlv.setImageResource(tashchezCell.background);
//
//            return convertView;
//


        if(convertView == null && getItem(position).cellType.contains("definition"))
        {
            Log.d("123", position + "def inflate");
           inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(defLayout, parent, false);
        }
        else if(convertView == null)
        {
            Log.d("123", position + "slv inflate");
            inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(slvLayout, parent, false);
        }

        TypeTashchezCell tashchezCell = getItem(position);
        EditText editText;
        TextView textView;
        ImageView imageView;

        if(getItem(position).cellType.contains("definition"))
        {
            Log.d("123", position + "def");
            textView = (TextView)convertView.findViewById(R.id.tashchez_text_view);
            textView.setText(tashchezCell.content);


            imageView =(ImageView)convertView.findViewById(R.id.tashchez_image_def);
            imageView.setImageResource(tashchezCell.background);
        }
        else
        {
            Log.d("123", position + "slv");
            editText = (EditText)convertView.findViewById(R.id.tashchez_edit_text);


            imageView =(ImageView)convertView.findViewById(R.id.tashchez_image_solve);
            imageView.setImageResource(tashchezCell.background);
        }


        return convertView;
    }


//    public static class ViewHolder
//    {
//        public TextView textView;
//        public EditText editText;
//        public ImageView imageViewDef;
//        public ImageView imageViewSlv;
//    }
}

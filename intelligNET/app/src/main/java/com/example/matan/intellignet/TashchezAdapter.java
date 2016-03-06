package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by matan on 16/12/2015.
 */
public class TashchezAdapter extends ArrayAdapter<TypeTashchezCell>
{
    private static final int DEFINITION = 0;
    private static final int SOLVE = 1;
    private int slvLayout;
    private int defLayout;
    private LayoutInflater inflater;
    //private ViewHolder viewHolder;
    private Activity activity;
    private InputMethodManager imm;
    private newEditText editText;
    private TashchezPassEditText tashchezPassEditText;
    private ImageView coverImage;
    public static Activity context;


    public TashchezAdapter(Activity activity, int defLayout, int slvLayout, ArrayList data, TashchezPassEditText tashchezPassEditText) {
        super(activity, slvLayout, data);
        this.slvLayout = slvLayout;
        this.defLayout = defLayout;
        inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //viewHolder = new ViewHolder();
        this.activity = activity;
        this.tashchezPassEditText = tashchezPassEditText;
        coverImage = (ImageView) activity.findViewById(R.id.symbolImage);
        //this.context = this;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getItemViewType(int position)
    {
        if(getItem(position).cellType.contains("definition"))
            return DEFINITION;
        else
            return SOLVE;

    }


//    public static class ViewHolder
//    {
//        public TextView textView;
//        public EditText editText;
//        public ImageView imageViewDef;
//        public ImageView imageViewSlv;
//    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

//            TypeTashchezCell tashchezCell = getItem(position);
//            int type = getItemViewType(position);
//
//            if(convertView == null)
//            {
//                viewHolder = new ViewHolder();
//                if(type == DEFINITION)
//                {
//                    Log.d("1234", position + "def inflate");
//                    convertView = inflater.inflate(defLayout, null);////////maybe null
//                    viewHolder.textView = (TextView)convertView.findViewById(R.id.tashchez_text_view);
//
//                    viewHolder.imageViewDef =(ImageView)convertView.findViewById(R.id.tashchez_image_def);
//                }
//
//                if(type == SOLVE)
//                {
//                    Log.d("1234", position + "slv inflate");
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
//            if(type == DEFINITION)
//            {
//                viewHolder.imageViewDef.setImageResource(tashchezCell.background);
//                viewHolder.textView.setText(tashchezCell.content);
//            }
//            if(type == SOLVE)
//                viewHolder.imageViewSlv.setImageResource(tashchezCell.background);
//
//            return convertView;
        int type = getItemViewType(position);
        if (convertView == null)
        {
            switch (type)
            {
                case DEFINITION:
                    Log.d("1234", position + "def inflate");
                    //inflater = activity.getLayoutInflater();
                    convertView = inflater.inflate(defLayout, parent, false);break;

                case SOLVE:
                    Log.d("1234", position + "slv inflate");
                //    inflater = activity.getLayoutInflater();
                    convertView = inflater.inflate(slvLayout, parent, false);break;
            }
    }


        final TypeTashchezCell tashchezCell = getItem(position);

        TextView textView;
        ImageView imageView;

        switch (type)
        {
            case DEFINITION:
                Log.d("1234", position + "def");
                textView = (TextView)convertView.findViewById(R.id.tashchez_text_view);
                textView.setText(tashchezCell.content);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        coverImage.setVisibility(View.GONE);
                        paintAnswer(tashchezCell);
                        //TashchezAdapter.context.getView(position, convertView, parent);
                    }
                });




                imageView =(ImageView)convertView.findViewById(R.id.tashchez_image_def);
                imageView.setImageResource(tashchezCell.background);break;






            case SOLVE:
                Log.d("1234", position + "slv");
                editText = (newEditText)convertView.findViewById(R.id.tashchez_edit_text);

                tashchezPassEditText.setEditText(editText);

                imageView =(ImageView)convertView.findViewById(R.id.tashchez_image_solve);
                if(!tashchezCell.onEdit)
                    imageView.setImageResource(tashchezCell.background);
                else
                    imageView.setImageResource(R.drawable.ic_solve_sign);

               //when getting into solveMode the coverImage need to disappear when click on solve cell
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {

                            coverImage.setVisibility(View.GONE);
                            TashchezUI.solveMode = true;
                        }
                    }
                });


                break;
        }

        return convertView;
    }




    private void paintAnswer(TypeTashchezCell typeTashchezCell)
    {
        int toPaint = typeTashchezCell.index;

        if(typeTashchezCell.cellType.contains("definition"))
        {
            for(int i=0 ; i<typeTashchezCell.answerNumOfLetter ; i++)
            {
                if(typeTashchezCell.cellType.contains("Down") && typeTashchezCell.cellType.contains("Left") && typeTashchezCell.cellType.endsWith("Left"))
                {
                    if(i==0)
                        toPaint += TashchezUI.NUM_ROW;
                    else
                        toPaint += 1;
                }
                else if(typeTashchezCell.cellType.contains("Left") && typeTashchezCell.cellType.contains("Down") && typeTashchezCell.cellType.endsWith("Down"))
                {
                    if(i==0)
                        toPaint += 1;
                    else
                        toPaint += TashchezUI.NUM_ROW;
                }
                else if(typeTashchezCell.cellType.contains("Up") && typeTashchezCell.cellType.contains("Left")&& typeTashchezCell.cellType.endsWith("Left"))
                {
                    if(i==0)
                        toPaint -= TashchezUI.NUM_ROW;
                    else
                        toPaint += 1;
                }
                else if(typeTashchezCell.cellType.contains("Right") && typeTashchezCell.cellType.contains("Down") && typeTashchezCell.cellType.endsWith("Down"))
                {
                    if(i==0)
                        toPaint -= 1;
                    else
                        toPaint += TashchezUI.NUM_ROW;
                }
                else if(typeTashchezCell.cellType.contains("Left"))
                    toPaint += 1;
                else if(typeTashchezCell.cellType.contains("Down"))
                    toPaint += TashchezUI.NUM_ROW;

                typeTashchezCell.onEdit = true;
            }
        }
    }

}
